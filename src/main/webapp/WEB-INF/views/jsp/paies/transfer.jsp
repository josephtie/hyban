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
	        			tabledata += '<option value="'+response[i].id+'" >['+response[i].customer.customerId+' - '+response[i].customer.name+'] '+response[i].number+'</option>';
	        		}
	        		//tabledata += "";
		    	  	
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
			jQuery('#widgetTitle').html('Virements');
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
	        <h4 id="widgetTitle" class="widgettitle">Virements</h4>
	    </div>
	    
	    <div class="tabbable">
        	<ul class="nav nav-tabs buttons-icons">
            	<li class="active"><a data-toggle="tab" href="#transferi">Virement (Interne)</a></li>
            	<li><a data-toggle="tab" href="#transfere">Virement (Externe)</a></li>
        	</ul>
    		<div class="tab-content">
		        <input type="hidden" class="form-control input-default" id="id" placeholder="id">
		        <input type="hidden" class="form-control input-default" id="branch" value="${user.branch.id}" placeholder="id">
        		
        		<div id="transferi" class="tab-pane active">
        			<div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Compte Emetteur</label>
	                        <select data-placeholder="Choisir un compte ..." style="width:300px" class="chzn-select" id="transferiSenderAccount" tabindex="1"></select>
	                    </div>
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Compte R&eacute;cepteur</label>
	                        <select data-placeholder="Choisir un compte ..." style="width:300px" class="chzn-select" id="transferiReceiverAccount" tabindex="1"></select>
	                    </div>
	                </div>
	                <div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Montant</label>
	                        <input type="text" class="form-control input-default" id="transferiAmount" placeholder="Montant">
	                    </div>
	                </div>
		            
		            <p class="stdformbutton">
	                    <button class="btn btn-primary" onclick="transferiSave()">Enregistrer</button>
	                    <button id="transferiReset" type="reset" class="btn btn-default">Annuler</button>
	                </p>
        		</div>
        		
        		<div id="transfere" class="tab-pane">
        			<div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Type Op&eacute;ration</label>
	                        <select data-placeholder="Choisir un type operation ..." style="width:300px" class="chzn-select" id="transfereOperationtype" tabindex="1"></select>
	                    </div>
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Compte</label>
	                        <select data-placeholder="Choisir un compte ..." style="width:300px" class="chzn-select" id="transfereAccount" tabindex="1"></select>
	                    </div>
	                </div>
	                <div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Montant</label>
	                        <input type="text" class="form-control input-default" id="transfereAmount" placeholder="Montant">
	                    </div>
	                </div>
	                <div class="row">
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Nom Emetteur / Recepteur</label>
	                        <input type="text" class="form-control input-default" id="transfereApplicantName" placeholder="Nom du emetteur">
	                    </div>
	                    <div class="form-group col-md-4">
	                    	<label for="bin">Banque Emetteur / Recepteur</label>
	                        <input type="text" class="form-control input-default" id="transfereApplicantBank" placeholder="Banque Emetteur">
	                    </div>
	                    <div class="form-group col-md-4">
	                    	<label for="bin">N&deg; Compte Emetteur</label>
	                        <input type="text" class="form-control input-default" id="transfereApplicantAccountNumber" placeholder="Num. Compte Emetteur">
	                    </div>
		            </div>
		            <p class="stdformbutton">
	                    <button class="btn btn-primary" onclick="transfereSave()">Enregistrer</button>
	                    <button id="checkremittancebeReset" type="reset" class="btn btn-default">Annuler</button>
	                </p>
        		</div>
        	</div>
        </div>
    </div><!--widgetbox-->
</div><!-- widgetcontent-->