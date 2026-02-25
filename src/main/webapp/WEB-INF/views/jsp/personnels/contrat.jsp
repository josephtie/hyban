<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
	<div class="col-md-12">
		<div class="panel panel-warning">
			<div class="panel-heading">
				<div class="panel-title-box">
					<h3>Liste des Contrats</h3>
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
										<li><a href="#" onclick="widgetView1()">Contrats en cours</a></li>
										<li><a href="#" onclick="widgetView()">Contrats Expires par Mois</a></li>
										<li><a href="#" onclick="widgetView2()">Contrats Expires par Date</a></li>
									</ul>
								</div>

							</div>
				<div id="tableWidget" >
				<div class="col-md-3">
                                <button id="btnGenererExcell" type="button"  class="btn btn-success "  onclick="chargercontratExcell()"><i class="fa fa-plus"></i>Exporter Excell</button>
                               <br/>
                  </div>
			    <table id="table" class="table table-info table-striped"
				   data-toggle="table"
				   data-click-to-select="true"
				   data-single-select="true"
				   data-sort-name="flag" data-sort-order="desc"
				   data-url="${pageContext.request.contextPath}/personnels/listcontratpersonneljson"
				   data-side-pagination="server"
				   data-pagination="true" data-search="true"
				   data-search="true"
				   data-page-list="[ 20, 50, 100, 200, 500,2000]">
				<thead>
				<tr>
					<th data-field="personnel" data-formatter="matriculeFormatter" data-align="left" data-sortable="true">Matricule</th>
					<th data-field="personnel" data-formatter="nomCompletFormatter" data-align="left" data-sortable="true">Nom</th>
					<th data-field="personnel" data-formatter="situationMatrimonialeFormatter">Sit. Matri</th>
					<th data-field="typeContrat" data-formatter="typeContratFormatter">Type de contrat</th>
                    <th data-field="fonction" data-formatter="fonctionFormatter">Fonction</th>
					<th data-field="personnel" data-formatter="modepaiementFormatter" data-align="center">Mode paiement</th>
                    <th data-field="personnel" data-formatter="numeroCompteFormatter"  data-align="center">Comptebanq/telephone</th>

					<th data-field="dDebut">Date d&eacute;but</th>
					<th data-field="dFin">Date fin</th>
					<th data-field="categorie" data-formatter="categorieFormatter" data-align="right">Salaire cat&eacute;goriel</th>
					<th data-field="netPayer" data-align="right">Net &agrave; payer</th>
					<th data-field="id" data-formatter="optionFormatter" data-width="100px" data-align="center">Options</th>
				</tr>
				</thead>
			</table>
		</div><!--widgetcontent-->

							<br/><br/>
		<div id="tableWidgethisto" class="widgetcontent">
		<div class="row">
            <div class="form-group">
			<div class="col-md-3">
				<select id="periodePaieImpression" name="periodePaieImpression" onchange="chargercontratParPeriode()" class="form-control select2" required="required"></select>
				<br/>
			</div>
			<div class="col-md-3">
                <button id="btnGenererExcellPeriode" type="button"  class="btn btn-success "  onclick="chargercontratParPeriodeExcell()"><i class="fa fa-plus"></i>Exporter Excell</button>
               <br/>
            </div></div></div>
			<table id="tablepm" class="table table-info table-striped"
				   data-toggle="table"
				   data-click-to-select="true"
				   data-single-select="true"
				   data-sort-name="flag" data-sort-order="desc"
			<%--   	data-url="${pageContext.request.contextPath}/personnels/listcontratpersonneljson" --%>
				   data-side-pagination="server"
				   data-pagination="true"
				   data-search="true"
				   data-page-list="[ 20, 50, 100, 200, 500,2000]">
				<thead>
				<tr>
					<th data-field="personnel" data-formatter="matriculeFormatter" data-align="left" data-sortable="true">Matricule</th>
					<th data-field="personnel" data-formatter="nomCompletFormatter" data-align="left" data-sortable="true">Nom</th>
					<th data-field="personnel" data-formatter="sexeFormatter" data-align="left">Sexe</th>
					<th data-field="personnel" data-formatter="cnpsFormatter">Num CNPS</th>
					<th data-field="personnel" data-formatter="situationMatrimonialeFormatter">Sit. Matri</th>
					<th data-field="personnel" data-formatter="nombreEnfantFormatter" data-align="right">Nbre d'enfants</th>
					<th data-field="typeContrat" data-formatter="typeContratFormatter">Type de contrat</th>
					<th data-field="fonction" data-formatter="fonctionFormatter">Fonction</th>
					<th data-field="dDebut">Date d&eacute;but</th>
					<th data-field="dFin">Date fin</th>
					<th data-field="categorie" data-formatter="categorieFormatter" data-align="right">Salaire cat&eacute;goriel</th>
					<th data-field="netPayer" data-align="right">Net &agrave; payer</th>
					<!-- <th data-field="id" data-formatter="optionFormatter" data-width="100px" data-align="center">Options</th> -->
				</tr>
				</thead>
			</table>
		</div><!--widgetcontent-->

		<div id="tableWidgetDate" class="widgetcontent">
		            <div class="row">
                    <div class="form-group">
						<div class="col-md-3">
								<input type="text" id="dateDebw" name="dateDebw"  data-date-format='dd/mm/yyyy' maxlength="10" class="form-control datepicker"  placeholder="Date de debut de contrat" required="required" />
						<br/>
						</div>
						<div class="col-md-3">
							<input type="text" id="dateFinw" name="dateFinw"  data-date-format='dd/mm/yyyy' maxlength="10" class="form-control datepicker" placeholder="Date de fin de contrat" required="required" />
						<br/>
						</div>
						<div class="col-md-3">
								<button id="btnGenerer" type="button"  class="btn btn-success "  onclick="chargercontratParDate()"><i class="fa fa-plus"></i>Rechercher</button>
						<br/>
						</div>
                 	<div class="col-md-3">
                 		<button id="btnGenererExcellDate" type="button"  class="btn btn-success "  onclick="chargercontratParDateExell()"><i class="fa fa-plus"></i>Exporter Excell</button>
                 		<br/>
                 	</div>
        			</div>
        			</div>
        			<table id="tablepmDate" class="table table-info table-striped"
        				   data-toggle="table"
        				   data-click-to-select="true"
        				   data-single-select="true"
        				   data-sort-name="flag" data-sort-order="desc"
        			<%--   	data-url="${pageContext.request.contextPath}/personnels/listcontratpersonneljson" --%>
        				   data-side-pagination="server"
        				   data-pagination="true"
        				   data-search="true"
        				   data-page-list="[ 20, 50, 100, 200, 500,2000]">
        				<thead>
        				<tr>
        					<th data-field="personnel" data-formatter="matriculeFormatter" data-align="left" data-sortable="true">Matricule</th>
        					<th data-field="personnel" data-formatter="nomCompletFormatter" data-align="left" data-sortable="true">Nom</th>
        					<th data-field="personnel" data-formatter="sexeFormatter" data-align="left">Sexe</th>
        					<th data-field="personnel" data-formatter="cnpsFormatter">Num CNPS</th>
        					<th data-field="personnel" data-formatter="situationMatrimonialeFormatter">Sit. Matri</th>
        					<th data-field="personnel" data-formatter="nombreEnfantFormatter" data-align="right">Nbre d'enfants</th>
        					<th data-field="typeContrat" data-formatter="typeContratFormatter">Type de contrat</th>
        					<th data-field="fonction" data-formatter="fonctionFormatter">Fonction</th>
        					<th data-field="dDebut">Date d&eacute;but</th>
        					<th data-field="dFin">Date fin</th>
        					<th data-field="categorie" data-formatter="categorieFormatter" data-align="right">Salaire cat&eacute;goriel</th>
        					<th data-field="netPayer" data-align="right">Net &agrave; payer</th>
        					<!-- <th data-field="id" data-formatter="optionFormatter" data-width="100px" data-align="center">Options</th> -->
        				</tr>
        				</thead>
        			</table>
        		</div><!--widgetcontent-->
	    </div><!--widgetbox-->
