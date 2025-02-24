<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="widget">
	<div class="widgetbox">                        
	    <div class="headtitle">
	        <div class="btn-group">
	            <button data-toggle="dropdown" class="btn dropdown-toggle">Action <span class="caret"></span></button>
				<ul class="dropdown-menu">
	              	<li><a href="#">Listes</a></li>
	              	<li class="divider"></li>
	              	<li><a href="#" data-toggle="modal" data-target="#rhpModal">Nouveau</a></li>
	            </ul>
	        </div>
	        <h4 id="widgetTitle" class="widgettitle">Liste des service</h4>
	    </div>
	    <div id="tableWidget" class="widgetcontent">
	    	<table id="table" class="table table-info table-striped"
			       	data-toggle="table"
			       	data-click-to-select="true"
			       	data-single-select="true"
			       	data-sort-name="flag" data-sort-order="desc"
			       	data-url="${pageContext.request.contextPath}/personnels/listservicejson"
			       	data-side-pagination="server"
			       	data-pagination="true"
			       	data-page-list="[5, 10, 20, 50, 100, 200]"
			       	data-search="true">
			    <thead>
			        <tr>
			        	<th data-field="libelle" data-formatter="directionFormatter" data-align="left" data-sortable="true">Direction</th>
			        	<th data-field="libelle" data-formatter="departementFormatter" data-align="left" data-sortable="true">Departement</th>
			        	<th data-field="libelle" data-formatter="serviceFormatter" data-align="left" data-sortable="true">Service</th>
			        	<th data-field="id" data-formatter="optionFormatter" data-align="center">Options</th>
			        </tr>
			    </thead>
			</table>
	    </div><!--widgetcontent-->
    </div><!--widgetbox-->
</div><!-- widgetcontent-->

<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formAjout" class="form-horizontal" role="form" novalidate="novalidate" ng-controller="formAjoutCtrl">
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Service / Departement / Direction</h4>
				</div>
                <div class="modal-body">
                	<div class="form-group">
                        <label for="exercice" class="col-md-2 control-label">Type</label>
                        <div class="col-md-10">
                            <select id="typeService" name="idTypeService" class="form-control select2" required="required">
								<option value="1" data-libelle="Direction">Direction</option>
								<option value="2" data-libelle="Departement">Departement</option>
								<option value="3" data-libelle="Service" selected="selected">Service</option>
							</select>
                        </div>
                    </div>
                	<div class="form-group direction">
                        <label for="direction" class="col-md-2 control-label">Direction</label>
                        <div class="col-md-10">
                            <select id="direction" name="idDirection" class="form-control select2">
								<c:forEach items="${listeDirection}" var="direction">
									<option value="${direction.id}">${direction.libelle}</option>
								</c:forEach>
							</select>
                        </div>
                    </div>
                	<div class="form-group departement">
                        <label for="departement" class="col-md-2 control-label">Departement</label>
                        <div class="col-md-10">
                            <select id="departement" name="idDepartement" class="form-control select2">
								<option value="0">-- Departement --</option>
							</select>
                        </div>
                    </div>
                	<div class="form-group">
                        <label id="libelleType" for="libelle" class="col-md-2 control-label">Service</label>
                        <div class="col-md-10">
                            <input type="text" id="libelle" name="libelle" ng-model="service.libelle" class="form-control" placeholder="Libell�" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                	<input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="service.id">
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal deleteModal  fade bs-delete-modal-static" role="dialog" data-backdrop="static">
    <div class="modal-dialog ">
        <div class="modal-content">
	        <form id="formDelete" ng-controller="formDeleteCtrl" action="#" method="post">
	            <div class="modal-header ">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	                <span class="circle bg-danger">
	                    <i class="fa fa-question-circle"></i>
	                    Etes vous s�r de vouloir supprimer ?
	                </span>
	            </div>
	            <div class="modal-body">
	            	<h4 ng-bind="service.info"></h4>
	            </div>
	            <div class="modal-footer">
                	<input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="service.id">
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
        	</div>
        </form>
    </div>
