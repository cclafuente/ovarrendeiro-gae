<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:og="http://ogp.me/ns#"
      xmlns:fb="https://www.facebook.com/2008/fbml">

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageId" value="2"/>
</jsp:include>

<body>

<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/es_ES/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<div id="contenedor">

<jsp:include page="/common/menu.jsp"/> 

<div class="titulo"><fmt:message key="productList.title"/></div>

<div class="wrapperProductos">
  <c:forEach items="${productList}" var="pBean">
  <div class="productBox">	
  	<div class="productTxt">
 	<ul>
 		<li style="font-weight: bold">
	 	<c:out value="${pBean.product.name}"/>
	 	</li>
	 	<li><c:out value="${pBean.product.comentario}" escapeXml="false"/></li>
	 </ul>
	 </div>
	 <div class="productImages">
	 		<c:forEach items="${pBean.imageList}" var="pImage" varStatus="status">
	 			<a href="<c:url value='/image.do?imageId=${pImage.id}'/>" rel="lytebox[productImages_${pImage.productId}]" 
	 				title="${pBean.product.name}">
	 			<c:if test="${status.first}">
	 				<img src="<c:url value='/image.do?imageId=${pImage.id}'/>" 
	 					title="IMAXES DE ${pBean.product.name}"
	 					alt="${pBean.product.name}"/>
	 			</c:if>
	 			</a>
	 		</c:forEach>
	 </div>
 </div>
 </c:forEach>
</div>

<div class="fb-like" data-href="http://www.panaderiaovarrendeiro.es" data-send="true" data-show-faces="true"></div>

</div>

</body>
</html>