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

<div class="titulo"><fmt:message key="pedido.list.byProduct"/></div>

<div class="searchForm fila">
<form action="<c:url value="/pedidoProductList.do"/>">
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
	<!--  <a href="<c:url value="/pedidoForm.do"/>">--><a class="button" href="<c:url value="/pedidoFormCustomer.do"/>"> <fmt:message key="pedido.add"/></a> 
</div>

<table class="sortable fila" id="tablaPedidosPorProducto">
<thead>
  <tr>
  	<th width="100px"><fmt:message key="customer"/></th>
  	<th width="70px">Data</th>
  	<c:forEach items="${productList}" var="product">
  		<th class="thProducto"><c:out value="${product.product.name}"/></th>
  	</c:forEach> 
  </tr>
</thead>
<tbody>  
  <c:forEach items="${pedidoList}" var="pedido">
 	<tr>
 		<td style="font-weight: bold">
	 		<c:choose>
	 			<c:when test="${pedido.email ne null}">
	 				<c:out value="${pedido.email}"/>
	 			</c:when>
	 			<c:otherwise>
	 				<c:out value="${customerMap[pedido.customerId].nombre}"/>
	 			</c:otherwise>
	 		</c:choose>
	 	</td>
	 	<td><fmt:formatDate value="${pedido.fechaEntrega}" pattern="EEE, dd/MM/yyyy"/></td>
	 	<c:forEach items="${productList}" var="product">
	 		<td>
	 			<c:set var="mapaActual" value="${totalesPorPedido[pedido.id.id]}"/>
	 			<c:out value="${mapaActual[product.product.id]}"/>
	 		</td>
	 	</c:forEach>
	 </tr>
 </c:forEach>
 </tbody>
 <tfoot>
 	<td class="total"><fmt:message key="pedido.totalProducto"/></td>
 	<td>&nbsp;</td>
 	<c:forEach items="${productList}" var="product">
	 		<td class="total">
	 			<c:out value="${totalesProducto[product.product.id]}"/>
	 		</td>
	 	</c:forEach>
  </tr>
 </tfoot>
</table>

<div class="fila">
<button onclick="window.print()" class="button print">
<fmt:message key="imprimir"/>
</button>
</div>

</div>
</body>
</html>