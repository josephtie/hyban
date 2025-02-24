<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
    <div class="col-md-12">
        <div class="panel panel-warning">
            <div class="panel-heading">
                <div class="panel-title-box">
                    <h3>Liste des Comptes d'entreprises</h3>
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
                   data-url="${pageContext.request.contextPath}/parametrages/paginercptevirmtbanks"
                   data-side-pagination="server"
                   data-pagination="true"
                   data-page-list="[5, 10, 20, 50, 100, 200]"
                   data-search="true">
                <thead>
                    <tr>
                            <th data-field="bank" data-formatter="bankFormatter">Banque</th>
                            <th data-field="numguichCpteVirmt">Guichet</th>
                            <th data-field="numcpteCpteVirmt">Numero de compte</th>
                            <th data-field="donneurOrdCpteVirmt">Donneur ordre</th>
                            <th data-field="codEtablVirmt" data-align="center">Code Etab.</th>
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
                    <h4 class="modal-title" id="myModalLabel">Compte de virement</h4>
                </div>
                <div class="modal-body">
                 <%--    <div class="form-group">
                        <label for="idTypeSanction" class="col-md-2 control-label">Type sanction</label>
                        <div class="col-md-10">
                            <select class="form-control" id="idTypeSanction" name="idTypeSanction" ng-model="sanction.typeSanction.id" ng-init="sanction.typeSanction.id=typeSanction">
                                <c:forEach items="${listeTypeSanction}" var="typdeSanction">
                                    <option value="${typdeSanction.id}">${typdeSanction.libelle}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div> --%>
                    <div class="form-group">
						<label class="col-md-2 control-label"> </label>
						<div class="col-md-10">
							<div class="row">
								<div class="col-md-8 ">
									<label for="telephone">Banque</label>
									 <select class="form-control input-small select2" id="idbank" name="idbank"  ng-model="cptevirmtBanque.bank.id" ng-init="cptevirmtBanque.bank.id=bank">
                                    <c:forEach items="${listeBanques}" var="banques">
                                        <option value="${banques.id}">${banques.sigle} (${banques.codbanq})</option>
                                    </c:forEach>
                                </select>
								</div>
								<div class="col-md-4">
									<label for="adresse">RIB</label> 
									<input type="text" class="form-control input-default" id="ribCpteVirmt" ng-model="cptevirmtBanque.ribCpteVirmt" name="ribCpteVirmt" placeholder="RIB" maxlength="2" >
								</div>
							</div>
						</div>
					</div>  
                    
                    
                         <div class="form-group">
						<label class="col-md-2 control-label"> </label>
						<div class="col-md-10">
							<div class="row">
								<div class="col-md-4 ">
									<label for="telephone">Num. guichet</label> 
									 <input type="text" class="form-control input-default" id="numguichCpteVirmt" ng-model="cptevirmtBanque.numguichCpteVirmt" name="numguichCpteVirmt" maxlength="5" placeholder="Numero de Guichet" >
								</div>
								<div class="col-md-8">
									<label for="adresse">Num. compte</label> 
									<input type="text" class="form-control input-default" id="numcpteCpteVirmt" ng-model="cptevirmtBanque.numcpteCpteVirmt" name="numcpteCpteVirmt" maxlength="12" placeholder="Numero de Compte"  >
								</div>
							</div>
						</div>
					</div>         
            
					   <div class="form-group">
						<label class="col-md-2 control-label"> </label>
						<div class="col-md-10">
							<div class="row">
								<div class="col-md-4 ">
									<label for="telephone">Donneur Ordre</label> 
									 <input type="text" class="form-control input-default" id="donneurOrdCpteVirmt" ng-model="cptevirmtBanque.donneurOrdCpteVirmt" name="donneurOrdCpteVirmt" maxlength="15" placeholder="Donneur d'ordre" >
								</div>
								<div class="col-md-8">
									<label for="adresse">Code. etablissement</label> 
									<input type="text" class="form-control input-default" id="codEtablVirmt" ng-model="cptevirmtBanque.codEtablVirmt" name="codEtablVirmt" placeholder="Code Etablissement" maxlength="5" >
								</div>
							</div>
						</div>
					</div> 
                  
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="cptevirmtBanque.id">
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
                        Etes vous sur de vouloir supprimer ?
                    </span>
                </div>
                <div class="modal-body">
                    <h4 ng-bind="cptevirmtBanque.info"></h4>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="cptevirmtBanque.id">
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
       $scope.bank = jQuery("#idbank option:first").val();
        $scope.pupulateForm = function (cptevirmtBanque) {
            $scope.cptevirmtBanque = cptevirmtBanque;
        };
        $scope.initForm = function () {
            var bank = {};
            bank.id = jQuery("#idbank option:first").val();
            $scope.cptevirmtBanque = {bank: bank};
        }; 
    }).controller('formDeleteCtrl', function ($scope) {
        $scope.pupulateForm = function (cptevirmtBanque) {
            $scope.cptevirmtBanque = cptevirmtBanque;
        };

    });
    //End AngularJs


    var actionUrl = "/parametrages/enregistrercptevirmtbank";
    var actionDeleteUrl = "/parametrages/supprimercptevirmtbank";
    var action = "ajout";
    var $table;
    
    
    jQuery(document).ready(function ($) {
    	
    	
    	
        $table = $('#table');
        //Fermeture du modal
        $('#rhpModal').on('hidden.bs.modal', function () {
            var $scope = angular.element(document.getElementById("formAjout")).scope();
            $scope.$apply(function () {
                $scope.initForm();
            });
            //$("#id").val(""); //Initialisation de l'id
        });

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
                            //$("#formAjout")[0].reset(); //Initialisation du formulaire
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
                    $(".deleteModal .modal-body .alert p").html("Verifier que vous etes connectes au serveur.");
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
    function statutFormatter(statut){
		var optionActif = '<small class="label label-danger"><i class="fa fa-clock-o"></i> -- Desactive </small>';
		if(statut == true)
			optionActif = '<small class="label label-success"><i class="fa fa-clock-o"></i> -- Active </small>';
		
		return optionActif;
	}
    //Functions
    function typeSanctionFormatter(typeSanction){
        return typeSanction.libelle;
    }

    function optionFormatter(id, row, index) {
        var option = '<a onclick="edit(' + row.id + ')" data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier sanction [LIBELLE : ' + row.libelle + ' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
        option += '&nbsp;<a onclick="del(' + row.id + ')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer sanction [LIBELLE : ' + row.libelle + ' ]"> <span class="glyphicon glyphicon-trash"></span></a>';

        return option;
    }
    function bankFormatter(bank){
    	return bank.sigle
    }
    function edit(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formAjout")).scope();

        var rows = $table.bootstrapTable('getData');
        var cptevirmtBanque = _.findWhere(rows, {id: idTypeSaction});
        jQuery("#idbank").val(cptevirmtBanque.bank.id).trigger("change");
        $scope.$apply(function () {
            $scope.pupulateForm(cptevirmtBanque);
        });
    }
 
    function del(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formDelete")).scope();

        var rows = $table.bootstrapTable('getData');
        var cptevirmtBanque = _.findWhere(rows, {id: idTypeSaction});
        cptevirmtBanque.info = cptevirmtBanque.bank.sigle;
        $scope.$apply(function () {
            $scope.pupulateForm(cptevirmtBanque);
        });
    }
</script>
