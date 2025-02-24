<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="widget">
	<div class="widgetbox">
		<div class="headtitle">
			<h4 id="widgetTitle" class="widgettitle">Livre de paie</h4>
		</div>
		<div id="tableWidget" class="widgetcontent">
			<div id="toolbar">
			    <div class="form-inline">
<!-- 			        <button id="btnGenerer" type="submit" data-toggle="modal" data-target="#rhpModal" title="Cocher les employ�s mis en sommeil" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>G�n�rer livre de paie</button> -->
<!-- 			    <button id="btnGenerer" type="button" data-toggle="modal" data-target="#rhpModalImprimer" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>Imprimer bulletin</button> -->
			    </div>
			</div>
			<form action="#" id="formList">
				<table id="table" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-sort-name="id"
					data-sort-order="desc"
					data-url="${pageContext.request.contextPath}/contratpersonnel/listcontratpersonnelActifjson"
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[10, 20, 50, 100, 200]" 
					data-search="true">
					<thead>
						<tr>
						
							<th data-field="personnel" data-formatter="matriFormatter" data-align="left" data-sortable="true">Matricule</th>
							<th data-field="personnel" data-formatter="nomFormatter" data-align="left" data-sortable="true">Nom</th>
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
			</form>
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
                	<input type="hidden"  id="idPeriode" name="id">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div> --%>
<!-- Livre de paie -->
<%-- <div class="modal fade" id="rhpModalValider"  role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static" >
    <div class="modal-dialog" role="document" style="width:80%;" >
        <div class="modal-content">
            <form id="formGenererValidBull" class="form-horizontal" >
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Valider les bulletins de paie</h4>
				</div>
                <div class="modal-body">
                <div id="toolbar">
			    <div class="form-inline">
			       <!--  <button id="btnGenerer5" type="submit" data-toggle="modal" data-target="#rhpModal5 " title="Cocher les employ�s mis en sommeil" class="btn btn-primary btn-xs"><i class="fa fa-plus"></i>G�n�rer tous les bulletins</button> -->
			    </div>
			   </div>
                
              <form action="#" id="formList">
				<table id="tableliv" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-sort-name="name"
					data-sort-order="desc"
					data-show-columns="true" 
					data-url="${pageContext.request.contextPath}/paie/savebullPersonnel" 
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[10, 20, 50, 100, 200]" 
					data-search="true">
					<thead>
						<tr>
							<th data-field="matricule"  data-align="left" data-sortable="true">Matricule</th>
							<th data-field="nomPrenom"  data-align="left" data-sortable="true">Nom</th>
							<th data-field="nombrePart"  data-align="left">Nbre part</th>
							<th data-field="anciennete"  data-align="center">Anciennet�</th>
							<th data-field="salaireBase"   data-align="left">Salaire Base</th>
							<th data-field="sursalaire"  data-align="center">Sursalaire</th>
							<th data-field="primeAnciennete"  data-align="center">Prime. Anciennet�</th>
							<th data-field="indemniteLogement"  data-align="right">Indem logement</th>
							<th data-field="brutImposable" data-align="right">Brut imposable</th>
							<th data-field="its" data-align="right">ITS</th>							
							<th data-field="cn"  data-align="left" data-sortable="true">CN</th>
							<th data-field="igr"  data-align="left" data-sortable="true">IGR</th>
							<th data-field="totalRetenueFiscale"  data-align="left">Retenue fiscale</th>
							<th data-field="cnps"  data-align="center">CNPS</th>
							<th data-field="avceAcpte"   data-align="left">Avance</th>
							<th data-field="pretAlios"  data-align="center">Pr�t</th>
							<th data-field="carec"  data-align="center">Carec</th>
							<th data-field="totalRetenue"  data-align="right">Total retenu</th>
							<th data-field="indemniteRepresentation" data-align="right">Indem de repres.</th>
							<th data-field="indemniteTransport" data-align="right">Indem de transport.</th>
							
							<th data-field="netPayer"  data-align="center">Net � payer</th>
							<th data-field="totalBrut"   data-align="left">Total brut</th>
							<th data-field="is"  data-align="center">IS</th>
							<th data-field="ta"  data-align="center">TA</th>
							<th data-field="fpc"  data-align="right">FPC</th>
							<th data-field="prestationFamiliale" data-align="right">Prest familiale</th>
							<th data-field="accidentTravail" data-align="right">Acc de travail</th>
							<th data-field="retraite" data-align="right">Retraite</th>
							<th data-field="totalPatronal" data-align="right"> Total patronal</th>
							<th data-field="totalMasseSalariale" data-align="right">Total masse salariale</th>
							<!-- 
							
							<th data-field="id" data-formatter="optionFormatter1" data-align="center">Options</th> -->
							
						</tr>
					</thead>
				</table>
			
                	
                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                	<!-- <input type="hidden"  id="idPeriode" name="id"> -->
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" onclick="genererLivreDePaie()" class="btn btn-success">Valider</button>
                    
                </div>
            </form>
        </div>
    </div>
