/**
 * Created by lewisstet on 2/25/2015.
 * Updated 3/2017 by martzth
 */


/**
 * DEFINE GLOBAL VARIABLES
 */

	var GEOJSON_PARSER = new OpenLayers.Format.GeoJSON();
	
	// This is what an the default array for the Directionality Heading Slices circle looks like: no directionality selected(active = false).
	// Old files may be saved with an empty array which leads to the circles not being initialized correctly.
	// If that is the case, initialize with the default of no directionality heading slices.
	var BACKWARDS_COMPATABILITY_FOR_MISSING_CIRLCES = 
		"[{\"theta\": -1.5707963267948966,\"nxtTheta\": -1.1780972450961724,\"x1\": 140,\"x2\": 140,\"x3\": 178.268343236509,\"y1\": 130,\"y2\": 30,\"y3\": 37.61204674887132,\"active\": false}," +
		 "{\"theta\": -1.1780972450961724,\"nxtTheta\": -0.7853981633974483,\"x1\": 140,\"x2\": 178.268343236509,\"x3\": 210.71067811865476,\"y1\": 130,\"y2\": 37.61204674887132,\"y3\": 59.28932188134526,\"active\": false}," +
		 "{\"theta\": -0.7853981633974483,\"nxtTheta\": -0.39269908169872414,\"x1\": 140,\"x2\": 210.71067811865476,\"x3\": 232.3879532511287,\"y1\": 130,\"y2\": 59.28932188134526,\"y3\": 91.73165676349103,\"active\": false}," +
		 "{\"theta\": -0.39269908169872414,\"nxtTheta\": 0,\"x1\": 140,\"x2\": 232.3879532511287,\"x3\": 240,\"y1\": 130,\"y2\": 91.73165676349103,\"y3\": 130,\"active\": false}," +
		 "{\"theta\": 0,\"nxtTheta\": 0.39269908169872414,\"x1\": 140,\"x2\": 240,\"x3\": 232.3879532511287,\"y1\": 130,\"y2\": 130,\"y3\": 168.26834323650897,\"active\": false}," +
		 "{\"theta\": 0.39269908169872414,\"nxtTheta\": 0.7853981633974483,\"x1\": 140,\"x2\": 232.3879532511287,\"x3\": 210.71067811865476,\"y1\": 130,\"y2\": 168.26834323650897,\"y3\": 200.71067811865476,\"active\": false}," +
		 "{\"theta\": 0.7853981633974483,\"nxtTheta\": 1.1780972450961724,\"x1\": 140,\"x2\": 210.71067811865476,\"x3\": 178.268343236509,\"y1\": 130,\"y2\": 200.71067811865476,\"y3\": 222.3879532511287,\"active\": false}," +
		 "{\"theta\": 1.1780972450961724,\"nxtTheta\": 1.5707963267948966,\"x1\": 140,\"x2\": 178.268343236509,\"x3\": 140,\"y1\": 130,\"y2\": 222.3879532511287,\"y3\": 230,\"active\": false}," +
		 "{\"theta\": 1.5707963267948966,\"nxtTheta\": 1.9634954084936207,\"x1\": 140,\"x2\": 140,\"x3\": 101.73165676349103,\"y1\": 130,\"y2\": 230,\"y3\": 222.3879532511287,\"active\": false}," +
		 "{\"theta\": 1.9634954084936207,\"nxtTheta\": 2.356194490192345,\"x1\": 140,\"x2\": 101.73165676349103,\"x3\": 69.28932188134526,\"y1\": 130,\"y2\": 222.3879532511287,\"y3\": 200.71067811865476,\"active\": false}," +
		 "{\"theta\": 2.356194490192345,\"nxtTheta\": 2.748893571891069,\"x1\": 140,\"x2\": 69.28932188134526,\"x3\": 47.61204674887132,\"y1\": 130,\"y2\": 200.71067811865476,\"y3\": 168.268343236509,\"active\": false}," +
		 "{\"theta\": 2.748893571891069,\"nxtTheta\": 3.141592653589793,\"x1\": 140,\"x2\": 47.61204674887132,\"x3\": 40,\"y1\": 130,\"y2\": 168.268343236509,\"y3\": 130,\"active\": false}," +
		 "{\"theta\": 3.141592653589793,\"nxtTheta\": 3.534291735288517,\"x1\": 140,\"x2\": 40,\"x3\": 47.61204674887131,\"y1\": 130,\"y2\": 130,\"y3\": 91.73165676349107,\"active\": false}," +
		 "{\"theta\": 3.534291735288517,\"nxtTheta\": 3.9269908169872414,\"x1\": 140,\"x2\": 47.61204674887131,\"x3\": 69.28932188134523,\"y1\": 130,\"y2\": 91.73165676349107,\"y3\": 59.28932188134526,\"active\": false}," +
		 "{\"theta\": 3.9269908169872414,\"nxtTheta\": 4.319689898685966,\"x1\": 140,\"x2\": 69.28932188134523,\"x3\": 101.73165676349106,\"y1\": 130,\"y2\": 59.28932188134526,\"y3\": 37.61204674887132,\"active\": false}," +
		 "{\"theta\": 4.319689898685966,\"nxtTheta\": 4.71238898038469,\"x1\": 140,\"x2\": 101.73165676349106,\"x3\": 139.99999999999997,\"y1\": 130,\"y2\": 37.61204674887132,\"y3\": 30,\"active\": false}]";

