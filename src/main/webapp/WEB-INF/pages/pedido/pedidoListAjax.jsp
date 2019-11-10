<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<table class="sortable" width="600px" id="tablaPedidos">
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
	 	<a href="<c:url value="/pedidoForm.do?id=${pedido.id.id}"/>" title="Editar Pedido">
	 		<c:out value="${pedido.email}"/>
	 	</a>
	 	</td>
	 	<td><c:out value="${pedido.total}" escapeXml="false"/></td>
	 	<td><fmt:formatDate value="${pedido.fechaEntrega}" pattern="dd/MM/yyyy" /></td>
	 	<td>
	 		<table>
	 		<thead>
	 			<tr>
	 				<th>Producto</th>
	 				<th>Cantidade</th>
	 				<th>Subtotal</th>
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