</div> --%>



<!-- Modification info du personnel -->
<div class="modal fade" id="rhpModalModif" tabindex="-1" role="dialog" aria-labelledby="rhpModalModif" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formModificationPersonnel" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">Pret du personnel (<span id="infoPersonnel">Information du personnel</span>)</h4>
				</div>
                <div class="modal-body">
                 <div class="form-group">
				                        <label for="service" class="col-md-4 control-label">Type</label>
				                        <div class="col-md-8">
				                            <select id="pretpers" name="pretpers" class="form-control select2">
				                                <optgroup label="concmoisan">
											</select>
				                        </div>
				                    </div>
		                            <div class="form-group">
				                        <label for="montant" class="col-md-4 control-label">Montant</label>
				                        <div class="col-md-8">
				                            <input type="text" id="montant" name="montant" class="form-control" placeholder="Montant" />
				                        </div>
				                    </div>
		                            <div class="form-group">
				                        <label for="" class="col-md-4 control-label">P�riode d�but pr�lev.</label>
				                        <div class="col-md-8">
				                            <select id="periodedeb" name="periodedeb" class="form-control select2">
											<optgroup label="libelle">
											</select>
				                        </div>
				                    </div>
		                            <div class="form-group">
				                        <label for="" class="col-md-4 control-label">Date contraction</label>
				                        <div class="col-md-8">
				                            <input type="text" id="libelle" name="libelle" class="form-control" placeholder="Date contraction" />
				                        </div>
				                    </div>
		                            <div class="form-group">
				                        <label for="" class="col-md-4 control-label">Mensualit�</label>
				                        <div class="col-md-8">
				                            <input type="text" id="libelle" name="libelle" class="form-control" placeholder="1" />
				                        </div>
				                    </div>
                </div>
                <div class="modal-footer">
                    <input type="hidden" value="" id="idPersonnel" name="idPersonnel">
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>


<script type="text/javascript">
var contextPath = "<%=request.getContextPath() %>";
app.controller('formAjoutCtrl', function ($scope) {
    $scope.pupulateForm = function (fonction) {
        $scope.fonction = fonction;
    };
}).controller('formDeleteCtrl', function ($scope) {
    $scope.pupulateForm = function (fonction) {
    	$scope.fonction = fonction;
    };

});

