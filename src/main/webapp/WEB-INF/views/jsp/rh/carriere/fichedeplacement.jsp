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

                    <li class="divider"></li>
                    <li><a href="#" data-toggle="modal" data-target="#rhpModal">Nouveau</a></li>
                </ul>
            </div>
            <h4 id="widgetTitle" class="widgettitle">Liste du personnel </h4>
        </div>
        <div id="tableWidget" class="widgetcontent">
            <table id="table" class="table table-info table-striped"
                   data-toggle="table" data-click-to-select="true"
                   data-single-select="false"
                   data-sort-name="id"
                   data-sort-order="desc"
                   data-url="${pageContext.request.contextPath}/absence/paginerordremission"
                   data-side-pagination="server"
                   data-pagination="true"
                   data-page-list="[10, 20, 50, 100, 200,500,1000]"
                   data-search="true">
                <thead>
                <tr>
                    <th data-field="missions" data-formatter="serviceFormatter" data-align="left">Service</th>
                    <th data-field="missions" data-formatter="missionFormatter" data-align="center">Mission</th>
                    <th data-field="personnel" data-formatter="matriFormatter" data-align="left" data-sortable="true">Matricule</th>
                    <th data-field="personnel" data-formatter="nomFormatter" data-align="left" data-sortable="true">Nom</th>
                    <th data-field="fonctionMission"  data-align="right">Fonction</th>
                    <th data-field="lieuMission"   data-align="left">lieu</th>
                    <th data-field="periode"  data-align="center">periode</th>
                    <th data-field="mMontant"  data-align="center">montant journalier</th>

                    <th data-field="id" data-formatter="optionFormatter" data-width="80px" data-align="center">Options</th>

                    <!-- <th data-field="state" data-checkbox="true"></th> -->

                </tr>
                </thead>
            </table>
        </div><!--widgetcontent-->

    </div><!--widgetbox-->
</div><!-- widgetcontent-->

