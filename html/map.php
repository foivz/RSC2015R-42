<html>
<head>
    <title>Leaflet marker array example</title>

    <link rel="stylesheet" href="src/leaflet/leaflet.css" />
    <!--[if lte IE 8]><link rel="stylesheet" href="src/leaflet/leaflet.ie.css" /><![endif]-->

    <script src="/src/leaflet/leaflet.js"></script>

    <script language="javascript">
            var map;
			$(document).ready(function() {
    			setInterval("ajaxd()",1000);
			});
			var arr;
			function ajaxd() { 
  				$.ajax({
   					type: "GET",
   					url: "/API/location_update.php",
   					data: "user=success",
   					success: function(msg){
						arr = [];
						var blju = JSON.parse(msg);
     					$.each(blju, function(idx, obj) {
							console.log(obj.location);
							var res = obj.location.split(":");
							arr.push(res);
						});
            for (var i=0; i<arr.length; i++) {

                var lon = arr[i][1];
                var lat = arr[i][0];
                var popupText = arr[i][2];

                var markerLocation = new L.LatLng(lat, lon);
                var marker = new L.Marker(markerLocation);
                map.addLayer(marker);

                marker.bindPopup(popupText);

            }
   					}
 				});
			}
        function init() {
            map = new L.Map('map');

            L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {

            }).addTo(map);
            map.attributionControl.setPrefix(''); // Don't show the 'Powered by Leaflet' text.

            var london = new L.LatLng(45.026463, 14.572405);
            map.setView(london, 20);

            // Define an array. This could be done in a seperate js file.
            // This tidy formatted section could even be generated by a server-side script
            // or fetched seperately as a jsonp request.
            var markers = [
                [ -0.119623, 51.5006728],
                [ -0.1244324, 51.5006728],
                [ -0.119623, 51.503308],
                [ -0.1279688, 51.5077286]
            ];

            //Loop through the markers array

        }
    </script>

</head>
<body onLoad="javascript:init()" >
<div id="map" style="height: 200px"></div>

</body>
</html>
