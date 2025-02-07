
let numRows = -1;
import { getElev, getNearestIntersectionJSON } from "./api.js";
import { toggleControlsOn } from "./files.js";

function getCookie(cname) {
  let name = cname + "=";
  let decodedCookie = decodeURIComponent(document.cookie);
  let ca = decodedCookie.split(';');
  for(let i = 0; i <ca.length; i++) {
      let c = ca[i];
      while (c.charAt(0) == ' ') {
          c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
          return c.substring(name.length, c.length);
      }
  }
  return "";
}

function isOdd(num) { return (num % 2) == 1;}


 /***
  * @brief Show and hide RGA related fields. 
  * Note: extra RGA fields in addition to MAP message should only appear at the "Reference Point" dialog.
  * @param {type} Boolean show or hide RGA fields
  */
 function hideRGAFields(hide=true){
  if(hide){        
     $(".extra_rga_field").hide();
  }else{        
     $(".extra_rga_field").show();
  }
}

function enable_rga_fields(enable=true){    
  if(enable){        
       $(".extra_rga_field_input").prop('disabled', false);
  }else{     
       $(".extra_rga_field_input").prop('disabled', true);
  }
  add_rga_fields_validation(enable);
}

function add_rga_fields_validation(enable=true){
  if(enable){
    $("input:text.required").attr('data-parsley-required', true);
  }else{
    $("input:text.required").attr('data-parsley-required', false);
  }

}

/*********************************************************************************************************************/
/**
 * Purpose: creates sidebar element for the individual roadsigns
 * @params  the feature and it's metadata
 * @event loads the sidebar and all of the metadata into the forms
 */