</div>

<script type="text/javascript">
	//AngularJS
	app.controller('formAjoutCtrl', function ($scope) {
	    $scope.pupulateForm = function (service) {
	        $scope.service = service;
	    };
	    $scope.initForm = function () {
	    	$scope.service = {};
	    };
	}).controller('formDeleteCtrl', function ($scope) {
	    $scope.pupulateForm = function (service) {
	    	$scope.service = service;
	    };
	
	});
	//End AngularJs


	var actionUrl = "/personnels/enregisterservice";
	var actionDeleteUrl = "/personnels/supprimerservice";
	var action = "ajout";
	var $table;
	jQuery(document).ready(function($){
		$table = $('#table');
		$( ".select2" ).select2();
		
		$("#direction").change(function(){
			loadDepartement(this.value);
		});
		
		$("#typeService").change(function(){
			$("#libelleType").html($("#typeService :selected").data("libelle"));
			var typeService = parseInt(this.value);
			switchService(typeService);
		});
		
		//Fermeture du modal
		$('#rhpModal').on('hidden.bs.modal', function () {
			switchService(0);
			var $scope = angular.element(document.getElementById("formAjout")).scope();
			$scope.$apply(function () {
	            $scope.initForm();
	        });
			//$("#id").val(""); //Initialisation de l'id
		});
		
		//Envoi des donnees
		$("#formAjout").submit(function(e){
			e.preventDefault();
            var formData = $(this).serialize();
            $.ajax({
                type: "POST",
                url: baseUrl + actionUrl,
                cache: false,
                data: formData,
                success: function (reponse) {
                    if (reponse.result == "succes") {
                    	if(action == "ajout"){
                    		//Update combo direction
                    		jQuery("#direction").append('<option value="'+reponse.row.id+'">'+reponse.row.libelle+'</option>');
                    		jQuery("#direction").select2().trigger('change');
                    		//Rechargement de la liste (le tableau)
                    		$table.bootstrapTable('refresh');
                    		//$("#formAjout")[0].reset(); //Initialisation du formulaire
                            $("#rhpModal").modal('hide');
                    	}
                    	else{
                    		//TODO Update combo direction
                    		
                    		//MAJ de la ligne modifi�e
                    		$table.bootstrapTable('updateRow', {
                                index: $table.find('tr.selected').data('index'),
                                row: reponse.data
                            });
                    	}
                    }
                    else if(reponse.result == "erreur_champ_obligatoire"){
                    	alert("Saisie invalide");
                    }
                },
                error: function () {
                	$("#rhpModal .modal-body div.alert").alert();
                	$("#rhpModal .modal-body .alert h4").html("Erreur survenue");
                	$("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
                	$("#rhpModal .modal-footer span").removeClass('loader');
                },
                beforeSend: function () {
                    $("#formAjout").attr("disabled", true);
                    $("#rhpModal .modal-footer span").addClass('loader');
                },
                complete: function () {
                    $("#formAjout").removeAttr("disabled");
                    $("#rhpModal .modal-footer span").removeClass('loader');
                }
            });
		});
		
		$("#formDelete").submit(function(e){
			e.preventDefault();
	        var formData = $(this).serialize();
	        var idSup = [];
	      	//Le formulaire formDelete doit avoir un seul champ input:text
	        idSup.push(parseInt($("#formDelete :text").val()));
	        
	        $.ajax({
	            type: "POST",
	            url: baseUrl + actionDeleteUrl,
	            cache: false,
	            data: formData,
	            success: function (reponse) {
	                if (reponse.result == true) {
                        $table.bootstrapTable('remove', {
                            field: 'id',
                            values: idSup
                        });
                        $(".deleteModal").modal('hide');
	                }
	                else if(reponse.result == "erreur_champ_obligatoire"){
	                	alert("Saisie invalide");
	                }
	            },
	            error: function (err) {
	            	$(".deleteModal .modal-body div.alert").alert();
	            	$(".deleteModal .modal-body .alert h4").html("Erreur survenue");
	            	$(".deleteModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
	            	$(".deleteModal .modal-footer span").removeClass('loader');
	            },
	            beforeSend: function () {
	                $("#formDelete").attr("disabled", true);
	                $(".deleteModal .modal-footer span").addClass('loader');
	            },
	            complete: function () {
	                $("#formDelete").removeAttr("disabled");
	                $(".deleteModal .modal-footer span").removeClass('loader');
	            }
	        });
		});
	});
	
	function loadDepartement(idDirection){
		jQuery.ajax({
            type: "GET",
            url: baseUrl + "/personnels/listdepartementbydirection",
            cache: false,
            data: {id: idDirection},
            success: function (data) {
            	jQuery.each(data, function(index, departement){
            		jQuery("#departement").html('<option value="'+departement.id+'">'+departement.libelle+'</option>');
            	});
            	jQuery("#departement").select2().trigger('change');
            },
            error: function () {
            	/*$("#rhpModal .modal-body div.alert").alert();
            	$("#rhpModal .modal-body .alert h4").html("Erreur survenue");
            	$("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
            	$("#rhpModal .modal-footer span").removeClass('loader');*/
            },
            beforeSend: function () {
            	jQuery("#departement").html('<option value="0">Aucun r�sultat</option>');
            	jQuery("#departement").select2().trigger('change');
                /*$("#formAjout").attr("disabled", true);
                $("#rhpModal .modal-footer span").addClass('loader');*/
            },
            complete: function () {
                /*$("#formAjout").removeAttr("disabled");
                $("#rhpModal .modal-footer span").removeClass('loader');*/
            }
        });
	}
	
	function switchService(typeService){
		switch(typeService){
		case 1 : //Direction
			jQuery("#typeService").select2("val",typeService);
			jQuery(".direction, .departement").hide();
			break;
		case 2 : //Departement
			jQuery("#typeService").select2("val",typeService);
			jQuery(".direction").show();
			jQuery(".departement").hide();
			break;
		default : //Service
			jQuery("#typeService").select2("val",3);
			jQuery(".departement, .direction").show();
			break;
		}
	}

	//Functions 
	function directionFormatter(libelle, row, index) {
		var direction = "";
		switch(row.typeService.id){
		case 1 : //Direction
			direction = libelle;
			break;
		case 2 : //Departement
			if(row.serviceParent){
				direction = row.serviceParent.libelle;
			}
			break;
		case 3 : //Service
			if(row.serviceParent){
				if(row.serviceParent.serviceParent){
					direction = row.serviceParent.serviceParent.libelle;
				}
			}
			break;
		}
		return direction;
	}
	
	function departementFormatter(libelle, row, index) {
		var departement = "";
		switch(row.typeService.id){
		case 2 : //Departement
			departement = libelle;
			break;
		case 3 : //Service
			if(row.serviceParent){
				departement = row.serviceParent.libelle;
			}
			break;
		default:
			departement = "";
			break;
		}
		return departement;
	}
	
	function serviceFormatter(libelle, row, index) {
		if(row.typeService.id == 3){
			return libelle;
		}
		return "";
	}
	
	function optionFormatter(id, row, index) {
		var option = '<a onclick="edit('+row.id+')" data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier fonction [LIBELLE : '+row.libelle+' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
		option += '&nbsp;<a onclick="del('+row.id+')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer fonction [LIBELLE : '+row.libelle+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';
		
        return option;
    }
	
	function edit(idService){
		var $scope = angular.element(document.getElementById("formAjout")).scope();
        
		var rows = $table.bootstrapTable('getData');
		var service = _.findWhere(rows, {id: idService});
		switchService(service.typeService.id);
		$scope.$apply(function () {
            $scope.pupulateForm(service);
        });
	}
	
	function del(idService){
		var $scope = angular.element(document.getElementById("formDelete")).scope();
        
		var rows = $table.bootstrapTable('getData');
		var service = _.findWhere(rows, {id: idService});
		service.info = service.libelle;
		$scope.$apply(function () {
            $scope.pupulateForm(service);
        });
	}
</script>
