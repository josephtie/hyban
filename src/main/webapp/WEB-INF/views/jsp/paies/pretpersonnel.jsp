<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
	<div class="col-md-12">
		<div class="panel panel-warning">
			<div class="panel-heading">
				<div class="panel-title-box">
					<h3>Liste des Emplois/Fonctions</h3>
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
							<br><br><br>
		<div id="tableWidget" class="widgetcontent">
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

<!-- Generation des bulletins -->
<%-- <div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
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
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                </div>
            </form>
        </div>
    </div>
</div> --%>

<!-- Pret -->
<div class="modal main-popup fade" id="rhpModalPret"  role="dialog"  aria-labelledby="rhpModalPret" data-backdrop="static">
    <div class="modal-dialog" role="document" style="width:60%;">
        <div class="modal-content">
            <div class="modal-header">
            	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Pret / Avance</h4>
			</div>
            <div class="modal-body">
               	<div class="row">
               		<div class="col-sm-5">
               			<div class="widgetbox" >                        
	                        <div class="headtitle">
	                            <h4 class="widgettitle">Informations personnelles</h4>
	                        </div>
	                        <div class="widgetcontent">
	                            <div class="form-group">
			                        <label class="col-md-3 control-label">Photo</label>
			                        <div class="col-md-9">
			                            
			                        </div>
			                    </div>
	                            <div class="form-group">
			                        <label class="col-md-3 control-label">Matricule</label>
			                        <div class="col-md-9">
			                         <label id="matricules" ></label>			                            
			                        </div>
			                    </div>
	                            <div class="form-group">
			                        <label class="col-md-3 control-label">Nom</label>
			                        <div class="col-md-9">			                      
			                           <label id="nomcomplet" ></label>	
			                        </div>
			                    </div>
	                            <div class="form-group">
			                        <label class="col-md-3 control-label">N&eacute;(e) le</label>
			                        <div class="col-md-9">
			                           <label id="dnaiss" ></label>	
			                        </div>
			                    </div>
	                            <div class="form-group">
			                        <label class="col-md-3 control-label">A</label>
			                        <div class="col-md-9">
			                           <label id="lieunaiss" ></label>	
			                        </div>
			                    </div>
	                            <div class="form-group">
			                        <label class="col-md-3 control-label">Sexe</label>
			                        <div class="col-md-9">
			                           <label id="sexe" ></label>	
			                        </div>
			                    </div>
	                            <div class="form-group">
			                        <label class="col-md-3 control-label" id="typeserv" ></label>
			                        <div class="col-md-9">
			                         <label id="serv" ></label>
			                        </div>
			                    </div>
	                        </div><!--widgetcontent-->
	                    </div>
               		</div>
               		<div class="col-sm-7">
               			<form id="formPret" action="#">
                			<div class="widgetbox">                        
		                        <div class="headtitle">
		                            <h4 class="widgettitle">Pret / Avance</h4>
		                        </div>
		                        <div class="widgetcontent" style="padding-bottom:0px;">
		                            <div class="form-group">
				                        <label for="service" class="col-md-4 control-label">Type</label>
				                        <div class="col-md-8">
				                            <select id="pret" name="pret" class="form-control select2">
				                            <c:forEach items="${listPrets}" var="pret">
				                            	<option value="${pret.id}">${pret.libelle}</option>
				                            </c:forEach>
											</select>
				                        </div>
				                    </div>
		                            <div class="form-group">
				                        <label for="montant" class="col-md-4 control-label">Montant</label>
				                        <div class="col-md-8">
				                            <input type="text" id="montant" name="montant" class="form-control" placeholder="Montant" />
				                        </div>
				                    </div>
		                            <div class="form-group">
				                        <label for="" class="col-md-4 control-label">Periode debut prelev.</label>
				                        <div class="col-md-8">
				                            <select id="periodepaie" name="periodepaie" class="form-control select2">
											<c:forEach items="${listPeriodepaie}" var="periodePaie">
				                            	<option value="${periodePaie.id}">${periodePaie.mois.mois} ${periodePaie.annee.annee}</option>
				                            </c:forEach>
											</select>
				                        </div>
				                    </div>
		                            <div class="form-group">
				                        <label for="" class="col-md-4 control-label">Date contraction</label>
				                        <div class="col-md-8">
				                            <input type="text" id="dEmprunt" name="dEmprunt" class="form-control" placeholder="Date contraction" />
				                        </div>
				                    </div>
		                            <div class="form-group">
				                        <label for="" class="col-md-4 control-label">Mensualite</label>
				                        <div class="col-md-8">
				                            <input type="text" id="echelonage" name="echelonage" class="form-control"  />
				                             <input type="hidden" id="idpers" name="idpers" class="form-control"  />
				                          
				                        </div>
				                    </div>
		                            <!-- <div class="form-group">
				                        <label class="col-md-4 control-label">Pr�l�vement/P�riode</label>
				                        <div class="col-md-8">
											{{prelevement}}
				                        </div>
				                    </div> -->
				                    <div class="form-group">
				                        <div class="col-md-12">
				                        	<!-- <span class="pull-right"> -->
				                        	 <span></span>&nbsp;
				                        	
				                        		<input class="btn btn-default" type="button" data-dismiss="modal" value="Annuler" />&nbsp;
				                            	<input class="btn btn-success" type="submit" value="Valider" />
				                        	<!-- </span> -->
				                        </div>
				                    </div>
		                        </div><!--widgetcontent-->
		                    </div>
		                 </form>
               		</div>
               	</div>
               	
               	<div class="row">
               		<div class="col-sm-12">
                        <div class="widgetbox">                        
	                        <div class="headtitle">
	                            <h4 class="widgettitle">Historiques des prets</h4>
	                        </div>
	                        <div class="widgetcontent">
	                            <table id="tablef" class="table table-info table-striped"
								data-toggle="table" data-click-to-select="true"
					data-single-select="false"
					data-sort-name="id"
					data-sort-order="desc"
					data-url="${pageContext.request.contextPath}/paie/pretPersonneljson"
					data-side-pagination="server" 
					data-pagination="true"
					data-page-list="[10, 20, 50, 100, 200]" 
					data-search="true">
									<thead>
										<tr>
											<th data-field="personnel" data-formatter="matrisFormatter" data-align="left" data-sortable="true">Matricule</th>
											<th data-field="personnel" data-formatter="nomsFormatter"  data-align="left" data-sortable="true">Nom</th>
											<th data-field="pret" data-formatter="pretFormatter" data-align="left">Pret</th>
											<th data-field="montant" data-align="left">Montant</th>
											<th data-field="echelonage" data-align="left">Modalites</th>
											<th data-field="dEmprunt" data-align="center">Date</th>
											<th data-field="periode" data-formatter="periodeFormatter" data-align="center">Periode</th>											
											<th data-field="id" data-formatter="pretiFormatter" data-align="center"></th>
										</tr>
									</thead>
								</table>
	                        </div><!--widgetcontent-->
	                    </div>
                   	</div>
                   </div>
            </div>
        </div>
    </div>
