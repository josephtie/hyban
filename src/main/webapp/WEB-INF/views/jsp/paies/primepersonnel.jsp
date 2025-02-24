<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
	<div class="col-md-12">
		<div class="panel panel-warning">
			<div class="panel-heading">
				<div class="panel-title-box">
					<h3>Liste  des Primes attribu&eacute;s</h3>
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
			<table id="table" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-sort-name="id"
					data-sort-order="desc"
					data-url="${pageContext.request.contextPath}/personnels/listcontratpersonneljson"
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
<div class="modal main-popup fade" id="listPrimesDiversModal" role="dialog" aria-labelledby="listPrimesDiversModalLabel"   data-backdrop="static">
    <div class="modal-dialog" style="width:50%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="listMouvementCongeModalLabel">Liste des primes individuels <span id="employe" class="danger">Mars 2016</span></h4>
            </div>
            <div class="modal-body">
         
                <form id="formMouvementConge" >
                    <h3 style="margin-top: 30px;">Saisie de prime</h3>
                     <input type="text"class="hidden"  id="lpom" name="lpom" value=""/>
                    <br>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label>Prime</label> 
                                 <select class="form-control input-small" id="primePop" name="primePop" >
                                    <c:forEach items="${listePrimes}" var="prime">
                                        <option value="${prime.id}">${prime.libelle}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <label>Montant</label>
                                <input type="text" class="form-control " id="montantpop" name="montantpop" placeholder="Montant"   required="required" ><!-- //ng-model="mouvementConge.montant" -->
                            </div>
                           <div class="col-md-2">
                                <label>Valeur</label>
                                <input type="text" class="form-control" id="valeurpop" name="valeurpop" placeholder="valeur"  required="required" ><!-- ng-model="mouvementConge.montantVerse -->
                            </div> 
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div id="actionMouvementConge" class="col-md-12 text-right">
                                <span></span>&nbsp;
                                <input type="text"class="hidden" ng-hide="true"  name="idPersonnelp" ng-model="primePersonnel.personnel.id" value=""/>
                                
                                 <input type="text" class="hidden"  value="" id="idP" name="idP" >
                               <!--  <button type="button" id="btnCancelMouvementConge" class="btn btn-default">Annuler</button> -->
                                <button type="submit" class="btn btn-success">Valider</button>
                            </div>
                        </div>
                    </div>					
                </form>
                <p>&nbsp;</p>
                <div id="toolbarMouvementConge" >
                   <%--<div class="form-inline">--%>
                        <%--<button type="button" class="btn btn-primary" title="Nouveau cong�" id="btnAddMouvementConge"><span class="glyphicon glyphicon-plus"></span> Refresh</button>--%>
                    <%--</div>--%>
                </div>
                <table id="tableConge"  class="table table-info table-striped"
                       data-toggle="table"
                       data-toolbar="#toolbarMouvementConge"
                       data-single-select="true"
                       data-sort-order="desc"
                       data-pagination="false"
                       data-search="true">
                    <thead>
                        <tr>
                            <th data-field="prime" data-formatter="primeFormatter">Prime</th>
                            <th data-field=mtmontant>Montant</th>
                            <th data-field="valeur">Valeur</th>
                            <th data-field="id" data-formatter="optionFormatterPrime" data-width="80px" data-align="center">Options</th>
                           <!--  <th data-field="mtnVerse" data-align="right">Montant vers&eacute;</th> -->
                        </tr>
                    </thead>
                </table>
                <br>
            </div>
        </div>
    </div>
</div>


<%--<div class="modal deleteModal  fade bs-delete-modal-static" id="rhpModalPretDel" role="dialog" data-backdrop="static">--%>
    <%--<div class="modal-dialog ">--%>
        <%--<div class="modal-content">--%>
	        <%--<form id="formDelete"  action="#" method="post">--%>
	            <%--<div class="modal-header ">--%>
	                <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>--%>
	                <%--<span class="circle bg-danger">--%>
	                    <%--<i class="fa fa-question-circle"></i>--%>
	                   <%----%>
	                <%--</span>--%>
	            <%--</div>--%>
	            <%--<div class="modal-body">--%>
	             <%--Etes vous sur de vouloir supprimer cet pret ?--%>
	            	<%--<h4 id="labelPret"></h4>--%>
	            <%--</div>--%>
	            <%--<div class="modal-footer">--%>
                	<%--<input type="hidden"  id="idPretperso"  value="" name="idPretperso" >--%>
                	<%--<span></span>&nbsp;--%>
                    <%--<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>--%>
                    <%--<button type="submit" class="btn btn-success">Valider</button>--%>
                <%--</div>--%>
                <%--</form>--%>
        	<%--</div>--%>
        	<%----%>
        	<%----%>
        <%----%>
    <%--</div>--%>
