function laneSelectInteractionCallback(evt, overlayLayers, lanes, laneWidths, deleteMode, selected){
    if (evt.selected?.length > 0) {
      console.log('Lane feature selected:', evt.selected[0]);
    }else{
      console.log("No lane feature selected, ignore");
      return;
    }

    let selectedLane = evt.selected[0];
    // Find the layer by checking which vector source contains the feature
    const laneLayer = overlayLayers.getLayers().getArray().find(layer=>{
      return selectedLane && layer instanceof ol.layer.Vector && layer.getSource().hasFeature(selectedLane)
    })

    if (deleteMode){
        if(selectedLane.get("source")) {
          // Watch out for computed lanes relying on this lane
          let dependentLanes = [];
          for(let i = 0; i < laneLayer.getSource().getFeatures().length; i++) {
            if(laneLayer.getSource().getFeatures()[i].get("computed") &&
              laneLayer.getSource().getFeatures()[i].get("referenceLaneID") == selectedLane.get("laneNumber")) {
              dependentLanes.push(laneLayer.getSource().getFeatures()[i]);
            }
          }
          let doDelete = confirm(dependentLanes.length + " computed lanes depend on this lane. " +
                      "Deleting this lane will delete them all. Continue?");

          if(doDelete) {
            dependentLanes.push(selectedLane);		// Don't forget to delete this as well
            for(var i = 0; i < dependentLanes.length; i++) {
              deleteMarker(laneLayer, dependentLanes[i], lanes, selected);
            }
            laneWidths.getSource().clear();
          }
          else {
            // controls.del.unselect(selectedLane);
          }
        }
        else if(selectedLane.get("computed")) {
          // Check if the source lane for this computed lane
          // has any other computed lanes.
          let r = Number(selectedLane.get("referenceLaneNumber"));          
          let computedCount = 0;
          for(let i = 0; i < laneLayer.getSource().getFeatures().length; i++) {
              if(laneLayer.getSource().getFeatures()[i].get("computed") &&
              laneLayer.getSource().getFeatures()[i].get("referenceLaneID")  == laneLayer.getSource().getFeatures()[r].get("laneNumber")) {
                computedCount++;
              }
          }

          if(computedCount == 1) {
            // This was the only computed lane for the source lane, it is no longer a source lane
            laneLayer.getSource().getFeatures()[r].set("source", false);
          }

          // Delete this computed lane
          deleteMarker(laneLayer,selectedLane,lanes, selected);
          laneWidths.getSource().clear();
        }
        else {
          deleteMarker(laneLayer, selectedLane,lanes, selected);
          laneWidths.getSource().clear();
        }
    }
}


function vectorAddInteractionCallback(evt){
  if (evt.selected?.length > 0) {
    console.log('Vector feature added:', evt.selected[0]);
  }else{
    console.log("No vector feature added, ignore");
    return;
  }

  let selectedVector = evt.selected[0];
  updateFeatureLocation(selectedVector);
}


function vectorSelectInteractionCallback(evt, overlayLayers, lanes, deleteMode, selected){
  if (evt.selected?.length > 0) {
    console.log('Vector feature selected:', evt.selected[0]);

    let selectedVector = evt.selected[0];
    const vectorLayer = overlayLayers.getLayers().getArray().find(layer=>{
      return selectedVector && layer instanceof ol.layer.Vector && layer.getSource().hasFeature(selectedVector)
    })

    if (deleteMode){
      deleteMarker(vectorLayer, selectedVector, lanes, selected);
    } else {
      updateFeatureLocation( selectedVector );
    }
  }else if (evt.deselected?.length >0 ){
    console.log('Vector feature deselected:', evt.deselected[0]);
    $("#attributes").hide();
  }else{
    console.log("No vector feature selected, ignore");
  }
  
}

/**
 * Purpose: if lat/long is modified, it changes the location
 * @params  the feature and it's metadata
 * @event changes the location on the map by redrawing
 */
