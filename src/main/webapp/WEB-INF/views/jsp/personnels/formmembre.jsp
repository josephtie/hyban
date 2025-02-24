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
                    <h3>Liste des Membres</h3>
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
            <form id="formAjout" name="formAjout" class="form-horizontal" role="form" novalidate="novalidate" ng-controller="formAjoutCtrl">
                <div class="modal-header">
                    <!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Membres</h4>
                </div>
                <div class="modal-body">
                    <div class="panel panel-default tabs">
                        <ul class="nav nav-tabs" role="tablist">
                            <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Information personnel</a></li>
                            <li><a href="#tab-second" role="tab" data-toggle="tab" >Donn&eacute;es Eglise</a></li>
                            <li><a href="#tab-third" role="tab" data-toggle="tab" id ="three">Engagements</a></li>

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
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Fonction</label>--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" id="fonction" name="fonction" class="form-control" placeholder="Fonction" onkeyup='this.value=this.value.toUpperCase()'  ng-model="membre.fonction" ng-required="true"/>
                                                        <div ng-show="formAjout.fonction.$pristine &&  formAjout.fonction.$error.required">
                                                            <div style="color:red;">La Fonction est obligatoire.</div>
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" id="employeur" name="employeur" class="form-control" onkeyup='this.value=this.value.toUpperCase()' placeholder="Employeur"  ng-model="membre.employeur"/>
                                                    </div>

                                                </div>

                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" id="lieuHabitation" name="lieuHabitation" onkeyup='this.value=this.value.toUpperCase()' class="form-control" ng-required="true" placeholder="Habitation" ng-model="membre.lieuHabitation"/>
                                                        <div ng-show="formAjout.lieuHabitation.$pristine &&  formAjout.lieuHabitation.$error.required">
                                                            <div style="color:red;">Le lieu d'habitation est obligatoire.</div>
                                                        </div>
                                                    </div>

                                                </div>


                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Fonction</label>--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" class="form-control datetimePicker" id="dNaiss" name="dNaiss" placeholder="Date de Naissance"  ng-required="true" ng-model="membre.dNaiss">
                                                    </div>
                                                    <div ng-show="formAjout.dNaiss.$pristine &&  formAjout.dNaiss.$error.required">
                                                        <div style="color:red;">La Date de naissance est obligatoire.</div>
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
                                                <%--<div class="form-group">--%>
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Type</label>--%>
                                                    <%--<div class="col-md-9 col-xs-7">--%>
                                                        <%--<label id="eventsNon" class="radio-inline">--%>
                                                            <%--<input name="typeMbre" id="eventPub" type="radio" value="1"> VISITEUR--%>
                                                        <%--</label>--%>
                                                        <%--<label id="eventsOui" class="radio-inline">--%>
                                                            <%--<input name="typeMbre"  id="eventPrs"type="radio"   value="2"> SYMPATHISANT--%>
                                                        <%--</label>--%>
                                                        <%--<label id="eventsGrp" class="radio-inline">--%>
                                                            <%--<input name="typeMbre"  id="eventGrp"type="radio"   value="3"> MEMBRE--%>
                                                        <%--</label>--%>
                                                        <%--<div ng-show="formAjout.typeMbre.$invalid &&  formAjout.typeMbre.$error.required">--%>
                                                            <%--<div style="color:red;">Type de Membre est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    <%--</div>--%>

                                                <%--</div>--%>
                                                <div class="form-group">
                                                    <label class="col-md-3 col-xs-5 control-label">Civilit&eacute;</label>
                                                    <div class="col-md-9 col-xs-7">
                                                        <select id="civilite" name="civilite" class="form-control select2" ng-model="membre.civilite" ng-required="true">
                                                            <option value="0" > Aucun</option>
                                                            <option value="1" selected="selected"> MR </option>
                                                            <option value="2"> MME </option>
                                                            <option value="3" >MLLE</option>
                                                            <option value="11" >ANCIEN</option>
                                                            <option value="12" >SERVITEUR</option>
                                                            <option value="4"> REVEREND </option>
                                                            <option value="5"> DIACRE </option>
                                                            <option value="6" >APOTRE</option>
                                                            <option value="7"> DOCTEUR </option>
                                                            <option value="8"> EVANGELISTE </option>
                                                            <option value="9"> PROPHETE </option>
                                                            <option value="10">PASTEUR </option>
                                                        </select>
                                                        <div ng-show="formAjout.civilite.$invalid &&  formAjout.civilite.$error.required">
                                                        <div style="color:red;">La Civilit&eacute;  est obligatoire.</div>
                                                         </div>
                                                    </div>

                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 col-xs-5 control-label">Matricule</label>
                                                    <div class="col-md-9 col-xs-7">
                                                        <div class="pull-right verif-matricule"><img src="<%=request.getContextPath() %>/resources/front-end/images/loaders/loader27.gif" /></div>
                                                        <input type="text" id="matricule" name="matricule" ng-required="true"  class="form-control" ng-model="membre.matricule" placeholder="Matricule" onkeyup='this.value=this.value.toUpperCase()'/>
                                                        <div ng-show="formAjout.matricule.$pristine &&  formAjout.matricule.$error.required">
                                                        <div style="color:red;">Matricule est obligatoire.</div>
                                                    </div>
                                                    </div>

                                                </div>


                                                <div class="form-group">
                                                    <label class="col-md-3 col-xs-5 control-label">Nom Complet</label>
                                                    <div class="col-md-9 col-xs-7">
                                                        <input type="text"  id="nomComplet" name="nomComplet" class="form-control" ng-required="true" onkeyup='this.value=this.value.toUpperCase()'  ng-model="membre.nomComplet" placeholder="Nom Complet"/>
                                                        <div ng-show="formAjout.nomComplet.$pristine &&  formAjout.nomComplet.$error.required">
                                                            <div style="color:red;">nom et prenom est obligatoire.</div>
                                                        </div>
                                                    </div>

                                                </div>
                                                <%--<div class="form-group">--%>
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Prenom</label>--%>
                                                    <%--<div class="col-md-9 col-xs-7">--%>
                                                        <%--<input type="text" id="prenom" name="prenom" ng-required="true"  class="form-control" ng-model="membre.prenom"/>--%>
                                                    <%--</div>--%>
                                                    <%--<div ng-show="formAjout.prenom.$dirty &&  formAjout.prenom.$error.required">--%>
                                                        <%--<div style="color:red;">Prenom est obligatoire.</div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
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
                                                        <select id="situationMatrimoniale" name="situationMatrimoniale" class="form-control select2" ng-model="membre.situationMatrimoniale" ng-required="true">
                                                            <option value="0" > AUCUN</option>
                                                            <option value="1" selected="selected"> MARIE(E) </option>
                                                            <option value="2"> CELIBATAIRE </option>
                                                            <option value="3" > DIVORCE(E) </option>
                                                            <option value="4"> VEUF(VE) </option>
                                                            <option value="5"> NON RENSEIGNE </option>
                                                            <option value="6" > (DOTE(E))</option>
                                                            <option value="7"> EN COUPLE (NON MARIE,NON DOTE) </option>
                                                            <option value="8"> SEPARE(E) </option>
                                                        </select>
                                                        <div ng-show="formAjout.situationMatrimoniale.$invalid &&  formAjout.situationMatrimoniale.$error.required">
                                                            <div style="color:red;">Situation Matrimoniale est obligatoire.</div>
                                                        </div>
                                                    </div>

                                                </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 col-xs-5 control-label">Date arriv&eacute;e</label>
                                                        <div class="col-md-9 col-xs-7">
                                                            <input type="text" id="dEntree" name="dEntree" class="form-control datetimePicker" ng-model="membre.dEntree" placeholder="Date d'arrivée" ng-required="true"/>
                                                            <div ng-show="formAjout.dEntree.$pristine &&  formAjout.dEntree.$error.required">
                                                                <div style="color:red;">Date d'arriv&eacute;e est obligatoire.</div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 col-xs-5 control-label">Nombre d'enfants</label>
                                                    <div class="col-md-9 col-xs-7">
                                                        <input type="number" id="nbreEnft" name="nbreEnft" class="form-control" ng-model="membre.nbreEnft" placeholder="Nombre enfants" ng-required="true"/>
                                                        <div ng-show="formAjout.nbreEnft.$pristine &&  formAjout.nbreEnft.$error.required">
                                                            <div style="color:red;">Cellulaire est obligatoire.</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 col-xs-5 control-label">Cellulaire</label>
                                                        <div class="col-md-9 col-xs-7">
                                                            <input type="text" id="cellulaire" name="cellulaire" ng-required="true" class="form-control" ng-model="membre.cellulaire" placeholder="225xxxxxxxx"/>
                                                            <div ng-show="formAjout.cellulaire.$pristine &&  formAjout.cellulaire.$error.required">
                                                                <div style="color:red;">Cellulaire est obligatoire.</div>
                                                            </div>
                                                        </div>

                                                    </div>



                                                <%--<div class="form-group">--%>
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Email</label>--%>
                                                    <%--<div class="col-md-9 col-xs-7">--%>
                                                        <%--<input type="text" id="email" name="email"   class="form-control" ng-model="membre.email" placeholder="Email" />--%>
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
                                                    <label class="col-md-8 col-xs-8 control-label">Appel de fonds</label>
                                                    <div class="col-md-4 col-xs-4">
                                                        <label class="switch">
                                                            <input type="checkbox" name="cotise" ng-model="membre.cotise"  />
                                                            <span></span>
                                                        </label>
                                                        <%--<div ng-show="formAjout.cotise.$dirty &&  formAjout.cotise.$error.required">--%>
                                                        <%--<div style="color:red;">cotiser est obligatoire.</div>--%>
                                                    </div>
                                                    </div>

                                                </div>
                                                <%--<div class="form-group">--%>
                                                    <%--<label class="col-md-6 col-xs-6 control-label">Notifications</label>--%>
                                                    <%--<div class="col-md-6 col-xs-6">--%>
                                                        <%--<label class="switch">--%>
                                                            <%--<input type="checkbox" name="rappel" ng-model="membre.rappel"/>--%>
                                                            <%--<span></span>--%>
                                                        <%--</label>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                                <%--<div class="form-group">--%>
                                                    <%--<label class="col-md-6 col-xs-6 control-label">Mailing</label>--%>
                                                    <%--<div class="col-md-6 col-xs-6">--%>
                                                        <%--<label class="switch">--%>
                                                            <%--<input type="checkbox" name="mailing" ng-model="membre.mailing"/>--%>
                                                            <%--<span></span>--%>
                                                        <%--</label>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                                <div class="form-group">
                                                    <label class="col-md-8 col-xs-8 control-label">SMS</label>
                                                    <div class="col-md-4 col-xs-4">
                                                        <label class="switch">
                                                            <input type="checkbox" name="sms" ng-model="membre.sms"  />
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                    <%--<div ng-show="formAjout.sms.$dirty &&  formAjout.sms.$error.required">--%>
                                                    <%--<div style="color:red;">Sms est obligatoire.</div>--%>
                                                    <%--</div>--%>
                                                </div>
                                            <%--<div class="form-group">--%>
                                                <%--&lt;%&ndash;<label class="col-md-3 col-xs-5 control-label">Sit. Matri</label>&ndash;%&gt;--%>
                                                <%--<div class="col-md-12 col-xs-12">--%>
                                                 <%----%>
                                                    <%--&lt;%&ndash;<select id="typeMbre" name="typeMbre" class="form-control select2" ng-model="membre.typeMbre" ng-required="true">&ndash;%&gt;--%>

                                                        <%--&lt;%&ndash;<c:forEach items="${listtypMembre}" var="mbretype">&ndash;%&gt;--%>
                                                            <%--&lt;%&ndash;<option value="${mbretype.id}">${mbretype.intitule}</option>&ndash;%&gt;--%>
                                                        <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>

                                                    <%--&lt;%&ndash;</select>&ndash;%&gt;--%>
                                                    <%--<div ng-show="formAjout.typeMbre.$invalid &&  formAjout.typeMbre.$error.required">--%>
                                                        <%--<div style="color:red;">Type de membre est obligatoire.</div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>

                                            <%--</div>--%>

                                            <div class="form-group">
                                                <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                <div class="col-md-12 col-xs-12">
                                                    <input type="text" id="telephone" name="telephone"   class="form-control" ng-model="membre.telephone" placeholder="Telephone" />
                                                    <%--<div ng-show="formAjout.pays.$pristine &&  formAjout.pays.$error.required">--%>
                                                    <%--<div style="color:red;">Pays est obligatoire.</div>--%>
                                                    <%--</div>--%>
                                                </div>
                                            </div>

                                                    <div class="form-group">
                                                        <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                        <div class="col-md-12 col-xs-12">
                                                            <input type="text" id="email" name="email"   class="form-control" ng-model="membre.email" placeholder="Email" />
                                                            <%--<div ng-show="formAjout.pays.$pristine &&  formAjout.pays.$error.required">--%>
                                                                <%--<div style="color:red;">Pays est obligatoire.</div>--%>
                                                            <%--</div>--%>
                                                        </div>
                                                    </div>

                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" id="nation" name="nation" class="form-control" ng-required="true" placeholder="Nationalite" ng-model="membre.nation" onkeyup='this.value=this.value.toUpperCase()' ng-required="true"/>
                                                        <div ng-show="formAjout.nation.$pristine &&  formAjout.nation.$error.required">
                                                            <div style="color:red;">Nationalit&eacute; est obligatoire.</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" id="ethnie" name="ethnie" class="form-control" placeholder="Ethnie" ng-model="membre.ethnie" onkeyup='this.value=this.value.toUpperCase()'/>
                                                    </div>
                                                </div>
                                            <div class="form-group">
                                                <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                <div class="col-md-12 col-xs-12">
                                                    <%--<select id="pays" name="pays" class="form-control select2" ng-required="true" placeholder="Pays " ng-model="membre.pays">--%>
                                                    <%--<c:forEach items="${listpays}" var="pays">--%>
                                                    <%--<option value="${pays.id}">${pays.libelle} </option>--%>
                                                    <%--</c:forEach>--%>
                                                    <%--</select>--%>
                                                    <input type="text" id="pays" name="pays" class="form-control" placeholder="Pays" ng-model="membre.pays" onkeyup='this.value=this.value.toUpperCase()' ng-required="true"/>
                                                    <div ng-show="formAjout.pays.$pristine &&  formAjout.pays.$error.required">
                                                        <div style="color:red;">Pays est obligatoire.</div>
                                                    </div>
                                                </div>
                                            </div>
                                                    <div class="form-group">
                                                        <%--<label class="col-md-3 col-xs-5 control-label">Fonction</label>--%>
                                                        <div class="col-md-12 col-xs-12">
                                                            <%--<select id="ville" name="ville" class="form-control select2" ng-required="true" placeholder="Pays " ng-model="membre.pays">--%>
                                                                <%--&lt;%&ndash;<c:forEach items="${listpays}" var="pays">&ndash;%&gt;--%>
                                                                    <%--&lt;%&ndash;<option value="${pays.id}">${pays.libelle} </option>&ndash;%&gt;--%>
                                                                <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                                                            <%--</select>--%>
                                                            <input type="text" id="ville" name="ville" class="form-control" placeholder="Ville"  ng-required="true" ng-model="membre.ville" onkeyup='this.value=this.value.toUpperCase()'/>
                                                            <div ng-show="formAjout.ville.$pristine &&  formAjout.ville.$error.required">
                                                                <div style="color:red;">Ville est obligatoire.</div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <%--<label class="col-md-3 col-xs-5 control-label">Fonction</label>--%>
                                                        <div class="col-md-12 col-xs-12">
                                                            <input type="text" class="form-control input-default" id="commune" name="commune" placeholder="Commune"  ng-required="true" ng-model="membre.commune"  onkeyup='this.value=this.value.toUpperCase()'>
                                                        </div>
                                                        <div ng-show="formAjout.commune.$pristine &&  formAjout.commune.$error.required">
                                                            <div style="color:red;">La Commune est obligatoire.</div>
                                                        </div>
                                                    </div>





                                            </div>
                                        </div>
                                    </div>
                                </div>

                            <div class="tab-pane sectionContrat" id="tab-second">
                                <div class="row">
                                <div class="col-md-6 col-sm-8 col-xs-7">

                                    <%--<form action="#" class="form-horizontal">--%>
                                    <div class="panel panel-default">
                                        <div class="panel-body">
                                            <h3><span class="fa fa-pencil"></span>Infos &eacute;glise</h3>
                                            <%--<p>This information lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer faucibus, est quis molestie tincidunt, elit arcu faucibus erat.</p>--%>
                                        </div>
                                        <div class="panel-body form-group-separated">

                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-5 control-label">Date de conversion</label>
                                                <div class="col-md-8 col-xs-7">
                                                    <input type="text" id="dCvers" name="dCvers"   class="form-control datetimePicker" ng-model="infochurch.dCvers" placeholder="Date de conversion"/>
                                                </div>
                                                <%--<div ng-show="formAjout.dCvers.$invalid &&  formAjout.dCvers.$error.required">--%>
                                                    <%--<div style="color:red;">Sexe est obligatoire.</div>--%>
                                                <%--</div>--%>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-5 control-label">Baptis&eacute;(e) le</label>
                                                <div class="col-md-8 col-xs-7">
                                                    <input type="text" id="dbapteme" name="dbapteme" class="form-control datetimePicker" ng-required="true" ng-model="infochurch.dbapteme" placeholder="Baptis&eacute;(e) le"/>
                                                    <div ng-show="formAjout.dbapteme.$invalid &&  formAjout.dbapteme.$error.required">
                                                        <div style="color:red;"> baptise le est obligatoire.</div>
                                                    </div>
                                                </div>
                                                .
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-5 control-label">Baptis&eacute;(e) par</label>
                                                <div class="col-md-8 col-xs-7">
                                                    <input type="text" id="baptisePar" name="baptisePar" ng-required="true" class="form-control" ng-model="infochurch.baptisePar" placeholder="Baptis&eacute;(e) par" onkeyup='this.value=this.value.toUpperCase()'/>
                                                    <div ng-show="formAjout.baptisePar.$pristine &&  formAjout.baptisePar.$error.required">
                                                        <div style="color:red;"> baptise Par est obligatoire.</div>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-5 control-label">Lieu du Bapteme</label>
                                                <div class="col-md-8 col-xs-7">
                                                    <input type="lieubapteme" id="lieubapteme" name="lieubapteme" ng-required="true"  class="form-control" ng-model="infochurch.lieubapteme" placeholder="Lieu du Bapteme" onkeyup='this.value=this.value.toUpperCase()'/>
                                                    <div ng-show="formAjout.lieubapteme.$pristine &&  formAjout.lieubapteme.$error.required">
                                                        <div style="color:red;">Lieu bapteme est obligatoire.</div>
                                                    </div>
                                                </div>

                                            </div>


                                        </div>
                                    </div>

                                </div>
                                    <div class="col-md-3">


                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <h3><span class="fa fa-cog"></span> Participe a la vie d'&eacute;glise</h3>
                                                <p></p>
                                            </div>
                                            <div class="panel-body form-horizontal form-group-separated">
                                                <div class="form-group">
                                                    <label class="col-md-6 col-xs-6 control-label">vie d'&eacute;glise</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <label class="switch">
                                                            <input type="checkbox" name="vieEglise" ng-model="infochurch.vieEglise"  />
                                                            <span></span>
                                                        </label>
                                                        <%--<div ng-show="formAjout.vieEglise.$dirty &&  formAjout.vieEglise.$error.required">--%>
                                                            <%--<div style="color:red;">vie Eglise est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>

                                                </div>
                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>ng-required="true"--%>
                                                    <div class="col-md-6 col-xs-6">
                                                        <input type="text" id="dvieEglisedate" name="dvieEglisedate" class="form-control datetimePicker" placeholder="Depuis le" ng-model="infochurch.dvieEglisedate"  />
                                                        <%--<div ng-show="formAjout.vieEglisedate.$dirty &&  formAjout.vieEglisedate.$error.required">--%>
                                                            <%--<div style="color:red;">Date debut est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>
                                                    <div class="col-md-6 col-xs-6">
                                                        <input type="text" id="dvieEglisedatefin" name="dvieEglisedatefin" class="form-control datetimePicker" placeholder="Au" ng-model="infochurch.dvieEglisedatefin"/>

                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>ng-required="true"--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" id="qualiteViemembre" name="qualiteViemembre"  class="form-control" placeholder="Qaulit&eacute;" ng-model="infochurch.qualiteViemembre" onkeyup='this.value=this.value.toUpperCase()'/>
                                                        <%--<div ng-show="formAjout.qltitevieEgl.$dirty &&  formAjout.qltitevieEgl.$error.required">--%>
                                                            <%--<div style="color:red;">Champs est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-6 col-xs-6 control-label">Travail a l'&eacute;glise</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <label class="switch">
                                                            <input type="checkbox" name="work" ng-model="infochurch.work" />
                                                            <span></span>
                                                        </label>
                                                        <%--<div ng-show="formAjout.work.$dirty &&  formAjout.work.$error.required">--%>
                                                            <%--<div style="color:red;">Champs est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>
                                                </div>





                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>ng-required="true"--%>
                                                    <div class="col-md-6 col-xs-6">
                                                        <input type="text" id="demployeDate"  name="demployeDate" class="form-control datetimePicker" placeholder="Depuis le" ng-model="infochurch.demployeDate"/>
                                                        <%--<div ng-show="formAjout.employeDate.$dirty &&  formAjout.employeDate.$error.required">--%>
                                                            <%--<div style="color:red;">Champs est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>
                                                    <div class="col-md-6 col-xs-6">
                                                        <input type="text" id="demployeDatefin" name="demployeDatefin" class="form-control datetimePicker" placeholder="Au" ng-model="infochurch.demployeDatefin"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>ng-required="true"--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" id="employeQlte" name="employeQlte"  class="form-control" placeholder="Qaulit&eacute;" ng-model="infochurch.employeQlte" onkeyup='this.value=this.value.toUpperCase()'/>
                                                        <%--<div ng-show="formAjout.employeQlte.$dirty &&  formAjout.employeQlte.$error.required">--%>
                                                            <%--<div style="color:red;">Champs est obligatoire.</div>--%>
                                                        <%--</div> --%>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" id="lieuposte" name="lieuposte" class="form-control" placeholder="Lieu de poste" ng-model="infochurch.lieuposte" onkeyup='this.value=this.value.toUpperCase()'/>
                                                        <%--<div ng-show="formAjout.lieuposte.$dirty &&  formAjout.lieuposte.$error.required">--%>
                                                            <%--<div style="color:red;">Champs est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>
                                                </div>







                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-3">


                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <h3><span class="fa fa-cog"></span> Autres </h3>
                                                <p></p>
                                            </div>
                                            <div class="panel-body form-horizontal form-group-separated">


                                                <div class="form-group">
                                                    <label class="col-md-6 col-xs-6 control-label">Sous Discipline</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <label class="switch">
                                                            <input type="checkbox" name="discipline" ng-model="infochurch.discipline"  />
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                    <%--<div ng-show="formAjout.cotise.$dirty &&  formAjout.cotise.$error.required">--%>
                                                    <%--<div style="color:red;">Comfirmation est obligatoire.</div>--%>
                                                    <%--</div>--%>
                                                </div>
                                                <div class="form-group">
                                                <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>ng-required="true"--%>
                                                <div class="col-md-6 col-xs-6">
                                                    <input type="text" id="ddisciplDate"  name="ddisciplDate" class="form-control datetimePicker" placeholder="Depuis le" ng-model="infochurch.ddisciplDate"/>
                                                    <%--<div ng-show="formAjout.employeDate.$dirty &&  formAjout.employeDate.$error.required">--%>
                                                    <%--<div style="color:red;">Champs est obligatoire.</div>--%>
                                                    <%--</div>--%>
                                                </div>
                                                <div class="col-md-6 col-xs-6">
                                                    <input type="text" id="ddisciplDatefin" name="ddisciplDatefin" class="form-control datetimePicker" placeholder="Au" ng-model="infochurch.ddisciplDatefin"/>
                                                </div>
                                            </div>

                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                    <div class="col-md-6 col-xs-6">
                                                        <textarea name="motifdiscp" id="motifdiscp"  rows="4" placeholder="Motif" cols="20"
                                                       ng-model="infochurch.motifdiscp" onkeyup='this.value=this.value.toUpperCase()'></textarea>

                                                    <%--<div ng-show="formAjout.lieuposte.$dirty &&  formAjout.lieuposte.$error.required"disciplMotifid="disciplMotif" name="disciplMotif" class="form-control" />>--%>
                                                        <%--<div style="color:red;">Champs est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>
                                                </div>


                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <div class="tab-pane sectionGroupe" id="tab-third">
                                <div class="row" id="groupInsert">
                                    <%--<form action="#" class="form-horizontal">--%>
                                    <div class="col-md-6 col-sm-8 col-xs-7"  >


                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <h3><span class="fa fa-pencil"></span>Comit&eacute;s-Groupes</h3>
                                                <%--<p>This information lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer faucibus, est quis molestie tincidunt, elit arcu faucibus erat.</p>--%>
                                            </div>
                                            <div class="panel-body form-group-separated">

                                                <input type="text" class="hidden" ng-hide="true" value="" id="membreGrp" name="membreGrp" ng-model="membre.id">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-5 control-label">Groupe</label>
                                                    <div class="col-md-8 col-xs-7">
                                                        <select class="form-control input-small select2 " id="groupe" name="groupe"  required="required" >

                                                            <c:forEach items="${listgroupe}" var="groupe">
                                                                <option value="${groupe.id}">${groupe.intitule}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <%--<div ng-show="formAjout.dCvers.$invalid &&  formAjout.dCvers.$error.required">--%>
                                                    <%--<div style="color:red;">Sexe est obligatoire.</div>--%>
                                                    <%--</div>--%>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-5 control-label">Categorie</label>
                                                    <div class="col-md-8 col-xs-7">
                                                        <select class="form-control input-small select2 " id="catgroupe" name="catgroupe"  required="required" >

                                                            <c:forEach items="${listcategorie}" var="categorie">
                                                                <option value="${categorie.id}">${categorie.intitule}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <%--<div ng-show="formAjout.dbapteme.$invalid &&  formAjout.dbapteme.$error.required">--%>
                                                            <%--<div style="color:red;"> baptise le est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>
                                                    .
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-5 control-label"></label>
                                                    <div class="col-md-8 col-xs-7" align="right">
                                                        <button class="btn btn-warning" id="addMember">Ajouter >>></button>
                                                 </div>

                                               </div>



                                            </div>
                                        </div>

                                    </div>
                                    <div class="col-md-6 col-sm-8 col-xs-7">


                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <h3><span class="fa fa-cog"></span> Liste des fonctions </h3>
                                                <p></p>
                                            </div>
                                            <div class="panel-body form-horizontal form-group-separated">

                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>ng-required="true"--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <table class="table table-info table-striped" id="members-selected"
                                                               data-toggle="table"
                                                               data-click-to-select="true"
                                                        <%--data-url="${pageContext.request.contextPath}/membre/paginermembres"--%>
                                                               data-side-pagination="server"
                                                               data-pagination="true"

                                                               data-height="340px"

                                                               data-page-list="[10, 20, 50, 100, 200, 500]"
                                                        >
                                                            <thead>
                                                            <tr>

                                                                <th data-field="groupe" data-formatter="groupeFormatter" >Groupe</th>
                                                                <th data-field="categMbreGrp" data-formatter="categorieFormatter">Categorie</th>
                                                                <%--<th data-field="cellulaire">Cellulaire</th>--%>
                                                            </tr>
                                                            </thead>
                                                        </table>
                                                    </div>

                                                </div>










                                            </div>
                                        </div>
                                    </div>
                                    <%--</form>--%>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="membre.id">
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
                    <h4 ng-bind="membre.info"></h4>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="membre.id">
                    <span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
        </div>
        </form>
    </div>
