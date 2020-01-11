<%-- 
    Document   : menu
    Created on : 6-lug-2019, 15.09.09
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
                        <div class="col-md-12">
                            <div style="text-align: center;">
                                <span><i class="fas fa-info-circle" data-toggle="tooltip" title="Il peso delle due immagini sommate deve essere inferiore ai 2.4MB. Puoi comprimere le immagini a questo link. In alternativa puoi caricare una immagine sola e la seconda sostituirla con una dal peso minore e poi aggiornare il menu con la seconda immagine corretta."></i></span><br>
                                <a href="https://compressjpeg.com/" target="_blank" class="btn btn-warning" style="margin: auto auto; color: black; font-weight: 900;">Comprimi immagine</a>
                            </div>
                            <div class="card card-plain">
                                <div class="card-header card-header-warning">
                                    <h4 class="card-title mt-0">Sezioni Menu</h4>
                                    <a style="float: right; cursor: pointer;" data-toggle="modal" data-target="#addMenu"><i class="fas fa-plus"></i> Aggiungi sezione al menu</a>                                       
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
                                            </th>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="menu" items="${menudao.getAllMenu()}">
                                                    <tr>
                                                        <td style="cursor: pointer;" onclick="updateMenu(${menu.id});">
                                                            ${menu.id}
                                                        </td>
                                                        <td style="cursor: pointer;" onclick="updateMenu(${menu.id});">
                                                            <div class="image-liquid image-holder--original col-3 zoom" style="cursor: unset; background-image: url('${menu.copertina}');"></div>
                                                        </td>
                                                        <td style="cursor: pointer;" onclick="updateMenu(${menu.id});">
                                                            ${menu.nome}
                                                        </td>
                                                        <td>
                                                            <a class="btn btn-outline-dark" href="${menu.immagine}" target="_blank" rel="noopener">Visualizza Menu</a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <footer class="footer">
                </footer>
            </div>
        </div>

        <!-- ###########################    MODALI   ###########################-->

        <!-- Modifica prodotto -->
        <div class="modal fade" id="updateMenu" tabindex="-1" role="dialog" aria-labelledby="updateMenu"
             aria-hidden="true">
        </div>

        <!-- Add new Menu -->
        <div class="modal fade" id="addMenu" tabindex="-1" role="dialog" aria-labelledby="addMenu" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: fit-content;">
                <div class="modal-content container">
                    <div class="modal-header">
                        <h5 class="modal-title" id="catImgChangeTitle">Nuova sezione del Menu</h5>
                        <button type="button" class="close" onclick='closeModal();' aria-label="Close" >
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form method="POST" action="addMenu" enctype="multipart/form-data">
                        <div class="row">
                            <div class="col-md-6">
                                <p>Copertina</p>
                                <img id="ADDcopertinaMenu" src="" alt="newMenu_Modal_COP" class="inputImg mb-3 invisible"/><br>
                                <input type='file' name="ADDcopertinaMenu" onchange="readURL(this, 'ADDcopertinaMenu'); $('#ADDcopertinaMenu').removeClass('invisible');"/> 
                            </div>
                            <div class="col-md-6">
                                <p>Immagine</p>
                                <img id="ADDimgMenu" src="" alt="newMenu_Modal_IMG" class="inputImg mb-3 invisible"/><br>
                                <input type='file' name="ADDimmagineMenu" onchange="readURL(this, 'ADDimgMenu'); $('#ADDimgMenu').removeClass('invisible');"/> 
                            </div>                
                        </div>

                        <input maxlength="100" type="text" name="nome" placeholder="Nome" class="form-control mt-4 mb-4" required/>
                        <button type="button" class="btn btn-secondary" onclick='closeModal();'>Annulla</button>
                        <button id="addMenuSubmit" type="submit" class="btn btn-primary">Salva</button>
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
            /* Update of Product */
            function updateMenu(id) {
                $.ajax({
                    type: "POST",
                    url: "ajax/updateMenu.jsp",
                    data: {id: id},
                    cache: false,
                    success: function (response) {
                        $("#updateMenu").html(response);
                        openModal('updateMenu');
                    },
                    error: function () {
                        alert("Errore updateMenu");
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
