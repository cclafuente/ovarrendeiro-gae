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
 
<div class="titulo">Detalles Producto</div> 
 	
 	<div>
 	<a href="<c:url value='/productForm.do?id=${pBean.product.id}'/>">
 		editar producto <c:out value='${pBean.product.id}'/> &nbsp;
 	</a>
  		temporada:&nbsp;
 		<b><c:out value='${pBean.temporada}'/></b>
 		&nbsp;modelo:&nbsp;
 		<b><c:out value='${pBean.product.name}'/></b>
	 	&nbsp;
	 	<c:if test='${pBean.product.price ne 0}'>
	 	&nbsp;precio:&nbsp;
	 	<b><c:out value='${pBean.product.price}'/></b>
	 	</c:if>
	</div>
 	
 	<dl class="slideShowGallery">
 	
 	<dt>
 		<c:forEach items='${pBean.imageList}' var="image" varStatus="imageStatus">
 			<c:if test="${imageStatus.first}">
 				<a class="thickbox" href="<c:url value='/image.do?imageId=${image.id}'/>" title="${pBean.product.name}"> 
 					<img src="<c:url value='/image.do?imageId=${image.id}'/>" title="${pBean.product.name}" alt="${pBean.product.name}"/>
 				</a>
 			</c:if>
 		</c:forEach>	
 	</dt>		
 	
 	<dd>	
 		<c:forEach items='${pBean.imageList}' var="image" varStatus="imageStatus">
 			<a href="<c:url value='/image.do?imageId=${image.id}'/>" rev='<c:url value='/image.do?imageId=${image.id}'/>'> 
 				<img src="<c:url value='/image.do?imageId=${image.id}'/>" title="${pBean.product.name}" alt="${pBean.product.name}"/>
 			</a> 				
 		</c:forEach>
 	</dd>
 	
 	</dl>
 	
 	

</div>

</body>

</html>