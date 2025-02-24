<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">

    .stepwizard-step p {
        margin-top: 10px;
    }
    .stepwizard-row {
        display: table-row;
    }
    .stepwizard {
        display: table;
        width: 100%;
        position: relative;
    }
    .stepwizard-step button[disabled] {
        opacity: 1 !important;
        filter: alpha(opacity=100) !important;
    }
    .stepwizard-row:before {
        top: 14px;
        bottom: 0;
        position: absolute;
        content: " ";
        width: 100%;
        height: 1px;
        background-color: #ccc;
        z-order: 0;
    }
    .stepwizard-step {
        display: table-cell;
        text-align: center;
        position: relative;
    }
    .btn-circle {
        width: 30px;
        height: 30px;
        text-align: center;
        padding: 6px 0;
        font-size: 12px;
        line-height: 1.428571429;
        border-radius: 15px;
    }
    h3{
        border-bottom: solid 1px #ccc;
    }

    /*.table-responsive {
        overflow-x: visible !important;
        overflow-y: visible !important;
    }*/
</style>

<div class="widget">
    <div class="widgetbox">
        <div class="headtitle">
            <div class="btn-group">
                <button data-toggle="dropdown" class="btn dropdown-toggle"> Action <span class="caret"></span></button>
                <ul class="dropdown-menu">
                    <li><a href="#">Listes</a></li>
                    <li class="divider"></li>
                    <li><a href="#" data-toggle="modal" data-target="#rhpModal">Nouveau</a></li>
                </ul>
            </div>
            <h4 id="widgetTitle" class="widgettitle">Liste du personnel</h4>
        </div>
        <div id="tableWidget" class="widgetcontent">
            <div class="table-responsive">
                <table id="table" class="table table-info table-striped"
                       data-toggle="table" 
                       data-click-to-select="true"
                       data-single-select="true"
                       data-url="${pageContext.request.contextPath}/personnels/listpersonneljson"
                       data-side-pagination="server" 
                       data-pagination="true"
                       data-page-list="[10, 20, 50, 100, 200]" 
                       data-search="true">
                    <thead>
                        <tr>
                            <th data-field="matricule" data-align="left">Matricule</th>
                            <th data-field="nomComplet" data-align="left">Nom</th>
                            <th data-field="sexe" data-align="left">Sexe</th>
                            <th data-field="dNaissance" data-align="center">N&eacute;(e) le</th>
                            <th data-field="lieuNaissance" data-align="left">A</th>
                            <th data-field="numeroCnps" data-align="center">N� CNPS</th>
                            <th data-field="telephone" data-align="center">T&eacute;l&eacute;phone</th>
                            <th data-field="situationMatri" data-align="center">Sit. Matri</th>
                            <th data-field="situationMed" data-align="center">Medaille</th>
                            <th data-field="id" data-formatter="optionFormatter">Options</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="modal main-popup fade" id="rhpModal" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" style="width:70%;">
        <div class="modal-content">
            <form id="formAjout" class="form-horizontal panel-wizard" role="form" novalidate="novalidate" ng-controller="formAjoutCtrl">
                <input type="text" name="statut" ng-show="false" value="true" ng-model="personnel.statut" />
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">Personnel</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="stepwizard">
                            <div class="stepwizard-row setup-panel">
                                <div class="stepwizard-step">
                                    <a href="#step-1" type="button" class="btn btn-primary btn-circle">1</a>
                                    <p>Informations personnelles</p>
                                </div>
                                <div class="stepwizard-step">
                                    <a href="#step-2" type="button" class="btn btn-default btn-circle" disabled="disabled">2</a>
                                    <p>Autres informations</p>
                                </div>
                                <div class="stepwizard-step">
                                    <a href="#step-3" type="button" class="btn btn-default btn-circle" disabled="disabled">3</a>
                                    <p>Contrat</p>
                                </div>
                            </div>
                        </div>

                        <div class="row setup-content" id="step-1">
                            <div class="col-xs-12">
                                <h3 class="text-right"> Informations personnelles</h3>
                                <br>
                                <div class="form-group">
                                    <div class="col-md-4">
                                        <label for="matricule">Matricule <span class="required">*</span></label> 
                                        <div class="pull-right verif-matricule"><img src="<%=request.getContextPath() %>/resources/front-end/images/loaders/loader27.gif" /></div>
                                        <input type="text" class="form-control input-default" id="matricule" name="matricule" placeholder="matricule" required="required" ng-model="personnel.matricule" />
                                    </div>
                                    <div class="col-md-4">
                                        <label for="matricule">Type employ� <span class="required">*</span></label> 
                                        <div>
                                            <select id=typeEmp name="typeEmp" class="form-control select2">
                                                <option>-- Choix du type employ� --</option>
                                                <option value="M"  selected="selected"> MENSUEL </option>
                                                <option value="j" > JOURNALIER </option>
                                                <option value="h" > HORAIRE </option>

                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <label for="matricule">Medaille</label> 
                                        <div>
                                            <select id="situationMedaille" name="situationMedaille" class="form-control select2">
                                                <option>-- Choix du type de medaille --</option>
                                                <option value="1"  > HONNEUR </option>
                                                <option value="2" > VERMEILLE </option>
                                                <option value="3" > ARGENT </option>
                                                <option value="4" > OR </option>
                                                <option value="0"   selected="selected">AUCUN</option>

                                            </select>
                                        </div>
                                    </div>	
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <label for="nom">Nom <span class="required">*</span></label> 
                                                <input type="text" class="form-control input-default" id="nom" name="nom" placeholder="Nom" required="required" ng-model="personnel.nom">
                                            </div>
                                            <div class="col-md-4 ">
                                                <label for="prenom">Pr&eacute;nom(s) <span class="required">*</span></label> 
                                                <input type="text" class="form-control input-default" id="prenom" name="prenom" placeholder="Prenom" required="required" ng-model="personnel.prenom">
                                            </div>
                                            <div class="col-md-4">
                                                <label for="matricule">Qualit� de l'emploi <span class="required">*</span></label> 
                                                <div>
                                                    <select id="situationEmploie" name="situationEmploie" class="form-control select2">
                                                        <option>-- Choix du type d'emploi --</option>
                                                        <option value="1"  > DIRECTEUR </option>
                                                        <option value="2" > CADRE SUPERIEUR </option>
                                                        <option value="3" > CADRE MOYEN </option>
                                                        <option value="4" > EMPLOYE QUALIFIE </option>
                                                        <option value="5"  >EMPLOYE NON QUALIFIE </option>
                                                        <option value="6" > OUVRIER QUALIFIE </option>
                                                        <option value="7" > OUVRIER NON QUALIFIE </option>
                                                        <option value="8" > AUTRE </option>														
                                                        <option value="0"   selected="selected">AUCUN</option>

                                                    </select>
                                                </div>
                                            </div>		
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <label for="datenaissance">N&eacute(e) le <span class="required">*</span></label> 
                                                <input type="text" class="form-control input-default" id="datenaissance" name="datenaissance" placeholder="N�(e) le" maxlength="10" required="required" ng-model="personnel.dNaissance">
                                            </div>
                                            <div class="col-md-4 ">
                                                <label for="lieunaissance">Lieu de Naissance <span class="required">*</span></label> 
                                                <input type="text" class="form-control input-default" id="lieunaissance" name="lieunaissance" placeholder="Lieu de naissance" required="required" ng-model="personnel.lieuNaissance">
                                            </div>
                                            <div class="col-md-4">
                                                <label for="sexe">Sexe <span class="required">*</span></label>
                                                <div>
                                                    <label id="sexeMasculin" class="radio-inline">
                                                        <input name="sexe" type="radio" value="Masculin"> Masculin
                                                    </label>
                                                    <label id="sexeFeminin" class="radio-inline">
                                                        <input name="sexe" type="radio" value="Feminin"> Feminin
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <label for="nationalite">Nationnalit&eacute; <span class="required">*</span></label> 
                                                <select id="nationalite" name="nationalite" class="form-control select2">
                                                    <option>-- Choix de la nationnalit� --</option>
                                                    <option value="1" selected="selected"> Ivoirienne </option>
                                                    <option value="2"> Togolaise </option>
                                                </select>
                                            </div>
                                            <div class="col-md-4">
                                                <label for="situationmatrimoniale">Situation matrimoniale <span class="required">*</span></label> 
                                                <select id="situationmatrimoniale" name="situationmatrimoniale" class="form-control select2">
                                                    <option>-- Choix de la situation --</option>
                                                    <option value="1"> MARIE </option>
                                                    <option value="2" selected="selected"> CELIBATAIRE </option>
                                                    <option value="3" > DIVORCE </option>
                                                    <option value="4" > VEUF(VE) </option>
                                                </select>
                                            </div>
                                            <div class="col-md-4">
                                                <label for="nombreenfant">Nombre d'enfant <span class="required">*</span></label> 
                                                <select id="nombreenfant" name="nombreenfant" class="form-control select2">
                                                    <option>-- Nombre d'enfant --</option>
                                                    <option value="0" selected="selected"> 0 </option>
                                                    <option value="1"> 1 </option>
                                                    <option value="2"> 2 </option>
                                                    <option value="3"> 3 </option>
                                                    <option value="4"> 4 </option>
                                                    <option value="5"> 5 </option>
                                                    <option value="6"> 6 </option>
                                                    <option value="7"> 7 </option>
                                                    <option value="8"> 8 </option>
                                                    <option value="9"> 9 </option>
                                                    <option value="10"> 10 </option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <button class="btn btn-success nextBtn btn-lg pull-right" type="button" > Suivant </button>

                            </div>
                        </div>
                        <div class="row setup-content" id="step-2">
                            <div class="col-xs-12">
                                <h3 class="text-right"> Autres Informations</h3>
                                <br>

                                <div class="form-group">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <label for="numerocnps">Num&eacute; CNPS</label> 
                                                <div class="pull-right verif-numerocnps"><img src="<%=request.getContextPath() %>/resources/front-end/images/loaders/loader27.gif" /></div>
                                                <input type="text" class="form-control input-default" id="numerocnps" name="numerocnps" placeholder="Numero CNPS" ng-model="personnel.numeroCnps">
                                            </div>
                                            <div class="col-md-4">
                                                <label for="residence">Residence</label> 
                                                <input type="text" class="form-control input-default" id="residence" name="residence" placeholder="Residence" ng-model="personnel.residence" >
                                            </div>
                                            <div class="col-md-4">
                                                <label for="residence">Telephone</label> 
                                                <input type="text" class="form-control input-default" id="telephone" name="telephone" placeholder="Telephone" ng-model="personnel.telephone" >
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-3">
                                                <label for="datearrivee">Date d'arriv&eacute;e <span class="required">*</span></label> 
                                                <input type="text" class="form-control input-default" id="datearrivee" name="datearrivee" placeholder="Date arriv�e" maxlength="10" required="required" ng-model="personnel.dArrivee">
                                            </div>
                                            	<div class="col-md-3">
														<label for="dateRetourcg">Date de retour du dernier cong�</label> 
														<input type="text" class="form-control input-default" id="dateRetourcg" name="dateRetourcg" placeholder="Date de retour du dernier cong�" required="required" maxlength="10"  ng-model="personnel.dRetourconge">
													</div>
                                            <div class="col-md-3">
                                                <label for="email"> Email </label> 
                                                <input type="email" class="form-control input-default" id="email" name="email" placeholder="Email" ng-model="personnel.email">
                                            </div>
                                            <div class="col-md-3">
                                                <label>CAREC</label>
                                                <div>
                                                    <label id="carecOui" class="radio-inline">
                                                        <input name="carec" type="radio" value="true"> Oui
                                                    </label>
                                                    <label id="carecNon" class="radio-inline">
                                                        <input name="carec" type="radio" value="false"> Non
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-3">
                                                <label for="banque">Banque</label> 
                                               <!--  <input type="text" class="form-control input-default" id="banque" name="banque" placeholder="Banque" ng-model="personnel.banque"> -->
                                              <select class="form-control input-small select2" id="idBanque" name="idBanque" ng-model="personnel.banquek.id" ng-init="personnel.banquek.id=banque">
                                    <c:forEach items="${listeBanques}" var="banques">
                                        <option value="${banques.id}">${banques.sigle} (${banques.codbanq})</option>
                                    </c:forEach>
                                </select>
                                            
                                            </div>
                                            <div class="col-md-3">
                                                <label for="numerocompte">Numero de guichet</label> 
                                                <input type="text" class="form-control input-default" id="numeroguichet" name="numeroguichet" maxlength="5" placeholder="Numero de Compte" ng-model="personnel.numeroGuichet">
                                            </div>
                                             <div class="col-md-3">
                                                <label for="numerocompte">Numero de compte</label> 
                                                <input type="text" class="form-control input-default" id="numerocompte" name="numerocompte" placeholder="Numero de Compte" maxlength="12" ng-model="personnel.numeroCompte">
                                            </div>
                                            <div class="col-md-3">
                                                <label for="rib">RIB</label> 
                                                <input type="text" class="form-control input-default" id="rib" name="rib" placeholder="RIB" maxlength="2" ng-model="personnel.rib">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <label for="modepaiement">Mode paiement <span class="required">*</span></label> 
                                                <select id="modepaiement" name="modepaiement" class="form-control select2">
                                                    <option>-- Mode de paiement --</option>
                                                    <option value="Virement" selected="selected"> Virement </option>
                                                    <option value="Ch�que"> Ch�que </option>
                                                    <option value="Esp�ce"> Espece </option>
                                                </select>
                                            </div>
                                            <div class="col-md-4">
                                                <label for="typeService">Division <span class="required">*</span></label> 
                                                <select id="typeService" name="typeService" class="form-control select2"></select>
                                            </div>
                                            <div class="col-md-4">
                                                <label id="choix">Libell&eacute; division <span class="required">*</span></label> 
                                                <select id="service" name="service" class="form-control select2"></select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <button class="btn btn-success nextBtn btn-lg pull-right" type="button" > Suivant </button>
                            </div>
                        </div>
                        <div class="row setup-content sectionContrat" id="step-3">
                            <div class="col-xs-12">
                                <div class="col-md-12">
                                    <h3 class="text-right"> Contrat </h3>
                                    <br>
                                    <div class="form-group">
                                        <div class="col-md-12">
                                            <div class="row">
                                                <div class="col-md-4 ">
                                                    <label for="fonction">Emploi <span class="required">*</span></label> 
                                                    <select id="fonction" name="fonction" class="form-control select2">

                                                    </select>
                                                </div>
                                                <div class="col-md-4 ">
                                                    <label for="typeContrat">Type contrat <span class="required">*</span></label> 
                                                    <select id="typeContrat" name="typecontrat" class="form-control select2">

                                                    </select>
                                                </div>
                                                <div class="col-md-4 ">
                                                    <label for="categorie">Cat&eacute;gorie <span class="required">*</span></label> 
                                                    <select id="categorie" name="categorie" class="form-control select2">

                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-12">
                                            <div class="row">
                                            <div class="col-md-4 ">
                                                        <label for="indemnitelogement">Indemnit&eacute; de logement<span class="required">*</span></label> 
                                                        <input type="text" class="form-control input-default" id="indemnitelogement" name="indemnitelogement" placeholder="Indemnit� de logement" required="required" ng-model="contrat.indemniteLogmt">
                                                </div> 
                                                <div class="col-md-4">
                                                    <label for="datedebut">Date d&eacute;but du contrat <span class="required">*</span></label> 
                                                    <input type="text" class="form-control input-default" id="datedebut" name="datedebut" placeholder="Date debut contrat" maxlength="10" required="required" ng-model="contrat.dDebut">
                                                </div>
                                                <div class="col-md-4">
                                                    <label for="datefin">Date fin du contrat</label> 
                                                    <input type="text" class="form-control input-default" id="datefin" name="datefin" placeholder="Date fin contrat" maxlength="10" ng-model="contrat.dFin">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-12">
                                            <div class="row">
                                                <div class="col-md-4 ">
                                                    <label for="salairenet">Net � payer <span class="required">*</span></label> 
                                                    <input type="number" class="form-control input-default" id="salairenet" name="salairenet" placeholder="Net � payer" required="required" ng-model="contrat.netPayer">
                                                </div>
                                                
                                                <div class="col-md-4 ">
                                                    <label for="ancienneteInitial">Anciennet&eacute; initiale <span class="required">*</span></label> 
                                                    <select id="ancienneteInitial" name="ancienneteinitial" class="form-control select2" ng-model="contrat.ancienneteInitial">
                                                        <option>-- Anciennet&eacute; initiale --</option>
                                                        <option value="0" selected="selected"> 0 </option>
                                                        <option value="1"> 1 </option>
                                                        <option value="2"> 2 </option>
                                                        <option value="3"> 3 </option>
                                                        <option value="4"> 4 </option>
                                                        <option value="5"> 5 </option>
                                                        <option value="6"> 6 </option>
                                                        <option value="7"> 7 </option>
                                                        <option value="8"> 8 </option>
                                                        <option value="9"> 9 </option>
                                                        <option value="10"> 10 </option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <button class="btn btn-success btn-lg pull-right" type="submit"> Terminer </button>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <span></span>&nbsp;
                    <input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="personnel.id">
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal main-popup fade" id="listContratModal" ng-controller="listContratCtrl" role="dialog" aria-labelledby="listContratModalLabel" data-backdrop="static">
    <div class="modal-dialog" style="width:60%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="listContratModalLabel">Liste des contrats</h4>
            </div>
            <div class="modal-body">
                <table>
                    <tbody>
                        <tr>
                            <th style="width:150px;">Matricule</th>
                            <td style="width:300px;">{{personnel.matricule}}</td>
                            <th style="width:80px;">N&deg; CNPS</th>
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
                    </tbody>
                </table>
                <p>&nbsp;</p>
                <div class="text-right"><button type="button" class="btn btn-primary" title="Nouveau contrat" id="btnAddContrat"><span class="glyphicon glyphicon-plus"></span> Contrat</button></div>
                <form id="formContrat" class="form-contrat">
                    <br>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-4">
                                <label for="fonctionPop">Emploi</label> 
                                <select id="fonctionPop" name="idFonction" class="form-control select2" ng-model="personnel.fonction.id">

                                </select>
                            </div>
                            <div class="col-md-4">
                                <label for="typecontratPop">Type contrat</label> 
                                <select id="typecontratPop" name="idTypeContrat" class="form-control select2" ng-model="contrat.typeContrat.id">

                                </select>
                            </div>
                            <div class="col-md-4">
                                <label for="categoriePop">Cat&eacute;gorie</label> 
                                <select id="categoriePop" name="idCategorie" class="form-control select2" ng-model="contrat.categorie.id">

                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-4">
                                <label for="datedebutPop">Date d&eacute;but du contrat</label> 
                                <input type="text" class="form-control input-default" id="datedebutPop" name="dateDebut" placeholder="Date debut contrat" maxlength="10" required="required" ng-model="contrat.dDebut">
                            </div>
                            <div class="col-md-4">
                                <label for="datefinPop">Date fin du contrat</label> 
                                <input type="text" class="form-control input-default" id="datefinPop" name="dateFin" placeholder="Date fin contrat" maxlength="10" ng-model="contrat.dFin">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-4 ">
                                <label for="salairenetPop">Net � payer</label> 
                                <input type="text" class="form-control input-small" id="salairenetPop" name="netAPayer" placeholder="0" required="required" ng-model="contrat.netAPayer">
                            </div>
                            <div class="col-md-4 ">
                                <label for="indemnitelogementPop">Indemnit&eacute; de logement</label> 
                                <input type="text" class="form-control input-small" id="indemnitelogementPop" name="indemniteLogement" placeholder="0" required="required" ng-model="contrat.indemniteLogement">
                            </div>
                            <div class="col-md-4 ">
                                <label for="ancienneteinitialPop">Anciennet&eacute; initiale</label>
                                <input type="number" class="form-control input-small" id="ancienneteinitialPop" name="ancienete" placeholder="0" value="0" ng-model="contrat.ancienneteInitial">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div id="actionContrat" class="col-md-12 text-right">
                                <span></span>&nbsp;
                                <input type="text"class="hidden" ng-hide="true" name="idPersonnel" ng-model="personnel.id"/>
                                <button type="button" id="btnCancelContrat" type="reset" class="btn btn-default">Annuler</button>
                                <button type="submit" class="btn btn-success">Valider</button>
                            </div>
                        </div>
                    </div>					
                </form>
                <p>&nbsp;</p>
                <table id="tableContrat" class="table table-info table-striped"
                       data-toggle="table" data-click-to-select="true"
                       data-single-select="true" data-sort-name="name"
                       data-sort-order="desc"
                       data-pagination="true"
                       data-page-list="[10, 20, 50, 100, 200]" 
                       data-search="false">
                    <thead>
                        <tr>
                            <th data-field="typeContrat" data-formatter="typeContratFormatter">Type de contrat</th>
                            <th data-field="fonction" data-formatter="fonctionFormatter">Fonction</th>
                            <th data-field="dDebut">Date d&eacute;but</th>
                            <th data-field="dFin">Date fin</th>
                            <th data-field="categorie" data-formatter="categorieFormatter" data-align="right">Salaire cat&eacute;goriel</th>
                            <th data-field="netPayer" data-align="right">Net &agrave; payer</th>
                            <th data-field="statut" data-formatter="statutFormatter">Statut</th>
                        </tr>
                    </thead>
                </table>
                <br>
            </div>
        </div>
    </div>