/**
 * DEFINE VARIABLES NEEDED ACROSS FUNCTIONS THAT SHOULD BE PASSED DIRECTLY BUT AREN'T
 */

	var calls =0;
    var filesToSend; //2019/04, MF:Added for onTraceChangeRSM()
    var trace; //2019/04, MF:Added since it was not declared.



/**
 * Purpose: saves map object as geojson
 * @params  feature objects
 * @event compiles layer data into object
 */

function saveMap(){

	if ( polyMarkers.features.length > 0 &&
		 polyMarkers.features[0] != null &&
		 polyMarkers.features[0].attributes != null &&
		 polyMarkers.features[0].attributes.title != null ) {
		 
		if(polyMarkers.features[0].attributes.title == "circle"){
			if ( polygons.features[0].attributes.elevation != null &&
					polygons.features[0].attributes.elevation.length > 101 ) {
				polygons.features[0].attributes.elevation = polygons.features[0].attributes.elevation[101]
			}
		}
	}

	var layers = {
		"vectors" : GEOJSON_PARSER.write(vectors.features, true),
		"lanes" : GEOJSON_PARSER.write(lanes.features, true),
        "polygons" : GEOJSON_PARSER.write(polygons.features, true),
        "area" : GEOJSON_PARSER.write(area.features, true),
		"laneMarkers" : GEOJSON_PARSER.write(laneMarkers.features, true),
        "polyMarkers" : GEOJSON_PARSER.write(polyMarkers.features, true)
	};

	console.log("Layers: ", layers);

	saveFile( layers );
}


/**
 * Purpose: saves compiled object as geojson file
 * @params  map object
 * @event saves as geojson and loads save menu option
 */

function saveFile( data ){
	var textToWrite = JSON.stringify( data );
	var textFileAsBlob = new Blob([textToWrite], {type:'text/plain'});
	var fileNameToSaveAs = "TIM_message.geojson";

	var downloadLink = document.createElement("a");
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
			console.log("error saving firefox file");
		}
		// IE 10+
		try {
			window.navigator.msSaveBlob(textFileAsBlob, fileNameToSaveAs);
		}
		catch(e){
			console.log("error saving IE file");
		}
	}

	try {
		downloadLink.click();
	}
	catch(e) {
		console.log("unable to click the download link.  Are you using IE?");
	}

	document.body.removeChild(downloadLink);
}


