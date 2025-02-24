<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
	<div class="col-md-12">
		<div class="panel panel-warning">
			<div class="panel-heading">
				<div class="panel-title-box">
					<h3>Livre de gratification</h3>
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
							<br><br><br><br>
			<div id="toolbar">
			    <div class="form-inline">
			        <button id="btnGenerer" type="button" data-toggle="modal" data-target="#rhpModal" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>G�n�rer la gratification</button>
			        <button id="btnImprimer" onclick="jQuery('#printAll').attr('disabled', 'disabled');" type="button" data-toggle="modal" data-target="#rhpModalImprimer" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>Imprimer bulletin</button>
			        <button id="btnVrmt" type="button" data-toggle="modal" data-target="#rhpModalVirmt" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>Ordre de virement</button>
			    </div>
			</div>
			<form action="#" id="formList">
				<table id="table" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-sort-name="name"
					data-sort-order="desc"
			       	data-show-columns="true" 
					data-url="${pageContext.request.contextPath}/gratification/listgratificationjson"
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[10, 20, 50, 100, 200]" 
					data-search="true">
					<thead>
						<tr>
							<!-- <th data-field="contratPersonnel" data-formatter="matriculeFormatter" data-align="left" data-sortable="true">Matricule</th> -->
							<th data-field="contratPersonnel" data-formatter="enSommeilFormatter" data-align="left" data-sortable="true">En Sommeil</th>
							<th data-field="contratPersonnel" data-formatter="nomFormatter" data-align="left" data-sortable="true">Nom</th>
							<th data-field="nombrePart" data-align="right">Nbre Part</th>
							<th data-field="montantGratificationBase"data-align="right">Gratif. Base</th>
							<th data-field="montantBrutImposable"data-align="right">Brut Impos.</th>
							<th data-field="montantIts" data-align="right" data-visible="false">ITS</th>
							<th data-field="montantCn" data-align="right" data-visible="false">CN</th>
							<th data-field="montantIgr" data-align="right" data-visible="false">IGR</th>
							<th data-field="montantTotalRetenueFiscale" data-align="right">Total Ret. Fisc.</th>
							<th data-field="montantCnps" data-align="right">CNPS</th>
							<th data-field="montantTotalRetenu" data-align="right">Total Retenue</th>
							<th data-field="indemniteTransp" data-align="right">Indem. de Trans.</th>
							<th data-field="montantNetPayer" data-align="right">Net &agrave; Payer</th>
							<th data-field="montantTotalBrut" data-align="right" data-visible="false">Total Brut</th>
							<th data-field="montantIs" data-align="right" data-visible="false">IS</th>
							<th data-field="montantTa" data-align="right" data-visible="false">TA</th>
							<th data-field="montantFpc" data-align="right" data-visible="false">FPC</th>
							<th data-field="totalChargeFiscalePatronale" data-align="right" data-visible="false">Total</th>
							<th data-field="prestationFamil" data-align="right" data-visible="false">Prest. Fam.</th>
							<th data-field="accidentTrav" data-align="right" data-visible="false">Acc. Trav.</th>
							<th data-field="montantRetraite" data-align="right" data-visible="false">Retraite</th>
							<th data-field="montantTotalChargeSocialePatronale" data-align="right" data-visible="false">Total</th>
							<th data-field="montantTotalPatronal" data-align="right" data-visible="false">Total Patronal</th>
							<th data-field=montantTotalMasseSalarialeMensuelle data-align="right" data-visible="false">Total Masse Sal. Mens.</th>
							<th data-field=montantTotalMasseSalarialeAnnuelle data-align="right" data-visible="false">Total Masse Sal. Ann.</th>
							<!-- <th data-field=contratPersonnel data-formatter="dateEntreFormatter" data-align="center">Date Entr&eacute;e</th>
							<th data-field=contratPersonnel data-formatter="situationMatrimonialeFormatter" data-align="left">Sit. Matri.</th>
							<th data-field=contratPersonnel data-formatter="enfantFormatter" data-align="right">Nbre Enf.</th>
							<th data-field=contratPersonnel data-formatter="fonctionFormatter" data-align="left">Fonction</th> -->
							<th data-field="contratPersonnel" data-formatter="optionFormatter" data-align="center">Options</th>
						</tr>
					</thead>
				</table>
			</form>
		</div>
	</div>
</div>
</div>
</div>

