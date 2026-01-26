<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:url value="/" var="contextPath"/>
<div class="row" >
    <div class="col-md-12">
        <div class="panel panel-warning">
            <div class="panel-heading">
                <div class="panel-title-box">
                    <h3>Liste du personnel non standard</h3>
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
                                        <li><a href="#"   data-toggle="modal" data-target="#rhpModal">Nouveau</a></li>
                                        <%--<li><a href="${pageContext.request.contextPath}/membre/addmembre"  >Nouveau</a></li>--%>
                                    </ul>
                                </div>

                            </div>
            <table id="table" class="table table-info table-striped"
                   data-toggle="table"
                   data-click-to-select="true"
                   data-single-select="true"
                   data-url="${pageContext.request.contextPath}/membre/paginermembres"
                   data-side-pagination="server"
                   data-pagination="true"
                   data-show-export="true"
                   data-export-dataType="all"
                   data-show-columns="true"
                   data-page-list="[5, 10, 20, 50, 100, 200]"
                   data-search="true">
                <thead>
                    <tr>
                        <th data-field="matricule">Matricule</th>
                        <th data-field="civil">Civilite</th>
                        <th data-field="nomComplet">Nom & Pr&eacute;noms</th>
                        <th data-field="sexe">Sexe</th>
                        <th data-field="lieuHabitation">Lieu d'habitation</th>
                        <th data-field="cellulaire">Cellulaire</th>
                       <th data-field="fonction" >Fonction</th>
                        <th data-field="cotise" data-formatter="cotiseFormatter" data-align="left" >Cotise</th>
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
    <div class="modal-dialog modal-dialog-scrollable" style="width:60%;">
        <div class="modal-content">
            <form id="formAjout" name="formAjout" class="form-horizontal" novalidate="novalidate" role="form" ng-controller="formAjoutCtrl">
                <div class="modal-header">
                    <!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Employes non standard</h4>
                </div>
                <div class="modal-body"  style="height: 600px;overflow-y: scroll">
                    <div class="panel panel-default tabs">
                        <ul class="nav nav-tabs" role="tablist">
                            <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Information personnel</a></li>
                            <li><a href="#tab-second" role="tab" data-toggle="tab" >Contrat Specifique</a></li>


                        </ul>
                        <div class="panel-body tab-content">
                            <div class="tab-pane active" id="tab-first">
                                <div class="row">

                                    <div class="col-md-3 col-sm-4 col-xs-5">


                                        <div class="panel panel-default">
                                            <div class="panel-body form-horizontal form-group-separated">
                                                <h3 id="nomcplet"><span class="fa fa-user"></span> </h3>
                                                <p id="fnction"></p>
                                                <div class="text-center" id="user_image" class="profile-mini">

                                                    <img id="defImg"  style="width: 133px;height:171px" class="img-thumbnail"/>


                                                </div>
                                                <br>
                                                <div class="form-group">
                                                    <div class="col-md-12 col-xs-12">
                                                         <input  type="file"   id="imag"  onchange="javascript:verifier();"  name="files[]" multiple/>
                                                        <label class="col-md-3 col-xs-5 control-label" id="error" style="color: #ff0033"></label>

                                                    </div>


                                                </div>

                                            </div>
                                            <div class="panel-body form-group-separated">



                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" id="lieuHabitation" name="lieuHabitation" onkeyup='this.value=this.value.toUpperCase()' class="form-control" ng-required="true" placeholder="Habitation" ng-model="employe.lieuHabitation"/>

                                                    </div>

                                                </div>


                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Fonction</label>--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" class="form-control datetimePicker" id="dNaiss" name="dNaiss" placeholder="Date de Naissance"  ng-required="true" ng-model="employe.dNaiss">
                                                    </div>

                                                </div>




                                            </div>
                                        </div>


                                    </div>
                                    <div class="col-md-6 col-sm-8 col-xs-7">

                                        <%--<form action="#" class="form-horizontal">--%>
                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <h3><span class="fa fa-pencil"></span> Information generale</h3>
                                                <%--<p>This information lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer faucibus, est quis molestie tincidunt, elit arcu faucibus erat.</p>--%>
                                            </div>
                                            <div class="panel-body form-group-separated">


                                                <div class="form-group">
                                                    <label class="col-md-3 col-xs-5 control-label">Matricule</label>
                                                    <div class="col-md-9 col-xs-7">
                                                        <div class="pull-right verif-matricule"><img src="<%=request.getContextPath() %>/resources/static/front-end/images/loaders/loader27.gif" /></div>
                                                        <input type="text" id="matricule" name="matricule" ng-required="true"  class="form-control" ng-model="employe.matricule" placeholder="Matricule" onkeyup='this.value=this.value.toUpperCase()'/>

                                                    </div>

                                                </div>


                                                <div class="form-group">
                                                    <label class="col-md-3 col-xs-5 control-label">Nom Complet</label>
                                                    <div class="col-md-9 col-xs-7">
                                                        <input type="text"  id="nomComplet" name="nomComplet" class="form-control" ng-required="true" onkeyup='this.value=this.value.toUpperCase()'  ng-model="employe.nomComplet" placeholder="Nom Complet"/>

                                                    </div>

                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-3 col-xs-5 control-label">Sexe</label>
                                                    <div class="col-md-9 col-xs-7">
                                                        <label  >
                                                            <input name="sexe" id="sexeMasculin" type="radio" class="radio-inline" value="Masculin" ng-required="!sexe" >Masculin
                                                        </label>
                                                        <label  >
                                                            <input name="sexe" type="radio" id="sexeFeminin" class="radio-inline" value="Feminin" ng-required="!sexe">  Feminin
                                                        </label>
                                                        <div ng-show="formAjout.sexe.$invalid &&  formAjout.sexe.$error.required">
                                                            <div style="color:red;">Sexe est obligatoire.</div>
                                                        </div>
                                                    </div>

                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-3 col-xs-5 control-label">Sit. Matri</label>
                                                    <div class="col-md-9 col-xs-7">
                                                        <select id="situationMatrimoniale" name="situationMatrimoniale" class="form-control select2" ng-model="employe.situationMatrimoniale" ng-required="true">
                                                            <option value="0" > AUCUN</option>
                                                            <option value="1" selected="selected"> MARIE(E) </option>
                                                            <option value="2"> CELIBATAIRE </option>
                                                            <option value="3" > DIVORCE(E) </option>
                                                            <option value="4"> VEUF(VE) </option>

                                                        </select>

                                                    </div>

                                                </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 col-xs-5 control-label">Date arriv&eacute;e</label>
                                                        <div class="col-md-9 col-xs-7">
                                                            <input type="text" id="dEntree" name="dEntree" class="form-control datetimePicker" ng-model="employe.dEntree" placeholder="Date d'arrivée" ng-required="true"/>

                                                        </div>
                                                    </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 col-xs-5 control-label">Nombre d'enfants</label>
                                                    <div class="col-md-9 col-xs-7">
                                                        <input type="number" id="nbreEnft" name="nbreEnft" class="form-control" ng-model="employe.nbreEnft" placeholder="Nombre enfants" ng-required="true"/>

                                                    </div>
                                                </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 col-xs-5 control-label">Cellulaire</label>
                                                        <div class="col-md-9 col-xs-7">
                                                            <input type="text" id="cellulaire" name="cellulaire" ng-required="true" class="form-control" ng-model="employe.cellulaire" placeholder="225xxxxxxxx"/>

                                                        </div>

                                                    </div>



                                                <%--<div class="form-group">--%>
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Email</label>--%>
                                                    <%--<div class="col-md-9 col-xs-7">--%>
                                                        <%--<input type="text" id="email" name="email"   class="form-control" ng-model="employe.email" placeholder="Email" />--%>
                                                    <%--</div>--%>
                                                    <%--&lt;%&ndash;<div ng-show="formAjout.email.$invalid &&  formAjout.email.$error.required">&ndash;%&gt;--%>
                                                        <%--&lt;%&ndash;<div style="color:red;">Email est obligatoire.</div>&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                                <%--</div>--%>


                                            </div>
                                        </div>

                                    </div>

                                    <div class="col-md-3">


                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <h3><span class="fa fa-cog"></span> Settings</h3>
                                                <p></p>
                                            </div>
                                            <div class="panel-body form-horizontal form-group-separated">
                                                <div class="form-group">
                                                    <label class="col-md-8 col-xs-8 control-label">Versement Salaire</label>
                                                    <div class="col-md-4 col-xs-4">
                                                        <label class="switch">
                                                            <input type="checkbox" name="cotise" ng-model="employe.cotise"  />
                                                            <span></span>
                                                        </label>
                                                        <%--<div ng-show="formAjout.cotise.$dirty &&  formAjout.cotise.$error.required">--%>
                                                        <%--<div style="color:red;">cotiser est obligatoire.</div>--%>
                                                    </div>
                                                  </div>

                                                </div>


                                                    <div class="form-group">
                                                        <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                        <div class="col-md-12 col-xs-12">
                                                            <input type="text" id="telephone" name="telephone"   class="form-control" ng-model="employe.telephone" placeholder="Telephone" />
                                                            <%--<div ng-show="formAjout.pays.$pristine &&  formAjout.pays.$error.required">--%>
                                                            <%--<div style="color:red;">Pays est obligatoire.</div>--%>
                                                            <%--</div>--%>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                        <div class="col-md-12 col-xs-12">
                                                            <input type="text" id="email" name="email"   class="form-control" ng-model="employe.email" placeholder="Email" />
                                                            <%--<div ng-show="formAjout.pays.$pristine &&  formAjout.pays.$error.required">--%>
                                                                <%--<div style="color:red;">Pays est obligatoire.</div>--%>
                                                            <%--</div>--%>
                                                        </div>
                                                    </div>

                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" id="nation" name="nation" class="form-control" ng-required="true" placeholder="Nationalite" ng-model="employe.nation" onkeyup='this.value=this.value.toUpperCase()' ng-required="true"/>

                                                    </div>
                                                </div>








                                            </div>
                                        </div>
                                    </div>
                                </div>

                            <div class="tab-pane sectionContrat" id="tab-second">
                                <div class="row">
                                <div class="col-md-10 col-sm-8 col-xs-7">

                                    <%--<form action="#" class="form-horizontal">--%>
                                    <div class="panel panel-default">
                                        <div class="panel-body">
                                            <h3><span class="fa fa-pencil"></span>Contrat specifique</h3>
                                            <%--<p>This information lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer faucibus, est quis molestie tincidunt, elit arcu faucibus erat.</p>--%>
                                        </div>
                                        <div class="panel-body form-group-separated">
                                                  <div class="form-group">
                                                    <label class="col-md-4 col-xs-5 control-label">Type de contrat specifique</label>
                                                     <div class="col-md-8 col-xs-7">
                                                      <select id="contratspecifique" name="contratspecifique" class="form-control select2" ng-model="employe.situationSpedifique" ng-required="true">
                                                               <option value="0" > AUCUN</option>
                                                               <option value="STAFF_PDG" selected="selected">STAFF_PDG </option>
                                                                <option value="DOZO"> DOZO </option>
                                                                  <option value="AGENT_SECURITE" > AGENT_SECURITE</option>


                                                     </select>


                                              </div>

                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-5 control-label">Date debut </label>
                                                <div class="col-md-8 col-xs-7">
                                                    <input type="text" id="dCvers" name="dCvers"   class="form-control datetimePicker" ng-model="infochurch.dCvers" placeholder="Date de conversion"/>
                                                </div>

                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-5 control-label">Date de fin</label>
                                                <div class="col-md-8 col-xs-7">
                                                    <input type="text" id="dbapteme" name="dbapteme" class="form-control datetimePicker" ng-required="true" ng-model="infochurch.dbapteme" placeholder="Baptis&eacute;(e) le"/>

                                                </div>
                                                .
                                            </div>
                                              <div class="form-group">
                                                <label class="col-md-4 col-xs-5 control-label">Mode de paiement</label>
                                                <div class="col-md-8 col-xs-7">
                                                      <select id="modepaiement" name="modepaiement" class="form-control select2">
                                                      <option value="" disabled selected>-- Selectionnez Mode de paiement--</option>
                                                       <option value="virement-bancaire" selected="selected"> Virement </option>
                                                       <option value="transfert-mobile-money"> transfert-mobile-money </option>
                                                       <option value="transfert-wave"> transfert-wave </option>

                                                     </select>
                                                </div>
                                                .
                                            </div>

                                               <div class="form-group">
                                                 <label class="col-md-4 col-xs-5 control-label">Numero de compte bancaire ou mobile</label>
                                                 <div class="col-md-8 col-xs-7">
                                                  <input type="text" id="dbapteme" name="dbapteme" class="form-control datetimePicker" ng-required="true" ng-model="infochurch.dbapteme" placeholder="compte bancaire ou mobile"/>

                                               </div>

                                                <div class="form-group">
                                                      <label class="col-md-4 col-xs-5 control-label">Net a payer</label>
                                                     <div class="col-md-8 col-xs-7">
                                                   <input type="text" id="netpayer" name="netpayer" class="form-control " ng-required="true" ng-model="infochurch.netpayer" placeholder="Net a payer"/>

                                                </div>
                                                                                            .
                                       </div>


                                        </div>



                                        </div>
                                    </div>

                                </div>



                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="employe.id">
                    <span></span>&nbsp;
                    <button type="button" class="btn btn-default" onclick="resetEditForm()" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success"  ng-disabled="formAjout.$invalid" >Valider</button>
                </div>
            </form>
        </div>
       </div>

