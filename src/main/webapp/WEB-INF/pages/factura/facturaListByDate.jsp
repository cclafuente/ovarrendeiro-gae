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
	
	function guardarFactura(customerId, fecha){
		$.ajax({
			type: "POST",
	        url: "facturaFormAjax.do",
	        data: 'fecha=' + $('#fecha_' + customerId + '_' + fecha).val() + '&customerId=' + customerId + '&total=' + $('#customer_' + customerId + '_' + fecha + '_total').val(),
	        success: function(datos){
	            $('#customer_' + customerId + '_' + fecha + '_total').css('color', 'blue');
	            $('#tdcustomer_' + customerId + '_' + fecha).css('color', 'blue');
	            $('#button_' + customerId + '_' + fecha).css('visibility','hidden');
	        }
		});
	}
//]]>
</script> 
<div id="contenedor">

<jsp:include page="/common/menu.jsp"/> 

<div class="titulo"><fmt:message key="facturaList.title"/></div>

<div class="searchForm fila">
<form action="<c:url value="/facturaListByDate.do"/>">
	<fmt:message key="factura.formulario.busqueda"/>
	<ul>
		<li>
			<label for="fechaInicio"><fmt:message key="fechaFacturacion"/></label>
			<input type="text" id="fechaInicio" name="fechaInicio" maxlength="10" class="w75"/>
		</li>
		<li>
			<input type="submit" value="CREAR FACTURACION DIA"/>
		</li>
	</ul>
</form>
</div>

<div class="fila">
<table class="sortable w400">
<thead>
  <tr>
  	<th width="100px"><fmt:message key="customer"/></th>
  	<th width="20px">Total</th>
  	<th width="50px">Data</th>
  	<th width="20px"><fmt:message key="guardar"/></th>
  </tr>
</thead>
<tbody>  
  <c:forEach items="${allCustomers}" var="customer">
  	<fmt:formatDate var="currentDateString" value="${currentDate}" pattern="ddMMyyyy" />
  	<c:set var="keyclientedia" value="${customer.id}_${currentDateString}"/>
  	<c:set var="facturaClienteActual" value="${mapaClientesFechaFacturas[keyclientedia]}"/>
 	<tr id="trcustomer_${customer.id}">
 		<td style="font-weight: bold">
			<c:out value="${customer.nombre}"/>
		</td>
	 	<td>
	 		<c:if test="${facturaClienteActual eq null}">
	 		<input type="number" id="customer_${customer.id}_${currentDateString}_total"/>
	 		</c:if>
	 		<c:if test="${facturaClienteActual ne null}">
	 		<input type="number" id="customer_${customer.id}_${currentDateString}_total" value="${facturaClienteActual.total}" style="color: blue"/>
	 		</c:if>
	 	</td>
	 	<td id="tdcustomer_${customer.id}_${currentDateString}">
	 		<input type="hidden" id="fecha_${customer.id}_${currentDateString}" value="<fmt:formatDate value="${currentDate}" pattern="dd/MM/yyyy" />"/></input>
	 		<fmt:formatDate value="${currentDate}" pattern="EEE, dd/MM/yyyy" />
	 	</td>
	 	<td>
	 		<c:if test="${facturaClienteActual eq null}">
	 		<button id="button_${customer.id}_${currentDateString}" onclick="guardarFactura(${customer.id}, ${currentDateString})"><fmt:message key="guardar"/></button>
	 		</c:if>
		</td>
	 </tr>
 </c:forEach>
 </tbody>
</table>
</div>

</div>

</body>
</html>