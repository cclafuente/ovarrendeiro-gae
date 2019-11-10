<%@ page contentType="text/html;charset=iso-8859-1" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageId" value="1"/>
</jsp:include>



<body>

<div id="contenedor">
<!-- empeza a cabeceira -->
<jsp:include page="/common/menu.jsp"/>

<!--fin cabeceira -->
<div class="titulo"><fmt:message key="menu.admininicio"/></div>

			<ul>
						<li class="${sessionInfo.activeSubMenu == '4'? 'menuActivo' : ''}">
				 			<a href="<c:url value="/facturaList.do?pendientes=pendientes"/>" data-role="button" data-icon="star" data-theme="a">
				 				<fmt:message key="submenu.4"/>
				 			</a>
				 		</li>
				 		<li class="${sessionInfo.activeSubMenu == '4'? 'menuActivo' : ''}">
				 			<a href="<c:url value="/facturaListByDate.do"/>" data-role="button" data-icon="star" data-theme="a">
				 				<fmt:message key="facturacion.dia"/>
				 			</a>
				 		</li>
				 		<li class="${sessionInfo.activeSubMenu == '4'? 'menuActivo' : ''}">
				 			<a href="<c:url value="/facturaListByPeriod.do"/>" data-role="button" data-icon="star" data-theme="a">
				 				<fmt:message key="facturacion.periodo"/>
				 			</a>
				 		</li>
				 		<li class="${sessionInfo.activeSubMenu == '7'? 'menuActivo' : ''}">
				 			<a href="<c:url value="/pedidoList.do"/>" data-role="button" data-icon="star" data-theme="a">
				 				<fmt:message key="submenu.7"/>
				 			</a>
				 		</li>
				 		<li class="${sessionInfo.activeSubMenu == '1'? 'menuActivo' : ''}">
				 			<a href="<c:url value="/productAdminList.do"/>" data-role="button" data-icon="star" data-theme="a">
				 				<fmt:message key="submenu.1"/>
				 			</a>
				 		</li>
						<li class="${sessionInfo.activeSubMenu == '2'? 'menuActivo' : ''}">
				 			<a href="<c:url value="/noticeList.do"/>" data-role="button" data-icon="star" data-theme="a">
				 				<fmt:message key="submenu.2"/>
				 			</a>
				 		</li>
				 		<li class="${sessionInfo.activeSubMenu == '3'? 'menuActivo' : ''}">
				 			<a href="<c:url value="/customerList.do"/>" data-role="button" data-icon="star" data-theme="a">
				 				<fmt:message key="submenu.3"/>
				 			</a>
				 		</li>
				 		<li class="${sessionInfo.activeSubMenu == '8'? 'menuActivo' : ''}">
				 			<a href="<c:url value="/scripts/myCart.html"/>" data-role="button" data-icon="star" data-theme="a">
				 				<fmt:message key="submenu.8"/>
				 			</a>
				 		</li>
		</ul>	
</div>
</body>
</html>
