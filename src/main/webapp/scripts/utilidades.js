var productList = null;

/**
se valida si un valor es un email valido
**/
function validateEmail(email){
	var regexp = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	if(regexp.test(email)){
   		return 	true;
	}else{
   		return false;
	}
}

function validarPedido(){
		if(!$.trim($("#total").val()).length) {
            $("#total").parents('li').addClass('warning');
            return false;
     	}else{
     		$("#total").parents('li').removeClass('warning');
     	}
		if(!$.trim($("#fechaEntrega").val()).length) {
            $("#fechaEntrega").parents('li').addClass('warning');
            return false;
     	}else{
     		$("#fechaEntrega").parents('li').removeClass('warning');
     	}
     	if(!$.trim($("#email").val()).length) {
            $("#email").parents('li').addClass('warning');
            return false;
     	}
     	
     	if(!validateEmail($("#email").val())){
     		$("#email").parents('li').addClass('warning');
            return false;
     	}else{
     		$("#email").parents('li').removeClass('warning');
     	}
     	
		return true;
}
	
	
function loadProducts(){
		ProductManager.getAllProducts(1, function(lista){
		var resultado = "<ul>";
		for (i=0; i<lista.length; i++) {
			resultado += "<li>" + lista[i].id + "</li>";
		}
		resultado += "</ul>";
		var listaInfGenerados = document.getElementById("contenedor");
		listaInfGenerados.innerHTML = resultado;
		productList = lista;
	});
		
	$.each (productList, function(key, product){
		alert(key);
	});
}

function generarLineas(contenedor){
	FacturaUtil.crearLineas(dwr.util.getValue('year'),dwr.util.getValue('mes'), dwr.util.getValue('tipo'),
	function(lineas){
		var lineasContainer = document.getElementById(contenedor);
			
		while (lineasContainer.hasChildNodes()) {
	        lineasContainer.removeChild(lineasContainer.firstChild);
		}
			
		for (i=0; i<lineas.length; i++){
			var element = document.createElement("li");
			var textNode = document.createTextNode(lineas[i].numeroLinea);
			element.appendChild(textNode);
			var cantidad = document.createElement("input");
			cantidad.name = "cant_" + lineas[i].numeroLinea;
			cantidad.type = "text";
			element.appendChild(cantidad);
			var iva = document.createElement("input");
			iva.name = "iva_" + lineas[i].numeroLinea;
			iva.type = "text";
			element.appendChild(iva);
			/*iva.onclick = cargarIva(1, "iva_" + lineas[i].numeroIva);*/ 
			var selectedProd = document.createElement("select");
			selectedProd.name = "prod_" + lineas[i].numeroLinea;
			$.each (productList, function(key, product){
				var selectOpt = document.createElement("option");
				selectOpt.value = product.id;
				var selectOptText = document.createTextNode(product.name);
				selectOpt.appendChild(selectOptText);
				selectedProd.appendChild(selectOpt);
			});
			element.appendChild(selectedProd);
			lineasContainer.appendChild(element);
		}
	}); 
}

var gFiles = 0;
function addFile() {
    var li = document.createElement('li');
    li.setAttribute('id', 'file-' + gFiles);
    li.innerHTML = '<input type="file" name="file[]"><span onclick="removeFile(\'file-' + gFiles + '\')" style="cursor:pointer;">Remove</span>';
    document.getElementById('files-root').appendChild(li);
    gFiles++;
}
function removeFile(aId) {
    var obj = document.getElementById(aId);
    obj.parentNode.removeChild(obj);
}