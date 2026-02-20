<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row" >
	<div class="col-md-12">
		<div class="panel panel-warning">
			<div class="panel-heading">
				<div class="panel-title-box">
					<h3>Liste du personnel</h3>
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
											<li><a href="#" onclick="widgetView('new')">Livre de paie</a></li>
											<%--<li class="divider"></li>--%>
											<%--<li><a href="#" onclick="widgetView('histo')">Historique Bulletins</a></li>--%>
									</ul>
								</div>

							</div>
						<br>


               <div id="tableWidget" class="widgetcontent">
                <div id="toolbar1">
                    <div class="form-inline">
                     <button id="btnGenerer" type="submit" data-toggle="modal" data-target="#rhpModal" title="Cocher les employes mis en sommeil" class="btn btn-success "><i class="fa fa-plus"></i>Generer livre de paie</button>
                    <button id="btnGenerer" type="button" data-toggle="modal" data-target="#rhpModalImprimer" class="btn btn-success "  onclick="chargerBull()"><i class="fa fa-plus"></i>Imprimer bulletin</button>
                    <button id="btnVrmt" type="button" data-toggle="modal" data-target="#rhpModalVirmt" class="btn btn-success "><i class="fa fa-plus"></i>Ordre de virement</button>
                    <button id="btnGenererNet" type="submit" title="Prise en compte Net apayer" class="btn btn-info "><i class="fa fa-plus"></i>Net à payer regul</button>
                    </div>
                </div>
		    	<form action="#" id="formList">
		<table id="table" class="table table-info table-striped"
        			       	data-toggle="table"
        			       	data-click-to-select="true"
        			       	data-single-select="true"
        			       	data-sort-name="flag" data-sort-order="desc"
        				   data-url="<%=request.getContextPath()%>/personnels/specifique/special-contracts/listcontratspecjson"
        			       	data-side-pagination="server"
        			       	data-pagination="true"
        			       	data-page-list="[ 20, 50, 100, 200,500]"
        			       	data-search="true">
        			    <thead>
        			        <tr>
        			        	<th data-field="employee" data-formatter="matriFormatter" data-align="left" data-sortable="true">Matricule</th>
        			        	<th data-field="employee" data-formatter="nomFormatter" data-align="left" data-sortable="true">Nom & Prenom</th>
        			        	<th data-field="typeContrat"   data-align="left" data-sortable="true">Categ Speciale</th>
        			        	<th data-field="fonction" data-formatter="fonctionFormatter"data-align="left"  data-sortable="true">Fonction</th>
        			        	<th data-field="remunerationForfaitaire" data-align="left"  data-sortable="true">Net a payer</th>
        			        	<th data-field="modepaiement" data-align="left"  data-sortable="true">Mode de paiement</th>
        			        	<th data-field="id" data-formatter="optionFormatter" data-width="100px" data-align="center">Options</th>
        			        </tr>
        			    </thead>
        			</table>
			</form>
		</div>
    </div><!--widgetbox-->
</div><!-- widgetcontent-->
</div>
</div>

