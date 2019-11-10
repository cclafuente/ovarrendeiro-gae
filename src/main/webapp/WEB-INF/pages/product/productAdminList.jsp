<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML>
<html>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageId" value="2"/>
</jsp:include>

<body>
<div id="contenedor">

<jsp:include page="/common/menu.jsp"/> 

<div class="titulo"><fmt:message key="producto.admin.title"/></div>

<div class="subtitulo">
	<a href="<c:url value="/productForm.do"/>"><fmt:message key="producto.add"/></a> 
</div>

<table class="sortable" width="600px">
<thead>
  <tr>
  	<th width="50px">Tipo</th>
  	<th width="100px">Temporada</th>
  	<th width="50px">Precio</th>
  	<th width="50px">Nombre</th>
  	<th class="sorttable_nosort" width="100px">Comentario</th>
  	<th class="sorttable_nosort" width="50px">Act.</th>
  	<th class="sorttable_nosort" width="100px">Imagen</th>
  </tr>
</thead>
<tbody>  
  <c:forEach items="${productList}" var="pBean">
 	<tr>
 		<td style="font-weight: bold">
	 	<a href="<c:url value="/productForm.do?id=${pBean.product.id}"/>" title="Editar Producto">
	 		<c:out value="${pBean.tipo}"/>
	 	</a>
	 	</td>
	 	<td style="font-weight: bold">
	 	<a href="<c:url value="/productForm.do?id=${pBean.product.id}"/>" title="Editar Producto">
	 		<c:out value="${pBean.temporada}"/>
	 	</a>
	 	</td>
	 	<td><c:out value="${pBean.product.price}"/></td>
	 	<td><c:out value="${pBean.product.name}"/></td>
	 	<td><c:out value="${pBean.product.comentario}"/></td>
	 	<td><fmt:formatDate value="${pBean.product.lastUpdate}" pattern="dd/MM/yyyy" /></td>
	 	<td>
	 	<c:forEach items="${pBean.imageList}" var="pImage" varStatus="status">
	 	<a href="<c:url value='/image.do?imageId=${pImage.id}'/>" rel="lytebox[productImages_${pImage.productId}]" title="${pBean.product.name}">
	 		<c:if test="${status.first}">
	 			<img src="<c:url value='/image.do?imageId=${pImage.id}'/>" width="80px" height="60px" 
	 				style="float: right;" title="IMAXES DO PRODUCTO ${pBean.product.name}"/>
	 		</c:if>
	 	</a>
	 	</c:forEach>
	 	</td>
	</tr>
 </c:forEach>
 </tbody>
</table>

</div>

</body>
</html>