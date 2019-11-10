<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE HTML>
<html>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageId" value="2"/>
</jsp:include>

<body>
   
  <div id="contenedor">
  <jsp:include page="/common/menu.jsp"/>
    
    <div class="titulo"><fmt:message key="productForm.title"/></div>
    
    <form:form commandName="product" method="post"  id="productForm" action="productForm.do">
	<ul>
		<form:hidden path="id"/>
		<li><fmt:message key="producto.nombre"/></li>
		<li><form:input path="name" id="name"/></li>
		
		<li><fmt:message key="producto.tipo"/>&nbsp;</li>
		<li>
			<form:select path="tipo" items="${tipoList}" itemLabel="label" itemValue="value"/>
		</li>
		
		<li><fmt:message key="producto.temporada"/>&nbsp;</li>
		<li>
			<form:select path="temporada" items="${temporadaList}" itemLabel="label" itemValue="value"/>
		</li>
		
		
		<li><fmt:message key="producto.precio"/>&nbsp;</li>
		<li>
			<form:input path="price"/>
		</li>
		
		<li><fmt:message key="producto.iva"/>&nbsp;</li>
		<li>
			<form:input path="iva"/>
		</li>
		
		<li><fmt:message key="comentario"/></li>
		<li>
			<form:textarea path="comentario"/>
		<li>
		
		<li>Reservable (aparecerá en pedidos):&nbsp;&nbsp;<form:checkbox path="reservable"/></li>
		
		<li>
		<input type="submit" class="button" name="save" onclick="bCancel=false" value="save"/>
		&nbsp;
		<c:if test="${product.id ne null}">
            <input type="submit" class="button" name="remove" value="remove"/>
        </c:if>
		</li>
		
	</form:form>
	
	<c:if test="${product.id ne null}">
	<ul>
		<li>
			<a href="<c:url value='/productLyte.do?gotoimages=true&productId=${product.id}'/>">
				EDITAR IMAGENES
			</a>
		</li>
	</ul>
	</c:if>
	</div>
	
</body>
</html>