<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML>
<html>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageId" value="4"/>
</jsp:include>

<body>
<div id="contenedor">

<jsp:include page="/common/menu.jsp"/> 

<div class="titulo"><fmt:message key="noticeList.title"/></div>

<div class="subtitulo fila">
	<a href="<c:url value="/noticeForm.do"/>"><fmt:message key="notice.add"/></a> 
</div>

<table class="sortable w600">
<thead>
  <tr>
  	<th width="100px">Titulo</th>
  	<th width="400px">Corpo</th>
  	<th class="50px">Data</th>
  </tr>
</thead>
<tbody>  
  <c:forEach items="${noticeList}" var="notice">
 	<tr>
 		<td style="font-weight: bold">
	 	<a href="<c:url value="/noticeForm.do?id=${notice.id}"/>" title="Editar Factura">
	 		<c:out value="${notice.title}"/>
	 	</a>
	 	</td>
	 	<td><c:out value="${notice.body}" escapeXml="false"/></td>
	 	<td><fmt:formatDate value="${notice.lastUpdate}" pattern="dd/MM/yyyy" /></td>
	</tr>
 </c:forEach>
 </tbody>
</table>

</div>

</body>
</html>