<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog modal-dialog-scrollable" style="width:60%;">
        <div class="modal-content">
            <form id="formAjout" name="formAjout" class="form-horizontal" novalidate="novalidate" role="form" ng-controller="formAjoutCtrl">

                <!-- HEADER -->
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">Employes non standard</h4>
                </div>

                <!-- BODY -->
                <div class="modal-body" style="height: 600px; overflow-y: scroll">

                    <div class="panel panel-default tabs">

                        <!-- NAV TABS -->
                        <ul class="nav nav-tabs" role="tablist">
                            <li class="active">
                                <a href="#tab-first" role="tab" data-toggle="tab">Information personnelle</a>
                            </li>
                            <li>
                                <a href="#tab-second" role="tab" data-toggle="tab">Contrat specifique</a>
                            </li>
                        </ul>

                        <!-- TAB CONTENT -->
                        <div class="panel-body tab-content">

                            <!-- TAB 1 -->
                            <div class="tab-pane fade in active" id="tab-first">
                                <div class="row">

                                    <!-- COL 1 -->
                                    <div class="col-md-3 col-sm-4 col-xs-5">
                                        <div class="panel panel-default">
                                            <div class="panel-body form-horizontal form-group-separated">
                                                <h3 id="nomcplet"><span class="fa fa-user"></span></h3>
                                                <p id="fnction"></p>
                                                <div class="text-center profile-mini" id="user_image">
                                                    <img id="defImg" style="width:133px;height:171px" class="img-thumbnail"/>
                                                </div>
                                                <br>
                                               <!--  <div class="form-group">
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="file" id="imag" onchange="javascript:verifier();" name="files[]" multiple/>
                                                        <label class="control-label" id="error" style="color:#ff0033"></label>
                                                    </div>
                                                </div>-->
                                            </div>

                                            <div class="panel-body form-group-separated">
                                                <div class="form-group">
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" id="lieuHabitation" name="lieuHabitation"
                                                               onkeyup="this.value=this.value.toUpperCase()"
                                                               class="form-control"
                                                               placeholder="Habitation" ng-model="employee.lieuHabitation"/>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <div class="col-md-12 col-xs-12">
                                                        <input type="text" class="form-control datePicker" id="dofbrid"
                                                               name="dofbrid" placeholder="Date de naissance"
                                                                 ng-model="employee.dofbrid"/>
                                                                 <div ng-show="formAjout.dofbrid.$invalid && formAjout.dofbrid.$error.required">
                                                                 <div style="color:red;">Date de naissance est obligatoire.</div>
                                                                 </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- COL 2 -->
                                    <div class="col-md-6 col-sm-8 col-xs-7">
                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <h3><span class="fa fa-pencil"></span> Informations generales</h3>
                                            </div>

                                            <div class="panel-body form-group-separated">

                                                <div class="form-group">
                                                    <label class="col-md-3 col-xs-5 control-label">Matricule</label>
                                                    <div class="col-md-9 col-xs-7">
                                                        <div class="pull-right verif-matricule">
                                                            <img src="<%=request.getContextPath() %>/static/front-end/images/loaders/loader27.gif"/>
                                                        </div>
                                                        <input type="text" id="matricule" name="matricule"
                                                               ng-required="true" class="form-control"
                                                               ng-model="employee.matricule" placeholder="Matricule"
                                                               onkeyup="this.value=this.value.toUpperCase()"/>
                                                        <div ng-show="formAjout.matricule.$invalid && formAjout.matricule.$error.required">
                                                            <div style="color:red;">Matricule est obligatoire.</div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-3 col-xs-5 control-label">Nom</label>
                                                    <div class="col-md-9 col-xs-7">
                                                        <input type="text" id="nom" name="nom" class="form-control"
                                                               ng-required="true" ng-model="employee.nom"
                                                               placeholder="Nom" onkeyup="this.value=this.value.toUpperCase()"/>
                                                        <div ng-show="formAjout.nom.$invalid && formAjout.nom.$error.required">
                                                            <div style="color:red;">Nom est obligatoire.</div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-3 col-xs-5 control-label">Prenom</label>
                                                    <div class="col-md-9 col-xs-7">
                                                        <input type="text" id="prenom" name="prenom" class="form-control"
                                                               ng-required="true" ng-model="employee.prenom"
                                                               placeholder="Prénom" onkeyup="this.value=this.value.toUpperCase()"/>
                                                        <div ng-show="formAjout.prenom.$invalid && formAjout.prenom.$error.required">
                                                            <div style="color:red;">Prenom est obligatoire.</div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-3 col-xs-5 control-label">Sexe</label>
                                                    <div class="col-md-9 col-xs-7">
                                                        <label>
                                                            <input name="sexe" id="sexeMasculin" type="radio"
                                                                   class="radio-inline" value="Masculin"
                                                                   ng-model="employee.sexe" ng-required="true"/> Masculin
                                                        </label>
                                                        <label>
                                                            <input name="sexe" id="sexeFeminin" type="radio"
                                                                   class="radio-inline" value="Feminin"
                                                                   ng-model="employee.sexe" ng-required="true"/> Féminin
                                                        </label>
                                                        <div ng-show="formAjout.sexe.$invalid && formAjout.sexe.$error.required">
                                                            <div style="color:red;">Sexe est obligatoire.</div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-3 col-xs-5 control-label">Sit. Matri</label>
                                                    <div class="col-md-9 col-xs-7">
                                                        <select id="situationMatrimoniale" name="situationMatrimoniale"
                                                                class="form-control select2"
                                                                ng-model="employee.situationMatrimoniale"
                                                                ng-required="true">
                                                            <option value="0">AUCUN</option>
                                                            <option value="1">MARIE(E)</option>
                                                            <option value="2">CELIBATAIRE</option>
                                                            <option value="3">DIVORCE(E)</option>
                                                            <option value="4">VEUF(VE)</option>
                                                        </select>
                                                        <div ng-show="formAjout.situationMatrimoniale.$invalid && formAjout.situationMatrimoniale.$error.required">
                                                            <div style="color:red;">Situation matrimoniale est obligatoire.</div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-3 col-xs-5 control-label">Nombre d''enfants</label>
                                                    <div class="col-md-9 col-xs-7">
                                                        <input type="number" id="nbreEnft" name="nbreEnft"
                                                               class="form-control" ng-model="employee.nbreEnft"
                                                               placeholder="Nombre enfants"/>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>

                                    <!-- COL 3 -->
                                    <div class="col-md-3">
                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <h3><span class="fa fa-cog"></span> Parametres</h3>
                                            </div>

                                            <div class="panel-body form-horizontal form-group-separated">
                                                <div class="form-group">
                                                    <label class="col-md-8 col-xs-8 control-label">Actif</label>
                                                    <div class="col-md-4 col-xs-4">
                                                        <label class="switch">
                                                            <input type="checkbox" name="actif" ng-model="employee.actif"/>
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="col-md-12 col-xs-12">
                                                    <input type="text" id="phoneNumber" name="phoneNumber"
                                                           class="form-control" ng-model="employee.phoneNumber"
                                                           placeholder="Téléphone"/>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="col-md-12 col-xs-12">
                                                    <input type="text" id="email" name="email"
                                                           class="form-control" ng-model="employee.email"
                                                           placeholder="Email"/>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="col-md-12 col-xs-12">
                                                    <label for="nationalite">Nationalite <span class="required">*</span></label>
                                                    <select id="nationalite" name="nationalite"  ng-model="employee.nationalite"
                                                            class="form-control select2" ng-required="true">
                                                        <option value="0">AUCUN</option>
                                                        <option value="1">IVOIRIENNE</option>
                                                        <option value="2">BURKINABE</option>
                                                        <option value="3">CAMEROUNAISE</option>
                                                        <option value="4">CENTRAFRICAINE</option>
                                                        <option value="5">GUINEE</option>
                                                        <option value="6">ITALIENNE</option>
                                                        <option value="7">BENINOISE</option>
                                                        <option value="8">MALIENNE</option>
                                                        <option value="9">NIGERIENNE</option>
                                                        <option value="10">SENEGALAISE</option>
                                                        <option value="11">TOGOLAISE</option>
                                                        <option value="12">TUNISIENNE</option>
                                                        <option value="13">TURC</option>
                                                    </select>
                                                    <div ng-show="formAjout.nationalite.$invalid && formAjout.nationalite.$error.required">
                                                        <div style="color:red;">Nationalite est obligatoire.</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>

                            <!-- TAB 2 -->
                            <div class="tab-pane fade sectionContrat" id="tab-second">
                                <div class="row">
                                    <div class="col-md-10 col-sm-8 col-xs-7">
                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <h3><span class="fa fa-pencil"></span> Contrat specifique</h3>
                                            </div>

                                            <div class="panel-body form-group-separated">

                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-5 control-label">Type de contrat</label>
                                                    <div class="col-md-8 col-xs-7">
                                                        <select id="typeContrat" name="typeContrat"
                                                                class="form-control select2"
                                                                ng-model="speccontrat.typeContrat" ng-required="true">
                                                            <option value="0">AUCUN</option>
                                                            <option value="STAFF_PDG">STAFF_PDG</option>
                                                            <option value="DOZO">DOZO</option>
                                                            <option value="STAGE">STAGE</option>
                                                            <option value="AGENT_SECURITE">AGENT_SECURITE</option>
                                                        </select>
                                                        <div ng-show="formAjout.typeContrat.$invalid && formAjout.typeContrat.$error.required">
                                                            <div style="color:red;">Type de contrat est obligatoire.</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                        <label class="col-md-4 col-xs-5 control-label">Emploi</label>
                                                         <div class="col-md-8 col-xs-7">

                                                        </select>
                                                         <select id="fonction" name="fonction" class="form-control input-small select2" ng-model="speccontrat.fonction.id"     ng-init="speccontrat.fonction.id=fonction">
                                                              <option value="" disabled selected>-- Selectionnez fonction/emploi--</option>
                                                          </select>

                                                        <div ng-show="formAjout.fonction.$invalid && formAjout.fonction.$error.required">
                                                              <div style="color:red;">fonction est obligatoire.</div>
                                                         </div>
                                                       </div>
                                                </div>
                                                   <div class="form-group">
                                                          <label class="col-md-4 col-xs-5 control-label">Site</label>
                                                          <div class="col-md-8 col-xs-7">

                                                             </select>
                                                             <select id="site" name="site" class="form-control input-small select2" ng-model="speccontrat.site.id"     ng-init="speccontrat.site.id=site">
                                                               <option value="" disabled selected>-- Selectionnez Site/emploi--</option>
                                                              </select>

                                                               <div ng-show="formAjout.site.$invalid && formAjout.site.$error.required">
                                                                <div style="color:red;">Site est obligatoire.</div>
                                                               </div>
                                                          </div>
                                                  </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-5 control-label">Date debut</label>
                                                    <div class="col-md-8 col-xs-7">
                                                        <input type="text" id="dDeb" name="dDeb"
                                                               class="form-control datePicker"
                                                               ng-model="speccontrat.dDeb"
                                                               placeholder="Date de debut"/>
                                                        <div ng-show="formAjout.dDeb.$invalid && formAjout.dDeb.$error.required">
                                                            <div style="color:red;">Date de debut est obligatoire.</div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-5 control-label">Date de fin</label>
                                                 <div class="col-md-8 col-xs-7">
                                                            <input type="text" id="dFin" name="dFin"
                                                            class="form-control datePicker"
                                                             ng-model="speccontrat.dFin"
                                                              placeholder="Date de fin"/>
                                                            <div ng-show="formAjout.dFin.$invalid && formAjout.dFin.$error.required">
                                                             <div style="color:red;">Date de fin est obligatoire.</div>
                                                              </div>
                                                           </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-5 control-label">Mode de paiement</label>
                                                    <div class="col-md-8 col-xs-7">
                                                        <select id="modepaiement" name="modepaiement"
                                                                class="form-control select2"
                                                                ng-model="speccontrat.modepaiement" ng-required="true">
                                                            <option value="" disabled selected>-- Sélectionnez --</option>
                                                            <option value="virement-bancaire">Virement</option>
                                                            <option value="transfert-mobile-money">Mobile Money</option>
                                                            <option value="transfert-wave">Wave</option>
                                                        </select>
                                                        <div ng-show="formAjout.modepaiement.$invalid && formAjout.modepaiement.$error.required">
                                                            <div style="color:red;">Mode de paiement est obligatoire.</div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-5 control-label">Numero de compte /Telephone</label>
                                                    <div class="col-md-8 col-xs-7">
                                                        <input type="text" id="paiementNumber" name="paiementNumber"
                                                               class="form-control" ng-required="true"
                                                               ng-model="speccontrat.paiementNumber"
                                                               placeholder="Compte bancaire ou mobile"/>
                                                        <div ng-show="formAjout.paiementNumber.$invalid && formAjout.paiementNumber.$error.required">
                                                            <div style="color:red;">Numero de compte est obligatoire.</div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-5 control-label">Net a payer</label>
                                                    <div class="col-md-8 col-xs-7">
                                                        <input type="text" id="netpayer" name="netpayer"
                                                               class="form-control" ng-required="true"
                                                               ng-model="speccontrat.remunerationForfaitaire"
                                                               placeholder="Net à payer"/>
                                                        <div ng-show="formAjout.netpayer.$invalid && formAjout.netpayer.$error.required">
                                                            <div style="color:red;">Net a payer est obligatoire.</div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div> <!-- END TAB CONTENT -->
                    </div>
                </div>

                <!-- FOOTER -->
                <div class="modal-footer">
                    <input type="hidden" id="id" name="id" ng-model="employee.id">
                    <button type="button" class="btn btn-default" onclick="resetEditForm()" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success" ng-disabled="formAjout.$invalid" >Valider</button>
                </div>

            </form>
        </div>
    </div>