function referencePointWindow(feature, selected, rgaEnabled){
  $("#attributes").hide();
  //---------------------------------------
  $(".selection-panel").text('Reference Point Configuration');
  $(".lane-info-tab").find('a:contains("Lane Info")').text('Marker Info');
  $(".lane-info-tab").find('a:contains("Approach Info")').text('Marker Info');
  $('#lane-info-tab').addClass('active');
  $('#spat-info-tab').removeClass('active');
  $('.spat-info-tab').removeClass('active');
  $('.spat-info-tab').hide();
  $('#intersection-info-tab').removeClass('active');
  $('.intersection-info-tab').removeClass('active');
  $('.intersection-info-tab').hide();
  $('#connection-tab').removeClass('active');
  $('.connection-tab').removeClass('active');
  $('.connection-tab').hide();
  $('#computed-tab').removeClass('active');
  $('.computed-tab').removeClass('active');
  $('.computed-tab').hide();
  $("#lat").prop('readonly', false);
  $("#long").prop('readonly', false);
  $("#elev").prop('readonly', false);
  $("#lane_attributes").hide();
  $(".lane_type_attributes").hide();
  $(".lane_number").hide();
  $(".lane_width").hide();
  $(".descriptive_name").hide();
  $(".lane_type").hide();
  $(".approach_type").hide();
  $(".verified_lat").hide();
  $(".verified_long").hide();
  $(".verified_elev").hide();
  $(".approach_name").hide();
  $(".shared_with").hide();
//    $("#clone").hide()
  $(".btnClone").hide();
//----------------------------------------
  $(".lat").show();
  $(".long").show();
  $(".intersection").show();
  $(".region").show();
  $(".elev").show();
  $(".revision").show();
  
  //Show additional RGA related fields
  hideRGAFields(false);
  $('.road_authority_id').show();
  $('.road_authority_id_type').show();
  
  $(".master_lane_width").show();
  $(".intersection_name").show();
  if (selected == "child"){
    $(".intersection-info-tab").find('a:contains("Speed Limits")').text('Intersection Info');
    $('.layer').show();
    $('.lane-speed-text').hide();		
    $('.intersection-info-tab').show();
    $(".velocity").show();
  }
//----------------------------------------
  if(feature.get("marker").name != "Reference Point Marker"){
    $(".selection-panel").text('Verified Point Configuration');
    $("#lat").prop('readonly', true);
    $("#long").prop('readonly', true);
    $("#elev").prop('readonly', true);
    $(".intersection").hide();
    $(".region").hide();
    $(".verified_lat").show();
    $(".verified_long").show();
    $(".verified_elev").show();
    $(".revision").hide();
    $(".master_lane_width").hide();
    $(".intersection_name").hide();
    $(".approach_name").hide();
    $('.intersection-info-tab').hide();
    hideRGAFields(true);
    $('.road_authority_id').hide();
    $('.road_authority_id_type').hide();
  }
        
  if(feature.get("marker").name == "Reference Point Marker"){    
      //Enable or disable rga fields on reference point marker depend on whether current RGA toggle is enabled/disabled.
      enable_rga_fields(rgaEnabled);
  }

  $('#revision').val(feature.get("revisionNum"));
  if (! feature.get("elevation")){
    $("#elev").val("");
  } else {
    $("#elev").val(feature.get("elevation"));
  }

  if (! feature.get("verifiedElev")){
    $("#verified_elev").val("");
  } else {
    $("#verified_elev").val(feature.get("verifiedElev"));
  }
  
  if (! feature.get("masterLaneWidth")){
    $("#master_lane_width").val("366");
  } else {
    $("#master_lane_width").val(feature.get("masterLaneWidth"));
  }

  if (! feature.get("layerID")){
      $("#layer").val("1");
  } else {
      $("#layer").val( feature.get("layerID"));
  }

  if ( feature.get("intersectionName") ){
    $("#intersection_name").val(feature.get("intersectionName"));
  }

  if (! feature.get("regionID")){
      $("#region").val("");
  } else {
      $("#region").val(feature.get("regionID"));
  }
  
  if (! feature.get("majorVersion")){
      $("#major_version").val("");
  } else {
      $("#major_version").val(feature.get("majorVersion"));
  }
  
  if (! feature.get("minorVersion")){
      $("#minor_version").val("");
  } else {
      $("#minor_version").val(feature.get("minorVersion"));
  }
  
  if (!feature.get("roadAuthorityId")){
      $("#road_authority_id").val("");
  } else {
      $("#road_authority_id").val(feature.get("roadAuthorityId"));
  }

  if (! feature.get("roadAuthorityIdType")){
      $("#road_authority_id_type").val("");
  } else {
      $("#road_authority_id_type").val(feature.get("roadAuthorityIdType"));
  }
  
  if (! feature.get("mappedGeometryId")){
      $("#mapped_geometry_id").val("");
  } else {
      $("#mapped_geometry_id").val(feature.get("mappedGeometryId"));
  }
  
  if (! feature.get("contentVersion")){
      $("#content_version").val("");
  } else {
      $("#content_version").val(feature.get("contentVersion"));
  }
  
  if (! feature.get("contentDateTime")){
      $("#content_date_time").val("");
  } else {
      $("#content_date_time").val(feature.get("contentDateTime"));
  }

  if (selected == "child"){
  if(feature.get("marker").name != "Reference Point Marker") {
    $('.btnDone').prop('disabled', true);
  } else {
    $('.btnDone').prop('disabled', false);
  }
      $('.intersection-btn').prop('disabled', false);
      $('.btnClose').prop('readonly', false);
  } else {
      $('.btnDone').prop('disabled', false);
  }
  
  if (! feature.get("speedLimitType")) {
    //  removeSpeedForm();
    //  addSpeedForm();
  } else {
    //  rebuildSpeedForm(feature.get("speedLimitType"));
  }

  $("#attributes").show();
}


async function populateRefWindow(feature, lat, lon)
{
  getNearestIntersectionJSON(feature, lat, lon);

	if(!feature.get("elevation")){
    let elev = getElev(lat, lon);
    
    if (!feature.get("elevation")?.value) {
      $('#elev').val(elev);
    }
  }
    
  if (feature.get("verifiedElev")){
    $('#verified_elev').val(feature.get("verifiedElev"));
  } else {
    //If verified elevation does not exist in feature, update it with new elevation value
    $('#verified_elev').val(elev);
  }
  
	if (feature.get("verifiedLat")){
		$('#verified_lat').val(feature.get("verifiedLat"));	
	} else {
		$('#verified_lat').val(lat);
  }
  
	if (feature.get("verifiedLon")){
		$('#verified_long').val(feature.get("verifiedLon"));
	} else {
		$('#verified_long').val(lon);
	}	
}


