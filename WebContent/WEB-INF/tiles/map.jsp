<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<style>
#map {
	height: 600px;
	height: 600px;
	margin: 0px;
	padding: 0px
}
</style>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBiKZEYI58kG67y8dT50HG4ByxMmWHbwXA">
</script>

<script type="text/javascript">
	var contentString;
	var markerLat, markerLong, addresses, rooms, rent;
    markerLat = [
        <c:forEach var="houses" items="${houses}">
            <c:out value="${houses.lat}"/>,
        </c:forEach>];
    markerLong = [
      <c:forEach var="houses" items="${houses}">
          <c:out value="${houses.lng}"/>,
      </c:forEach>];
    rooms = [
          <c:forEach var="houses" items="${houses}">
              <c:out value="${houses.rooms}"/>,
          </c:forEach>];
    rent = [
	        <c:forEach var="houses" items="${houses}">
	            <c:out value="${houses.rent}"/>,
	        </c:forEach>];
    ids = [<c:forEach var="houses" items="${houses}">
			    <c:out value="${houses.id}"/>,
	      </c:forEach>];

    function initialize() {
        var map;
        var initlatlng = new google.maps.LatLng(markerLat[0],markerLong[0]);
        var mapOptions = {
            zoom: 8,
            center: new google.maps.LatLng(53.482243, -7.955507),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById('map'), mapOptions);
        var infowindow = new google.maps.InfoWindow(); 
        var marker, i;

        for (i = 0; i < markerLat.length; i++) {
            marker = new google.maps.Marker({
                position: new google.maps.LatLng(markerLat[i], markerLong[i]),
                map: map
            });
            
            google.maps.event.addListener(marker, 'click', (function(marker, i) {
                return function() {
                	contentString = '<div id="infowindow"><a href="house/' + ids[i] + '">' + "View house" +
                	 '</a>' + '<br><u> Rooms available:</u> ' + rooms[i] + '<br><u> Rent p/m</u>: &#0128;' 
                	 + rent[i] + '<br><img src="https://maps.googleapis.com/maps/api/streetview?size=800x500&location=' + markerLat[i] +',' + markerLong[i] + '"/>' + '</div>';
                    
                    infowindow.setContent(contentString);
                    infowindow.open(map, marker);
                }
            })(marker, i));
        }
    }

    google.maps.event.addDomListener(window, 'load', initialize);
</script>

<div class="container">
<div id="map"></div>
</div>