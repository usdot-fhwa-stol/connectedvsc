import { getCookie, makeDroppable, rebuildConnections, toggle, toggleBars, toggleLanes, togglePoints, toggleWidthArray } from "./utils.js";
import {openChildMap, selected}  from "./parent-child.js"
import { saveMap, toggleControlsOn } from "./files.js";
import {barStyle, connectionsStyle, errorMarkerStyle, laneStyle, vectorStyle, widthStyle} from "./style.js"
import { boxSelectInteractionCallback, laneMarkersInteractionCallback, laneSelectInteractionCallback, vectorAddInteractionCallback, vectorSelectInteractionCallback } from "./interactions.js";

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
let hiddenDrag, intersectionSidebar, deleteMode, currentControl;
let tmp_lane_attributes = {}
let $imgs
let numRows = -1;
let rowHtml;
let speedLimits = [];
let speedForm;

/***
 * A global variable to store current RGA toggle status. 
 * Some UI fields are disabled or enabled depends on the current RGA status.
 * @type Boolean Indicator whether RGA fields are enabled.
 */
let rgaEnabled=false;

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
  let centerPoint = map.getView().getCenter();
  let lonLat = ol.proj.toLonLat(centerPoint);
  setCookie("isd_latitude", lonLat[1], 365);
  setCookie("isd_longitude", lonLat[0], 365);  
  setCookie("isd_zoom", map.getView().getZoom(), 365);
  $('#zoomLevel .zoom').text(map.getView().getZoom());
}
map.on("moveend",onMoveEnd );

/***
 * Lanes layer interactions
 */
//Add select feature event on lanes layer
const laneSelectInteraction = new ol.interaction.Select({
  condition: ol.events.condition.click,
  layers: [lanes]
});

laneSelectInteraction.on('select', (evt)=>{ laneSelectInteractionCallback(evt, overlayLayers, lanes, laneWidths, deleteMode, selected) });
map.addInteraction(laneSelectInteraction);


/***
 * Lane Markers layer interactions
 */
const laneMarkersInteraction = new ol.interaction.Select({
  condition: ol.events.condition.click,
  layers: [laneMarkers]
});
laneMarkersInteraction.on('select', (evt)=>{ laneMarkersInteractionCallback(evt, overlayLayers, lanes, laneConnections, deleteMode, selected) });
map.addInteraction(laneMarkersInteraction);

/***
 * Vectors layer interactions
 */
//Add select feature event on vectors layer
const vectorAddInteraction = new ol.interaction.Select({
  condition: ol.events.condition.click,
  layers: [vectors]
});
vectorAddInteraction.on('select', (evt)=>{ vectorSelectInteractionCallback(evt, overlayLayers, lanes, deleteMode, selected, rgaEnabled) });
map.addInteraction(vectorAddInteraction);

//Add feature event on vectors layer
vectors.getSource().on("addfeature", evt => { vectorAddInteractionCallback(evt) })


/***
 * Box/StopBar layer interactions
 */
//Add select feature event on vectors layer
const boxSelectInteraction = new ol.interaction.Select({
  condition: ol.events.condition.click,
  layers: [box]
});
boxSelectInteraction.on('select', (evt)=>{ boxSelectInteractionCallback(evt, overlayLayers, lanes, deleteMode, selected) });
map.addInteraction(boxSelectInteraction);


