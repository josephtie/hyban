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
					<li class="divider"></li>
					<li><a href="#" data-toggle="modal" data-target="#rhpModalBanque">Changer logo</a></li>
					<li class="divider"></li>
				</ul>
			</div>
			<h4 id="widgetTitle" class="widgettitle">Liste du personnel</h4>
		</div>
		<div id="tableWidget" class="widgetcontent">
			<table id="table" class="table table-info table-striped"
				data-toggle="table" 
				data-click-to-select="true"
				data-single-select="true" 
				data-sort-name="raisonsoc"
				data-sort-order="desc"
				data-url="${pageContext.request.contextPath}/parametrages/societejson"
				data-side-pagination="server" 
				data-pagination="true"
				data-page-list="[10, 20, 50, 100, 200]" 
				data-search="false">
				<thead>
					<tr>
						<th data-field="sigle" data-align="left">Sigle</th>
						<th data-field="raisonsoc" data-align="left">Nom</th>
						<th data-field="activitepp" data-align="left">Activite</th>
						<th data-field="adress" data-align="center">Adresse</th>
						<th data-field="formjuri" data-align="left">Forme Juridique</th>
						<th data-field="telephone" data-align="center">Telephone</th>
						<th data-field="commune" data-align="center" ata-sortable="true">Commune</th>
						<th data-field="cpteContrib" data-align="center">Compte contrib</th>
						<th data-field="id" data-formatter="optionFormatter" data-align="center">Options</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
	<div class="modal-dialog" style="width:70%;">
		<div class="modal-content">
			<form id="formAjout" class="form-horizontal panel-wizard" role="form" novalidate="novalidate" ng-controller="formAjoutCtrl">
			
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
						            <p>Informations generales</p>
						        </div>
						        <div class="stepwizard-step">
						            <a href="#step-2" type="button" class="btn btn-default btn-circle" disabled="disabled">2</a>
						            <p>Localisation</p>
						        </div>
						        <div class="stepwizard-step">
						            <a href="#step-3" type="button" class="btn btn-default btn-circle" disabled="disabled">3</a>
						            <p>Informations contribuable</p>
						        </div>
						    </div>
						</div>

						    <div class="row setup-content" id="step-1">
						        <div class="col-xs-12">
						            
						                <h3 class="text-right"> Informations generales</h3>
						                	<div class="profilethumb">
											<label for="adresse">RIB</label> 
												<input type="text" class="form-control input-default" id="ribCpteVirmt" name="ribCpteVirmt" placeholder="RIB" maxlength="2" >
										  </div>
						                
										<div class="form-group">
											<div class="col-md-8">
												<label for="matricule">Raison Sociale</label> 
												<input type="text" class="form-control input-default" id="raisonsoc" name="raisonsoc" placeholder="Raison sociale" required="required" ng-model="societe.raisonsoc">
											</div>
											<div class="col-md-4">
												<label for="matricule">Taux prestation familliale</label> 
												<input type="text" class="form-control input-default" id="txprest" name="txprest" placeholder="Taux Prestation familliale" required="required" ng-model="societe.txprest">
									
											</div>
											
										</div>
										
										<div class="form-group">
											<div class="col-md-12">
												<div class="row">
													
													<div class="col-md-8 ">
														<label for="prenom">Activite principale</label> 
														<input type="text" class="form-control input-default" id="activitepp" name="activitepp" placeholder="Activit�" required="required" ng-model="societe.activitepp">
													</div>
													<div class="col-md-4">
												<label for="matricule">Accidents du travail</label> 
												<input type="number" class="form-control input-default" id="txacctr" name="txacctr" placeholder="Accidents du travail" required="required" ng-model="societe.txacctr">
									
											</div>
												</div>
											</div>
										</div>
										
										<div class="form-group">
											<div class="col-md-12">
												<div class="row">
													<div class="col-md-4">
														<label for="datenaissance">Forme juridique</label> 
														<input type="text" class="form-control input-default" id="formjuri" name="formjuri" placeholder="Forme " maxlength="50" required="required" ng-model="societe.formjuri">
													</div>
													<div class="col-md-4">
														<label for="nom">Sigle</label> 
														<input type="text" class="form-control input-default" id="sigle" name="sigle" placeholder="Sigle" required="required" ng-model="societe.sigle">
													</div>
													<div class="col-md-4">
												<label for="matricule">Regime de retraite</label> 
												<input type="number" class="form-control input-default" id="txretraite" name="txretraite" placeholder="Regime de retraite" required="required" ng-model="societe.txretraite">
									
											</div>
												</div>
											</div>
										</div>
										
						                <button class="btn btn-success nextBtn btn-lg pull-right" type="button" > Suivant </button>
						            
						        </div>
						    </div>
						    <div class="row setup-content" id="step-2">
						        <div class="col-xs-12">
						            
						                <h3 class="text-right"> Localisation</h3>
						                <br>
										
										<div class="form-group">
											<div class="col-md-12">
												<div class="row">
													<div class="col-md-4">
														<label for="numerocnps">Adresse</label> 
														<input type="text" class="form-control input-default" id="adress" name="adress" placeholder="adress" ng-model="societe.adress">
													</div>
													<div class="col-md-8">
														<label for="residence">B.P.</label> 
														<input type="text" class="form-control input-default" id="bp" name="bp" placeholder="bp" ng-model="societe.bp" >
													</div>
												</div>
											</div>
										</div>
										
										<div class="form-group">
											<div class="col-md-12">
												<div class="row">
													<div class="col-md-4">
														<label for="datearrivee">Telephone</label> 
														<input type="text" class="form-control input-default" id="telephone" name="telephone" placeholder="telephone" maxlength="8" required="required" ng-model="societe.telephone">
													</div>
													<div class="col-md-4">
														<label for="email"> Commune </label> 
														<input type="text" class="form-control input-default" id="commune" name="commune" placeholder="commune" ng-model="societe.commune">
													</div>
													<div class="col-md-4">
														<label>Quartier</label>
														<div>
														<input type="text" class="form-control input-default" id="quartier" name="quartier" placeholder="Quartier" ng-model="societe.quartier">
										                </div>
													</div>
												</div>
											</div>
										</div>
										
										<div class="form-group">
											<div class="col-md-12">
												<div class="row">
													<div class="col-md-4">
														<label for="banque">Rue</label> 
														<input type="text" class="form-control input-default" id="rue" name="rue" placeholder="Rue" ng-model="societe.rue">
													</div>
													<div class="col-md-4">
														<label for="numerocompte">N� de Lot</label> 
														<input type="text" class="form-control input-default" id="lot" name="lot" placeholder="Lot" ng-model="societe.lot">
													</div>
													<div class="col-md-4">
														<label for="rib">Section Parcelle</label> 
														<input type="text" class="form-control input-default" id="sectpartiell" name="sectpartiell" placeholder="section partielle" ng-model="societe.sectpartiell">
													</div>
												</div>
											</div>
										</div>
								<!-- 		<div class="form-group">
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
										</div> -->
						                <button class="btn btn-success nextBtn btn-lg pull-right" type="button" > Suivant </button>
						        </div>
						    </div>
						    <div class="row setup-content sectionContrat" id="step-3">
						        <div class="col-xs-12">
						            <div class="col-md-12">
						                <h3 class="text-right"> Informations contribuable </h3>
						                <br>
						                <div class="form-group">
											<div class="col-md-12">
												<div class="row">
													<div class="col-md-4 ">
														<label for="fonction">Centre d'Imp�t</label> 
														<input type="text" class="form-control input-default" id="centreImpot" name="centreImpot" placeholder="Centre d'Imp�t" ng-model="societe.centreImpot">
													</div>
													<div class="col-md-4 ">
														<label for="typeContrat">Code Activit�</label> 
													<input type="text" class="form-control input-default" id="codeActivite" name="codeActivite" placeholder="Code Activit�" ng-model="societe.codeActivite">
															
													
													</div>
													<div class="col-md-4 ">
														<label for="categorie"> Code Etab.</label> 
														<input type="text" class="form-control input-default" id="codeEts" name="codeEts" placeholder="Code Etab." ng-model="societe.codeEts">
														
													</div>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-12">
												<div class="row">
													<div class="col-md-4">
														<label for="datedebut">Nom comptable</label> 
														<input type="text" class="form-control input-default" id="nomcomptable" name="nomcomptable" placeholder="Nomcomptable" maxlength="10" required="required" ng-model="societe.nomcomptable">
													</div>
													<div class="col-md-4">
														<label for="datefin">Tel. comptable</label> 
														<input type="text" class="form-control input-default" id="telcomptable" name="telcomptable" placeholder="Tel comptable" required="required" ng-model="societe.telcomptable">
													</div>
													<div class="col-md-4">
														<label for="datefin">Adresse comptable</label> 
														<input type="text" class="form-control input-default" id="adrcomptable" name="adrcomptable" placeholder="adressebcomptable"  ng-model="societe.adrcomptable">
													</div>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-12">
												<div class="row">
													<div class="col-md-4 ">
														<label for="salairenet">N� Employeur</label> 
														<input type="text" class="form-control input-default" id="codeEmployeur" name="codeEmployeur" placeholder="Code Employeur" required="required" ng-model="contrat.codeEmployeur">
													</div>
												<div class="col-md-4 ">
														<label for="indemnitelogement">Compte contribuable</label> 
														<input type="text" class="form-control input-default" id="cpteContrib" name="cpteContrib" placeholder="Compte contrib" maxlength="8" required="required" ng-model="societe.cpteContrib">
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
					<input type="text" class="hidden" ng-hide="true" value="" id="id" name="id" ng-model="societe.id">
				</div>
			</form>
		</div>
	</div>
