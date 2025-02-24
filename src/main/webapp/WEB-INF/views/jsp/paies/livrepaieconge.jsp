<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row" >
	<div class="col-md-12">
		<div class="panel panel-warning">
			<div class="panel-heading">
				<div class="panel-title-box">
					<h3>Prevision Cong&eacute; de ${periode}</h3>
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
		<div id="tableWidget" class="widgetcontent">
			<div id="toolbar">
			    <div class="form-inline">
			        <button id="btnImprimer" type="button" onclick="chargerLivrePaieConge()" class="btn btn-success btn-xs"><span class="glyphicon glyphicon-print"></span> Imprimer Bulletin Cong&eacute;</button>
			   
			    </div>
			</div>
			<form action="#" id="formList">
				<table id="table" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-sort-name="name"
					data-sort-order="desc"
					data-url="${pageContext.request.contextPath}/conge/listprovisioncongejson"
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[10, 20, 50, 100, 200]" 
					data-search="true">
					<thead>
						<tr>
							<th data-field="contratPersonnel" data-formatter="nomFormatter" data-align="left" data-sortable="true">Nom Complet</th>
							<th data-field="nbrePart" data-align="right" data-sortable="true">Nbre Part</th>
							<!-- <th data-field="tempsPresenceEffectif" data-align="right">Tps Pr&eacute;s. Eff.</th> -->
							<th data-field="montantSalaireMoyenMensuel" data-align="right" data-visible="false">Sal. Moy. Mens.</th>
							<th data-field="indemniteRepresentMoyenMensuel" data-align="right" data-visible="false">Ind. Rep. Men.</th>
							<th data-field="nombreJourCongeDu" data-align="right" data-visible="false">Nbre Jour Cng</th>
							<th data-field="montantProvisionConge" data-align="right">Prov. Cng.</th>
							<th data-field="montantAllocationCongeNet" data-align="right">All. Cng. Net</th>
							<th data-field="montantBaseImposableAllocationConge" data-align="right">Base Imp. All. Cng.</th>
							<th data-field="montantTotalRetenue" data-align="right">Tot. Ret. Fis.</th>
							<th data-field="montantTotalProvisionImpot" data-align="right">Tot.l Pr&eacute;v. Imp.</th>
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
<div class="modal fade" id="rhpModalImprimer" tabindex="-1" role="dialog" aria-labelledby="rhpModalImprimerLabel" data-backdrop="static">
    <div class="modal-dialog" style="width:90%;" role="document">
        <div class="modal-content">
            <div id="formImprimer" class="form-horizontal" >
                <div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalImprimerLabel">Imprimer Bulletin de Conge de ${periode}</h4>
				</div>
                <div class="modal-body">
                <div class="col-md-3">
                		<select id="periodePaieImpression" name="periodePaieImpression" onchange="chargerLivrePaieCongePeriode()" class="form-control select2" required="required"></select>
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
							<th data-field="nom" data-align="left" data-sortable="true">Nom</th>
							<th data-field="nombrePart" data-align="right">Nbre Part</th>
							<!-- <th data-field="tempsPresenceEffectif"data-align="right">Tps. Prs. Eff.</th> -->
							<th data-field="salaireMoyenMensuel"data-align="right">Sal. Moy. Mens.</th>
							<th data-field="nombreJourConge" data-align="right" data-visible="false">Nbre Jour Cong.</th>
							<th data-field="montantProvisionConge" data-align="right" data-visible="true">Prov. Cong.</th>
							<th data-field="baseImpotAllocationConge" data-align="right" data-visible="true">Base Imp. Alloc. Cong.</th>
							<th data-field="its" data-align="right" data-visible="false">ITS</th>
							<th data-field="cn" data-align="right" data-visible="false">CN</th>
							<th data-field="igr" data-align="right" data-visible="false">IGR</th>
							<th data-field="cnps" data-align="right" data-visible="false">CNPS</th>
							<th data-field="totalRetenue" data-align="right" data-visible="true">Tot. Ret.</th>
							<th data-field="montantAllocationCongeNet" data-align="right">Cng. Net</th>
							<th data-field="is" data-align="right" data-visible="false">IS</th>
							<th data-field="ta" data-align="right" data-visible="false">TA</th>
							<th data-field="fpc" data-align="right" data-visible="false">FPC</th>
							<th data-field="totalProvisionImpot" data-align="right" data-visible="true">Tot. Prov. Imp.</th>
							<th data-field="prestationFamiliale" data-align="right" data-visible="false">Prest. Fam.</th>
							<th data-field="accidentTravail" data-align="right" data-visible="false">Acc. Trav.</th>
							<th data-field="retraite" data-align="right" data-visible="false">Retraite</th>
							<th data-field="totalChargeSociale" data-align="right" data-visible="true">Tot. Char. Soc.</th>
							<th data-field="totalPatronal" data-align="right" data-visible="true">Total Patronal</th>
							<th data-field="id" data-formatter="impressionCongeFormatter" data-align="center" data-sortable="true">Options</th>
						</tr>
					</thead>
				</table>
                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                  <!--   <button id="printAll" type="button" onclick="genererGratification()" class="btn btn-success"><span class="glyphicon glyphicon-print"></span> Imprimer tous</button> -->
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
	jQuery(document).ready(function($) {
		$( ".select2" ).select2();
		//chargerLivrePaieConge();
		chargerPeriodePaie();
	});
	
	function nomFormatter(contratPersonnel){
		return contratPersonnel.personnel.nom + " " + contratPersonnel.personnel.prenom;
	}
	
	function genererBulletinConge(id){
		jQuery.ajax({
	        type: "POST",
	        url: baseUrl+"/conge/genererbulletinconge",
	        cache: false,
	        data: {
	        	id: id
			},
	        beforeSend: function () {
	    		//jQuery('#idButton').hide();
	    		//jQuery('#idWait').show();
	        },
	        success: function (response) {
	        	if (response != null) {
	        		//alert(response.fpc);
					alert('Enregistr� avec succ�s');
				} else {
					alert('Impossible d\'effectuer cet enregistrement');
				}
	        },
	        error: function () {
	            
	        },
	        complete: function () {
	    		jQuery('#table').bootstrapTable('refresh', {
	                url: baseUrl + '/conge/listprovisioncongejson'
	            });
	        }
	    });
	}
	
	
	function chargerLivrePaieConge(){
		$tableImprimer = jQuery('#tableImprimer');
		$tableImprimer.bootstrapTable('removeAll');
		/* jQuery.ajax({
	        type: 'POST',
	        url: baseUrl+'/conge/chargerlivrepaieconge',
	        cache: false,
	        data: {periodepaie: jQuery('#periodePaieImpression').val()},
			success: function (response) {
				if (response != null) {
	        		//jQuery('#myCustomerAccountTransactionModalLabel').html("Liste Transaction(s) Compte :: " + response[0].account.number + " (" + response[0].account.accountType.worded + ")");
					rows = [];
					/*if(response.length > 0)
						jQuery('#printAll').removeAttr('disabled');
					else
						jQuery('#printAll').attr('disabled', 'disabled');
	        		for (i = 0; i < response.length; i++) {
	        			 rows.push({
	            		    	id : response[i].id,
	            		    	nom : response[i].contratPersonnel.personnel.nom + ' ' + response[i].contratPersonnel.personnel.prenom,
	            		    	nombrePart : response[i].nombrePart,
	            		    	tempsPresenceEffectif : response[i].tempsPresenceEffectif,
	            		    	salaireMoyenMensuel : response[i].montantSalaireMoyenMensuel,
	            		    	nombreJourConge : response[i].nombreJourCongeDu,
	            		    	montantProvisionConge : response[i].montantProvisionConge,
	            		    	baseImpotAllocationConge : response[i].montantBaseImposableAllocationConge,
	            		    	//montantTotalRetenue : response[i].montantTotalRetenue,
	            		    	its : response[i].montantITS,
	            		    	cn : response[i].montantCN,
	            		    	igr : response[i].montantIGR,
	            		    	cnps : response[i].montantCNPS,
	            		    	totalRetenue : response[i].montantTotalRetenue,
	            		    	montantAllocationCongeNet : response[i].montantAllocationCongeNet,
	            		    	is : response[i].montantIs,
	            		    	ta : response[i].montantTa,
	            		    	fpc : response[i].prestationFamil,
	            		    	totalProvisionImpot : response[i].accidentTrav,
	            		    	prestationFamiliale : response[i].prestationFamil,
	            		    	accidentTravail : response[i].accidentTrav,
	            		    	retraite : response[i].montantRetraite,
	            		    	totalChargeSociale : response[i].montantTotalProvisionChargeSocial,
	            		    	totalPatronal : response[i].montantTotalChargePatronale
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
	            
	        },
			complete : function() {
				
			}
	    }); */
		//return rows;
		jQuery('#rhpModalImprimer').modal('show');
	}
	
	function chargerLivrePaieCongePeriode(){
		$tableImprimer = jQuery('#tableImprimer');
		$tableImprimer.bootstrapTable('removeAll');
		jQuery.ajax({
	        type: 'POST',
	        url: baseUrl+'/conge/chargerlivrepaiecongeperiode',
	        cache: false,
	        data: {periodepaie: jQuery('#periodePaieImpression').val()},
			success: function (response) {
				if (response != null) {
	        		//jQuery('#myCustomerAccountTransactionModalLabel').html("Liste Transaction(s) Compte :: " + response[0].account.number + " (" + response[0].account.accountType.worded + ")");
					rows = [];
					/*if(response.length > 0)
						jQuery('#printAll').removeAttr('disabled');
					else
						jQuery('#printAll').attr('disabled', 'disabled');*/
	        		for (i = 0; i < response.length; i++) {
	        			 rows.push({
	            		    	id : response[i].id,
	            		    	nom : response[i].contratPersonnel.personnel.nom + ' ' + response[i].contratPersonnel.personnel.prenom,
	            		    	nombrePart : response[i].nombrePart,
	            		    	tempsPresenceEffectif : response[i].tempsPresenceEffectif,
	            		    	salaireMoyenMensuel : response[i].montantSalaireMoyenMensuel,
	            		    	nombreJourConge : response[i].nombreJourCongeDu,
	            		    	montantProvisionConge : response[i].montantProvisionConge,
	            		    	baseImpotAllocationConge : response[i].montantBaseImposableAllocationConge,
	            		    	//montantTotalRetenue : response[i].montantTotalRetenue,
	            		    	its : response[i].montantITS,
	            		    	cn : response[i].montantCN,
	            		    	igr : response[i].montantIGR,
	            		    	cnps : response[i].montantCNPS,
	            		    	totalRetenue : response[i].montantTotalRetenue,
	            		    	montantAllocationCongeNet : response[i].montantAllocationCongeNet,
	            		    	is : response[i].montantIs,
	            		    	ta : response[i].montantTa,
	            		    	fpc : response[i].prestationFamil,
	            		    	totalProvisionImpot : response[i].accidentTrav,
	            		    	prestationFamiliale : response[i].prestationFamil,
	            		    	accidentTravail : response[i].accidentTrav,
	            		    	retraite : response[i].montantRetraite,
	            		    	totalChargeSociale : response[i].montantTotalProvisionChargeSocial,
	            		    	totalPatronal : response[i].montantTotalChargePatronale
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
	            
	        },
			complete : function() {
				jQuery('#rhpModalImprimer').modal('show');
			}
	    });
		//return rows;
	}
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
	function impressionCongeFormatter(id, row, index){
		var option = '<a target="_blank" href="${pageContext.request.contextPath}/conge/JRBulletinConge?idbul='+row.id+'" title="Imprimer Bulletin"><span class="glyphicon glyphicon-print"></span></a>&nbsp;';
	    return option;
	}

	function optionFormatter(id, row, index) {
		var option = '<a onclick="genererBulletinConge('+row.contratPersonnel.id+')" href="#" title="Modification">  <span class="glyphicon glyphicon-ok"></span></a>&nbsp;';
	    return option;
	}
</script>