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
            <h4 id="widgetTitle" class="widgettitle">Liste des formations</h4>
        </div>
        <div id="tableWidget" class="widgetcontent">
            <table id="table" class="table table-info table-striped"
                   data-toggle="table"
                   data-click-to-select="true"
                   data-single-select="true"
                   data-url="${pageContext.request.contextPath}/formation/paginerformations"
                   data-side-pagination="server"
                   data-pagination="true"
                   data-page-list="[5, 10, 20, 50, 100, 200]"
                   data-search="false">
                <thead>
                    <tr>
                        <th data-field="intitule">Intitul&eacute; Formation</th>
                        <th data-field="cabinetFormation" data-formatter="cabinetFormationFormatter">Cabinet de formation</th>
                        <th data-field="lieu">Lieu</th>
                        <th data-field="dPrevue">Date prevue</th>
                        <th data-field="dDebut">Date debut</th>
                        <th data-field="dFin">Date fin</th>
                        <th data-field="demandeFormation" data-formatter="beneficiaireFormatter">B&eacute;n&eacute;ficiaire</th>
                        <th data-field="nbreParticipant" data-align="right">Nbre participants</th>
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
                    <h4 class="modal-title" id="myModalLabel">Formation</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-8">
                                <label for="idDemandeFormation">Besoin/Demande de formation</label>
                                <select id="idDemandeFormation" name="idDemandeFormation" class="form-control select2">

                                </select>
                            </div>
                            <div class="col-md-4">
                                <label for="idCabinetFormation">Cabinet de formation retenu</label>
                                <select id="idCabinetFormation" name="idCabinetFormation" class="form-control select2">
                                    <c:forEach items="${listeCabinet}" var="contrat" >
                                        <option value="${contrat.id}">${contrat.nom}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <%--<label for="idTheme">Th&egrave;me</label> --%>
                                <%--<select id="idTheme" name="idTheme" class="form-control select2">--%>

                                <%--</select>--%>
                            </div>
                            <div class="col-md-10">
                                <label for="intitule">Intitul&eacute; Formation</label>
                                <textarea type="text" class="form-control input-small" id="intitule" name="intitule" placeholder="Intitul&eacute;" ng-model="formation.intitule"> </textarea>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label for="lieu">Lieu</label> 
                                <input type="text" class="form-control input-small" id="lieu" name="lieu" placeholder="Lieu de la formation" ng-model="formation.lieu">
                            </div>
                            <div class="col-md-6">
                                <label for="datePrevue">Date prevue</label>
                                <input type="text" class="form-control input-small datePicker" id="datePrevue" name="datePrevue" placeholder="Date prevue" ng-model="formation.dPrevue">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label for="dateDebut">Date debut</label> 
                                <input type="text" class="form-control input-small datePicker" id="dateDebut" name="dateDebut" placeholder="Date debut" ng-model="formation.dDebut">
                            </div>
                            <div class="col-md-6">
                                <label for="dateFin">Date fin</label> 
                                <input type="text" class="form-control input-small datePicker" id="dateFin" name="dateFin" placeholder="Date fin" ng-model="formation.dFin">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label for="objectif">Objectif</label>
                                <textarea class="form-control input-small" id="objectif" name="objectif" placeholder="Objectif" ng-model="formation.objectif">
                                
                                </textarea>
                            </div>
                            <div class="col-md-6">
                                <label for="participant">Nombre de participant(s)</label> 
                                <input type="number" class="form-control input-small" id="participant" name="participant" placeholder="Nombre de participant(s)" ng-model="formation.nbreParticipant">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-12">
                                <label for="planFormation">Plan de formation</label> 
                                <textarea class="form-control" id="planFormation" name="planFormation" ng-model="formation.plan" placeholder="Plan de formation">
                                
                                </textarea>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-12">
                                <label for="commentaire">Commentaire</label> 
                                <textarea class="form-control" id="commentaire" name="commentaire" ng-model="formation.commentaire" placeholder="Commentaire">
                                
                                </textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="formation.id">
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
                    <h4 ng-bind="formation.info"></h4>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="formation.id">
                    <span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
        </div>
        </form>
    </div>
</div>