</div>

<div class="modal main-popup deleteModal  fade bs-delete-modal-static" role="dialog" data-backdrop="static">
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
                    <h4 ng-bind="personnel.info"></h4>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="personnel.id">
                    <span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
        </div>
        </form>
    </div>
</div>

<div class="modal main-popup fade" id="listEnfantModal" ng-controller="listEnfantCtrl" role="dialog" aria-labelledby="listEnfantModalLabel" data-backdrop="static">
    <div class="modal-dialog" style="width:60%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="listEnfantModalLabel">Liste des enfants</h4>
            </div>
            <div class="modal-body">
                <h3>Personnel</h3>
                <table>
                    <tbody>
                        <tr>
                            <th style="width:150px;">Matricule</th>
                            <td style="width:300px;">{{personnel.matricule}}</td>
                            <th style="width:80px;">N&deg; CNPS</th>
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
                    </tbody>
                </table>
                <form id="formEnfant" class="form-enfant">
                    <h3 style="margin-top: 30px;">Veuillez renseigner les informations de l'enfant</h3>
                    <br>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-4">
                                <label>Matricule</label> 
                                <input type="text" class="form-control input-default" ng-model="enfant.matricule" name="matricule" placeholder="Matricule" maxlength="10">
                            </div>
                            <div class="col-md-4">
                                <label>Nom et pr&eacute;noms</label> 
                                <input type="text" class="form-control input-default" ng-model="enfant.nom" name="nom" placeholder="Nom et pr�noms" maxlength="100">
                            </div>
                            <div class="col-md-4">
                                <label>Sexe</label>
                                <div>
                                    <label id="sexeEnfantMasculin" class="radio-inline">
                                        <input type="radio" ng-model="enfant.sexe" name="sexe" value="M"> Masculin
                                    </label>
                                    <label id="sexeEnfantFeminin" class="radio-inline">
                                        <input type="radio" ng-model="enfant.sexe" name="sexe" value="F"> Feminin
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-4">
                                <label>Date de naissance</label> 
                                <input type="text" class="form-control input-default" ng-model="enfant.dateNaissance" name="dateNaissance" id="dateNaissanceEnfant" placeholder="Date de naissance" maxlength="10">
                            </div>
                            <div class="col-md-8">
                                <label>Lieu de naissance</label> 
                                <input type="text" class="form-control input-default" ng-model="enfant.lieuNaissance" name="lieuNaissance" placeholder="Lieu de naissance" maxlength="100">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div id="actionEnfant" class="col-md-12 text-right">
                                <span></span>&nbsp;
                                <input type="text"class="hidden" ng-hide="true" name="idPersonnel" ng-model="personnel.id"/>
                                <button type="button" id="btnCancelEnfant" class="btn btn-default">Annuler</button>
                                <button type="submit" class="btn btn-success">Valider</button>
                            </div>
                        </div>
                    </div>					
                </form>
                <p>&nbsp;</p>
                <div id="toolbar">
                    <div class="form-inline">
                        <button type="button" class="btn btn-primary" title="Nouvel enfant" id="btnAddEnfant"><span class="glyphicon glyphicon-plus"></span> Enfant</button>
                    </div>
                </div>
                <table id="tableEnfant" class="table table-info table-striped"
                       data-toggle="table"
                       data-toolbar="#toolbar"
                       data-single-select="true" data-sort-name="nom"
                       data-sort-order="desc"
                       data-pagination="false"
                       data-search="true">
                    <thead>
                        <tr>
                            <th data-field="matricule">Matricule</th>
                            <th data-field="nom">Nom</th>
                            <th data-field="dNaissance">Date de naissance</th>
                            <th data-field="lieuNaissance">Lieu de naissance</th>
                            <th data-field="sexe" data-align="center">Sexe</th>
                        </tr>
                    </thead>
                </table>
                <br>
            </div>
        </div>
    </div>
