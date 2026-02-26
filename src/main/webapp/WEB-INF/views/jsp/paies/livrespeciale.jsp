<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row" >
	<div class="col-md-12">
		<div class="panel panel-warning">
			<div class="panel-heading">
				<div class="panel-title-box">
					<h3>Liste du personnel</h3>
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
											<li><a href="#" onclick="widgetView('new')">Livre de paie</a></li>
											<%--<li class="divider"></li>--%>
											<%--<li><a href="#" onclick="widgetView('histo')">Historique Bulletins</a></li>--%>
									</ul>
								</div>

							</div>
						<br>


               <div id="tableWidget" class="widgetcontent">
                <div id="toolbar1">
                    <div class="form-inline">
                     <button id="btnGenerer" type="submit" data-toggle="modal" data-target="#rhpModal" title="Cocher les employes mis en sommeil" class="btn btn-info "><i class="fa fa-plus"></i>Generer livre de paie</button>
                    <button id="btnGenerer" type="button" data-toggle="modal" data-target="#rhpModalImprimer" class="btn btn-info "  onclick="chargerBull()"><i class="fa fa-plus"></i>Imprimer bulletin</button>
                    <button id="btnVrmt" type="button" data-toggle="modal" data-target="#rhpModalVirmt" class="btn btn-info "><i class="fa fa-plus"></i>Ordre de virement</button>
                    <button id="btnGenererNet" type="submit" title="Prise en compte Net apayer" class="btn btn-info "><i class="fa fa-plus"></i>Net à payer regul</button>
                    </div>
                </div>
		    	<form action="#" id="formList">
		<table id="table" class="table table-info table-striped"
        			       	data-toggle="table"
        			       	data-click-to-select="true"
        			       	data-single-select="true"
        			       	data-sort-name="flag" data-sort-order="desc"
        				   data-url="<%=request.getContextPath()%>/personnels/specifique/special-contracts/listcontratspecjson"
        			       	data-side-pagination="server"
        			       	data-pagination="true"
        			       	data-page-list="[ 20, 50, 100, 200,500]"
        			       	data-search="true">
        			    <thead>
        			        <tr>
        			        	<th data-field="employee" data-formatter="matriFormatter" data-align="left" data-sortable="true">Matricule</th>
        			        	<th data-field="employee" data-formatter="nomFormatter" data-align="left" data-sortable="true">Nom & Prenom</th>
        			        	<th data-field="typeContrat"   data-align="left" data-sortable="true">Categ Speciale</th>
        			        	<th data-field="fonction" data-formatter="fonctionFormatter"data-align="left"  data-sortable="true">Fonction</th>
        			        	<th data-field="remunerationForfaitaire" data-align="left"  data-sortable="true">Net a payer</th>
        			        	<th data-field="modepaiement" data-align="left"  data-sortable="true">Mode de paiement</th>
        			        	<th data-field="id" data-formatter="optionFormatter" data-width="100px" data-align="center">Options</th>
        			        </tr>
        			    </thead>
        			</table>
			</form>
		</div>
    </div><!--widgetbox-->
</div><!-- widgetcontent-->
</div>
</div>




<!-- Generation des bulletins -->
<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formGenerer" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Generation du livre de paie (Personnel Specifique)</h4>
				</div>
                <div class="modal-body">
                	<h3>Vous etes sur le point de generer le livre de paie de la periode de <span id="periodePaie" class="danger">Mars 2016</span> du personnel Speciale.</h3>
                	<br/>
                	<h4>En cliquant sur le bouton "Valider", le proccessus sera lanc&eacute;.</h4>
                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                	<input type="hidden"  id="idPeriode" name="id">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Modification info du personnel -->
