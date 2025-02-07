import { getElevation } from "./api.js";
/*********************************************************************************************************************/
/**
 * Purpose: dot functions that bind the metadata to the feature object
 * @params  the feature and it's metadata
 * @event creates variables attached to the feature object and store the values
 */

function onFeatureAdded(lanes, vectors, laneMarkers, laneWidths, isLoadMap){
	laneMarkers.getSource().clear();
	let laneFeatures = lanes.getSource().getFeatures();
	for(let i = 0; i < laneFeatures.length; i++)
	{
			let laneFeat = laneFeatures[i];
			let max = laneFeat.getGeometry().getCoordinates().length;
			if (typeof laneFeat.get("elevation") == 'undefined') {
				laneFeat.set("elevation", []);
			}
			if (typeof laneFeat.get("laneWidth") == 'undefined') {
				laneFeat.set("laneWidth", []);
			} else if (laneFeat.get("laneWidth").constructor !== Array) {
				// Old maps may contain laneWidth as a single value, so initialize an empty array
				laneFeat.set("laneWidth", []);
				for(var z = 0; z < max; z++) {
					laneFeat.get("laneWidth")[z] = 0;
				}
			}
			if(!laneFeat.get("computed")) 
			{
			// Loop through the lane vertices and see if there are any new dots to
			// for(var j=0; j< max; j++){
			// 	// If the laneIndex marker doesn't exist in this vertex, this is a new dot
			// 	if(typeof laneFeat.geometry.components[j].nodeInitialized === 'undefined') {
			// 		if(isLoadMap) {
			// 			// Saved maps will not include the nodeInitialized flag.
			// 			// Inject this flag when loading a map
			// 			laneFeat.geometry.components[j].nodeInitialized = true;
			// 		} else {
			// 			// Insert dummy values into the laneWidth and elevation arrays so that
			// 			// they are the correct size for the next loop through.  This is done because
			// 			// when a new dot is found, we need to perform a call to look up elevation which
			// 			// is an asynchronous call.  If we try to do this in a single pass over the vertices
			// 			// the call can take too long to return and the code iterates to the next nodes
			// 			// but a value has not yet been inserted into the elevation array causing the array
			// 			// to be too small.
			// 			(laneFeat.get("elevation")).splice(j, 0, "tempValue");
			// 			(laneFeat.get("laneWidth")).splice(j, 0, "tempValue");
			// 		}
			// 	}
			// }
		
				// Now run through and 
				let laneCoordinates = laneFeat.getGeometry().getCoordinates();
				for(let j=0; j< max; j++)
				{
						// Create a dot & latlon for this line vertex
						let dot = new ol.Feature(new ol.geom.Point(laneCoordinates[j]));
						let lonLat = new ol.proj.toLonLat(laneCoordinates[j]);
						let latLon = {lat: lonLat[1], lon: lonLat[0]}

						// If the laneIndex marker doesn't exist in this vertex, this is a new dot
					// if(typeof laneFeat.geometry.components[j].nodeInitialized === 'undefined') {
					if(!laneFeat.getGeometry()){
					// Insert new values for elevation and laneWidth for the new dot
							getElevation(dot, latlon, i, j, function(elev, i, j, latlon, dot){
							laneFeat.get("elevation")[j] = {'value': elev, 'edited': true, 'latlon': latlon};
						});
						laneFeat.get("laneWidth")[j] = 0;
						
						// If this is a source lane for computed lanes, record this index as a new node
						if(laneFeat.attributes.source) {
										if(typeof laneFeat.attributes.newNodes === 'undefined') {
												laneFeat.attributes.newNodes = [];
												
												// Count how many computes lane exist for this source lane
												laneFeat.attributes.computedLaneCount = 0;
												for(var c = 0; c < lanes.features.length; c++) {
												if(lanes.features[c].attributes.computed &&
													lanes.features[c].attributes.referenceLaneID == laneFeat.attributes.laneNumber) {
													laneFeat.attributes.computedLaneCount++;
												}
											}
										}
										laneFeat.attributes.newNodes.push(j);
									}
						
						// Mark the dot as being seen
						laneFeat.geometry.components[j].nodeInitialized = true;
					} else {
						// This node already existed
						
						// Compare the latitude and longitude from the existing lane values to see if the node moved
						var latMatch = ((laneFeat.get("elevation")[j].latlon.lat).toString().match(/^-?\d+(?:\.\d{0,11})?/)[0] == (latLon.lat).toString().match(/^-?\d+(?:\.\d{0,11})?/)[0]);
						var lonMatch = ((laneFeat.get("elevation")[j].latlon.lon).toString().match(/^-?\d+(?:\.\d{0,11})?/)[0] == (latLon.lon).toString().match(/^-?\d+(?:\.\d{0,11})?/)[0]);
						// console.log("latMatch: " + latMatch + " lonMatch: " + lonMatch + " elevation edited: " + laneFeat.get("elevation")[j].edited)
						// If the node elevation has never been edited or has moved along either axis,
						// get a new elevation value
						if (!laneFeat.get("elevation")[j].edited || !latMatch || !lonMatch){
											getElevation(dot, latLon, i, j, function(elev, i, j, latLon, dot){
													laneFeat.get("elevation")[j] = {'value': elev, 'edited': true, 'latlon': latLon};
											});
									}
					}

					buildDots(i, j, dot, latLon, lanes, laneMarkers);
			}
		} else {
				buildComputedFeature(i,
								laneFeat.attributes.laneNumber,
								laneFeat.attributes.referenceLaneID,
										laneFeat.attributes.referenceLaneNumber,
										laneFeat.attributes.offsetX,
										laneFeat.attributes.offsetY,
										laneFeat.attributes.rotation,
										laneFeat.attributes.scaleX,
										laneFeat.attributes.scaleY,
										laneFeat.attributes.computedLaneID);
		}
	};

	if (laneWidths.getSource().getFeatures().length != 0) {
		toggleWidthArray(lanes, vectors, laneWidths);
	}
}