</div>



<div class="modal fade" id="rhpModalBanque" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document" style="width: 60%;">
		<div class="modal-content">
			<div id="formAjoutBanklogo" class="form-horizontal" role="form" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Logo de Societ�</h4>
				</div>
				<div class="modal-body">
			
                                    <div class="form-group">                               
                                <div class="col-lg-2">
                                 <img src="${contextPath}${urllogo}" alt="" style="width: 126px; height: 44px;"/>
                                    <!-- <img src="" alt="" style="width: 150px; height: 150px;"/> -->
                                    <input name="excelFile" id="excelFile" type="file"  onblur="javascript:verifier();" name="files[]" multiple/>
                                    <label class="erro" id="error" style="color:navy;position: relative;left: 250px"></label>
                                </div>
                            </div><!-- End .form-group  -            
                       <div class="form-group">
						<label class="col-md-2 control-label"> </label>
						<div class="col-md-10">
							<div class="row">
								<div class="col-md-8 ">
									<label for="telephone">Banque</label> 
									 <select class="form-control input-small select2" id="idbank" name="idbank" ng-model="cpteVirmtBanque.bank.id" >
                                    <c:forEach items="${listeBanques}" var="banques">
                                        <option value="${banques.id}">${banques.sigle} (${banques.codbanq})</option>
                                    </c:forEach>
                                </select>
								</div>
								<div class="col-md-4">
									<label for="adresse">RIB</label> 
									<input type="text" class="form-control input-default" id="ribCpteVirmt" name="ribCpteVirmt" placeholder="RIB" maxlength="2" >
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
									 <input type="text" class="form-control input-default" id="numguichCpteVirmt" name="numguichCpteVirmt" maxlength="5" placeholder="Numero de Compte" >
								</div>
								<div class="col-md-8">
									<label for="adresse">Num. compte</label> 
									<input type="text" class="form-control input-default" id="numcpteCpteVirmt" name="numcpteCpteVirmt" placeholder="Numero de Compte" maxlength="12" >
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
									 <input type="text" class="form-control input-default" id="donneurOrdCpteVirmt" name="donneurOrdCpteVirmt" maxlength="15" placeholder="Donneur d'ordre" >
								</div>
								<div class="col-md-8">
									<label for="adresse">Code. etablissement</label> 
									<input type="text" class="form-control input-default" id="codEtablVirmt" name="codEtablVirmt" placeholder="Code Etablissement" maxlength="5" >
								</div>
							</div>
						</div>
					</div>  -->
			
				</div>
				<div class="modal-footer">
					<span></span>&nbsp;
					<input type="hidden" id="id" name="id" >
					<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
					<button type="submit" id="btSubmitLogo" onclick=" savelogo()" class="btn btn-success">Valider</button>
				</div>
				       
				        
               
                <br>
			</div>
			</div>
			</div>
		</div>