<!-- Generation des bulletins -->
<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div id="formGenerer" class="form-horizontal" >
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">G&eacute;n&eacute;ration du bulletin de paie</h4>
				</div>
                <div class="modal-body">
                	<h3>Vous �tes sur le point de g&eacute;n&eacute;rer le bulletin de paie de la p&eacute;riode de <span id="periodePaie" class="danger"></span> du personnel.</h3>
                	<br/>
                	<h4>En cliquant sur le bouton "Valider", le proccessus sera lanc&eacute;.</h4>
                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="button" onclick="genererGratification()" class="btn btn-success">Valider</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Generation des bulletins -->
<div class="modal fade" id="rhpModalImprimer" tabindex="-1" role="dialog" aria-labelledby="rhpModalImprimerLabel" data-backdrop="static">
    <div class="modal-dialog" style="width:90%;" role="document">
        <div class="modal-content">
            <div id="formImprimer" class="form-horizontal" >
                <div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalImprimerLabel">Imprimer Bulletin de Gratification</h4>
				</div>
                <div class="modal-body">
					<div class="col-md-3">
                		<select id="periodePaieImpression" name="periodePaieImpression" onchange="chargerGratificationParPeriode()" class="form-control select2" required="required"></select>
                		<br/>
                	</div>
                	<table id="tableImprimer" class="table table-info table-striped responsive"
						data-toggle="table"
				       	data-click-to-select="true"
			       		data-show-columns="true" 
				       	data-single-select="true"
				       	data-sort-order="desc"
				       	data-side-pagination="server">
					<thead>
						<tr>
							<!-- <th data-field="contratPersonnel" data-formatter="matriculeFormatter" data-align="left" data-sortable="true">Matricule</th> -->
							<!-- <th data-field="contratPersonnel" data-formatter="enSommeilFormatter" data-align="left" data-sortable="true">En Sommeil</th> -->
							<th data-field="nom" data-align="left" data-sortable="true">Nom</th>
							<th data-field="nombrePart" data-align="right">Nbre Part</th>
							<th data-field="gratificationBase"data-align="right">Gratif. Base</th>
							<th data-field="brutImposable"data-align="right">Brut Impos.</th>
							<th data-field="its" data-align="right" data-visible="false">ITS</th>
							<th data-field="cn" data-align="right" data-visible="false">CN</th>
							<th data-field="igr" data-align="right" data-visible="false">IGR</th>
							<th data-field="totalRetenueFiscale" data-align="right">Total Ret. Fisc.</th>
							<th data-field="cnps" data-align="right">CNPS</th>
							<th data-field="totalRetenue" data-align="right">Total Retenue</th>
							<th data-field="indemniteTransport" data-align="right">Indem. de Trans.</th>
							<th data-field="netPayer" data-align="right">Net &agrave; Payer</th>
							<th data-field="totalBrut" data-align="right" data-visible="false">Total Brut</th>
							<th data-field="is" data-align="right" data-visible="false">IS</th>
							<th data-field="ta" data-align="right" data-visible="false">TA</th>
							<th data-field="fpc" data-align="right" data-visible="false">FPC</th>
							<th data-field="totalChargeFiscalePatronale" data-align="right" data-visible="false">Total</th>
							<th data-field="prestationFamiliale" data-align="right" data-visible="false">Prest. Fam.</th>
							<th data-field="accidentTravail" data-align="right" data-visible="false">Acc. Trav.</th>
							<th data-field="retraite" data-align="right" data-visible="false">Retraite</th>
							<th data-field="totalChargeSocialePatronale" data-align="right" data-visible="false">Total</th>
							<th data-field="totalPatronal" data-align="right" data-visible="false">Total Patronal</th>
							<th data-field=totalMasseSalarialeMensuelle data-align="right" data-visible="false">Total Masse Sal. Mens.</th>
							<th data-field=totalMasseSalarialeAnnuelle data-align="right" data-visible="false">Total Masse Sal. Ann.</th>
							<th data-field=dateEntree data-align="center" data-visible="false">Date Entr&eacute;e</th>
							<th data-field=situationMatrimoniale data-align="left" data-visible="false">Sit. Matri.</th>
							<th data-field=nombreEnfant data-align="right" data-visible="false">Nbre Enf.</th>
							<th data-field=fonction data-align="left" data-visible="false">Fonction</th>
							<th data-field="id" data-formatter="impressionGratificationFormatter" data-align="center" data-sortable="true">Options</th>
						</tr>
					</thead>
				</table>
                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button id="printAll" type="button" onclick="genererGratification()" class="btn btn-success"><span class="glyphicon glyphicon-print"></span> Imprimer tous</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modification info du personnel -->