function buildDots(i, j, dot, latlon, lanes, laneMarkers){
	// Don't look at computed dots, they are handled by other functions
	let laneFeatures = lanes.getSource().getFeatures();
	if(!laneFeatures[i].get("computed")) {
    	dot.setProperties({"lane": i, "number": j, "LatLon": latlon,
    		"descriptiveName" : laneFeatures[i].get("descriptiveName"),
        	"laneNumber": laneFeatures[i].get("laneNumber"), "laneWidth": laneFeatures[i].get("laneWidth"), "laneType": laneFeatures[i].get("laneType"), "sharedWith": laneFeatures[i].get("sharedWith"),
	        "stateConfidence": laneFeatures[i].get("stateConfidence"), "spatRevision": laneFeatures[i].get("spatRevision"), "signalGroupID": laneFeatures[i].get("signalGroupID"), "lane_attributes": laneFeatures[i].get("lane_attributes"),
    	    "startTime": laneFeatures[i].get("startTime"), "minEndTime": laneFeatures[i].get("minEndTime"), "maxEndTime": laneFeatures[i].get("maxEndTime"),
        	"likelyTime": laneFeatures[i].get("likelyTime"), "nextTime": laneFeatures[i].get("nextTime"), "signalPhase": laneFeatures[i].get("signalPhase"), "typeAttribute": laneFeatures[i].get("typeAttribute"),
	        "connections": laneFeatures[i].get("connections"), "elevation": laneFeatures[i].get("elevation")[j],
    	    "computed": laneFeatures[i].get("computed"), "source": laneFeatures[i].get("source")
	    });
	    laneMarkers.getSource().addFeature(dot);
	}
}

