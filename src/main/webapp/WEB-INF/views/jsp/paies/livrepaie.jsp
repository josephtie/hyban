
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
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-sort-name="id"
					data-sort-order="desc"
					data-url="${pageContext.request.contextPath}/personnels/listcontratpersonneljson"
					data-side-pagination="server" 
					data-pagination="true"
			        data-show-export="true"
			        data-page-list="[10, 20, 50, 100, 200,1000]"
                    data-export-dataType="all"
					data-search="true">
					<thead>
						<tr>
						
							<th data-field="personnel" data-formatter="matriFormatter" data-align="left" data-sortable="true">Matricule</th>
							<th data-field="personnel" data-formatter="nomFormatter" data-align="left" data-sortable="true">Nom</th>
							<th data-field="personnel" data-formatter="nomfstatutFormatterL" data-align="left" data-sortable="true">Statut</th>
							<th data-field="personnel" data-formatter="sexeFormatter" data-align="left">Sexe</th>
							<th data-field="personnel" data-formatter="datnaisFormatter" data-align="center">N&eacute;(e) le</th>
							<th data-field="personnel"  data-formatter="lieunaisFormatter" data-align="left">A</th>
							<!-- <th data-field="personnel" data-formatter="telephoneFormatter" data-align="center">T&eacute;l&eacute;phone</th> -->
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
			</form>
		</div>
	
	
		<div id="tableWidgethisto" class="widgetcontent">
			<div id="toolbar">
			    <div class="form-inline">
			    
			   <!--  <button id="btnGenerer" type="button" data-toggle="modal" data-target="#rhpModalImprimer" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>Imprimer bulletin</button> -->
			    </div>
			</div>
			<form action="#" id="formListhisto">
			<div class="col-md-3">
                		<select id="periodePaieImpression" name="periodePaieImpression" onchange="chargerbulletinParPeriode()" class="form-control select2" required="required"></select>
                		<br/>
                </div>
                <div style="max-height: 500px; overflow-y: auto;">
				<table id="tableBullhisto" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-show-export="true"
					data-sort-name="name"
					data-show-footer="false"
					<%-- data-url="${pageContext.request.contextPath}/paie/listbulletinMoisActif" --%>
					data-sort-order="desc"
					data-show-columns="true" 
					<%-- data-url="${pageContext.request.contextPath}/paie/savebullPersonnel"  --%>
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[ 20, 50, 100, 200,500,1000]"
					data-search="true">
					<thead>
						<tr>
							<th data-field="contratPersonnel" data-formatter="matriculesFormatter"  data-align="left" data-sortable="true">Matricule</th>
							<th data-field="contratPersonnel" data-formatter="nomcompletFormatter" data-align="left" data-sortable="true">Nom</th>
							<th data-field="contratPersonnel" data-formatter="nomstatutFormatter" data-align="left" data-sortable="true">Statut</th>
							<th data-field="nombrePart"  data-align="left">Nbre part</th>
							<th data-field="anciennete"  data-align="center">Anciennet&eacute;</th>
							<th data-field="montantSalairbase"   data-align="left">Salaire Base</th>
							<th data-field="montantSursalaire"  data-align="center">Sursalaire</th>
							<th data-field=montantPrimeanciennete  data-align="center">Prime. Anciennet&eacute;</th>
							<th data-field="montantIndemnitelogement"  data-align="right">Indem logement</th>
							<th data-field="indemniteTransportImp" data-align="right">Indem de transport Imp.</th>
						   <th data-field="montantautreIndemImposable" data-align="right">Autres Indem Imp.</th>
							<th data-field="mtautreImposable" data-align="right">Autres Imp.</th>
							<th data-field="brutImpo" data-align="right">Brut imposable</th>
							<th data-field="mtindemniteRespons" data-align="right">Indem de respons.</th>
							<th data-field="indemniteRepresent" data-align="right">Indem de repres.</th>
							<th data-field="mtindemniteTranspImp" data-align="right">Indem transp </th>
							<th data-field="mtautreNonImposable" data-align="right">Autres Non Imp.</th>

							<th data-field="mtbrutNonImpo" data-align="right">Brut Non Imp.</th>
							<th data-field="montantIts" data-align="right">ITS</th>							
						<%--	<th data-field="montantCn"  data-align="left" data-sortable="true">CN</th>--%>
						<%--	<th data-field="montantIgr"  data-align="left" data-sortable="true">IGR</th>--%>
							<th data-field="montanttotalretenuefiscal"  data-align="left">Retenue fiscale</th>
							<th data-field="montantbaseCnps"  data-align="left">Base cnps</th>
							<th data-field="montantCnps"  data-align="center">CNPS</th>
							<th data-field="montantavanceetacompte"   data-align="left">Avance & Acompte</th>
							<th data-field="montantpretaloes"  data-align="center">Pret</th>
							<th data-field="montantcarec"  data-align="center">Carec</th>
							<th data-field="mtautrePrelevement"  data-align="center">Autre prelevements</th>
							<th data-field="montanttotalretenue"  data-align="right">Total retenue</th>
							<th data-field="indemniteRepresent" data-align="right">Indem de repres.</th>
							<th data-field="indemniteTransp" data-align="right">Indem de transport.</th>
							<th data-field="mtregularisation"  data-align="center">Regularisation</th>
							<th data-field="montantNetapayer"  data-align="center">Net a payer</th>
							<th data-field="montanttotalbrut"   data-align="left">Total brut</th>
							<th data-field="montantIs"  data-align="center">IS</th>
							<th data-field="montantTa"  data-align="center">TA</th>
							<th data-field="montantFpc"  data-align="right">FPC</th>
							<th data-field="prestationFamil" data-align="right">Prest familiale</th>
							<th data-field="accidentTrav" data-align="right">Acc de travail</th>
							<th data-field="montantRetraite" data-align="right">Retraite</th>
							<th data-field="montanttotalpatronal" data-align="right"> Total patronal</th>
							<th data-field="montanttotalmassesalarial" data-align="right">Total masse salariale</th>
							 
							
							<th data-field="id" data-formatter="optionFormatterbull" data-align="center">Options</th> 
							
						</tr>
					</thead>
		
				</table>
				</div>
			</form>
		</div>
	</div>
</div>
				</div><!-- widgetcontent-->
			</div>
		</div>
	</div>
</div>


