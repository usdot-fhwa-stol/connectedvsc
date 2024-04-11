/**
 * Created by martzth on 3/20/2015.
 * Updated 3/2017 by martzth
 */

/**
 * DEFINE GLOBAL VARIABLES
 */

    var proj_name, host;
    var message_json_input, message_hex_input, message_text_input;
    var message_status_div;
    var message;

/**
 * Define functions that must bind on load
 */

$(document).ready(function() {
    proj_name = window.location.pathname.split('/')[1];
    host = window.location.host;

    message_json_input = $('#message_json');
    message_hex_input = $('#message_hex');
    message_text_input = $('#message_text');
    message_status_div = $('#message_status');


    /**
     * Purpose: change deposit state
     * @params: click event
     * @event: checks/unchecks deposit on msg type
     */

    $("#message_deposit_modal").on('shown.bs.modal', function (e) {
        resetMessageForm();
        if( !errorCheck() ) {
            message = createMessageJSON();
            message_json_input.val(JSON.stringify(message, null, 2))
        } else {
            $("#message_deposit").prop('disabled', true);
        }
    });

    $('#message_deposit_modal :checkbox').click(function () {
        var $this = $(this);

        if ($this.is(':checked')) {
            $("#ttl").show();
            $("#message_deposit").html('Encode & Deposit')
        } else {
            $("#ttl").hide();
            $("#message_deposit").html('Encode')
        }
    });

    $('#message_type').on("change", function(){
        var msg_type = $('#message_type').val();
        $('#deposit_check').prop('checked', false);
        $("#ttl").hide();
        $("#message_deposit").html('Encode')

        if( msg_type !== "ASD"){
            $('#deposit_check').prop('disabled', true);
        } else {
            $('#deposit_check').prop('disabled', false);
        }
        resetMessageForm();
        message_json_input.val(JSON.stringify(createMessageJSON(), null, 2));
    });

    $('#node_offsets').on("change", function(){
        setCookie("tim_node_offsets", $('#node_offsets').val(), 365);
        resetMessageForm();
        message_json_input.val(JSON.stringify(createMessageJSON(), null, 2));
    });

    $('#enable_elevation').on("change", function(){
        setCookie("tim_enable_elevation", $('#enable_elevation').is(":checked"), 365);
        resetMessageForm();
        message_json_input.val(JSON.stringify(createMessageJSON(), null, 2));
    });


    /**
     * Purpose: to allow deposit of message (ASD only)
     * @params: message header (BBox + ttl)
     * @event: attaches header to message and POSTS to server
     */

    $('#message_deposit').click(function () {
        var message_json = message_json_input.val();

        if (document.getElementById('deposit_check').checked) {

            if (area.features.length == 0) {
                $('#alert_placeholder').html('<div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span>' + "Cannot deposit without an applicable region defined." + '</span></div>')
            } else {

                var time = $('#time').val();

                var spatHeader = {
                    "timeToLive": time
                }

                message.deposit = spatHeader;
                message_json = JSON.stringify(message, null, 2);
            }
        }

        $.ajax({
            type: "POST",
            url: "/" + proj_name + "/builder/messages/travelerinfo",
            contentType: "text/plain",
            data: message_json,
            success: function (result) {
                console.log("success: ", result);
                setMessageResult(true, result.hexString, "hex");
                setMessageResult(true, result.readableString, "text");
            },
            error: function (xhr, status, error) {
                console.log("fail: ", xhr.responseText);
                setMessageResult(false, xhr.responseText);
            }
        });
    });
});

function setMessageResult( success, message, type ){

    if( success ) {
        message_status_div.removeClass('has-error').addClass('has-success');
    }
    else {
        message_status_div.removeClass('has-success').addClass('has-error');
    }

    if(type == "hex"){
        message_hex_input.val( message );
        $('.message_size').text((message.length/2) + " bytes");
    } else {
        message_text_input.val( message );
    }
}


/**
 * Purpose: reset message form on close
 * @params: click event
 * @event: clears boxes of messages
 */

$('.close').click(function() {
    resetMessageForm();
});

function resetMessageForm() {
    $('#alert_placeholder').html("");
    $("#message_deposit").prop('disabled', false);
    message_json_input.val("")
    message_hex_input.val("")
    message_text_input.val("")
    $('.message_size').text("");
    message_status_div.removeClass('has-error has-success');
}


/**
 * Purpose: create JSON from map elements
 * @params: map layers and elements
 * @event: builds JSON message for deposit
 * Note: each variable is stored in the feature object model
 */