<div class="modal fade" id="rhpModalModif" tabindex="-1" role="dialog" aria-labelledby="rhpModalModif" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div id="formModificationPersonnel" class="form-horizontal" >
                <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Modification du personnel (<span id="infoPersonnel">Information du personnel</span>)</h4>
				</div>
                <div class="modal-body">
                	<div class="form-group">
                        <label for="libelle" class="col-md-4 control-label">Nombre d'enfants</label>
                        <div class="col-md-8">
                            <input type="text" id="nombreEnfant" name="libelle" class="form-control" placeholder="0" />
                        </div>
                    </div>
                	<div class="form-group">
                        <label for="salaireDeBase" class="col-md-4 control-label">Situation matrimoniale</label>
                        <div class="col-md-8">
                            <select id="situationMatrimoniale" name="situationMatrimoniale" class="form-control select2">
								<option value="1">Mari&eacute;(e)</option>
								<option value="2">C&eacute;libataire</option>
							</select>
                        </div>
                    </div>
                	<div class="form-group">
                        <label for="statut" class="col-md-4 control-label">En Sommeil</label>
                        <div class="col-md-8">
                            <select id="statut" name="statut" class="form-control select2">
								<option value="OUI">Oui</option>
								<option value="NON">Non</option>
							</select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="hidden" value="" id="idPersonnel" name="id">
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="button" onclick="editPersInfo()" class="btn btn-success">Valider</button>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="rhpModalVirmt" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formGenererVirmt" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->

                    <h4 class="modal-title" id="myModalLabel">G&eacute;n&eacute;ration d'ordre de virement</h4>
				</div>
                <div class="modal-body">
                	  <div class="form-group">
						<label class="col-md-2 control-label"> </label>
						<div class="col-md-10">
							<div class="row">
								<div class="col-md-6 ">
									<label for="telephone">Cpte Entreprise Banque</label> 
									 <select class="form-control input-small select2" id="idbankEnt" name="idbankEnt"  >
                                    <c:forEach items="${listeBanquesEmp}" var="banquesEnt">
                                        <option value="${banquesEnt.id}">${banquesEnt.sigle} (${banquesEnt.codbanq})</option>
                                    </c:forEach>
                                </select>
								</div>
								
								<div class="col-md-6 ">
									<label for="telephone"> Banque</label> 
									 <select class="form-control input-small select2" id="idbank" name="idbank"  >
                                    <c:forEach items="${listeBanques}" var="banques">
                                        <option value="${banques.id}">${banques.sigle} (${banques.codbanq})</option>
                                    </c:forEach>
                                </select>
								</div>
								
							</div>
						</div>
					</div> 
					      	<div class="form-group">
                        <label for="statut" class="col-md-2 control-label">Periode de gratification</label>
                        <div class="col-md-6">
                      <select id="periodePaieImpressionV" name="periodePaieImpressionV"  class="form-control input-small select2" required="required">
 									 <c:forEach items="${listePeriodes}" var="periode">
                                        <option value="${periode.id}">${periode.mois.mois} (${periode.annee.annee})</option>
                                    </c:forEach></select>
                        </div>
                          <div class="col-md-6">
                    
                        </div>
                    </div>
					
                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                	<!-- <input type="hidden"  id="idPeriode" name="id"> -->
                	 <input id="valtab" type="hidden" class="form-control" placeholder="Montant" value="${promotion}"/>  
                	 
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">

