<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
#ui-datepicker-div{
	z-index: 1000000!important;
}
</style>


<div class="widget">
	<div class="widgetbox">
		<div class="headtitle">
			<div class="btn-group">
				<button data-toggle="dropdown" class="btn dropdown-toggle">
					Action <span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a href="#" onclick="widgetView('list')">Listes</a></li>
					<li class="divider"></li>
					<li><a href="#" onclick="initializeUser(); data-toggle="modal" data-target="#rhpModal">Nouveau</a></li>
					<!-- <li><a href="#" onclick="widgetView('new')">Nouveau</a></li> -->
				</ul>
			</div>
			<h4 id="widgetTitle" class="widgettitle">Liste des utilisateurs</h4>
		</div>
		<div id="tableWidget" class="widgetcontent">
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
						<th data-field="utilisateur" data-formatter="dateNaissanceFormatter" data-align="center" data-sortable="true">Date de Naissance</th>
						<th data-field="utilisateur" data-formatter="adresseFormatter" data-align="left" data-sortable="true">Adresse</th>
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
	<!--widgetbox-->
</div>
<!-- widgetcontent-->

<div class="modal fade" id="rhpModal" role="dialog"
	aria-labelledby="rhpModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<form id="formAjout" class="form-horizontal" role="form" novalidate="novalidate" method="POST" ng-controller="formAjoutCtrl">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Utilisateur</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="idRole" class="col-md-3 control-label">Role</label>
						<div class="col-md-9">
							<select id="idRole" name="idRole" class="form-control select2" ng-model="utilisateur.role.id">
								<option value="0">Choisir Role</option>
								<option value="1">Administrateur</option>
								<option value="2">DAF</option>
								<option value="3">RH</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="nom" class="col-md-3 control-label">Nom & prénom(s)</label>
						<div class="col-md-9">
							<input type="text" class="form-control input-default" placeholder="Nom et prenom" required="required" name="nom" id="nom" ng-model="utilisateur.nomComplet">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label"> </label>
						<div class="col-md-9">
							<div class="row">
								<div class="col-md-4">
									<label for="naissance">N&eacute(e) le</label> 
									<input type="text" class="form-control input-default" id="naissance" name="naissance" placeholder="Né(e) le" maxlength="10" required="required" ng-model="utilisateur.dNaissance">
								</div>
								<div class="col-md-8 ">
									<label for="email">Email</label> 
									<input type="email" class="form-control input-default" id="email" name="email" placeholder="Email" required="required" ng-model="utilisateur.email">
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
									<input type="text" class="form-control input-default" id="telephone" name="telephone" placeholder="Téléphone" maxlength="8" required="required" ng-model="utilisateur.telephone">
								</div>
								<div class="col-md-8">
									<label for="adresse">Adresse</label> 
									<input type="text" class="form-control input-default" id="adresse" name="adresse" placeholder="Adresse" ng-model="utilisateur.adresse">
								</div>
							</div>
						</div>
					</div>
					
			
				</div>
				<div class="modal-footer">
					<span></span>&nbsp;
					<input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="utilisateur.id">
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
	                    Etes vous sûr de vouloir supprimer ?
	                </span>
	            </div>
	            <div class="modal-body">
	            	<h4 ng-bind="utilisateur.info"></h4>
	            </div>
	            <div class="modal-footer">
                	<input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="utilisateur.id">
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
}

function initializeUser(){
	loadDataToForm(null, 0, null, null, null, null, null);
}

function loadDataToForm(id, role, nom, dateNaissance, email, telephone, adresse){
	/*if(id == null){
		jQuery('#branch').removeAttr('readonly');
		jQuery('#cashier').removeAttr('readonly');
		jQuery('#branch').select2('val', 0);
		jQuery('#cashier').select2('val', 0);
		jQuery('#cashdeskModalLabel').html('Nouvelle Caisse');
	} else {
		jQuery('#cashier').val(cashier).trigger('change');
		jQuery('#branch').attr('readonly', 'readonly');
		jQuery('#cashdeskModalLabel').html('Modifier Caisse');
	}*/
	jQuery('#id').val(id);
	$("#nom").val(nom);
	$("#naissance").val(dateNaissance);
	$("#email").val(email);
	$("#telephone").val(telephone);
	$('#adresse').val(adresse);
}

jQuery(document).ready(function($){
	$( ".select2" ).select2();
	$table = $('#table');
	$("#naissance").datepicker({
		dateFormat: 'dd/mm/yy',
        showOtherMonths:true
    });
	
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
					jAlert("'Enregistré avec succès", "UTILISATEUR");
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
                   		//MAJ de la ligne modifiée
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
               	$("#rhpModal .modal-body .alert p").html("Verifier que vous êtes connectés au serveur.");
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
            	$(".deleteModal .modal-body .alert p").html("Verifier que vous êtes connectés au serveur.");
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
		var optionActif = '<small class="label label-danger"><i class="fa fa-clock-o"></i> -- Desactivé </small>';
		if(utilisateur.actif == true)
			optionActif = '<small class="label label-success"><i class="fa fa-clock-o"></i> -- Activé </small>';
		
		return optionActif;
	}
	
	function optionFormatter(id, row, index) {
		var option = '<a onclick="edit('+row.id+')"   data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier Utilisateur [NOM : '+row.utilisateur.nomComplet+' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
		option += '&nbsp;<a onclick="del('+row.id+')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer Utilisateur [NOM : '+row.utilisateur.nomComplet+' ]"> <span class="glyphicon glyphicon-trash"></span></a>&nbsp;&nbsp;';
		if(row.actif == true){
			option += '&nbsp;<a href="#" data-action="desactiver" title="Désactiver Utilisateur [NOM : '+row.utilisateur.nomComplet+' ]"><span class="glyphicon glyphicon-remove" style="color:red;"></span></a>';
		}
		else{
			option += '&nbsp;<a href="#" data-action="activer" title="Activer Utilisateur [NOM : '+row.utilisateur.nomComplet+' ]"><span class="glyphicon glyphicon-ok"></span></a>';
		}
		
        return option;
    }
	
	function edit(idUtilisateur){
		var $scope = angular.element(document.getElementById("formAjout")).scope();
        
		var rows = $table.bootstrapTable('getData');
		var utilisateur = _.findWhere(rows, {id: idUtilisateur});
		action = "modifier";
		$scope.$apply(function () {
            $scope.pupulateForm(utilisateur);
        });
	}
	
	function del(idUtilisateur){
		var $scope = angular.element(document.getElementById("formDelete")).scope();
        
		var rows = $table.bootstrapTable('getData');
		var utilisateur = _.findWhere(rows, {id: idUtilisateur});
		utilisateur.info = utilisateur.nomComplet;
		$scope.$apply(function () {
            $scope.pupulateForm(utilisateur);
        });
	}
	
</script>