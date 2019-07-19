<%-- 
    Document   : prodotti
    Created on : 1-giu-2019, 18.36.13
    Author     : Roberto97
--%>

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
        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link href="css/styles.css" rel="stylesheet" />

        <style>

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
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header card-header-danger">
                                        <h4 class="card-title ">Categorie</h4>
                                        <a style="float: right; cursor: pointer;" data-toggle="modal" data-target="#addCategory"><i class="fas fa-plus"></i> Aggiungi categoria</a>                                       
                                        <p class="card-category"> Categorie di prodotti</p>                                        
                                    </div>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="table">
                                                <thead class=" text-danger">
                                                <th>
                                                    ID
                                                </th>
                                                <th>
                                                    Immagine
                                                </th>
                                                <th>
                                                    Nome
                                                </th>
                                                <th>
                                                    N° Prodotti
                                                </th>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td colspan="4" class="subDivisionTable text-danger"> Prodotti confezionati</td>
                                                    </tr>
                                                    <c:forEach var="categoria" items="${categorydao.getConfCategories()}">
                                                        <tr>
                                                            <td>
                                                                ${categoria.id}
                                                            </td>
                                                            <td>
                                                                <div data-toggle="tooltip" title="Aggiorna i dati della categoria '${categoria.nome}'" onclick="catImgChange(${categoria.id});" class="image-liquid image-holder--original col-3 zoom" style="background-image: url('${categoria.immagine}');"></div>
                                                            </td>
                                                            <td>
                                                                <a style="color:#333333;" href="#${categoria.nome}">${categoria.nome}</a>
                                                            </td>
                                                            <td>
                                                                ${productdao.getAllProductsOfCategory(categoria.nome).size()}
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                    <tr>
                                                        <td colspan="4" class="subDivisionTable text-danger"> Prodotti freschi</td>
                                                    </tr>
                                                    <c:forEach var="categoria" items="${categorydao.getFreshCategories()}">
                                                        <tr>
                                                            <td>
                                                                ${categoria.id}
                                                            </td>
                                                            <td>
                                                                <div onclick="catImgChange(${categoria.id});" data-toggle="tooltip" title="Aggiorna i dati della categoria '${categoria.nome}'" class="image-liquid image-holder--original col-3 zoom" style="background-image: url('${categoria.immagine}');"></div>
                                                            </td>
                                                            <td>
                                                                <a style="color:#333333;" href="#${categoria.nome}">${categoria.nome}</a>
                                                            </td>
                                                            <td>
                                                                ${productdao.getAllProductsOfCategory(categoria.nome).size()}
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="card card-plain">
                                    <div class="card-header card-header-warning">
                                        <h4 class="card-title mt-0"> Prodotti</h4>
                                        <a style="float: right; cursor: pointer;" data-toggle="modal" data-target="#addConfezionato"><i class="fas fa-plus"></i> Aggiungi prodotto confezionato</a>                                       
                                        <p class="card-category">Prodotti Confezionati</p>
                                    </div>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="table table-hover">
                                                <thead class="text-warning">
                                                <th>
                                                    ID
                                                </th>
                                                <th>
                                                    Immagine
                                                </th>
                                                <th>
                                                    Nome
                                                </th>
                                                <th>
                                                    Descrizione
                                                </th>
                                                <th>
                                                    Categoria
                                                </th>
                                                <th>
                                                    Disponibilità
                                                </th>
                                                <th>
                                                    Prezzo
                                                </th>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="categoria" items="${categorydao.getConfCategories()}">
                                                        <tr>
                                                            <td id="${categoria.nome}" colspan="7" class="subDivisionTable text-warning"> ${categoria.nome}</td>
                                                        </tr>
                                                        <c:forEach var="prodotto" items="${productdao.getAllProductsOfCategory(categoria.nome)}">
                                                            <tr style="cursor: pointer;" onclick="updateProd(${prodotto.id});">
                                                                <td>
                                                                    ${prodotto.id}
                                                                </td>
                                                                <td>
                                                                    <div class="image-liquid image-holder--original col-3 zoom" style="cursor: unset; background-image: url('${prodotto.immagine}');"></div>
                                                                </td>
                                                                <td>
                                                                    ${prodotto.nome}
                                                                </td>
                                                                <td>
                                                                    ${prodotto.descrizione}
                                                                </td>
                                                                <td>
                                                                    ${prodotto.categoria}
                                                                </td>
                                                                <td>
                                                                    <c:if test="${prodotto.disponibile eq false}">Non </c:if>Disponibile
                                                                    </td>
                                                                    <td>
                                                                        € ${prodotto.costo}
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="card card-plain">
                                    <div class="card-header card-header-warning">
                                        <h4 class="card-title mt-0"> Prodotti</h4>
                                        <a style="float: right; cursor: pointer;" data-toggle="modal" data-target="#addFresco"><i class="fas fa-plus"></i> Aggiungi prodotto fresco</a>                                       
                                        <p class="card-category">Prodotti Freschi</p>
                                    </div>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="table table-hover">
                                                <thead class="text-warning">
                                                <th>
                                                    ID
                                                </th>
                                                <th>
                                                    Immagine
                                                </th>
                                                <th>
                                                    Nome
                                                </th>
                                                <th>
                                                    Descrizione
                                                </th>
                                                <th>
                                                    Categoria
                                                </th>
                                                <th>
                                                    Disponibilità
                                                </th>
                                                <th>
                                                    Prezzo
                                                </th>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="categoria" items="${categorydao.getFreshCategories()}">
                                                        <tr>
                                                            <td id="${categoria.nome}" colspan="7" class="subDivisionTable text-warning"> ${categoria.nome}</td>
                                                        </tr>
                                                        <c:forEach var="prodotto" items="${productdao.getAllProductsOfCategory(categoria.nome)}">
                                                            <tr style="cursor: pointer;" onclick="updateProd(${prodotto.id});">
                                                                <td>
                                                                    ${prodotto.id}
                                                                </td>
                                                                <td>
                                                                    <div class="image-liquid image-holder--original col-3 zoom" style="cursor: unset; background-image: url('${prodotto.immagine}');"></div>
                                                                </td>
                                                                <td>
                                                                    ${prodotto.nome}
                                                                </td>
                                                                <td>
                                                                    ${prodotto.descrizione}
                                                                </td>
                                                                <td>
                                                                    ${prodotto.categoria}
                                                                </td>
                                                                <td>
                                                                    <c:if test="${prodotto.disponibile eq false}">Non </c:if>Disponibile
                                                                    </td>
                                                                    <td>
                                                                        € ${prodotto.costo}
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${productdao.getNullCategoryProducts() ne null}">
                                <div class="col-md-12">
                                    <div class="card card-plain">
                                        <div class="card-header card-header-info">
                                            <h4 class="card-title mt-0"> Prodotti</h4>                                      
                                            <p class="card-category">Prodotti senza categoria</p>
                                        </div>
                                        <div class="card-body">
                                            <div class="table-responsive">
                                                <table class="table table-hover">
                                                    <thead class="text-warning">
                                                    <th>
                                                        ID
                                                    </th>
                                                    <th>
                                                        Immagine
                                                    </th>
                                                    <th>
                                                        Nome
                                                    </th>
                                                    <th>
                                                        Descrizione
                                                    </th>
                                                    <th>
                                                        Disponibilità
                                                    </th>
                                                    <th>
                                                        Prezzo
                                                    </th>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="prodotto" items="${productdao.getNullCategoryProducts()}">
                                                            <tr style="cursor: pointer;" onclick="updateProd(${prodotto.id});">
                                                                <td>
                                                                    ${prodotto.id}
                                                                </td>
                                                                <td>
                                                                    <div class="image-liquid image-holder--original col-3 zoom" style="cursor: unset; background-image: url('${prodotto.immagine}');"></div>
                                                                </td>
                                                                <td>
                                                                    ${prodotto.nome}
                                                                </td>
                                                                <td>
                                                                    ${prodotto.descrizione}
                                                                </td>
                                                                <td>
                                                                    <c:if test="${prodotto.disponibile eq false}">
                                                                        Non 
                                                                    </c:if>
                                                                    Disponibile
                                                                </td>
                                                                <td>
                                                                    € ${prodotto.costo}
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
                <footer class="footer">
                </footer>
            </div>
        </div>

        <!-- ###########################    MODALI   ###########################-->

        <!-- Cambio immagine categoria -->
        <div class="modal fade" id="catImgChangeModal" tabindex="-1" role="dialog" aria-labelledby="catImgChangeModal"
             aria-hidden="true">
        </div>

        <!-- Modifica prodotto -->
        <div class="modal fade" id="updateProd" tabindex="-1" role="dialog" aria-labelledby="updateProd"
             aria-hidden="true">
        </div>

        <!-- Add new Category -->
        <div class="modal fade" id="addCategory" tabindex="-1" role="dialog" aria-labelledby="updateProd" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content container">
                    <div class="modal-header">
                        <h5 class="modal-title" id="catImgChangeTitle">Nuova categoria</h5>
                        <button type="button" class="close" onclick='closeModal();' aria-label="Close" >
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form method="POST" action="addCategory" enctype="multipart/form-data">
                        <div class="modal-body">
                            <div class="text-center mb-4">
                                <img id="InputIMGCat" src="" alt="Modal_IMG_Cat" class="inputImg mb-3 invisible"/>
                                <input type='file' name="immagine" onchange="readURL(this, 'InputIMGCat'); $('#InputIMGCat').removeClass('invisible');" required/>
                            </div>
                            <input maxlength="45" type="text" name="nome" class="form-control mb-4" placeholder="Nome" required/>
                            <textarea style="min-height: 100px;" name="descrizione" class="form-control mb-4" placeholder="Descrizione" required></textarea>
                            <input type="checkbox" name="fresco"/> Fresco
                        </div>
                        <button type="button" class="btn btn-secondary" onclick='closeModal();'>Annulla</button>
                        <button id="catImgChangeSubmit" type="submit" class="btn btn-primary">Salva</button>
                    </form>
                </div>
            </div>
        </div>

        <!-- Add Confezionato -->
        <div class="modal fade" id="addConfezionato" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content container">
                    <div class="modal-header">
                        <h5 class="modal-title" id="catImgChangeTitle">Nuovo prodotto confezionato</h5>
                        <button type="button" class="close" onclick='closeModal();' aria-label="Close" >
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form method="POST" action="addProduct" enctype="multipart/form-data">
                        <div class="modal-body">
                            <div class="row mb-5">
                                <div class="col-md-6">
                                    <div style="height: 100%;" id="toDeleteConf"></div>
                                    <img id="InputIMGConf" src="" alt="Modal_IMG_Conf" class="inputImg mb-3 invisible"/>
                                    <input type='file' name="immagine" onchange="readURL(this, 'InputIMGConf'); $('#InputIMGConf').removeClass('invisible'); $('#toDeleteConf').addClass('invisible');" required/>
                                </div>
                                <div class="col-md-6">
                                    <input maxlength="45" type="text" name="nome" class="form-control mb-4" placeholder="Nome" required/>
                                    <select class="form-control mb-4" id="categoria" name="categoria" required>
                                        <c:forEach var="cat" items="${categorydao.getConfCategories()}">
                                            <option>${cat.nome}</option>
                                        </c:forEach>
                                    </select>                       
                                </div>
                            </div>
                            <input pattern="[0-9]+(.|,){0,1}+[0-9]{0,1,2}" type="text" name="costo" class="form-control mt-5" placeholder="Costo" required /><br>
                            <textarea style="min-height: 100px;" name="descrizione" class="form-control" placeholder="Descrizione" required></textarea><br>
                            <input class="mb-4" type="checkbox" name="disponibile" checked/> Disponibile<br>
                            <input type="hidden" name="fresco" value="false" required />
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" onclick='closeModal();'>Annulla</button>
                            <button id="catImgChangeSubmit" type="submit" class="btn btn-primary">Salva</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Add Fresco -->
        <div class="modal fade" id="addFresco" tabindex="-1" role="dialog" aria-hidden="true">
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
        </div>
        <!-- ###########################    FINE MODALI   ###########################-->

        <!--   Core JS Files   -->
        <script src="assets/js/core/jquery.min.js"></script>
        <script src="assets/js/core/popper.min.js"></script>
        <script src="assets/js/core/bootstrap-material-design.min.js"></script>
        <script src="assets/js/plugins/perfect-scrollbar.jquery.min.js"></script>   
        <script src="assets/js/plugins/bootstrap-notify.js"></script>
        <script src="assets/js/plugins/chartist.min.js"></script>
        <script src="assets/js/material-dashboard.js?v=2.1.1" type="text/javascript"></script>
        <script defer src="https://use.fontawesome.com/releases/v5.7.2/js/all.js" integrity="sha384-0pzryjIRos8mFBWMzSSZApWtPl/5++eIfzYmTgBBmXYdhvxPc+XcFEk+zJwDgWbP" crossorigin="anonymous"></script>
        <script src="js/bootstrap-maxlength.js"></script>
        <script>

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
        <script>
            /* Cambio immagine categoria */
            function catImgChange(id) {
                $('[role="tooltip"]').remove();
                $.ajax({
                    type: "POST",
                    url: "ajax/catImgChangeModal.jsp",
                    data: {id: id},
                    cache: false,
                    success: function (response) {
                        $("#catImgChangeModal").html(response);
                        openModal('catImgChangeModal');
                    },
                    error: function () {
                        alert("Errore catImgChange");
                    }
                });
            }

            /* Update of Product */
            function updateProd(id) {
                $.ajax({
                    type: "POST",
                    url: "ajax/updateProd.jsp",
                    data: {id: id},
                    cache: false,
                    success: function (response) {
                        $("#updateProd").html(response);
                        openModal('updateProd');
                    },
                    error: function () {
                        alert("Errore updateProd");
                    }
                });
            }

            function openModal(id) {
                $('body').addClass('modal-open');
                $("#" + id).addClass('show');
                $("#" + id).css({display: 'block'});
                $('body').append('<div class="modal-backdrop fade show"></div>');
            }
            function closeModal() {
                $('body').removeClass('modal-open');
                $('.modal').removeClass('show');
                $('.modal').css({display: 'none'});
                $('.modal-backdrop').remove();
            }

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
                //utf8: if true the input will count using utf8 bytesize/encoding. For example: the '£' character is counted as two characters.
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
