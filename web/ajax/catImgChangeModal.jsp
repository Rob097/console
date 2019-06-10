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
            <button type="button" class="close" onclick='closeModal();' aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
        </div>
        <form method="POST" action="catImgChange" enctype="multipart/form-data">
            <img id="InputIMG" src="${categoria.immagine}" alt="${categoria.nome}_Modal_IMG" class="inputImg mb-3"/>
            <input type='file' name="immagine" onchange="readURL(this);"/> 
          
            <input type="text" name="nome" value="${categoria.nome}" class="form-control mt-4 mb-4" required/>
            
            <input type="hidden" name="fresco" value="${categoria.freschi}" required/>
            <input type="hidden" name="oldImg" value="${categoria.immagine}" required/>
            <input type="hidden" name="id" value="${categoria.id}" required>
            <button type="button" class="btn btn-secondary" onclick='closeModal();'>Annulla</button>
            <button id="catImgChangeSubmit" type="submit" class="btn btn-primary">Salva</button>
        </form>
    </div>
</div>