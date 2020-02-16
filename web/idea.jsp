<%-- 
    Document   : idea
    Created on : 16-giu-2019, 17.12.43
    Author     : Roberto97
--%>

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

        <!-- Per impedire alla maggior parte dei motori di ricerca di indicizzare la pagina -->
        <meta name="robots" content="noindex">
        <!-- Per impedire solo a Google di indicizzare la pagina -->
        <meta name="googlebot" content="noindex">

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
                        <a href="idee.jsp"><i class="fas fa-arrow-left"></i> Torna a tutti le idee</a>
                        <c:choose>
                            <c:when test="${param.id eq 'new'}" >
                                <form method="POST" action="addIdea"  enctype="multipart/form-data">
                                    <button class="btn btn-outline-info mt-4">Aggiungi</button>
                                    <input maxlength="45" style="font-size: 3.3125rem; line-height: 1.15em; height: 80px;" class="form-control mb-4" type="text" name="titolo" value="Titolo" required/>

                                    <h4>Cosa usare</h4>
                                    <div class="form-group form-inline" id="dynamic_form">                                        
                                        <div class="form-group col-lg-5 col-md-12 name">
                                            <input type="text" class="form-control" id="ingrediente" name="ingrediente" placeholder="Cosa">
                                        </div>
                                        <div class="form-group col-lg-5 col-md-12 name">
                                            <input type="text" class="form-control" id="quantity" name="quantity" placeholder="Quanto">
                                        </div>
                                        <div class="button-group form-group col-lg-2 col-md-12">
                                            <a href="javascript:void(0)" class="btn btn-primary" id="plus" style="padding: .375rem .75rem;">+</a>
                                            <a href="javascript:void(0)" class="btn btn-danger" id="minus" style="padding: .375rem .75rem;">-</a>
                                        </div>
                                    </div>     

                                    <textarea id="editor" name="procedimento" required>Scrivi l'idea qui.</textarea>

                                    <div class="row">
                                        <div class="col-md-6 mt-5">
                                            <div class="row mt-2">
                                                <div class="col-md-4">
                                                    <label for="creatore">Autore</label>
                                                    <select style="width: auto;" class="form-control mb-4" name="autore" id="autore" required>
                                                        <c:forEach var="aut" items="${ricettedao.getAllCreators()}">
                                                            <option value="${aut}">${aut}</option>
                                                        </c:forEach>
                                                        <option value="New">Nuovo autore</option>
                                                    </select>
                                                </div>
                                                <c:choose>
                                                    <c:when test="${ricettedao.getAllCreators() eq null}">
                                                        <div class="col-md-8">
                                                            <div id="newCreatorDIV" style="text-align: left;">
                                                                <label for="newCreator">Nuovo autore</label>
                                                                <input class="form-control mb-4" type="text" id="newCreator" name="newCreator" required/>
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="col-md-8">
                                                            <div id="newCreatorDIV" class="invisible" style="text-align: left;">
                                                                <label for="newCreator">Nuovo autore</label>
                                                                <input class="form-control mb-4" type="hidden" id="newCreator" name="newCreator" required/>
                                                            </div>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <label for="categoria">Categoria</label>
                                                    <select style="width: auto;" class="form-control mb-4" name="categoria" id="categoria" required>
                                                        <option value="nostre" 
                                                                <c:if test="${ide.categoria eq true}">
                                                                    selected
                                                                </c:if>
                                                                >
                                                            Nostra
                                                        </option>
                                                        <option value="utenti" 
                                                                <c:if test="${ide.categoria eq false}">
                                                                    selected
                                                                </c:if>
                                                                >
                                                            Utenti
                                                        </option>
                                                    </select>
                                                </div>

                                                <div class="col-md-4">
                                                    <label for="categoria">Prodotti assiociato</label>
                                                    <select class="form-control mb-4" name="selectTag" id="selectProdTag" onchange="addTag(this.value);">
                                                        <c:forEach var="prod" items="${productdao.getAllProducts()}">
                                                            <option value="${prod.id}">${prod.nome}</option>
                                                        </c:forEach>
                                                    </select>

                                                    <select data-role="tagsinput" name="tags" id="tags" multiple>
                                                    </select>
                                                    <input type="hidden" name="tag" id="tag" value="" />
                                                </div>
                                            </div>
                                            <div style="width: fit-content;">
                                                <label for="timeInput">Tempo (minuti)</label>
                                                <input class="form-control mb-4" type="number" id="timeInput" name="timeInput" value="${idea.tempo}" min="1" required>

                                                <label for="difficultInput">Difficoltà</label>
                                                <select class="form-control mb-4" name="difficultInput" id="difficultInput" required>
                                                    <option value="Difficile" 
                                                            <c:if test="${idea.difficolta eq 'Difficile'}">
                                                                selected
                                                            </c:if>>
                                                        Difficile
                                                    </option>
                                                    <option value="Media"
                                                            <c:if test="${idea.difficolta eq 'Media'}">
                                                                selected
                                                            </c:if>>
                                                        Media
                                                    </option>
                                                    <option value="Facile"
                                                            <c:if test="${idea.difficolta eq 'Facile'}">
                                                                selected
                                                            </c:if>>
                                                        Facile
                                                    </option>
                                                </select>
                                                <label for="difficultInput">Visibilità</label>
                                                <select class="form-control mb-4" name="approvata" id="approvata" required>
                                                    <option value="0"
                                                            <c:if test="${idea.approvata eq false}">
                                                                selected
                                                            </c:if>>
                                                        Non Pubblicata
                                                    </option>
                                                    <option value="1"
                                                            <c:if test="${idea.approvata eq true}">
                                                                selected
                                                            </c:if>>
                                                        Pubblicata
                                                    </option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-6 text-center mt-5">
                                            <label for="meta_descrizione">Meta Descrizione</label>
                                            <textarea maxlength="160" class="form-control mb-5" id="meta_descrizione" name="meta_descrizione"></textarea>
                                            <img id="InputIMGBlog" src="img/ico/add.svg" alt="add_Modal_IMG" class="mb-3" style="width: 100%; border-radius: 5%; max-width: 500px;"/>
                                            <input type='file' name="immagine" onchange="readURL(this, 'InputIMGBlog');" required/>
                                        </div>
                                    </div>
                                </form>
                            </c:when>
                            <c:when test="${!param.id.matches('[0-9]+') && !param.id.matches('new')}">
                                <h1>Errore nell'URL. Scegli un altra idea</h1>
                            </c:when>
                            <c:otherwise>
                                <c:set var="idea" value="${ricettedao.getRecipe(param.id)}"/>
                                <c:set var="commenti" value="${ricettedao.getComments(idea.id)}"/>
                                <c:choose>
                                    <c:when test="${idea eq null}">
                                        <h1>NESSUNA IDEA TROVATO CON QUESTO ID</h1>
                                    </c:when>
                                    <c:otherwise>
                                        <form method="POST" action="updateRecipe"  enctype="multipart/form-data">
                                            <div class="mt-4">
                                                <button class="btn btn-outline-info">Aggiorna</button>
                                                <a class="btn btn-outline-warning" target="_blank" rel="noopener" href="/../Bortoleto/idea/${idea.id}/${idea.nome.replace(' ', '-')}">Visualizza</a>
                                            </div>
                                            <input type="hidden" name="id" value="${idea.id}" />
                                            <input maxlength="45" style="font-size: 3.3125rem; line-height: 1.15em; height: 80px;" class="form-control mb-4" type="text" name="titolo" value="${idea.nome}" required/>

                                            <h4>Cosa usare</h4>
                                            <div id="acutalIngredients">
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
                                            </div>
                                            <div class="form-group form-inline" id="dynamic_form">                                                   
                                                <div class="form-group col-lg-5 col-md-12 name">
                                                    <input type="text" class="form-control" id="ingrediente" name="ingrediente" placeholder="Cosa">
                                                </div>
                                                <div class="form-group col-lg-5 col-md-12 name">
                                                    <input type="text" class="form-control" id="quantity" name="quantity" placeholder="Quanto">
                                                </div>
                                                <div class="button-group form-group col-lg-2 col-md-12">
                                                    <a href="javascript:void(0)" class="btn btn-primary" id="plus" style="padding: .375rem .75rem;">+</a>
                                                    <a href="javascript:void(0)" class="btn btn-danger" id="minus" style="padding: .375rem .75rem;">-</a>
                                                </div>
                                            </div>     

                                            <textarea id="editor" name="procedimento" required>${idea.procedimento}</textarea>

                                            <div class="row">
                                                <div class="col-md-6 mt-5">

                                                    <div id="ratingDiv" class="no-padding mb-5 personalized center-small col-lg-12 col-md-12 col-12">
                                                        <c:set var="rate" value="${ricettedao.getRate(idea.id)}"/>
                                                        <label class="text-muted">${ricettedao.getNumberRate(idea.id)} valutazioni (${rate} <i class="far fa-star"></i>)</label><br>
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

                                                    <div class="row mt-2">
                                                        <div class="col-md-4">
                                                            <label for="creatore">Autore</label>
                                                            <select style="width: auto;" class="form-control mb-4" name="autore" id="autore" required>
                                                                <c:forEach var="aut" items="${ricettedao.getAllCreators()}">
                                                                    <option value="${aut}" 
                                                                            <c:if test="${aut eq idea.creatore}">
                                                                                selected
                                                                            </c:if>
                                                                            >
                                                                        ${aut}
                                                                    </option>
                                                                </c:forEach>
                                                                <option value="New">Nuovo autore</option>
                                                            </select>
                                                        </div>
                                                        <div class="col-md-8">
                                                            <div id="newCreatorDIV" class="invisible" style="text-align: left;">
                                                                <label for="newCreator">Nuovo autore</label>
                                                                <input class="form-control mb-4" type="hidden" id="newCreator" name="newCreator" required/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <label for="categoria">Categoria</label>
                                                            <select style="width: auto;" class="form-control mb-4" name="categoria" id="categoria" required>
                                                                <option value="nostre" 
                                                                        <c:if test="${idea.category eq true}">
                                                                            selected
                                                                        </c:if>
                                                                        >
                                                                    Nostra
                                                                </option>
                                                                <option value="utenti" 
                                                                        <c:if test="${idea.category eq false}">
                                                                            selected
                                                                        </c:if>
                                                                        >
                                                                    Utenti
                                                                </option>
                                                            </select>
                                                        </div>

                                                        <div class="col-md-6">
                                                            <label for="categoria">Prodotti assiociato</label>
                                                            <select class="form-control mb-4" name="selectTag" id="selectProdTag" onchange="addTag(this.value);">
                                                                <c:forEach var="prod" items="${productdao.getAllProducts()}">
                                                                    <option value="${prod.id}">${prod.nome}</option>
                                                                </c:forEach>
                                                            </select>

                                                            <select data-role="tagsinput" name="tags" id="tags" multiple>
                                                            </select>
                                                            <input type="hidden" name="tag" id="tag" value="" />
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <label for="views">Visualizzazioni</label>
                                                            <h4 id="views"><i class="far fa-eye mr-2"></i> ${idea.views}</h4>

                                                            <label for="comments">Commenti</label>
                                                            <h4 id="comments"><i class="far fa-comment mr-2"></i> ${commenti.size()} 
                                                                <c:if test="${commenti.size() > 0}">
                                                                    <span onclick="toggleComments(${idea.id});" id="viewComments" style="font-size: 15px; color: #00b6ff; cursor: pointer;">
                                                                        Visualizza
                                                                    </span>
                                                                </c:if>
                                                            </h4>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <label for="timeInput">Tempo (minuti)</label>
                                                            <input class="form-control mb-4" type="number" id="timeInput" name="timeInput" value="${idea.tempo}" min="1" required>

                                                            <label for="difficultInput">Difficoltà</label>
                                                            <select class="form-control mb-4" name="difficultInput" id="difficultInput" required>
                                                                <option value="Difficile" 
                                                                        <c:if test="${idea.difficolta eq 'Difficile'}">
                                                                            selected
                                                                        </c:if>>
                                                                    Difficile
                                                                </option>
                                                                <option value="Media"
                                                                        <c:if test="${idea.difficolta eq 'Media'}">
                                                                            selected
                                                                        </c:if>>
                                                                    Media
                                                                </option>
                                                                <option value="Facile"
                                                                        <c:if test="${idea.difficolta eq 'Facile'}">
                                                                            selected
                                                                        </c:if>>
                                                                    Facile
                                                                </option>
                                                            </select>
                                                            <label for="difficultInput">Visibilità</label>
                                                            <select class="form-control mb-4" name="approvata" id="approvata" required>
                                                                <option value="0"
                                                                        <c:if test="${idea.approvata eq false}">
                                                                            selected
                                                                        </c:if>>
                                                                    Non Pubblicata
                                                                </option>
                                                                <option value="1"
                                                                        <c:if test="${idea.approvata eq true}">
                                                                            selected
                                                                        </c:if>>
                                                                    Pubblicata
                                                                </option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div id="commentiBox">
                                                    </div>
                                                </div>
                                                <div class="col-md-6 text-center mt-5">
                                                    <label for="meta_descrizione">Meta Descrizione</label>
                                                    <textarea maxlength="160" class="form-control mb-5" id="meta_descrizione" name="meta_descrizione">${idea.meta_descrizione}</textarea>
                                                    <img id="InputIMGRecipe" src="${idea.immagine}" alt="${idea.nome}_Modal_IMG" class="mb-3" style="border-radius: 5%; max-width: 500px; max-height: 500px;"/>
                                                    <input type='file' name="immagine" onchange="readURL(this, 'InputIMGRecipe');"/>
                                                    <input type="hidden" name="oldIMG" value="${idea.immagine}" required />
                                                </div>
                                            </div>
                                        </form>
                                        <form method="POST" action="updateRecipe">
                                            <button type="button" onclick="$('#sureToDeleteBlog').removeClass('invisible')" class="btn btn-outline-danger">Elimina</button>
                                            <div class="text-center invisible mt-4" id="sureToDeleteBlog" style="width: fit-content;">
                                                <input type="hidden" name="id" value="${idea.id}" />
                                                <input type="hidden" name="immagine" value="${idea.immagine}" required/>
                                                <p>Sicuro di eliminare l'idea '${idea.nome}'?</p>
                                                <input type="hidden" name="DELETE" value="true"/>
                                                <input type="password" class="form-control" name="password" placeholder="Password" required /><br>
                                                <button id="confirm_delete_${idea.id}" type="submit" class="btn btn-danger">Si</button>
                                                <button onclick="$('#sureToDeleteBlog').addClass('invisible')" id="confirm_delete_${idea.id}_button" type="button" class="btn btn-secondary">Annulla</button>
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
        </div>>

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
        <script src="js/bootstrap-maxlength-min.js"></script>
        <script src="js/tagsinput.js"></script>
        <script>

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
                        url: "ajax/toggleRicetteComments.jsp",
                        data: {type: type, id: id},
                        cache: false,
                        success: function (response) {
                            $('#commentiBox').html(response);
                        },
                        error: function () {
                            alert("Errore toggle Ricette comments");
                        }
                    });
                    $('#viewComments').html('Nascondi');
                } else {
                    type = 2;
                    $('#viewComments').html('Visualizza');
                    $('#commentiBox').html('');
                }

            }

            function removeIng(id, ing) {
                $.ajax({
                    type: "POST",
                    url: "removeRecipeIng",
                    data: {id: id, ing: ing},
                    cache: false,
                    success: function (response) {
                        $('#acutalIngredients').html(response);
                    },
                    error: function () {
                        alert("Errore remove Ricetta ingredients");
                    }
                });
            }
            var added = 0;
            $('#tags').tagsinput({
                /*typeahead: {
                 source: []
                 },*/
                freeInput: true,
                allowDuplicates: false,
                itemValue: 'id', // this will be used to set id of tag
                itemText: 'text' // this will be used to set text of tag                
            });
            $('#ptagadded').html('Tag Aggiunti: ' + added);

            $(document).ready(function () {

                var mytagsinput = $('#tags');
                var o = null;

            <c:if test="${param.id ne null && param.id ne '0' && param.id.matches('[0-9]+')}">
                <c:forEach var="tag" items="${ricettedao.getAllProdsOfIdea(param.id)}">
                    <c:set var="tagText" value="${productdao.getProduct(tag).nome}" />
                if (o === null) {
                    o = "${tag}";
                } else {
                    o += ";${tag}";
                }
                mytagsinput.tagsinput('add', {id: '${tag}', text: '${tagText}'});
                $('#tag').val(o);
                </c:forEach>
            </c:if>
            });

            function addTag(element) {
                event.preventDefault();
                added++;
                $('#ptagadded').html('Tag Aggiunti: ' + added);

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
        </script>
        <script src="js/dynamic-form.js"></script>
        <script>
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

                //appendToParent: true, // appends the maxlength indicator badge to the parent of the input rather than to the body.
                //message: an alternative way to provide the message text, i.e. 'You have typed %charsTyped% chars, %charsRemaining% of %charsTotal% remaining'. %charsTyped%, %charsRemaining% and %charsTotal% will be replaced by the actual values. This overrides the options separator, preText, postText and showMaxLength. Alternatively you may supply a function that the current text and max length and returns the string to be displayed. For example, function(currentText, maxLength) { return '' + Math.ceil(currentText.length / 160) + ' SMS Message(s)';}
                //utf8: true //the input will count using utf8 bytesize/encoding. For example: the '£' character is counted as two characters.
                //showOnReady: shows the badge as soon as it is added to the page, similar to alwaysShow
                //twoCharLinebreak: count linebreak as 2 characters to match IE/Chrome textarea validation
                //customMaxAttribute: String -- allows a custom attribute to display indicator without triggering native maxlength behaviour. Ignored if value greater than a native maxlength attribute. 'overmax' class gets added when exceeded to allow user to implement form validation.
                //allowOverMax: Will allow the input to be over the customMaxLength. Useful in soft max situations.
            });
        </script>
    </body>
</html>
