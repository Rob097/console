<%-- 
    Document   : articolo
    Created on : 14-giu-2019, 19.36.47
    Author     : Roberto97
--%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="request" value="<%=request%>"/> <!-- Request lo chiamo con una scriplets e lo salvo in una variabile perchè serve per diversi metodi java -->
<c:set var="response" value="<%=response%>"/>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <link rel="icon" type="image/png" href="https://lh3.googleusercontent.com/1nJwqw8n93uSSVkiOcuosGxA84pLvNAH5WDakvcRHohk2ccrL0SmxBlHB87WOxZXcWkD2ToK0YmNzspklIqHjZI8XQcVFfiDhpawN03k_rwm2pARMbFxIFSQiI3fvlC529-UVTMNbg=w2400">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <title>
            Console
        </title>
        <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
        <!--     Fonts and icons     -->
        <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!-- CSS Files -->
        <link href="assets/css/material-dashboard.css?v=2.1.1" rel="stylesheet" />
        <link href="css/styles.css" rel="stylesheet" />
        <link href="css/tagsinput.css" rel="stylesheet" />
        <!-- include summernote css/js -->
        <link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css">

        <style>
            .btn{
                cursor: pointer;
                display: initial;
                padding: 0.46875rem 1rem !important;
            }
            .btn-group, .btn-group-vertical {
                position: relative;
                display: inline-block;
                vertical-align: middle;
            }
            .comments-area {
                background: #fafaff;
                border: 1px solid #eee;
                padding: 50px 20px;
                margin-top: 50px;
            }
            h5.personalized {
                font-family: "Playfair Display", serif;
                color: #000;
                line-height: 1.2em;
                margin-bottom: 0;
                margin-top: 0;
                font-weight: normal;
            }
            .comments-area .date {
                font-size: 13px;
                color: #cccccc;
            }
            .comments-area .comment {
                color: #777777;
            }  
        </style>

    </head>

    <body class="">
        <a class="rightGold" href="#topPage" id="myBtn45" title="Torna in cima"><i class="fas fa-arrow-up"></i></a>
        <div class="wrapper ">
            <div class="sidebar" data-color="purple" data-background-color="white" data-image="img/ico/sidebar-1.jpg">
                <!-- Load with javascript from /ajax -->
            </div>
            <div class="main-panel">
                <div id="navbar">

                </div>
                <!-- End Navbar -->
                <div class="content">
                    <div class="container-fluid">
                        <a href="articoli.jsp"><i class="fas fa-arrow-left"></i> Torna a tutti gli articoli</a>
                        <c:choose>
                            <c:when test="${param.id eq 'new'}" >
                                <form method="POST" action="addBlog"  enctype="multipart/form-data">
                                    <button class="btn btn-outline-info mt-4">Aggiungi</button>
                                    <input style="font-size: 3.3125rem; line-height: 1.15em; height: 80px;" class="form-control mb-4" type="text" name="titolo" value="Titolo" maxlength="45" required/>
                                    <textarea id="editor" name="testo" required>Scrivi l'articolo qui.</textarea>

                                    <div class="row">
                                        <div class="col-md-6 mt-5">

                                            <div class=" mb-5">     
                                                <h3>Tag</h3>

                                                <select class="form-control mb-4" name="selectTag" id="selectProdTag" onchange="addTag(this.value);">
                                                    <option style="color: #ad9966;" disabled="true">Categorie</option>
                                                    <c:forEach var="cat" items="${categorydao.getAllCategories()}">
                                                        <option value="${cat.nome}">${cat.nome}</option>
                                                    </c:forEach>
                                                    <option style="color: #ad9966;" disabled="true">Prodotti</option>
                                                    <c:forEach var="prod" items="${productdao.getAllProducts()}">
                                                        <option value="${prod.nome}">${prod.nome}</option>
                                                    </c:forEach>
                                                </select>

                                                <select data-role="tagsinput" name="tags" id="tags" multiple>
                                                </select>
                                                <input type="hidden" name="tag" id="tag" value="" />
                                            </div>

                                            <div class="row">
                                                <div class="col-md-4">
                                                    <label for="categoria">Categoria</label>
                                                    <select style="width: auto;" class="form-control mb-4" name="categoria" id="categoria" required>
                                                        <c:forEach var="cat" items="${catblogdao.getAllCatBlog()}">
                                                            <option value="${cat.nome}">${cat.nome}</option>
                                                        </c:forEach>
                                                        <option value="New">Nuova categoria</option>
                                                    </select>
                                                </div>
                                                <%String c = "ATTENZIONE!\nCreando una nuova categoria, il primo articolo inserito risulterà senza immagine.\nConsiglio:\n1) Creare l'articolo \n2)Modificare l'articolo reinserendo l'immagine.";%>
                                                <c:choose>                                                    
                                                    <c:when test="${catblogdao.getAllCatBlog() eq null}">
                                                        <div class="col-md-8">
                                                            <div id="newCategoryDIV" style="text-align: left;">
                                                                <label for="newCategory">Nuova categoria</label><i class="fas fa-info-circle ml-2" data-toggle="tooltip" title="<%=c%>"></i>
                                                                <input maxlength="45" class="form-control mb-4" type="text" id="newCategory" name="newCategory" required/>
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="col-md-8">
                                                            <div id="newCategoryDIV" class="invisible" style="text-align: left;">
                                                                <label for="newCategory">Nuova categoria</label><i class="fas fa-info-circle ml-2" data-toggle="tooltip" title="<%=c%>"></i>
                                                                <input maxlength="45" class="form-control mb-4" type="hidden" id="newCategory" name="newCategory" required/>
                                                            </div>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>

                                            <div class="row mt-2">
                                                <div class="col-md-4">
                                                    <label for="creatore">Autore</label>
                                                    <select style="width: auto;" class="form-control mb-4" name="autore" id="autore" required>
                                                        <c:forEach var="aut" items="${blogdao.getAllCreators()}">
                                                            <option value="${aut}">${aut}</option>
                                                        </c:forEach>
                                                        <option value="New">Nuovo autore</option>
                                                    </select>
                                                </div>
                                                <c:choose>
                                                    <c:when test="${blogdao.getAllCreators() eq null}">
                                                        <div class="col-md-8">
                                                            <div id="newCreatorDIV" style="text-align: left;">
                                                                <label for="newCreator">Nuovo autore</label>
                                                                <input maxlength="45" class="form-control mb-4" type="text" id="newCreator" name="newCreator" required/>
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="col-md-8">
                                                            <div id="newCreatorDIV" class="invisible" style="text-align: left;">
                                                                <label for="newCreator">Nuovo autore</label>
                                                                <input maxlength="45" class="form-control mb-4" type="hidden" id="newCreator" name="newCreator" required/>
                                                            </div>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <label for="pubblicato">Visibilità</label>
                                            <select class="form-control mb-4" name="pubblicato" id="pubblicato" required>
                                                <option value="0"
                                                        <c:if test="${articolo.pubblicato eq false}">
                                                            selected
                                                        </c:if>>
                                                    Non Pubblicato
                                                </option>
                                                <option value="1"
                                                        <c:if test="${articolo.pubblicato eq true}">
                                                            selected
                                                        </c:if>>
                                                    Pubblicato
                                                </option>
                                            </select>
                                        </div>
                                        <div class="col-md-6 text-center mt-5">
                                            <img id="InputIMGBlog" src="img/ico/add.svg" alt="add_Modal_IMG" class="mb-3" style="width: 100%; border-radius: 5%; max-width: 500px;"/>
                                            <input type='file' accept="image/x-png, image/jpg, image/jpeg"  name="immagine" onchange="readURL(this, 'InputIMGBlog');"/>
                                        </div>
                                    </div>
                                </form>
                            </c:when>
                            <c:when test="${!param.id.matches('[0-9]+') && !param.id.matches('new')}">
                                <h1>Errore nell'URL. Scegli un altro articolo</h1>
                            </c:when>
                            <c:otherwise>
                                <c:set var="articolo" value="${blogdao.getBlogById(param.id)}"/>
                                <c:set var="commenti" value="${commblogdao.getAllCommOfBlog(articolo.id)}"/>
                                <c:choose>
                                    <c:when test="${articolo eq null}">
                                        <h1>NESSUN ARTICOLO TROVATO CON QUESTO ID</h1>
                                    </c:when>
                                    <c:otherwise>
                                        <form method="POST" action="updateBlog"  enctype="multipart/form-data" multiple>
                                            <div class="mt-4">
                                                <button class="btn btn-outline-info">Aggiorna</button>
                                                <a class="btn btn-outline-warning" target="_blank" rel="noopener" href="../MacAPP/articolo.jsp?id=${articolo.id}">Visualizza</a>
                                            </div>
                                            <input type="hidden" name="id" value="${articolo.id}" />
                                            <input maxlength="45" style="font-size: 3.3125rem; line-height: 1.15em; height: 80px;" class="form-control mb-4" type="text" name="titolo" value="${articolo.nome}" required/>

                                            <textarea id="editor" name="testo" required>${articolo.testo}</textarea>
                                            <div class="row">
                                                <div class="col-md-6 mt-5">

                                                    <div id="ratingDiv" class="no-padding mb-5 personalized center-small col-lg-12 col-md-12 col-12">
                                                        <c:set var="rate" value="${blogdao.getRate(articolo.id)}"/>
                                                        <label class="text-muted">${blogdao.getNumberRate(articolo.id)} valutazioni (${rate} <i class="far fa-star"></i>)</label><br>
                                                        <fieldset class="rating text-center" style="display: initial;">
                                                            <input type="radio" id="star5" name="rating" value="5" 
                                                                   <c:if test="${rate >= 5}">
                                                                       checked
                                                                   </c:if>/>
                                                            <label class = "full" for="star5" title="5 stelle"></label>
                                                            <input type="radio" id="star4half" name="rating" value="4.5" 
                                                                   <c:if test="${rate >= 4.5 && rate < 5}">
                                                                       checked
                                                                   </c:if>/>
                                                            <label class="half" for="star4half" title="4.5 stelle"></label>
                                                            <input type="radio" id="star4" name="rating" value="4" 
                                                                   <c:if test="${rate >= 4 && rate < 4.5}">
                                                                       checked
                                                                   </c:if>/>
                                                            <label class = "full" for="star4" title="4 stelle"></label>
                                                            <input type="radio" id="star3half" name="rating" value="3.5" 
                                                                   <c:if test="${rate >= 3.5 && rate < 4}">
                                                                       checked
                                                                   </c:if>/>
                                                            <label class="half" for="star3half" title="3.5 stelle"></label>
                                                            <input type="radio" id="star3" name="rating" value="3" 
                                                                   <c:if test="${rate >= 3 && rate < 3.5}">
                                                                       checked
                                                                   </c:if>/>
                                                            <label class = "full" for="star3" title="3 stelle"></label>
                                                            <input type="radio" id="star2half" name="rating" value="2.5"
                                                                   <c:if test="${rate >= 2.5 && rate < 3}">
                                                                       checked
                                                                   </c:if>/>
                                                            <label class="half" for="star2half" title="2.5 stelle"></label>
                                                            <input type="radio" id="star2" name="rating" value="2" 
                                                                   <c:if test="${rate >= 2 && rate < 2.5}">
                                                                       checked
                                                                   </c:if>/>
                                                            <label class = "full" for="star2" title="2 stelle"></label>
                                                            <input type="radio" id="star1half" name="rating" value="1.5" 
                                                                   <c:if test="${rate >= 1.5 && rate < 2}">
                                                                       checked
                                                                   </c:if>/>
                                                            <label class="half" for="star1half" title="1.5 stelle"></label>
                                                            <input type="radio" id="star1" name="rating" value="1" 
                                                                   <c:if test="${rate >= 1 && rate < 1.5}">
                                                                       checked
                                                                   </c:if>/>
                                                            <label class = "full" for="star1" title="1 stella"></label>
                                                            <input type="radio" id="starhalf" name="rating" value="0.5" 
                                                                   <c:if test="${rate >= 0.5 && rate < 1}">
                                                                       checked
                                                                   </c:if>/>
                                                            <label class="half" for="starhalf" title="0.5 stelle"></label>
                                                        </fieldset><br>                                
                                                    </div>

                                                    <div class=" mb-5">     
                                                        <h3>Tag</h3>

                                                        <select class="form-control mb-4" name="selectTag" id="selectProdTag" onchange="addTag(this.value);">
                                                            <option style="color: #ad9966;" disabled="true">Categorie</option>
                                                            <c:forEach var="cat" items="${categorydao.getAllCategories()}">
                                                                <option value="${cat.nome}">${cat.nome}</option>
                                                            </c:forEach>
                                                            <option style="color: #ad9966;" disabled="true">Prodotti</option>
                                                            <c:forEach var="prod" items="${productdao.getAllProducts()}">
                                                                <option value="${prod.nome}">${prod.nome}</option>
                                                            </c:forEach>
                                                        </select>

                                                        <select data-role="tagsinput" name="tags" id="tags" multiple>
                                                        </select>
                                                        <input type="hidden" name="tag" id="tag" value="" />
                                                    </div>

                                                    <div class="row">
                                                        <div class="col-md-4">
                                                            <label for="categoria">Categoria</label>
                                                            <%String c1 = "ATTENZIONE!\nCreando una nuova categoria, il primo articolo inserito risulterà senza immagine.\nConsiglio:\n1) Creare l'articolo \n2)Modificare l'articolo reinserendo l'immagine.";%>
                                                            <select style="width: auto;" class="form-control mb-4" name="categoria" id="categoria" required>
                                                                <c:forEach var="cat" items="${catblogdao.getAllCatBlog()}">
                                                                    <option value="${cat.nome}" <c:if test="${cat.nome eq articolo.categoria}">selected</c:if>>${cat.nome}</option>
                                                                </c:forEach>
                                                                <option value="New">Nuova categoria</option>
                                                            </select>
                                                            <input type="hidden" name="OLDCategory" value="${articolo.categoria}"/>
                                                        </div>
                                                        <div class="col-md-8">
                                                            <div id="newCategoryDIV" class="invisible" style="text-align: left;">
                                                                <label for="newCategory">Nuova categoria</label><i class="fas fa-info-circle ml-2" data-toggle="tooltip" title="<%=c1%>"></i>
                                                                <input maxlength="45" class="form-control mb-4" type="hidden" id="newCategory" name="newCategory" required/>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="row mt-2">
                                                        <div class="col-md-4">
                                                            <label for="creatore">Autore</label>
                                                            <select style="width: auto;" class="form-control mb-4" name="autore" id="autore" required>
                                                                <c:forEach var="aut" items="${blogdao.getAllCreators()}">
                                                                    <option value="${aut}" <c:if test="${aut eq articolo.creatore}">selected</c:if>>${aut}</option>
                                                                </c:forEach>
                                                                <option value="New">Nuovo autore</option>
                                                            </select>
                                                        </div>
                                                        <div class="col-md-8">
                                                            <div id="newCreatorDIV" class="invisible" style="text-align: left;">
                                                                <label for="newCreator">Nuovo autore</label>
                                                                <input maxlength="45" class="form-control mb-4" type="hidden" id="newCreator" name="newCreator" required/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <label for="pubblicato">Visibilità</label>
                                                    <select class="form-control mb-4" name="pubblicato" id="pubblicato" required>
                                                        <option value="0"
                                                                <c:if test="${articolo.pubblicato eq false}">
                                                                    selected
                                                                </c:if>>
                                                            Non Pubblicato
                                                        </option>
                                                        <option value="1"
                                                                <c:if test="${articolo.pubblicato eq true}">
                                                                    selected
                                                                </c:if>>
                                                            Pubblicato
                                                        </option>
                                                    </select>

                                                    <label for="views">Visualizzazioni</label>
                                                    <h4 id="views"><i class="far fa-eye mr-2"></i> ${articolo.views}</h4>

                                                    <label for="comments">Commenti</label>
                                                    <h4 id="comments"><i class="far fa-comment mr-2"></i> ${commenti.size()} 
                                                        <c:if test="${commenti.size() > 0}">
                                                            <span onclick="toggleComments(${articolo.id});" id="viewComments" style="font-size: 15px; color: #00b6ff; cursor: pointer;">Visualizza</span></h4>
                                                        </c:if>
                                                    <div id="commentiBox">
                                                    </div>
                                                </div>
                                                <div class="col-md-6 text-center mt-5">
                                                    <img id="InputIMGBlog" src="${articolo.immagine}" alt="${articolo.nome}_Modal_IMG" class="mb-3" style="width: 100%; border-radius: 5%; max-width: 500px;"/>
                                                    <input type='file' name="immagine" onchange="readURL(this, 'InputIMGBlog');" value="${articolo.immagine}"/>
                                                    <input type="hidden" name="oldIMG" value="${articolo.immagine}" required />
                                                </div>
                                            </div>
                                        </form>
                                        <form method="POST" action="updateBlog">
                                            <button type="button" onclick="$('#sureToDeleteBlog').removeClass('invisible')" class="btn btn-outline-danger">Elimina</button>
                                            <div class="text-center invisible mt-4" id="sureToDeleteBlog" style="width: fit-content;">
                                                <input type="hidden" name="id" value="${articolo.id}" />
                                                <input type="hidden" name="immagine" value="${articolo.immagine}" required/>
                                                <input type="hidden" name="categoria" value="${articolo.categoria}" />
                                                <p>Sicuro di eliminare l'articolo '${articolo.nome}'?</p>
                                                <input type="hidden" name="DELETE" value="true"/>
                                                <input type="password" class="form-control" name="password" placeholder="Password" required /><br>
                                                <button id="confirm_delete_${categoria.id}" type="submit" class="btn btn-danger">Si</button>
                                                <button onclick="$('#sureToDeleteBlog').addClass('invisible')" id="confirm_delete_${categoria.id}_button" type="button" class="btn btn-secondary">Annulla</button>
                                            </div>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <footer class="footer">
                </footer>
            </div>
        </div>

        <!--   Core JS Files   -->
        <script src="assets/js/core/jquery.min.js"></script>
        <script src="assets/js/core/popper.min.js"></script>
        <script src="assets/js/core/bootstrap-material-design.min.js"></script>
        <script src="assets/js/plugins/perfect-scrollbar.jquery.min.js"></script>        
        <script src="assets/js/plugins/bootstrap-notify.js"></script>
        <!-- Chartist JS -->
        <script src="assets/js/plugins/chartist.min.js"></script>
        <script src="assets/js/material-dashboard.js?v=2.1.1" type="text/javascript"></script>
        <script defer src="https://use.fontawesome.com/releases/v5.7.2/js/all.js" integrity="sha384-0pzryjIRos8mFBWMzSSZApWtPl/5++eIfzYmTgBBmXYdhvxPc+XcFEk+zJwDgWbP" crossorigin="anonymous"></script>

        <script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
        <script src="https://unpkg.com/gijgo@1.9.13/js/messages/messages.it-it.js" type="text/javascript"></script>
        <script src="js/tagsinput.js"></script>
        <script src="js/bootstrap-maxlength.js"></script>
        <script>
        </script>
        <script type="text/javascript">
            $(document).ready(function () {
            $("#editor").editor({
            height: 500,
            locale: 'it-it'
            });
            });
        </script>

        <script type="text/javascript">
            $(document).ready(function () {
            $("#editor").editor({
            height: 400
            });
            $('[role="body"]').css("background-color", "white");
            });
            $("#categoria").change(function () {
            var selected = $(this).children("option:selected").val();
            if (selected === 'New') {
            $('#newCategoryDIV').removeClass('invisible');
            $('#newCategory').prop("type", "text");
            } else {
            $('#newCategoryDIV').addClass('invisible');
            $('#newCategory').prop("type", "hidden");
            }
            });
            $("#autore").change(function () {
            var selected = $(this).children("option:selected").val();
            if (selected === 'New') {
            $('#newCreatorDIV').removeClass('invisible');
            $('#newCreator').prop("type", "text");
            } else {
            $('#newCreatorDIV').addClass('invisible');
            $('#newCreator').prop("type", "hidden");
            }
            });
            function toggleComments(id) {
            var type;
            if ($('#viewComments').html() === 'Visualizza') {
            type = 1;
            $.ajax({
            type: "POST",
            url: "ajax/toggleBlogComments.jsp",
            data: {type: type, id: id},
            cache: false,
            success: function (response) {
            $('#commentiBox').html(response);
            },
            error: function () {
            alert("Errore toggle blog comments");
            }
            });
            $('#viewComments').html('Nascondi');
            } else {
            type = 2;
            $('#viewComments').html('Visualizza');
            $('#commentiBox').html('');
            }

            }

            $('#tags').tagsinput({
            allowDuplicates: false,
            itemValue: 'id', // this will be used to set id of tag
            itemText: 'text', // this will be used to set text of tag
            freeInput: true
            });

            $(document).ready(function () {

            var mytagsinput = $('#tags');
            var o = null;

            <c:if test="${param.id ne null && param.id ne '0' && param.id.matches('[0-9]+')}">
                <c:forEach var="tag" items="${blogdao.getAllTagsOfBlog(param.id)}">
                    <c:set var="tagText" value="${blogdao.getTagName(tag)}" />
                    if (o === null) {
                    o = "${tagText}";
                    } else {
                    o += ";${tagText}";
                    }
                    //console.log("o: " + o + " tag: ${tagText}");
                    //add my tags object
                    mytagsinput.tagsinput('add', {id: '${tagText}', text: '${tagText}'});
                    $('#tag').val(o);
                </c:forEach>
            </c:if>
            });
            function addTag(element) {
            event.preventDefault();
            var val = element;
            var mytagsinput = $('#tags');
            //add my tags object
            mytagsinput.tagsinput('add', {id: val, text: val});
            var inp = '';
            inp = $('#tag').val();
            if (inp === '') {
            $('#tag').val(val);
            } else {
            inp += ";" + val;
            $('#tag').val(inp);
            }
            }

            $('#tags').on('itemRemoved', function (event) {
            var t = $('#tag').val().split(";");
            var n = "";
            for (var i = 0; i < t.length; i++) {
            console.log(t[i]);
            if (t[i] === "" + event.item.id) {
            } else {
            if (n === "") {
            n += t[i];
            } else {
            n += ";" + t[i];
            }
            }
            }

            $('#tag').val(n);
            });
        </script>
        <script>
            $(document).ready(function () {
            $().ready(function () {
            $sidebar = $('.sidebar');
            $sidebar_img_container = $sidebar.find('.sidebar-background');
            $full_page = $('.full-page');
            $sidebar_responsive = $('body > .navbar-collapse');
            window_width = $(window).width();
            fixed_plugin_open = $('.sidebar .sidebar-wrapper .nav li.active a p').html();
            $('.switch-sidebar-image input').change(function () {
            $full_page_background = $('.full-page-background');
            $input = $(this);
            if ($input.is(':checked')) {
            if ($sidebar_img_container.length != 0) {
            $sidebar_img_container.fadeIn('fast');
            $sidebar.attr('data-image', '#');
            }

            if ($full_page_background.length != 0) {
            $full_page_background.fadeIn('fast');
            $full_page.attr('data-image', '#');
            }

            background_image = true;
            } else {
            if ($sidebar_img_container.length != 0) {
            $sidebar.removeAttr('data-image');
            $sidebar_img_container.fadeOut('fast');
            }

            if ($full_page_background.length != 0) {
            $full_page.removeAttr('data-image', '#');
            $full_page_background.fadeOut('fast');
            }

            background_image = false;
            }
            });
            $('.switch-sidebar-mini input').change(function () {
            $body = $('body');
            $input = $(this);
            if (md.misc.sidebar_mini_active == true) {
            $('body').removeClass('sidebar-mini');
            md.misc.sidebar_mini_active = false;
            $('.sidebar .sidebar-wrapper, .main-panel').perfectScrollbar();
            } else {

            $('.sidebar .sidebar-wrapper, .main-panel').perfectScrollbar('destroy');
            setTimeout(function () {
            $('body').addClass('sidebar-mini');
            md.misc.sidebar_mini_active = true;
            }, 300);
            }

            // we simulate the window Resize so the charts will get updated in realtime.
            var simulateWindowResize = setInterval(function () {
            window.dispatchEvent(new Event('resize'));
            }, 180);
            // we stop the simulation of Window Resize after the animations are completed
            setTimeout(function () {
            clearInterval(simulateWindowResize);
            }, 1000);
            });
            });
            });
        </script>
        <script>
            /* Sidebar */
            $(function () {
            $(".sidebar").load("ajax/sideBar.jsp?page=<c:out value='${pageContext.request.getRequestURI().replace("/console/", "").replace(".jsp", "").toLowerCase()}' />");
            $("#navbar").load("ajax/navbar.jsp?page=<c:out value='${StringUtils.capitalize(pageContext.request.getRequestURI().replace("/console/", "").replace(".jsp", "").toLowerCase())}' />");
            $("footer").load("ajax/footer.jsp");
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

            //appendToParent: true, // appends the maxlength indicator badge to the parent of the input rather than to the body.
            //message: an alternative way to provide the message text, i.e. 'You have typed %charsTyped% chars, %charsRemaining% of %charsTotal% remaining'. %charsTyped%, %charsRemaining% and %charsTotal% will be replaced by the actual values. This overrides the options separator, preText, postText and showMaxLength. Alternatively you may supply a function that the current text and max length and returns the string to be displayed. For example, function(currentText, maxLength) { return '' + Math.ceil(currentText.length / 160) + ' SMS Message(s)';}
            //utf8: true //the input will count using utf8 bytesize/encoding. For example: the '£' character is counted as two characters.
            //showOnReady: shows the badge as soon as it is added to the page, similar to alwaysShow
            //twoCharLinebreak: count linebreak as 2 characters to match IE/Chrome textarea validation
            //customMaxAttribute: String -- allows a custom attribute to display indicator without triggering native maxlength behaviour. Ignored if value greater than a native maxlength attribute. 'overmax' class gets added when exceeded to allow user to implement form validation.
            //allowOverMax: Will allow the input to be over the customMaxLength. Useful in soft max situations.
            });
            <c:if test="${response.getHeader('NOTIFICA') ne null}">
                $.notify({
                // options
                message: "${response.getHeader('NOTIFICA')}"
                }, {
                // settings
                element: 'body',
                type: "warning",
                allow_dismiss: true,
                placement: {
                from: "top",
                align: "center"
                },
                animate: {
                enter: 'animated fadeInDown',
                exit: 'animated fadeOutUp'
                },
                icon_type: 'class',
                template: '<div class="col-xs-11 col-sm-3 alert alert-{0} alert-with-icon" data-notify="container" role="alert">' +
                '<i class="material-icons" data-notify="icon">warning</i>' +
                '<button type="button" class="close" data-dismiss="alert" aria-label="Close">' +
                '<i class="material-icons">close</i>' +
                '</button>' +
                '<span data-notify="message">{2}</span>' +
                '</div>'
                });
            </c:if>
        </script>
    </body>
</html>