/**
 * Purpose: loads map objects from geojson
 * @params  saved object
 * @event rebuilds markers on map
 */

function loadMap( data )
{
	var vectorLayerAsOL = GEOJSON_PARSER.read(data.vectors);
	var lanesLayerAsOL = GEOJSON_PARSER.read(data.lanes);
    var polygonsLayerAsOL = GEOJSON_PARSER.read(data.polygons);
    var areaLayerAsOL = GEOJSON_PARSER.read(data.area);
	var laneMarkersLayerAsOL = GEOJSON_PARSER.read(data.laneMarkers);
    var polyMarkersLayerAsOL = GEOJSON_PARSER.read(data.polyMarkers);

	vectors.addFeatures(vectorLayerAsOL);
	lanes.addFeatures(lanesLayerAsOL);
    polygons.addFeatures(polygonsLayerAsOL);
    area.addFeatures(areaLayerAsOL);
	laneMarkers.addFeatures(laneMarkersLayerAsOL);
    polyMarkers.addFeatures(polyMarkersLayerAsOL);
    
	circles_temp = [];
	circles = [];
	for (d=0; d < 360; (d =d + 22.5)){
		drawCircle(ctx, cx, cy, cr, "black", "white", d, circles_reset);
	}

	var feat = vectors.features;
	for(var a = 0; a < feat.length; a++){
		var iconAddress = feat[a].attributes.marker.img_src;
		feat[a].style = {externalGraphic: iconAddress, graphicHeight: 50, graphicWidth: 50, graphicYOffset: -50};
        if (feat[a].attributes.marker.type == "TIM"){
            if (feat[a].attributes.heading){            	
            	if(feat[a].attributes.heading.length == 0) {
            		circles_temp = JSON.parse(BACKWARDS_COMPATABILITY_FOR_MISSING_CIRLCES);
            		circles = JSON.parse(BACKWARDS_COMPATABILITY_FOR_MISSING_CIRLCES);
            	}
            	else {
            		circles_temp = JSON.parse(JSON.stringify(feat[a].attributes.heading));
                    circles = JSON.parse(JSON.stringify(feat[a].attributes.heading));
            	}
                drawCircleSlices(circles);
            }            
            mutcd = feat[a].attributes.mutcd;
            priority = feat[a].attributes.priority;
            direction = feat[a].attributes.direction;
            info_type = feat[a].attributes.infoType;
            content = feat[a].attributes.content;
        }
	}

	var ft = lanes.features;
	for(var i=0; i< ft.length; i++) {
		if(typeof lanes.features[i].attributes.elevation == 'string') {
			var temp = lanes.features[i].attributes.elevation;
			lanes.features[i].attributes.elevation = [];
			for (j = 0; j < lanes.features[i].geometry.getVertices().length; j++) {
				var dot = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Point(lanes.features[i].geometry.getVertices()[j].x, lanes.features[i].geometry.getVertices()[j].y));
				var latlon = new OpenLayers.LonLat(dot.geometry.x, dot.geometry.y).transform(toProjection, fromProjection);
				lanes.features[i].attributes.elevation[j] = ({'value': temp, 'edited': false, 'latlon': latlon});
			}
		}
	}

	var pft = polygons.features;
	for(var i=0; i< pft.length; i++) {
		if(typeof polygons.features[i].attributes.elevation == 'string') {
			var temp = polygons.features[i].attributes.elevation;
			polygons.features[i].attributes.elevation = [];
			for (j = 0; j < polygons.features[i].geometry.getVertices().length; j++) {
				var dot = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Point(polygons.features[i].geometry.getVertices()[j].x, polygons.features[i].geometry.getVertices()[j].y));
				var latlon = new OpenLayers.LonLat(dot.geometry.x, dot.geometry.y).transform(toProjection, fromProjection);
				lanes.features[i].attributes.elevation[j] = ({'value': temp, 'edited': false, 'latlon': latlon});
			}
		}
	}
	
	try {
		var center = new OpenLayers.LonLat(feat[0].attributes.LonLat.lon,feat[0].attributes.LonLat.lat);
		center.transform(new OpenLayers.Projection("EPSG:4326"), map.getProjectionObject());
		var view_zoom = 18;
		if (getCookie("tim_zoom") !== ""){
			view_zoom = getCookie("tim_zoom");
		}
		map.setCenter(center,view_zoom);
	}
	catch (err){
		console.log("No vectors to reset view");
	}

	vectors.redraw();
	
    toggleControlsOn('modify');
    toggleControlsOn('none');
}


