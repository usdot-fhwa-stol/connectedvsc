import {addRow, deleteRow, getCookie, makeDroppable, onMappedGeomIdChangeCallback, onRegionIdChangeCallback, onRoadAuthorityIdChangeCallback, rebuildConnections, removeSpeedForm, resetRGAStatus, resetSpeedDropdowns, saveConnections, saveSpeedForm, setLaneAttributes, setRGAStatus, toggle, toggleBars, toggleLanes, toggleLaneTypeAttributes, togglePoints, toggleWidthArray, unselectFeature, updateSharedWith, updateTypeAttributes } from "./utils.js";
import {newChildMap, newParentMap, openChildMap, openParentMap, selected, updateChildParent}  from "./parent-child-latest.js"
import {deleteTrace, loadKMLTrace, loadRSMTrace, revisionNum, saveMap, toggleControlsOn,} from "./files.js";
import {barStyle, connectionsStyle, errorMarkerStyle, laneStyle, vectorStyle, widthStyle} from "./style.js";
import {boxSelectInteractionCallback, laneMarkersInteractionCallback, laneSelectInteractionCallback, vectorAddInteractionCallback, vectorSelectInteractionCallback} from "./interactions.js";
import {populateAutocompleteSearchPlacesDropdown } from "./api.js";
import {buildComputedFeature, onFeatureAdded } from "./features.js";
import {onMoveEnd, onPointerMove, onZoomIn, onZoomOut } from "./map-event.js";

const tilesetURL = "http://localhost:8080/azuremap/api/proxy/tileset/";
let nodeObject = [];
const aerialTilesetId = "microsoft.imagery";
const roadTilesetId = "microsoft.base.road";
const hybridTilesetId = "microsoft.base.hybrid.road";
let viewLon = -83.05084664848823; //-77.149279; // -81.831733
let viewLat = 42.33697589046676 // 38.955995; //  28.119692
let viewLonLat = [viewLon, viewLat];
let viewCenter = ol.proj.fromLonLat(viewLonLat);
let viewZoom = 19;
let map;
let selectedLayer, selectedMarker;
let vectors, lanes, laneMarkers, box, laneConnections, errors, laneWidths;
let overlayLayersGroup, baseLayersGroup;
let computingLane = false;
let computedLaneSource;
let dropdownCheck = false;
let sharedWith_object, typeAttribute_object, typeAttributeName, laneTypeOptions = [];
let typeAttributeNameSaved = "";
let sharedWith = [];
let typeAttribute = [];
let nodeLaneWidth = [];
let signalPhase, stateConfidence, laneNum, laneType, approachType, intersectionID, approachID;
let hiddenDrag, intersectionSidebar, deleteMode, currentControl;
let $imgs;
let rowHtml;
let speedForm;
let rgaEnabled = false;

