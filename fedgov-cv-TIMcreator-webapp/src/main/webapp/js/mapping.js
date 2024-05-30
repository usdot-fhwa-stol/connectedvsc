/**
 * Created by lewisstet on 2/25/2015.
 * Updated 3/2017 by martzth
 */


/**
 * DEFINE GLOBAL VARIABLES
 */

    var map;
    var vectors, lanes, laneMarkers, area, polygons, polyMarkers, radiuslayer, trace, laneWidths;
    var fromProjection, toProjection;
    var temp_lat, temp_lon, selected_marker, selected_layer, selected_marker_limit;
    var mutcd, priority, direction, extent, info_type, ttl;
    var circle_bounds;

    var content = [];
    var elevation_url = 'https://dev.virtualearth.net/REST/v1/Elevation/List?hts=ellipsoid&points=';
    var nodeLaneWidth = [];
    var circles_temp = [];
    var circles_reset = [];

    var bingResolutions = [156543.03390625, 78271.516953125, 39135.7584765625,
        19567.87923828125, 9783.939619140625, 4891.9698095703125,
        2445.9849047851562, 1222.9924523925781, 611.4962261962891,
        305.74811309814453, 152.87405654907226, 76.43702827453613,
        38.218514137268066, 19.109257068634033, 9.554628534317017,
        4.777314267158508, 2.388657133579254, 1.194328566789627,
        0.5971642833948135, 0.29858214169740677, 0.14929107084870338,
        0.07464553542435169];
     var bingServerResolutions = [156543.03390625, 78271.516953125, 39135.7584765625,
        19567.87923828125, 9783.939619140625, 4891.9698095703125,
        2445.9849047851562, 1222.9924523925781, 611.4962261962891,
        305.74811309814453, 152.87405654907226, 76.43702827453613,
        38.218514137268066, 19.109257068634033, 9.554628534317017,
        4.777314267158508, 2.388657133579254, 1.194328566789627,
        0.5971642833948135, 0.29858214169740677];

    const getApiKey = (() => {
        let cachedApiKey = null;
    
        return async function () {
            if (cachedApiKey) {
                return cachedApiKey;
            }
    
            const res = await fetch('/private-resources/js/TIMcreator-webapp-keys.js');
            const text = await res.text();
    
            // Extract the API key from the file content
            const regex = /var\s+apiKey\s*=\s*"([^"]+)"/;
            const match = regex.exec(text);
            cachedApiKey = match?.[1];
    
            if (!cachedApiKey) {
                throw new Error('API key not found in the file');
            }
    
            return cachedApiKey;
        };
    })();

/**
 * Define functions that must bind on load
 */