/**
 * Purpose: loads file
 * @params  -
 * @event clears map and then opens modal to choose file
 */

function loadFile() {

	calls = 0;

	var c = confirm("Loading a new map will clear all current work. Continue?");
	if (c === true) {
		lanes.destroyFeatures();
        polygons.destroyFeatures();
		laneMarkers.destroyFeatures();
        polyMarkers.destroyFeatures();
		vectors.destroyFeatures();
        area.destroyFeatures();
		radiuslayer.destroyFeatures();
		deleteTrace();
		laneWidths.destroyFeatures();

		var ua = window.navigator.userAgent;
		var msie10 = ua.indexOf('MSIE ');
	    var msie11 = ua.indexOf('Trident/');
	    var msie12 = ua.indexOf('Edge/');
		
		if (msie10 > 0 || msie11 > 0 || msie12 > 0){
			$('#open_file_modal').modal('show');
			$('#fileToLoad2').one('change', onChange);
		}
		else {
			$('#fileToLoad').click();
			$('#fileToLoad').one('change', onChange);
		}

        $('#drawPoly').prop('disabled', false);
        $('#editPoly').prop('disabled', false);
        $('#drawLanes').prop('disabled', false);
        $('#editLanes').prop('disabled', false);
	}
}

//03/2019 MF: Updated function name to differentiate from loadRSMTrace
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

function onChange(event) {
	if (calls == 0) {
		var reader = new FileReader();
		reader.onload = onReaderLoad;
		reader.readAsText(event.target.files[0]);
	}
	calls++;
}

function onReaderLoad(event){
	var data = JSON.parse(event.target.result);
	loadMap( data );
	$('#open_file_modal').modal('hide');
}

function onTraceChange(event) {
	var reader = new FileReader();
	reader.onload = onTraceReaderLoad;
	reader.readAsText(event.target.files[0]);
}


/*
2019/04, MF: Event to process all the RSM file, convert to KML, and load onto the map.
*/
function onTraceChangeRSM (event) {

    //var filesToSend = new Array();
    filesToSend = [];

    console.log("# of files: " + event.target.files.length);

    if (event.target.files.length == 0)
    {
        aler("No files selected.");
        return;
    }

    //Need to have a list of promises per file, to call Promise.all, which process each file sequentially
    var promiseArray = [];

    //Loop thru each file and process file/blobs data encoded as a data URL, base64 encoding
    for (var i= 0; i < event.target.files.length; i++)
    {
        promiseArray.push( rsmFileReader(event.target.files[i]) );
    }

    //Need to process using Promise.all, because FileReader is asynchronous
    //Wait for all files to be read into the filesToSend, before calling the "callRSMWebservice"
    Promise.all(promiseArray).then(values => {
      console.log("All file(s) have been read.");
      callRSMWebservice(filesToSend);
    })
    .catch((err)=> {
         alert("Unable to load the files, please try again. Error: " + err);
    });

}//end onTraceChangeRSM

