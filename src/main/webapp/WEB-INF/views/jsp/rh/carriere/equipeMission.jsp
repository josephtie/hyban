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
	              	<li><a href="#" data-toggle="modal" data-target="#listParticipantModal">Nouveau</a></li>
	            </ul>
	        </div>
	        <h4 id="widgetTitle" class="widgettitle">Liste des &eacute;quipes</h4>
	    </div>
	    <div id="tableWidget" class="widgetcontent">
	    	<table id="table" class="table table-info table-striped"
			       	data-toggle="table"
			       	data-click-to-select="true"
			       	data-single-select="true"
			       	data-sort-name="flag" data-sort-order="desc"
			       	data-url="${pageContext.request.contextPath}/absence/listequipejson"
			       	data-side-pagination="server"
			       	data-pagination="true"
			       	data-page-list="[5, 10, 20, 50, 100, 200]"
			       	data-search="false">
			    <thead>
			        <tr>
						<th data-field="intitule" data-align="left" data-sortable="true">D&eacute;signation</th>
			        	<th data-field="missions" data-align="left" data-formatter="missFormatter">Mission</th>

			        	<th data-field="nbrembre" data-align="right" data-sortable="true">Effectif</th>
			        	<%--<th data-field="montantIndemniteLogement" data-align="right" data-sortable="true">Imdenite de logement</th>--%>
			        	<th data-field="id"  data-formatter="optionFormatter" data-width="100px" data-align="center">Options</th>
			        </tr>
			    </thead>
			</table>
	    </div><!--widgetcontent-->
    </div><!--widgetbox-->