</div>

<div class="modal  fade " style="z-index: 2000" id="rhpModalPretModif" role="dialog" data-backdrop="static">
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


   <!-- Modification info du personnel -->
   <%--<div class="modal  sfade" id="rhpModalPretModif" style="z-index: 2000" role="dialog" aria-labelledby="rhpModalPretModif" data-backdrop="static">--%>
    <%--<div class="modal-dialog" >--%>
        <%--<div class="modal-content">--%>
           	<%--<form id="formPretmodif" action="#">--%>
                			<%--<div class="widgetbox">                        --%>
		                        <%--<div class="headtitle">--%>
		                            <%--<h4 class="widgettitle">Modification Pret / Avance</h4>--%>
		                        <%--</div>--%>
		                        <%--<div class="widgetcontent" style="padding-bottom:0px;">--%>
		                            <%--<div class="form-group">--%>
				                        <%--<label for="service" class="col-md-4 control-label">Type</label>--%>
				                        <%--<div class="col-md-8">--%>
				                            <%--<select id="pret1" name="pret1" class="form-control select2">--%>
				                            <%--<c:forEach items="${listPrets}" var="pret">--%>
				                            	<%--<option value="${pret.id}">${pret.libelle}</option>--%>
				                            <%--</c:forEach>--%>
											<%--</select>--%>
				                        <%--</div>--%>
				                    <%--</div>--%>
		                            <%--<div class="form-group">--%>
				                        <%--<label for="montant" class="col-md-4 control-label">Montant</label>--%>
				                        <%--<div class="col-md-8">--%>
				                            <%--<input type="text" id="montant1" name="montant1" class="form-control" placeholder="Montant" />--%>
				                        <%--</div>--%>
				                    <%--</div>--%>
		                            <%--<div class="form-group">--%>
				                        <%--<label for="" class="col-md-4 control-label">Periode debut prelev.</label>--%>
				                        <%--<div class="col-md-8">--%>
				                            <%--<select id="periodepaie1" name="periodepaie1" class="form-control select2">--%>
											<%--<c:forEach items="${listPeriodepaie}" var="periodePaie">--%>
				                            	<%--<option value="${periodePaie.id}">${periodePaie.mois.mois} ${periodePaie.annee.annee}</option>--%>
				                            <%--</c:forEach>--%>
											<%--</select>--%>
				                        <%--</div>--%>
				                    <%--</div>--%>
		                            <%--<div class="form-group">--%>
				                        <%--<label for="" class="col-md-4 control-label">Date contraction</label>--%>
				                        <%--<div class="col-md-8">--%>
				                            <%--<input type="text" id="dEmprunt1" name="dEmprunt1" class="form-control" placeholder="Date contraction" />--%>
				                        <%--</div>--%>
				                    <%--</div>--%>
		                            <%--<div class="form-group">--%>
				                        <%--<label for="" class="col-md-4 control-label">Mensualite</label>--%>
				                        <%--<div class="col-md-8">--%>
				                            <%--<input type="text" id="echelonage1" name="echelonage1" class="form-control"  />--%>
				                             <%--<input type="hidden" id="idpers1" name="idpers1" class="form-control"  />--%>
				                          <%----%>
				                        <%--</div>--%>
				                    <%--</div>--%>
		                            <%--<!-- <div class="form-group">--%>
				                        <%--<label class="col-md-4 control-label">Pr�l�vement/P�riode</label>--%>
				                        <%--<div class="col-md-8">--%>
											<%--{{prelevement}}--%>
				                        <%--</div>--%>
				                    <%--</div> -->--%>
				                    <%--<div class="form-group">--%>
				                        <%--<div class="col-md-12">--%>
				                        	<%--<!-- <span class="pull-right"> -->--%>
				                        	 <%--<span></span>&nbsp;--%>
				                                <%--<input type="hidden" id="idpret" name="idpret" class="form-control"  />--%>
				                        		<%--<input class="btn btn-default" type="button" data-dismiss="modal" value="Annuler" />&nbsp;--%>
				                            	<%--<input class="btn btn-success" type="submit" value="Valider" />--%>
				                        	<%--<!-- </span> -->--%>
				                        <%--</div>--%>
				                    <%--</div>--%>
		                        <%--</div><!--widgetcontent-->--%>
		                    <%--</div>--%>
		                 <%--</form>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>