function toggleLaneTypeAttributes(attribute, values) {
  let laneTypeOptions = [];
  $(".lane_type ul li").each(function() { laneTypeOptions.push($(this).text()) });
	for (let i = 0; i < laneTypeOptions.length; i++) {
		$('.' + laneTypeOptions[i] + '_type_attributes').parent().hide();
	}
	
	updateTypeAttributes(attribute)
	
	if ( $('.' + attribute + '_type_attributes').length === 0 ){
	    $('#' + attribute + '_type_attributes').multiselect({
	        onChange: function(option, checked){
	            updateTypeAttributes(attribute)
	        },
	        maxHeight: 200,
	        buttonClass: attribute + '_type_attributes btn btn-default',
	        buttonText: function(options, select) {
	            if (options.length === 0) {
	                return 'Select '+ attribute + ' Type Attribute'
	            } else if (options.length > 1) {
	                return options.length + ' selected';
	            } else {
	                let labels = [];
	                options.each(function() {
	                    if ($(this).attr('label') !== undefined) {
	                        labels.push($(this).attr('label'));
	                    }
	                    else {
	                        labels.push($(this).html());
	                    }
	                });
	                return labels.join(', ') + '';
	            }
	        }
	    });
	}
	
	$('#' + attribute + '_type_attributes').multiselect('deselectAll', false);
    $('#' + attribute + '_type_attributes').multiselect("refresh");
    $('.' + attribute + '_type_attributes').parent().show();
    $("label[for='lane_type_attributes']").show();
    $(".lane_type_attributes").show();
}

function updateSharedWith(){
    sharedWith_object = $('#shared_with option:selected').map(function(a, item){return item.value;})
}

function updateTypeAttributes(attribute) {
	// typeAttributeName = attribute;
	// typeAttribute_object = $('#' + attribute + '_type_attributes option:selected').map(function(a, item){return item.value;})
}


function removeLaneAttributes( attribute ) {
  let attrId = parseInt(attribute.id.match(/(\d+)$/)[0], 10);
  if( tmp_lane_attributes[attrId] ) {
      delete tmp_lane_attributes[attrId];
      return
  }
  if( selectedMarker.attributes['lane_attributes'] )
      if( selectedMarker.attributes['lane_attributes'][attrId] )
          delete selectedMarker.attributes['lane_attributes'][attrId];
}

function updateDisplayedLaneAttributes( feature ){
  removeDisplayedLaneAttributes();
  let laneAttributes = feature.get('lane_attributes');
  if( laneAttributes == null || $.isEmptyObject(laneAttributes)) {
      return;
  }

  $('#attr_droppable').find('p').remove();
  for( let attribute in laneAttributes ) {
      let img = '<img id="lane_img_'+ laneAttributes[attribute].id +'" class="dragged-img" src="'+ laneAttributes[attribute].img_src +'">';
      $('#attr_droppable').append(img);
  }


  $('#attr_droppable').find('img').each(function() {
      makeDraggable( $(this) )
  });
}


function removeDisplayedLaneAttributes(){
  $('#attr_droppable').empty().append(
      '<h4 class="list-group-item-heading">Lane Attributes</h4>' +
      '<p class="help-block text-center">drop content here</p>');
}

/**
 * Purpose: makes the attributes dragable
 * @params  element
 * @event allows icon to be dragged onto the other sidebar
 */
