<%@ page contentType="text/plain;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

  <c:forEach items="${customerList}" var="customer">
 	<c:out value="${customer.id}"/>;<c:out value="${customer.nombre}"/>;<c:out value="${customer.identificador}"/>;<c:out value="${customer.email}"/>;<fmt:formatDate value="${customer.lastUpdate}" pattern="dd/MM/yyyy" />;
 </c:forEach>