<div class="modal deleteModal  fade bs-delete-modal-static" style="z-index: 2000" id="rhpModalPretDel" role="dialog" data-backdrop="static">
    <div class="modal-dialog ">
        <div class="modal-content">
	        <form id="formDelete"  action="#" method="post">
	            <div class="modal-header ">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	                <span class="circle bg-danger">
	                    <i class="fa fa-question-circle"></i>
	                   
	                </span>
	            </div>
	            <div class="modal-body">
	             Etes vous sur de vouloir supprimer cet pret ?
	            	<h4 id="labelPret"></h4>
	            </div>
	            <div class="modal-footer">
                	<input type="hidden"  id="idPretperso"  value="" name="idPretperso" >
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
app.controller('infoPersoCtrl', function ($scope) {
    $scope.pupulateForm = function (contratPersonnel) {
        $scope.personnel = contratPersonnel.personnel;
        $scope.libelleService = contratPersonnel.libelleService;
    };
}).controller('formDeleteCtrl', function ($scope) {
    $scope.pupulateForm = function (categorie) {
    	$scope.categorie = categorie;
    };

});
//End AngularJs

var actionUrl = "/paie/enregisterlivrepaie";
var actionDeleteUrl = "/paie/delpretIndividuel";
var action = "ajout";
var $table;
jQuery(document).ready(function($) {
	$table = jQuery('#tablef');
	jQuery( ".select2" ).select2();
	jQuery("#dEmprunt").datepicker({
		dateFormat: 'dd/mm/yy',
        showOtherMonths:true
    });
	//Envoi des donnees
		$("#formDelete").submit(function(e){
		e.preventDefault();
        var formData = $(this).serialize();       
      
        
        $.ajax({
            type: "POST",
            url: baseUrl + actionDeleteUrl,
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == true) {
                  
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
                jQuery('#tablef'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/pretPersonneljson' });
            }
        });
	});
	
	$("#formPretmodif").submit(function(e){
		e.preventDefault();
        var formData = $(this).serialize();
        console.log("form", formData);
        jQuery.ajax({
            type: "GET",
            url: baseUrl + "/paie/updatepretPersonnel",
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == "success") {
                	$table.bootstrapTable('refresh');
                	jQuery("#formPretmodif")[0].reset(); //Initialisation du formulaire
                	jQuery("#rhpModalPretModif").modal('hide');
                	alert(reponse.result);
                }
                else if(reponse.result == "erreur_champ_obligatoire"){
                	alert("Saisie invalide");
                }
            },
            error: function () {
            	jQuery("#rhpModalPret div.alert").alert();
            	jQuery("#rhpModalPret .alert h4").html("Erreur survenue");
            	jQuery("#rhpModalPret .alert p").html("Verifier que vous etes connectes au serveur.");
            	//$("#formPret span.load").removeClass('loader');
            },
            beforeSend: function () {
            	jQuery("#formPret").attr("disabled", true);
                jQuery("#formPret span.load").addClass('loader');
            },
            complete: function () {
            	jQuery("#formPretmodif").removeAttr("disabled");
                //$("#formPret span.load").removeClass('loader');
                jQuery('#tablef'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/pretPersonneljson' });
            }
        });
	});
	$("#formPret").submit(function(e){
		e.preventDefault();
        var formData = $(this).serialize();
        console.log("form", formData);
        jQuery.ajax({
            type: "POST",
            url: baseUrl + "/paie/savepretPersonnel",
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == "success") {
                	$table.bootstrapTable('refresh');
                	jQuery("#formPret")[0].reset(); //Initialisation du formulaire
                 //   $("#rhpModal").modal('hide');
            		alert('Operation effexctuee avec succes');
                }
                else if(reponse.result == "erreur_champ_obligatoire"){
                	alert("Saisie invalide");
                }
            },
            error: function () {
            	jQuery("#rhpModalPret div.alert").alert();
            	jQuery("#rhpModalPret .alert h4").html("Erreur survenue");
            	jQuery("#rhpModalPret .alert p").html("Verifier que vous �tes connect�s au serveur.");
            	//$("#formPret span.load").removeClass('loader');
            },
            beforeSend: function () {
            	jQuery("#formPret").attr("disabled", true);
                jQuery("#formPret span.load").addClass('loader');
            },
            complete: function () {
            	jQuery("#formPret").removeAttr("disabled");
                //$("#formPret span.load").removeClass('loader');
                jQuery('#tablef'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/pretPersonneljson' });
            }
        });
	});
});

