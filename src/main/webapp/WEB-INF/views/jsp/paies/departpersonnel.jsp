<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
	<div class="col-md-12">
		<div class="panel panel-warning">
			<div class="panel-heading">
				<div class="panel-title-box">
					<h3>Liste des Departs/Démission</h3>
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
										<li><a href="#" data-toggle="modal" data-target="#rhpModal">Nouveau</a></li>
									</ul>
								</div>

							</div>
							<br><br><br>
		<div id="tableWidget" class="widgetcontent">
			<table id="table" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-sort-name="id"
					data-sort-order="desc"
					data-url="${pageContext.request.contextPath}/personnels/listcontratpersonnelDepartjson"
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[10, 20, 50, 100, 200]" 
					data-search="true">
					<thead>
						<tr>
						
							<th data-field="personnel" data-formatter="matriFormatter" data-align="left" data-sortable="true">Matricule</th>
							<th data-field="personnel" data-formatter="nomFormatter" data-align="left" data-sortable="true">Nom</th>
							<th data-field="personnel" data-formatter="nomfstatutFormatterL" data-align="left" data-sortable="true">Statut</th>
							<th data-field="personnel" data-formatter="sexeFormatter" data-align="left">Sexe</th>
							<th data-field="personnel" data-formatter="datnaisFormatter" data-align="center">N&eacute;(e) le</th>
							<th data-field="personnel"  data-formatter="lieunaisFormatter" data-align="left">A</th>
							<th data-field="personnel" data-formatter="telephoneFormatter" data-align="center">T&eacute;l&eacute;phone</th>
							<th data-field="personnel" data-formatter="situaFormatter" data-align="center">Sit. Matri</th>
							<th data-field="personnel" data-formatter="nbreenftFormatter" data-align="right">Nbre d'enfants</th>
							<th data-field="categorie" data-formatter="salcatFormatter" data-align="right">Salaire cat&eacute;goriel</th>
							<th data-field="netAPayer" data-align="right">Net &agrave; payer</th>
							<th data-field="id" data-formatter="optionFormatter" data-align="center">Options</th>
							
							<!-- <th data-field="state" data-checkbox="true"></th> -->
						
						</tr>
					</thead>
				</table>
		</div>
	</div>
</div>
	</div>
</div>

<!-- Generation des bulletins -->
<%-- <div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formGenerer" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">G&eacute;n&eacute;ration du bulletin de paie</h4>
				</div>
                <div class="modal-body">
                	<h3>Vous �tes sur le point de g&eacute;n&eacute;rer le bulletin de paie de la p&eacute;riode de <span id="periodePaie" class="danger">Mars 2016</span> du personnel.</h3>
                	<br/>
                	<h4>En cliquant sur le bouton "Valider", le proccessus sera lanc&eacute;.</h4>
                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div> --%>

