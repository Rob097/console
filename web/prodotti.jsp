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
        <!-- CSS Files -->
        <link href="assets/css/material-dashboard.css?v=2.1.1" rel="stylesheet" />
        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link href="assets/demo/demo.css" rel="stylesheet" />

        <style>
            .zoom {
                padding: 50px;
                background-color: transparent;
                transition: transform .2s;
                margin: 0 auto;
                width: 50px;
                height: 50px;
                background-position: center center;
                background-size: contain;
                background-repeat: no-repeat;
                cursor: pointer;
            }
            .zoom:hover {
                transform: scale(2.5); /* (150% zoom - Note: if the zoom is too large, it will go outside of the viewport) */
                z-index: 1;
            }
            .inputImg{
                max-width: 180px;
            }
        </style>
    </head>

    <body class="">
        <div class="wrapper ">
            <div class="sidebar" data-color="purple" data-background-color="white" data-image="assets/img/sidebar-1.jpg">
                <!-- Load with javascript from /ajax -->
            </div>
            <div class="main-panel">
                <!-- Navbar -->
                <nav class="navbar navbar-expand-lg navbar-transparent navbar-absolute fixed-top ">
                    <div class="container-fluid">
                        <div class="navbar-wrapper">
                            <a class="navbar-brand" href="#pablo">${StringUtils.capitalize(pageContext.request.getRequestURI().replace("/console/", "").replace(".jsp", "").toLowerCase())}</a>
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
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header card-header-primary">
                                        <h4 class="card-title ">Categorie</h4>
                                        <p class="card-category"> Categorie di prodotti</p>
                                    </div>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="table">
                                                <thead class=" text-primary">
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
                                                        <td colspan="4" class="subDivisionTable"> Prodotti confezionati</td>
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
                                                        <td colspan="4" class="subDivisionTable"> Prodotti freschi</td>
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
                                                                ${categoria.nome}
                                                            </td>
                                                            <td>
                                                                ${productdao.getAllProductsOfCategory(categoria.nome).size()}
                                                            </td>
                                                            <!--<td>
                                                                <a href="#" data-toggle="tooltip" title="Aggiorna i dati della categoria '${categoria.nome}'">
                                                                    <i class="material-icons">refresh</i>
                                                                </a>
                                                            </td>-->
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
                                    <div class="card-header card-header-primary">
                                        <h4 class="card-title mt-0"> Prodotti</h4>
                                        <p class="card-category">Prodotti Confezionati</p>
                                    </div>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="table table-hover">
                                                <thead class="">
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
                                                            <td id="${categoria.nome}" colspan="7" class="subDivisionTable"> ${categoria.nome}</td>
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
                                    <div class="card-header card-header-primary">
                                        <h4 class="card-title mt-0"> Prodotti</h4>
                                        <p class="card-category">Prodotti Freschi</p>
                                    </div>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="table table-hover">
                                                <thead class="">
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
                                                            <td id="${categoria.nome}" colspan="7" class="subDivisionTable"> ${categoria.nome}</td>
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
                        </div>
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

        <!-- ###########################    MODALI   ###########################-->

        <!-- Cambio immagine categoria -->
        <div class="modal fade" id="catImgChangeModal" tabindex="-1" role="dialog" aria-labelledby="catImgChangeModal"
             aria-hidden="true">
        </div>

        <!-- Modifica prodotto -->
        <div class="modal fade" id="updateProd" tabindex="-1" role="dialog" aria-labelledby="updateProd"
             aria-hidden="true">
        </div>

        <!-- ###########################    FINE MODALI   ###########################-->

        <!--   Core JS Files   -->
        <script src="assets/js/core/jquery.min.js"></script>
        <script src="assets/js/core/popper.min.js"></script>
        <script src="assets/js/core/bootstrap-material-design.min.js"></script>
        <script src="assets/js/plugins/perfect-scrollbar.jquery.min.js"></script>
        <!-- Plugin for the momentJs  -->
        <script src="assets/js/plugins/moment.min.js"></script>
        <!--  Plugin for Sweet Alert -->
        <script src="assets/js/plugins/sweetalert2.js"></script>
        <!-- Forms Validations Plugin -->
        <script src="assets/js/plugins/jquery.validate.min.js"></script>
        <!-- Plugin for the Wizard, full documentation here: https://github.com/VinceG/twitter-bootstrap-wizard -->
        <script src="assets/js/plugins/jquery.bootstrap-wizard.js"></script>
        <!--	Plugin for Select, full documentation here: http://silviomoreto.github.io/bootstrap-select -->
        <script src="assets/js/plugins/bootstrap-selectpicker.js"></script>
        <!--  Plugin for the DateTimePicker, full documentation here: https://eonasdan.github.io/bootstrap-datetimepicker/ -->
        <script src="assets/js/plugins/bootstrap-datetimepicker.min.js"></script>
        <!--  DataTables.net Plugin, full documentation here: https://datatables.net/  -->
        <script src="assets/js/plugins/jquery.dataTables.min.js"></script>
        <!--	Plugin for Tags, full documentation here: https://github.com/bootstrap-tagsinput/bootstrap-tagsinputs  -->
        <script src="assets/js/plugins/bootstrap-tagsinput.js"></script>
        <!-- Plugin for Fileupload, full documentation here: http://www.jasny.net/bootstrap/javascript/#fileinput -->
        <script src="assets/js/plugins/jasny-bootstrap.min.js"></script>
        <!--  Full Calendar Plugin, full documentation here: https://github.com/fullcalendar/fullcalendar    -->
        <script src="assets/js/plugins/fullcalendar.min.js"></script>
        <!-- Vector Map plugin, full documentation here: http://jvectormap.com/documentation/ -->
        <script src="assets/js/plugins/jquery-jvectormap.js"></script>
        <!--  Plugin for the Sliders, full documentation here: http://refreshless.com/nouislider/ -->
        <script src="assets/js/plugins/nouislider.min.js"></script>
        <!-- Include a polyfill for ES6 Promises (optional) for IE11, UC Browser and Android browser support SweetAlert -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/core-js/2.4.1/core.js"></script>
        <!-- Library for adding dinamically elements -->
        <script src="assets/js/plugins/arrive.min.js"></script>
        <!--  Google Maps Plugin    -->
        <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>
        <!-- Chartist JS -->
        <script src="assets/js/plugins/chartist.min.js"></script>
        <!--  Notifications Plugin    -->
        <script src="assets/js/plugins/bootstrap-notify.js"></script>
        <!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
        <script src="assets/js/material-dashboard.js?v=2.1.1" type="text/javascript"></script>
        <!-- Material Dashboard DEMO methods, don't include it in your project! -->
        <script src="assets/demo/demo.js"></script>
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
            function readURL(input) {
                if (input.files && input.files[0]) {
                    var reader = new FileReader();

                    reader.onload = function (e) {
                        $('#InputIMG')
                                .attr('src', e.target.result);
                    };

                    reader.readAsDataURL(input.files[0]);
                }
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
        </script>
    </body>

</html>