<div class="modal fade" id="rhpModalModiftemps"  role="dialog" aria-labelledby="rhpModalModiftemps" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formModificationPersonneltemps" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">Modification du temps de travail du personnel (<span id="infoPersonnelmo">Information du personnel</span>)</h4>
				</div>
                <div class="modal-body">
                	<div class="form-group">
                        <label for="libelle" class="col-md-4 control-label">Nombre de jours</label>
                        <div class="col-md-8">
                            <input type="text" id="jourTravail"  name="jourTravail" class="form-control" required="required" placeholder=""30" />
                        </div>
                    </div>
                    	<div class="form-group">
                        <label for="libelle" class="col-md-4 control-label">Temps travail</label>
                        <div class="col-md-8">
                            <input type="text" id="temptravail"  name="temptravail" required="required" class="form-control" placeholder="173.33"/>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <input type="hidden" value="" id="matricule" name="matricule">
                    <input type="hidden" value="" id="idpers" name="idpers">
                     <input type="hidden" value="" id="idperiod" name="idperiod">
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>


<!-- Modification info du personnel -->
<div class="modal fade" id="rhpModalModiftemps"  role="dialog" aria-labelledby="rhpModalModiftemps" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formModificationPersonneltemps" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">Modification du temps de travail du personnel (<span id="infoPersonnelmo">Information du personnel</span>)</h4>
				</div>
                <div class="modal-body">
                	<div class="form-group">
                        <label for="libelle" class="col-md-4 control-label">Nombre de jours</label>
                        <div class="col-md-8">
                            <input type="text" id="jourTravail"  name="jourTravail" class="form-control" required="required" placeholder=""30" />
                        </div>
                    </div>
                    	<div class="form-group">
                        <label for="libelle" class="col-md-4 control-label">Temps travail</label>
                        <div class="col-md-8">
                            <input type="text" id="temptravail"  name="temptravail" required="required" class="form-control" placeholder="173.33"/>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <input type="hidden" value="" id="matricule" name="matricule">
                    <input type="hidden" value="" id="idpers" name="idpers">
                     <input type="hidden" value="" id="idperiod" name="idperiod">
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Livre de paie -->
<div class="modal fade" id="rhpModalValider"  role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static" >
    <div class="modal-dialog" role="document" style="width:80%;"  >
        <div class="modal-content">
            <form id="formGenererValidBull" class="form-horizontal" >
                <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Valider les bulletins de paie de ${periode}</h4>
				</div>
                <div class="modal-body">
					<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
					<button type="submit" onclick="genererLivreDePaie()" class="btn btn-info">Valider</button>
					 <button id="exportExcelLiv" class="btn btn-info" > Exporter en Excel</button>



                <!-- <div id="toolbar">
			    <div class="form-inline">
			        <button id="btnGenerer5" type="submit" data-toggle="modal" data-target="#rhpModal5 " title="Cocher les employ�s mis en sommeil" class="btn btn-primary btn-xs"><i class="fa fa-plus"></i>G�n�rer tous les bulletins</button>
			    </div>
			   </div> -->

              <%-- <form action="#" id="formList"> --%>

				<div class="table-responsive"  style="max-height: 500px; overflow-y: auto;">
				<table id="tableliv" class="table table-info table-striped table-responsive" style="overflow:auto"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-sort-name="name"
					data-pagination="true"
					data-sort-order="desc"
					data-show-footer="false"
					<%-- data-url="${pageContext.request.contextPath}/paie/savelivrePersonnel"  data-show-columns="true"  --%>
					data-side-pagination="server"
					data-pagination="true"
					data-page-list="[ 20, 50, 100, 200, 500,2000]"
					data-show-export="true"
					data-export-dataType="all"
					data-search="true">
					<thead>

							<tr>
							<th data-field="matricule"  data-align="left" data-sortable="true" style="height: 65px">Matricule</th>
							<th data-field="nomPrenom"  data-align="left" data-sortable="true">Nom  &   Prenoms  </th>
							<th data-field="jourTravail"  data-align="left" data-sortable="true">Nbre de jrs trv mois </th>
								<%--<th data-field="contratPersonnel" data-formatter="nomstatutFormatter" data-align="left" data-sortable="true">Statut</th>--%>



							<th data-field="mtavceAcpte"   data-align="left">Avance & Acompte</th>
							<th data-field="mtpretAlios"  data-align="center">Pret</th>

							<th data-field="mtregularisation"  data-align="center">Regularisation</th>
							<th data-field="mtnetPayer"  data-align="center">Net a payer</th>

							<th data-field="mttotalMasseSalariale" data-align="right">Total masse salariale</th>


							<!-- <th data-field="id" data-formatter="optionFormatter1" data-align="center">Options</th>  -->

						   </tr>

					</thead>
				</table>

						</div>

                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;


                </div>
            </form>
        </div>
    </div>
