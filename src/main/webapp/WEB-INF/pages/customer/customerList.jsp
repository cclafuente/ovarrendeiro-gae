<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML>
<html>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageId" value="5"/>
</jsp:include>

<body>
<div id="contenedor">

<jsp:include page="/common/menu.jsp"/> 

<div class="titulo"><fmt:message key="customerList.title"/></div>

<div class="subtitulo fila">
	<a href="<c:url value="/customerForm.do"/>"><fmt:message key="customer.add"/></a> 
</div>

<div class="fila">
<table class="sortable w600">
<thead>
  <tr>
  	<th width="100px"><fmt:message key="customer.nombre"/></th>
  	<th width="200px"><fmt:message key="customer.identificador"/></th>
  	<th width="200px"><fmt:message key="customer.email"/></th>
  	<th width="60px"><fmt:message key="customer.pagos.pendientes"/></th>
  	<th width="60px"><fmt:message key="customer.pedidos"/></th>
  </tr>
</thead>
<tbody>  
  <c:forEach items="${customerList}" var="customer">
	 	<tr>
	 		<td style="font-weight: bold">
		 	<a href="<c:url value="/customerForm.do?id=${customer.id}"/>" title="Editar Datos Cliente">
		 		<c:out value="${customer.nombre}"/>
		 	</a>
		 	</td>
		 	<td><c:out value="${customer.identificador}"/></td>
		 	<td><c:out value="${customer.email}"/></td>
		 	<td><a title="<fmt:message key="customer.pagos.pendientes"/>" href="<c:url value="/facturaList.do?customerId=${customer.id}&pendientes=pendientes"/>"><fmt:message key="customer.pagos.pendientes"/></a></td>
			<td><a title="<fmt:message key="customer.pedidos"/>" href="<c:url value="/pedidoList.do?customerId=${customer.id}"/>"><fmt:message key="customer.pedidos"/></a></td>	
		</tr>
 	</c:forEach>
 </tbody>
</table>
</div>

</div>

</body>
</html>