function createMessageJSON()
{
    var spatMessage = {};

    //Feature object models
    var laneFeat = lanes.features;
    var polyFeat = polygons.features;

    var regionsArray = {"regions":[]};
    var nodesArray = {"regionNodes":[]};

    var regionArray = regionsArray["regions"];
    var nodeArray = nodesArray["regionNodes"];

    //LANES
    for(var j=0; j< laneFeat.length; j++){

            for(var m=0; m< lanes.features[j].geometry.components.length; m++){
                var latlon = new OpenLayers.LonLat(lanes.features[j].geometry.components[m].x,lanes.features[j].geometry.components[m].y).transform(toProjection, fromProjection)
                nodeArray[m] = {
                    "nodeNumber": m,
                    "nodeLat": latlon.lat,
                    "nodeLong": latlon.lon,
                    "nodeElevation": lanes.features[j].attributes.elevation[m].value,
                    "laneWidth": lanes.features[j].attributes.laneWidth[m]
                }
            }

            var ext; //since extent is an optional field, try catch

            try{
                ext = getExtent(lanes.features[j].attributes.extent);
            } catch (err){
                ext = "";
            }

            regionArray[j] = {
                "regionType": "lane",
                "laneNodes": nodeArray,
                "extent": ext
            }

        nodeArray = [];
    }

    //POLYGONS
    for(var j=0; j< polyFeat.length; j++){
    	// Subtract one from the components length because components includes a duplicate
    	// first node at the end of the array to "close" the polygon
    	var max = polygons.features[j].geometry.components[0].components.length-1;
        if(polygons.features[j].attributes.title == "circle"){

            circle_bounds = $.extend(true, {}, polygons.features[j].geometry.bounds);
            circle_bounds = circle_bounds.transform(toProjection, fromProjection);
            var minX = circle_bounds.left;
            var minY = circle_bounds.bottom;
            var maxX = circle_bounds.right;
            var maxY = circle_bounds.top;

            //calculate the center coordinates
            var startX = (minX + maxX) / 2;
            var startY = (minY + maxY) / 2;

            nodeArray = [
                {
                    "nodeLat": startY,
                    "nodeLong": startX
                },
                {
                    "nodeLat": maxY,
                    "nodeLong": startX
                }
            ]

            regionArray[j] = {
                "regionType": "circle",
                "radius": $('#radius').val(),
                "laneNodes": nodeArray,
                "extent": ext
            }
        } else {
            for (var m = 0; m < max; m++) {
                var latlonP = new OpenLayers.LonLat(polygons.features[j].geometry.components[0].components[m].x, polygons.features[j].geometry.components[0].components[m].y).transform(toProjection, fromProjection)
                nodeArray[m] = {
                    "nodeNumber": m,
                    "nodeLat": latlonP.lat,
                    "nodeLong": latlonP.lon,
                    "nodeElevation": polygons.features[j].attributes.elevation[m].value
                }
            }

            regionArray[j] = {
                "regionType": "region",
                "laneNodes": nodeArray,
                "extent": ext
            }
        }

        nodeArray = [];
    }

    //CENTRAL MARKER AND VERIFIED POINT
    for ( var f = 0; f < vectors.features.length; f++) {
        var feature = vectors.features[f];
        if (vectors.features[f].attributes.marker.type == "TIM") {

            for(a=0; a<(feature.attributes.content).length; a++){
                if( (feature.attributes.content)[a] == 0 ){
                    (feature.attributes.content)[a] = (12544 + Number(feature.attributes.speedLimit)).toString();
                }
            }

            var anchor = {
                "name": feature.attributes.marker.name,
                "referenceLat": feature.attributes.LonLat.lat,
                "referenceLon": feature.attributes.LonLat.lon,
                "referenceElevation": feature.attributes.elevation,
                "masterLaneWidth": feature.attributes.masterLaneWidth,
                "sspTimRights": feature.attributes.sspTimRights,
                "packetID": feature.attributes.packetID,
                "content": feature.attributes.content,
                "sspTypeRights": feature.attributes.sspTypeRights,
                "sspContentRights": feature.attributes.sspContentRights,
                "sspLocationRights": feature.attributes.sspLocationRights,
                "direction": (feature.attributes.direction).substring(1,2),
                "mutcd": (feature.attributes.mutcd).substring(1,2),
                "infoType": (feature.attributes.infoType).substring(1,2),
                "priority": feature.attributes.priority,
                "startTime": feature.attributes.startTime,
                "endTime": feature.attributes.endTime,
                "heading": getHeading(feature.attributes.heading)
            }
        }

        if (vectors.features[f].attributes.marker.type == "VER") {

            var verified = {
                "verifiedMapLat": feature.attributes.LonLat.lat,
                "verifiedMapLon": feature.attributes.LonLat.lon,
                "verifiedMapElevation": feature.attributes.elevation,
                "verifiedSurveyedLat": feature.attributes.verifiedLat,
                "verifiedSurveyedLon": feature.attributes.verifiedLon,
                "verifiedSurveyedElevation": feature.attributes.verifiedElev
            }

        }

    }

    if (area.features.length != 0) {
        //BOUNDING BOX
        var nwPoint = new OpenLayers.Geometry.Point(area.features[0].geometry.getVertices()[1].x, area.features[0].geometry.getVertices()[1].y).transform(toProjection, fromProjection);
        var sePoint = new OpenLayers.Geometry.Point(area.features[0].geometry.getVertices()[3].x, area.features[0].geometry.getVertices()[3].y).transform(toProjection, fromProjection);
        var nwLat = nwPoint.y;
        var nwLon = nwPoint.x;
        var seLat = sePoint.y;
        var seLon = sePoint.x;

        var applicableRegion = {
            "nwLat": nwLat,
            "nwLon": nwLon,
            "seLat": seLat,
            "seLon": seLon
        };

        spatMessage.applicableRegion = applicableRegion;
    }


    //Put it all together
    spatMessage.regions = regionArray;
    spatMessage.anchorPoint = anchor;
    spatMessage.verifiedPoint = verified;
    spatMessage.messageType = $("#message_type").val();
    spatMessage.nodeOffsets = $("#node_offsets").val();
    spatMessage.enableElevation = $("#enable_elevation").is(":checked");

    return spatMessage;
}

