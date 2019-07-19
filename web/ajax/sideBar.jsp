<%-- 
    Document   : sideBar
    Created on : 1-giu-2019, 18.24.49
    Author     : Roberto97
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.time.format.DateTimeFormatter"%>

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
            <a class="nav-link" href="http://example.com" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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
        <%--<li class="nav-item <c:if test='${param.page.equals("")}'> active </c:if>">
            <a class="nav-link" href="./map.html">
                <i class="material-icons">location_ons</i>
                <p>Maps</p>
            </a>
        </li>
        <li class="nav-item <c:if test='${param.page.equals("")}'> active </c:if>">
            <a class="nav-link" href="./notifications.html">
                <i class="material-icons">notifications</i>
                <p>Notifications</p>
            </a>
        </li>
        <li class="nav-item <c:if test='${param.page.equals("")}'> active </c:if>">
        <a class="nav-link" href="./rtl.html">
            <i class="material-icons">language</i>
            <p>RTL Support</p>
        </a>
    </li>--%>
        <!--<li class="nav-item active-pro ">
            <a class="nav-link" href="./upgrade.html">
                <i class="material-icons">unarchive</i>
                <p>Upgrade to PRO</p>
            </a>
        </li>-->
    </ul>
</div>
<div class="sidebar-background" style="background-image: url(img/ico/sidebar-1.jpg)"></div>

<script>
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