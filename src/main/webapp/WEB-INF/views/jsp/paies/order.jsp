<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	var contextPath = "<%=request.getContextPath() %>";
	
	jQuery(document).ready(function() {
		jQuery('#formWidget').hide();
		jQuery('#tableWidget').show();
		
		jQuery("#birthday").datepicker({
			dateFormat: 'dd/mm/yy',
	        showOtherMonths:true
	    });
		
		loadAccountsTypes();
		loadCustomerAdvisor();
	});
	
	jQuery('#reset').click(function(){
		loadDataToForm(jQuery('#id').val(), null, null, null, null, null, null, null, null);
	});
	
	function loadAccountsTypes(){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/settings/loadaccountstypes",
            cache: false,
            success: function (response) {
            	if (response != null) {
					tabledata = '';
	        		for(i =0 ; i < response.length ; i++){
	        			tabledata += '<option value="'+response[i].id+'" >'+response[i].worded+'</option>';
	        		}
	        		//tabledata += "";
		    	  	jQuery('#accountType').html(tabledata);
		    	  	jQuery("#accountType").trigger("liszt:updated");
		    	  	jQuery('#accountType option:selected').val()
				} else {
					alert('Failure! An error has occurred!');
				}
            },
            error: function () {
                
            },
        });
	}
	
	function loadCustomerAdvisor(){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/settings/loadcustomersadvisors",
            cache: false,
            success: function (response) {
            	if (response != null) {
					tabledata = '';
	        		for(i =0 ; i < response.length ; i++){
	        			tabledata += '<option value="'+response[i].id+'" >'+response[i].name+'</option>';
	        		}
	        		//tabledata += "";
		    	  	jQuery('#customerAdvisor').html(tabledata);
		    	  	jQuery("#customerAdvisor").trigger("liszt:updated");
		    	  	jQuery('#customerAdvisor option:selected').val()
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
	
	function save(){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/customers/saveaccount",
            cache: false,
            data: {
				id: jQuery('#id').val(),
				accounttype: jQuery('#accountType').val(),
				customeradvisor: jQuery('#customerAdvisor').val(),
				authorizeamount: jQuery('#authorizeAmount').val(),
				name: jQuery('#name').val(),
				birthday: jQuery('#birthday').val(),
				address: jQuery('#address').val(),
				phonenumber: jQuery('#phoneNumber').val(),
				email: jQuery('#email').val()
			},
            beforeSend: function () {
        		//jQuery('#idButton').hide();
        		//jQuery('#idWait').show();
            },
            success: function (response) {
            	if (response != null) {
            		loadDataToForm(null, null, null, null, null, null, null, null, null);
					alert('Enregistré avec succès');
					widgetView('list');
				} else {
					//alert('L\'utilisateur à été enregistré avec succès');
					alert('Impossible d\'effectuer cet enregistrement');
				}
            },
            error: function () {
                
            },
            complete: function () {
        		jQuery('#table').bootstrapTable('refresh', {
                    url: contextPath + '/customers/accountsjson'
                });
            }
        });
	}
	
	function edit(id){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/customers/loadaccount",
            cache: false,
            data: {id: id},
			success: function (response) {
            	if (response != null) {
            		loadDataToForm(id, response.accountType.id, response.customer.customerAdvisor.id, response.authorizeAmount, response.customer.name, response.customer.birthday, response.customer.address, response.customer.phoneNumber, response.customer.email);
            		widgetView('edit');
				} else {
					alert('Impossible de charger cet objet');
				}
            },
            error: function () {
                
            }
        });
	}
	
	function del(id){
		var r = confirm('Confirmer la suppression ?');
		if (r == true) {
			jQuery.ajax({
	            type: 'POST',
	            url: contextPath+'/customers/deleteaccount',
	            cache: false,
	            data: {id: id},
				success: function (response) {
	            	if (response.result == true) {
	            		alert('Supprimé avec succès');
					} else {
						alert('Impossible de supprimer cet objet');
					}
	            },
	            error: function () {
	                
	            },
	            complete: function () {
            		jQuery('#table').bootstrapTable('refresh', {
                        url: contextPath + '/customers/accountsjson'
                    });
	            }
	        });
		} else {
		    
		} 
	}
	
	function accounTypeFormatter(accountType) {
        return accountType.worded;
    }
	
	function customerAdvisorFormatter(customer) {
        return customer.customerAdvisor.name;
    }
	
	function customerNameFormatter(customer) {
        return customer.name;
    }
	
	function customerBirthdayFormatter(customer) {
        return customer.bday;
    }
	
	function customerPhoneNumberFormatter(customer) {
        return customer.phoneNumber;
    }
	
	function customerAddressFormatter(customer) {
        return customer.address;
    }
	
	function customerEmailFormatter(customer) {
        return customer.email;
    }
	
	function optionFormatter(id, row, index) {
		var option = '<a onclick="edit(\''+row.id+'\')" data-toggle="modal" href="#" title="Modifier Compte [Type : '+row.accountType.worded+' - Numero : '+row.number+' ]"><span class="glyphicon glyphicon-file"></span></a>&nbsp;';
		/* option += '&nbsp;<a onclick="del(\''+row.id+'\')" data-toggle="modal" href="#" title="Suprimer Compte [Type : '+row.accountType.worded+' - Numero : '+row.number+' ]"><span class="glyphicon glyphicon-trash"></span></a>'; */
        return option;
    }