function initMap() {
  const baseAerialLayer = new ol.layer.Tile({
    title: "Aerial",
    source: new ol.source.XYZ({
      url: tilesetURL + aerialTilesetId+"/{z}/{x}/{y}"
    }),
    type: "base",
    visible: false,
  });

  const baseRoadLayer = new ol.layer.Tile({
    title: "Road",
    type: "base",
    source: new ol.source.XYZ({
      url: tilesetURL + roadTilesetId+"/{z}/{x}/{y}"
    }),
    visible: false,
  });

  const baseHybridLayer = new ol.layer.Tile({
    title: "Hybrid",
    type: "base",
    source: new ol.source.XYZ({
      url: tilesetURL + hybridTilesetId+"/{z}/{x}/{y}"
    }),
    visible: false,
  });

  const osmLayer = new ol.layer.Tile({
    title: "OpenStreetMap",
    type: "base",
    source: new ol.source.OSM(),
    visible: true,
  });

  const lanesSource = new ol.source.Vector();
  lanes = new ol.layer.Vector({
    title: "Lane Layer",
    source: lanesSource,
    style: laneStyle,
    visible: true,
  });

  const vectorSource = new ol.source.Vector();
  vectors = new ol.layer.Vector({
    title: "Vector Layer",
    source: vectorSource,
    style: vectorStyle,
    visible: true,
  });

  const boxSource = new ol.source.Vector();
  box = new ol.layer.Vector({
    title: "Stop Bar Layer",
    source: boxSource,
    style: barStyle,
    visible: true,
  });

  const laneMarkersSource = new ol.source.Vector();
  laneMarkers = new ol.layer.Vector({
    title: "Lane Marker Layer",
    source: laneMarkersSource,
    style: laneStyle,
    visible: true,
  });

  const laneWidthsSource = new ol.source.Vector();
  laneWidths = new ol.layer.Vector({
    title: "Width Layer",
    source: laneWidthsSource,
    style: widthStyle,
    visible: true,
  });

  const laneConnectionsSource = new ol.source.Vector();
  laneConnections = new ol.layer.Vector({
    title: "Connection Layer",
    source: laneConnectionsSource,
    style: connectionsStyle,
    visible: true,
  });

  const errorsSource = new ol.source.Vector();
  errors = new ol.layer.Vector({
    title: "Error Layer",
    source: errorsSource,
    style: errorMarkerStyle,
    visible: true,
  });

  /**
   * Layer Group for base layers
   */
  baseLayersGroup = new ol.layer.Group({
    title: "Base Layer",
    layers: [
      osmLayer,
      baseAerialLayer,
      baseRoadLayer,
      baseHybridLayer,
    ],
  });

  /**
   * Layer Group for overlay layers
   **/
  overlayLayersGroup = new ol.layer.Group({
    title: "Overlays",
    layers: [
      lanes,
      vectors,
      box,
      laneMarkers,
      laneWidths,
      laneConnections,
      errors,
    ],
  });

  if (getCookie("isd_latitude") !== "") {
    viewLat = getCookie("isd_latitude");
  }
  if (getCookie("isd_longitude") !== "") {
    viewLon = getCookie("isd_longitude");
  }
  if (getCookie("isd_zoom") !== "") {
    viewZoom = getCookie("isd_zoom");
  }
  if (getCookie("isd_message_type") !== "") {
    $("#message_type").val(getCookie("isd_message_type"));
  }
  if (getCookie("isd_node_offsets") !== "") {
    $("#node_offsets").val(getCookie("isd_node_offsets"));
  }
  if (getCookie("isd_enable_elevation") !== "") {
    $("#enable_elevation").prop(
      "checked",
      "true" == getCookie("isd_enable_elevation")
    );
  } else {
    $("#enable_elevation").prop("checked", true);
  }

  map = new ol.Map({
    view: new ol.View({
      center: viewCenter,
      zoom: viewZoom,
      projection: "EPSG:3857",
    }),
    target: "map",
  });

  map.addLayer(baseLayersGroup);
  map.addLayer(overlayLayersGroup);

  // Add LayerSwitcher
  const layerSwitcher = new LayerSwitcher({
    reverse: true, // Put base maps at the bottom
    groupSelectStyle: "group", // Show layers as grouped
  });
  map.addControl(layerSwitcher);
}

function registerMapEvents() {
  map.on("moveend", (event) => {
    onMoveEnd(event, map);
  });
  map.on("pointermove", (event) => {
    onPointerMove(event, map);
  });
  document.getElementById('customZoomOut').addEventListener('click', (event) => {
    onZoomOut(event, map);
  });
  document.getElementById('customZoomIn').addEventListener('click', (event) => {
    onZoomIn(event, map);
  });
}