async function init() {

    //Set initial variables needed to do stuff. :-/
    var d = new Date();
    var t = (d.getTime()).toString();
    circles_temp = JSON.parse(JSON.stringify(circles));
    $('#packet_id').val(t.substr(t.length-9));

    $('option:selected').prop("selected", false)
    $('#deposit_check').removeAttr('checked');
    $('#ttl').hide();
    $('#drawPoly').prop('disabled', false);
    $('#editPoly').prop('disabled', false);
    $('#drawLanes').prop('disabled', false);
    $('#editLanes').prop('disabled', false);
    $('#drawCircle').prop('disabled', false);
    $('#movePoly').prop('disabled', false);


    /*********************************************************************************************************************/
    /**
     * Purpose: create map object which will house bing map tiles
     * @params  openlayers2
     * @event setting all the parameters -> will note certain areas
     *
     * Note: each layer is defined in this section, and layers interact with the sidebar
     * by showing/hiding DOM elements. Also, all data is loaded into the forms via these feature objects
     */

    const apiKey = await getApiKey();

	map = new OpenLayers.Map('map', {
        allOverlays: false,
        fractionalZoom: true,
        controls: [
            new OpenLayers.Control.Navigation({
                dragPanOptions: {
                    enableKinetic: true
                }
            }),
            new OpenLayers.Control.LayerSwitcher(),
            new OpenLayers.Control.Zoom({
                zoomInId: "customZoomIn",
                zoomOutId: "customZoomOut"
            })
        ]});

    fromProjection = new OpenLayers.Projection("EPSG:4326");   // Transform from WGS 1984
    toProjection   = new OpenLayers.Projection("EPSG:3857"); // to Spherical Mercator Projection

    //Sets the standard viewport to Detroit unless overwritten by cookie
    var view_lat = 42.3373873;
    var view_lon = -83.051308;
    var view_zoom = 19;
    if (getCookie("tim_latitude") !== ""){
        view_lat = getCookie("tim_latitude")
    }
    if (getCookie("tim_longitude") !== ""){
        view_lon = getCookie("tim_longitude")
    }
    if (getCookie("tim_zoom") !== ""){
        view_zoom = getCookie("tim_zoom")
    }
    if (getCookie("tim_node_offsets") !== ""){
        $('#node_offsets').val(getCookie("tim_node_offsets"));
    }
    if (getCookie("tim_enable_elevation") !== ""){
        $('#enable_elevation').prop('checked',"true" == getCookie("tim_enable_elevation"));
    } else {
        $('#enable_elevation').prop('checked', true);
    }

    //Set cookie anytime map is moved
    map.events.register("moveend", map, function() {
        var center_point = map.getCenter();
        var center_lonlat = new OpenLayers.LonLat(center_point.lon,center_point.lat).transform(toProjection, fromProjection)
        setCookie("tim_latitude", center_lonlat.lat, 365);
        setCookie("tim_longitude", center_lonlat.lon, 365);
        setCookie("tim_zoom", map.getZoom(), 365);
        $('#zoomLevel .zoom').text(map.getZoom());
    });


    /* Establish bing layer types - zoom is defined using the zoom level and resolutions. sever resolutions is
    a smaller array because bing only has so many tile sets. once we pass those, we use the resolutions array for fractional zoom.
     http://stackoverflow.com/questions/42396112/magnifying-tiles-in-openlayers-2-or-increasing-maxzoom-without-distorting-projec*/

    var road = new OpenLayers.Layer.Bing({
        name: "Road",
        key: apiKey,
        type: "Road",
        numZoomLevels: 22,
        resolutions: bingResolutions,
        serverResolutions: bingServerResolutions,
        transitionEffect: 'resize'
    });
    var hybrid = new OpenLayers.Layer.Bing({
        name: "Hybrid",
        key: apiKey,
        type: "AerialWithLabels",
        numZoomLevels: 22,
        resolutions: bingResolutions,
        serverResolutions: bingServerResolutions,
        transitionEffect: 'resize'
    });
    var aerial = new OpenLayers.Layer.Bing({
        name: "Aerial",
        key: apiKey,
        type: "Aerial",
        numZoomLevels: 22,
        resolutions: bingResolutions,
        serverResolutions: bingServerResolutions,
        transitionEffect: 'resize'
    });
    
    //Create style maps for the lanes
    var laneDefault = {
        strokeColor: "#FF9900",
        fillColor: "#FF9900",
        strokeOpacity: 1,
        strokeWidth: 4,
        fillOpacity: .9,
        pointRadius: 6
    };

    var areaDefault = {
        strokeColor: "#00FF33",
        fillColor: "#00FF33",
        strokeOpacity: 1,
        strokeWidth: 3,
        fillOpacity: 0,
        pointRadius: 2
    };

    var polyDefault = {
        strokeColor: "#FF9900",
        fillColor: "#FF9900",
        strokeOpacity: 1,
        strokeWidth: 4,
        fillOpacity: .2,
        pointRadius: 6
    };

    var polyDefault2 = {
        strokeColor: "#FF9900",
        fillColor: "#FF9900",
        strokeOpacity: 1,
        strokeWidth: 4,
        fillOpacity: .9,
        pointRadius: 6
    };

    var vectorDefault = {
        strokeColor: "#FF9900",
        fillColor: "#FF9900",
        strokeOpacity: 1,
        strokeWidth: 1,
        fillOpacity: 0,
        pointRadius: 1
    };

    var widthDefault = {
        strokeColor: "#FFFF00",
        fillColor: "#FFFF00",
        strokeOpacity: .5,
        strokeWidth: 1,
        fillOpacity: .1,
        pointRadius: 1
    };

    var context = null;
    
    var laneStyleMap = new OpenLayers.StyleMap({
        "default": new OpenLayers.Style(laneDefault, {context:context})
    });

    var areaStyleMap = new OpenLayers.StyleMap({
        "default": new OpenLayers.Style(areaDefault, {context: context})
    });

    var polyStyleMap = new OpenLayers.StyleMap({
        "default": new OpenLayers.Style(polyDefault, {context:context})
    });

    var polyStyleMap2 = new OpenLayers.StyleMap({
        "default": new OpenLayers.Style(polyDefault2, {context:context})
    });

    var vectorStyleMap = new OpenLayers.StyleMap({
        "default": new OpenLayers.Style(vectorDefault, {context:context})
    });

    var widthStyleMap = new OpenLayers.StyleMap({
        "default": new OpenLayers.Style(widthDefault, {context: context})
    });
    
    //Create new layers for lanes themselves
    lanes = new OpenLayers.Layer.Vector("Lane Layer", {
		eventListeners:{
	        'featureselected':function(evt){
	        	if (deleteMode){
	        		deleteMarker(this, evt.feature);
                    $('#drawPoly').prop('disabled', false);
                    $('#editPoly').prop('disabled', false);
                    $('#drawCircle').prop('disabled', false);
                    $('#movePoly').prop('disabled', false);
	        	}
	        },
            'beforefeatureadded':function(evt) {
                if (lanes.features.length != 0) {
                    alert("Service Region already defined.")
                    return false;
                }
            },
            'featureadded':function(evt) {
                $('#drawPoly').prop('disabled', true);
                $('#editPoly').prop('disabled', true);
                $('#drawCircle').prop('disabled', true);
                $('#movePoly').prop('disabled', true);
            }
		}, styleMap: laneStyleMap
    });

    //Create new layers for the polygons
    polygons = new OpenLayers.Layer.Vector("Polygon Layer", {
        eventListeners:{
            'featureselected':function(evt){
                if (deleteMode){
                    deleteMarker(this, evt.feature);
                    $('#drawLanes').prop('disabled', false);
                    $('#editLanes').prop('disabled', false);
                }
            },
            'beforefeatureadded':function(evt) {
                if (polygons.features.length != 0) {
                    alert("Region already defined.")
                    return false;
                }
            },
            'featureadded':function(evt) {
                $('#drawLanes').prop('disabled', true);
                $('#editLanes').prop('disabled', true);
            }
        }, styleMap: polyStyleMap
    });

    //Creates the deposit area (bright green)
    area = new OpenLayers.Layer.Vector("Deposit Area Layer", {
        eventListeners:{
            'featureselected':function(evt) {
                selected_marker = evt.feature;
                $(".selection-panel").text('Applicable Region');
                $(".marker-info-tab a").text("Region Info");

                if (deleteMode) {
                    deleteMarker(this, selected_marker);
                    return false;
                }

                $('#attribute-tabs li').removeClass('active');

                $('#itis-tab').removeClass('active');
                $('#direction-tab').removeClass('active');
                $('#content-tab').removeClass('active');
                $('#marker-info-tab').addClass('active');

                $("#nwlat").prop('readonly', true);
                $("#nwlong").prop('readonly', true);
                $("#selat").prop('readonly', true);
                $("#selong").prop('readonly', true);

                $(".lat").hide();
                $(".long").hide();
                $(".elev").hide();
                $('.radius').hide();
                $(".verified_lat").hide();
                $(".verified_long").hide();
                $(".verified_elev").hide();
                $(".start_time").hide();
                $(".end_time").hide();
                $(".info-type").hide();
                $(".extent").hide();
                $(".lane_width").hide();
                $(".master_lane_width").hide();
                $(".speed_limit").hide();
                $('.direction-tab').hide();
                $('.content-tab').hide();
                $('.itis-tab').hide();
                $('.ssp_tim_rights').hide();
                $('.ssp_loc_rights').hide();
                $('.ssp_type_rights').hide();
                $('.ssp_content_rights').hide();

                $(".nwlat").show();
                $(".nwlong").show();
                $(".selat").show();
                $(".selong").show();

                var nwPoint = new OpenLayers.Geometry.Point(selected_marker.geometry.getVertices()[1].x, selected_marker.geometry.getVertices()[1].y).transform( toProjection, fromProjection);
                var sePoint = new OpenLayers.Geometry.Point(selected_marker.geometry.getVertices()[3].x, selected_marker.geometry.getVertices()[3].y).transform( toProjection, fromProjection);

                selected_layer = this;
                
                $("#nwlat").val(nwPoint.y);
                $("#nwlong").val(nwPoint.x);
                $("#selat").val(sePoint.y);
                $("#selong").val(sePoint.x);

                $("#attributes").show();
            },
            'featureunselected':function(evt){
                $("#attributes").hide();
                selected_marker = null;
            },
            'beforefeatureadded':function(evt) {
                if (area.features.length != 0) {
                    alert("Applicable Region already defined.")
                    return false;
                }
            }
        },styleMap: areaStyleMap
    });

    //Creates the lane markers (orange dots)
    laneMarkers = new OpenLayers.Layer.Vector("Lane Marker Layer", {
		eventListeners:{
	        'featureselected':function(evt){
	        	selected_marker = evt.feature;
	        	$(".selection-panel").text('Region of Use');
                $(".marker-info-tab a").text("Lane Info");
				console.log("selected: ", selected_marker)
				
                // delete marker and return
	        	if (deleteMode){
	        		deleteMarker(this, selected_marker);
	        		 return false;
	        	} else {
	        		updateNonReferenceFeatureLocation( selected_marker );
	        	}
				
				$('#attribute-tabs li').removeClass('active');

                $('#itis-tab').removeClass('active');
				$('#direction-tab').removeClass('active');
				$('#content-tab').removeClass('active');
				$('#marker-info-tab').addClass('active');
				
				$("#lat").prop('readonly', false);
        		$("#long").prop('readonly', false);
                $("#elev").prop('readonly', false);

                $('.radius').hide();
                $(".verified_lat").hide();
	        	$(".verified_long").hide();
                $(".verified_elev").hide();
	        	$(".start_time").hide();
	        	$(".end_time").hide();
                $(".info-type").hide();
                $(".nwlat").hide();
                $(".nwlong").hide();
                $(".selat").hide();
                $(".selong").hide();
                $(".speed_limit").hide();
                $(".master_lane_width").hide();
                $('.ssp_tim_rights').hide();
                $('.ssp_loc_rights').hide();
                $('.ssp_type_rights').hide();
                $('.ssp_content_rights').hide();

	        	$('.direction-tab').hide();
	        	$('.content-tab').hide();
                $('.itis-tab').hide();
				//-------------------------------------
	        	$(".lat").show();
	        	$(".long").show();
                $(".elev").show();
                $(".lane_width").show();

                //This is an exmaple of how data is loaded if it is bound to the object model
        		if ( selected_marker.attributes.number == 0 ) {
                    $(".extent").show();
                    $(".regionFeatures br").show();
        		} else {
                    $(".extent").hide();
                    $(".regionFeatures br").hide();
                }
        		selected_layer = this;

                if(lanes.features[selected_marker.attributes.lane].attributes.laneWidth){
                    nodeLaneWidth = lanes.features[selected_marker.attributes.lane].attributes.laneWidth;
                }

                if (! nodeLaneWidth[selected_marker.attributes.number]){
                    $("#lane_width").val("0");
                } else {
                    $("#lane_width").val(nodeLaneWidth[selected_marker.attributes.number]);
                }

                if (! selected_marker.attributes.elevation.value){
                    $("#elev").val("");
                } else {
                    $("#elev").val(selected_marker.attributes.elevation.value);
                }
        		
                if (! selected_marker.attributes.extent) {
                    $('#extent .dropdown-toggle').html("Select An Extent <span class='caret'></span>");
                } else {
                    $('#extent .dropdown-toggle').html(selected_marker.attributes.extent + " <span class='caret'></span>");
                }

	            temp_lat = selected_marker.attributes.LonLat.lat;
	            temp_lon = selected_marker.attributes.LonLat.lon;
	            populateAttributeWindow(temp_lat, temp_lon);
	            $("#attributes").show();
	        },
	        'featureunselected':function(evt){
	        	$("#attributes").hide();
	            selected_marker = null;
	        }
		},
		styleMap: laneStyleMap
    });

    //Creates the poly markers layer (orange dots)
    polyMarkers = new OpenLayers.Layer.Vector("Poly Marker Layer", {
        eventListeners:{
            'featureselected':function(evt){
                selected_marker = evt.feature;
                $(".selection-panel").text('Region of Use');
                if(selected_marker.attributes.title == "circle"){
                    $(".marker-info-tab a").text("Circle Info");
                    $('.radius').show();
                } else {
                    $(".marker-info-tab a").text("Region Info");
                    $('.radius').hide();
                }
                console.log("selected: ", selected_marker)

                // delete marker and return
                if (deleteMode){
                    deleteMarker(this, selected_marker);
                    return false;
                } else {
                	updateNonReferenceFeatureLocation( selected_marker );
                }

                $('#attribute-tabs li').removeClass('active');

                $('#itis-tab').removeClass('active');
                $('#direction-tab').removeClass('active');
                $('#content-tab').removeClass('active');
                $('#marker-info-tab').addClass('active');

                $("#lat").prop('readonly', false);
                $("#long").prop('readonly', false);
                $("#elev").prop('readonly', false);

                $(".verified_lat").hide();
                $(".verified_long").hide();
                $(".verified_elev").hide();
                $(".start_time").hide();
                $(".end_time").hide();
                $(".info-type").hide();
                $(".nwlat").hide();
                $(".nwlong").hide();
                $(".selat").hide();
                $(".selong").hide();
                $(".lane_width").hide();
                $(".master_lane_width").hide();
                $(".extent").hide();
                $(".speed_limit").hide();
                $(".regionFeatures br").hide();
                $('.ssp_tim_rights').hide();
                $('.ssp_type_rights').hide();
                $('.ssp_content_rights').hide();
                $('.ssp_loc_rights').hide();
                
                $('.direction-tab').hide();
                $('.content-tab').hide();
                $('.itis-tab').hide();

                //-------------------------------------
                $(".lat").show();
                $(".long").show();
                $(".elev").show();

                selected_layer = this;

                if (! selected_marker.attributes.elevation.value){
                    $("#elev").val("");
                } else {
                    $("#elev").val(selected_marker.attributes.elevation.value);
                }
                
                temp_lat = selected_marker.attributes.LonLat.lat;
                temp_lon = selected_marker.attributes.LonLat.lon;
                populateAttributeWindow(temp_lat, temp_lon);
                $("#attributes").show();
            },
            'featureunselected':function(evt){
                $("#attributes").hide();
                selected_marker = null;
            }
        },
        styleMap: polyStyleMap2
    });


    //Creates layer for centeral marker and verified points
    vectors = new OpenLayers.Layer.Vector("Vector Layer",{
		eventListeners:{
			'featureadded' : function (evt) {
				selected_marker = evt.feature;
				updateFeatureLocation(evt.feature);
			},
	        'featureselected':function(evt){
	        	selected_marker = evt.feature;
	        	if (deleteMode){
	        		deleteMarker(this, selected_marker);
	        		content = [];
	        	} else {
	        		updateFeatureLocation( selected_marker );
	        	}
	        },
	        'featureunselected':function(evt){
	        	$("#attributes").hide();
	        	selected_marker = null;
	        }
		}, styleMap: vectorStyleMap
    });

    laneWidths = new OpenLayers.Layer.Vector("Width Layer", {
        eventListeners:{
            'featureadded':function(evt){
            }
        }, styleMap: widthStyleMap
    });

    radiuslayer = new OpenLayers.Layer.Vector("Radius");

    var updateDisplay = function( event ) { // 5
        $('.measurement').text( (event.measure).toFixed(3) + ' ' + event.units );
        copyTextToClipboard((event.measure).toFixed(3));
    };
       
    //Controls for the lane layer to draw and modify
    controls = {
            line: new OpenLayers.Control.DrawFeature(lanes,
                        OpenLayers.Handler.Path, {featureAdded: onFeatureAdded}),
            modify: new OpenLayers.Control.ModifyFeature(lanes),
            change: new OpenLayers.Control.ModifyFeature(polygons),
            drag: dragHandler(),
            area: new OpenLayers.Control.DrawFeature(area,
                OpenLayers.Handler.RegularPolygon, {
                    handlerOptions: {
                        sides: 4,
                        irregular: true
                    }
                }),
            polygon: new OpenLayers.Control.DrawFeature(polygons,
                OpenLayers.Handler.Polygon, {featureAdded: onFeatureAdded}),
            circle: new OpenLayers.Control.DrawFeature(polygons,
                OpenLayers.Handler.RegularPolygon, {featureAdded: onFeatureAdded, handlerOptions: {
                    sides: 100
                }}),
            dragPoly: new OpenLayers.Control.DragFeature(polygons),
            edit: new OpenLayers.Control.ModifyFeature(area),
            del: new OpenLayers.Control.SelectFeature([lanes, vectors, area, polygons], {toggle: false, autoActivate:true}),
            none: new OpenLayers.Control.SelectFeature([laneMarkers, polyMarkers, vectors, area], {toggle:true, autoActivate:true}),
            measure: new OpenLayers.Control.Measure(
                OpenLayers.Handler.Path, {
                    persist: true,
                    immediate: true,
                    geodesic: true,
                    displaySystem: 'metric',
                    eventListeners: {
                        'measurepartial': updateDisplay
                    }
                })
        };

    controls.edit.mode = OpenLayers.Control.ModifyFeature.DRAG | OpenLayers.Control.ModifyFeature.RESIZE;

	for(var key in controls) {
		map.addControl(controls[key]);
	}

    //Panning of map updates the tile age
    map.events.register("moveend", map, tileAge);

    //All layers and their bound actions defined above are added to the map div here
    map.addLayers([aerial, road, hybrid, area, laneMarkers, polyMarkers, lanes, polygons, vectors, radiuslayer, laneWidths]);
    try {
        var location = new OpenLayers.LonLat(view_lon, view_lat);
        location.transform(new OpenLayers.Projection("EPSG:4326"), map.getProjectionObject());
        map.setCenter(location, view_zoom);
    }
    catch (err) {
        console.log("No vectors to reset view");
    }

    $('#OpenLayers_Control_MinimizeDiv_innerImage').attr('src', "img/layer-switcher-minimize.png");
    $('#OpenLayers_Control_MaximizeDiv_innerImage').attr('src', "img/layer-switcher-maximize.png");

}