function buildComputedFeature(i, laneNumber, referenceLaneID, referenceLaneNumber, offsetX, offsetY, rotation, scaleX, scaleY, computedLaneID){

	var r = Number(referenceLaneNumber);
	var max = lanes.features[r].geometry.getVertices().length;

	var initialize = false;
	if(typeof computedLaneID === 'undefined') {
		computedLaneID = Math.random().toString(36).substr(2, 9);
		initialize = true;
	}

	var points = [];
	var zeroLatlon = "";
	for (var j = 0; j < max; j++) {
		if (j == 0 ){
			// Apply offset to first dot's lat/lon.  No scaling or rotation needs to be performed
			var zeroPoint = new OpenLayers.Geometry.Point(
					lanes.features[r].geometry.getVertices()[j].x + offsetX / 100,
					lanes.features[r].geometry.getVertices()[j].y + offsetY / 100);
			var zeroDot = new OpenLayers.Feature.Vector(zeroPoint);
			zeroLatlon = new OpenLayers.LonLat(zeroDot.geometry.x, zeroDot.geometry.y).transform(toProjection, fromProjection);
			points.push(zeroPoint);
			buildComputedDot(i, j, laneNumber,
								referenceLaneID, referenceLaneNumber, 
								zeroDot, zeroLatlon,
								offsetX, offsetY,
								rotation,
								scaleX, scaleY,
								computedLaneID,
								initialize);
		} else {
			// Apply offset & scaling to dot
			var deltaScaleX = 
				(lanes.features[r].geometry.getVertices()[j].x - lanes.features[r].geometry.getVertices()[0].x) * scaleX/100;
			var deltaScaleY = 
				(lanes.features[r].geometry.getVertices()[j].y - lanes.features[r].geometry.getVertices()[0].y) * scaleY/100;
			var tempPoint = new OpenLayers.Geometry.Point(
					lanes.features[r].geometry.getVertices()[j].x + deltaScaleX + (offsetX / 100),
					lanes.features[r].geometry.getVertices()[j].y + deltaScaleY + (offsetY / 100));
			var tempDot = new OpenLayers.Feature.Vector(tempPoint);
			var tempLatlon = new OpenLayers.LonLat(tempDot.geometry.x, tempDot.geometry.y).transform(toProjection, fromProjection);
			
			// Apply rotation
			var inverse = inverseVincenty(zeroLatlon.lat, zeroLatlon.lon, tempLatlon.lat, tempLatlon.lon);
			var direct = directVincenty(zeroLatlon.lat, zeroLatlon.lon,
							Number(inverse.bearing) + Number(rotation), Number(inverse.distance));
			var newPoint = new OpenLayers.Geometry.Point(direct.lon, direct.lat).transform(fromProjection, toProjection);
			var newDot = new OpenLayers.Feature.Vector(newPoint);
			var newLatlon = new OpenLayers.LonLat(newDot.geometry.x, newDot.geometry.y).transform(toProjection, fromProjection);
			
			points.push(newPoint);
			buildComputedDot(i, j, laneNumber,
								referenceLaneID, referenceLaneNumber,
								newDot, newLatlon,
								offsetX, offsetY,
								rotation,
								scaleX, scaleY,
								computedLaneID,
								initialize);
		}
	}
	connectComputedDots(i, points, initialize);

	lanes.features[r].attributes.source = true;
	if(typeof lanes.features[r].attributes.newNodes !== 'undefined') {
		lanes.features[r].attributes.computedLaneCount--;
		if(lanes.features[r].attributes.computedLaneCount == 0) {
			// Remove any saved new nodes
			delete lanes.features[r].attributes.newNodes;
			delete lanes.features[r].attributes.computedLaneCount;
		}
	}
}

