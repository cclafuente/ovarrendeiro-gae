<%@ page contentType="text/html;charset=iso-8859-1" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageId" value="1"/>
</jsp:include>

<body onload="initialize()" onunload="GUnload()">

<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/es_ES/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>
<script type="text/javascript">
//<![CDATA[
 function initialize() {
    var latlng = new google.maps.LatLng(43.214129, -8.695872);
    var myOptions = {
      zoom: 13,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.HYBRID
    };
    var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
    
    var contentString = '<a href="https://maps.google.es/maps?q=Panadería O Varrendeiro, Avenida das Flores, 11, 15100 Carballo, Galicia" target="_blank"><ul><li><b>'
    		contentString = contentString + 'Panaderia O Varrendeiro</b></li><li>Avda. das Flores, nº 11</li><li>981-70-16-91</li></ul></a>';
    
   	var infowindow = new google.maps.InfoWindow({
    	content: contentString
	});

	var marker = new google.maps.Marker({
    	position: latlng,
    	map: map,
    	title: "<fmt:message key="application.title"/>"
	});

	google.maps.event.addListener(marker, 'click', function() {
  		infowindow.open(map,marker);
	});
}
//]]>
</script>

<div id="contenedor">
<!-- empeza a cabeceira -->
<jsp:include page="/common/menu.jsp"/>

<!--fin cabeceira -->
<div class="titulo"><fmt:message key="menu.inicio"/></div>

<div class="wrapperPantallaInicial">
	
	<div class="box1 descripcionEmpresa">
		<ul>
			<li>
			<fmt:message key="description.1"/>
			</li> 
			<li>
			<fmt:message key="description.2"/>	
			</li>
        	<li>
        	<fmt:message key="description.3"/>
			</li>
			<li>
        	<fmt:message key="description.4"/>
			</li>
			<li>
        	<fmt:message key="description.5"/>
			</li>
			<li class="description especiais">
        	Descontos especiais para bares e restaurantes.
        	</li>
        </ul>
        <div class="box1Direccion">
		<div class="subtitulo subtituloDireccion">DIRECCIÓN</div>
			<ul>
				<li>Localidade: <b>Carballo (A Coruña)</b></li>	
				<li>Rúa: <b>Avda das Flores, nº 11</b></li>
				<li>Teléfono: <b><a href="tel: 981701691">981701691</a></b></li>
				<li><strong>Correo&nbsp;electrónico:&nbsp;</strong><a href="mailto:info@panaderiaovarrendeiro.es">info@panaderiaovarrendeiro.es</a></li>
			</ul>
		</div>
	</div>
	
	<!-- fin da parte de descripcion -->
	<c:forEach items="${noticeList}" var="notice">
		<div class="box1 box1Noticias">
		<ul>
			<li><b><c:out value="${notice.title}"/></b></li>
			<li><fmt:formatDate value="${notice.lastUpdate}" pattern="dd/MM/yyyy"/></li>
			<li><c:out value="${notice.body}" escapeXml="false"/></li>
		</ul>
		</div>
	</c:forEach>
	
	<div class="imagenes_y_mapa">
	
	<!-- identificador para el div que contendra el mapa -->
	<div id="map_canvas" class="map_canvas_inicio box1">
	</div>
	
	<div class="box1 fotos_inicio">
		<div class="foto_inicio">
			<img class="imgInicio" src="<c:url value='/img/carlos_atendendo.jpg'/>" alt="carlos atendendo" title="carlos atendendo">
		</div>
		<div class="foto_inicio">
			<img class="imgInicio" src="<c:url value='/img/despacho2.jpg'/>" alt="despacho pan" title="despacho pan">
		</div>
		<div class="foto_inicio">
			<img class="imgInicio" src="<c:url value='/img/isabel_diante.jpg'/>" alt="isabel diante" title="isabel diante">
		</div>
		<div class="foto_inicio">
			<img class="imgInicio" src="<c:url value='/img/carlos_sacando_pan.jpg'/>" alt="carlos sacando pan" title="carlos sacando pan">
		</div>
	</div>
	
	</div>
		
		
			
</div>
	
<div class="fb-like" data-href="http://www.panaderiaovarrendeiro.es" data-send="true" data-show-faces="true"></div>

</div>
</body>
</html>