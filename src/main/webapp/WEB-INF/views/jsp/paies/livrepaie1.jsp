
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
	              	<li><a href="#" onclick="widgetView('new')">Livre de paie</a></li>
	              	<li class="divider"></li>
	              	 <li><a href="#" onclick="widgetView('histo')">Historique Bulletins</a></li> 
	            </ul>
	        </div>
			<h4 id="widgetTitle" class="widgettitle">Livre de paie de  ${periode}</h4>
		</div>
		<div id="tableWidget" class="widgetcontent">
			<div id="toolbar1">
			    <div class="form-inline">
			     <button id="btnGenerer" type="submit" data-toggle="modal" data-target="#rhpModal" title="Cocher les employ�s mis en sommeil" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>G�n�rer livre de paie</button>
			    <button id="btnGenerer" type="button" data-toggle="modal" data-target="#rhpModalImprimer" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>Imprimer bulletin</button>
			    <button id="btnVrmt" type="button" data-toggle="modal" data-target="#rhpModalVirmt" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>Ordre de virement</button>
			    </div>
			</div>
			<form action="#" id="formList">
				<table id="table" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-sort-name="id"
					data-sort-order="desc"
					data-url="${pageContext.request.contextPath}/personnels/listcontratpersonnelActifjson"
					data-side-pagination="server" 
					data-pagination="true"
			
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
				<table id="tableBull" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-show-export="true"
					data-sort-name="name"
					<%-- data-url="${pageContext.request.contextPath}/paie/listbulletinMoisActif" --%>
					data-sort-order="desc"
					data-show-columns="true" 
					<%-- data-url="${pageContext.request.contextPath}/paie/savebullPersonnel"  --%>
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[10, 20, 50, 100, 200]" 
					data-search="true">
					<thead>
						<tr>
							<th data-field="contratPersonnel" data-formatter="matriculesFormatter"  data-align="left" data-sortable="true">Matricule</th>
							<th data-field="contratPersonnel" data-formatter="nomcompletFormatter" data-align="left" data-sortable="true">Nom</th>
							<th data-field="nombrePart"  data-align="left">Nbre part</th>
							<th data-field="anciennete"  data-align="center">Anciennet�</th>
							<th data-field="montantSalairbase"   data-align="left">Salaire Base</th>
							<th data-field="montantSursalaire"  data-align="center">Sursalaire</th>
							<th data-field=montantPrimeanciennete  data-align="center">Prime. Anciennet�</th>
							<th data-field="montantIndemnitelogement"  data-align="right">Indem logement</th>
							<th data-field="brutImpo" data-align="right">Brut imposable</th>
							<th data-field="montantIts" data-align="right">ITS</th>							
							<th data-field="montantCn"  data-align="left" data-sortable="true">CN</th>
							<th data-field="montantIgr"  data-align="left" data-sortable="true">IGR</th>
							<th data-field="montanttotalretenuefiscal"  data-align="left">Retenue fiscale</th>
							<th data-field="montantCnps"  data-align="center">CNPS</th>
							<th data-field="montantavanceetacompte"   data-align="left">Avance</th>
							<th data-field="montantpretaloes"  data-align="center">Pr�t</th>
							<th data-field="montantcarec"  data-align="center">Carec</th>
							<th data-field="montanttotalretenue"  data-align="right">Total retenue</th>
							<th data-field="indemniteRepresent" data-align="right">Indem de repres.</th>
							<th data-field="indemniteTransp" data-align="right">Indem de transport.</th>
							
							<th data-field="montantNetapayer"  data-align="center">Net � payer</th>
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
			</form>
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
                	<h3>Vous �tes sur le point de g&eacute;n&eacute;rer le bulletin de paie de la p&eacute;riode de <span id="periodePaie" class="danger">Mars 2016</span> du personnel.</h3>
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
    <div class="modal-dialog" role="document" style="width:80%;" >
        <div class="modal-content">
            <form id="formGenererValidBull" class="form-horizontal" >
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Valider les bulletins de paie de ${periode}</h4>
				</div>
                <div class="modal-body">
                <!-- <div id="toolbar">
			    <div class="form-inline">
			        <button id="btnGenerer5" type="submit" data-toggle="modal" data-target="#rhpModal5 " title="Cocher les employ�s mis en sommeil" class="btn btn-primary btn-xs"><i class="fa fa-plus"></i>G�n�rer tous les bulletins</button>
			    </div>
			   </div> -->
            
              <%-- <form action="#" id="formList"> --%>
				<table id="tableliv" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"					
					data-sort-name="name"
					data-pagination="true"
					data-sort-order="desc"
					
					<%-- data-url="${pageContext.request.contextPath}/paie/savebullPersonnel"  data-show-columns="true"  --%>
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[10, 20, 50, 100, 200]" 
					data-show-export="true"					
					data-export-dataType="all"
					data-search="true">
					<thead>
						<tr>
							<th data-field="matricule"  data-align="left" data-sortable="true">Matricule</th>
							<th data-field="nomPrenom"  data-align="left" data-sortable="true">Nom</th>
							<th data-field="nombrePart"  data-align="left">Nbre part</th>
							<th data-field="anciennete"  data-align="center">Anciennet�</th>
							<th data-field="mtsalaireBase"   data-align="left">Salaire Base</th>
							<th data-field="mtsursalaire"  data-align="center">Sursalaire</th>
							<th data-field="mtprimeAnciennete"  data-align="center">Prime. Anciennet�</th>
							<th data-field="mtindemniteLogement"  data-align="right">Indem logement</th>
							<th data-field="mtbrutImposable" data-align="right">Brut imposable</th>
							<th data-field="mtits" data-align="right">ITS</th>							
							<th data-field="mtcn"  data-align="left" data-sortable="true">CN</th>
							<th data-field="mtigr"  data-align="left" data-sortable="true">IGR</th>
							<th data-field="mttotalRetenueFiscale"  data-align="left">Retenue fiscale</th>
							<th data-field="mtcnps"  data-align="center">CNPS</th>
							<th data-field="mtavceAcpte"   data-align="left">Avance</th>
							<th data-field="mtpretAlios"  data-align="center">Pr�t</th>
							<th data-field="mtcarec"  data-align="center">Carec</th>
							<th data-field="mttotalRetenue"  data-align="right">Total retenu</th>
							<th data-field="mtindemniteRepresentation" data-align="right">Indem de repres.</th>
							<th data-field="mtindemniteTransport" data-align="right">Indem de transport.</th>
							
							<th data-field="mtnetPayer"  data-align="center">Net � payer</th>
							<th data-field="mttotalBrut"   data-align="left">Total brut</th>
							<th data-field="mtis"  data-align="center">IS</th>
							<th data-field="mtta"  data-align="center">TA</th>
							<th data-field="mtfpc"  data-align="right">FPC</th>
							<th data-field="mtprestationFamiliale" data-align="right">Prest familiale</th>
							<th data-field="mtaccidentTravail" data-align="right">Acc de travail</th>
							<th data-field="mtretraite" data-align="right">Retraite</th>
							<th data-field="mttotalPatronal" data-align="right"> Total patronal</th>
							<th data-field="mttotalMasseSalariale" data-align="right">Total masse salariale</th>
						
							
							<!-- <th data-field="id" data-formatter="optionFormatter1" data-align="center">Options</th>  -->
							
						</tr>
					</thead>
				</table>
			
                	
                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                	<!-- <input type="hidden"  id="idPeriode" name="id"> -->
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" onclick="genererLivreDePaie()" class="btn btn-success">Valider</button>
                    
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