<!-- Generation des bulletins -->
<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formGenerer" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">G&eacute;n&eacute;ration du bulletin de paie</h4>
				</div>
                <div class="modal-body">
                	<h3>Vous etes sur le point de generer le bulletin de paie de la periode de <span id="periodePaie" class="danger">Mars 2016</span> du personnel.</h3>
                	<br/>
                	<h4>En cliquant sur le bouton "Valider", le proccessus sera lanc&eacute;.</h4>
                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                	<input type="hidden"  id="idPeriode" name="id">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="rhpModalVirmt" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formGenererVirmt" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->

                    <h4 class="modal-title" id="myModalLabel">G&eacute;n&eacute;ration d'ordre de virement</h4>
				</div>
                <div class="modal-body">
                	  <div class="form-group">
						<label class="col-md-2 control-label"> </label>
						<div class="col-md-10">
							<div class="row">
								<div class="col-md-6 ">
									<label for="telephone">Cpte Entreprise Banque</label> 
									 <select class="form-control input-small select2" id="idbankEnt" name="idbankEnt"  >
                                    <c:forEach items="${listeBanquesEmp}" var="banquesEnt">
                                        <option value="${banquesEnt.id}">${banquesEnt.sigle} (${banquesEnt.codbanq})</option>
                                    </c:forEach>
                                </select>
								</div>
								
								<div class="col-md-6 ">
									<label for="telephone"> Banque</label> 
									 <select class="form-control input-small select2" id="idbank" name="idbank"  >
                                    <c:forEach items="${listeBanques}" var="banques">
                                        <option value="${banques.id}">${banques.sigle} (${banques.codbanq})</option>
                                    </c:forEach>
                                </select>
								</div>
								
							</div>
						</div>
					</div> 
                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                	<input type="hidden"  id="idPeriode" name="id">
                	 <input id="valtab" type="hidden" class="form-control" placeholder="Montant" value="${promotion}"/>  
                	 
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Livre de paie -->
<div class="modal fade" id="rhpModalValider"  role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static" >
    <div class="modal-dialog" role="document" style="width:80%;"  >
        <div class="modal-content">
            <form id="formGenererValidBull" class="form-horizontal" >
                <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> 
                    <h4 class="modal-title" id="myModalLabel">Valider les bulletins de paie de ${periode}</h4>
				</div>
                <div class="modal-body">
					<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
					<button type="submit" onclick="genererLivreDePaie()" class="btn btn-success">Valider</button>
					 <button id="exportExcelLiv" class="btn btn-success" > Exporter en Excel</button>



                <!-- <div id="toolbar">
			    <div class="form-inline">
			        <button id="btnGenerer5" type="submit" data-toggle="modal" data-target="#rhpModal5 " title="Cocher les employ�s mis en sommeil" class="btn btn-primary btn-xs"><i class="fa fa-plus"></i>G�n�rer tous les bulletins</button>
			    </div>
			   </div> -->
            
              <%-- <form action="#" id="formList"> --%>

				<div class="table-responsive"  style="max-height: 500px; overflow-y: auto;">
				<table id="tableliv" class="table table-info table-striped table-responsive" style="overflow:auto"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"					
					data-sort-name="name"
					data-pagination="true"
					data-sort-order="desc"
					data-show-footer="false"
					<%-- data-url="${pageContext.request.contextPath}/paie/savebullPersonnel"  data-show-columns="true"  --%>
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[ 25, 50, 100, 200,500,1000]"
					data-show-export="true"					
					data-export-dataType="all"
					data-search="true">
					<thead>

							<tr>
							<th data-field="matricule"  data-align="left" data-sortable="true" style="height: 65px">Matricule</th>
							<th data-field="nomPrenom"  data-align="left" data-sortable="true">Nom  &   Prenoms  </th>
								<%--<th data-field="contratPersonnel" data-formatter="nomstatutFormatter" data-align="left" data-sortable="true">Statut</th>--%>
							<th data-field="nombrePart"  data-align="left">Nbre part</th>
							<th data-field="anciennete"  data-align="center">Anciennet&eacute;</th>
							<th data-field="mtsalaireBase"   data-align="left" >Salaire Base</th>
							<th data-field="mtsursalaire"  data-align="center">Sursalaire</th>
							<th data-field="mtprimeAnciennete"  data-align="center">Prime. Anciennet&eacute;</th>
							<th data-field="mtindemniteLogement"  data-align="right">Indem logement</th>
							<th data-field="mtindemniteTransportImp"  data-align="right">Transp Imp</th>
							<th data-field="mtautreIndemImposable"  data-align="right">Indem Imp</th>

                       		<c:forEach items="${listePrimesImp}" var="rubrique" >
							   <th  data-field="prime${rubrique.id}" data-rubrique="${rubrique.id}" data-formatter="primeFormatter" data-align="right">${rubrique.libelle}</th>
						   </c:forEach>
							<c:forEach items="${listePrimesImposetNon}" var="rubrique" >
								<th  data-field="primeI${rubrique.id}" data-rubrique="${rubrique.id}" data-formatter="primeFormatterI" data-align="right">${rubrique.libelle}</th>
							</c:forEach>

								<%--<th data-field="mtautreImposable"  data-align="right">Autre Primes Imp</th>--%>
								<th data-field="mtbrutImposable" data-align="right">Brut imposable</th>
							<%--<th data-field="mtindemniteResponsabilte" data-align="right">Indem de respons.</th>--%>
							<th data-field="mtindemniteRepresentation" data-align="right">Indem de repres.</th>
							<th data-field="mtindemniteTransport" data-align="right">Indem de transport.</th>


								<c:forEach items="${listePrimesNonImpos}" var="rubrique" >
									<th  data-field="primes${rubrique.id}" data-rubrique="${rubrique.id}" data-formatter="primeFormatterP" data-align="right">${rubrique.libelle}</th>
								</c:forEach>
								<c:forEach items="${listePrimesImposetNon}" var="imposableetNON" >
									<th  data-field="rubriq${imposableetNON.id}" data-rubrique="${imposableetNON.id}" data-formatter="primeNonIMFormatter" data-align="right">${imposableetNON.libelle}</th>
								</c:forEach>
							<%--<th data-field="mtautreNonImposable" data-align="right">Autres Primes NonImp.</th>--%>
							<th data-field="mtbrutNonImposable" data-align="right">Brut Non Imp.</th>

							<th data-field="mtits" data-align="right">ITS</th>							
							<%--<th data-field="mtcn"  data-align="left" data-sortable="true">CN</th>--%>
							<%--<th data-field="mtigr"  data-align="left" data-sortable="true">IGR</th>--%>

							<th data-field="mttotalRetenueFiscale"  data-align="left">Retenue fiscale</th>
							<th data-field="mtbasecnps"  data-align="left">base Cnps</th>
							
							<th data-field="mtcnps"  data-align="center">CNPS</th>
							<c:forEach items="${listePrimesSociale}" var="rubrique" >
                            	<th  data-field="primeS${rubrique.id}" data-rubrique="${rubrique.id}" data-formatter="primeSFormatter" data-align="right">${rubrique.libelle}</th>
                            </c:forEach>
                            <th data-field="retenueSociiale"   data-align="left">Retenue Sociale</th>
							<th data-field="mtavceAcpte"   data-align="left">Avance & Acompte</th>
							<th data-field="mtpretAlios"  data-align="center">Pret</th>
							<%--<th data-field="mtcarec"  data-align="center">Carec</th>--%>

								<c:forEach items="${listePrimesMutuelle}" var="rubrique" >
								<th  data-field="primeM${rubrique.id}" data-rubrique="${rubrique.id}" data-formatter="primeMFormatter" data-align="right">${rubrique.libelle}</th>
							</c:forEach>
								<%--<th data-field="mtautrePrelevment"  data-align="center">Autre prelevements</th>--%>
							<th data-field="mttotalRetenue"  data-align="right">Total retenue</th>

								<c:forEach items="${listePrimesGains}" var="rubrique" >
									<th  data-field="primeG${rubrique.id}" data-rubrique="${rubrique.id}" data-formatter="primeGFormatter" data-align="right">${rubrique.libelle}</th>
								</c:forEach>
							<%--<th data-field="mtregularisation"  data-align="center">Regularisation</th>--%>
							<th data-field="mtnetPayer"  data-align="center">Net a payer</th>
							<th data-field="mttotalBrut"   data-align="left">Total brut</th>
							<th data-field="mtis"  data-align="center">IS</th>
							<th data-field="mtta"  data-align="center">TA</th>
							<th data-field="mtfpc"  data-align="right">FPC</th>
							<th data-field="mtfpcregul"  data-align="right">FPC REGUL</th>
							<th data-field="mtprestationFamiliale" data-align="right">Prest familiale</th>
							<th data-field="mtaccidentTravail" data-align="right">Acc de travail</th>
							<th data-field="mtretraite" data-align="right">Retraite</th>
							<th data-field="mttotalPatronal" data-align="right"> Total patronal</th>
							<th data-field="mttotalMasseSalariale" data-align="right">Total masse salariale</th>
						
							
							<!-- <th data-field="id" data-formatter="optionFormatter1" data-align="center">Options</th>  -->
							
						   </tr>

					</thead>
				</table>
					<%--<c:forEach items="${listePrimes}" var="rubrique" >--%>
						<%--${rubrique.libelle}--%>
					<%--</c:forEach>--%>
						</div>

                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                	<!-- <input type="hidden"  id="idPeriode" name="id"> -->
					<%--<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>--%>
                    <%--<button type="submit" onclick="genererLivreDePaie()" class="btn btn-success">Valider</button>--%>
                    
                </div>
            </form>
        </div>
    </div>
</div>



<!-- Modification info du personnel -->
<div class="modal fade" id="rhpModalModif" tabindex="-1" role="dialog" aria-labelledby="rhpModalModif" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formModificationPersonnel" ng-controller="modificationPersonnelCtrl" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">Modification du personnel (<span id="infoPersonnel">Information du personnel</span>)</h4>
				</div>
                <div class="modal-body">
                	<div class="form-group">
                        <label for="libelle" class="col-md-4 control-label">Nombre d'enfants</label>
                        <div class="col-md-8">
                            <input type="text" id="nombreenfant" name="nombreenfant" ng-model="personnel.nombrEnfant" class="form-control" placeholder="0" />
                        </div>
                    </div>
                	<div class="form-group">
                        <label for="salaireDeBase" class="col-md-4 control-label">Situation matrimoniale</label>
                        <div class="col-md-8">
                            <select id="situationmatrimoniale" name="situationmatrimoniale" class="form-control select2" ng-model="personnel.situationMatrimoniale">
								<option value="1">Mari&eacute;(e)</option>
								<option value="2">C&eacute;libataire</option>
								<option value="2">Veuf/Veuve</option>
							</select>
                        </div>
                    </div>
                	<div class="form-group">
                        <label for="statut" class="col-md-4 control-label">Statut</label>
                        <div class="col-md-8">
                            <select id="statut" name="statut" class="form-control select2" ng-model="personnel.statut">
								<option value="true">Actif</option>
								<option value="false">En sommeil</option>
							</select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="text" class="hidden" ng-show="false" id="idPersonnel" name="idPersonnel" ng-model="personnel.id">
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
                            <input type="text" id="jourTravail"  name="jourTravail" class="form-control" required="required" placeholder="30" />
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