var actionUrl = "/paie/savebullPersonnel";
var action = "ajout";
var $table;
jQuery(document).ready(function($) {
	
	
	jQuery( ".select2" ).select2();
	loadPeriods();
	loadPrets();
	var periode='';var periodeID='';
	periode='${activeMois}';
	periodeID='${activeMoisId}';
	jQuery('#idPeriode1').val(periodeID);
	$table = $('#table');
	//alert(periode);
	//alert(periodeID);
	jQuery('#periodePaie').html(periode);
	jQuery('#idPeriode').val(periodeID);
});
	//alert(jQuery('#idPeriode').val());
	//Envoi des donnees
	/* jQuery("#formModificationBulletin").submit(function(e){
		e.preventDefault();
        var formData = $(this).serialize();
        console.log("form", formData);
        jQuery.ajax({
            type: "POST",
            url: baseUrl + "/personnels/modifierbulletinpaie",
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == "succes") {
                	//MAJ de la ligne modifi�e
            		$table.bootstrapTable('updateRow', {
                        index: $table.find('tr.selected').data('index'),
                        row: reponse.data
                    });
            		jQuery("#formAjout")[0].reset(); //Initialisation du formulaire
            		jQuery("#rhpModal").modal('hide');
                }
                else if(reponse.result == "erreur_champ_obligatoire"){
                	alert("Saisie invalide");
                }
            },
            error: function () {
            	jQuery("#rhpModalModif .modal-body div.alert").alert();
            	jQuery("#rhpModalModif .modal-body .alert h4").html("Erreur survenue");
            	jQuery("#rhpModalModif .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
            	jQuery("#rhpModalModif .modal-footer span").removeClass('loader');
            },
            beforeSend: function () {
            	jQuery("#formModificationPersonnel").attr("disabled", true);
            	jQuery("#rhpModalModif .modal-footer span").addClass('loader');
            },
            complete: function () {
            	jQuery("#formModificationPersonnel").removeAttr("disabled");
            	jQuery("#rhpModalModif .modal-footer span").removeClass('loader');
            }
        });
	}); */
	
	/*	jQuery("#formImrimBull").submit(function(e){
	alert(id);
		var id='${activeMoisId}';
		location.href = baseUrl +  "/paie/bulletinMoisPersonnel?idbul="+id; 
 		jQuery.ajax({
	        type: "GET",
	        url: baseUrl+"/paie/bulletinMoisPersonnel",
	        cache: false,
	        data: {
	        	idbul: id
	        },
			success: function (response) {
	        	if (response != null) {
	        		//alert(response.result);
	        		//jQuery("#rhpModal").modal('hide');
				} else {
					alert('Impossible de charger cet objet');
				}
	        },
	        error: function () {
	            
	        }
	    });
	}); 

	
	jQuery("#formModificationPersonnel").submit(function(e){
		e.preventDefault();
        var formData = $(this).serialize();
        console.log("form", formData);
        jQuery.ajax({
            type: "POST",
            url: baseUrl + "/personnels/editerpersonnel",
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == "succes") {
                	//MAJ de la ligne modifi�e
            		$table.bootstrapTable('updateRow', {
                        index: $table.find('tr.selected').data('index'),
                        row: reponse.data
                    });
            		jQuery("#formModificationPersonnel")[0].reset(); //Initialisation du formulaire
            		jQuery("#rhpModalModif").modal('hide');
                }
                else if(reponse.result == "erreur_champ_obligatoire"){
                	alert("Saisie invalide");
                }
            },
            error: function () {
            	jQuery("#rhpModalModif .modal-body div.alert").alert();
            	jQuery("#rhpModalModif .modal-body .alert h4").html("Erreur survenue");
            	jQuery("#rhpModalModif .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
            	jQuery("#rhpModalModif .modal-footer span").removeClass('loader');
            },
            beforeSend: function () {
            	jQuery("#formModificationPersonnel").attr("disabled", true);
            	jQuery("#rhpModalModif .modal-footer span").addClass('loader');
            },
            complete: function () {
            	jQuery("#formModificationPersonnel").removeAttr("disabled");
            	jQuery("#rhpModalModif .modal-footer span").removeClass('loader');
            	 jQuery ('#table'). bootstrapTable ('refresh', {  url: baseUrl +'/contratpersonnel/listcontratpersonnelActifjson' });
            	
            }
        });
	});
	*/
	function loadPrets(){
		jQuery.ajax({
            type: "GET",
            url: contextPath+"/paie/listPret",
            cache: false,
            success: function (response) {
            
            	if (response != null) {
					tabledata = '';
	        		for(i =0 ; i < response.total ; i++){
	        			tabledata += '<option value="'+response.rows[i].id+'" >'+response.rows[i].libelle+'</option>';
	        		}
	        		//tabledata += "";
	        			console.log(tabledata);
		    	  	jQuery('#pretpers').html(tabledata);
		    	 // 	jQuery("#mois").trigger("liszt:updated");
		    	 // 	jQuery('#mois option:selected').val()
				} else {
					alert('Failure! An error has occurred!');
				}
            },
            error: function () {
                
            },
        });
	}
	
	function loadPeriods(){
		jQuery.ajax({
            type: "GET",
            url: contextPath+"/paie/listperiodesupAuPret",
            cache: false,
            success: function (response) {
            	
            	if (response != null) {
					tabledata = '';
	        		for(i =0 ; i < response.total ; i++){
	        			tabledata += '<option value="'+response.rows[i].id+'" >'+response.rows[i].concmoisan+'</option>';
	        		}
	        		//tabledata += "";
	        		console.log(tabledata);
		    	  	jQuery('#periodedeb').html(tabledata);
		    	 // 	jQuery("#mois").trigger("liszt:updated");
		    	 // 	jQuery('#mois option:selected').val()
				} else {
					alert('Failure! An error has occurred!');
				}
            },
            error: function () {
                
            },
        });
	}