</div>

<div class="modal main-popup fade" id="listConjointModal" ng-controller="listConjointCtrl" role="dialog" aria-labelledby="listConjointModalLabel" data-backdrop="static">
    <div class="modal-dialog" style="width:60%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="listConjointModalLabel">Liste des conjoint(e)s</h4>
            </div>
            <div class="modal-body">
                <h3>Personnel</h3>
                <table>
                    <tbody>
                        <tr>
                            <th style="width:150px;">Matricule</th>
                            <td style="width:300px;">{{personnel.matricule}}</td>
                            <th style="width:80px;">N&deg; CNPS</th>
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
                    </tbody>
                </table>
                <form id="formConjoint" class="form-conjoint">
                    <h3 style="margin-top: 30px;">Conjoint(e)</h3>
                    <br>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-4">
                                <label>Matricule</label> 
                                <input type="text" class="form-control input-default" ng-model="conjoint.matricule" name="matricule" placeholder="Matricule" maxlength="10">
                            </div>
                            <div class="col-md-4">
                                <label>Nom et pr&eacute;noms</label> 
                                <input type="text" class="form-control input-default" ng-model="conjoint.nom" name="nom" placeholder="Nom et pr�noms" maxlength="100">
                            </div>
                            <div class="col-md-4">
                                <label>Sexe</label>
                                <div>
                                    <label id="sexeConjointMasculin" class="radio-inline">
                                        <input type="radio" ng-model="conjoint.sexe" name="sexe" value="M" checked="checked"> Masculin
                                    </label>
                                    <label id="sexeConjointFeminin" class="radio-inline">
                                        <input type="radio" ng-model="conjoint.sexe" name="sexe" value="F"> Feminin
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-4">
                                <label>Date de naissance</label> 
                                <input type="text" class="form-control input-default datePicker" ng-model="conjoint.dateNaissance" name="dateNaissance" id="dateNaissanceConjoint" placeholder="Date de naissance" maxlength="10">
                            </div>
                            <div class="col-md-4">
                                <label>Lieu de naissance</label> 
                                <input type="text" class="form-control input-default" ng-model="conjoint.lieuNaissance" name="lieuNaissance" placeholder="Lieu de naissance" maxlength="100">
                            </div>
                            <div class="col-md-4">
                                <label>Lieu de r&eacute;sidence</label> 
                                <input type="text" class="form-control input-default" ng-model="conjoint.lieuResidence" name="lieuResidence" placeholder="Lieu de r�sidence" maxlength="100">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-4">
                                <label>T&eacute;l&eacute;phone</label> 
                                <input type="text" class="form-control input-default" ng-model="conjoint.telephone" name="telephone" placeholder="T�l�phone" maxlength="32">
                            </div>
                            <div class="col-md-8">
                                <label>E-mail</label> 
                                <input type="email" class="form-control input-default" ng-model="conjoint.email" name="email" placeholder="Email" maxlength="100">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div id="actionConjoint" class="col-md-12 text-right">
                                <span></span>&nbsp;
                                <input type="text"class="hidden" ng-hide="true" name="idPersonnel" ng-model="personnel.id"/>
                                <button type="button" id="btnCancelConjoint" class="btn btn-default">Annuler</button>
                                <button type="submit" class="btn btn-success">Valider</button>
                            </div>
                        </div>
                    </div>					
                </form>
                <p>&nbsp;</p>
                <div id="toolbarConjoint">
                    <div class="form-inline">
                        <button type="button" class="btn btn-primary" title="Ajouter un(e) conjoint(e)" id="btnAddConjoint"><span class="glyphicon glyphicon-plus"></span> Conjoint(e)</button>
                    </div>
                </div>
                <table id="tableConjoint" class="table table-info table-striped"
                       data-toggle="table"
                       data-toolbar="#toolbarConjoint"
                       data-single-select="true" data-sort-name="nom"
                       data-sort-order="desc"
                       data-pagination="false"
                       data-search="true">
                    <thead>
                        <tr>
                            <th data-field="matricule">Matricule</th>
                            <th data-field="nom">Nom</th>
                            <th data-field="dNaissance">Date de naissance</th>
                            <th data-field="lieuNaissance">Lieu de naissance</th>
                            <th data-field="sexe" data-align="center">Sexe</th>
                            <th data-field="telephone">T&eacute;l&eacute;phone</th>
                            <th data-field="email">E-mail</th>
                        </tr>
                    </thead>
                </table>
                <br>
            </div>
        </div>
    </div>
</div>

<div class="modal main-popup fade" id="listPersonnePrevenirModal" ng-controller="listPersonnePrevenirCtrl" role="dialog" aria-labelledby="listPersonnePrevenirModalLabel" data-backdrop="static">
    <div class="modal-dialog" style="width:60%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="listPersonnePrevenirModalLabel">Liste des personnes &agrave; pr&eacute;venir</h4>
            </div>
            <div class="modal-body">
                <h3>Personnel</h3>
                <table>
                    <tbody>
                        <tr>
                            <th style="width:150px;">Matricule</th>
                            <td style="width:300px;">{{personnel.matricule}}</td>
                            <th style="width:80px;">N&deg; CNPS</th>
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
                    </tbody>
                </table>
                <form id="formPersonnePrevenir" class="form-personne-prevenir">
                    <h3 style="margin-top: 30px;">Personne &agrave; pr&eacute;venir</h3>
                    <br>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-4">
                                <label>Filiation</label> 
                                <select class="form-control" name="filiation">
                                    <option value="Conjoint(e)">Conjoint(e)</option>
                                    <option value="P�re/M�re">P&egrave;re/M&egrave;re</option>
                                    <option value="Tuteur/Tutrice">Tuteur/Tutrice</option>
                                    <option value="Autre">Autre</option>
                                    <!--<option value=""></option>
                                    <option value=""></option>-->
                                </select>
                            </div>
                            <div class="col-md-4">
                                <label>Nom et pr&eacute;noms</label> 
                                <input type="text" class="form-control input-default" ng-model="personnePrevenir.nom" name="nom" placeholder="Nom et pr�noms" maxlength="100">
                            </div>
                            <div class="col-md-4">
                                <label>Sexe</label>
                                <div>
                                    <label id="sexePersonnePrevenirMasculin" class="radio-inline">
                                        <input type="radio" ng-model="personnePrevenir.sexe" name="sexe" value="M" checked="checked"> Masculin
                                    </label>
                                    <label id="sexePersonnePrevenirFeminin" class="radio-inline">
                                        <input type="radio" ng-model="personnePrevenir.sexe" name="sexe" value="F"> Feminin
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-4">
                                <label>T&eacute;l&eacute;phone</label> 
                                <input type="text" class="form-control input-default" ng-model="personnePrevenir.telephone" name="telephone" placeholder="T�l�phone" maxlength="32">
                            </div>
                            <div class="col-md-8">
                                <label>E-mail</label> 
                                <input type="email" class="form-control input-default" ng-model="personnePrevenir.email" name="email" placeholder="Email" maxlength="100">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div id="actionPersonnePrevenir" class="col-md-12 text-right">
                                <span></span>&nbsp;
                                <input type="text"class="hidden" ng-hide="true" name="idPersonnel" ng-model="personnel.id"/>
                                <button type="button" id="btnCancelPersonnePrevenir" class="btn btn-default">Annuler</button>
                                <button type="submit" class="btn btn-success">Valider</button>
                            </div>
                        </div>
                    </div>					
                </form>
                <p>&nbsp;</p>
                <div id="toolbarPersonnePrevenir">
                    <div class="form-inline">
                        <button type="button" class="btn btn-primary" title="Ajouter un(e) conjoint(e)" id="btnAddPersonnePrevenir"><span class="glyphicon glyphicon-plus"></span> Personne &agrave; pr&eacute;venir</button>
                    </div>
                </div>
                <table id="tablePersonnePrevenir" class="table table-info table-striped"
                       data-toggle="table"
                       data-toolbar="#toolbarPersonnePrevenir"
                       data-single-select="true" data-sort-name="nom"
                       data-sort-order="desc"
                       data-pagination="false"
                       data-search="true">
                    <thead>
                        <tr>
                            <th data-field="filiation">Filiation</th>
                            <th data-field="sexe" data-align="center">Sexe</th>
                            <th data-field="nom">Nom</th>
                            <th data-field="telephone">T&eacute;l&eacute;phone</th>
                            <th data-field="email">E-mail</th>
                        </tr>
                    </thead>
                </table>
                <br>
            </div>
        </div>
    </div>
</div>

<div class="modal main-popup fade" id="listAffectationModal" ng-controller="listAffectationCtrl" role="dialog" aria-labelledby="listAffectationModalLabel" data-backdrop="static">
    <div class="modal-dialog" style="width:60%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="listAffectationModalLabel">Liste des affectations</h4>
            </div>
            <div class="modal-body">
                <h3>Personnel</h3>
                <table>
                    <tbody>
                        <tr>
                            <th style="width:150px;">Matricule</th>
                            <td style="width:300px;">{{personnel.matricule}}</td>
                            <th style="width:80px;">N&deg; CNPS</th>
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
                    </tbody>
                </table>
                <form id="formAffectation" class="form-affectation">
                    <h3 style="margin-top: 30px;">Affectation</h3>
                    <br>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-4">
                                <label>Poste</label>
                                <select class="form-control input-small" id="idPoste" name="idPoste" ng-model="posteId" ng-init="posteId=poste">
                                    <c:forEach items="${listePostes}" var="poste">
                                        <option value="${poste.id}">${poste.libelle}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <label>Date debut</label> 
                                <input type="text" class="form-control input-small datePicker" name="dateDebut" placeholder="Date debut affectation" maxlength="10" required="required" ng-model="affectation.dDebut">
                            </div>
                            <div class="col-md-4">
                                <label>Date fin</label>
                                <input type="text" class="form-control input-small datePicker" name="dateFin" placeholder="Date fin affectation" maxlength="10" required="required" ng-model="affectation.dFin">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-12">
                                <label>Observation</label>
                                <textarea class="form-control" name="observation" placeholder="Observation" ng-model="affectation.observation">
                                    
                                </textarea>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div id="actionAffectation" class="col-md-12 text-right">
                                <span></span>&nbsp;
                                <input type="text"class="hidden" ng-hide="true" name="idPersonnel" ng-model="personnel.id"/>
                                <button type="button" id="btnCancelAffectation" class="btn btn-default">Annuler</button>
                                <button type="submit" class="btn btn-success">Valider</button>
                            </div>
                        </div>
                    </div>					
                </form>
                <p>&nbsp;</p>
                <div id="toolbarAffectation">
                    <div class="form-inline">
                        <button type="button" class="btn btn-primary" title="Nouvelle affectation" id="btnAddAffectation"><span class="glyphicon glyphicon-plus"></span> Nouvelle affectation</button>
                    </div>
                </div>
                <table id="tableAffectation" class="table table-info table-striped"
                       data-toggle="table"
                       data-toolbar="#toolbarAffectation"
                       data-single-select="true" data-sort-name="nom"
                       data-sort-order="desc"
                       data-pagination="false"
                       data-search="true">
                    <thead>
                        <tr>
                            <th data-field="poste" data-formatter="posteFormatter">Poste</th>
                            <th data-field="dDebut">Date debut</th>
                            <th data-field="dFin">Date fin</th>
                            <th data-field="observation">Observation</th>
                        </tr>
                    </thead>
                </table>
                <br>
            </div>
        </div>
    </div>
