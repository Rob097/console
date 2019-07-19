<%-- 
    Document   : login
    Created on : 19-mag-2019, 11.42.22
    Author     : Roberto97
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="request" value="<%=request%>"/> <!-- Request lo chiamo con una scriplets e lo salvo in una variabile perchè serve per diversi metodi java -->
<c:set var="response" value="<%=response%>"/> <!-- Response lo chiamo con una scriplets e lo salvo in una variabile perchè serve per diversi metodi java -->
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" href="https://lh3.googleusercontent.com/1nJwqw8n93uSSVkiOcuosGxA84pLvNAH5WDakvcRHohk2ccrL0SmxBlHB87WOxZXcWkD2ToK0YmNzspklIqHjZI8XQcVFfiDhpawN03k_rwm2pARMbFxIFSQiI3fvlC529-UVTMNbg=w2400" sizes="16x16" type="image/png" alt="dashboard">
        <title>Console Macelleria Dellantonio</title>
        <meta name="Description" content="Dashboard Macelleria Dellantonio">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Bootstrap CSS CDN -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!-- Font Awesome JS -->
        <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js" integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ" crossorigin="anonymous"></script>
        <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js" integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY" crossorigin="anonymous"></script>


        <!-- fine bootstrap include -->
        <link href="css/mdb.min.css" rel="stylesheet">
        <style>  
            #containerLogin{
                margin: auto auto;
                background-color: rgba(134, 62, 62, 0.82);
                color: white;
                border-radius: 30px;
                box-shadow: 0px 0px 100px 0px black;
                border: none;
                width: 100%;
                max-width: 450px;
                padding: 20px;
                margin: auto;
            }
            html,
            body {
                height: 100%;
            }

            body {
                display: -ms-flexbox;
                display: flex;
                -ms-flex-align: center;
                align-items: center;
                padding-top: 40px;
                padding-bottom: 40px;
                background-color: #f5f5f5;
                background-image: url(https://lh3.googleusercontent.com/ZGUA2SSJE0oy5KOF5i72_NdgsgDPryUD_xbTQF8Rui3zp1dHmfMq0kd9rKFGABIa6SaYwDyvrgeu1LuHZIlvSBwuuKAFc97ofXY4yo5GtmkEJ-nbKuXdiAZUN2vI0d-R7_5RXpf1bw=w2400);
                background-attachment: fixed;
                background-repeat: no-repeat;
                background-size: cover;
                background-position: center center;
            }

            .form-signin {
                width: 100%;
                max-width: 330px;
                padding: 15px;
                margin: auto;
            }
            .form-signin .checkbox {
                font-weight: 400;
            }
            .form-signin .form-control {
                position: relative;
                box-sizing: border-box;
                height: auto;
                padding: 10px;
                font-size: 16px;
            }
            .form-signin .form-control:focus {
                z-index: 2;
            }
            .form-signin input[type="email"] {
                margin-bottom: -1px;
                border-bottom-right-radius: 0;
                border-bottom-left-radius: 0;
            }
            .form-signin input[type="password"] {
                margin-bottom: 10px;
                border-top-left-radius: 0;
                border-top-right-radius: 0;
            }
            .invalid-feedback{
                color: white !important;
            }
        </style>
    </head>
    <body class="text-center">
        <div id="containerLogin">
            <form class="form-signin needs-validation" novalidate method="POST" action="login">
                <img class="mb-4" src="https://lh3.googleusercontent.com/1nJwqw8n93uSSVkiOcuosGxA84pLvNAH5WDakvcRHohk2ccrL0SmxBlHB87WOxZXcWkD2ToK0YmNzspklIqHjZI8XQcVFfiDhpawN03k_rwm2pARMbFxIFSQiI3fvlC529-UVTMNbg=w2400" alt="LogoMacelleria" style='width: 50%;'>
                <h1 class="h3 mb-3 font-weight-normal">Accedi</h1>

                <div class="mb-3">
                    <input type="email" class="form-control" id="inputEmail" name="email" placeholder="Email" required autofocus>
                    <div class="invalid-feedback">
                        Il campo relativo all'email non è complilato in modo corretto
                    </div>
                </div>
                <div class="mb-3">
                    <input type="password" class="form-control" id="inputPassword" name="password" placeholder="Password" required>
                    <div class="invalid-feedback">
                        Il campo relativo alla password non è complilato in modo corretto
                    </div>
                </div>
                <div class="checkbox mb-3">
                    <label>
                        <input type="checkbox" name="ricordami"> Ricordami
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block mb-3" type="submit">Accedi</button>                          
            </form>

            <c:if test="${LOGGED eq false}">
                <div class="besideemailbox">Attenzione, l'email o la password sono sbagliate</div>
                ${LOGGED = null}
            </c:if>
        </div>

        <script>
            (function () {
                'use strict';

                window.addEventListener('load', function () {
                    // Fetch all the forms we want to apply custom Bootstrap validation styles to
                    var forms = document.getElementsByClassName('needs-validation');

                    // Loop over them and prevent submission
                    var validation = Array.prototype.filter.call(forms, function (form) {
                        form.addEventListener('submit', function (event) {
                            if (form.checkValidity() === false) {
                                event.preventDefault();
                                event.stopPropagation();
                            } else {
                            }
                            form.classList.add('was-validated');
                        }, false);
                    });
                }, false);
            })();
        </script>
    </body>
</html>