</div><!-- widgetcontent-->


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
				<h3>Equipe de Mission</h3>

				<p>&nbsp;</p>

				<br>
				<div class="form-group">
					<div class="row">
						<div class="col-md-5">
							<label for="equipe">Equipe</label>
							<input type="text" class="form-control input-small" ng-model="equipe.intutile" id="intutile" name="intutile" placeholder="Equipe"  required="required">
							<input type="hidden" class="form-control input-small" ng-model="equipe.id" id="eqId" name="eqId"   required="required">

						</div>
						<div class="col-md-5">
							<label for="equipe">Mission</label>
							<select id="idMission" name="idMission" class="form-control select2">

							</select>

						</div>
						<div class="col-md-2">
							<button type="button" class="btn btn-info" id="addGroupe" title="Ajout dune equipe">Valider</button>
							<%--<label for="reference">R&eacute;f&eacute;rence * {{factureFormation.id}}</label>--%>
							<%--<input type="text" class="form-control input-small" ng-model="factureFormation.reference" id="reference" name="reference" placeholder="R�f�rence" maxlength="100" required="required">--%>
						</div>
					</div>
				</div>
				<h2>Constitution des &eacute;quipes</h2>
				<div class="form-group">
					<div class="row">
						<div class="col-md-8">
							<label for="idPersonnel">Personnel</label>
							<select class="form-control select2 multipe" multiple="multiple"   id="idPersonnel" name="idPersonnel">
								<c:forEach items="${listeContrat}" var="contrat" >
									<option value="${contrat.personnel.id}">${contrat.personnel.nomComplet}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-2">
							<label for="idPersonnel">Responsabilit&eacute;</label>
							<select id="categorie" name="categorie" class="form-control select2" >
								<option value="0"  selected="selected"> Aucun </option>
								<option value="Chef &eacute;quipe" > Chef &eacute;quipe </option>
								<option value="Membre" > Membre </option>
								<option value="Chauffeur" > Chauffeur </option>


							</select>
							<%--<label for="reference">R&eacute;f&eacute;rence * {{factureFormation.id}}</label>--%>
							<%--<input type="text" class="form-control input-small" ng-model="factureFormation.reference" id="reference" name="reference" placeholder="R�f�rence" maxlength="100" required="required">--%>
						</div>
						<div class="col-md-2">
							<label for="equipe">Nuit&eacute;/Frais</label>
							<input type="number" class="form-control input-small"  id="nuite" name="nuite" placeholder="Nuite / Frais"  required="required">
							<%--<input type="hidden" class="form-control input-small" ng-model="equipe.id" id="eqId" name="eqId"   required="required">--%>

						</div>
					</div>
				</div>
				<div id="toolbarParticipant">
				<button class="btn btn-success" id="btnAddParticipants" data-id="{{equipe.id}}">Ajouter</button>
				<span></span>
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
						<th data-field="responsabilte" >Responsabilite</th>
						<th data-field="nuite" >Frais de mission</th>
						<th data-field="personnel" data-formatter="telephoneFormatter">T&eacute;l&eacute;phone</th>
						<%--<th data-field="personnel" data-formatter="beneficiaireFormatter">Division</th>--%>
					</tr>
					</thead>
				</table>
				<br>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document" style="width:65%;">
		<div class="modal-content">
			<form id="formAjout" class="form-horizontal" role="form" novalidate="novalidate" ng-controller="formAjoutCtrl">
				<div class="modal-header">
					<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
					<h4 class="modal-title" id="myModalLabel">Ordre de Mission de Lequipe :  {{equipe.intitule}}</h4>
					<input type="text" class="hidden" ng-hide="true" value="{{equipe.id}}"  id="idEquipe" name="idEquipe" ng-model="equipe.id.id">
					<input type="text" class="hidden" ng-hide="true" value="{{equipe.missions.id}}"  id="mission" name="mission" ng-model="equipe.missions.id">

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
												<%--<select id="idMissions" name="idMissions" class="form-control select2">--%>
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
												<textarea name="natureMission" id="natureMission" cols="50" rows="4"  ng-model="equipe.missions.natureMission"></textarea>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="form-group">
											<div class="col-md-1"> </div>
											<div class="col-md-5">
												<label for="description" class=" control-label">Ordonnateur</label>
												<input type="text" class="form-control" id="service" name="service" ng-model="equipe.missions.service" placeholder="Service Demandeur" />
											</div>
											<div class="col-md-5">
												<label  class=" control-label">Imputation Budgetaire</label>
												<input type="text" class="form-control" id="ImputationBudg" name="ImputationBudg" ng-model="equipe.missions.imputationBudg" placeholder="Imputation Budgetaire" />
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

												<input type="text" class="form-control" id="nomAutorite" name="nomAutorite" ng-model="equipe.missions.nomAutorite" placeholder="Nom autorit&eacute;" />
											</div>
											<div class="col-md-5">
												<label  class=" control-label">Titre Autorite</label>
												<input type="text" class="form-control" id="titreAutorite" name="titreAutorite" ng-model="equipe.missions.titreAutorite" placeholder="Titre Autorite" />
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-lg-5">
							<%--<div class="row">--%>
								<%--<div class="form-group">--%>
									<%--<div class="col-md-6">--%>
										<%--<label  class=" control-label"> Fonction  </label>--%>
										<%--<input type="text" class="form-control" id="fonctionMission" name="fonctionMission" ng-model="missionPersonnel.fonctionMission" value="{{contrat.fonction.libelle}}" placeholder="Fonction "  readonly="readonly"/>--%>
									<%--</div>--%>
									<%--<div class="col-md-6">--%>
										<%--<label  class=" control-label"> Responsabilit&eacute; dans la mission</label>--%>
										<%--<input type="text" class="form-control" id="respMission" name="respMission" ng-model="missionPersonnel.respMission" placeholder="Responsabilit&eacute; dans la mission" />--%>
									<%--</div>--%>

								<%--</div>--%>
							<%--</div>--%>
							<div class="row">
								<div class="form-group">
									<div class="col-md-8">
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
							<div class="row">
								<div class="form-group">

									<div class="col-md-6">
										<label for="description" class=" control-label">Nombre de jour</label>
										<input type="number" class="form-control" id="nbrejour" name="nbrejour" ng-model="missionPersonnel.nbrejour" placeholder="Nombre de jour" />
									</div>
									<div class="col-md-6">
										<label  class=" control-label">Nombre de Nuit&eacute;s</label>
										<input type="number" class="form-control" id="nbrenuite" name="nbrenuite" ng-model="missionPersonnel.nbrenuite" placeholder="Nombre de Nuit&eacute;s" />
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

									<div class="col-md-8">
										<label  class=" control-label">Moyen de Transport</label>

										<input type="text" class="form-control" id="moyenTransp" name="moyenTransp" ng-model="missionPersonnel.nomAutorite" placeholder="Moyen de Transport;" />
									</div>

									<div class="col-md-4">
										<%--<label  class=" control-label">Montant Journalier</label>--%>
										<%--<input type="text" class="form-control" id="montant" name="montant" ng-model="missionPersonnel.montant" placeholder="Montant" />--%>
									</div>
								</div>
							</div>
							<%--<div class="row">--%>
							<%--<div class="form-group">--%>

							<%--<div class="col-md-7">--%>
							<%--<label  class=" control-label">Montant Journalier</label>--%>
							<%--<input type="text" class="form-control" id="montant" name="montant" ng-model="missionPersonnel.montant" placeholder="Montant" />--%>
							<%--</div>--%>
							<%--<div class="col-md-5">--%>

							<%--</div>--%>

							<%--</div>--%>
							<%--</div>--%>
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
	            	<h4 ng-bind="categorie.info"></h4>
	            </div>
	            <div class="modal-footer">
                	<input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="categorie.id">
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
app.controller('listParticipantCtrl', function ($scope) {
    $scope.pupulateForm = function (equipe,equipeMission) {
        $scope.equipe = equipe;
        $scope.equipeMission = equipeMission;
    };
    $scope.initForm = function () {
    	$scope.fonction = {};
    };
}).controller('formAjoutCtrl', function ($scope) {
    $scope.populateForm = function (equipe,missionPersonnel) {
        $scope.equipe = equipe;
        if(missionPersonnel){
            $scope.missionPersonnel = missionPersonnel;
            $scope.absenceId = primePersonnel.prime.id;
            $scope.personnel = missionPersonnel.personnel;
            //  $scope.statutAbs = absencePersonnel.statut === true ? 'true' : 'false';
        }
    };
    $scope.initForm = function () {
        $scope.missionPersonnel = {};
    };
})
	.controller('formDeleteCtrl', function ($scope) {
    $scope.pupulateForm = function (categorie) {
    	$scope.categorie = categorie;
    };

});
//End AngularJs