function optionFormatter(id, row, index) {
	var option = '<a onclick="edit('+row.personnel.id+')" data-toggle="modal" data-target="#rhpModalPret" href="#" title="Ajouter pr�t [LIBELLE : '+row.personnel.nom+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
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
function pretiFormatter(id, row, index) {
	 var option = '<a onclick="editPret('+row.id+')" data-toggle="modal" data-target="#rhpModalPretModif" href="#" title="Ajouter pr�t [LIBELLE : '+row.personnel.nom+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
	option += '&nbsp;<a onclick="delPret('+row.id+')" data-toggle="modal" data-target="#rhpModalPretDel" href="#" title="Suprimer bulletin [LIBELLE : '+row.id+' ]"> <span class="glyphicon glyphicon-trash"></span></a>'; 
	
    return option;
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
function edit(idFonction){
//	var $scope = angular.element(document.getElementById("formDelete")).scope();
	
	var idp=idFonction;
	//alert(idp);
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/personnels/choisirpersonnelpret",
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

function editPret(idFonction){
//	var $scope = angular.element(document.getElementById("formDelete")).scope();
    //jQuery("#rhpModalPret").modal('hide');
	var idp=idFonction;
	//alert(idp);
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/paie/searchpretIndividuel",
        cache: false,
        data: {id:idp},
        success: function (response) {
        	if (response != null) {
        		console.log(response);
        		//jQuery('#pret1').html(response.matricule);
        		jQuery("#formPretmodif")[0].reset(); 
        		jQuery('#pret1').val(response.pret.id);
    			jQuery("#pret1").val(response.pret.id).trigger('change');
    			jQuery('#pret1').trigger('liszt:updated');
        		jQuery('#montant1').val(response.montant);
        		jQuery('#periodepaie1').val(response.periode.id);
    			jQuery("#periodepaie1").val(response.periode.id).trigger('change');
    			jQuery('#periodepaie1').trigger('liszt:updated');        	
        		jQuery('#dEmprunt1').val(response.dEmprunt);
        		jQuery('#echelonage1').val(response.echelonage);
        		jQuery('#idpers1').val(response.personnel.id);        		
        		jQuery('#idpret').val(response.id);
        		
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

function delPret(idFonction){
//	var $scope = angular.element(document.getElementById("formDelete")).scope();
	jQuery('#idPretperso').val(idFonction);
	
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/paie/searchpretIndividuel",
        cache: false,
        data: {id:idFonction},
        success: function (response) {
        	if (response != null) {
        		console.log(response);
        		//jQuery('#pret1').html(response.matricule);
        		jQuery("#formPretmodif")[0].reset(); 
        		jQuery('#pret1').val(response.pret.id);
    			jQuery("#pret1").val(response.pret.id).trigger('change');
    			jQuery('#pret1').trigger('liszt:updated');
        		jQuery('#montant1').val(response.montant);
        		jQuery('#periodepaie1').val(response.periode.id);
    			jQuery("#periodepaie1").val(response.periode.id).trigger('change');
    			jQuery('#periodepaie1').trigger('liszt:updated');        	
        		jQuery('#dEmprunt1').val(response.dEmprunt);
        		jQuery('#echelonage1').val(response.echelonage);
        		jQuery('#idpers1').val(response.personnel.id);        		
        		jQuery('#idpret').val(response.id);
        		jQuery('#idPretperso').val(response.id);
        		jQuery('#labelPret').html(response.personnel.nom+' '+response.pret.libelle+' '+response.montant);
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
	
}
</script>