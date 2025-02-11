import { getCookie, unselectFeature } from "./utils.js";
import { onFeatureAdded } from "./features.js";
let isLoadMap = false;

/**
 * DEFINE VARIABLES NEEDED ACROSS FUNCTIONS THAT SHOULD BE PASSED DIRECTLY BUT AREN'T
 */

let tempSpeedLimits = [];
let loadType;
let tempLayerID;
let calls =0;
let filesToSend; //2019/04, MF:Added for onTraceChangeRSM()
let trace; //2019/04, MF:Added since it was not declared.
let revisionNum = 0;

/**
 * Purpose: loads file
 * @params  -
 * @event clears map and then opens modal to choose file
 */

function loadFile(map, lanes, vectors, laneMarkers, laneWidths, box, errors, selected) {
	let c = false;
    if (lanes.getSource().getFeatures().length != 0 || laneMarkers.getSource().getFeatures().length != 0 || vectors.getSource().getFeatures().length != 0 || box.getSource().getFeatures().length != 0) {
         c = confirm("Loading a new map will clear all current work. Continue?");
    } else {
        c = true;
    }

	loadType= "load";
	calls = 0;

   if (c === true) {
        lanes.getSource().clear();
        laneMarkers.getSource().clear();
        vectors.getSource().clear();
        box.getSource().clear();
		errors.getSource().clear();
		// deleteTrace();
		laneWidths.getSource().clear();

        let ua = window.navigator.userAgent;
        let msie10 = ua.indexOf('MSIE ');
        let msie11 = ua.indexOf('Trident/');
        let msie12 = ua.indexOf('Edge/');

        if (msie10 > 0 || msie11 > 0 || msie12 > 0) {
            $('#open_file_modal').modal('show');
            $('#fileToLoad2').one('change', (event)=>{
              onChange(event,map, lanes, vectors, laneMarkers, box, laneWidths, selected);
            });
        }
        else {
            $('#fileToLoad').click();
            $('#fileToLoad').one('change',  (event)=>{
              onChange(event,map, lanes, vectors, laneMarkers, box, laneWidths, selected);
            });
        }
   }
}

function onChange(event,map, lanes, vectors, laneMarkers, box, laneWidths, selected) {
	if (calls == 0) {
		let reader = new FileReader();
		reader.onload = (event)=>{
      onReaderLoad(event,map, lanes, vectors, laneMarkers, box, laneWidths, selected)
    };
		reader.readAsText(event.target.files[0]);
	}
	calls++;
}


function onReaderLoad(event,map,lanes, vectors, laneMarkers, box, laneWidths, selected){
	let data = JSON.parse(event.target.result);
	loadMap(data, map, lanes, vectors, laneMarkers, box, laneWidths, selected);
	$('#open_file_modal').modal('hide');
}


/**
 * Purpose: loads map objects from geojson
 * @params  saved object
 * @event rebuilds markers on map
 */