/*********************************************************************************************************************/
/**
 * Purpose: copies measurement to clipboard
 * @params  measurement value
 * @event copy
 */

function copyTextToClipboard(text) {
    var textArea = document.createElement("textarea");

    //
    // *** This styling is an extra step which is likely not required. ***
    //
    // Why is it here? To ensure:
    // 1. the element is able to have focus and selection.
    // 2. if element was to flash render it has minimal visual impact.
    // 3. less flakyness with selection and copying which **might** occur if
    //    the textarea element is not visible.
    //
    // The likelihood is the element won't even render, not even a flash,
    // so some of these are just precautions. However in IE the element
    // is visible whilst the popup box asking the user for permission for
    // the web page to copy to the clipboard.
    //

    // Place in top-left corner of screen regardless of scroll position.
    textArea.style.position = 'fixed';
    textArea.style.top = 0;
    textArea.style.left = 0;

    // Ensure it has a small width and height. Setting to 1px / 1em
    // doesn't work as this gives a negative w/h on some browsers.
    textArea.style.width = '2em';
    textArea.style.height = '2em';

    // We don't need padding, reducing the size if it does flash render.
    textArea.style.padding = 0;

    // Clean up any borders.
    textArea.style.border = 'none';
    textArea.style.outline = 'none';
    textArea.style.boxShadow = 'none';

    // Avoid flash of white box if rendered for any reason.
    textArea.style.background = 'transparent';


    textArea.value = text;

    document.body.appendChild(textArea);

    textArea.select();

    try {
        var successful = document.execCommand('copy');
        var msg = successful ? 'successful' : 'unsuccessful';
        console.log('Copying text command was ' + msg);
    } catch (err) {
        console.log('Oops, unable to copy');
    }

    document.body.removeChild(textArea);
}