<!-- Solde tout comptes -->

 <div class="modal main-popup fade" id="listSoldeModal" ng-controller="listSoldeCtrl" role="dialog" aria-labelledby="listSoldeModalLabel" data-backdrop="static"  >
            <div class="modal-dialog" style="width:60%;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="listSoldeModalLabel">Soldes de tous les comptes</h4>
                    </div>
                    <div class="modal-body">
                        <h3>Personnel</h3>
                        <table>
                            <tbody>
                            <tr>
                                <th style="width:150px;">Matricule</th>
                                <td style="width:300px;">{{personnel.matricule}}</td>
                                <th style="width:150px;">N&deg; CNPS</th>
                                <td>{{personnel.numeroCnps}}</td>
                            </tr>
                            <tr>
                                <th>Nom</th>
                                <td>{{personnel.nomComplet}}</td>
                                <th>Sexe</th>
                                <td>{{personnel.sexe}}</td>
                            </tr>
                            <tr>
                                <th>N&eacute;(e) le</th>
                                <td>{{personnel.dNaissance}}</td>
                                <th>A</th>
                                <td>{{personnel.lieuNaissance}}</td>
                            </tr>
                            <tr>
                                <th>Situation matrimoniale</th>
                                <td>{{personnel.situationMatri}}</td>
                                <th>T&eacute;l&eacute;phone</th>
                                <td>{{personnel.telephone}}</td>
                            </tr>

                            </tbody>
                        </table><div style="max-height: 500px; overflow-y: auto;">
                        <form id="formSolde" class="form-solde" >
                            <h3 style="margin-top: 30px;">Calcul de solde de tout compte</h3>
                            <input type="text" class="hidden" name="idCtrat" id="idCtrat" ng-model="idCtrat"/>

                            <br>

                                            <div class="form-group">
                                                <div class="row">

                                                  <div class="col-md-3">
                                                     <label for="sanctionsalaire">Nnre de Jours dus de cong&eacute;s  <span class="required">*</span></label>
                                                       <input type="text" id="joursabsence" class="form-control input-default" ng-disabled="justifie" ng-model="departPersonnel.joursabsence" required="required" name="joursabsence" placeholder="30" maxlength="4">
                                                  </div>
                                                  <div class="col-md-3">
                                                        <label for="statut">Pr&eacute;avis non effectu&eacute; (jours)</label>
                                                     <input type="text" id= "heursabsence" class="form-control input-default" ng-disabled="justifie" ng-model="departPersonnel.heursabsence" name="heursabsence"  required="required" placeholder="173.33" maxlength="6">
                                                 </div>
                                                 <div class="col-md-3">
                                                        <label for="sanctionsalaire">Primes dues<span class="required">*</span> </label>
                                                    <input type="text" id= "heursabsence" class="form-control input-default" ng-disabled="justifie" ng-model="departPersonnel.heursabsence" name="heursabsence"  required="required" placeholder="173.33" maxlength="6">
                                                </div>


                                               <div class="col-md-3">

                                                          <label for="sexe">13e mois applicable <span class="required">*</span></label>
                                                                                                        <div>
                                                                                                            <label  >
                                                                                                                <input name="sexe" id="sexeMasculin" type="radio" class="radio-inline" value="OUI">Masculin
                                                                                                            </label>
                                                                                                            <label  >
                                                                                                               <input name="sexe" type="radio" id="sexeFeminin" class="radio-inline" value="NON">  Feminin
                                                                                                            </label>
                                                        </div>
                                               </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                       <div class="row">

                                                     <div class="col-md-3">
                                                    <label for="sanctionsalaire">Acomptes vers&eacute;s <span class="required">*</span></label>
                                                     <input type="text" id="joursabsence" class="form-control input-default" ng-disabled="justifie" ng-model="departPersonnel.joursabsence" required="required" name="joursabsence" placeholder="30" maxlength="4">
                                                    </div>
                                                   <div class="col-md-3">
                                                     <label for="statut">Avantages en nature</label>
                                                      <input type="text" id= "heursabsence" class="form-control input-default" ng-disabled="justifie" ng-model="departPersonnel.heursabsence" name="heursabsence"  required="required" placeholder="173.33" maxlength="6">
                                                    </div>
                                                   <div class="col-md-3">
                                                     <label for="sanctionsalaire">Heures suppl&eacute;mentaires <span class="required">*</span> </label>
                                                      <input type="text" id= "heursabsence" class="form-control input-default" ng-disabled="justifie" ng-model="departPersonnel.heursabsence" name="heursabsence"  required="required" placeholder="173.33" maxlength="6">
                                                  </div>
                                                 <div class="col-md-3">
                                                     <label for="sanctionsalaire"> 13e mois applicable <span class="required">*</span></label>
                                                   </div>
                                                 </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <label>Observations</label>
                                                        <textarea class="form-control" name="observation" ng-model="departPersonnel.observation" placeholder="Observation"></textarea>
                                                    </div>
                                                </div>
                                            </div>

                            <div class="form-group">
                                <div class="row">
                                    <div id="actionSolde" class="col-md-12 text-right">
                                        <span></span>&nbsp;
                                        <input type="text" name="idPersonnel" class="hidden" ng-model="departPersonnel.contratPersonnel.id"/>
                                        <input type="text"class="hidden" ng-hide="true" name="id" ng-model="departPersonnel.id"/>
                                        <button type="button" id="btnCancelSolde" class="btn btn-default">Annuler</button>
                                        <button type="submit" data-action="add" data-index="-1" class="btn btn-success">Valider</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                        </div>
                        <p>&nbsp;</p>
                        <div id="toolbarSolde">
                            <div class="form-inline">
                                <button type="button" class="btn btn-primary" title="Nouvel solde" id="btnAddSolde"><span class="glyphicon glyphicon-plus"></span> Nouvelle prime</button>
                            </div>
                        </div>
                        <table id="tableSolde" class="table table-info table-striped"
                               data-toggle="table"
                               data-toolbar="#toolbarSolde"
                               data-single-select="true"
                               data-sort-order="desc"
                               data-pagination="true"
                               data-page-list="[5, 10, 20, 50, 100, 200]"
                               data-search="true">
                            <!--   data-formatter="commentaireSoldeFormatter" -->
                            <thead>
                            <tr>
                                <th data-field="prime" data-formatter="primeFormatter">Prime</th>
                                <th data-field=mtmontant>Montant</th>
                                <th data-field="valeur">Valeur</th>
                                <th data-field="mtnVerse" data-align="right">Montant vers&eacute;</th>
                                <th data-field="id" data-formatter="optionFormatterPrime" data-align="center"></th>
                            </tr>
                            </thead>
                        </table>
                        <br>
                    </div>
                </div>
            </div>
        </div>



  