function loadMap( data, map ,lanes, vectors, laneMarkers, box, laneWidths, selected)
{
	let vectorLayerAsOL = new ol.format.GeoJSON().readFeatures(data.vectors,{featureProjection: 'EPSG:3857' , dataProjection: 'EPSG:3857'});
	let lanesLayerAsOL = new ol.format.GeoJSON().readFeatures(data.lanes,{featureProjection: 'EPSG:3857', dataProjection: 'EPSG:3857'});
	let laneMarkersLayerAsOL = new ol.format.GeoJSON().readFeatures(data.laneMarkers,{featureProjection: 'EPSG:3857', dataProjection: 'EPSG:3857'});
  	let stopLayerAsOL = new ol.format.GeoJSON().readFeatures(data.box, {featureProjection: 'EPSG:3857' , dataProjection: 'EPSG:3857'});
	let lanesSource = new ol.source.Vector({
    	features: lanesLayerAsOL,
  	});

	let vectorsSource = new ol.source.Vector({
		features: vectorLayerAsOL,
	});

	let laneMarkersSource = new ol.source.Vector({
    	features: laneMarkersLayerAsOL,
  	});
	let stopBoxSource  = new ol.source.Vector({
		features: stopLayerAsOL
	})

	// console.log(data.vectors)
	// console.log(data.lanes)
	// console.log(data.laneMarkers)
	// console.log(data.box)
	let c = false;
	if ((lanesLayerAsOL.length != 0 || laneMarkersLayerAsOL.length != 0 || stopLayerAsOL.length != 0) && selected == 'parent'){
		c = confirm("You are trying to load a child map using the parent open command. Functionality will be limited to parent controls. Continue?");
	} else {
		c = true;
	}
  
	if (c === true) {
    	vectors.setSource(vectorsSource);
		lanes.setSource(lanesSource);
		laneMarkers.setSource(laneMarkersSource);
		box.setSource(stopBoxSource);

		let feat = vectors.getSource().getFeatures();   
		for (let a = 0; a < feat.length; a++) {
			let iconAddress = feat[a].getProperties().marker.img_src;
			let IconInfo = {src: iconAddress, height: 50, width: 50};
			feat[a].setStyle(new ol.style.Style({
				image: new ol.style.Icon(IconInfo)
			}));
			if (feat[a].getProperties().marker.name == "Reference Point Marker") {
				let intersectionID = feat[a].getProperties().intersectionID;
				let regionID = feat[a].getProperties().regionID ? feat[a].getProperties().regionID : '';
        		console.log("intersection ID" + intersectionID + ", region ID" + regionID)
			}
		};

		let ft = lanes.getSource().getFeatures();    
		for (let i = 0; i < ft.length; i++) {
			if ( typeof ft[i].getProperties().elevation == 'string') {
				let temp = ft[i].getProperties().elevation;
        		// console.log(temp)
				ft[i].getProperties().elevation = [];
				for (let j = 0; j < ft[i].getGeometry().getCoordinates().length; j++) {
          			let latlon = new ol.proj.toLonLat(ft[i].getGeometry().getCoordinates()[j])
					ft[i].getProperties().elevation[j] = ({'value': temp, 'edited': false, 'latlon': latlon});
				}
			}
		}

		try {
			let center = new ol.proj.fromLonLat([feat[0].getProperties().LonLat.lon, feat[0].getProperties().LonLat.lat]);
			let viewZoom = 18;
			if (getCookie("isd_zoom") !== "") {
				viewZoom = getCookie("isd_zoom");
			}
			map.getView().setCenter(center);
			map.getView().setZoom(viewZoom);
			let overlayLayersGroup = new ol.layer.Group({
				title: 'Overlays',
				layers: [vectors]
			  });
			unselectFeature(map, overlayLayersGroup, feat[0]);
		}
		catch (err) {
			console.error(err)
			console.log("No vectors to reset view");
		}

		$("#dragSigns").click();
		$("#dragSigns").click();

		toggleControlsOn('modify', lanes, vectors, laneMarkers, laneWidths, true);
		toggleControlsOn('none', lanes, vectors, laneMarkers, laneWidths, true);
		console.log("loaded map")
	}

}



function loadKMLTrace() {

	var c = confirm("Loading a new KML stencil will clear any other stencil. Continue?");
	if (c === true) {
		if (trace != undefined){
			trace.destroy();
		}

		var ua = window.navigator.userAgent;
		var msie10 = ua.indexOf('MSIE ');
		var msie11 = ua.indexOf('Trident/');
		var msie12 = ua.indexOf('Edge/');

		if (msie10 > 0 || msie11 > 0 || msie12 > 0){
			$('#open_file_modal').modal('show');
			$('#fileToLoad2').one('change', onTraceChange); //Modal uses fileToLoad2
		}
		else {
			$('#kmlToLoad').click();
			$('#kmlToLoad').one('change', onTraceChange);
		}

	}
}

//03/2019 MF: Added new function for RSM
function loadRSMTrace() {

	var c = confirm("Loading a new RSM stencil will clear any other stencil. Continue?");
	if (c === true) {
		if (trace != undefined){
			trace.destroy();
		}

		var ua = window.navigator.userAgent;
		var msie10 = ua.indexOf('MSIE ');
		var msie11 = ua.indexOf('Trident/');
		var msie12 = ua.indexOf('Edge/');

		if (msie10 > 0 || msie11 > 0 || msie12 > 0){
			$('#open_file_modal').modal('show');
			$('#fileToLoad2').one('change', onTraceChangeRSM); //Modal uses fileToLoad2
		}
		else {
			$('#rsmToLoad').click();
			$('#rsmToLoad').one('change', onTraceChangeRSM);
		}

	}
}

function loadUpdateFile(map, lanes, vectors, laneMarkers, laneWidths, box, selected) {
	loadType= "update";
	calls = 0;
	for (let a = 0; a < vectors.getSource().getFeatures().length; a++) {
		let feature = vectors.getSource().getFeatures()[a];
		if (feature.get('marker').name === "Reference Point Marker") {
			tempSpeedLimits = feature.get('speedLimitType');
			tempLayerID = feature.get('layerID');
		}
	}

	var ua = window.navigator.userAgent;
	var msie10 = ua.indexOf('MSIE ');
	var msie11 = ua.indexOf('Trident/');
	var msie12 = ua.indexOf('Edge/');

	if (msie10 > 0 || msie11 > 0 || msie12 > 0) {
		$('#open_file_modal').modal('show');
		$('#fileToLoad2').one('change', (event) => {
			onchange(event, map, lanes, vectors, laneMarkers, box, laneWidths, selected)
		});
	}
	else {
		$('#fileToLoad').click();
		$('#fileToLoad').one('change', (event) => {
			onchange(event, map, lanes, vectors, laneMarkers, box, laneWidths, selected)
		});
	}
}