</div>





<!-- Modification info du personnel -->
<div class="modal fade" id="rhpModalModiftemps"  role="dialog" aria-labelledby="rhpModalModiftemps" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formModificationPersonneltemps" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">Modification du temps de travail du personnel (<span id="infoPersonnelmo">Information du personnel</span>)</h4>
				</div>
                <div class="modal-body">
                	<div class="form-group">
                        <label for="libelle" class="col-md-4 control-label">Nombre de jours</label>
                        <div class="col-md-8">
                            <input type="text" id="jourTravail"  name="jourTravail" class="form-control" required="required" placeholder=""30" />
                        </div>
                    </div>
                    	<div class="form-group">
                        <label for="libelle" class="col-md-4 control-label">Temps travail</label>
                        <div class="col-md-8">
                            <input type="text" id="temptravail"  name="temptravail" required="required" class="form-control" placeholder="173.33"/>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <input type="hidden" value="" id="matricule" name="matricule">
                    <input type="hidden" value="" id="idpers" name="idpers">
                     <input type="hidden" value="" id="idperiod" name="idperiod">
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>


<!-- Modification info du personnel -->
<div class="modal fade" id="rhpModalModiftemps"  role="dialog" aria-labelledby="rhpModalModiftemps" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formModificationPersonneltemps" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">Modification du temps de travail du personnel (<span id="infoPersonnelmo">Information du personnel</span>)</h4>
				</div>
                <div class="modal-body">
                	<div class="form-group">
                        <label for="libelle" class="col-md-4 control-label">Nombre de jours</label>
                        <div class="col-md-8">
                            <input type="text" id="jourTravail"  name="jourTravail" class="form-control" required="required" placeholder=""30" />
                        </div>
                    </div>
                    	<div class="form-group">
                        <label for="libelle" class="col-md-4 control-label">Temps travail</label>
                        <div class="col-md-8">
                            <input type="text" id="temptravail"  name="temptravail" required="required" class="form-control" placeholder="173.33"/>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <input type="hidden" value="" id="matricule" name="matricule">
                    <input type="hidden" value="" id="idpers" name="idpers">
                     <input type="hidden" value="" id="idperiod" name="idperiod">
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal  fade "  id="rhpModalPretModif" role="dialog" data-backdrop="static">
	<div class="modal-dialog ">
		<div class="modal-content">
				<form id="formPretmodif" action="#">
					<div class="widgetbox">
							<div class="headtitle">
							<h4 class="widgettitle">Modification Pret / Avance</h4>
					</div>
					<div class="widgetcontent" style="padding-bottom:0px;">
					<div class="form-group">
							<label for="service" class="col-md-4 control-label">Type</label>
							<div class="col-md-8">
							<select id="pret1" name="pret1" class="form-control select2">
							<c:forEach items="${listPrets}" var="pret">
							<option value="${pret.id}">${pret.libelle}</option>
							</c:forEach>
							</select>
							</div>
					</div>
					<div class="form-group">
							<label for="montant" class="col-md-4 control-label">Montant</label>
							<div class="col-md-8">
							<input type="text" id="montant1" name="montant1" class="form-control" placeholder="Montant" />
							</div>
					</div>
					<div class="form-group">
							<label for="" class="col-md-4 control-label">Periode debut prelev.</label>
							<div class="col-md-8">
							<select id="periodepaie1" name="periodepaie1" class="form-control select2">
							<c:forEach items="${listPeriodepaie}" var="periodePaie">
							<option value="${periodePaie.id}">${periodePaie.mois.mois} ${periodePaie.annee.annee}</option>
							</c:forEach>
							</select>
							</div>
					</div>
					<div class="form-group">
							<label for="" class="col-md-4 control-label">Date contraction</label>
							<div class="col-md-8">
							<input type="text" id="dEmprunt1" name="dEmprunt1" class="form-control" placeholder="Date contraction" />
							</div>
					</div>
					<div class="form-group">
							<label for="" class="col-md-4 control-label">Mensualite</label>
								<div class="col-md-8">
									<input type="text" id="echelonage1" name="echelonage1" class="form-control"  />
									<input type="hidden" id="idpers1" name="idpers1" class="form-control"  />

								</div>
					</div>
					<div class="form-group">
							<label class="col-md-4 control-label">Prelevement/Periode</label>
							<div class="col-md-8">
							{{prelevement}}
					</div>
							</div> -->
					<div class="form-group">
							<div class="col-md-12">
							<span class="pull-right">
							<span></span>&nbsp;&ndash;%&gt;
							<input type="hidden" id="idpret" name="idpret" class="form-control"  />
							<input class="btn btn-default" type="button" data-dismiss="modal" value="Annuler" />&nbsp;
							<input class="btn btn-success" type="submit" value="Valider" />
							<!-- </span> -->
							</div>
					</div>
							<%--</div><!--widgetcontent-->--%>
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
	                    Etes vous s�r de vouloir supprimer ?
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
    $scope.pupulateForm = function (employee) {
        $scope.employee = employee;
    };
     $scope.pupulateContrat = function (speccontrat) {
          $scope.speccontrat = speccontrat;
     };
    $scope.initForm = function () {
    	$scope.employee = {};
    	$scope.speccontrat= {};
    };
}).controller('formDeleteCtrl', function ($scope) {
    $scope.pupulateForm = function (employee) {
    	$scope.employee = employee;
    };

});
//End AngularJs

