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
					<li><a href="#" data-toggle="modal" data-target="#testModal">TestT</a></li>
				</ul>
			</div>
			<h4 id="widgetTitle" class="widgettitle">Liste du personnel</h4>
		</div>
		<div id="tableWidget" class="widgetcontent">
			<table id="table" class="table table-info table-striped"
				data-toggle="table" 
				data-click-to-select="true"
				data-single-select="true" 
				data-sort-name="nomComplet"
				data-sort-order="desc"
				data-url="${pageContext.request.contextPath}/personnels/listpersonneljson"
				data-side-pagination="server" 
				data-pagination="true"
				data-page-list="[10, 20, 50, 100, 200]" 
				data-search="false">
				<thead>
					<tr>
						<th data-field="matricule" data-align="left">Matricule</th>
						<th data-field="nomComplet" data-align="left">Nom</th>
						<th data-field="sexe" data-align="left">Sexe</th>
						<th data-field="dNaissance" data-align="center">N&eacute;(e) le</th>
						<th data-field="lieuNaissance" data-align="left">A</th>
						<th data-field="numeroCnps" data-align="center">N� CNPS</th>
						<th data-field="telephone" data-align="center" ata-sortable="true">T&eacute;l&eacute;phone</th>
						<th data-field="situationMatri" data-align="center">Sit. Matri</th>
						<th data-field="situationMed" data-align="center">Medaille</th>
						<th data-field="id" data-formatter="optionFormatter" data-align="center">Options</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<div class="modal fade" id="testModal" tabindex="-1" role="dialog" aria-labelledby="customerTransactionModalLabel" data-backdrop="static">
    <div class="modal-dialog" style="width:50%;" role="document">
        <div class="modal-content form-horizontal">
       		<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myCustomerTransactionModalLabel"> Test</h4>
			</div>
               	<div class="modal-body">
               		<input type="file" class="form-control input-default" id="photo" name="photo" placeholder="Prenom" >
               </div>
               <div class="modal-footer">
                   <button type="button" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-print"></span> Imprimer</button>
               </div>
        </div>
    </div>
</div>

