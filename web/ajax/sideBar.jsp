<%-- 
    Document   : sideBar
    Created on : 1-giu-2019, 18.24.49
    Author     : Roberto97
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="request" value="<%=request%>"/>
<%@page import="java.time.format.DateTimeFormatter"%>
<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
<div class="logo">
    <a href="https://www.macelleriadellantonio.it" target="_blank" rel="noopener" class="simple-text logo-normal">
        'L Bortoleto
    </a>
</div>
<div class="sidebar-wrapper">
    <ul class="nav navbar-nav nav-mobile-menu">
        <li class="nav-item dropdown">
            <a class="nav-link" href="#pablo" id="navbarDropdownProfile" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="material-icons">person</i>
                <p class="d-lg-none d-md-block">
                    Account
                </p>
            </a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownProfile">
                <a class="dropdown-item" href="logout">Log out</a>
            </div>
        </li>
        <c:set var="notifiche" value="${consoledao.getAllNotifiche()}"/>
        <li class="nav-item dropdown">
            <a class="nav-link" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="material-icons">notifications</i>
                <span class="notification" style="color: #9c27b0; font-weight: 800;">
                    <c:choose>
                        <c:when test="${notifiche eq null || notifiche.isEmpty()}">
                            0
                        </c:when>
                        <c:otherwise>
                            ${notifiche.size()}
                        </c:otherwise>
                    </c:choose>
                </span>
                <p class="d-lg-none d-md-block">
                    Notifiche
                </p>
            </a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
                <c:choose>
                    <c:when test="${notifiche eq null || notifiche.isEmpty()}">
                        <a class="dropdown-item">Nessuna notifica</a>
                    </c:when>
                    <c:otherwise>
                        <div><p class="text-center mt-2 mb-0"><span style="color: #9c27b0; font-weight: 800;">${notifiche.size()}</span> Notifiche</p></div>
                        <div class="dropdown-divider"></div>
                        <a onclick="deleteALLNotifiche();" class="dropdown-item" style="color: red; font-weight: 600; cursor: pointer;">Elimina tutte</a>
                        <c:forEach var="notifica" items="${notifiche}">
                            <a style="cursor: pointer;" class="dropdown-item" onclick="deleteNotifica(${notifica.id}, '${notifica.link}');">${notifica.testo}<span style="color: #9c27b0; font-weight: 900;" class="ml-1 mr-1"> | </span><span style="color: rgb(128, 140, 150);" class="stats">${notifica.data.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd-MM HH:mm"))}</span></a>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
            </div>
        </li>
    </ul>
    <div class="dropdown-divider"></div>
    <ul class="nav">
        <li class="nav-item <c:if test='${param.page.equals("dashboard")}'> active </c:if> ">
                <a class="nav-link" href="./dashboard.jsp">
                    <i class="material-icons">dashboard</i>
                    <p>Dashboard</p>
                </a>
            </li>
            <li class="nav-item <c:if test='${param.page.equals("ordini") || param.page.equals("ordine")}'> active </c:if>">
                <a class="nav-link" href="./ordini.jsp">
                    <i class="material-icons">shopping_cart</i>
                    <p>Ordini</p>
                </a>
            </li>
            <li class="nav-item <c:if test='${param.page.equals("prodotti")}'> active </c:if>">
                <a class="nav-link" href="./prodotti.jsp">
                    <i class="material-icons">content_paste</i>
                    <p>Prodotti</p>
                </a>
            </li>
            <li class="nav-item <c:if test='${param.page.equals("menu")}'> active </c:if>">
                <a class="nav-link" href="./menu.jsp">
                    <i class="material-icons">content_paste</i>
                    <p>Menu Ristorante</p>
                </a>
            </li>
            <li class="nav-item <c:if test='${param.page.equals("articoli") || param.page.equals("articolo")}'> active </c:if>">
                <a class="nav-link" href="./articoli.jsp">
                    <i class="material-icons">library_books</i>
                    <p>Articoli Blog</p>
                </a>
            </li>
            <li class="nav-item <c:if test='${param.page.equals("idee") || param.page.equals("idea")}'> active </c:if>">
                <a class="nav-link" href="./idee.jsp">
                    <i class="material-icons">library_books</i>
                    <p>Idee in cucina</p>
                </a>
            </li>
            <li class="nav-item <c:if test='${param.page.equals("statistiche")}'> active </c:if>">
                <a class="nav-link" href="./statistiche.jsp">
                    <i class="material-icons">bubble_chart</i>
                    <p>Statistiche</p>
                </a>
            </li>
        <c:choose>
            <c:when test="${consoledao.getShopStatus() eq true}">
                <li class="nav-item active-pro" data-toggle="tooltip" title="Attenzione stai per SPEGNERE il negozio!" style="bottom: 6rem !important; width: 100%">
                    <!-- Default checked -->
                    <div id="onOffDiv" class="checkbox" style="width: fit-content; margin: auto auto;">
                        <label>
                            <input onchange="setOFF();" data-size="small" type="checkbox" checked data-toggle="toggle" id="onOff" checked>
                            Spegni il Sito web
                        </label>
                    </div>
                </li>
            </c:when>
            <c:otherwise>
                <li class="nav-item active-pro" data-toggle="tooltip" title="Attenzione stai per ACCENDERE il negozio!" style="bottom: 6rem !important; width: 100%">
                    <!-- Default checked -->
                    <div class="checkbox" style="width: fit-content; margin: auto auto;">
                        <label>
                            <input onchange="setON();" data-size="small" type="checkbox" data-toggle="toggle" id="onOff">
                            Accendi il Sito web
                        </label>
                    </div>
                </li>
            </c:otherwise>
        </c:choose>
        <li class="nav-item active-pro" data-toggle="tooltip" title="Il peso delle immagini che carichi non deve superare i 2.4MB di peso. Vai a questo link per comprimerle.">
            <a class="nav-link" rel="nofollow noopener" href="https://picresize.com/" target="_blank" style="background-color: #fc960e; color: black; font-weight: bold;">
                <i class="material-icons">unarchive</i>
                <p>Comprimi immagini</p>
            </a>
        </li>
    </ul>
</div>
<div class="sidebar-background" style="background-image: url(img/ico/sidebar-1.jpg)"></div>
<div class="modal fade" id="SpegniSitoCheck" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Spegnimento Sito Web</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>Sei sicuro di voler spegnere momentaneamente il sito web. Gli utenti non potranno più utilizzarlo</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                <form action="changeStatus" method="POST">
                    <input type="hidden" name="stato" value="false" />
                    <button type="submit" class="btn btn-primary">Spegni sito web</button>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="AccendiSitoCheck" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Accensione Sito Web</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>Sei sicuro di voler riaccendere il sito web. Gli utenti potranno tornare ad utilizzarlo</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                <form action="changeStatus" method="POST">
                    <input type="hidden" name="stato" value="true" />
                    <button type="submit" class="btn btn-primary">Accendi sito web</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
<script>
</script>
<script>
    function setOFF() {
        $('#SpegniSitoCheck').modal('toggle');
        $('#SpegniSitoCheck').on('hidden.bs.modal', function (e) {
            $('#onOffDiv').html('<label><div class="toggle btn btn-primary btn-sm" data-toggle="toggle" style="width: 82px; height: 29px;"><input onchange="setOFF();" data-size="small" type="checkbox" checked="" data-toggle="toggle" id="onOff"><div class="toggle-group"><label class="btn btn-primary btn-sm toggle-on">On</label><label class="btn btn-default btn-sm active toggle-off">Off</label><span class="toggle-handle btn btn-default btn-sm"></span></div></div>Spegni il Sito web</label>');
        });
    }
    function setON() {
        $('#AccendiSitoCheck').modal('toggle');
        $('#AccendiSitoCheck').on('hidden.bs.modal', function (e) {
            $('#onOffDiv').html('<label><div class="toggle btn btn-sm btn-default off" data-toggle="toggle" style="width: 82px; height: 29px;"><input onchange="setON();" data-size="small" type="checkbox" checked="" data-toggle="toggle" id="onOff"><div class="toggle-group"><label class="btn btn-primary btn-sm toggle-on">On</label><label class="btn btn-default btn-sm active toggle-off">Off</label><span class="toggle-handle btn btn-default btn-sm"></span></div></div>Spegni il Sito web</label>');
        });
    }

    function deleteALLNotifiche() {
        $.ajax({
            type: "POST",
            url: "deleteALLNotifiche",
            cache: false,
            success: function () {
                location.reload();
            },
            error: function () {
                alert("Errore delete all notifiche");
            }
        });
    }
    function deleteNotifica(id, link) {
        $.ajax({
            type: "POST",
            url: "deleteNotifica",
            data: {id: id},
            cache: false,
            success: function () {
                location.href = link;
            },
            error: function () {
                alert("Errore delete notifica");
            }
        });
    }
</script>