</div>

<div class="modal main-popup fade" id="listPromotionModal" ng-controller="listPromotionCtrl" role="dialog" aria-labelledby="listPromotionModalLabel" data-backdrop="static">
    <div class="modal-dialog" style="width:60%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="listPromotionModalLabel">Liste des promotions</h4>
            </div>
            <div class="modal-body">
                <h3>Personnel</h3>
                <table>
                    <tbody>
                        <tr>
                            <th style="width:150px;">Matricule</th>
                            <td style="width:300px;">{{personnel.matricule}}</td>
                            <th style="width:80px;">N&deg; CNPS</th>
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
                    </tbody>
                </table>
                <form id="formPromotion" class="form-promotion">
                    <h3 style="margin-top: 30px;">Promotion</h3>
                    <br>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label>Promotion</label>
                                <select class="form-control input-small" id="idPromotion" name="idPromotion" ng-model="promotionPersonnel.promotion.id" ng-init="promotionPersonnel.promotion.id=promotion">
                                    <c:forEach items="${listePromotions}" var="promotion">
                                        <option value="${promotion.id}">${promotion.libelle}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label>Date promotion</label> 
                                <input type="text" class="form-control input-small datePicker" name="datePromotion" placeholder="Date promotion" maxlength="10" required="required" ng-model="promotionPersonnel.dPromotion">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-12">
                                <label>Commentaire</label>
                                <textarea class="form-control" name="commentaire" placeholder="Commentaire" ng-model="promotionPersonnel.commentaire">
                                    
                                </textarea>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div id="actionPromotion" class="col-md-12 text-right">
                                <span></span>&nbsp;
                                <input type="text"class="hidden" ng-hide="true" name="idPersonnel" ng-model="personnel.id"/>
                                <button type="button" id="btnCancelPromotion" class="btn btn-default">Annuler</button>
                                <button type="submit" class="btn btn-success">Valider</button>
                            </div>
                        </div>
                    </div>					
                </form>
                <p>&nbsp;</p>
                <div id="toolbarPromotion">
                    <div class="form-inline">
                        <button type="button" class="btn btn-primary" title="Nouvelle affectation" id="btnAddPromotion"><span class="glyphicon glyphicon-plus"></span> Nouvelle promotion</button>
                    </div>
                </div>
                <table id="tablePromotion" class="table table-info table-striped"
                       data-toggle="table"
                       data-toolbar="#toolbarPromotion"
                       data-single-select="true" data-sort-name="nom"
                       data-sort-order="desc"
                       data-pagination="false"
                       data-search="true">
                    <thead>
                        <tr>
                            <th data-field="promotion" data-formatter="promotionFormatter">Promotion</th>
                            <th data-field="promotion" data-formatter="descriptionFormatter">Description</th>
                            <th data-field="dPromotion">Date promotion</th>
                            <th data-field="commentaire">Commentaire</th>
                        </tr>
                    </thead>
                </table>
                <br>
            </div>
        </div>
    </div>
</div>

<div class="modal main-popup fade" id="listSanctionModal" ng-controller="listSanctionCtrl" role="dialog" aria-labelledby="listSanctionModalLabel" data-backdrop="static">
    <div class="modal-dialog" style="width:60%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="listSanctionModalLabel">Liste des sanctions</h4>
            </div>
            <div class="modal-body">
                <h3>Personnel</h3>
                <table>
                    <tbody>
                        <tr>
                            <th style="width:150px;">Matricule</th>
                            <td style="width:300px;">{{personnel.matricule}}</td>
                            <th style="width:80px;">N&deg; CNPS</th>
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
                    </tbody>
                </table>
                <form id="formSanction" class="form-sanction">
                    <h3 style="margin-top: 30px;">Sanction</h3>
                    <br>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-4">
                                <label>Sanction</label>
                                <select class="form-control input-small" id="idSanction" name="idSanction" ng-model="sanctionPersonnel.sanction.id" ng-init="sanctionPersonnel.sanction.id=sanction">
                                    <c:forEach items="${listeSanctions}" var="sanction">
                                        <option value="${sanction.id}">${sanction.faute} (${sanction.typeSanction.libelle})</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <label>Date debut</label> 
                                <input type="text" class="form-control input-small datePicker" name="dateDebut" placeholder="Date debut sanction" maxlength="10" required="required" ng-model="sanctionPersonnel.dDebut">
                            </div>
                            <div class="col-md-4">
                                <label>Date fin</label>
                                <input type="text" class="form-control input-small datePicker" name="dateFin" placeholder="Date fin sanction" maxlength="10" required="required" ng-model="sanctionPersonnel.dFin">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-12">
                                <label>Observation</label>
                                <textarea class="form-control" name="observation" placeholder="Observation" ng-model="sanctionPersonnel.observation">
                                    
                                </textarea>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div id="actionSanction" class="col-md-12 text-right">
                                <span></span>&nbsp;
                                <input type="text"class="hidden" ng-hide="true" name="idPersonnel" ng-model="personnel.id"/>
                                <button type="button" id="btnCancelSanction" class="btn btn-default">Annuler</button>
                                <button type="submit" class="btn btn-success">Valider</button>
                            </div>
                        </div>
                    </div>					
                </form>
                <p>&nbsp;</p>
                <div id="toolbarSanction">
                    <div class="form-inline">
                        <button type="button" class="btn btn-primary" title="Nouvelle sanction" id="btnAddSanction"><span class="glyphicon glyphicon-plus"></span> Nouvelle sanction</button>
                    </div>
                </div>
                <table id="tableSanction" class="table table-info table-striped"
                       data-toggle="table"
                       data-toolbar="#toolbarSanction"
                       data-single-select="true" data-sort-name="nom"
                       data-sort-order="desc"
                       data-pagination="false"
                       data-search="true">
                    <thead>
                        <tr>
                            <th data-field="sanction" data-formatter="sanctionFormatter">Faute</th>
                            <th data-field="sanction" data-formatter="commentaireFormatter">Commentaire</th>
                            <th data-field="dDebut">Date debut</th>
                            <th data-field="dFin">Date fin</th>
                            <th data-field="observation">Observation</th>
                        </tr>
                    </thead>
                </table>
                <br>
            </div>
        </div>
    </div>
</div>

<div class="modal main-popup fade" id="listMouvementCongeModal" ng-controller="listMouvementCongeCtrl" role="dialog" aria-labelledby="listMouvementCongeModalLabel" data-backdrop="static">
    <div class="modal-dialog" style="width:60%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="listMouvementCongeModalLabel">Liste des mouvements cong&eacute;</h4>
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
                <form id="formMouvementConge" class="form-mouvement-conge">
                    <h3 style="margin-top: 30px;">Mouvement cong&eacute;</h3>
                    <br>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-4">
                                <label>Date d&eacute;part</label> 
                                <input type="text" class="form-control input-small datePicker" name="dateDepart" placeholder="Date depart" maxlength="10" required="required" ng-model="mouvementConge.dDebut">
                            </div>
                            <div class="col-md-4">
                                <label>Date retour</label>
                                <input type="text" class="form-control input-small datePicker" name="dateRetour" placeholder="Date retour" maxlength="10" required="required" ng-model="mouvementConge.dFin">
                            </div>
                          <!--   <div class="col-md-4">
                                <label>Date retour</label>
                                <input type="text" class="form-control " name="montantVerse" placeholder="Date retour" maxlength="10" required="required" ng-model="mouvementConge.montantVerse">
                            </div> -->
                           <div class="col-md-4">
                                <label>Montant vers&eacute;</label>
                                <input type="text" class="form-control" name="montantVerse" placeholder="Montant verse" maxlength="11" required="required" ng-model="mouvementConge.mtnVerse">
                            </div> 
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div id="actionMouvementConge" class="col-md-12 text-right">
                                <span></span>&nbsp;
                                <input type="text"class="hidden" ng-hide="true" name="idPersonnel" ng-model="personnel.id"/>
                                <button type="button" id="btnCancelMouvementConge" class="btn btn-default">Annuler</button>
                                <button type="submit" class="btn btn-success">Valider</button>
                            </div>
                        </div>
                    </div>					
                </form>
                <p>&nbsp;</p>
                <div id="toolbarMouvementConge" ng-show="mouvementConge">
                    <div class="form-inline">
                        <button type="button" class="btn btn-primary" title="Nouveau cong�" id="btnAddMouvementConge"><span class="glyphicon glyphicon-plus"></span> Nouveau cong&eacute;</button>
                    </div>
                </div>
                <table id="tableMouvementConge" class="table table-info table-striped"
                       data-toggle="table"
                       data-toolbar="#toolbarMouvementConge"
                       data-single-select="true"
                       data-sort-order="desc"
                       data-pagination="false"
                       data-search="true">
                    <thead>
                        <tr>
                            <th data-field="dDepart">Date depart</th>
                            <th data-field="dRetour">Date retour</th>
                            <th data-field="nombreJourPris">Jours pris</th>
                            <th data-field="mtnVerse" data-align="right">Montant vers&eacute;</th>
                        </tr>
                    </thead>
                </table>
                <br>
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
                <h4 class="modal-title" id="listAbsenceModalLabel">Liste des absences</h4>
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
                    <h3 style="margin-top: 30px;">Absence</h3>
                    <br>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-4">
                                <label>Motif</label> 
                                <select class="form-control input-small" id="idAbsence" name="idAbsence" ng-model="absencePersonnel.absence.id" ng-init="absencePersonnel.absence.id=absence">
                                    <c:forEach items="${listeAbsences}" var="absence">
                                        <option value="${absence.id}">${absence.faute}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <label>Date d&eacute;part</label> 
                                <input type="text" class="form-control input-small datetimePicker" name="dateDebut" placeholder="Date depart" maxlength="10" required="required" ng-model="mouvementConge.dDebut">
                            </div>
                            <div class="col-md-4">
                                <label>Date retour</label>
                                <input type="text" class="form-control input-small datetimePicker" name="dateFin" placeholder="Date retour" maxlength="10" required="required" ng-model="mouvementConge.dRet">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-3">
                                <label for="statut">Justifi&eacute; ?</label> 
                                <select class="form-control input-small" id="statut" name="statut" ng-model="absencePersonnel.statut" ng-init="absencePersonnel.statut='true'">
                                    <option value="false">Non Justifi&eacute;</option>
                                    <option value="true">Justifi&eacute;</option>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label for="sanctionsalaire">Incidence </label> 
                                <select class="form-control input-small" id="sanctionsalaire" name="sanctionsalaire" ng-model="absencePersonnel.sanctionsalaire" ><!-- //ng-init="absencePersonnel.sanctionsalaire='false'" -->
                                    <option value="1">Salaire</option>
                                    <option value="2">Cong�</option>
                                    <option value="3">Aucun</option>
                                </select>
                            </div>
                            
                            
                            <div class="col-md-3">
                                <label for="sanctionsalaire">Nombre jours </label> 
                                 <input type="text" id="joursabsence" class="form-control input-default" ng-model="absencePersonnel.joursabsence" name="joursabsence" placeholder="30" maxlength="4">
                            </div>
                            
                            <div class="col-md-3">
                                <label for="sanctionsalaire">Nombre d'heure</label> 
                                <input type="text" id= "heursabsence" class="form-control input-default" ng-model="absencePersonnel.heursabsence" name="heursabsence" placeholder="173.33" maxlength="6">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-12">
                                <label>Observations</label>
                                <textarea class="form-control" name="observation" ng-model="absencePersonnel.observation" placeholder="Observation"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div id="actionAbsence" class="col-md-12 text-right">
                                <span></span>&nbsp;
                                <input type="text"class="hidden" ng-hide="true" name="idPersonnel" ng-model="personnel.id"/>
                                <button type="button" id="btnCancelAbsence" class="btn btn-default">Annuler</button>
                                <button type="submit" class="btn btn-success">Valider</button>
                            </div>
                        </div>
                    </div>					
                </form>
                <p>&nbsp;</p>
                <div id="toolbarAbsence">
                    <div class="form-inline">
                        <button type="button" class="btn btn-primary" title="Nouvelle absence" id="btnAddAbsence"><span class="glyphicon glyphicon-plus"></span> Nouvelle absence</button>
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
                    <thead>
                        <tr>
                            <th data-field="dDebut">Date depart</th>
                            <th data-field="dRet">Date retour</th>
                            <th data-field="absences" data-formatter="motifFormatter">Motif</th>
                            <th data-field="statut" data-formatter="statutAbsenceFormatter" data-align="center">Statut</th>
                            <th data-field="absences" data-formatter="commentaireAbsenceFormatter">Commentaire</th>
                            <th data-field="observation">Observation</th>
                            <th data-field="id" data-formatter="optionAbsenceFormatter" data-align="center"></th>
                        </tr>
                    </thead>
                </table>
                <br>
            </div>
        </div>
    </div>
