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
                    <%--<li><a href="#" data-toggle="modal" data-target="#rhpModal">Nouveau</a></li>--%>
                </ul>
            </div>
            <h4 id="widgetTitle" class="widgettitle">Liste des ordres de missions </h4>
        </div>

        <div id="tablewidget" class="widgetcontent">
            <table id="tablem" class="table table-info table-striped"
                   data-toggle="table" data-click-to-select="true"
                   data-single-select="false"
                   data-sort-name="id"
                   data-sort-order="desc"
                   data-url="${pageContext.request.contextPath}/absence/paginerdeplacementmission"
                   data-side-pagination="server"
                   data-pagination="true"
                   data-page-list="[10, 20, 50, 100, 200,500,1000]"
                   data-search="true">
                <thead>
                <tr>
                    <th data-field="missionsPersonnel" data-formatter="nomFormatter" data-align="left" data-sortable="true">Nom</th>
                    <th data-field="partantDe"  data-align="left">Lieu de depart</th>
                    <th data-field="destinantionMission"  data-align="center">Destinations</th>
                    <th data-field="naturDeplacemnt"  data-align="left" data-sortable="true">Nature Deplacement</th>
                    <th data-field="accompagnateur"  data-align="left" data-sortable="true">Accompagnateur</th>
                    <%--<th data-field="personnel" data-formatter="nomFormatter" data-align="left" data-sortable="true">Nom</th>--%>
                    <%--<th data-field="fonctionMission"  data-align="right">Fonction</th>--%>
                    <%--<th data-field=""   data-align="left">lieu de destination</th>--%>
                    <%--<th data-field="periode"  data-align="center">periode</th>--%>
                    <%--<th data-field="mMontant"  data-align="center">montant journalier</th>--%>

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
                    <!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Ordre de Mission de :  {{missionPersonnel.personnel.nomComplet}}</h4>
                    <input type="text" class="hidden" ng-hide="true" value="{{missionPersonnel.personnel.id}}"  id="idPersonnel" name="idPersonnel" ng-model="missionPersonnel.personnel.id">
                    <input type="text" class="hidden" ng-hide="true" value="{{missionPersonnel.missions.id}}"  id="idMission" name="idMission" ng-model="missionPersonnel.missions.id">

                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-7">
                            <div class="widgetbox" >
                            <div class="headtitle">
                                <h4 class="widgettitle">Informations sur la mission</h4>
                            </div>
                            <div class="widgetcontent">
                    <div class="row">
                        <%--<div class="form-group">--%>
                            <%--<div class="col-md-1"></div>--%>
                            <%--<div class="col-md-10">--%>
                                <%--<label  class=" control-label"> Choisir Mission </label>--%>
                                <%--&lt;%&ndash;<select id="lignebudget" name="lignebudget" class="form-control select2"    ng-model="documents.ligneBudgets" ng-required="true"></select></p>&ndash;%&gt;--%>
                                <%--<select id="idMission" name="idMission" class="form-control select2">--%>
                                    <%--&lt;%&ndash;<option value="0"></option>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<c:forEach items="${listmission}" var="mission">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<option value="${mission.id}">${mission.natureMission} </option>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                                <%--</select>--%>
                            <%--</div><div class="col-md-1"></div>--%>
                        <%--</div>--%>
                    <div class="form-group">

                        <div class="col-md-10">
                            <label  class=" control-label"> Mission </label>
                         <textarea name="natureMission" id="natureMission" cols="50" rows="4"  ng-model="missionPersonnel.missions.natureMission"></textarea>
                        </div>
                    </div>
                    </div>
                     <div class="row">
                    <div class="form-group">
                        <div class="col-md-1"> </div>
                        <div class="col-md-5">
                            <label for="description" class=" control-label">Service Demandeur</label>
                            <input type="text" class="form-control" id="service" name="service" ng-model="missionPersonnel.missions.service" placeholder="Service Demandeur" />
                       </div>
                        <div class="col-md-5">
                            <label  class=" control-label">Imputation Budgetaire</label>
                            <input type="text" class="form-control" id="ImputationBudg" name="ImputationBudg" ng-model="missionPersonnel.missions.imputationBudg" placeholder="Imputation Budgetaire" />
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
                            <div class="col-md-1"> </div>
                            <div class="col-md-5">
                                <label  class=" control-label">Nom autorit&eacute;</label>

                                <input type="text" class="form-control" id="nomAutorite" name="nomAutorite" ng-model="missionPersonnel.missions.nomAutorite" placeholder="Nom autorit&eacute;" />
                            </div>
                            <div class="col-md-5">
                                <label  class=" control-label">Titre Autorite</label>
                                <input type="text" class="form-control" id="titreAutorite" name="titreAutorite" ng-model="missionPersonnel.missions.titreAutorite" placeholder="Titre Autorite" />
                            </div>
                        </div>
                    </div>
                        </div>
                        </div>
                        </div>
                    <div class="col-lg-5">
                        <div class="row">
                            <div class="form-group">
                                <div class="col-md-6">
                                    <label  class=" control-label"> Fonction de la mission </label>
                                    <input type="text" class="form-control" id="fonctionMission" name="fonctionMission" ng-model="missionPersonnel.fonctionMission" placeholder="Fonction de la mission" />
                                </div>
                                <div class="col-md-6">
                                    <label  class=" control-label"> Lieu de la mission </label>
                                    <input type="text" class="form-control" id="lieuMission" name="lieuMission" ng-model="missionPersonnel.lieuMission" placeholder="Lieu de la mission" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">

                                <div class="col-md-6">
                                    <label for="description" class=" control-label">Date de depart</label>
                                    <input type="text" class="form-control" id="dDebut" name="dDebut" ng-model="missionPersonnel.dDebut" placeholder="Date de depart" />
                                </div>
                                <div class="col-md-6">
                                    <label  class=" control-label">Date de retour</label>
                                    <input type="text" class="form-control" id="dRet" name="dRet" ng-model="missionPersonnel.dRet" placeholder="Date de retour" />
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
                                    <label  class=" control-label">Moyen de Transport</label>

                                    <input type="text" class="form-control" id="moyenTransp" name="moyenTransp" ng-model="missionPersonnel.nomAutorite" placeholder="Moyen de Transport;" />
                                </div>
                                <div class="col-md-6">
                                    <label  class=" control-label">Nombre de Nuit&eacute;s</label>
                                    <input type="number" class="form-control" id="nbrenuite" name="nbrenuite" ng-model="missionPersonnel.nbrenuite" placeholder="Nombre de Nuit&eacute;s" />
                                </div>
                                <%--<div class="col-md-4">--%>
                                    <%--<label  class=" control-label">Montant</label>--%>
                                    <%--<input type="text" class="form-control" id="montant" name="montant" ng-model="missionPersonnel.montant" placeholder="Titre Autorite" />--%>
                                <%--</div>--%>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">

                                <div class="col-md-7">
                                    <label  class=" control-label">Montant Journalier</label>
                                    <input type="text" class="form-control" id="montant" name="montant" ng-model="missionPersonnel.montant" placeholder="Montant" />
                                </div>
                                <div class="col-md-5">

                                </div>

                            </div>
                        </div>
                    </div>
                    </div>
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
        $scope.populateForm = function (missionPersonnel) {
          //  $scope.contrat = contrat;
          //  if(missionPersonnel){
                $scope.missionPersonnel = missionPersonnel;

                //  $scope.statutAbs = absencePersonnel.statut === true ? 'true' : 'false';
            //}
        };
        $scope.initForm = function () {
            $scope.missionPersonnel = {};
        };
    }).controller('formDeleteCtrl', function ($scope) {
        $scope.populateForm = function (mission) {
            $scope.mission = mission;
        };

    });
    //End AngularJs


    var actionUrl = "/absence/enregistrerordremission";
    var actionDeleteUrl = "/absence/supprimermission";
    var action = "ajout";
    var $table;
    jQuery(document).ready(function ($) {
        $table = $('#table');$tablem = $('#tablem');$(".select2" ).select2();

        jQuery("#dDebut,dRet").datepicker({
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
                            //alert("jjjjj");
                            tabledata += '<option value="'+reponse.rows[i].id+'" >' + reponse.rows[i].natureMission + ' (' + reponse.rows[i].service + ')</option>';
                        }
                        $('#idMission').select2('val', 0);
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
        function widgetView(view){

            if(view == 'new'){
                jQuery('#tableMission').show('slow');
                jQuery('#tableWidget').hide('slow');
            }
            else{
                jQuery('#tableMission').hide('slow');
                jQuery('#tableWidget').show('slow');
            }

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
                            $tablem.bootstrapTable('refresh');
                            //$("#formAjout")[0].reset(); //Initialisation du formulaire
                            $("#rhpModal").modal('hide');
                        } else {
                            //MAJ de la ligne modifi�e
                            $tablem.bootstrapTable('updateRow', {
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
                    jQuery('#tableMission').show();
                    jQuery('#tableWidget').hide();
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
    function matriFormatter(personnel, row, index) {
        if(row.personnel == ''){
            return "";
        }
        return row.personnel.matricule;
    }

    function nomFormatter(missionsPersonnel, row, index) {
        if(row.missionsPersonnel== ''){
            return "";
        }
        return row.missionsPersonnel.personnel.nom+" "+row.missionsPersonnel.personnel.prenom;
    }
    function serviceFormatter (missions, row, index) {
        return row.missions.service;
    }

    function missionFormatter (missions, row, index) {
        return row.missions.natureMission;
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
        var option = '<a onclick="edit(' + row.id + ')" data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier mission [LIBELLE :   ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
        option +=  '&nbsp; <a   target="_blank" href="${pageContext.request.contextPath}/absence/printfeuille?id='+row.id+'" title="Imprimer Feuille de deplacement [NOM : '+row.id+' ]"><span class="glyphicon glyphicon-print"></span> </a>&nbsp;';
        <%--option +=  '&nbsp; <a   target="_blank" href="${pageContext.request.contextPath}/absence/printfraismission?id='+row.id+'" title="Imprimer Note de frais [NOM : '+row.personnel.nomComplet+' ]"><span class="glyphicon glyphicon-print"></span> </a>&nbsp;';--%>
        option += '&nbsp;<a onclick="del(' + row.id + ')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer mission [LIBELLE : ' + row.libelle + ' ]"> <span class="glyphicon glyphicon-trash"></span></a>';

        return option;
    }

    function edit(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formAjout")).scope();

        var rows = $tablem.bootstrapTable('getData');
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
</script>
