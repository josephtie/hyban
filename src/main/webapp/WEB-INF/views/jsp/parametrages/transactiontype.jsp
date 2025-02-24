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
			jQuery('#widgetTitle').html('Liste Types Transactions');
		} else if(view == 'new'){
			jQuery('#flag').attr('readOnly', false);
    		loadDataToForm(null, null, null, null);
			jQuery('#widgetTitle').html('Nouveau Type Transaction');
			jQuery('#formWidget').show('slow');
			jQuery('#tableWidget').hide('slow');
		} else{
			jQuery('#flag').attr('readOnly', true);
			jQuery('#widgetTitle').html('Modifier Type Transaction');
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
            url: contextPath+"/settings/savetransactiontype",
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
                    url: contextPath + '/settings/transactionstypesjson'
                });
            }
        });
	}
	
	function edit(id){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/settings/loadtransactiontype",
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
	            url: contextPath+'/settings/deletetransactiontype',
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
                        url: contextPath + '/settings/transactionstypesjson'
                    });
	            }
	        });
		} else {
		    
		} 
	}
	
	function optionFormatter(id, row, index) {
		var option = '<a onclick="edit(\''+row.id+'\')" data-toggle="modal" href="#" title="Modifier Type Transaction [Flag : '+row.flag+' - Libelle : '+row.worded+' - Description : '+row.description+' ]"><span class="glyphicon glyphicon-file"></span></a>&nbsp;';
		option += '&nbsp;<a onclick="del(\''+row.id+'\')" data-toggle="modal" href="#" title="Suprimer Type Transaction [Flag : '+row.flag+' - Libelle : '+row.worded+' - Description : '+row.description+' ]"><span class="glyphicon glyphicon-trash"></span></a>';
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
	        <h4 id="widgetTitle" class="widgettitle">Liste Types Transactions</h4>
	    </div>
	    <div id="tableWidget" class="widgetcontent">
	    	<table id="table" class="table table-info table-striped"
			       	data-toggle="table"
			       	data-click-to-select="true"
			       	data-single-select="true"
			       	data-sort-name="flag" data-sort-order="desc"
			       	data-url="${pageContext.request.contextPath}/settings/transactionstypesjson"
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
	        <div class="row">
       
        <div class="col-lg-8">
              
              <div class="panel panel-default gradient">
              
                            <div style="margin-bottom: 20px;">
                                <ul id="myTab" class="nav nav-tabs pattern">
                                    <li class="active" id="degriser" ><a href="#home" data-toggle="tab">Gestion des periodes automatique</a></li>
                                    <c:if test="${periodePaieEncour == null}">
                                    	<li class="" id="griser"><a href="#profile" data-toggle="tab">Activation de la periode</a></li>
                                    </c:if>
                                </ul>

                            <div class="tab-content">
                            
                                <div class="tab-pane fade active in" id="home">
                                    <div class="panel-heading">
                                    <h4 class="clearfix">
                                       <span class="left">Périodes automatiques</span>
                                    </h4>
                               </div>
                                
                            <div class="panel panel-default">

                                <div class="panel-body"> 
                                        
                                     <form:form class="form-horizontal" method="post" commandName="printDTO" id="lop" action="${pageContext.request.contextPath}/rhp/periodepaie/setting/postgenererperiode">
                                       
                                      <div class="form-group">
                                            <label class="col-lg-3 control-label" for="moisFermer">Année / Mois</label>
                                            <div class="col-lg-4">
                                                <form:select id="annee4" path="val1" data-placeholder="Choisir l'annee..."  class="nostyle form-control" >
                                                    <form:option value="-1" label="Choisir l'annee..." />  
                                                    <form:options items="${listAnnee}" itemValue="id" itemLabel="annee" />
                                                </form:select>
                                            </div>
                                            <div class="col-lg-1" style="margin-left: -30px; margin-top: 10px;">
                                              <a href="#" data-toggle="modal" data-target="#myModalAnneeForm"><span class="minia-icon-file-add"></span></a>
                                            </div>
                                            
                                            <div class="col-lg-4" style="margin-left: -20px;">
                                                <form:select id="seler" path="val5" data-placeholder="Choisir le mois..."  class="nostyle form-control" >
                                                    <form:option value="-1" label="Choisir le mois..." />  
                                                    <form:options items="${listMois}" itemValue="id" itemLabel="mois" />
                                                </form:select>
                                            </div>
                                        </div><!-- End .form-group  -->  
                                    <div class="form-group" >
			                         <label class="col-lg-3 control-label" for="radios">Periode personnalisée</label>
			                         <div class="col-lg-4" id="check">
			                             <label class="radio-inline">
			                                 <form:radiobutton path="s3"  id="radioSelOui" name="group1" value="Oui" />Oui 
			                             </label>
			                               <label class="radio-inline">
			                                 <form:radiobutton path="s3"  id="radioSelNon" name="group1" value="Non"/>Non
			                             </label>
			                         </div>
			                          </div>
                                       <div class="form-group" id="cacher">
                                            <label class="col-lg-3 control-label" >Saisir le jour de debut</label>
                                            <div class="col-lg-4">
                                                <form:input id="gr" path="val4"  type="text"  class="form-control " placeholder="Le jour de debut"/>
                                                 <span class="errorY" style="color:#B94A48;font-family:sans-serif;"></span>
                                                <span class="help-block blue"></span> 
                                            </div>
                                        </div><!-- End .form-group  --> 
                                        
                                       <div class="form-group">
                                            <label class="col-lg-3 control-label" for="periode">Période</label>
                                            <div class="col-lg-8">
                                                 <form:select id="periode" path="val3" type="text" class="nostyle form-control" placeholder="veuillez selectionner le nombre d'année..." >
                                                    <form:option value="-1" label="veuillez selectionner le nombre d'année..." /> 
                                                    <form:option value="5" label="Sur 5 ans" />  
                                                    <form:option value="10" label="Sur 10 ans" /> 
                                                    <form:option value="15" label="Sur 15 ans" /> 
                                                    <form:option value="20" label="Sur 20 ans" />  
                                                    <form:option value="25" label="Sur 25 ans" />
                                                    <form:option value="30" label="Sur 30 ans" />
                                                </form:select>
                                            <label class="ok" id="select_ok" style="color:#B94A48;"></label> 
                                            </div>
                                        </div><!-- End .form-group -->   
                                        
                                         <div class="form-group">
                                            <div class="col-lg-offset-3 col-lg-8">
                                                <button type="submit" id="auto" class="btn btn-info">Valider</button>
                                                <button type="button" class="btn btn-default" onclick="history.back(); return false;">Annuler</button>
                                            </div>
                                        </div><!-- End .form-group  -->                   
		                   
                                   </form:form>
                                
                               </div>

                           </div><!-- End .panel -->
                               
                        </div>
		                
		               <c:if test="${periodePaieEncour == null}">
                       <div class="tab-pane fade" id="profile">
                           <div class="panel panel-default gradient">
                             <div class="panel-heading">
                                    <h4 class="clearfix">
                                        <span class="left icon16 icomoon-icon-grid-view"></span>
                                        <span class="left">Activation de la période</span>
                                    </h4>
                             </div>
			
			           <div class="row">

                           <div class="col-lg-12">

                            <div class="panel panel-default">

                                <div class="panel-body">
                                   
                                    <form:form class="form-horizontal" method="post" commandName="periodepaieAction" action="${pageContext.request.contextPath}/action/periodepaie/setting/postperiodepaieActive">
                                                                               
                                        <div class="form-group">
                                            <label class="col-lg-3 control-label" for="mois">Periode Paie</label>
                                            <div class="col-lg-7">
                                                <form:select id="selegop" path="id" type="text" class="nostyle select2 form-control" placeholder="Choisir la periode a activer..." >
                                                    <form:option value="-1" label="Choisir la periode a activer..." /> 
                                                    <form:options items="${listPeriodePaie}" itemValue="id" itemLabel="affiche" />
                                                </form:select>
                                            <label class="error" id="select_error" style="color:#B94A48;"></label> 
                                            </div>
                                        </div><!-- End .form-group  -->                     
                                    
                               
                                        <div class="form-group">
                                            <div class="col-lg-offset-3 col-lg-9">
                                                <button type="submit" id="subop" class="btn btn-info">Valider</button>
                                                <button type="button" class="btn btn-default" onclick="history.back(); return false;">Annuler</button>
                                            </div>
                                        </div><!-- End .form-group  -->                   

                                    </form:form>
                                 
                                      </div>

	                                  </div><!-- End .panel -->
	
	                              </div><!-- End .span6 -->
	
	                              </div><!-- End .row -->
	
	                           </div><!-- End .panel -->

                               </div><!-- End .profile -->
                               </c:if>
                               
                           </div><!-- End .content -->
                           
                       </div>

                   </div><!-- End .span6 -->  

                 </div><!-- End panel panel-default gradient --> 
               
               </div><!-- End .row -->
	          <!--   <div class="form-group">
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
                </p> -->
	        
	    </div><!-- widgetcontent-->
    </div><!--widgetbox-->
</div><!-- widgetcontent-->