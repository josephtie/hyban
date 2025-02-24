<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	var contextPath = "<%=request.getContextPath() %>";
	
	jQuery(document).ready(function() {
		jQuery('#formWidget').hide();
		jQuery('#tableWidget').show();
	});
	
	jQuery('#reset').click(function(){
		loadDataToForm(jQuery('#id').val(), null, null, null);
	});
	
	function widgetView(view){
		if(view == 'list'){
			jQuery('#formWidget').hide('slow');
			jQuery('#tableWidget').show('slow');
			jQuery('#widgetTitle').html('Liste Types Comptes');
		} else if(view == 'new'){
			jQuery('#flag').attr('readOnly', false);
    		loadDataToForm(null, null, null, null);
			jQuery('#widgetTitle').html('Nouveau Type Compte');
			jQuery('#formWidget').show('slow');
			jQuery('#tableWidget').hide('slow');
		} else{
			jQuery('#flag').attr('readOnly', true);
			jQuery('#widgetTitle').html('Modifier Type Compte');
			jQuery('#formWidget').show('slow');
			jQuery('#tableWidget').hide('slow');
		}
		return false;
	}
	
	function loadDataToForm(id, flag, worded, description){
		jQuery('#id').val(id);
		jQuery('#flag').val(flag);
		jQuery('#worded').val(worded);
		jQuery('#description').val(description);
	}
	
	function save(){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/settings/saveaccounttype",
            cache: false,
            data: {
				id: jQuery('#id').val(),
				flag: jQuery('#flag').val(),
				worded: jQuery('#worded').val(),
				description: jQuery('#description').val()
			},
            beforeSend: function () {
        		//jQuery('#idButton').hide();
        		//jQuery('#idWait').show();
            },
            success: function (response) {
            	if (response != null) {
            		loadDataToForm(null, null, null, null);
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
                    url: contextPath + '/settings/accountstypesjson'
                });
            }
        });
	}
	
	function edit(id){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/settings/loadaccounttype",
            cache: false,
            data: {id: id},
			success: function (response) {
            	if (response != null) {
            		loadDataToForm(response.id, response.flag, response.worded, response.description);
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
	            url: contextPath+'/settings/deleteaccounttype',
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
                        url: contextPath + '/settings/accountstypesjson'
                    });
	            }
	        });
		} else {
		    
		} 
	}
	
	function optionFormatter(id, row, index) {
		var option = '<a onclick="edit(\''+row.id+'\')" data-toggle="modal" href="#" title="Modifier Type Compte [Flag : '+row.flag+' - Libelle : '+row.worded+' - Description : '+row.description+' ]"><span class="glyphicon glyphicon-file"></span></a>&nbsp;';
		option += '&nbsp;<a onclick="del(\''+row.id+'\')" data-toggle="modal" href="#" title="Suprimer Type Compte [Flag : '+row.flag+' - Libelle : '+row.worded+' - Description : '+row.description+' ]"><span class="glyphicon glyphicon-trash"></span></a>';
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
	        <h4 id="widgetTitle" class="widgettitle">Liste Types Comptes</h4>
	    </div>
	    <div id="tableWidget" class="widgetcontent">
	    	<table id="table" class="table table-info table-striped"
			       	data-toggle="table"
			       	data-click-to-select="true"
			       	data-single-select="true"
			       	data-sort-name="flag" data-sort-order="desc"
			       	data-url="${pageContext.request.contextPath}/settings/accountstypesjson"
			       	data-side-pagination="server"
			       	data-pagination="true"
			       	data-page-list="[5, 10, 20, 50, 100, 200]"
			       	data-search="true">
			    <thead>
			        <tr>
			        	<th data-field="flag" data-align="right" data-sortable="true">Flag</th>
			        	<th data-field="worded" data-align="left" data-sortable="true">Libell&eacute;</th>
			        	<th data-field="description" data-align="left" data-sortable="true">Description</th>
			        	<th data-field="id" data-formatter="optionFormatter" data-align="center" data-sortable="false">Options</th>
			        </tr>
			    </thead>
			</table>
	    </div><!--widgetcontent-->
	    
	    <div id="formWidget" class="widgetcontent">
	        
	            <div class="form-group">
	                <label for="flag">Flag</label>
	                <input type="text" class="form-control input-default" id="flag" placeholder="Flag">
	            </div>
	            <div class="form-group">
	                <label for="libelle">Libell&eacute;</label>
	                <input type="text" class="form-control input-default" id="worded" placeholder="Libelle">
	            </div>
	            <div class="form-group">
	                <label for="description">Description</label>
	                <textarea id="description" cols="80" rows="5" class="form-control input-default" style="resize: vertical" placeholder="Description"></textarea>
	            </div>
	            <input type="hidden" class="form-control input-default" id="id" placeholder="id">
	            
	            <p class="stdformbutton">
                    <button class="btn btn-primary" onclick="save()">Enregistrer</button>
                    <button id="reset" type="reset" class="btn btn-default">Annuler</button>
                </p>
	        
	    </div><!-- widgetcontent-->
    </div><!--widgetbox-->
</div><!-- widgetcontent-->