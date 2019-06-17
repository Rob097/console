<%-- 
    Document   : recipeIngs
    Created on : 16-giu-2019, 18.17.01
    Author     : Roberto97
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="idea" value="${ricettedao.getRecipe(param.id)}"/>

<c:choose>
    <c:when test="${idea.ingredienti.size() > 0 && idea.ingredienti.size() < 2}">
        <c:if test="${!idea.ingredienti.get(0).equals('')}">
            <c:forEach items="${idea.ingredienti}" var="ingrediente">
                <p>
                    <a style="cursor: pointer;" onclick="removeIng(${idea.id}, '${ingrediente}');">
                        <i class="fas fa-times mr-2 ml-4" style="color: red;"></i>
                    </a>${ingrediente}
                </p>
            </c:forEach>
        </c:if>
    </c:when>
    <c:when test="${idea.ingredienti.size() > 1}">
        <c:forEach items="${idea.ingredienti}" var="ingrediente">
            <p>
                <a style="cursor: pointer;" onclick="removeIng(${idea.id}, '${ingrediente}');">
                    <i class="fas fa-times mr-2 ml-4" style="color: red;"></i>
                </a>${ingrediente}
            </p>
        </c:forEach>
    </c:when>
</c:choose>