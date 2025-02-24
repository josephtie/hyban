<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	var contextPath = "<%=request.getContextPath() %>";
	
	jQuery(document).ready(function() {
		jQuery('#formWidget').show();
		
		jQuery("#birthday").datepicker({
			dateFormat: 'dd/mm/yy',
	        showOtherMonths:true
	    });
		
		loadAccounts();
		loadOperationsTypes()
	});
	
	jQuery('#reset').click(function(){
		loadDataToForm(jQuery('#id').val(), null, null, null, null, null, null, null, null);
	});
	
	function loadAccounts(){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/customers/loadaccounts",
            cache: false,
            success: function (response) {
            	if (response != null) {
					tabledata = '';
	        		for(i =0 ; i < response.length ; i++){
	        			tabledata += '<option value="'+response[i].id+'" >['+response[i].customer.customerId+' - '+response[i].customer.name+'] '+response[i].number+' ('+response[i].accountType.worded+')</option>';
	        		}
	        		//tabledata += "";
		    	  	jQuery('#depositAccount').html(tabledata);
		    	  	jQuery('#depositAccount').trigger("liszt:updated");
		    	  	jQuery('#depositAccount option:selected').val()
		    	  	
		    	  	jQuery('#withdrawalAccount').html(tabledata);
		    	  	jQuery('#withdrawalAccount').trigger("liszt:updated");
		    	  	jQuery('#withdrawalAccount option:selected').val()
		    	  	
		    	  	jQuery('#checkremittancebiSenderAccount').html(tabledata);
		    	  	jQuery('#checkremittancebiSenderAccount').trigger("liszt:updated");
		    	  	jQuery('#checkremittancebiSenderAccount option:selected').val();
		    	  	
		    	  	jQuery('#checkremittancebiReceiverAccount').html(tabledata);
		    	  	jQuery('#checkremittancebiReceiverAccount').trigger("liszt:updated");
		    	  	jQuery('#checkremittancebiReceiverAccount option:selected').val();
		    	  	
		    	  	jQuery('#checkremittancenbAccount').html(tabledata);
		    	  	jQuery('#checkremittancenbAccount').trigger("liszt:updated");
		    	  	jQuery('#checkremittancenbAccount option:selected').val(); 
		    	  	
		    	  	jQuery('#checkremittancebeAccount').html(tabledata);
		    	  	jQuery('#checkremittancebeAccount').trigger("liszt:updated");
		    	  	jQuery('#checkremittancebeAccount option:selected').val(); 
		    	  	
		    	  	jQuery('#transferiSenderAccount').html(tabledata);
		    	  	jQuery('#transferiSenderAccount').trigger("liszt:updated");
		    	  	jQuery('#transferiSenderAccount option:selected').val()
		    	  	
		    	  	jQuery('#transferiReceiverAccount').html(tabledata);
		    	  	jQuery('#transferiReceiverAccount').trigger("liszt:updated");
		    	  	jQuery('#transferiReceiverAccount option:selected').val();
		    	  	
		    	  	jQuery('#transfereAccount').html(tabledata);
		    	  	jQuery('#transfereAccount').trigger("liszt:updated");
		    	  	jQuery('#transfereAccount option:selected').val();
				} else {
					alert('Failure! An error has occurred!');
				}
            },
            error: function () {
                
            },
        });
	}
	
	function loadOperationsTypes(){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/settings/loadoperationstypes",
            cache: false,
            success: function (response) {
            	if (response != null) {
					tabledata = '';
	        		for(i =0 ; i < response.length ; i++){
	        			tabledata += '<option value="'+response[i].id+'" >'+response[i].worded+'</option>';
	        		}
	        		//tabledata += "";
		    	  	
		    	  	jQuery('#transfereOperationtype').html(tabledata);
		    	  	jQuery('#transfereOperationtype').trigger("liszt:updated");
		    	  	jQuery('#transfereOperationtype option:selected').val()
				} else {
					alert('Failure! An error has occurred!');
				}
            },
            error: function () {
                
            },
        });
	}
	
	function widgetView(view){
		if(view == 'list'){
			jQuery('#formWidget').hide('slow');
			jQuery('#tableWidget').show('slow');
			jQuery('#widgetTitle').html('Liste Comptes');
		} else if(view == 'new'){
			jQuery('#flag').attr('readOnly', false);
    		loadDataToForm(null, null, null, null);
			jQuery('#widgetTitle').html('Nouveau Compte');
			jQuery('#formWidget').show('slow');
			jQuery('#tableWidget').hide('slow');
		} else{
			jQuery('#flag').attr('readOnly', true);
			jQuery('#widgetTitle').html('Modifier Compte');
			jQuery('#formWidget').show('slow');
			jQuery('#tableWidget').hide('slow');
		}
		return false;
	}
	
	function loadDataToForm(id, accountType, customerAdvisor, authorizeAmount, name, birthday, address, phoneNumber, email){
		jQuery('#id').val(id);
		jQuery('#accountType').val(accountType);
		jQuery('#customerAdvisor').val(customerAdvisor);
		jQuery('#authorizeAmount').val(authorizeAmount);
		jQuery('#name').val(name);
		jQuery('#birthday').val(birthday);
		jQuery('#address').val(address);
		jQuery('#phoneNumber').val(phoneNumber);
		jQuery('#email').val(email);
	}
	
	function depositSave(){
		jQuery.ajax({
            type: 'POST',
            url: contextPath+'/operations/savetransactiondeposit',
            cache: false,
            data: {
				id: jQuery('#id').val(),
				depositAccount: jQuery('#depositAccount').val(),
				branchId: jQuery('#branchId').val(),
				depositApplicantName: jQuery('#depositApplicantName').val(),
				depositApplicantAddress: jQuery('#depositApplicantAddress').val(),
				depositApplicantPhone: jQuery('#depositApplicantPhone').val(),
				depositAmount: jQuery('#depositAmount').val()
			},
            beforeSend: function () {
        		//jQuery('#idButton').hide();
        		//jQuery('#idWait').show();
            },
            success: function (response) {
            	if (response != null) {
            		//loadDataToFormDeposit(null, null, null, null, null, null, null, null, null);
					alert('Enregistré avec succès');
					widgetView('list');
				} else {
					//alert('L\'utilisateur à été enregistré avec succès');
					alert('Impossible d\'effectuer cet enregistrement');
				}
            },
            error: function () {
                
            }
        });
	}
	
	function withdrawalSave(){
		jQuery.ajax({
            type: 'POST',
            url: contextPath+'/operations/savetransactionwithdrawal',
            cache: false,
            data: {
				id: jQuery('#id').val(),
				withdrawalAccount: jQuery('#withdrawalAccount').val(),
				branchId: jQuery('#branchId').val(),
				withdrawalAmount: jQuery('#withdrawalAmount').val()
			},
            beforeSend: function () {
        		//jQuery('#idButton').hide();
        		//jQuery('#idWait').show();
            },
            success: function (response) {
            	if (response != null) {
            		//loadDataToFormDeposit(null, null, null, null, null, null, null, null, null);
					alert('Enregistré avec succès');
					widgetView('list');
				} else {
					//alert('L\'utilisateur à été enregistré avec succès');
					alert('Impossible d\'effectuer cet enregistrement');
				}
            },
            error: function () {
                
            }
        });
	}
	
	function checkremittancebiSave(){
		jQuery.ajax({
            type: 'POST',
            url: contextPath+'/operations/savetransactioncheckremittancebi',
            cache: false,
            data: {
				id: jQuery('#id').val(),
				checkremittancebiSenderAccount: jQuery('#checkremittancebiSenderAccount').val(),
				checkremittancebiReceiverAccount: jQuery('#checkremittancebiReceiverAccount').val(),
				branchId: jQuery('#branchId').val(),
				checkremittancebiAmount: jQuery('#checkremittancebiAmount').val()
			},
            beforeSend: function () {
        		//jQuery('#idButton').hide();
        		//jQuery('#idWait').show();
            },
            success: function (response) {
            	if (response != null) {
            		//loadDataToFormDeposit(null, null, null, null, null, null, null, null, null);
					alert('Enregistré avec succès');
					widgetView('list');
				} else {
					//alert('L\'utilisateur à été enregistré avec succès');
					alert('Impossible d\'effectuer cet enregistrement');
				}
            },
            error: function () {
                
            }
        });
	}
	
	function checkremittancebeSave(){
		jQuery.ajax({
            type: 'POST',
            url: contextPath+'/operations/savetransactioncheckremittancebe',
            cache: false,
            data: {
				id: jQuery('#id').val(),
				checkremittancebeAccount: jQuery('#checkremittancebeAccount').val(),
				branchId: jQuery('#branchId').val(),
				checkremittancebeAmount: jQuery('#checkremittancebeAmount').val(),
				checkremittancebeApplicantBank: jQuery('#checkremittancebeApplicantBank').val(),
				checkremittancebeApplicantAccountNumber: jQuery('#checkremittancebeApplicantAccountNumber').val(),
				checkremittancebeApplicantCheckNumber: jQuery('#checkremittancebeApplicantCheckNumber').val(),
				checkremittancebeApplicantName: jQuery('#checkremittancebeApplicantName').val()
			},
            beforeSend: function () {
        		//jQuery('#idButton').hide();
        		//jQuery('#idWait').show();
            },
            success: function (response) {
            	if (response != null) {
            		//loadDataToFormDeposit(null, null, null, null, null, null, null, null, null);
					alert('Enregistré avec succès');
					widgetView('list');
				} else {
					//alert('L\'utilisateur à été enregistré avec succès');
					alert('Impossible d\'effectuer cet enregistrement');
				}
            },
            error: function () {
                
            }
        });
	}
	
	function checkremittancenbSave(){
		jQuery.ajax({
            type: 'POST',
            url: contextPath+'/operations/savetransactioncheckremittancenb',
            cache: false,
            data: {
				id: jQuery('#id').val(),
				checkremittancenbAccount: jQuery('#checkremittancenbAccount').val(),
				branchId: jQuery('#branchId').val(),
				checkremittancenbAmount: jQuery('#checkremittancenbAmount').val(),
				checkremittancenbApplicantName: jQuery('#checkremittancenbApplicantName').val(),
				checkremittancenbApplicantAddress: jQuery('#checkremittancenbApplicantAddress').val(),
				checkremittancenbApplicantPhone: jQuery('#checkremittancenbApplicantPhone').val()
			},
            beforeSend: function () {
        		//jQuery('#idButton').hide();
        		//jQuery('#idWait').show();
            },
            success: function (response) {
            	if (response != null) {
            		//loadDataToFormDeposit(null, null, null, null, null, null, null, null, null);
					alert('Enregistré avec succès');
					widgetView('list');
				} else {
					//alert('L\'utilisateur à été enregistré avec succès');
					alert('Impossible d\'effectuer cet enregistrement');
				}
            },
            error: function () {
                
            }
        });
	}
	
	function transfereSave(){
		jQuery.ajax({
            type: 'POST',
            url: contextPath+'/operations/savetransactiontransfere',
            cache: false,
            data: {
				id: jQuery('#id').val(),
				transfereOperationtype: jQuery('#transfereOperationtype').val(),
				transfereAccount: jQuery('#transfereAccount').val(),
				branchId: jQuery('#branchId').val(),
				transfereApplicantName: jQuery('#transfereApplicantName').val(),
				transfereApplicantBank: jQuery('#transfereApplicantBank').val(),
				transfereApplicantAccountNumber: jQuery('#transfereApplicantAccountNumber').val(),
				transfereAmount: jQuery('#transfereAmount').val()
			},
            beforeSend: function () {
        		//jQuery('#idButton').hide();
        		//jQuery('#idWait').show();
            },
            success: function (response) {
            	if (response != null) {
            		//loadDataToFormDeposit(null, null, null, null, null, null, null, null, null);
					alert('Enregistré avec succès');
					widgetView('list');
				} else {
					//alert('L\'utilisateur à été enregistré avec succès');
					alert('Impossible d\'effectuer cet enregistrement');
				}
            },
            error: function () {
                
            }
        });
	}
	
	function transferiSave(){
		jQuery.ajax({
            type: 'POST',
            url: contextPath+'/operations/savetransactiontransferi',
            cache: false,
            data: {
				id: jQuery('#id').val(),
				transferiSenderAccount: jQuery('#transferiSenderAccount').val(),
				branchId: jQuery('#branchId').val(),
				transferiReceiverAccount: jQuery('#transferiReceiverAccount').val(),
				transferiAmount: jQuery('#transferiAmount').val()
			},
            beforeSend: function () {
        		//jQuery('#idButton').hide();
        		//jQuery('#idWait').show();
            },
            success: function (response) {
            	if (response != null) {
            		//loadDataToFormDeposit(null, null, null, null, null, null, null, null, null);
					alert('Enregistré avec succès');
					widgetView('list');
				} else {
					//alert('L\'utilisateur à été enregistré avec succès');
					alert('Impossible d\'effectuer cet enregistrement');
				}
            },
            error: function () {
                
            }
        });
	}
	
