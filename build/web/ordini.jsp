<%-- 
    Document   : ordini
    Created on : 20-giu-2019, 17.38.55
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

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--     Fonts and icons     -->
        <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!-- CSS Files -->
        <link href="assets/css/material-dashboard.css?v=2.1.1" rel="stylesheet" />
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

                        <c:choose>
                            <c:when test="${!consoledao.getTypeDelivery().isEmpty()}">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="card">
                                            <div class="card-header card-header-danger">
                                                <h4 class="card-title ">Tipi di spedizione</h4>                                       
                                            </div>
                                            <div class="card-body">
                                                <div class="table-responsive">
                                                    <table class="table">
                                                        <thead class=" text-primary">
                                                        <th>
                                                            Tipo
                                                        </th>
                                                        <th>
                                                            N° Ordini
                                                        </th>
                                                        <th>
                                                            TOT
                                                        </th>
                                                        <th>
                                                            Data ultimo ordine
                                                        </th>
                                                        <th>
                                                            Totale ultimo ordine
                                                        </th>
                                                        <th>
                                                            In Preparazione
                                                        </th>
                                                        <th>
                                                            Spediti
                                                        </th>
                                                        <th>
                                                            Consegnati
                                                        </th>
                                                        <th>
                                                            Ritirati
                                                        </th>
                                                        <th>
                                                            Altro
                                                        </th>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="tipo" items="${consoledao.getTypeDelivery()}">
                                                                <tr>
                                                                    <td>
                                                                        <a href="#card-${tipo}" style="color: #333333;">${tipo}</a>
                                                                    </td>
                                                                    <td>
                                                                        ${consoledao.getNumberOfType(tipo)}
                                                                    </td>
                                                                    <td>
                                                                        € ${consoledao.getTotOfType(tipo)}
                                                                    </td>
                                                                    <td>
                                                                        ${consoledao.getLastOfType(tipo).data}
                                                                    </td>
                                                                    <td>
                                                                        € ${consoledao.getLastOfType(tipo).tot}
                                                                    </td>
                                                                    <td>
                                                                        ${consoledao.getNumberByStatusOfType("preparazione", tipo)}
                                                                    </td>
                                                                    <td>
                                                                        ${consoledao.getNumberByStatusOfType("spedito", tipo)}
                                                                    </td>
                                                                    <td>
                                                                        ${consoledao.getNumberByStatusOfType("consegnato", tipo)}
                                                                    </td>
                                                                    <td>
                                                                        ${consoledao.getNumberByStatusOfType("ritirato", tipo)}
                                                                    </td>
                                                                    <td>
                                                                        ${consoledao.getNumberByStatusOfType("altro", tipo)}
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <c:forEach var="tipo" items="${consoledao.getTypeDelivery()}">
                                        <div class="col-md-12">
                                            <div class="card" id="card-${tipo}">
                                                <div class="card-header card-header-warning">
                                                    <h4 class="card-title ">Ordini ${tipo}</h4>
                                                </div>
                                                <div class="card-body">
                                                    <div class="table-responsive">
                                                        <table class="table">
                                                            <thead class=" text-primary">
                                                            <th>
                                                                ID
                                                            </th>
                                                            <th>
                                                                Data
                                                            </th>
                                                            <th>
                                                                Nome
                                                            </th>
                                                            <th>
                                                                Email
                                                            </th>
                                                            <th>
                                                                Indirizzo
                                                            </th>
                                                            <th>
                                                                Prodotti
                                                            </th>
                                                            <th>
                                                                Tipo Spedizione
                                                            </th>
                                                            <th>
                                                                TOT
                                                            </th>
                                                            <th>
                                                                Stato
                                                            </th>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach var="ordine" items="${consoledao.getOrdersOfType(tipo)}">
                                                                    <tr>
                                                                        <td>
                                                                            <a style="color: black;" href="ordine.jsp?id=${ordine.id}">${ordine.id}</a>
                                                                        </td>
                                                                        <td>
                                                                            ${ordine.data}
                                                                        </td>
                                                                        <td>
                                                                            ${ordine.nome}
                                                                        </td>
                                                                        <td>
                                                                            ${ordine.email}
                                                                        </td>
                                                                        <td>
                                                                            ${ordine.citta}. ${ordine.indirizzo}, ${ordine.zip}
                                                                        </td>
                                                                        <td>
                                                                            ${ordine.toString()}
                                                                        </td>
                                                                        <td>
                                                                            ${ordine.tipo}
                                                                        </td>
                                                                        <td>
                                                                            € ${ordine.tot}
                                                                        </td>
                                                                        <td>
                                                                            ${StringUtils.capitalize(ordine.stato)}
                                                                        </td>
                                                                    </tr>
                                                                </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:otherwise>
                                Non ci sono ordini
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
        <script src="assets/js/plugins/jquery.validate.min.js"></script>
        <script src="assets/js/plugins/chartist.min.js"></script>
        <script src="assets/js/material-dashboard.js?v=2.1.1" type="text/javascript"></script>
        <script crossorigin src="https://unpkg.com/react@16/umd/react.production.min.js"></script>
        <script crossorigin src="https://unpkg.com/react-dom@16/umd/react-dom.production.min.js"></script>
        <script src="https://unpkg.com/prop-types@15.6.2/prop-types.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.34/browser.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/apexcharts@latest"></script>
        <script src="https://unpkg.com/react-apexcharts@1.1.0/dist/react-apexcharts.iife.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
        <!--<script src="js/apexChart.js"  type="text/javascript"></script>-->s
        <script defer src="https://use.fontawesome.com/releases/v5.7.2/js/all.js" integrity="sha384-0pzryjIRos8mFBWMzSSZApWtPl/5++eIfzYmTgBBmXYdhvxPc+XcFEk+zJwDgWbP" crossorigin="anonymous"></script>

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
            /* Preferiti modal */
            $(function () {
                $(".sidebar").load("ajax/sideBar.jsp?page=<c:out value='${pageContext.request.getRequestURI().replace("/console/", "").replace(".jsp", "").toLowerCase()}' />");
                $("#navbar").load("ajax/navbar.jsp?page=<c:out value='${StringUtils.capitalize(pageContext.request.getRequestURI().replace("/console/", "").replace(".jsp", "").toLowerCase())}' />");
                $("footer").load("ajax/footer.jsp");
            });
        </script>
    </body>

</html>