</div>

<div class="modal modal-options fade" id="optionsModal" ng-controller="optionsModalCtrl" role="dialog" aria-labelledby="optionModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="optionModalLabel">Options personnel / {{personnel.nom}} {{personnel.prenom}} - {{personnel.telephone}}</h4>
            </div>
            <div class="modal-body">
                <div class="bs-glyphicons">
                    <ul class="bs-glyphicons-list">
                        <li>
                            <a onclick="javascript:listMouvementConge({{personnel.id}})" data-toggle="modal" data-target="#listMouvementCongeModal" href="#" title="Stock cong� [NOM : {{personnel.nom}} {{personnel.prenom}}]">
                                <span class="f-logo iconfa-list" aria-hidden="true"></span>
                                <span class="glyphicon-class">Cong&eacute;s</span>
                            </a>
                        </li>
                        <li>
                            <a onclick="listContrat({{personnel.id}})" data-toggle="modal" data-target="#listContratModal" href="#" data-action="lister" title="Contrat du personnel {{personnel.nom}} {{personnel.prenom}}">
                                <span class="f-logo iconfa-certificate" aria-hidden="true"></span>
                                <span class="glyphicon-class">Contrats</span>
                            </a>
                        </li>
                        <li>
                            <a onclick="listPersonnePrevenir({{personnel.id}})" data-toggle="modal" data-target="#listPersonnePrevenirModal" href="#" title="Personne � prevenir">
                                <span class="f-logo iconfa-phone" aria-hidden="true"></span>
                                <span class="glyphicon-class">Personnes � contacter</span>
                            </a>
                        </li>
                        <li>
                            <a onclick="listEnfant({{personnel.id}})" data-toggle="modal" data-target="#listEnfantModal" href="#" title="Enfant">
                                <span class="f-logo iconfa-group" aria-hidden="true"></span>
                                <span class="glyphicon-class">Enfants</span>
                            </a>
                        </li>
                        <li>
                            <a onclick="listConjoint({{personnel.id}})" data-toggle="modal" data-target="#listConjointModal" href="#" title="Conjoint">
                                <span class="f-logo iconfa-heart" aria-hidden="true"></span>
                                <span class="glyphicon-class">Conjoint(e)</span>
                            </a>
                        </li>
                        <li>
                            <a onclick="listAffectations({{personnel.id}})" data-toggle="modal" data-target="#listAffectationModal" href="#" title="Affectations">
                                <span class="f-logo iconfa-random" aria-hidden="true"></span>
                                <span class="glyphicon-class">Affectations</span>
                            </a>
                        </li>
                        <li>
                            <a onclick="listPromotions({{personnel.id}})" data-toggle="modal" data-target="#listPromotionModal" href="#" title="Promotions">
                                <span class="f-logo iconfa-trophy" aria-hidden="true"></span>
                                <span class="glyphicon-class">Promotions</span>
                            </a>
                        </li>
                        <li>
                            <a onclick="listSanctions({{personnel.id}})" data-toggle="modal" data-target="#listSanctionModal" href="#" title="Sanctions">
                                <span class="f-logo iconfa-tags" aria-hidden="true"></span>
                                <span class="glyphicon-class">Sanctions</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
//AngularJS
app.controller('formAjoutCtrl', function ($scope) {
    $scope.personnel = {};
    $scope.populateForm = function (personnel) {
        $scope.personnel = personnel;
    };
    $scope.pupulateContrat = function (contrat) {
        $scope.contrat = contrat;
    };
    $scope.initForm = function () {
        $scope.personnel = {};
        $scope.contrat = {};
    };
}).controller('optionsModalCtrl', function ($scope) {
    $scope.populateForm = function (personnel) {
        $scope.personnel = personnel;
    };
}).controller('listMouvementCongeCtrl', function ($scope) {
    $scope.mouvementConge = false;
    $scope.populateForm = function (personnel) {
        $scope.mouvementConge = personnel.nombreJourdu > 0 ? true : false;
        $scope.personnel = personnel;
    };
    $scope.populateContrat = function (mouvementConge) {
        $scope.mouvementConge = mouvementConge;
    };
}).controller('listAbsenceCtrl', function ($scope) {
    $scope.absence = jQuery("#idAbsence option:first").val();
    $scope.populateForm = function (personnel) {
        $scope.personnel = personnel;
    };
    $scope.populateContrat = function (absence) {
        $scope.absence = absence;
    };
}).controller('listContratCtrl', function ($scope) {
    $scope.populateForm = function (personnel) {
        $scope.personnel = personnel;
    };
    $scope.populateContrat = function (contrat) {
        $scope.contrat = contrat;
    };
}).controller('listEnfantCtrl', function ($scope) {
    $scope.populateForm = function (personnel, enfant) {
        $scope.personnel = personnel;
        $scope.enfant = enfant;
    };
}).controller('listConjointCtrl', function ($scope) {
    $scope.conjoint = {sexe: "M"};
    $scope.populateForm = function (personnel, conjoint) {
        $scope.personnel = personnel;
        $scope.conjoint = conjoint;
    };
}).controller('listPersonnePrevenirCtrl', function ($scope) {
    $scope.populateForm = function (personnel, personnePrevenir) {
        $scope.personnel = personnel;
        $scope.personnePrevenir = personnePrevenir;
    };
}).controller('listAffectationCtrl', function ($scope) {
    $scope.poste = jQuery("#idPoste option:first").val();
    $scope.populateForm = function (personnel, affectation) {
        $scope.personnel = personnel;
        $scope.affectation = affectation;
    };
}).controller('listPromotionCtrl', function ($scope) {
    $scope.promotion = jQuery("#idPromotion option:first").val();
    $scope.populateForm = function (personnel, promotion) {
        $scope.personnel = personnel;
        $scope.promotion = promotion;
    };
}).controller('listSanctionCtrl', function ($scope) {
    $scope.sanction = jQuery("#idSanction option:first").val();
    $scope.populateForm = function (personnel, sanction) {
        $scope.personnel = personnel;
        $scope.sanction = sanction;
    };
}).controller('formDeleteCtrl', function ($scope) {
    $scope.populateForm = function (personnel) {
        $scope.personnel = personnel;
    };
});
//End AngularJs

