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
	            $('#customer_' + customerId + '_' + fecha + '_total').addClass('guardado');
	        }
		});
	}
	
	function guardarFacturas(customerId){
		$('input[name^="customer_' + customerId + '"]').each(function(index) {
    		if (!$(this).hasClass('guardado')){
    			if ($(this).val().length > 0){
    				var fecha = $(this).attr('id');
    				var n = fecha.lastIndexOf("_");
    				var p = fecha.lastIndexOf("_") - 8;
    				fecha = fecha.substring(p, n);
    				guardarFactura(customerId, fecha);
    			}
    		}
		});
	}
	
	function totalCliente(customerId){
		var total = 0;
		$('input[name^="customer_' + customerId + '"]').each(function(index) {
    		if ($(this).val().length > 0){
    			total = total + parseFloat($(this).val());
    		}
		});
		$('#customer_total_input_' + customerId).val(total);
	}
	
	function calcularTotalClientes(){
		$('tr[id^="trcustomer_"]').each(function(index) {
  			var customerId = $(this).attr('id');
  			customerId = customerId.substring(11);
  			totalCliente(customerId);
  		});
	}
	
	$(document).ready(function() {
  		// Handler for .ready() called.
  		$('tr[id^="trcustomer_"]').each(function(index) {
  			var customerId = $(this).attr('id');
  			customerId = customerId.substring(11);
  			totalCliente(customerId);
  		});
	});
	
//]]>
</script> 
<div id="contenedor">

<jsp:include page="/common/menu.jsp"/> 

<div class="titulo"><fmt:message key="facturaList.title"/></div>

<div class="searchForm fila">
<form action="<c:url value="/facturaListByPeriod.do"/>">
	<fmt:message key="factura.formulario.busqueda"/>
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
			<input type="submit" value="FACTURACION PERIODO"/>
		</li>
	</ul>
</form>
</div>

<div class="fila">
<table class="sortable w400">
<thead>
  <tr>
  	<th width="100px"><fmt:message key="customer"/></th>
  	<c:forEach items="${fechas}" var="fecha">
  		<th width="50px"><fmt:formatDate value="${fecha}" pattern="EEE, dd/MM/yyyy" /></th>
  	</c:forEach>
  	<th width="20px" style="color: red;">Total Cliente</th>
  </tr>
</thead>
<tbody>  
  <c:forEach items="${allCustomers}" var="customer">
  	<tr id="trcustomer_${customer.id}">
	  	<td style="font-weight: bold">
			<c:out value="${customer.nombre}"/>
		</td>
		 	<c:forEach items="${fechas}" var="currentDate">
		  		<fmt:formatDate var="currentDateString" value="${currentDate}" pattern="ddMMyyyy" />
		  		<c:set var="keyclientedia" value="${customer.id}_${currentDateString}"/>
		  		<c:set var="facturaClienteActual" value="${mapaClientesFechaFacturas[keyclientedia]}"/>
		 		<td>
			 		<c:if test="${facturaClienteActual eq null}">
			 		<input type="number" id="customer_${customer.id}_${currentDateString}_total" name="customer_${customer.id}_${currentDateString}_total" onchange="totalCliente(${customer.id})" class="w75"/>
			 		</c:if>
			 		<c:if test="${facturaClienteActual ne null}">
			 		<input type="number" id="customer_${customer.id}_${currentDateString}_total" name="customer_${customer.id}_${currentDateString}_total" value="${facturaClienteActual.total}" onchange="totalCliente(${customer.id})" readonly="readonly" style="color: blue" class="w75 guardado"/>
			 		</c:if>
			 		<input type="hidden" id="fecha_${customer.id}_${currentDateString}" value="<fmt:formatDate value="${currentDate}" pattern="dd/MM/yyyy" />"/></input>
			 	</td>
			</c:forEach>
		<td style="color: red"><input type="text" id="customer_total_input_${customer.id}"/></td>
		<td>
			<button id="button_${customer.id}" onclick="guardarFacturas(${customer.id})"><fmt:message key="guardar"/></button>
		</td>
 	</tr>
 </c:forEach>
 </tbody>
</table>
</div>

</div>

</body>
</html>