</script>

<div class="widget">
	<div class="widgetbox">                        
	    <div class="headtitle">
	        <h4 id="widgetTitle" class="widgettitle">Caisse / Guichet</h4>
	    </div>
	    
	    <div class="tabbable">
        	<ul class="nav nav-tabs buttons-icons">
        		<li class="active"><a data-toggle="tab" href="#deposit">Versement Esp&egrave;ce</a></li>
            	<li><a data-toggle="tab" href="#withdrawal">Retrait Esp&egrave;ce</a></li>
            	<li><a data-toggle="tab" href="#checkremittancebi">Remise Ch&egrave;que (Barr&eacute; Interne)</a></li>
            	<li><a data-toggle="tab" href="#checkremittancenb">Remise Ch&egrave;que (Non Barr&eacute;)</a></li>
            	<li><a data-toggle="tab" href="#checkremittancebe">Remise Ch&egrave;que (Barr&eacute; Externe)</a></li>
        	</ul>
    		<div class="tab-content">
		        <input type="hidden" class="form-control input-default" id="id" placeholder="id">
		        <input type="hidden" class="form-control input-default" id="branch" value="${user.branch.id}" placeholder="id">
        		<div id="deposit" class="tab-pane active">
        			<div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Compte</label>
	                        <select data-placeholder="Choisir un compte ..." style="width:300px" class="chzn-select" id="depositAccount" tabindex="1"></select>
	                    </div>
	                </div>
	                <div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Montant</label>
	                        <input type="text" class="form-control input-default" id="depositAmount" placeholder="Montant">
	                    </div>
	                </div>
	                <div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Nom D&eacute;posant</label>
	                        <input type="text" class="form-control input-default" id="depositApplicantName" placeholder="Nom du deposant">
	                    </div>
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Adresse D&eacute;posant</label>
	                        <input type="text" class="form-control input-default" id="depositApplicantAddress" placeholder="Adresse du deposant">
	                    </div>
	                    <div class="form-group col-md-4">
	                    	<label for="bin">T&eacute;l&eacute;phone D&eacute;posant</label>
	                        <input type="text" class="form-control input-default" id="depositApplicantPhone" placeholder="Telephone du deposant">
	                    </div>
	                </div>
		            
		            <p class="stdformbutton">
	                    <button class="btn btn-primary" onclick="depositSave()">Enregistrer</button>
	                    <button id="depositReset" type="reset" class="btn btn-default">Annuler</button>
	                </p>
        		</div>
        		
        		<div id="withdrawal" class="tab-pane">
        			<div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Compte</label>
	                        <select data-placeholder="Choisir un compte ..." style="width:300px" class="chzn-select" id="withdrawalAccount" tabindex="1"></select>
	                    </div>
	                </div>
	                <div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Montant</label>
	                        <input type="text" class="form-control input-default" id="withdrawalAmount" placeholder="Montant">
	                    </div>
	                </div>
		            
		            <p class="stdformbutton">
	                    <button class="btn btn-primary" onclick="withdrawalSave()">Enregistrer</button>
	                    <button id="withdrawalReset" type="reset" class="btn btn-default">Annuler</button>
	                </p>
        		</div>
        		
        		<div id="checkremittancebi" class="tab-pane">
        			<div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Compte Emetteur</label>
	                        <select data-placeholder="Choisir un compte ..." style="width:300px" class="chzn-select" id="checkremittancebiSenderAccount" tabindex="1"></select>
	                    </div>
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Compte R&eacute;cepteur</label>
	                        <select data-placeholder="Choisir un compte ..." style="width:300px" class="chzn-select" id="checkremittancebiReceiverAccount" tabindex="1"></select>
	                    </div>
	                </div>
	                <div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Montant</label>
	                        <input type="text" class="form-control input-default" id="checkremittancebiAmount" placeholder="Montant">
	                    </div>
	                </div>
		            
		            <p class="stdformbutton">
	                    <button class="btn btn-primary" onclick="checkremittancebiSave()">Enregistrer</button>
	                    <button id="checkremittancebiReset" type="reset" class="btn btn-default">Annuler</button>
	                </p>
        		</div>
        		
        		<div id="checkremittancenb" class="tab-pane">
        			<div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Compte Emetteur</label>
	                        <select data-placeholder="Choisir un compte ..." style="width:300px" class="chzn-select" id="checkremittancenbAccount" tabindex="1"></select>
	                    </div>
	                </div>
	                <div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Montant</label>
	                        <input type="text" class="form-control input-default" id="checkremittancenbAmount" placeholder="Montant">
	                    </div>
	                </div>
	                <div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Nom R&eacute;cepteur</label>
	                        <input type="text" class="form-control input-default" id="checkremittancenbApplicantName" placeholder="Nom du recepteur">
	                    </div>
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Adresse R&eacute;cepteur</label>
	                        <input type="text" class="form-control input-default" id="checkremittancenbApplicantAddress" placeholder="Adresse du recepteur">
	                    </div>
	                    <div class="form-group col-md-4">
	                    	<label for="bin">T&eacute;l&eacute;phone R&eacute;cepteur</label>
	                        <input type="text" class="form-control input-default" id="checkremittancenbApplicantPhone" placeholder="Telephone du recepteur">
	                    </div>
		            
			            <p class="stdformbutton">
		                    <button class="btn btn-primary" onclick="checkremittancenbSave()">Enregistrer</button>
		                    <button id="checkremittancenbReset" type="reset" class="btn btn-default">Annuler</button>
		                </p>
	                </div>
        		</div>
        		
        		<div id="checkremittancebe" class="tab-pane">
        			<div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Compte R&eacute;cepteur</label>
	                        <select data-placeholder="Choisir un compte ..." style="width:300px" class="chzn-select" id="checkremittancebeAccount" tabindex="1"></select>
	                    </div>
	                </div>
	                <div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Montant</label>
	                        <input type="text" class="form-control input-default" id="checkremittancebeAmount" placeholder="Montant">
	                    </div>
	                </div>
	                <div class="row">
	                    <div class="form-group col-md-3">
	                    	<label for="bin">Nom Emetteur</label>
	                        <input type="text" class="form-control input-default" id="checkremittancebeApplicantName" placeholder="Nom du emetteur">
	                    </div>
	                    <div class="form-group col-md-3">
	                    	<label for="bin">Banque Emetteur</label>
	                        <input type="text" class="form-control input-default" id="checkremittancebeApplicantBank" placeholder="Banque Emetteur">
	                    </div>
	                    <div class="form-group col-md-3">
	                    	<label for="bin">N&deg; Compte Emetteur</label>
	                        <input type="text" class="form-control input-default" id="checkremittancebeApplicantAccountNumber" placeholder="Num. Compte Emetteur">
	                    </div>
	                    <div class="form-group col-md-3">
	                    	<label for="bin">N&deg; Ch&egrave;que Emetteur</label>
	                        <input type="text" class="form-control input-default" id="checkremittancebeApplicantCheckNumber" placeholder="Num. Cheque Emetteur">
	                    </div>
		            </div>
		            <p class="stdformbutton">
	                    <button class="btn btn-primary" onclick="checkremittancebeSave()">Enregistrer</button>
	                    <button id="checkremittancebeReset" type="reset" class="btn btn-default">Annuler</button>
	                </p>
        		</div>
        	</div>
        </div>
    </div><!--widgetbox-->
</div><!-- widgetcontent-->