</div>

<div class="modal animated fadeIn" id="modal_change_photo" tabindex="-1" role="dialog" aria-labelledby="smallModalHead" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <h4 class="modal-title" id="smallModalHead">Change photo</h4>
                    </div>
                    <form id="cp_crop" method="post" action="http://aqvatarius.com/themes/atlant/html/assets/crop_image.php">
                        <div class="modal-body">
                            <div class="text-center" id="cp_target">Use form below to upload file. Only .jpg files.</div>
                            <input type="hidden" name="cp_img_path" id="cp_img_path"/>
                            <input type="hidden" name="ic_x" id="ic_x"/>
                            <input type="hidden" name="ic_y" id="ic_y"/>
                            <input type="hidden" name="ic_w" id="ic_w"/>
                            <input type="hidden" name="ic_h" id="ic_h"/>
                        </div>
                    </form>
                    <form id="cp_upload" method="post" enctype="multipart/form-data" action="http://aqvatarius.com/themes/atlant/html/assets/upload_image.php">
                        <div class="modal-body form-horizontal form-group-separated">
                            <div class="form-group">
                                <label class="col-md-4 control-label">New Photo</label>
                                <div class="col-md-4">
                                    <input type="file" class="fileinput btn-info" name="file" id="cp_photo" data-filename-placement="inside" title="Select file"/>
                                </div>
                            </div>
                        </div>
                    </form>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-success disabled" id="cp_accept">Accept</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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
                    <h4 ng-bind="employe.info"></h4>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="employe.id">
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
        $scope.pupulateForm = function (membre) {
            $scope.membre = membre;
        };
       $scope.initForm = function () {
            $scope.membre = {};
        };
       }).controller('formDeleteCtrl', function ($scope) {
            $scope.pupulateForm = function (membre) {
                $scope.membre = membre;
            };

       });

    var actionUrl = "/membre/enregistrermembre";
    var actionDeleteUrl = "/membre/supprimermembre";
    var actionUrlgrp ="/membre/savecategmembre";
    var action = "ajout";
    var $table;
    jQuery(document).ready(function ($) {
        $table = $('#table');//.form-contrat, .form-absence, .form-mouvement-conge, .form-enfant, .form-conjoint,, #btnAddContrat, .verif-matricule, .verif-numerocnps
    $(".select2").select2();


       // $("#formAjout")[0].reset();
        $('.datetimePicker').datetimepicker({
            timepicker: false,
            formatDate: 'd/m/Y',
            format: 'd/m/Y',

        });
        function isEmail(email) {
            alert("ggg");
            var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
            return regex.test(email);
        }
        $('#contratSpecifique,#civil,#groupe,#catgroupe').select2({
            placeholder: 'Choisir une  option',
            allowClear: true

        });
        $("#defImg").attr('src',baseUrl + '/resources/users/no-image.jpg');
        //Fermeture du modal
        $('#rhpModal').on('hidden.bs.modal', function () {

            var $scope = angular.element(document.getElementById("formAjout")).scope();
            $(".sectionContrat input, .sectionContrat select").removeAttr("disabled");
            $scope.$apply(function () {
                $scope.initForm();
            });
            $("#defImg").attr('src',baseUrl + '/resources/users/no-image.jpg');
            //$("#id").val(""); //Initialisation de l'id
            // $('#tab-third').hide(); $('#three').hide();
        });


        $("#matricule").blur(function (e) {
            var mat = this.value;
            if(mat.length < 3 || $("#id").val()){
                return;
            }
            var $verificationLoader = $(".verif-matricule");
            $.ajax({
                type: "GET",
                url: baseUrl + "/membre/cherchpersonMatricule",
                cache: false,
                data: {matricule: mat},
                success: function (reponse) {
                    if (reponse.row) {
                        toastr.error("Ce matricule existe deja.");
                    }
                },
                error: function (err) {
                    $verificationLoader.hide(1000);
                },
                beforeSend: function () {
                    $verificationLoader.show();
                },
                complete: function () {
                    $verificationLoader.hide(1000);
                }
            });
        });


// webcam
//         jQuery("#webcam").webcam({
//
//             width: 320,
//             height: 240,
//             mode: "callback",
//             swffile: "/jscam_canvas_only.swf", // canvas only doesn't implement a jpeg encoder, so the file is much smaller
//
//             onTick: function(remain) {
//
//                 if (0 == remain) {
//                     jQuery("#status").text("Cheese!");
//                 } else {
//                     jQuery("#status").text(remain + " seconds remaining...");
//                 }
//             },
//
//             onSave: function(data) {
//
//                 var col = data.split(";");
//                 // Work with the picture. Picture-data is encoded as an array of arrays... Not really nice, though =/
//             },
//
//             onCapture: function () {
//                 webcam.save();
//
//                 // Show a flash for example
//             },
//
//             debug: function (type, string) {
//                 // Write debug information to console.log() or a div, ...
//             },
//
//             onLoad: function () {
//                 // Page load
//                 var cams = webcam.getCameraList();
//                 for(var i in cams) {
//                     jQuery("#cams").append("<li>" + cams[i] + "</li>");
//                 }
//             }
//         });

        //Envoi des donnees
        function isValidMatricule() {
            var mat=$('#matricule').val();
            var $verificationLoader = $(".verif-matricule");
            var gtmatr= false;
            $.ajax({
                type: "GET",
                url: baseUrl + "/membre/cherchpersonMatricule",
                cache: false,
                data: {matricule: mat},
                success: function (reponse) {
                    if (reponse.row) {
                        toastr.error("Ce matricule existe deja.");
                        gtmatr= true;
                    }
                },
                error: function (err) {
                    $verificationLoader.hide(1000);
                },
                beforeSend: function () {
                    $verificationLoader.show();
                },
                complete: function () {
                    $verificationLoader.hide(1000);
                }
            });
            return gtmatr;
        }
        $("#formAjout").submit(function (e) {

            e.preventDefault();


                formData = new FormData($(this)[0]);
                //  var formData = $(this).serialize();
                console.log("form", formData);
                // formData.append();
                jQuery.ajax({
                    type: "POST",
                    url: baseUrl + actionUrl,
                    cache: false,
                    processData: false,
                    contentType: false,
                    data: formData,
                    success: function (reponse) {
                        if (reponse.result && reponse.status) {
                            if (action == "ajout") {
                                //Rechargement de la liste (le tableau)
                                $table.bootstrapTable('refresh');
                                $("#formAjout")[0].reset(); //Initialisation du formulaire
                                $("#rhpModal").modal('hide');
                                toastr.success('Success messages!');
                            } else {
                                //MAJ de la ligne modifi�e
                                $table.bootstrapTable('updateRow', {
                                    index: $table.find('tr.selected').data('index'),
                                    row: reponse.data
                                });
                                toastr.success('Success messages!');
                            }
                        } else {
                            alert(reponse.message);
                            toastr.error('Echec operation!');
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
    function cotiseFormatter(cotise){
		var optionActif = '<small class="label label-danger"><i class="fa fa-clock-o"></i> -- Non </small>';
		if(cotise == true)
			optionActif = '<small class="label label-success"><i class="fa fa-clock-o"></i> -- Oui </small>';

		return optionActif;
	}
    function statutFormatter(statut) {
        return statut ? '<span style="color:green;">En cours</span>' : '<span style="color:red;">Termin&eacute;</span>';
    }
    function resetEditForm(){
        $("#defImg").attr('src',baseUrl + '/resources/users/no-image.jpg');
        $("#formAjout")[0].reset();

    }
    function capturePhoto(){
        // jQuery.ajax({
        //     type: "GET",
        //     url: baseUrl + "/membre/trouverphotomembre",
        //     cache: false,
        //      data: {id: idPersonnel},
        //     success: function (reponse) {
        //         //	if(reponse.rows>0){
        //
        //         $tableEvent.bootstrapTable('load', reponse.rows);
        //         //   }
        //     },
        //     beforeSend: function () {
        //         $tableEvent.bootstrapTable('load', []);
        //         jQuery("#btnAddAbsence,.form-mouvement-conge").hide();
        //     },
        //     error: function () {
        //
        //     },
        //     complete: function () {
        //
        //     }
        // });
    }
    function verifier(){

        //$('#btnSubmitUtiliExterne').click(function(){
        var file = jQuery('#imag').val();

        var exts = ['png','jpg'];
        // first check if file field has any value
        if ( file ) {
            // split file name at dot
            var get_ext = file.split('.');
            // reverse name to check extension
            get_ext = get_ext.reverse();
            // check file type is valid as given in 'exts' array
            if ( jQuery.inArray ( get_ext[0].toLowerCase(), exts ) > -1 ){
                jQuery("#error").html(' ok');
                return true;
            } else {
                jQuery('.erro').show();
                jQuery("#error").html('png;jpeg');
                jQuery("#imag").focus();
                jQuery("#imag").val("");
                return false;
            }
        }
    };
    //Functions
    function typeSanctionFormatter(typeSanction){
        return typeSanction.libelle;
    }

    function optionFormatter(id, row, index) {
         var option = '<a onclick="edit(' + row.id + ')" data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier membre [LIBELLE : ' + row.nomComplet + ' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
        <%--var option = '<a onclick="edit(' + row.id + ')"  href="${pageContext.request.contextPath}/membre/addmembre" title="Modifier membre [LIBELLE : ' + row.nomComplet + ' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';--%>
        // option += '&nbsp;<a onclick="del(' + row.id + ')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer sanction [LIBELLE : ' + row.nomComplet + ' ]"> <span class="glyphicon glyphicon-trash"></span></a>&nbsp;';
        option += '&nbsp;<a onclick="openOptionsModal(' + id + ',' + index + ')" data-toggle="modal" data-target=".modal-options" href="#" title="Voir plus"><span class="glyphicon glyphicon-eye-open"></span></a>';
        return option;
    }

    function edit(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formAjout")).scope();

        var rows = $table.bootstrapTable('getData');
         membre = _.findWhere(rows, {id: idTypeSaction});
         console.log(membre);

        $('#membreGrp').val(membre.id);

        if (membre.sexe == "Masculin") {
            document.getElementById('sexeMasculin').checked=true;
            document.getElementById('sexeFeminin').checked=false;
        } else if (membre.sexe == "Feminin") {
            document.getElementById('sexeMasculin').checked=false;
            document.getElementById('sexeFeminin').checked=true;
        }
        if(membre.civilite!=null){
            $("#civilite").val(membre.civilite).trigger("change");
        }
        if(membre.situationMatrimoniale!=null){
            $("#situationMatrimoniale").val(membre.situationMatrimoniale).trigger("change");
        }
        loadMembreLastContrat(membre.id);

        $("#defImg").attr('src',baseUrl + membre.urlDemande);
      //  $('#tab-third').show(); $('#three').show();
        $scope.$apply(function () {
            $scope.pupulateForm(membre);
        });
        $tableFinal.bootstrapTable('refresh',{ url: baseUrl +'/membre/listergroupedunmembre?id='+membre.id});
    }
    function loadMembreLastContrat(idPersonnel) {
        jQuery.ajax({
            type: "GET",
            url: baseUrl + "/infochurchs/listcontratparpersonneljson",
            cache: false,
            data: {idpersonnel: idPersonnel},
            success: function (reponse) {
                var $scope = angular.element(document.getElementById("formAjout")).scope();
                var lastContrat = _.max(reponse.rows, function (infochurch) {
                    return infochurch.id
                });
                console.log("LastContrat", lastContrat);
                if (lastContrat) {
                    $scope.$apply(function () {
                       // $scope.pupulateContrat(lastContrat);
                        $scope.infochurch=lastContrat;
                    });
                    // updateComboContrat(lastContrat);
                }
                jQuery(".sectionContrat input, .sectionContrat select").attr("disabled", "disabled");
            }
        });
    }
    function del(idTypeSaction) {
        var $scope = angular.element(document.getElementById("formDelete")).scope();

        var rows = $table.bootstrapTable('getData');
        var membre = _.findWhere(rows, {id: idTypeSaction});
        membre.info = membre.nom;
        $scope.$apply(function () {
            $scope.pupulateForm(membre);
        });
    }

    function openOptionsModal(idPersonnel, index) {
        indexRowUpdate = index;
        var $scope = angular.element(document.getElementById("optionsModal")).scope();
        var membre = _.findWhere($table.bootstrapTable('getData'), {id: idPersonnel});
        $scope.$apply(function () {
            $scope.populateForm(membre);
        });
    }
    function listAppelfonds(idPersonnel) {
        console.log('mbreid', idPersonnel);
        var $scope = angular.element(document.getElementById("listAppelfondsModal")).scope();
        // loadMouvementCongeByPersonnel(idPersonnel);
        var rows = $table.bootstrapTable('getData');
        var membre = _.findWhere(rows, {id: idPersonnel});
        console.log('membre', membre);
        $scope.$apply(function () {
            $scope.populateForm(membre);
        });
        // loadAppelfondsByMembres(membre.id);
        jQuery.ajax({
            type: "GET",
            url: baseUrl + "/membre/listMvtComptemembrejson",
            cache: false,
            data: {id: membre.id},
            success: function (reponse) {
                console.log(reponse.rows);
                var sumdebit=0;
                var   sunmcredit=0; var solde=0;
                for (i = 0; i < reponse.rows.length; i++) {

                    sumdebit +=reponse.rows[i].montantDebit;
                    sunmcredit +=reponse.rows[i].montantCredit;
                }
                $("#debt").html(new Intl.NumberFormat("fr-FR").format(sumdebit));
                $("#credt").html(new Intl.NumberFormat("fr-FR").format(sunmcredit));
                solde=sumdebit-sunmcredit;
                $("#sold").html(new Intl.NumberFormat("fr-FR").format(solde));
                $tableConge.bootstrapTable('removeAll');
                $tableConge.bootstrapTable('prepend', reponse.rows);

                //  jQuery("idPersonnel").val(reponse.rows[0].membre.id);
                //  alert(jQuery("idPersonnel").val());
            },
            beforeSend: function () {
                $tableConge.bootstrapTable('load', []);
            },
            error: function () {

            },
            complete: function () {
                $tableConge.bootstrapTable ('refresh', {url: baseUrl +'/membre/listMvtComptemembrejson?id='+membre.id});
            }
        });
    }
    // function listAppelfonds(idPersonnel) {
    //     console.log('mbreid', idPersonnel);
    //     var $scope = angular.element(document.getElementById("listAppelfondsModal")).scope();
    //     // loadMouvementCongeByPersonnel(idPersonnel);
    //     var rows = $table.bootstrapTable('getData');
    //     var membre = _.findWhere(rows, {id: idPersonnel});
    //     console.log('membre', membre);
    //     $scope.$apply(function () {
    //         $scope.populateForm(membre);
    //     });
    //     loadAppelfondsByMembres(membre.id);
    // }
    function listEvents(idPersonnel) {
        console.log('mbreid', idPersonnel);
        var $scope = angular.element(document.getElementById("listEventsModal")).scope();
        // loadMouvementCongeByPersonnel(idPersonnel);
        var rows = $table.bootstrapTable('getData');
        var membre = _.findWhere(rows, {id: idPersonnel});
        console.log('membre', membre);
        $scope.$apply(function () {
            $scope.populateForm(membre);
        });
        loadEventsByMembres(membre.id);
    }
    function listReunions(idPersonnel) {
        console.log('mbreid', idPersonnel);
        var $scope = angular.element(document.getElementById("listReunionsModal")).scope();
        // loadMouvementCongeByPersonnel(idPersonnel);
        var rows = $table.bootstrapTable('getData');
        var membre = _.findWhere(rows, {id: idPersonnel});
        console.log('membre', membre);
        $scope.$apply(function () {
            $scope.populateForm(membre);
        });
        loadReunionsByMembres(membre.id);
    }

    function listCourriers(idPersonnel) {
        console.log('mbreid', idPersonnel);
        var $scope = angular.element(document.getElementById("listCourriersModal")).scope();
        // loadMouvementCongeByPersonnel(idPersonnel);
        var rows = $table.bootstrapTable('getData');
        var membre = _.findWhere(rows, {id: idPersonnel});
        console.log('membre', membre);
        $scope.$apply(function () {
            $scope.populateForm(membre);
        });
        loadCourriersByMembres(membre.id);
    }
    function listReglements(idPersonnel) {
        console.log('mbreid', idPersonnel);
        var $scope = angular.element(document.getElementById("listReglementsModal")).scope();
        // loadMouvementCongeByPersonnel(idPersonnel);
        var rows = $table.bootstrapTable('getData');
        var membre = _.findWhere(rows, {id: idPersonnel});
        console.log('membre', membre);
        $scope.$apply(function () {
            $scope.populateForm(membre);
        });
        loadReglementByMembres(membre.id);
    }
    function listProfils(idPersonnel) {
        console.log('mbreid', idPersonnel);
        var $scope = angular.element(document.getElementById("listProfilsModal")).scope();
        // loadMouvementCongeByPersonnel(idPersonnel);
        var rows = $table.bootstrapTable('getData');
        var membre = _.findWhere(rows, {id: idPersonnel});
        loadProfilsByMembres(membre.id);
        $scope.membre=membre;
        console.log('membre', membre);
        $scope.$apply(function () {
            $scope.populateForm(membre);
        });

    }
    function loadEventsByMembres(idPersonnel){
        jQuery.ajax({
            type: "GET",
            url: baseUrl + "/evenement/personevents",
            cache: false,
            data: {id: idPersonnel},
            success: function (reponse) {
                //	if(reponse.rows>0){

                $tableEvent.bootstrapTable('load', reponse.rows);
                //   }
            },
            beforeSend: function () {
                $tableEvent.bootstrapTable('load', []);
                jQuery("#btnAddAbsence,.form-mouvement-conge").hide();
            },
            error: function () {

            },
            complete: function () {

            }
        });
    }

    function loadCourriersByMembres(idPersonnel){
        jQuery.ajax({
            type: "GET",
            url: baseUrl + "/evenement/personevents",
            cache: false,
            data: {id: idPersonnel},
            success: function (reponse) {
                //	if(reponse.rows>0){

                $tableCour.bootstrapTable('load', reponse.rows);
                //   }
            },
            beforeSend: function () {
                $tableCour.bootstrapTable('load', []);
                jQuery("#btnAddAbsence,.form-mouvement-conge").hide();
            },
            error: function () {

            },
            complete: function () {

            }
        });
    }
    $("#btnAddContrat").click(function (e) {
        $(".form-profils").show(500);
        //Charger le dernier contrat
        var contratsPersonnel = $tableProfils.bootstrapTable("getData");
        var dernierContrat = _.max(contratsPersonnel, function(infochurch){ return infochurch.id;});
        console.log("gfgfgffg",dernierContrat);
        dernierContrat.id = "";
        var $scope = angular.element(document.getElementById("listProfilsModal")).scope();
        $scope.$apply(function () {
            $scope.populateContrat(dernierContrat);
        });
        $scope.infochurch=dernierContrat;

    });
    $("#btnCancelContrat, #listContratModal button.close").click(function (e) {
        $(".form-profils").hide(500);
    });
    $("#formProfils").submit(function () {
        var formData = $(this).serialize();
        $.ajax({
            type: "POST",
            url: baseUrl + "/infochurchs/savecontratpersonnel",
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == "success") {
                    $tableProfils.bootstrapTable('prepend', reponse.row);
                    $("#btnAddContrat, .form-profils").hide(500);
                    toastr.success("Succes de l'operation");
                } else if (reponse.result == "erreur_champ_obligatoire") {
                    alert("Saisie invalide");
                    toastr.error("Echec de l'operation");
                }
            },
            beforeSend: function () {
                $("#formProfils").attr("disabled", true);
                $("#actionContrat span").addClass('loader');
            },
            error: function () {
                toastr.error("Errors ")
                $("#actionContrat span").removeClass('loader');
            },
            complete: function () {
                $("#formProfils").removeAttr("disabled");
                $("#actionContrat span").removeClass('loader');
            }
        });
        return false;
    });
    function loadReunionsByMembres(idPersonnel){
        jQuery.ajax({
            type: "GET",
            url: baseUrl + "/evenement/personevents",
            cache: false,
            data: {id: idPersonnel},
            success: function (reponse) {
                //	if(reponse.rows>0){

                $tableReun.bootstrapTable('load', reponse.rows);
                //   }
            },
            beforeSend: function () {
                $tableReun.bootstrapTable('load', []);
                jQuery("#btnAddAbsence,.form-mouvement-conge").hide();
            },
            error: function () {

            },
            complete: function () {

            }
        });
    }
    function loadAppelfondsByMembres(idPersonnel){
        jQuery.ajax({
            type: "GET",
            url: baseUrl + "/appel/appelmembrejson",
            cache: false,
            data: {id: idPersonnel},
            success: function (reponse) {
                //	if(reponse.rows>0){

                $tableConge.bootstrapTable('load', reponse.rows);
                //   }
            },
            beforeSend: function () {
                $tableConge.bootstrapTable('load', []);
                jQuery("#btnAddAbsence,.form-mouvement-conge").hide();
            },
            error: function () {

            },
            complete: function () {

            }
        });
    }
    function loadProfilsByMembres(idPersonnel){
        jQuery.ajax({
            type: "GET",
            url: baseUrl + "/infochurchs/listcontratparpersonneljson",
            cache: false,
            data: {idpersonnel: idPersonnel},
            success: function (reponse) {
                //	if(reponse.rows>0){
                var contratActif = _.findWhere(reponse.rows, {statut: true});

                if (contratActif) {
                    jQuery("#btnAddContrat").hide(500);
                } else {
                    jQuery("#btnAddContrat").show(500);
                }
                $tableProfils.bootstrapTable('load', reponse.rows);

                //   }
            },
            beforeSend: function () {
                $tableProfils.bootstrapTable('load', []);
                jQuery("#btnAddContrat,.form-profils").hide();
            },
            error: function () {

            },
            complete: function () {

            }
        });
    }
    function loadReglementByMembres(idPersonnel){
        jQuery.ajax({
            type: "GET",
            url: baseUrl + "/appel/encaissementpersonneljson",
            cache: false,
            data: {id: idPersonnel},
            success: function (reponse) {
                //	if(reponse.rows>0){

                $tableRegl.bootstrapTable('load', reponse.rows);
                //   }
            },
            beforeSend: function () {
                $tableRegl.bootstrapTable('load', []);
                jQuery("#btnAddAbsence,.form-mouvement-conge").hide();
            },
            error: function () {

            },
            complete: function () {

            }
        });
    }
    function optionFormatterPrime(id, row, index) {
        var option = '<a href="#" onclick="javascript:editAbsence(' + id + ', '+index+')" title="Modifier"><span class="glyphicon glyphicon-pencil"></span></a>';
        option += '&nbsp;<a onclick="deleteprime('+id+')" href="#" title="Suprimer prime [LIBELLE : '+row.appelfond.libelle+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';

        return option;
    }
    function AppeloptionFormatter(id, row, index) {
        var option = '<a onclick="listAbsence('+row.id+')" data-toggle="modal" data-target="#listAbsenceModal" href="#" title="Ajouter pr�t [LIBELLE : '+row.membres.nom+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
      //  option += '&nbsp;<a data-toggle="modal" href="#" title="Suprimer bulletin [LIBELLE : '+row.Membres.nom+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';

        return option='';
    }
    function periodAppelFormatter(periodePaie, row, index) {
        if(row.periodePaie.id == ''){
            return "";
        }
        return row.periodePaie.affiche;
    }

    function groupeFormatter(groupe){
        return groupe.intitule;
    }
    function categorieFormatter(categMbreGrp,row){
        if(row.categMbreGrp.id==''){
            return "";
        }
        return categMbreGrp.intitule;
    }
    // $('#pays').change(function() {
    //     var i = $('#pays').val();
    //     jQuery.ajax({
    //         type: "GET",
    //         url: baseUrl + "/parametrages/listVilledepaysjson",
    //         cache: false,
    //         data: {id: i},
    //         success: function (reponse) {
    //             //	if(reponse.rows>0){
    //             $('#ville').val('');
    //             tabledata = '<option value="0" data-libelle="CHOISIR VILLE" >CHOISIR VILLE</option>';
    //             for (i = 0; i < reponse.rows.length; i++) {
    //                 tabledata += '<option value="'+reponse.rows[i].id+'" data-libelle="'+reponse.rows[i].ville.libelle +'" >' + reponse.rows[i].ville.libelle +  '</option>';
    //             }
    //             $('#ville').html(tabledata);
    //             $('#ville').select2('val', 0);
    //             //$('#ville').val(reponse.rows);
    //             // $('#ville').select2({
    //             //     data: reponse.rows,
    //             //     width: "180px"
    //             // });
    //             //   }
    //         },
    //         beforeSend: function () {
    //             // $tableRegl.bootstrapTable('load', []);
    //             // jQuery("#btnAddAbsence,.form-mouvement-conge").hide();
    //         },
    //         error: function () {
    //
    //         },
    //         complete: function () {
    //
    //         }
    //     });
    //
    // });
    function  nomFormatterAppel(membres, row, index) {
        if(row.membres.nom == ''){
            return "";
        }
        return membres.nomcomplet;
    }
    function  matriculeFormatter(membres, row, index) {
        if(row.membres.matricule == ''){
            return "";
        }
        return row.membres.matricule;
    }
    function  vieEgliseFormatter(vieEglise,row){
        var color='<span style="color:red;">NON</span>';
        if(vieEglise == true){
            return "'<span style=\"color:green;\">OUI</span>'" + row.qualiteViemembre;
        }else{
            return color;
        }
    }
    function  workFormatter(work,row){
        var color='<span style="color:red;">NON</span>';
        if(work == true){
            return "'<span style=\"color:green;\">OUI</span>'" + row.employeQlte;
        }else{ return color;}
    }
    function  confirmationFormatter(confirmation,row){
        var color='<span style="color:red;">NON</span>';
        if(confirmation == true){
            return "'<span style=\"color:green;\">OUI</span>'" + row.lieuconfirmation;
        }else{ return color;}
    }
    function  disciplineFormatter(discipline,row){
        var color='<span style="color:red;">NON</span>';
        if(discipline == true){
            return "'<span style=\"color:green;\">OUI</span>'" + row.ddisciplDate;
        }else{ return color;}
    }
    function typeEventFormatter(events,row, index){
        return row.events.typeEvents.libelle;
    }
    function rappelFormatter(events,row, index){
        return row.events.rappel.type;
    }

    function titreFormatter(events,row, index){
        return row.events.title;
    }

    function lieuFormatter(events,row, index){
        return row.events.lieu;
    }

    function descriptionFormatter(events,row, index){
        return row.events.description;
    }
    function timeFormatter(events,row, index){
        return row.events.dtiming ;
    }
    function matriculeFormatter(membres){
        return membres.matricule;
    }

    function nomCompletFormatter(membres){
        return membres.nomComplet;
    }

    function professionFormatter(membres){
        return membres.fonction;
    }

    function cnpsFormatter(personnel){
        return personnel.numeroCnps;
    }

    function situationMatrimonialeFormatter(membres){
        return membres.situationMatri;
    }

    function nombreEnfantFormatter(membres){
        return membres.nbreEnft;
    }

    function typeContratFormatter(typeMbre){
        return typeMbre.intitule;
    }
    function optionEventFormatter(id, row, index) {
        var option = '<a onclick="edit(' + row.id + ')" data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier sanction [LIBELLE : ' + row.titre + ' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
        option += '&nbsp;<a onclick="del(' + row.id + ')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer sanction [LIBELLE : ' + row.titre + ' ]"> <span class="glyphicon glyphicon-trash"></span></a>';

        return option='';
    }
    function  fixeAppelFormatter(appelfond,row) {
        var optionActif = '<small class="label label-success"><i class="fa fa-clock-o"></i> -- FIXE </small>';
        if(row.appelfond.choiceMt == true)
            optionActif = '<small class="label label-danger"><i class="fa fa-clock-o"></i> -- VARIABLE </small>';
        return optionActif;
    }
    function  periodeAFormatter(appelfond, row, index) {
        if(row.periodePaie == ''){
            return "";
        }
        return row.periodePaie.affiche;
    }
    function  libelleAppelFormatter(appelfond, row, index) {
        if(row.appelfond.libelle == ''){
            return "";
        }
        return row.appelfond.libelle;
    }
    function primeFormatter(appelfond, row, index) {
        if(row.appelfond.id == ''){
            return "";
        }
        return row.appelfond.libelle;
    }

    $('#addMember').on('click',function (e) {
        e.preventDefault();
       // alert("hhh");
         var members = [];
        // members.push({ groupe: $('#groupe').val(),
        //     Membres: $('#membreGrp').val(),
        //     categMbreGrp: $('#catgroupe').val()
        //
        // });
        // console.log('ggg',members);
        // $tableFinal.bootstrapTable('prepend',members);
        var idmbre = $('#formAjout')
            .find('#membreGrp')
            .val();
        var groupe = $('#formAjout')
            .find('#groupe')
            .val();
        var catgroupe = $('#formAjout')
            .find('#catgroupe')
            .val();

        $.ajax({
            type: "POST",
            url: baseUrl + actionUrlgrp,
            cache: false,
            dataType: "json",
            data:{
                membreGrp :   idmbre,
                groupe :   groupe,
                catgroupe:catgroupe
            },
            success: function (reponse) {
                if (reponse.result && reponse.status) {
                      toastr.success("Operation reussie");
                    $tableFinal.bootstrapTable('refresh',{ url: baseUrl +'/membre/listergroupedunmembre?id='+idmbre});

                } else {
                    alert(reponse.message);
                    toastr.error("Operation echou&eacute");
                }

            },
            error: function (response) {
                console.log("Impossible de joindre le serveur.");
            },

        });
    });
</script>