var action = "ajout", indexRowUpdate = 0;
var $table, $tableAbsence, $tableMouvementConge, $tableContrat, $tableEnfant, $tableConjoint, $tableAffectation, $tablePromotion, $tableSanction, $tablePersonnePrevenir;
jQuery(document).ready(function ($) {
    $("#tableWidget .fixed-table-body").removeClass("fixed-table-body");
    $(".form-contrat, .form-absence, .form-mouvement-conge, .form-enfant, .form-conjoint, .form-personne-prevenir, #btnAddContrat, .verif-matricule").hide();
    $(".select2").select2();
    loadTypeContrat();
    loadFonction();
    loadCategorie();
    loadTypeService();
    $table = $('#table');
    $tableAbsence = $('#tableAbsence');
    $tableContrat = $('#tableContrat');
    $tableMouvementConge = $('#tableMouvementConge');
    $tableEnfant = $('#tableEnfant');
    $tableConjoint = $('#tableConjoint');
    $tableAffectation = $('#tableAffectation');
    $tablePromotion = $('#tablePromotion');
    $tableSanction = $('#tableSanction');
    $tablePersonnePrevenir = $('#tablePersonnePrevenir');
    $('.datetimePicker').datetimepicker({
            timepicker: true,
            formatDate: 'd/m/Y H:i',
            format: 'd/m/Y H:i',
            value: new Date()
        });
    $("#datenaissance, .datePicker, #dateNaissanceEnfant, #datearrivee, #datedebut, #datefin, #datedebutPop, #datefinPop").datepicker({
        dateFormat: 'dd/mm/yy',
        showOtherMonths: true
    });
    jQuery("#joursabsence").blur(function(){
		var nbjou;
		nbjou = $("#joursabsence").val();
		jQuery("#heursabsence").val((nbjou*173.33)/30);
		
	});
    
    jQuery("#heursabsence").blur(function(){
		var nbheu;
		nbheu = $("#heursabsence").val();
		jQuery("#joursabsence").val((nbheu*30)/173.33);
		
	});
    $("#datearrivee").change(function(){
        var id = $("#id").val();
        var idPersonnel = parseInt(id) ? parseInt(id) : 0;
        if(idPersonnel <= 0){
            $("#datedebut").val(this.value);
        }
    });
    
    $("#matricule").blur(function (e) {
        var mat = this.value;
        if(mat.length < 3 || $("#id").val()){
            return;
        }
        var $verificationLoader = $(".verif-matricule");
        $.ajax({
            type: "GET",
            url: baseUrl + "/personnels/cherchpersonMatricule",
            cache: false,
            data: {matri: mat},
            success: function (reponse) {
                if (reponse.row) {
                    alert("Ce matricule existe dej�.");
                }
            },
            error: function (err) {
                $verificationLoader.hide(500);
            },
            beforeSend: function () {
                $verificationLoader.show();
            },
            complete: function () {
                $verificationLoader.hide(500);
            }
        });
    });
    
    $("#numerocnps").blur(function (e) {
        var cnps = this.value;
        if(cnps.length < 3 || $("#id").val()){
            return;
        }
        var $verificationLoader = $(".verif-numerocnps");
        $.ajax({
            type: "GET",
            url: baseUrl + "/personnels/cherchpersonNumCnps",
            cache: false,
            data: {cnps: cnps},
            success: function (reponse) {
                if (reponse.row) {
                    alert("Ce num�ro CNPS existe dej�.");
                }
            },
            error: function (err) {
                $verificationLoader.hide(500);
            },
            beforeSend: function () {
                $verificationLoader.show();
            },
            complete: function () {
                $verificationLoader.hide(500);
            }
        });
    });
    
    $("#btnAddContrat").click(function (e) {
        $(".form-contrat").show(500);
        //Charger le dernier contrat
        var contratsPersonnel = $tableContrat.bootstrapTable("getData");
        var dernierContrat = _.max(contratsPersonnel, function(contrat){ return contrat.id;});
        dernierContrat.id = "";
        var $scope = angular.element(document.getElementById("listContratModal")).scope();
        $scope.$apply(function () {
            $scope.populateContrat(dernierContrat);
        });
        $("#categoriePop").select2("val", dernierContrat.categorie.id);
        $("#typecontratPop").select2("val", dernierContrat.typeContrat.id);
        $("#fonctionPop").select2("val", dernierContrat.fonction.id);
    });
    $("#btnCancelContrat, #listContratModal button.close").click(function (e) {
        $(".form-contrat").hide(500);
    });
    
    //Absence
    $("#btnAddAbsence").click(function (e) {
        $(".form-absence").show(500);
    });
    $("#btnCancelAbsence, #listAbsenceModal button.close").click(function (e) {
        $(".form-absence").hide(500);
    });
    
    //MouvementConge
    $("#btnAddMouvementConge").click(function (e) {
        $(".form-mouvement-conge").show(500);
    });
    $("#btnCancelMouvementConge, #listMouvementCongeModal button.close").click(function (e) {
        $(".form-mouvement-conge").hide(500);
    });
    
    //Enfant 
    $("#btnAddEnfant").click(function (e) {
        $(".form-enfant").show(500);
    });
    $("#btnCancelEnfant, #listEnfantModal button.close").click(function (e) {
        $(".form-enfant").hide(500);
    });
    //Conjoint
    $("#btnAddConjoint").click(function (e) {
        $(".form-conjoint").show(500);
    });
    $("#btnCancelConjoint, #listConjointModal button.close").click(function (e) {
        $(".form-conjoint").hide(500);
    });
    //Personne � prevenir
    $("#btnAddPersonnePrevenir").click(function (e) {
        $(".form-personne-prevenir").show(500);
    });
    $("#btnCancelPersonnePrevenir, #listPersonnePrevenirModal button.close").click(function (e) {
        $(".form-personne-prevenir").hide(500);
    });
    //Affectation
    $("#btnAddAffectation").click(function (e) {
        $(".form-affectation").show(500);
    });
    $("#btnCancelAffectation, #listAffectationModal button.close").click(function (e) {
        $(".form-affectation").hide(500);
    });
    //Promotion
    $("#btnAddPromotion").click(function (e) {
        $(".form-promotion").show(500);
    });
    $("#btnCancelPromotion, #listPromotionModal button.close").click(function (e) {
        $(".form-promotion").hide(500);
    });
    //Sanction
    $("#btnAddSanction").click(function (e) {
        $(".form-sanction").show(500);
    });
    $("#btnCancelSanction, #listSanctionModal button.close").click(function (e) {
        $(".form-sanction").hide(500);
    });
    
    $("#typeService").change(function () {
        var typeService = parseInt(this.value);
        switch (typeService) {
            case 1 :
                $("#choix").show().html("Direction");
                loadServiceByTypeService(typeService, 0);
                break;
            case 2 :
                $("#choix").show().html("Departement");
                loadServiceByTypeService(typeService, 0);
                break;
            case 3 :
                $("#choix").show().html("Service");
                loadServiceByTypeService(typeService, 0);
                break;
        }
    });
    $("#dateNaissance").datepicker({
        dateFormat: 'dd/mm/yy',
        showOtherMonths: true
    });
    
    $("#formAbsence").submit(function (e) {
        e.preventDefault();
        var formData = $(this).serialize();
        $.ajax({
            type: "POST",
            url: baseUrl + "/absence/enregistrerabsencepersonnel",
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == true) {
                    $tableAbsence.bootstrapTable('prepend', reponse.row);
                    $("#btnAddAbsence, .form-absence").hide(500);
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
            }
        });
        return false;
    });
    
    $("#formMouvementConge").submit(function (e) {
        e.preventDefault();
        var formData = $(this).serialize();
        $.ajax({
            type: "POST",
            url: baseUrl + "/personnel/enregistrermvtconge",
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == true) {
                    $tableMouvementConge.bootstrapTable('prepend', reponse.row);
                    $("#btnAddMouvementConge, .form-mouvement-conge").hide(500);
                } else if (reponse.result == false) {
                    alert("Saisie invalide");
                }
            },
            beforeSend: function () {
                $("#formMouvementConge").attr("disabled", true);
                $("#actionMouvementConge span").addClass('loader');
            },
            error: function () {
                $("#actionMouvementConge span").removeClass('loader');
            },
            complete: function () {
                $("#formMouvementConge").removeAttr("disabled");
                $("#actionMouvementConge span").removeClass('loader');
            }
        });
        return false;
    });
    
    $("#formContrat").submit(function () {
        var formData = $(this).serialize();
        $.ajax({
            type: "POST",
            url: baseUrl + "/personnels/savecontratpersonnel",
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == "success") {
                    $tableContrat.bootstrapTable('prepend', reponse.row);
                    $("#btnAddContrat, .form-contrat").hide(500);
                } else if (reponse.result == "erreur_champ_obligatoire") {
                    alert("Saisie invalide");
                }
            },
            beforeSend: function () {
                $("#formContrat").attr("disabled", true);
                $("#actionContrat span").addClass('loader');
            },
            error: function () {
                $("#actionContrat span").removeClass('loader');
            },
            complete: function () {
                $("#formContrat").removeAttr("disabled");
                $("#actionContrat span").removeClass('loader');
            }
        });
        return false;
    });
    $("#formEnfant").submit(function () {
        var formData = $(this).serialize();
        $.ajax({
            type: "POST",
            url: baseUrl + "/personnel/enregistrerenfant",
            cache: false,
            data: formData,
            success: function (reponse) {
                if ((reponse.result && reponse.status) == true) {
                    $tableEnfant.bootstrapTable('prepend', reponse.row);
                    $(".form-enfant").hide(500);
                } else if (reponse.result == false) {
                    alert("Saisie invalide");
                }
            },
            beforeSend: function () {
                $("#formEnfant").attr("disabled", true);
                $("#actionEnfant span").addClass('loader');
            },
            error: function () {
                $("#actionEnfant span").removeClass('loader');
            },
            complete: function () {
                $("#formEnfant").removeAttr("disabled");
                $("#actionEnfant span").removeClass('loader');
            }
        });
        return false;
    });
    $("#formConjoint").submit(function () {
        var formData = $(this).serialize();
        $.ajax({
            type: "POST",
            url: baseUrl + "/personnel/enregistrerconjoint",
            cache: false,
            data: formData,
            success: function (reponse) {
                if ((reponse.result && reponse.status) == true) {
                    $tableConjoint.bootstrapTable('prepend', reponse.row);
                    $(".form-conjoint").hide(500);
                } else if (reponse.result == false) {
                    alert("Erreur survenue, r�essayer plus tard");
                }
            },
            beforeSend: function () {
                $("#formConjoint").attr("disabled", true);
                $("#actionConjoint span").addClass('loader');
            },
            error: function () {
                $("#actionConjoint span").removeClass('loader');
            },
            complete: function () {
                $("#formConjoint").removeAttr("disabled");
                $("#actionConjoint span").removeClass('loader');
            }
        });
        return false;
    });
    $("#formPersonnePrevenir").submit(function () {
        var formData = $(this).serialize();
        $.ajax({
            type: "POST",
            url: baseUrl + "/personnel/enregistrerpersonneprevenir",
            cache: false,
            data: formData,
            success: function (reponse) {
                if ((reponse.result && reponse.status) == true) {
                    $tablePersonnePrevenir.bootstrapTable('prepend', reponse.row);
                    $(".form-personne-prevenir").hide(500);
                } else if (reponse.result == false) {
                    alert("Erreur survenue, r�essayer plus tard");
                }
            },
            beforeSend: function () {
                $("#formPersonnePrevenir").attr("disabled", true);
                $("#actionPersonnePrevenir span").addClass('loader');
            },
            error: function () {
                $("#actionPersonnePrevenir span").removeClass('loader');
            },
            complete: function () {
                $("#formPersonnePrevenir").removeAttr("disabled");
                $("#actionPersonnePrevenir span").removeClass('loader');
            }
        });
        return false;
    });
    
    $("#formAffectation").submit(function () {
        var formData = $(this).serialize();
        $.ajax({
            type: "POST",
            url: baseUrl + "/carriere/enregistreraffectation",
            cache: false,
            data: formData,
            success: function (reponse) {
                if ((reponse.result && reponse.status) == true) {
                    $tableAffectation.bootstrapTable('prepend', reponse.row);
                    $(".form-affectation").hide(500);
                } else if (reponse.result == false) {
                    alert("Erreur survenue, r�essayer plus tard");
                }
            },
            beforeSend: function () {
                $("#formAffectation").attr("disabled", true);
                $("#actionAffectation span").addClass('loader');
            },
            error: function () {
                $("#actionAffectation span").removeClass('loader');
            },
            complete: function () {
                $("#formAffectation").removeAttr("disabled");
                $("#actionAffectation span").removeClass('loader');
            }
        });
        return false;
    });
    
    $("#formPromotion").submit(function () {
        var formData = $(this).serialize();
        $.ajax({
            type: "POST",
            url: baseUrl + "/carriere/enregistrerpromotionpersonnel",
            cache: false,
            data: formData,
            success: function (reponse) {
                if ((reponse.result && reponse.status) == true) {
                    $tablePromotion.bootstrapTable('prepend', reponse.row);
                    $(".form-promotion").hide(500);
                } else if (reponse.result == false) {
                    alert("Erreur survenue, r�essayer plus tard");
                }
            },
            beforeSend: function () {
                $("#formPromotion").attr("disabled", true);
                $("#actionPromotion span").addClass('loader');
            },
            error: function () {
                $("#actionPromotion span").removeClass('loader');
            },
            complete: function () {
                $("#formPromotion").removeAttr("disabled");
                $("#actionPromotion span").removeClass('loader');
            }
        });
        return false;
    });
    
    $("#formSanction").submit(function () {
        var formData = $(this).serialize();
        $.ajax({
            type: "POST",
            url: baseUrl + "/carriere/enregistrersanctionpersonnel",
            cache: false,
            data: formData,
            success: function (reponse) {
                if ((reponse.result && reponse.status) == true) {
                    $tableSanction.bootstrapTable('prepend', reponse.row);
                    $(".form-sanction").hide(500);
                } else if (reponse.result == false) {
                    alert("Erreur survenue, r�essayer plus tard");
                }
            },
            beforeSend: function () {
                $("#formSanction").attr("disabled", true);
                $("#actionSanction span").addClass('loader');
            },
            error: function () {
                $("#actionSanction span").removeClass('loader');
            },
            complete: function () {
                $("#formSanction").removeAttr("disabled");
                $("#actionSanction span").removeClass('loader');
            }
        });
        return false;
    });
    $('#rhpModal').on('hidden.bs.modal', function (e) {
        $(".sectionContrat input, .sectionContrat select").removeAttr("disabled");
        var $scope = angular.element(document.getElementById("formAjout")).scope();
        $scope.$apply(function () {
            $scope.initForm();
        });
    });
    $("#typeContrat").change(function () {
        if ($(this).val() == 1) { //TODO replace with 2 (CDI)
            $("#datefin").val("").attr("disabled", "disabled");
        } else {
            $("#datefin").val("").removeAttr("disabled");
        }
    });
    $("#datefin").change(function () {
        isValidContrat();
    });
    $("#formAjout").submit(function () {
        var formData = $(this).serialize();
        if (action == "ajout") {
            if (!isValidContrat()) {
                return false;
            }
            suiteUrl = "/personnels/enregistrerpersonnel";
        } else {
            suiteUrl = "/personnels/modifierpersonnel";
        }

        $.ajax({
            type: "POST",
            url: baseUrl + suiteUrl,
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == "succes") {
                    if (action == "ajout") {
                        //Rechargement de la liste (le tableau)
                        $table.bootstrapTable('refresh');
                        $("#rhpModal").modal("hide");
                    } else {
                        //MAJ de la ligne modifi�e
                        $table.bootstrapTable('updateRow', {
                            index: indexRowUpdate,
                            row: reponse.row
                        });
                        $("#rhpModal").modal("hide");
                    }
                } else if (reponse.result == "erreur_champ_obligatoire") {
                    alert("Saisie invalide");
                }
            },
            beforeSend: function () {
                $("#formAjout").attr("disabled", true);
                $("#rhpModal .modal-footer span").addClass('loader');
            },
            error: function () {
                alert("Erreur survenue. R�essayer plus tard");
                $("#rhpModal .modal-body div.alert").alert();
                $("#rhpModal .modal-body .alert h4").html("Erreur survenue");
                $("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
                $("#rhpModal .modal-footer span").removeClass('loader');
            },
            complete: function () {
                $("#formAjout").removeAttr("disabled");
                $("#rhpModal .modal-footer span").removeClass('loader');
            }
        });
        return false;
    });
    /* Debut du wizar*/
    var navListItems = $('div.setup-panel div a'),
            allWells = $('.setup-content'),
            allNextBtn = $('.nextBtn');
    allWells.hide();
    navListItems.click(function (e) {
        e.preventDefault();
        var $target = $($(this).attr('href')),
                $item = $(this);
        if (!$item.hasClass('disabled')) {
            navListItems.removeClass('btn-primary').addClass('btn-success');
            $item.addClass('btn-primary');
            allWells.hide();
            $target.show();
            $target.find('input:eq(0)').focus();
        }
    });
    allNextBtn.click(function () {
        var curStep = $(this).closest(".setup-content"),
                curStepBtn = curStep.attr("id"),
                nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
                curInputs = curStep.find("input[type='text'],input[type='url']"),
                isValid = true;
        $(".form-group").removeClass("has-error");
        for (var i = 0; i < curInputs.length; i++) {
            if (!curInputs[i].validity.valid) {
                isValid = false;
                $(curInputs[i]).closest(".form-group").addClass("has-error");
            }
        }

        if (isValid)
            nextStepWizard.removeAttr('disabled').trigger('click');
    });
    $('div.setup-panel div a.btn-primary').trigger('click');
    /* Fin du wizar*/

});
function isValidContrat() {
    //La dur�e du contrat doit etre inferieur � 2 ans
    var dateDebut = new Date(jQuery("#datedebut").datepicker('getDate'));
    var dateProbableFin = new Date(dateDebut.getUTCFullYear() + 2, dateDebut.getUTCMonth(), dateDebut.getDate());
    var dateFin = new Date(jQuery("#datefin").datepicker('getDate'));
    if (dateFin > dateProbableFin) {
        alert("La dur�e du contrat est invalide.\nLa dur�e doit �tre comprise entre 0 et 2 ans");
        jQuery("#datefin").val("");
        return false;
    }
    return true;
}