</div>

        <div class="modal modal-options fade" id="optionsModal" ng-controller="optionsModalCtrl" role="dialog" aria-labelledby="optionModalLabel">
            <div class="modal-dialog" >
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="optionModalLabel">Options membre / {{membre.nomComplet}}  - {{membre.cellulaire}}</h4>
                    </div>
                    <div class="modal-body">
                        <div class="bs-glyphicons">
                            <ul class="glyphicons-list">
                                <c:if test="${profil.libelle eq 'ROLE_ADMIN'}">

                                    <li>
                                        <a onclick="javascript:listAppelfonds({{membre.id}})" data-toggle="modal" data-target="#listAppelfondsModal" href="#" title="Appel de fonds [NOM : {{membre.nom}} {{membre.prenom}}]">
                                            <span class="glyphicon glyphicon-shopping-cart" ></span>
                                            <span class="glyphicon-class">Appel de fonds</span>
                                        </a>
                                    </li>
                                </c:if>
                                <c:if test="${profil.libelle eq 'ROLE_TRES'}">

                                    <li>
                                        <a onclick="javascript:listAppelfonds({{membre.id}})" data-toggle="modal" data-target="#listAppelfondsModal" href="#" title="Appel de fonds [NOM : {{membre.nom}} {{membre.prenom}}]">
                                            <span class="glyphicon glyphicon-shopping-cart" ></span>
                                            <span class="glyphicon-class">Appel de fonds</span>
                                        </a>
                                    </li>
                                </c:if>

                                <li>
                                    <a onclick="listEvents({{membre.id}})" data-toggle="modal" data-target="#listEventsModal" href="#" data-action="lister" title="Evenement {{personnel.nom}} {{personnel.prenom}}">
                                        <span class="glyphicon glyphicon-th" ></span>
                                        <span class="glyphicon-class">Evenements</span>
                                    </a>
                                </li>
                                <%--<li>--%>
                                    <%--<a onclick="listReunions({{membre.id}})" data-toggle="modal" data-target="#listReunionsModal" href="#" title="Reunion">--%>
                                        <%--<span class="glyphicon glyphicon-earphone" ></span>--%>
                                        <%--<span class="glyphicon-class">Reunion</span>--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<a onclick="listCourriers({{membre.id}})" data-toggle="modal" data-target="#listCourriersModal" href="#" title="Courrier">--%>
                                        <%--<span class="glyphicon glyphicon-envelope" ></span>--%>
                                        <%--<span class="glyphicon-class">Courriers</span>--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                                <c:if test="${profil.libelle eq 'ROLE_ADMIN'}">
                                <li>
                                    <a onclick="listReglements({{membre.id}})" data-toggle="modal" data-target="#listReglementsModal" href="#" title="Conjoint">
                                        <span class="glyphicon glyphicon-check" ></span>
                                        <span class="glyphicon-class">Reglements</span>
                                    </a>
                                </li>
                                </c:if>
                                <c:if test="${profil.libelle eq 'ROLE_TRES'}">
                                    <li>
                                        <a onclick="listReglements({{membre.id}})" data-toggle="modal" data-target="#listReglementsModal" href="#" title="Conjoint">
                                            <span class="glyphicon glyphicon-check" ></span>
                                            <span class="glyphicon-class">Reglements</span>
                                        </a>
                                    </li>
                                </c:if>
                                <li>
                                    <a onclick="listProfils({{membre.id}})" data-toggle="modal" data-target="#listProfilsModal" href="#" title="profil">
                                        <span class="glyphicon glyphicon-baby-formula" ></span>
                                        <span class="glyphicon-class">Profils</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal main-popup fade" id="listAppelfondsModal" ng-controller="listAppelfondsCtl" role="dialog" aria-labelledby="listPrimesDiversModalLabel"   data-backdrop="static">
            <div class="modal-dialog" style="width:50%;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="listAppelfondsModalLabel">Liste des Appels de fonds du membre <span id="employe" class="danger"></span></h4>
                    </div>
                    <div class="modal-body" >
                        <h3>Membre</h3>
                        <table>
                            <tbody>
                            <tr>
                                <th style="width:150px;">Matricule</th>
                                <td style="width:300px;" align="center">{{membre.matricule}}</td>
                                <th style="width:150px;">Nom</th>
                                <td align="center">{{membre.nomComplet}}</td>
                                <th style="width:150px;">Sexe</th>
                                <td align="center" style="width:300px;">{{membre.sexe}}</td>
                            </tr>
                            <tr>
                                <th style="width:150px;">Domicile</th>
                                <td align="center">{{membre.lieuHabitation}}</td>
                                <th style="width:150px;">Contact</th>
                                <td align="left" style="width:300px;">{{membre.cellulaire}}</td>
                                <th style="width:150px;">Email</th>
                                <td align="left" style="width:300px;">{{membre.email}}</td>
                            </tr>

                            </tbody>
                        </table>

                        <p>&nbsp;</p>
                        <div id="toolbarMouvementConge" >
                            <div class="form-inline">
                                <button type="button" class="btn btn-primary" title="Nouvelle resolution" id="btnAddAbsence"><span class="glyphicon glyphicon-plus"></span> Nouvelle resolution</button>
                            </div>
                        </div>
                        <table id="tableConge"  class="table table-info table-striped"
                               data-toggle="table"
                               data-toolbar="#toolbarMouvementConge"
                               data-single-select="true"
                               data-height="250"
                               data-show-export="true"
                               data-export-dataType="all"
                               data-sort-order="desc"
                               data-export
                               data-pagination="false"
                               data-search="true">
                            <thead>
                            <tr>
                                <th data-field="periodePaie" data-formatter="periodAppelFormatter">Periode debut</th>
                                <th data-field="appelfond" data-formatter="primeFormatter">Prime</th>

                                <th data-field=mtmontant>Montant</th>
                                <th data-field="mtmontantVerse" data-align="right">Montant vers&eacute;</th>
                                <th data-field="mtmontantRestant" data-align="right">Montant restant</th>
                                <th data-field="id" data-formatter="optionFormatterPrime" data-align="center"></th>
                                <!--  <th data-field="mtnVerse" data-align="right">Montant vers&eacute;</th> -->
                            </tr>
                            </thead>
                        </table>
                        <br>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal main-popup fade" id="listEventsModal" ng-controller="listEventsCtl" role="dialog" aria-labelledby="listEventsDiversModalLabel"   data-backdrop="static">
            <div class="modal-dialog" style="width:50%;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="listEventsModalLabel">Liste des Evenements de membre <span id="employe" class="danger"></span></h4>
                    </div>
                    <div class="modal-body" >
                        <h3>Membre</h3>
                        <table>
                            <tbody>
                            <tr>
                                <th style="width:150px;">Matricule</th>
                                <td style="width:300px;" align="center">{{membre.matricule}}</td>
                                <th style="width:150px;">Nom</th>
                                <td align="center">{{membre.nomComplet}}</td>
                                <th style="width:150px;">Sexe</th>
                                <td align="center" style="width:300px;">{{membre.sexe}}</td>
                            </tr>
                            <tr>
                                <th style="width:150px;">Domicile</th>
                                <td align="center">{{membre.lieuHabitation}}</td>
                                <th style="width:150px;">Contact</th>
                                <td align="left" style="width:300px;">{{membre.cellulaire}}</td>
                                <th style="width:150px;">Email</th>
                                <td align="left" style="width:300px;">{{membre.email}}</td>
                            </tr>

                            </tbody>
                        </table>

                        <p>&nbsp;</p>
                        <%--<div id="toolbarMouvementConge" >--%>
                            <%--<div class="form-inline">--%>
                                <%--<button type="button" class="btn btn-primary" title="Nouvelle resolution" id="btnAddAbsence"><span class="glyphicon glyphicon-plus"></span> Nouvelle resolution</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <table id="tableEvent"  class="table table-info table-striped"
                               data-toggle="table"
                               data-toolbar="#toolbarMouvementConge"
                               data-single-select="true"
                               data-height="300"
                               data-show-export="true"
                               data-export-dataType="all"
                               data-sort-order="desc"
                               data-export
                               data-pagination="false"
                               data-search="true">
                            <thead>
                            <tr>
                                <th data-field="events" data-formatter="titreFormatter">Titre</th>
                                <th data-field="events" data-formatter="descriptionFormatter">Description</th>
                                <th data-field="events" data-formatter="lieuFormatter">Lieu</th>
                                <th data-field="events" data-formatter="timeFormatter">Debut-Fin</th>
                                <%--<th data-field="events">Fin</th>--%>
                                <th data-field="events" data-formatter="rappelFormatter">Notification</th>
                                <th data-field="events" data-formatter="typeEventFormatter">Type</th>
                                <%--<th data-field="id" data-formatter="optionEventFormatter" data-width="100px" data-align="center">Options</th>--%>
                                <!--  <th data-field="mtnVerse" data-align="right">Montant vers&eacute;</th> -->
                            </tr>
                            </thead>
                        </table>
                        <br>
                    </div>
                </div>
            </div>
        </div>


        <%--<div class="modal main-popup fade" id="listCourriersModal" ng-controller="listCourriersCtl" role="dialog" aria-labelledby="listCourriersDiversModalLabel"   data-backdrop="static">--%>
            <%--<div class="modal-dialog" style="width:50%;">--%>
                <%--<div class="modal-content">--%>
                    <%--<div class="modal-header">--%>
                        <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close">--%>
                            <%--<span aria-hidden="true">&times;</span>--%>
                        <%--</button>--%>
                        <%--<h4 class="modal-title" id="listCourriersModalLabel">Liste des Courriers de membre <span id="employes" class="danger"></span></h4>--%>
                    <%--</div>--%>
                    <%--<div class="modal-body" >--%>
                        <%--<h3>Membre</h3>--%>
                        <%--<table>--%>
                            <%--<tbody>--%>
                            <%--<tr>--%>
                                <%--<th style="width:150px;">Matricule</th>--%>
                                <%--<td style="width:300px;" align="center">{{membre.matricule}}</td>--%>
                                <%--<th style="width:150px;">Nom</th>--%>
                                <%--<td align="center">{{membre.nomcomplet}}</td>--%>
                                <%--<th style="width:150px;">Sexe</th>--%>
                                <%--<td align="center" style="width:300px;">{{membre.sexe}}</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<th style="width:150px;">Domicile</th>--%>
                                <%--<td align="center">{{membre.lieuHabitation}}</td>--%>
                                <%--<th style="width:150px;">Contact</th>--%>
                                <%--<td align="left" style="width:300px;">{{membre.cellulaire}}</td>--%>
                                <%--<th style="width:150px;">Email</th>--%>
                                <%--<td align="left" style="width:300px;">{{membre.email}}</td>--%>
                            <%--</tr>--%>

                            <%--</tbody>--%>
                        <%--</table>--%>

                        <%--<p>&nbsp;</p>--%>
                        <%--&lt;%&ndash;<div id="toolbarMouvementConge" >&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<div class="form-inline">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<button type="button" class="btn btn-primary" title="Nouvelle resolution" id="btnAddAbsence"><span class="glyphicon glyphicon-plus"></span> Nouvelle resolution</button>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                        <%--<table id="tableCourrier"  class="table table-info table-striped"--%>
                               <%--data-toggle="table"--%>
                               <%--data-toolbar="#toolbarMouvementConge"--%>
                               <%--data-single-select="true"--%>
                               <%--data-height="300"--%>
                               <%--data-show-export="true"--%>
                               <%--data-export-dataType="all"--%>
                               <%--data-sort-order="desc"--%>
                               <%--data-export--%>
                               <%--data-pagination="false"--%>
                               <%--data-search="true">--%>
                            <%--<thead>--%>
                            <%--<tr>--%>
                                <%--<th data-field="reference">Reference</th>--%>
                                <%--<th data-field="objet">Objet</th>--%>
                                <%--<th data-field="libelle">Libelle</th>--%>
                                <%--<th data-field="modeleDoc" data-formatter="modeldocFormatter">Modele document</th>--%>
                                <%--<th data-field="id" data-formatter="optionFormatterDoc" data-width="100px" data-align="center">Options</th>--%>
                            <%--</tr>--%>
                            <%--</thead>--%>
                        <%--</table>--%>
                        <%--<br>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>




        <%--<div class="modal main-popup fade" id="listReunionsModal" ng-controller="listReunionsCtl" role="dialog" aria-labelledby="listReunionsDiversModalLabel"   data-backdrop="static">--%>
            <%--<div class="modal-dialog" style="width:50%;">--%>
                <%--<div class="modal-content">--%>
                    <%--<div class="modal-header">--%>
                        <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close">--%>
                            <%--<span aria-hidden="true">&times;</span>--%>
                        <%--</button>--%>
                        <%--<h4 class="modal-title" id="listCourriersModalLabel">Liste des Courriers de membre <span id="employes" class="danger"></span></h4>--%>
                    <%--</div>--%>
                    <%--<div class="modal-body" >--%>
                        <%--<h3>Membre</h3>--%>
                        <%--<table>--%>
                            <%--<tbody>--%>
                            <%--<tr>--%>
                                <%--<th style="width:150px;">Matricule</th>--%>
                                <%--<td style="width:300px;" align="center">{{membre.matricule}}</td>--%>
                                <%--<th style="width:150px;">Nom</th>--%>
                                <%--<td align="center">{{membre.nomcomplet}}</td>--%>
                                <%--<th style="width:150px;">Sexe</th>--%>
                                <%--<td align="center" style="width:300px;">{{membre.sexe}}</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<th style="width:150px;">Domicile</th>--%>
                                <%--<td align="center">{{membre.lieuHabitation}}</td>--%>
                                <%--<th style="width:150px;">Contact</th>--%>
                                <%--<td align="left" style="width:300px;">{{membre.cellulaire}}</td>--%>
                                <%--<th style="width:150px;">Email</th>--%>
                                <%--<td align="left" style="width:300px;">{{membre.email}}</td>--%>
                            <%--</tr>--%>

                            <%--</tbody>--%>
                        <%--</table>--%>

                        <%--<p>&nbsp;</p>--%>
                        <%--&lt;%&ndash;<div id="toolbarMouvementConge" >&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<div class="form-inline">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<button type="button" class="btn btn-primary" title="Nouvelle resolution" id="btnAddAbsence"><span class="glyphicon glyphicon-plus"></span> Nouvelle resolution</button>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                        <%--<table id="tableReunion"  class="table table-info table-striped"--%>
                               <%--data-toggle="table"--%>
                               <%--data-toolbar="#toolbarMouvementConge"--%>
                               <%--data-single-select="true"--%>
                               <%--data-height="300"--%>
                               <%--data-show-export="true"--%>
                               <%--data-export-dataType="all"--%>
                               <%--data-sort-order="desc"--%>
                               <%--data-export--%>
                               <%--data-pagination="false"--%>
                               <%--data-search="true">--%>
                            <%--<thead>--%>
                            <%--<tr>--%>
                                <%--<th data-field="reference">Reference</th>--%>
                                <%--<th data-field="objet">Objet</th>--%>
                                <%--<th data-field="libelle">Libelle</th>--%>
                                <%--<th data-field="modeleDoc" data-formatter="modeldocFormatter">Modele document</th>--%>
                                <%--<th data-field="id" data-formatter="optionFormatter" data-width="100px" data-align="center">Options</th>--%>
                            <%--</tr>--%>
                            <%--</thead>--%>
                        <%--</table>--%>
                        <%--<br>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>

        <div class="modal main-popup fade" id="listReglementsModal" ng-controller="listReglementsCtl" role="dialog" aria-labelledby="listReglementsDiversModalLabel"   data-backdrop="static">
            <div class="modal-dialog" style="width:50%;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="listReglementsModalLabel">Liste des  Reglements de membre <span id="employejs" class="danger"></span></h4>
                    </div>
                    <div class="modal-body" >
                        <h3>Membre</h3>
                        <table>
                            <tbody>
                            <tr>
                                <th style="width:150px;">Matricule</th>
                                <td style="width:300px;" align="center">{{membre.matricule}}</td>
                                <th style="width:150px;">Nom</th>
                                <td align="center">{{membre.nomComplet}}</td>
                                <th style="width:150px;">Sexe</th>
                                <td align="center" style="width:300px;">{{membre.sexe}}</td>
                            </tr>
                            <tr>
                                <th style="width:150px;">Domicile</th>
                                <td align="center">{{membre.lieuHabitation}}</td>
                                <th style="width:150px;">Contact</th>
                                <td align="left" style="width:300px;">{{membre.cellulaire}}</td>
                                <th style="width:150px;">Email</th>
                                <td align="left" style="width:300px;">{{membre.email}}</td>
                            </tr>

                            </tbody>
                        </table>

                        <p>&nbsp;</p>
                        <%--<div id="toolbarMouvementConge" >--%>
                        <%--<div class="form-inline">--%>
                        <%--<button type="button" class="btn btn-primary" title="Nouvelle resolution" id="btnAddAbsence"><span class="glyphicon glyphicon-plus"></span> Nouvelle resolution</button>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <table id="tableReglements"  class="table table-info table-striped"
                               data-toggle="table"
                               data-toolbar="#toolbarMouvementConge"
                               data-single-select="true"
                               data-height="300"
                               data-show-export="true"
                               data-export-dataType="all"
                               data-sort-order="desc"
                               data-export
                               data-pagination="false"
                               data-search="true">
                            <thead>
                            <tr>
                                <th data-field="periodePaie"  data-formatter="periodeAFormatter" data-align="center">Periode debut</th>
                                <th data-field="appelfond" data-formatter="libelleAppelFormatter" data-align="center">Appel fond</th>
                                <th data-field="appelfond" data-formatter="fixeAppelFormatter" data-align="center">Fixe/Variable</th>
                                <%--<th data-field="membres" data-formatter="matriculeFormatter" data-align="center">Matricule</th>--%>
                                <%--<th data-field="membres" data-formatter="nomFormatterAppel" data-align="left">Nom</th>--%>
                                <th data-field="dcreate" data-align="center">Date de reglement</th>
                                <th data-field="mMontantdu" data-align="center">Montant du</th>
                                <th data-field="mMontantverse"  data-align="center">Montant verse</th>
                                <th data-field="mMontantrestant"  data-align="center">Montant restant</th>


                                <th data-field="id" data-formatter="AppeloptionFormatter" data-align="center">Options</th>
                            </tr>
                            </thead>
                        </table>
                        <br>
                    </div>
                </div>
            </div>
        </div>


        <div class="modal main-popup fade" id="listProfilsModal" ng-controller="listProfilsCtl" role="dialog" aria-labelledby="listProfilsModalLabel"   data-backdrop="static">
            <div class="modal-dialog" style="width:60%;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="listProfilsModalLabel">Historique des Profils  de membre <span id="employejs" class="danger"></span></h4>
                    </div>
                    <div class="modal-body" >
                        <h3>Membre</h3>
                        <table>
                            <tbody>
                            <tr>
                                <th style="width:150px;">Matricule</th>
                                <td style="width:300px;" align="center">{{membre.matricule}}</td>
                                <th style="width:150px;">Nom</th>
                                <td align="center">{{membre.nomComplet}}</td>
                                <th style="width:150px;">Sexe</th>
                                <td align="center" style="width:300px;">{{membre.sexe}}</td>
                            </tr>
                            <tr>
                                <th style="width:150px;">Domicile</th>
                                <td align="center">{{membre.lieuHabitation}}</td>
                                <th style="width:150px;">Contact</th>
                                <td align="left" style="width:300px;">{{membre.cellulaire}}</td>
                                <th style="width:150px;">Email</th>
                                <td align="left" style="width:300px;">{{membre.email}}</td>
                            </tr>

                            </tbody>
                        </table>

                        <p>&nbsp;</p>

                        <div class="text-right"><button type="button" class="btn btn-primary" title="Nouveau Profil" id="btnAddContrat"><span class="glyphicon glyphicon-plus"></span> Profil</button></div>


                            <form id="formProfils" class="form-profils" name="formProfils" role="form" novalidate="novalidate">
                                <div class="row">
                                    <div class="col-md-6 col-sm-8 col-xs-7">

                                        <%--<form action="#" class="form-horizontal">--%>
                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <h3><span class="fa fa-pencil"></span>Infos &eacute;glise</h3>
                                                <%--<p>This information lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer faucibus, est quis molestie tincidunt, elit arcu faucibus erat.</p>--%>
                                            </div>
                                            <div class="panel-body form-group-separated">

                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-5 control-label">Date de conversion</label>
                                                    <div class="col-md-8 col-xs-7">
                                                        <input type="text" id="dCversPop" name="dCversPop"   class="form-control datetimePicker" ng-model="infochurch.dCvers" placeholder="Date de conversion"/>
                                                    </div>
                                                    <%--<div ng-show="formAjout.dCvers.$invalid &&  formAjout.dCvers.$error.required">--%>
                                                    <%--<div style="color:red;">Sexe est obligatoire.</div>--%>
                                                    <%--</div>--%>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-5 control-label">Baptis&eacute;(e) le</label>
                                                    <div class="col-md-8 col-xs-7">
                                                        <input type="text" id="dbaptemePop" name="dbaptemePop" class="form-control datetimePicker" ng-required="true" ng-model="infochurch.dbapteme" placeholder="Baptis&eacute;(e) le"/>
                                                        <div ng-show="formProfils.dbaptemePop.$invalid &&  formProfils.dbaptemePop.$error.required">
                                                            <div style="color:red;"> baptise Par est obligatoire.</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-5 control-label">Baptis&eacute;(e) par</label>
                                                    <div class="col-md-8 col-xs-7">
                                                        <input type="text" id="baptiseParPop" name="baptiseParPop" required="required" class="form-control" placeholder="Baptis&eacute;(e) par" />
                                                        <div ng-show="formProfils.baptiseParPop.$pristine &&  formProfils.baptiseParPop.$error.required">
                                                            <div style="color:red;"> Baptis&eacute;(e) le est obligatoire.</div>
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-5 control-label">Lieu du Bapteme</label>
                                                    <div class="col-md-8 col-xs-7">
                                                        <input type="text" id="lieubaptemePop" name="lieubaptemePop" required="required"  class="form-control" placeholder="Lieu du Bapteme" />
                                                        <div ng-show="formProfils.lieubaptemePop.$pristine &&  formProfils.lieubaptemePop.$error.required">
                                                            <div style="color:red;">Lieu bapteme est obligatoire.</div>
                                                        </div>
                                                    </div>

                                                </div>


                                            </div>
                                        </div>

                                    </div>
                                    <div class="col-md-3">


                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <h3><span class="fa fa-cog"></span> Participe a la vie d'&eacute;glise</h3>
                                                <p></p>
                                            </div>
                                            <div class="panel-body form-horizontal form-group-separated">
                                                <div class="form-group">
                                                    <label class="col-md-6 col-xs-6 control-label">vie d'&eacute;glise</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <label class="switch">
                                                            <input type="checkbox" name="vieEglisePop" ng-model="infochurch.vieEglise"  />
                                                            <span></span>
                                                        </label>
                                                        <%--<div ng-show="formAjout.vieEglise.$dirty &&  formAjout.vieEglise.$error.required">--%>
                                                        <%--<div style="color:red;">vie Eglise est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>

                                                </div>
                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>ng-required="true"--%>
                                                    <div class="col-md-6 col-xs-6">
                                                        <input type="text" id="dvieEglisedatePop" name="dvieEglisedatePop" class="form-control datetimePicker" placeholder="Depuis le" ng-model="infochurch.dvieEglisedate"  />
                                                        <%--<div ng-show="formAjout.vieEglisedate.$dirty &&  formAjout.vieEglisedate.$error.required">--%>
                                                        <%--<div style="color:red;">Date debut est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>
                                                    <div class="col-md-6 col-xs-6">
                                                        <input type="text" id="dvieEglisedatefinPop" name="dvieEglisedatefinPop" class="form-control datetimePicker" placeholder="Au" ng-model="infochurch.dvieEglisedatefin"/>

                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>ng-required="true"--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" id="qualiteViemembrePop" name="qualiteViemembrePop"  class="form-control" placeholder="Qaulit&eacute;" ng-model="infochurch.qualiteViemembre" onkeyup='this.value=this.value.toUpperCase()'/>
                                                        <%--<div ng-show="formAjout.qltitevieEgl.$dirty &&  formAjout.qltitevieEgl.$error.required">--%>
                                                        <%--<div style="color:red;">Champs est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-6 col-xs-6 control-label">Travail a l'&eacute;glise</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <label class="switch">
                                                            <input type="checkbox" name="workPop" ng-model="infochurch.work" />
                                                            <span></span>
                                                        </label>
                                                        <%--<div ng-show="formAjout.work.$dirty &&  formAjout.work.$error.required">--%>
                                                        <%--<div style="color:red;">Champs est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>
                                                </div>





                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>ng-required="true"--%>
                                                    <div class="col-md-6 col-xs-6">
                                                        <input type="text" id="demployeDatePop"  name="demployeDatePop" class="form-control datetimePicker" placeholder="Depuis le" ng-model="infochurch.demployeDate"/>
                                                        <%--<div ng-show="formAjout.employeDate.$dirty &&  formAjout.employeDate.$error.required">--%>
                                                        <%--<div style="color:red;">Champs est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>
                                                    <div class="col-md-6 col-xs-6">
                                                        <input type="text" id="demployeDatefinPop" name="demployeDatefinPop" class="form-control datetimePicker" placeholder="Au" ng-model="infochurch.demployeDatefin"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>ng-required="true"--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" id="employeQltePop" name="employeQltePop"  class="form-control" placeholder="Qaulit&eacute;" ng-model="infochurch.employeQlte" onkeyup='this.value=this.value.toUpperCase()'/>
                                                        <%--<div ng-show="formAjout.employeQlte.$dirty &&  formAjout.employeQlte.$error.required">--%>
                                                        <%--<div style="color:red;">Champs est obligatoire.</div>--%>
                                                        <%--</div> --%>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" id="lieupostePop" name="lieupostePop" class="form-control" placeholder="Lieu de poste" ng-model="infochurch.lieuposte" onkeyup='this.value=this.value.toUpperCase()'/>
                                                        <%--<div ng-show="formAjout.lieuposte.$dirty &&  formAjout.lieuposte.$error.required">--%>
                                                        <%--<div style="color:red;">Champs est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>
                                                </div>







                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-3">


                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <h3><span class="fa fa-cog"></span> Autres Comfirmations</h3>
                                                <p></p>
                                            </div>
                                            <div class="panel-body form-horizontal form-group-separated">
                                                <div class="form-group">
                                                    <label class="col-md-6 col-xs-6 control-label">Saint Esprit</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <label class="switch">
                                                            <input type="checkbox" name="confirmationPop" ng-model="infochurch.confirmation"  />
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                    <%--<div ng-show="formAjout.cotise.$dirty &&  formAjout.cotise.$error.required">--%>
                                                    <%--<div style="color:red;">Comfirmation est obligatoire.</div>--%>
                                                    <%--</div>--%>
                                                </div>





                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" id="lieuStespritPop" name="lieuStespritPop" class="form-control" placeholder="Lieu" ng-model="infochurch.lieuStesprit"/>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-6 col-xs-6 control-label">Sous Discipline</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <label class="switch">
                                                            <input type="checkbox" name="disciplinePop" ng-model="infochurch.discipline"  />
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                    <%--<div ng-show="formAjout.cotise.$dirty &&  formAjout.cotise.$error.required">--%>
                                                    <%--<div style="color:red;">Comfirmation est obligatoire.</div>--%>
                                                    <%--</div>--%>
                                                </div>
                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>ng-required="true"--%>
                                                    <div class="col-md-6 col-xs-6">
                                                        <input type="text" id="ddisciplDatePop"  name="ddisciplDatePop" class="form-control datetimePicker" placeholder="Depuis le" ng-model="infochurch.ddisciplDate"/>
                                                        <%--<div ng-show="formAjout.employeDate.$dirty &&  formAjout.employeDate.$error.required">--%>
                                                        <%--<div style="color:red;">Champs est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>
                                                    <div class="col-md-6 col-xs-6">
                                                        <input type="text" id="ddisciplDatefinPop" name="ddisciplDatefinPop" class="form-control datetimePicker" placeholder="Au" ng-model="infochurch.ddisciplDatefin"/>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <%--<label class="col-md-3 col-xs-5 control-label">Habitation</label>--%>
                                                    <div class="col-md-6 col-xs-6">
                                                        <textarea name="motifdiscpPop" id="motifdiscpPop"  rows="4" placeholder="Motif" cols="20"
                                                                  ng-model="infochurch.motifdiscp" onkeyup='this.value=this.value.toUpperCase()'></textarea>

                                                        <%--<div ng-show="formAjout.lieuposte.$dirty &&  formAjout.lieuposte.$error.required"disciplMotifid="disciplMotif" name="disciplMotif" class="form-control" />>--%>
                                                        <%--<div style="color:red;">Champs est obligatoire.</div>--%>
                                                        <%--</div>--%>
                                                    </div>
                                                </div>


                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div id="actionContrat" class="col-md-12 text-right">
                                                <span></span>&nbsp;
                                                <input type="text"class="hidden" ng-hide="true" name="idMembres" ng-model="membre.id"/>

                                                <button type="button" id="btnCancelContrat" type="reset" class="btn btn-default">Annuler</button>
                                                <button type="submit" class="btn btn-success" ng-disabled="formProfils.$invalid" >Valider</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        <p>&nbsp;</p>
                        <table id="tableProfils"  class="table table-info table-striped"
                               data-toggle="table"

                               data-single-select="true"
                               data-height="300"
                               data-show-export="true"
                               data-export-dataType="all"
                               data-sort-order="desc"
                               data-export
                               data-pagination="false"
                               data-search="true">
                            <thead>
                            <tr>
                                <th data-field="dbapteme" data-align="right">Date de bapteme</th>
                                <th data-field="vieEglise" data-formatter="vieEgliseFormatter" data-align="left" data-sortable="true">Vie d'eglise/Responsabilite</th>
                                <th data-field="work" data-formatter="workFormatter" data-align="left" data-sortable="true">Employ&eacute; / Fonction</th>
                                <th data-field="confirmation" data-formatter="confirmationFormatter" data-align="left">Saint Esprit / Lieu</th>

                                <th data-field="discipline" data-formatter="disciplineFormatter">Sous discipline/ Date</th>
                                <th data-field="motifdiscp"  data-align="center">Motif discipline</th>



                                <th data-field="statut" data-align="right" data-formatter="statutFormatter">Statut</th>

                                <%--<th data-field="id" data-formatter="AppeloptionFormatter" data-align="center">Options</th>--%>
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
    app.controller('formAjoutCtrl', function ($scope) {
      //  $scope.typeSanction = jQuery("#idTypeSanction option:first").val();
        $scope.pupulateForm = function (membre) {
            $scope.membre = membre;
        };
        $scope.pupulateContrat = function (infochurch) {
            $scope.infochurch = infochurch;
        };
         $scope.initForm = function () {
            // var typeSanction = {};
            // typeSanction.id = jQuery("#idTypeSanction option:first").val();
            $scope.membre = {};
            $scope.infochurch = {};
        };
    }).controller('optionsModalCtrl', function ($scope) {
        $scope.populateForm = function (membre) {
            $scope.membre = membre;
        };
    }).controller('listAppelfondsCtl', function ($scope) {
       // $scope.mouvementCongeAction = false;
        $scope.populateForm = function (membre) {
           // $scope.mouvementCongeAction = membre.nombreJourdu > 0 ? true : false;
            $scope.membre = membre;
        };
        $scope.populateContrat = function (mouvementConge) {
           // $scope.mouvementConge = mouvementConge;
        };
    }).controller('listEventsCtl', function ($scope) {
        // $scope.mouvementCongeAction = false;
        $scope.populateForm = function (membre) {
            // $scope.mouvementCongeAction = membre.nombreJourdu > 0 ? true : false;
            $scope.membre = membre;
        };
        $scope.populateContrat = function (mouvementConge) {
            // $scope.mouvementConge = mouvementConge;
        };
    }).controller('listReunionsCtl', function ($scope) {
            // $scope.mouvementCongeAction = false;
            $scope.populateForm = function (membre) {
                // $scope.mouvementCongeAction = membre.nombreJourdu > 0 ? true : false;
                $scope.membre = membre;
            };
            $scope.populateContrat = function (mouvementConge) {
                // $scope.mouvementConge = mouvementConge;
            };
        })
         .controller('listProfilsCtl', function ($scope) {
                // $scope.mouvementCongeAction = false;
                $scope.populateForm = function (membre) {
                    // $scope.mouvementCongeAction = membre.nombreJourdu > 0 ? true : false;
                    $scope.membre = membre;
                };
                $scope.populateContrat = function (infochurch) {
                     $scope.infochurch = infochurch;
                };
        })
        .controller('listReglementsCtl', function ($scope) {
            // $scope.mouvementCongeAction = false;
            $scope.populateForm = function (membre) {
                // $scope.mouvementCongeAction = membre.nombreJourdu > 0 ? true : false;
                $scope.membre = membre;
            };
            $scope.populateContrat = function (mouvementConge) {
                // $scope.mouvementConge = mouvementConge;
            };
        }).controller('listCourriersCtl', function ($scope) {
            // $scope.mouvementCongeAction = false;
            $scope.populateForm = function (membre) {
                // $scope.mouvementCongeAction = membre.nombreJourdu > 0 ? true : false;
                $scope.membre = membre;
            };
            $scope.populateContratpp = function (mouvementConge) {
                // $scope.mouvementConge = mouvementConge;
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
        $table = $('#table');$tableEvent=$('#tableEvent');//.form-contrat, .form-absence, .form-mouvement-conge, .form-enfant, .form-conjoint,, #btnAddContrat, .verif-matricule, .verif-numerocnps
        $tableConge=$('#tableConge');$tableRegl=$('#tableReglements');$tableReun=$('#tableReunion');$tableCour=$('#tableCourrier');$tableProfils=$('#tableProfils');
        $(" .form-appel-fonds").hide();$(" .verif-matricule").hide(); $(" .form-profils").hide();$('#btnAddContrat').hide();
        var btnAddMember = $('#addMember');
        $tableFinal = $('#members-selected');
        // $('#tab-third').hide();
        // $('#three').hide();
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
        $('#situationMatrimoniale,#civil,#groupe,#catgroupe').select2({
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
        loadAppelfondsByMembres(membre.id);
    }
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