/**
 * Purpose: removes features from the map
 * @params  map layers and features
 * @event remove features and all of it's metadata
 */

function Clear(){
	var r = confirm("Clear all of the map features?");
	if (r == true) {
		lanes.destroyFeatures();
		laneMarkers.destroyFeatures();
		vectors.destroyFeatures();
        area.destroyFeatures();
        polygons.destroyFeatures();
        polyMarkers.destroyFeatures();
        radiuslayer.destroyFeatures();
        deleteTrace();
        laneWidths.destroyFeatures();
        try {
            for (d = 0; d < 360; (d = d + 22.5)) {
                drawCircle(ctx, cx, cy, cr, "black", "white", d, circles_reset);
            }
        } catch (err){
            console.log("could not reset circle")
        }

        circles_temp = JSON.parse(JSON.stringify(circles_reset));
        circles = JSON.parse(JSON.stringify(circles_reset));

        $('#drawPoly').prop('disabled', false);
        $('#editPoly').prop('disabled', false);
        $('#drawLanes').prop('disabled', false);
        $('#editLanes').prop('disabled', false);
        $('#drawCircle').prop('disabled', false);
        $('#movePoly').prop('disabled', false);
	}
}

function deleteMarker(layer, feature) {
	$("#attributes").hide();

    try {
        if (feature.attributes.marker.type == "TIM") {
            for (d = 0; d < 360; (d = d + 22.5)) {
                drawCircle(ctx, cx, cy, cr, "black", "white", d, circles_reset);
            }
        }
    } catch (err){
        console.log("type not defined")
    }

    circles_temp = JSON.parse(JSON.stringify(circles_reset));
    circles = JSON.parse(JSON.stringify(circles_reset));

	layer.removeFeatures(feature);
}

/*********************************************************************************************************************/
/**
 * Purpose: toggle control of all the layers and modal windows
 * @params  click events and the corresponding feature type
 * @event loads help or the drawing control with a specific element in mind
 */

function toggleControlsOn(state) {
	if( state == 'help'){
		$("#instructions_modal").modal('show');
	} else {
	$("#instructions_modal").modal('hide');
	toggleControl(state);
        if( state == 'modify' || state == 'del' || state == 'dragPoly') {
            laneMarkers.destroyFeatures();
            polyMarkers.destroyFeatures();
            radiuslayer.destroyFeatures();
            controls.del.unselectAll();
        } else {
            onFeatureAdded();
        }
    }
}

function toggleControl(element) {
    for(key in controls) {
        var control = controls[key];
        if(element == key) {
            control.activate();
        } else {
            control.deactivate();
        }
    }
}

function unselectFeature( feature ) {
	
	if( feature.layer != null ) {
		console.log("unselecting ", feature)
		controls.none.unselect( feature );
	}
}
//this is just chillin' not sure what it's for :-/
var tmp = 0;

/*********************************************************************************************************************/
/**
 * Purpose: dot functions that bind the metadata to the feature object
 * @params  the feature and it's metadata
 * @event creates variables attached to the feature object and store the values
 */

