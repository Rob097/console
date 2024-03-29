<%-- 
    Document   : footer
    Created on : 13-lug-2019, 12.02.19
    Author     : Roberto97
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="year" value="${now}" pattern="yyyy" />

<div class="container-fluid">
    <nav class="float-left">
        <ul>
            <li>
                <a target="_blank" rel="noopener" href="/../Bortoleto/bottega-online/">
                    Bottega Online
                </a>
            </li>
            <li>
                <a target="_blank" rel="noopener" href="/../Bortoleto/La-Macelleria">
                    Macelleria
                </a>
            </li>
            <li>
                <a target="_blank" rel="noopener" href="/../Bortoleto/Il-Ristorante">
                    Ristorante
                </a>
            </li>            
            <li>
                <a target="_blank" rel="noopener" href="/../Bortoleto/Il-Blog/">
                    Blog
                </a>
            </li>
            <li>
                <a target="_blank" rel="noopener" href="/../Bortoleto/Idee-in-Cucina/">
                    Idee
                </a>
            </li>
        </ul>
    </nav>
    <div class="copyright float-right">
        &copy; ${year}  Macelleria Dellantonio,  made with <i class="material-icons">favorite</i> by <a href="mailto:dellantonio47@gmail.com">Dellantonio Roberto</a>. All rights reserved
    </div>
</div>