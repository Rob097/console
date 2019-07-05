<%-- 
    Document   : ordine
    Created on : 3-lug-2019, 18.25.24
    Author     : Roberto97
--%>

<%@page import="java.time.LocalDate"%>
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

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--     Fonts and icons     -->
        <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!-- CSS Files -->
        <link href="assets/css/material-dashboard.css?v=2.1.1" rel="stylesheet" />
        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link href="assets/demo/demo.css" rel="stylesheet" />
        <link href="css/styles.css" rel="stylesheet" />


        <style>
        </style>
    </head>
    <body>
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
                        <h6 clasS="mb-4"><a href="ordini.jsp"><i class="fas fa-arrow-left mr-2"></i>Torna a tutti gli ordini</a></h6>
                        <c:choose>
                            <c:when test="${param.id eq null || param.id.equals('')}">
                                <h6 style='font-size: xx-large'>L'id cercato non è valido</h6>
                            </c:when>
                            <c:otherwise>
                                <c:set var="ordine" value="${consoledao.getOrder(param.id)}"/>
                                <c:choose>
                                    <c:when test="${ordine ne null}">

                                        <h6 style='font-size: x-large'>Ordine <u>${ordine.id}</u></h6>
                                        <div class='row mt-5 mb-5'>
                                            <div class='col-md-4'>
                                                <p><span class="text-warning">Nome e Cognome:</span> ${ordine.nome}</p>
                                                <p><span class="text-warning">Email:</span> ${ordine.email}</p>
                                                <p><span class="text-warning">Data:</span> ${ordine.data}</p>
                                            </div>
                                            <div class='col-md-4'>
                                                <p><span class="text-warning">Città:</span> ${ordine.citta}</p>
                                                <p><span class="text-warning">Indirizzo:</span> ${ordine.indirizzo}</p>
                                                <p><span class="text-warning">ZIP:</span> ${ordine.zip}</p>
                                            </div>
                                            <div class='col-md-4'>
                                                <p><span class="text-warning">Spedizione:</span> ${ordine.tipo}</p>
                                                <p><span class="text-warning">Totale:</span> € ${ordine.tot}</p>
                                            </div>
                                        </div>

                                        <div class="col-md-12">
                                            <div class="card" id="card-${tipo}">
                                                <div class="card-body">
                                                    <div class="table-responsive">
                                                        <table class="table">
                                                            <thead class=" text-warning">
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
                                                                Quantità
                                                            </th>
                                                            <th>
                                                                Prezzo unitario
                                                            </th>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach var="prodotto" items="${consoledao.getProdOfOrder(ordine.prodotti, request)}">
                                                                    <tr>
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
                                                                            ${prodotto.quantita}
                                                                        </td>
                                                                        <td>
                                                                            € ${prodotto.costo}
                                                                        </td>
                                                                    </tr>
                                                                </c:forEach>
                                                                <c:if test="${ordine.tipo.equals('KEATCHEN')}">
                                                                    <tr>
                                                                        <td>
                                                                            <div class="image-liquid image-holder--original col-3 zoom" style="cursor: unset; background-image: url('https://lh3.googleusercontent.com/6QOIaGv5w7KSjKo8XuMGgg_aXFQPbjQUX1TADK7LBCmxvRvNQncQRs-xDhdOz8rxnDg1tHL-zuybheQTO5a-ad3TE1YkXASmHIY7zd1jVhLMzbJE-W1rD_7NwYN-phBCflLlXuRB5Q=w2400');"></div>
                                                                        </td>
                                                                        <td colspan="3">
                                                                            Kit spedizione alimenti freschi Keatchen
                                                                        </td>
                                                                        <td>
                                                                            € 12
                                                                        </td>
                                                                    </tr>
                                                                </c:if>
                                                                <tr>
                                                                    <td>
                                                                        <h6 style="font-weight: 900 !important; text-align: center;">TOTALE</h6>
                                                                    </td>
                                                                    <td colspan='3'>                                                                            
                                                                    </td>
                                                                    <td style="font-weight: 900 !important;">
                                                                        € ${ordine.tot}
                                                                    </td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <form method="POST" class="text-center" action="changeOrderStatus" id="changeOrderStatus">
                                            <input type="hidden" name="id" value="${ordine.id}" />
                                            <div class="form-group">
                                                <label for="status"><b>Imposta stato:</b></label>
                                                <select class="form-control" id="status" name="stato" required>
                                                    <option value="1" <c:if test="${ordine.stato eq 'preparazione'}">selected</c:if>>Preparazione</option>
                                                    <option value="2" <c:if test="${ordine.stato eq 'spedito'}">selected</c:if>>Spedito</option>
                                                    <option value="3" <c:if test="${ordine.stato eq 'consegnato'}">selected</c:if>>Consegnato</option>
                                                    <option value="4" <c:if test="${ordine.stato eq 'ritirato'}">selected</c:if>>Ritirato</option>
                                                    <option value="5" <c:if test="${ordine.stato eq 'altro'}">selected</c:if>>Altro</option>
                                                </select>
                                                <input id="senderButtonUpdStatus" type="submit" class="btn btn-outline-success" value="Aggiorna">
                                            </div>
                                        </form>

                                        <div class="container comment-form" style="margin-top: 5rem;">
                                            <!--Section description-->
                                            <form method="POST" class="text-center" action="orderSent" id="sendEmailOrderSent">
                                                <div class="form-row">
                                                    <div class="col-md-6 mb-3">
                                                        <input type="text" class="form-control" name="nome" id="nome" value="${ordine.nome}" required>
                                                        <div class="invalid-feedback">
                                                            Il campo relativo al nome non è complilato in modo corretto
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 mb-3">
                                                        <input type="email" class="form-control" name="email" id="email" value="${ordine.email}" required>
                                                        <div class="invalid-feedback">
                                                            Il campo relativo all'email non è complilato in modo corretto
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-row">
                                                    <div class="col-12 mb-3">
                                                        <input type="text" class="form-control" name="oggetto" id="oggetto" value="Ordine spedito" required>
                                                        <div class="invalid-feedback">
                                                            Il campo relativo all'oggetto non è complilato in modo corretto
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-row">
                                                    <div class="col-12 mb-3">
                                                        <textarea class="form-control" rows="5" id="messaggio" name="testo" required>