function buildComputedDot(i, j, laneNumber, referenceLaneID, referenceLaneNumber, dot, latlon, offsetX, offsetY, rotation, scaleX, scaleY, computedLaneID, initialize){
	if(typeof initialize === 'undefined') {
		initialize = false;
	}

	var r = Number(referenceLaneNumber);
	
	if(initialize) {
		dot.attributes={"lane": i, "number": j, "LatLon": latlon,
	    		"descriptiveName" : "",
		        "laneNumber": laneNumber, "laneWidth": lanes.features[r].attributes.laneWidth, "laneType": lanes.features[r].attributes.laneType, "sharedWith": lanes.features[r].attributes.sharedWith,
		        "stateConfidence": lanes.features[r].attributes.stateConfidence, "spatRevision": lanes.features[r].attributes.spatRevision, "signalGroupID": lanes.features[r].attributes.signalGroupID, "lane_attributes": lanes.features[r].attributes.lane_attributes,
		        "startTime": lanes.features[r].attributes.startTime, "minEndTime": lanes.features[r].attributes.minEndTime, "maxEndTime": lanes.features[r].attributes.maxEndTime,
		        "likelyTime": lanes.features[r].attributes.likelyTime, "nextTime": lanes.features[r].attributes.nextTime, "signalPhase": lanes.features[r].attributes.signalPhase, "typeAttribute": lanes.features[r].attributes.typeAttribute,
		        "connections": lanes.features[r].attributes.connections, "elevation": lanes.features[r].attributes.elevation[j].value,
		        "computed" : true,
		        "computedLaneID": computedLaneID, "referenceLaneID": referenceLaneID, "referenceLaneNumber": referenceLaneNumber, "offsetX": offsetX, "offsetY": offsetY, "rotation": rotation, "scaleX": scaleX, "scaleY": scaleY
		    };
	} else {
		dot.attributes={"lane": i, "number": j, "LatLon": latlon,
	    		"descriptiveName" : lanes.features[i].attributes.descriptiveName,
		        "laneNumber": laneNumber, "laneWidth": lanes.features[i].attributes.laneWidth, "laneType": lanes.features[i].attributes.laneType, "sharedWith": lanes.features[i].attributes.sharedWith,
		        "stateConfidence": lanes.features[i].attributes.stateConfidence, "spatRevision": lanes.features[i].attributes.spatRevision, "signalGroupID": lanes.features[i].attributes.signalGroupID, "lane_attributes": lanes.features[i].attributes.lane_attributes,
		        "startTime": lanes.features[i].attributes.startTime, "minEndTime": lanes.features[i].attributes.minEndTime, "maxEndTime": lanes.features[i].attributes.maxEndTime,
		        "likelyTime": lanes.features[i].attributes.likelyTime, "nextTime": lanes.features[i].attributes.nextTime, "signalPhase": lanes.features[i].attributes.signalPhase, "typeAttribute": lanes.features[i].attributes.typeAttribute,
		        "connections": lanes.features[i].attributes.connections,
		        "computed" : lanes.features[i].attributes.computed, "computedLaneID": lanes.features[i].attributes.computedLaneID,
		        "referenceLaneID": lanes.features[i].attributes.referenceLaneID, "referenceLaneNumber": lanes.features[i].attributes.referenceLaneNumber,
		        "offsetX": lanes.features[i].attributes.offsetX, "offsetY": lanes.features[i].attributes.offsetY,
		        "rotation": lanes.features[i].attributes.rotation,
		        "scaleX": lanes.features[i].attributes.scaleX, "scaleY": lanes.features[i].attributes.scaleY
		    };
		
		// Elevation value depends on an array based on the number of nodes in the lane.
		// If a new vertex was added to the source lane via edit mode, then this has shifted
		// the lane's
		var elevationVal;
		if(typeof lanes.features[r].attributes.newNodes !== 'undefined') {
			// There are nodes added via editing
			if(lanes.features[r].attributes.newNodes.includes(j)) {
				// The point at this index is a new point, it does not have any saved data so default to 0
				elevationVal = 0;
			} else {
				// The point at this index is not a new point
				elevationVal = lanes.features[i].attributes.elevation[j].value;
			}
		} else {
			// No points were added to the source, copy elevation value directly
			elevationVal = lanes.features[i].attributes.elevation[j].value;
		}
		
		dot.attributes.elevation = elevationVal;
	}
	
    laneMarkers.addFeatures(dot);
}

