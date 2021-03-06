function getCurrentPosition(callback , skills){

	navigator.geolocation.getCurrentPosition(function(position){

					var longitude = position.coords.longitude;
			    	var latitude = position.coords.latitude;
			    	callback(latitude , longitude , skills);
				}, function(e){
					$("#jobSearchForm").unmask();
					switch (e.code) {
						case e.PERMISSION_DENIED:
							alert('You have denied access to your position. You will ' +
									'not get the most out of the application now.'); 
							break;
						case e.POSITION_UNAVAILABLE:
							alert('There was a problem getting your position.'); 
							break;
						case e.TIMEOUT:
									alert('The application has timed out attempting to get ' +
											'your location.'); 
							break;
						default:
							alert('There was a horrible Geolocation error that has ' +
									'not been defined.');
					}
				},
					{ timeout: 45000 }

				);
}

function callback(latitude , longitude , skills){

	console.log('longitude .. '+longitude);
	console.log('latitude .. '+latitude);
	var userLocation = new google.maps.LatLng(latitude , longitude);
	$.get("/jobs/nearme/"+skills+"?longitude="+longitude+"&latitude="+latitude  , function (jobs){ 
		 $("#jobSearchForm").unmask();
		 renderAllJobs(jobs , userLocation);
	});
}

function renderAllJobs(jobs , userLocation){
	var directionsService = new google.maps.DirectionsService();
	$.each(jobs , function(index , job){
		renderJob(job , userLocation , directionsService);
	});
}

function renderJob(job , userLocation , directionsService){
	console.log(JSON.stringify(job));
	console.log(job.title);
	console.log(job.id);
	console.log(job.lngLat[0]);
	console.log(job.lngLat[1]);
	var jobRow = "<div class='row'>";
	jobRow += "<h2>"+job.title+" at "+job.company+"</h2>";
	jobRow += "<div id='routeMap-"+job.id+"' class='span6' style='height: 500px'></div>";
	jobRow += "<div id='directionsPanel-"+job.id+"' class='span5 offset1' style='height: 500px;overflow:scroll'></div>";
	jobRow += "</div>";
	$('#results').append(jobRow);

	var mapOptions = {
					  zoom: 3,
					  center: userLocation,
					  mapTypeControlOptions: {
					     style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
					   },
					  mapTypeId: google.maps.MapTypeId.ROADMAP,
					  zoomControlOptions: {
						  style: google.maps.ZoomControlStyle.SMALL
					  }
			};

	var map = new google.maps.Map(document.getElementById('routeMap-'+job.id),
					      mapOptions);

	var directionRenderer = new google.maps.DirectionsRenderer({suppressMarkers: true});
	directionRenderer.setMap(map);

	// Start/Finish icons
 var icons = {
  start: new google.maps.MarkerImage(
   // URL
   'http://icons.iconarchive.com/icons/icons-land/vista-people/48/Office-Customer-Male-Light-icon.png',
   // (width,height)
   new google.maps.Size( 44, 32 ),
   // The origin point (x,y)
   new google.maps.Point( 0, 0 ),
   // The anchor point (x,y)
   new google.maps.Point( 22, 32 )
  ),
  end: new google.maps.MarkerImage(
   // URL
   'http://icons.iconarchive.com/icons/mad-science/olive/32/Martinis-Briefcase-icon.png',
   // (width,height)
   new google.maps.Size( 44, 32 ),
   // The origin point (x,y)
   new google.maps.Point( 0, 0 ),
   // The anchor point (x,y)
   new google.maps.Point( 22, 32 )
  )
 };

	var request = {
				   origin : userLocation,
				   destination : new google.maps.LatLng(job.lngLat[1], job.lngLat[0]),
				   travelMode : google.maps.DirectionsTravelMode.DRIVING,
				   unitSystem: google.maps.UnitSystem.METRIC
	};

	directionsService.route(request , function(result , status){
		if(status == google.maps.DirectionsStatus.OK){
			directionRenderer.setDirections(result);
			console.log(result);
			var leg = result.routes[0].legs[0];
			 makeMarker( map , leg.start_location, icons.start, "title" );
			 makeMarker( map , leg.end_location, icons.end, 'title' );

			directionRenderer.setPanel(document.getElementById("directionsPanel-"+job.id));
		}
	});

}

function makeMarker( map , position, icon, title ) {
 new google.maps.Marker({
  position: position,
  map: map,
  icon: icon,
  title: title
 });
}
