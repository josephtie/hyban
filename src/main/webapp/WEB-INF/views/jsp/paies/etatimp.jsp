<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row" >
    <div class="col-md-12">
        <div class="panel panel-warning">
            <div class="panel-heading">
                <div class="panel-title-box">
                    <h3>Cotisation fiscale </h3>
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
                                            <li><a href="#" data-toggle="modal" data-target="#rhpModal3">FDFP</a></li>
                                            <li><a href="#" data-toggle="modal" data-target="#rhpModal4">ITS</a></li>
                                    </ul>
                                </div>

                            </div>
<%--<div class="widget">--%>
	<%--<div class="widgetbox">                        --%>
	    <%--<div class="headtitle">--%>
	        <%--<div class="btn-group">--%>
	            <%--<button data-toggle="dropdown" class="btn dropdown-toggle">Action <span class="caret"></span></button>--%>
				<%--<ul class="dropdown-menu">	              --%>
	              <%----%>
	              	<%--<li><a href="#" data-toggle="modal" data-target="#rhpModal3">FDFP</a></li>--%>
	              	<%--<li class="divider"></li>--%>
	              	<%--<li><a href="#" data-toggle="modal" data-target="#rhpModal4">ITS</a></li>--%>
	           <%----%>
	              	<%----%>
	            <%--</ul>--%>
	        <%--</div>--%>
	        <%--<h4 id="widgetTitle" class="widgettitle">Cotisations fiscales</h4>--%>
	    <%--</div>--%>
	    <div id="formWidget" class="widgetcontent">
	    
	    	<%-- <table id="table" class="table table-info table-striped"
			       	data-toggle="table"
			       	data-click-to-select="true"
			       	data-single-select="true"
			       	data-sort-name="flag" data-sort-order="desc"
			       	data-url="${pageContext.request.contextPath}/parametrages/Exerciceactif"
			       	data-side-pagination="server"
			       	data-pagination="true"
			       	data-page-list="[5, 10, 20, 50, 100, 200]"
			       	data-search="true">
			    <thead>
			        <tr>
			        	<th data-field="annee" data-align="left" data-sortable="true">Exercice</th>
			        	<th data-field="cloture" data-align="left">Cl�turer</th>
			        	<!-- <th data-field="id" data-formatter="optionFormatter" data-align="center">Options</th> -->
			        </tr>
			    </thead>
			</table> --%>
	    </div><!--widgetcontent-->
    </div><!--widgetbox-->
