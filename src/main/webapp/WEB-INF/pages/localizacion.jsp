<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:og="http://ogp.me/ns#"
      xmlns:fb="https://www.facebook.com/2008/fbml">

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageId" value="3"/>
</jsp:include>

<body onload="initialize()" onunload="GUnload()">
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/es_ES/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAAv59MEs3Fp68QXDZRysAswBS18u6nnkXX3dEWsOmSIMxtkmEvExQ49eDvphKY9r37LK0UgojumQAqig" type="text/javascript"></script>	
<script src="<c:url value='/scripts/extinfowindow.js'/>" type="text/javascript"></script>
<script type="text/javascript">
    function initialize() {
      if (GBrowserIsCompatible()) {
        var map = new GMap2(document.getElementById("map_canvas"));
        map.addControl(new GSmallMapControl());
        map.addControl(new GMapTypeControl());
        map.setCenter(new GLatLng(43.214129, -8.695872), 16);
        
       	var blueIcon = new GIcon(G_DEFAULT_ICON);
       	blueIcon.image = "http://www.google.com/intl/en_us/mapfiles/ms/micons/red-dot.png";
                
       	// Set up our GMarkerOptions object
       	markerOptions = { icon:blueIcon };

        var point = new GLatLng(43.214129, -8.695872);
        map.addOverlay(new GMarker(point, markerOptions));

        var marker = new GMarker(point, markerOptions);
        GEvent.addListener(marker, "click", function() {
          marker.openExtInfoWindow(map, 
          				"simple_example_window", 
          				"<ul><li><b>Panadería O Varrendeiro</b></li><li>Avda. das Flores, nº 11</li><li>981-70-16-91</li></ul>", 
          				{beakOffset: 3}
          );
        });
        map.addOverlay(marker);       
      }
    }
</script>


<div id="contenedor">
  
  <jsp:include page="/common/menu.jsp"/>
    
  <div class="titulo">Localización</div>
  
  <div>
 		<div class="subtitulo" style="width: 350px;">DIRECCIÓN</div>
		<div class="box1" style="width: 350px;">
			<ul>
				<li>Localidade: <b>Carballo (A Coruña)</b></li>	
				<li>Rúa: <b>Avda das Flores, nº 11</b></li>
				<li>Teléfono: <b>981701691</b></li>
				<li><strong>mail:&nbsp;</strong><a href="mailto:info@panaderiaovarrendeiro.es">info@panaderiaovarrendeiro.es</a></li>
			</ul>
		</div>	
	    
	    <!-- identificador para el div que contendra el mapa -->
		<div id="map_canvas" style="width: 550px; height: 400px; margin-top: 10px; margin-left:410px;">
		</div>
	</div> 

<div class="fb-like" data-href="http://www.panaderiaovarrendeiro.es" data-send="true" data-show-faces="true"></div>

</div>
	
</body>
</html>