<%-- 
    Document   : statistiche
    Created on : 18-giu-2019, 18.37.06
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
            .wrap {
                margin: 45px auto;
                max-width: inherit;
                position: relative;
            }

            .chart-box {
                padding-left: 0;
            }

            .chart-year,
            .chart-quarter {
                width: 96%;
                max-width: 48%;
                box-shadow: none;
            }

            .chart-year {
                float: left;
                position: relative;
                transition: 1s ease transform;
                z-index: 3;
            }

            .chart-year.chart-quarter-activated {
                transform: translateX(0);
                transition: 1s ease transform;
            }

            .chart-quarter {
                float: left;
                position: relative;
                z-index: -2;
                transition: 1s ease transform;
            }

            .chart-quarter.active {
                transition: 1.1s ease-in-out transform;
                transform: translateX(0);
                z-index: 1;
            }

            @media screen and (min-width: 480px) {
                .chart-year {
                    transform: translateX(50%);
                }
                .chart-quarter {
                    transform: translateX(-50%);
                }
            }

            select#model {
                display: none;
                position: absolute;
                top: -40px;
                left: 0;
                z-index: 2;
                cursor: pointer;
                transform: scale(0.8);
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

                        <div class="card">
                            <div class="card-header card-header-tabs card-header-info ">
                                <h3>Letture</h3>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-lg-6 col-12">
                                        <h3>Blog</h3>

                                        <div id="app-blog">
                                            <div class="wrap">
                                                <div id="chart-year-blog" class="chart-box chart-year">

                                                </div>
                                                <div id="chart-quarter-blog" class="chart-box chart-quarter">

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-6 col-12">
                                        <h3>Idee</h3>

                                        <div id="app-idee">
                                            <div class="wrap">
                                                <div id="chart-year-idee" class="chart-box chart-year">

                                                </div>
                                                <div id="chart-quarter-idee" class="chart-box chart-quarter">

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer">
                                <div class="stats">
                                    <i class="material-icons">date_range</i> Real Time  
                                </div>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-header card-header-tabs card-header-info ">
                                <h3>Commenti</h3>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-lg-6 col-12">
                                        <h3>Blog</h3>

                                        <div id="app-blog-comments">
                                            <div class="wrap">
                                                <div id="chart-year-blog-comments" class="chart-box chart-year">

                                                </div>
                                                <div id="chart-quarter-blog-comments" class="chart-box chart-quarter">

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-6 col-12">
                                        <h3>Idee</h3>

                                        <div id="app-idee-comments">
                                            <div class="wrap">
                                                <div id="chart-year-idee-comments" class="chart-box chart-year">

                                                </div>
                                                <div id="chart-quarter-idee-comments" class="chart-box chart-quarter">

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer">
                                <div class="stats">
                                    <i class="material-icons">date_range</i> Real Time  
                                </div>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-header card-header-tabs card-header-info ">
                                <h3>Valutazione</h3>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-lg-6 col-12">
                                        <h3>Blog</h3>

                                        <div id="app-blog-rate">
                                            <div class="wrap">
                                                <div id="chart-year-blog-rate" class="chart-box chart-year">

                                                </div>
                                                <div id="chart-quarter-blog-rate" class="chart-box chart-quarter">

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-6 col-12">
                                        <h3>Idee</h3>

                                        <div id="app-idee-rate">
                                            <div class="wrap">
                                                <div id="chart-year-idee-rate" class="chart-box chart-year">

                                                </div>
                                                <div id="chart-quarter-idee-rate" class="chart-box chart-quarter">

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-12">
                                        <h3>Prodotti</h3>

                                        <div id="app-product-rate">
                                            <div class="wrap">
                                                <div id="chart-year-product-rate" class="chart-box chart-year">

                                                </div>
                                                <div id="chart-quarter-product-rate" class="chart-box chart-quarter">

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer">
                                <div class="stats">
                                    <i class="material-icons">date_range</i> Real Time  
                                </div>
                            </div>
                        </div>
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
        <!-- Chartist JS -->
        <script src="assets/js/plugins/chartist.min.js"></script>
        <!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
        <script src="assets/js/material-dashboard.js?v=2.1.1" type="text/javascript"></script>
        <script defer src="https://use.fontawesome.com/releases/v5.7.2/js/all.js" integrity="sha384-0pzryjIRos8mFBWMzSSZApWtPl/5++eIfzYmTgBBmXYdhvxPc+XcFEk+zJwDgWbP" crossorigin="anonymous"></script>


        <script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
        <script crossorigin src="https://unpkg.com/react@16/umd/react.production.min.js"></script>
        <script crossorigin src="https://unpkg.com/react-dom@16/umd/react-dom.production.min.js"></script>
        <script src="https://unpkg.com/prop-types@15.6.2/prop-types.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.34/browser.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/apexcharts@latest"></script>
        <script src="https://unpkg.com/react-apexcharts@1.1.0/dist/react-apexcharts.iife.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
        <script src="js/apexChart.js"  type="text/javascript"></script>
        <script>
            initBlogViewChart('blog');
            initBlogViewChart('idee');
            initCommentChart('blog');
            initCommentChart('idee');
            initRateChart('blog');
            initRateChart('idee');
            initRateChart('product');
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
    </body>
</html>