<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document" style="width:65%;">
        <div class="modal-content">
            <form id="formAjout" class="form-horizontal" role="form" novalidate="novalidate" ng-controller="formAjoutCtrl">
                <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Fiche de deplacement de :  {{missionPersonnel.personnel.nomComplet}}</h4>
                    <input type="text" class="hidden" ng-hide="true" value="{{missionPersonnel.personnel.id}}"  id="idPersonnel" name="idPersonnel" ng-model="missionPersonnel.personnel.id">

                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-7">
                            <div class="widgetbox" >
                            <div class="headtitle">
                                <h4 class="widgettitle">Informations sur l' ordre de mission</h4>
                            </div>
                            <div class="widgetcontent">
                    <div class="row">
                        <div class="form-group">
                            <label  class=" control-label col-md-2"> Numero </label>
                            <div class="col-md-5">

                                <input type="text" class="form-control" id="numero" name="numero" ng-model="missionPersonnel.id" placeholder="Numero" />
                                <%--<label  class=" control-label"> Choisir Mission </label>--%>
                                <%--&lt;%&ndash;<select id="lignebudget" name="lignebudget" class="form-control select2"    ng-model="documents.ligneBudgets" ng-required="true"></select></p>&ndash;%&gt;--%>
                                <%--<select id="idMission" name="idMission" class="form-control select2">--%>
                                    <%--&lt;%&ndash;<option value="0"></option>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<c:forEach items="${listmission}" var="mission">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<option value="${mission.id}">${mission.natureMission} </option>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                                <%--</select>--%>
                            </div><div class="col-md-4"></div>
                        </div>
                    <div class="form-group">

                        <div class="col-md-10">
                            <label  class=" control-label"> Mission </label>
                         <textarea name="natureMission" id="natureMission" style="margin: 0px; width: 428px; height: 71px;"  ng-model="missionPersonnel.missions.natureMission"></textarea>
                        </div>
                    </div>
                    </div>
                     <div class="row">
                    <div class="form-group">
                        <div class="col-md-1"> </div>
                        <div class="col-md-5">
                            <label  class=" control-label"> Fonction  </label>
                            <input type="text" class="form-control" id="fonctionMission" name="fonctionMission" ng-model="missionPersonnel.fonctionMission" value="{{contrat.fonction.libelle}}" placeholder="Fonction de la mission"  readonly="readonly"/>
                       </div>
                        <div class="col-md-5">
                            <label  class=" control-label"> Lieu de la mission </label>
                            <input type="text" class="form-control" id="lieuMission" name="lieuMission" ng-model="missionPersonnel.lieuMission" placeholder="Lieu de la mission" />
                        </div>
                    </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <div class="col-md-1"> </div>
                            <div class="col-md-5">
                                <label for="description" class=" control-label">Date de depart</label>
                                <input type="text" class="form-control" id="dDebut" name="dDebut" ng-model="missionPersonnel.dDebut" placeholder="Date de depart" />
                            </div>
                            <div class="col-md-5">
                                <label  class=" control-label">Date de retour</label>
                                <input type="text" class="form-control" id="dRet" name="dRet" ng-model="missionPersonnel.dRet" placeholder="Date de retour" />
                            </div>

                        </div>
                    </div>
                      <div class="row">
                                        <div class="form-group">
                                            <div class="col-md-1"> </div>
                                            <div class="col-md-5">
                                                <label  class=" control-label">Nombre de Nuit&eacute;s</label>
                                                <input type="number" class="form-control" id="nbrenuite" name="nbrenuite" ng-model="missionPersonnel.nbrenuite" placeholder="Nombre de Nuit&eacute;s" />
                                            </div>
                                            <div class="col-md-5">
                                                <label  class=" control-label">Montant Journalier</label>
                                                <input type="text" class="form-control" id="montant" name="montant" ng-model="missionPersonnel.montant" placeholder="Montant" />                                            </div>

                                        </div>
                         </div>
                        </div>
                        </div>
                        </div>
                    <div class="col-lg-5">
                        <div class="row">
                            <div class="form-group">
                                <div class="col-md-6">
                                    <label  class=" control-label"> Partant de </label>
                                    <input type="text" class="form-control" id="partantDe" name="partantDe" ng-model="deplacementPersonnel.partantDe"  placeholder="Partant de"  />
                                </div>
                                <div class="col-md-6">
                                    <label  class=" control-label"> Date de deplacement </label>
                                    <input type="text" class="form-control" id="dDepart" name="dDepart" ng-model="deplacementPersonnel.dDepart" placeholder="Date de depacement" />
                                </div>

                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label  class=" control-label"> Pour se rendre a </label>
                                <div class="col-md-10">

                                    <textarea name="destinantionMission" id="destinantionMission"   ng-model="deplacementPersonnel.destinantionMission" style="margin: 0px; width: 440px; height: 71px;"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <p>Effectue la mission avec:</p>
                                <div class="col-md-5">
                                    <label for="description" class=" control-label">Nom & Pr&eacute;noms</label>
                                    <input type="text" class="form-control" id="accompagnateur" name="accompagnateur" ng-model="deplacementPersonnel.accompagnateur" placeholder="Nom & Pr&eacute;noms" />
                                </div>
                                <div class="col-md-4">
                                    <label  class=" control-label">Groupe</label>
                                    <input type="text" class="form-control" id="groupe" name="groupe" ng-model="deplacementPersonnel.groupe" placeholder="Categorie" />
                                </div>
                                <div class="col-md-2">
                                    <label  class=" control-label">Indice</label>
                                    <input type="text" class="form-control" id="indice" name="indice" ng-model="deplacementPersonnel.indice" placeholder="Indice" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">

                                <div class="col-md-10">
                                    <label for="description" class=" control-label">Itineraire</label>
                                    <input type="text" class="form-control" id="itineraireAccompagnateur" name="itineraireAccompagnateur" ng-model="deplacementPersonnel.itineraireAccompagnateur" placeholder="Itineraire" />
                                </div>
                                <%--<div class="col-md-3">--%>
                                    <%--<label  class=" control-label">Deplacement(Temporaire/Definitif)</label>--%>
                                    <%--<input type="text" class="form-control" id="dRet" name="dRet" ng-model="missionPersonnel.dRet" placeholder="Date de retour" />--%>
                                <%--</div>--%>
                                <%--<div class="col-md-3">--%>
                                    <%--<label  class=" control-label">Poids de bagages autoris&eacute;</label>--%>
                                    <%--<input type="text" class="form-control" id="dRet" name="dRet" ng-model="missionPersonnel.dRet" placeholder="Date de retour"/>--%>
                                <%--</div>--%>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">

                                <div class="col-md-6">
                                    <label  class=" control-label">Deplacement(Temporaire/Definitif)</label>
                                    <input type="text" class="form-control" id="naturDeplacemnt" name="naturDeplacemnt" ng-model="depacementPersonnel.naturDeplacemnt"  value="Temporaire" placeholder="Temporaire/Definitif" />
                                </div>
                                <div class="col-md-6">
                                    <label  class=" control-label">Poids de bagages autoris&eacute;</label>
                                    <input type="text" class="form-control" id="poidsAutorise" name="poidsAutorise" ng-model="depacementPersonnel.poidsAutorise" placeholder="Poids de bagages" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <p>Autorit&eacute; ordonnateur<p>
                                <div class="col-md-3">
                                    <label  class=" control-label">Nom </label>
                                    <input type="text" class="form-control" id="autoriteAutorise" name="autoriteAutorise" ng-model="depacementPersonnel.autoriteAutorise" placeholder="Nom de l'autorité" />
                                </div>
                                <div class="col-md-4">
                                    <label  class=" control-label">Titre </label>
                                    <input type="text" class="form-control" id="titreAutorise" name="titreAutorise" ng-model="depacementPersonnel.titreAutorise" placeholder="Titre de l'autorite" />
                                </div>
                                <div class="col-md-4">
                                    <label  class=" control-label">Date </label>
                                    <input type="text" class="form-control" id="dSignt" name="dSignt" ng-model="depacementPersonnel.dSignt" placeholder="Date de Signature" />
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

                    </div>
                    </div>
                <%--<div class="row">--%>
                    <%--<div class="col-md-9">--%>
                    <%--<div class="panel panel-default">--%>
                        <%--<div class="panel-body">--%>
                            <%--<h3><span class="fa fa-pencil"></span>Ajout- Avances au depart</h3>--%>
                            <%--&lt;%&ndash;<p>This information lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer faucibus, est quis molestie tincidunt, elit arcu faucibus erat.</p>&ndash;%&gt;--%>
                        <%--</div>--%>
                        <%--<div class="panel-body form-group-separated">--%>

                            <%--<input type="text" class="hidden" ng-hide="true" value="" id="membreGrp" name="membreGrp" >--%>
                            <%--<div class="form-group">--%>

                                <%--<div class="col-md-3">--%>
                                    <%--<input type="text" id="designation" name="designation"  class="form-control" placeholder="Designation " />--%>
                                <%--</div>--%>
                                <%--<div class="col-md-2">--%>
                                    <%--<input type="text" id="pu" name="pu"  class="form-control montant" placeholder="Prix unitaire" />--%>
                                <%--</div>--%>
                                <%--<div class="col-md-2">--%>
                                    <%--<input type="number" id="Qte" name="Qte"  class="form-control" placeholder="Nombre" />--%>
                                <%--</div>--%>
                                <%--<div class="col-md-2">--%>
                                    <%--<input type="number" id="Tx" name="Tx"  class="form-control" placeholder="Taux" />--%>
                                <%--</div>--%>
                                <%--<div class="col-md-3">--%>
                                    <%--<input type="text" id="totlign" name="totlign" class="form-control montant" placeholder="Total ligne" />--%>
                                <%--</div>--%>
                            <%--</div>--%>

                            <%--<div class="form-group">--%>
                                <%--<label class="col-md-4 col-xs-5 control-label"></label>--%>
                                <%--<div class="col-md-8 col-xs-7" align="right">--%>
                                    <%--<button class="btn btn-warning" id="addMember" type="button">Ajouter >>></button>--%>
                                <%--</div>--%>

                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div >--%>
                        <%--<table class="table table-info table-striped" id="tabledet">--%>
                            <%--<thead>--%>
                            <%--<tr>--%>
                                <%--<th></th>--%>
                                <%--<th>Designation</th>--%>
                                <%--<th class="text-center">Prix Unitaire</th>--%>
                                <%--<th class="text-center">Nombre</th>--%>
                                <%--<th class="text-center">Taux</th>--%>
                                <%--<th class="text-center">Avance</th>--%>
                                <%--<th class="text-center">Decompte</th>--%>
                            <%--</tr>--%>
                            <%--</thead>--%>
                            <%--<tbody id="choixArt">--%>


                            <%--</tbody>--%>

                        <%--</table>--%>
                    <%--</div></div>--%>
                    <%--<div class="col-md-3"></div>--%>
                <%--</div>--%>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="missionPersonnel.id">
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
                    <h4 ng-bind="missionPersonnel.info"></h4>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="missionPersonnel.id">
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
        $scope.populateForm = function (missionPersonnel,depacementPersonnel) {
            //$scope.contrat = contrat;
            if(missionPersonnel){
                $scope.missionPersonnel = missionPersonnel;
              //  $scope.absenceId = primePersonnel.prime.id;
                $scope.depacementPersonnel = depacementPersonnel;
                //  $scope.statutAbs = absencePersonnel.statut === true ? 'true' : 'false';
            }
        };
        $scope.initForm = function () {
            $scope.missionPersonnel = {};
            $scope.depacementPersonnel = {};
        };
    }).controller('formDeleteCtrl', function ($scope) {
        $scope.populateForm = function (mission) {
            $scope.mission = mission;
        };

    });
    //End AngularJs


    var actionUrl = "/absence/enregisterDemandeM";
    var actionDeleteUrl = "/absence/supprimermission";
    var action = "ajout";
    var $table;
    jQuery(document).ready(function ($) {
        $table = $('#table');$tablem = $('#tablem');$(".select2" ).select2();
        // jQuery('#tableMission').hide();
        // jQuery('#tableWidget').show(
        jQuery("#dDebut,#dDepart,#dSignt").datepicker({
            dateFormat: 'dd/mm/yy',
            showOtherMonths:true
        });
        jQuery("#dRet").datepicker({
            dateFormat: 'dd/mm/yy',
            showOtherMonths:true
        });
        //Fermeture du modal
        $('#rhpModal').on('hidden.bs.modal', function () {
            var $scope = angular.element(document.getElementById("formAjout")).scope();
            $scope.$apply(function () {
                $scope.initForm();
            });
            //$("#id").val(""); //Initialisation de l'id
        });
        if ($('.montant')) {
            $('.montant').css('text-align', 'right');
            $('.montant').attr('maxlength', '18');

            $('.montant').keyup(function (event) {
                this.value = this.value.replace(/[^0-9]/g, '');
            });
            $('.montant').keydown(function (event) {
                this.value = this.value.replace(/[^0-9]/g, '');
            });
            $('.montant').keypress(function (event) {
                this.value = this.value.replace(/[^0-9]/g, '');
            });

            $('.montant').change(function (event) {

                if (this.value != "") {
                    var nombre = this.value;

                    var nombre = new Intl.NumberFormat("fr-FR").format(nombre);

                    this.value = nombre;

                }


            });
        }
        chargerMission();
        $('#idMission').change(function() {
            var i = $('#idMission').val();

            loadMissionById(i);
        });

        function chargerMission(){
            $.ajax({
                type: "POST",
                url: baseUrl + "/absence/listermission",
                cache: false,
                success: function (reponse) {
                    if (reponse != null) {
                        tabledata = '<option value="0" data-libelle="CHOISIR MISSION" >CHOISIR MISSION</option>';
                        console.log("hhkop",reponse);
                        for (i = 0; i < reponse.rows.length; i++) {
                          //  alert("jjjjj");
                            tabledata += '<option value="'+reponse.rows[i].id+'" >' + reponse.rows[i].natureMission + ' (' + reponse.rows[i].service + ')</option>';
                        }
                      //  $('#idMission').select2('val', 0);
                        $('#idMission').html(tabledata);
                        //   jQuery('#choixLigne').select2('val', 0);
                    } else {
                        alert('Failure! An error has occurred!');
                    }
                },
                error: function () {

                },
                complete: function () {

                }
            });
        }

        function loadMissionById(idPersonnel){
            $("#natureMission").val("");
            $("#service").val("");
            $("#ImputationBudg").val("");
            $("#titreAutorite").val("");
            $("#nomAutorite").val("");
            $.ajax({
                type: "POST",
                url: baseUrl + "/absence/trouvermission",
                cache: false,
                data: {id: idPersonnel},
                success: function (reponse) {
                    //	if(reponse.rows>0){
                    // var $scope = angular.element(document.getElementById("formAjout")).scope();

                    //  var rows = reponse.row;
                    var mission = {};reponse.row;//_.findWhere(rows, {id: idTypeSaction});
                    mission=reponse.row;
                    $("#natureMission").val(mission.natureMission);
                    $("#service").val(mission.service);
                    $("#ImputationBudg").val(mission.imputationBudg);
                    $("#titreAutorite").val(mission.titreAutorite);
                    $("#nomAutorite").val(mission.nomAutorite);
                    console.log("row value",mission);

                    // $tableRegl.bootstrapTable('load', reponse.rows);
                    //   }
                },
                beforeSend: function () {
                    // $tableRegl.bootstrapTable('load', []);
                    // jQuery("#btnAddAbsence,.form-mouvement-conge").hide();
                },
                error: function () {

                },
                complete: function () {

                }
            });
        }
        //Envoi des donnees
        $("#formAjout").submit(function(e) {
            e.preventDefault();
            var numero, partantDe, dDepart, destinantionMission, accompagnateur, groupe, indice, itineraireAccompagnateur,poidsAutorise ,naturDeplacemnt, autoriteAutorise,dSignt,lignebudget;
            numero = $('#formAjout').find('#numero').val();
            partantDe = $('#formAjout').find('#partantDe').val();
            dDepart = $('#formAjout').find('#dDepart').val();
            destinantionMission = $('#formAjout').find('#destinantionMission').val();
            accompagnateur = $('#formAjout').find('#accompagnateur').val();
            groupe = $('#formAjout').find('#groupe').val();
            indice = $('#formAjout').find('#indice').val();
            itineraireAccompagnateur = $('#formAjout').find('#itineraireAccompagnateur').val();
            naturDeplacemnt = $('#formAjout').find('#naturDeplacemnt').val();
            poidsAutorise = $('#formAjout').find('#poidsAutorise').val();
            autoriteAutorise = $('#formAjout').find('#autoriteAutorise').val();
            titreAutorise = $('#formAjout').find('#titreAutorise').val();
            dSignt = $('#formAjout').find('#dSignt').val();
            //alert(idprojet);alert(idbudget);alert(lignebudget);
            if (numero == "" || destinantionMission == "" ) {
                toastr.info("informations attendues");
                return;
            // } else if ( checkedRows.length==0 ) {
            //     toastr.info("informations attendues, Details de la demande de moyens");
            //     return;
            } else {
                deplacementMission = {
                    partantDe: partantDe,
                    dDepart: dDepart,
                    destinantionMission: destinantionMission,
                    accompagnateur: accompagnateur,
                    groupe: groupe,
                    indice: indice,
                    itineraireAccompagnateur: itineraireAccompagnateur,
                    naturDeplacemnt: naturDeplacemnt,
                    poidsAutorise: poidsAutorise,
                    autoriteAutorise: autoriteAutorise,
                    titreAutorise: titreAutorise,
                    missionsPersonnel: {id:numero},

                    dSignt: dSignt
                }
                $.ajax({
                    type: "POST",
                    url: baseUrl + actionUrl,
                    cache: false,
                    data: {
                        deplacementPersonnel: JSON.stringify(deplacementMission)
                       // details: JSON.stringify(checkedRows)
                    },
                    success: function (reponse) {
                        if (reponse.result == "succes") {
                           // if (action == "ajout") {
                                //Rechargement de la liste (le tableau)
                               // $table1.bootstrapTable('refresh');
                                $("#formAjout")[0].reset(); //Initialisation du formulaire
                                 $("#rhpModal").modal('hide');
                                toastr.success("Feuille de deplacement enregistré");
                                // $("#liste").show();
                                // $("#news").hide();
                            //} else {  //MAJ de la ligne modifi�e
                            //     $table1.bootstrapTable('updateRow', {
                            //         index: $table1.find('tr.selected').data('index'),
                            //         row: reponse.data
                            //     });
                           // }
                          //  toastr.success("feuille Depacement engegistré");
                            window.open(baseUrl+"/absence/listfeuilleDeplacement");
                        } else if (reponse.result == "erreur_champ_obligatoire") {
                            alert("Saisie invalide");
                            toastr.error("Demande d'achat engegistré");
                        }
                    },
                    error: function () {
                        $("#rhpModal .modal-body div.alert").alert();
                        $("#rhpModal .modal-body .alert h4").html("Erreur survenue");
                        $("#rhpModal .modal-body .alert p").html("Verifier que vous êtes connectes au serveur.");
                        $("#formAjout span").removeClass('loader');
                    },
                    beforeSend: function () {
                        $("#formAjout").attr("disabled", true);
                        $("#formAjout  span").addClass('loader');
                    },
                    complete: function () {
                        $("#formAjout").removeAttr("disabled");
                        $("#formAjout span").removeClass('loader');
                    }
                });
            }
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
                        $(".deleteModal").modal('hide');   toastr.success("suppression reussie ");
                    } else if (reponse.result == "erreur_champ_obligatoire") {
                        alert("Saisie invalide");   toastr.error("erreur");
                    }
                },
                error: function (err) {
                    $(".deleteModal .modal-body div.alert").alert();
                    $(".deleteModal .modal-body .alert h4").html("Erreur survenue");
                    $(".deleteModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
                    $(".deleteModal .modal-footer span").removeClass('loader');toastr.error("erreur");
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
    function matriFormatter(personnel, row, index) {
        if(row.personnel == ''){
            return "";
        }
        return row.personnel.matricule;
    }
    function serviceFormatter (missions, row, index) {
        return row.missions.service;
    }

    function missionFormatter (missions, row, index) {
        return row.missions.natureMission;
    }
    function nomFormatter(personnel, row, index) {
        if(row.personnel== ''){
            return "";
        }
        return row.personnel.nom+" "+row.personnel.prenom;
    }

    function nomstatutFormatter(contratPersonnel, row, index) {
        if(row.contratPersonnel== ''){
            return "";
        }
        if(row.contratPersonnel.personnel.carec==true){
            if(row.contratPersonnel.personnel.stage==true)
                statfonct = "Stagiaire";
            else
                statfonct = "Fonctionnaire";
        }else{
            statfonct = "Contractuel";
        }
        return statfonct;
    }

    function nomfstatutFormatter(contratPersonnel, row, index) {
        if(row.contratPersonnel== ''){
            return "";
        }
        if(row.contratPersonnel.personnel.carec==true){

            if(row.contratPersonnel.personnel.stage==true)
                statfonct = "Stagiaire";
            else
                statfonct = "Fonctionnaire";
        }else{
            statfonct = "Contractuel";
        }
        return statfonct;
        //return row.contratPersonnel.personnel.statfonct;
    }



    function sexeFormatter(personnel, row, index) {
        if(row.personnel.sexe == ''){
            return "";
        }
        return row.personnel.sexe;
    }
    function datnaisFormatter(personnel, row, index) {
        if(row.personnel.dNaissance == ''){
            return "";
        }
        return row.personnel.dNaissance;
    }
    function lieunaisFormatter(personnel, row, index) {
        if(row.personnel.lieuNaissance == ''){
            return "";
        }
        return row.personnel.lieuNaissance;
    }
    function telephoneFormatter(personnel, row, index) {
        if(row.personnel.telephone == ''){
            return "";
        }
        return row.personnel.telephone;
    }

    function situaFormatter(personnel, row, index) {
        if(row.personnel.situationMatrimoniale == ''){
            return "";
        }
        return row.personnel.situationMatri;
    }

    function nbreenftFormatter(personnel, row, index) {
        if(row.personnel.nombrEnfant == ''){
            return "";
        }
        return row.personnel.nombrEnfant;
    }
    function salcatFormatter(categorie, row, index) {
        if(row.categorie.salaireBase == ''){
            return "";
        }
        return row.categorie.salaireBase;
    }
    function sommeilFormatter(personnel, row, index) {

        optionActif = '<small class="label label-success"><i class="fa fa-clock-o"></i> -- Activ&eacute; </small>';
        if(row.personnel.statut == false)
            var optionActif = '<small class="label label-danger"><i class="fa fa-clock-o"></i> -- Desactiv&eacute; </small>';

        return optionActif;
    }
    function optionFormatter(id, row, index) {
        var option = '<a onclick="edit(' + row.id + ')" data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier mission [LIBELLE : ' + row.libelle + ' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
        option += '&nbsp;<a onclick="del(' + row.id + ')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer mission [LIBELLE : ' + row.libelle + ' ]"> <span class="glyphicon glyphicon-trash"></span></a>';

        return option;
    }

    function edit(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formAjout")).scope();

        var rows = $table.bootstrapTable('getData');
        var missionPersonnel = _.findWhere(rows, {id: idTypeSaction});
        console.log("dddd",missionPersonnel);
        $scope.$apply(function () {
            $scope.populateForm(missionPersonnel);
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

    var checkedRows = [];
    jQuery('#addMember').on('click',function (e) {
        var design =jQuery('#designation').val() ;//$('#pu').val()
        var pu= jQuery('#pu').val().replace(/\s/g,'');
        console.log('fffff',pu);
        var pu1= parseFloat(pu);
        var qtete = jQuery('#Qte').val();
        var qtete1 = parseFloat(jQuery('#Qte').val());
        var qtaxe1 = parseFloat(jQuery('#Tx').val());
        var mtlign= qtete1*pu1;
        var mtlignDecomp=mtlign -(mtlign*qtaxe1/100) ;

        jQuery('#totlign').val(mtlignDecomp);
        if(pu=="" || qtete==""){
            toastr.info("Veuillez saisir un prix unitaire pour le reglement");
        }
        else{
            //function rowArticle(libelArt, codeArt, condit, pu, idArt) {
            var tdContent = '', corp = '', incop = '';
            tdContent = rowArticle(design, pu, qtete1,qtaxe1 ,mtlignDecomp);
            jQuery('<tr>' + tdContent + '</tr>').appendTo("#choixArt");

            checkedRows.push({
                designation: design,
                prixUnit: pu1,
                qteUnit:qtete,
                taxe:qtaxe1,
                montligne: mtlign
            });
            var montant=0;
            jQuery.each(checkedRows, function(index, row,value) {
                // if (value.id === row.id)
                console.log("check value",value)
                console.log("check totlign",row)
                var mont = parseFloat(row.montligne);
                mont = mont ? mont : 0;
                montant += mont;

            });
        }
        jQuery("#subtot").html(new Intl.NumberFormat("fr-FR").format((montant)));
        if(jQuery("#taxe").val()==""){
            jQuery("#subtva").html(new Intl.NumberFormat("fr-FR").format((montant*18/100)));
            jQuery("#subttc").html(new Intl.NumberFormat("fr-FR").format((montant+montant*18/100)));
            jQuery("#sttc").html(new Intl.NumberFormat("fr-FR").format((montant+montant*18/100)));
        }else{
            jQuery("#subtva").html(new Intl.NumberFormat("fr-FR").format((montant*jQuery("#taxe").val()/100)));
            jQuery("#subttc").html(new Intl.NumberFormat("fr-FR").format((montant+montant* jQuery("#taxe").val()/100)));
            jQuery("#sttc").html(new Intl.NumberFormat("fr-FR").format((montant+montant* jQuery("#taxe").val()/100)));
        }
        // $("#subttc").html(new Intl.NumberFormat("fr-FR").format((montant+montant*18/100)));
        // $("#sttc").html(new Intl.NumberFormat("fr-FR").format((montant+montant*18/100)));
        console.log(checkedRows,"hhhhh");
        jQuery("#Qte").val('');
        jQuery("#Tx").val('');
        jQuery("#designation").val('');
        jQuery("#pu").val('');
        jQuery("#totlign").val('');
    });
    jQuery("#Qte").blur(function(){
        var facteur =jQuery('#pu').val().replace(/\s/g,'');
        console.log("prixUnit",facteur);
        var pu1= parseFloat(facteur);
        var acteur = jQuery("#Qte").val();

        console.log("ggg",pu1);
        jQuery("#totlign").val(acteur*pu1);
    });
    jQuery("#Tx").blur(function(){
        var facteur =jQuery('#totlign').val().replace(/\s/g,'');
        console.log("prixUnit",facteur);
        var pu1= parseFloat(facteur);
        var acteur = jQuery("#Tx").val();

        console.log("ggg",pu1);
        jQuery("#totlign").val(pu1-(pu1*acteur/100));
    });
    function rowArticle(libelArt, put, qtete,tax, mtlign) {

        tdContent = '<td onclick="javascript:deleteLine(this);"><div class="checkbox"><input type="checkbox" checked="checked" ></div></td>';
        tdContent += '<td class="text-center">' + libelArt + '</td>';
        tdContent += '<td class="text-center">' + new Intl.NumberFormat("fr-FR").format(put) + '</td>';
        tdContent += '<td class="text-center">' + qtete + '</td>';
        tdContent += '<td class="text-center">' + tax + '</td>';
        tdContent += '<td class="text-center">' + (new Intl.NumberFormat("fr-FR").format((put*qtete)-mtlign))+ '</td>';
        tdContent += '<td class="text-center "  id="montHt">' + new Intl.NumberFormat("fr-FR").format(mtlign) + '</td>';
        return '<tr >' + tdContent + '</tr>';

    }
    function deleteLine(obj) {
        alert('hhhhhhhhhhhhhhhhhhhhhhh');
        console.log("Array",checkedRows);
        jQuery(obj).parents('tr').remove();

        console.log("Array1",checkedRows);
        jQuery.each(checkedRows, function(index, value) {
            console.log("index",index);
            checkedRows.slice(index,1);
            //}
        });
        //
        //
        // console.log(" After Array",checkedRows);
        jQuery.each(checkedRows, function(index, row,value) {
            // if (value.id === row.id)
            //  console.log("check value",value)
            console.log("check totlign",row)
            var mont = parseFloat(row.montligne);
            mont = mont ? mont : 0;
            montant += mont;
            console.log("check montant tot",montant);
            //checkedRows.splice(index,1);
            //}
        });
        // majTotalTcc();
        jQuery("#subtot").html(new Intl.NumberFormat("fr-FR").format((montant)));
        if(jQuery("#taxe").val()==""){
            jQuery("#subtva").html(new Intl.NumberFormat("fr-FR").format((montant*18/100)));
            jQuery("#subttc").html(new Intl.NumberFormat("fr-FR").format((montant+montant*18/100)));
            jQuery("#sttc").html(new Intl.NumberFormat("fr-FR").format((montant+montant*18/100)));
        }else{
            jQuery("#subtva").html(new Intl.NumberFormat("fr-FR").format((montant*jQuery("#taxe").val()/100)));
            jQuery("#subttc").html(new Intl.NumberFormat("fr-FR").format((montant+montant* jQuery("#taxe").val()/100)));
            jQuery("#sttc").html(new Intl.NumberFormat("fr-FR").format((montant+montant* jQuery("#taxe").val()/100)));
        }
        jQuery("#subtva").html(new Intl.NumberFormat("fr-FR").format((montant*18/100)));
        jQuery("#subttc").html(new Intl.NumberFormat("fr-FR").format((montant+montant*18/100)));
        jQuery("#sttc").html(new Intl.NumberFormat("fr-FR").format((montant+montant*18/100)));

        jQuery("#subtot").html('');
        jQuery("#sttc").html('');
        jQuery("#subtva").html('');
        jQuery("#subttc").html('');
    }
</script>
