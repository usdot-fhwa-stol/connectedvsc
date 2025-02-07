// import {widthStyleMap} from "./style.js";
import { getCookie } from "./utils.js";
import {openChildMap, selected}  from "./parent-child.js"
import { saveMap, toggleControlsOn } from "./files.js";
import {barStyle, connectionsStyle, errorMarkerStyle, laneStyle, lineStyle, pointStyle, vectorStyle, widthStyle} from "./style.js"
import { laneSelectInteractionCallback, vectorAddInteractionCallback, vectorSelectInteractionCallback } from "./interactions.js";

const sessionKey = "dtCq3hh0v9ryfET1T6268NDkntdI12SjDncgQkBuCpgFnAsS0qMRJQQJ99BBACYeBjF4WcqXAAAgAZMP2IF9";
const nodeObject = [];
const aerialTilesetId = "microsoft.imagery";
const roadTilesetId = "microsoft.base.road";
const hybridTilesetId = "microsoft.base.hybrid";
let viewLon = -77.149279; // -81.831733
let viewLat = 38.955995; //  28.119692
let viewLonLat = [viewLon, viewLat];
let viewCenter =  ol.proj.fromLonLat(viewLonLat);
let viewZoom = 19;
let map;
let vectors, lanes, laneMarkers, box, laneConnections, errors, trace, laneWidths;

/**
 * DEFINE GLOBAL VARIABLES from main.js
 */
var hidden_drag, intersection_sidebar, deleteMode, currentControl;
var tmp_lane_attributes = {}
var $imgs
var numRows = -1;
var rowHtml;
var speedLimits = [];
var speedForm;

// const baseAerialLayer = new ol.layer.Tile({
//   title: 'Aerial',  // ✅ Must have a title
//   source: new ol.source.XYZ({
//     url:"https://atlas.microsoft.com/map/tile?api-version=2.1&tilesetId="+aerialTilesetId+"&zoom={z}&x={x}&y={y}&subscription-key="+sessionKey
//   }),
//   type: 'base',  
//   visible: true
// });


// const baseRoadLayer = new ol.layer.Tile({
//   title: 'Road',  // ✅ Must have a title
//   source: new ol.source.XYZ({
//     url:"https://atlas.microsoft.com/map/tile?api-version=2.1&tilesetId="+roadTilesetId+"&zoom={z}&x={x}&y={y}&subscription-key="+sessionKey
//   }),
// });


// const baseHybridLayer = new ol.layer.Tile({
//   title: 'Hybrid',  // ✅ Must have a title
//   source: new ol.source.XYZ({
//     url:"https://atlas.microsoft.com/map/tile?api-version=2.1&tilesetId="+hybridTilesetId+"&zoom={z}&x={x}&y={y}&subscription-key="+sessionKey
//   }),
// });

const osmLayer = new ol.layer.Tile({
  title: 'OpenStreetMap',  // ✅ Must have a title
  type: 'base',            // ✅ Helps distinguish base layers
  source: new ol.source.OSM(),
  visible: true
});

const satelliteLayer = new ol.layer.Tile({
  title: 'Esri',  // ✅ Must have a title
  type: 'base',
  source: new ol.source.XYZ({
    attributions:
    'Tiles © <a href="https://services.arcgisonline.com/ArcGIS/' +
    'rest/services/World_Topo_Map/MapServer">ArcGIS</a>',
    url:
      'https://server.arcgisonline.com/ArcGIS/rest/services/' +
      'World_Topo_Map/MapServer/tile/{z}/{y}/{x}',
  }),
  visible: false
});


const lanesSource = new ol.source.Vector();
lanes = new ol.layer.Vector({
  title: "Lane Layer",
  source: lanesSource,
  style: laneStyle,
  visible: true
});


const vectorSource = new ol.source.Vector();
vectors = new ol.layer.Vector({
  title: "Vector Layer",
  source: vectorSource,
  style: vectorStyle,
  visible: true
});

const boxSource = new ol.source.Vector();
box = new ol.layer.Vector({
  title: "Stop Bar Layer",
  source: boxSource,
  style: barStyle,
  visible: true
});


const laneMarkersSource = new ol.source.Vector();
laneMarkers = new ol.layer.Vector({
  title: "Lane Marker Layer",
  source: laneMarkersSource,
  style: laneStyle,
  visible: true
});

const laneWidthsSource = new ol.source.Vector();
laneWidths = new ol.layer.Vector({
  title: "Width Layer",
  source: laneWidthsSource,
  style: widthStyle,
  visible: true
});