<div class="modal fade" id="rhpModalCalculenvers"  role="dialog" aria-labelledby="rhpModalCalculenvers" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formCalculenvers" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">Calcul a l'envers du personnel (<span id="infoPersonnelmo1">Information du personnel</span>)</h4>
				</div>
                <div class="modal-body">
                	<div class="form-group">
                        <label for="libelle" class="col-md-4 control-label">Net a payer</label>
                        <div class="col-md-8">
                            <input type="text" id="netApayer"  name="netApayer" class="form-control" required="required" placeholder="Net a payer" />
                        </div>
                    </div>
                   	<div class="form-group">
                        <label for="libelle" class="col-md-4 control-label">Nouveau Sursalaire</label>
                        <div class="col-md-8">
                            <input type="text" id="sursal"  name="sursal" required="required" class="form-control" disabled="disabled"/>
                        </div>
                    </div> 
  
                </div>
                <div class="modal-footer">
                    <input type="hidden" value="" id="matriculec" name="matriculec">
                    <input type="hidden" value="" id="idpersc" name="idpersc">
                     <input type="hidden" value="" id="idperiodc" name="idperiodc">
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-info" id="enversCalc" >Calculer</button>
                    <button type="submit" class="btn btn-success" id="enversCalcValider" >Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>


<div class="modal deleteModal  fade bs-delete-modal-static" role="dialog" data-backdrop="static">
    <div class="modal-dialog ">
        <div class="modal-content">
	        <form id="formDelete"  action="#" method="post">
	            <div class="modal-header ">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	                <span class="circle bg-danger">
	                    <i class="fa fa-question-circle"></i>
	                    Etes vous sur de vouloir supprimer ?
	                </span>
	            </div>
	            <div class="modal-body">
	            	<h4 ng-bind="primePersonnel.info"></h4>
	            </div>
	            <div class="modal-footer">
                	<input type="text" class="hidden" ng-hide="true" value="" name="id" >
                	<span></span>&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
                </form>
        	</div>
        
    </div>
</div>
<div class="modal fade" id="rhpModalImprimer"  role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static" >
    <div class="modal-dialog" role="document" style="width:80%;" >
        <div class="modal-content">
            <form id="formImrimBull" class="form-horizontal" >
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Imprimer les bulletins de paie de  ${periode}</h4>

				</div>
                <div class="modal-body">
					<br>
 <button id="exportExcelBull" class="btn btn-success" style="margin-bottom: 10px;">
                                    Exporter en Excel
                                </button>
                    <div class="table-responsive" style="max-height: 500px; overflow-y: auto;">
				<table id="tableBull" class="table table-info table-striped"
					data-toggle="table" 
					data-click-to-select="true"
					data-single-select="false"					
					data-show-export="true" 
					data-click-to-select="true"
					data-show-footer="false"
					data-export-data-type="all"
					data-sort-name="name"
		        	data-url="${pageContext.request.contextPath}/paie/listbulletinMoisActif"
					data-sort-order="desc"
					data-show-columns="true" 
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[ 25, 50, 100, 200,500,1000]"
					data-search="true">
					<thead>
						<tr>
							<th data-field="contratPersonnel" data-formatter="matriculesFormatter"  data-align="left" data-sortable="true">Matricule</th>
							<th data-field="contratPersonnel" data-formatter="nomcompletFormatter" data-align="left" data-sortable="true">Nom  &   Prenoms  s</th>
							<th data-field="contratPersonnel" data-formatter="nomfstatutFormatter" data-align="left" data-sortable="true">Statut</th>
							<th data-field="nombrePart"  data-align="left">Nbre part</th>
							<th data-field="anciennete"  data-align="center">Anciennete</th>
							<th data-field="salairbase"   data-align="left" >Salaire Base</th>
							<th data-field="sursalaire"  data-align="center">Sursalaire</th>
							<th data-field=primeanciennete  data-align="center">Prime. Anciennete</th>
							<th data-field="indemnitelogement"  data-align="right">Indem logement</th>
							<th data-field="indemniteTransportImp" data-align="right">Indem de transport Imp.</th>
						   <th data-field="autreIndemImposable" data-align="right">Indem Imp.</th>
							<c:forEach items="${listePrimesImp}" var="rubrique" >
								<th  data-field="primeb${rubrique.id}" data-rubrique="${rubrique.id}" data-formatter="primeFormatterBull" data-align="right">${rubrique.libelle}</th>
							</c:forEach>
							<c:forEach items="${listePrimesImposetNon}" var="rubrique" >
								<th  data-field="primebI${rubrique.id}" data-rubrique="${rubrique.id}" data-formatter="primeFormatterBullI" data-align="right">${rubrique.libelle}</th>
							</c:forEach>
							<%--<th data-field="mtautreImposable" data-align="right">Autres Primes Imp.</th>--%>
							<th data-field="brutImposable" data-align="right">Brut imposable</th>
							<%--<th data-field="mtindemniteRespons" data-align="right">Indem de respons.</th>--%>
							<th data-field="indemniteRepresent" data-align="right">Indem de repres.</th>
							<th data-field="indemniteTransport" data-align="right">Indem transp </th>
							<%--<th data-field="mtautreNonImposable" data-align="right">Autres Primes Non Imp.</th>--%>
								<c:forEach items="${listePrimesNonImpos}" var="rubrique" >
									<th  data-field="primeb${rubrique.id}" data-rubrique="${rubrique.id}" data-formatter="primeFormatterBull" data-align="right">${rubrique.libelle}</th>
								</c:forEach>
								<c:forEach items="${listePrimesImposetNon}" var="imposableetNON" >
									<th  data-field="rubriqb${imposableetNON.id}" data-rubrique="${imposableetNON.id}" data-formatter="primeNonIMFormatterbull" data-align="right">${imposableetNON.libelle}</th>
								</c:forEach>
							<th data-field="brutNonImposable" data-align="right">Brut Non Imp.</th>
							<th data-field="its" data-align="right">ITS</th>
							<%--<th data-field="cn"  data-align="left" data-sortable="true">CN</th>--%>
							<%--<th data-field="igr"  data-align="left" data-sortable="true">IGR</th>--%>
							<th data-field="totalretenuefiscal"  data-align="left">Retenue fiscale</th>
							<th data-field="basecnps"  data-align="left">Base cnps</th>
							<th data-field="cnps"  data-align="center">CNPS</th>
							<c:forEach items="${listePrimesSociale}" var="rubrique" >
                             <th  data-field="primeb${rubrique.id}" data-rubrique="${rubrique.id}" data-formatter="primeFormatterBull" data-align="right">${rubrique.libelle}</th>
                            </c:forEach>
                            <th data-field="retenueSociiale"   data-align="left">Retenue Sociale</th>
							<th data-field="avanceetacompte"   data-align="left">Avance & Acompte</th>
							<th data-field="pretaloes"  data-align="center">Pret</th>
							<!-- <th data-field="montantcarec"  data-align="center">Carec</th> -->
								<c:forEach items="${listePrimesMutuelle}" var="rubrique" >
								<th  data-field="primeb${rubrique.id}" data-rubrique="${rubrique.id}" data-formatter="primeFormatterBull" data-align="right">${rubrique.libelle}</th>
							</c:forEach>
							<%--<th data-field="mtautrePresslevement"  data-align="center">Autre prelevement</th>--%>
							<th data-field="totalretenue"  data-align="right">Total retenue</th>
							<c:forEach items="${listePrimesGains}" var="rubrique" >
									<th  data-field="primeb${rubrique.id}" data-rubrique="${rubrique.id}" data-formatter="primeFormatterBull" data-align="right">${rubrique.libelle}</th>
								</c:forEach>
							<%--<th data-field="mtregularisation"  data-align="center">Regularisation</th>--%>
							
							<th data-field="netapayer"  data-align="center">Net a payer</th>
							<th data-field="totalbrut"   data-align="left">Total brut</th>
							<th data-field="its"  data-align="center">IS</th>
							<th data-field="ta"  data-align="center">TA</th>
							<th data-field="fpc"  data-align="right">FPC</th>
							<th data-field="prestationFamiliale" data-align="right">Prest familiale</th>
							<th data-field="accidentTravail" data-align="right">Acc de travail</th>
							<th data-field="retraite" data-align="right">Retraite</th>
							<th data-field="totalpatronal" data-align="right"> Total patronal</th>
							<th data-field="totalmassesalarial" data-align="right">Total masse salariale</th>
							 
							
							<th data-field="id" data-formatter="optionFormatter2" data-align="center">Options</th> 
							
						</tr>
					</thead>
				</table>

                    </div>
                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                	<!-- <input type="hidden"  id="idPeriode1" name="id"> --> 
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Imprimer</button>
                    
                </div>
            </form>
        </div>
    </div>
