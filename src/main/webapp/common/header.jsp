<%@ page contentType="text/html;charset=iso-8859-1" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<head>
    <link rel="Shortcut Icon" href="favicon.JPG"/>
    <title><fmt:message key="application.title"/> - <fmt:message key="header.title.page${param.pageId}"/></title>
    <fmt:message key="application.fechaGeneracion" var="fechaGeneracion"/>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta name="DESCRIPTION" content="<fmt:message key="header.description.page${param.pageId}"/>"/>
	<meta name="Keywords" content="<fmt:message key="header.keywords.page${param.pageId}"/>"/>
	<meta name="AUTHOR" content="cclafuente@gmail.com"/>
	<meta name="locality" content="Carballo, A Coruña, Galicia, España, U.E."/>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1"/>
	<meta http-equiv="CONTENT-LANGUAGE" content="Español"/>
	<meta http-equiv="VW96.OBJECT TYPE" content="Catalogo"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="RATING" content="General"/>
	<meta name="ROBOTS" content="index,follow"/>
	<meta name="REVISIT-AFTER" content="7 days"/>
	<meta name="og:image" content="http://www.panaderiaovarrendeiro.es/img/despacho1.jpg"/>
	<meta name="google-site-verification" content="YOSezbIyQpHccmV2CoVU8vcTZLFTYCGHLGGU3nOKfgo" />
	<meta property="fb:admins" content="1235239264" />
	<meta property="og:title" content="Panadería O Varrendeiro" />
	<meta property="og:image" content="http://www.panaderiaovarrendeiro.es/img/despacho1.jpg" />
	<script type="text/javascript" src="<c:url value='/scripts/lytebox.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/scripts/sorttable.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/scripts/scriptaculous.js?load=effects,builder'/>"></script>
	<script type='text/javascript' src="<c:url value='/dwr/engine.js'/>"></script>
  	<script type='text/javascript' src="<c:url value='/dwr/util.js'/>"></script>
  	<script type='text/javascript' src="<c:url value='/dwr/interface/ProductManager.js'/>"></script>
	<!--  <script type='text/javascript' src="<c:url value='/dwr/interface/FacturaUtil.js'/>"></script>-->
  	<script type="text/javascript" src="<c:url value='/scripts/jquery-1.6.2.min.js'/>"></script>
  	<script type="text/javascript" src="<c:url value='/scripts/jquery-ui-1.8.15.custom.min.js'/>"></script>
  	<script type="text/javascript" src="<c:url value='/scripts/utilidades.js?v=${fechaGeneracion}'/>"></script>
	<link type="text/css" rel="stylesheet" href="<c:url value='/styles/estilos.css?v=${fechaGeneracion}'/>" media="screen" charset="utf-8" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/styles/estilosprint.css?v=${fechaGeneracion}'/>" media="print" charset="utf-8" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/styles/lytebox.css'/>" media="screen" charset="utf-8" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/styles/thickbox.css'/>" media="screen" charset="utf-8" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/styles/jquery-gallery.css'/>" media="screen" charset="utf-8" />	
	<link type="text/css" rel="stylesheet" href="<c:url value='/styles/jquery-ui-1.8.15.custom.css'/>" media="screen" charset="utf-8" />
	<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyB_aFXFOFy5PdXgKKMBq93GEhVMThejGuc" type="text/javascript"></script>
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.css" />
	<script src="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.js"></script>
	
	
	
<script type="text/javascript">
//<![CDATA[
  	var _gaq = _gaq || [];
  	_gaq.push(['_setAccount', 'UA-17110210-1']);
  	_gaq.push(['_trackPageview']);

  	(function() {
    	var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    	ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  	})();
//]]>
</script>
<!-- fechaGeneracion:  <fmt:message key="application.fechaGeneracion"/> -->
</head>
