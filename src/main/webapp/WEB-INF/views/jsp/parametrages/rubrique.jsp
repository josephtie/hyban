


<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
    <div class="col-md-12">
        <div class="panel panel-warning">
            <div class="panel-heading">
                <div class="panel-title-box">
                    <h3>Liste des Rubriques</h3>
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
            <table id="table" class="table table-info table-striped"
                   data-toggle="table"
                   data-click-to-select="true"
                   data-single-select="true"
                   data-url="${pageContext.request.contextPath}/parametrages/loadrubriques"
                   data-side-pagination="server"
                   data-pagination="true"
                   data-page-list="[5, 10, 20, 50, 100, 200]"
                   data-search="true">
                <thead>
                    <tr>
                       <th data-field="libelle" data-align="left" data-sortable="true">Libell&eacute;</th>
			        	<th data-field="stretatimposition" data-align="left"   data-sortable="true">Type</th>
			        	<th data-field="taux" data-align="left" data-sortable="true">Taux</th>	
			        	<th data-field="mtExedent" data-align="left" data-sortable="true">Montant exedent</th>			        	
			        	<th data-field="active" data-align="left" data-formatter="statutFormatter" data-sortable="true">Active </th>
			        	<th data-field="id" data-formatter="optionFormatter" data-width="100px" data-align="center">Options</th>
                    </tr>
                </thead>
            </table>
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
                    <h4 class="modal-title" id="myModalLabel">Rubriques de paie (Primes)</h4>
                </div>
                <div class="modal-body">
          
                    <div class="form-group">
                        <label for="faute" class="col-md-2 control-label">Libelle</label>
                        <div class="col-md-10">
                            <input type="text" id="libelle" name="libelle"  class="form-control" placeholder="Rubrique" ng-model="rubrique.libelle" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="commentaire" class="col-md-2 control-label">Taux</label>
                        <div class="col-md-10">
                            <input class="form-control" id="taux" name="taux" placeholder="Taux" ng-model="rubrique.taux"/>
                                
                           
                        </div>
                    </div>
                       <div class="form-group">
                        <label for="commentaire" class="col-md-2 control-label">Montant Excedent</label>
                        <div class="col-md-10">
                            <input class="form-control" id="mtExedent" name="mtExedent" placeholder="Montant Excedent" ng-model="rubrique.mtExedent"/>
                                
                           
                        </div>
                    </div>
                    
                    
                     <div class="form-group">
                        <label for="faute" class="col-md-2 control-label">Type d'imposition</label>
                        <div class="col-md-10">
                          <!--   <input type="text" id="etatImposition" name="etatImposition" class="form-control" placeholder="Etat imposition" /> -->
                            
                              <select id="etatImposition" name="etatImposition" class="form-control select2" ng-model="rubrique.etatImposition">
                                                    <option>-- Type imposition --</option>
                                                    <option value="1" selected="selected"> Imposable </option>
                                                    <option value="2"> Non Imposable </option>
                                                    <option value="3"> Imposable & Non Imposable</option>
                                  <option value="4"> Retenue Mutuelle</option>
                                  <option value="5"> Regularisation</option>
                                    <option value="6"> Retenue Sociale</option>
                                                </select>
                            
                        </div>
                    </div>
                      <div class="form-group">
                      <label for="faute" class="col-md-2 control-label">Active</label>
                          <div class="col-md-10">
                                               
                                                <div>
                                                   
                                                    <label id="banqueNon" class="radio-inline">
                                                        <input name="active" type="radio" value="false"> Non
                                                    </label>
                                                     <label id="banqueOui" class="radio-inline">
                                                        <input name="active" type="radio" value="true"> Oui
                                                    </label>
                                                </div>
                                            </div>
                    </div>
                    <div class="form-group">
                        <label for="faute" class="col-md-2 control-label">Permanent</label>
                        <div class="col-md-10">

                            <div>

                                <label id="permanentNon" class="radio-inline">
                                    <input name="permanent" type="radio" value="false"> Non
                                </label>
                                <label id="permanentOui" class="radio-inline">
                                    <input name="permanent" type="radio" value="true"> Oui
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" id="idR" name="idR" >
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
            <form id="formDelete"  action="#" method="post">
                <div class="modal-header ">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <span class="circle bg-danger">
                        <i class="fa fa-question-circle"></i>
                        Etes vous sur de vouloir supprimer ?
                    </span>
                </div>
                <div class="modal-body">
                    <h4 ng-bind="rubrique.info"></h4>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" name="id" >
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
      //  $scope.typeSanction = jQuery("#idTypeSanction option:first").val();
        $scope.pupulateForm = function (rubrique) {
            $scope.rubrique = rubrique;
        };
  
    }).controller('formDeleteCtrl', function ($scope) {
        $scope.pupulateForm = function (rubrique) {
            $scope.rubrique = rubrique;
        };

    });  
    //End AngularJs


     var actionUrl = "/parametrages/enregistrerrubrique";
    var actionDeleteUrl = "/parametrages/supprimerrubrique";
    var action = "ajout";
    var $table;
    jQuery(document).ready(function ($) {
        $table = $('#table');

        //Envoi des donnees
        $("#formAjout").submit(function (e) {
            e.preventDefault();

            var formData = $(this).serialize();
            console.log("form", formData);
            $.ajax({
                type: "POST",
                url: baseUrl + actionUrl,
                cache: false,
                data: formData,
                success: function (reponse) {
                    if (reponse.result && reponse.status) {
                        if (action == "ajout") {
                            //Rechargement de la liste (le tableau)
                            $table.bootstrapTable('refresh');
                            $("#formAjout")[0].reset(); //Initialisation du formulaire
                            $("#rhpModal").modal('hide');
                        } else {
                            //MAJ de la ligne modifi�e
                            $table.bootstrapTable('updateRow', {
                                index: $table.find('tr.selected').data('index'),
                                row: reponse.data
                            });
                        }
                    } else {
                        alert(reponse.message);
                    }
                },
                error: function () {
                    $("#rhpModal .modal-body div.alert").alert();
                    $("#rhpModal .modal-body .alert h4").html("Erreur survenue");
                    $("#rhpModal .modal-body .alert p").html("Verifier que vous etes connectes au serveur.");
                 //   $("#rhpModal .modal-footer span").removeClass('loader');
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

        $("#formDelete").submit(function (e) {
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
                    } else if (reponse.result == "erreur_champ_obligatoire") {
                        alert("Saisie invalide");
                    }
                },
                error: function (err) {
                    $(".deleteModal .modal-body div.alert").alert();
                    $(".deleteModal .modal-body .alert h4").html("Erreur survenue");
                    $(".deleteModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
                //    $(".deleteModal .modal-footer span").removeClass('loader');
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
    function statutFormatter(active){
		var optionActif = '<small class="label label-danger"><i class="fa fa-clock-o"></i> -- Desactive </small>';
		if(active == true)
			optionActif = '<small class="label label-success"><i class="fa fa-clock-o"></i> -- Active </small>';
		
		return optionActif;
	}
    //Functions
    function typeSanctionFormatter(typeSanction){
        return typeSanction.libelle;
    }
    //  function etatImpotFormatter(row){
    // 	alert(row.etatImposition);
    // 	if(row.etatImposition='')
		// 	stretatimposition="";
		// if(row.etatImposition=1)
		// 	stretatimposition="Imposable";
		// if(row.etatImposition=2)
		// 	stretatimposition="Non Imposable";
		// if(row.etatImposition=3)
		// 	stretatimposition="Imposable et Non Imposable";
		// return stretatimposition;
    //
    // }
    function optionFormatter(id, row, index) {
        var option = '<a onclick="edit(' + row.id + ')" data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier sanction [LIBELLE : ' + row.libelle + ' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
     /*    option += '&nbsp;<a onclick="del(' + row.id + ')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer sanction [LIBELLE : ' + row.libelle + ' ]"> <span class="glyphicon glyphicon-trash"></span></a>'; */

        return option;
    }

/*     function edit(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formAjout")).scope();

        var rows = $table.bootstrapTable('getData');
        var rubrique = _.findWhere(rows, {id: idTypeSaction});
        if (rubrique.active == true) {
            jQuery("#banqueOui span").addClass("checked");
            jQuery("#banqueOui :radio").attr("checked", true);
            jQuery("#banqueNon span").removeClass("checked");
        } else if(rubrique.active == false) {
            jQuery("#banqueNon span").addClass("checked");
            jQuery("#banqueNon :radio").attr("checked", true);
            jQuery("#banqueOui span").removeClass("checked");
        }
        $scope.rubrique = rubrique;
    } */
 
    function edit(id){
		jQuery.ajax({
            type: "POST",
            url: baseUrl+"/parametrages/trouverrubrique",
            cache: false,
            data: {id: id},
			success: function (response) {
				console.log(response.row.libelle);
            	if (response != null) {
            		loadDataToForm(response.row.id, response.row.libelle, response.row.taux, response.row.etatImposition, response.row.active,response.row.permanent);
            		//loadDataToForm(response.id, response.branch.id, response.name, response.bday, response.phoneNumber, response.address, response.email, null);
				} else {
					alert('Impossible de charger cet objet');
				}
            },
            error: function () {
                
            }
        });
	}
    
    function initializeUser(){
    	loadDataToForm(null, 0, null, null, null, null, null);
    }

    function loadDataToForm(id, libelle, taux, etatImposition,active,permanent){
    	jQuery('#idR').val(id);
    	jQuery('#etatImposition').select2('val', etatImposition);
    	 jQuery("#etatImposition").val(etatImposition).trigger("change");
    	jQuery("#taux").val(taux);
    	jQuery("#libelle").val(libelle);

        if (permanent == true) {
            jQuery("#permanentOui span").addClass("checked");
            jQuery("#permanentOui :radio").attr("checked", true);
            jQuery("#permanentNon span").removeClass("checked");
        } else if(permanent == false) {
            jQuery("#permanentNon span").addClass("checked");
            jQuery("#permanentNon :radio").attr("checked", true);
            jQuery("#permanentOui span").removeClass("checked");
        }
    	if (active == true) {
            jQuery("#banqueOui span").addClass("checked");
            jQuery("#banqueOui :radio").attr("checked", true);
            jQuery("#banqueNon span").removeClass("checked");
        } else if(active == false) {
            jQuery("#banqueNon span").addClass("checked");
            jQuery("#banqueNon :radio").attr("checked", true);
            jQuery("#banqueOui span").removeClass("checked");
        }

    }
    
    function del(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formDelete")).scope();

        var rows = $table.bootstrapTable('getData');
        var rubrique = _.findWhere(rows, {id: idTypeSaction});
        rubrique.info = rubrique.libelle;
        $scope.rubrique = rubrique;
      /*   $scope.$apply(function () {
            $scope.pupulateForm(rubrique);
        }); */
    }  
</script>
