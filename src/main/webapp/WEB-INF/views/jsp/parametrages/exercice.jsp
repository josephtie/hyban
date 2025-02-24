<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
	<div class="col-md-12">
		<div class="panel panel-warning">
			<div class="panel-heading">
				<div class="panel-title-box">
					<h3>Liste des Exercices</h3>
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
	    	<table id="table" class="table table-info table-striped"
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
			        	<th data-field="cloture" data-align="left">Cloturer</th>
			        	<!-- <th data-field="id" data-formatter="optionFormatter" data-align="center">Options</th> -->
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
            <form id="formAjout" class="form-horizontal" role="form" novalidate="novalidate" ng-controller="formAjoutCtrl">
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Emploi</h4>
				</div>
                <div class="modal-body">
                	<div class="form-group">
                        <label for="libelle" class="col-md-2 control-label">Ann�e</label>
                        <div class="col-md-10">
                            <input type="text" id="libelle" name="libelle" ng-model="annee.annee" class="form-control" placeholder="Annee" />
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
                    <!-- <div class="alert alert-block">
                          <button data-dismiss="alert" class="close" type="button">&times;</button>
                          <h4>Statut</h4>
                          <p style="margin: 8px 0"></p>
                    </div>alert -->
                </div>
                <div class="modal-footer">
                	<input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="fonction.id">
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
		loadBranchs();
		
		$table = $('#table');
		//Fermeture du modal
		$('#rhpModal').on('hidden.bs.modal', function () {
			$("#id").val(""); //Initialisation de l'id
		});
		
		//Envoi des donnees
		$("#formAjout").submit(function(e){
			e.preventDefault();
			
            var formData = $(this).serialize();
            console.log("form", formData);
            $.ajax({
                type: "POST",
                url: contextPath+"/parametrages/saveperiodepaie",
                cache: false,
                data: formData,
                success: function (reponse) {
                       if (reponse.result == "success") {
                    	
                    		alert('Operation effectu�e avec succ�s');
                    		//Rechargement de la liste (le tableau)
                    		//jQuery(table.bootstrapTable('refresh'));
                    		jQuery("#formAjout")[0].reset(); //Initialisation du formulaire
                    		jQuery("#rhpModal").modal('hide');
                          //  jQuery('#rhpModal').modal('hide');
                    	}
                    	/* else{
                    		//MAJ de la ligne modifi�e
                    		$table.bootstrapTable('updateRow', {
                                index: $table.find('tr.selected').data('index'),
                                row: reponse.data
                            });
                    	} */
                 
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
                	jQuery("#formAjout").removeAttr("disabled");
                    jQuery("#rhpModal .modal-footer span").removeClass('loader');
                    jQuery ('#table'). bootstrapTable ('refresh', {  url: contextPath +'/parametrages/Exerciceactif'
                       });
                }
            });
		});
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
	
	function optionFormatter(id, row, index) {
	/* 	'&nbsp;<a onclick="del(\''+row.id+'\')" data-toggle="modal" href="#" title="Suprimer Conseiller Client [NOM : '+row.id+' ]"> <span class="glyphicon glyphicon-trash"></span></a>&nbsp;&nbsp;'; */
		var option ='' 

		
		
        return option;
    }
</script>