<div class="modal deleteModal  fade bs-delete-modal-static" style="z-index: 2000" id="rhpModalPretDel" role="dialog" data-backdrop="static">
    <div class="modal-dialog ">
        <div class="modal-content">
	        <form id="formDelete"  action="#" method="post">
	            <div class="modal-header ">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	                <span class="circle bg-danger">
	                    <i class="fa fa-question-circle"></i>
	                   
	                </span>
	            </div>
	            <div class="modal-body">
	             Etes vous sur de vouloir supprimer cet pret ?
	            	<h4 id="labelPret"></h4>
	            </div>
	            <div class="modal-footer">
                	<input type="hidden"  id="idPretperso"  value="" name="idPretperso" >
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
                </form>
        	</div>
        	
        	
        
    </div>
</div>
<script type="text/javascript">
app.controller('listSoldeCtrl', function ($scope) {
    $scope.SoldeId = jQuery("#idSolde option:first").val();

    $scope.populateForm = function (personnel, departPersonnel) {
        $scope.personnel = personnel;
        if(departPersonnel){
         $scope.departPersonnel = departPersonnel;
        //  $scope.absenceId = departPersonnel.contratPersonnel.id;
         $scope.personnel = departPersonnel.personnel;
                          //  $scope.statutAbs = absencePersonnel.statut === true ? 'true' : 'false';
         }
          };

    $scope.initForm = function () {
        $scope.departPersonnel = {};
    };

    $scope.pupulateContrat = function (contrat) {
      //  $scope.contrat = contrat;
    };

    $scope.populatePrime = function (soldeTouscompte) {
      //  $scope.soldeTouscompte = soldeTouscompte;
    };
})

.controller('formDeleteCtrl', ['$scope', function ($scope) {
    $scope.populateForm = function (categorie) {
        $scope.categorie = categorie;
    };
}]);


