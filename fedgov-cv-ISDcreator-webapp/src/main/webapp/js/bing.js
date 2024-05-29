/**
 * Created by martzth on 2/15/2017.
 * Updated 3/2017 by martzth
 */


/**
 * Purpose: to lookup addresses using the bing API
 * @params: input text -> address, city, and state
 * @event: sets lat/long cookie and moves map
 *
 * @deprecated for google geocomplete in main.js
 */

/*
 $('#address-search-btn').click(function () {

 var str = $('#address-search').val();
 var address = str.split(',');
 var street = address[0];
 var city = address[1];
 var state = address[2];
 $('#address-search').val('');

 $.ajax({
 url: "https://dev.virtualearth.net/REST/v1/Locations/US/" + state + "/" + city + "/" + street + "?&key=" + apiKey,
 dataType: "jsonp",
 jsonp: "jsonp",
 success: function (result) {

 var search_lat = result.resourceSets[0].resources[0].point.coordinates[0];
 var search_lon = result.resourceSets[0].resources[0].point.coordinates[1];

 setCookie("tim_latitude", search_lat, 365);
 setCookie("tim_longitude", search_lon, 365);

 try {
 var location = new OpenLayers.LonLat(search_lon, search_lat);
 location.transform(new OpenLayers.Projection("EPSG:4326"), map.getProjectionObject());
 map.setCenter(location, 18);
 }
 catch (err) {
 console.log("No vectors to reset view");
 }
 },
 error: function (error){
 console.log("Location error: ", error);
 }
 });

 });
 */


/**
 * Purpose: to find and display tile age
 * @params: lat/long -> on map move (event registered in mapping.js)
 * @event: sets tile age text on DOM
 */

async function tileAge() {
    var apiKey = await getApiKey();

    var convertedLonLat = new OpenLayers.LonLat(map.getCenter().lon, map.getCenter().lat).transform(toProjection, fromProjection);
    var current_zoom = map.getZoom();
    if (current_zoom > 18) {
        current_zoom = 18;
    }
    $.ajax({
        url: "https://dev.virtualearth.net/REST/v1/Imagery/Metadata/Aerial/" + convertedLonLat.lat + "," + convertedLonLat.lon + "?uriScheme=https&zl=" + current_zoom + "&key=" + apiKey,
        dataType: "jsonp",
        jsonp: "jsonp",
        success: function (result) {
            try {
                var start = (result.resourceSets[0].resources[0].vintageStart).split(" ");
                var end = (result.resourceSets[0].resources[0].vintageEnd).split(" ");
                $('#tileAge .start').text(start[1] + "/" + start[2]);
                $('#tileAge .end').text(end[1] + "/" + end[2]);
            }
            catch (err) {
                $('#tileAge .start').text("");
                $('#tileAge .end').text("");
            }
        },
        error: function (error) {
            console.log("Location error: ", error);
        }
    });

}


/**
 * Purpose: set cookie so map loads to same position
 * @params: name, lat/lon or zoom, days of expiration (365)
 * @event: sets cookie
 */

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}