function onFeatureAdded(){
	
    laneMarkers.destroyFeatures();
    var ft = lanes.features;
    for(var i=0; i< ft.length; i++){
        if (typeof lanes.features[i].attributes.elevation == 'undefined'){
            lanes.features[i].attributes.elevation = [];
        }
        if (typeof lanes.features[i].attributes.laneWidth == 'undefined'){
            lanes.features[i].attributes.laneWidth = [];
        }
        var max = lanes.features[i].geometry.getVertices().length;
        var nodeElevationsLanes = (lanes.features[i].attributes.elevation).slice(0);
        var nodeLaneWidths = (lanes.features[i].attributes.laneWidth).slice(0);
        var added =  (typeof lanes.features[i].attributes.laneWidth[max-1] == 'undefined');
        for(j=0; j< max; j++){
            var dot = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Point(lanes.features[i].geometry.getVertices()[j].x, lanes.features[i].geometry.getVertices()[j].y));
            var latlon = new OpenLayers.LonLat(dot.geometry.x, dot.geometry.y).transform(toProjection, fromProjection);

            if (typeof lanes.features[i].attributes.laneWidth[j] == 'undefined'){
                lanes.features[i].attributes.laneWidth[j] = 0;
            }

            if (typeof lanes.features[i].attributes.elevation[j] == 'undefined'){
                lanes.features[i].attributes.elevation[j] = {'value': -9999, 'edited': false, 'latlon': latlon};
            }
            for (k = 0; k < nodeElevationsLanes.length; k++) {
                var latMatch = ((nodeElevationsLanes[k].latlon.lat).toString().match(/^-?\d+(?:\.\d{0,11})?/)[0] === (latlon.lat).toString().match(/^-?\d+(?:\.\d{0,11})?/)[0]);
                var lonMatch = ((nodeElevationsLanes[k].latlon.lon).toString().match(/^-?\d+(?:\.\d{0,11})?/)[0] === (latlon.lon).toString().match(/^-?\d+(?:\.\d{0,11})?/)[0]);
                if(!latMatch && !lonMatch) {
                    if(added) {
                        lanes.features[i].attributes.laneWidth[j] = 0;
                    }
                } else {
                    lanes.features[i].attributes.laneWidth[j] = nodeLaneWidths[k];
                    if(nodeElevationsLanes[k].edited) {
                        lanes.features[i].attributes.elevation[j] = nodeElevationsLanes[k];
                        buildLaneDots(i, j, dot, latlon);
                        break;
                    }
                }
            }
            if (!lanes.features[i].attributes.elevation[j].edited || !latMatch || !lonMatch){
                getElevation(dot, latlon, i, j, function(elev, i, j, latlon, dot){
                    lanes.features[i].attributes.elevation[j] = {'value': elev, 'edited': true, 'latlon': latlon};
                    buildLaneDots(i, j, dot, latlon);
                });
            }
        }
    }

    if (laneWidths.features.length != 0) {
        laneWidths.destroyFeatures();
        toggleWidthArray();
    }

    polyMarkers.destroyFeatures();
    radiuslayer.destroyFeatures();
    var ft = polygons.features;
    for(var i=0; i< ft.length; i++){
        if (typeof polygons.features[i].attributes.elevation == 'undefined'){
            polygons.features[i].attributes.elevation = [];
        }

        var max = polygons.features[i].geometry.getVertices().length;

        if (max === 100){

            var bounds = polygons.features[i].geometry.bounds;
            var minX = bounds.left;
            var minY = bounds.bottom;
            var maxX = bounds.right;
            var maxY = bounds.top;
            //calculate the center coordinates
            var startX = (minX + maxX) / 2;
            var startY = (minY + maxY) / 2;

            var dot_c = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Point(startX, startY));
            var dot_c_latlon = new OpenLayers.LonLat(dot_c.geometry.x, dot_c.geometry.y).transform(toProjection, fromProjection);
            getElevation(dot_c, dot_c_latlon, 0, 101, function(elev, i, j, dot_c_latlon, dot_c){
                polygons.features[i].attributes.elevation[j] = {'value': elev, 'edited': true, 'latlon': dot_c_latlon};
                buildPolyDots(i, j, dot_c, latlon, "circle");
            });

            var startPoint = new OpenLayers.Geometry.Point(startX, startY);
            var endPoint = new OpenLayers.Geometry.Point(maxX, startY);
            var radius = new OpenLayers.Geometry.LineString([startPoint, endPoint]);
            if (polygons.features[i].attributes.radius == undefined) {
                var len = (radius.getGeodesicLength(new OpenLayers.Projection("EPSG:900913")));
                //var len = Math.round(radius.getLength()).toString();
                $('#radius').val(len);
            } else {
                $('#radius').val(polygons.features[i].attributes.radius);
            }

            var radiusDefault = {
                strokeColor: "#0500bd",
                strokeWidth: 3,
                //label: len + " m",
                labelAlign: "left",
                labelXOffset: "20",
                labelYOffset: "10"
            };

            var fea = new OpenLayers.Feature.Vector(radius, {
                'length': len
            }, radiusDefault);

            radiuslayer.addFeatures([fea]);

            polygons.features[i].attributes.title = "circle";
            max = 0;
        }

        if (polygons.features[0].attributes.title == "circle"){
            $('#editPoly').prop('disabled', true);
        } else {
            $('#editPoly').prop('disabled', false);
        }

        var nodeElevationsPoly = (polygons.features[i].attributes.elevation).slice(0);
        for(j=0; j< max; j++){
            var dot = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Point(polygons.features[i].geometry.getVertices()[j].x, polygons.features[i].geometry.getVertices()[j].y));
            var latlon = new OpenLayers.LonLat(dot.geometry.x, dot.geometry.y).transform(toProjection, fromProjection);

            if (typeof polygons.features[i].attributes.elevation[j] == 'undefined'){
                polygons.features[i].attributes.elevation[j] = {'value': -9999, 'edited': false, 'latlon': latlon};
            }
            for(k=0; k < nodeElevationsPoly.length; k++){
                var latMatchPoly = ((nodeElevationsPoly[k].latlon.lat).toString().match(/^-?\d+(?:\.\d{0,11})?/)[0] === (latlon.lat).toString().match(/^-?\d+(?:\.\d{0,11})?/)[0]);
                var lonMatchPoly = ((nodeElevationsPoly[k].latlon.lon).toString().match(/^-?\d+(?:\.\d{0,11})?/)[0] === (latlon.lon).toString().match(/^-?\d+(?:\.\d{0,11})?/)[0]);
                if(latMatchPoly && lonMatchPoly && nodeElevationsPoly[k].edited){
                    polygons.features[i].attributes.elevation[j] = nodeElevationsPoly[k];
                    buildPolyDots(i, j, dot, latlon);
                    break;
                }
            }
            if (!polygons.features[i].attributes.elevation[j].edited || !latMatchPoly || !lonMatchPoly){
                getElevation(dot, latlon, i, j, function(elev, i, j, latlon, dot){
                    polygons.features[i].attributes.elevation[j] = {'value': elev, 'edited': true, 'latlon': latlon};
                    buildPolyDots(i, j, dot, latlon);
                });
            }
        }
    }

}

function buildLaneDots(i, j, dot, latlon){
    dot.attributes={"lane": i, "number": j, "LatLon": latlon,
        "laneNumber": lanes.features[i].attributes.laneNumber, "laneWidth": lanes.features[i].attributes.laneWidth, "extent": lanes.features[i].attributes.extent,
        "elevation": lanes.features[i].attributes.elevation[j]
    };
    laneMarkers.addFeatures(dot);
}

function buildPolyDots(i, j, dot, latlon, title){
    dot.attributes={"area": i, "number": j, "LatLon": latlon,
        "elevation": polygons.features[i].attributes.elevation[j],
        "title": title
    };
    polyMarkers.addFeatures(dot);
}


/*********************************************************************************************************************/
/**
 * Purpose: drag handler for the vector layer
 * @params  the feature and it's metadata
 * @event creates variables attached to the feature object and store the values
 */

function dragHandler () {
	var selectFeature = new OpenLayers.Control.SelectFeature(vectors,{
		toggle:true
	});

    return new OpenLayers.Control.DragFeature(vectors, {
		autoActivate: true,
		clickFeature: function(feature) {
			selectFeature.clickFeature(feature);
		},
		clickoutFeature: function(feature) {
			selectFeature.clickoutFeature(feature);
		},
		onStart: function(feature){
			selected_marker = feature;
		},
        onComplete: function() {
            console.log("dragged: ", this.feature);
			updateFeatureLocation( this.feature )
        }
    });

}


/*********************************************************************************************************************/
/**
 * Purpose: creates sidebar element for the individual roadsigns
 * @params  the feature and it's metadata
 * @event loads the sidebar and all of the metadata into the forms
 */