</div>


<script type="text/javascript">
//AngularJS
app.controller('modificationPersonnelCtrl', function ($scope) {
    $scope.populateForm = function (presonnel) {
        $scope.personnel = presonnel;
    };

 

});  
    /* .controller('formDeleteCtrl', function ($scope) {
    $scope.pupulateForm = function (fonction) {
    	$scope.fonction = fonction;
    };/* .controller('listMouvementCon�geCtrl', function ($scope) {
        $scope.mouvementCongeAction = false;
        $scope.populateForm = function (personnel) {
            $scope.mouvementCongeAction = personnel.nombreJourdu > 0 ? true : false;
            $scope.personnel = personnel;
        };  */
      /*    $scope.populateContrat = function (mouvementConge) {
            $scope.mouvementConge = mouvementConge;
        }; */


//End AngularJs
var contextPath = "<%=request.getContextPath() %>";
var actionUrl = "/paie/savebullPersonnel";
var action = "ajout";
var $table; var  $tableConge;
jQuery(document).ready(function($) {
	// $("#tableWidget .fixed-table-body").removeClass("fixed-table-body");
/* 	jQuery('#tableBull').DataTable(); */
	jQuery('#tableWidgethisto').hide();
	jQuery('#tableWidget').show();
	jQuery( ".select2" ).select2();
	var periode='';var periodeID='';
	chargerPeriodePaie()
	periode='${activeMois}';
	  $tableConge = $('#tableConge');
	periodeID='${activeMoisId}';
	jQuery('#idPeriode1').val(periodeID);
	jQuery('#idperiod').val(periodeID);
	$table = $('#table');
	$tablebull = $('#tableBull');
//	alert(periode);
	//alert(periodeID);
	jQuery('#periodePaie').html(periode);
	jQuery('#idPeriode').val(periodeID);
	//alert(jQuery('#idPeriode').val());
	//Envoi des donnees
	
/* 	 jQuery("#btnCancelMouvementConge, #listMouvementCongeModal button.close").click(function (e) {
		 jQuery(".form-mouvement-conge").hide(500);
    }); */
    
	
	jQuery("#jourTravail").blur(function(){
		var nbjou;
		nbjou = $("#jourTravail").val();
		jQuery("#temptravail").val((nbjou*173.33)/30);
		
	});
	function download(){
		
	 	var url = contexPath + jQuery("#valtab").val();
	 	window.open(url, '_blank');
	}
	jQuery("#temptravail").blur(function(){
		var nbheu;
		nbheu = $("#temptravail").val();
		jQuery("#jourTravail").val((nbheu*30)/173.33);
		
	});
	jQuery("#formModificationBulletin").submit(function(e){
		e.preventDefault();
        var formData = $(this).serialize();
        console.log("form", formData);
        jQuery.ajax({
            type: "POST",
            url: baseUrl + "/personnels/modifierbulletinpaie",
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == "succes") {
                	//MAJ de la ligne modifi�e
            		$table.bootstrapTable('updateRow', {
                        index: $table.find('tr.selected').data('index'),
                        row: reponse.data
                    });
            		jQuery("#formAjout")[0].reset(); //Initialisation du formulaire
            		jQuery("#rhpModal").modal('hide');
                }
                else if(reponse.result == "erreur_champ_obligatoire"){
                	alert("Saisie invalide");
                }
            },
            error: function () {
            	jQuery("#rhpModalModif .modal-body div.alert").alert();
            	jQuery("#rhpModalModif .modal-body .alert h4").html("Erreur survenue");
            	jQuery("#rhpModalModif .modal-body .alert p").html("Verifier que vous etes connectes au serveur.");
            	jQuery("#rhpModalModif .modal-footer span").removeClass('loader');
            },
            beforeSend: function () {
            	jQuery("#formModificationPersonnel").attr("disabled", true);
            	jQuery("#rhpModalModif .modal-footer span").addClass('loader');
            },
            complete: function () {
            	jQuery("#formModificationPersonnel").removeAttr("disabled");
            	jQuery("#rhpModalModif .modal-footer span").removeClass('loader');
            }
        });
	});
	    $('#exportExcelBull').click(function () {
                $('#tableBull').tableExport({
                    type: 'excel',
                    fileName: 'export_bulletinpaie',
                    exportDataType: 'all' // 'all', 'selected' ou 'basic'
                });
            });

        $('#exportExcelLiv').click(function () {
                        $('#tableliv').tableExport({
                            type: 'excel',
                            fileName: 'export_livrepaie',
                            exportDataType: 'all' // 'all', 'selected' ou 'basic'
                        });
        });
/* 	jQuery("tableBull").tableExport({
	    headings: true,                    // (Boolean), display table headings (th/td elements) in the <thead>
	    footers: true,                     // (Boolean), display table footers (th/td elements) in the <tfoot>
	    formats: ["xls", "csv", "txt"],    // (String[]), filetypes for the export
	    fileName: "id",                    // (id, String), filename for the downloaded file
	    bootstrap: false,                   // (Boolean), style buttons using bootstrap
	    position: "bottom",                 // (top, bottom), position of the caption element relative to table
	    ignoreRows: null,                  // (Number, Number[]), row indices to exclude from the exported file
	    ignoreCols: null  ,                 // (Number, Number[]), column indices to exclude from the exported file
	    ignoreCSS: ".tableexport-ignore"   // (selector, selector[]), selector(s) to exclude from the exported file
	});
	 */
	jQuery("#formImrimBull").submit(function(e){
//	alert(id);'${activeMoisId}';
		var id='${activeMoisId}';
		location.href = baseUrl +  "/paie/bulletinMoisPersonnel?idbul="+id; 

	});
	function chargerPeriodePaie(){
		jQuery.ajax({
	        type: "POST",
	        url: baseUrl + "/parametrages/periodeall",
	        cache: false,
	        success: function (response) {
	        	if (response != null) {
	        		tabledata = '<option value="0" data-libelle="CHOISIR PERIODE PAIE" >CHOISIR PERIODE PAIE</option>';
	        		for (i = 0; i < response.length; i++) {
						tabledata += '<option value="'+response[i].id+'" data-libelle="'+response[i].mois.mois + ' ' + response[i].annee.annee+'" >' + response[i].mois.mois + ' ' + response[i].annee.annee + '</option>';
					}
					jQuery('#periodePaieImpression').html(tabledata);
					jQuery('#periodePaieImpression').select2('val', 0);
					//jQuery('#branch').select2('val', index);
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
	
	jQuery("#formModificationPersonnel").submit(function(e){
		e.preventDefault();
        var formData = $(this).serialize();
        console.log("form", formData);
        jQuery.ajax({
            type: "POST",
            url: baseUrl + "/personnels/editerpersonnel",
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == "succes") {
                	//MAJ de la ligne modifi�e
            		$table.bootstrapTable('updateRow', {
                        index: $table.find('tr.selected').data('index'),
                        row: reponse.data
                    });
            		jQuery("#formModificationPersonnel")[0].reset(); //Initialisation du formulaire
            		jQuery("#rhpModalModif").modal('hide');
                }
                else if(reponse.result == "erreur_champ_obligatoire"){
                	alert("Saisie invalide");
                }
            },
            error: function () {
            	jQuery("#rhpModalModif .modal-body div.alert").alert();
            	jQuery("#rhpModalModif .modal-body .alert h4").html("Erreur survenue");
            	jQuery("#rhpModalModif .modal-body .alert p").html("Verifier que vous etes connectes au serveur.");
            	jQuery("#rhpModalModif .modal-footer span").removeClass('loader');
            },
            beforeSend: function () {
            	jQuery("#formModificationPersonnel").attr("disabled", true);
            	jQuery("#rhpModalModif .modal-footer span").addClass('loader');
            },
            complete: function () {
            	jQuery("#formModificationPersonnel").removeAttr("disabled");
            	jQuery("#rhpModalModif .modal-footer span").removeClass('loader');
            	$table.bootstrapTable('refresh', { url: baseUrl +'/contratpersonnel/listcontratpersonnelActifjson' });
            	location.reload();
            	
            }
        });
	});

});

jQuery("#formMouvementConge").submit(function (e) {
    e.preventDefault();
    var formData = jQuery(this).serialize();
    jQuery.ajax({
        type: "POST",
        url: baseUrl + "/paie/saveprimepersonnel",
        cache: false,
        data: formData,
        success: function (reponse) {
            if (reponse.result == true) {
                $tableConge.bootstrapTable('prepend', reponse.row);
               // jQuery("#btnAddMouvementConge, .form-mouvement-conge").hide(500);
            } else if (reponse.result == false) {
                alert("Saisie invalide");
            }
        },
        beforeSend: function () {
        	//jQuery("#formMouvementConge").attr("disabled", true);
        	//jQuery("#actionMouvementConge span").addClass('loader');
        },
        error: function () {
        	jQuery("#actionMouvementConge span").removeClass('loader');
        },
        complete: function () {
        	//jQuery("#formMouvementConge").removeAttr("disabled");
        	//jQuery("#actionMouvementConge span").removeClass('loader');
        }
    });
    return false;
});

jQuery("#formGenerer").submit(function(e){
	//alert('bonjour');
	e.preventDefault();
	periodeID='${activeMoisId}';
	jQuery('#idPeriode').val(periodeID);
	jQuery('#idPeriode1').val(periodeID);
	jQuery('#idperiod').val(periodeID);

	jQuery.ajax({
        type: "GET",
        url: contextPath + "/paie/savebullPersonnel",
        cache: false,
        data: {id: '${activeMoisId}'},
        success: function (response) {
            if (response.result == "success") {
      
            	jQuery("#rhpModal").modal('hide');            	
            	jQuery("#rhpModalValider").modal();
            //	jQuery ('#tableliv').bootstrapTable ('refresh', {  url: baseUrl +'/paie/savebullPersonnel?id='+ jQuery('#idPeriode').val()});
            }
            else if(response.result == "erreur_champ_obligatoire"){
            	alert("Saisie invalide");
            }
        },
        error: function () {
        	jQuery("#rhpModal .modal-body div.alert").alert();
        	jQuery("#rhpModal .modal-body .alert h4").html("Erreur survenue");
        	jQuery("#rhpModal .modal-body .alert p").html("Verifier que vous etes connectes au serveur.");
        	jQuery("#rhpModal .modal-footer span").removeClass('loader');
        },
        beforeSend: function () {
        	jQuery("#formAjout").attr("disabled", true);
        	jQuery("#rhpModal .modal-footer span").addClass('loader');
        },
        complete: function () {
        	jQuery("#formGenerer").removeAttr("disabled");
        	jQuery("#rhpModal .modal-footer span").removeClass('loader');
       	jQuery('#tableliv').bootstrapTable('refresh', {  url: baseUrl +'/paie/savebullPersonnel?id='+ jQuery('#idPeriode').val()});
       //	location.reload();
   
        }
    });
});

jQuery("#formGenererVirmt").submit(function(e){
	
	// 
	 
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/paie/postVirementBulletin?banque="+jQuery('#idbank').val()+"&banqueEmp="+jQuery('#idbankEnt').val(),
        cache: false,
		success: function (response) {
        	
			jQuery("#valtab").val(response.message);
		
        },
        error: function () {
            
        },
        complete: function () {
        	window.open(baseUrl +jQuery('#valtab').val());
        
        }
    });
	 
	 
});

