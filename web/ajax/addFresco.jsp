<%-- 
    Document   : addFresco
    Created on : 10 set 2019, 15:00:36
    Author     : Roberto97
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="request" value="<%=request%>"/> <!-- Request lo chiamo con una scriplets e lo salvo in una variabile perchè serve per diversi metodi java -->

<div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content container">
        <div class="modal-header">
            <h5 class="modal-title" id="catImgChangeTitle">Nuovo prodotto fresco</h5>
            <button type="button" class="close" onclick='closeModal();' aria-label="Close" >
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <form method="POST" action="addProduct" enctype="multipart/form-data">
            <div class="modal-body">
                <div class="row mb-5">
                    <div class="col-md-6">
                        <div style="height: 100%;" id="toDeleteFresc"></div>
                        <img id="InputIMGFresc" src="" alt="Modal_IMG_Fresc" class="inputImg mb-3 invisible"/>
                        <input type='file' name="immagine" onchange="readURL(this, 'InputIMGFresc'); $('#InputIMGFresc').removeClass('invisible'); $('#toDeleteFresc').addClass('invisible');" required/>
                    </div>
                    <div class="col-md-6">
                        <input maxlength="45" type="text" name="nome" class="form-control mb-4" placeholder="Nome" required/>
                        <select class="form-control mb-4" id="categoria" name="categoria" required>
                            <c:forEach var="cat" items="${categorydao.getFreshCategories()}">
                                <option>${cat.nome}</option>
                            </c:forEach>
                        </select>                       
                    </div>
                </div>
                <input pattern="[0-9]+(.|,)+[0-9]{1,2}" type="text" name="costo" class="form-control mt-5" placeholder="Costo" required /><br>
                <textarea style="min-height: 100px;" name="descrizione" class="form-control" placeholder="Descrizione" required></textarea><br>
                <div class="form-group form-inline" id="dynamic_form_freso">                                        
                    <div class="form-group col-lg-4 col-md-12 name">
                        <input type="text" class="form-control" id="variante" name="variante" placeholder="Variante">
                    </div>
                    <div class="form-group col-lg-3 col-md-12 name">
                        <input type="text" class="form-control" id="scelta" name="scelta" placeholder="Scelte">
                    </div>
                    <div class="form-group col-lg-3 col-md-12 name">
                        <input type="text" class="form-control" id="supplement" name="supplement" placeholder="Supplemento">
                    </div>
                    <div class="button-group form-group col-lg-2 col-md-12">
                        <a href="javascript:void(0)" class="btn btn-primary" id="plus_fresco" style="padding: .375rem .75rem;">+</a>
                        <a href="javascript:void(0)" class="btn btn-danger" id="minus_fresco" style="padding: .375rem .75rem;">-</a>
                    </div>
                </div>
                <input class="mb-4" type="checkbox" name="disponibile" checked/> Disponibile<br>
                <input type="hidden" name="fresco" value="true" required />
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick='closeModal();'>Annulla</button>
                <button id="catImgChangeSubmit" type="submit" class="btn btn-primary">Salva</button>
            </div>
        </form>
    </div>
</div>
<script src="js/dynamic-form.js"></script>
<script>
</script>
<script>
    $(document).ready(function () {
        var dynamic_form = $("#dynamic_form_freso").dynamicForm("#dynamic_form_freso", "#plus_fresco", "#minus_fresco", {
            limit: 10,
            formPrefix: "dynamic_form",
            normalizeFullForm: false
        });

        dynamic_form.inject([{p_name: 'Hemant', quantity: '123', remarks: 'testing remark'}, {p_name: 'Harshal', quantity: '123', remarks: 'testing remark'}]);

        $("#dynamic_form_freso #minus5").on('click', function () {
            var initDynamicId = $(this).closest('#dynamic_form_freso').parent().find("[id^='dynamic_form_freso']").length;
            if (initDynamicId === 2) {
                $(this).closest('#dynamic_form_freso').next().find('#minus_fresco').hide();
            }
            $(this).closest('#dynamic_form_freso').remove();
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
// See full documentation here : https://github.com/mimo84/bootstrap-maxlength/

    $('input[maxlength]').maxlength({
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