function registerMapInteraction() {
  /***
   * Lanes layer interactions
   */
  //Add select feature event on lanes layer
  const laneSelectInteraction = new ol.interaction.Select({
    condition: ol.events.condition.click,
    layers: [lanes],
  });

  laneSelectInteraction.on("select", (event) => {
    laneSelectInteractionCallback(event, overlayLayersGroup, lanes, laneWidths, deleteMode, selected);
  });
  map.addInteraction(laneSelectInteraction);

  /***
   * Lane Markers layer interactions
   */
  const laneMarkersInteraction = new ol.interaction.Select({
    condition: ol.events.condition.click,
    layers: [laneMarkers],
  });
  laneMarkersInteraction.on("select", (event) => {
    selectedLayer = laneMarkers;
    selectedMarker = laneMarkersInteractionCallback( event, overlayLayersGroup, lanes, laneConnections, deleteMode, selected, speedForm);
    if (selectedMarker) {
      signalPhase = selectedMarker.get("signalPhase")? selectedMarker.get("signalPhase"): null;
      stateConfidence = selectedMarker.get("stateConfidence")? selectedMarker.get("stateConfidence"): null;
      laneNum = selectedMarker.get("laneNumber")? selectedMarker.get("laneNumber"): null;
      laneType = selectedMarker.get("laneType")? selectedMarker.get("laneType"): null;
      typeAttributeName = laneType;
    }
  });
  map.addInteraction(laneMarkersInteraction);

  /***
   * Vectors layer interactions
   */
  //Add select feature event on vectors layer
  const vectorAddInteraction = new ol.interaction.Select({
    condition: ol.events.condition.click,
    layers: [vectors],
  });
  vectorAddInteraction.on("select", (event) => {
    selectedLayer = vectors;
    selectedMarker = vectorSelectInteractionCallback(event, overlayLayersGroup, lanes, deleteMode, selected, rgaEnabled, speedForm);
  });
  map.addInteraction(vectorAddInteraction);

  //Add feature event on vectors layer
  vectors.getSource().on("addfeature", (event) => {
    selectedLayer = vectors;
    selectedMarker = vectorAddInteractionCallback(event,  selected, rgaEnabled, speedForm);
  });

  /***
   * Box/Stop Bar layer interactions
   */
  const boxSelectInteraction = new ol.interaction.Select({
    condition: ol.events.condition.click,
    layers: [box],
  });
  boxSelectInteraction.on("select", (event) => {
    selectedLayer = box;
    selectedMarker = boxSelectInteractionCallback(event, overlayLayersGroup, lanes, deleteMode, selected);
  });
  map.addInteraction(boxSelectInteraction);
}

function initTopNavBar() {
  /***
   * Register navbar files events
   */
  $("#openChild").click(() => {
    openChildMap(map, lanes, vectors, laneMarkers, laneWidths, box, errors);
  });

  $("#saveMap").click(() => {
    saveMap(vectors, box, lanes, laneMarkers, selected);
  });

  $("#openParent").click(() => {
    openParentMap(map, lanes, vectors, laneMarkers, laneWidths, box, errors);
  });

  $("#newParentMap").click(() => {
    newParentMap(lanes, vectors, laneMarkers, laneWidths, box, errors);
  });

  $("#newChildMap").click(() => {
    newChildMap(map, lanes, vectors, laneMarkers, laneWidths, box, errors);
  });

  $("#updateChildParent").click(() => {
    updateChildParent(map, lanes, vectors, laneMarkers, laneWidths, box);
  });

  $("#loadKMLTrace").click(() => {
    loadKMLTrace(map);
  });

  $("#loadRSMTrace").click(() => {
    loadRSMTrace();
  });

  $("#deleteTrace").click(() => {
    deleteTrace();
  });

  /***
   * Purpose: Autocomplete for allowing place search
   * @params Address input box
   * @event places API from google -> set cookie and move map to location
   */
  let searchInput = $("#address-search");
  searchInput.keyup((event) => {
    if (
      event.key === "ArrowDown" ||
      event.key === "ArrowLeft" ||
      event.key === "ArrowRight" ||
      event.key === "ArrowUp"
    ) {
      return;
    }
    let searchResultDropdown = $("#dropdown-menu-search");
    searchResultDropdown.empty();
    let inputText = event.target.value;
    if (inputText?.length > 0) {
      populateAutocompleteSearchPlacesDropdown(map, inputText);
    } else {
      searchResultDropdown.hide();
    }
  });
}

