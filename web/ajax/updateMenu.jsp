<%-- 
    Document   : updateMenu
    Created on : 6-lug-2019, 15.20.58
    Author     : Roberto97
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="request" value="<%=request%>"/> <!-- Request lo chiamo con una scriplets e lo salvo in una variabile perchè serve per diversi metodi java -->
<c:set var="menu" value="${menudao.getMenu(request.getParameter('id'))}" />
<!-- Add .modal-dialog-centered to .modal-dialog to vertically center the modal -->
<div class="modal-dialog modal-dialog-centered" role="document" style="max-width: fit-content;">


    <div class="modal-content container">
        <div class="modal-header">
            <h5 class="modal-title" id="catImgChangeTitle">${menu.nome}</h5>
            <button type="button" class="close" onclick='closeModal();' aria-label="Close" >
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
        </div>
        <form method="POST" action="updateMenu" enctype="multipart/form-data">
            <input type="hidden" name="DELETE" value="false"/>

            <div class="row">
                <div class="col-md-6">
                    <p>Copertina</p>
                    <img id="copertinaMenu" src="${menu.copertina}" alt="${menu.nome}_Modal_IMG" class="inputImg mb-3"/><br>
                    <input type='file' name="copertinaMenu" onchange="readURL(this, 'copertinaMenu');"/> 
                </div>
                <div class="col-md-6">
                    <p>Immagine</p>
                    <img id="imgMenu" src="${menu.immagine}" alt="${menu.nome}_Modal_IMG" class="inputImg mb-3"/><br>
                    <input type='file' name="immagineMenu" onchange="readURL(this, 'imgMenu');"/> 
                </div>                
            </div>

            <input type="text" name="nome" value="${menu.nome}" class="form-control mt-4 mb-4" required/>

            <input type="hidden" name="oldImg" value="${menu.immagine}" required/>
            <input type="hidden" name="oldCopertina" value="${menu.copertina}" required/>
            <input type="hidden" name="id" value="${menu.id}" required>
            <button type="button" class="btn btn-secondary" onclick='closeModal();'>Annulla</button>
            <button id="updateMenuSubmit" type="submit" class="btn btn-primary">Salva</button>
        </form>
        <div class="text-center">
            <button onclick="$('#sureToDeleteMenu').removeClass('invisible')" id="delete_${menu.id}" type="button" class="btn btn-danger">Elimina</button>
        </div>
        <div class="text-center invisible mt-4" id="sureToDeleteMenu">
            <p>Sicuro di eliminare la categoria '${menu.nome}'?</p>
            <form method="POST" action="updateMenu" enctype="multipart/form-data">
                <input type="hidden" name="id" value="${menu.id}" required/>
                <input type="hidden" name="oldImg" value="${menu.immagine}" required/>
                <input type="hidden" name="oldCopertina" value="${menu.copertina}" required/>
                <input type="hidden" name="DELETE" value="true"/>
                <button id="confirm_delete_${menu.id}" type="submit" class="btn btn-danger">Si</button>
                <button onclick="$('#sureToDeleteMenu').addClass('invisible')" id="confirm_delete_${menu.id}" type="button" class="btn btn-secondary">Annulla</button>
            </form>
        </div>
    </div>
</div>
