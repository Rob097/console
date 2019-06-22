<%-- 
    Document   : articolo
    Created on : 14-giu-2019, 19.36.47
    Author     : Roberto97
--%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="request" value="<%=request%>"/> <!-- Request lo chiamo con una scriplets e lo salvo in una variabile perchè serve per diversi metodi java -->

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <link rel="icon" type="image/png" href="https://lh3.googleusercontent.com/1nJwqw8n93uSSVkiOcuosGxA84pLvNAH5WDakvcRHohk2ccrL0SmxBlHB87WOxZXcWkD2ToK0YmNzspklIqHjZI8XQcVFfiDhpawN03k_rwm2pARMbFxIFSQiI3fvlC529-UVTMNbg=w2400">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <title>
            Dashboard
        </title>
        <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
        <!--     Fonts and icons     -->
        <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!-- CSS Files -->
        <link href="assets/css/material-dashboard.css?v=2.1.1" rel="stylesheet" />
        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link href="css/styles.css" rel="stylesheet" />
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
            <div class="sidebar" data-color="purple" data-background-color="white" data-image="assets/img/sidebar-1.jpg">
                <!-- Load with javascript from /ajax -->
            </div>
            <div class="main-panel">
                <!-- Navbar -->
                <nav class="navbar navbar-expand-lg navbar-transparent navbar-absolute fixed-top ">
                    <div class="container-fluid">
                        <div class="navbar-wrapper">
                            <a id="topPage" class="navbar-brand" href="#pablo">${StringUtils.capitalize(pageContext.request.getRequestURI().replace("/console/", "").replace(".jsp", "").toLowerCase())}</a>
                        </div>
                        <button class="navbar-toggler" type="button" data-toggle="collapse" aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="navbar-toggler-icon icon-bar"></span>
                            <span class="navbar-toggler-icon icon-bar"></span>
                            <span class="navbar-toggler-icon icon-bar"></span>
                        </button>
                        <div class="collapse navbar-collapse justify-content-end">
                            <form class="navbar-form">
                                <div class="input-group no-border">
                                    <input type="text" value="" class="form-control" placeholder="Search...">
                                    <button type="submit" class="btn btn-white btn-round btn-just-icon">
                                        <i class="material-icons">search</i>
                                        <div class="ripple-container"></div>
                                    </button>
                                </div>
                            </form>
                            <ul class="navbar-nav">
                                <li class="nav-item">
                                    <a class="nav-link" href="#pablo">
                                        <i class="material-icons">dashboard</i>
                                        <p class="d-lg-none d-md-block">
                                            Stats
                                        </p>
                                    </a>
                                </li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link" href="http://example.com" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <i class="material-icons">notifications</i>
                                        <span class="notification">5</span>
                                        <p class="d-lg-none d-md-block">
                                            Some Actions
                                        </p>
                                    </a>
                                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
                                        <a class="dropdown-item" href="#">Mike John responded to your email</a>
                                        <a class="dropdown-item" href="#">You have 5 new tasks</a>
                                        <a class="dropdown-item" href="#">You're now friend with Andrew</a>
                                        <a class="dropdown-item" href="#">Another Notification</a>
                                        <a class="dropdown-item" href="#">Another One</a>
                                    </div>
                                </li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link" href="#pablo" id="navbarDropdownProfile" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <i class="material-icons">person</i>
                                        <p class="d-lg-none d-md-block">
                                            Account
                                        </p>
                                    </a>
                                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownProfile">
                                        <a class="dropdown-item" href="#">Profilo</a>
                                        <a class="dropdown-item" href="#">Impostazioni</a>
                                        <div class="dropdown-divider"></div>
                                        <a class="dropdown-item" href="logout">Log out</a>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
                <!-- End Navbar -->
                <div class="content">
                    <div class="container-fluid">
                        <a href="articoli.jsp"><i class="fas fa-arrow-left"></i> Torna a tutti gli articoli</a>
                        <c:choose>
                            <c:when test="${param.id eq 'new'}" >
                                <form method="POST" action="addBlog"  enctype="multipart/form-data">
                                    <button class="btn btn-outline-info mt-4">Aggiungi</button>
                                    <input style="font-size: 3.3125rem; line-height: 1.15em; height: 80px;" class="form-control mb-4" type="text" name="titolo" value="Titolo" required/>
                                    <textarea id="editor" name="testo" required>Scrivi l'articolo qui.</textarea>

                                    <div class="row">
                                        <div class="col-md-6 mt-5">
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
                                                <%String c = "ATTENZIONE!\nCreando una nuova categoria, il primo articolo inserito risulterà senza immagine.\nConsiglio:\n1) Creare un articolo vuoto per creare la nuova categoria.\n2)creare il vero articolo nella catgoria scelta\n3)eliminare l'articolo vuoto.";%>
                                                <c:choose>                                                    
                                                    <c:when test="${catblogdao.getAllCatBlog() eq null}">
                                                        <div class="col-md-8">
                                                            <div id="newCategoryDIV" style="text-align: left;">
                                                                <label for="newCategory">Nuova categoria</label><i class="fas fa-info-circle ml-2" data-toggle="tooltip" title="<%=c%>"></i>
                                                                <input class="form-control mb-4" type="text" id="newCategory" name="newCategory" required/>
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="col-md-8">
                                                            <div id="newCategoryDIV" class="invisible" style="text-align: left;">
                                                                <label for="newCategory">Nuova categoria</label><i class="fas fa-info-circle ml-2" data-toggle="tooltip" title="<%=c%>"></i>
                                                                <input class="form-control mb-4" type="hidden" id="newCategory" name="newCategory" required/>
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
                                        </div>
                                        <div class="col-md-6 text-center mt-5">
                                            <img id="InputIMGBlog" src="img/ico/add.svg" alt="add_Modal_IMG" class="mb-3" style="width: 100%; border-radius: 5%; max-width: 500px;"/>
                                            <input type='file' name="immagine" onchange="readURL(this, 'InputIMGBlog');"/>
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
                                        <form method="POST" action="updateBlog"  enctype="multipart/form-data">
                                            <button class="btn btn-outline-info mt-4">Aggiorna</button>
                                            <input type="hidden" name="id" value="${articolo.id}" />
                                            <input style="font-size: 3.3125rem; line-height: 1.15em; height: 80px;" class="form-control mb-4" type="text" name="titolo" value="${articolo.nome}" required/>
                                           
                                            <textarea id="editor" name="testo" required>${articolo.testo}</textarea>
                                            <div class="row">
                                                <div class="col-md-6 mt-5">
                                                    <div class="row">
                                                        <div class="col-md-4">
                                                            <label for="categoria">Categoria</label>
                                                            <%String c1 = "ATTENZIONE!\nCreando una nuova categoria, il primo articolo inserito risulterà senza immagine.\nConsiglio:\n1) Creare un articolo vuoto per creare la nuova categoria.\n2)creare il vero articolo nella catgoria scelta\n3)eliminare l'articolo vuoto.";%>
                                                            <select style="width: auto;" class="form-control mb-4" name="categoria" id="categoria" required>
                                                                <c:forEach var="cat" items="${catblogdao.getAllCatBlog()}">
                                                                    <option value="${cat.nome}" <c:if test="${cat.nome eq articolo.categoria}">selected</c:if>>${cat.nome}</option>
                                                                </c:forEach>
                                                                <option value="New">Nuova categoria</option>
                                                            </select>
                                                        </div>
                                                        <div class="col-md-8">
                                                            <div id="newCategoryDIV" class="invisible" style="text-align: left;">
                                                                <label for="newCategory">Nuova categoria</label><i class="fas fa-info-circle ml-2" data-toggle="tooltip" title="<%=c1%>"></i>
                                                                <input class="form-control mb-4" type="hidden" id="newCategory" name="newCategory" required/>
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
                                                                <input class="form-control mb-4" type="hidden" id="newCreator" name="newCreator" required/>
                                                            </div>
                                                        </div>
                                                    </div>

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
                    <div class="container-fluid">
                        <nav class="float-left">
                            <ul>
                                <li>
                                    <a href="https://www.creative-tim.com">
                                        Creative Tim
                                    </a>
                                </li>
                                <li>
                                    <a href="https://creative-tim.com/presentation">
                                        About Us
                                    </a>
                                </li>
                                <li>
                                    <a href="http://blog.creative-tim.com">
                                        Blog
                                    </a>
                                </li>
                                <li>
                                    <a href="https://www.creative-tim.com/license">
                                        Licenses
                                    </a>
                                </li>
                            </ul>
                        </nav>
                        <div class="copyright float-right">
                            &copy;
                            <script>
                                document.write(new Date().getFullYear())
                            </script>, made with <i class="material-icons">favorite</i> by
                            <a href="https://www.creative-tim.com" target="_blank">Creative Tim</a> for a better web.
                        </div>
                    </div>
                </footer>
            </div>
        </div>

        <!--   Core JS Files   -->
        <script src="assets/js/core/jquery.min.js"></script>
        <script src="assets/js/core/popper.min.js"></script>
        <script src="assets/js/core/bootstrap-material-design.min.js"></script>
        <script src="assets/js/plugins/perfect-scrollbar.jquery.min.js"></script>
        <!-- Chartist JS -->
        <script src="assets/js/plugins/chartist.min.js"></script>
        <!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
        <script src="assets/js/material-dashboard.js?v=2.1.1" type="text/javascript"></script>
        <script defer src="https://use.fontawesome.com/releases/v5.7.2/js/all.js" integrity="sha384-0pzryjIRos8mFBWMzSSZApWtPl/5++eIfzYmTgBBmXYdhvxPc+XcFEk+zJwDgWbP" crossorigin="anonymous"></script>

        <script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
        <script src="https://unpkg.com/gijgo@1.9.13/js/messages/messages.it-it.js" type="text/javascript"></script>
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
            });
        </script>
    </body>
</html>