function initSideBar() {
  hiddenDrag = $("#hidden-drag");
  intersectionSidebar = $("#sidebar");
  /**
   * Register navbar Show events
   */
  $("#toggleBars").click(() => {
    toggleBars(box);
  });
  $("#toggleLanes").click(() => {
    toggleLanes(lanes, laneMarkers);
  });
  $("#togglePoints").click(() => {
    togglePoints(vectors);
  });
  $("#builderShow").click(() => {
    toggle(lanes, vectors, laneMarkers, laneWidths, laneConnections);
  });
  $("#toggleLaneWidth").click(() => {
    toggleWidthArray(lanes, vectors, laneWidths);
  });

  /***
   * Register sidebar bottom layer control events
   */
  $("button[name='layerControl']").click(function (e) {
    deleteMode = false;
    $("#dragSigns i").removeClass("fa-unlock").addClass("fa-lock");
    $(this).addClass("current").siblings().removeClass("active");
    currentControl = this.value;
    if (!$(this).hasClass("active")) {
      if (currentControl === "drag") {
        $("#dragSigns i").removeClass("fa-lock").addClass("fa-unlock");
      }
      if (currentControl === "del") {
        deleteMode = true;
      }
      toggleControlsOn(
        currentControl,
        lanes,
        vectors,
        laneMarkers,
        laneWidths,
        false
      );
    } else {
      deleteMode = false;
      toggleControlsOn("none", lanes, vectors, laneMarkers, laneWidths, false);
    }
  });

  $("#builder").click(() => {
    toggle(lanes, vectors, laneMarkers, laneWidths, laneConnections);
  });

  let intersectionContents = $("#intersection-tab-contents");
  let arrayLength = intersection_features.length;
  for (let i = 0; i < arrayLength; i++) {
    let html = '<div class="col-lg-6">';
    html +=
      '<img id="intersection_img_' +
      i +
      '" class="drag-intersection-img" src="' +
      intersection_features[i].img_src +
      '">';
    html += "<p>" + intersection_features[i].name + "</p>";
    html += "</div>";
    intersectionContents.append(html);
  }

  let laneContents = $("#lane-tab-contents");
  arrayLength = lane_attributes.length;
  for (let i = 0; i < arrayLength; i++) {
    let html = '<div class="col-lg-4">';
    html +=
      '<img id="lane_img_' +
      lane_attributes[i].id +
      '" class="drag-lane-img" src="' +
      lane_attributes[i].img_src +
      '">';
    html += "</div>";
    laneContents.append(html);
  }

  $imgs = intersectionSidebar.find(".drag-intersection-img,.drag-lane-img");
  $imgs.draggable({
    appendTo: "body",
    containment: "body",
    zIndex: 150000,
    cursorAt: { left: 25, top: 50 },
    revert: function () {
      if ($(this).hasClass("drag-lane-img")) {
        return "invalid";
      }
    },
    helper: function () {
      let container = $("<div/>");
      let dragged = $(this).clone();
      dragged.attr("class", "dragged-img");
      container.append(dragged);
      return container;
    },
    start: function (e, ui) {
      hiddenDrag.removeClass("hidden");
    },
    stop: function (e) {
      hiddenDrag.addClass("hidden");

      // check to see if intersection markers have already been placed
      let id = parseInt(this.id.match(/(\d+)$/)[0], 10);
      let num_features = vectors.getSource().getFeatures().length;
      for (let i = 0; i < num_features; i++) {
        if (id == vectors.getSource().getFeatures()[i].get("marker").id) {
          console.log("marker already placed");
          return;
        }
      }

      if ($(this).hasClass("drag-intersection-img")) {
        if (currentControl != "drag") {
          $("#dragSigns").click();
        }
        let pixel = [e.pageX, e.pageY - 50]; // subtract 50px because of navbar
        clone(this, pixel);
      }
    },
  }); 
  
}

 /**
   * Purpose: clone marker image onto layer
   * @params  object, pixel
   * @event places clone of marker image onto map post drag
   */

function clone(object, pixel) {
  let coordinate = map.getCoordinateFromPixel(pixel);
  let clonedFeature = new ol.Feature(new ol.geom.Point(coordinate));
  let IconInfo = { src: object.src, height: 50, width: 50 };
  clonedFeature.setStyle(
    new ol.style.Style({
      image: new ol.style.Icon(IconInfo),
    })
  );
  let lonLat = ol.proj.toLonLat(coordinate);
  let lonLatObj = { lon: lonLat[0], lat: lonLat[1] };
  clonedFeature.set("LonLat", lonLatObj);
  clonedFeature.set("verifiedLat", clonedFeature.get("LonLat").lat);
  clonedFeature.set("verifiedLon", clonedFeature.get("LonLat").lon);

  let intersection_id = parseInt(object.id.match(/(\d+)$/)[0], 10);
  clonedFeature.set("marker", intersection_features[intersection_id]);
  vectors.getSource().addFeature(clonedFeature);
}