<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
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
												<label for="matricule">Matricule</label> 
												<input type="text" class="form-control input-default" id="matricule" name="matricule" placeholder="matricule" required="required" ng-model="personnel.matricule">
											</div>
											<div class="col-md-4">
												<label for="matricule">Type employ�</label> 
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
														<label for="nom">Nom</label> 
														<input type="text" class="form-control input-default" id="nom" name="nom" placeholder="Nom" required="required" ng-model="personnel.nom">
													</div>
													<div class="col-md-4 ">
														<label for="prenom">Pr&eacute;nom(s)</label> 
														<input type="text" class="form-control input-default" id="prenom" name="prenom" placeholder="Prenom" required="required" ng-model="personnel.prenom">
													</div>
													<div class="col-md-4">
												<label for="matricule">Qualit� de l'emploi</label> 
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
														<label for="datenaissance">N&eacute(e) le</label> 
														<input type="text" class="form-control input-default" id="datenaissance" name="datenaissance" placeholder="N�(e) le" maxlength="10" required="required" ng-model="personnel.dNaissance">
													</div>
													<div class="col-md-4 ">
														<label for="lieunaissance">Lieu de Naissance</label> 
														<input type="text" class="form-control input-default" id="lieunaissance" name="lieunaissance" placeholder="Lieu de naissance" required="required" ng-model="personnel.lieuNaissance">
													</div>
													<div class="col-md-4">
														<label for="sexe">Sexe</label>
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
														<label for="nationalite">Nationnalit&eacute;</label> 
														<select id="nationalite" name="nationalite" class="form-control select2">
															<option>-- Choix de la nationnalit� --</option>
															<option value="1" selected="selected"> Ivoirienne </option>
															<option value="2"> Togolaise </option>
														</select>
													</div>
													<div class="col-md-4">
														<label for="situationmatrimoniale">Situation matrimoniale</label> 
														<select id="situationmatrimoniale" name="situationmatrimoniale" class="form-control select2">
															<option>-- Choix de la situation --</option>
															<option value="1"> MARIE </option>
															<option value="2" selected="selected"> CELIBATAIRE </option>
															<option value="3" > DIVORCE </option>
															<option value="4" > VEUF(VE) </option>
														</select>
													</div>
													<div class="col-md-4">
														<label for="nombreenfant">Nombre d'enfant</label> 
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
													<div class="col-md-4">
														<label for="datearrivee">Date d'arriv&eacute;e</label> 
														<input type="text" class="form-control input-default" id="datearrivee" name="datearrivee" placeholder="Date arriv�e" maxlength="10" required="required" ng-model="personnel.dArrivee">
													</div>
													<div class="col-md-4">
														<label for="email"> Email </label> 
														<input type="email" class="form-control input-default" id="email" name="email" placeholder="Email" ng-model="personnel.email">
													</div>
													<div class="col-md-4">
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
													<div class="col-md-4">
														<label for="banque">Banque</label> 
														<input type="text" class="form-control input-default" id="banque" name="banque" placeholder="Banque" ng-model="personnel.banque">
													</div>
													<div class="col-md-4">
														<label for="numerocompte">Numero de compte</label> 
														<input type="text" class="form-control input-default" id="numerocompte" name="numerocompte" placeholder="Numero de Compte" ng-model="personnel.numeroCompte">
													</div>
													<div class="col-md-4">
														<label for="rib">RIB</label> 
														<input type="text" class="form-control input-default" id="rib" name="rib" placeholder="RIB" ng-model="personnel.rib">
													</div>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-12">
												<div class="row">
													<div class="col-md-4">
														<label for="modepaiement">Mode paiement</label> 
														<select id="modepaiement" name="modepaiement" class="form-control select2">
															<option>-- Mode de paiement --</option>
															<option value="Virement" selected="selected"> Virement </option>
															<option value="Ch�que"> Ch�que </option>
															<option value="Esp�ce"> Espece </option>
														</select>
													</div>
													<div class="col-md-4">
														<label for="typeService">Division</label> 
														<select id="typeService" name="typeService" class="form-control select2"></select>
													</div>
													<div class="col-md-4">
														<label id="choix">Libell&eacute; division</label> 
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
														<label for="fonction">Emploi</label> 
														<select id="fonction" name="fonction" class="form-control select2">
															
														</select>
													</div>
													<div class="col-md-4 ">
														<label for="typeContrat">Type contrat</label> 
														<select id="typeContrat" name="typecontrat" class="form-control select2">
															
														</select>
													</div>
													<div class="col-md-4 ">
														<label for="categorie">Cat&eacute;gorie</label> 
														<select id="categorie" name="categorie" class="form-control select2">
															
														</select>
													</div>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-12">
												<div class="row">
													<div class="col-md-4">
														<label for="datedebut">Date d&eacute;but du contrat</label> 
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
														<label for="salairenet">Net � payer</label> 
														<input type="number" class="form-control input-default" id="salairenet" name="salairenet" placeholder="Net � payer" required="required" ng-model="contrat.netPayer">
													</div>
													<!--div class="col-md-4 ">
														<label for="indemnitelogement">Indemnit&eacute; de logement</label> 
														<input type="text" class="form-control input-default" id="indemnitelogement" name="indemnitelogement" placeholder="Indemnit� de logement" required="required" ng-model="contrat.indemniteLogmt">
													</div> -->
													<div class="col-md-4 ">
														<label for="ancienneteInitial">Anciennet&eacute; initiale</label> 
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

