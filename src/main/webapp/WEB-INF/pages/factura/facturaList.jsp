<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML>
<html>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageId" value="6"/>
</jsp:include>

<body>
<script type="text/javascript">
//<![CDATA[
	$(function() {
		$("#fechaInicio").datepicker({dateFormat: 'dd/mm/yy'});
		$("#fechaFin").datepicker({dateFormat: 'dd/mm/yy'});
	});
	
	function pagar(){
		var factIds = '';
		$("input[name='facturaIds']").each(function(index){
			factIds = factIds + 'facturaIds=' + $(this).val() + '&';
		});
		$.ajax({
			type: "POST",
	        url: "facturapaymentajax.do",
	        data: factIds,
	        success: function(datos){
	            alert(datos);
	        }
		});
	}
//]]>
</script> 
<div id="contenedor">

<jsp:include page="/common/menu.jsp"/> 

<div class="titulo"><fmt:message key="facturaList.title"/></div>

<div class="searchForm fila">
<form action="<c:url value="/facturaList.do"/>">
	<fmt:message key="factura.formulario.busqueda"/>
	<ul>
		<li>
			<label for="customerId"><fmt:message key="factura.selectCustomer"/></label>
			<select name="customerId">
				<option value="">TODOS</option>
				<c:forEach var="customer" items="${allCustomers}">
					<option value="${customer.id}">${customer.nombre}</option>
				</c:forEach>
			</select>
		</li>
		<li>
			<label for="fechaInicio"><fmt:message key="fechaInicio"/></label>
			<input type="text" id="fechaInicio" name="fechaInicio" maxlength="10" class="w75"/>
		</li>
		<li>
			<label for="fechaFin"><fmt:message key="fechaFin"/></label>
			<input type="text" id="fechaFin" name="fechaFin" maxlength="10" class="w75"/>
		</li>
		<li>
			<label for="pendientes"><fmt:message key="factura.formulario.solopendientes"/></label>
			<input type="checkbox" value="pendientes" name="pendientes" id="pendientes"
			 <c:if test="${(param.pendientes eq 'pendientes')}">
			 	checked="checked"
			 </c:if>
			/>
		</li>
		<li>
			<input type="submit" value="BUSCAR"/>
		</li>
	</ul>
</form>
</div>

<div class="subtitulo fila">
	<a href="<c:url value="/facturaForm.do"/>"><fmt:message key="factura.add"/></a> 
</div>

<c:if test="${selectedCustomer ne null}">
	<p class="fila"><fmt:message key="facturas.pendientes"/> <b><c:out value="${selectedCustomer.nombre}"/></b></p>
</c:if>
<div class="fila">
<table class="sortable w600">
<thead>
  <tr>
  	<th width="100px"><fmt:message key="customer"/></th>
  	<th width="50px"><fmt:message key="factura.numero"/></th>
  	<th width="50px">Total</th>
  	<th width="50px">Data</th>
  	<th width="20px"><fmt:message key="factura.pagado"/></th>
  	<th width="300px">Lineas</th>
  </tr>
</thead>
<tbody>  
  <c:forEach items="${facturaList}" var="factura">
 	<tr>
 		<td style="font-weight: bold">
		 	<a href="<c:url value="/facturaForm.do?id=${factura.factura.id.id}"/>" title="<fmt:message key="factura.edit"/>">
		 		<c:out value="${factura.customer.nombre}"/>
		 	</a>
	 	</td>
	 	<td><c:out value="${factura.factura.numero}"/></td>
	 	<td><fmt:formatNumber value="${factura.factura.total}" maxFractionDigits="2"/></td>
	 	<td><fmt:formatDate value="${factura.factura.fecha}" pattern="dd/MM/yyyy" /></td>
	 	<td><INPUT NAME="facturaIds" TYPE="hidden" VALUE="${factura.factura.id.id}"/><fmt:message key="${factura.factura.pagado}"/></td>
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
	 		<c:forEach items="${factura.factura.lineasFactura}" var="linea">
	 			<tr>
	 				<td><c:out value="${productMap[linea.productId].name}"/></td>
	 				<td><fmt:formatNumber value="${linea.cantidad}" maxFractionDigits="2"/></td>
	 				<td><fmt:formatNumber value="${linea.total}" maxFractionDigits="2"/></td>
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

<c:if test="${selectedCustomer ne null}">
<div class="fila">
	<ul>
		<li>
			<p><fmt:message key="facturas.sumaTotal"/> <b style="color: red"><fmt:formatNumber value="${totalPendiente}" maxFractionDigits="2"/></b>&nbsp;€</p>
		</li>
		<li>
			<button onclick="pagar()"><fmt:message key="factura.realizarPago"/></button>
		</li>
	</ul>
</div>
</c:if>

</div>

</body>
</html>