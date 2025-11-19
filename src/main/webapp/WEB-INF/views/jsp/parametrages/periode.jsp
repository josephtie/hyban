<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
	<div class="col-md-12">
		<div class="panel panel-warning">
			<div class="panel-heading">
				<div class="panel-title-box">
					<h3>Liste des Periodes</h3>
					<span>de paie</span>
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
	    <div id="tableWidget" class="widgetcontent">
	    	<table id="table" class="table table-info table-striped"
			       	data-toggle="table"
			       	data-click-to-select="true"
			       	data-single-select="true"
			       
			       	data-url="${pageContext.request.contextPath}/parametrages/periodactif"
			       	data-side-pagination="server"
			       	data-pagination="true"
			       	data-page-list="[5, 10, 20, 50, 100, 200]"
			       	data-search="true">
			    <thead>
			        <tr>
			        	 <th data-field="mois" data-formatter="periodeFormatter" data-align="left" data-sortable="true">Periode</th> 
			        	<th data-field="ddeb" data-align="left" data-sortable="true">Date d'ouverture</th>
			        	<th data-field="dfin" data-align="left" data-sortable="true">Date de fin</th>
			        	<th data-field="id" data-formatter="optionFormatter" data-align="center">Options</th>
			        </tr>
			    </thead>
			</table>
		</div>

						</div>
					</div>
				</div>
			</div>
		</div>

<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formCloturer" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">G&eacute;n&eacute;ration du bulletin de paie</h4>
				</div>
                <div class="modal-body">
                	<h3>Vous etes sur le point de cloturer  la p&eacute;riode de paie <span id="periodePaie" title="" class="danger"></span>Les bulletins du personnel ne seront plus modifiable pour ladite periode.</h3>
                	<br/>
                	<h4>En cliquant sur le bouton "Valider", le proccessus sera lanc&eacute;.</h4>
                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                	<input type="hidden"  id="idPeriode" name="id">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%-- <div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formAjout" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">P&eacute;riode</h4>
				</div>
                <div class="modal-body">
                	<div class="form-group">
                        <label for="annee" class="col-md-2 control-label">Exercice</label>
                        <div class="col-md-10">
                            <select id="annee" name="exercice" class="form-control select2">
								<optgroup label="Exercice">
								 
								<option value="1">2016</option> -->
							</select>
                        </div>
                    </div>
                	<div class="form-group">
                        <label for="mois" class="col-md-2 control-label">Mois</label>
                        <div class="col-md-10">
                            <select id="mois" name="mois" class="form-control select2">
								<optgroup label="Mois">
							</select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="button" class="btn btn-success" onclick="save()">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>
 --%>
<script type="text/javascript">
var contextPath = "<%=request.getContextPath() %>";
var $table;
	jQuery(document).ready(function($){
		$( ".select2" ).select2();
		$table =jQuery('#table');
		loadBranchs();
		periode='${activeMois}';
		periodeID='${activeMoisId}';
		jQuery('#periodePaie').html(periode);
		jQuery('#idPeriode').val(periodeID);
	//	loadAnnee();
	});
	function loadBranchs(){
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
	}
	
	function loadAnnee(){

     }
	
	function save(){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/parametrages/saveperiodepaie",
            cache: false,
            data: {
            	idyear: jQuery('#annee').val(),
            	idmonth: jQuery('#mois').val()
			},
            beforeSend: function () {
        		//jQuery('#idButton').hide();
        		//jQuery('#idWait').show();
            },
            success: function (response) {
            	if (response != null) {
            	//	loadDataToForm(null, null, null, null, null, null, null,null,null);
					alert('Enregistr� avec succ�s');
					//widgetView('list');
					jQuery('#rhpModal').hide();
					
				} else {
					//alert('L\'utilisateur � �t� enregistr� avec succ�s');
					alert('Impossible d\'effectuer cet enregistrement');
				}
            },
            error: function () {
                
            },
            complete: function () {
        		jQuery('#table').bootstrapTable('refresh', {
                    url: contextPath + '/parametrages/periodactif'
                });
        		jQuery('#tableWidget').show();
            }
        });
	}
	function optionFormatter(id, row, index) {

		var option ='&nbsp;<a onclick="del(\''+row.id+'\')" data-toggle="modal" data-target="#rhpModal"  title="Cl�turer periode de paie [PERIODE : '+row.id+' ]"> <span class="glyphicon glyphicon-file"></span></a>&nbsp;&nbsp;';
		
        return option;
    }
	
	function periodeFormatter(mois) {
		console.log(mois);
	if(mois==null)
	{ 
		return;
	}
	return mois.mois;
       
    }
	jQuery("#formCloturer").submit(function(e){
	//	alert('bonjour');
		e.preventDefault();
		jQuery.ajax({
	        type: "POST",
	        url: contextPath + "/parametrages/cloturePeriode",
	        cache: false,
	        data: {id: '${activeMoisId}'},
	        success: function (response) {
	            if (response.result == "success") {
	                alert('Operation effectu�e avec succ�s');
	            	jQuery("#rhpModal").modal('hide');
	            }else{
	            	alert('error:pas de bulletin pour cette periode,veuillez generer des bulletins pour cette periode');
	            	jQuery("#rhpModal").modal('hide');
	            }
	        },
	        error: function () {
	        	 jQuery("#rhpModal .modal-body div.alert").alert();
	        	jQuery("#rhpModal .modal-body .alert h4").html("Erreur survenue");
	        	jQuery("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
	        	jQuery("#rhpModal .modal-footer span").removeClass('loader');
	        },
	        beforeSend: function () {
	        	 jQuery("#formAjout").attr("disabled", true);
	        	jQuery("#rhpModal .modal-footer span").addClass('loader');
	        },
	        complete: function () {
	        	/* jQuery("#formGenerer").removeAttr("disabled");
	        	jQuery("#rhpModal .modal-footer span").removeClass('loader'); */
	        	 jQuery ('#table'). bootstrapTable ('refresh', {  url: contextPath +'/parametrages/periodactif' });
	        }
	    });
	});
</script>