function makeDraggable( selector ) {
  selector.draggable({
      appendTo: 'body', containment: 'body', zIndex: 150000, cursorAt: {left:25, top:25},
      revert: function() {
          return 'invalid';
      },
      start: function(e, ui) {
        $('#hidden-drag').removeClass('hidden');
        $('.trash_droppable').toggleClass("hidden");
      },
      stop: function(e) {
        $('#hidden-drag').addClass('hidden');
        $('.trash_droppable').toggleClass("hidden");
      }
  });
}
/**
 * Purpose: populate reference point modal window
 * @params  the feature and it's metadata
 * @event loads the appropriate data - elevation is doen through ajax
 */

function populateAttributeWindow(lat, lon) {
	$('#lat').val(lat);
	$('#long').val(lon);
}


function rebuildConnections(connections) {
  //TODO clear if empty rows
  $('#tab_intersects > tbody').empty();
  if (connections === null || connections === undefined || connections.length < 1) {
      addRow(null, null);
  } else {
      for(var i = 0; i < connections.length; i++) {
          addRow(null, connections[i]);
      }
  }
}


function addRow(readOnly, valueSets) {
  let rowHtml;
  $.get("js/row.html", function (data) {
    rowHtml = data;
  })
  $('#tab_intersects tbody').append(rowHtml);
  numRows++;
  changeRow('New', numRows, readOnly, valueSets);
  populateConnectionIdDropdown(numRows)
  makeDroppable(numRows);
}


/*****************************************
 * Lane connection layer table
 * Methods
 *****************************************/
function changeRow(oldVal, newVal, readOnly, valueSets) {
  $('#row' + oldVal).attr('id', 'row' + newVal);
  $('#connectionId' + oldVal).attr('id', 'connectionId' + newVal);
  $('input[name="remoteID' + oldVal + '"]').attr('name', 'remoteID' + newVal);
  $('input[name="toLane' + oldVal + '"]').attr('name', 'toLane' + newVal);
  $('input[name="signal_id' + oldVal + '"]').attr('name', 'signal_id' + newVal);
  $('#maneuvers' + oldVal).attr('id', 'maneuvers' + newVal);
  $('#attr_droppable' + oldVal).attr('id', 'attr_droppable' + newVal);
  $('#delete' + oldVal).attr('onclick', 'deleteRow(' + newVal + ')')
      .attr('id', 'delete' + newVal);
  if (readOnly && readOnly !== undefined) {
      for (var i = 0; i < readOnly.length; i++) {
          $('input[name="' + readOnly[i] + newVal + '"').prop('readonly', true);
      }
  }
  if (valueSets && valueSets !== undefined) {
      for (var set in valueSets) {
          if (valueSets.hasOwnProperty(set)) {
              if (set === 'maneuvers') {
                  for (var k = 0; k < valueSets[set].length; k++) {
                      for(var j = 0; j < lane_attributes.length; j++) {
                          if (lane_attributes[j].id.toString() === valueSets[set][k]){
                              addLaneManeuversToContainer($('#attr_droppable' + newVal),
                                  '<img id="lane_img_'+ newVal +'_' + lane_attributes[j].id + '" class="dragged-img" src="'+ lane_attributes[j].img_src +'">');
                          }
                      }
                  }
              } else if (set === 'connectionId'){
                $('#connectionId' + newVal + ' .dropdown-toggle').html(valueSets[set]+'<span class="caret"></span>')
              } else {
                  $('input[name="' + set + newVal + '"').attr('value', valueSets[set]);
              }
          }
      }
  }
}


function populateConnectionIdDropdown(rowId) {
	// Add an empty option at the top. Use a non-breaking space(uni-code 200b), to force the
	// entry in the dropdown menu to maintain the same height as the rest of the list.
	$('#connectionId' + rowId + ' .dropdown-menu').append(
    		$('<li><a href="#">\u200b</a></li>').click(function(){
    			    var selText = $(this).children('a').text();
    			    $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+'<span class="caret"></span>');
    		    }))
    for(let i = 1; i <= 255; i++) {
        $('#connectionId' + rowId + ' .dropdown-menu').append(
        		$('<li><a href="#">' + i + '</a></li>').click(function(){
        			    var selText = $(this).children('a').text();
        			    $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+'<span class="caret"></span>');
        		    }))
    }
}


