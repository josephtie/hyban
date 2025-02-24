<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	var contextPath = "<%=request.getContextPath() %>";
	
	jQuery(document).ready(function() {
		jQuery('#formWidget').hide();
		jQuery('#tableWidget').show();
		
		// loadBanks();
	});
	
	jQuery('#reset').click(function(){
		loadDataToForm(jQuery('#id').val(), null, null, null, null, null, null);
	});
	
	function widgetView(view){
		if(view == 'list'){
			jQuery('#formWidget').hide('slow');
			jQuery('#tableWidget').show('slow');
			jQuery('#widgetTitle').html('Liste Agences');
		} else if(view == 'new'){
			jQuery('#flag').attr('readOnly', false);
    		loadDataToForm(null, null, null, null);
			jQuery('#widgetTitle').html('Nouvelle Agence');
			jQuery('#formWidget').show('slow');
			jQuery('#tableWidget').hide('slow');
		} else{
			jQuery('#flag').attr('readOnly', true);
			jQuery('#widgetTitle').html('Modifier Agence');
			jQuery('#formWidget').show('slow');
			jQuery('#tableWidget').hide('slow');
		}
		return false;
	}
	
	/* function loadBanks(){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/settings/loadbanks",
            cache: false,
            success: function (response) {
            	if (response != null) {
					tabledata = '';
	        		for(i =0 ; i < response.length ; i++){
	        			tabledata += '<option value="'+response[i].id+'" >'+response[i].name+'</option>';
	        		}
	        		//tabledata += "";
		    	  	jQuery('#idBank').html(tabledata);
		    	  	jQuery("#idBank").trigger("liszt:updated");
		    	  	jQuery('#idBank option:selected').val()
				} else {
					alert('Failure! An error has occurred!');
				}
            },
            error: function () {
                
            },
        });
	} */
	
	/* function loadDataToForm(id, idBank, branchCode, name, address, manager, phoneNumber, email){
		jQuery('#id').val(id);
		if(idBank == null){
			jQuery('#idBank').trigger('liszt:updated');
    	  	jQuery('#idBank option:selected').val()
		} else{
			jQuery('#idBank').val(idBank);
			jQuery("#idBank").val(idBank).trigger('change');
			jQuery('#idBank').trigger('liszt:updated');
		}
		jQuery('#branchCode').val(branchCode);
		jQuery('#name').val(name);
		jQuery('#address').val(address);
		jQuery('#manager').val(manager);
		jQuery('#phoneNumber').val(phoneNumber);
		jQuery('#email').val(email);
	} */
	
	function loadDataToForm(id, branchCode, name, address, manager, phoneNumber, email){
		jQuery('#id').val(id);
		jQuery('#branchCode').val(branchCode);
		jQuery('#name').val(name);
		jQuery('#address').val(address);
		jQuery('#manager').val(manager);
		jQuery('#phoneNumber').val(phoneNumber);
		jQuery('#email').val(email);
	}
	
	function save(){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/settings/savebranch",
            cache: false,
            data: {
				id: jQuery('#id').val(),
				branchcode: jQuery('#branchCode').val(),
				name: jQuery('#name').val(),
				address: jQuery('#address').val(),
				manager: jQuery('#manager').val(),
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
					alert('Enregistr� avec succ�s');
					widgetView('list');
				} else {
					//alert('L\'utilisateur � �t� enregistr� avec succ�s');
					alert('Impossible d\'effectuer cet enregistrement');
				}
            },
            error: function () {
                
            },
            complete: function () {
        		jQuery('#table').bootstrapTable('refresh', {
                    url: contextPath + '/settings/branchsjson'
                });
            }
        });
	}
	
	function edit(id){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/settings/loadbranch",
            cache: false,
            data: {id: id},
			success: function (response) {
            	if (response != null) {
            		loadDataToForm(response.id, response.branchCode, response.name, response.address, response.manager, response.phoneNumber, response.email);
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
	            url: contextPath+'/settings/deletebranch',
	            cache: false,
	            data: {id: id},
				success: function (response) {
	            	if (response.result == true) {
	            		alert('Supprim� avec succ�s');
					} else {
						alert('Impossible de supprimer cet objet');
					}
	            },
	            error: function () {
	                
	            },
	            complete: function () {
            		jQuery('#table').bootstrapTable('refresh', {
                        url: contextPath + '/settings/branchsjson'
                    });
	            }
	        });
		} else {
		    
		} 
	}
	
	function bankFormatter(bank) {
        return bank.name;
    }
	
	function optionFormatter(id, row, index) {
		var option = '<a onclick="edit(\''+row.id+'\')" data-toggle="modal" href="#" title="Modifier Agence [CODE AGENCE : '+row.branchCode+' - Nom : '+row.name+' ]"><span class="glyphicon glyphicon-file"></span></a>&nbsp;';
		option += '&nbsp;<a onclick="del(\''+row.id+'\')" data-toggle="modal" href="#" title="Horaire Agence [CODE AGENCE : '+row.branchCode+' - Nom : '+row.name+' ]"><span class="glyphicon glyphicon-calendar"></span></a>&nbsp;';
		option += '&nbsp;<a onclick="del(\''+row.id+'\')" data-toggle="modal" href="#" title="Suprimer Agence [CODE AGENCE : '+row.branchCode+' - Nom : '+row.name+' ]"><span class="glyphicon glyphicon-trash"></span></a>';
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
	        <h4 id="widgetTitle" class="widgettitle">Liste Agences</h4>
	    </div>
	    <div id="tableWidget" class="widgetcontent">
	    	<table id="table" class="table table-info table-striped"
			       	data-toggle="table"
			       	data-click-to-select="true"
			       	data-single-select="true"
			       	data-sort-name="flag" data-sort-order="desc"
			       	data-url="${pageContext.request.contextPath}/settings/branchsjson"
			       	data-side-pagination="server"
			       	data-pagination="true"
			       	data-page-list="[5, 10, 20, 50, 100, 200]"
			       	data-search="true">
			    <thead>
			        <tr>
			        	<th data-field="bank" data-formatter="bankFormatter" data-align="left" data-sortable="true">Banques</th>
			        	<th data-field="branchCode" data-align="left" data-sortable="true">Code Agence</th>
			        	<th data-field="name" data-align="left" data-sortable="true">Nom</th>
			        	<th data-field="address" data-align="left" data-sortable="true">Adresse</th>
			        	<th data-field="phoneNumber" data-align="center" data-sortable="true">T&eacute;l&eacute;phone</th>
			        	<th data-field="email" data-align="left" data-sortable="true">Email</th>
			        	<th data-field="manager" data-align="left" data-sortable="true">Chef d'Agence</th>
			        	<th data-field="id" data-formatter="optionFormatter" data-align="center" data-sortable="false">Options</th>
			        </tr>
			    </thead>
			</table>
	    </div><!--widgetcontent-->
	    
	    <div id="formWidget" class="widgetcontent">
	        
	            <div class="row">
                    <div class="form-group col-md-4">
                    	<label for="bin">Code Agence</label>
                        <input type="text" class="form-control input-default" id="branchCode" placeholder="Code Agence">
                    </div>
                    <div class="form-group col-md-4">
                    	<label for="bin">Nom</label>
                        <input type="text" class="form-control input-default" id="name" placeholder="Nom">
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
                <div class="row">
                    <div class="form-group col-md-12">
                    	<label for="bin">Chef d'Agence</label>
                        <input type="text" class="form-control input-default" id="manager" placeholder="Directeur">
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