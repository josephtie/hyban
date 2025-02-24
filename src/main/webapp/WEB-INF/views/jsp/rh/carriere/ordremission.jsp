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

                    <li class="divider"></li>
                    <li><a href="#" data-toggle="modal" data-target="#rhpModal">Nouveau</a></li>
                </ul>
            </div>
            <h4 id="widgetTitle" class="widgettitle">Liste du personnel </h4>
        </div>
        <div id="tableWidget" class="widgetcontent">
            <table id="table" class="table table-info table-striped"
                   data-toggle="table" data-click-to-select="true"
                   data-single-select="false"
                   data-sort-name="id"
                   data-sort-order="desc"
                   data-url="${pageContext.request.contextPath}/personnels/listcontratpersonnelActifjson"
                   data-side-pagination="server"
                   data-pagination="true"
                   data-page-list="[10, 20, 50, 100, 200,500,1000]"
                   data-search="true">
                <thead>
                <tr>

                    <th data-field="personnel" data-formatter="matriFormatter" data-align="left" data-sortable="true">Matricule</th>
                    <th data-field="personnel" data-formatter="nomFormatter" data-align="left" data-sortable="true">Nom</th>
                    <th data-field="personnel" data-formatter="sexeFormatter" data-align="left">Sexe</th>
                    <th data-field="personnel" data-formatter="datnaisFormatter" data-align="center">N&eacute;(e) le</th>
                    <th data-field="personnel"  data-formatter="lieunaisFormatter" data-align="left">A</th>
                    <th data-field="personnel" data-formatter="telephoneFormatter" data-align="center">T&eacute;l&eacute;phone</th>
                    <th data-field="personnel" data-formatter="situaFormatter" data-align="center">Sit. Matri</th>
                    <th data-field="personnel" data-formatter="nbreenftFormatter" data-align="right">Nbre d'enfants</th>
                    <th data-field="personnel" data-formatter="salcatFormatter" data-align="right">Salaire cat&eacute;goriel</th>
                    <th data-field="netPayer" data-align="right">Net &agrave; payer</th>
                    <th data-field="personnel" data-formatter="sommeilFormatter" data-align="right">En sommeil</th>
                    <th data-field="id" data-formatter="optionFormatter" data-width="80px" data-align="center">Options</th>

                    <!-- <th data-field="state" data-checkbox="true"></th> -->

                </tr>
                </thead>
            </table>
        </div><!--widgetcontent-->

    </div><!--widgetbox-->
</div><!-- widgetcontent-->