$(document).ready(function () { 
  hiddenDrag = $('#hidden-drag');
  intersectionSidebar = $('#sidebar');
  /***
   * Register navbar files events
   */
  $("#openChild").click(function () {
    openChildMap(map, lanes,vectors,laneMarkers, laneWidths, box, errors);
  }); 

  $("#saveMap").click(()=>{
    saveMap(vectors, box, lanes, laneMarkers, selected);
  })

  /**
   * Register navbar Show events
   */
  $("#toggleBars").click(() => { toggleBars(box) });
  $("#toggleLanes").click(() => { toggleLanes(lanes, laneMarkers) });
  $("#togglePoints").click(() => { togglePoints(vectors) });
  $("#builderShow").click(() => { toggle(lanes, vectors, laneMarkers, laneWidths, laneConnections); })
  $("#toggleLaneWidth").click(() => { toggleWidthArray(lanes, vectors, laneWidths); })


  /***
   * Register sidebar bottom layer control events
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

  $("#builder").click(() => { toggle(lanes, vectors, laneMarkers, laneWidths, laneConnections); })


  $("#add_row").click(function() {
    addRow(null, null);
  });

  /***
     * Purpose: Autocomplete for allowing place search
     * @params Address input box
     * @event places API from google -> set cookie and move map to location
     */
  let searchInput = $("#address-search");
  searchInput.keyup((event)=>{
    if(event.key==="ArrowDown" || event.key==="ArrowLeft" || event.key==="ArrowRight" || event.key==="ArrowUp"){
        return;
    }
    let searchResultDropdown=$("#dropdown-menu-search");
    searchResultDropdown.empty();
    let inputText = event.target.value;
    if(inputText?.length>0){
        // populateAutocompleteSearchPlacesDropdown(inputText);
    }else{
        searchResultDropdown.hide();
    }
  });

  //--- Create Intersection markers
  let intersectionContents = $('#intersection-tab-contents');
  let arrayLength = intersection_features.length;
  for (let i = 0; i < arrayLength; i++) {
      let html = '<div class="col-lg-6">';
      html += '<img id="intersection_img_'+ i +'" class="drag-intersection-img" src="'+ intersection_features[i].img_src +'">';
      html += '<p>' + intersection_features[i].name + '</p>';
      html += '</div>';
      intersectionContents.append(html);
  }
  //--- end intersection

  //--- Create Lane attribute markers
  let laneContents = $('#lane-tab-contents');
  arrayLength = lane_attributes.length;
  for (let i = 0; i < arrayLength; i++) {
      let html = '<div class="col-lg-4">';
      html += '<img id="lane_img_'+ lane_attributes[i].id +'" class="drag-lane-img" src="'+ lane_attributes[i].img_src +'">';
      html += '</div>';
      laneContents.append(html);
  }
  //--- end lane


  /**
   * Purpose: allow marker images in sidebar to be dragable onto layer
   * @params  image object
   * @event makes images draggable
   */

  $imgs = intersectionSidebar.find('.drag-intersection-img,.drag-lane-img');
  $imgs.draggable({
      appendTo: 'body', containment: 'body', zIndex: 150000, cursorAt: {left:25, top:50},
      revert: function() {
          if( $(this).hasClass('drag-lane-img') ){
              return 'invalid';
          }
      },
      helper: function() {
          let container = $('<div/>');
          let dragged = $(this).clone();
          dragged.attr('class', 'dragged-img');
          container.append(dragged);
          return container;
      },
      start: function(e, ui) {
          hiddenDrag.removeClass('hidden');
      },
      stop: function(e) {
          hiddenDrag.addClass('hidden');

          // check to see if intersection markers have already been placed
          let id = parseInt(this.id.match(/(\d+)$/)[0], 10);
          let num_features = vectors.features.length;
          for( let i=0; i < num_features; i++) {
              if( id == vectors.features[i].attributes.marker.id ) {
                  console.log("marker already placed");
                  return;
              }
          }

          if( $(this).hasClass('drag-intersection-img') ) {
            if (currentControl != 'drag'){
              $('#dragSigns').click();
            }
              let point = new OpenLayers.Geometry.Point(e.pageX, e.pageY - 50); // subtract 50px because of navbar
              clone(this, point);
          }
      }
  });
  //--- end drag


  /**
   * Purpose: clone marker image onto layer
   * @params  object, point
   * @event places clone of marker image onto map post drag
   */

  function clone(object, point) {
      let lonlat = mapUpdate.getLonLatFromPixel(point);

      let cloned_feature = new OpenLayers.Feature.Vector(
          new OpenLayers.Geometry.Point(lonlat.lon, lonlat.lat),
          {some:'data'},
          {externalGraphic: object.src, graphicHeight: 50, graphicWidth: 50, graphicYOffset: -50});

      cloned_feature.attributes = {"LonLat": lonlat.transform(toProjection, fromProjection)};
      cloned_feature.attributes.verifiedLat = cloned_feature.attributes.LonLat.lat;
      cloned_feature.attributes.verifiedLon = cloned_feature.attributes.LonLat.lon;

      let intersection_id = parseInt(object.id.match(/(\d+)$/)[0], 10);
      cloned_feature.attributes['marker'] = intersection_features[intersection_id];

      vectors.addFeatures(cloned_feature);
  }

  makeDroppable(null);
  $.get("js/row.html", function(data) {
      rowHtml = data;
      rebuildConnections(nodeObject);
  });


  /**
   * Purpose: uses sheepit form to allow for multiple speed codes
   * @params  DOM form
   * @event creates form and establishes parameters
   */

  speedForm = $('#speedForm').sheepIt({
      separator: '',
      allowRemoveLast: true,
      allowRemoveCurrent: true,
      allowRemoveAll: true,
      allowAdd: true,
      allowAddN: true,
      maxFormsCount: 13,
      minFormsCount: 0,
      iniFormsCount: 1,
      afterAdd: function(source, newForm) {
          $("[id*=speedLimitType]").change(function() {
              resetSpeedDropdowns();
          });
      }
  });

  $("#speedForm_add").click(function(){
      resetSpeedDropdowns();
  });
  
  $(".datetimepicker").each(function(){
    $(this).datetimepicker(
        {
        format:'d/m/Y H:m:s'
        }
    );
  });

})