/*
2019/04, MF: Added to call the read each RSM file into a base64 content.
*/
function rsmFileReader(file){

    //Need to create a promise per each file processed.
    return new Promise ((resolve, reject) => {

        let reader = new FileReader();

        //Define the onload of the FileReader, which is asynchronous
        reader.onload = function (theFile) {

           return function(e){

                if (e.target.result != null
                    && e.target.result != undefined
                    && e.target.result != "data:" //meaning empty file(s)
                    )
                {
                  //Create an object with specific properties
                  var fileObject = {};
                  fileObject.filename = theFile.name;
                  fileObject.text = e.target.result.substr(e.target.result.indexOf(",")+1,e.target.result.length);

                  //Append each fileObject to the List
                  filesToSend.push(fileObject);
                  console.log ("fileObject.filename: " + fileObject.filename);
                }

                resolve(); //end promise with a success
           };

        }(file);//end onload

        reader.onerror = function (err){
            console.log("reader.error: " + err);
            reject(err);
        };
        //Read each file as a data URL as base64
        reader.readAsDataURL(file); //calls the reader.onLoad() defined above
    });
}

/*
2019/04, MF: Added to call the webservice to convert the RSM files (base64 content) to KML
*/
function callRSMWebservice(filesToSend){

   if ( filesToSend == null ||
        filesToSend == undefined ||
        filesToSend.length == 0)
   {
        alert("Empty file(s), please try again.");
        return;
   }

   webapp_root = window.location.pathname.split( '/' )[1]

   $.ajax({
       url: "/" + webapp_root + "/builder/messages/rsm_converter",
       type: "POST",
       contentType: "text/plain",
       data: JSON.stringify({'files': filesToSend}),
       success: function (result){

           console.log ("result.successful:" + result.successful);
           console.log ("result.errorMessage:" + result.errorMessage);

           /*
            //Keep for DEBUGGING: Add "<div id = "results"> <div id = "inputText">" to index.html
            $("#inputText").empty();
            $("#results").empty();
            $("#inputText").append(JSON.stringify({'files': filesToSend}));
            $("#results").append(result.successful  + "<br/>" + result.errorMessage+ "<br/>" + result.kmlDocument);
           */

            if (result.successful){
                //Add KML to map
                addKmltoMap(result.kmlDocument);
            }
            else{
                //Show error to the user
                alert(result.errorMessage);
            }

       },
       error: function (XMLHttpRequest, textStatus, errorThrown) {

            if (errorThrown=="Bad Request") {
                alert(JSON.parse(XMLHttpRequest.responseText).errorMessage);
            }
            else{
                alert(errorThrown + " . Please try again or contact the System Administrator.");
            }

            //Keep for DEBUGGING:
            console.log("Webservice Error: " + textStatus + "; " +  errorThrown +  "; "  + XMLHttpRequest.responseText );
            //$("#results").append(XMLHttpRequest.responseText + "; " + textStatus + "; " + errorThrown).append("<br/>");
       }
   })
};

/*
2019/04, MF: Added to parse the KML and add the layer to the map.
             Also added code to center the map based on the KML layer.
*/
function addKmltoMap(kmlDocument)
{
    var kmlParser = new OpenLayers.Format.KML({
        extractStyles: true,
        extractAttributes: true,
        internalProjection: new OpenLayers.Projection("EPSG:3857"),
        externalProjection: new OpenLayers.Projection("EPSG:4326")
    });

    var feature_list = kmlParser.read(kmlDocument);

    //Add KML to map
    trace = new OpenLayers.Layer.Vector("Trace");
    trace.addFeatures(feature_list);
    map.addLayer(trace);
    $('#open_file_modal').modal('hide');

    //Center on new layer and with current zoom
    var ft = trace.features[0];
    var bounds = ft.geometry.bounds;
    map.setCenter(bounds.getCenterLonLat(),map.getZoom());
    //console.log("map.getZoom(): " + map.getZoom());
}

// 2019/04, MF: Updated to move common code to addKMLtoMap()
function onTraceReaderLoad(event){
	var data = event.target.result;
	addKmltoMap(data);
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

function destroyClickedElement(event){
	document.body.removeChild(event.target);
}

