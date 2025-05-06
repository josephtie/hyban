<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
#ui-datepicker-div{
	z-index: 1000000!important;
}
</style>


<div class="row" >
	<div class="col-md-12">
		<div class="panel panel-warning">
			<div class="panel-heading">
				<div class="panel-title-box">
					<h3>Liste des Utilisateurs</h3>
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
				data-sort-name="name"
				data-sort-order="desc" 
				data-url="${pageContext.request.contextPath}/parametrages/listutilisateurjson"
				data-side-pagination="server" 
				data-pagination="true"
				data-page-list="[10, 20, 50, 100, 200]" 
				data-search="false">
				<thead>
					<tr>
						<th data-field="utilisateur" data-formatter="nomCompletFormatter" data-align="left" data-sortable="true">Nom</th>
						<!--<th data-field="utilisateur" data-formatter="dateNaissanceFormatter" data-align="center" data-sortable="true">Date de Naissance</th>
						<th data-field="utilisateur" data-formatter="adresseFormatter" data-align="left" data-sortable="true">Adresse</th>-->
						<th data-field="utilisateur" data-formatter="telephoneFormatter" data-align="center" ata-sortable="true">T&eacute;l&eacute;phone</th>
						<th data-field="utilisateur" data-formatter="emailFormatter" data-align="left" data-sortable="true">Email</th>
						<th data-field="role" data-formatter="roleFormatter" data-align="left" data-sortable="true">Role</th>
						<th data-field="utilisateur" data-formatter="actifFormatter" data-align="center" data-sortable="true">Status</th>
						<th data-field="id" data-formatter="optionFormatter" data-align="center" data-sortable="false">Options</th>
					</tr>
				</thead>
			</table>
		</div>
		<!--widgetcontent-->
</div>
				</div>
	</div>
	<!--widgetbox-->
</div>
<!-- widgetcontent-->

		<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<form id="formAjout" class="form-horizontal" role="form" novalidate="novalidate" ng-controller="formAjoutCtrl">
						<div class="modal-header">
							<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
							<h4 class="modal-title" id="myModalLabel">Utilisateur</h4>
						</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="idRole" class="col-md-3 control-label">Role</label>
						<div class="col-md-9">
							<select id="idRole" name="idRole" class="form-control select2">
								<option value="0">Choisir Role</option>
								<option value="1">Administrateur</option>
								<option value="2">DAF</option>
								<option value="3">RH</option>
								<option value="4">Pointage</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="nom" class="col-md-3 control-label">Nom & pr&eacutenom(s)</label>
						<div class="col-md-9">
							<input type="text" class="form-control input-default" placeholder="Nom et prenom" required="required" name="nom" id="nom" >
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label"> </label>
						<div class="col-md-9">
							<div class="row">
								<div class="col-md-4">
									<label for="naissance">Username</label>
									<input type="text" class="form-control input-default" id="naissance" name="naissance" placeholder="username"  required="required" >
								</div>
								<div class="col-md-8 ">
									<label for="email">Email</label> 
									<input type="email" class="form-control input-default" id="email" name="email" placeholder="Email" required="required" >
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label"> </label>
						<div class="col-md-9">
							<div class="row">
								<div class="col-md-4 ">
									<label for="telephone">T&eacute;l&eacute;phone</label> 
									<input type="text" class="form-control input-default" id="telephone" name="telephone" placeholder="T�l�phone" maxlength="8" required="required" >
								</div>
								<div class="col-md-8">
									<label for="adresse">Adresse</label> 
									<input type="text" class="form-control input-default" id="adresse" name="adresse" placeholder="Adresse" >
								</div>
							</div>
						</div>
					</div>
					
			
				</div>
				<div class="modal-footer">
					<span></span>&nbsp;
					<input type="hidden" id="id" name="id" >
					<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
					<button type="button" onclick=" save()" class="btn btn-success">Valider</button>
				</div>
			</div>
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
	                    Etes vous sur de vouloir supprimer ?
	                </span>
	            </div>
	            <div class="modal-body">
	            	<h4 ng-bind="utilisateur.info"></h4>
	            </div>
	            <div class="modal-footer">
                	<input type="text" class="hidden" ng-show="false" value="" name="id" ng-model="utilisateur.id">
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
    $scope.pupulateForm = function (utilisateur) {
        $scope.utilisateur = utilisateur;
    };
}).controller('formDeleteCtrl', function ($scope) {
    $scope.pupulateForm = function (utilisateur) {
    	$scope.utilisateur = utilisateur;
    };

});
//End AngularJs