</div><!-- widgetcontent-->
<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formAjout" class="form-horizontal" role="form" novalidate="novalidate" >
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Cotisation mensuelle</h4>
				</div>
                <div class="modal-body">
                	
                	<div class="form-group">
                        <label for="mois" class="col-md-4 control-label">Mois </label>
                        <div class="col-md-8">
                           <select id="periode" name="periode" class="form-control select2" required="required">
                           </select>
                        </div>
                    </div>
                 
                	<div class="form-group">
                        <label for="statut" class="col-md-4 control-label">Exporter vers..</label>
                        <div class="col-md-8">
                            <select id="print" name="print" class="form-control select2">
								<option value="p">PDF</option>
								<option value="e">EXCEL</option>
							</select>
                        </div>
                    </div>
                    	<div class="form-group" style="margin-top: -5px;">
                               				<div class="col-lg-6  col-lg-offset-3">
                               					<div class="col-lg-3">
                                     				<input id="tPf" name="tPf" type="number" class="form-control" value="5.75" placeholder="5.75%" style="width: 60px;" />
                                 				</div>
                               					<label class="col-lg-9" for="tPf" style="margin-top: 10px;">% Prestation familiale</label>
                                 				
                               				</div> 
                               			</div><!-- End .form-group  -->  
                               			
                               			<div class="form-group" style="margin-top: -5px;">
                               				<div class="col-lg-6  col-lg-offset-3">
                               						<div class="col-lg-3">
                                     					<input id="tAt" name="tAt" type="number" class="form-control" value="" placeholder="3.0%" style="width: 60px;"/>
                                 					</div>
                                 					<label class="col-lg-9" for="tAt" style="margin-top: 10px;">% Accidents du travail</label>
                               				</div> 
                               			</div><!-- End .form-group  --> 
                               				
                               			<div class="form-group" style="margin-top: -5px;">
                               				<div class="col-lg-6 col-lg-offset-3">
                               					<div class="col-lg-3">
                                     				<input id="tCnps" name="tCnps" type="number" class="form-control" value="" placeholder="8%" style="width: 60px;"/>
                                 				</div>
                                 				<label class="col-lg-9" for="tCnps" style="margin-top: 10px;">% Regime de retraite</label>
                                 				
                               				</div>
                               				
                                        </div><!-- End .form-group  --> 
                    <!-- <div class="alert alert-block">
                          <button data-dismiss="alert" class="close" type="button">&times;</button>
                          <h4>Statut</h4>
                          <p style="margin: 8px 0"></p>
                    </div>alert -->
                </div>
                <div class="modal-footer">
                	<!-- <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="fonction.id"> -->
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="rhpModal1" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formAjout1" class="form-horizontal" role="form" novalidate="novalidate" >
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Cotisation trimestrielle</h4>
				</div>
                <div class="modal-body">
                	
                	<div class="form-group">
                        <label for="mois" class="col-md-4 control-label">Mois </label>
                        <div class="col-md-8">
                           <select id="periode1" name="periode1" class="form-control select2" required="required">
                           </select>
                        </div>
                    </div>
                 
                	<div class="form-group">
                        <label for="statut" class="col-md-4 control-label">Exporter vers..</label>
                        <div class="col-md-8">
                            <select id="print1" name="print1" class="form-control select2">
								<option value="p">PDF</option>
								<option value="e">EXCEL</option>
							</select>
                        </div>
                    </div>
                    	<div class="form-group" style="margin-top: -5px;">
                               				<div class="col-lg-6  col-lg-offset-3">
                               					<div class="col-lg-3">
                                     				<input id="tPf1" name="tPf1" type="number" class="form-control" value="5.75" placeholder="5.75%" style="width: 60px;" />
                                 				</div>
                               					<label class="col-lg-9" for="tPf" style="margin-top: 10px;">% Prestation familiale</label>
                                 				
                               				</div> 
                               			</div><!-- End .form-group  -->  
                               			
                               			<div class="form-group" style="margin-top: -5px;">
                               				<div class="col-lg-6  col-lg-offset-3">
                               						<div class="col-lg-3">
                                     					<input id="tAt1" name="tAt1" type="number" class="form-control" value="" placeholder="3.0%" style="width: 60px;"/>
                                 					</div>
                                 					<label class="col-lg-9" for="tAt" style="margin-top: 10px;">% Accidents du travail</label>
                               				</div> 
                               			</div><!-- End .form-group  --> 
                               				
                               			<div class="form-group" style="margin-top: -5px;">
                               				<div class="col-lg-6 col-lg-offset-3">
                               					<div class="col-lg-3">
                                     				<input id="tCnps1" name="tCnps1" type="number" class="form-control" value="" placeholder="8%" style="width: 60px;"/>
                                 				</div>
                                 				<label class="col-lg-9" for="tCnps" style="margin-top: 10px;">% Regime de retraite</label>
                                 				
                               				</div>
                               				
                                        </div><!-- End .form-group  --> 
                    <!-- <div class="alert alert-block">
                          <button data-dismiss="alert" class="close" type="button">&times;</button>
                          <h4>Statut</h4>
                          <p style="margin: 8px 0"></p>
                    </div>alert -->
                </div>
                <div class="modal-footer">
                	<!-- <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="fonction.id"> -->
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="rhpModal2" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formAjout2" class="form-horizontal" role="form" novalidate="novalidate" >
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Cotisation Annuelle</h4>
				</div>
                <div class="modal-body">
                	
                	<div class="form-group">
                        <label for="mois" class="col-md-4 control-label">Mois </label>
                        <div class="col-md-8">
                           <select id="annee" name="annee" class="form-control select2" required="required">
                           </select>
                        </div>
                    </div>
             
                </div>
                <div class="modal-footer">
                	<!-- <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="fonction.id"> -->
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="rhpModal3" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formAjout3" class="form-horizontal" role="form" novalidate="novalidate" >
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Cotisation Annuelle</h4>
				</div>
                <div class="modal-body">
                	
                	<div class="form-group">
                        <label for="mois" class="col-md-4 control-label">Mois </label>
                        <div class="col-md-8">
                           <select id="periode3" name="periode3" class="form-control select2" required="required">
                           </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="statut" class="col-md-4 control-label">Exporter vers..</label>
                        <div class="col-md-8">
                            <select id="print2" name="print2" class="form-control select2">
								<option value="p">PDF</option>
								<option value="e">EXCEL</option>
							</select>
                        </div>
                    </div>
             
                </div>
                <div class="modal-footer">
                	<!-- <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="fonction.id"> -->
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="rhpModal4" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formAjout4" class="form-horizontal" role="form" novalidate="novalidate" >
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel"></h4>
				</div>
                <div class="modal-body">
                	
                	<div class="form-group">
                        <label for="mois" class="col-md-4 control-label">Mois </label>
                        <div class="col-md-8">
                           <select id="periode4" name="periode4" class="form-control select2" required="required">
                           </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="statut" class="col-md-4 control-label">Exporter vers..</label>
                        <div class="col-md-8">
                            <select id="print4" name="print4" class="form-control select2">
								<option value="p">PDF</option>
								<option value="e">EXCEL</option>
							</select>
                        </div>
                    </div>
             
                </div>
                <div class="modal-footer">
                	<!-- <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="fonction.id"> -->
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="rhpModal5" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formAjout5" class="form-horizontal" role="form" novalidate="novalidate" >
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Etat Annuelle 301</h4>
				</div>
                <div class="modal-body">
                	
                	<div class="form-group">
                        <label for="mois" class="col-md-4 control-label">Ann�e </label>
                        <div class="col-md-8">
                           <select id="annee1" name="annee1" class="form-control select2" required="required">
                           </select>
                        </div>
                    </div>
             
                </div>
                <div class="modal-footer">
                	<!-- <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="fonction.id"> -->
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

	jQuery(document).ready(function($){
		$( ".select2" ).select2();
		//loadBranchs();
		chargerPeriodePaie();
		chargerAnneePaie();
		$table = $('#table');
		//Fermeture du modal
		$('#rhpModal').on('hidden.bs.modal', function () {
			$("#id").val(""); //Initialisation de l'id
		});
		
		//Envoi des donnees
		$("#formAjout").submit(function(e){
			e.preventDefault();
			
			 window.open(baseUrl + "/paie/JRCotisationMensuelEmployeur?periode="+jQuery('#periode').val()+"&tPf="+ jQuery('#tPf').val()+"&tAt="+ jQuery('#tAt').val()+"&tCnps="+ jQuery('#tCnps').val()+"&print="+ jQuery('#print').val());
      
		});
		
		
		$("#formAjout1").submit(function(e){
			e.preventDefault();
				
			 window.open(baseUrl  + "/paie/jrDisaTrimestriel?periode1="+jQuery('#periode1').val()+"&tPf1="+ jQuery('#tPf1').val()+"&tAt1="+ jQuery('#tAt1').val()+"&tCnps1="+ jQuery('#tCnps1').val()+"&print1="+ jQuery('#print1').val());
	      
			});
		
		$("#formAjout2").submit(function(e){
				e.preventDefault();
				
				 window.open(baseUrl + + "/paie/jrDisa?annee="+jQuery('#annee').val());
	      
			});
		
		$("#formAjout3").submit(function(e){
			e.preventDefault();
			 window.open(baseUrl + "/paie/jrFdfp?periode="+jQuery('#periode3').val()+"&print="+ jQuery('#print2').val());	
				
	      
			});
		$("#formAjout4").submit(function(e){
			e.preventDefault();
				
			 window.open(baseUrl + "/paie/jrIts?periode="+jQuery('#periode4').val()+"&print="+ jQuery('#print4').val());
	      
			});
		
		
		$("#formAjout5").submit(function(e){
			e.preventDefault();
			
			 location.href = baseUrl + "/paie/JrEtat?annee="+jQuery('#annee1').val();
      
		});
	});
	/* function loadBranchs(){
		jQuery.ajax({
            type: "GET",
            url: contextPath+"/parametrages/listmois",
            cache: false,
            success: function (response) {
            	console.log(response);
            	if (response != null) {
					tabledata = '';
	        		for(i =0 ; i < response.total ; i++){
	        			tabledata += '<option value="'+response.rows[i].id+'" >'+response.rows[i].mois+'</option>';
	        		}
	        		//tabledata += "";
		    	  	jQuery('#mois').html(tabledata);
		    	 // 	jQuery("#mois").trigger("liszt:updated");
		    	 // 	jQuery('#mois option:selected').val()
				} else {
					alert('Failure! An error has occurred!');
				}
            },
            error: function () {
                
            },
        });
	} */
	function chargerPeriodePaie(){
		jQuery.ajax({
	        type: "POST",
	        url: baseUrl + "/parametrages/periodeall",
	        cache: false,
	        success: function (response) {
	        	if (response != null) {
	        		tabledata = '<option value="0" data-libelle="CHOISIR PERIODE PAIE" >CHOISIR PERIODE PAIE</option>';
	        		for (i = 0; i < response.length; i++) {
						tabledata += '<option value="'+response[i].id+'" data-libelle="'+response[i].mois.mois + ' ' + response[i].annee.annee+'" >' + response[i].mois.mois + ' ' + response[i].annee.annee + '</option>';
					}
						jQuery('#periode').html(tabledata);
						jQuery('#periode').select2('val', 0);
						jQuery('#periode1').html(tabledata);
					
						jQuery('#periode1').select2('val', 0);
						jQuery('#periode2').html(tabledata);
						jQuery('#periode2').select2('val', 0);
					
						jQuery('#periode3').html(tabledata);
						jQuery('#periode3').select2('val', 0);
						
						jQuery('#periode4').html(tabledata);
						jQuery('#periode4').select2('val', 0);
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
	
	function chargerAnneePaie(){
		jQuery.ajax({
	        type: "GET",
	        url: baseUrl + "/parametrages/exercicearecuperer",
	        cache: false,
	        success: function (response) {
	        	if (response != null) {
	        		tabledata = '<option value="0" data-libelle="CHOISIR ANNEE DE PAIE" >CHOISIR ANNEE DE PAIE</option>';
	        		for (i = 0; i < response.length; i++) {
					 console.log(response);
						tabledata += '<option value="'+response[i].id+'" data-libelle="'+response[i].annee + '" >'  + response[i].annee + '</option>';
					}
				
					jQuery('#annee').html(tabledata);
					jQuery('#annee').select2('val', 0);
					jQuery('#annee1').html(tabledata);
					jQuery('#annee1').select2('val', 0);
				
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
	function optionFormatter(id, row, index) {
	/* 	'&nbsp;<a onclick="del(\''+row.id+'\')" data-toggle="modal" href="#" title="Suprimer Conseiller Client [NOM : '+row.id+' ]"> <span class="glyphicon glyphicon-trash"></span></a>&nbsp;&nbsp;'; */
		var option ='' 

		
		
        return option;
    }
</script>
