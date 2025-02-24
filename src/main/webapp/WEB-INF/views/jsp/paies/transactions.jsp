<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	var contextPath = "<%=request.getContextPath() %>";
	
	jQuery(document).ready(function() {
		
		jQuery("#birthday").datepicker({
			dateFormat: 'dd/mm/yy',
	        showOtherMonths:true
	    });
		
		loadCustomerAdvisor();
	});
	
	jQuery('#reset').click(function(){
		loadDataToForm(jQuery('#id').val(), null, null, null, null, null, null);
	});
	
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
			jQuery('#widgetTitle').html('Liste Clients');
		} else if(view == 'new'){
			jQuery('#flag').attr('readOnly', false);
    		loadDataToForm(null, null, null, null);
			jQuery('#widgetTitle').html('Nouveau Client');
			jQuery('#formWidget').show('slow');
			jQuery('#tableWidget').hide('slow');
		} else{
			jQuery('#flag').attr('readOnly', true);
			jQuery('#widgetTitle').html('Modifier Client');
			jQuery('#formWidget').show('slow');
			jQuery('#tableWidget').hide('slow');
		}
		return false;
	}
	
	function loadDataToForm(id, customerAdvisor, name, birthday, address, phoneNumber, email){
		jQuery('#id').val(id);
		jQuery('#customerAdvisor').val(customerAdvisor);
		jQuery('#name').val(name);
		jQuery('#birthday').val(birthday);
		jQuery('#address').val(address);
		jQuery('#phoneNumber').val(phoneNumber);
		jQuery('#email').val(email);
	}
	
	function save(){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/customers/savecustomer",
            cache: false,
            data: {
				id: jQuery('#id').val(),
				customeradvisor: jQuery('#customerAdvisor').val(),
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
            		loadDataToForm(null, null, null, null, null, null, null);
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
                    url: contextPath + '/customers/customersjson'
                });
            }
        });
	}
	
	function edit(id){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/customers/loadcustomer",
            cache: false,
            data: {id: id},
			success: function (response) {
            	if (response != null) {
            		loadDataToForm(id, response.customerAdvisor.id, response.name, response.bday, response.address, response.phoneNumber, response.email);
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
	            url: contextPath+'/customers/deletecustomer',
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
                        url: contextPath + '/customers/customersjson'
                    });
	            }
	        });
		} else {
		    
		} 
	}
	
	function operationTypeFormatter(operationType) {
        return operationType.worded;
    }
	
	function transactionTypeFormatter(transactionType) {
        return transactionType.worded;
    }
	
	function senderFormatter(sender) {
		if(sender == null)
			return '-';
		return '[' + sender.customer.customerId + '] ' + sender.number + ' [' + sender.customer.name + ']';
    }
	
	function receiverFormatter(receiver) {
		if(receiver == null)
			return '-';
        return '[' + receiver.customer.customerId + '] ' + receiver.number + ' [' + receiver.customer.name + ']';
    }
	
	function branchFormatter(branch) {
        return branch.name;
    }

    function rowStyle(row, index) {
        var classes = ['active', 'success', 'info', 'warning', 'danger'];

        if (row.status == 0) {
            return {
                classes: classes[3]
            };
        }

        if (row.status == 1) {
            return {
                classes: classes[1]
            };
        }

        if (row.status == 2) {
            return {
                classes: classes[2]
            };
        }

        if (row.status == 3) {
            return {
                classes: classes[4]
            };
        }
        
        return {};
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
	              	<!-- <li><a href="#" onclick="widgetView('new')">Nouveau</a></li> -->
	            </ul>
	        </div>
	        <h4 id="widgetTitle" class="widgettitle">Liste Transactions</h4>
	    </div>
	    <div id="tableWidget" class="widgetcontent">
	    	<table id="table" class="table table-info table-striped"
			       	data-toggle="table"
			       	data-click-to-select="true"
			       	data-single-select="true"
			       	data-row-style="rowStyle"
			       	data-sort-name="flag" data-sort-order="desc"
			       	data-url="${pageContext.request.contextPath}/operations/transactionsjson"
			       	data-side-pagination="server"
			       	data-pagination="true"
			       	data-page-list="[5, 10, 20, 50, 100, 200]"
			       	data-show-columns="true" 
			       	data-search="true">
			    <thead>
			        <tr>
			        	<!-- <th data-field="customerAdvisor" data-formatter="bankFormatter" data-align="center" data-visible="false">Banque</th> -->
			        	<!-- <th data-field="customerAdvisor" data-formatter="branchFormatter" data-align="center" data-visible="false">Agence</th> -->
			        	<th data-field="number" data-align="center" data-sortable="true" data-switchable="false">N&deg; Transaction</th>
			        	<th data-field="tranDate" data-align="center" data-sortable="true" >Date Transaction</th>
			        	<th data-field="transactionType" data-align="left" data-sortable="true" data-formatter="transactionTypeFormatter" >Type Transaction</th>
			        	<th data-field="operationType" data-align="left" data-sortable="true" data-formatter="operationTypeFormatter" >Type Op&eacute;ration</th>
			        	<th data-field="sender" data-align="left" data-sortable="true" data-formatter="senderFormatter" >Compte D&eacute;bit&eacute;</th>
			        	<th data-field="receiver" data-align="left" data-sortable="true" data-formatter="receiverFormatter" >Compte Cr&eacute;dit&eacute;</th>
			        	<th data-field="nameSenderReceiver" data-align="left" data-sortable="true" data-visible="false">Nom (Emetteur / R&eacute;cepteur)</th>
			        	<th data-field="addressSenderReceiver" data-align="left" data-sortable="true" data-visible="false">Adresse</th>
			        	<th data-field="phoneSenderReceiver" data-align="center" data-sortable="true" data-visible="false">T&eacute;l&eacute;phone</th>
			        	<th data-field="bankSenderReceiver" data-align="left" data-sortable="true" data-visible="false">Banque</th>
			        	<th data-field="accountNumberSenderReceiver" data-align="center" data-sortable="true" data-visible="false">N&deg; Compte</th>
			        	<th data-field="ckeckNumberSenderReceiver" data-align="center" data-sortable="true" data-visible="false">N&deg; Ch&egrave;que</th>
			        	<th data-field="atmCode" data-align="center" data-sortable="true" data-visible="false">Code GAB</th>
			        	<th data-field="branch" data-align="left" data-sortable="true" data-formatter="branchFormatter" >Agence</th>
			        	<th data-field="operationStatus" data-align="left" data-sortable="true" data-visible="false">Statut</th>
			        	<th data-field="amt" data-align="right" data-sortable="true" >Montant</th>
			        	<!-- <th data-field="customerAdvisor" data-formatter="customerAdvisorFormatter" data-align="left" data-sortable="true">Conseillers</th>
			        	<th data-field="name" data-align="left" data-sortable="true" >Nom</th>
			        	<th data-field="bday" data-align="center" data-sortable="true">Date de Naissance</th>
			        	<th data-field="address" data-align="left" data-sortable="true">Adresse</th>
			        	<th data-field="phoneNumber" data-align="center" data-sortable="true">T&eacute;l&eacute;phone</th>
			        	<th data-field="email" data-align="left" data-sortable="true">Email</th>
			        	<th data-field="code" data-align="center" data-visible="false">Code</th>
			        	<th data-field="password" data-align="center" data-visible="false">Mot de Passe</th> -->
			        </tr>
			    </thead>
			</table>
	    </div><!--widgetcontent-->
    </div><!--widgetbox-->
</div><!-- widgetcontent-->