const laneConnectionsSource = new ol.source.Vector();
laneConnections = new ol.layer.Vector({
  title: "Connection Layer",
  source: laneConnectionsSource,
  style: connectionsStyle,
  visible: true
});

const errorsSource = new ol.source.Vector();
errors = new ol.layer.Vector({
  title: "Error Layer",
  source: errorsSource,
  style: errorMarkerStyle,
  visible: true
});

/**
 * Layer Group for base layers
 */
const baseLayers = new ol.layer.Group({
  title: 'Base Layer',  // ✅ Needed for LayerSwitcher to display
  layers: [osmLayer, satelliteLayer]
});

/**
 * Layer Group for overlay layers
 **/
const overlayLayers = new ol.layer.Group({
  title: 'Overlays',  // ✅ Needed for LayerSwitcher to display
  layers: [lanes, vectors, box, laneMarkers, laneWidths, laneConnections, errors ]
});


if (getCookie("isd_latitude") !== ""){
  viewLat = getCookie("isd_latitude")
}
if (getCookie("isd_longitude") !== ""){
  viewLon = getCookie("isd_longitude")
}
if (getCookie("isd_zoom") !== ""){
  viewZoom = getCookie("isd_zoom")
}
if (getCookie("isd_message_type") !== ""){
  $('#message_type').val(getCookie("isd_message_type"));
}
if (getCookie("isd_node_offsets") !== ""){
  $('#node_offsets').val(getCookie("isd_node_offsets"));
}
if (getCookie("isd_enable_elevation") !== ""){
  $('#enable_elevation').prop('checked',"true" == getCookie("isd_enable_elevation"));
} else {
  $('#enable_elevation').prop('checked', true);
}

map = new ol.Map({
  view: new ol.View({
    center: viewCenter,
    zoom: viewZoom,
  }),
  target: 'map',
});


map.addLayer(baseLayers);
map.addLayer(overlayLayers);


// Add LayerSwitcher
const layerSwitcher = new LayerSwitcher({
  reverse: true,          // Put base maps at the bottom
  groupSelectStyle: 'group' // Show layers as grouped
});
map.addControl(layerSwitcher);


//Set cookie anytime map is moved
function onMoveEnd(evt) {
  var centerPoint = map.getView().getCenter();
  let lonLat = ol.proj.toLonLat(centerPoint);
  setCookie("isd_latitude", lonLat[1], 365);
  setCookie("isd_longitude", lonLat[0], 365);  
  setCookie("isd_zoom", map.getView().getZoom(), 365);
  $('#zoomLevel .zoom').text(map.getView().getZoom());
}
map.on("moveend",onMoveEnd );


//Add select feature event on lanes layer
const laneSelectInteraction = new ol.interaction.Select({
  condition: ol.events.condition.click,
  layers: [lanes]
});

laneSelectInteraction.on('select', (evt)=>{ laneSelectInteractionCallback(evt, overlayLayers, lanes, laneWidths, deleteMode, selected) });
map.addInteraction(laneSelectInteraction);

//Add select feature event on vectors layer
const vectorAddInteraction = new ol.interaction.Select({
  condition: ol.events.condition.click,
  layers: [vectors]
});
vectorAddInteraction.on('select', (evt)=>{ vectorSelectInteractionCallback(evt, overlayLayers, lanes, deleteMode, selected) });
map.addInteraction(vectorAddInteraction);

//Add feature event on vectors layer
vectors.getSource().on("addfeature", evt=>{ vectorAddInteractionCallback(evt) })


$("#openChild").click(function() {
  openChildMap(map, lanes,vectors,laneMarkers, laneWidths, box, errors);
}); 

$("#saveMap").click(()=>{
  saveMap(vectors, box, lanes, laneMarkers, selected);
})



/**
 * Purpose: toggle marker lock
 * @params  click event
 * @event toggle marker lock
 */
$("button[name='layerControl']").click(function(e) {
	deleteMode = false;
	$("#dragSigns i").removeClass('fa-unlock').addClass('fa-lock')
	$(this).addClass('current').siblings().removeClass('active');
	currentControl = this.value;
	if(!$(this).hasClass('active')){
		if(currentControl === 'drag'){
			$("#dragSigns i").removeClass('fa-lock').addClass('fa-unlock')
		}
		if(currentControl === 'del'){
			deleteMode = true;
		}
		toggleControlsOn(currentControl,lanes, vectors, laneMarkers, laneWidths, false);
	} else {
		deleteMode = false;
		toggleControlsOn('none', lanes, vectors, laneMarkers, laneWidths, false);
	}
});