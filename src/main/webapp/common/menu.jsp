<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div id="cabecera">
	
	<div id="tituloCabecera">
		<h2><a href="<c:url value="/index.do"/>">Panadería O Varrendeiro</a></h2>
	</div>
	
	<div id="menu">
	 		<div class="${sessionInfo.activeMenu == '1'? 'menuActivo' : 'menuInactivo'}" id="menu1">
	 			<h3><a href="<c:url value="/index.do"/>">Inicio</a></h3>
	 		</div>
	 		<div class="${sessionInfo.activeMenu == '2'? 'menuActivo' : 'menuInactivo'}" id="menu2">
	 			<h3><a href="<c:url value="/productList.do"/>"><fmt:message key="productList.title"/></a></h3>
	 		</div>
	 		<div>
	 			<a href="http://mail.google.com/a/panaderiaovarrendeiro.es/">Mail Corp.</a>
	 		</div>
	 		<div class="${sessionInfo.admin ? 'menuActivo' : 'menuInactivo'}" id="menu6">
	 		<a href="<c:url value="/adminmain.do"/>">Adm.</a>
	 		</div>
	</div>

</div>