var actionUrl = "/personnels/specifique/enregisteremployee";
var actionDeleteUrl = "/personnels/supprimeremployee";
var action = "ajout";
var $table;
jQuery(document).ready(function($){
	$table = $('#table');
	$("#datenaissance, .datePicker, #dateNaissanceEnfant, #datearrivee, #datedebut, #datefin,  #dDeb, #dFin,#datedebutPop, #datefinPop,#dateDepot").datepicker({
            format: 'dd/mm/yyyy',
            showOtherMonths: true
        });
     loadFonction();
     loadSite();
	//Fermeture du modal
	$('#rhpModal').on('hidden.bs.modal', function () {
		var $scope = angular.element(document.getElementById("formAjout")).scope();
		$scope.$apply(function () {
            $scope.initForm();
        });
		//$("#id").val(""); //Initialisation de l'id
	});
	
	//Envoi des donnees
	$("#formAjout").submit(function(e){
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
                	if(action == "ajout"){
                		//Rechargement de la liste (le tableau)
                		$table.bootstrapTable('refresh');
                		$("#formAjout")[0].reset(); //Initialisation du formulaire
                        $("#rhpModal").modal('hide');
                	}
                	else{
                		//MAJ de la ligne modifi�e
                		$table.bootstrapTable('updateRow', {
                            index: $table.find('tr.selected').data('index'),
                            row: reponse.data
                        });
                	}
                }
                else if(reponse.result == "erreur_champ_obligatoire"){
                	alert("Saisie invalide");
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
	
	$("#formDelete").submit(function(e){
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
                }
                else if(reponse.result == "erreur_champ_obligatoire"){
                	alert("Saisie invalide");
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
	//$( ".select2" ).select2();
});


function optionFormatter(id, row, index) {
	/*var option = '<a onclick="selectPersInfo('+id+','+row.id+')" data-toggle="modal" data-target="#rhpModalModif" href="#" title="Modifier personnel [LIBELLE : '+row.employee.matricule+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';*/
	var option = '<a onclick="cherch2('+row.id+')" data-toggle="modal" data-target="#rhpModalModiftemps" href="#" title="Modifier personnel [LIBELLE : '+row.employee.matricule+' ]">  <span class="glyphicon glyphicon-list"></span></a>&nbsp;';
	option += '<a onclick="cherch2('+row.id+')" data-toggle="modal" data-target="#rhpModalModiftemps" href="#" title="Modifier personnel [LIBELLE : '+row.employee.matricule+' ]">  <span class="glyphicon glyphicon-list"></span></a>&nbsp;';
	option += '&nbsp;<a onclick="cherch('+row.employee.id+')" data-toggle="modal" data-target="#rhpModalPretModif" href="#" title="Calcul a lenvers bulletin [LIBELLE : '+row.employee.matricule+' ]"> <span class="glyphicon glyphicon-pencil"></span></a>';
	/* option += '<a onclick="listMouvementConge('+id+','+row.employee.id+')" data-toggle="modal" data-target="#listPrimesDiversModal" href="#" title="Modifier personnel [LIBELLE : '+row.employee.matricule+' ]">  <span class="glyphicon glyphicon-briefcase"></span></a>&nbsp;'; */

    return option;
}

function nationaliteFormatter(nationnalite) {
    return nationnalite.libelle;
}

function matriFormatter(employee, row, index) {
	if(row.employee.matricule == ''){
		return "";
	}
	return row.employee.matricule;
}
function specialFormatter(employee, row, index) {
	if(row.employee.typeContrat == ''){
		return "";
	}
	return row.employee.typeContrat;
}

function nomFormatter(employee, row, index) {
	if(row.employee.nom == ''){
		return "";
	}
	return row.employee.nomComplet;
}
function fonctionFormatter(fonction, row, index) {
	if(row.fonction.libelle == ''){
		return "";
	}
	return row.fonction.libelle;
}
//Chargement des fonctions
function loadFonction() {
    jQuery.ajax({
        type: "GET",
        url: baseUrl + "/personnels/listfonction",
        cache: false,
        success: function (response) {
            console.log(response);
            if (response != null) {
                tabledata = '';
                for (i = 0; i < response.length; i++) {
                    tabledata += '<option value="' + response[i].id + '" >' + response[i].libelle + '</option>';
                }
                //tabledata += "";
                jQuery('#fonction, #fonctionPop').html(tabledata);
            } else {
                alert('Failure! An error has occurred!');
            }
        },
        error: function () {

        },
    });
}

//Chargement des site
function loadSite() {
                     jQuery.ajax({
                         type: "GET",
                         url: baseUrl + "/carriere/paginersitess",
                         cache: false,
                         success: function (response) {
                             console.log(response);
                             if (response != null) {
                                 tabledata1 = '';
                                 for (i = 0; i < response.rows.length; i++) {
                                     tabledata1 += '<option value="' + response.rows[i].id + '" >' + response.rows[i].libelle + '</option>';
                                 }
                                 //tabledata += "";
                                 jQuery('#site').html(tabledata1);
                             } else {
                                 alert('Failure! An error has occurred!');
                             }
                         },
                         error: function () {

                         },
                     });

}

function edit(idCategorie){
	var $scope = angular.element(document.getElementById("formAjout")).scope();
    
	var rows = $table.bootstrapTable('getData');
	var employee = _.findWhere(rows, {id: idCategorie});

	$scope.$apply(function () {
        $scope.pupulateForm(employee);
    });
      //  jQuery(".sectionContrat input, .sectionContrat select").attr("disabled", "disabled");
       jQuery("#paiementNumber").removeAttr("disabled");
        jQuery("#modepaiement").removeAttr("disabled");
        updateComboAndRadioEmployee(employee)
        loadContratActifEmploye(employee);
}


// Chargement du contrat spécial de l'employé
function loadContratActifEmploye(employee) {
    jQuery.ajax({
        type: "GET",
        url: baseUrl + "/personnels/specifique/special-contracts/employee/" + employee.id,
        cache: false,
        success: function (response) {
            console.log("Contrat reçu:", response);
            if (response != null) {

                var $scope = angular.element(document.getElementById("formAjout")).scope();
                $scope.$apply(function () {
                    $scope.speccontrat = response.row; // 👈 Injection directe du DTO
                });

                // Mise à jour des champs non-Angular (select2, etc.)
                jQuery("#typeContrat").val(response.row.typeContrat).trigger("change");
                jQuery("#fonction").val(response.row.fonctionId).trigger("change");
                jQuery("#site").val(response.row.site).trigger("change");
                jQuery("#modepaiement").val(response.row.modepaiement).trigger("change");
                jQuery("#netpayer").val(response.row.remunerationForfaitaire).trigger("change");
                jQuery("#Ddeb").val(response.row.Ddeb).trigger("change");
                jQuery("#Dfin").val(response.row.Dfin).trigger("change");

            } else {
                alert('Aucun contrat trouvé pour cet employé.');
            }
        },
        error: function () {
            alert('Erreur lors du chargement du contrat.');
        },
    });
}



function updateComboAndRadioEmployee(employee) {
  //  loadServiceByTypeService(employee.service.typeService.id, employee.service.id);
    jQuery("#nationalite").val(employee.nationnalite.id).trigger("change");

    jQuery("#situationMatrimoniale").val(employee.situationMatrimoniale.toString().trim().toUpperCase()).trigger("change");
    jQuery("#nombreenfant").val(employee.nombrEnfant).trigger("change");
    jQuery("#situationEmploie").val(employee.situationEmploie).trigger("change");
    //Initialisation
     // Reset radios
       document.getElementById('sexeMasculin').checked = false;
       document.getElementById('sexeFeminin').checked = false;

       if (employee.sexe) {
           var sexe = employee.sexe.toString().trim().toLowerCase();

           if (sexe === "masculin") {
               document.getElementById('sexeMasculin').checked = true;
           }
           else if (sexe === "feminin") {
               document.getElementById('sexeFeminin').checked = true;
           }
       }


 }
function del(idCategorie){
	var $scope = angular.element(document.getElementById("formDelete")).scope();
    
	var rows = $table.bootstrapTable('getData');
	var employee = _.findWhere(rows, {id: idCategorie});
	employee.info =  employee.matricule+" "+employee.nomComplet;
	$scope.$apply(function () {
        $scope.pupulateForm(employee);
    });
}
</script>