<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document" style="width:65%;">
        <div class="modal-content">
            <form id="formAjout" class="form-horizontal" role="form" novalidate="novalidate" ng-controller="formAjoutCtrl">
                <div class="modal-header">
                    <!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Ordre de Mission de :  {{contrat.personnel.nomComplet}}</h4>
                    <input type="text" class="hidden" ng-hide="true" value="{{contrat.personnel.id}}"  id="idPersonnel" name="idPersonnel" ng-model="missionPersonnel.personnel.id">

                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-7">
                            <div class="widgetbox" >
                            <div class="headtitle">
                                <h4 class="widgettitle">Informations sur la mission</h4>
                            </div>
                            <div class="widgetcontent">
                    <div class="row">
                        <div class="form-group">
                            <div class="col-md-1"></div>
                            <div class="col-md-10">
                                <label  class=" control-label"> Choisir Mission </label>
                                <%--<select id="lignebudget" name="lignebudget" class="form-control select2"    ng-model="documents.ligneBudgets" ng-required="true"></select></p>--%>
                                <select id="idMission" name="idMission" class="form-control select2">
                                    <%--<option value="0"></option>--%>
                                    <%--<c:forEach items="${listmission}" var="mission">--%>
                                        <%--<option value="${mission.id}">${mission.natureMission} </option>--%>
                                    <%--</c:forEach>--%>
                                </select>
                            </div><div class="col-md-1"></div>
                        </div>
                    <div class="form-group">

                        <div class="col-md-10">
                            <label  class=" control-label"> Mission </label>
                         <textarea name="natureMission" id="natureMission" cols="50" rows="4"  ng-model="mission.natureMission"></textarea>
                        </div>
                    </div>
                    </div>
                     <div class="row">
                    <div class="form-group">
                        <div class="col-md-1"> </div>
                        <div class="col-md-5">
                            <label for="description" class=" control-label">Ordonnateur</label>
                            <input type="text" class="form-control" id="service" name="service" ng-model="mission.service" placeholder="Service Demandeur" />
                       </div>
                        <div class="col-md-5">
                            <label  class=" control-label">Imputation Budgetaire</label>
                            <input type="text" class="form-control" id="ImputationBudg" name="ImputationBudg" ng-model="mission.imputationBudg" placeholder="Imputation Budgetaire" />
                        </div>
                    </div>
                    </div>
                    <%--<div class="row">--%>
                        <%--<div class="form-group">--%>

                            <%--<div class="col-md-6">--%>
                                <%--<label  class=" control-label">Moyen de Transport</label>--%>
                                <%--<input type="text" class="form-control" id="moyenTransp" name="moyenTransp" ng-model="mission.moyenTransp" placeholder="Moyen de Transport" />--%>
                            <%--</div>--%>
                            <%--<div class="col-md-6">--%>
                                <%--<label  class=" control-label">Imputation Budgetaire</label>--%>
                                <%--<input type="text" class="form-control" id="ImputationBudg" name="ImputationBudg" ng-model="mission.imputationBudg" placeholder="Imputation Budgetaire" />--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="row">
                        <div class="form-group">
                            <div class="col-md-1"> </div>
                            <div class="col-md-5">
                                <label  class=" control-label">Nom autorit&eacute;</label>

                                <input type="text" class="form-control" id="nomAutorite" name="nomAutorite" ng-model="mission.nomAutorite" placeholder="Nom autorit&eacute;" />
                            </div>
                            <div class="col-md-5">
                                <label  class=" control-label">Titre Autorite</label>
                                <input type="text" class="form-control" id="titreAutorite" name="titreAutorite" ng-model="mission.titreAutorite" placeholder="Titre Autorite" />
                            </div>
                        </div>
                    </div>
                        </div>
                        </div>
                        </div>
                    <div class="col-lg-5">
                        <div class="row">
                            <div class="form-group">
                                <div class="col-md-6">
                                    <label  class=" control-label"> Fonction  </label>
                                    <input type="text" class="form-control" id="fonctionMission" name="fonctionMission" ng-model="missionPersonnel.fonctionMission" value="{{contrat.fonction.libelle}}" placeholder="Fonction "  readonly="readonly"/>
                                </div>
                                <div class="col-md-6">
                                    <label  class=" control-label"> Responsabilit&eacute; dans la mission</label>
                                    <input type="text" class="form-control" id="respMission" name="respMission" ng-model="missionPersonnel.respMission" placeholder="Responsabilit&eacute; dans la mission" />
                                </div>

                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <div class="col-md-8">
                                    <label  class=" control-label"> Lieu de la mission </label>
                                    <input type="text" class="form-control" id="lieuMission" name="lieuMission" ng-model="missionPersonnel.lieuMission" placeholder="Lieu de la mission" />
                                </div>


                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">

                                <div class="col-md-6">
                                    <label for="description" class=" control-label">Date de depart</label>
                                    <input type="text" class="form-control" id="dDebut" name="dDebut" ng-model="missionPersonnel.dDebut" placeholder="Date de depart" />
                                </div>
                                <div class="col-md-6">
                                    <label  class=" control-label">Date de retour</label>
                                    <input type="text" class="form-control" id="dRet" name="dRet" ng-model="missionPersonnel.dRet" placeholder="Date de retour" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">

                                <div class="col-md-6">
                                    <label for="description" class=" control-label">Nombre de jour</label>
                                    <input type="number" class="form-control" id="nbrejour" name="nbrejour" ng-model="missionPersonnel.nbrejour" placeholder="Nombre de jour" />
                                </div>
                                <div class="col-md-6">
                                    <label  class=" control-label">Nombre de Nuit&eacute;s</label>
                                    <input type="number" class="form-control" id="nbrenuite" name="nbrenuite" ng-model="missionPersonnel.nbrenuite" placeholder="Nombre de Nuit&eacute;s" />
                                </div>
                            </div>
                        </div>
                        <%--<div class="row">--%>
                        <%--<div class="form-group">--%>

                        <%--<div class="col-md-6">--%>
                        <%--<label  class=" control-label">Moyen de Transport</label>--%>
                        <%--<input type="text" class="form-control" id="moyenTransp" name="moyenTransp" ng-model="mission.moyenTransp" placeholder="Moyen de Transport" />--%>
                        <%--</div>--%>
                        <%--<div class="col-md-6">--%>
                        <%--<label  class=" control-label">Imputation Budgetaire</label>--%>
                        <%--<input type="text" class="form-control" id="ImputationBudg" name="ImputationBudg" ng-model="mission.imputationBudg" placeholder="Imputation Budgetaire" />--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <div class="row">
                            <div class="form-group">

                                <div class="col-md-8">
                                    <label  class=" control-label">Moyen de Transport</label>

                                    <input type="text" class="form-control" id="moyenTransp" name="moyenTransp" ng-model="missionPersonnel.nomAutorite" placeholder="Moyen de Transport;" />
                                </div>

                                <div class="col-md-4">
                                    <label  class=" control-label">Montant Journalier</label>
                                    <input type="text" class="form-control" id="montant" name="montant" ng-model="missionPersonnel.montant" placeholder="Montant" />
                                </div>
                            </div>
                        </div>
                        <%--<div class="row">--%>
                            <%--<div class="form-group">--%>

                                <%--<div class="col-md-7">--%>
                                    <%--<label  class=" control-label">Montant Journalier</label>--%>
                                    <%--<input type="text" class="form-control" id="montant" name="montant" ng-model="missionPersonnel.montant" placeholder="Montant" />--%>
                                <%--</div>--%>
                                <%--<div class="col-md-5">--%>

                                <%--</div>--%>

                            <%--</div>--%>
                        <%--</div>--%>
                    </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="missionPersonnel.id">
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
                    <h4 ng-bind="missionPersonnel.info"></h4>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="missionPersonnel.id">
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
        $scope.populateForm = function (contrat,missionPersonnel) {
            $scope.contrat = contrat;
            if(missionPersonnel){
                $scope.missionPersonnel = missionPersonnel;
                $scope.absenceId = primePersonnel.prime.id;
                $scope.personnel = missionPersonnel.personnel;
                //  $scope.statutAbs = absencePersonnel.statut === true ? 'true' : 'false';
            }
        };
        $scope.initForm = function () {
            $scope.missionPersonnel = {};
        };
    }).controller('formDeleteCtrl', function ($scope) {
        $scope.populateForm = function (mission) {
            $scope.mission = mission;
        };

    });
    //End AngularJs


    var actionUrl = "/absence/enregistrerordremission";
    var actionDeleteUrl = "/absence/supprimermission";
    var action = "ajout";
    var $table;
    jQuery(document).ready(function ($) {
        $table = $('#table');$tablem = $('#tablem');$(".select2" ).select2();
        // jQuery('#tableMission').hide();
        // jQuery('#tableWidget').show();
        jQuery("#dDebut").datepicker({
            dateFormat: 'dd/mm/yy',
            showOtherMonths:true
        });
        jQuery("#dRet").datepicker({
            dateFormat: 'dd/mm/yy',
            showOtherMonths:true
        });

        $( "#dDebut" ).datepicker({ dateFormat: 'dd-mm-yy' });
        $( "#dRet" ).datepicker({ dateFormat: 'dd-mm-yy' });
        //Fermeture du modal
        $('#rhpModal').on('hidden.bs.modal', function () {
            var $scope = angular.element(document.getElementById("formAjout")).scope();
            $scope.$apply(function () {
                $scope.initForm();
            });
            //$("#id").val(""); //Initialisation de l'id
        });
        chargerMission();
        $('#idMission').change(function() {
            var i = $('#idMission').val();

            loadMissionById(i);
        });
        function process(str){
            var parts = str.split("/");
            console.log("date",new Date(parts[2], parts[1] - 1, parts[0]));
            return new Date(parts[2], parts[1] - 1, parts[0]);
        }
        $('#dRet').change(function() {

            //var i = $('#idMission').val();
            var start = $('#dDebut').datepicker('getDate');
            var end   = $('#dRet').datepicker('getDate');
           // var date1 =   new Date(jQuery("#dDebut").datepicker('getDate'));
            console.log("date1",start);
           // var date2 = new Date(jQuery("#dRet").datepicker('getDate'));
            console.log("date2",end);
            var days   = (end - start)/1000/60/60/24;
            console.log("resut",days);
          //  var date2 = new Date($('#dRet').val());
       // var nbr=    Math.round((process(date2)-process(date1))/(1000*60*60*24));
       //       var Difference_In_Time =   date2.getTime()-date1.getTime();
       //       console.log("difference",Difference_In_Time);
       //       var Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
       //      console.log("difference jours",Difference_In_Days);
       //       var In_Days = Math.round(Math.abs(Difference_In_Days));;
            $('#nbrejour').val(days+1);
            $('#nbrenuite').val(days);
           // loadMissionById(i);
        })
        function datediff(first, second) {
            // Take the difference between the dates and divide by milliseconds per day.
            // Round to nearest whole number to deal with DST.
            return Math.round((second-first)/(1000*60*60*24));
        }
        function chargerMission(){
            $.ajax({
                type: "POST",
                url: baseUrl + "/absence/listermission",
                cache: false,
                success: function (reponse) {
                    if (reponse != null) {
                        tabledata = '<option value="0" data-libelle="CHOISIR MISSION" >CHOISIR MISSION</option>';
                        console.log("hhkop",reponse);
                        for (i = 0; i < reponse.rows.length; i++) {
                          //  alert("jjjjj");
                            tabledata += '<option value="'+reponse.rows[i].id+'" >' + reponse.rows[i].natureMission + ' (' + reponse.rows[i].service + ')</option>';
                        }
                      //  $('#idMission').select2('val', 0);
                        $('#idMission').html(tabledata);
                        //   jQuery('#choixLigne').select2('val', 0);
                    } else {
                        alert('Failure! An error has occurred!');
                    }
                },
                error: function () {

                },
                complete: function () {

                }
            });
        }

        function loadMissionById(idPersonnel){
            $("#natureMission").val("");
            $("#service").val("");
            $("#ImputationBudg").val("");
            $("#titreAutorite").val("");
            $("#nomAutorite").val("");
            $.ajax({
                type: "POST",
                url: baseUrl + "/absence/trouvermission",
                cache: false,
                data: {id: idPersonnel},
                success: function (reponse) {
                    //	if(reponse.rows>0){
                    // var $scope = angular.element(document.getElementById("formAjout")).scope();

                    //  var rows = reponse.row;
                    var mission = {};reponse.row;//_.findWhere(rows, {id: idTypeSaction});
                    mission=reponse.row;
                    $("#natureMission").val(mission.natureMission);
                    $("#service").val(mission.service);
                    $("#ImputationBudg").val(mission.imputationBudg);
                    $("#titreAutorite").val(mission.titreAutorite);
                    $("#nomAutorite").val(mission.nomAutorite);
                    console.log("row value",mission);

                    // $tableRegl.bootstrapTable('load', reponse.rows);
                    //   }
                },
                beforeSend: function () {
                    // $tableRegl.bootstrapTable('load', []);
                    // jQuery("#btnAddAbsence,.form-mouvement-conge").hide();
                },
                error: function () {

                },
                complete: function () {

                }
            });
        }
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
                            $tablem.bootstrapTable('refresh');
                            //$("#formAjout")[0].reset(); //Initialisation du formulaire
                            $("#rhpModal").modal('hide');
                            toastr.success("enregistrement reussie ");
                        } else {
                            //MAJ de la ligne modifi�e
                            $tablem.bootstrapTable('updateRow', {
                                index: $table.find('tr.selected').data('index'),
                                row: reponse.data
                            });
                            toastr.success("modification reussie ");
                        }
                       // location.href(baseUrl+"/absence/listordremission");
                        window.open(baseUrl+"/absence/listordremission");
                    } else{
                        alert(reponse.message);
                        toastr.error("erreur ");
                    }
                },
                error: function () {
                    $("#rhpModal .modal-body div.alert").alert();
                    $("#rhpModal .modal-body .alert h4").html("Erreur survenue");
                    $("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
                    $("#rhpModal .modal-footer span").removeClass('loader');toastr.error("erreur ");
                },
                beforeSend: function () {
                    $("#formAjout").attr("disabled", true);
                    $("#rhpModal .modal-footer span").addClass('loader');
                },
                complete: function () {
                    $("#formAjout").removeAttr("disabled");
                    $("#rhpModal .modal-footer span").removeClass('loader');
                    jQuery('#tableMission').show();
                    jQuery('#tableWidget').hide();
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
                    if (reponse.status == true) {
                        $table.bootstrapTable('remove', {
                            field: 'id',
                            values: idSup
                        });
                        $(".deleteModal").modal('hide');   toastr.success("suppression reussie ");
                    } else if (reponse.result == "erreur_champ_obligatoire") {
                        alert("Saisie invalide");   toastr.error("erreur");
                    }
                },
                error: function (err) {
                    $(".deleteModal .modal-body div.alert").alert();
                    $(".deleteModal .modal-body .alert h4").html("Erreur survenue");
                    $(".deleteModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
                    $(".deleteModal .modal-footer span").removeClass('loader');toastr.error("erreur");
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

    //Functions
    function matriFormatter(personnel, row, index) {
        if(row.personnel == ''){
            return "";
        }
        return row.personnel.matricule;
    }

    function nomFormatter(personnel, row, index) {
        if(row.personnel== ''){
            return "";
        }
        return row.personnel.nom+" "+row.personnel.prenom;
    }

    function nomstatutFormatter(contratPersonnel, row, index) {
        if(row.contratPersonnel== ''){
            return "";
        }
        if(row.contratPersonnel.personnel.carec==true){
            if(row.contratPersonnel.personnel.stage==true)
                statfonct = "Stagiaire";
            else
                statfonct = "Fonctionnaire";
        }else{
            statfonct = "Contractuel";
        }
        return statfonct;
    }

    function nomfstatutFormatter(contratPersonnel, row, index) {
        if(row.contratPersonnel== ''){
            return "";
        }
        if(row.contratPersonnel.personnel.carec==true){

            if(row.contratPersonnel.personnel.stage==true)
                statfonct = "Stagiaire";
            else
                statfonct = "Fonctionnaire";
        }else{
            statfonct = "Contractuel";
        }
        return statfonct;
        //return row.contratPersonnel.personnel.statfonct;
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
    function lieunaisFormatter(personnel, row, index) {
        if(row.personnel.lieuNaissance == ''){
            return "";
        }
        return row.personnel.lieuNaissance;
    }
    function telephoneFormatter(personnel, row, index) {
        if(row.personnel.telephone == ''){
            return "";
        }
        return row.personnel.telephone;
    }

    function situaFormatter(personnel, row, index) {
        if(row.personnel.situationMatrimoniale == ''){
            return "";
        }
        return row.personnel.situationMatri;
    }

    function nbreenftFormatter(personnel, row, index) {
        if(row.personnel.nombrEnfant == ''){
            return "";
        }
        return row.personnel.nombrEnfant;
    }
    function salcatFormatter(categorie, row, index) {
        if(row.categorie.salaireBase == ''){
            return "";
        }
        return row.categorie.salaireBase;
    }
    function sommeilFormatter(personnel, row, index) {

        optionActif = '<small class="label label-success"><i class="fa fa-clock-o"></i> -- Activ&eacute; </small>';
        if(row.personnel.statut == false)
            var optionActif = '<small class="label label-danger"><i class="fa fa-clock-o"></i> -- Desactiv&eacute; </small>';

        return optionActif;
    }
    function optionFormatter(id, row, index) {
        var option = '<a onclick="edit(' + row.id + ')" data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier mission [LIBELLE : ' + row.libelle + ' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
        option += '&nbsp;<a onclick="del(' + row.id + ')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer mission [LIBELLE : ' + row.libelle + ' ]"> <span class="glyphicon glyphicon-trash"></span></a>';

        return option;
    }

    function edit(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formAjout")).scope();

        var rows = $table.bootstrapTable('getData');
        var contrat = _.findWhere(rows, {id: idTypeSaction});
        console.log("dddd",contrat);
        $scope.$apply(function () {
            $scope.populateForm(contrat);
        });
    }

    function del(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formDelete")).scope();

        var rows = $table.bootstrapTable('getData');
        var mission = _.findWhere(rows, {id: idTypeSaction});
        mission.info = mission.natureMission;
        $scope.$apply(function () {
            $scope.populateForm(mission);
        });
    }
</script>
