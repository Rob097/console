<%-- 
    Document   : navbar
    Created on : 11-lug-2019, 18.14.35
    Author     : Roberto97
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="java.time.format.DateTimeFormatter"%>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-transparent navbar-absolute fixed-top ">
    <div class="container-fluid">
        <div class="navbar-wrapper">
            <a id="topPage" class="navbar-brand" href="#pablo">${param.page}</a>
        </div>
        <button class="navbar-toggler" type="button" data-toggle="collapse" aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation">
            <span class="sr-only">Toggle navigation</span>
            <span class="navbar-toggler-icon icon-bar"></span>
            <span class="navbar-toggler-icon icon-bar"></span>
            <span class="navbar-toggler-icon icon-bar"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end">
            <ul class="navbar-nav">
                <c:set var="notifiche" value="${consoledao.getAllNotifiche()}"/>
                <li class="nav-item dropdown">
                    <a class="nav-link" href="http://example.com" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="material-icons">notifications</i>
                        <span class="notification">
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
            </ul>
        </div>
    </div>
</nav>

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