</div>


<div class="modal fade" id="rhpModalImprimer"  role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static" >
    <div class="modal-dialog" role="document" style="width:80%;" >
        <div class="modal-content">
            <form id="formImrimBull" class="form-horizontal" >
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Imprimer les bulletins de paie de  ${periode}</h4>

				</div>
                <div class="modal-body">
					<br>
                  <button id="exportExcelBull" class="btn btn-info" style="margin-bottom: 10px;">
                                    Exporter en Excel
                                </button>

                    <div class="table-responsive" style="max-height: 500px; overflow-y: auto;">
				<table id="tableBull" class="table table-info table-striped"
					data-toggle="table"
					data-single-select="false"
					data-show-export="true"
					data-click-to-select="true"
					data-show-footer="false"
					data-export-data-type="all"
		        	data-url="${pageContext.request.contextPath}/paie/listbulletinSpecialMoisActif"
					data-sort-order="desc"
					data-show-columns="true"
					data-side-pagination="server"
					data-pagination="true"
					data-page-list="[ 50, 100, 200, 500,2000]"
					data-search="true">
					<thead>
						<tr>
							<th data-field="matricule"  data-align="left" data-sortable="true" style="height: 65px">Matricule</th>
                        	<th data-field="nomPrenom"  data-align="left" data-sortable="true">Nom  &   Prenoms  </th>
                        	<th data-field="jourTravail"  data-align="left" data-sortable="true">Nbre de jrs trv mois </th>
                        	<th data-field="specialcontract" data-formatter="typecontratFormatter" data-align="left" data-sortable="true">Statut</th>--%>
                        	<th data-field="specialcontract" data-formatter="renumerationFormatter" data-align="left" data-sortable="true">Renumeration</th>--%>



                        	<th data-field="avceAcpte"   data-align="left">Avance & Acompte</th>
                        	<th data-field="pretAlios"  data-align="center">Pret</th>

                        	<th data-field="regularisation"  data-align="center">Regularisation</th>
                        	<th data-field="netPayer"  data-align="center">Net a payer</th>




						<!--	<th data-field="id" data-formatter="optionFormatter2" data-align="center">Options</th>-->

						</tr>
					</thead>
				</table>

                    </div>
                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                	<!-- <input type="hidden"  id="idPeriode1" name="id"> -->
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                  <!--  <button type="submit" class="btn btn-success">Imprimer</button>-->
                    <button id="julayaExp" type="button" class="btn btn-info">Export julaya</button>

                </div>
            </form>
        </div>
    </div>
</div>




