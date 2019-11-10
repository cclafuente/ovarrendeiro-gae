<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageId" value="7"/>
</jsp:include>

<body>
<script type="text/javascript">
//<![CDATA[
	function calcularLinea(numero){
		var cantidad = dwr.util.getValue('cant_' + numero);
		var idProducto = dwr.util.getValue('prod_' + numero);
		var precio = dwr.util.getValue('precio_' + numero);
		var iva = dwr.util.getValue('iva_' + numero);
		dwr.util.setValue('subtotal_' + numero, ((precio * cantidad) + ((precio * cantidad * iva)/100)).toFixed(2));
		var totalAgrupado = 0;
		var n = $("tbody tr").length;
		dwr.util.setValue('numLineas', n);
		for(var i = 1; i <= n; i++){
			if (dwr.util.getValue('cant_' + i) != ""){
				totalAgrupado += parseFloat(dwr.util.getValue('subtotal_' + i));
			}
		}
		/*dwr.util.setValue('total', totalAgrupado.toFixed(2));*/
		dwr.util.setValue('total', totalAgrupado.toFixed(2));
		dwr.util.setValue('totalLectura', totalAgrupado.toFixed(2));
	}
	
	$(function() {
		$("#fechaEntrega").datepicker({dateFormat: 'dd/mm/yy'});
	});
	
	$(document).ready(function(){
		var totalAgrupado = 0;
		var n = $("tbody tr").length;
		dwr.util.setValue('numLineas', n);
		for(var i = 1; i <= n; i++){
			if (dwr.util.getValue('cant_' + i) != ""){
				totalAgrupado += parseFloat(dwr.util.getValue('subtotal_' + i));
			}
		}
		dwr.util.setValue('total', totalAgrupado.toFixed(2));
		dwr.util.setValue('totalLectura', totalAgrupado.toFixed(2));
	});
//]]>
</script>  
<div id="contenedor">
<jsp:include page="/common/menu.jsp"/>
    
<div class="titulo"><fmt:message key="pedidoForm.title"/></div>

<div class="mensaje">
	<c:if test="${param.added}">
		<fmt:message key="pedido.customer.addedOK"/>
	</c:if>
	<c:if test="${param.edited}">
		<fmt:message key="pedido.editedOK"/>
	</c:if>
	<c:if test="${param.removed}">
		<fmt:message key="pedido.removedOK"/>
	</c:if>
</div>
   
<form:form commandName="pedido" method="post" id="pedidoForm" action="pedidoFormCustomer.do">

<div style="float: left; width: 440px; border: 1px solid white;">

<input type="hidden" name="numLineas"/>

