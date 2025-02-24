<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
    <div class="col-md-12">
        <div class="panel panel-warning">
            <div class="panel-heading">
                <div class="panel-title-box">
                    <h3>Liste des Demandes de Formation</h3>

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
                   data-sort-name="libelle" data-sort-order="desc"
                   data-url="${pageContext.request.contextPath}/formation/paginerdemandeformations"
                   data-side-pagination="server"
                   data-pagination="true"
                   data-page-list="[5, 10, 20, 50, 100, 200]"
                   data-search="false">
                <thead>
                    <tr>
                        <th data-field="exercice" data-formatter="exerciceFormatter">Ann&eacute;e de Demande</th>
                        <th data-field="service" data-formatter="serviceFormatter">Direction/Departement/Service</th>

                        <th data-field="dDemande">Date de demande</th>
                        <th data-field="objet">Objet</th>
                        <th data-field="commentaire">Resultats attendus</th>
                        <th data-field="etatDde" data-formatter="statutFormatter">Statut </th>
                        <th data-field="id" data-formatter="optionFormatter" data-width="100px" data-align="center">Options</th>
                    </tr>
                </thead>
            </table>
        </div><!--widgetcontent-->
    </div><!--widgetbox-->
</div><!-- widgetcontent-->
            </div>
        </div>
<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formAjout" name="formAjout" class="form-horizontal" role="form" novalidate="novalidate" ng-controller="formAjoutCtrl">
                <div class="modal-header">
                    <!-- <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Demande de formation</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="idAnnee" class="col-md-3 control-label">Ann&eacute;e de la Demande</label>
                        <div class="col-md-9">
                            <select id="idAnnee" name="idAnnee" class="form-control select2"  ng-required="true" ng-model="demandeformation.exercice.id">
                            <option value="0">Choisir une Ann&eacute;e</option>
                            <c:forEach items="${listeAnnee}" var="annee" >
                                <option value="${annee.id}">${annee.annee}</option>
                            </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="idService" class="col-md-3 control-label">Service</label>
                        <div class="col-md-9">
                            <select class="form-control select2" id="idService" name="idService"  ng-required="true" ng-model="demandeformation.service.id" >
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dateDemande" class="col-md-3 control-label">Date de demande</label>
                        <div class="col-md-9">
                            <input type="text" ng-required="true" class="form-control input-small datePicker" id="dateDemande" name="dateDemande" placeholder="Date de demande" maxlength="10"  ng-model="demandeformation.dDemande">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="objet" class="col-md-3 control-label">Objet</label>
                        <div class="col-md-9">
                            <input type="text" ng-required="true" id="objet" name="objet" ng-model="demandeformation.objet" class="form-control" placeholder="Objet" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="commentaire" class="col-md-3 control-label">Observations</label>
                        <div class="col-md-9">
                            <textarea class="form-control" id="description" name="commentaire" ng-model="demandeformation.commentaire" placeholder="Commentaire">
                                
                            </textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="demandeformation.id">
                    <span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success" ng-disabled="formAjout.$invalid" >Valider</button>
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
                    <h4 ng-bind="demandeformation.info"></h4>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="demandeformation.id">
                    <span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>

        </form> </div>
    </div>
</div>
<div class="modal valideModal  fade bs-valide-modal-static" role="dialog" data-backdrop="static">
    <div class="modal-dialog ">
        <div class="modal-content">
            <form id="formValide" ng-controller="formValideCtrl" action="#" method="post">
                <div class="modal-header ">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <span class="circle bg-danger">
                        <i class="fa fa-question-circle"></i>
                        Etes vous sur de vouloir valider cette demande de formation ?
                    </span>
                </div>
                <div class="modal-body">
                    <h4 ng-bind="demandeformation.info"></h4>
                    <br>
                    <div class="form-group">
                        <label for="dateDemande" class="col-md-3 control-label">Date de Validation</label>
                        <div class="col-md-9">
                            <input type="text" ng-required="true" class="form-control input-small datePicker" id="dateValide" name="dateValide" placeholder="Date de Validation" maxlength="10"  >
                        </div>
                    </div>
                    <br>
                </div>
                <div class="modal-footer">

                    <input type="text" class="hidden" ng-hide="true" value="" name="idde" id="idde">
                    <input type="text" class="hidden" ng-hide="true" value="2" name="touche" id="touche">
                    <input type="text" class="hidden" ng-hide="true" value="3" name="jalios" id="jalios">
                    <span></span>&nbsp;
                    <button type="button" class="btn btn-default"  id="refuse" data-dismiss="modal" >Refuser</button>
                    <button type="button" class="btn btn-success"  id="accepter"  >Valider</button>
                </div>

        </form> </div>
    </div>
</div>

