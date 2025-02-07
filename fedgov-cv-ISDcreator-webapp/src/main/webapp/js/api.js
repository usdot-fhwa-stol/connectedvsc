
const google_elevation_url = '/msp/googlemap/api/elevation';
const intersection_url = "//api.geonames.org/findNearestIntersectionJSON"
async function getElevation(dot, latlon, i, j, callback){
    try {
      const response = await fetch(google_elevation_url + "/" + latlon.lat + '/' + latlon.lon);
      const result = await response.json();
      let elev = result?.elevation;
      if (elev == null || elev == undefined) {
        elev = -9999; // any sea value is set to -9999 by default. This brings it back to sea level as we know it
      } else {
        elev = Math.round(elev);
      }
      callback(elev, i, j, latlon, dot);
    } catch (error) {
      callback(-9999, i, j, latlon, dot);
    }
  }

async function getElev(lat, lon) {
  try {
    const response = await fetch(google_elevation_url + "/" + lat + '/' + lon);
    const result = await response.json();
    let elev = result?.elevation;
    if (elev == null || elev == undefined) {
      elev = -9999; //any sea value is set to -9999 by default. This brings it back to sea level as we know it
    } else {
      elev = Math.round(elev);
    }
    return elev;
  } catch (error) {
    console.error(error);
    return -9999;
  }
}

async function getNearestIntersectionJSON(feature, lat, lon) {
  document.getElementById('intersection_name').value =  feature.get("intersectionName")? feature.get("intersectionName") : "Temporary Name";
  try {
    const response = await fetch(`${intersection_url}?lat=${lat}&lng=${lon}&username=NA`);
    const result = await response.json();
    if (result.intersection) {
      let name = result.intersection.street1 + " & " + result.intersection.street2;
      document.getElementById('intersection_name').value = name;
      feature.set("intersectionName", name);
    } else {
      console.log("intersection not found");      
    }
  } catch (error) {
    console.error(error);
  }
}
export {
  getElevation,
  getElev,
  getNearestIntersectionJSON
}