function listMouvementConge(idpm,idPersonnel) {
	jQuery('#lpom').val(idpm);

    loadMouvementCongeByPersonnel(idPersonnel);
    loadContratPersonnel(idpm);
    jQuery('#idPersonnelp').val(idpm);
    jQuery('#lpom').val(idpm);
    console.log(idpm);
 
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
            	  jQuery('#idPersonnelp').val(reponse.row.id);	
            	  jQuery('#lpom').val(reponse.row.id);
            	  
            //}
            //	if(reponse.rows>0){

			//	$t  jQuery("#idPersonnelp").val(idpm);ableConge.bootstrapTable('load', reponse.rows);
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
    
    
function genererLivreDePaie(){

	jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/savelivrepaie",
        cache: false,
		success: function (response) {
        	if (response != null) {
        		//alert(response.result);
        		jQuery("#rhpModalValider").modal('hide');
        		$tablebull.bootstrapTable('refresh', { url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
			} else {
				alert('Impossible de charger cet objet');
			}
        },
        error: function () {
            
        },
        complete: function () {
       	$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
     //   location.reload();
        }
    });
}
function chargerBull(){
	var periodeID='';
	periodeID='${activeMoisId}';
	// location.reload();
	//alert(periodeID);
	$tablebull = jQuery('#tableBull');
	$tablebull.bootstrapTable('refresh', { url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
}

function genererExcel(){

	jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/savelivrepaie",
        cache: false,
		success: function (response) {
        	if (response != null) {
        		//alert(response.result);
        		jQuery("#rhpModalValider").modal('hide');
        		$tablebull.bootstrapTable('refresh', { url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
			} else {
				alert('Impossible de charger cet objet');
			}
        },
        error: function () {
            
        },
        complete: function () {
       	$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
     //   location.reload();
        }
    });
}
function optionFormatter(id, row, index) {
	var option = '<a onclick="selectPersInfo('+id+','+row.personnel.id+')" data-toggle="modal" data-target="#rhpModalModif" href="#" title="Modifier personnel [LIBELLE : '+row.personnel.matricule+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	option += '<a onclick="cherch2('+row.personnel.id+')" data-toggle="modal" data-target="#rhpModalModiftemps" href="#" title="Modifier personnel [LIBELLE : '+row.matricule+' ]">  <span class="glyphicon glyphicon-list"></span></a>&nbsp;';
	option += '&nbsp;<a onclick="cherch('+row.personnel.id+')" data-toggle="modal" data-target="#rhpModalCalculenvers" href="#" title="Calcul a lenvers bulletin [LIBELLE : '+row.libelle+' ]"> <span class="glyphicon glyphicon-pencil"></span></a>';
	/* option += '<a onclick="listMouvementConge('+id+','+row.personnel.id+')" data-toggle="modal" data-target="#listPrimesDiversModal" href="#" title="Modifier personnel [LIBELLE : '+row.matricule+' ]">  <span class="glyphicon glyphicon-briefcase"></span></a>&nbsp;'; */
	
    return option;
}


/* function optionFormatterPrime(id, row, index) {
	var option = '<a onclick="cherchprime(\''+row.id+'\')"  href="#" title="Modifier prime [LIBELLE : '+row.prime.libelle+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	 option += '&nbsp;<a  data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer prime [LIBELLE : '+row.prime.id+' ]"> <span class="glyphicon glyphicon-trash"></span></a>'; 
	
    return option;
} */


function nomImpFormatter(listIndemniteBrut, row, index) {
if(row.listIndemniteBrut.size > 0){
        return "";
    }

}
//function primeFormatter(prime, row, index) {
//	if(row.prime.id == ''){
//		return "";
//	}
//	return row.prime.libelle;
//}
 function optionFormatter1(id, row, index) {
	//var option = '<a onclick="cherch2(\''+row.matricule+'\')" data-toggle="modal" data-target="#rhpModalModiftemps" href="#" title="Modifier personnel [LIBELLE : '+row.matricule+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	/* option += '&nbsp;<a data-toggle="modal" href="#" title="Suprimer bulletin [LIBELLE : '+row.salaireBase+' ]"> <span class="glyphicon glyphicon-trash"></span></a>'; */
	
    return option;
} 

function optionFormatter2(id, row, index) {
	var option = '<a   target="_blank" href="${pageContext.request.contextPath}/paie/jrBulletinPersonnel?idbul='+row.id+'" title="Modifier Personnel [NOM : '+row.contratPersonnel.personnel.nom+' '+row.contratPersonnel.personnel.prenom+' ]"><span class="glyphicon glyphicon-print"></span> </a>&nbsp;';
	//option += '&nbsp;<a data-toggle="modal" href="#" title="Suprimer bulletin [LIBELLE : '+row.salaireBase+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';
	
    return option;
} 

function optionFormatterbull(id, row, index) {
	var option = '<a   target="_blank" href="${pageContext.request.contextPath}/paie/jrBulletinPersonnel?idbul='+row.id+'" title="Modifier Personnel [NOM : '+row.contratPersonnel.personnel.nom+' '+row.contratPersonnel.personnel.prenom+' ]"><span class="glyphicon glyphicon-print"></span> </a>&nbsp;';
	//option += '&nbsp;<a data-toggle="modal" href="#" title="Suprimer bulletin [LIBELLE : '+row.salaireBase+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';
	
    return option;
} 
function matriFormatter(personnel, row, index) {
	if(row.personnel.matricule == ''){
		return "";
	}
	return row.personnel.matricule;
}

function primeNonIMFormatter(idRub,row,index){
    var $rubrique = $(this)[0];
    var idSpanPrime1 = 'rubriq'+ row.contratPersonnel.id+$rubrique.rubrique;
    $("#"+idSpanPrime1).html(0);
    //Recuperer via ajax la prime du personnel
    jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/primeIndividuel",
        data: {idPrime: $rubrique.rubrique,idPeriode:periodeID,idCtrat:row.contratPersonnel.id},
        cache: false,
        success: function (response) {
            if (response != null) {
                for (i = 0; i < response.length; i++) {
                    console.log(response[i].prime.mtExedent);
                    if(response[i].prime.etatImposition==3){
                        $("#"+idSpanPrime1).html(response[i].prime.mtExedent);
                    }
//                   else
//                        $("#"+idSpanPrime).html(response[i].montant);

				}

               
            } else {
                alert('Impossible de charger cet objet');
            }
        },
        error: function () {

        },
        complete: function () {
            //$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
            //   location.reload();
        }
    });
    return '<span id="'+idSpanPrime1+'"></span>';
}

function primeNonIMFormatterbull(idRub,row,index){
    var $rubrique = $(this)[0];
    var periodeID='';
    periodeID= '${activeMoisId}';
    var idSpanPrime1b = 'rubriqb'+ row.contratPersonnel.id+$rubrique.rubrique;
    $("#"+idSpanPrime1b).html(0);
    //Recuperer via ajax la prime du personnel
    jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/primeIndividuel",
        data: {idPrime: $rubrique.rubrique,idPeriode:periodeID,idCtrat:row.contratPersonnel.id},
        cache: false,
        success: function (response) {
            if (response != null) {
                for (i = 0; i < response.length; i++) {
                    console.log(response[i].prime.mtExedent);
                    if(response[i].prime.etatImposition==3){
                        $("#"+idSpanPrime1b).html(response[i].prime.mtExedent);
                    }
//                   else
//                        $("#"+idSpanPrime).html(response[i].montant);

				}

               
            } else {
                alert('Impossible de charger cet objet');
            }
        },
        error: function () {

        },
        complete: function () {
            //$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
            //   location.reload();
        }
    });
    return '<span id="'+idSpanPrime1b+'"></span>';
}
function primeFormatterI(idRub,row,index){
    var $rubrique = $(this)[0];
    var idSpanPrimeI = 'primeI'+ row.contratPersonnel.id+$rubrique.rubrique;
    $("#"+idSpanPrimeI).html(0);
    //alert('bbbbbbbb');
    //Recuperer via ajax la prime du personnel
    jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/primeIndividuel",
        data: {idPrime: $rubrique.rubrique,idPeriode:periodeID,idCtrat:row.contratPersonnel.id},
        cache: false,
        success: function (response) {
            if (response != null) {
                for (i = 0; i < response.length; i++) {

                    $("#"+idSpanPrimeI).html(response[i].montant-response[i].prime.mtExedent);
                    //}
                }

                //$("#"+idSpanPrime).html(response.montant);
                //jQuery('#sursal').val(response.sursalaire);
//                jQuery("#rhpModalCalculenvers").modal('hide');
                //$tablebull.bootstrapTable('refresh', { url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
            } else {
                alert('Impossible de charger cet objet');
            }
        },
        error: function () {

        },
        complete: function () {
            //$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
            //   location.reload();
        }
    });
    return '<span id="'+idSpanPrimeI+'"></span>';
}
function primeGFormatter(idRub,row,index){
    var $rubrique = $(this)[0];
    var idSpanPrimeG = 'primeG'+ row.contratPersonnel.id+$rubrique.rubrique;
    $("#"+idSpanPrimeG).html(0);
    //alert('bbbbbbbb');
    //Recuperer via ajax la prime du personnel
    jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/primeIndividuel",
        data: {idPrime: $rubrique.rubrique,idPeriode:periodeID,idCtrat:row.contratPersonnel.id},
        cache: false,
        success: function (response) {
            if (response != null) {
                for (i = 0; i < response.length; i++) {

                    $("#"+idSpanPrimeG).html(response[i].montant);
                    //}
                }

                //$("#"+idSpanPrime).html(response.montant);
                //jQuery('#sursal').val(response.sursalaire);
//                jQuery("#rhpModalCalculenvers").modal('hide');
                //$tablebull.bootstrapTable('refresh', { url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
            } else {
                alert('Impossible de charger cet objet');
            }
        },
        error: function () {

        },
        complete: function () {
            //$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
            //   location.reload();
        }
    });
    return '<span id="'+idSpanPrimeG+'"></span>';
}