function initMISC() {
  //Set initial status of various form elements
  $(".phases").hide();
  $(".lane_type_attributes select").hide();
  $("#lane_num_check").hide();
  $("#lane_type_check").hide();

  $('[data-toggle="tooltip"]').tooltip();
  /**
   * Purpose: link to help doc in config
   * @params  click event
   * @event load appropriate help window for a field
   */

  $(".fa-question-circle").click(function () {
    let tag = $(this).attr("tag");
    let obj = $.grep(help_notes, function (e) {
      return e.value === tag;
    });
    $("#help_modal").modal("show");
    $("#min").html(obj[0].min);
    $("#max").html(obj[0].max);
    $("#units").html(obj[0].units);
    $("#description").html(obj[0].description);
    $("#help_modal h4").html(obj[0].title);
  });

  /*********************************************************************************************************************/
  /**
   * Purpose: misc. functions that allow specific data to be visible a certain way
   * @params  -
   * @event varies
   *
   * Note: the ul/li select boxes should one da become select boxes with options, but the styling was hard to replicate
   * at first.
   */
  $("#approach_type .dropdown-menu li a").click(function () {
    let selText = $(this).text();
    approachType = selText;
    $(this)
      .parents(".btn-group")
      .find(".dropdown-toggle")
      .html(selText + ' <span class="caret"></span>');
  });

  $("#phase .dropdown-menu li a").click(function () {
    let selText = $(this).text();
    signalPhase = selText;
    $(this)
      .parents(".btn-group")
      .find(".dropdown-toggle")
      .html(selText + ' <span class="caret"></span>');
    let val = selText.substring(1, 2);
    $(".phases").hide();
    $("#phase" + val).show();
  });

  $("#confidence .dropdown-menu li a").click(function () {
    let selText = $(this).text();
    stateConfidence = selText;
    $(this)
      .parents(".btn-group")
      .find(".dropdown-toggle")
      .html(selText + ' <span class="caret"></span>');
  });

  $("#lane_number .dropdown-menu li a").click(function () {
    let selText = $(this).text();
    laneNum = selText;
    $(this)
      .parents(".btn-group")
      .find(".dropdown-toggle")
      .html(selText + ' <span class="caret"></span>');
  });

  $("#approach_name .dropdown-menu li a").click(function () {
    let selText = $(this).text();
    approachID = selText;
    $(this)
      .parents(".btn-group")
      .find(".dropdown-toggle")
      .html(selText + ' <span class="caret"></span>');
  });

  $("#lane_type .dropdown-menu li a").click(function () {
    let selText = $(this).text();
    laneType = selText;
    $(this)
      .parents(".btn-group")
      .find(".dropdown-toggle")
      .html(selText + ' <span class="caret"></span>');
    typeAttribute_object = toggleLaneTypeAttributes(laneType);
    typeAttributeName = laneType;
  });

  $(".lane_type ul li").each(() => {
    laneTypeOptions.push($(this).text());
  });

  makeDroppable(null);

  $.get("js/row.html", function (data) {
    rowHtml = data;
    rebuildConnections(nodeObject);
  });

  $("#add_row").click(() => {
    addRow(null, null);
  });

  $(document).on("click", "[id^=rowDelete]", function () {
    let id = $(this)
      .attr("id")
      .replace(/^rowDelete/, "");
    deleteRow(id);
  });

  resetRGAStatus();
  $("#rga_switch").on("click", () => {
    rgaEnabled = setRGAStatus();
    console.log(rgaEnabled)
  });

  $("#road_authority_id").on("keyup", () => {
    onRoadAuthorityIdChangeCallback();
  });

  $("#mapped_geometry_id").on("keyup", () => {
    onMappedGeomIdChangeCallback();
  })

  $("#region").on("change", () => {
    onRegionIdChangeCallback($(this).val());
  })
  //region

  speedForm = $("#speedForm").sheepIt({
    separator: "",
    allowRemoveLast: true,
    allowRemoveCurrent: true,
    allowRemoveAll: true,
    allowAdd: true,
    allowAddN: true,
    maxFormsCount: 13,
    minFormsCount: 0,
    iniFormsCount: 1,
    afterAdd: function (source, newForm) {
      $("[id*=speedLimitType]").change(() => {
        console.log("speedForm limit type change");
        resetSpeedDropdowns(speedForm);
      });
    },
  });

  $("#speedForm_add").click(function () {
    resetSpeedDropdowns(speedForm);
  });

  $(".close.builder").click(() => {
    toggle(lanes, vectors, laneMarkers, laneWidths, laneConnections);
  });

  $(".datetimepicker").each(function () {
    $(this).datetimepicker({
      format: "d/m/Y H:m:s",
    });
  });
}