function loadTypeService() {
    jQuery.ajax({
        type: "POST",
        url: baseUrl + "/parametrages/listetypeservice",
        cache: false,
        success: function (response) {
            if (response != null) {
                tabledata = '';
                for (i = 0; i < response.length; i++) {
                    tabledata += '<option value="' + response[i].id + '" data-libelle="' + response[i].libelle + '" >' + response[i].libelle + '</option>';
                }
                //tabledata += "";
                jQuery('#typeService, #typeServicePop').html(tabledata);
                jQuery('#typeService, #typeServicePop').val("1").trigger("change");
            } else {
                alert('Failure! An error has occurred!');
            }
        },
        error: function () {

        },
    });
}

function loadServiceByTypeService(typeService, defaultValue) {
    jQuery.ajax({
        type: "POST",
        url: baseUrl + "/personnels/listservicepartype",
        cache: false,
        data: {idType: typeService},
        success: function (response) {
            if (response != null) {
                tabledata = '';
                for (i = 0; i < response.length; i++) {
                    tabledata += '<option value="' + response[i].id + '" data-libelle="' + response[i].libelle + '" >' + response[i].libelle + '</option>';
                }
                //tabledata += "";
                jQuery('#service').html(tabledata);
                if (response.length > 0) {
                    if (defaultValue > 0) {
                        jQuery('#service').val(defaultValue).trigger("change");
                    } else {
                        jQuery('#service').val(response[0].id).trigger("change");
                    }
                }
            } else {
                alert('Failure! An error has occurred!');
            }
        },
        error: function () {

        },
    });
}

function optionFormatter(id, row, index) {
    var menuOptions = '<a href="#" onclick="javascript:edit(' + id + ')" data-toggle="modal" data-target="#rhpModal" title="Modifier [NOM : '+row.nom + ' ' + row.prenom +']"><span class="glyphicon glyphicon-pencil"></span></a>&nbsp;&nbsp;&nbsp;';
    menuOptions += '<a href="#" onclick="javascript:listAbsence(' + id + ')" data-toggle="modal" data-target="#listAbsenceModal" title="Absence [NOM : '+row.nom + ' ' + row.prenom +']"><span class="glyphicon glyphicon-tasks"></span></a>&nbsp;&nbsp;&nbsp;';
    menuOptions += '<a onclick="openOptionsModal(' + id + ',' + index + ')" data-toggle="modal" data-target=".modal-options" href="#" title="Voir plus"><span class="glyphicon glyphicon-eye-open"></span></a>';
    return menuOptions;
}

function openOptionsModal(idPersonnel, index) {
    indexRowUpdate = index;
    var $scope = angular.element(document.getElementById("optionsModal")).scope();
    var personnel = _.findWhere($table.bootstrapTable('getData'), {id: idPersonnel});
    $scope.$apply(function () {
        $scope.populateForm(personnel);
    });
}

function edit(idPersonnel) {
    var $scope = angular.element(document.getElementById("formAjout")).scope();
    var rows = $table.bootstrapTable('getData');
    var personnel = _.findWhere(rows, {id: idPersonnel});
    loadPersonnelLastContrat(personnel.id);
    updateComboAndRadioPersonnel(personnel);
    action = "modifier";
    $scope.$apply(function () {
        $scope.populateForm(personnel);
    });
    jQuery(".sectionContrat input, .sectionContrat select").attr("disabled", "disabled");
}

function listAbsence(idPersonnel) {
    var $scope = angular.element(document.getElementById("listAbsenceModal")).scope();
    loadAbsenceByPersonnel(idPersonnel);
    var rows = $table.bootstrapTable('getData');
    var personnel = _.findWhere(rows, {id: idPersonnel});
    $scope.$apply(function () {
        $scope.populateForm(personnel);
    });
}

function listContrat(idPersonnel) {
    var $scope = angular.element(document.getElementById("listContratModal")).scope();
    loadContratByPersonnel(idPersonnel);
    var rows = $table.bootstrapTable('getData');
    var personnel = _.findWhere(rows, {id: idPersonnel});
    $scope.$apply(function () {
        $scope.populateForm(personnel);
    });
}

function listMouvementConge(idPersonnel) {
    var $scope = angular.element(document.getElementById("listMouvementCongeModal")).scope();
    loadMouvementCongeByPersonnel(idPersonnel);
    var rows = $table.bootstrapTable('getData');
    var personnel = _.findWhere(rows, {id: idPersonnel});
    $scope.$apply(function () {
        $scope.populateForm(personnel);
    });
}

function listEnfant(idPersonnel) {
    var $scope = angular.element(document.getElementById("listEnfantModal")).scope();
    loadEnfantByPersonnel(idPersonnel);
    var rows = $table.bootstrapTable('getData');
    var personnel = _.findWhere(rows, {id: idPersonnel});
    var enfant = {};
    enfant.sexe = "M";
    $scope.$apply(function () {
        $scope.populateForm(personnel, enfant);
    });
}

function listConjoint(idPersonnel) {
    var $scope = angular.element(document.getElementById("listConjointModal")).scope();
    loadConjointByPersonnel(idPersonnel);
    var rows = $table.bootstrapTable('getData');
    var personnel = _.findWhere(rows, {id: idPersonnel});
    var enfant = {};
    enfant.sexe = "M";
    $scope.$apply(function () {
        $scope.populateForm(personnel, enfant);
    });
}

function listPersonnePrevenir(idPersonnel) {
    var $scope = angular.element(document.getElementById("listPersonnePrevenirModal")).scope();
    loadPersonnePrevenirByPersonnel(idPersonnel);
    var rows = $table.bootstrapTable('getData');
    var personnel = _.findWhere(rows, {id: idPersonnel});
    var personnePrevenir = {};
    personnePrevenir.sexe = "M";
    personnePrevenir.filiation = "Conjoint(e)";
    $scope.$apply(function () {
        $scope.populateForm(personnel, personnePrevenir);
    });
}

function listAffectations(idPersonnel) {
    var $scope = angular.element(document.getElementById("listAffectationModal")).scope();
    loadAffectationByPersonnel(idPersonnel);
    var personnel = _.findWhere($table.bootstrapTable('getData'), {id: idPersonnel});
    $scope.$apply(function () {
        $scope.populateForm(personnel, {});
    });
}

function listPromotions(idPersonnel) {
    var $scope = angular.element(document.getElementById("listPromotionModal")).scope();
    loadPromotionByPersonnel(idPersonnel);
    var personnel = _.findWhere($table.bootstrapTable('getData'), {id: idPersonnel});
    $scope.$apply(function () {
        $scope.populateForm(personnel, {});
    });
}

function listSanctions(idPersonnel) {
    var $scope = angular.element(document.getElementById("listSanctionModal")).scope();
    loadSanctionByPersonnel(idPersonnel);
    var personnel = _.findWhere($table.bootstrapTable('getData'), {id: idPersonnel});
    $scope.$apply(function () {
        $scope.populateForm(personnel, {});
    });
}
function del(idPersonnel) {
    var $scope = angular.element(document.getElementById("formDelete")).scope();
    var rows = $table.bootstrapTable('getData');
    var personnel = _.findWhere(rows, {id: idPersonnel});
    personnel.info = personnel.nom + ' ' + personnel.prenom;
    $scope.$apply(function () {
        $scope.populateForm(personnel);
    });
}

