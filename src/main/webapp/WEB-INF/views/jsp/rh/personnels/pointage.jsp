<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
    <div class="col-md-12">
        <div class="panel panel-warning">
            <div class="panel-heading">
                <div class="panel-title-box">
                    <h3>Liste de Presence</h3>
                    <span></span>
                </div>
                <ul class="panel-controls" style="margin-top: 2px;">
                    <li><a href="#" class="panel-fullscreen"><span class="fa fa-expand"></span></a></li>
                    <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="fa fa-cog"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#" class="panel-collapse"><span class="fa fa-angle-down"></span> Collapse</a></li>
                            <li><a href="#" class="panel-remove"><span class="fa fa-times"></span> Remove</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div class="panel-body panel-body-table">
                <div class="row">

                    <div class="col-md-12">
                        <div class="fixed-table-toolbar">
                            <div class="bars">
                                <div class="btn-group">
                                    <a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">Action <span class="caret"></span></a>
                                    <ul class="dropdown-menu" role="menu">
                                        <%--<li role="presentation" class="dropdown-header">Dropdown header</li>--%>
                                        <%--<li><a href="#" data-toggle="modal" data-target="#rhpModal">Nouveau</a></li>--%>
                                    </ul>
                                </div>

                            </div>
                            <div id="tableWidget" class="widgetcontent">
                                <div id="toolbar">
                                    <div class="row">
                                        <div class="col-md-4" style="min-width: 250px;">
                                            <div class="input-group">
                                                <input type="text" id="datePointage" class="form-control datetimePicker" />
                                                <span class="input-group-btn">
                                <button id="btnCharger" class="btn btn-success" type="button">Afficher</button>
                            </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <table id="table" class="table table-info table-striped"
                                       data-toggle="table"
                                       data-toolbar="#toolbar"
                                       data-click-to-select="true"
                                       data-single-select="true"
                                       data-unique-id="id"
                                       data-sort-name="intitule" data-sort-order="desc"
                                       data-url="${pageContext.request.contextPath}/personnels/listpersonneljson"
                                       data-side-pagination="server"
                                       data-pagination="true"
                                       data-page-list="[5, 10, 20, 50, 100, 200]"
                                       data-search="false">
                                    <thead>
                                    <tr>
                                        <th data-field="matricule" data-align="left">Matricule</th>
                                        <th data-field="nomComplet" data-align="left">Nom</th>
                                        <th data-field="sexe" data-align="left">Sexe</th>
                                        <th data-field="dNaissance" data-align="center">N&eacute;(e) le</th>
                                        <th data-field="pointage" data-formatter="heureArriveeFormatter" data-width="100px">Heure d'arriv&eacute;e</th>
                                        <th data-field="pointage" data-formatter="heurePauseFormatter" data-width="100px">Heure de pause</th>
                                        <th data-field="pointage" data-formatter="heureRepriseFormatter" data-width="100px">Heure de reprise</th>
                                        <th data-field="pointage" data-formatter="heureDepartFormatter" data-width="100px">Heure de depart</th>
                                        <th data-field="pointage" data-formatter="descriptionFormatter">observations</th>
                                        <th data-field="id" data-formatter="optionFormatter" data-width="90px" data-valign="middle" data-align="center">Options</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div><!--widgetcontent-->
                        </div><!--widgetbox-->
                    </div><!-- widgetcontent-->
                </div>
            </div>
        </div>

        <div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form id="formAjout" class="form-horizontal" role="form" novalidate="novalidate" ng-controller="formAjoutCtrl">
                        <div class="modal-header">
                            <!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                            <h4 class="modal-title" id="myModalLabel">Th&egrave;me</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="intitule" class="col-md-2 control-label">Intitul&eacute;</label>
                                <div class="col-md-10">
                                    <input type="text" id="intitule" name="intitule" ng-model="theme.intitule" class="form-control" placeholder="Intitul�" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="description" class="col-md-2 control-label">Description</label>
                                <div class="col-md-10">
                            <textarea class="form-control" id="description" name="description" ng-model="theme.description" placeholder="Description">

                            </textarea>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="theme.id">
                            <span></span>&nbsp;
                            <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                            <button type="submit" class="btn btn-success">Valider</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal deleteModal  fade bs-delete-modal-static" role="dialog" data-backdrop="static">
            <div class="modal-dialog ">
                <div class="modal-content">
                    <form id="formDelete" ng-controller="formDeleteCtrl" action="#" method="post">
                        <div class="modal-header ">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <span class="circle bg-danger">
                        <i class="fa fa-question-circle"></i>
                        Etes vous s�r de vouloir supprimer ?
                    </span>
                        </div>
                        <div class="modal-body">
                            <h4 ng-bind="theme.info"></h4>
                        </div>
                        <div class="modal-footer">
                            <input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="theme.id">
                            <span></span>&nbsp;
                            <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                            <button type="submit" class="btn btn-success">Valider</button>
                        </div>
                </div>
                </form>
            </div>
        </div>

        <script type="text/javascript">
            //AngularJS
            app.controller('formAjoutCtrl', function ($scope) {
                $scope.pupulateForm = function (theme) {
                    $scope.theme = theme;
                };
                $scope.initForm = function () {
                    $scope.theme = {};
                };
            }).controller('formDeleteCtrl', function ($scope) {
                $scope.pupulateForm = function (theme) {
                    $scope.theme = theme;
                };

            });
            //End AngularJs


            var actionUrl = "/formation/enregistrertheme";
            var actionDeleteUrl = "/formation/supprimertheme";
            var action = "ajout";
            var $table, listePersonnel = [];
            jQuery(document).ready(function ($) {
                $.datetimepicker.setLocale('fr');
                $table = $('#table');
                $table.on('load-success.bs.table', function () {
                    listePersonnel = $.map($table.bootstrapTable('getData'), function (personnel) {
                        return personnel;
                    });

                    var datePointage = $("#datePointage").val();
                    loadPointagePersonnelParDate(listePersonnel, datePointage);
                });

                $table.on('post-body.bs.table', function () {
                    $('.timePicker').datetimepicker({
                        datepicker: false,
                        formatTime: 'H:i',
                        format: 'H:i'
                        //value: new Date()
                    });
                });

                $("#btnCharger").click(function () {
                    var datePointage = $("#datePointage").val();
                    loadPointagePersonnelParDate(listePersonnel, datePointage);
                });

                $('.datetimePicker').datetimepicker({
                    timepicker: false,
                    formatDate: 'd/m/Y',
                    format: 'd/m/Y',
                    value: new Date()
                });
                //Fermeture du modal
                $('#rhpModal').on('hidden.bs.modal', function () {
                    var $scope = angular.element(document.getElementById("formAjout")).scope();
                    $scope.$apply(function () {
                        $scope.initForm();
                    });
                    //$("#id").val(""); //Initialisation de l'id
                });

                //Envoi des donnees
                $("#formAjout").submit(function (e) {
                    e.preventDefault();

                    var formData = $(this).serialize();
                    console.log("form", formData);
                    $.ajax({
                        type: "POST",
                        url: baseUrl + actionUrl,
                        cache: false,
                        data: formData,
                        success: function (reponse) {
                            if (reponse.result && reponse.status) {
                                if (action == "ajout") {
                                    //Rechargement de la liste (le tableau)
                                    $table.bootstrapTable('refresh');
                                    //$("#formAjout")[0].reset(); //Initialisation du formulaire
                                    $("#rhpModal").modal('hide');
                                } else {
                                    //MAJ de la ligne modifi�e
                                    $table.bootstrapTable('updateRow', {
                                        index: $table.find('tr.selected').data('index'),
                                        row: reponse.data
                                    });
                                }
                            } else {
                                alert(reponse.message);
                            }
                        },
                        error: function () {
                            $("#rhpModal .modal-body div.alert").alert();
                            $("#rhpModal .modal-body .alert h4").html("Erreur survenue");
                            $("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
                            $("#rhpModal .modal-footer span").removeClass('loader');
                        },
                        beforeSend: function () {
                            $("#formAjout").attr("disabled", true);
                            $("#rhpModal .modal-footer span").addClass('loader');
                        },
                        complete: function () {
                            $("#formAjout").removeAttr("disabled");
                            $("#rhpModal .modal-footer span").removeClass('loader');
                        }
                    });
                });

                $("#formDelete").submit(function (e) {
                    e.preventDefault();
                    var formData = $(this).serialize();
                    var idSup = [];
                    //Le formulaire formDelete doit avoir un seul champ input:text
                    idSup.push(parseInt($("#formDelete :text").val()));

                    $.ajax({
                        type: "POST",
                        url: baseUrl + actionDeleteUrl,
                        cache: false,
                        data: formData,
                        success: function (reponse) {
                            if (reponse.result == true) {
                                $table.bootstrapTable('remove', {
                                    field: 'id',
                                    values: idSup
                                });
                                $(".deleteModal").modal('hide');
                            } else if (reponse.result == "erreur_champ_obligatoire") {
                                alert("Saisie invalide");
                            }
                        },
                        error: function (err) {
                            $(".deleteModal .modal-body div.alert").alert();
                            $(".deleteModal .modal-body .alert h4").html("Erreur survenue");
                            $(".deleteModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
                            $(".deleteModal .modal-footer span").removeClass('loader');
                        },
                        beforeSend: function () {
                            $("#formDelete").attr("disabled", true);
                            $(".deleteModal .modal-footer span").addClass('loader');
                        },
                        complete: function () {
                            $("#formDelete").removeAttr("disabled");
                            $(".deleteModal .modal-footer span").removeClass('loader');
                        }
                    });
                });
            });

            //Functions
            function heureArriveeFormatter(pointage) {
                return pointage ? '<input type="text" class="form-control input-small timePicker" name="heureArrivee" value="' + pointage.hArrivee + '">' : '<input type="text" class="form-control input-small timePicker" name="heureArrivee" placeholder="hh:mm">';
            }

            function heurePauseFormatter(pointage) {
                return pointage ? '<input type="text" class="form-control input-small timePicker" name="heurePause" value="' + pointage.hPause + '">' : '<input type="text" class="form-control input-small timePicker" name="heurePause" placeholder="hh:mm">';
            }
            function heureRepriseFormatter(pointage) {
                return pointage ? '<input type="text" class="form-control input-small timePicker" name="heureReprise" value="' + pointage.hReprise + '">' : '<input type="text" class="form-control input-small timePicker" name="heureReprise" placeholder="hh:mm">';
            }

            function heureDepartFormatter(pointage) {
                return pointage ? '<input type="text" class="form-control input-small timePicker" name="heureDepart" value="' + pointage.hDepart + '">' : '<input type="text" class="form-control input-small timePicker" name="heureDepart" placeholder="hh:mm">';
            }

            function descriptionFormatter(pointage) {
                return pointage ? '<input type="text" class="form-control input-small" name="description" value="' + pointage.description + '">' : '<input type="text" class="form-control input-small" name="description" placeholder="Observation">';
            }

            function matriculeFormatter(personnel) {
                return personnel.matricule;
            }

            function nomCompletFormatter(personnel) {
                return personnel.nomComplet;
            }

            function sexeFormatter(personnel) {
                return personnel.sexe;
            }

            function telephoneFormatter(personnel) {
                return personnel.telephone;
            }

            function optionFormatter(id, row, index) {
                var actionTitle, icon;
                if (row.pointage) {
                    actionTitle = "Modifier";
                    icon = 'glyphicon-pencil';
                } else {
                    actionTitle = "Enregistrer";
                    icon = 'glyphicon-save';
                }
                var idPointage = row.pointage ? row.pointage.id : 0;
                //var option = '<a onclick="edit(' + row.id + ')" data-toggle="modal" data-target="#rhpModal" href="#" title="'+actionTitle+'">  <span class="glyphicon '+icon+'"></span></a>&nbsp;';
                var option = '<span></span><button class="btn btn-success" onclick="edit(' + row.id + ',' + idPointage + ',' + index + ', this)" title="' + actionTitle + '"><span class="glyphicon ' + icon + '"></span> Valider</button>';
                return option;
            }

            function edit(idPersonnel, idPointage, index, currentButton) {

                var currentPointage = _.findWhere($table.bootstrapTable('getData'), {id: idPointage});
                //  alert(currentPointage);
                var $btnCurrent = jQuery(currentButton);
                var $currentTr = jQuery("tr[data-uniqueid='" + idPersonnel + "']"), dataPointage = {};
                var $loaderPointage = $currentTr.find('span');
                dataPointage.idPersonnel = idPersonnel;
                dataPointage.id = idPointage ? idPointage : "";
                dataPointage.datePointage = jQuery("#datePointage").val();
                dataPointage.heureArrivee = $currentTr.find('input[name="heureArrivee"]').val() ? dataPointage.datePointage + ' ' + $currentTr.find('input[name="heureArrivee"]').val() : "";
                dataPointage.heurePause = $currentTr.find('input[name="heurePause"]').val() ? dataPointage.datePointage + ' ' + $currentTr.find('input[name="heurePause"]').val() : "";
                dataPointage.heureReprise = $currentTr.find('input[name="heureReprise"]').val() ? dataPointage.datePointage + ' ' + $currentTr.find('input[name="heureReprise"]').val() : "";
                dataPointage.heureDepart = $currentTr.find('input[name="heureDepart"]').val() ? dataPointage.datePointage + ' ' + $currentTr.find('input[name="heureDepart"]').val() : "";
                dataPointage.description = $currentTr.find('input[name="description"]').val();
                jQuery.ajax({
                    url: baseUrl + "/personnel/enregistrerpointage",
                    type: "POST",
                    cache: false,
                    data: dataPointage,
                    success: function (reponse) {
                        var contratPersonnel = {}, indexRow = jQuery("tr[data-uniqueid='" + idPersonnel + "']").data("index");
                        alert(indexRow);
                        contratPersonnel.personnel = idPersonnel;
                        contratPersonnel.pointage = reponse.row || currentPointage;
                        $table.bootstrapTable('updateRow', {
                            index: indexRow,
                            row: contratPersonnel
                        });
                    },
                    beforeSend: function () {
                        $btnCurrent.hide();
                        $loaderPointage.addClass('loader');
                    },
                    error: function () {
                        $loaderPointage.removeClass('loader');
                    },
                    complete: function () {
                        $loaderPointage.removeClass('loader');
                        $btnCurrent.show();
                        //  $table.bootstrapTable('prepend');
                        // 'prepend', reponse.row
                    }
                });
            }

            function del(idPointage) {
                var $scope = angular.element(document.getElementById("formDelete")).scope();

                var rows = $table.bootstrapTable('getData');
                var theme = _.findWhere(rows, {id: idPointage});
                theme.info = theme.intitule;
                $scope.$apply(function () {
                    $scope.pupulateForm(theme);
                });
            }


            function loadPointagePersonnelParDate(listePersonnel, datePointage) {
                var tabIds = jQuery.map(listePersonnel, function (personnel) {
                    return personnel.id;
                });

                var idsPersonnel = tabIds.join(' '), listSize = tabIds.length;
                jQuery.ajax({
                    type: "POST",
                    url: baseUrl + "/personnel/listerpointagesparlistpersonneletdate",
                    cache: false,
                    data: {listPersonnel: idsPersonnel, listSize: listSize, datePointage: datePointage},
                    success: function (reponse) {
                        if (reponse.result && reponse.status) {
                            var listePointages = reponse.rows || [];

                            if (reponse.rows.length > 0) {
                                var tabPersonnelPointageIds = jQuery.map(listePointages, function (pointage) {
                                    return pointage ? {idPersonnel: pointage.personnel.id, idPointage: pointage.id} : null;
                                });

                                jQuery.each(listePersonnel, function (index, personnel) {
                                    var personnelPointage = _.findWhere(tabPersonnelPointageIds, {idPersonnel: personnel.id});
                                    var pointage = null;
                                    if (personnelPointage) {
                                        pointage = _.findWhere(listePointages, {id: personnelPointage.idPointage});
                                    }

                                    personnel.pointage = pointage;
                                    var indexRow = jQuery("tr[data-uniqueid='" + personnel.id + "']").data("index");
                                    $table.bootstrapTable('updateRow', {
                                        index: indexRow,
                                        row: personnel
                                    });
                                });
                            }
                        } else {
                            if (reponse.errors[0].message) {
                                alert(reponse.errors[0].message);
                            }
                        }
                    },
                    beforeSend: function () {
                        $table.bootstrapTable('showLoading');
                    },
                    error: function () {
                        $table.bootstrapTable('hideLoading');
                    },
                    complete: function () {
                        $table.bootstrapTable('hideLoading');
                    }
                });
            }

        </script>