jQuery("#formGenerer").submit(function(e){
	//alert('bonjour');
	e.preventDefault();
	periodeID='${activeMoisId}';
	jQuery('#idPeriode').val(periodeID);
	jQuery('#idPeriode1').val(periodeID);
	jQuery.ajax({
        type: "GET",
        url: contextPath + "/paie/savebullPersonnel",
        cache: false,
        data: {id: '${activeMoisId}'},
        success: function (response) {
            if (response.result == "success") {
      
            	jQuery("#rhpModal").modal('hide');            	
            	jQuery("#rhpModalValider").modal();
            	//alert('verger');
            }
            else if(response.result == "erreur_champ_obligatoire"){
            	alert("Saisie invalide");
            }
        },
        error: function () {
        	jQuery("#rhpModal .modal-body div.alert").alert();
        	jQuery("#rhpModal .modal-body .alert h4").html("Erreur survenue");
        	jQuery("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
        	jQuery("#rhpModal .modal-footer span").removeClass('loader');
        },
        beforeSend: function () {
        	jQuery("#formAjout").attr("disabled", true);
        	jQuery("#rhpModal .modal-footer span").addClass('loader');
        },
        complete: function () {
        	jQuery("#formGenerer").removeAttr("disabled");
        	jQuery("#rhpModal .modal-footer span").removeClass('loader');
       	jQuery ('#tableliv'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/savebullPersonnel?id='+ jQuery('#idPeriode').val()});
        }
    });
});

function genererLivreDePaie(){
	
	jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/savelivrepaie",
        cache: false,
		success: function (response) {
        	if (response != null) {
        		alert(response.result);
        		jQuery("#rhpModalValider").modal('hide');
			} else {
				alert('Impossible de charger cet objet');
			}
        },
        error: function () {
            
        },
        complete: function () {
        //	jQuery("#formGenerer").removeAttr("disabled");
        //	jQuery("#rhpModal .modal-footer span").removeClass('loader');
       	jQuery ('#tableBull'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ jQuery('#idPeriode').val()});
        }
    });
}
function optionFormatter(id, row, index) {
	var option = '<a onclick="cherch(\''+row.personnel.id+'\')" data-toggle="modal" data-target="#rhpModalModif" href="#" title="Modifier personnel [LIBELLE : '+row.personnel.matricule+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	option += '&nbsp;<a data-toggle="modal" href="#" title="Suprimer bulletin [LIBELLE : '+row.libelle+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';
	
    return option;
}

function optionFormatter1(id, row, index) {
	var option = '<a onclick="cherch(\''+row.matricule+'\')" data-toggle="modal" data-target="#rhpModalModif" href="#" title="Modifier personnel [LIBELLE : '+row.matricule+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	option += '&nbsp;<a data-toggle="modal" href="#" title="Suprimer bulletin [LIBELLE : '+row.salaireBase+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';
	
    return option;
}

function optionFormatter2(id, row, index) {
	var option = '<a   target="_blank" href="${pageContext.request.contextPath}/paie/jrBulletinPersonnel?idbul='+row.id+'" title="Modifier Personnel [NOM : '+row.contratPersonnel.personnel.nom+' '+row.contratPersonnel.personnel.prenom+' ]"><span class="glyphicon glyphicon-print"></span> </a>&nbsp;';
	//option += '&nbsp;<a data-toggle="modal" href="#" title="Suprimer bulletin [LIBELLE : '+row.salaireBase+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';
	
    return option;
} 


function matriFormatter(personnel, row, index) {
	if(row.personnel.matricule == ''){
		return "";
	}
	return row.personnel.matricule;
}

function nomFormatter(personnel, row, index) {
	if(row.personnel.nom == ''){
		return "";
	}
	return row.personnel.nom+" "+row.personnel.prenom;
}


function matriculesFormatter(contratPersonnel, row, index) {
	if(row.contratPersonnel.personnel.matricule == ''){
		return "";
	}
	return row.contratPersonnel.personnel.matricule;
}

function nomcompletFormatter(contratPersonnel, row, index) {
	if(row.contratPersonnel.personnel.nom == ''){
		return "";
	}
	return row.contratPersonnel.personnel.nom+" "+row.contratPersonnel.personnel.prenom;
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

function cherch(idFonction){
//	var $scope = angular.element(document.getElementById("formDelete")).scope();
	
	jQuery('#idPersonnel').val(idFonction);
/* 	var rows = $table.bootstrapTable('getData');
	var fonction = _.findWhere(rows, {id: idFonction});
	fonction.info = fonction.libelle;
	$scope.$apply(function () {
        $scope.pupulateForm(fonction);
    }); */
}
</script>