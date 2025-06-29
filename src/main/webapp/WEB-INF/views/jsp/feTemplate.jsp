<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel=icon" href="<%=request.getContextPath()%>/static/back-office/favicon.ico" />
        <title><tiles:insertAttribute name="title" /></title>



                   <link rel="stylesheet" href="${pageContext.request.contextPath}/static/back-office/css/theme-default.css" rel="stylesheet" type="text/css" media="screen" />
                   <link rel="stylesheet" href="${pageContext.request.contextPath}/static/front-end/css/rhp-style.css" rel="stylesheet" type="text/css" media="screen" />
                   <link rel="stylesheet" href="${pageContext.request.contextPath}/static/back-office/css/mcustomscrollbar/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css" media="screen" />
                   <link rel="stylesheet" href="${pageContext.request.contextPath}/static/back-office/css/site.css" rel="stylesheet" type="text/css" media="screen" />
                   <link rel="stylesheet" href="${pageContext.request.contextPath}/static/back-office/assets/select2/select2.css" rel="stylesheet" type="text/css" media="screen" />
                   <link rel="stylesheet" href="${pageContext.request.contextPath}/static/back-office/css/bootstrap/bootstrap-glyphicons.css" rel="stylesheet" type="text/css" media="screen" />
                    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/back-office/assets/bootstrap-table/src/bootstrap-table.css" rel="stylesheet" type="text/css" media="screen" />



                   <script src="${pageContext.request.contextPath}/static/back-office/js/plugins/jquery/jquery.min.js"></script>
                   <script src="${pageContext.request.contextPath}/static/back-office/js/plugins/jquery/jquery-ui.min.js"></script>
                   <script src="${pageContext.request.contextPath}/static/back-office/js/plugins/bootstrap/bootstrap.min.js"></script>
                   <script src="${pageContext.request.contextPath}/static/back-office/js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
                   <script src="${pageContext.request.contextPath}/static/back-office/js/plugins/scrolltotop/scrolltopcontrol.js"></script>
                   <script src="${pageContext.request.contextPath}/static/back-office/assets/select2/select2.min.js"></script>

                   <script src="${pageContext.request.contextPath}/static/back-office/js/plugins/bootstrap/bootstrap-select.js"></script>
                  <script src="${pageContext.request.contextPath}/static/back-office/assets/bootstrap-table/dist/bootstrap-table.js"></script>
                   <script src="${pageContext.request.contextPath}/static/back-office/js/plugins/bootstrap/bootstrap-datepicker.js"></script>
                   <script src="${pageContext.request.contextPath}/static/back-office/assets/bootstrap-table/tableExport.min.js"></script>
                   <script src="${pageContext.request.contextPath}/static/back-office/assets/bootstrap-table/dist/extensions/export/bootstrap-table-export.min.js"></script>
                   <script src="${pageContext.request.contextPath}/static/back-office/js/plugins/morris/raphael-min.js"></script>
                   <script src="${pageContext.request.contextPath}/static/back-office/js/plugins/morris/morris.min.js"></script>
                   <script src="${pageContext.request.contextPath}/static/back-office/js/plugins/icheck/icheck.min.js"></script>
                     <script src="${pageContext.request.contextPath}/static/back-office/js/Chart.js"></script>
                     <!-- <script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/Chart.min.js"></script>-->
                      <!--  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>-->
                   <script src="${pageContext.request.contextPath}/static/front-end/js/jquery.datetimepicker.full.min.js"></script>
                  <script src="${pageContext.request.contextPath}/static/front-end/js/bootstrap-table.js"></script>
                   <script src="${pageContext.request.contextPath}/static/front-end/js/underscore-min.js"></script>
                   <script src="${pageContext.request.contextPath}/static/back-office/js/plugins.js"></script>
                   <script src="${pageContext.request.contextPath}/static/back-office/js/actions.js"></script>
                   <script src="${pageContext.request.contextPath}/static/front-end/js/angular.js"></script>

        <!-- Core stylesheets do not remove -->

<style>
        .panel-custom {
            border-color: #33414e;
        }
        .panel-custom > .panel-heading {
            background-color: #33414e;
            color: white;
            border-color: #33414e;
        }
        .panel-custom > .panel-body {
            background-color: #f8f9fa; /* Couleur de fond du contenu */
        }
        .page-container {
            display: flex;
        }

        .page-sidebar {
            width: 250px; /* Ajustez selon vos besoins */
            background-color: #f8f9fa; /* Couleur de fond pour le menu */
        }

        .page-content {
            flex: 1; /* Prend le reste de l'espace */
            padding: 20px; /* Ajoutez un padding pour éviter le chevauchement */
        }
    </style>
</head>


<body id="rhpaieApp" ng-app="rhpaieApp" >
    <script type="text/javascript">
        var app = angular.module('rhpaieApp', []);
        var baseUrl = "<%=request.getContextPath() %>";
    </script>

    <div class="page-container">

        <tiles:insertAttribute name="menu" />

         <div class="page-content">
             <tiles:insertAttribute name="header" />
             <tiles:insertAttribute name="contentHeader" />
             <div class="page-content-wrap" >
                 <tiles:insertAttribute name="content" />
                 <tiles:insertAttribute name="footer" />
             </div>
         </div>

    </div>
    <audio id="audio-alert" src="${pageContext.request.contextPath}/back-office/audio/alert.mp3" preload="auto"></audio>
    <audio id="audio-fail" src="${pageContext.request.contextPath}/back-office/audio/fail.mp3" preload="auto"></audio>


    <script type="text/javascript">
        //AngularJS


app.controller('contratStatCtrl', function ($scope, $http) {
    $scope.contratAterme = {nombre: 0, contrats: []};

    $http.get(baseUrl + "/personnels/listexpirecontratpersonneldays")
        .then(function (response) {
            $scope.contratAterme.nombre = response.data.length;
            $scope.contratAterme.contrats = response.data;
        }, function (error) {
            console.error("Erreur lors du chargement des contrats expirés :", error);
        });
});



        //End AngularJs
    </script>
</body>
</html>