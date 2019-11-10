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
 
 <div class="titulo"><fmt:message key="product.edit.images"/></div>

 <a href="<c:url value='/productForm.do?id=${pBean.product.id}'/>"><fmt:message key="productForm.title"/></a>
 	productId&nbsp;<c:out value='${pBean.product.id}'/>
 	
 	<ul>
 	<c:forEach items='${pBean.imageList}' var="image" varStatus="imageStatus">
 		<li>
 		<a href="<c:url value='/productDetails/removeImage.do?imageId=${image.id}&productId=${pBean.product.id}'/>"> 
 			<img src="<c:url value='/image.do?imageId=${image.id}'/>" height="60px" width="60px"/>
 			eliminar esta imagen
 		</a>
 		</li> 				
 	</c:forEach>
 	</ul>
 
 <p>
  	<button title="masImagenes" name="Mas Imágenes" value="Add Image" onclick="addFile();" >
  	Mas Imagenes
  	</button>
 </p>	

<form  method="POST" id="productForm" action="/productImages.do?productId=${pBean.product.id}" enctype="multipart/form-data" data-ajax="false">
	<ul>
		<li>modelo&nbsp;${pBean.product.name}</li>
		<li>temporada&nbsp;${pBean.temporada}</li>
	</ul>
				
	FOTOS
	<ol id="files-root">
		<li><input type="file" name="file[]">
	</ol>
	<input type="submit" class="button" name="save" onclick="bCancel=false"  data-ajax="false" value="save"/>
	
</form>
 
</div>
 	
</body>
</html>