<%-- <div class="modal fade" id="listContratModal" ng-controller="listContratCtrl" tabindex="-1" role="dialog" aria-labelledby="listContratModalLabel" data-backdrop="static">
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
 --%>
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
	            	<h4 ng-bind="societe.info"></h4>
	            	<input type="text" class="hidden" ng-hide="false" value="" ng-bind="societe.id" name="id" ng-model="societe.id">
	            </div>
	            <div class="modal-footer">
                    	<input type="text" class="hidden" ng-hide="true" value="" name="id" ng-model="societe.id">
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
                </form>
        	</div>
        
    </div>
</div>

<script type="text/javascript">
//AngularJS
app.controller('formAjoutCtrl', function ($scope) {
    $scope.pupulateForm = function (societe) {
    	$scope.societe = societe;
	};
    $scope.pupulateContrat = function (contrat) {
    	$scope.contrat = contrat;
	};
	$scope.initForm = function () {
	    $scope.societe = {};
	    $scope.contrat = {};
	};
}).controller('listContratCtrl', function ($scope) {
    $scope.pupulateForm = function (societe) {
        $scope.societe = societe;
    };
}).controller('formDeleteCtrl', function ($scope) {
    $scope.pupulateForm = function (societe) {
    	$scope.societe = societe;
    };
});
//End AngularJs