<script type="text/javascript">
    //AngularJS
    app.controller('formAjoutCtrl', function ($scope) {
        $scope.pupulateForm = function (demandeformation) {
            $scope.demandeformation = demandeformation;
        };
        $scope.initForm = function () {
            $scope.demandeformation = {};
        };
    }).controller('formValideCtrl', function ($scope) {
        $scope.pupulateForm = function (demandeformation) {
            $scope.demandeformation = demandeformation;
        };
    }) .controller('formDeleteCtrl', function ($scope) {
        $scope.pupulateForm = function (demandeformation) {
            $scope.demandeformation = demandeformation;
        };
    });
    //End AngularJs


    var actionUrl = "/formation/enregistrerdemandeformation";
    var actionDeleteUrl = "/formation/supprimerdemandeformation";
    var actionValideUrl = "/formation/validerdemandeformation";
    var actionRefuserUrl = "/formation/refuserdemandeformation";
    var action = "ajout";
    var $table;
    jQuery(document).ready(function ($) {
        $table = $('#table');
        $(".select2").select2();
        $(".datePicker").datepicker({
            dateFormat: 'dd/mm/yy',
            showOtherMonths: true
        });
        loadService();
        //Fermeture du modal
        $('#rhpModal').on('hidden.bs.modal', function () {
            var $scope = angular.element(document.getElementById("formAjout")).scope();
            $scope.$apply(function () {
                $scope.initForm();
            });
            //$("#id").val(""); //Initialisation de l'id
        });

        $("#idService").change(function () {
            var typeService = $("#idService option:selected").data("type");
            $serviceLabel = jQuery("label[for='idService']");
            switch (typeService) {
                case 2 : //Departement
                    $serviceLabel.html('Departement');
                    break;
                case 3 : //Service
                    $serviceLabel.html('Service');
                    break;
                default : //Direction
                    $serviceLabel.html('Direction');
                    break;
            }
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
                            toastr.success("enregistrement reussie ");
                        } else {
                            //MAJ de la ligne modifi�e
                            $table.bootstrapTable('updateRow', {
                                index: $table.find('tr.selected').data('index'),
                                row: reponse.data
                            });toastr.success("modification reussie ");
                        }
                    } else {
                        alert(reponse.message);toastr.error("erreur");
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
                        $(".deleteModal").modal('hide');toastr.success("suppression reussie ");
                    } else if (reponse.result == "erreur_champ_obligatoire") {
                        alert("Saisie invalide");toastr.error("erreur ");
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
        $("#accepter").on('click',function (e) {
                e.preventDefault();
                var formData = $(this).serialize();
            var dateV = $('#formValide')
                .find('#dateValide')
                .val();
            var jalios = $('#formValide')
                .find('#jalios')
                .val();
            var id = $('#formValide')
                .find('#idde')
                .val();
                $.ajax({
                    type: "POST",
                    url: baseUrl + actionValideUrl,
                    cache: false,
                    data: {
                        id:id,
                        dateValide:dateV,
                        jalios:jalios
                    },
                    success: function (reponse) {
                        if (reponse.result == true) {

                            $(".valideModal").modal('hide');toastr.success("validation reussie ");
                            $table.bootstrapTable('refresh');
                        } else if (reponse.result == "erreur_champ_obligatoire") {
                            alert("Saisie invalide");
                            toastr.error("erreur ");
                        }
                    },
                    error: function (err) {
                        $(".valideModal .modal-body div.alert").alert();
                        $(".valideModal .modal-body .alert h4").html("Erreur survenue");
                        $(".valideModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
                        $(".valideModal .modal-footer span").removeClass('loader');
                    },
                    beforeSend: function () {
                        $("#formValide").attr("disabled", true);
                        $(".valideModal .modal-footer span").addClass('loader');
                    },
                    complete: function () {
                        $("#formValide").removeAttr("disabled");
                        $(".valideModal .modal-footer span").removeClass('loader');
                    }
        });

      });
        $("#refuse").on('click',function (e) {
            e.preventDefault();
            var formData = $(this).serialize();
            var dateV = $('#formValide')
                .find('#dateValide')
                .val();
            var touche = $('#formValide')
                .find('#touche')
                .val();
            var id = $('#formValide')
                .find('#idde')
                .val();
            // var idSup = [];
            // //Le formulaire formDelete doit avoir un seul champ input:text
            // idSup.push(parseInt($("#formDelete :text").val()));

            $.ajax({
                type: "POST",
                url: baseUrl + actionRefuserUrl,
                cache: false,
                data: {
                    id:id,
                    dateValide:dateV,
                    touche:touche
                },
                success: function (reponse) {
                    if (reponse.result == true) {
                        $table.bootstrapTable('refresh');
                        $(".valideModal").modal('hide');
                        toastr.success("validation reussie ");
                    } else if (reponse.result == "erreur_champ_obligatoire") {
                        alert("Saisie invalide");
                        toastr.error("erreur ");
                    }
                },
                error: function (err) {
                    $(".valideModal .modal-body div.alert").alert();
                    $(".valideModal .modal-body .alert h4").html("Erreur survenue");
                    $(".valideModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
                    $(".valideModal .modal-footer span").removeClass('loader');
                },
                beforeSend: function () {
                    $("#formValide").attr("disabled", true);
                    $(".valideModal .modal-footer span").addClass('loader');
                },
                complete: function () {
                    $("#formValide").removeAttr("disabled");
                    $(".valideModal .modal-footer span").removeClass('loader');
                }
            });

        });
    });
    //Functions
    function serviceFormatter(service, row) {
        if (service.typeService.id === 2) { //Departement
            return service.serviceParent.libelle;
        }
        if (service.typeService.id === 3) { //Service
            return service.serviceParent.serviceParent.libelle;
        }
        //Direction
        return service.libelle;
    }
    function statutFormatter(statut){
        var optionActif = '<small class="label label-info"><i class="fa fa-clock-o"></i> -- SAISIE </small>';
        if(statut == 3)
            optionActif = '<small class="label label-success"><i class="fa fa-clock-o"></i> -- VALIDER </small>';
        if(statut == 2)
            optionActif = '<small class="label label-warning"><i class="fa fa-clock-o"></i> --NON  VALIDER </small>';
        return optionActif;
    }
    function exerciceFormatter(exercice, row) {
        // if (service.typeService.id === 2) { //Departement
        //     return service.serviceParent.libelle;
        // }
        // if (service.typeService.id === 3) { //Service
        //     return service.serviceParent.serviceParent.libelle;
        // }
        //Direction
        return exercice.annee;
    }

    function optionFormatter(id, row, index) {
        var option ='';

        if(row.etatDde == 2 || row.etatDde == 1)
        { option += '&nbsp;<a onclick="valider(' + row.id + ')" data-toggle="modal" data-target=".valideModal" href="#" title="Valider Demande de formation [LIBELLE : ' + row.objet + ' ]"> <span class="glyphicon glyphicon-barcode"></span></a>';
        }
        option += '<a onclick="edit(' + row.id + ')" data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier Demande de formation [LIBELLE : ' + row.objet + ' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
        option += '&nbsp;<a onclick="del(' + row.id + ')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer Demande de formation [LIBELLE : ' + row.objet + ' ]"> <span class="glyphicon glyphicon-trash"></span></a>';

        return option;
    }

    function edit(idDemandeFormation) {
        var $scope = angular.element(document.getElementById("formAjout")).scope();

        var rows = $table.bootstrapTable('getData');
        var demandeformation = _.findWhere(rows, {id: idDemandeFormation});

        $scope.$apply(function () {
            $scope.pupulateForm(demandeformation);
        });
        jQuery("#idService").val(demandeformation.service.id).trigger('change');
        jQuery("#idAnnee").val(demandeformation.exercice.id).trigger('change');
    }

    function valider(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formValide")).scope();

        var rows = $table.bootstrapTable('getData');
        var demandeformation = _.findWhere(rows, {id: idTypeSaction});
        console.log("ddformation",demandeformation);
        demandeformation.info = demandeformation.objet;
        jQuery("#idde").val(demandeformation.id);
        $scope.$apply(function () {
            $scope.pupulateForm(demandeformation);
        });
    }
    function del(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formDelete")).scope();

        var rows = $table.bootstrapTable('getData');
        var demandeformation = _.findWhere(rows, {id: idTypeSaction});
        console.log("ddformation",demandeformation);
        demandeformation.info = demandeformation.objet;
        $scope.$apply(function () {
            $scope.pupulateForm(demandeformation);
        });
    }

    function loadService() {
        var $selectService = jQuery("#idService");
        $selectService.html('');
        jQuery.getJSON(baseUrl + "/personnels/listservicejson",
                function (reponse) {
                    jQuery.each(reponse.rows,
                            function (index, row) {
                                $selectService.append('<option value="' + row.id + '" data-type="' + row.typeService.id + '"> ' + row.libelle + '</option>');
                            });
                    if (reponse.rows.length > 0) {
                        $selectService.val(reponse.rows[0].id).trigger("change");
                        $serviceLabel = jQuery("label[for='idService']");
                        switch (reponse.rows[0].typeService.id) {
                            case 2 : //Departement
                                $serviceLabel.html('Departement');
                                break;
                            case 3 : //Service
                                $serviceLabel.html('Service');
                                break;
                            default : //Direction
                                $serviceLabel.html('Direction');
                                break;
                        }
                    }
                }
        );
    }
</script>