function referencePointWindow(feature){
	$("#attributes").hide();
	//---------------------------------------
	$(".selection-panel").text( feature.attributes.marker.name + ' Sign');
    $(".marker-info-tab a").text("Marker Info");
    selected_marker_limit = feature.attributes.marker.limit
	$("#lat").prop('readonly', false);
	$("#long").prop('readonly', false);
    $("#elev").prop('readonly', false);
    
	$('#attribute-tabs li').removeClass('active');

    $('#itis-tab').removeClass('active');
	$('#direction-tab').removeClass('active');
	$('#content-tab').removeClass('active');
	$('#marker-info-tab').addClass('active');

    $('.radius').hide();
	$(".lane_width").hide();
	$(".verified_lat").hide();
	$(".verified_long").hide();
    $(".verified_elev").hide();
    $(".extent").hide();
    $(".nwlat").hide();
    $(".nwlong").hide();
    $(".selat").hide();
    $(".selong").hide();
    $(".regionFeatures br").hide();
    $('.ssp_loc_rights').hide();
	//----------------------------------------
	$(".start_time").show();
	$(".end_time").show();
	$(".lat").show();
	$(".long").show();
	$(".intersection").show();
    $(".elev").show();
    $(".info-type").show();
	$('.direction-tab').show();
	$('.content-tab').show();
    $('.itis-tab').show();
    $(".speed_limit").show();
    $(".master_lane_width").show();
    $('.ssp_tim_rights').show();
    $('.ssp_type_rights').show();
    $('.ssp_content_rights').show();
    $('.ssp_loc_rights').show();

	//----------------------------------------
	if(feature.attributes.marker.name == "Verified Point Marker"){
				
		$(".selection-panel").text('Verified Point Configuration');
		$(".start_time").hide();
		$(".end_time").hide();
		$(".intersection").hide();
		$(".lane_width").hide();
		$(".info-type").hide();
		$('.direction-tab').hide();
		$('.content-tab').hide();
        $('.itis-tab').hide();
	    $(".speed_limit").hide();
	    $(".master_lane_width").hide();
	    $('.ssp_tim_rights').hide();
	    $('.ssp_loc_rights').hide();
		
		$("#lat").prop('readonly', true);
		$("#long").prop('readonly', true);
        $("#elev").prop('readonly', true);
    	$(".verified_lat").show();
    	$(".verified_long").show();
        $(".verified_elev").show();
	}
	    
	if (selected_marker.attributes.verifiedElev){
		$("#verified_elev").val(selected_marker.attributes.verifiedElev);
	}
	
    if (! selected_marker.attributes.elevation) {
        $('#elev').val('');
    } else {
        $('#elev').val(selected_marker.attributes.elevation);
    }
    
	if (! selected_marker.attributes.sspLocationRights){
		$("#ssp_loc_rights").val('');
	} else {
		$("#ssp_loc_rights").val(selected_marker.attributes.sspLocationRights);
	}
    
    if (! selected_marker.attributes.masterLaneWidth) {
        $('#master_lane_width').val('366');
    } else {
        $('#master_lane_width').val(selected_marker.attributes.masterLaneWidth);
    }
	
    if (! selected_marker.attributes.startTime) {
        $('#start_time input').val('');
    } else {
        $('#start_time input').val(selected_marker.attributes.startTime);
    }

    if (! selected_marker.attributes.endTime) {
        $('#end_time input').val('');
    } else {
        $('#end_time input').val(selected_marker.attributes.endTime);
    }

    if (! selected_marker.attributes.content) {
        removeITISForm();
        addITISForm();
    } else {
        rebuildITISForm(selected_marker.attributes.content);
    }
    
    if (! selected_marker.attributes.sspTypeRights) {
    	$('#ssp_type_rights').val('');
    } else {
    	$('#ssp_type_rights').val(selected_marker.attributes.sspTypeRights);
    }
    
    if (! selected_marker.attributes.sspContentRights) {
    	$('#ssp_content_rights').val('');
    } else {
    	$('#ssp_content_rights').val(selected_marker.attributes.sspContentRights);
    }
    
    if (! selected_marker.attributes.sspTimRights) {
    	$('#ssp_tim_rights').val('');
    } else {
    	$('#ssp_tim_rights').val(selected_marker.attributes.sspTimRights);
    }

    if (! selected_marker.attributes.priority) {
        $('#priority .dropdown-toggle').html("Select A Priority <span class='caret'></span>");
    } else {
        $('#priority .dropdown-toggle').html(selected_marker.attributes.priority + " <span class='caret'></span>");
    }

    if (! selected_marker.attributes.mutcd) {
        $('#mutcd .dropdown-toggle').html("Select A MUTCD Code <span class='caret'></span>");
    } else {
        $('#mutcd .dropdown-toggle').html(selected_marker.attributes.mutcd + " <span class='caret'></span>");
    }

    if (! selected_marker.attributes.direction) {
        $('#direction .dropdown-toggle').html("Select A Direction <span class='caret'></span>");
    } else {
        $('#direction .dropdown-toggle').html(selected_marker.attributes.direction + " <span class='caret'></span>");
    }
    
    if (! selected_marker.attributes.infoType) {
        $('#info-type .dropdown-toggle').html("Select A Type <span class='caret'></span>");
    } else {
        $('#info-type .dropdown-toggle').html(selected_marker.attributes.infoType + " <span class='caret'></span>");
    }
    
	if (selected_marker.attributes.heading){
	    drawCircleSlices(selected_marker.attributes.heading);
	}

    selected_layer = feature.layer;
	$("#attributes").show();
}


/**
 * Purpose: if lat/long is modified, it changes the location
 * @params  the feature and it's metadata
 * @event changes the location on the map by redrawing
 */

function updateFeatureLocation( feature ) {
	referencePointWindow(feature);
	feature.attributes.LonLat = (new OpenLayers.LonLat(feature.geometry.x, feature.geometry.y)).transform(toProjection, fromProjection);
	$('#long').val(feature.attributes.LonLat.lon);
	$('#lat').val(feature.attributes.LonLat.lat);
	populateRefWindow(feature, feature.attributes.LonLat.lat, feature.attributes.LonLat.lon);
}

function updateNonReferenceFeatureLocation( feature ) {
	feature.attributes.LonLat = (new OpenLayers.LonLat(feature.geometry.x, feature.geometry.y)).transform(toProjection, fromProjection);
	$('#long').val(feature.attributes.LonLat.lon);
	$('#lat').val(feature.attributes.LonLat.lat);
	populateRefWindow(feature, feature.attributes.LonLat.lat, feature.attributes.LonLat.lon);
}


/**
 * Purpose: populate reference point modal window
 * @params  the feature and it's metadata
 * @event loads the appropriate data - elevation is doen through ajax
 */

function populateAttributeWindow(temp_lat, temp_lon){
	$('#lat').val(temp_lat);
	$('#long').val(temp_lon);
}

function populateRefWindow(feature, lat, lon)
{
    $.ajax({
        url: elevation_url + lat + ',' + lon + '&key=' + apiKey,
        dataType: 'jsonp',
        jsonp: "jsonp",
        cache: false,
        success: function(result){
            elev = result.resourceSets[0].resources[0].elevations[0];
            if (elev == null){
                elev = -9999; //any sea value is set to -9999 by default. This brings it back to sea level as we know it
            }
            if (! feature.attributes.elevation ) {
                $('#elev').val(elev);
            } else {
                if (feature.attributes.number > -1) {
                    if (!feature.attributes.elevation.value) {
                        $('#elev').val(elev);
                    }
                }
            }
            if (feature.attributes.marker.type == "VER"){
                feature.attributes.elevation = elev
            }
            if (feature.attributes.verifiedElev){
                $('#verified_elev').val(feature.attributes.verifiedElev);
            } else {
                $('#verified_elev').val(elev);
            }
        }
    });


	if (feature.attributes.verifiedLat){
		$('#verified_lat').val(feature.attributes.verifiedLat);	
	} else {
		$('#verified_lat').val(lat);
	}
	if (feature.attributes.verifiedLon){
		$('#verified_long').val(feature.attributes.verifiedLon);
	} else {
		$('#verified_long').val(lon);
	}
	
}


/*********************************************************************************************************************/
/**
 * Purpose: validate the data and save the data to the feature
 * @params  the sidebar form elements
 * @event validates all the visible data using parsley js. If it is not accepted, it turns the form locations
 * with issues red, otherwise, it allows the data object to be created and saved to the feature
 */