<table class="w300">
	<thead>
		<tr>
			<th width="50px">Nombre</th>
			<th width="50px">Precio</th>
			<th width="50px">Cantidade</th>
			<th width="50px">Subtotal</th>
			<th width="55px">Imaxe</th>
		</tr>
	</thead>
	<tbody>  
	<c:if test="${pedido.id eq null}">
	<c:forEach items="${productList}" var="pBean" varStatus="status">
 		<tr>
 			<td style="font-weight: bold"><c:out value="${pBean.product.name}"/></td>
	 		<td style="font-weight: bold"><fmt:formatNumber maxFractionDigits="2" value="${pBean.product.price + (pBean.product.price * pBean.product.iva / 100)}"/>
		 			<input type="hidden" id="precio_${status.count}" name="precio_${status.count}" value="${pBean.product.price}"/>
		 			<input type="hidden" id="num_${status.count}" name="num_${status.count}"/>
		 			<input type="hidden" id="prod_${status.count}" name="prod_${status.count}" value="${pBean.product.id}"/>
		 			<input type="hidden" id="iva_${status.count}" name="iva_${status.count}" value="${pBean.product.iva}"/>
		 	</td>
		 	<td><input type="number" style="height: 30px; font-size: larger;" size="5" name="cant_${status.count}" id="cant_${status.count}" onchange="calcularLinea('${status.count}')"/></td> 
		 	<td><input type="text" style="height: 30px; font-size: larger;" size="7" name="subtotal_${status.count}" id="subtotal_${status.count}" readonly="readonly"/></td>
		 	<td>
				<c:forEach items="${pBean.imageList}" var="pImage" varStatus="status">
		 		<c:if test="${status.first}">
		 			<img src="<c:url value='/image.do?imageId=${pImage.id}'/>" width="55px" height="55px" 
		 				style="float: right;" alt="${pBean.product.name}"/>
		 		</c:if>
		 		</c:forEach>
		 	</td>
		</tr>
	</c:forEach>
	</c:if>
	<c:if test="${pedido.id ne null}">
	<input type="hidden" id="longSavedKey" name="longSavedKey" value="${pedido.id.id}"/>
	<c:forEach items="${productList}" var="pBean" varStatus="status">
 		<tr>
 			<td style="font-weight: bold"><c:out value="${pBean.product.name}"/></td>
	 		<td style="font-weight: bold"><fmt:formatNumber maxFractionDigits="2" value="${pBean.product.price + (pBean.product.price * pBean.product.iva / 100)}"/>
		 			<input type="hidden" id="precio_${status.count}" name="precio_${status.count}" value="${pBean.product.price}"/>
		 			<input type="hidden" id="num_${status.count}" name="num_${status.count}"/>
		 			<input type="hidden" id="prod_${status.count}" name="prod_${status.count}" value="${pBean.product.id}"/>
		 			<input type="hidden" id="iva_${status.count}" name="iva_${status.count}" value="${pBean.product.iva}"/>
		 	</td>
		 	<c:set var="contieneProducto" value="${false}"/>
		 	<c:forEach items="${pedido.lineasPedido}" var="lineaPedido">
		 		<c:if test="${lineaPedido.productId eq pBean.product.id}">
		 		<c:set var="contieneProducto" value="${true}"/>
		 			<td>
		 			<input type="text" style="height: 30px; font-size: larger;" size="5" name="cant_${status.count}" id="cant_${status.count}" onchange="calcularLinea('${status.count}')" value="${lineaPedido.cantidad}"/></td>
		 			</td>
		 			<td>
		 			<input type="text" style="height: 30px; font-size: larger;" size="7" name="subtotal_${status.count}" id="subtotal_${status.count}" value="${lineaPedido.total}" readonly="readonly"/>
		 			</td>
		 		</c:if>
		 	</c:forEach>
		 	<c:if test="${contieneProducto eq false}">
		 		<td>
		 			<input type="text" style="height: 30px; font-size: larger;" size="5" name="cant_${status.count}" id="cant_${status.count}" onchange="calcularLinea('${status.count}')"/></td>
		 		</td>
		 		<td>
		 			<input type="text" style="height: 30px; font-size: larger;" size="7" name="subtotal_${status.count}" id="subtotal_${status.count}" readonly="readonly"/>
		 		</td>
		 	</c:if>
		 	<td>
				<c:forEach items="${pBean.imageList}" var="pImage" varStatus="status">
		 		<c:if test="${status.first}">
		 			<img src="<c:url value='/image.do?imageId=${pImage.id}'/>" width="55px" height="55px" 
		 				style="float: right;"/>
		 		</c:if>
		 		</c:forEach>
		 	</td>
		</tr>
	</c:forEach>
	</c:if>
	
	
 	</tbody>
</table>

</div>


<div style="float: left; width: 250px; background-color: #E6E6E6">
<ul>
	<li>
		<label for="total">Total</label>
		<span id="totalLectura"></span>
		<form:hidden path="total"/>
	</li>
	<li>
		<label for="fechaEntrega"><fmt:message key="pedido.fechaEntrega"/></label>
		<form:input path="fechaEntrega" size="10"/>
	</li>
	<li>
		<label for="customerId"><fmt:message key="customer"/></label>
		<form:select path="customerId" itemLabel="nombre" itemValue="id" items="${customerList}"/>
	</li>
	<li>
		<input type="submit" value="Realizar pedido"/>
		<c:if test="${pedido.id ne null}">
		<input type="submit" class="button" name="remove" value="Eliminar" title="Eliminar"/>
		</c:if>
	</li>
</ul>	
</div>
		
</form:form>
	
<div id="bottomPedidoCustomerForm"/>
	<ul>
		<li><a href="<c:url value="/pedidoList.do"/>"><fmt:message key="pedidoList.title"/></a></li>
		<li><a href="<c:url value="/pedidoFormCustomer.do"/>"><fmt:message key="pedido.add"/></a></li>
	</ul>
</div>

</div>
	
</body>

</html>
