<%-- 
    Document   : updateProd
    Created on : 2-giu-2019, 17.12.33
    Author     : Roberto97
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="request" value="<%=request%>"/> <!-- Request lo chiamo con una scriplets e lo salvo in una variabile perchè serve per diversi metodi java -->
<c:set var="prodotto" value="${productdao.getProduct(request.getParameter('id'))}" />
<c:set var="varianti" value="${productdao.getProductVariant(prodotto.id)}" />
<!-- Add .modal-dialog-centered to .modal-dialog to vertically center the modal -->
<div class="modal-dialog modal-dialog-centered" role="document">


    <div class="modal-content container">
        <div class="modal-header">
            <h5 class="modal-title" id="catImgChangeTitle">${prodotto.nome} <span>(${productdao.getRate(prodotto.id)}<i class="fas fa-star ml-2 mr-2"></i> |  ${productdao.getNumberRate(prodotto.id)} <i class="fas fa-user ml-2 mr-2"></i>)</span></h5>
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
                        <input maxlength="45" type="text" name="nome" class="form-control mb-4" value="${prodotto.nome}" required/>
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
                <label class="mt-5" for="costo">Costo</label>
                <input type="text" name="costo" class="form-control mb-4" value="${prodotto.costo}" required />
                <label class="mt-1" for="peso">Peso (kg)</label>
                <input type="text" name="peso" class="form-control mb-4" value="${prodotto.peso}" required />

                <label for="descrizione">Descrizione</label>
                <textarea style="min-height: 100px;" name="descrizione" class="form-control mb-4" required>${prodotto.descrizione}</textarea>
                <label for="meta_descrizione">Meta Descrizione</label>
                <textarea maxlength="160" style="min-height: 60px;" name="meta_descrizione" class="form-control mb-4">${prodotto.meta_descrizione}</textarea>

                <label for="dynamic_form">Varianti</label>
                <c:if test="${varianti ne null && !varianti.isEmpty()}">
                    <div id="acutalVariants">
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
                                        <li>${scelta.variantName} (€ ${scelta.supplement} | ${scelta.pesoMaggiore} kg.)</li>
                                    </c:forEach>
                                </ul>
                            </p>
                        </c:forEach>
                    </div>
                </c:if>
                <div class="form-group form-inline" id="dynamic_form">                                        
                    <div class="form-group col-lg-5 col-md-12 name">
                        <input type="text" class="form-control" id="variante" name="variante" placeholder="Variante">
                    </div>
                    <div class="form-group col-lg-5 col-md-12 name">
                        <input type="text" class="form-control" id="scelta" name="scelta" placeholder="Scelte">
                    </div>
                    <div class="form-group col-lg-5 col-md-12 name">
                        <input type="text" class="form-control" id="supplement" name="supplement" placeholder="Costo">
                    </div>
                    <div class="form-group col-lg-5 col-md-12 name">
                        <input type="text" class="form-control" id="pesoVariante" name="pesoVariante" placeholder="Peso">
                    </div>
                    <div class="button-group form-group col-lg-2 col-md-12">
                        <a href="javascript:void(0)" class="btn btn-primary" id="plus" style="padding: .375rem .75rem;">+</a>
                        <a href="javascript:void(0)" class="btn btn-danger" id="minus" style="padding: .375rem .75rem;">-</a>
                    </div>
                </div>

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
<script src="js/dynamic-form.js"></script>
<script>
    function removeVariant(idProd, variant) {
                $.ajax({
                    type: "POST",
                    url: "removeVariants",
                    data: {idProd: idProd, variant: variant},
                    cache: false,
                    success: function (response) {
                        $('#acutalVariants').html(response);
                    },
                    error: function () {
                        alert("Errore remove Variant");
                    }
                });
            }
            
                    $(document).ready(function () {
                        var dynamic_form = $("#dynamic_form").dynamicForm("#dynamic_form", "#plus", "#minus", {
                            limit: 10,
                            formPrefix: "dynamic_form",
                            normalizeFullForm: false
                        });

                        dynamic_form.inject([{p_name: 'Hemant', quantity: '123', remarks: 'testing remark'}, {p_name: 'Harshal', quantity: '123', remarks: 'testing remark'}]);

                        $("#dynamic_form #minus5").on('click', function () {
                            var initDynamicId = $(this).closest('#dynamic_form').parent().find("[id^='dynamic_form']").length;
                            if (initDynamicId === 2) {
                                $(this).closest('#dynamic_form').next().find('#minus').hide();
                            }
                            $(this).closest('#dynamic_form').remove();
                        });

                        $('form').on('submit', function (event) {
                            var values = {};
                            $.each($('form').serializeArray(), function (i, field) {
                                values[field.name] = field.value;
                            });
                            console.log(values);
                            event.preventDefault();
                        });
                    });

                    // Code with description of parameters.