function imprimerBulletinGratification(id){
	jQuery.ajax({
        type: "GET",
        url: baseUrl+"/gratification/imprimerbulletingratification",
        cache: false,
        data: {
        	idgratification: id
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
}

function impressionGratificationFormatter(id, row, index){
	var option = '<a target="_blank" href="${pageContext.request.contextPath}/gratification/jrGratificationPersonnel?idbul='+row.id+'" title="Imprimer Bulletin"><span class="glyphicon glyphicon-print"></span></a>&nbsp;';
    return option;
}

function chargerGratificationParPeriode(){
	$tableImprimer = jQuery('#tableImprimer');
	$tableImprimer.bootstrapTable('removeAll');
	jQuery.ajax({
        type: 'POST',
        url: baseUrl+'/gratification/chargergratificationparperiode',
        cache: false,
        data: {periodepaie: jQuery('#periodePaieImpression').val()},
		success: function (response) {
			if (response != null) {
        		//jQuery('#myCustomerAccountTransactionModalLabel').html("Liste Transaction(s) Compte :: " + response[0].account.number + " (" + response[0].account.accountType.worded + ")");
				rows = [];
				if(response.length > 0)
					jQuery('#printAll').removeAttr('disabled');
				else
					jQuery('#printAll').attr('disabled', 'disabled');
        		for (i = 0; i < response.length; i++) {
        			 rows.push({
            		    	id : response[i].id,
            		    	nom : response[i].contratPersonnel.personnel.nom + ' ' + response[i].contratPersonnel.personnel.prenom,
            		    	nombrePart : response[i].nombrePart,
            		    	gratificationBase : response[i].montantGratificationBase,
            		    	brutImposable : response[i].montantBrutImposable,
            		    	its : response[i].montantIts,
            		    	cn : response[i].montantCn,
            		    	igr : response[i].montantIgr,
            		    	totalRetenueFiscale : response[i].montantTotalRetenueFiscale,
            		    	cnps : response[i].montantCnps,
            		    	totalRetenue : response[i].montantTotalRetenu,
            		    	indemniteTransport : response[i].indemniteTransp,
            		    	netPayer : response[i].montantNetPayer,
            		    	is : response[i].montantIs,
            		    	totalChargeFiscalePatronale : response[i].totalChargeFiscalePatronale,
            		    	prestationFamiliale : response[i].prestationFamil,
            		    	accidentTravail : response[i].accidentTrav,
            		    	retraite : response[i].montantRetraite,
            		    	totalChargeSocialePatronale : response[i].montantTotalChargeSocialePatronale,
            		    	totalMasseSalarialeMensuelle : response[i].montantTotalMasseSalarialeMensuelle,
            		    	totalMasseSalarialeAnnuelle : response[i].montantTotalMasseSalarialeAnnuelle,
            		    	dateEntree : response[i].contratPersonnel.personnel.dArrivee,
            		    	situationMatrimoniale : response[i].contratPersonnel.personnel.situationMatri,
            		    	nombreEnfant : response[i].contratPersonnel.personnel.nombrEnfant,
            		    	fonction : response[i].contratPersonnel.fonction.libelle
            		 });
				}
        		//$customerAccountTable = jQuery('#customerAccountTable').bootstrapTable();
       			$tableImprimer.bootstrapTable('append', rows);
       			console.log('chargement', rows);
    			$tableImprimer.bootstrapTable('scrollTo', 0);
        		//$customerAccountTable.bootstrapTable('refresh');
    			//getAccount();
			} else {
				alert('Failure! An error has occurred!');
			}
        },
        error: function () {
            
        }
    });
	//return rows;
}
jQuery("#formGenererVirmt").submit(function(e){
	

	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/gratification/postVirementBulletin?banque="+jQuery('#idbank').val()+"&banqueEmp="+jQuery('#idbankEnt').val()+"&periodePaieImpressionV="+jQuery('#periodePaieImpressionV').val(),
        cache: false,
		success: function (response) {
        	
			jQuery("#valtab").val(response.message);
		
        },
        error: function () {
            
        },
        complete: function () {
        	window.open(baseUrl +jQuery('#valtab').val());
        
        }
    });
	 
	 
});
function chargerPeriodePaie(){
	jQuery.ajax({
        type: "POST",
        url: baseUrl + "/parametrages/periodeouverte",
        cache: false,
        success: function (response) {
        	if (response != null) {
        		tabledata = '<option value="0" data-libelle="CHOISIR PERIODE PAIE" >CHOISIR PERIODE PAIE</option>';
        		for (i = 0; i < response.length; i++) {
					tabledata += '<option value="'+response[i].id+'" data-libelle="'+response[i].mois.mois + ' ' + response[i].annee.annee+'" >' + response[i].mois.mois + ' ' + response[i].annee.annee + '</option>';
				}
				jQuery('#periodePaieImpression').html(tabledata);
				jQuery('#periodePaieImpression').select2('val', 0);
				/* jQuery('#periodePaieImpressionV').html(tabledata);
				jQuery('#periodePaieImpressionV').select2('val', 0); */
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

function genererGratification(){
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/gratification/generergratification",
        cache: false,
		success: function (response) {
        	if (response != null) {
        		alert(response.result);
        		jQuery("#rhpModal").modal('hide');
			} else {
				alert('Impossible de charger cet objet');
			}
        },
        error: function () {
            
        }
    });
}

function selectPersInfo(idPersonnel){
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/personnels/choisirpersonnel",
        cache: false,
        data: {id: idPersonnel},
		success: function (response) {
        	if (response != null) {
        		jQuery('#idPersonnel').val(response.id);
        		jQuery('#nombreEnfant').val(response.nombrEnfant);
        		jQuery('#situationMatrimoniale').val(response.situationMatrimoniale).trigger('change');
        		jQuery('#statut').val(response.enSommeil).trigger('change');
			} else {
				alert('Impossible de charger cet objet');
			}
        },
        error: function () {
            
        }
    });
}

function editPersInfo(){
	var status;
	if(jQuery('#statut').val() == 'OUI')
		status = false;
	else
		status = true;
	jQuery.ajax({
		type : "POST",
		url : baseUrl + "/personnels/editerpersonnel",
		cache : false,
		data : {
			id : jQuery('#idPersonnel').val(),
			nombreenfant : jQuery('#nombreEnfant').val(),
			situationmatrimoniale : jQuery('#situationMatrimoniale').val(),
			statut : status
		},
		beforeSend : function() {
			//jQuery('#idButton').hide();
			//jQuery('#idWait').show();
		},
		success : function(response) {
			if (response != null) {
				//loadDataToForm(null, null, null, null, null, null, null, null);
				alert('Enregistr� avec succ�s');
				jQuery('#rhpModalModif').modal('hide');
				//widgetView('list');
			} else {
				//alert('L\'utilisateur � �t� enregistr� avec succ�s');
				alert('Impossible d\'effectuer cet enregistrement');
			}
		},
		error : function() {

		},
		complete : function() {
			jQuery('#table').bootstrapTable('refresh', {
				url : baseUrl + '/gratification/listgratificationjson'
			});
		}
	});
}

function enSommeilFormatter(contratPersonnel){
	return contratPersonnel.personnel.enSommeil;
}

function dateEntreFormatter(contratPersonnel){
	return contratPersonnel.personnel.dArrivee;
}

function situationMatrimonialeFormatter(contratPersonnel){
	return contratPersonnel.personnel.situationMatri;
}

function enfantFormatter(contratPersonnel){
	return contratPersonnel.personnel.nombrEnfant;
}

function fonctionFormatter(contratPersonnel){
	return contratPersonnel.fonction.libelle;
}

function matriculeFormatter(contratPersonnel){
	return contratPersonnel.personnel.matricule;
}

function nomFormatter(contratPersonnel){
	return contratPersonnel.personnel.nom + " " + contratPersonnel.personnel.prenom;
}

function ancienneteFormatter(contratPersonnel){
	return contratPersonnel.ancienneteInitial;
}

var actionUrl = "/paie/enregisterlivrepaie";
jQuery(document).ready(function($) {
	$( ".select2" ).select2();
	
	chargerPeriodePaie();

	periode='${activeMois}';
	jQuery('#periodePaie').html(periode);
	
	//Envoi des donnees
	$("#formModificationPersonnel").submit(function(e){
		e.preventDefault();
        var formData = $(this).serialize();
        console.log("form", formData);
        $.ajax({
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
            		$("#formAjout")[0].reset(); //Initialisation du formulaire
                    $("#rhpModal").modal('hide');
                }
                else if(reponse.result == "erreur_champ_obligatoire"){
                	alert("Saisie invalide");
                }
            },
            error: function () {
            	$("#rhpModalModif .modal-body div.alert").alert();
            	$("#rhpModalModif .modal-body .alert h4").html("Erreur survenue");
            	$("#rhpModalModif .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
            	$("#rhpModalModif .modal-footer span").removeClass('loader');
            },
            beforeSend: function () {
                $("#formModificationPersonnel").attr("disabled", true);
                $("#rhpModalModif .modal-footer span").addClass('loader');
            },
            complete: function () {
                $("#formModificationPersonnel").removeAttr("disabled");
                $("#rhpModalModif .modal-footer span").removeClass('loader');
            }
        });
	});
	
	/*$("#formGenerer").click(function(e){
		e.preventDefault();
		$.ajax({
            type: "POST",
            url: baseUrl + actionUrl,
            cache: false,
            data: {},
            success: function (reponse) {
                if (reponse.result == "succes") {
                	//Notification de succ�s
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
	});*/
});

function optionFormatter(id, row, index) {
	var option = '<a onclick="selectPersInfo('+row.contratPersonnel.personnel.id+')" data-toggle="modal" data-target="#rhpModalModif" href="#" title="[NOM : '+row.contratPersonnel.personnel.nom+']">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
	//option += '<a data-toggle="modal" data-target="#rhpModalModif" href="#" title="Edition du bulletin">  <span class="glyphicon glyphicon-file"></span></a>';
    return option;
}
</script>