var action = "ajout";
var $table;

function nomCompletFormatter(utilisateur) {
    return utilisateur.nomComplet;
}

function dateNaissanceFormatter(utilisateur) {
    return utilisateur.dNaissance;
}

function adresseFormatter(utilisateur) {
    return utilisateur.adresse;
}

function telephoneFormatter(utilisateur) {
    return utilisateur.telephone;
}

function emailFormatter(utilisateur) {
    return utilisateur.email;
}

function roleFormatter(role) {
	if(role.id == 1) return 'ADMINISTRATEUR';
	if(role.id == 2) return 'DAF';
	if(role.id == 3) return 'RH';
	if(role.id == 4) return 'Pointage';
}

function initializeUser(){
	loadDataToForm(null, 0, null, null, null, null, null);
}

function loadDataToForm(id, role, nom, dateNaissance, email, telephone, adresse){
	jQuery('#id').val(id);
	jQuery('#idRole').select2('val', role);
	jQuery("#nom").val(nom);
	jQuery("#naissance").val(dateNaissance);
	jQuery("#email").val(email);
	jQuery("#telephone").val(telephone);
	jQuery('#adresse').val(adresse);
}

jQuery(document).ready(function($){
	$( ".select2" ).select2();
	$table = $('#table');

	
	$("#formAjout").submit(function(){
		var formData = $(this).serialize();
		
		$.ajax({
            type: "POST",
            url:  baseUrl + "/parametrages/enregistrerutilisateur",
            cache: false,
            data: formData,
            success: function (reponse) {
            	/*if (response != null) {
            		$("#rhpModal").modal("hide");
            		jQuery('#table').bootstrapTable('refresh');
            		//loadDataToForm(null, null, null, null, null, null, null, null);
					jAlert("'Enregistr� avec succ�s", "UTILISATEUR");
				}
            	else {
					alert('Impossible d\'effectuer cet enregistrement');
				}*/
            	
            	if (reponse.result == "succes") {
                   	if(action == "ajout"){
                   		//Rechargement de la liste (le tableau)
                   		$table.bootstrapTable('refresh');
                   		$("#formAjout")[0].reset(); //Initialisation du formulaire
                   		$("#rhpModal").modal("hide");
                   	}
                   	else{
                   		//MAJ de la ligne modifi�e
                   		$table.bootstrapTable('updateRow', {
                               index: $table.find('tr.selected').data('index'),
                               row: reponse.data
                           });
                   		$("#formAjout")[0].reset(); //Initialisation du formulaire
                   		$("#rhpModal").modal("hide");
                   	}
                   }
                   else if(reponse.result == "erreur_champ_obligatoire"){
                   	alert("Saisie invalide");
                   }
            },
            beforeSend: function () {
                   $("#formAjout").attr("disabled", true);
                   $("#rhpModal .modal-footer span").addClass('loader');
               },
            error: function () {
               	$("#rhpModal .modal-body div.alert").alert();
               	$("#rhpModal .modal-body .alert h4").html("Erreur survenue");
               	$("#rhpModal .modal-body .alert p").html("Verifier que vous etes connect&eacutes au serveur.");
               	$("#rhpModal .modal-footer span").removeClass('loader');
               },
            complete: function () {
                   $("#formAjout").removeAttr("disabled");
                   $("#rhpModal .modal-footer span").removeClass('loader');
                   $table.bootstrapTable('refresh', {
                       url: baseUrl + '/parametrages/listutilisateurjson'
                   });
                   $("#rhpModal").modal("hide");
               }
        });
		return false;
	})
	
	$("#formDelete").submit(function(e){
		e.preventDefault();
        var formData = $(this).serialize();
        var idSup = [];
      	//Le formulaire formDelete doit avoir un seul champ input:text
        idSup.push(parseInt($("#formDelete :text").val()));
        
        $.ajax({
            type: "POST",
            url: baseUrl + "/parametrages/supprimerutilisateur",
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
            	alert("Err");
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
	
	function actifFormatter(utilisateur){
		var optionActif = '<small class="label label-danger"><i class="fa fa-clock-o"></i> -- Desactive </small>';
		if(utilisateur.actif == true)
			optionActif = '<small class="label label-success"><i class="fa fa-clock-o"></i> -- Active </small>';
		
		return optionActif;
	}
	
	function optionFormatter(id, row, index) {
		var option = '<a onclick="edit('+row.id+')"   data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier Utilisateur [NOM : '+row.utilisateur.nomComplet+' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
		option += '&nbsp;<a onclick="del(\'' + row.id + '\', \'' + row.utilisateur.nomComplet + '\')" href="#" title="Suprimer Utilisateur [NOM : '+row.utilisateur.nomComplet+' ]"> <span class="glyphicon glyphicon-trash"></span></a>&nbsp;&nbsp;';
		if(row.utilisateur.actif == true){
			option += '&nbsp;<a onclick="enable('+row.id+')" href="#" title="Desactiver Utilisateur [NOM : '+row.utilisateur.nomComplet+' ]"><span class="glyphicon glyphicon-remove" style="color:red;"></span></a>';
		}
		else{
			option += '&nbsp;<a onclick="enable('+row.id+')" href="#" title="Activer Utilisateur [NOM : '+row.utilisateur.nomComplet+' ]"><span class="glyphicon glyphicon-ok"></span></a>';
		}
		
        return option;
    }

	function save() {
		jQuery.ajax({
			type : "POST",
			url : baseUrl + "/parametrages/enregistrerutilisateur",
			cache : false,
			data : {
				id : jQuery('#id').val(),
				idRole : jQuery('#idRole').val(),
				nom : jQuery('#nom').val(),
				naissance : jQuery('#naissance').val(),
				telephone : jQuery('#telephone').val(),
				adresse : jQuery('#adresse').val(),
				email : jQuery('#email').val()
			},
			beforeSend : function() {
				//jQuery('#idButton').hide();
				//jQuery('#idWait').show();
			},
			success : function(response) {
				if (response != null) {
					//loadDataToForm(null, null, null, null, null, null, null, null);
					alert('Enregistr� avec succ�s');
					jQuery('#rhpModal').modal('hide');
					//widgetView('list');
				} else {
					//alert('L\'utilisateur � �t� enregistr� avec succ�s');
					alert('Impossible d\'effectuer cet enregistrement');
				}
			},
			error : function() {

			},
			complete : function() {
				jQuery('#table').bootstrapTable('refresh', {
					url : baseUrl + '/parametrages/listutilisateurjson'
				});
			}
		});
	}
	
	function edit(id){
		jQuery.ajax({
            type: "POST",
            url: baseUrl+"/parametrages/choisirutilisateur",
            cache: false,
            data: {id: id},
			success: function (response) {
            	if (response != null) {
            		loadDataToForm(response.id, response.role.id, response.utilisateur.nomComplet, response.utilisateur.dNaissance, response.utilisateur.email, response.utilisateur.telephone, response.utilisateur.adresse);
            		//loadDataToForm(response.id, response.branch.id, response.name, response.bday, response.phoneNumber, response.address, response.email, null);
				} else {
					alert('Impossible de charger cet objet');
				}
            },
            error: function () {
                
            }
        });
	}
	
	function enable(id){
		jQuery.ajax({
            type: "POST",
            url: baseUrl+"/parametrages/changerstatut",
            cache: false,
            data: {id: id},
			success: function (response) {
            	if (response != null) {
            		
				} else {
					alert('Impossible de charger cet objet');
				}
            },
            error: function () {
                
            },
			complete : function() {
				jQuery('#table').bootstrapTable('refresh', {
					url : baseUrl + '/parametrages/listutilisateurjson'
				});
			}
        });
	}

	function del(id, name) {
		var r = confirm('Supprimer ' + name + ' ?');
		if (r == true) {
			jQuery.ajax({
				type : 'POST',
				url : baseUrl + '/parametrages/supprimerutilisateur',
				cache : false,
				data : {
					id : id
				},
				success : function(response) {
					if (response.result == true) {
						alert('Supprim� avec succ�s');
					} else {
						alert('Impossible de supprimer cet objet');
					}
				},
				error : function() {

				},
				complete : function() {
					jQuery('#table').bootstrapTable('refresh', {
						url : baseUrl + '/parametrages/listutilisateurjson'
					});
				}
			});
		} else {

		}
	}
	
</script>