<div class="modal fade" id="rhpModalImprimer"  role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static" >
    <div class="modal-dialog" role="document" style="width:80%;" >
        <div class="modal-content">
            <form id="formImrimBull" class="form-horizontal" >
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Imprimer les bulletins de paie de  ${periode}</h4>
				</div>
                <div class="modal-body">
              <!--   <div id="toolbar">
  					<select class="form-control">
    					<option value="">Export Basic</option>
    					<option value="all">Export All</option>
    					<option value="selected">Export Selected</option>
 					 </select>
			  </div> -->
             <!--        <div class="columns columns-right btn-group pull-right">
                <div class="export btn-group open"><button class="btn btn-default dropdown-toggle" data-toggle="dropdown" type="button">
                <i class="glyphicon glyphicon-export icon-share"></i> <span class="caret"></span></button><ul class="dropdown-menu" role="menu">
            
                <li data-type="excel"><a href="javascript:exports()">MS-Excel</a></li>
                </ul>
                </div>
                </div> -->
              <!--   <div id="toolbar">
			    <div class="form-inline">
			        <button id="btnGenerer5" type="submit" data-toggle="modal" data-target="#rhpModal5 " title="Cocher les employ�s mis en sommeil" class="btn btn-primary btn-xs"><i class="fa fa-plus"></i>G�n�rer tous les bulletins</button>
			    </div>
			   </div> -->
                
              <%-- <form action="#" id="formList"> --%>
				<table id="tableBull" class="table table-info table-striped"
					data-toggle="table" 
					data-click-to-select="true"
					data-single-select="false"					
					data-show-export="true" 
					data-click-to-select="true"
					data-sort-name="name"
					data-url="${pageContext.request.contextPath}/paie/listbulletinMoisActif"
					data-sort-order="desc"
					data-show-columns="true" 
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[10, 20, 50, 100, 200]" 
					data-search="true">
					<thead>
						<tr>
							<th data-field="contratPersonnel" data-formatter="matriculesFormatter"  data-align="left" data-sortable="true">Matricule</th>
							<th data-field="contratPersonnel" data-formatter="nomcompletFormatter" data-align="left" data-sortable="true">Nom</th>
							<th data-field="nombrePart"  data-align="left">Nbre part</th>
							<th data-field="anciennete"  data-align="center">Anciennet�</th>
							<th data-field="montantSalairbase"   data-align="left">Salaire Base</th>
							<th data-field="montantSursalaire"  data-align="center">Sursalaire</th>
							<th data-field=montantPrimeanciennete  data-align="center">Prime. Anciennet�</th>
							<th data-field="montantIndemnitelogement"  data-align="right">Indem logement</th>
							<th data-field="brutImpo" data-align="right">Brut imposable</th>
							<th data-field="montantIts" data-align="right">ITS</th>							
							<th data-field="montantCn"  data-align="left" data-sortable="true">CN</th>
							<th data-field="montantIgr"  data-align="left" data-sortable="true">IGR</th>
							<th data-field="montanttotalretenuefiscal"  data-align="left">Retenue fiscale</th>
							<th data-field="montantCnps"  data-align="center">CNPS</th>
							<th data-field="montantavanceetacompte"   data-align="left">Avance</th>
							<th data-field="montantpretaloes"  data-align="center">Pr�t</th>
							<th data-field="montantcarec"  data-align="center">Carec</th>
							<th data-field="montanttotalretenue"  data-align="right">Total retenue</th>
							<th data-field="indemniteRepresent" data-align="right">Indem de repres.</th>
							<th data-field="indemniteTransp" data-align="right">Indem de transport.</th>
							
							<th data-field="montantNetapayer"  data-align="center">Net � payer</th>
							<th data-field="montanttotalbrut"   data-align="left">Total brut</th>
							<th data-field="montantIs"  data-align="center">IS</th>
							<th data-field="montantTa"  data-align="center">TA</th>
							<th data-field="montantFpc"  data-align="right">FPC</th>
							<th data-field="prestationFamil" data-align="right">Prest familiale</th>
							<th data-field="accidentTrav" data-align="right">Acc de travail</th>
							<th data-field="montantRetraite" data-align="right">Retraite</th>
							<th data-field="montanttotalpatronal" data-align="right"> Total patronal</th>
							<th data-field="montanttotalmassesalarial" data-align="right">Total masse salariale</th>
							 
							
							<th data-field="id" data-formatter="optionFormatter2" data-align="center">Options</th> 
							
						</tr>
					</thead>
				</table>
			
                	
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
});/* .controller('formDeleteCtrl', function ($scope) {
    $scope.pupulateForm = function (fonction) {
    	$scope.fonction = fonction;
    };

}); */
//End AngularJs
var contextPath = "<%=request.getContextPath() %>";
var actionUrl = "/paie/savebullPersonnel";
var action = "ajout";
var $table;
jQuery(document).ready(function($) {
	
	jQuery('#tableWidgethisto').hide();
	jQuery('#tableWidget').show();
	jQuery( ".select2" ).select2();
	var periode='';var periodeID='';
	chargerPeriodePaie()
	periode='${activeMois}';
	periodeID='${activeMoisId}';
	jQuery('#idPeriode1').val(periodeID);
	jQuery('#idperiod').val(periodeID);
	$table = $('#table');
	$tablebull = $('#tableBull');
	//alert(periode);
	//alert(periodeID);
	jQuery('#periodePaie').html(periode);
	jQuery('#idPeriode').val(periodeID);
	//alert(jQuery('#idPeriode').val());
	//Envoi des donnees
	
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
            	jQuery("#rhpModalModif .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
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
	
	  
	
	jQuery("#formImrimBull").submit(function(e){
//	alert(id);
		var id='${activeMoisId}';
		location.href = baseUrl +  "/paie/bulletinMoisPersonnel?idbul="+id; 
/* 		jQuery.ajax({
	        type: "GET",
	        url: baseUrl+"/paie/bulletinMoisPersonnel",
	        cache: false,
	        data: {
	        	idbul: id
	        },
			success: function (response) {
	        	if (response != null) {
	        		//alert(response.result);
	        		//jQuery("#rhpModal").modal('hide');
				} else {
					alert('Impossible de charger cet objet');
				}
	        },
	        error: function () {
	            
	        }
	    }); */
	});
	function chargerPeriodePaie(){
		jQuery.ajax({
	        type: "POST",
	        url: baseUrl + "/parametrages/periodecloturee",
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
            	jQuery("#rhpModalModif .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
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
        	jQuery("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
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
       	location.reload();
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
function genererLivreDePaie(){

	jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/savelivrepaie",
        cache: false,
		success: function (response) {
        	if (response != null) {
        		alert(response.result);
        		jQuery("#rhpModalValider").modal('hide');
        	//	$tablebull.bootstrapTable('refresh', { url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
			} else {
				alert('Impossible de charger cet objet');
			}
        },
        error: function () {
            
        },
        complete: function () {
       	$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ periodeID});
        location.reload();
        }
    });
}
function optionFormatter(id, row, index) {
	var option = '<a onclick="selectPersInfo('+id+','+row.personnel.id+')" data-toggle="modal" data-target="#rhpModalModif" href="#" title="Modifier personnel [LIBELLE : '+row.personnel.matricule+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	option += '<a onclick="cherch2('+row.personnel.id+')" data-toggle="modal" data-target="#rhpModalModiftemps" href="#" title="Modifier personnel [LIBELLE : '+row.matricule+' ]">  <span class="glyphicon glyphicon-list"></span></a>&nbsp;';
	option += '&nbsp;<a data-toggle="modal" href="#" title="Suprimer bulletin [LIBELLE : '+row.libelle+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';
	
    return option;
}

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

function nomFormatter(personnel, row, index) {
	if(row.personnel.nom == ''){
		return "";
	}
	return row.personnel.nom+" "+row.personnel.prenom;
}



function sommeilFormatter(personnel, row, index) {
	var optionActif = '<small class="label label-danger"><i class="fa fa-clock-o"></i> -- Desactiv� </small>';
	if(row.personnel.statut == true)
		optionActif = '<small class="label label-success"><i class="fa fa-clock-o"></i> -- Activ� </small>';
	
	return optionActif;
}
function somFormatter(contratPersonnel, row, index) {
	if(row.contratPersonnel.personnel.matricule == ''){
		return "";
	}
	return row.contratPersonnel.personnel.matricule;
}


function matriculesFormatter(contratPersonnel, row, index) {
	if(row.contratPersonnel.personnel.matricule == ''){
		return "";
	}
	return row.contratPersonnel.personnel.matricule;
}

function nomcompletFormatter(contratPersonnel, row, index) {
	if(row.contratPersonnel.personnel.nom == ''){
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

	jQuery('#idPersonnel').val(idFonction);
	jQuery.ajax({
        type: "GET",
        url: baseUrl+"/personnels/cherchpersonnel",
        data: {idPersonnel: idFonction},
        cache: false,
		success: function (response) {
        	if (response != null) {
        	//	alert(response.result);
        		jQuery('#idPersonnel').val(response.id);
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
        		alert('Operation effexctu�e avec succes');
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
	$tableImprimer = jQuery('#tableBull');
	$tableImprimer.bootstrapTable('removeAll');
	$tableImprimer.bootstrapTable ('refresh', { url: baseUrl +'/paie/chargerbulletinparperiode?id='+ jQuery('#periodePaieImpression').val()});
    $tableImprimer.bootstrapTable('scrollTo', 0);
  
}

var $table = jQuery('#tableliv'),
selections = [];

jQuery(function () {
$table.on('check.bs.table check-all.bs.table ' +
        'uncheck.bs.table uncheck-all.bs.table', function (e, rows) {
    var ids = $.map(!$.isArray(rows) ? [rows] : rows, function (row) {
            return row.id;
        }),
        func = $.inArray(e.type, ['check', 'check-all']) > -1 ? 'union' : 'difference';

    selections = _[func](selections, ids);
});
});

</script>

<%-- 
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
	              	<li><a href="#"></a></li>
	              	<li class="divider"></li>
	              	 <li><a href="#" onclick="widgetView('histo')">Historique Bulletins</a></li> 
	            </ul>
	        </div>
			<h4 id="widgetTitle" class="widgettitle">Livre de paie de  ${periode}</h4>
		</div>
		<div id="tableWidget" class="widgetcontent">
			<div id="toolbar1">
			    <div class="form-inline">
			     <button id="btnGenerer" type="submit" data-toggle="modal" data-target="#rhpModal" title="Cocher les employ�s mis en sommeil" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>G�n�rer livre de paie</button>
			    <button id="btnGenerer" type="button" data-toggle="modal" data-target="#rhpModalImprimer" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>Imprimer bulletin</button>
			    </div>
			</div>
			<form action="#" id="formList">
				<table id="table" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-sort-name="id"
					data-sort-order="desc"
					data-url="${pageContext.request.contextPath}/personnels/listcontratpersonnelActifjson"
					data-side-pagination="server" 
					data-pagination="true"
			
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
				<table id="tableBull" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-show-export="true"
					data-sort-name="name"
					data-url="${pageContext.request.contextPath}/paie/listbulletinMoisActif"
					data-sort-order="desc"
					data-show-columns="true" 
					data-url="${pageContext.request.contextPath}/paie/savebullPersonnel" 
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[10, 20, 50, 100, 200]" 
					data-search="true">
					<thead>
						<tr>
							<th data-field="contratPersonnel" data-formatter="matriculesFormatter"  data-align="left" data-sortable="true">Matricule</th>
							<th data-field="contratPersonnel" data-formatter="nomcompletFormatter" data-align="left" data-sortable="true">Nom</th>
							<th data-field="nombrePart"  data-align="left">Nbre part</th>
							<th data-field="anciennete"  data-align="center">Anciennet�</th>
							<th data-field="montantSalairbase"   data-align="left">Salaire Base</th>
							<th data-field="montantSursalaire"  data-align="center">Sursalaire</th>
							<th data-field=montantPrimeanciennete  data-align="center">Prime. Anciennet�</th>
							<th data-field="montantIndemnitelogement"  data-align="right">Indem logement</th>
							<th data-field="brutImpo" data-align="right">Brut imposable</th>
							<th data-field="montantIts" data-align="right">ITS</th>							
							<th data-field="montantCn"  data-align="left" data-sortable="true">CN</th>
							<th data-field="montantIgr"  data-align="left" data-sortable="true">IGR</th>
							<th data-field="montanttotalretenuefiscal"  data-align="left">Retenue fiscale</th>
							<th data-field="montantCnps"  data-align="center">CNPS</th>
							<th data-field="montantavanceetacompte"   data-align="left">Avance</th>
							<th data-field="montantpretaloes"  data-align="center">Pr�t</th>
							<th data-field="montantcarec"  data-align="center">Carec</th>
							<th data-field="montanttotalretenue"  data-align="right">Total retenue</th>
							<th data-field="indemniteRepresent" data-align="right">Indem de repres.</th>
							<th data-field="indemniteTransp" data-align="right">Indem de transport.</th>
							
							<th data-field="montantNetapayer"  data-align="center">Net � payer</th>
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
			</form>
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
                	<h3>Vous �tes sur le point de g&eacute;n&eacute;rer le bulletin de paie de la p&eacute;riode de <span id="periodePaie" class="danger">Mars 2016</span> du personnel.</h3>
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
<!-- Livre de paie -->
<div class="modal fade" id="rhpModalValider"  role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static" >
    <div class="modal-dialog" role="document" style="width:80%;" >
        <div class="modal-content">
            <form id="formGenererValidBull" class="form-horizontal" >
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Valider les bulletins de paie de ${periode}</h4>
				</div>
                <div class="modal-body">
                <!-- <div id="toolbar">
			    <div class="form-inline">
			        <button id="btnGenerer5" type="submit" data-toggle="modal" data-target="#rhpModal5 " title="Cocher les employ�s mis en sommeil" class="btn btn-primary btn-xs"><i class="fa fa-plus"></i>G�n�rer tous les bulletins</button>
			    </div>
			   </div> -->
            
              <form action="#" id="formList">
				<table id="tableliv" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"					
					data-sort-name="name"
					data-sort-order="desc"
					
					data-url="${pageContext.request.contextPath}/paie/savebullPersonnel"  data-show-columns="true" 
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[10, 20, 50, 100, 200]" 
					data-show-export="true"					
					data-export-dataType="all"
					data-search="true">
					<thead>
						<tr>
							<th data-field="matricule"  data-align="left" data-sortable="true">Matricule</th>
							<th data-field="nomPrenom"  data-align="left" data-sortable="true">Nom</th>
							<th data-field="nombrePart"  data-align="left">Nbre part</th>
							<th data-field="anciennete"  data-align="center">Anciennet�</th>
							<th data-field="mtsalaireBase"   data-align="left">Salaire Base</th>
							<th data-field="mtsursalaire"  data-align="center">Sursalaire</th>
							<th data-field="mtprimeAnciennete"  data-align="center">Prime. Anciennet�</th>
							<th data-field="mtindemniteLogement"  data-align="right">Indem logement</th>
							<th data-field="mtbrutImposable" data-align="right">Brut imposable</th>
							<th data-field="mtits" data-align="right">ITS</th>							
							<th data-field="mtcn"  data-align="left" data-sortable="true">CN</th>
							<th data-field="mtigr"  data-align="left" data-sortable="true">IGR</th>
							<th data-field="mttotalRetenueFiscale"  data-align="left">Retenue fiscale</th>
							<th data-field="mtcnps"  data-align="center">CNPS</th>
							<th data-field="mtavceAcpte"   data-align="left">Avance</th>
							<th data-field="mtpretAlios"  data-align="center">Pr�t</th>
							<th data-field="mtcarec"  data-align="center">Carec</th>
							<th data-field="mttotalRetenue"  data-align="right">Total retenu</th>
							<th data-field="mtindemniteRepresentation" data-align="right">Indem de repres.</th>
							<th data-field="mtindemniteTransport" data-align="right">Indem de transport.</th>
							
							<th data-field="mtnetPayer"  data-align="center">Net � payer</th>
							<th data-field="mttotalBrut"   data-align="left">Total brut</th>
							<th data-field="mtis"  data-align="center">IS</th>
							<th data-field="mtta"  data-align="center">TA</th>
							<th data-field="mtfpc"  data-align="right">FPC</th>
							<th data-field="mtprestationFamiliale" data-align="right">Prest familiale</th>
							<th data-field="mtaccidentTravail" data-align="right">Acc de travail</th>
							<th data-field="mtretraite" data-align="right">Retraite</th>
							<th data-field="mttotalPatronal" data-align="right"> Total patronal</th>
							<th data-field="mttotalMasseSalariale" data-align="right">Total masse salariale</th>
						
							
							<!-- <th data-field="id" data-formatter="optionFormatter1" data-align="center">Options</th>  -->
							
						</tr>
					</thead>
				</table>
			
                	
                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                	<!-- <input type="hidden"  id="idPeriode" name="id"> -->
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" onclick="genererLivreDePaie()" class="btn btn-success">Valider</button>
                    
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
                    <input type="text" class="hidden" ng-show="false" id="idPersonnel" name="idPersonnel" ng-model="id">
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


<div class="modal fade" id="rhpModalImprimer"  role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static" >
    <div class="modal-dialog" role="document" style="width:80%;" >
        <div class="modal-content">
            <form id="formImrimBull" class="form-horizontal" >
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Imprimer les bulletins de paie de  ${periode}</h4>
				</div>
                <div class="modal-body">
              <!--   <div id="toolbar">
  					<select class="form-control">
    					<option value="">Export Basic</option>
    					<option value="all">Export All</option>
    					<option value="selected">Export Selected</option>
 					 </select>
			  </div> -->
             <!--        <div class="columns columns-right btn-group pull-right">
                <div class="export btn-group open"><button class="btn btn-default dropdown-toggle" data-toggle="dropdown" type="button">
                <i class="glyphicon glyphicon-export icon-share"></i> <span class="caret"></span></button><ul class="dropdown-menu" role="menu">
            
                <li data-type="excel"><a href="javascript:exports()">MS-Excel</a></li>
                </ul>
                </div>
                </div> -->
              <!--   <div id="toolbar">
			    <div class="form-inline">
			        <button id="btnGenerer5" type="submit" data-toggle="modal" data-target="#rhpModal5 " title="Cocher les employ�s mis en sommeil" class="btn btn-primary btn-xs"><i class="fa fa-plus"></i>G�n�rer tous les bulletins</button>
			    </div>
			   </div> -->
                
              <form action="#" id="formList">
				<table id="tableBull" class="table table-info table-striped"
					data-toggle="table" 
					data-click-to-select="true"
					data-single-select="false"					
					data-show-export="true" 
					data-click-to-select="true"
					data-sort-name="name"
					data-url="${pageContext.request.contextPath}/paie/listbulletinMoisActif"
					data-sort-order="desc"
					data-show-columns="true" 
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[10, 20, 50, 100, 200]" 
					data-search="true">
					<thead>
						<tr>
							<th data-field="contratPersonnel" data-formatter="matriculesFormatter"  data-align="left" data-sortable="true">Matricule</th>
							<th data-field="contratPersonnel" data-formatter="nomcompletFormatter" data-align="left" data-sortable="true">Nom</th>
							<th data-field="nombrePart"  data-align="left">Nbre part</th>
							<th data-field="anciennete"  data-align="center">Anciennet�</th>
							<th data-field="montantSalairbase"   data-align="left">Salaire Base</th>
							<th data-field="montantSursalaire"  data-align="center">Sursalaire</th>
							<th data-field=montantPrimeanciennete  data-align="center">Prime. Anciennet�</th>
							<th data-field="montantIndemnitelogement"  data-align="right">Indem logement</th>
							<th data-field="brutImpo" data-align="right">Brut imposable</th>
							<th data-field="montantIts" data-align="right">ITS</th>							
							<th data-field="montantCn"  data-align="left" data-sortable="true">CN</th>
							<th data-field="montantIgr"  data-align="left" data-sortable="true">IGR</th>
							<th data-field="montanttotalretenuefiscal"  data-align="left">Retenue fiscale</th>
							<th data-field="montantCnps"  data-align="center">CNPS</th>
							<th data-field="montantavanceetacompte"   data-align="left">Avance</th>
							<th data-field="montantpretaloes"  data-align="center">Pr�t</th>
							<th data-field="montantcarec"  data-align="center">Carec</th>
							<th data-field="montanttotalretenue"  data-align="right">Total retenue</th>
							<th data-field="indemniteRepresent" data-align="right">Indem de repres.</th>
							<th data-field="indemniteTransp" data-align="right">Indem de transport.</th>
							
							<th data-field="montantNetapayer"  data-align="center">Net � payer</th>
							<th data-field="montanttotalbrut"   data-align="left">Total brut</th>
							<th data-field="montantIs"  data-align="center">IS</th>
							<th data-field="montantTa"  data-align="center">TA</th>
							<th data-field="montantFpc"  data-align="right">FPC</th>
							<th data-field="prestationFamil" data-align="right">Prest familiale</th>
							<th data-field="accidentTrav" data-align="right">Acc de travail</th>
							<th data-field="montantRetraite" data-align="right">Retraite</th>
							<th data-field="montanttotalpatronal" data-align="right"> Total patronal</th>
							<th data-field="montanttotalmassesalarial" data-align="right">Total masse salariale</th>
							 
							
							<th data-field="id" data-formatter="optionFormatter2" data-align="center">Options</th> 
							
						</tr>
					</thead>
				</table>
			
                	
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
});/* .controller('formDeleteCtrl', function ($scope) {
    $scope.pupulateForm = function (fonction) {
    	$scope.fonction = fonction;
    };

}); */
//End AngularJs
var contextPath = "<%=request.getContextPath() %>";
var actionUrl = "/paie/savebullPersonnel";
var action = "ajout";
var $table;
jQuery(document).ready(function($) {
	
	jQuery('#tableWidgethisto').hide();
	jQuery('#tableWidget').show();
	jQuery( ".select2" ).select2();
	var periode='';var periodeID='';
	chargerPeriodePaie()
	periode='${activeMois}';
	periodeID='${activeMoisId}';
	jQuery('#idPeriode1').val(periodeID);
	jQuery('#idperiod').val(periodeID);
	$table = $('#table');
	//alert(periode);
	//alert(periodeID);
	jQuery('#periodePaie').html(periode);
	jQuery('#idPeriode').val(periodeID);
	//alert(jQuery('#idPeriode').val());
	//Envoi des donnees
	
	jQuery("#jourTravail").blur(function(){
		var nbjou;
		nbjou = $("#jourTravail").val();
		jQuery("#temptravail").val((nbjou*173.33)/30);
		
	});
	
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
            	jQuery("#rhpModalModif .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
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
	
	jQuery("#formImrimBull").submit(function(e){
//	alert(id);
		var id='${activeMoisId}';
		location.href = baseUrl +  "/paie/bulletinMoisPersonnel?idbul="+id; 
/* 		jQuery.ajax({
	        type: "GET",
	        url: baseUrl+"/paie/bulletinMoisPersonnel",
	        cache: false,
	        data: {
	        	idbul: id
	        },
			success: function (response) {
	        	if (response != null) {
	        		//alert(response.result);
	        		//jQuery("#rhpModal").modal('hide');
				} else {
					alert('Impossible de charger cet objet');
				}
	        },
	        error: function () {
	            
	        }
	    }); */
	});
	function chargerPeriodePaie(){
		jQuery.ajax({
	        type: "POST",
	        url: baseUrl + "/parametrages/periodecloturee",
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
            	jQuery("#rhpModalModif .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
            	jQuery("#rhpModalModif .modal-footer span").removeClass('loader');
            },
            beforeSend: function () {
            	jQuery("#formModificationPersonnel").attr("disabled", true);
            	jQuery("#rhpModalModif .modal-footer span").addClass('loader');
            },
            complete: function () {
            	jQuery("#formModificationPersonnel").removeAttr("disabled");
            	jQuery("#rhpModalModif .modal-footer span").removeClass('loader');
            	 jQuery ('#table'). bootstrapTable ('refresh', {  url: baseUrl +'/contratpersonnel/listcontratpersonnelActifjson' });
            	
            }
        });
	});

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
            	//alert('verger');
            }
            else if(response.result == "erreur_champ_obligatoire"){
            	alert("Saisie invalide");
            }
        },
        error: function () {
        	jQuery("#rhpModal .modal-body div.alert").alert();
        	jQuery("#rhpModal .modal-body .alert h4").html("Erreur survenue");
        	jQuery("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
        	jQuery("#rhpModal .modal-footer span").removeClass('loader');
        },
        beforeSend: function () {
        	jQuery("#formAjout").attr("disabled", true);
        	jQuery("#rhpModal .modal-footer span").addClass('loader');
        },
        complete: function () {
        	jQuery("#formGenerer").removeAttr("disabled");
        	jQuery("#rhpModal .modal-footer span").removeClass('loader');
       	jQuery ('#tableliv'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/savebullPersonnel?id='+ jQuery('#idPeriode').val()});
        }
    });
});

function genererLivreDePaie(){
	$tablebull = jQuery('#tableBull');
	jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/savelivrepaie",
        cache: false,
		success: function (response) {
        	if (response != null) {
        		alert(response.result);
        		jQuery("#rhpModalValider").modal('hide');
			} else {
				alert('Impossible de charger cet objet');
			}
        },
        error: function () {
            
        },
        complete: function () {
        //	jQuery("#formGenerer").removeAttr("disabled");
        //	jQuery("#rhpModal .modal-footer span").removeClass('loader');
       	$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ jQuery('#idPeriode').val()});
        }
    });
}
function optionFormatter(id, row, index) {
	var option = '<a onclick="selectPersInfo('+id+','+row.personnel.id+')" data-toggle="modal" data-target="#rhpModalModif" href="#" title="Modifier personnel [LIBELLE : '+row.personnel.matricule+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	option += '<a onclick="cherch2('+row.personnel.id+')" data-toggle="modal" data-target="#rhpModalModiftemps" href="#" title="Modifier personnel [LIBELLE : '+row.matricule+' ]">  <span class="glyphicon glyphicon-list"></span></a>&nbsp;';
	option += '&nbsp;<a data-toggle="modal" href="#" title="Suprimer bulletin [LIBELLE : '+row.libelle+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';
	
    return option;
}

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

function nomFormatter(personnel, row, index) {
	if(row.personnel.nom == ''){
		return "";
	}
	return row.personnel.nom+" "+row.personnel.prenom;
}



function sommeilFormatter(personnel, row, index) {
	var optionActif = '<small class="label label-danger"><i class="fa fa-clock-o"></i> -- Desactiv� </small>';
	if(row.personnel.statut == true)
		optionActif = '<small class="label label-success"><i class="fa fa-clock-o"></i> -- Activ� </small>';
	
	return optionActif;
}
function somFormatter(contratPersonnel, row, index) {
	if(row.contratPersonnel.personnel.matricule == ''){
		return "";
	}
	return row.contratPersonnel.personnel.matricule;
}


function matriculesFormatter(contratPersonnel, row, index) {
	if(row.contratPersonnel.personnel.matricule == ''){
		return "";
	}
	return row.contratPersonnel.personnel.matricule;
}

function nomcompletFormatter(contratPersonnel, row, index) {
	if(row.contratPersonnel.personnel.nom == ''){
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

	jQuery('#idPersonnel').val(idFonction);
	jQuery.ajax({
        type: "GET",
        url: baseUrl+"/personnels/cherchpersonnel",
        data: {idPersonnel: idFonction},
        cache: false,
		success: function (response) {
        	if (response != null) {
        	//	alert(response.result);
        		jQuery('#idPersonnel').val(response.id);
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
jQuery("#formModificationPersonneltemps").submit(function(e){	
	//e.preventDefault();
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
        		alert('Operation effexctu�e avec succes');
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
function widgetView(view){
	if(view == 'histo'){
		jQuery('#tableWidget').hide('slow');	
		jQuery('#tableWidgethisto').show('slow');
	} else{
		
		jQuery('#tableWidget').show('slow');	
		jQuery('#tableWidgethisto').hide('slow');
	}
	
}
function chargerbulletinParPeriode(){
	$tableImprimer = jQuery('#tableBull');
	$tableImprimer.bootstrapTable('removeAll');
	$tableImprimer.bootstrapTable ('refresh', { url: baseUrl +'/paie/chargerbulletinparperiode?id='+ jQuery('#periodePaieImpression').val()});
    $tableImprimer.bootstrapTable('scrollTo', 0);
  
}

var $table = jQuery('#tableliv'),
selections = [];

jQuery(function () {
$table.on('check.bs.table check-all.bs.table ' +
        'uncheck.bs.table uncheck-all.bs.table', function (e, rows) {
    var ids = $.map(!$.isArray(rows) ? [rows] : rows, function (row) {
            return row.id;
        }),
        func = $.inArray(e.type, ['check', 'check-all']) > -1 ? 'union' : 'difference';

    selections = _[func](selections, ids);
});
});

</script> --%>

<%-- <%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="widget">
	<div class="widgetbox">
		<div class="headtitle">
		    <div class="btn-group">
	            <button data-toggle="dropdown" class="btn dropdown-toggle">Action <span class="caret"></span></button>
				<ul class="dropdown-menu">
	              	<li><a href="#"></a></li>
	              	<li class="divider"></li>
	              	 <li><a href="#" onclick="widgetView('histo')">Historique Bulletins</a></li> 
	            </ul>
	        </div>
			<h4 id="widgetTitle" class="widgettitle">Livre de paie de  ${periode}</h4>
		</div>
		<div id="tableWidget" class="widgetcontent">
			<div id="toolbar1">
			    <div class="form-inline">
			     <button id="btnGenerer" type="submit" data-toggle="modal" data-target="#rhpModal" title="Cocher les employ�s mis en sommeil" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>G�n�rer livre de paie</button>
			    <button id="btnGenerer" type="button" data-toggle="modal" data-target="#rhpModalImprimer" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>Imprimer bulletin</button>
			    </div>
			</div>
			<form action="#" id="formList">
				<table id="table" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-sort-name="id"
					data-sort-order="desc"
					data-url="${pageContext.request.contextPath}/personnels/listcontratpersonnelActifjson"
					data-side-pagination="server" 
					data-pagination="true"
			
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
							<!-- <th data-field="netAPayer" data-align="right">Net &agrave; payer</th> -->
							<th data-field="personnel" data-formatter="sommeilFormatter" data-align="right">En sommeil</th>
							<th data-field="id" data-formatter="optionFormatter" data-align="center">Options</th>
							
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
				<table id="tableBull" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-show-export="true"
					data-sort-name="name"
					data-url="${pageContext.request.contextPath}/paie/listbulletinMoisActif"
					data-sort-order="desc"
					data-show-columns="true" 
					data-url="${pageContext.request.contextPath}/paie/savebullPersonnel" 
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[10, 20, 50, 100, 200]" 
					data-search="true">
					<thead>
						<tr>
							<th data-field="contratPersonnel" data-formatter="matriculesFormatter"  data-align="left" data-sortable="true">Matricule</th>
							<th data-field="contratPersonnel" data-formatter="nomcompletFormatter" data-align="left" data-sortable="true">Nom</th>
							<th data-field="nombrePart"  data-align="left">Nbre part</th>
							<th data-field="anciennete"  data-align="center">Anciennet�</th>
							<th data-field="montantSalairbase"   data-align="left">Salaire Base</th>
							<th data-field="montantSursalaire"  data-align="center">Sursalaire</th>
							<th data-field=montantPrimeanciennete  data-align="center">Prime. Anciennet�</th>
							<th data-field="montantIndemnitelogement"  data-align="right">Indem logement</th>
							<th data-field="brutImpo" data-align="right">Brut imposable</th>
							<th data-field="montantIts" data-align="right">ITS</th>							
							<th data-field="montantCn"  data-align="left" data-sortable="true">CN</th>
							<th data-field="montantIgr"  data-align="left" data-sortable="true">IGR</th>
							<th data-field="montanttotalretenuefiscal"  data-align="left">Retenue fiscale</th>
							<th data-field="montantCnps"  data-align="center">CNPS</th>
							<th data-field="montantavanceetacompte"   data-align="left">Avance & Acompte</th>
							<th data-field="montantpretaloes"  data-align="center">ALIOS</th>
							<th data-field="montantcarec"  data-align="center">Carec</th>
							<th data-field="montanttotalretenue"  data-align="right">Total retenue</th>
							<th data-field="indemniteRepresent" data-align="right">Indem de repres.</th>
							<th data-field="indemniteTransp" data-align="right">Indem de transport.</th>
							
							<th data-field="montantNetapayer"  data-align="center">Net � payer</th>
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
			</form>
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
                	<h3>Vous �tes sur le point de g&eacute;n&eacute;rer le bulletin de paie de la p&eacute;riode de <span id="periodePaie" class="danger">Mars 2016</span> du personnel.</h3>
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
<!-- Livre de paie -->
<div class="modal fade" id="rhpModalValider"  role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static" >
    <div class="modal-dialog" role="document" style="width:80%;" >
        <div class="modal-content">
            <form id="formGenererValidBull" class="form-horizontal" >
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Valider les bulletins de paie de ${periode}</h4>
				</div>
                <div class="modal-body">
                <!-- <div id="toolbar">
			    <div class="form-inline">
			        <button id="btnGenerer5" type="submit" data-toggle="modal" data-target="#rhpModal5 " title="Cocher les employ�s mis en sommeil" class="btn btn-primary btn-xs"><i class="fa fa-plus"></i>G�n�rer tous les bulletins</button>
			    </div>
			   </div> -->
            
              <form action="#" id="formList">
				<table id="tableliv" class="table table-info table-striped"
					data-toggle="table" data-click-to-select="true"
					data-single-select="false"					
					data-sort-name="name"
					data-sort-order="desc"
					
					data-url="${pageContext.request.contextPath}/paie/savebullPersonnel"  data-show-columns="true" 
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[10, 20, 50, 100, 200]" 
					data-show-export="true"					
					data-export-dataType="all"
					data-search="true">
					<thead>
						<tr>
							<th data-field="matricule"  data-align="left" data-sortable="true">Matricule</th>
							<th data-field="nomPrenom"  data-align="left" data-sortable="true">Nom</th>
							<th data-field="nombrePart"  data-align="left">Nbre part</th>
							<th data-field="anciennete"  data-align="center">Anciennet�</th>
							<th data-field="mtsalaireBase"   data-align="left">Salaire Base</th>
							<th data-field="mtsursalaire"  data-align="center">Sursalaire</th>
							<th data-field="mtprimeAnciennete"  data-align="center">Prime. Anciennet�</th>
							<th data-field="mtindemniteLogement"  data-align="right">Indem logement</th>
							<th data-field="mtbrutImposable" data-align="right">Brut imposable</th>
							<th data-field="mtits" data-align="right">ITS</th>							
							<th data-field="mtcn"  data-align="left" data-sortable="true">CN</th>
							<th data-field="mtigr"  data-align="left" data-sortable="true">IGR</th>
							<th data-field="mttotalRetenueFiscale"  data-align="left">Retenue fiscale</th>
							<th data-field="mtcnps"  data-align="center">CNPS</th>
							<th data-field="mtavceAcpte"   data-align="left">Avance & Acompte</th>
							<th data-field="mtpretAlios"  data-align="center">ALIOS</th>
							<th data-field="mtcarec"  data-align="center">Carec</th>
							<th data-field="mttotalRetenue"  data-align="right">Total retenu</th>
							<th data-field="mtindemniteRepresentation" data-align="right">Indem de repres.</th>
							<th data-field="mtindemniteTransport" data-align="right">Indem de transport.</th>
							
							<th data-field="mtnetPayer"  data-align="center">Net � payer</th>
							<th data-field="mttotalBrut"   data-align="left">Total brut</th>
							<th data-field="mtis"  data-align="center">IS</th>
							<th data-field="mtta"  data-align="center">TA</th>
							<th data-field="mtfpc"  data-align="right">FPC</th>
							<th data-field="mtprestationFamiliale" data-align="right">Prest familiale</th>
							<th data-field="mtaccidentTravail" data-align="right">Acc de travail</th>
							<th data-field="mtretraite" data-align="right">Retraite</th>
							<th data-field="mttotalPatronal" data-align="right"> Total patronal</th>
							<th data-field="mttotalMasseSalariale" data-align="right">Total masse salariale</th>
						
							
							<!-- <th data-field="id" data-formatter="optionFormatter1" data-align="center">Options</th>  -->
							
						</tr>
					</thead>
				</table>
			
                	
                </div>
                <div class="modal-footer">
                	<span></span>&nbsp;
                	<!-- <input type="hidden"  id="idPeriode" name="id"> -->
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" onclick="genererLivreDePaie()" class="btn btn-success">Valider</button>
                    
                </div>
            </form>
        </div>
    </div>
</div>



<!-- Modification info du personnel -->
<div class="modal fade" id="rhpModalModif" tabindex="-1" role="dialog" aria-labelledby="rhpModalModif" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formModificationPersonnel" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">Modification du personnel (<span id="infoPersonnel">Information du personnel</span>)</h4>
				</div>
                <div class="modal-body">
                	<div class="form-group">
                        <label for="libelle" class="col-md-4 control-label">Nombre d'enfants</label>
                        <div class="col-md-8">
                            <input type="text" id="nombreenfant" name="nombreenfant" class="form-control" placeholder="0" />
                        </div>
                    </div>
                	<div class="form-group">
                        <label for="salaireDeBase" class="col-md-4 control-label">Situation matrimoniale</label>
                        <div class="col-md-8">
                            <select id="situationmatrimoniale" name="situationmatrimoniale" class="form-control select2">
							
								<option value="1">Mari&eacute;(e)</option>
								<option value="2">C&eacute;libataire</option>
								<option value="2">Veuf/Veuve</option>
							</select>
                        </div>
                    </div>
                	<div class="form-group">
                        <label for="statut" class="col-md-4 control-label">Statut</label>
                        <div class="col-md-8">
                            <select id="statut" name="statut" class="form-control select2">
                                  
								  <option value="true">Actif</option>
								  <option value="false">En sommeil</option>
							</select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="hidden" value="" id="idPersonnel" name="idPersonnel">
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


<div class="modal fade" id="rhpModalImprimer"  role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static" >
    <div class="modal-dialog" role="document" style="width:80%;" >
        <div class="modal-content">
            <form id="formImrimBull" class="form-horizontal" >
                <div class="modal-header">
<!--                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
                    <h4 class="modal-title" id="myModalLabel">Imprimer les bulletins de paie de  ${periode}</h4>
				</div>
                <div class="modal-body">
              <!--   <div id="toolbar">
  					<select class="form-control">
    					<option value="">Export Basic</option>
    					<option value="all">Export All</option>
    					<option value="selected">Export Selected</option>
 					 </select>
			  </div> -->
             <!--        <div class="columns columns-right btn-group pull-right">
                <div class="export btn-group open"><button class="btn btn-default dropdown-toggle" data-toggle="dropdown" type="button">
                <i class="glyphicon glyphicon-export icon-share"></i> <span class="caret"></span></button><ul class="dropdown-menu" role="menu">
            
                <li data-type="excel"><a href="javascript:exports()">MS-Excel</a></li>
                </ul>
                </div>
                </div> -->
              <!--   <div id="toolbar">
			    <div class="form-inline">
			        <button id="btnGenerer5" type="submit" data-toggle="modal" data-target="#rhpModal5 " title="Cocher les employ�s mis en sommeil" class="btn btn-primary btn-xs"><i class="fa fa-plus"></i>G�n�rer tous les bulletins</button>
			    </div>
			   </div> -->
                
              <form action="#" id="formList">
				<table id="tableBull" class="table table-info table-striped"
					data-toggle="table" 
					data-click-to-select="true"
					data-single-select="false"					
					data-show-export="true" 
					data-click-to-select="true"
					data-sort-name="name"
					data-url="${pageContext.request.contextPath}/paie/listbulletinMoisActif"
					data-sort-order="desc"
					data-show-columns="true" 
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[10, 20, 50, 100, 200]" 
					data-search="true">
					<thead>
						<tr>
							<th data-field="contratPersonnel" data-formatter="matriculesFormatter"  data-align="left" data-sortable="true">Matricule</th>
							<th data-field="contratPersonnel" data-formatter="nomcompletFormatter" data-align="left" data-sortable="true">Nom</th>
							<th data-field="nombrePart"  data-align="left">Nbre part</th>
							<th data-field="anciennete"  data-align="center">Anciennet�</th>
							<th data-field="montantSalairbase"   data-align="left">Salaire Base</th>
							<th data-field="montantSursalaire"  data-align="center">Sursalaire</th>
							<th data-field=montantPrimeanciennete  data-align="center">Prime. Anciennet�</th>
							<th data-field="montantIndemnitelogement"  data-align="right">Indem logement</th>
							<th data-field="brutImpo" data-align="right">Brut imposable</th>
							<th data-field="montantIts" data-align="right">ITS</th>							
							<th data-field="montantCn"  data-align="left" data-sortable="true">CN</th>
							<th data-field="montantIgr"  data-align="left" data-sortable="true">IGR</th>
							<th data-field="montanttotalretenuefiscal"  data-align="left">Retenue fiscale</th>
							<th data-field="montantCnps"  data-align="center">CNPS</th>
							<th data-field="montantavanceetacompte"   data-align="left">Avance & Acompte</th>
							<th data-field="montantpretaloes"  data-align="center">ALIOS</th>
							<th data-field="montantcarec"  data-align="center">Carec</th>
							<th data-field="montanttotalretenue"  data-align="right">Total retenue</th>
							<th data-field="indemniteRepresent" data-align="right">Indem de repres.</th>
							<th data-field="indemniteTransp" data-align="right">Indem de transport.</th>
							
							<th data-field="montantNetapayer"  data-align="center">Net � payer</th>
							<th data-field="montanttotalbrut"   data-align="left">Total brut</th>
							<th data-field="montantIs"  data-align="center">IS</th>
							<th data-field="montantTa"  data-align="center">TA</th>
							<th data-field="montantFpc"  data-align="right">FPC</th>
							<th data-field="prestationFamil" data-align="right">Prest familiale</th>
							<th data-field="accidentTrav" data-align="right">Acc de travail</th>
							<th data-field="montantRetraite" data-align="right">Retraite</th>
							<th data-field="montanttotalpatronal" data-align="right"> Total patronal</th>
							<th data-field="montanttotalmassesalarial" data-align="right">Total masse salariale</th>
							 
							
							<th data-field="id" data-formatter="optionFormatter2" data-align="center">Options</th> 
							
						</tr>
					</thead>
				</table>
			
                	
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
/* app.controller('formGenerer', function ($scope) {
    $scope.pupulateForm = function (fonction) {
        $scope.fonction = fonction;
=======
app.controller('modificationPersonnelCtrl', function ($scope) {
    $scope.populateForm = function (presonnel) {
        $scope.personnel = presonnel;
>>>>>>> .r16
    };
});/* .controller('formDeleteCtrl', function ($scope) {
    $scope.pupulateForm = function (fonction) {
    	$scope.fonction = fonction;
    };

}); */
//End AngularJs
var contextPath = "<%=request.getContextPath() %>";
var actionUrl = "/paie/savebullPersonnel";
var action = "ajout";
var $table;
jQuery(document).ready(function($) {
	
	jQuery('#tableWidgethisto').hide();
	jQuery('#tableWidget').show();
	jQuery( ".select2" ).select2();
	var periode='';var periodeID='';
	chargerPeriodePaie()
	periode='${activeMois}';
	periodeID='${activeMoisId}';
	jQuery('#idPeriode1').val(periodeID);
	jQuery('#idperiod').val(periodeID);
	$table = $('#table');
	//alert(periode);
	//alert(periodeID);
	jQuery('#periodePaie').html(periode);
	jQuery('#idPeriode').val(periodeID);
	//alert(jQuery('#idPeriode').val());
	//Envoi des donnees
	
	jQuery("#jourTravail").blur(function(){
		var nbjou;
		nbjou = $("#jourTravail").val();
		jQuery("#temptravail").val((nbjou*173.33)/30);
		
	});
	
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
            	jQuery("#rhpModalModif .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
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
	
	jQuery("#formImrimBull").submit(function(e){
//	alert(id);
		var id='${activeMoisId}';
		location.href = baseUrl +  "/paie/bulletinMoisPersonnel?idbul="+id; 
/* 		jQuery.ajax({
	        type: "GET",
	        url: baseUrl+"/paie/bulletinMoisPersonnel",
	        cache: false,
	        data: {
	        	idbul: id
	        },
			success: function (response) {
	        	if (response != null) {
	        		//alert(response.result);
	        		//jQuery("#rhpModal").modal('hide');
				} else {
					alert('Impossible de charger cet objet');
				}
	        },
	        error: function () {
	            
	        }
	    }); */
	});
	function chargerPeriodePaie(){
		jQuery.ajax({
	        type: "POST",
	        url: baseUrl + "/parametrages/periodecloturee",
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
            	jQuery("#rhpModalModif .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
            	jQuery("#rhpModalModif .modal-footer span").removeClass('loader');
            },
            beforeSend: function () {
            	jQuery("#formModificationPersonnel").attr("disabled", true);
            	jQuery("#rhpModalModif .modal-footer span").addClass('loader');
            },
            complete: function () {
            	jQuery("#formModificationPersonnel").removeAttr("disabled");
            	jQuery("#rhpModalModif .modal-footer span").removeClass('loader');
            	$table.bootstrapTable ('refresh', {  url: baseUrl +'/contratpersonnel/listcontratpersonnelActifjson' });
            	
            }
        });
	});

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
            	//alert('verger');
            }
            else if(response.result == "erreur_champ_obligatoire"){
            	alert("Saisie invalide");
            }
        },
        error: function () {
        	jQuery("#rhpModal .modal-body div.alert").alert();
        	jQuery("#rhpModal .modal-body .alert h4").html("Erreur survenue");
        	jQuery("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
        	jQuery("#rhpModal .modal-footer span").removeClass('loader');
        },
        beforeSend: function () {
        	jQuery("#formAjout").attr("disabled", true);
        	jQuery("#rhpModal .modal-footer span").addClass('loader');
        },
        complete: function () {
        	jQuery("#formGenerer").removeAttr("disabled");
        	jQuery("#rhpModal .modal-footer span").removeClass('loader');
       	jQuery ('#tableliv'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/savebullPersonnel?id='+ jQuery('#idPeriode').val()});
        }
    });
});

function genererLivreDePaie(){
	$tablebull = jQuery('#tableBull');
	jQuery.ajax({
        type: "GET",
        url: baseUrl+"/paie/savelivrepaie",
        cache: false,
		success: function (response) {
        	if (response != null) {
        		alert(response.result);
        		jQuery("#rhpModalValider").modal('hide');
			} else {
				alert('Impossible de charger cet objet');
			}
        },
        error: function () {
            
        },
        complete: function () {
        //	jQuery("#formGenerer").removeAttr("disabled");
        //	jQuery("#rhpModal .modal-footer span").removeClass('loader');
       	$tablebull.bootstrapTable('refresh', {  url: baseUrl +'/paie/bulletinperiodeactifjson?id='+ jQuery('#idPeriode').val()});
        }
    });
}
function optionFormatter(id, row, index) {
	var option = '<a onclick="selectPersInfo('+id+','+row.personnel.id+')" data-toggle="modal" data-target="#rhpModalModif" href="#" title="Modifier personnel [LIBELLE : '+row.personnel.matricule+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	option += '<a onclick="cherch2('+row.personnel.id+')" data-toggle="modal" data-target="#rhpModalModiftemps" href="#" title="Modifier personnel [LIBELLE : '+row.matricule+' ]">  <span class="glyphicon glyphicon-list"></span></a>&nbsp;';
	option += '&nbsp;<a data-toggle="modal" href="#" title="Suprimer bulletin [LIBELLE : '+row.libelle+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';
	
    return option;
}

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

function nomFormatter(personnel, row, index) {
	if(row.personnel.nom == ''){
		return "";
	}
	return row.personnel.nom+" "+row.personnel.prenom;
}



function sommeilFormatter(personnel, row, index) {
	var optionActif = '<small class="label label-danger"><i class="fa fa-clock-o"></i> -- Desactiv� </small>';
	if(row.personnel.statut == true)
		optionActif = '<small class="label label-success"><i class="fa fa-clock-o"></i> -- Activ� </small>';
	
	return optionActif;
}
function somFormatter(contratPersonnel, row, index) {
	if(row.contratPersonnel.personnel.matricule == ''){
		return "";
	}
	return row.contratPersonnel.personnel.matricule;
}


function matriculesFormatter(contratPersonnel, row, index) {
	if(row.contratPersonnel.personnel.matricule == ''){
		return "";
	}
	return row.contratPersonnel.personnel.matricule;
}

function nomcompletFormatter(contratPersonnel, row, index) {
	if(row.contratPersonnel.personnel.nom == ''){
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

	jQuery('#idPersonnel').val(idFonction);
	jQuery.ajax({
        type: "GET",
        url: baseUrl+"/personnels/cherchpersonnel",
        data: {idPersonnel: idFonction},
        cache: false,
		success: function (response) {
        	if (response != null) {
        	//	alert(response.result);
        		jQuery('#idPersonnel').val(response.id);
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
jQuery("#formModificationPersonneltemps").submit(function(e){	
	//e.preventDefault();
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
        		alert('Operation effexctu�e avec succes');
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
function widgetView(view){
	if(view == 'histo'){
		jQuery('#tableWidget').hide('slow');	
		jQuery('#tableWidgethisto').show('slow');
	} else{
		
		jQuery('#tableWidget').show('slow');	
		jQuery('#tableWidgethisto').hide('slow');
	}
	
}
function chargerbulletinParPeriode(){
	$tableImprimer = jQuery('#tableBull');
	$tableImprimer.bootstrapTable('removeAll');
	$tableImprimer.bootstrapTable ('refresh', { url: baseUrl +'/paie/chargerbulletinparperiode?id='+ jQuery('#periodePaieImpression').val()});
    $tableImprimer.bootstrapTable('scrollTo', 0);
  
}

var $table = jQuery('#tableliv'),
selections = [];

jQuery(function () {
$table.on('check.bs.table check-all.bs.table ' +
        'uncheck.bs.table uncheck-all.bs.table', function (e, rows) {
    var ids = $.map(!$.isArray(rows) ? [rows] : rows, function (row) {
            return row.id;
        }),
        func = $.inArray(e.type, ['check', 'check-all']) > -1 ? 'union' : 'difference';

    selections = _[func](selections, ids);
});
});

</script> --%>