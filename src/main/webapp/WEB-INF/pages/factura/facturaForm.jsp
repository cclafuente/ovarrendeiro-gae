<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE HTML>
<html>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageId" value="6"/>
</jsp:include>

<body onload="loadProducts()">
<script type="text/javascript">
//<![CDATA[
	$(function() {
		$("#fecha").datepicker({dateFormat: 'dd/mm/yy'});
	});

	var arrayList = null;
	
	function loadProducts(){
			ProductManager.getAllProducts(1, function(lista){
			arrayList = lista;
			
			if ($("#contenedorLineas2").find('tr').size() == 0){
			  addNuevaLinea();
			}
			
		});
	}

	function generarLineas(){
	FacturaUtil.crearLineas(dwr.util.getValue('year'),dwr.util.getValue('mes'), dwr.util.getValue('tipo'),
	
	function(lineas){
			var lineasContainer = document.getElementById('contenedorLineas2');
			
			while (lineasContainer.hasChildNodes()) {
		        lineasContainer.removeChild(lineasContainer.firstChild);
			}
			
			for (i=0; i<lineas.length; i++){
				var element = document.createElement("tr");
				
				var litextNode = document.createElement("td");
				litextNode.width = "50px";
				var textNode = document.createTextNode(lineas[i].numeroLinea);
				litextNode.appendChild(textNode);
				element.appendChild(litextNode);
				
				var licantidad = document.createElement("td");
				licantidad.width = "50px";
				var cantidad = document.createElement("input");
				cantidad.name = "cant_" + lineas[i].numeroLinea;
				cantidad.type = "text";
				cantidad.id = "cant_" + lineas[i].numeroLinea;
				cantidad.size = 3;
				licantidad.appendChild(cantidad);
				element.appendChild(licantidad);
				 
				var liselectedProd = document.createElement("td");
				liselectedProd.width = "50px";  
				var selectedProd = document.createElement("select");
				selectedProd.name = "prod_" + lineas[i].numeroLinea;
				selectedProd.id = "prod_" + lineas[i].numeroLinea;
				$.each (arrayList, function(key, product){
					var selectOpt = document.createElement("option");
					selectOpt.value = product.id;
					var selectOptText = document.createTextNode(product.name);
					selectOpt.appendChild(selectOptText);
					selectedProd.appendChild(selectOpt);
				});
				liselectedProd.appendChild(selectedProd);
				element.appendChild(liselectedProd);
				
				var liprecioUnidad = document.createElement("td");
				liprecioUnidad.width = "50px";
				var precioUnidad = document.createElement("input");
				precioUnidad.name = "precioUnidad_" + lineas[i].numeroLinea;
				precioUnidad.type = "text";
				precioUnidad.size = 5;
				precioUnidad.id = "precioUnidad_" + lineas[i].numeroLinea;
				precioUnidad.readonly = "readonly";
				precioUnidad.tabIndex = lineas[i].numeroLinea + 500;
				liprecioUnidad.appendChild(precioUnidad);
				element.appendChild(liprecioUnidad);
				
				var liiva = document.createElement("td");
				liiva.width = "50px";				
				var iva = document.createElement("input");
				iva.name = "iva_" + lineas[i].numeroLinea;
				iva.type = "text";
				iva.size = 6;
				iva.id = "iva_" + lineas[i].numeroLinea;
				iva.readonly = "readonly";
				iva.tabIndex = lineas[i].numeroLinea + 600;
				liiva.appendChild(iva);
				element.appendChild(liiva);
				
				var lisubtotal = document.createElement("td");
				lisubtotal.width = "50px";
				var subtotal = document.createElement("input");
				subtotal.name = "subtotal_" + lineas[i].numeroLinea;
				subtotal.type = "text";
				subtotal.size = 6;
				subtotal.id = "subtotal_" + lineas[i].numeroLinea;
				subtotal.readonly = "readonly";
				subtotal.tabIndex = lineas[i].numeroLinea + 700;
				lisubtotal.appendChild(subtotal);
				element.appendChild(lisubtotal);
				
				lineasContainer.appendChild(element);
				
				$('#cant_' + lineas[i].numeroLinea).blur(function(){
					var numero = this.id.substring(5, this.id.length);
					calcularLinea(numero);
				});
				
				$('#prod_' + lineas[i].numeroLinea).change(function(){
					var numero = this.id.substring(5, this.id.length);
					calcularLinea(numero);
				});
				
			}
			
			var numLineas = document.createElement("input");
			numLineas.name = "numLineas";
			numLineas.type = "hidden";
			numLineas.id = "numLineas";
			numLineas.value = lineas.length;
			lineasContainer.appendChild(numLineas);
			
			
		}); 
	}
	
	
	function addLinea(numeroLinea){
		
		var lineasContainer = document.getElementById('contenedorLineas2');
		
		var element = document.createElement("tr");
				
		var litextNode = document.createElement("td");
		litextNode.width = "50px";
		var textNode = document.createTextNode(numeroLinea);
		litextNode.appendChild(textNode);
		element.appendChild(litextNode);
				
		var licantidad = document.createElement("td");
		licantidad.width = "50px";
		var cantidad = document.createElement("input");
		cantidad.name = "cant_" + numeroLinea;
		cantidad.type = "text";
		cantidad.id = "cant_" + numeroLinea;
		cantidad.size = 3;
		licantidad.appendChild(cantidad);
		element.appendChild(licantidad);
				 
		var liselectedProd = document.createElement("td");
		liselectedProd.width = "50px";  
		var selectedProd = document.createElement("select");
		selectedProd.name = "prod_" + numeroLinea;
		selectedProd.id = "prod_" + numeroLinea;
		
		$.each (arrayList, function(key, product){
			var selectOpt = document.createElement("option");
			selectOpt.value = product.id;
			var selectOptText = document.createTextNode(product.name);
			selectOpt.appendChild(selectOptText);
			selectedProd.appendChild(selectOpt);
		});
		liselectedProd.appendChild(selectedProd);
		element.appendChild(liselectedProd);
				
		var liprecioUnidad = document.createElement("td");
		liprecioUnidad.width = "50px";
		var precioUnidad = document.createElement("input");
		precioUnidad.name = "precioUnidad_" + numeroLinea;
		precioUnidad.type = "text";
		precioUnidad.size = 5;
		precioUnidad.id = "precioUnidad_" + numeroLinea;
		precioUnidad.readonly = "readonly";
		precioUnidad.tabIndex = numeroLinea + 500;
		liprecioUnidad.appendChild(precioUnidad);
		element.appendChild(liprecioUnidad);
				
		var liiva = document.createElement("td");
		liiva.width = "50px";				
		var iva = document.createElement("input");
		iva.name = "iva_" + numeroLinea;
		iva.type = "text";
		iva.size = 6;
		iva.id = "iva_" + numeroLinea;
		iva.readonly = "readonly";
		iva.tabIndex = numeroLinea + 600;
		liiva.appendChild(iva);
		element.appendChild(liiva);
				
		var lisubtotal = document.createElement("td");
		lisubtotal.width = "50px";
		var subtotal = document.createElement("input");
		subtotal.name = "subtotal_" + numeroLinea;
		subtotal.type = "text";
		subtotal.size = 6;
		subtotal.id = "subtotal_" + numeroLinea;
		subtotal.readonly = "readonly";
		subtotal.tabIndex = numeroLinea + 700;
		lisubtotal.appendChild(subtotal);
		element.appendChild(lisubtotal);
				
		lineasContainer.appendChild(element);
				
		$('#cant_' + numeroLinea).blur(function(){
			var numero = this.id.substring(5, this.id.length);
			calcularLinea(numero);
		});
				
		$('#prod_' + numeroLinea).change(function(){
			var numero = this.id.substring(5, this.id.length);
			calcularLinea(numero);
		});
		
	}
	
	function addNuevaLinea(){
		var numero = $("#contenedorLineas2").find('tr').size();
		addLinea(numero + 1);
		
		if (document.getElementById("numLineas") == null){
			var numLineas = document.createElement("input");
			numLineas.name = "numLineas";
			numLineas.type = "hidden";
			numLineas.id = "numLineas";
			numLineas.value = numero + 2;
			document.getElementById('contenedorLineas2').appendChild(numLineas);
		}else{
			var numLineas = document.getElementById("numLineas");
			numLineas.value = numero + 2;
		}
	}
	
	function calcularLinea(numero){
		var cantidad = dwr.util.getValue('cant_' + numero);
		var idProducto = dwr.util.getValue('prod_' + numero);
		$.each (arrayList, function(key, product){
			if (product.id == idProducto){
				dwr.util.setValue('precioUnidad_' + numero, product.price);
				dwr.util.setValue('iva_' + numero, product.iva);
				dwr.util.setValue('subtotal_' + numero, ((product.price * cantidad) + ((product.price * cantidad * product.iva)/100)).toFixed(2));
			}
		}); 
		var totalAgrupado = 0;
		for(var i = 1; i <= numero; i++){
			totalAgrupado += parseFloat(dwr.util.getValue('subtotal_' + numero));
		}
		dwr.util.setValue('total', totalAgrupado.toFixed(2));
	}