function primeSFormatter(idRub,row,index){
    var $rubrique = $(this)[0];
    var idSpanPrimeS = 'primeS'+ row.contratPersonnel.id+$rubrique.rubrique;
    $("#"+idSpanPrimeS).html(0);
    //alert('bbbbbbbb');
    //Recuperer via ajax la prime du personnel
    jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/primeIndividuel",
        data: {idPrime: $rubrique.rubrique,idPeriode:periodeID,idCtrat:row.contratPersonnel.id},
        cache: false,
        success: function (response) {
            if (response != null) {
                for (i = 0; i < response.length; i++) {
                    console.log(response[i].montant);
                    console.log(response[i].prime.libelle);
//                        if(response[i].montant=null)
//                        $("#"+idSpanPrimeS).html(0);
//                        else
                        $("#"+idSpanPrimeS).html(response[i].montant);
                    //}
                }

                //$("#"+idSpanPrime).html(response.montant);
                //jQuery('#sursal').val(response.sursalaire);
//                jQuery("#rhpModalCalculenvers").modal('hide');
                //$tablebull.bootstrapTable('refresh', { url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
            } else {
                alert('Impossible de charger cet objet');
            }
        },
        error: function () {

        },
        complete: function () {
            //$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
            //   location.reload();
        }
    });
    return '<span id="'+idSpanPrimeS+'"></span>';
}


