<%-- 
    Document   : profilo
    Created on : 21-mag-2019, 15.22.54
    Author     : Roberto97
--%>

<%@page import="java.time.DayOfWeek"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.temporal.TemporalAdjusters"%>
<%@page import="java.time.LocalDate"%>
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

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--     Fonts and icons     -->
        <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
        <!-- CSS Files -->
        <link href="assets/css/material-dashboard.css?v=2.1.1" rel="stylesheet" />
        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link href="assets/demo/demo.css" rel="stylesheet" />
        <link href="css/styles.css" rel="stylesheet" />


        <style>
            @media(max-width: 1420px) and (min-width: 992px){
                .card-icon{
                    width: 100%;
                    text-align: center;
                    margin-bottom: 10px;
                }
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
                            <div class="col-lg-3 col-md-6 col-sm-6">
                                <div class="card card-stats">
                                    <div class="card-header card-header-info card-header-icon">
                                        <div class="card-icon">
                                            <!--<i style="font-size: 36px; width: 56px; height: 56px;" class="far fa-eye material-icons"></i>-->
                                            <i class="material-icons">remove_red_eye</i>
                                        </div>
                                        <p class="card-category">Visualizzazioni</p>
                                        <h3 class="card-title">n° ${consoledao.getWeekViews()}
                                        </h3>
                                    </div>
                                    <div class="card-footer">
                                        <div class="stats">
                                            <i class="material-icons">date_range</i> 
                                            <c:choose>
                                                <c:when test="${LocalDate.now().getDayOfWeek().name().equals(DayOfWeek.MONDAY.name())}">
                                                    Da oggi
                                                </c:when>
                                                <c:otherwise>
                                                    Dal ${LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6 col-sm-6">
                                <div class="card card-stats">
                                    <div class="card-header card-header-warning card-header-icon">
                                        <div class="card-icon">
                                            <i class="material-icons">email</i>
                                        </div>
                                        <p class="card-category">Email Sub</p>
                                        <h3 class="card-title">n° ${consoledao.getTotalEmailSub()}</h3>
                                    </div>
                                    <div class="card-footer">
                                        <div class="stats">
                                            <i class="material-icons">date_range</i> In totale
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6 col-sm-6">
                                <div class="card card-stats">
                                    <div class="card-header card-header-success card-header-icon">
                                        <div class="card-icon">
                                            <i class="material-icons">store</i>
                                        </div>
                                        <p class="card-category">Entrate</p>
                                        <h3 class="card-title">€ ${consoledao.getTotalRevenue()}</h3>
                                    </div>
                                    <div class="card-footer">
                                        <div class="stats">
                                            <i class="material-icons">date_range</i> In totale
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6 col-sm-6">
                                <div class="card card-stats">
                                    <div class="card-header card-header-danger card-header-icon">
                                        <div class="card-icon">
                                            <i class="material-icons">info_outline</i>
                                        </div>
                                        <p class="card-category">Uscite</p>
                                        <h3 class="card-title" id="valoreUscite">€ </h3>
                                    </div>
                                    <div class="card-footer">
                                        <div class="stats" id="usciteDate">
                                            <i class="material-icons">date_range</i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="card card-chart">
                                    <div class="card-header card-header-info">
                                        <div class="ct-chart" id="dailySalesChart"></div>
                                    </div>
                                    <div class="card-body">
                                        <h4 class="card-title">Visualizzazioni ultimo mese</h4>
                                        <p class="card-category" data-toggle="tooltip" title="Mese scorso: ${consoledao.getViewsChanges(true)}">
                                            <c:choose>
                                                <c:when test="${consoledao.getViewsChanges(false).equals('Dati insufficienti per un confronto')}">
                                                    <span class="text-right">
                                                        ${consoledao.getViewsChanges(false)}
                                                    </span>
                                                </c:when>
                                                <c:when test="${!consoledao.getViewsChanges(false).equals('Dati insufficienti per un confronto') && consoledao.getViewsChanges(false) >= 0}">
                                                    <span class="text-success">
                                                        <i class="fa fa-long-arrow-up"></i> ${consoledao.getViewsChanges(false)}% 
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-danger">
                                                        <i class="fa fa-long-arrow-down"></i> ${consoledao.getViewsChanges(false)}% 
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                            rispetto al mese scorso.</p>
                                    </div>
                                    <div class="card-footer">
                                        <div class="stats" data-toggle="tooltip" title="Prossimo aggiornamento: ${LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}">
                                            <i class="material-icons">access_time</i> Ultimo aggiornamento:  
                                            <c:choose>
                                                <c:when test="${LocalDate.now().getDayOfWeek().name().equals(DayOfWeek.MONDAY.name())}">
                                                    oggi
                                                </c:when>
                                                <c:otherwise>
                                                    il ${LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="card card-chart">
                                    <div class="card-header card-header-warning">
                                        <div class="ct-chart" id="websiteViewsChart"></div>
                                    </div>
                                    <div class="card-body">
                                        <h4 class="card-title">Iscrizioni email ultimo mese</h4>
                                        <p class="card-category" data-toggle="tooltip" title="Mese scorso: ${consoledao.getEmailChanges(true)}">
                                            <c:choose>
                                                <c:when test="${consoledao.getEmailChanges(false) >= 0}">
                                                    <span class="text-success">
                                                        <i class="fa fa-long-arrow-up"></i> ${consoledao.getEmailChanges(false)}% 
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-danger">
                                                        <i class="fa fa-long-arrow-down"></i> ${consoledao.getEmailChanges(false)}% 
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                            </span> 
                                            rispetto al mese scorso.</p></p>
                                    </div>
                                    <div class="card-footer">
                                        <div class="stats">
                                            <i class="material-icons">access_time</i> Ultimo aggiornamento: ${consoledao.getLastEmailSub()}
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="card card-chart">
                                    <div class="card-header card-header-success">
                                        <div class="ct-chart" id="completedTasksChart"></div>
                                    </div>
                                    <div class="card-body">
                                        <h4 class="card-title">Entrate ultimo mese</h4>
                                        <p class="card-category" data-toggle="tooltip" title="Mese scorso: € ${consoledao.getRevenueChanges(true)}">
                                            <c:choose>
                                                <c:when test="${consoledao.getRevenueChanges(false).equals('Dati insufficienti per un confronto')}">
                                                    <span class="text-right">
                                                        ${consoledao.getRevenueChanges(false)}
                                                    </span>
                                                </c:when>
                                                <c:when test="${!consoledao.getRevenueChanges(false).equals('Dati insufficienti per un confronto') && consoledao.getRevenueChanges(false) >= 0}">
                                                    <span class="text-success">
                                                        <i class="fa fa-long-arrow-up"></i> ${consoledao.getRevenueChanges(false)}% 
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-danger">
                                                        <i class="fa fa-long-arrow-down"></i> ${consoledao.getRevenueChanges(false)}% 
                                                    </span>
                                                </c:otherwise>
                                            </c:choose> 
                                            rispetto al mese scorso.</p></p>
                                        </p>
                                    </div>
                                    <div class="card-footer">
                                        <div class="stats">
                                            <i class="material-icons">access_time</i> Ultimo aggiornamento: ${consoledao.getLastRevenue()}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-6 col-md-12">
                                <div class="card">
                                    <div class="card-header card-header-tabs card-header-info">
                                        <h3>Visualizzazioni per pagina</h3>
                                    </div>
                                    <div class="card-body">
                                        <div class="chart" id="columnChart"></div>
                                    </div>
                                    <div class="card-footer">
                                        <div class="stats">
                                            <i class="material-icons">date_range</i> Fino  
                                            <c:choose>
                                                <c:when test="${LocalDate.now().getDayOfWeek().name().equals(DayOfWeek.MONDAY.name())}">
                                                    ad oggi
                                                </c:when>
                                                <c:otherwise>
                                                    al ${LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-12">
                                <div class="card">
                                    <div class="card-header card-header-tabs card-header-success">
                                        <h3>Vendita prodotti</h3>
                                    </div>
                                    <div class="card-body">
                                        <div class="chart" id="pieProduct"></div>
                                    </div>
                                    <div class="card-footer">
                                        <div class="stats">
                                            <i class="material-icons">access_time</i> Ultimo aggiornamento: ${consoledao.getLastRevenue()}
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

        <!--   Core JS Files   -->
        <script src="assets/js/core/jquery.min.js"></script>
        <script src="assets/js/core/popper.min.js"></script>
        <script src="assets/js/core/bootstrap-material-design.min.js"></script>
        <script src="assets/js/plugins/perfect-scrollbar.jquery.min.js"></script>
        <!-- Plugin for the momentJs 
        <script src="assets/js/plugins/moment.min.js"></script>
        <!--  Plugin for Sweet Alert 
        <script src="assets/js/plugins/sweetalert2.js"></script>
        <!-- Forms Validations Plugin -->
        <script src="assets/js/plugins/jquery.validate.min.js"></script>
        <!-- Plugin for the Wizard, full documentation here: https://github.com/VinceG/twitter-bootstrap-wizard -
        <script src="assets/js/plugins/jquery.bootstrap-wizard.js"></script>
        <!--	Plugin for Select, full documentation here: http://silviomoreto.github.io/bootstrap-select 
        <script src="assets/js/plugins/bootstrap-selectpicker.js"></script>
        <!--  Plugin for the DateTimePicker, full documentation here: https://eonasdan.github.io/bootstrap-datetimepicker/ 
        <script src="assets/js/plugins/bootstrap-datetimepicker.min.js"></script>
        <!--  DataTables.net Plugin, full documentation here: https://datatables.net/  
        <script src="assets/js/plugins/jquery.dataTables.min.js"></script>
        <!--	Plugin for Tags, full documentation here: https://github.com/bootstrap-tagsinput/bootstrap-tagsinputs  
        <script src="assets/js/plugins/bootstrap-tagsinput.js"></script>
        <!-- Plugin for Fileupload, full documentation here: http://www.jasny.net/bootstrap/javascript/#fileinput 
        <script src="assets/js/plugins/jasny-bootstrap.min.js"></script>
        <!--  Full Calendar Plugin, full documentation here: https://github.com/fullcalendar/fullcalendar 
        <script src="assets/js/plugins/fullcalendar.min.js"></script>
        <!-- Vector Map plugin, full documentation here: http://jvectormap.com/documentation/ 
        <script src="assets/js/plugins/jquery-jvectormap.js"></script>
        <!--  Plugin for the Sliders, full documentation here: http://refreshless.com/nouislider/
        <script src="assets/js/plugins/nouislider.min.js"></script>
        <!-- Include a polyfill for ES6 Promises (optional) for IE11, UC Browser and Android browser support SweetAlert
        <script src="https://cdnjs.cloudflare.com/ajax/libs/core-js/2.4.1/core.js"></script>
        <!-- Library for adding dinamically elements 
        <script src="assets/js/plugins/arrive.min.js"></script>
        <!--  Google Maps Plugin   
        <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>
        <!-- Chartist JS -->
        <script src="assets/js/plugins/chartist.min.js"></script>
        <!--  Notifications Plugin    
        <script src="assets/js/plugins/bootstrap-notify.js"></script>
        <!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
        <script src="assets/js/material-dashboard.js?v=2.1.1" type="text/javascript"></script>
        <!-- Material Dashboard DEMO methods, don't include it in your project! 
        <script src="assets/demo/demo.js"></script>-->
        <script crossorigin src="https://unpkg.com/react@16/umd/react.production.min.js"></script>
        <script crossorigin src="https://unpkg.com/react-dom@16/umd/react-dom.production.min.js"></script>
        <script src="https://unpkg.com/prop-types@15.6.2/prop-types.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.34/browser.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/apexcharts@latest"></script>
        <script src="https://unpkg.com/react-apexcharts@1.1.0/dist/react-apexcharts.iife.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
        <script src="js/apexChart.js"  type="text/javascript"></script>
        <script>

        </script>
        <script>
            initColumnChart();
            initPieChart();
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
            var initialDate = new Date(2018, 9, 31);
            var today = new Date();
            var payTimes = today.getUTCFullYear() - initialDate.getUTCFullYear();
            if (today.getMonth() > initialDate.getMonth()) {
                payTimes += 1;
            }
            var dominioCost = 38.50;
            var hostCost = 79.51;
            var tot = dominioCost + 0.22 * dominioCost + hostCost;
            tot = tot.toFixed(2);
            let formatted_initialDate = initialDate.getDate() + "-" + (initialDate.getMonth() + 1) + "-" + initialDate.getFullYear();

            $('#valoreUscite').html($('#valoreUscite').html() + tot);
            $('#usciteDate').html($('#usciteDate').html() + " Dal " + formatted_initialDate);

        </script>
        <script>
            /* Preferiti modal */
            $(function () {
                $(".sidebar").load("ajax/sideBar.jsp?page=<c:out value='${pageContext.request.getRequestURI().replace("/console/", "").replace(".jsp", "").toLowerCase()}' />");
            });
        </script>
        <script>
            $(document).ready(function () {
                // Javascript method's body can be found in assets/js/demos.js
                md.initDashboardPageCharts();
            });
        </script>
    </body>

</html>