/**
 * Purpose: makes the attributes droppable
 * @params  id of element
 * @event allows icon to be dropped onto the other sidebar
 */

function makeDroppable (id){
  //--- Drop Functionality
  var containerName = "attr_droppable";
  if (id !== null && id !== undefined){
      containerName += id;
  }
  $('#' + containerName).droppable({
      accept: ".drag-lane-img",
      activate: function( event, ui ) {
          $(this).find( "p" ).html( "Drop attributes here" );
      },
      over: function( event, ui ) {
          $(this).find( "p" ).html( "Drop it!" );
      },
      drop: function( event, ui ) {
          $(this).find( "p").remove();
          var container = $(this);
          var attr = $(ui.helper.children());
          if (id !== null && id !== undefined) {
              attr[0].id = "lane_img_" + id + "_" + attr[0].id.match(/\d+$/g)[0];
          }
          console.log(attr);
          makeDraggable( attr );

          if (containerName === "attr_droppable"){
              addLaneAttributeToContainer( container, attr[0] );
          } else {
              addLaneManeuversToContainer( container, attr[0]);
          }
      },
      out: function( event, ui ) {
          $(this).find( "p" ).html( "Come back!" );
      },
      deactivate: function( event, ui ) {
          $(this).find( "p" ).html( "Drop attributes here" );
      }
  });

  $('.trash_droppable').droppable({
      accept: ".dragged-img",
      drop: function( event, ui ) {
          var attr = $(ui.helper)[0];
          attr.remove();
          removeLaneAttributes(attr);
      }
  });
}


function toggle(lanes, vectors, laneMarkers, laneWidths, laneConnections) {
  let divPosition = $("#sidebar").offset();
  if(divPosition.left < 0){
      $("sidebar").show();
      $("#sidebar").animate({"left":12},1000);
  } else {
      $("#sidebar").animate({"left":-800},1000);
      $("sidebar").hide();
      toggleControlsOn('none', lanes, vectors, laneMarkers, laneWidths, false);
      laneConnections.getSource().clear();
  }
}

function toggleLanes(lanes, laneMarkers){
  if(document.getElementById("laneIcon").className == "fa fa-square-o"){
      lanes.setVisible(true);
      laneMarkers.setVisible(true);
      document.getElementById("laneIcon").className = "fa fa-check-square-o";
  } else {
      lanes.setVisible(false);
      laneMarkers.setVisible(false);
      document.getElementById("laneIcon").className = "fa fa-square-o";
  }
}

function toggleBars(box){
  if(document.getElementById("boxIcon").className == "fa fa-square-o"){
      box.setVisible(true);
      document.getElementById("boxIcon").className = "fa fa-check-square-o";
  } else {
      box.setVisible(false);
      document.getElementById("boxIcon").className = "fa fa-square-o";
  }
}

function togglePoints(vectors){
  if(document.getElementById("pointIcon").className == "fa fa-square-o"){
      vectors.setVisible(true);
      document.getElementById("pointIcon").className = "fa fa-check-square-o";
  } else {
      vectors.setVisible(false);
      document.getElementById("pointIcon").className = "fa fa-square-o";
  }
}


