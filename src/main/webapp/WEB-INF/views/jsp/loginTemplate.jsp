<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE html>
<html class="body-full-height">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title><tiles:insertAttribute name="title" /></title>

    <link rel="icon" type="image/x-icon" href="<c:url value='/static/back-office/favicon.ico'/>">

   <link rel="stylesheet" href="<%=request.getContextPath()%>/static/back-office/css/theme-default.css" rel="stylesheet" type="text/css" media="screen" />

    <script>
        var baseUrl = "<c:url value='/'/>";
        jQuery(document).ready(function () {
            jQuery('#login').submit(function () {
                var u = jQuery('#username').val();
                var p = jQuery('#password').val();
                if (u === '' || p === '') {
                    jQuery('.login-alert').fadeIn();
                    return false;
                }
            });
        });
    </script>
</head>

<body>
    <div class="login-container">
        <div class="login-box animated fadeInDown">
            <div class="login-logo"></div>
            <div class="login-body">
                <div class="login-title"><strong>Bienvenue</strong>, Authentification</div>

                <!-- Message d'alerte -->
                <div class="login-alert" style="display: none; color: red;">Veuillez remplir tous les champs.</div>

                <tiles:insertAttribute name="content" />
            </div>
            <div class="login-footer">
                <div class="pull-left">
                    &copy; 2025 SMART PAIE
                </div>
                <div class="pull-right">
                    <a href="#">About</a> |
                    <a href="#">Privacy</a> |
                    <a href="#">Contact Us</a>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
