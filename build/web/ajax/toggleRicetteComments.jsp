<%-- 
    Document   : toggleRicetteComments
    Created on : 16-giu-2019, 17.22.43
    Author     : Roberto97
--%>

<%@page import="java.time.format.DateTimeFormatter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="request" value="<%=request%>"/> <!-- Request lo chiamo con una scriplets e lo salvo in una variabile perchè serve per diversi metodi java -->
<c:set var="idea" value="${ricettedao.getRecipe(param.id)}"/>
<c:set var="commenti" value="${ricettedao.getComments(idea.id)}"/>

<div class="comments-area">
    <div class="comment-list">
        <c:forEach items="${commenti}" var="commento" >
            <div class="single-comment justify-content-between d-flex">
                <div class="user justify-content-between d-flex">
                    <div class="desc">
                        <h5 class="personalized"><a style="color: #9c27b0;">${commento.nome}</a></h5>
                        <p class="personalized date">${commento.data.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))}</p>
                        <p class="personalized comment">
                            ${commento.testo}
                        </p>
                    </div>
                </div>
            </div>
            <hr/>
        </c:forEach>
    </div>
</div>