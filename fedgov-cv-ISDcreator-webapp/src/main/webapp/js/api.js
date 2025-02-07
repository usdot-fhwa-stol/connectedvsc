
const google_elevation_url='/msp/googlemap/api/elevation';
async function getElevation(dot, latlon, i, j, callback){
    // $.ajax({
		// url: google_elevation_url+"/"+latlon.lat+'/'+latlon.lon,
    //     success: function(result){
		// 	// elev = result?.elevation;
    //   //       if (elev == null || elev == undefined){
    //   //           elev = -9999; //any sea value is set to -9999 by default. This brings it back to sea level as we know it
    //   //       }else{
		// 	// 	elev = Math.round(elev);
		// 	// }
    //       callback(elev, i, j, latlon, dot);
    //     },
    //     error: function(error){
    //         callback(-9999, i, j, latlon, dot);
    //     }
    // });
    callback(-9999, i, j, latlon, dot);
  }

export {
  getElevation
}