$(".btnDone").click(function(){

    //validation steps
	$('#attributes').parsley().validate();

	var error_count = 0;

    //validates the select2 box separately from the parsley
    var code_check = /^(n((1-((4|8|10|16)th|3rd|2)|3-4)|[1-9]\d*)|\d+)$/;
    var itis_check = false;

    $('.itis_code_list').each(function (i, obj) {
        if ($(this).val() !== null) {
            $.each($(this).val(), function (j, val) {
                if (!code_check.test(val)) {
                    error_count++;
                    $('.select2-selection').css('border-color', 'red');
                    itis_check = true;
                }
            })
        }
    });

	if (selected_marker.attributes.marker == null) {
		error_count += $(".parsley-errors-list li:visible").length;
	} else {
		if (selected_marker.attributes.marker.type === "TIM") {
			error_count += $("#marker-info-tab .row:not([style='display: none;']) .parsley-errors-list li").length
					+ $("#content-tab .parsley-errors-list li").length
					+ $("#direction-tab .parsley-errors-list li").length
                    + $("#itis-tab .parsley-errors-list li").length;
		} else {
			error_count += $(".parsley-errors-list li:visible").length;
		}
	}

    //If no errors are found, then allow the feature data to be saved to the object
	if ( error_count === 0) {
	
		$("#attributes").hide();
        $('.select2-selection').css('border-color', '');

        content = [];
        $('.itis_code_list').each(function(i, obj) {
            content[i] = new createElement($(this).val(), $('#itisForm_' + i + '_itis_text').val());
        });

		var move = new OpenLayers.LonLat($('#long').val(), $('#lat').val()).transform(fromProjection, toProjection)
	
		if (selected_layer.name == "Lane Marker Layer"){
			var vert = lanes.features[selected_marker.attributes.lane].geometry.components[selected_marker.attributes.number];
			vert.move(move.lon - vert.x, move.lat - vert.y);
			selected_marker.move(move);
			lanes.redraw();
			if ( selected_marker.attributes.number == 0 ) {
                selected_marker.attributes.extent = extent;
                (lanes.features[selected_marker.attributes.lane]).attributes.extent = extent;
			}
            selected_marker.attributes.elevation = $('#elev').val();
            (lanes.features[selected_marker.attributes.lane]).attributes.elevation[selected_marker.attributes.number].value = $("#elev").val();
            (lanes.features[selected_marker.attributes.lane]).attributes.elevation[selected_marker.attributes.number].edited = true;
            nodeLaneWidth[selected_marker.attributes.number] = $("#lane_width").val();
            (lanes.features[selected_marker.attributes.lane]).attributes.laneWidth = nodeLaneWidth;
            nodeLaneWidth = [];
			selected_marker.attributes.LatLon = new OpenLayers.LonLat($('#long').val(), $('#lat').val());
		}

        if (selected_layer.name == "Poly Marker Layer"){
            if (polygons.features[selected_marker.attributes.area].attributes.title === "circle"){

                var temp_radius = $('#radius').val();

                //moving the center point doesn't matter until the circle is adjusted because on redraw, the center point will move accordingly
                //fix the full circle moving first

                var center = selected_marker.geometry;
                var user_move = new OpenLayers.LonLat($('#long').val(), $('#lat').val()).transform(fromProjection, toProjection);
                var change_z = polygons.features[0].attributes.elevation;

                console.log(user_move, center)

                polygons.destroyFeatures();
                var temp_proj = new OpenLayers.LonLat(user_move.lon,user_move.lat).transform(toProjection, fromProjection);
                var temp_c = new OpenLayers.Geometry.Polygon.createRegularPolygon(new OpenLayers.Geometry.Point(user_move.lon,user_move.lat), $('#radius').val() * 1/Math.cos(temp_proj.lat * (Math.PI / 180)) *.999769942386, 100);
                var new_c = new OpenLayers.Feature.Vector(temp_c);
                new_c.attributes.title = "circle";
                new_c.attributes.elevation = change_z;
                new_c.attributes.radius = temp_radius;

                polygons.addFeatures([new_c]);
                polygons.redraw();

            } else {
                var space = polygons.features[selected_marker.attributes.area].geometry.components[0].components[selected_marker.attributes.number];
                space.move(move.lon - space.x, move.lat - space.y);
                selected_marker.move(move);
                polygons.redraw();
                selected_marker.attributes.LatLon = new OpenLayers.LonLat($('#long').val(), $('#lat').val());
                selected_marker.attributes.elevation = $('#elev').val();
                (polygons.features[selected_marker.attributes.area]).attributes.elevation[selected_marker.attributes.number].value = $("#elev").val();
                (polygons.features[selected_marker.attributes.area]).attributes.elevation[selected_marker.attributes.number].edited = true;
            }
        }
		
		if (selected_layer.name == "Vector Layer"){
			selected_marker.move(move);
			if (selected_marker.attributes.marker.name == "Verified Point Marker"){
				selected_marker.attributes.verifiedLat = $("#verified_lat").val();
				selected_marker.attributes.verifiedLon = $("#verified_long").val();
                selected_marker.attributes.verifiedElev = $("#verified_elev").val();
			} else {
				selected_marker.attributes.startTime = $("#start_time input").val();
				selected_marker.attributes.endTime = $("#end_time input").val();
                selected_marker.attributes.packetID = $("#packet_id").val();
				selected_marker.attributes.content = content;
                selected_marker.attributes.elevation = $("#elev").val();
                selected_marker.attributes.masterLaneWidth = $("#master_lane_width").val();
                selected_marker.attributes.sspTimRights = $("#ssp_tim_rights").val();
                selected_marker.attributes.sspTypeRights = $("#ssp_type_rights").val();
                selected_marker.attributes.sspContentRights = $("#ssp_content_rights").val();
    			selected_marker.attributes.sspLocationRights = $("#ssp_loc_rights").val();
				selected_marker.attributes.mutcd = mutcd;
				selected_marker.attributes.infoType = info_type;
				selected_marker.attributes.priority = priority;
                if (direction == undefined) {
                    selected_marker.attributes.direction = '';
                } else {
                    selected_marker.attributes.direction = direction;
                }
                circles_temp = JSON.parse(JSON.stringify(circles));
				selected_marker.attributes.heading = circles_temp;
			}	
		}
		$('#attributes').parsley().reset();
		unselectFeature( selected_marker );
	} else {

        //Load the tab which has a validation error
		if( $("#marker-info-tab .row:not([style='display: none;']) .parsley-errors-list li").length > 0 ){
            $('#itis-tab').removeClass('active');
			$('#direction-tab').removeClass('active');
			$('#content-tab').removeClass('active');
			$('#marker-info-tab').addClass('active');
		}
		if ( $("#content-tab .parsley-errors-list li").length > 0 ){
            $('#itis-tab').removeClass('active');
			$('#direction-tab').removeClass('active');
			$('#content-tab').addClass('active');
			$('#marker-info-tab').removeClass('active');
		}
		if ( $("#direction-tab .parsley-errors-list li").length > 0 ){
            $('#itis-tab').removeClass('active');
			$('#direction-tab').addClass('active');
			$('#content-tab').removeClass('active');
			$('#marker-info-tab').removeClass('active');
		}
        if ( $("#itis-tab .parsley-errors-list li").length > 0 || itis_check){
            $('#itis-tab').addClass('active');
            $('#direction-tab').removeClass('active');
            $('#content-tab').removeClass('active');
            $('#marker-info-tab').removeClass('active');
        }
		
	}
    onFeatureAdded();
});


/*********************************************************************************************************************/
/**
 * Purpose: if cancel - prevents data from being stored
 * @params  the sidebar form elements
 * @event removes all form data and clears any temp objects that may be housing data so that next load can start clean
 * from the feature object
 */

$(".btnClose").click(function(){
	$("#attributes").hide();

    $('.itis_code_list').each(function(i, obj) {
        $('#itisForm_' + i + '_itis_text').val("");
        $('#itisForm_' + i + '_itis_codes').empty();
    });
    removeITISForm();
    $('.select2-selection').css('border-color', '');

	ctx.clearRect(0,0,300,300);

	for (d=0; d < 360; (d =d + 22.5)){
		drawCircle(ctx, cx, cy, cr, "black", "white", d, circles_reset);
	}

    circles = JSON.parse(JSON.stringify(circles_temp));
	
    drawCircleSlices(circles);
    circles_reset = [];
    nodeLaneWidth = [];

    $('#attributes').parsley().reset();
	unselectFeature( selected_marker );
	
});

/*********************************************************************************************************************/
/**
 * Purpose: misc. functions that allow specific data to be visible a certain way
 * @params  -
 * @event createElement is for select2 itis codes box, drawCircleSlices is used with the heading,
 * other options allow time to be chosen, or for data to parse ul/li select box text into text and number
 *
 * Note: the ul/li select boxes should one da become select boxes with options, but the styling was hard to replicate
 * at first.
 */

function createElement(code, value){
    if (code === null){
        this.codes = []
    } else {
        this.codes = code
    }
    this.text = value
}

