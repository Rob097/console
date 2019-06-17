<%-- 
    Document   : updateProd
    Created on : 2-giu-2019, 17.12.33
    Author     : Roberto97
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="request" value="<%=request%>"/> <!-- Request lo chiamo con una scriplets e lo salvo in una variabile perchè serve per diversi metodi java -->
<c:set var="prodotto" value="${productdao.getProduct(request.getParameter('id'))}" />
<!-- Add .modal-dialog-centered to .modal-dialog to vertically center the modal -->
<div class="modal-dialog modal-dialog-centered" role="document">


    <div class="modal-content container">
        <div class="modal-header">
            <h5 class="modal-title" id="catImgChangeTitle">${prodotto.nome}</h5>
            <button type="button" class="close" onclick='closeModal();' aria-label="Close" >
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <form method="POST" action="updateProd" enctype="multipart/form-data">
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6">
                        <div style="height: 100%;">
                            <img id="InputIMGUpdateProd" src="${prodotto.immagine}" alt="${prodotto.nome}_Modal_IMG" class="inputImg mb-3"/>
                        </div>
                        <input type='file' name="immagine" onchange="readURL(this, 'InputIMGUpdateProd');"/> 
                        <input type="hidden" name="oldImg" value="${prodotto.immagine}" required/>
                    </div>
                    <div class="col-md-6">
                        <input type="text" name="nome" class="form-control mb-4" value="${prodotto.nome}" required/>
                        <select class="form-control mb-4" id="categoria" name="categoria">
                            <c:choose>
                                <c:when test="${prodotto.fresco == true}">
                                    <c:forEach var="cat" items="${categorydao.getFreshCategories()}">
                                        <option <c:if test="${cat.nome eq prodotto.categoria}">selected</c:if>>${cat.nome}</option>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="cat" items="${categorydao.getConfCategories()}">
                                        <option <c:if test="${cat.nome eq prodotto.categoria}">selected</c:if>>${cat.nome}</option>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </select>
                        <p class="text-muted">Prodotto 
                            <c:choose>
                                <c:when test="${prodotto.isFresco()}">
                                    Fresco
                                </c:when>
                                <c:otherwise>
                                    Confezionato
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </div>  
                <label class="mt-3" for="costo">Costo</label>
                <input type="text" name="costo" class="form-control mb-4" value="${prodotto.costo}" required />

                <label for="descrizione">Descrizione</label>
                <textarea name="descrizione" class="form-control mb-4" required>${prodotto.descrizione}</textarea>
                <input type="checkbox" name="disponibile" <c:if test="${prodotto.isDisponibile()}">checked</c:if>> Disponibile
                <input type="hidden" name="fresco" value="${prodotto.isFresco()}" />
            </div>
            <input type="hidden" name="idProd" value="${prodotto.id}" required/>
            <button type="button" class="btn btn-secondary" onclick='closeModal();'>Annulla</button>
            <button id="catImgChangeSubmit" type="submit" class="btn btn-primary">Salva</button>
        </form>
        <div class="text-center">
            <button onclick="$('#sureToDelete').removeClass('invisible')" id="delete_${prodotto.id}" type="button" class="btn btn-danger">Elimina</button>
        </div>
        <div class="text-center invisible" id="sureToDelete">
            <c:choose>
                <c:when test="${prodotto.categoria ne null}">
                    <p>Sicuro di eliminare '${prodotto.nome}' della categoria '${prodotto.categoria}' ?</p>
                </c:when>
                <c:otherwise>
                    <p>Sicuro di eliminare '${prodotto.nome}' ?</p>
                </c:otherwise>
            </c:choose>            
            <form method="POST" action="updateProd">
                <input type="hidden" name="idProd" value="${prodotto.id}" required/>
                <input type="hidden" name="immagine" value="${prodotto.immagine}" required/>
                <input type="hidden" name="DELETE" value="true"/>
                <button id="confirm_delete_${prodotto.id}" type="submit" class="btn btn-danger">Si</button>
                <button onclick="$('#sureToDelete').addClass('invisible')" id="confirm_delete_${prodotto.id}" type="button" class="btn btn-secondary">Annulla</button>
            </form>
        </div>
    </div>
</div>