<div class="modal  fade "  id="rhpModalPretModif" role="dialog" data-backdrop="static">
	<div class="modal-dialog ">
		<div class="modal-content">
			<form id="formPretmodif" action="#">
				  <div class="modal-header">
                      <h4 class="modal-title" id="myModalLabel">Ajout de pret ou Avance du personnel specifique (<span id="infoPersonnel">Information du personnel</span>)</h4>
                	</div>
                  <div class="modal-body">
					<div class="form-group">
							<label for="service" class="col-md-4 control-label">Type</label>
							<div class="col-md-8">
							<select id="pret1" name="pret1" class="form-control select2">
							<c:forEach items="${listPrets}" var="pret">
							<option value="${pret.id}">${pret.libelle}</option>
							</c:forEach>
							</select>
							</div>
					</div>
					<div class="form-group">
							<label for="montant" class="col-md-4 control-label">Montant</label>
							<div class="col-md-8">
							<input type="text" id="montant1" name="montant1" class="form-control" placeholder="Montant" />
							</div>
					</div>
					<div class="form-group">
							<label for="" class="col-md-4 control-label">Periode debut prelev.</label>
							<div class="col-md-8">
							<select id="periodepaie1" name="periodepaie1" class="form-control select2">
							<c:forEach items="${listPeriodepaie}" var="periodePaie">
							<option value="${periodePaie.id}">${periodePaie.mois.mois} ${periodePaie.annee.annee}</option>
							</c:forEach>
							</select>
							</div>
					</div>
					<div class="form-group">
							<label for="" class="col-md-4 control-label">Date contraction</label>
							<div class="col-md-8">
							<input type="text" id="dEmprunt1" name="dEmprunt1" class="form-control" placeholder="Date contraction" />
							</div>
					</div>
					<div class="form-group">
							<label for="" class="col-md-4 control-label">Mensualite</label>
								<div class="col-md-8">
									<input type="text" id="echelonage1" name="echelonage1" class="form-control"  />
									<input type="hidden" id="idpers1" name="idpers1" class="form-control"  />

								</div>
					</div>
					<div class="form-group">
							<label class="col-md-4 control-label">Prelevement/Periode</label>
							<div class="col-md-8">
							{{prelevement}}
					</div>
				 </div>
                 <div class="modal-footer">

							<div class="col-md-12">
							<span class="pull-right">
							<span></span>&nbsp;&ndash;%&gt;
							<input type="hidden" id="idpret" name="idpret" class="form-control"  />
							<input class="btn btn-default" type="button" data-dismiss="modal" value="Annuler" />
							<input class="btn btn-success" type="submit" value="Valider" />
                 <div>
							<%--</div><!--widgetcontent-->--%>
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
	            	<h4 ng-bind="employe.info"></h4>
	            </div>
	            <div class="modal-footer">
                	<input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="employe.id">
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
    $scope.pupulateForm = function (employee) {
        $scope.employee = employee;
    };
     $scope.pupulateContrat = function (speccontrat) {
          $scope.speccontrat = speccontrat;
     };
    $scope.initForm = function () {
    	$scope.employee = {};
    	$scope.speccontrat= {};
    };
}).controller('formDeleteCtrl', function ($scope) {
    $scope.pupulateForm = function (employee) {
    	$scope.employee = employee;
    };

});
//End AngularJs
var contextPath = "<%=request.getContextPath() %>";
var actionUrl = "/personnels/specifique/enregisteremployee";
var actionDeleteUrl = "/personnels/supprimeremployee";
var action = "ajout";
var $table;
jQuery(document).ready(function($){
	$table = $('#table');
	$("#datenaissance, .datePicker, #dateNaissanceEnfant, #datearrivee, #datedebut, #datefin,  #dDeb, #dFin,#datedebutPop, #datefinPop,#dateDepot,#dEmprunt1").datepicker({
            format: 'dd/mm/yyyy',
            showOtherMonths: true
        });
        	var periode='';var periodeID='';
        	chargerPeriodePaie()
        	periode='${activeMois}';
        	  $tableConge = $('#tableConge');
        	periodeID='${activeMoisId}';
        	jQuery('#idPeriode1').val(periodeID);
        	jQuery('#idperiod').val(periodeID);
        	$table = $('#table');
        	$tablebull = $('#tableBull');
        //	alert(periode);
        	//alert(periodeID);
        	jQuery('#periodePaie').html(periode);
        	jQuery('#idPeriode').val(periodeID);

	//Fermeture du modal
$('#rhpModal').on('hidden.bs.modal', function () {
    var $scope = angular.element(document.getElementById("formAjout")).scope();


});

	//Envoi des donnees
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
                 if (reponse.result && reponse.status) {
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

	jQuery("#formGenerer").submit(function(e){
    	//alert('bonjour');
    	e.preventDefault();
    	periodeID='${activeMoisId}';
    	jQuery('#idPeriode').val(periodeID);
    	jQuery('#idPeriode1').val(periodeID);
    	jQuery('#idperiod').val(periodeID);

    	jQuery.ajax({
            type: "GET",
            url: contextPath + "/paie/savebullEmployee",
            cache: false,
            data: {id: '${activeMoisId}'},
            success: function (response) {
                if (response.result == "success") {

                	jQuery("#rhpModal").modal('hide');
                	jQuery("#rhpModalValider").modal();
                //	jQuery ('#tableliv').bootstrapTable ('refresh', {  url: baseUrl +'/paie/savebullPersonnel?id='+ jQuery('#idPeriode').val()});
                }
                else if(response.result == "erreur_champ_obligatoire"){
                	alert("Saisie invalide");
                }
            },
            error: function () {
            	jQuery("#rhpModal .modal-body div.alert").alert();
            	jQuery("#rhpModal .modal-body .alert h4").html("Erreur survenue");
            	jQuery("#rhpModal .modal-body .alert p").html("Verifier que vous etes connectes au serveur.");
            	jQuery("#rhpModal .modal-footer span").removeClass('loader');
            },
            beforeSend: function () {
            	jQuery("#formAjout").attr("disabled", true);
            	jQuery("#rhpModal .modal-footer span").addClass('loader');
            },
            complete: function () {
            	jQuery("#formGenerer").removeAttr("disabled");
            	jQuery("#rhpModal .modal-footer span").removeClass('loader');
           	jQuery('#tableliv').bootstrapTable('refresh', {  url: baseUrl +'/paie/savebullEmployee?id='+ jQuery('#idPeriode').val()});
           //	location.reload();

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

	jQuery("#jourTravail").blur(function(){
	    var nbjou = parseFloat($("#jourTravail").val()) || 0;

        var heures = nbjou * 173.33 / 30;

        $("#temptravail").val(Math.round(heures * 100) / 100);

	});

	function cherch2(matricule){
    // alert(idFonction);
    	jQuery('#matricule').val(matricule);

    	jQuery('#infoPersonnelmo').html(matricule);
    	jQuery('#idpers').val(matricule);

    	jQuery.ajax({
            type: "GET",
            url: baseUrl+"/paie/cherchtempeffectifEmp",
            data: {idPersonnel: jQuery('#idpers').val(),
            	idPeriodDep:jQuery('#idperiod').val()},
            cache: false,
    		success: function (response) {
            	if (response != null) {
            		//alert(response.result);
            		jQuery('#jourTravail').val(response.jourspresence);
            		jQuery('#temptravail').val(response.heurspresence);
            		jQuery('#idperiod').val(response.periodePaie.id);
            		jQuery('#idpers').val(response.employee.matricule);

    			} else {
    				alert('Impossible de charger cet objet');
    			}
            },
            error: function () {

            },
            complete: function () {
            //	jQuery("#formGenerer").removeAttr("disabled");
            //	jQuery("#rhpModal .modal-footer span").removeClass('loader');
        //   	$tablebull.bootstrapTable('refresh', {url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ jQuery('#idPeriode').val()});
            }
        });

    }

jQuery("#formModificationPersonneltemps").submit(function(e){
	e.preventDefault();
	//alert();
    var formData = jQuery(this).serialize();
    console.log("form", formData);
    jQuery.ajax({
        type: "POST",
        url: baseUrl + "/paie/savetempeffectifEmp",
        cache: false,
        data: { temptravail:jQuery('#temptravail').val(),
        	   jourtravail:jQuery('#jourTravail').val(),
        	   idPers: $('#idpers').val(),
              idPeriodDep:jQuery('#idperiod').val()},
        success: function (reponse) {
            if (reponse.result == "success") {
            	//$table.bootstrapTable('refresh');
            	jQuery("#formModificationPersonneltemps")[0].reset(); //Initialisation du formulaire
            	jQuery("#rhpModalModiftemps").modal('hide');
        		alert('Operation effectuee avec succes');
            }
            else if(reponse.result == "erreur_champ_obligatoire"){
            	alert("Saisie invalide");
            }
        },
        error: function () {
        	/* jQuery("#rhpModalModiftemps div.alert").alert();
        	jQuery("#rhpModalModiftemps .alert h4").html("Erreur survenue");
        	jQuery("#rhpModalModiftemps .alert p").html("Verifier que vous �tes connect�s au serveur."); */
        	//$("#formModificationPersonneltemps span.load").removeClass('loader');
        },
        beforeSend: function () {
        	/* jQuery("#formModificationPersonneltemps").attr("disabled", true);
            jQuery("#formModificationPersonneltemps span.load").addClass('loader'); */
        },
        complete: function () {
        /* 	jQuery("#formModificationPersonneltemps").removeAttr("disabled");
            //$("#formModificationPersonneltemps span.load").removeClass('loader');*/
            $table.bootstrapTable ('refresh', {  url: baseUrl +'/personnels/specifique/special-contracts/listcontratspecjson' });
        }
    });

});
    $('#exportExcelBull').click(function () {
                $('#tableBull').tableExport({
                    type: 'excel',
                    fileName: 'export_bulletinpaie',
                    exportDataType: 'all' // 'all', 'selected' ou 'basic'
                });
            });
	$("#formPretmodif").submit(function(e){
		e.preventDefault();
        var formData = $(this).serialize();
        console.log("form", formData);
        jQuery.ajax({
            type: "POST",
            url: baseUrl + "/paie/savepretEmployee",
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == "success") {
                	$table.bootstrapTable('refresh');
                	jQuery("#formPretmodif")[0].reset(); //Initialisation du formulaire
                	jQuery("#rhpModalPretModif").modal('hide');
                	alert(reponse.result);
                }
                else if(reponse.result == "erreur_champ_obligatoire"){
                	alert("Saisie invalide");
                }
            },
            error: function () {
            	jQuery("#rhpModalPret div.alert").alert();
            	jQuery("#rhpModalPret .alert h4").html("Erreur survenue");
            	jQuery("#rhpModalPret .alert p").html("Verifier que vous etes connectes au serveur.");
            	//$("#formPret span.load").removeClass('loader');
            },
            beforeSend: function () {
            	jQuery("#formPret").attr("disabled", true);
                jQuery("#formPret span.load").addClass('loader');
            },
            complete: function () {
            	jQuery("#formPretmodif").removeAttr("disabled");
                //$("#formPret span.load").removeClass('loader');
                jQuery('#tablef'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/pretPersonneljson' });
            }
        });
	});

	$('#julayaExp').click(function () {

                  window.location.href = contextPath + "/paie/exportSeciale";
     });
function optionFormatter(id, row, index) {
	/*var option = '<a onclick="selectPersInfo('+id+','+row.id+')" data-toggle="modal" data-target="#rhpModalModif" href="#" title="Modifier personnel [LIBELLE : '+row.employee.matricule+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';*/
	/*var option = '<a onclick="cherch2(' +row.employee.matricule+ ')" data-toggle="modal" data-target="#rhpModalModiftemps" href="#" title="Modifier personnel [LIBELLE : '+row.employee.matricule+' ]">  <span class="glyphicon glyphicon-list"></span></a>&nbsp;';*/
var option = '<a onclick="cherch2(\'' + row.employee.matricule + '\')" ' +
             'data-toggle="modal" data-target="#rhpModalModiftemps" href="#" ' +
             'title="Modifier personnel [LIBELLE : ' + row.employee.matricule + ' ]">' +
             '<span class="glyphicon glyphicon-list"></span></a>&nbsp;';
   option += '<a onclick="editPret('+row.employee.id+')" data-toggle="modal" data-target="#rhpModalPretModif" href="#" title="Ajouter pr�t [LIBELLE : '+row.employee.nom+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	/*option += '&nbsp;<a onclick="editPret(\'' + row.employee.matricule + '\')" '+'data-toggle="modal" data-target="#rhpModalPretModif" href="#" title="Pret & Avance [LIBELLE : '+row.employee.matricule+' ]"> <span class="glyphicon glyphicon-pencil"></span></a>';*/
	/* option += '<a onclick="listMouvementConge('+id+','+row.employee.id+')" data-toggle="modal" data-target="#listPrimesDiversModal" href="#" title="Modifier personnel [LIBELLE : '+row.employee.matricule+' ]">  <span class="glyphicon glyphicon-briefcase"></span></a>&nbsp;'; */

    return option;
}

function nationaliteFormatter(nationnalite) {
    return nationnalite.libelle;
}

function matriFormatter(employee, row, index) {
	if(row.employee.matricule == ''){
		return "";
	}
	return row.employee.matricule;
}
function specialFormatter(employee, row, index) {
	if(row.employee.typeContrat == ''){
		return "";
	}
	return row.employee.typeContrat;
}
function typecontratFormatter(specialContract, row, index) {
	if(row.specialContract.typeContrat == ''){
		return "";
	}
	return row.specialContract.typeContrat;
}

function renumerationFormatter(specialContract, row, index) {
	if(row.specialContract.remunerationForfaitaire == ''){
		return "";
	}
	return row.specialContract.remunerationForfaitaire;
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


	function genererLivreDePaie(){
	periodeID='${activeMoisId}';
         	jQuery.ajax({
                 type: "POST",
                 url: baseUrl+"/paie/saveBulletinEmplivrepaie",
                 cache: false,
         		success: function (response) {
                 	if (response != null) {
                 		//alert(response.result);
                 		jQuery("#rhpModalValider").modal('hide');
                 		$tablebull.bootstrapTable('refresh', { url: baseUrl +'/paie/bulletinSpecialeperiodeactifjson?id='+ periodeID});
         			} else {
         				alert('Impossible de charger cet objet');
         			}
                 },
                 error: function () {

                 },
                 complete: function () {
                	$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinSpecialeperiodeactifjson?id='+ periodeID});
              //   location.reload();
                 }
             });
         }
function nomFormatter(employee, row, index) {
	if(row.employee.nom == ''){
		return "";
	}
	return row.employee.nomComplet;
}
function fonctionFormatter(fonction, row, index) {
	if(row.fonction.libelle == ''){
		return "";
	}
	return row.fonction.libelle;
}
//Chargement des fonctions
function loadFonction() {
    jQuery.ajax({
        type: "GET",
        url: baseUrl + "/personnels/listfonction",
        cache: false,
        success: function (response) {
            console.log(response);
            if (response != null) {
                tabledata = '';
                for (i = 0; i < response.length; i++) {
                    tabledata += '<option value="' + response[i].id + '" >' + response[i].libelle + '</option>';
                }
                //tabledata += "";
                jQuery('#fonction, #fonctionPop').html(tabledata);
            } else {
                alert('Failure! An error has occurred!');
            }
        },
        error: function () {

        },
    });
}

//Chargement des site
function loadSite() {
                     jQuery.ajax({
                         type: "GET",
                         url: baseUrl + "/carriere/paginersitess",
                         cache: false,
                         success: function (response) {
                             console.log(response);
                             if (response != null) {
                                 tabledata1 = '';
                                 for (i = 0; i < response.rows.length; i++) {
                                     tabledata1 += '<option value="' + response.rows[i].id + '" >' + response.rows[i].libelle + '</option>';
                                 }
                                 //tabledata += "";
                                 jQuery('#site').html(tabledata1);
                             } else {
                                 alert('Failure! An error has occurred!');
                             }
                         },
                         error: function () {

                         },
                     });

}

function edit(idCategorie){
	var $scope = angular.element(document.getElementById("formAjout")).scope();
    
	var rows = $table.bootstrapTable('getData');
	var employee = _.findWhere(rows, {id: idCategorie});

	$scope.$apply(function () {
        $scope.pupulateForm(employee);
    });
      //  jQuery(".sectionContrat input, .sectionContrat select").attr("disabled", "disabled");
       jQuery("#paiementNumber").removeAttr("disabled");
        jQuery("#modepaiement").removeAttr("disabled");
        updateComboAndRadioEmployee(employee)
        loadContratActifEmploye(employee);
}
function editPret(idFonction){
//	var $scope = angular.element(document.getElementById("formDelete")).scope();
    //jQuery("#rhpModalPret").modal('hide');
    	jQuery('#idpers1').val(idFonction);

}

// Chargement du contrat spécial de l'employé
function loadContratActifEmploye(employee) {
    jQuery.ajax({
        type: "GET",
        url: baseUrl + "/personnels/specifique/special-contracts/employee/" + employee.id,
        cache: false,
        success: function (response) {
            console.log("Contrat reçu:", response);
            if (response != null) {

                var $scope = angular.element(document.getElementById("formAjout")).scope();
                $scope.$apply(function () {
                    $scope.speccontrat = response.row; // 👈 Injection directe du DTO
                });

                // Mise à jour des champs non-Angular (select2, etc.)
                jQuery("#typeContrat").val(response.row.typeContrat).trigger("change");
                jQuery("#fonction").val(response.row.fonctionId).trigger("change");
                jQuery("#site").val(response.row.site).trigger("change");
                jQuery("#modepaiement").val(response.row.modepaiement).trigger("change");
                jQuery("#netpayer").val(response.row.remunerationForfaitaire).trigger("change");
                jQuery("#Ddeb").val(response.row.Ddeb).trigger("change");
                jQuery("#Dfin").val(response.row.Dfin).trigger("change");

            } else {
                alert('Aucun contrat trouvé pour cet employé.');
            }
        },
        error: function () {
            alert('Erreur lors du chargement du contrat.');
        },
    });
}



function updateComboAndRadioEmployee(employee) {
  //  loadServiceByTypeService(employee.service.typeService.id, employee.service.id);
    jQuery("#nationalite").val(employee.nationnalite.id).trigger("change");

    jQuery("#situationMatrimoniale").val(employee.situationMatrimoniale.toString().trim().toUpperCase()).trigger("change");
    jQuery("#nombreenfant").val(employee.nombrEnfant).trigger("change");
    jQuery("#situationEmploie").val(employee.situationEmploie).trigger("change");
    //Initialisation
     // Reset radios
       document.getElementById('sexeMasculin').checked = false;
       document.getElementById('sexeFeminin').checked = false;

       if (employee.sexe) {
           var sexe = employee.sexe.toString().trim().toLowerCase();

           if (sexe === "masculin") {
               document.getElementById('sexeMasculin').checked = true;
           }
           else if (sexe === "feminin") {
               document.getElementById('sexeFeminin').checked = true;
           }
       }


 }
function del(idCategorie){
	var $scope = angular.element(document.getElementById("formDelete")).scope();
    
	var rows = $table.bootstrapTable('getData');
	var employee = _.findWhere(rows, {id: idCategorie});
	employee.info =  employee.matricule+" "+employee.nomComplet;
	$scope.$apply(function () {
        $scope.pupulateForm(employee);
    });
}
</script>
