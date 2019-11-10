<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE HTML>
<html>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageId" value="5"/>
</jsp:include>

<body>
   
  <div id="contenedor">
  <jsp:include page="/common/menu.jsp"/>
    
    <div class="titulo"><fmt:message key="customerForm.title"/></div>
    
    <div class="fila">
    <form:form commandName="customer" method="post"  id="customerForm" action="customerForm.do">
	<ul>
		<form:hidden path="id"/>
		<li><fmt:message key="customer.nombre"/></li>
		<li><form:input path="nombre" id="nombre"/></li>
		
		<li><fmt:message key="customer.identificador"/></li>
		<li><form:input path="identificador" id="identificador"/></li>
		
		<li><fmt:message key="customer.email"/></li>
		<li><form:input path="email" id="email"/></li>
		
		<li>
		<input type="submit" class="button" name="save" onclick="bCancel=false" value="save"/>
		&nbsp;
		<c:if test="${customer.id ne null}">
            <input type="submit" class="button" name="remove" value="remove"/>
        </c:if>
		</li>
	</ul>
	</form:form>
	</div>
</div>
	
</body>
</html>