function connectComputedDots(i, points, initialize){
	if(typeof initialize === 'undefined') {
		initialize = false;
	}

	var computedLanePoints = new OpenLayers.Geometry.LineString(points);

    if(initialize) {
    	var computedLaneFeat = new OpenLayers.Feature.Vector(computedLanePoints);
    	var m;
    	for (var k = 0; k < laneMarkers.features.length; k++) {
    		if(laneMarkers.features[k].attributes.lane == i && laneMarkers.features[k].attributes.number == 0) {
    			// The first node of the matching laneMarkers
    			m = k;
    			break;
    		}
    	}

        var r = laneMarkers.features[m].attributes.referenceLaneNumber;
	    computedLaneFeat.attributes={
		        "connections": laneMarkers.features[m].attributes.connections, "elevation": lanes.features[r].attributes.elevation,
		        "laneNumber": laneMarkers.features[m].attributes.laneNumber, "laneType": laneMarkers.features[m].attributes.laneType,
		        "laneWidth": laneMarkers.features[m].attributes.laneWidth, "lane_attributes": laneMarkers.features[m].attributes.lane_attributes,
		        "likelyTime": laneMarkers.features[m].attributes.likelyTime, "maxEndTime": laneMarkers.features[m].attributes.maxEndTime,
		        "minEndTime": laneMarkers.features[m].attributes.minEndTime,"nextTime": laneMarkers.features[m].attributes.nextTime,
		        "sharedWith": laneMarkers.features[m].attributes.sharedWith,
		        "signalGroupID": laneMarkers.features[m].attributes.signalGroupID, "signalPhase": laneMarkers.features[m].attributes.signalPhase,
		        "spatRevision": laneMarkers.features[m].attributes.spatRevision,
		        "startTime": laneMarkers.features[m].attributes.startTime,
		        "stateConfidence": laneMarkers.features[m].attributes.stateConfidence,
		        "typeAttribute": laneMarkers.features[m].attributes.typeAttribute,
		        
		        "computed": laneMarkers.features[m].attributes.computed, "computedLaneID": laneMarkers.features[m].attributes.computedLaneID,
		        "referenceLaneID": laneMarkers.features[m].attributes.referenceLaneID, "referenceLaneNumber": laneMarkers.features[m].attributes.referenceLaneNumber,
		        "offsetX": laneMarkers.features[m].attributes.offsetX, "offsetY": laneMarkers.features[m].attributes.offsetY,
		        "rotation": laneMarkers.features[m].attributes.rotation,
		        "scaleX": laneMarkers.features[m].attributes.scaleX, "scaleY": laneMarkers.features[m].attributes.scaleY
		    };
	    
	    // Initialize the elevations lat/lon to match the laneMarkers
	    for(var l = 0; l < computedLaneFeat.attributes.elevation.length; l++) {
	    	for (var k = 0; k < laneMarkers.features.length; k++) {
	    		if(laneMarkers.features[k].attributes.lane == i && laneMarkers.features[k].attributes.number == l) {
	    			computedLaneFeat.attributes.elevation[l].latlon = laneMarkers.features[k].attributes.LatLon;
	    			break;
	    		}
	    	}
	    }
	    
    	lanes.addFeatures(computedLaneFeat);
    } else {
    	var r = lanes.features[i].attributes.referenceLaneNumber;
		for(var j = 0; j < computedLanePoints.components.length; j++) {
			if(typeof lanes.features[r].attributes.newNodes !== 'undefined' &&
					lanes.features[r].attributes.newNodes.includes(j)) {
				// The source lane had points added via edit and this is one of them
				var newPoint = new OpenLayers.Geometry.Point(
													computedLanePoints.components[j].x,
													computedLanePoints.components[j].y);
				lanes.features[i].geometry.addPoint(newPoint, j);
			}
			else {
				lanes.features[i].geometry.components[j].move(
											computedLanePoints.components[j].x - lanes.features[i].geometry.components[j].x,
											computedLanePoints.components[j].y - lanes.features[i].geometry.components[j].y);
			}
		}
		lanes.redraw();
    }
}

function toggleWidthArray(lanes, vectors, laneWidths) {
  if (laneWidths.getSource().getFeatures().length == 0) {
      var masterWidth;
			let vectorFeatures = vectors.getSource().getFeatures();
			let laneFeatures = lanes.getSource().getFeatures();
      for (var f = 0; f < vectorFeatures.length; f++) {
          if (vectorFeatures[f].get("marker").name == "Reference Point Marker") {
              masterWidth = parseFloat(vectorFeatures[f].get("masterLaneWidth"));
          }
					console.log(vectorFeatures[f].get("marker"))
					console.log(vectorFeatures[f])
      }

      for (var i = 0; i < laneFeatures.length; i++) {

          var widthList = [];
          var widthDeltaTotal = 0;
          var flipped = false;
          var isNegative = {"value": false, "node": "", "lane": ""};

          for (var j = 0; j < laneFeatures[i].geometry.components.length; j++) {

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
      laneWidths.getSource().clear();
  }

  if (isNegative.value){
      alert("Width deltas sum to less than zero on lane " + lanes.features[isNegative.lane].attributes.laneNumber + " at node " + isNegative.node + "!");
  }
}

export {
	onFeatureAdded
}