function registerModalButtonEvents() {
  
  /*********************************************************************************************************************/
  /**
   * Purpose: validate the data and save the data to the feature
   * @params  the sidebar form elements
   * @event validates all the visible data using parsley js. If it is not accepted, it turns the form locations
   * with issues red, otherwise, it allows the data object to be created and saved to the feature
   */
  $(".btnDone").click(() => {
    //Update Reference Point Configuration fields with parsley attributes
    let road_authority_id = $("#road_authority_id");
    let road_authority_id_type = $("#road_authority_id_type");
    road_authority_id_type.attr("data-parsley-required", "false");
    road_authority_id.attr("data-parsley-required", "false");
    if ($("#region").val()?.trim() === "0") {
      road_authority_id.attr("data-parsley-required", "true");
      road_authority_id_type.attr("data-parsley-required", "true");
    } else if (road_authority_id.val()?.length) {
      road_authority_id_type.attr("data-parsley-required", "true");
    }

    if (road_authority_id_type.val()?.trim()?.toLowerCase()) {
      road_authority_id.attr("data-parsley-required", "true");
    }

    $("#attributes").parsley().validate();

    if (
      selectedLayer.get("title") == "Lane Marker Layer" &&
      (computingLane || // Check if in computingLane since that means this is the first time this lane is created
        selectedMarker.get("number") == 0)
    ) {
      if (laneType != null && laneNum != null) {
        dropdownCheck = true;
      } else {
        dropdownCheck = false;
      }

      if (laneType == null) {
        $("#lane_type_check").show();
      } else {
        $("#lane_type_check").hide();
      }
      if (laneNum == null) {
        $("#lane_num_check").show();
      } else {
        $("#lane_num_check").hide();
      }
    } else {
      dropdownCheck = true;
    }

    if (
      $(".parsley-errors-list li:visible").length === 0 &&
      dropdownCheck === true
    ) {
      // When computing a lane, there is no initial marker since we are generating them from another lane.
      // Therefore we have to build the markers before we select the lane.
      if (selectedLayer.get("title") == "Lane Marker Layer" && computingLane) {
        if (laneNum == null) {
          let selText = $("#lane_number .dropdown-menu li a").text();
          laneNum = selText;
        }

        buildComputedFeature(
          lanes.getSource().getFeatures().length,
          laneNum,
          $("#referenceLaneID").val(),
          $("#referenceLaneNumber").val(),
          $("#offset-X").val(),
          $("#offset-Y").val(),
          $("#rotation").val(),
          $("#scale-X").val(),
          $("#scale-Y").val(),
          selectedMarker.get("lane"),
          lanes,
          laneMarkers
        );
        selectedMarker = selectComputedFeature(laneNum);

        // The Latitude and Longitude text boxes in the Lane Info tab have not yet been set
        // since the lane's nodes were just created
        $("#lat").val(selectedMarker.attributes.LatLon.lat);
        $("#long").val(selectedMarker.attributes.LatLon.lon);

        nodeLaneWidth = lanes.features[selectedMarker.attributes.lane].attributes.laneWidth;
      }

      setLaneAttributes(selectedMarker);
      $("#attributes").hide();

      sharedWith_object = updateSharedWith();
      typeAttribute_object = updateTypeAttributes(typeAttributeName);
      nodeObject = saveConnections(selectedMarker);

      sharedWith = [];
      for (let i = 0; i < sharedWith_object.length; i++) {
        sharedWith[i] = sharedWith_object[i];
      }

      typeAttributeNameSaved = typeAttributeName;
      typeAttribute = [];
      for (let i = 0; i < typeAttribute_object.length; i++) {
        typeAttribute[i] = typeAttribute_object[i];
      }

      let move = ol.proj.fromLonLat([
        parseFloat($("#long").val()),
        parseFloat($("#lat").val()),
      ]);
      
      if (selectedLayer.get("title") == "Lane Marker Layer") {
        let currentLaneSpeedLimits = saveSpeedForm(speedForm);
        lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("speedLimitType", currentLaneSpeedLimits);
        selectedMarker.set("speedLimitType", currentLaneSpeedLimits);
        selectedMarker.getGeometry().setCoordinates(move);
        if (selectedMarker.get("number") == 0) {
          selectedMarker.set("spatRevision", $("#spat_revision").val());
          selectedMarker.set("signalGroupID", $("#signal_group_id").val());
          selectedMarker.set("startTime", $("#start_time").val());
          selectedMarker.set("minEndTime", $("#min_end_time").val());
          selectedMarker.set("maxEndTime", $("#max_end_time").val());
          selectedMarker.set("likelyTime", $("#likely_time").val());
          selectedMarker.set("nextTime", $("#next_time").val());
          selectedMarker.set("sharedWith", sharedWith);
          selectedMarker.set("typeAttribute", typeAttribute);

          if (nodeObject != null) {
            selectedMarker.set("connections", nodeObject);
            lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("connections", nodeObject);
          }

          if (laneNum != null) {
            selectedMarker.set("laneNumber", laneNum);
            lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("laneNumber", laneNum);
          }
          if (laneType != null) {
            selectedMarker.set("laneType", laneType);
            lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("laneType", laneType);
          }
          if (stateConfidence != null) {
            selectedMarker.set("stateConfidence", stateConfidence);
            lanes.getSource().getFeatures()[selectedMarker.get("lane")].set(  "stateConfidence",  stateConfidence);
          }
          if (signalPhase != null) {
            selectedMarker.set("signalPhase", signalPhase);
            lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("signalPhase", signalPhase);
          }

          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("descriptiveName",$("#descriptive_name").val());
          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("spatRevision",$("#spat_revision").val());
          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("signalGroupID",$("#signal_group_id").val());
          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("startTime",$("#start_time").val());
          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("minEndTime",$("#min_end_time").val());
          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("maxEndTime",$("#max_end_time").val());
          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("likelyTime",$("#likely_time").val());
          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("nextTime", $("#next_time").val());
          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("sharedWith", sharedWith);
          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("typeAttribute", typeAttribute);
          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("lane_attributes",selectedMarker.get("lane_attributes"));
        }
        selectedMarker.set("LatLon", {
          lat: parseFloat($("#lat").val()),
          lon: parseFloat($("#long").val()),
        });

        nodeLaneWidth[selectedMarker.get("number")] = $("#lane_width").val();
        lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("laneWidth", nodeLaneWidth);
        nodeLaneWidth = [];

        selectedMarker.set("elevation", $("#elev").val());
        lanes.getSource().getFeatures()[selectedMarker.get("lane")].get("elevation")[selectedMarker.get("number")].value = $("#elev").val();
        lanes.getSource().getFeatures()[selectedMarker.get("lane")].get("elevation")[selectedMarker.get("number")].edited = true;

        if (selectedMarker.get("computed")) {
          selectedMarker.set("offsetX", $("#offset-X").val());
          selectedMarker.set("offsetY", $("#offset-Y").val());
          selectedMarker.set("rotation", $("#rotation").val());
          selectedMarker.set("scaleX", $("#scale-X").val());
          selectedMarker.set("scaleY", $("#scale-Y").val());
          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("offsetX", $("#offset-X").val());
          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("offsetY", $("#offset-Y").val());
          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("rotation", $("#rotation").val());
          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("scaleX", $("#scale-X").val());
          lanes.getSource().getFeatures()[selectedMarker.get("lane")].set("scaleY", $("#scale-Y").val());
        }
      }

      if (selectedLayer.get("title") == "Stop Bar Layer") {
        if (approachType != null) {
          selectedMarker.set("approachType", approachType);
        }

        if (approachID != null) {
          selectedMarker.set("approachID", approachID);
        }
      }

      if (selectedLayer.get("title") == "Vector Layer") {
        if (selected == "child") {
          selectedMarker.set("speedLimitType", saveSpeedForm(speedForm));
          selectedMarker.set("layerID", $("#layer").val());
        } else {
          selectedMarker.getGeometry().setCoordinates(move);
          if (selectedMarker.get("marker").name == "Verified Point Marker") {
            selectedMarker.set("verifiedLat", $("#verified_lat").val());
            selectedMarker.set("verifiedLon", $("#verified_long").val());
            selectedMarker.set("verifiedElev", $("#verified_elev").val());
            selectedMarker.set("elevation", $("#elev").val());
          }
        }
        if (selectedMarker.get("marker").name == "Reference Point Marker") {
          selectedMarker.set("intersectionName", $("#intersection_name").val());
          selectedMarker.set("elevation", $("#elev").val());
          selectedMarker.set("intersectionID", $("#intersection").val());
          intersectionID = $("#intersection").val();
          selectedMarker.set("regionID", $("#region").val());
          selectedMarker.set(
            "roadAuthorityIdType",
            $("#road_authority_id_type").val()
          );
          selectedMarker.set("roadAuthorityId", $("#road_authority_id").val());
          selectedMarker.set("majorVersion", $("#major_version").val());
          selectedMarker.set("minorVersion", $("#minor_version").val());
          selectedMarker.set(
            "mappedGeometryId",
            $("#mapped_geometry_id").val()
          );
          selectedMarker.set("contentVersion", $("#content_version").val());
          selectedMarker.set("contentDateTime", $("#content_date_time").val());
          selectedMarker.set("masterLaneWidth", $("#master_lane_width").val());
          selectedMarker.set("revisionNum", revisionNum);
        }
      }
      $("#attributes").parsley().reset();
      unselectFeature(map, overlayLayersGroup, selectedMarker);

      computingLane = false;
      computedLaneSource = null;
    }
    onFeatureAdded(lanes, vectors, laneMarkers, laneWidths, false);
    //unselect the current marker
  });

  /*********************************************************************************************************************/
  /**
   * Purpose: if cancel - prevents data from being stored
   * @params  the sidebar form elements
   * @event removes all form data and clears any temp objects that may be housing data so that next load can start clean
   * from the feature object
   */

  $(".btnClose").click(() => {
    $("#attributes").hide();
    $("#shared_with").multiselect("deselectAll", false);
    $("#shared_with").multiselect("select", sharedWith);
    for (let i = 0; i < laneTypeOptions.length; i++) {
      if (
        laneTypeOptions[i] != typeAttributeNameSaved &&
        $("." + laneTypeOptions[i] + "_type_attributes").length !== 0
      ) {
        $("#" + laneTypeOptions[i] + "_type_attributes").multiselect(
          "deselectAll",
          false
        );
        $("#" + laneTypeOptions[i] + "_type_attributes").multiselect("refresh");
      }
    }
    removeSpeedForm(speedForm);
    $("#attributes").parsley().reset();
    rebuildConnections([]);
    if (selectedMarker != null) {
      unselectFeature(map, overlayLayersGroup, selectedMarker);
    }
    $("input[name=include-spat]").attr("checked", false);
    $(".phases").hide();
    stateConfidence = null;
    signalPhase = null;
    $("#descriptive_name").val("");
    laneNum = null;
    nodeLaneWidth = [];
    onFeatureAdded(lanes, vectors, laneMarkers, laneWidths, false);

    if (computingLane) {
      $("#offset-X").val("");
      $("#offset-Y").val("");
      $("#rotation").val("");
      $("#scale-X").val("");
      $("#scale-Y").val("");
      computingLane = false;
      computedLaneSource = null;
    }
  });

  $(".btnClone").click(() => {
    computingLane = true;

    // The current selected_marker is the 0 node of the lane clicked to clone.
    // Set it as the source for the computed lane and then unselect it
    computedLaneSource = selectedMarker;
    unselectFeature(map, overlayLayersGroup, selectedMarker);

    // Turning on placeComputed will allow the user to select a point on the map
    // for the computed lane
    toggleControlsOn(
      "placeComputed",
      lanes,
      vectors,
      laneMarkers,
      laneWidths,
      false
    );
  });
}

$(document).ready(() => {
  initMISC();
  initMap();
  registerMapEvents();
  registerMapInteraction();
  initTopNavBar();
  initSideBar();
  registerModalButtonEvents();
});