<div class="modal main-popup fade" id="listFactureFormationModal" ng-controller="listFactureFormationCtrl" role="dialog" aria-labelledby="listFactureFormationModalLabel" data-backdrop="static">
    <div class="modal-dialog" style="width:60%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="listFactureFormationModalLabel">Liste des factures</h4>
            </div>
            <div class="modal-body">
                <h3>Formation</h3>
                <table>
                    <tbody>
                        <tr>
                            <th style="width:150px;">Intitul&eacute;</th>
                            <td style="width:300px;">{{formation.intitule}}</td>
                            <th style="width:80px;">Th&egrave;me</th>
                            <td>{{formation.theme.intitule}}</td>
                        </tr>
                        <tr>
                            <th>Lieu</th>
                            <td>{{formation.lieu}}</td>
                            <th>Date pr&eacute;vue</th>
                            <td>{{formation.dPrevue}}</td>
                        </tr>
                        <tr>
                            <th>Date debut</th>
                            <td>{{formation.dDebut}}</td>
                            <th>Date fin</th>
                            <td>{{formation.dFin}}</td>
                        </tr>
                        <tr>
                            <th>B&eacute;n&eacute;ficiaire</th>
                            <td>{{formation.beneficiaire}}</td>
                            <th>Participants</th>
                            <td>{{formation.nbreParticipant}}</td>
                        </tr>
                    </tbody>
                </table>
                <form id="formFactureFormation" class="form-facture-formation">
                    <h3 style="margin-top: 30px;">Facture</h3>
                    <br>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label for="idCabinet">Cabinet de formation</label> 
                                <select class="form-control select2" id="idCabinet" name="idCabinet">
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label for="reference">R&eacute;f&eacute;rence * {{factureFormation.id}}</label> 
                                <input type="text" class="form-control input-small" ng-model="factureFormation.reference" id="reference" name="reference" placeholder="R�f�rence" maxlength="100" required="required">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label for="emission">Date d'&eacute;mission</label> 
                                <input type="text" class="form-control input-small datePicker" ng-model="factureFormation.emission" id="emission" name="emission" placeholder="Date d'�mission" maxlength="10">
                            </div>
                            <div class="col-md-6">
                                <label for="montant">Montant</label> 
                                <input type="number" class="form-control input-small" ng-model="factureFormation.email" id="montant" name="montant" placeholder="Montant">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="checkbox">
                                    <label for="statut">
                                        <input type="checkbox" checked="checked" class="form-control input-small" ng-model="factureFormation.statut" id="statut" name="statut">
                                        Actif ?
                                    </label> 
                                </div>
                            </div>
                            <div id="actionFactureFormation" class="col-md-6 text-right">
                                <span></span>&nbsp;
                                <input type="text"class="hidden" ng-hide="true" name="idFormation" ng-model="formation.id"/>
                                <button type="button" id="btnCancelFactureFormation" class="btn btn-default">Annuler</button>
                                <button type="submit" class="btn btn-success">Valider</button>
                            </div>
                        </div>
                    </div>					
                </form>
                <p>&nbsp;</p>
                <div id="toolbarFactureFormation">
                    <div class="form-inline">
                        <button type="button" class="btn btn-primary" title="Ajouter une facture" id="btnAddFactureFormation"><span class="glyphicon glyphicon-plus"></span> Nouvelle facture</button>
                    </div>
                </div>
                <table id="tableFactureFormation" class="table table-info table-striped"
                       data-toggle="table"
                       data-toolbar="#toolbarFactureFormation"
                       data-single-select="true" data-sort-name="nom"
                       data-sort-order="desc"
                       data-pagination="false"
                       data-search="true">
                    <thead>
                        <tr>
                            <th data-field="reference">R&eacute;f&eacute;rence</th>
                            <th data-field="cabinetFormation" data-formatter="cabinetFormationFormatter">Cabinet de formation</th>
                            <th data-field="dEmission">Date d'&eacute;mission</th>
                            <th data-field="mnt" data-align="right">montant</th>
                            <th data-field="statut" data-formatter="statutFormatter">Actif</th>
                        </tr>
                    </thead>
                </table>
                <br>
            </div>
        </div>
    </div>
</div>

