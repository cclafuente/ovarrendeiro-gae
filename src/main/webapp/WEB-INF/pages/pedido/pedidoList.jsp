<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML>
<html>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageId" value="7"/>
</jsp:include>

<body>
<script type="text/javascript">
//<![CDATA[
	$(function() {
		$("#fechaInicio").datepicker({dateFormat: 'dd/mm/yy'});
		$("#fechaFin").datepicker({dateFormat: 'dd/mm/yy'});
	});
	
//]]>
</script> 
<div id="contenedor">

<jsp:include page="/common/menu.jsp"/> 

<div class="titulo"><fmt:message key="pedidoList.title"/></div>

<div class="searchForm fila">
<form action="<c:url value="/pedidoList.do"/>">
	<fmt:message key="pedidoList.formbusqueda"/>
	<ul>
		<li>
			<label for="fechaInicio"><fmt:message key="fechaInicio"/></label>
			<input type="text" id="fechaInicio" name="fechaInicio" maxlength="10" class="w75"/>
		</li>
		<li>
			<label for="fechaFin"><fmt:message key="fechaFin"/></label>
			<input type="text" id="fechaFin" name="fechaFin" maxlength="10" class="w75"/>
		</li>
		<li>
			<input type="submit" value="BUSCAR" class="button"/>
		</li>
	</ul>
</form>
</div>

<div class="subtitulo fila">
	<!--  <a href="<c:url value="/pedidoForm.do"/>">-->
	<ul>
		<li><a href="<c:url value="/pedidoFormCustomer.do"/>"> <fmt:message key="pedido.add"/></a></li>
		<li><a href="<c:url value="/pedidoProductList.do"/>"> <fmt:message key="pedido.list.byProduct"/></a></li>
	</ul> 
</div>

<div class="fila">
<table class="sortable w600" id="tablaPedidos">
<thead>
  <tr>
  	<th width="100px"><fmt:message key="customer"/></th>
  	<th width="50px">Total</th>
  	<th width="50px">Data</th>
  	<th width="450px">Lineas</th>
  </tr>
</thead>
<tbody>  
  <c:forEach items="${pedidoList}" var="pedido">
 	<tr>
 		<td style="font-weight: bold">
	 	<a href="<c:url value="/pedidoForm.do?id=${pedido.id.id}"/>" title="Ver detalle Pedido">
	 		<c:choose>
	 			<c:when test="${pedido.email ne null}">
	 				<c:out value="${pedido.email}"/>
	 			</c:when>
	 			<c:otherwise>
	 				<c:out value="${customerMap[pedido.customerId].nombre}"/>
	 			</c:otherwise>
	 		</c:choose>
	 	</a>
	 	</td>
	 	<td><c:out value="${pedido.total}" escapeXml="false"/></td>
	 	<td><fmt:formatDate value="${pedido.fechaEntrega}" pattern="dd/MM/yyyy" /></td>
	 	<td>
	 		<table>
	 		<thead>
	 			<tr>
	 				<th width="100px">Producto</th>
	 				<th width="92px">Cantidade</th>
	 				<th width="92px">Subtotal</th>
	 			</tr>
	 		</thead>
	 		<tbody>	
	 		<c:forEach items="${pedido.lineasPedido}" var="linea">
	 			<tr>
	 				<td><c:out value="${productMap[linea.productId].name}"/></td>
	 				<td><c:out value="${linea.cantidad}"/></td>
	 				<td><c:out value="${linea.total}"/></td>
	 			</tr>
	 		</c:forEach>
	 		</tbody>
	 		</table>
	 	</td>
	</tr>
 </c:forEach>
 </tbody>
</table>
</div>

<div class="fila">
<button onclick="window.print()" class="button print">
<fmt:message key="imprimir"/>
</button>
</div>

</div>

</body>
</html>