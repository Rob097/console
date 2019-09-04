<%-- 
    Document   : productVariants
    Created on : 28-ago-2019, 18.10.49
    Author     : Roberto97
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="request" value="<%=request%>"/> <!-- Request lo chiamo con una scriplets e lo salvo in una variabile perchè serve per diversi metodi java -->
<c:set var="prodotto" value="${productdao.getProduct(request.getParameter('id'))}" />
<c:set var="varianti" value="${productdao.getProductVariant(prodotto.id)}" />

<c:forEach items="${varianti}" var="variante" >
    <c:set var="name" value="${variante.getKey()}" />
    <c:set var="array" value="${variante.getValue()}" />
    <p>
        <a style="cursor: pointer;" onclick="removeVariant(${prodotto.id}, '${name}');">
            <i class="fas fa-times mr-2 ml-4" style="color: red;"></i>
        </a>
        ${name}:<br>
    <ul style="margin-left: 20px;">
        <c:forEach items="${array}" var="scelta" >
            <li>${scelta.variantName} (+ € ${scelta.supplement})</li>
        </c:forEach>
    </ul>
</p>
</c:forEach>