/**
 * Purpose: pretty terrible error check
 * @params: DOM elements
 * @event: just checking that a marker exists, etc so that the message can build appropriately
 */

function errorCheck(){

    var status = false; //false means there are no errors

    if (lanes.features.length == 0 && polygons.features.length == 0){
        $('#alert_placeholder').html('<div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span>' + "Cannot deposit without a region defined." + '</span></div>')
        status = true;
    }

    if (vectors.features.length != 2){
        $('#alert_placeholder').html('<div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span>' + "Missing anchor or verified points." + '</span></div>')
        status = true;
    }

    try{
        for ( var f = 0; f < vectors.features.length; f++) {
            var feature = vectors.features[f];
            if (vectors.features[f].attributes.marker.type == "TIM") {

                if (feature.attributes.startTime == "" || feature.attributes.startTime == undefined || feature.attributes.endTime == "" || feature.attributes.endTime == undefined){
                    $('#alert_placeholder').html('<div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span>' + "Set start and end time on the anchor point." + '</span></div>')
                    status = true;
                }

                if (feature.attributes.content[0].codes.length == 0 && feature.attributes.content[0].text == ""){
                    $('#alert_placeholder').html('<div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span>' + "ITIS information is required." + '</span></div>')
                    status = true;
                }

                if (feature.attributes.priority == "" || feature.attributes.priority == undefined){
                    $('#alert_placeholder').html('<div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span>' + "Missing priority level." + '</span></div>')
                    status = true;
                }
                if (feature.attributes.mutcd == "" || feature.attributes.mutcd == undefined){
                    $('#alert_placeholder').html('<div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span>' + "Missing mutcd codes." + '</span></div>')
                    status = true;
                }
            }
        }
    } catch (err){
        $('#alert_placeholder').html('<div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span>' + "Missing one or more fields on anchor or verified point." + '</span></div>')
        status = true;
    }

    return status;
}

/**
 * Purpose: figure out which slices are active on the circle
 * @params: headings circle
 * @returns: an array of active slices (headings)
 */

function getHeading(headingsCircle){

    var totalSlices = 0;

    var headingsArray = {"headings":[]};
    var headingArray = headingsArray["headings"];

    for(var i=0; i< headingsCircle.length; i++){
        if (headingsCircle[i].active){
            totalSlices++;
            headingArray.push(i);
        }
    }

    if(totalSlices == 0){
        headingArray = [];
    }

    return headingArray;
}


/**
 * Purpose: gets extent
 * @params: full extent text
 * @returns: extent number
 */

function getExtent(text){
    var result = text.split(")");
    return result[0].slice(1,result[0].length)
}