function toggleWidthArray(lanes, vectors, laneWidths) {
  let isNegative = {"value": false, "node": "", "lane": ""};
  if (laneWidths.getSource().getFeatures().length == 0) {
    let masterWidth;
    let vectorFeatures = vectors.getSource().getFeatures();
    for (let vectorFeature of vectorFeatures) {
      console.log(vectorFeature)
      if (vectorFeature.get("marker").name == "Reference Point Marker") {
          masterWidth = parseFloat(vectorFeature.get("masterLaneWidth"));
      }
    }
    let laneFeatures = lanes.getSource().getFeatures();
    for (let i = 0; i < laneFeatures.length; i++) {
      let widthList = [];
      let widthDeltaTotal = 0;
      let flipped = false;

      for (let j = 0; j < laneFeatures[i].getGeometry().getCoordinates().length; j++) {
        let point1 = '';
        let point2 = '';
        if (j < laneFeatures[i].getGeometry().getCoordinates().length - 1) {
            if (laneFeatures[i].getGeometry().getCoordinates()[j][0] == laneFeatures[i].getGeometry().getCoordinates()[j + 1][0] && laneFeatures[i].getGeometry().getCoordinates()[j][1] == laneFeatures[i].getGeometry().getCoordinates()[j + 1][1]) {
                j++; //to prevent dots that are the exact same.
            }
        }

        if (j < laneFeatures[i].getGeometry().getCoordinates().length - 1) {
          point1 = new ol.proj.toLonLat(laneFeatures[i].getGeometry().getCoordinates()[j]);
          point2 = new ol.proj.toLonLat(laneFeatures[i].getGeometry().getCoordinates()[j+1]);
        } else {
          point1 = new ol.proj.toLonLat(laneFeatures[i].getGeometry().getCoordinates()[j]);
          if (laneFeatures[i].getGeometry().getCoordinates()[j][0] == laneFeatures[i].getGeometry().getCoordinates()[j-1][0] && laneFeatures[i].getGeometry().getCoordinates()[j][1] == laneFeatures[i].getGeometry().getCoordinates()[j-1][1]) {
            point2 = new ol.proj.toLonLat(laneFeatures[i].getGeometry()[j - 2]);
            flipped = true;
          } else {
            point2 = new ol.proj.toLonLat(laneFeatures[i].getGeometry().getCoordinates()[j - 1]);
          }
        }
        let widthDelta = parseFloat(laneFeatures[i].get("laneWidth")[j]);
        if (isNaN(widthDelta) || widthDelta == null || typeof widthDelta == "undefined") {
            widthDelta = 0
        }

        widthDeltaTotal = widthDeltaTotal + widthDelta;

        if (masterWidth + widthDeltaTotal < 0){
            console.log(masterWidth + widthDeltaTotal)
            isNegative = {"value": true, "node": j+1, "lane": i};
            widthDeltaTotal = 0 - masterWidth;
        }

        let inverse = inverseVincenty(point1[1], point1[0], point2[1], point2[0]);

        let direct1 = directVincenty(point1[1], point1[0], inverse.bearing + 90, (((masterWidth + widthDeltaTotal) / 2) / 100));
        let direct2 = directVincenty(point1[1], point1[0], inverse.bearing - 90, (((masterWidth + widthDeltaTotal) / 2) / 100));

        let newPoint1 = new ol.geom.Point(ol.proj.fromLonLat([direct1.lon, direct1.lat]));
        let newPoint2 = new ol.geom.Point(ol.proj.fromLonLat([direct2.lon, direct2.lat]));

        if (j == laneFeatures[i].getGeometry().getCoordinates().length - 1) {
            j++; //flips the j value since it's the last lane point and we need to build in reverse
        }

        if (isOdd(j) && !flipped) {
          widthList.push(newPoint1, newPoint2);
          let coordinates = widthList.map(point => point.getCoordinates());
          let widthBox = new ol.geom.LinearRing(coordinates);
          laneWidths.getSource().addFeature(new ol.Feature(new ol.geom.Polygon([widthBox.getCoordinates()])));
          widthList = [];
          widthList.push(newPoint1, newPoint2);
        } else {
          widthList.push(newPoint2, newPoint1);
          let coordinates = widthList.map(point => point.getCoordinates());
          let widthBox =new  ol.geom.LinearRing(coordinates);
          laneWidths.getSource().addFeature(new ol.Feature(new ol.geom.Polygon([widthBox.getCoordinates()])));
          widthList = [];
          widthList.push(newPoint2, newPoint1);
          flipped = false;
        }
      }
    }
  } else {
      laneWidths.getSource().clear();
  }

  if (isNegative.value){
      alert("Width deltas sum to less than zero on lane " + laneFeatures[isNegative.lane].attributes.laneNumber + " at node " + isNegative.node + "!");
  }
}