</div><!-- widgetcontent-->
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="rhpModal" role="dialog" tabindex="-1" aria-labelledby="rhpModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<form id="formAjout" class="form-horizontal" role="form" novalidate="novalidate" ng-controller="formAjoutCtrl">
				<div class="modal-header">
					<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
					<h4 class="modal-title" id="myModalLabel">Mettre fin au contrat</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<div class="col-md-12">
							<label ng-bind="contrat.info"></label>
						</div>
					</div>
					<div class="form-group">
						<label for="libelle" class="col-md-2 control-label">Date debut</label>
						<div class="col-md-10">
							<input type="text" ng-model="contrat.dDebut" class="form-control" disabled="disabled" required="required" />
						</div>
					</div>
					<div class="form-group">
						<label for="libelle" class="col-md-2 control-label">Date de modification</label>
						<div class="col-md-10">
							<input type="text" id="dateMod" name="dateMod" ng-model="contrat.dMod" data-date-format='dd/mm/yyyy' maxlength="10" class="form-control datepicker" placeholder="Date de modification" required="required" />
						</div>
					</div>
						<div class="form-group">
                    		<label for="libelle" class="col-md-2 control-label">Date de Fin/ dArret'</label>
                    		<div class="col-md-10">
                    		<input type="text" id="dateFin" name="dateFin" ng-model="contrat.dFin" data-date-format='dd/mm/yyyy' maxlength="10" class="form-control datepicker"   placeholder="Date de fin / Arret" required="required" />
                    			</div>
                    		</div>
					<div class="form-group">
						<label for="faute" class="col-md-2 control-label">Depart</label>
						<div class="col-md-10">

							<div>

								<label id="permanentNon" class="radio-inline">
									<input name="permanent" type="radio" value="false"> Non
								</label>
								<label id="permanentOui" class="radio-inline">
									<input name="permanent" type="radio" value="true"> Oui
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="matricule" class="col-md-2 control-label">Observation<span class="required">*</span></label>
						<div>
							<select id=ObservCtrat name="ObservCtrat" class="form-control select2">
								<option>-- Choix --</option>
								<option value="Aucun"  selected="selected"> Aucun </option>
								<option value="Demission" > Demission </option>
								<option value="Deces" > Deces </option>
								<option value="Fin de contrat" > Fin de contrat </option>
								<option value="Renvoi" > Renvoi </option>
								<option value="Modification" > Modification </option>
							</select>
						</div>
                     </div>
				</div>
				<div class="modal-footer">
					<input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="contrat.id">
					<span></span>&nbsp;
					<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
					<button type="submit" class="btn btn-success">Valider</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">

    //AngularJS
		app.controller('formAjoutCtrl', function ($scope) {
			$scope.pupulateForm = function (contrat) {
				$scope.contrat = contrat;
			};
			$scope.initForm = function () {
				$scope.contrat = {};
			};
		});
    //End AngularJs


			var actionUrl = "/personnels/endcontratpersonnel";
			var action = "ajout";
			var $table;
			jQuery(document).ready(function($){
				$table = $('#table');
				$tableImprimer = $('#tablepm');
				jQuery('#tableWidgethisto').hide();
				jQuery('#tableWidgetDate').hide();
				jQuery('#tableWidget').show();
				$(".select2").select2();
				// jQuery("#dateFin,  #dateDew, #dateMod, #dateFinw").datepicker({
				//  format: 'DD/MM/YYYY',
				//  showOtherMonths: true
				// });

				$('.datepicker').datepicker({
					format: 'dd/mm/yyyy',
					showOtherMonths: true
				});

				// $('.datepicker').datetimepicker({
				// 	format: 'DD/MM/YYYY',
				// 	useCurrent: false,
				// 	showClear: true,
				// 	showClose: true
				// });

				chargerPeriodePaie();
				//Fermeture du modal
				$('#rhpModal').on('hidden.bs.modal', function () {
					var $scope = angular.element(document.getElementById("formAjout")).scope();
					$scope.$apply(function () {
						$scope.initForm();
					});
					//$("#id").val(""); //Initialisation de l'id
				});

				//Envoi des donnees
				$("#formAjout").submit(function(e){
					e.preventDefault();

					var formData = $(this).serialize();
					$.ajax({
						type: "POST",
						url: baseUrl + actionUrl,
						cache: false,
						data: formData,
						success: function (reponse) {
							if (reponse.result == "success") {
								$table.bootstrapTable('refresh');
								$("#rhpModal").modal('hide');
							}
							else if(reponse.result == "erreur_champ_obligatoire" || reponse.result == "failed"){
								alert("Date invalide");
							}
						},
						error: function () {
							$("#rhpModal .modal-body div.alert").alert();
							$("#rhpModal .modal-body .alert h4").html("Erreur survenue");
							$("#rhpModal .modal-body .alert p").html("Verifier que vous etes connectes au serveur.");
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
			});

			//Functions
			function optionFormatter(id, row, index) {
				var option = '<a onclick="finish('+row.id+')" data-toggle="modal" data-target="#rhpModal" href="#" title="Mettre fin">  <span class="glyphicon glyphicon-pencil"></span></a>';
				return option;
			}

			function chargercontratParPeriode(){

				$tableImprimer = jQuery('#tablepm');
				$tableImprimer.bootstrapTable('removeAll');
				$tableImprimer.bootstrapTable ('refresh', {url: baseUrl +'/personnels/listcontratpersonnelExpjson?id='+ jQuery('#periodePaieImpression').val()});
				$tableImprimer.bootstrapTable('scrollTo', 0);


			}


			function chargercontratParDate(){

				$tableDatew = jQuery('#tablepmDate');
				$tableDatew.bootstrapTable('removeAll');
				$tableDatew.bootstrapTable ('refresh', {url: baseUrl +'/personnels/listcontratpersonnelExpDatejson?dateDebw='+ jQuery('#dateDebw').val()+'&dateFinw='+ jQuery('#dateFinw').val()});
				$tableDatew.bootstrapTable('scrollTo', 0);


			}
			function chargercontratParDateExell(){
			$('#tablepmDate').tableExport({
										type: 'excel',
										fileName: 'export_Contratdate',
										exportDataType: 'all' // 'all', 'selected' ou 'basic'
			 });
			}
			function chargercontratParPeriodeExcell(){
				  $('#tablepm').tableExport({
												type: 'excel',
												fileName: 'export_Contratmois',
												exportDataType: 'all' // 'all', 'selected' ou 'basic'
					 });
			}

			function   chargercontratExcell(){
			  $('#table').tableExport({
											type: 'excel',
											fileName: 'export_Contrat',
											exportDataType: 'all' // 'all', 'selected' ou 'basic'
				 });
			}
			function finish(idContrat){
				var $scope = angular.element(document.getElementById("formAjout")).scope();

				var rows = $table.bootstrapTable('getData');
				var contrat = _.findWhere(rows, {id: idContrat});
				contrat.info = contrat.personnel.matricule + " | " + contrat.personnel.nomComplet + " | " + contrat.fonction.libelle;
				if (contrat.depart == true) {
					jQuery("#permanentOui span").addClass("checked");
					jQuery("#permanentOui :radio").attr("checked", true);
					jQuery("#permanentNon span").removeClass("checked");
				} else if(contrat.depart == false) {
					jQuery("#permanentNon span").addClass("checked");
					jQuery("#permanentNon :radio").attr("checked", true);
					jQuery("#permanentOui span").removeClass("checked");
				}
				$scope.$apply(function () {
					$scope.pupulateForm(contrat);
				});
			}

			function del(idFonction){
				var $scope = angular.element(document.getElementById("formDelete")).scope();

				var rows = $table.bootstrapTable('getData');
				var fonction = _.findWhere(rows, {id: idFonction});
				fonction.info = fonction.libelle;
				$scope.$apply(function () {
					$scope.pupulateForm(fonction);
				});
			}

			function matriculeFormatter(personnel){
				return personnel.matricule;
			}
			function modepaiementFormatter(personnel){
				return personnel.modePaiement;
			}
			function modepaiementFormatter(personnel){
				return personnel.modePaiement;
			}

			function numeroCompteFormatter(personnel){
				if(personnel.numeroCompte == ''){
                		return personnel.telephone;
                	}
                	return personnel.numeroCompte;
			}

			function nomCompletFormatter(personnel){
				return personnel.nomComplet;
			}

			function sexeFormatter(personnel){
				return personnel.sexe;
			}

			function cnpsFormatter(personnel){
				return personnel.numeroCnps;
			}

			function situationMatrimonialeFormatter(personnel){
				return personnel.situationMatri;
			}

			function nombreEnfantFormatter(personnel){
				return personnel.nombrEnfant;
			}

			function typeContratFormatter(typeContrat){
				return typeContrat.libelle;
			}

			function fonctionFormatter(fonction){
				return fonction.libelle;
			}

			function categorieFormatter(categorie){
				return categorie.salaireBase;
			}

			function chargerPeriodePaie(){
				jQuery.ajax({
					type: "POST",
					url: baseUrl + "/parametrages/periodeall",
					cache: false,
					success: function (response) {
						if (response != null) {
							tabledata = '<option value="0" data-libelle="CHOISIR PERIODE PAIE" >CHOISIR PERIODE PAIE</option>';
							for (i = 0; i < response.length; i++) {
								tabledata += '<option value="'+response[i].id+'" data-libelle="'+response[i].mois.mois + ' ' + response[i].annee.annee+'" >' + response[i].mois.mois + ' ' + response[i].annee.annee + '</option>';
							}
							jQuery('#periodePaieImpression').html(tabledata);
							jQuery('#periodePaieImpression').select2('val', 0);
							//jQuery('#branch').select2('val', index);
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

			function widgetView(){

					jQuery('#tableWidget').hide('slow');
					jQuery('#tableWidgetDate').hide('slow');
					jQuery('#tableWidgethisto').show('slow');

			}
			function widgetView1(){
			jQuery('#tableWidget').show('slow');
			jQuery('#tableWidgethisto').hide('slow');
			jQuery('#tableWidgetDate').hide('slow');
			}
			function widgetView2(){
				jQuery('#tableWidgetDate').show('slow');
				jQuery('#tableWidgethisto').hide('slow');
				jQuery('#tableWidget').hide('slow');
			}


</script>
