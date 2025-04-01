<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
    <div class="col-md-12">
        <div class="panel panel-warning">
            <div class="panel-heading">
                <div class="panel-title-box">
                    <h3>Liste  des Primes attribu&eacute;s</h3>
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
                                   data-sort-name="id"
                                   data-sort-order="desc"
                                   data-url="${pageContext.request.contextPath}/personnels/listcontratpersonneljson"
                                   data-side-pagination="server"
                                   data-pagination="true"
                                   data-page-list="[10, 20, 50, 100, 200]"
                                   data-search="true">
                                <thead>
                                <tr>

                                    <th data-field="personnel" data-formatter="matriFormatter" data-align="left" data-sortable="true">Matricule</th>
                                    <th data-field="personnel" data-formatter="nomFormatter" data-align="left" data-sortable="true">Nom</th>
                                    <th data-field="personnel" data-formatter="nomfstatutFormatterL" data-align="left" data-sortable="true">Statut</th>
                                    <th data-field="personnel" data-formatter="sexeFormatter" data-align="left">Sexe</th>
                                    <th data-field="personnel" data-formatter="datnaisFormatter" data-align="center">N&eacute;(e) le</th>
                                    <th data-field="personnel"  data-formatter="lieunaisFormatter" data-align="left">A</th>
                                    <th data-field="personnel" data-formatter="telephoneFormatter" data-align="center">T&eacute;l&eacute;phone</th>
                                    <th data-field="personnel" data-formatter="situaFormatter" data-align="center">Sit. Matri</th>
                                    <th data-field="personnel" data-formatter="nbreenftFormatter" data-align="right">Nbre d'enfants</th>
                                    <th data-field="categorie" data-formatter="salcatFormatter" data-align="right">Salaire cat&eacute;goriel</th>
                                    <th data-field="netAPayer" data-align="right">Net &agrave; payer</th>
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

        <div class="modal main-popup fade" id="listAbsenceModal" ng-controller="listAbsenceCtrl" role="dialog" aria-labelledby="listAbsenceModalLabel" data-backdrop="static">
            <div class="modal-dialog" style="width:60%;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="listAbsenceModalLabel">Liste des primes individuelles</h4>
                    </div>
                    <div class="modal-body">
                        <h3>Personnel</h3>
                        <table>
                            <tbody>
                            <tr>
                                <th style="width:150px;">Matricule</th>
                                <td style="width:300px;">{{personnel.matricule}}</td>
                                <th style="width:150px;">N&deg; CNPS</th>
                                <td>{{personnel.numeroCnps}}</td>
                            </tr>
                            <tr>
                                <th>Nom</th>
                                <td>{{personnel.nomComplet}}</td>
                                <th>Sexe</th>
                                <td>{{personnel.sexe}}</td>
                            </tr>
                            <tr>
                                <th>N&eacute;(e) le</th>
                                <td>{{personnel.dNaissance}}</td>
                                <th>A</th>
                                <td>{{personnel.lieuNaissance}}</td>
                            </tr>
                            <tr>
                                <th>Situation matrimoniale</th>
                                <td>{{personnel.situationMatri}}</td>
                                <th>T&eacute;l&eacute;phone</th>
                                <td>{{personnel.telephone}}</td>
                            </tr>
                            <tr>
                                <th>Stock cong&eacute; (Jour)</th>
                                <td>{{personnel.nombreJourdu}}</td>
                                <th>Stock cong&eacute; (Montant)</th>
                                <td>{{personnel.mtcongedu}}</td>
                            </tr>
                            </tbody>
                        </table>
                        <form id="formAbsence" class="form-absence">
                            <h3 style="margin-top: 30px;">Primes individuelles</h3>
                            <input type="text" class="hidden" name="idCtrat" id="idCtrat" ng-model="idCtrat"/>

                            <br>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-4">
                                        <label>Prime</label>
                                        <select class="form-control input-small" id="idAbsence" name="idAbsence" ng-model="absenceId"  ng-init="absenceId=absenceId">
                                            <c:forEach items="${listePrimes}" var="prime">
                                                <option value="${prime.id}">${prime.libelle}</option>
                                            </c:forEach>
                                        </select>
                                    </div>



                                    <div class="col-md-3">
                                        <label >Montants <span class="required">*</span></label>
                                        <input type="text" id="montantop" class="form-control input-default"  ng-model="primePersonnel.montant"  name="montantop" placeholder="valeur" maxlength="8">
                                    </div>

                                    <div class="col-md-3">
                                        <label >Nombre d'heure Supp<span class="required"></span></label>
                                        <input type="text" id= "valeurop" class="form-control input-default"  ng-model="primePersonnel.valeur" name="valeurop"   placeholder="Heure supp." maxlength="2">
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="row">
                                    <div id="actionAbsence" class="col-md-12 text-right">
                                        <span></span>&nbsp;
                                        <input type="text" name="idPersonnel" class="hidden" ng-model="primePersonnel.contratPersonnel.id"/>
                                        <input type="text"class="hidden" ng-hide="true" name="id" ng-model="primePersonnel.id"/>
                                        <button type="button" id="btnCancelAbsence" class="btn btn-default">Annuler</button>
                                        <button type="submit" data-action="add" data-index="-1" class="btn btn-success">Valider</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <p>&nbsp;</p>
                        <div id="toolbarAbsence">
                            <div class="form-inline">
                                <button type="button" class="btn btn-primary" title="Nouvelle prime" id="btnAddAbsence"><span class="glyphicon glyphicon-plus"></span> Nouvelle prime</button>
                            </div>
                        </div>
                        <table id="tableAbsence" class="table table-info table-striped"
                               data-toggle="table"
                               data-toolbar="#toolbarAbsence"
                               data-single-select="true"
                               data-sort-order="desc"
                               data-pagination="true"
                               data-page-list="[5, 10, 20, 50, 100, 200]"
                               data-search="true">
                            <!--   data-formatter="commentaireAbsenceFormatter" -->
                            <thead>
                            <tr>
                                <th data-field="prime" data-formatter="primeFormatter">Prime</th>
                                <th data-field=mtmontant>Montant</th>
                                <th data-field="valeur">Valeur</th>
                                <th data-field="mtnVerse" data-align="right">Montant vers&eacute;</th>
                                <th data-field="id" data-formatter="optionFormatterPrime" data-align="center"></th>
                            </tr>
                            </thead>
                        </table>
                        <br>
                    </div>
                </div>
            </div>
        </div>


        <script type="text/javascript">
            //AngularJS
            app.controller('listAbsenceCtrl', function ($scope) {
                $scope.absenceId = jQuery("#idAbsence option:first").val();
                $scope.populateForm = function (personnel, primePersonnel) {
                    $scope.personnel = personnel;
                    if(primePersonnel){
                        $scope.primePersonnel = primePersonnel;
                        $scope.absenceId = primePersonnel.prime.id;
                        $scope.personnel = primePersonnel.contratPersonnel.personnel;
                        //  $scope.statutAbs = absencePersonnel.statut === true ? 'true' : 'false';
                    }
                };
                $scope.initForm = function () {
                    $scope.primePersonnel = {};
                };
                $scope.pupulateContrat = function (contrat) {
                    $scope.contrat = contrat;
                };
                $scope.populatePrime = function (prime) {
                    $scope.prime = prime;
                };

            });
            //End AngularJs

            var actionUrl = "/paie/enregisterlivrepaie";
            var actionDeleteUrl = "/paie/delpretIndividuel";
            var action = "ajout";
            var $table,$tableAbsence;
            jQuery(document).ready(function($) {
                $table = jQuery('#table');
                $(".form-absence").hide();
                $tableAbsence = $('#tableAbsence');


                jQuery( ".select2" ).select2();


            });

            $("#btnAddAbsence").click(function (e) {
                $(".form-absence").show(500);


                $("#actionAbsence button:submit").data("action", "add");
            });
            $("#btnCancelAbsence, #listAbsenceModal button.close").click(function (e) {
                $(".form-absence").hide(500);
                 jQuery("#formAbsence")[0].reset();
                var $scope = angular.element(document.getElementById("listAbsenceModal")).scope();
                $scope.$apply(function () {
                    $scope.initForm();
                });
            });
            function optionFormatter(id, row, index) {
                var option = '<a onclick="listAbsence('+row.id+','+row.personnel.id+')" data-toggle="modal" data-target="#listAbsenceModal" href="#" title="Ajouter pr�t [LIBELLE : '+row.personnel.nom+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
                option += '&nbsp;<a data-toggle="modal" href="#" title="Suprimer bulletin [LIBELLE : '+row.libelle+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';

                return option;
            }
            function nomfstatutFormatterL(personnel, row, index) {
                if(row.personnel== ''){
                    return "";
                }
                if(row.personnel.carec==false){

                    if(row.personnel.stage==true)
                        statfonct = "Stagiaire";
                    else
                        statfonct = "Consultant";
                }else{
                    statfonct = "Contractuel";
                }
                return statfonct;
                //return row.contratPersonnel.personnel.statfonct;
            }


            function matriFormatter(personnel, row, index) {
                if(row.personnel.matricule == ''){
                    return "";
                }
                return row.personnel.matricule;
            }
            function matrisFormatter(personnel, row, index) {
                if(row.personnel.matricule == ''){
                    return "";
                }
                return row.personnel.matricule;
            }
            function periodeFormatter(periode, row, index) {
                if(row.periode.mois.mois == ''){
                    return "";
                }
                return row.periode.mois.mois+' '+row.periode.annee.annee ;
            }




            $("#formAbsence").submit(function (e) {
                //alert('hhhh');/
                $("#idPersonnel").val($("#idCtrat").val());
                e.preventDefault();
                var formData = $(this).serialize();
                $.ajax({
                    type: "POST",
                    url: baseUrl + "/paie/saveprimepersonnel",
                    cache: false,
                    data: formData,
                    success: function (reponse) {
                        if (reponse.result == true) {
                            if($("#actionAbsence button:submit").data("action") == "add"){
                                $tableAbsence.bootstrapTable('prepend', reponse.row);
                            }
                            else{
                                $tableAbsence.bootstrapTable('load',reponse.rows );
                            }
                            $(".from-absence").hide(500);
                        } else if (reponse.result == false) {
                            alert("Saisie invalide");
                        }
                    },
                    beforeSend: function () {
                        $("#formAbsence").attr("disabled", true);
                        $("#actionAbsence span").addClass('loader');

                    },
                    error: function () {
                        $("#actionAbsence span").removeClass('loader');
                    },
                    complete: function () {
                        $("#formAbsence").removeAttr("disabled");
                        $("#actionAbsence span").removeClass('loader');
                        $tableAbsence.bootstrapTable('refresh');
                    }
                });
                return false;
            });

          function listAbsence(idctrat, idPersonnel) {
              var $scope = angular.element(document.getElementById("listAbsenceModal")).scope();

              var rows = $table.bootstrapTable('getData');
              var contrat = _.findWhere(rows, { id: idctrat });

              console.log("Contrat trouvé :", contrat);

              if (!contrat) {
                  console.error("Aucun contrat trouvé avec id:", idctrat);
                  return;
              }

              try {
                  if (!$scope.$$phase) { // Vérifie si un digest est en cours
                      $scope.$apply(function () {
                          $scope.populateForm(contrat, null);
                          $scope.personnel = contrat.personnel;
                          $scope.idPersonnel = contrat.id;
                          loadAbsenceByPersonnel(contrat.id);
                          $scope.idCtrat = contrat.id;
                          $("#idCtrat").val(contrat.id);
                          $("#idPersonnel").val(contrat.id);
                      });
                  } else {
                      console.warn("Digest déjà en cours, mise à jour directe du scope.");
                      $scope.populateForm(contrat, null);
                      $scope.personnel = contrat.personnel;
                      $scope.idPersonnel = contrat.id;
                       $("#idPersonnel").val(contrat.id);
                      loadAbsenceByPersonnel(contrat.id);
                      $scope.idCtrat = contrat.id;
                      $("#idCtrat").val(contrat.id);
                  }
              } catch (e) {
                  console.error("Erreur lors de l'exécution de $scope.$apply():", e);
              }

              alert($("#idCtrat").val());
          }
            function loadAbsenceByPersonnel(idPersonnel) {
                //alert(idPersonnel);
                jQuery.ajax({
                    type: "GET",
                    url: baseUrl + "/paie/searchlesprimeIndividuel1Mois",
                    cache: false,
                    data: {id: idPersonnel},
                    success: function (reponse) {
                        $tableAbsence.bootstrapTable('load', reponse.rows);

                        jQuery("#idPersonnel").val(reponse.rows[0].contratPersonnel.id);
                        //  alert(jQuery("#idPersonnel").val());
                    },
                    beforeSend: function () {
                        $tableAbsence.bootstrapTable('load', []);
                    },
                    error: function () {

                    },
                    complete: function () {

                    }
                });
            }


            function editAbsence(idAbsencePersonnel, index) {
                var $scope = angular.element(document.getElementById("listAbsenceModal")).scope();
                var rows = $tableAbsence.bootstrapTable('getData');
                var primePersonnel = _.findWhere(rows, {id: idAbsencePersonnel});

                $scope.$apply(function () {
                    $scope.populateForm(primePersonnel.contrat,primePersonnel);
                    $scope.primePersonnel=primePersonnel;

                    $scope.personnel=primePersonnel.contratPersonnel.personnel;
                });

                jQuery(".form-absence").show(500);
                jQuery("#actionAbsence button:submit").data("action", "update");
                jQuery("#actionAbsence button:submit").data("", index);
            }

            function loadMouvementCongeByPersonnel(idPersonnel) {
                jQuery.ajax({
                    type: "GET",
                    url: baseUrl + "/paie/searchlesprimeIndividuel1Mois",
                    cache: false,
                    data: {id: idPersonnel},
                    success: function (reponse) {
                        //	if(reponse.rows>0){

                        $tableConge.bootstrapTable('load', reponse.rows);
                        //	alert(reponse.rows[0].contratPersonnel.id);
                        jQuery("#idPersonnel").val(reponse.rows[0].contratPersonnel.id);
                        //   }
                    },
                    beforeSend: function () {
                        $tableConge.bootstrapTable('load', []);
                        //jQuery("#btnAddMouvementConge,.form-mouvement-conge").hide();
                    },
                    error: function () {

                    },
                    complete: function () {

                    }
                });
            }
            function loadContratPersonnel(idm) {

                var ki=idm;
                console.log(ki);
                jQuery.ajax({
                    type: "POST",
                    url: baseUrl + "/personnels/cherchcontratpersonnelActif",
                    cache: false,
                    data: {id: idm},
                    success: function (reponse) {

                        console.log(reponse.row);
                        jQuery('#idPersonnelp').val(reponse.row.personnel.id);
                        jQuery('#lpom').val(reponse.row.id);
                        // jQuery('#idP').val(reponse.row.id);
                        jQuery('#employe').html('Matricule : '+reponse.row.personnel.matricule+' '+reponse.row.personnel.nom+' '+reponse.row.personnel.prenom);
                        jQuery('#matricules').html(reponse.row.personnel.matricule);
                        jQuery('#nomcomplet').html(reponse.row.personnel.nom+' '+reponse.row.personnel.prenom);
                        jQuery('#dnaiss').html(reponse.dNaissance);
                        jQuery('#lieunaiss').html(reponse.lieuNaissance);
                        jQuery('#sexe').html(reponse.sexe);

                        //}
                        //	if(reponse.rows>0){

                        //	$t  jQuery("#idPersonnelp").val(idCtrat);ableConge.bootstrapTable('load', reponse.rows);
                        //   }
                    },
                    beforeSend: function () {
                        //  $tableConge.bootstrapTable('load', []);
                        //jQuery("#btnAddMouvementConge,.form-mouvement-conge").hide();
                    },
                    error: function () {

                    },
                    complete: function () {

                    }
                });
            }
            function pretFormatter(pret, row, index) {
                if(row.pret.libelle == ''){
                    return "";
                }
                return row.pret.libelle;
            }
            function nomFormatter(personnel, row, index) {
                if(row.personnel.nom == ''){
                    return "";
                }
                return row.personnel.nom+" "+row.personnel.prenom;
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
                if(row.categorie.salaireDeBase == ''){
                    return "";
                }
                return row.categorie.salaireDeBase;
            }

            function optionFormatterPrime(id, row, index) {
                var option = '<a href="#" onclick="javascript:editAbsence(' + id + ', '+index+')" title="Modifier"><span class="glyphicon glyphicon-pencil"></span></a>';
                option += '&nbsp;<a onclick="deleteprime('+id+')" href="#" title="Suprimer prime [LIBELLE : '+row.prime.id+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';

                return option;
            }

            function primeFormatter(prime, row, index) {
                if(row.prime.id == ''){
                    return "";
                }
                return row.prime.libelle;
            }

            function edit(idFonction){
//	var $scope = angular.element(document.getElementById("formDelete")).scope();

                var idp=idFonction;
                //alert(idp);
                jQuery.ajax({
                    type: "POST",
                    url: baseUrl+"/paie/searchprimeIndividuel1",
                    cache: false,
                    data: {id:idp},
                    success: function (response) {
                        if (response != null) {
                            console.log(response);
                            jQuery('#matricules').html(response.matricule);
                            jQuery('#nomcomplet').html(response.nom+' '+response.prenom);
                            jQuery('#dnaiss').html(response.dNaissance);
                            jQuery('#lieunaiss').html(response.lieuNaissance);
                            jQuery('#sexe').html(response.sexe);
                            jQuery('#typeserv').html(response.service.typeService.libelle);
                            jQuery('#serv').html(response.service.libelle);
                            jQuery('#idpers').val(response.id);

                            //tabledata += "";
                            /* jQuery('#typeService, #typeServicePop').html(tabledata);
                            jQuery('#typeService, #typeServicePop').val("1").trigger("change"); */
                        } else {
                            alert('Failure! An error has occurred!');
                        }
                    },
                    error: function () {

                    },
                });
                jQuery('#tablef'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/pretPersonneljson' });
            }

            function cherchprime(idFonction){
                jQuery('#lpom').val(idFonction);

                jQuery.ajax({
                    type: "POST",
                    url: baseUrl+"/paie/searchprimeIndividuel1",
                    data: {id: idFonction},
                    cache: false,
                    success: function (response) {
                        if (response != null) {
                            console.log(response);
                            jQuery("#formMouvementConge")[0].reset();
                            loadDataToForm(response.id,response.montant,response.valeur,response.prime.id,response.contratPersonnel.id);
                        } else {
                            alert('Impossible de charger cet objet');
                        }
                    },
                    error: function () {

                    },
                    complete: function () {

                    }
                });

            }

            function deleteprime(idFonction){

                jQuery.ajax({
                    type: "POST",
                    url: baseUrl+"/paie/delpretIndividuel1",
                    data: {id: idFonction},
                    cache: false,
                    success: function (response) {
                        if (response != null) {
                            //jQuery("#formMouvementConge")[0].reset();
                            $tableAbsence.bootstrapTable('load',response.rows );
                            //alert("Suppression effectuée")
                        } else {
                            alert('Impossible de charger cet objet');
                        }
                    }
                    ,
                    beforeSend: function () {
                        $tableAbsence.bootstrapTable('load', []);
                        //jQuery("#btnAddMouvementConge,.form-mouvement-conge").hide();
                    },
                    error: function () {

                    },
                    complete: function () {
                        $tableAbsence.bootstrapTable('refresh' );
                    }
                });

            }



            function loadDataToForm(id, montant, valeur,idPrime,idCtrat){
                jQuery("#idP").val(id);

                jQuery("#lpom").val(idCtrat);
                if(idPrime == null){
                    jQuery("#primePop").trigger('liszt:updated');
                    jQuery("#primePop option:selected").val()
                } else{
                    jQuery("#primePop").val(idPrime);
                    jQuery("#primePop").val(idPrime).trigger('change');
                    jQuery("#primePop").trigger('liszt:updated');
                }
                if(montant==null){
                    jQuery("#montantpop").val(0);
                }else{
                    jQuery("#montantpop").val(montant);
                }

                jQuery("#valeurpop").val(valeur);
                //alert(jQuery('#montant').val());
            }

               </script>