var actionUrl = "/paie/enregisterlivrepaie";
var actionDeleteUrl = "/paie/delpretIndividuel";
var action = "ajout";
var $table;
jQuery(document).ready(function($) {
$(".form-solde").hide(500);
	$table = jQuery('#table');
	$tableSolde = jQuery('#tableSolde');
	jQuery( ".select2" ).select2();
	jQuery("#dEmprunt").datepicker({
		dateFormat: 'dd/mm/yy',
        showOtherMonths:true
    });

          $("#btnAddSolde").click(function (e) {
                    $(".form-solde").show(500);
                    var $scope = angular.element(document.getElementById("formSolde")).scope();
                    $scope.$apply(function () {
                    $scope.initForm();
                    });

                     $("#actionSolde button:submit").data("action", "add");
           });

          $("#btnCancelSolde, #listSoldeModal button.close").click(function (e) {
            $(".form-solde").hide(500);
              jQuery("#formSolde")[0].reset();
              var $scope = angular.element(document.getElementById("formSolde")).scope();
              $scope.$apply(function () {
                 $scope.initForm();
              });
         });


	//Envoi des donnees
		$("#formDelete").submit(function(e){
		e.preventDefault();
        var formData = $(this).serialize();       
      
        
        $.ajax({
            type: "POST",
            url: baseUrl + actionDeleteUrl,
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == true) {
                  
                	jQuery(".deleteModal").modal('hide');
                }
                else if(reponse.result == "erreur_champ_obligatoire"){
                	alert("Saisie invalide");
                }
            },
            error: function (err) {
            	jQuery(".deleteModal .modal-body div.alert").alert();
            	jQuery(".deleteModal .modal-body .alert h4").html("Erreur survenue");
            	jQuery(".deleteModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
            	jQuery(".deleteModal .modal-footer span").removeClass('loader');
            },
            beforeSend: function () {
            	jQuery("#formDelete").attr("disabled", true);
                jQuery(".deleteModal .modal-footer span").addClass('loader');
            },
            complete: function () {
            	jQuery("#formDelete").removeAttr("disabled");
                jQuery(".deleteModal .modal-footer span").removeClass('loader');
                jQuery('#tablef'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/pretPersonneljson' });
            }
        });
	});
	
	$("#formPretmodif").submit(function(e){
		e.preventDefault();
        var formData = $(this).serialize();
        console.log("form", formData);
        jQuery.ajax({
            type: "GET",
            url: baseUrl + "/paie/updatepretPersonnel",
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
	$("#formPret").submit(function(e){
		e.preventDefault();
        var formData = $(this).serialize();
        console.log("form", formData);
        jQuery.ajax({
            type: "POST",
            url: baseUrl + "/paie/savepretPersonnel",
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == "success") {
                	$table.bootstrapTable('refresh');
                	jQuery("#formPret")[0].reset(); //Initialisation du formulaire
                 //   $("#rhpModal").modal('hide');
            		alert('Operation effexctuee avec succes');
                }
                else if(reponse.result == "erreur_champ_obligatoire"){
                	alert("Saisie invalide");
                }
            },
            error: function () {
            	jQuery("#rhpModalPret div.alert").alert();
            	jQuery("#rhpModalPret .alert h4").html("Erreur survenue");
            	jQuery("#rhpModalPret .alert p").html("Verifier que vous �tes connect�s au serveur.");
            	//$("#formPret span.load").removeClass('loader');
            },
            beforeSend: function () {
            	jQuery("#formPret").attr("disabled", true);
                jQuery("#formPret span.load").addClass('loader');
            },
            complete: function () {
            	jQuery("#formPret").removeAttr("disabled");
                //$("#formPret span.load").removeClass('loader');
                jQuery('#tablef'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/pretPersonneljson' });
            }
        });
	});
});

function optionFormatter(id, row, index) {
	var option = '<a onclick="edit('+row.id+')" data-toggle="modal" data-target="#listSoldeModal" href="#" title="Ajouter pr�t [LIBELLE : '+row.personnel.nom+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	option += '&nbsp;<a data-toggle="modal" href="#" title="Suprimer bulletin [LIBELLE : '+row.libelle+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';
	
    return option;
}
function nomfstatutFormatterL(personnel, row, index) {
    if(row.personnel== ''){
        return "";
    }
    if(row.personnel.carec==false){

        if(row.personnel.stage==true)
            statfonct = "Stagiaire";
        else
            statfonct = "Consultant";
    }else{
        statfonct = "Contractuel";
    }
    return statfonct;
    //return row.contratPersonnel.personnel.statfonct;
}
function pretiFormatter(id, row, index) {
	 var option = '<a onclick="editPret('+row.id+')" data-toggle="modal" data-target="#rhpModalPretModif" href="#" title="Ajouter pr�t [LIBELLE : '+row.personnel.nom+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	option += '&nbsp;<a onclick="delPret('+row.id+')" data-toggle="modal" data-target="#rhpModalPretDel" href="#" title="Suprimer bulletin [LIBELLE : '+row.id+' ]"> <span class="glyphicon glyphicon-trash"></span></a>'; 
	
    return option;
}
function matriFormatter(personnel, row, index) {
	if(row.personnel.matricule == ''){
		return "";
	}
	return row.personnel.matricule;
}
function matrisFormatter(personnel, row, index) {
	if(row.personnel.matricule == ''){
		return "";
	}
	return row.personnel.matricule;
}
function periodeFormatter(periode, row, index) {
	if(row.periode.mois.mois == ''){
		return "";
	}
	return row.periode.mois.mois+' '+row.periode.annee.annee ;
}
function pretFormatter(pret, row, index) {
	if(row.pret.libelle == ''){
		return "";
	}
	return row.pret.libelle;
}
function nomFormatter(personnel, row, index) {
	if(row.personnel.nom == ''){
		return "";
	}
	return row.personnel.nom+" "+row.personnel.prenom;
}
function nomsFormatter(personnel, row, index) {
	if(row.personnel.nom == ''){
		return "";
	}
	return row.personnel.nom+" "+row.personnel.prenom;
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
	if(row.categorie.salaireDeBase == ''){
		return "";
	}
	return row.categorie.salaireDeBase;
}
function edit(idFonction) {
    var $scope = angular.element(document.getElementById("listSoldeModal")).scope();

                 var rows = $table.bootstrapTable('getData');
                 var departPersonnel = _.findWhere(rows, {id: idFonction});

                 $scope.$apply(function () {
                     $scope.populateForm(departPersonnel);
                     $scope.departPersonnel=departPersonnel;

                     $scope.personnel=departPersonnel.personnel;
                 });

   //jQuery(".form-solde").hide(500);
}

function editPret(idFonction){
//	var $scope = angular.element(document.getElementById("formDelete")).scope();
    //jQuery("#rhpModalPret").modal('hide');
	var idp=idFonction;
	//alert(idp);
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/paie/searchpretIndividuel",
        cache: false,
        data: {id:idp},
        success: function (response) {
        	if (response != null) {
        		console.log(response);
        		//jQuery('#pret1').html(response.matricule);
        		jQuery("#formPretmodif")[0].reset(); 
        		jQuery('#pret1').val(response.pret.id);
    			jQuery("#pret1").val(response.pret.id).trigger('change');
    			jQuery('#pret1').trigger('liszt:updated');
        		jQuery('#montant1').val(response.montant);
        		jQuery('#periodepaie1').val(response.periode.id);
    			jQuery("#periodepaie1").val(response.periode.id).trigger('change');
    			jQuery('#periodepaie1').trigger('liszt:updated');        	
        		jQuery('#dEmprunt1').val(response.dEmprunt);
        		jQuery('#echelonage1').val(response.echelonage);
        		jQuery('#idpers1').val(response.personnel.id);        		
        		jQuery('#idpret').val(response.id);
        		
				//tabledata += "";
				/* jQuery('#typeService, #typeServicePop').html(tabledata);
				jQuery('#typeService, #typeServicePop').val("1").trigger("change"); */
			} else {
				alert('Failure! An error has occurred!');
			}
        },
        error: function () {
            
        },
    });
	jQuery('#tablef'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/pretPersonneljson' });
}

function delPret(idFonction){
//	var $scope = angular.element(document.getElementById("formDelete")).scope();
	jQuery('#idPretperso').val(idFonction);
	
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/paie/searchpretIndividuel",
        cache: false,
        data: {id:idFonction},
        success: function (response) {
        	if (response != null) {
        		console.log(response);
        		//jQuery('#pret1').html(response.matricule);
        		jQuery("#formPretmodif")[0].reset(); 
        		jQuery('#pret1').val(response.pret.id);
    			jQuery("#pret1").val(response.pret.id).trigger('change');
    			jQuery('#pret1').trigger('liszt:updated');
        		jQuery('#montant1').val(response.montant);
        		jQuery('#periodepaie1').val(response.periode.id);
    			jQuery("#periodepaie1").val(response.periode.id).trigger('change');
    			jQuery('#periodepaie1').trigger('liszt:updated');        	
        		jQuery('#dEmprunt1').val(response.dEmprunt);
        		jQuery('#echelonage1').val(response.echelonage);
        		jQuery('#idpers1').val(response.personnel.id);        		
        		jQuery('#idpret').val(response.id);
        		jQuery('#idPretperso').val(response.id);
        		jQuery('#labelPret').html(response.personnel.nom+' '+response.pret.libelle+' '+response.montant);
				//tabledata += "";
				/* jQuery('#typeService, #typeServicePop').html(tabledata);
				jQuery('#typeService, #typeServicePop').val("1").trigger("change"); */
			} else {
				alert('Failure! An error has occurred!');
			}
        },
        error: function () {
            
        },
    });
	
}
</script>