<div class="modal fade" id="listContratModal" ng-controller="listContratCtrl" tabindex="-1" role="dialog" aria-labelledby="listContratModalLabel" data-backdrop="static">
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
								<select id="typecontratPop" name="idTypeContrat" class="form-control select2" ng-model="personnel.typecontrat.id">
									
								</select>
							</div>
							<div class="col-md-4">
								<label for="categoriePop">Cat&eacute;gorie</label> 
								<select id="categoriePop" name="idCategorie" class="form-control select2" ng-model="personnel.categorie.id">
										
								</select>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label for="datedebutPop">Date d&eacute;but du contrat</label> 
								<input type="text" class="form-control input-default" id="datedebutPop" name="dateDebut" placeholder="Date debut contrat" maxlength="10" required="required" ng-model="personnel.dDebut">
							</div>
							<div class="col-md-4">
								<label for="datefinPop">Date fin du contrat</label> 
								<input type="text" class="form-control input-default" id="datefinPop" name="dateFin" placeholder="Date fin contrat" maxlength="10" ng-model="personnel.dFin">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-4 ">
								<label for="salairenetPop">Net � payer</label> 
								<input type="text" class="form-control input-small" id="salairenetPop" name="netAPayer" placeholder="0" required="required" ng-model="personnel.salairenet">
							</div>
							<div class="col-md-4 ">
								<label for="indemnitelogementPop">Indemnit&eacute; de logement</label> 
								<input type="text" class="form-control input-small" id="indemnitelogementPop" name="indemniteLogement" placeholder="0" required="required" ng-model="personnel.indemnitelogement">
							</div>
							<div class="col-md-4 ">
								<label for="ancienneteinitialPop">Anciennet&eacute; initiale</label>
								<input type="number" class="form-control input-small" id="ancienneteinitialPop" name="ancienete" placeholder="0" value="0">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div id="actionContrat" class="col-md-12 text-right">
							<span></span>&nbsp;
							<input type="text"class="hidden" ng-hide="true" name="idPersonnel" ng-model="personnel.id"/>
								<button id="btnCancelContrat" type="reset" class="btn btn-default">Annuler</button>
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

<script type="text/javascript">
//AngularJS
app.controller('formAjoutCtrl', function ($scope) {
    $scope.pupulateForm = function (personnel) {
    	$scope.personnel = personnel;
	};
    $scope.pupulateContrat = function (contrat) {
    	$scope.contrat = contrat;
	};
	$scope.initForm = function () {
	    $scope.personnel = {};
	    $scope.contrat = {};
	};
}).controller('listContratCtrl', function ($scope) {
    $scope.pupulateForm = function (personnel) {
        $scope.personnel = personnel;
    };
}).controller('formDeleteCtrl', function ($scope) {
    $scope.pupulateForm = function (personnel) {
    	$scope.personnel = personnel;
    };
});
//End AngularJs