//]]>	
</script>  
  <div id="contenedor">
  
  	<jsp:include page="/common/menu.jsp"/>
    
    <div class="titulo"><fmt:message key="facturaForm.title"/></div>
    
    <form:form commandName="factura" method="post" id="facturaForm" action="facturaForm.do">
	<div class="detalleFactura fila">
		<ul>
		<li><label for="fecha"><fmt:message key="factura.fecha"/></label><form:input path="fecha" cssClass="w75"/></li>
			<c:choose>
				<c:when test="${factura.id ne null}">
					<c:forEach items="${customerList}" var="customer">
						<c:if test="${customer.id eq factura.customerId}">
							<li>
								<label><fmt:message key="factura.customer.nombre"/></label>
								<span><c:out value="${customer.nombre}"/></span>
							</li>
							<li>
								<label><fmt:message key="customer.identificador"/></label>
								<span><c:out value="${customer.identificador}"/></span>
							</li>
							<li>
								<label><fmt:message key="customer.direccion"/></label>
								<span><c:out value="${customer.direccion}"/></span>
							</li>
						</c:if>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<li>
						<label><fmt:message key="factura.customer.nombre"/></label>
						<form:select path="customerId" items="${customerList}" itemLabel="nombre" itemValue="id"/>
					</li>	
				</c:otherwise>
			</c:choose>
			<li>
				<label><fmt:message key="factura.numero"/></label>
				<form:input path="numero" id="numero" cssClass="w75"/>
			</li>
			<li>
				<label><fmt:message key="factura.pagado"/></label>
				<form:checkbox path="pagado"/>
			</li>	
		</ul>
	</div>
	
	<div class="lineasFactura fila">
		<table>
		<thead>
			<tr>
				<th width="50px"><fmt:message key="factura.linea.numero"/></th>
				<th width="50px"><fmt:message key="factura.linea.cantidad"/></th>
				<th width="100px"><fmt:message key="factura.linea.producto"/></th>
				<th width="50px"><fmt:message key="factura.linea.precio"/></th>
				<th width="50px"><fmt:message key="factura.linea.iva"/></th>
				<th width="50px"><fmt:message key="factura.linea.subtotal"/></th>
			</tr>
		</thead>
		<tbody id="contenedorLineas2">
			<c:if test="${factura.id ne null}">
			<c:forEach items="${factura.lineasFactura}" var="lineaFactura">
				<tr>
					<td width="50px"><c:out value="${lineaFactura.numeroLinea}"/></td>
					<td width="50px"><c:out value="${lineaFactura.cantidad}"/></td>
					<td width="100px"><c:out value="${mapaProductos[lineaFactura.productId].name}"/></td>
					<td width="50px"><c:out value="${lineaFactura.precio}"/></td>
					<td width="50px"><c:out value="${lineaFactura.iva}"/></td>
					<td width="50px"><c:out value="${lineaFactura.total}"/></td>
				</tr>
			</c:forEach>
			</c:if>
		</tbody>
		</table>
	</div>

<div class="fila">
<ul>
<li>
				<label><fmt:message key="factura.total"/></label>
				<form:input path="total" id="total" maxlength="6" cssClass="w75"/>
			</li>
			<li>
			<c:if test="${factura.id ne null}">
				<input type="submit" class="button" name="remove" value="Eliminar" title="Eliminar"/>
	            <input type="hidden" name="longSavedKey" value="${factura.id.id}"/>
	            <span onclick="window.print()" class="button print">
	            	<fmt:message key="factura.imprimir"/>
	            </span>
	        </c:if>
			</li>
			<c:if test="${factura.id eq null}">
				<li>
					<span class="botonmas button" onclick="addNuevaLinea();">
						<fmt:message key="factura.nueva.linea"/>
					</span>
				</li>
				<li>
					<input type="submit" class="button" name="save" onclick="bCancel=false" value="Gardar" title="Gardar"/>
				</li>	
			</c:if>
</ul>
</div>
</form:form>
	
</div>
	
</body>

</html>
