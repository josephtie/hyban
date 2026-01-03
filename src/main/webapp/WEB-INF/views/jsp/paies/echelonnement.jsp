<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
	<div class="col-md-12">
		<div class="panel panel-warning">
			<div class="panel-heading">
				<div class="panel-title-box">
					<h3>Liste des Ech&eacute;ances </h3>
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
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-show-export="true"
					data-sort-name="id"
					data-sort-order="desc"
					data-url="${pageContext.request.contextPath}/paie/echelonnjson"
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[20, 50, 100, 200, 500,2000]"
					data-search="true">
					<thead>
						<tr>
						
							<th data-field="pretPersonnel" data-formatter="matriFormatter" data-align="left" data-sortable="true">Matricule</th>
							<th data-field="pretPersonnel" data-formatter="nomFormatter" data-align="left" data-sortable="true">Nom</th>
							<th data-field="pretPersonnel" data-formatter="pretFormatter" data-align="left">Pret</th>
							<th data-field="montant"  data-align="center">Montant</th>
							<th data-field="periodePaie"  data-formatter="periodeFormatter" data-align="left">Mois</th>
							<th data-field="libpayer"  data-align="center">Pay&eacute;</th>
						
							<th data-field="id" data-formatter="optionFormatter" data-align="center">Options</th>
							
							<!-- <th data-field="state" data-checkbox="true"></th> -->
						
						</tr>
					</thead>
				</table>
		</div>
	</div>
</div>
			</div>
		</div>

<!-- Generation des bulletins -->
<div class="modal fade" id="rhpModal"  role="dialog" aria-labelledby="rhpModalLabel" >
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formAjout" class="form-horizontal" role="form">
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Modifier une modalite</h4>
				</div>
                <div class="modal-body">
               
                    	<div class="form-group">
                        <label for="mois" class="col-md-2 control-label">Mois</label>
                        <div class="col-md-10">
                             <select id="periodPaie" name="periodPaie" class="form-control select2">
											<c:forEach items="${listPeriodepaie}" var="periodePaie">
				                            	<option value="${periodePaie.id}">${periodePaie.mois.mois} ${periodePaie.annee.annee}</option>
				                            </c:forEach>
							</select>
                        </div>
                    </div>                
                </div>
                <div class="modal-footer">                
                	<span></span>&nbsp;
                	  <input type="hidden" id="idechel" name="idechel"  />
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Pret -->

<script type="text/javascript">
//AngularJS
app.controller('infoPersoCtrl', function ($scope) {
    $scope.pupulateForm = function (contratPersonnel) {
        $scope.personnel = contratPersonnel.personnel;
        $scope.libelleService = contratPersonnel.libelleService;
    };
}).controller('formDeleteCtrl', function ($scope) {
    $scope.pupulateForm = function (categorie) {
    	$scope.categorie = categorie;
    };

});
//End AngularJs

var actionUrl = "/paie/enregisterlivrepaie";
var actionDeleteUrl = "/personnels/supprimercategorie";
var action = "ajout";
var $table;
jQuery(document).ready(function($) {
	$table = $('#table');
	$( ".select2" ).select2();
	$("#dEmprunt").datepicker({
		dateFormat: 'dd/mm/yy',
        showOtherMonths:true
    });
	//loadBranchs();
	//Envoi des donnees
	$("#formAjout").submit(function(e){
		e.preventDefault();
        var formData = $(this).serialize();
        console.log("form", formData);
        $.ajax({
            type: "POST",
            url: baseUrl + "/paie/updateEchelonnmt",
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == "success") {
                	$table.bootstrapTable('refresh');
                	jQuery('#formAjout')[0].reset(); //Initialisation du formulaire
                	
            		alert(reponse.result);
            		jQuery('#rhpModal').modal('hide');
                }
                else {
                	alert(reponse.result);
                }
            },
            error: function () {
            	jQuery("#rhpModal div.alert").alert();
            	jQuery("#rhpModal .alert h4").html("Erreur survenue");
            	jQuery("#rhpModal .alert p").html("Verifier que vous �tes connect�s au serveur.");
            	//$("#formPret span.load").removeClass('loader');
            },
            beforeSend: function () {
            	jQuery("#formAjout").attr("disabled", true);
                jQuery("#formAjout span.load").addClass('loader');
            },
            complete: function () {
            
                jQuery('#table'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/echelonnjson' });
            }
        });
	});
});

function optionFormatter(id, row, index) {
	var option = '<a onclick="edit('+row.id+')" data-toggle="modal" data-target="#rhpModal" href="#" title="Ajouter pr�t [LIBELLE : '+row.pretPersonnel.personnel.nom+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	option += '&nbsp;<a data-toggle="modal" href="#" title="Suprimer bulletin [LIBELLE : '+row.pretPersonnel.personnel.nom+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';
	
    return option;
}

function pretiFormatter(id, row, index) {
	/* var option = '<a onclick="edit('+row.personnel.id+')" data-toggle="modal" data-target="#rhpModalPret" href="#" title="Ajouter pr�t [LIBELLE : '+row.personnel.nom+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	option += '&nbsp;<a data-toggle="modal" href="#" title="Suprimer bulletin [LIBELLE : '+row.libelle+' ]"> <span class="glyphicon glyphicon-trash"></span></a>'; */
	var option='';
    return option;
}
function matriFormatter(pretPersonnel, row, index) {
	if(row.pretPersonnel.personnel.matricule == ''){
		return "";
	}
	return row.pretPersonnel.personnel.matricule;
}
/* function matrisFormatter(personnel, row, index) {
	if(row.personnel.matricule == ''){
		return "";
	}
	return row.personnel.matricule;
} */
function periodeFormatter(periodePaie, row, index) {
	if(row.periodePaie.affiche == ''){
		return "";
	}
	return row.periodePaie.affiche ;
}
function pretFormatter(pretPersonnel, row, index) {
	if(row.pretPersonnel.pret.libelle == ''){
		return "";
	}
	return row.pretPersonnel.pret.libelle;
}
function nomFormatter(pretPersonnel, row, index) {
	if(row.pretPersonnel.personnel.nom == ''){
		return "";
	}
	return row.pretPersonnel.personnel.nom+" "+row.pretPersonnel.personnel.prenom;
}
function nomsFormatter(personnel, row, index) {
	if(row.personnel.nom == ''){
		return "";
	}
	return row.personnel.nom+" "+row.personnel.prenom;
}
function sexeFormatter(personnel, row, index) {
	if(row.personnel.sexe == ''){
		return "";
	}
	return row.personnel.sexe;
}
function datnaisFormatter(personnel, row, index) {
	if(row.personnel.dNaissance == ''){
		return "";
	}
	return row.personnel.dNaissance;
}



function edit(idFonction){	
	jQuery('#idechel').val(idFonction);

    } 


</script>