<%-- 
    Document   : sideBar
    Created on : 1-giu-2019, 18.24.49
    Author     : Roberto97
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="logo">
    <a href="https://www.macelleriadellantonio.it" target="_blank" class="simple-text logo-normal">
        'L Bortoleto
    </a>
</div>
<div class="sidebar-wrapper">
    <ul class="nav">
        <li class="nav-item <c:if test='${param.page.equals("dashboard")}'> active </c:if> ">
            <a class="nav-link" href="./dashboard.jsp">
                <i class="material-icons">dashboard</i>
                <p>Dashboard</p>
            </a>
        </li>
        <li class="nav-item <c:if test='${param.page.equals("")}'> active </c:if>">
            <a class="nav-link" href="./user.html">
                <i class="material-icons">person</i>
                <p>Profilo</p>
            </a>
        </li>
        <li class="nav-item <c:if test='${param.page.equals("prodotti")}'> active </c:if>">
            <a class="nav-link" href="./prodotti.jsp">
                <i class="material-icons">content_paste</i>
                <p>Prodotti</p>
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
        <li class="nav-item <c:if test='${param.page.equals("")}'> active </c:if>">
            <a class="nav-link" href="./icons.html">
                <i class="material-icons">bubble_chart</i>
                <p>Icons</p>
            </a>
        </li>
        <li class="nav-item <c:if test='${param.page.equals("")}'> active </c:if>">
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
        </li>
        <li class="nav-item active-pro ">
            <a class="nav-link" href="./upgrade.html">
                <i class="material-icons">unarchive</i>
                <p>Upgrade to PRO</p>
            </a>
        </li>
    </ul>
</div>