var action = "ajout", indexRowUpdate = 0;
var actionDeleteUrl = "/parametrages/deletesociete";
var $table, $tableContrat, $tableCptevrmt;
jQuery(document).ready(function($) {
	$(".form-contrat, #btnAddContrat").hide();
	$(".select2").select2();
	loadTypeContrat();
	loadFonction();
	loadCategorie();
	loadTypeService();
	var urllogo='${urllogo}';
	$table = jQuery('#table');
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
	
	jQuery("#formAjout").submit(function(){
		var formData = $(this).serialize();
		
		
			suiteUrl = "/parametrages/enregistrersociete";
		
		
		
		$.ajax({
            type: "POST",
            url:  baseUrl + suiteUrl,
            cache: false,
            data: formData,
            success: function (reponse) {
            	if (reponse.result == "success") {
                   	if(action == "ajout"){
                   		//Rechargement de la liste (le tableau)
                   		$table.bootstrapTable('refresh');
                   		//$("#formAjout")[0].reset(); //Initialisation du formulaire
                   		jQuery("#rhpModal").modal("hide");
                   	}
                   	else{
                   		//MAJ de la ligne modifi�e
                   		$table.bootstrapTable('updateRow', {
                               index: indexRowUpdate,
                               row: reponse.row
                           });
                   		console.log(indexRowUpdate+'\n'+reponse.row);
                   		//$("#formAjout")[0].reset(); //Initialisation du formulaire
                   		jQuery("#rhpModal").modal("hide");
                   	}
                   }
                   else if(reponse.result == "erreur_champ_obligatoire"){
                   	alert("Saisie invalide");
                   }
            },
            beforeSend: function () {
            	jQuery("#formAjout").attr("disabled", true);
                   jQuery("#rhpModal .modal-footer span").addClass('loader');
               },
            error: function () {
            	jQuery("#rhpModal .modal-body div.alert").alert();
               	jQuery("#rhpModal .modal-body .alert h4").html("Erreur survenue");
               	jQuery("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
               	jQuery("#rhpModal .modal-footer span").removeClass('loader');
               },
            complete: function () {
            	jQuery("#formAjout").removeAttr("disabled");
                   jQuery("#rhpModal .modal-footer span").removeClass('loader');
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

function verifier(){
	
    //$('#btnSubmitUtiliExterne').click(function(){
    var file = jQuery('#excelFile').val();
    var exts = ['png','jpg'];
    // first check if file field has any value
    if ( file ) {
      // split file name at dot
      var get_ext = file.split('.');
      // reverse name to check extension
      get_ext = get_ext.reverse();
      // check file type is valid as given in 'exts' array
      if ( jQuery.inArray ( get_ext[0].toLowerCase(), exts ) > -1 ){
      	jQuery("#error").html('Verification effectu�e');
          return true;
      } else {
      	jQuery('.erro').show();
      	jQuery("#error").html('Respecter la taille et le type du fichier');
      	jQuery("#excelFile").focus();
      	jQuery("#excelFile").val("");
           return false;
      }
    }
  };
  
  
  function savelogo(){
	  alert('salut');
	  if(jQuery('#excelFile').val()) {
		
			jQuery('#btSubmitLogo').hide();
			jQuery('#loader-icon').show();		
			var oMyForm = new FormData();
			oMyForm.append('file', excelFile.files[0]);			
			jQuery.ajax({ 
	            type: "POST",
				url:   contextPath+"/parametrages/saveattrib", 
	            data: oMyForm, 
				enctype: 'multipart/form-data',
			    dataType: 'text',
			    processData: false,
			    contentType: false,
			    cache: false,
				beforeSubmit: function() {
					// jQuery("#progress-bar").width('0%');
					//jQuery('#btnSubmit').hide();
					//jQuery('#loader-icon').show();
					
				},
				success:function (){
					jQuery('#loader-icon').hide();
				},
	            complete: function () {
					jQuery('#loader-icon').hide();
					jQuery('#btSubmitLogo').show();
	        		alert('Fichier export� avec succ�s');
	        		jQuery("#rhpModalBanque").modal('hide');
	            },
				resetForm: true 
			}); 
			return false; 
		}
	  
  };
 function save(){
	//var formData = $(this).serialize();
	
	
		suiteUrl = "/parametrages/enregistrercptevirmtbank";
	
	
	
		jQuery.ajax({
        type: "POST",
        url:  baseUrl + suiteUrl,
        cache: false,
        data:{ 	id : jQuery('#id').val(),
        	idbank : jQuery('#idbank').val(),
        	ribCpteVirmt : jQuery('#ribCpteVirmt').val(),
        	numguichCpteVirmt : jQuery('#numguichCpteVirmt').val(),
        	numcpteCpteVirmt : jQuery('#numcpteCpteVirmt').val(),
        	donneurOrdCpteVirmt : jQuery('#donneurOrdCpteVirmt').val(),
        	codEtablVirmt : jQuery('#codEtablVirmt').val()},
        success: function (reponse) {
        	if (reponse.result == true) {
               	if(action == "ajout"){
               		//Rechargement de la liste (le tableau)
               		//$tableCptevrmt.bootstrapTable('refresh');
               		//$("#formAjout")[0].reset(); //Initialisation du formulaire
               		jQuery("#formAjoutBank")[0].reset();
               		jQuery("#rhpModalBanque").modal("hide");
               	}
               	else{
               		//MAJ de la ligne modifi�e
               		$tableCptevrmt.bootstrapTable('updateRow', {
                           index: indexRowUpdate,
                           row: reponse.row
                       });
               		console.log(indexRowUpdate+'\n'+reponse.row);
               		//$("#formAjout")[0].reset(); //Initialisation du formulaire
               		jQuery("#rhpModalBanque").modal("hide");
               	}
               }
               else if(reponse.result == "erreur_champ_obligatoire"){
               	alert("Saisie invalide");
               }
        },
        beforeSend: function () {
        	jQuery("#formAjout").attr("disabled", true);
               jQuery("#rhpModal .modal-footer span").addClass('loader');
           },
        error: function () {
        	jQuery("#rhpModal .modal-body div.alert").alert();
           	jQuery("#rhpModal .modal-body .alert h4").html("Erreur survenue");
           	jQuery("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
           	jQuery("#rhpModal .modal-footer span").removeClass('loader');
           },
        complete: function () {
        	jQuery("#formAjoutBank").removeAttr("disabled");
               jQuery("#rhpModal .modal-footer span").removeClass('loader');
           }
    });
	return false;
};


jQuery("#formDelete").submit(function(e){
	e.preventDefault();
    var formData = jQuery(this).serialize();
    var idSup = [];
  	//Le formulaire formDelete doit avoir un seul champ input:text
    idSup.push(parseInt(jQuery("#formDelete :text").val()));
    
    jQuery.ajax({
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
                jQuery(".deleteModal").modal('hide');
            }
            else if(reponse.result == "erreur_champ_obligatoire"){
            	alert("Saisie invalide");
            }
        },
        error: function (err) {
        	jQuery(".deleteModal .modal-body div.alert").alert();
        	jQuery(".deleteModal .modal-body .alert h4").html("Erreur survenue");
        	jQuery(".deleteModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
        	jQuery(".deleteModal .modal-footer span").removeClass('loader');
        },
        beforeSend: function () {
        	jQuery("#formDelete").attr("disabled", true);
            jQuery(".deleteModal .modal-footer span").addClass('loader');
        },
        complete: function () {
        	jQuery("#formDelete").removeAttr("disabled");
            jQuery(".deleteModal .modal-footer span").removeClass('loader');
        }
    });
});
//$( ".select2" ).select2();

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
	var option =          '<a onclick="edit('+row.id+','+index+')"   data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier Personnel [NOM : '+row.raisonsoc+' '+row.sigle+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	//option += '&nbsp;&nbsp;<a onclick="listContrat('+row.id+')" data-toggle="modal" data-target="#listContratModal" href="#" data-action="lister" title="Contrat du personnel ['+row.nom+' '+row.prenom+' ]"><span class="glyphicon glyphicon-list"></span></a>';
	/* option += '&nbsp;&nbsp;<a onclick="del('+row.id+')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer Personnel [NOM : '+row.raisonsoc+' '+row.sigle+' ]"> <span class="glyphicon glyphicon-trash"></span></a>'; */
	
    return option;
}

function bankFormatter(bank){
	return bank.sigle
}

function edit(idPersonnel, index){
	indexRowUpdate = index;
	var $scope = angular.element(document.getElementById("formAjout")).scope();
    
	var rows = $table.bootstrapTable('getData');
	var societe = _.findWhere(rows, {id: idPersonnel});
	//loadPersonnelLastContrat(personnel.id);
	//updateComboAndRadioPersonnel(personnel);
	action = "modifier";
	$scope.$apply(function () {
        $scope.pupulateForm(societe);
    });
	//jQuery(".sectionContrat input, .sectionContrat select").attr("disabled", "disabled");
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
	var societe = _.findWhere(rows, {id: idPersonnel});
	societe.info = societe.sigle+' '+societe.formjuri;
	
	$scope.$apply(function () {
        $scope.pupulateForm(societe);
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
	    	  	jQuery('#typeContrat, #typecontratPop').html(tabledata);
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


function bankFormatter(bank){
	return bank.sigle;
}
function updateComboAndRadioPersonnel(personnel){
	loadServiceByTypeService(personnel.service.typeService.id, personnel.service.id);
	jQuery("#nationalite").val(personnel.nationnalite.id).trigger("change");
	jQuery("#situationmatrimoniale").val(personnel.situationMatrimoniale).trigger("change");
	jQuery("#nombreenfant").val(personnel.nombrEnfant).trigger("change");
	jQuery("#modepaiement").val(personnel.modePaiement).trigger("change");
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
	if(personnel.type_salarie == "M"){
		jQuery("#typeMensuel span").addClass("checked");
		jQuery("#typeMensuel :radio").attr("checked", true);
		jQuery("#typeMensuel span").removeClass("checked");
	}
	else if(personnel.type_salarie == "J"){
		jQuery("#typeJournalier span").addClass("checked");
		jQuery("#typeJournalier :radio").attr("checked", true);
		jQuery("#typeJournalier span").removeClass("checked");
	}else{
		jQuery("#typeHoraire span").addClass("checked");
		jQuery("#typeHoraire :radio").attr("checked", true);
		jQuery("#typeHoraire span").removeClass("checked");
	}
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
