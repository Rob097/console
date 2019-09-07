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
            <input type="hidden" name="DELETE" value="false"/>
            <img id="InputIMGUpdateCat" src="${categoria.immagine}" alt="${categoria.nome}_Modal_IMG" class="inputImg mb-3"/>
            <input type='file' name="immagine" onchange="readURL(this, 'InputIMGUpdateCat');"/> 

            <input maxlength="45" type="text" name="nome" value="${categoria.nome}" class="form-control mt-4 mb-4" required/>
            <textarea style="min-height: 100px;" name="descrizione" class="form-control mb-4" required>${categoria.descrizione}</textarea>
            <input type="hidden" name="fresco" value="${categoria.freschi}" required/>
            <input type="hidden" name="oldImg" value="${categoria.immagine}" required/>
            <input type="hidden" name="id" value="${categoria.id}" required>
            <button type="button" class="btn btn-secondary" onclick='closeModal();'>Annulla</button>
            <button id="catImgChangeSubmit" type="submit" class="btn btn-primary">Salva</button>
        </form>
        <div class="text-center">
            <button onclick="$('#sureToDeleteCat').removeClass('invisible')" id="delete_${categoria.id}" type="button" class="btn btn-danger">Elimina</button>
        </div>
        <div class="text-center invisible mt-4" id="sureToDeleteCat">
            <p>Sicuro di eliminare la categoria '${categoria.nome}'?</p>
            <form method="POST" action="catImgChange">
                <input type="hidden" name="id" value="${categoria.id}" required/>
                <input type="hidden" name="immagine" value="${categoria.immagine}" />
                <input type="password" class="form-control" name="password" placeholder="Password" required /><br>
                <input type="hidden" name="DELETE" value="true"/>
                <button id="confirm_delete_${categoria.id}" type="submit" class="btn btn-danger">Si</button>
                <button onclick="$('#sureToDeleteCat').addClass('invisible')" id="confirm_delete_${categoria.id}" type="button" class="btn btn-secondary">Annulla</button>
            </form>
        </div>
    </div>
</div>
<script>
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

        //appendToParent: true, // appends the maxlength indicator badge to the parent of the input rather than to the body.
        //message: an alternative way to provide the message text, i.e. 'You have typed %charsTyped% chars, %charsRemaining% of %charsTotal% remaining'. %charsTyped%, %charsRemaining% and %charsTotal% will be replaced by the actual values. This overrides the options separator, preText, postText and showMaxLength. Alternatively you may supply a function that the current text and max length and returns the string to be displayed. For example, function(currentText, maxLength) { return '' + Math.ceil(currentText.length / 160) + ' SMS Message(s)';}
        //utf8: false //the input will count using utf8 bytesize/encoding. For example: the '£' character is counted as two characters.
                //showOnReady: shows the badge as soon as it is added to the page, similar to alwaysShow
                //twoCharLinebreak: count linebreak as 2 characters to match IE/Chrome textarea validation
                //customMaxAttribute: String -- allows a custom attribute to display indicator without triggering native maxlength behaviour. Ignored if value greater than a native maxlength attribute. 'overmax' class gets added when exceeded to allow user to implement form validation.
                //allowOverMax: Will allow the input to be over the customMaxLength. Useful in soft max situations.
    });
</script>