<%--</div>--%>
<script type="text/javascript">
//AngularJS

//End AngularJs

var actionUrl = "/paie/enregisterlivrepaie";
var actionDeleteUrl = "/paie/delpretIndividuel";
var action = "ajout";
var $table;
jQuery(document).ready(function($) {


	$table = jQuery('#tablef');
	  $tableConge = $('#tableConge');
	jQuery( ".select2" ).select2();
	jQuery("#dEmprunt").datepicker({
		dateFormat: 'dd/mm/yy',
        showOtherMonths:true
    });
	// //Envoi des donnees
	// 	$("#formDelete").submit(function(e){
	// 	e.preventDefault();
     //    var formData = $(this).serialize();
     //
     //
     //    $.ajax({
     //        type: "POST",
     //        url: baseUrl + actionDeleteUrl,
     //        cache: false,
     //        data: formData,
     //        success: function (reponse) {
     //            if (reponse.result == true) {
     //
     //            	jQuery(".deleteModal").modal('hide');
     //            }
     //            else if(reponse.result == "erreur_champ_obligatoire"){
     //            	alert("Saisie invalide");
     //            }
     //        },
     //        error: function (err) {
     //        	jQuery(".deleteModal .modal-body div.alert").alert();
     //        	jQuery(".deleteModal .modal-body .alert h4").html("Erreur survenue");
     //        	jQuery(".deleteModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
     //        	jQuery(".deleteModal .modal-footer span").removeClass('loader');
     //        },
     //        beforeSend: function () {
     //        	jQuery("#formDelete").attr("disabled", true);
     //            jQuery(".deleteModal .modal-footer span").addClass('loader');
     //        },
     //        complete: function () {
     //        	jQuery("#formDelete").removeAttr("disabled");
     //            jQuery(".deleteModal .modal-footer span").removeClass('loader');
     //            jQuery('#tablef'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/pretPersonneljson' });
     //        }
     //    });
	// });
	
	// $("#formPretmodif").submit(function(e){
	// 	e.preventDefault();
     //    var formData = $(this).serialize();
     //    console.log("form", formData);
     //    jQuery.ajax({
     //        type: "GET",
     //        url: baseUrl + "/paie/updatepretPersonnel",
     //        cache: false,
     //        data: formData,
     //        success: function (reponse) {
     //            if (reponse.result == "success") {
     //            	$table.bootstrapTable('refresh');
     //            	jQuery("#formPretmodif")[0].reset(); //Initialisation du formulaire
     //            	jQuery("#rhpModalPretModif").modal('hide');
     //            	alert(reponse.result);
     //            }
     //            else if(reponse.result == "erreur_champ_obligatoire"){
     //            	alert("Saisie invalide");
     //            }
     //        },
     //        error: function () {
     //        	jQuery("#rhpModalPret div.alert").alert();
     //        	jQuery("#rhpModalPret .alert h4").html("Erreur survenue");
     //        	jQuery("#rhpModalPret .alert p").html("Verifier que vous �tes connect�s au serveur.");
     //        	//$("#formPret span.load").removeClass('loader');
     //        },
     //        beforeSend: function () {
     //        	jQuery("#formPret").attr("disabled", true);
     //            jQuery("#formPret span.load").addClass('loader');
     //        },
     //        complete: function () {
     //        	jQuery("#formPretmodif").removeAttr("disabled");
     //            //$("#formPret span.load").removeClass('loader');
     //            jQuery('#tablef'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/pretPersonneljson' });
     //        }
     //    });
	// });
	// $("#formPret").submit(function(e){
	// 	e.preventDefault();
     //    var formData = $(this).serialize();
     //    console.log("form", formData);
     //    jQuery.ajax({
     //        type: "POST",
     //        url: baseUrl + "/paie/savepretPersonnel",
     //        cache: false,
     //        data: formData,
     //        success: function (reponse) {
     //            if (reponse.result == "success") {
     //            	$table.bootstrapTable('refresh');
     //            	jQuery("#formPret")[0].reset(); //Initialisation du formulaire
     //             //   $("#rhpModal").modal('hide');
     //        		alert('Operation effexctu�e avec succes');
     //            }
     //            else if(reponse.result == "erreur_champ_obligatoire"){
     //            	alert("Saisie invalide");
     //            }
     //        },
     //        error: function () {
     //        	jQuery("#rhpModalPret div.alert").alert();
     //        	jQuery("#rhpModalPret .alert h4").html("Erreur survenue");
     //        	jQuery("#rhpModalPret .alert p").html("Verifier que vous �tes connect�s au serveur.");
     //        	//$("#formPret span.load").removeClass('loader');
     //        },
     //        beforeSend: function () {
     //        	jQuery("#formPret").attr("disabled", true);
     //            jQuery("#formPret span.load").addClass('loader');
     //        },
     //        complete: function () {
     //        	jQuery("#formPret").removeAttr("disabled");
     //            //$("#formPret span.load").removeClass('loader');
     //            jQuery('#tablef'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/pretPersonneljson' });
     //        }
     //    });
	// });
});

