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
					<h3>Planning de cong&eacute;</h3>
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
		<div id="tableWidget" class="widgetcontent">
			<table id="table" class="table table-info table-striped"
				data-toggle="table" 
				data-click-to-select="true" 
				data-single-select="true" 
				data-sort-name="name"
				data-sort-order="desc" 
				data-show-export="true" 
				data-export-data-type="all"
				data-url="${pageContext.request.contextPath}/personnels/listplanningcongejson"
				data-side-pagination="server" 
				data-pagination="true"
				data-page-list="[10, 20, 50, 100, 200]" 
				data-search="true">
				<thead>
					<tr>
						<th data-field="contratPersonnel" data-formatter="matriculeFormatter" data-align="left" data-sortable="true">Matricule</th>
						<th data-field="contratPersonnel" data-formatter="nomCompletFormatter" data-align="left" data-sortable="true">Nom</th>
						<th data-field="contratPersonnel" data-formatter="dateNaissanceFormatter" data-align="center" data-sortable="true">Date de Naissance</th>
						<th data-field="contratPersonnel" data-formatter="lieuNaissanceFormatter" data-align="left" data-sortable="true">Lieu Naissance</th>
						<th data-field="contratPersonnel" data-formatter="serviceFormatter" data-align="center" ata-sortable="true">Service</th>
						<th data-field="contratPersonnel" data-formatter="fonctionFormatter" data-align="left" data-sortable="true">Fonction</th>
						<th data-field="dDepart" data-align="center" data-sortable="true">Date D&eacute;part</th>
						<th data-field="statut" data-formatter="actifFormatter" data-align="center" data-sortable="true">Consomm&eacute;</th>
						<th data-field="id" data-formatter="optionFormatter" data-align="center" data-sortable="false">Options</th>
					</tr>
				</thead>
			</table>
		</div>
		<!--widgetcontent-->

	</div>
	<!--widgetbox-->
</div>
				</div>	</div>
<!-- widgetcontent-->

<div class="modal fade" id="departCongeModal" role="dialog"
	aria-labelledby="departCongeModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div id="formAjout" class="form-horizontal" role="form" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Calendrier Cong&eacute;</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="nom" class="col-md-3 control-label">Date D&eacute;part</label>
						<div class="col-md-9">
							<input type="text" class="form-control input-default" id="dateDepart" name="dateDepart" placeholder="N�(e) le" maxlength="10" required="required" >
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

function nomCompletFormatter(contratPersonnel) {
    return contratPersonnel.personnel.nom + ' ' + contratPersonnel.personnel.prenom;
}

function matriculeFormatter(contratPersonnel) {
    return contratPersonnel.personnel.matricule;
}

function dateNaissanceFormatter(contratPersonnel) {
    return contratPersonnel.personnel.dNaissance;
}

function lieuNaissanceFormatter(contratPersonnel) {
    return contratPersonnel.personnel.lieuNaissance;
}

function serviceFormatter(contratPersonnel) {
    return contratPersonnel.personnel.service.libelle;
}

function fonctionFormatter(contratPersonnel) {
    return contratPersonnel.fonction.libelle;
}

function roleFormatter(role) {
	if(role.id == 1) return 'ADMINISTRATEUR';
	if(role.id == 2) return 'DAF';
	if(role.id == 3) return 'RH';
}

function loadDataToForm(id, dateDepart){
	jQuery("#id").val(id);
	jQuery("#dateDepart").val(dateDepart);
}

jQuery(document).ready(function($){
	$( ".select2" ).select2();
	$table = $('#table');
	$("#dateDepart").datepicker({
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
            		$("#departCongeModal").modal("hide");
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
                   		$("#departCongeModal").modal("hide");
                   	}
                   	else{
                   		//MAJ de la ligne modifi�e
                   		$table.bootstrapTable('updateRow', {
                               index: $table.find('tr.selected').data('index'),
                               row: reponse.data
                           });
                   		$("#formAjout")[0].reset(); //Initialisation du formulaire
                   		$("#departCongeModal").modal("hide");
                   	}
                   }
                   else if(reponse.result == "erreur_champ_obligatoire"){
                   	alert("Saisie invalide");
                   }
            },
            beforeSend: function () {
                   $("#formAjout").attr("disabled", true);
                   $("#departCongeModal .modal-footer span").addClass('loader');
               },
            error: function () {
               	$("#departCongeModal .modal-body div.alert").alert();
               	$("#departCongeModal .modal-body .alert h4").html("Erreur survenue");
               	$("#departCongeModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
               	$("#departCongeModal .modal-footer span").removeClass('loader');
               },
            complete: function () {
                   $("#formAjout").removeAttr("disabled");
                   $("#departCongeModal .modal-footer span").removeClass('loader');
                   $table.bootstrapTable('refresh', {
                       url: baseUrl + '/parametrages/listutilisateurjson'
                   });
                   $("#departCongeModal").modal("hide");
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
	
	function actifFormatter(statut){
		var optionActif = '<small class="label label-danger"><i class="fa fa-clock-o"></i> OUI </small>';
		if(statut == true)
			optionActif = '<small class="label label-success"><i class="fa fa-clock-o"></i> NON </small>';
		
		return optionActif;
	}
	
	function optionFormatter(id, row, index) {
		var option = '<a onclick="edit('+row.id+')"   data-toggle="modal" data-target="#departCongeModal" href="#" title="Modifier Date Depart ['+row.contratPersonnel.personnel.nom+' '+row.contratPersonnel.personnel.prenom+' :: '+row.dDepart+' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
		/*option += '&nbsp;<a onclick="del(\'' + row.id + '\', \'' + row.utilisateur.nomComplet + '\')" href="#" title="Suprimer Utilisateur [NOM : '+row.utilisateur.nomComplet+' ]"> <span class="glyphicon glyphicon-trash"></span></a>&nbsp;&nbsp;';
		if(row.utilisateur.actif == true){
			option += '&nbsp;<a onclick="enable('+row.id+')" href="#" title="D�sactiver Utilisateur [NOM : '+row.utilisateur.nomComplet+' ]"><span class="glyphicon glyphicon-remove" style="color:red;"></span></a>';
		}
		else{
			option += '&nbsp;<a onclick="enable('+row.id+')" href="#" title="Activer Utilisateur [NOM : '+row.utilisateur.nomComplet+' ]"><span class="glyphicon glyphicon-ok"></span></a>';
		}*/
		
        return option;
    }

	function save() {
		jQuery.ajax({
			type : "POST",
			url : baseUrl + "/personnels/enregistrerplanningconge",
			cache : false,
			data : {
				id : jQuery('#id').val(),
				dateDepart : jQuery('#dateDepart').val()
			},
			beforeSend : function() {
				//jQuery('#idButton').hide();
				//jQuery('#idWait').show();
			},
			success : function(response) {
				if (response != null) {
					loadDataToForm(null, null);
					alert('Enregistr� avec succ�s');
					jQuery('#departCongeModal').modal('hide');
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
					url : baseUrl + '/personnels/listplanningcongejson'
				});
			}
		});
	}
	
	function edit(id){
		jQuery.ajax({
            type: "POST",
            url: baseUrl+"/personnels/choisirplanningconge",
            cache: false,
            data: {id: id},
			success: function (response) {
            	if (response != null) {
            		loadDataToForm(response.id, response.dDepart);
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