// See full documentation here : https://github.com/mimo84/bootstrap-maxlength-min/

                    $('[maxlength]').maxlength({
                        alwaysShow: true, //if true the threshold will be ignored and the remaining length indication will be always showing up while typing or on focus on the input. Default: false.
                        // threshold: 10, //Ignored if alwaysShow is true. This is a number indicating how many chars are left to start displaying the indications. Default: 10
                        warningClass: "form-text text-muted mt-1", //it's the class of the element with the indicator. By default is the bootstrap "badge badge-success" but can be changed to anything you'd like.
                        limitReachedClass: "form-text text-muted mt-1", //it's the class the element gets when the limit is reached. Default is "badge badge-danger". Replace with text-danger if you want it to be red.
                        //separator: ' of ', //represents the separator between the number of typed chars and total number of available chars. Default is "/".
                        //preText: 'You have ', //is a string of text that can be outputted in front of the indicator. preText is empty by default.
                        //postText: ' chars remaining.', //is a string outputted after the indicator. postText is empty by default.
                        showMaxLength: true, //showMaxLength: if false, will display just the number of typed characters, e.g. will not display the max length. Default: true.
                        showCharsTyped: true, //if false, will display just the remaining length, e.g. will display remaining lenght instead of number of typed characters. Default: true.
                        placement: 'bottom-right-inside', //is a string, object, or function, to define where to output the counter. Possible string values are: bottom ( default option ), left, top, right, bottom-right, top-right, top-left, bottom-left and centered-right. Are also available : **bottom-right-inside** (like in Google's material design, **top-right-inside**, **top-left-inside** and **bottom-left-inside**. stom placements can be passed as an object, with keys top, right, bottom, left, and position. These are passed to $.fn.css. A custom function may also be passed. This method is invoked with the {$element} Current Input, the {$element} MaxLength Indicator, and the Current Input's Position {bottom height left right top width}.
                        allowOverMax: false,
                        //appendToParent: true, // appends the maxlength indicator badge to the parent of the input rather than to the body.
                        //message: an alternative way to provide the message text, i.e. 'You have typed %charsTyped% chars, %charsRemaining% of %charsTotal% remaining'. %charsTyped%, %charsRemaining% and %charsTotal% will be replaced by the actual values. This overrides the options separator, preText, postText and showMaxLength. Alternatively you may supply a function that the current text and max length and returns the string to be displayed. For example, function(currentText, maxLength) { return '' + Math.ceil(currentText.length / 160) + ' SMS Message(s)';}
                        //utf8: false the input will count using utf8 bytesize/encoding. For example: the '£' character is counted as two characters.
                        //showOnReady: shows the badge as soon as it is added to the page, similar to alwaysShow
                        //twoCharLinebreak: count linebreak as 2 characters to match IE/Chrome textarea validation
                        //customMaxAttribute: String -- allows a custom attribute to display indicator without triggering native maxlength behaviour. Ignored if value greater than a native maxlength attribute. 'overmax' class gets added when exceeded to allow user to implement form validation.
                        //allowOverMax: Will allow the input to be over the customMaxLength. Useful in soft max situations.
                    });
</script>