function primeMFormatter(idRub,row,index){
    var $rubrique = $(this)[0];
    var idSpanPrimeM = 'primeM'+ row.contratPersonnel.id+$rubrique.rubrique;
    $("#"+idSpanPrimeM).html(0);
    //alert('bbbbbbbb');
    //Recuperer via ajax la prime du personnel
    jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/primeIndividuel",
        data: {idPrime: $rubrique.rubrique,idPeriode:periodeID,idCtrat:row.contratPersonnel.id},
        cache: false,
        success: function (response) {
            if (response != null) {
                for (i = 0; i < response.length; i++) {
                    console.log(response[i].montant);
                    console.log(response[i].prime.libelle);
//                        if(response[i].montant=null)
//                        $("#"+idSpanPrimeM).html(0);
//                        else
                        $("#"+idSpanPrimeM).html(response[i].montant);
                    //}
                }

                //$("#"+idSpanPrime).html(response.montant);
                //jQuery('#sursal').val(response.sursalaire);
//                jQuery("#rhpModalCalculenvers").modal('hide');
                //$tablebull.bootstrapTable('refresh', { url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
            } else {
                alert('Impossible de charger cet objet');
            }
        },
        error: function () {

        },
        complete: function () {
            //$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
            //   location.reload();
        }
    });
    return '<span id="'+idSpanPrimeM+'"></span>';
}
	function primeFormatterP(idRub,row,index){
    var $rubrique = $(this)[0];
    var idSpanPrimeO = 'primes'+ row.contratPersonnel.id+$rubrique.rubrique;
    $("#"+idSpanPrimeO).html(0);
    //alert('bbbbbbbb');
    //Recuperer via ajax la prime du personnel
    jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/primeIndividuel",
        data: {idPrime: $rubrique.rubrique,idPeriode:periodeID,idCtrat:row.contratPersonnel.id},
        cache: false,
        success: function (response) {
            if (response != null) {
                for (i = 0; i < response.length; i++) {
                    console.log(response[i].montant);
                    console.log(response[i].prime.libelle);
//                        if(response[i].montant=null)
//                        $("#"+idSpanPrimeM).html(0);
//                        else
                    $("#"+idSpanPrimeO).html(response[i].montant);
                    //}
                }

                //$("#"+idSpanPrime).html(response.montant);
                //jQuery('#sursal').val(response.sursalaire);
//                jQuery("#rhpModalCalculenvers").modal('hide');
                //$tablebull.bootstrapTable('refresh', { url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
            } else {
                alert('Impossible de charger cet objet');
            }
        },
        error: function () {

        },
        complete: function () {
            //$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
            //   location.reload();
        }
    });
    return '<span id="'+idSpanPrimeO+'"></span>';
}
function primeFormatter(idRub,row,index){
    var $rubrique = $(this)[0];
    var idSpanPrime = 'prime'+ row.contratPersonnel.id+$rubrique.rubrique;
    $("#"+idSpanPrime).html(0);
    //Recuperer via ajax la prime du personnel
    jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/primeIndividuel",
        data: {idPrime: $rubrique.rubrique,idPeriode:periodeID,idCtrat:row.contratPersonnel.id},
        cache: false,
        success: function (response) {
            if (response != null) {
                for (i = 0; i < response.length; i++) {
                    //console.log(response[i].montant);
                    if(response[i].prime.etatImposition==3){
                         if(response[i].montant-response[i].prime.mtExedent==null)
//                        $("#"+idSpanPrime).html(0);
//                         else
                        $("#"+idSpanPrime).html(response[i].montant-response[i].prime.mtExedent);
                    }
                    else{
//                        if(response[i].montant=null)
//                        $("#"+idSpanPrime).html(0);
//                        else
                        $("#"+idSpanPrime).html(response[i].montant);
                    }
                }

                //$("#"+idSpanPrime).html(response.montant);
                //jQuery('#sursal').val(response.sursalaire);
//                jQuery("#rhpModalCalculenvers").modal('hide');
                //$tablebull.bootstrapTable('refresh', { url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
            } else {
                alert('Impossible de charger cet objet');
            }
        },
        error: function () {

        },
        complete: function () {
            //$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
            //   location.reload();
        }
    });
    return '<span id="'+idSpanPrime+'"></span>';
}
function primeFormatterBullI(idRub,row,index){

    var $rubrique = $(this)[0];
    var periodeID='';
    periodeID= '${activeMoisId}';
    var idSpanPrimebullI = 'primebI'+ row.contratPersonnel.id+$rubrique.rubrique;
    $("#"+idSpanPrimebullI).html(0);
    //Recuperer via ajax la prime du personnel
    jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/primeIndividuel",
        data: {idPrime: $rubrique.rubrique,idPeriode:periodeID,idCtrat:row.contratPersonnel.id},
        cache: false,
        success: function (response) {
            if (response != null) {
                for (i = 0; i < response.length; i++) {

                            $("#"+idSpanPrimebullI).html(response[i].montant-response[i].prime.mtExedent);
//
                }

            } else {
                alert('Impossible de charger cet objet');
            }
        },
        error: function () {

        },
        complete: function () {

        }
    });
    return '<span id="'+idSpanPrimebullI+'"></span>';
}
function primeFormatterBull(idRub,row,index){

    var $rubrique = $(this)[0];
    var periodeID='';
    periodeID= '${activeMoisId}';

    var idSpanPrimebull = 'primeb'+ row.contratPersonnel.id+$rubrique.rubrique;
    $("#"+idSpanPrimebull).html(0);
    //Recuperer via ajax la prime du personnel
    jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/primeIndividuel",
        data: {idPrime: $rubrique.rubrique,idPeriode:periodeID,idCtrat:row.contratPersonnel.id},
        cache: false,
        success: function (response) {
            if (response != null) {
                for (i = 0; i < response.length; i++) {
                    console.log(response[i].montant);
                    if(response[i].prime.etatImposition==3){
                         if(response[i].montant-response[i].prime.mtExedent==null)
//
                        $("#"+idSpanPrimebull).html(response[i].montant-response[i].prime.mtExedent);
                    }
                    else{

                        $("#"+idSpanPrimebull).html(response[i].montant);
                    }
                }

            } else {
                alert('Impossible de charger cet objet');
            }
        },
        error: function () {

        },
        complete: function () {
       
        }
    });
    return '<span id="'+idSpanPrimebull+'"></span>';
}
function nomFormatter(personnel, row, index) {
	if(row.personnel.nom == ''){
		return "";
	}
	return row.personnel.nom+" "+row.personnel.prenom;
}

function nomstatutFormatter(contratPersonnel, row, index) {
    if(row.contratPersonnel== ''){
        return "";
    }
    if(row.contratPersonnel.personnel.carec==false){
        if(row.contratPersonnel.personnel.stage==true)
            statfonct = "Stagiaire";
        else
            statfonct = "Consultant";
    }else{
        statfonct = "Contractuel";
    }
    return statfonct;
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
function nomfstatutFormatter(contratPersonnel, row, index) {
    if(row.contratPersonnel== ''){
        return "";
    }
    if(row.contratPersonnel.personnel.carec==false){

        if(row.contratPersonnel.personnel.stage==true)
            statfonct = "Stagiaire";
        else
            statfonct = "Consultant";
    }else{
        statfonct = "Contractuel";
    }
    return statfonct;
    //return row.contratPersonnel.personnel.statfonct;
}
function nomfstatutFormatterp(contratPersonnel, row, index) {
    if(row.contratPersonnel== ''){
        return "";
    }
    if(row.contratPersonnel.personnel.carec==false){

        if(row.contratPersonnel.personnel.stage==true)
            statfonct = "Stagiaire";
        else
            statfonct = "Consultant";
    }else{
        statfonct = "Contractuel";
    }
    return statfonct;
    //return row.contratPersonnel.personnel.statfonct;
}

function totalSalbaseFormatter(data) {
	var field = this.field;
	
    var total_sum = data.reduce(function(sum, row) {
            console.log(parseFloat(row[field]));
        return (sum) + (parseFloat(row[field]) || 0);
    }, 0);
    return total_sum.toLocaleString('fr-FR');
	 
	
}

function sommeilFormatter(personnel, row, index) {
	var optionActif = '<small class="label label-danger"><i class="fa fa-clock-o"></i> -- Desactiver </small>';
	if(row.personnel.statut == true)
		optionActif = '<small class="label label-success"><i class="fa fa-clock-o"></i> -- Activer </small>';
	
	return optionActif;
}
function salairbaseFormatter(value) {
	
	return value.toLocaleString('fr-FR');
}

jQuery("#enversCalc").click(function(){
 var idFonction=jQuery('#idpersc').val();
 var netpay=jQuery('#netApayer').val();
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/paie/calculalenvers",
        data: {idPersonnel: idFonction,netApayer:netpay},
        cache: false,
		success: function (response) {
        	if (response != null) {
        		console.log(response);
        		jQuery('#sursal').val(response.sursalaire);
        		//jQuery("#rhpModalValider").modal('hide');
        		//$tablebull.bootstrapTable('refresh', { url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
			} else {
				alert('Impossible de charger cet objet');
			}
        },
        error: function () {
            
        },
        complete: function () {
       	//$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
     //   location.reload();
        }
    });
	
});



jQuery("#enversCalcValider").click(function(){
	 var idFonction=jQuery('#idpersc').val();
	 var netpay=jQuery('#sursal').val();
		jQuery.ajax({
	        type: "POST",
	        url: baseUrl+"/personnels/updatecontratpersonnelSursal",
	        data: {idPersonnel: idFonction,sursalaire:netpay},
	        cache: false,
			success: function (response) {
	        	if (response != null) {
	        		console.log(response);
	        		//jQuery('#sursal').val(response.sursalaire);
	        		jQuery("#rhpModalCalculenvers").modal('hide');
	        		//$tablebull.bootstrapTable('refresh', { url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
				} else {
					alert('Impossible de charger cet objet');
				}
	        },
	        error: function () {
	            
	        },
	        complete: function () {
	       	//$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
	     //   location.reload();
	        }
	    });
		
	});


jQuery("#btnGenererNet").click(function(){
	var idFonction=jQuery('#idpersc').val();
	// var netpay=jQuery('#sursal').val();//  data: {idPersonnel: idFonction,sursa},
		jQuery.ajax({
	        type: "POST",
	        url: baseUrl+"/paie/calculalenvers-liste",
	        data: {id: idFonction},
	        cache: false,
			success: function (response) {
	        	if (response != null) {
	        		console.log(response);
	        		//jQuery('#sursal').val(response.sursalaire);
	        		jQuery("#rhpModalCalculenvers").modal('hide');
	        		//$tablebull.bootstrapTable('refresh', { url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
				} else {
					alert('Impossible de charger cet objet');
				}
	        },
	        error: function () {

	        },
	        complete: function () {
	       	//$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
	     //   location.reload();
	        }
	    });

	});


function matriculesFormatter(contratPersonnel, row, index) {
	if(row.contratPersonnel == ''){
		return "";
	}
	return row.contratPersonnel.personnel.matricule;
}

