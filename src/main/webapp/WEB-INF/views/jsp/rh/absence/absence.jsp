<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
    <div class="col-md-12">
        <div class="panel panel-warning">
            <div class="panel-heading">
                <div class="panel-title-box">
                    <h3>Liste des Absences</h3>
                    <span>Maladie & Congés</span>
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
                                        <li><a href="#" data-toggle="modal" data-target="#rhpModal">Nouveau</a></li>
                                    </ul>
                                </div>

                            </div>
            <table id="table" class="table table-info table-striped"
                   data-toggle="table"
                   data-click-to-select="true"
                   data-single-select="true"
                   data-sort-name="faute" data-sort-order="desc"
                   data-url="${pageContext.request.contextPath}/absence/paginerabsences"
                   data-side-pagination="server"
                   data-pagination="true"
                   data-page-list="[5, 10, 20, 50, 100, 200]"
                   data-search="false">
                <thead>
                    <tr>
                        <th data-field="faute">Motif</th>
                        <th data-field="commentaire">Commentaire</th>
                        <th data-field="type">Type absence</th>
                        <th data-field="id" data-formatter="optionFormatter" data-width="100px" data-align="center">Options</th>
                    </tr>
                </thead>
            </table>
        </div><!--widgetcontent-->
    </div><!--widgetbox-->
</div><!-- widgetcontent-->
            </div>
        </div>
<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formAjout" class="form-horizontal" role="form" novalidate="novalidate" ng-controller="formAjoutCtrl">
                <div class="modal-header">
                    <!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Absence</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="faute" class="col-md-2 control-label">Motif</label>
                        <div class="col-md-10">
                            <input type="text" id="faute" name="faute" ng-model="absence.faute" class="form-control" placeholder="Motif" />
                        </div>
                    </div>
                         <div class="form-group">
                                            <label for="typejust" class="col-md-2 control-label">Type</label>
                                            <div class="col-md-10">

                                                  <select class="form-control input-small" id="type" name="type"  ng-model="absence.type" >
                                                    <option value="Justifiée" >OUI</option>
                                                    <option value="Injustifiée" >NON</option>
                                                   select>
                                            </div>
                                        </div>
                    <div class="form-group">
                        <label for="commentaire" class="col-md-2 control-label">Commentaire</label>
                        <div class="col-md-10">
                            <textarea class="form-control" id="commentaire" name="commentaire" ng-model="absence.commentaire" placeholder="Commentaire">
                                
                            </textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="absence.id">
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
                    <h4 ng-bind="absence.info"></h4>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="absence.id">
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
        $scope.populateForm = function (absence) {
            $scope.absence = absence;
        };
        $scope.initForm = function () {
            $scope.absence = {};
        };
    }).controller('formDeleteCtrl', function ($scope) {
        $scope.populateForm = function (absence) {
            $scope.absence = absence;
        };

    });
    //End AngularJs

    var actionUrl = "/absence/enregistrerabsences";
    var actionDeleteUrl = "/absence/supprimerabsences";
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
                        } else {
                            //MAJ de la ligne modifi�e
                            $table.bootstrapTable('updateRow', {
                                index: $table.find('tr.selected').data('index'),
                                row: reponse.data
                            });
                        }
                    } else{
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

    function optionFormatter(id, row, index) {
        var option = '<a onclick="edit(' + row.id + ')" data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier absence [Faute : ' + row.faute + ' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
        option += '&nbsp;<a onclick="del(' + row.id + ')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer absence [Faute : ' + row.faute + ' ]"> <span class="glyphicon glyphicon-trash"></span></a>';

        return option;
    }

    function edit(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formAjout")).scope();

        var rows = $table.bootstrapTable('getData');
        var absence = _.findWhere(rows, {id: idTypeSaction});
         $scope.type=absence.type;
        $scope.$apply(function () {
            $scope.populateForm(absence);
        });
    }

    function del(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formDelete")).scope();

        var rows = $table.bootstrapTable('getData');
        var absence = _.findWhere(rows, {id: idTypeSaction});
        absence.info = absence.faute;
        $scope.$apply(function () {
            $scope.populateForm(absence);
        });
    }
</script>