<div class="modal main-popup fade" id="listParticipantModal" ng-controller="listParticipantCtrl" role="dialog" aria-labelledby="listParticipantModalLabel" data-backdrop="static">
    <div class="modal-dialog" style="width:60%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="listParticipantModalLabel">Liste des participants</h4>
            </div>
            <div class="modal-body">
                <h3>Formation</h3>
                <table>
                    <tbody>
                        <tr>
                            <th style="width:150px;">Intitul&eacute;</th>
                            <td style="width:300px;">{{formation.intitule}}</td>
                            <th style="width:80px;">Th&egrave;me</th>
                            <td>{{formation.theme.intitule}}</td>
                        </tr>
                        <tr>
                            <th>Lieu</th>
                            <td>{{formation.lieu}}</td>
                            <th>Date pr&eacute;vue</th>
                            <td>{{formation.dPrevue}}</td>
                        </tr>
                        <tr>
                            <th>Date debut</th>
                            <td>{{formation.dDebut}}</td>
                            <th>Date fin</th>
                            <td>{{formation.dFin}}</td>
                        </tr>
                        <tr>
                            <th>B&eacute;n&eacute;ficiaire</th>
                            <td>{{formation.beneficiaire}}</td>
                            <th>Participants</th>
                            <td>{{formation.nbreParticipant}}</td>
                        </tr>
                    </tbody>
                </table>
                <p>&nbsp;</p>
                <div id="toolbarParticipant">
                    <button class="btn btn-success" id="btnAddParticipants" data-id="{{formation.id}}">Ajouter</button>
                    <span></span>
                </div>
                <br>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-8">
                            <label for="idPersonnel">Personnel</label>
                            <select class="form-control select2 multiple"  multiple="multiple" id="idPersonnel" name="idPersonnel">
                                <c:forEach items="${listeContrat}" var="contrat" >
                                    <option value="${contrat.personnel.id}">${contrat.personnel.nomComplet}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <%--<label for="reference">R&eacute;f&eacute;rence * {{factureFormation.id}}</label>--%>
                            <%--<input type="text" class="form-control input-small" ng-model="factureFormation.reference" id="reference" name="reference" placeholder="R�f�rence" maxlength="100" required="required">--%>
                        </div>
                    </div>
                </div>
                <table id="tableParticipant" class="table table-info table-striped"
                       data-toggle="table"
                       data-toolbar="#toolbarParticipant"
                       data-click-to-select="true"
                       data-multiple-checkbox="true"
                       data-unique-id="id"
                       <%--data-url="${pageContext.request.contextPath}/formation/paginerformationpersonnelsduneformation?id={{formation.id}}"--%>
                       data-side-pagination="server"
                       data-pagination="true"
                       data-page-list="[5, 10, 20, 50, 100, 200]"
                       data-search="true">
                    <thead>
                        <tr>
                            <th data-field="state" data-formatter="stateFormatter" data-checkbox="true"></th>
                            <th data-field="personnel" data-formatter="matriculeFormatter">Matricule</th>
                            <th data-field="personnel" data-formatter="nomCompletFormatter">Nom</th>
                            <th data-field="personnel" data-formatter="sexeFormatter">Sexe</th>
                            <th data-field="personnel" data-formatter="telephoneFormatter">T&eacute;l&eacute;phone</th>
                            <th data-field="personnel" data-formatter="beneficiaireFormatter">Division</th>
                        </tr>
                    </thead>
                </table>
                <br>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //AngularJS
    app.controller('formAjoutCtrl', function ($scope) {
        $scope.pupulateForm = function (formation) {
            $scope.formation = formation;
        };
        $scope.initForm = function () {
            $scope.formation = {};
        };
    }).controller('listFactureFormationCtrl', function ($scope) {
        $scope.populateForm = function (formation, factureFormation) {
            $scope.formation = formation;
            $scope.factureFormation = factureFormation;
        };
    }).controller('listParticipantCtrl', function ($scope) {
        $scope.populateForm = function (formation, participant) {
            $scope.formation = formation;
            $scope.participant = participant;
        };
    }).controller('formDeleteCtrl', function ($scope) {
        $scope.pupulateForm = function (formation) {
            $scope.formation = formation;
        };
    });
    //End AngularJs


    var actionUrl = "/formation/enregistrerformation";
    var actionDeleteUrl = "/formation/supprimerformation";
    var action = "ajout";
    var $table, $tableFactureFormation, $tableParticipant;
    jQuery(document).ready(function ($) {
        $table = $('#table');
        $tableFactureFormation = $('#tableFactureFormation');
        $tableParticipant = $('#tableParticipant');
        $(".select2").select2();
        // $('.multiple').select2({
        //     placeholder: 'Choisir membre'
        //
        // });
        $(".form-facture-formation").hide();
        $(".datePicker").datepicker({
            dateFormat: 'dd/mm/yy',
            showOtherMonths: true
        });
        loadTheme();
        loadDemandeFormation();
        loadCabinetFormation();
        $("#listParticipantModal").on('show.bs.modal', function (e) {
            var idFormation = $("#btnAddParticipants").data("id");
            var listPersonnels = $tableParticipant.bootstrapTable('getData');
            if (listPersonnels.length === 0) {
                return;
            }
            var tabIdsContratPersonnel = $.map(listPersonnels, function (contratPersonnel) {
                return {id: contratPersonnel.id, idPersonnel: contratPersonnel.personnel.id};
            });
            var tabIdsPersonnel = $.map(listPersonnels, function(contratPersonnel){
                return contratPersonnel.personnel.id;
            });
            var idsPersonnel = tabIdsPersonnel.join(' '), listSize = tabIdsPersonnel.length;
            console.log("Participants", $tableParticipant.bootstrapTable("getData"));
            /*  jQuery.ajax({
                  type: "POST",
                  url: baseUrl + "/formation/enregistrerformationpersonnellist",
                  cache: false,
                  data: {listPersonnel: idsPersonnel, listSize: listSize, id: idFormation},
                  success: function (reponse) {
                      var listFormationPersonnel = reponse.rows || [];
                      console.log("listFormationPersonnel", listFormationPersonnel);
                    3333333333333333333333333333333333333333333333f333333333333333333333333333333333333333333333333s
                          var idPersonnel = formationPersonnel.personnel.id;
                          var contratPersonnelIds = _.findWhere(tabIdsContratPersonnel, {idPersonnel: idPersonnel});

                          var contratPersonnel = {}, $row = $tableParticipant.find("tr[data-uniqueid='" + contratPersonnelIds.id + "']"), indexRow;
                          indexRow = $row.data("index");
                          if (indexRow >= 0) {
                              console.log("Index", indexRow);
                              contratPersonnel.personnel = formationPersonnel.personnel;
                              contratPersonnel.formationPersonnel = formationPersonnel;
                              $tableParticipant.bootstrapTable('updateRow', {
                                  index: indexRow,
                                  row: contratPersonnel
                              });
                          }
                      });
                  }/*,
                  beforeSend: function () {
                      $btnCurrent.hide();
                      $loaderParticipant.addClass('loader');
                  },
                  error: function () {
                      $loaderParticipant.removeClass('loader');
                  },
                  complete: function () {
                      $loaderParticipant.removeClass('loader');
                      $btnCurrent.show();
                  }*/
           // });
        });

       $tableParticipant.on("check-all.bs.table", function () {
         $("#checkAll").attr("checked", "checked");
         $("#uniform-checkAll span").addClass("checked");
         });
         
         $tableParticipant.on("uncheck-some.bs.table", function () {
         $("#uniform-checkAll span").removeClass("checked");
         });

        $("#btnAddParticipants").on("click", function (e) {
            alert("gvcfvdgdfgd");
            var idFormation = $(this).data("id");
            var list = $("#idPersonnel").val();
            // var choixPersonnels = $tableParticipant.bootstrapTable('getSelections');
            // if (choixPersonnels.length === 0) {
            //     return;
            // }
            var tabIdsPersonnel = $.map(list, function (index) {
                console.log("item",index);
                return index;
            });
            console.log('pppppn',tabIdsPersonnel);

            // var tabIdsContratPersonnel = $.map(choixPersonnels, function (contratPersonnel) {
            //     return {id: contratPersonnel.id, idPersonnel: contratPersonnel.personnel.id};
            // });
            console.log('kkkkkk',tabIdsPersonnel);
            var idsPersonnel = tabIdsPersonnel.join(' '), listSize = tabIdsPersonnel.length;
            console.log('kkkkkkgggg',idsPersonnel);
            var $btnCurrent = jQuery(this);
            var $loaderParticipant = $('#toolbarParticipant span');

            jQuery.ajax({
                type: "POST",
                url: baseUrl + "/formation/enregistrerformationpersonnellist",
                cache: false,
                data: {listPersonnel: idsPersonnel, listSize: listSize, id: idFormation},
                success: function (reponse) {

                    var listFormationPersonnel = reponse.rows || [];
                    $.each(listFormationPersonnel, function (index, formationPersonnel) {
                        var idPersonnel = formationPersonnel.personnel.id;
                       // var contratPersonnelIds = _.findWhere(tabIdsContratPersonnel, {idPersonnel: idPersonnel});

                        var contratPersonnel = {}, $row = $tableParticipant.find("tr[data-uniqueid='" + idPersonnel + "']"), indexRow;
                        indexRow = $row.data("index");
                        if (indexRow >= 0) {
                            console.log("Index", indexRow);
                            contratPersonnel.personnel = formationPersonnel.personnel;
                            contratPersonnel.formationPersonnel = formationPersonnel;
                            $tableParticipant.bootstrapTable('updateRow', {
                                index: indexRow,
                                row: contratPersonnel
                            });
                        }
                    });
                },
                beforeSend: function () {
                    $btnCurrent.hide();
                    $loaderParticipant.addClass('loader');
                },
                error: function () {
                    $loaderParticipant.removeClass('loader');
                },
                complete: function () {
                    $loaderParticipant.removeClass('loader');
                    $btnCurrent.show();
                }
            });
        });

        //Facture formation 
        $("#btnAddFactureFormation").click(function (e) {
            $(".form-facture-formation").show(500);
        });
        $("#btnCancelFactureFormation, #listFactureFormationModal button.close").click(function (e) {
            $(".form-facture-formation").hide(500);
        });
        $("#formFactureFormation").submit(function () {
            var formData = $(this).serialize();
            $.ajax({
                type: "POST",
                url: baseUrl + "/formation/enregistrerfactureformation",
                cache: false,
                data: formData,
                success: function (reponse) {
                    if ((reponse.result && reponse.status) == true) {
                        $tableFactureFormation.bootstrapTable('prepend', reponse.row);
                        $(".form-facture-formation").hide(500);
                    } else if (reponse.result == false) {
                        alert("Erreur survenue, r�essayer plus tard");
                    }
                },
                beforeSend: function () {
                    $("#formFactureFormation").attr("disabled", true);
                    $("#actionFactureFormation span").addClass('loader');
                },
                error: function () {
                    $("#actionFactureFormation span").removeClass('loader');
                },
                complete: function () {
                    $("#formFactureFormation").removeAttr("disabled");
                    $("#actionFactureFormation span").removeClass('loader');
                }
            });
            return false;
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
    function stateFormatter(value, row, index) {
        console.log("State", row);
        if (row.formationPersonnel) { //Deja participant
            return {
                disabled: true
            };
        }
        return value;
    }
    function cabinetFormationFormatter(cabinetFormation) {
        return cabinetFormation.nom + ' / ' + (cabinetFormation.telephone || cabinetFormation.email || cabinetFormation.adresse);
    }

    function statutFormatter(statut) {
        return statut ? "Oui" : "Non";
    }

    function beneficiaireFormatter(demandeFormation) {
        if (demandeFormation.service.typeService.id === 2) { //Departement
            return 'Dept. ' + demandeFormation.service.serviceParent.libelle;
        }
        if (demandeFormation.service.typeService.id === 3) { //Service
            return 'Sce ' + demandeFormation.service.serviceParent.serviceParent.libelle;
        }
        //Direction
        return 'Direction ' + demandeFormation.service.libelle;
    }

    function themeFormatter(theme) {
        return theme.intitule;
    }

    function optionFormatter(id, row, index) {
        var option = '<a style="font-size:14px;" onclick="listParticipant(' + row.id + ')" data-toggle="modal" data-target="#listParticipantModal" href="#" title="Participant(s)"><span class="iconfa-group"></span></a>&nbsp;';
        option += '&nbsp;<a style="font-size:14px;" onclick="listFactureFormation(' + row.id + ')" data-toggle="modal" data-target="#listFactureFormationModal" href="#" title="Facture"><span class="iconfa-shopping-cart"></span></a>&nbsp;';
        option += '&nbsp;<a style="font-size:14px;" onclick="edit(' + row.id + ')" data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier formation [LIBELLE : ' + row.libelle + ' ]"><span class="iconfa-pencil"></span></a>&nbsp;';
        option += '&nbsp;<a style="font-size:14px;" onclick="del(' + row.id + ')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer formation [LIBELLE : ' + row.libelle + ' ]"><span class="iconfa-remove"></span></a>';

        return option;
    }

    function edit(idFormation) {
        var $scope = angular.element(document.getElementById("formAjout")).scope();

        var rows = $table.bootstrapTable('getData');
        var formation = _.findWhere(rows, {id: idFormation});
        console.log('dde',formation);
        jQuery("#idDemandeFormation").val(formation.demandeFormation.id).trigger('change');
        //jQuery("#idTheme").val(formation.theme.id).trigger('change');
        $scope.$apply(function () {
            $scope.pupulateForm(formation);
        });
    }

    function del(idFormation) {
        var $scope = angular.element(document.getElementById("formDelete")).scope();

        var rows = $table.bootstrapTable('getData');
        var formation = _.findWhere(rows, {id: idFormation});
        formation.info = formation.intitule;
        $scope.$apply(function () {
            $scope.pupulateForm(formation);
        });
    }

    function listParticipant(idFormation) {
        var $scope = angular.element(document.getElementById("listParticipantModal")).scope();
        //loadParticipantByFormation(idFormation);
        var rows = $table.bootstrapTable('getData');
        var formation = _.findWhere(rows, {id: idFormation});
        var beneficiaire = "";
        if (formation.demandeFormation.service.typeService.id === 2) { //Departement
            beneficiaire = 'Dept. ' + formation.demandeFormation.service.serviceParent.libelle;
        }
        if (formation.demandeFormation.service.typeService.id === 3) { //Service
            beneficiaire = 'Sce ' + formation.demandeFormation.service.serviceParent.serviceParent.libelle;
        }
        //Direction
        beneficiaire = 'Direction ' + formation.demandeFormation.service.libelle;
        formation.beneficiaire = beneficiaire;
        var factureFormation = {};
        factureFormation.statut = true;
        $scope.$apply(function () {
            $scope.populateForm(formation, factureFormation);
        });
        $tableParticipant.bootstrapTable('refresh',{url:baseUrl  +"/formation/paginerformationpersonnelsduneformation?id="+idFormation});
    }

    function listFactureFormation(idFormation) {

        var $scope = angular.element(document.getElementById("listFactureFormationModal")).scope();
        loadFactureFormationByFormation(idFormation);
        var rows = $table.bootstrapTable('getData');
        var formation = _.findWhere(rows, {id: idFormation});
        var beneficiaire = "";
        if (formation.demandeFormation.service.typeService.id === 2) { //Departement
            beneficiaire = 'Dept. ' + formation.demandeFormation.service.serviceParent.libelle;
        }
        if (formation.demandeFormation.service.typeService.id === 3) { //Service
            beneficiaire = 'Sce ' + formation.demandeFormation.service.serviceParent.serviceParent.libelle;
        }
        //Direction
        beneficiaire = 'Direction ' + formation.demandeFormation.service.libelle;
        formation.beneficiaire = beneficiaire;
        var factureFormation = {};
        factureFormation.statut = true;
        $scope.$apply(function () {
            $scope.populateForm(formation, factureFormation);
        });
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

    //Facture par formation
    function loadFactureFormationByFormation(idFormation) {
        jQuery.ajax({
            type: "POST",
            url: baseUrl + "/formation/listerfactureformationsparformation",
            cache: false,
            data: {id: idFormation},
            success: function (reponse) {
                $tableFactureFormation.bootstrapTable('load', reponse.rows);
            },
            beforeSend: function () {
                $tableFactureFormation.bootstrapTable('load', []);
                jQuery(".form-facture-formation").hide();
            }
        });
    }

    function loadTheme() {
        var $selectTheme = jQuery("#idTheme");
        $selectTheme.html('');
        jQuery.ajax({
            type: "POST",
            url: baseUrl + "/formation/listerthemes",
            cache: true,
            success: function (reponse) {
                jQuery.each(reponse.rows,
                        function (index, row) {
                            $selectTheme.append('<option value="' + row.id + '"> ' + row.intitule + '</option>');
                        });
                if (reponse.rows.length > 0) {
                    $selectTheme.val(reponse.rows[0].id).trigger("change");
                }
            }
        });
    }


    function loadCabinetFormation() {
        var $selectCabinetFormation = jQuery("#idCabinet");
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

    function loadDemandeFormation() {
        var $selectDemandeFormation = jQuery("#idDemandeFormation");
        $selectDemandeFormation.html('');
        jQuery.ajax({
            type: "POST",
            url: baseUrl + "/formation/listerdemandeformationsValide",
            cache: true,
            success: function (reponse) {
                jQuery.each(reponse.rows,
                        function (index, row) {
                            var libelleService = "";
                            switch (row.service.typeService.id) {
                                case 2 : //Departement
                                    libelleService = 'Departement ' + row.service.serviceParent.libelle;
                                    break;
                                case 3 : //Service
                                    libelleService = 'Service ' + row.service.serviceParent.serviceParent.libelle;
                                    break;
                                default : //Direction
                                    libelleService = 'Direction ' + row.service.libelle;
                                    break;
                            }
                            $selectDemandeFormation.append('<option value="' + row.id + '"> ' + row.objet + ' / ' + libelleService + '</option>');
                        });
                if (reponse.rows.length > 0) {
                    $selectDemandeFormation.val(reponse.rows[0].id).trigger("change");
                }
            }
        });
    }
</script>