function nomcompletFormatter(contratPersonnel, row, index) {
	if(row.contratPersonnel== ''){
		return "";
	}
	return row.contratPersonnel.personnel.nom+" "+row.contratPersonnel.personnel.prenom;
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


function selectPersInfo(idContrat, idPersonnel){
        var $scope = angular.element(document.getElementById("formModificationPersonnel")).scope();
        var contrat = _.findWhere($table.bootstrapTable('getData'), {id: idContrat});
        console.log("Personnel", contrat.personnel);
        $scope.$apply(function () {
            $scope.populateForm(contrat.personnel);
        });
        
        jQuery("#situationmatrimoniale").select2("val", contrat.personnel.situationMatrimoniale);
        var status = contrat.personnel.statut === true ? "true" : "false";
        jQuery("#statut").select2("val", status);
}
function cherch(idFonction){
	jQuery('#infoPersonnelmo1').html(idFonction);
	jQuery('#idPersonnel').val(idFonction);
	jQuery('#idpersc').val(idFonction);
	jQuery.ajax({
        type: "GET",
        url: baseUrl+"/personnels/cherchpersonnel",
        data: {idPersonnel: idFonction},
        cache: false,
		success: function (response) {
        	if (response != null) {
        	//	alert(response.result);
        		jQuery('#idPersonnel').val(response.id);
        	
        		//alert(	jQuery('#idpersc').val());
        		jQuery('#nombreenfant').val(response.nombrEnfant);
        		jQuery('#situationmatrimoniale').val(response.situationMatrimoniale).trigger('change');
        		jQuery('#statut').val(response.enSommeil).trigger('change');
        	
			} else {
				alert('Impossible de charger cet objet');
			}
        },
        error: function () {
            
        },
        complete: function () {
        //	jQuery("#formGenerer").removeAttr("disabled");
        //	jQuery("#rhpModal .modal-footer span").removeClass('loader');
    //   	$tablebull.bootstrapTable('refresh', {url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ jQuery('#idPeriode').val()});
        }
    });
	jQuery('#idPersonnel').val(idFonction);

}

function cherchprime(idFonction){
	
//	jQuery('#infoPersonnelmo1').html(idFonction);
//	jQuery('#idPersonnel').val(idFonction);
//	jQuery('#idpersc').val(idFonction);
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/paie/searchprimeIndividuel1",
        data: {id: idFonction},
        cache: false,
		success: function (response) {
        	if (response != null) {
        	console.log(response);
        		
        	loadDataToForm(response.id,response.montant,response.valeur,response.prime.id);
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

function loadDataToForm(id, montant, valeur,idPrime){
	jQuery("#id").val(id);

	
	if(idPrime == null){
		jQuery("#primePop").trigger('liszt:updated');
	  	jQuery("#primePop option:selected").val()
	} else{
		jQuery("#primePop").val(idPrime);
		jQuery("#primePop").val(idPrime).trigger('change');
		jQuery("#primePop").trigger('liszt:updated');
	}
	if(montant==null){
		jQuery("#montant").val(0);
	}else{
		jQuery("#montant").val(montant);
	}
	
	jQuery("#valeur").val(valeur);
	//alert(jQuery('#montant').val());
}


jQuery("#formModificationPersonneltemps").submit(function(e){	
	e.preventDefault();
	//alert();
   // var formData = jQuery(this).serialize();
    //console.log("form", formData);
    jQuery.ajax({
        type: "POST",
        url: baseUrl + "/paie/savetempeffectif",
        cache: false,
        data: { temptravail:jQuery('#temptravail').val(),
        	   jourtravail:jQuery('#jourTravail').val(),
        	   idPers:jQuery('#idpers').val(),
              idPeriodDep:jQuery('#idperiod').val()},
        success: function (reponse) {
            if (reponse.result == "success") {
            	//$table.bootstrapTable('refresh');
            	jQuery("#formModificationPersonneltemps")[0].reset(); //Initialisation du formulaire
            	jQuery("#rhpModalModiftemps").modal('hide');
        		alert('Operation effexctuee avec succes');
            }
            else if(reponse.result == "erreur_champ_obligatoire"){
            	alert("Saisie invalide");
            }
        },
        error: function () {
        	/* jQuery("#rhpModalPret div.alert").alert();
        	jQuery("#rhpModalPret .alert h4").html("Erreur survenue");
        	jQuery("#rhpModalPret .alert p").html("Verifier que vous �tes connect�s au serveur."); */
        	//$("#formPret span.load").removeClass('loader');
        },
        beforeSend: function () {
        	/* jQuery("#formPret").attr("disabled", true);
            jQuery("#formPret span.load").addClass('loader'); */
        },
        complete: function () {
        /* 	jQuery("#formPret").removeAttr("disabled");
            //$("#formPret span.load").removeClass('loader');*/
            $table.bootstrapTable ('refresh', {  url: baseUrl +'/personnels/listcontratpersonnelActifjson' }); 
        }
    });

});
function cherch2(matricule){
// alert(idFonction);
	jQuery('#matricule').val(matricule);
	
	jQuery('#infoPersonnelmo').html(matricule);
	jQuery('#idpers').val(matricule);

	jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/cherchtempeffectif",
        data: {idPersonnel: jQuery('#idpers').val(),
        	idPeriodDep:jQuery('#idperiod').val()},
        cache: false,
		success: function (response) {
        	if (response != null) {
        		//alert(response.result);
        		jQuery('#jourTravail').val(response.jourspresence);
        		jQuery('#temptravail').val(response.heurspresence);
        		jQuery('#idperiod').val(response.periodePaie.id);
        		jQuery('#idpers').val(response.personnel.id);
        	
			} else {
				alert('Impossible de charger cet objet');
			}
        },
        error: function () {
            
        },
        complete: function () {
        //	jQuery("#formGenerer").removeAttr("disabled");
        //	jQuery("#rhpModal .modal-footer span").removeClass('loader');
    //   	$tablebull.bootstrapTable('refresh', {url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ jQuery('#idPeriode').val()});
        }
    });

}


function cherch3(matricule){
	// alert(idFonction);
		jQuery('#matricule').val(matricule);
		
		jQuery('#infoPersonnelmo').html(matricule);
		jQuery('#idpers').val(matricule);

		jQuery.ajax({
	        type: "GET",
	        url: baseUrl+"/paie/cherchtempeffectif",
	        data: {idPersonnel: jQuery('#idpers').val(),
	        	idPeriodDep:jQuery('#idperiod').val()},
	        cache: false,
			success: function (response) {
	        	if (response != null) {
	        	//	alert(response.result);
	        		jQuery('#jourTravail').val(response.jourspresence);
	        		jQuery('#temptravail').val(response.heurspresence);
	        		jQuery('#idperiod').val(response.periodePaie.id);
	        		jQuery('#idpers').val(response.personnel.id);
	        	
				} else {
					alert('Impossible de charger cet objet');
				}
	        },
	        error: function () {
	            
	        },
	        complete: function () {
	        //	jQuery("#formGenerer").removeAttr("disabled");
	        //	jQuery("#rhpModal .modal-footer span").removeClass('loader');
	    //   	$tablebull.bootstrapTable('refresh', {url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ jQuery('#idPeriode').val()});
	        }
	    });

	}


function matriculeFormatter(personnel) {
    return personnel.matricule;
}

function nomCompletFormatter(personnel) {
    return personnel.nomComplet;
}

function sexeFormatter(personnel) {
    return personnel.sexe;
}

function telephoneFormatter(personnel) {
    return personnel.telephone;
}

/* function optionFormatterPrime(id, row, index) {
    var actionTitle, icon;
    if (row.pointage) {
        actionTitle = "Modifier";
        icon = 'glyphicon-pencil';
    } else {
        actionTitle = "Enregistrer";
        icon = 'glyphicon-save';
    }
    var idPointage = row.pointage ? row.pointage.id : 0;
    //var option = '<a onclick="edit(' + row.id + ')" data-toggle="modal" data-target="#rhpModal" href="#" title="'+actionTitle+'">  <span class="glyphicon '+icon+'"></span></a>&nbsp;';
    var option = '<span></span><button class="btn btn-success" onclick="edit(' + row.personnel.id + ',' + idPointage + ',' + index + ', this)" title="' + actionTitle + '"><span class="glyphicon ' + icon + '"></span> Valider</button>';
    return option;
} */
function widgetView(view){
	if(view == 'histo'){
		jQuery('#tableWidget').hide('slow');	
		jQuery('#tableWidgethisto').show('slow');
	} 
	if(view == 'new'){	
		jQuery('#tableWidget').show('slow');	
		jQuery('#tableWidgethisto').hide('slow');
	}
	
}
function chargerbulletinParPeriode(){
	$tableImprimer = jQuery('#tableBullhisto');
	$tableImprimer.bootstrapTable('removeAll');
	$tableImprimer.bootstrapTable ('refresh', { url: baseUrl +'/paie/chargerbulletinparperiode?id='+ jQuery('#periodePaieImpression').val()});
    $tableImprimer.bootstrapTable('scrollTo', 0);
  
}


</script>