/*!
 * JavaScript function to calculate the geodetic distance between two points specified by latitude/longitude using the Vincenty inverse formula for ellipsoids.
 *
 * Taken from http://movable-type.co.uk/scripts/latlong-vincenty.html and optimized / cleaned up by Mathias Bynens <http://mathiasbynens.be/>
 * Based on the Vincenty direct formula by T. Vincenty, “Direct and Inverse Solutions of Geodesics on the Ellipsoid with application of nested equations”, Survey Review, vol XXII no 176, 1975 <http://www.ngs.noaa.gov/PUBS_LIB/inverse.pdf>
 *
 * @param   {Number} lat1, lon1: first point in decimal degrees
 * @param   {Number} lat2, lon2: second point in decimal degrees
 * @returns {Number} distance in metres between points
 */
function inverseVincenty(lat1, lon1, lat2, lon2) {
  let a = 6378137,
      b = 6356752.314245,
      f = 1 / 298.257223563 // WGS-84 ellipsoid params
      let φ1 = toRad(lat1), λ1 = toRad(lon1);
      let φ2 = toRad(lat2), λ2 = toRad(lon2);

      let L = λ2 - λ1;
      let tanU1 = (1-f) * Math.tan(φ1), cosU1 = 1 / Math.sqrt((1 + tanU1*tanU1)), sinU1 = tanU1 * cosU1;
      let tanU2 = (1-f) * Math.tan(φ2), cosU2 = 1 / Math.sqrt((1 + tanU2*tanU2)), sinU2 = tanU2 * cosU2;

      let sinλ, cosλ, sinSqσ, sinσ=0, cosσ=0, σ=0, sinα, cosSqα=0, cos2σM=0, C;

      let λ = L, λʹ, iterations = 0, antimeridian = Math.abs(L) > Math.PI;
      do {
          sinλ = Math.sin(λ);
          cosλ = Math.cos(λ);
          sinSqσ = (cosU2*sinλ) * (cosU2*sinλ) + (cosU1*sinU2-sinU1*cosU2*cosλ) * (cosU1*sinU2-sinU1*cosU2*cosλ);
          if (sinSqσ == 0) break; // co-incident points
          sinσ = Math.sqrt(sinSqσ);
          cosσ = sinU1*sinU2 + cosU1*cosU2*cosλ;
          σ = Math.atan2(sinσ, cosσ);
          sinα = cosU1 * cosU2 * sinλ / sinσ;
          cosSqα = 1 - sinα*sinα;
          cos2σM = (cosSqα != 0) ? (cosσ - 2*sinU1*sinU2/cosSqα) : 0; // equatorial line: cosSqα=0 (§6)
          C = f/16*cosSqα*(4+f*(4-3*cosSqα));
          λʹ = λ;
          λ = L + (1-C) * f * sinα * (σ + C*sinσ*(cos2σM+C*cosσ*(-1+2*cos2σM*cos2σM)));
          let iterationCheck = antimeridian ? Math.abs(λ)-Math.PI : Math.abs(λ);
          if (iterationCheck > Math.PI) throw new Error('λ > π');
      } while (Math.abs(λ-λʹ) > 1e-12 && ++iterations<1000);
      if (iterations >= 1000) throw new Error('Formula failed to converge');

      let uSq = cosSqα * (a*a - b*b) / (b*b);
      let A = 1 + uSq/16384*(4096+uSq*(-768+uSq*(320-175*uSq)));
      let B = uSq/1024 * (256+uSq*(-128+uSq*(74-47*uSq)));
      let Δσ = B*sinσ*(cos2σM+B/4*(cosσ*(-1+2*cos2σM*cos2σM)-
          B/6*cos2σM*(-3+4*sinσ*sinσ)*(-3+4*cos2σM*cos2σM)));

      let s = b*A*(σ-Δσ);

      let α1 = Math.atan2(cosU2*sinλ,  cosU1*sinU2-sinU1*cosU2*cosλ);
      let α2 = Math.atan2(cosU1*sinλ, -sinU1*cosU2+cosU1*sinU2*cosλ);

      α1 = (α1 + 2*Math.PI) % (2*Math.PI); // normalise to 0..360
      α2 = (α2 + 2*Math.PI) % (2*Math.PI); // normalise to 0..360

      return {
          distance:       s,
          bearing: s==0 ? NaN : toDeg(α1)
      };
};