Il tuo ordine "${ordine.id}" è stato spedito con corriere ${ordine.tipo} in data ${LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}
                                                        </textarea>
                                                        <div class="invalid-feedback">
                                                            Il campo relativo al messaggio non è complilato in modo corretto
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="traking">Numero spedizione: </label>
                                                    <input type="text" class="form-control" id="traking" name="traking" required>
                                                    <div class="invalid-feedback">
                                                        Il campo relativo al traking non è complilato in modo corretto
                                                    </div>
                                                </div>
                                                <input type="hidden" name="id" value="${ordine.id}" />
                                                <input type="hidden" name="tipo" value="${ordine.tipo}" />
                                                <hr style="border: 3px solid rgb(121, 85, 72); width: 50%; border-radius: 100%;"/>
                                                <input id="senderButton" type="submit" class="btn primary-btn" value="Invia email">
                                            </form>
                                        </div>   
                                    </c:when>
                                    <c:otherwise>
                                        <h6 style='font-size: x-large'>L'ordine cercato non esiste</h6>
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
        <script defer src="https://use.fontawesome.com/releases/v5.7.2/js/all.js" integrity="sha384-0pzryjIRos8mFBWMzSSZApWtPl/5++eIfzYmTgBBmXYdhvxPc+XcFEk+zJwDgWbP" crossorigin="anonymous"></script>
        <script src="js/apexChart.js"  type="text/javascript"></script>
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
            });
        </script>
    </body>

</html>