var action = "ajout", indexRowUpdate = 0;
var $table, $tableContrat;
jQuery(document).ready(function($) {
	$(".form-contrat, #btnAddContrat").hide();
	$(".select2").select2();
	loadTypeContrat();
	loadFonction();
	loadCategorie();
	loadTypeService();
	$table = $('#table');
	$tableContrat = $('#tableContrat');
	$("#datenaissance, #datearrivee, #datedebut, #datefin, #datedebutPop, #datefinPop").datepicker({
		dateFormat: 'dd/mm/yy',
        showOtherMonths:true
    });
	
	$("#btnAddContrat").click(function(e){
		$(".form-contrat").show(500);
	});
	
	$("#btnCancelContrat").click(function(e){
		$(".form-contrat").hide(500);
	});
	
	$("#typeContrat").change(function(){
		
	});
	
	$("#typeService").change(function(){
		//$("#libelleType").html($("#typeService :selected").data("libelle"));
		var typeService = parseInt(this.value);
		switch(typeService){
		case 1 :
			$("#choix").show().html("Direction");
			loadServiceByTypeService(typeService, 0);
			/*$("#choix").show().html("Choix Direction");
			$("#direction").show();
			$("#departement").hide();
			$("#service").hide();*/
			break;
		case 2 :
			$("#choix").show().html("Departement");
			loadServiceByTypeService(typeService, 0);
			/*$("#choix").show().html("Choix Departement");
			$("#direction").hide();
			$("#departement").show();
			$("#service").hide();*/
			break;
		case 3 :
			$("#choix").show().html("Service");
			loadServiceByTypeService(typeService, 0);
			/*$("#choix").show().html("Choix Service");
			$("#direction").hide();
			$("#departement").hide();
			$("#service").show();*/
			break;
		}
	});

	$("#dateNaissance").datepicker({
		dateFormat: 'dd/mm/yy',
        showOtherMonths:true
    });
	
	$("#formContrat").submit(function(){
		var formData = $(this).serialize();
		$.ajax({
            type: "POST",
            url:  baseUrl + "/personnels/savecontratpersonnel",
            cache: false,
            data: formData,
            success: function (reponse) {
            	if (reponse.result == "success") {
            		$tableContrat.bootstrapTable('prepend', reponse.row);
            		$("#btnAddContrat, .form-contrat").hide(500);
            	}
                else if(reponse.result == "erreur_champ_obligatoire"){
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
	
	$('#rhpModal').on('hidden.bs.modal', function (e) {
		$(".sectionContrat input, .sectionContrat select").removeAttr("disabled");
		var $scope = angular.element(document.getElementById("formAjout")).scope();
		$scope.$apply(function () {
	        $scope.initForm();
	    });
	});
	
	$table .on('click-row.bs.table', function (e, row, $element) {
		console.log("event", e);
		console.log("row", row);
	});
	
	$("#typeContrat").change(function(){
		if($(this).val() == 1){ //TODO replace with 2 (CDI)
			$("#datefin").val("").attr("disabled", "disabled");
		}
		else{
			$("#datefin").val("").removeAttr("disabled");
		}
	});
	
	$("#datefin").change(function(){
		isValidContrat();
	});
	
	$("#formAjout").submit(function(){
		var formData = $(this).serialize();
		if(action == "ajout"){
			if(!isValidContrat()){
				return false;
			}
			suiteUrl = "/personnels/enregistrerpersonnel";
		}
		else{
			suiteUrl = "/personnels/modifierpersonnel";
		}
		
		$.ajax({
            type: "POST",
            url:  baseUrl + suiteUrl,
            cache: false,
            data: formData,
            success: function (reponse) {
            	if (reponse.result == "succes") {
                   	if(action == "ajout"){
                   		//Rechargement de la liste (le tableau)
                   		$table.bootstrapTable('refresh');
                   		//$("#formAjout")[0].reset(); //Initialisation du formulaire
                   		$("#rhpModal").modal("hide");
                   	}
                   	else{
                   		//MAJ de la ligne modifi�e
                   		$table.bootstrapTable('updateRow', {
                               index: indexRowUpdate,
                               row: reponse.row
                           });
                   		console.log(indexRowUpdate+'\n'+reponse.row);
                   		//$("#formAjout")[0].reset(); //Initialisation du formulaire
                   		$("#rhpModal").modal("hide");
                   	}
                   }
                   else if(reponse.result == "erreur_champ_obligatoire"){
                   	alert("Saisie invalide");
                   }
            },
            beforeSend: function () {
                   $("#formAjout").attr("disabled", true);
                   $("#rhpModal .modal-footer span").addClass('loader');
               },
            error: function () {
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
	
	allNextBtn.click(function(){
		var curStep = $(this).closest(".setup-content"),
		    curStepBtn = curStep.attr("id"),
		    nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
		    curInputs = curStep.find("input[type='text'],input[type='url']"),
		    isValid = true;
		$(".form-group").removeClass("has-error");
		for(var i=0; i<curInputs.length; i++){
		    if (!curInputs[i].validity.valid){
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

function isValidContrat(){
	//La dur�e du contrat doit etre inferieur � 2 ans
	var dateDebut = new Date(jQuery("#datedebut").datepicker('getDate'));
	var dateProbableFin = new Date(dateDebut.getUTCFullYear()+2, dateDebut.getUTCMonth(), dateDebut.getDate());
	var dateFin = new Date(jQuery("#datefin").datepicker('getDate'));
	if(dateFin > dateProbableFin){
		alert("La dur�e du contrat est invalide.\nLa dur�e doit �tre comprise entre 0 et 2 ans");
		jQuery("#datefin").val("");
		return false;
	}
	return true;
}

function loadTypeService(){
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/parametrages/listetypeservice",
        cache: false,
        success: function (response) {
        	if (response != null) {
				tabledata = '';
        		for (i = 0; i < response.length; i++) {
					tabledata += '<option value="'+response[i].id+'" data-libelle="'+response[i].libelle+'" >' + response[i].libelle + '</option>';
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

function loadServiceByTypeService(typeService, defaultValue){
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/personnels/listservicepartype",
        cache: false,
        data: {idType: typeService},
        success: function (response) {
        	if (response != null) {
				tabledata = '';
        		for (i = 0; i < response.length; i++) {
					tabledata += '<option value="'+response[i].id+'" data-libelle="'+response[i].libelle+'" >' + response[i].libelle + '</option>';
				}
				//tabledata += "";
				jQuery('#service').html(tabledata);
				if(response.length > 0){
					if(defaultValue > 0){
						jQuery('#service').val(defaultValue).trigger("change");
					}
					else{
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
	var option =          '<a onclick="edit('+row.id+','+index+')"   data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier Personnel [NOM : '+row.nom+' '+row.prenom+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	option += '&nbsp;&nbsp;<a onclick="listContrat('+row.id+')" data-toggle="modal" data-target="#listContratModal" href="#" data-action="lister" title="Contrat du personnel ['+row.nom+' '+row.prenom+' ]"><span class="glyphicon glyphicon-list"></span></a>';
	//option += '&nbsp;&nbsp;<a onclick="del('+row.id+')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer Personnel [NOM : '+row.nom+' '+row.prenom+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';
	
    return option;
}

function edit(idPersonnel, index){
	indexRowUpdate = index;
	var $scope = angular.element(document.getElementById("formAjout")).scope();
    
	var rows = $table.bootstrapTable('getData');
	var personnel = _.findWhere(rows, {id: idPersonnel});
	loadPersonnelLastContrat(personnel.id);
	updateComboAndRadioPersonnel(personnel);
	action = "modifier";
	$scope.$apply(function () {
        $scope.pupulateForm(personnel);
    });
	jQuery(".sectionContrat input, .sectionContrat select").attr("disabled", "disabled");
}

function listContrat(idPersonnel){
	var $scope = angular.element(document.getElementById("listContratModal")).scope();
	loadContratByPersonnel(idPersonnel);
	var rows = $table.bootstrapTable('getData');
	var personnel = _.findWhere(rows, {id: idPersonnel});
	
	$scope.$apply(function () {
        $scope.pupulateForm(personnel);
    });
}

function del(idPersonnel){
	var $scope = angular.element(document.getElementById("formDelete")).scope();
    
	var rows = $table.bootstrapTable('getData');
	var personnel = _.findWhere(rows, {id: idPersonnel});
	personnel.info = personnel.nom+' '+personnel.prenom;
	$scope.$apply(function () {
        $scope.pupulateForm(personnel);
    });
}

function loadContratByPersonnel(idPersonnel){
	jQuery.ajax({
        type: "GET",
        url:  baseUrl + "/personnels/listcontratparpersonneljson",
        cache: false,
        data: {idpersonnel: idPersonnel},
        success: function (reponse) {
        	$tableContrat.bootstrapTable('load', reponse.rows);
        	var contratActif = _.findWhere(reponse.rows, {statut: true});
    		if(contratActif){
    			jQuery("#btnAddContrat").hide(500);
    		}
    		else{
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

function loadPersonnelLastContrat(idPersonnel){
	jQuery.ajax({
        type: "GET",
        url:  baseUrl + "/personnels/listcontratparpersonneljson",
        cache: false,
        data: {idpersonnel: idPersonnel},
        success: function (reponse) {
        	var $scope = angular.element(document.getElementById("formAjout")).scope();
        	
        	var lastContrat = _.max(reponse.rows, function(contrat){return contrat.id});
        	console.log("LastContrat", lastContrat);
    		if(lastContrat){
    			$scope.$apply(function () {
                    $scope.pupulateContrat(lastContrat);
                });
    			updateComboContrat(lastContrat);
    		}
        }
    });
}

//Chargement des types contrats
function loadTypeContrat(){
	jQuery.ajax({
           type: "POST",
           url: baseUrl + "/parametrages/listetypecontrat",
           cache: false,
           success: function (response) {
           	console.log(response);
           	
           	if (response != null) {
				tabledata = '';
        		for(i =0 ; i < response.length ; i++){
        			tabledata += '<option value="'+response[i].id+'" >'+response[i].libelle+'</option>';
        		}
        		//tabledata += "";
	    	  	jQuery('#typeContrat, #t+65ypecontratPop').html(tabledata);
			} else {
				alert('Failure! An error has occurred!');
			}
           },
           error: function () {
               
           },
    });
} 

//Chargement des fonctions
 function loadFonction(){
	jQuery.ajax({
         type: "GET",
         url: baseUrl + "/personnels/listfonctionjson",
         cache: false,
         success: function (response) {
         	console.log(response);
         	if (response != null) {
				tabledata = '';
        		for(i =0 ; i < response.total ; i++){
        			tabledata += '<option value="'+response.rows[i].id+'" >'+response.rows[i].libelle+'</option>';
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
function loadCategorie(){
	jQuery.ajax({
        type: "GET",
        url: baseUrl + "/personnels/listcategorie",
        cache: false,
        success: function (response) {
        	console.log(response);
        	if (response != null) {
				tabledata = '';
        		for(i =0 ; i < response.length ; i++){
        			tabledata += '<option value="'+response[i].id+'" >'+response[i].libelle+'</option>';
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

function updateComboAndRadioPersonnel(personnel){
	loadServiceByTypeService(personnel.service.typeService.id, personnel.service.id);
	jQuery("#nationalite").val(personnel.nationnalite.id).trigger("change");
	jQuery("#situationmatrimoniale").val(personnel.situationMatrimoniale).trigger("change");
	jQuery("#nombreenfant").val(personnel.nombrEnfant).trigger("change");
	jQuery("#modepaiement").val(personnel.modePaiement).trigger("change");
	jQuery("#typeEmp").val(personnel.typeSalarie).trigger("change");
	jQuery("#situationMedaille").val(personnel.situationMedaille).trigger("change");
	jQuery("#situationEmploie").val(personnel.situationEmploie).trigger("change");
	
	//Initialisation
	jQuery("#formAjout :radio").attr("checked", false);
	
	
	
	//Sexe
	if(personnel.sexe == "Masculin"){
		jQuery("#sexeMasculin span").addClass("checked");
		jQuery("#sexeMasculin :radio").attr("checked", true);
		jQuery("#sexeFeminin span").removeClass("checked");
	}
	else if(personnel.sexe == "Feminin"){
		jQuery("#sexeFeminin span").addClass("checked");
		jQuery("#sexeFeminin :radio").attr("checked", true);
		jQuery("#sexeMasculin span").removeClass("checked");
	}
	//
	
	
	
	/* else if(personnel.type_salarie == "J"){
		jQuery("#typeJournalier span").addClass("checked");
		jQuery("#typeJournalier :radio").attr("checked", true);
		jQuery("#typeJournalier span").removeClass("checked");
	}else{
		jQuery("#typeHoraire span").addClass("checked");
		jQuery("#typeHoraire :radio").attr("checked", true);
		jQuery("#typeHoraire span").removeClass("checked");
	} */
	//Carec
	if(personnel.carec == true){
		jQuery("#carecOui span").addClass("checked");
		jQuery("#carecOui :radio").attr("checked", true);
		jQuery("#carecNon span").removeClass("checked");
	}
	else if(personnel.carec == false){
		jQuery("#carecNon span").addClass("checked");
		jQuery("#carecNon :radio").attr("checked", true);
		jQuery("#carecOui span").removeClass("checked");
	}
}

function updateComboContrat(contrat){
	jQuery("#fonction").val(contrat.fonction.id).trigger("change");
	jQuery("#typeContrat").val(contrat.typeContrat.id).trigger("change");
	jQuery("#categorie").val(contrat.categorie.id).trigger("change");
	jQuery("#ancienneteInitial").val(contrat.ancienneteInitial).trigger("change");
}


function statutFormatter(statut){
	return statut ? '<span style="color:green;">En cours</span>' : '<span style="color:red;">Termin�</span>';
}

function typeContratFormatter(typeContrat){
	return typeContrat.libelle;
}

function fonctionFormatter(fonction){
	return fonction.libelle;
}

function categorieFormatter(categorie){
	return categorie.salaireBase;
}
</script>