var actionUrl = "/absence/enregistrerordremissionGrp";
var actionUrlgrp = "/absence/enregistergroupe";
var actionDeleteUrl = "/personnels/supprimercategorie";
var action = "ajout";
var $table;
jQuery(document).ready(function($){
	$table = $('#table');
	$tableParticipant = $('#tableParticipant');
    $('#idPersonnel').select2();
    $('#categorie').select2();
    $('#idMissions').select2();
    $('#idMission').select2();
    jQuery("#dDebut").datepicker({
        dateFormat: 'dd/mm/yy',
        showOtherMonths:true
    });
    jQuery("#dRet").datepicker({
        dateFormat: 'dd/mm/yy',
        showOtherMonths:true
    });

    $( "#dDebut" ).datepicker({ dateFormat: 'dd-mm-yy' });
    $( "#dRet" ).datepicker({ dateFormat: 'dd-mm-yy' });
    chargerMission();
 //   $('#respMission').select2();
	//Fermeture du modal
	$('#listParticipantModal').on('hidden.bs.modal', function () {
		var $scope = angular.element(document.getElementById("listParticipantModal")).scope();
		$scope.$apply(function () {
            $scope.initForm();
        });
		//$("#id").val(""); //Initialisation de l'id
	});
    $('#idMissions').change(function() {
        var i = $('#idMissions').val();

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
                    $('#idMissions').html(tabledata);
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
    $('#dRet').blur(function() {

        //var i = $('#idMission').val();
        var start = $('#dDebut').datepicker('getDate');
        var end   = $('#dRet').datepicker('getDate');
        // var date1 =   new Date(jQuery("#dDebut").datepicker('getDate'));
        console.log("date1",start);
        // var date2 = new Date(jQuery("#dRet").datepicker('getDate'));
        console.log("date2",end);
        var days   = (end - start)/1000/60/60/24;
        console.log("resut",days);
        //  var date2 = new Date($('#dRet').val());
        // var nbr=    Math.round((process(date2)-process(date1))/(1000*60*60*24));
        //       var Difference_In_Time =   date2.getTime()-date1.getTime();
        //       console.log("difference",Difference_In_Time);
        //       var Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
        //      console.log("difference jours",Difference_In_Days);
        //       var In_Days = Math.round(Math.abs(Difference_In_Days));;
        $('#nbrejour').val(days+1);
        $('#nbrenuite').val(days);
        // loadMissionById(i);
    })
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
    $('#addGroupe').on('click',function (e) {
       // alert("hpo");
        var eqId =$('#eqId').val() ;
        var intutile =$('#intutile').val() ;
        var idMission =$('#idMission').val() ;
        if(intutile=="" ){
            toastr.info("Veuillez le nom de lequipe");
            return;
        }else{
            $.ajax({
                type: "POST",
                url: baseUrl + actionUrlgrp,
                cache: false,
                data: {
                    eqId:eqId,
                    intutile:intutile,
                    idMission:idMission
                },
                success: function (reponse) {
                    if (reponse.result == true) {
                        $("#eqId").val(reponse.row.id);
                        toastr.info("Nouvelle &eacute;quipe enregistr&eacute;e");
                    }
                    else if(reponse.result == "erreur_champ_obligatoire"){
                        alert("Saisie invalide");
                    }
                },
                error: function (err) {
                    $(".listParticipantModal .modal-body div.alert").alert();
                    $(".listParticipantModal .modal-body .alert h4").html("Erreur survenue");
                    $(".listParticipantModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
                    $(".listParticipantModal .modal-footer span").removeClass('loader');
                },
                beforeSend: function () {
                    // $("#formDelete").attr("disabled", true);
                    //  $(".deleteModal .modal-footer span").addClass('loader');
                },
                complete: function () {
                    // $("#formDelete").removeAttr("disabled");
                    // $(".deleteModal .modal-footer span").removeClass('loader');
                }
            });
        }

    });
	//Envoi des donnees
    $("#btnAddParticipants").on("click", function (e) {
        //alert("gvcfvdgdfgd");
        var idFormation = $(this).data("id");
        var list = $("#idPersonnel").val();
        var categorie = $("#categorie").val();
        var nuite = $("#nuite").val();
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
            url: baseUrl + "/absence/enregistrerformationpersonnellist",
            cache: false,
            data: {listPersonnel: idsPersonnel, listSize: listSize, id: idFormation,responsable:categorie,nuite:nuite},
            success: function (reponse) {
                $tableParticipant.bootstrapTable('refresh',{url:baseUrl  +"/absence/paginerformationpersonnelsduneequipe?id="+idFormation});

                // var listFormationPersonnel = reponse.rows || [];
                // $.each(listFormationPersonnel, function (index, equipeMission) {
                //     var idPersonnel = formationPersonnel.personnel.id;
                //     // var contratPersonnelIds = _.findWhere(tabIdsContratPersonnel, {idPersonnel: idPersonnel});
                //
                //     var contratPersonnel = {}, $row = $tableParticipant.find("tr[data-uniqueid='" + idPersonnel + "']"), indexRow;
                //     indexRow = $row.data("index");
                //     if (indexRow >= 0) {
                //         console.log("Index", indexRow);
                //         contratPersonnel.personnel = equipeMission.personnel;
                //         contratPersonnel.equipeMission = equipeMission;
                //         $tableParticipant.bootstrapTable('updateRow', {
                //             index: indexRow,
                //             row: contratPersonnel
                //         });
                //     }
                // });
            },
            beforeSend: function () {
                $btnCurrent.hide();
              //  $loaderParticipant.addClass('loader');
            },
            error: function () {
                $loaderParticipant.removeClass('loader');
            },
            complete: function () {
              //  $loaderParticipant.removeClass('loader');
                $btnCurrent.show();
                $tableParticipant.bootstrapTable('refresh',{url:baseUrl  +"/absence/paginerformationpersonnelsduneequipe?id="+idFormation});

            }
        });
    });


	$("#formAjout").submit(function(e){
		e.preventDefault();

        var formData = $(this).serialize();
        console.log("form", formData);
        $.ajax({
            type: "POST",
            url: baseUrl + actionUrl,
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == "succes") {
                	if(action == "ajout"){
                		//Rechargement de la liste (le tableau)
                		$table.bootstrapTable('refresh');
                		$("#formAjout")[0].reset(); //Initialisation du formulaire
                        $("#rhpModal").modal('hide');
                	}
                	else{
                		//MAJ de la ligne modifi�e
                		$table.bootstrapTable('updateRow', {
                            index: $table.find('tr.selected').data('index'),
                            row: reponse.data
                        });
                	}
                }
                else if(reponse.result == "erreur_champ_obligatoire"){
                	alert("Saisie invalide");
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

	$("#formDelete").submit(function(e){
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
                }
                else if(reponse.result == "erreur_champ_obligatoire"){
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
	//$( ".select2" ).select2();
});
function stateFormatter(value, row, index) {
    console.log("State", row);
    if (row.equipeMission) { //Deja participant
        return {
            disabled: true
        };
    }
    return value;
}

function matriculeFormatter(personnel) {
    return personnel.matricule;
}
function missFormatter(missions) {
    return missions.natureMission;
    //return missions.natureMission;
}

function nomCompletFormatter(personnel) {
    return personnel.nomComplet;
}

function telephoneFormatter(personnel) {
    return personnel.telephone;
}

function optionFormatter(id, row, index) {
	var option = '<a onclick="edit('+id+')" data-toggle="modal" data-target="#listParticipantModal" href="#" title="Modifier equipe [LIBELLE : '+row.libelle+' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
    option += '&nbsp;<a style="font-size:14px;" onclick="editequipe(' + row.id + ')" data-toggle="modal" data-target="#rhpModal" href="#" title="Facture"><span class="iconfa-shopping-cart"></span></a>&nbsp;';
	// option += '&nbsp;<a onclick="del('+id+')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer categorie [LIBELLE : '+row.libelle+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';
	option += '&nbsp;<a onclick="del('+id+')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer categorie [LIBELLE : '+row.libelle+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';

    return option;
}

function edit(idCategorie){
	var $scope = angular.element(document.getElementById("listParticipantModal")).scope();

	var rows = $table.bootstrapTable('getData');
	var equipe = _.findWhere(rows, {id: idCategorie});
			console.log("ropm",equipe);
			jQuery("#intutile").val(equipe.intitule);
          jQuery("#eqId").val(equipe.id);
	$scope.$apply(function () {
        $scope.pupulateForm(equipe);
    });
    $tableParticipant.bootstrapTable('refresh',{url:baseUrl  +"/absence/paginerformationpersonnelsduneequipe?id="+equipe.id});
   // jQuery("#idMission").trigger().val(equipe.id);
    jQuery("#idMission").val(equipe.missions.id).trigger("change");
}
function editequipe(idequipe){
    var $scope = angular.element(document.getElementById("formAjout")).scope();

    var rows = $table.bootstrapTable('getData');
    var equipe = _.findWhere(rows, {id: idequipe});
    console.log("dddd",equipe);
    $scope.$apply(function () {
        $scope.populateForm(equipe);
    });
}
function del(idCategorie){
	var $scope = angular.element(document.getElementById("formDelete")).scope();

	var rows = $table.bootstrapTable('getData');
	var categorie = _.findWhere(rows, {id: idCategorie});
	categorie.info = categorie.libelle;
	$scope.$apply(function () {
        $scope.pupulateForm(categorie);
    });


}
</script>