function loadAbsenceByPersonnel(idPersonnel) {
    jQuery.ajax({
        type: "POST",
        url: baseUrl + "/absence/listerabsencepersonnelsparpersonnel",
        cache: false,
        data: {idPersonnel: idPersonnel},
        success: function (reponse) {
            $tableAbsence.bootstrapTable('load', reponse.rows);
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

function loadMouvementCongeByPersonnel(idPersonnel) {
    jQuery.ajax({
        type: "POST",
        url: baseUrl + "/personnel/listermvtcongesparpersonnel",
        cache: false,
        data: {idPersonnel: idPersonnel},
        success: function (reponse) {
            $tableMouvementConge.bootstrapTable('load', reponse.rows);
        },
        beforeSend: function () {
            $tableMouvementConge.bootstrapTable('load', []);
            //jQuery("#btnAddMouvementConge,.form-mouvement-conge").hide();
        },
        error: function () {
            
        },
        complete: function () {
            
        }
    });
}

function loadContratByPersonnel(idPersonnel) {
    jQuery.ajax({
        type: "GET",
        url: baseUrl + "/personnels/listcontratparpersonneljson",
        cache: false,
        data: {idpersonnel: idPersonnel},
        success: function (reponse) {
            $tableContrat.bootstrapTable('load', reponse.rows);
            var contratActif = _.findWhere(reponse.rows, {statut: true});
            if (contratActif) {
                jQuery("#btnAddContrat").hide(500);
            } else {
                jQuery("#btnAddContrat").show(500);
            }
        },
        beforeSend: function () {
            $tableContrat.bootstrapTable('load', []);
            jQuery("#btnAddContrat,.form-contrat").hide();
            //$("#formAjout").attr("disabled", true);
            //$("#rhpModal .modal-footer span").addClass('loader');
        },
        error: function () {
            /*$("#rhpModal .modal-body div.alert").alert();
             $("#rhpModal .modal-body .alert h4").html("Erreur survenue");
             $("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
             $("#rhpModal .modal-footer span").removeClass('loader');*/
        },
        complete: function () {
            /*$("#formAjout").removeAttr("disabled");
             $("#rhpModal .modal-footer span").removeClass('loader');*/
        }
    });
}

//Enfant
function loadEnfantByPersonnel(idPersonnel) {
    jQuery.ajax({
        type: "POST",
        url: baseUrl + "/personnel/listerenfantsparpersonnel",
        cache: false,
        data: {idPersonnel: idPersonnel},
        success: function (reponse) {
            $tableEnfant.bootstrapTable('load', reponse.rows);
        },
        beforeSend: function () {
            $tableEnfant.bootstrapTable('load', []);
            jQuery(".form-enfant").hide();
            //$("#formAjout").attr("disabled", true);
            //$("#rhpModal .modal-footer span").addClass('loader');
        },
        error: function () {
            /*$("#rhpModal .modal-body div.alert").alert();
             $("#rhpModal .modal-body .alert h4").html("Erreur survenue");
             $("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
             $("#rhpModal .modal-footer span").removeClass('loader');*/
        },
        complete: function () {
            /*$("#formAjout").removeAttr("disabled");
             $("#rhpModal .modal-footer span").removeClass('loader');*/
        }
    });
}

//Enfant load sexe
function loadSexeEnfant(enfant) {
    //Initialisation
    jQuery("#formEnfant :radio").attr("checked", false);
    //Sexe
    if (enfant.sexe == "M") {
        jQuery("#sexeEnfantMasculin span").addClass("checked");
        jQuery("#sexeEnfantMasculin :radio").attr("checked", true);
        jQuery("#sexeEnfantFeminin span").removeClass("checked");
    } else if (enfant.sexe == "F") {
        jQuery("#sexeEnfantFeminin span").addClass("checked");
        jQuery("#sexeEnfantFeminin :radio").attr("checked", true);
        jQuery("#sexeEnfantMasculin span").removeClass("checked");
    }
}


//Conjoint
function loadConjointByPersonnel(idPersonnel) {
    jQuery.ajax({
        type: "POST",
        url: baseUrl + "/personnel/listerconjointsparpersonnel",
        cache: false,
        data: {idPersonnel: idPersonnel},
        success: function (reponse) {
            $tableConjoint.bootstrapTable('load', reponse.rows);
        },
        beforeSend: function () {
            $tableEnfant.bootstrapTable('load', []);
            jQuery(".form-conjoint").hide();
        }
    });
}

//Personne � prevenir 
function loadPersonnePrevenirByPersonnel(idPersonnel) {
    jQuery.ajax({
        type: "POST",
        url: baseUrl + "/personnel/listerpersonnesprevenirparpersonnel",
        cache: false,
        data: {idPersonnel: idPersonnel},
        success: function (reponse) {
            $tablePersonnePrevenir.bootstrapTable('load', reponse.rows);
        },
        beforeSend: function () {
            $tablePersonnePrevenir.bootstrapTable('load', []);
            jQuery(".form-personne-prevenir").hide();
        }
    });
}

//Affectations
function loadAffectationByPersonnel(idPersonnel) {
    jQuery.ajax({
        type: "POST",
        url: baseUrl + "/carriere/listeraffectationsparpersonnel",
        cache: false,
        data: {idPersonnel: idPersonnel},
        success: function (reponse) {
            $tableAffectation.bootstrapTable('load', reponse.rows);
        },
        beforeSend: function () {
            $tableAffectation.bootstrapTable('load', []);
            jQuery(".form-affectation").hide();
        }
    });
}

//Promotions
function loadPromotionByPersonnel(idPersonnel) {
    jQuery.ajax({
        type: "POST",
        url: baseUrl + "/carriere/listerpromotionpersonnelsparpersonnel",
        cache: false,
        data: {idPersonnel: idPersonnel},
        success: function (reponse) {
            $tablePromotion.bootstrapTable('load', reponse.rows);
        },
        beforeSend: function () {
            $tablePromotion.bootstrapTable('load', []);
            jQuery(".form-promotion").hide();
        }
    });
}

//Sanctions
function loadSanctionByPersonnel(idPersonnel) {
    jQuery.ajax({
        type: "POST",
        url: baseUrl + "/carriere/listersanctionpersonnelsparpersonnel",
        cache: false,
        data: {idPersonnel: idPersonnel},
        success: function (reponse) {
            $tableSanction.bootstrapTable('load', reponse.rows);
        },
        beforeSend: function () {
            $tableSanction.bootstrapTable('load', []);
            jQuery(".form-sanction").hide();
        }
    });
}

//Conjoint load sexe
function loadSexeConjoint(conjoint) {
    //Initialisation
    jQuery("#formConjoint :radio").attr("checked", false);
    //Sexe
    if (conjoint.sexe == "M") {
        jQuery("#sexeConjointMasculin span").addClass("checked");
        jQuery("#sexeConjointMasculin :radio").attr("checked", true);
        jQuery("#sexeConjointFeminin span").removeClass("checked");
    } else if (conjoint.sexe == "F") {
        jQuery("#sexeConjointFeminin span").addClass("checked");
        jQuery("#sexeConjointFeminin :radio").attr("checked", true);
        jQuery("#sexeConjointMasculin span").removeClass("checked");
    }
}

function loadPersonnelLastContrat(idPersonnel) {
    jQuery.ajax({
        type: "GET",
        url: baseUrl + "/personnels/listcontratparpersonneljson",
        cache: false,
        data: {idpersonnel: idPersonnel},
        success: function (reponse) {
            var $scope = angular.element(document.getElementById("formAjout")).scope();
            var lastContrat = _.max(reponse.rows, function (contrat) {
                return contrat.id
            });
            console.log("LastContrat", lastContrat);
            if (lastContrat) {
                $scope.$apply(function () {
                    $scope.pupulateContrat(lastContrat);
                });
                updateComboContrat(lastContrat);
            }
        }
    });
}

//Chargement des types contrats
function loadTypeContrat() {
    jQuery.ajax({
        type: "POST",
        url: baseUrl + "/parametrages/listetypecontrat",
        cache: false,
        success: function (response) {
            console.log(response);
            if (response != null) {
                tabledata = '';
                for (i = 0; i < response.length; i++) {
                    tabledata += '<option value="' + response[i].id + '" >' + response[i].libelle + '</option>';
                }
                jQuery('#typeContrat, #typecontratPop').html(tabledata);
            } else {
                alert('Erreur survenue, v�rifiez votre connexion Internet SVP.');
            }
        },
        error: function () {
            alert('Erreur de chargement des types de contrat');
        },
    });
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

//Chargement des cat�gories
function loadCategorie() {
    jQuery.ajax({
        type: "GET",
        url: baseUrl + "/personnels/listcategorie",
        cache: false,
        success: function (response) {
            console.log(response);
            if (response != null) {
                tabledata = '';
                for (i = 0; i < response.length; i++) {
                    tabledata += '<option value="' + response[i].id + '" >' + response[i].libelle + '</option>';
                }
                //tabledata += "";
                jQuery('#categorie').html(tabledata);
                jQuery('#categoriePop').html(tabledata);
            } else {
                alert('Failure! An error has occurred!');
            }
        },
        error: function () {

        },
    });
}

function updateComboAndRadioPersonnel(personnel) {
    loadServiceByTypeService(personnel.service.typeService.id, personnel.service.id);
    jQuery("#nationalite").val(personnel.nationnalite.id).trigger("change");
    jQuery("#idbanque").val(personnel.banquek.id).trigger("change");
    jQuery("#situationmatrimoniale").val(personnel.situationMatrimoniale).trigger("change");
    jQuery("#nombreenfant").val(personnel.nombrEnfant).trigger("change");
    jQuery("#modepaiement").val(personnel.modePaiement).trigger("change");
    jQuery("#typeEmp").val(personnel.typeSalarie).trigger("change");
    jQuery("#situationMedaille").val(personnel.situationMedaille).trigger("change");
    jQuery("#situationEmploie").val(personnel.situationEmploie).trigger("change");
    //Initialisation
    jQuery("#formAjout :radio").attr("checked", false);
    //Sexe
    if (personnel.sexe == "Masculin") {
        jQuery("#sexeMasculin span").addClass("checked");
        jQuery("#sexeMasculin :radio").attr("checked", true);
        jQuery("#sexeFeminin span").removeClass("checked");
    } else if (personnel.sexe == "Feminin") {
        jQuery("#sexeFeminin span").addClass("checked");
        jQuery("#sexeFeminin :radio").attr("checked", true);
        jQuery("#sexeMasculin span").removeClass("checked");
    }

    //Carec
    if (personnel.carec == true) {
        jQuery("#carecOui span").addClass("checked");
        jQuery("#carecOui :radio").attr("checked", true);
        jQuery("#carecNon span").removeClass("checked");
    } else if (personnel.carec == false) {
        jQuery("#carecNon span").addClass("checked");
        jQuery("#carecNon :radio").attr("checked", true);
        jQuery("#carecOui span").removeClass("checked");
    }
}

function updateComboContrat(contrat) {
    jQuery("#fonction").val(contrat.fonction.id).trigger("change");
    jQuery("#typeContrat").val(contrat.typeContrat.id).trigger("change");
    jQuery("#categorie").val(contrat.categorie.id).trigger("change");
    jQuery("#ancienneteInitial").val(contrat.ancienneteInitial).trigger("change");
}


function statutFormatter(statut) {
    return statut ? '<span style="color:green;">En cours</span>' : '<span style="color:red;">Termin�</span>';
}

function optionAbsenceFormatter(id, row, index) {
    return '<a href="#" onclick="javascript:editAbsence(' + id + ')" title="Modifier"><span class="glyphicon glyphicon-pencil"></span></a>';
}

function editAbsence(idAbsencePersonnel) {
    var $scope = angular.element(document.getElementById("listAbsenceModal")).scope();
    var rows = $tableAbsence.bootstrapTable('getData');
    var absencePersonnel = _.findWhere(rows, {id: idAbsencePersonnel});
    $scope.$apply(function () {
        $scope.populateForm(absencePersonnel);
    });
    jQuery(".form-absence").show(500);
}

function statutAbsenceFormatter(statut) {
    return statut ? "Justifi&eacute;" : "Non justifi&eacute;";
}

function commentaireAbsenceFormatter(absences) {
    return absences.commentaire;
}

function motifFormatter(absences) {
    return absences.faute;
}

function typeContratFormatter(typeContrat) {
    return typeContrat.libelle;
}

function posteFormatter(poste) {
    return poste.libelle;
}

function promotionFormatter(promotion) {
    return promotion.libelle;
}

function descriptionFormatter(promotion) {
    return promotion.description;
}

function sanctionFormatter(sanction) {
    return sanction.faute;
}

function commentaireFormatter(sanction) {
    return sanction.commentaire;
}

function fonctionFormatter(fonction) {
    return fonction.libelle;
}

function categorieFormatter(categorie) {
    return categorie.salaireBase;
}
</script>