function toggleControlsOn(state, lanes, vectors, laneMarkers, laneWidths, isLoadMap) {
	if( state == 'help'){
		$("#instructions_modal").modal('show');
	} else {
		$("#instructions_modal").modal('hide');
		toggleControl(state);
		if( state == 'modify' || state == 'del') {
			laneMarkers.getSource().clear();
			// controls.del.unselectAll();
		} else {
			onFeatureAdded(lanes, vectors, laneMarkers, laneWidths, isLoadMap);
		}
    }
}

function toggleControl(element) {
	// for(key in controls) {
	// 		let control = controls[key];
	// 		if(element == key) {
	// 				control.activate();
	// 		} else {
	// 				control.deactivate();
	// 				$('.measurement').text('');
	// 		}
	// }
}


function saveMap(vectors, box, lanes, laneMarkers, selected)
{
		$('#revision_modal').modal('show')
  	let clickCounter = 0;
		$('#revision_modal .btn').click(function(event){
				if(clickCounter++ > 0){
						return;
				}

				if(($('#revision_num').val()).match(/^\d+$/)){
					let vectorsFeatures = vectors.getSource().getFeatures();
					let boxFeatures = box.getSource().getFeatures();
					let lanesFeatures = lanes.getSource().getFeatures();
					let laneMarkersFeatures = laneMarkers.getSource().getFeatures();
					for ( let f = 0; f < vectorsFeatures.length; f++) {
						if (vectorsFeatures[f].get("marker").name == "Reference Point Marker") {
								revisionNum = $('#revision_num').val();
								$('#revision').val(revisionNum);
								vectorsFeatures[f].set("revisionNum", revisionNum);
						}
					}

					let layers = {
							"vectors" : new ol.format.GeoJSON().writeFeatures(vectorsFeatures, true),
							"box" :new ol.format.GeoJSON().writeFeatures(boxFeatures, true),
							"lanes" : new ol.format.GeoJSON().writeFeatures(lanesFeatures, true),
							"laneMarkers" : new ol.format.GeoJSON().writeFeatures(laneMarkersFeatures, true)
						};
						saveFile( layers, vectors, selected );
				} else {
					alert("Must enter a number value.");
					return false;
				}
	});
}


/**
 * Purpose: saves compiled object as geojson file
 * @params  map object
 * @event saves as geojson and loads save menu option
 */

function saveFile( data, vectors, selected )
{
	let textToWrite = JSON.stringify( data );
	let textFileAsBlob = new Blob([textToWrite], {type:'text/plain'});

	let fileNameToSaveAs;
	let referenceExists = false;
	let vectorsFeatures = vectors.getSource().getFeatures();
	let intersectionID = 0;
	for ( let f = 0; f < vectorsFeatures.length; f++) {
		let feature = vectorsFeatures[f];
		if (feature.get("marker").name == "Reference Point Marker") {
			if (feature.get("intersectionID")){
				referenceExists = true;
				intersectionID = feature.get("intersectionID");
			}
		}
	}

	if (!referenceExists){
		alert("Cannot save parent without valid and saved reference point");
		return;
	}

	if (selected == "parent") {
		fileNameToSaveAs = "ISD_" + intersectionID + "_parent_r" + revisionNum + ".geojson"
	}
	if (selected == "child"){
		fileNameToSaveAs = "ISD_" + intersectionID + "_child_r" + revisionNum + ".geojson"
	}

	let downloadLink = document.createElement("a");
	downloadLink.download = fileNameToSaveAs;
	//downloadLink.innerHTML = "Download File";
	if (window.webkitURL != null){
		// Chrome allows the link to be clicked
		// without actually adding it to the DOM.
		downloadLink.href = window.webkitURL.createObjectURL(textFileAsBlob);
	}
	else{
		// Firefox requires the link to be added to the DOM
		// before it can be clicked.
		try {
			downloadLink.href = window.URL.createObjectURL(textFileAsBlob);
			downloadLink.onclick = destroyClickedElement;
			downloadLink.style.display = "none";
			document.body.appendChild(downloadLink);
		}
		catch( e ){
			console.log("error saving firefox file")
		}
		// IE 10+
		try {
			window.navigator.msSaveBlob(textFileAsBlob, fileNameToSaveAs);
		}
		catch(e){
			console.log("error saving IE file")
		}
	}

	try {
		downloadLink.click();
	}
	catch(e) {
		console.log("Unable to click the download link.  Are you using IE?")
	}
        
	if(document.body.contains(downloadLink)){
			document.body.removeChild(downloadLink);
	}	
}

function deleteTrace(){
	if (trace != undefined){
		trace.destroy();
		trace = undefined;
		alert("Stencil Deleted");
	}
	else{
	    alert("No stencil to delete.")
	}
}

export {
    loadFile,
    loadMap,
	saveFile,
	saveMap,
	toggleControlsOn,
	toggleControl,
	revisionNum,
	loadKMLTrace,
	loadRSMTrace,
	loadUpdateFile
}