<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="widget">
    <div class="widgetbox">                        
        <div class="headtitle">
            <div class="btn-group">
                <button data-toggle="dropdown" class="btn dropdown-toggle">Action <span class="caret"></span></button>
                <ul class="dropdown-menu">
                    <li><a href="#">Listes</a></li>
                    <li class="divider"></li>
                    <li><a href="#" data-toggle="modal" data-target="#rhpModal">Nouveau</a></li>
                </ul>
            </div>
            <h4 id="widgetTitle" class="widgettitle">Liste des missions</h4>
        </div>
        <div id="tableWidget" class="widgetcontent">
            <table id="table" class="table table-info table-striped"
                   data-toggle="table"
                   data-click-to-select="true"
                   data-single-select="true"
                   data-sort-name="libelle" data-sort-order="desc"
                   data-url="${pageContext.request.contextPath}/absence/paginermission"
                   data-side-pagination="server"
                   data-pagination="true"
                   data-page-list="[5, 10, 20, 50, 100, 200]"
                   data-search="true">
                <thead>
                    <tr>
                        <th data-field="service" data-align="left" data-sortable="true">Service</th>
                        <th data-field="natureMission" data-align="left" data-sortable="true">Mission</th>
                        <%--<th data-field="lieuMission" data-align="left" data-sortable="true">Lieu</th>--%>
                        <th data-field="titreAutorite" data-align="left" data-sortable="true">Autorit&eacute;</th>
                        <th data-field="id" data-formatter="optionFormatter" data-width="100px" data-align="center">Options</th>
                    </tr>
                </thead>
            </table>
        </div><!--widgetcontent-->
    </div><!--widgetbox-->
</div><!-- widgetcontent-->

<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document" style="width:50%;">
        <div class="modal-content">
            <form id="formAjout" class="form-horizontal" role="form" novalidate="novalidate" ng-controller="formAjoutCtrl">
                <div class="modal-header">
                    <!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Missions</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                    <div class="form-group">

                        <div class="col-md-12">
                            <label  class=" control-label"> Mission </label>
                         <textarea name="natureMission" id="natureMission" cols="100" rows="4"  ng-model="mission.natureMission"></textarea>
                        </div>
                    </div>
                    </div>
                     <div class="row">
                    <div class="form-group">

                        <div class="col-md-6">
                            <label for="description" class=" control-label">Ordonnateur</label>
                            <input type="text" class="form-control" id="service" name="service" ng-model="mission.service" placeholder="Ordonnateur" />
                       </div>
                        <div class="col-md-6">
                            <label  class=" control-label">Imputation Budgetaire</label>
                            <input type="text" class="form-control" id="ImputationBudg" name="ImputationBudg" ng-model="mission.imputationBudg" placeholder="Imputation Budgetaire" />
                        </div>
                    </div>
                    </div>
                    <%--<div class="row">--%>
                        <%--<div class="form-group">--%>

                            <%--<div class="col-md-6">--%>
                                <%--<label  class=" control-label">Moyen de Transport</label>--%>
                                <%--<input type="text" class="form-control" id="moyenTransp" name="moyenTransp" ng-model="mission.moyenTransp" placeholder="Moyen de Transport" />--%>
                            <%--</div>--%>
                            <%--<div class="col-md-6">--%>
                                <%--<label  class=" control-label">Imputation Budgetaire</label>--%>
                                <%--<input type="text" class="form-control" id="ImputationBudg" name="ImputationBudg" ng-model="mission.imputationBudg" placeholder="Imputation Budgetaire" />--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="row">
                        <div class="form-group">

                            <div class="col-md-6">
                                <label  class=" control-label">Nom autorit&eacute;</label>

                                <input type="text" class="form-control" id="nomAutorite" name="nomAutorite" ng-model="mission.nomAutorite" placeholder="Nom autorit&eacute;" />
                            </div>
                            <div class="col-md-6">
                                <label  class=" control-label">Titre Autorite</label>
                                <input type="text" class="form-control" id="titreAutorite" name="titreAutorite" ng-model="mission.titreAutorite" placeholder="Titre Autorite" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="mission.id">
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
                        Etes vous sur de vouloir supprimer ?
                    </span>
                </div>
                <div class="modal-body">
                    <h4 ng-bind="mission.info"></h4>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="mission.id">
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
        $scope.populateForm = function (mission) {
            $scope.mission = mission;
        };
        $scope.initForm = function () {
            $scope.mission = {};
        };
    }).controller('formDeleteCtrl', function ($scope) {
        $scope.populateForm = function (mission) {
            $scope.mission = mission;
        };

    });
    //End AngularJs


    var actionUrl = "/absence/enregistrermission";
    var actionDeleteUrl = "/absence/supprimermission";
    var action = "ajout";
    var $table;
    jQuery(document).ready(function ($) {
        $table = $('#table');
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
                            toastr.success("enregistrement reussi");
                        } else {
                            //MAJ de la ligne modifi�e
                            $table.bootstrapTable('updateRow', {
                                index: $table.find('tr.selected').data('index'),
                                row: reponse.data
                            });
                            toastr.success("modification reussie");
                        }
                    } else{

                        alert(reponse.message);
                        toastr.error("erreur ");
                    }
                },
                error: function () {
                    $("#rhpModal .modal-body div.alert").alert();
                    $("#rhpModal .modal-body .alert h4").html("Erreur survenue");
                    $("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
                    $("#rhpModal .modal-footer span").removeClass('loader');
                    toastr.error("erreur ");
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
                    if (reponse.status == true) {
                        $table.bootstrapTable('remove', {
                            field: 'id',
                            values: idSup
                        });
                        $(".deleteModal").modal('hide');
                        toastr.success("suppression reussie ");
                    } else if (reponse.result == "erreur_champ_obligatoire") {
                        alert("Saisie invalide");
                        toastr.error("erreur ");
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

    function optionFormatter(id, row, index) {
        var option = '<a onclick="edit(' + row.id + ')" data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier mission [LIBELLE : ' + row.libelle + ' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
        option += '&nbsp;<a onclick="del(' + row.id + ')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer mission [LIBELLE : ' + row.libelle + ' ]"> <span class="glyphicon glyphicon-trash"></span></a>';

        return option;
    }

    function edit(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formAjout")).scope();

        var rows = $table.bootstrapTable('getData');
        var mission = _.findWhere(rows, {id: idTypeSaction});

        $scope.$apply(function () {
            $scope.populateForm(mission);
        });
    }

    function del(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formDelete")).scope();

        var rows = $table.bootstrapTable('getData');
        var mission = _.findWhere(rows, {id: idTypeSaction});
        mission.info = mission.natureMission;
        $scope.$apply(function () {
            $scope.populateForm(mission);
        });
    }
</script>
