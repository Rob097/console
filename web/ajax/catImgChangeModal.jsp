<%-- 
    Document   : catImgChangeModal
    Created on : 1-giu-2019, 19.58.58
    Author     : Roberto97
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="request" value="<%=request%>"/> <!-- Request lo chiamo con una scriplets e lo salvo in una variabile perchè serve per diversi metodi java -->
<c:set var="categoria" value="${categorydao.getById(request.getParameter('id'))}" />
<!-- Add .modal-dialog-centered to .modal-dialog to vertically center the modal -->
<div class="modal-dialog modal-dialog-centered" role="document">


    <div class="modal-content container">
        <div class="modal-header">
            <h5 class="modal-title" id="catImgChangeTitle">${categoria.nome}</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            Inserisci l'url dell'immagine
        </div>
        <form method="POST" action="catImgChange">
            <input type="text" id="urlCatImg" name="url" class="form-control mb-4" placeholder=URL">
            <input type="hidden" name="id" value="${categoria.id}">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
            <button id="catImgChangeSubmit" type="submit" class="btn btn-primary">Salva</button>
        </form>
    </div>
</div>