</script>

<div class="widget">
	<div class="widgetbox">                        
	    <div class="headtitle">
	        <div class="btn-group">
	            <button data-toggle="dropdown" class="btn dropdown-toggle">Action <span class="caret"></span></button>
				<ul class="dropdown-menu">
	              	<li><a href="#" onclick="widgetView('list')">Listes</a></li>
	              	<li class="divider"></li>
	              	<li><a href="#" onclick="widgetView('new')">Nouveau</a></li>
	            </ul>
	        </div>
	        <h4 id="widgetTitle" class="widgettitle">Liste Comptes</h4>
	    </div>
	    <div id="tableWidget" class="widgetcontent">
	    	<table id="table" class="table table-info table-striped"
			       	data-toggle="table"
			       	data-click-to-select="true"
			       	data-single-select="true"
			       	data-sort-name="flag" data-sort-order="desc"
			       	data-url="${pageContext.request.contextPath}/customers/accountsjson"
			       	data-side-pagination="server"
			       	data-pagination="true"
			       	data-page-list="[5, 10, 20, 50, 100, 200]"
			       	data-search="true">
			    <thead>
			        <tr>
			        	<th data-field="accountType" data-formatter="accounTypeFormatter" data-align="left" data-sortable="true">Type</th>
			        	<th data-field="number" data-align="center" data-sortable="true">Num&eacute;ro</th>
			        	<th data-field="customer" data-formatter="customerAdvisorFormatter" data-align="left" data-sortable="true">Conseillers</th>
			        	<th data-field="authorizeAmount" data-align="right" data-sortable="true">Montant Autoris&eacute;</th>
			        	<th data-field="customer" data-formatter="customerNameFormatter" data-align="left" data-sortable="true">Nom</th>
			        	<th data-field="customer" data-formatter="customerBirthdayFormatter" data-align="center" data-sortable="true">Date de Naissance</th>
			        	<th data-field="balance" data-align="right" data-sortable="true">Solde</th>
			        	<th data-field="id" data-formatter="optionFormatter" data-align="center" data-sortable="false">Options</th>
			        </tr>
			    </thead>
			</table>
	    </div><!--widgetcontent-->
	    
	    <div id="formWidget" class="widgetcontent">
	        
	            <div class="row">
                    <div class="form-group col-md-4">
                    	<label for="bin">Type de Compte</label>
                        <select data-placeholder="Choisir un type de compte ..." style="width:300px" class="chzn-select" id="accountType" tabindex="1"></select>
                    </div>
                    
                    <div class="form-group col-md-4">
                    	<label for="bin">Conseiller Client</label>
                        <select data-placeholder="Choisir un conseiller ..." style="width:300px" class="chzn-select" id="customerAdvisor" tabindex="1"></select>
                    </div>
                </div><div class="row">
                    <div class="form-group col-md-4">
                    	<label for="bin">Montant Autoris&eacute;</label>
                        <input type="text" class="form-control input-default" id="authorizeAmount" placeholder="Montant Autorisé">
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-4">
                    	<label for="bin">Nom</label>
                        <input type="text" class="form-control input-default" id="name" placeholder="Nom">
                    </div>
                    <div class="form-group col-md-4">
                    	<label for="bin">Date de naissance</label>
                        <input type="text" class="form-control input-default" id="birthday" placeholder="Date de naissance">
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-4">
                    	<label for="bin">Adresse</label>
                        <input type="text" class="form-control input-default" id="address" placeholder="Adresse">
                    </div>
                    <div class="form-group col-md-4">
                    	<label for="bin">T&eacute;l&eacute;phone</label>
                        <input type="text" class="form-control input-default" id="phoneNumber" placeholder="Telephone">
                    </div>
                    <div class="form-group col-md-4">
                    	<label for="bin">Email</label>
                        <input type="text" class="form-control input-default" id="email" placeholder="Email">
                    </div>
                </div>
	            <input type="hidden" class="form-control input-default" id="id" placeholder="id">
	            
	            <p class="stdformbutton">
                    <button class="btn btn-primary" onclick="save()">Enregistrer</button>
                    <button id="reset" type="reset" class="btn btn-default">Annuler</button>
                </p>
	        
	    </div><!-- widgetcontent-->
    </div><!--widgetbox-->
</div><!-- widgetcontent-->