function drawCircleSlices(circles){
	
	 for (var i = 0; i < circles.length; i++) {
		 if (circles[i].active){
	        ctx.beginPath();
	        	ctx.moveTo(circles[i].x1,circles[i].y1);
	        	ctx.arc(circles[i].x1,circles[i].y1,cr,circles[i].theta,circles[i].nxtTheta);
	        	ctx.lineTo(circles[i].x1,circles[i].y1);
	        ctx.closePath();	
	        	ctx.fillStyle = "LightSkyBlue";
	        	ctx.fill();
	        	ctx.strokeStyle = "black";
	        	ctx.stroke();
		 }
		 
		ctx.beginPath();
	        ctx.arc((cr * (Math.cos(circles[i].theta)))+circles[i].x1, (cr * (Math.sin(circles[i].theta)))+circles[i].y1, 4, 0, 2 * Math.PI);
	        ctx.fillStyle = "blue";
	        ctx.fill();
	    ctx.closePath();
	    
	    ctx.beginPath();
		    ctx.arc((cr * (Math.cos(circles[i].nxtTheta)))+circles[i].x1, (cr * (Math.sin(circles[i].nxtTheta)))+circles[i].y1, 4, 0, 2 * Math.PI);
		    ctx.fillStyle = "blue";
		    ctx.fill();
	    ctx.closePath();

	 }
	
}

$(function () {
    $('#start_time').datetimepicker({
        format: 'MM/DD/YYYY LT'
    });
    $('#end_time').datetimepicker({
        format: 'MM/DD/YYYY LT'
    });
});

$(document).ready(function () {
	$('input[type=radio][name=sign-type]').change(function() {
    	console.log(this.value);
    });
});

$(".dropdown-menu li a").click(function(){
	  var selText = $(this).text();
	  $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');

      var type = $(this).parents('.btn-group').attr('id');
      
		if (type == "mutcd") {
			mutcd = selText;
            changePriority(mutcd);
		}

		if (type == "priority") {
			priority = selText;
		}

		if (type == "direction") {
			direction = selText;
		}

		if (type == "extent") {
			extent = selText;
		}

		if (type == "info-type") {
			info_type = selText;
		}

        if(type == "time") {
            ttl = selText;
        }
});


function changePriority(mutcd){

    var change = true;

    switch(mutcd.substring(1,2)){
        case '0':
            priority = 0
            break;
        case '1':
            priority = 6
            break;
        case '2':
            priority = 5
            break;
        case '3':
            priority = 4
            break;
        case '4':
            priority = 3
            break;
        case '5':
            priority = 2
            break;
        case '6':
            priority = 1
            break;
        default:
            change = false;
    }

    if (change) {
        $('#priority .dropdown-toggle').html(priority + " <span class='caret'></span>");
    }
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function getElevation(dot, latlon, i, j, callback){
	
    $.ajax({
        url: elevation_url + latlon.lat + ',' + latlon.lon + '&key=' + apiKey,
        dataType: 'jsonp',
        jsonp: 'jsonp',
        cache: false,
        success: function(result){
            elev = result.resourceSets[0].resources[0].elevations[0];
            if (elev == null){
                elev = -9999; //any sea value is set to -9999 by default. This brings it back to sea level as we know it
            }
            callback(elev, i, j, latlon, dot);
        },
        error: function(error){
            callback(-9999, i, j, latlon, dot);
        }
    });
}

function toggleWidthArray() {

    if (laneWidths.features.length == 0) {
        var masterWidth;

        for (var f = 0; f < vectors.features.length; f++) {
            if (vectors.features[f].attributes.marker.type == "TIM") {
                masterWidth = parseFloat(vectors.features[f].attributes.masterLaneWidth);
            }
        }

        for (var i = 0; i < lanes.features.length; i++) {

            var widthList = [];
            var widthDeltaTotal = 0;
            var flipped = false;
            var isNegative = {"value": false, "node": "", "lane": ""};

            for (var j = 0; j < lanes.features[i].geometry.components.length; j++) {

                var point1 = '';
                var point2 = '';

                if (j < lanes.features[i].geometry.components.length - 1) {
                    if (lanes.features[i].geometry.components[j].x == lanes.features[i].geometry.components[j + 1].x && lanes.features[i].geometry.components[j].y == lanes.features[i].geometry.components[j + 1].y) {
                        j++; //to prevent dots that are the exact same.
                    }
                }

                if (j < lanes.features[i].geometry.components.length - 1) {
                    point1 = new OpenLayers.LonLat(lanes.features[i].geometry.components[j].x, lanes.features[i].geometry.components[j].y).transform(toProjection, fromProjection);
                    point2 = new OpenLayers.LonLat(lanes.features[i].geometry.components[j + 1].x, lanes.features[i].geometry.components[j + 1].y).transform(toProjection, fromProjection);
                } else {
                    point1 = new OpenLayers.LonLat(lanes.features[i].geometry.components[j].x, lanes.features[i].geometry.components[j].y).transform(toProjection, fromProjection);
                    if (lanes.features[i].geometry.components[j].x == lanes.features[i].geometry.components[j - 1].x && lanes.features[i].geometry.components[j].y == lanes.features[i].geometry.components[j - 1].y) {
                        point2 = new OpenLayers.LonLat(lanes.features[i].geometry.components[j - 2].x, lanes.features[i].geometry.components[j - 2].y).transform(toProjection, fromProjection); //to prevent dots that are the exact same.
                        flipped = true;
                    } else {
                        point2 = new OpenLayers.LonLat(lanes.features[i].geometry.components[j - 1].x, lanes.features[i].geometry.components[j - 1].y).transform(toProjection, fromProjection);
                    }
                }

                var widthDelta = parseFloat(lanes.features[i].attributes.laneWidth[j]);
                if (isNaN(widthDelta) || widthDelta == null || typeof widthDelta == "undefined") {
                    widthDelta = 0
                }

                widthDeltaTotal = widthDeltaTotal + widthDelta;

                if (masterWidth + widthDeltaTotal < 0){
                    console.log(masterWidth + widthDeltaTotal)
                    isNegative = {"value": true, "node": j+1, "lane": i};
                    widthDeltaTotal = 0 - masterWidth;
                }

                var inverse = inverseVincenty(point1.lat, point1.lon, point2.lat, point2.lon);

                var direct1 = directVincenty(point1.lat, point1.lon, inverse.bearing + 90, (((masterWidth + widthDeltaTotal) / 2) / 100));
                var direct2 = directVincenty(point1.lat, point1.lon, inverse.bearing - 90, (((masterWidth + widthDeltaTotal) / 2) / 100));

                var newPoint1 = new OpenLayers.Geometry.Point(direct1.lon, direct1.lat).transform(fromProjection, toProjection);
                var newPoint2 = new OpenLayers.Geometry.Point(direct2.lon, direct2.lat).transform(fromProjection, toProjection);

                if (j == lanes.features[i].geometry.components.length - 1) {
                    j++; //flips the j value since it's the last lane point and we need to build in reverse
                }

                if (isOdd(j) && !flipped) {
                    widthList.push(newPoint1, newPoint2);
                    widthBox = new OpenLayers.Geometry.LinearRing(widthList);
                    laneWidths.addFeatures(new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Polygon([widthBox])));
                    widthList = [];
                    widthList.push(newPoint1, newPoint2);
                } else {
                    widthList.push(newPoint2, newPoint1);
                    widthBox = new OpenLayers.Geometry.LinearRing(widthList);
                    laneWidths.addFeatures(new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Polygon([widthBox])));
                    widthList = [];
                    widthList.push(newPoint2, newPoint1);
                    flipped = false;
                }

            }
        }
    } else {
        laneWidths.destroyFeatures();
    }

    if (isNegative.value){
        alert("Width deltas sum to less than zero on lane " + lanes.features[isNegative.lane].attributes.laneNumber + " at node " + isNegative.node + "!");
    }

}

function isOdd(num) { return (num % 2) == 1;}