function optionFormatter(id, row, index) {
	var option = '<a onclick="listMouvementConge('+id+','+row.personnel.id+')" data-toggle="modal" data-target="#listPrimesDiversModal" href="#" title="Ajouter pr�t [LIBELLE : '+row.personnel.nom+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
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
jQuery("#formMouvementConge").submit(function (e) {
    e.preventDefault();
    var formData = jQuery(this).serialize();
    jQuery.ajax({
        type: "POST",
        url: baseUrl + "/paie/saveprimepersonnel",
        cache: false,
        data: formData,
        success: function (reponse) {
            if (reponse.result == true) {
            	jQuery("#formMouvementConge")[0].reset();
                jQuery('#lpom').val(reponse.contratPersonnel.id);

             //   loadMouvementCongeByPersonnel(idPersonnel)
            } else if (reponse.result == false) {
                alert("Saisie invalide");
            }
        },
        beforeSend: function () {
        	//jQuery("#formMouvementConge").attr("disabled", true);
        	//jQuery("#actionMouvementConge span").addClass('loader');
        },
        error: function () {
        	jQuery("#actionMouvementConge span").removeClass('loader');
        },
        complete: function () {
        	//jQuery("#formMouvementConge").removeAttr("disabled");
        	//jQuery("#actionMouvementConge span").removeClass('loader');
           $tableConge.bootstrapTable('refresh', { url: baseUrl +'/paie/searchlesprimeIndividuel1Mois?id='+ jQuery('#idPersonnel').val() });
        }
    });
    return false;
});
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

function listMouvementConge(idCtrat,idPersonnel) {
	jQuery('#lpom').val(idCtrat);

    loadMouvementCongeByPersonnel(idPersonnel);
    loadContratPersonnel(idPersonnel);
    jQuery('#idPersonnelp').val(idPersonnel);
    jQuery('#lpom').val(idCtrat);
    console.log(idCtrat);
 
}
    function loadMouvementCongeByPersonnel(idPersonnel) {
        jQuery.ajax({
            type: "GET",
            url: baseUrl + "/paie/searchlesprimeIndividuel1Mois",
            cache: false,
            data: {id: idPersonnel},
            success: function (reponse) {
            //	if(reponse.rows>0){

				$tableConge.bootstrapTable('load', reponse.rows);
             //   }
            },
            beforeSend: function () {
               $tableConge.bootstrapTable('load', []);
                //jQuery("#btnAddMouvementConge,.form-mouvement-conge").hide();
            },
            error: function () {
                
            },
            complete: function () {
                
            }
        });
    }
    function loadContratPersonnel(idm) {
    	
   var ki=idm;
   console.log(ki);
        jQuery.ajax({
            type: "POST",
            url: baseUrl + "/personnels/cherchcontratpersonnelActif",
            cache: false,
            data: {id: idm},
            success: function (reponse) {
            	
            console.log(reponse.row);
            	  jQuery('#idPersonnelp').val(reponse.row.personnel.id);
            	  jQuery('#lpom').val(reponse.row.id);
               // jQuery('#idP').val(reponse.row.id);
            	  jQuery('#employe').html('Matricule : '+reponse.row.personnel.matricule+' '+reponse.row.personnel.nom+' '+reponse.row.personnel.prenom);
            		jQuery('#matricules').html(reponse.row.personnel.matricule);
            		jQuery('#nomcomplet').html(reponse.row.personnel.nom+' '+reponse.row.personnel.prenom);
            		jQuery('#dnaiss').html(reponse.dNaissance);
            		jQuery('#lieunaiss').html(reponse.lieuNaissance);
            		jQuery('#sexe').html(reponse.sexe);
            	  
            //}
            //	if(reponse.rows>0){

			//	$t  jQuery("#idPersonnelp").val(idCtrat);ableConge.bootstrapTable('load', reponse.rows);
             //   }
            },
            beforeSend: function () {
             //  $tableConge.bootstrapTable('load', []);
                //jQuery("#btnAddMouvementConge,.form-mouvement-conge").hide();
            },
            error: function () {
                
            },
            complete: function () {
                
            }
        });
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

function optionFormatterPrime(id, row, index) {
	var option = '<a onclick="cherchprime(\''+row.id+'\')"  href="#" title="Modifier prime [LIBELLE : '+row.prime.libelle+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	 option += '&nbsp;<a onclick="deleteprime('+id+')" href="#" title="Suprimer prime [LIBELLE : '+row.prime.id+' ]"> <span class="glyphicon glyphicon-trash"></span></a>'; 
	
    return option;
}

function primeFormatter(prime, row, index) {
	if(row.prime.id == ''){
		return "";
	}
	return row.prime.libelle;
}

function edit(idFonction){
//	var $scope = angular.element(document.getElementById("formDelete")).scope();
	
	var idp=idFonction;
	//alert(idp);
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/paie/searchprimeIndividuel1",
        cache: false,
        data: {id:idp},
        success: function (response) {
        	if (response != null) {
        		console.log(response);
        		jQuery('#matricules').html(response.matricule);
        		jQuery('#nomcomplet').html(response.nom+' '+response.prenom);
        		jQuery('#dnaiss').html(response.dNaissance);
        		jQuery('#lieunaiss').html(response.lieuNaissance);
        		jQuery('#sexe').html(response.sexe);
        		jQuery('#typeserv').html(response.service.typeService.libelle);
        		jQuery('#serv').html(response.service.libelle);
        		jQuery('#idpers').val(response.id);
        		
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



function cherchprime(idFonction){
	jQuery('#lpom').val(idFonction);
//	jQuery('#infoPersonnelmo1').html(idFonction);
//	jQuery('#idPersonnel').val(idFonction);
//	jQuery('#idpersc').val(idFonction);
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/paie/searchprimeIndividuel1",
        data: {id: idFonction},
        cache: false,
		success: function (response) {
        	if (response != null) {
        	console.log(response);
        	jQuery("#formMouvementConge")[0].reset(); 
        	loadDataToForm(response.id,response.montant,response.valeur,response.prime.id,response.contratPersonnel.id);
			} else {
				alert('Impossible de charger cet objet');
			}
        },
        error: function () {
            
        },
        complete: function () {
       
        }
    });
 
}

function deleteprime(idFonction){
	//jQuery('#lpom').val(idFonction);
//	jQuery('#infoPersonnelmo1').html(idFonction);
//	jQuery('#idPersonnel').val(idFonction);
//	jQuery('#idpersc').val(idFonction);
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/paie/delpretIndividuel1",
        data: {id: idFonction},
        cache: false,
		success: function (response) {
        	if (response != null) {
        		jQuery("#formMouvementConge")[0].reset(); 
        		$tableConge.bootstrapTable('load', response.rows);
        	//loadDataToForm(response.id,response.montant,response.valeur,response.prime.id,response.contratPersonnel.id);
			} else {
				alert('Impossible de charger cet objet');
			}
        },
        error: function () {
            
        },
        complete: function () {
       
        }
    });
 
}


/* function editPret(idFonction){
//	var $scope = angular.element(document.getElementById("formDelete")).scope();

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
                jQuery("#formMouvementConge")[0].reset();
                loadDataToForm(response.id,response.montant,response.valeur,response.prime);
                /* 	jQuery('#montant1').val(response.montant);
                    jQuery('#periodepaie1').val(response.periode.id);
                    jQuery("#periodepaie1").val(response.periode.id).trigger('change');
                    jQuery('#periodepaie1').trigger('liszt:updated');
                    jQuery('#dEmprunt1').val(response.dEmprunt);
                    jQuery('#echelonage1').val(response.echelonage);
                    jQuery('#idpers1').val(response.personnel.id);
                    jQuery('#idpret').val(response.id); */

                //tabledata += "";
                /* jQuery('#typeService, #typeServicePop').html(tabledata);
                jQuery('#typeService, #typeServicePop').val("1").trigger("change");
            } else {
                alert('Failure! An error has occurred!');
            }
        },
        error: function () {

        },
    });
    jQuery('#tablef'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/pretPersonneljson' });
} */
function loadDataToForm(id, montant, valeur,idPrime,idCtrat){
	jQuery("#idP").val(id);
	
	jQuery("#lpom").val(idCtrat);
	if(idPrime == null){
		jQuery("#primePop").trigger('liszt:updated');
	  	jQuery("#primePop option:selected").val()
	} else{
		jQuery("#primePop").val(idPrime);
		jQuery("#primePop").val(idPrime).trigger('change');
		jQuery("#primePop").trigger('liszt:updated');
	}
	if(montant==null){
		jQuery("#montantpop").val(0);
	}else{
		jQuery("#montantpop").val(montant);
	}
	
	jQuery("#valeurpop").val(valeur);
	//alert(jQuery('#montant').val());
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