function updateFeatureLocation( feature ) {
  console.log("called updateFeatureLocation")
	// referencePointWindow(feature);
	// feature.attributes.LonLat = (new OpenLayers.LonLat(feature.geometry.x, feature.geometry.y)).transform(toProjection, fromProjection);
	// $('#long').val(feature.attributes.LonLat.lon);
	// $('#lat').val(feature.attributes.LonLat.lat);
	// populateRefWindow(feature, feature.attributes.LonLat.lat, feature.attributes.LonLat.lon);

  //   if (feature.attributes.marker.name == "Reference Point Marker") {
  //       if (!feature.attributes.intersectionID && !feature.attributes.intersectionIdEdit) {
  //           var tempLat = ((Math.abs(feature.attributes.LonLat.lat) % 1).toString().substr(3,3));
  //           var tempLon = ((Math.abs(feature.attributes.LonLat.lon) % 1).toString().substr(3,3));
  //           intersectionID = (((tempLat & 0xff) << 8) | (tempLon & 0xff)) >>> 0;
  //           $("#intersection").val(intersectionID);
  //       } else {
  //           intersectionID = feature.attributes.intersectionID;
  //           $("#intersection").val(feature.attributes.intersectionID);
  //       }
  //   }

  //   $("#intersection").on("propertychange change click keyup input paste", function(){
  //       if ($("#intersection").val() != intersectionID) {
  //           feature.attributes.intersectionIdEdit = true;
  //           feature.attributes.intersectionID = $("#intersection").val();
  //           intersectionID = $("#intersection").val();
  //       }
  //   });
}

function deleteMarker(featureLayer, feature, lanes, selected) {
  try {
      if (selected == "child" &&
          (typeof feature.get("marker") != 'undefined') &&
          (feature.get("marker").name == "Verified Point Marker" || feature.get("marker").name == "Reference Point Marker")) {
          alert("Cannot delete a reference point in a child map.")
      } else {
        // Computed lanes are dependent on the indexing of source lanes.
        // When removing a lane we must update all computed lanes references
        if(featureLayer === lanes) {
              // Find the index of this lane, we only care about source lanes after this index
              var i;
              for(i = 0; i < featureLayer.getSource().getFeatures().length; i++) {
                if(featureLayer.getSource().getFeatures()[i].get("laneNumber") == feature.get("laneNumber")) {
                  break;
                }
              }
              
              // We only care about computed lanes after the index of this lane being deleted
              // since there is no way computed lanes before this lane could refer to lanes created after
              for(var c = i; c < featureLayer.getSource().getFeatures().length; c++) {
                if(featureLayer.getSource().getFeatures()[c].get("computed")) {
                  var r = Number(featureLayer.getSource().getFeatures()[c].get("referenceLaneNumber"));
                  if(r > i) {
                    // This computed lane references a lane after the lane we are deleting.
                    // This referenced lane will slide down an index when this lane is deleted
                    // so reflect the change in the reference.
                    featureLayer.getSource().getFeatures()[c].set("referenceLaneNumber", r-1);
                  }
                }
              }
            }
            $("#attributes").hide();
            featureLayer.getSource().removeFeature(feature);
        }
  } catch (err){
    // Computed lanes are dependent on the indexing of source lanes.
    // When removing a lane we must update all computed lanes references
    if(featureLayer === lanes) {
        // Find the index of this lane, we only care about source lanes after this index
        var i;
        for(i = 0; i < featureLayer.getSource().getFeatures().length; i++) {
          if(featureLayer.getSource().getFeatures()[i].attributes.laneNumber == feature.attributes.laneNumber) {
            break;
          }
        }
        
        // We only care about computed lanes after the index of this lane being deleted
        // since there is no way computed lanes before this lane could refer to lanes created after
        for(var c = i; c < featureLayer.getSource().getFeatures().length; c++) {
          if(featureLayer.getSource().getFeatures()[c].attributes.computed) {
            var r = Number(featureLayer.getSource().getFeatures()[c].attributes.referenceLaneNumber);
            if(r > i) {
              // This computed lane references a lane after the lane we are deleting.
              // This referenced lane will slide down an index when this lane is deleted
              // so reflect the change in the reference.
              featureLayer.getSource().getFeatures()[c].attributes.referenceLaneNumber = r-1;
            }
          }
        }
      }
    
      $("#attributes").hide();
      featureLayer.getSource().removeFeature(feature);
  }
}


export {
  laneSelectInteractionCallback,
  vectorSelectInteractionCallback,
  vectorAddInteractionCallback,
  deleteMarker,
}