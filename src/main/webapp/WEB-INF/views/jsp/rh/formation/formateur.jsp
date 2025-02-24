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
            <h4 id="widgetTitle" class="widgettitle">Liste des formateurs</h4>
        </div>
        <div id="tableWidget" class="widgetcontent">
            <table id="table" class="table table-info table-striped"
                   data-toggle="table"
                   data-click-to-select="true"
                   data-single-select="true"
                   data-url="${pageContext.request.contextPath}/formation/paginerformateurs"
                   data-side-pagination="server"
                   data-pagination="true"
                   data-page-list="[5, 10, 20, 50, 100, 200]"
                   data-search="false">
                <thead>
                    <tr>
                        <th data-field="cabinetFormation" data-formatter="cabinetFormationFormatter">Cabinet de formation</th>
                        <th data-field="civilite">Civilit&eacute;</th>
                        <th data-field="nomComplet">Nom</th>
                        <th data-field="sexe">Sexe</th>
                        <th data-field="situationMatrimonial">Sit. matrimoniale</th>
                        <th data-field="dNaissance">Date naissance</th>
                        <th data-field="lieuNaissance">Lieu de naissance</th>
                        <th data-field="fixe">Fixe</th>
                        <th data-field="mobile">Mobile</th>
                        <th data-field="id" data-formatter="optionFormatter" data-width="100px" data-align="center">Options</th>
                    </tr>
                </thead>
            </table>
        </div><!--widgetcontent-->
    </div><!--widgetbox-->
</div><!-- widgetcontent-->

<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document" style="width: 60%;">
        <div class="modal-content">
            <form id="formAjout" role="form" novalidate="novalidate" ng-controller="formAjoutCtrl">
                <div class="modal-header">
                    <!-- <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Formateur</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-12">
                                <label for="cabinetFormationId">Cabinet de formation</label> 
                                <select id="cabinetFormationId" name="cabinetFormationId" class="form-control select2">
                                    
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label for="civilite">Civilit&eacute;</label> 
                                <select id="civilite" name="civilite" class="form-control" ng-model="formateur.civilite">
                                    <option value="M.">Monsieur</option>
                                    <option value="Mme">Madame</option>
                                    <option value="Mlle">Mademoiselle</option>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label for="nom">Nom du formateur</label> 
                                <input type="text" class="form-control input-small" id="nom" name="nom" placeholder="Nom du formateur" ng-model="formateur.nomComplet">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label for="situationMatrimonial">Situation matrimoniale</label> 
                                <select id="situationMatrimonial" ng-model="formateur.situationMat" name="situationMatrimonial" class="form-control">
                                    <option value="1"> MARIE </option>
                                    <option value="2"> CELIBATAIRE </option>
                                    <option value="3"> DIVORCE </option>
                                    <option value="4"> VEUF(VE) </option>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label for="sexeFormateurMasculin">Sexe</label> 
                                <div>
                                    <label id="sexeFormateurMasculin" class="radio-inline">
                                        <input type="radio" ng-model="formateur.sexe" name="sexe" value="M"> Masculin
                                    </label>
                                    <label id="sexeFormateurFeminin" class="radio-inline">
                                        <input type="radio" ng-model="formateur.sexe" name="sexe" value="F"> Feminin
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label for="dateNaissance">Date de naissance</label> 
                                <input type="text" class="form-control input-small datePicker" id="dateNaissance" name="dateNaissance" placeholder="Date de naissance" ng-model="formateur.dNaissance">
                            </div>
                            <div class="col-md-6">
                                <label for="lieuNaissance">Lieu de naissance</label> 
                                <input type="text" class="form-control input-small" id="lieuNaissance" name="lieuNaissance" placeholder="Lieu de naissance" ng-model="formateur.lieuNaissance">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label for="fixe">T&eacute;l&eacute;phone Fixe</label> 
                                <input type="text" class="form-control input-small" id="fixe" name="fixe" placeholder="Téléphone fixe" ng-model="formateur.fixe">
                            </div>
                            <div class="col-md-6">
                                <label for="mobile">T&eacute;l&eacute;phone Mobile</label> 
                                <input type="text" class="form-control input-small" id="mobile" name="mobile" placeholder="Téléphone mobile" ng-model="formateur.mobile">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="formateur.id">
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
                        Etes vous sûr de vouloir supprimer ?
                    </span>
                </div>
                <div class="modal-body">
                    <h4 ng-bind="formateur.info"></h4>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="formateur.id">
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
        $scope.pupulateForm = function (formateur) {
            $scope.formateur = formateur;
        };
        $scope.initForm = function () {
            $scope.formateur = {};
        };
    }).controller('formDeleteCtrl', function ($scope) {
        $scope.pupulateForm = function (formateur) {
            $scope.formateur = formateur;
        };
    });
    //End AngularJs


    var actionUrl = "/formation/enregistrerformateur";
    var actionDeleteUrl = "/formation/supprimerformateur";
    var action = "ajout";
    var $table;
    jQuery(document).ready(function ($) {
        $table = $('#table');
        $(".select2").select2();
        $(".datePicker").datepicker({
            dateFormat: 'dd/mm/yy',
            showOtherMonths: true
        });
        
        loadCabinetFormation();
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
                            //MAJ de la ligne modifiée
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
                    $("#rhpModal .modal-body .alert p").html("Verifier que vous êtes connectés au serveur.");
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
                    $(".deleteModal .modal-body .alert p").html("Verifier que vous êtes connectés au serveur.");
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
    function cabinetFormationFormatter(cabinetFormation){
        return cabinetFormation.nom;
    }
    
    function optionFormatter(id, row, index) {
        var option = '<a onclick="edit(' + row.id + ')" data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier formateur [LIBELLE : ' + row.libelle + ' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
        option += '&nbsp;<a onclick="del(' + row.id + ')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer formateur [LIBELLE : ' + row.libelle + ' ]"> <span class="glyphicon glyphicon-trash"></span></a>';

        return option;
    }

    function edit(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formAjout")).scope();

        var rows = $table.bootstrapTable('getData');
        var formateur = _.findWhere(rows, {id: idTypeSaction});

        $scope.$apply(function () {
            $scope.pupulateForm(formateur);
        });
    }

    function del(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formDelete")).scope();

        var rows = $table.bootstrapTable('getData');
        var formateur = _.findWhere(rows, {id: idTypeSaction});
        formateur.info = formateur.libelle;
        $scope.$apply(function () {
            $scope.pupulateForm(formateur);
        });
    }
    
    function loadCabinetFormation() {
        var $selectCabinetFormation = jQuery("#cabinetFormationId");
        $selectCabinetFormation.html('');
        jQuery.ajax({
            type: "POST",
            url: baseUrl + "/formation/listercabinetformations",
            cache: true,
            success: function (reponse) {
                jQuery.each(reponse.rows,
                        function (index, row) {
                            $selectCabinetFormation.append('<option value="' + row.id + '"> ' + row.nom + ' / ' + row.telephone + '</option>');
                        });
                if (reponse.rows.length > 0) {
                    $selectCabinetFormation.val(reponse.rows[0].id).trigger("change");
                }
            }
        });
    }
</script>
