<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE HTML>
<html>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageId" value="4"/>
</jsp:include>

<body>
   
  <div id="contenedor">
  <jsp:include page="/common/menu.jsp"/>
    
    <div class="titulo"><fmt:message key="noticeForm.title"/></div>
    <div class="fila">
    <form:form commandName="notice" method="post"  id="noticeForm" action="noticeForm.do">
	<ul>
		<form:hidden path="id"/>
		<li>Titulo</li>
		<li><form:input path="title" id="title"/></li>
		
		<li>Corpo&nbsp;</li>
		<li>
			<form:textarea path="body" cols="20" rows="10" id="body"/>
		</li>
		
		<li>
		<input type="submit" class="button" name="save" onclick="bCancel=false" value="save"/>
		&nbsp;
		<c:if test="${notice.id ne null}">
            <input type="submit" class="button" name="remove" value="remove"/>
        </c:if>
		</li>
	</ul>
	</form:form>
	</div>
</div>
	
</body>
</html>