/*!
* JavaScript function to calculate the destination point given start point latitude / longitude (numeric degrees), bearing (numeric degrees) and distance (in m).
*
* Taken from http://movable-type.co.uk/scripts/latlong-vincenty-direct.html and optimized / cleaned up by Mathias Bynens <http://mathiasbynens.be/>
* Based on the Vincenty direct formula by T. Vincenty, “Direct and Inverse Solutions of Geodesics on the Ellipsoid with application of nested equations”, Survey Review, vol XXII no 176, 1975 <http://www.ngs.noaa.gov/PUBS_LIB/inverse.pdf>
*/

function directVincenty(lat1, lon1, brng, dist) {
  let a = 6378137;
  let b = 6356752.3142;
  let f = 1 / 298.257223563;// WGS-84 ellipsiod
  let s = dist;
  let alpha1 = toRad(brng);
  let sinAlpha1 = Math.sin(alpha1);
  let cosAlpha1 = Math.cos(alpha1);
  let tanU1 = (1 - f) * Math.tan(toRad(lat1));
  let cosU1 = 1 / Math.sqrt((1 + tanU1 * tanU1)), sinU1 = tanU1 * cosU1;
  let sigma1 = Math.atan2(tanU1, cosAlpha1);
  let sinAlpha = cosU1 * sinAlpha1;
  let cosSqAlpha = 1 - sinAlpha * sinAlpha;
  let uSq = cosSqAlpha * (a * a - b * b) / (b * b);
  let A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
  let B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
  let sigma = s / (b * A);
  let sigmaP = 2 * Math.PI;
  let sinSigma;
  let cos2SigmaM;
  let cosSigma;
  let deltaSigma;
  while (Math.abs(sigma - sigmaP) > 1e-12) {
    cos2SigmaM = Math.cos(2 * sigma1 + sigma);
    sinSigma = Math.sin(sigma);
    cosSigma = Math.cos(sigma);
    deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) - B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
    sigmaP = sigma;
    sigma = s / (b * A) + deltaSigma;
  };
  let tmp = sinU1 * sinSigma - cosU1 * cosSigma * cosAlpha1
  let lat2 = Math.atan2(sinU1 * cosSigma + cosU1 * sinSigma * cosAlpha1, (1 - f) * Math.sqrt(sinAlpha * sinAlpha + tmp * tmp))
  let lambda = Math.atan2(sinSigma * sinAlpha1, cosU1 * cosSigma - sinU1 * sinSigma * cosAlpha1)
  let C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha))
  let L = lambda - (1 - C) * f * sinAlpha * (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
  let revAz = Math.atan2(sinAlpha, -tmp); // final bearing
  return {lat: toDeg(lat2), lon: lon1 + toDeg(L)};
};

function toRad(degrees){
  let pi = Math.PI;
  return degrees * (pi/180);
}

function toDeg(radians){
  let pi = Math.PI;
  return radians * (180/pi);
}

export {
  getCookie,
  isOdd,
  enable_rga_fields,
  add_rga_fields_validation,
  referencePointWindow,
  populateRefWindow,
  hideRGAFields,
  updateSharedWith,
  updateTypeAttributes,
  toggleLaneTypeAttributes,
  populateAttributeWindow,
  toggle,
  toggleBars,
  toggleLanes,
  togglePoints ,
  toggleWidthArray,
  directVincenty,
  inverseVincenty,
  toDeg,
  toRad,
  updateDisplayedLaneAttributes,
  removeLaneAttributes,
  rebuildConnections,
  numRows,
  makeDraggable,
  makeDroppable
}