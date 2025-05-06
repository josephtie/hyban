<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
.form-group {
    margin-bottom: 10px; /* Réduit l'espacement entre les groupes */
}

.form-control {
    height: 30px; /* Diminue la hauteur des champs */
    padding: 5px; /* Réduit l'espacement intérieur */
    font-size: 14px; /* Ajuste la taille du texte */
}

label {
    font-size: 13px; /* Réduit la taille des labels */
    margin-bottom: 2px; /* Diminue l'espacement sous le label */
}


</style>
<div class="row" >
	<div class="col-md-12">

		<div class="panel panel-warning">
			<div class="panel-heading">
				<div class="panel-title-box">
					<h3>Simulation et Calcul inverse</h3>
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
			<div class="panel-body panel-body">
         
                <form id="formMouvementConge" >
                    <h3 style="margin-top: 30px;">Saisie des valeurs</h3>
                     <input type="text"class="hidden"  id="lpom" name="lpom" value=""/>
                    <br>
                    <div class="row">
                    <div class="form-group">

						<div class="col-md-3">
							<label>Salaire Categoriel</label>
							<select class="form-control input-small select2" id="categorie" name="categorie" required="required">
								<c:forEach items="${listeCategorie}" var="prime">
									<option value="${prime.id}">${prime.libelle} ${prime.salaireDeBase}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-3">
							<label>Indemnit&eacute; Transport<span class="required">*</span></label>
							<input type="number" class="form-control " id="montantTransp" name="montantTransp" placeholder="Indemnite Transport"   required="required" ><!-- //ng-model="mouvementConge.montant" -->
						</div>
						<div class="col-md-3">
							<%--<label>Indemnit&eacute; Logement</label>--%>
							<%--<input type="text" class="form-control montant" id="montantLogmt" name="montantLogmt" placeholder="Indemnite Logement"  ><!-- ng-model="mouvementConge.montantVerse -->--%>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group">

							<div class="col-md-3">
								<label for="situationmatrimoniale">Situation matrimoniale <span class="required">*</span></label>
								<select id="situationmatrimoniale" name="situationmatrimoniale" class="form-control select2" required="required">
									<option>-- Choix de la situation --</option>
									<option value="1" selected="selected"> MARIE </option>
									<option value="2" > CELIBATAIRE </option>
									<option value="3" > DIVORCE </option>
									<option value="4" > VEUF(VE) </option>
								</select>
							</div>
							<div class="col-md-3">
								<label for="nombreenfant">Nombre d'enfant <span class="required">*</span></label>
								<select id="nombreenfant" name="nombreenfant" class="form-control select2" required="required">
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
							<div class="col-md-3"></div>


						</div>
					</div>
                    <div class="row">
                    <div class="form-group">
                    	<div class="col-md-3">
                    				<label>Salaire Net</label>
                    								<input type="number" class="form-control " id="salaireNet" name="salaireNet" placeholder="salaire Net"   required="required" ><!-- //ng-model="mouvementConge.montant" -->
                    		</div>
                           <div id="actionMouvementConge" class="col-md-3">
                                <span>Confirmer</span>&nbsp;

                                <%--<input type="text"class="hidden" ng-hide="true"  name="idPersonnelp" ng-model="primePersonnel.personnel.id" value=""/>--%>
                                <%----%>
                                 <%--<input type="text" class="hidden"  value="" id="idP" name="idP" >--%>
                               <!--  <button type="button" id="btnCancelMouvementConge" class="btn btn-default">Annuler</button> -->
                                <button type="submit" class="btn btn-success">Valider</button>
                            </div>
                        </div>
                             <div id="conge" class="col-md-3 "> </div>

                    </div>

                </form>
                <p>&nbsp;</p>
                <div id="toolbarMouvementConge" >
                   <%--<div class="form-inline">--%>
                        <%--<button type="button" class="btn btn-primary" title="Nouveau cong�" id="btnAddMouvementConge"><span class="glyphicon glyphicon-plus"></span> Refresh</button>--%>
                    <%--</div>--%>
                </div>
                <table id="tableConge"  class="table table-info table-striped"
                       data-toggle="table"
                       data-toolbar="#toolbarMouvementConge"
                       data-single-select="true"
                       data-sort-order="desc"
					   data-side-pagination="server"
					   data-pagination="true"
					   data-page-list="[20, 25, 50, 100, 200,500]"
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
						<%--<th data-field="mtprimeAnciennete"  data-align="center">Prime. Anciennet&eacute;</th>--%>
						<%--<th data-field="mtindemniteLogement"  data-align="right">Indem logement</th>--%>
						<%--<th data-field="mtindemniteTransportImp"  data-align="right">Transp Imp</th>--%>
						<%--<th data-field="mtautreIndemImposable"  data-align="right">Indem Imp</th>--%>

						<c:forEach items="${listePrimesImp}" var="rubrique" >
							<th  data-field="prime${rubrique.id}" data-rubrique="${rubrique.id}" data-formatter="primeFormatter" data-align="right">${rubrique.libelle}</th>
						</c:forEach>
						<c:forEach items="${listePrimesImposetNon}" var="rubrique" >
							<th  data-field="primeI${rubrique.id}" data-rubrique="${rubrique.id}" data-formatter="primeFormatterI" data-align="right">${rubrique.libelle}</th>
						</c:forEach>

						<%--<th data-field="mtautreImposable"  data-align="right">Autre Primes Imp</th>--%>
						<th data-field="mtbrutImposable" data-align="right">Brut imposable</th>
						<%--<th data-field="mtindemniteResponsabilte" data-align="right">Indem de respons.</th>--%>
						<%--<th data-field="mtindemniteRepresentation" data-align="right">Indem de repres.</th>--%>
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
						<%--<th data-field="mtcn"  data-align="left" data-sortable="true">CN</th>
						<th data-field="mtigr"  data-align="left" data-sortable="true">IGR</th>--%>

						<th data-field="mttotalRetenueFiscale"  data-align="left">Retenue fiscale</th>
						<th data-field="mtbasecnps"  data-align="left">base Cnps</th>

						<th data-field="mtcnps"  data-align="center">CNPS</th>
						<th data-field="cmu"  data-align="center">CMU Salarial</th>
						<%--<th data-field="mtavceAcpte"   data-align="left">Avance & Acompte</th>--%>
						<%--<th data-field="mtpretAlios"  data-align="center">Pret</th>--%>
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
						<th data-field="mtprestationFamiliale" data-align="right">Prest familiale</th>
						<th data-field="mtaccidentTravail" data-align="right">Acc de travail</th>
						<th data-field="mtretraite" data-align="right">Retraite</th>
						<th data-field="cmupatronal"   data-align="left">CMU Patronal</th>
						<th data-field="mttotalPatronal" data-align="right"> Total patronal</th>
						<th data-field="mttotalMasseSalariale" data-align="right">Total masse salariale</th>


						<!-- <th data-field="id" data-formatter="optionFormatter1" data-align="center">Options</th>  -->

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

//End AngularJs

var actionUrl = "/paie/enregisterlivrepaie";
var actionDeleteUrl = "/paie/delpretIndividuel";
var action = "ajout";
var $table;
jQuery(document).ready(function($) {


	$table = jQuery('#tablef');
	  $tableConge = $('#tableConge');
	jQuery( ".select2" ).select2();
	jQuery("#dEmprunt").datepicker({
		dateFormat: 'dd/mm/yy',
        showOtherMonths:true
    });

    if ($('.montant')) {
        $('.montant').css('text-align', 'right');
        $('.montant').attr('maxlength', '18');

        $('.montant').keyup(function (event) {
            this.value = this.value.replace(/[^0-9]/g, '');
        });
        $('.montant').keydown(function (event) {
            this.value = this.value.replace(/[^0-9]/g, '');
        });
        $('.montant').keypress(function (event) {
            this.value = this.value.replace(/[^0-9]/g, '');
        });

        $('.montant').change(function (event) {

            if (this.value != "") {
                var nombre = this.value;

                var nombre = new Intl.NumberFormat("fr-FR").format(nombre);

                this.value = nombre;

            }


        });
    }

});

function optionFormatter(id, row, index) {
	var option = '<a onclick="listMouvementConge('+id+','+row.personnel.id+')" data-toggle="modal" data-target="#listPrimesDiversModal" href="#" title="Ajouter pr�t [LIBELLE : '+row.personnel.nom+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
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
jQuery("#formMouvementConge").submit(function (e) {
    e.preventDefault();
    var id,categorie, montantTransp, situationmatrimoniale, salaireNet, nombreenfant, typebeneficiaireRemettant, membre, groupe, responsable, beneficiaireRemettant,contact,mmontantop,modepaiemt;

    //id = $('#formAjout').find('#id').val();
    categorie = $('#formMouvementConge').find('#categorie').val();
    console.log("categorie",categorie);
    montantTransp = $('#formMouvementConge').find('#montantTransp').val();
    console.log("montantTransp",montantTransp);
    situationmatrimoniale = $('#formMouvementConge').find('#situationmatrimoniale').val();
    console.log("situationmatrimoniale",situationmatrimoniale);
    nombreenfant = $('#formMouvementConge').find('#nombreenfant').val();
    console.log("nombreenfant",nombreenfant);
    salaireNet = $('#formMouvementConge').find('#salaireNet').val();
    console.log("description",salaireNet);
    jQuery.ajax({
        type: "GET",
        url: baseUrl + "/paie/simulcalculalenvers",
        cache: false,
        data: {
            categorie:categorie,
            montantTransp:montantTransp,
            situationmatrimoniale:situationmatrimoniale,
            nombreenfant:nombreenfant,
            salaireNet:salaireNet,
		},
        success: function (reponse) {
            if (reponse.result == true) {

                //jQuery('#lpom').val(reponse.contratPersonnel.id);
             //   $tableConge.bootstrapTable('load',reponse.row);
             //   loadMouvementCongeByPersonnel(idPersonnel)
				//toastr.success(" Simulation reussie")
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
         $tableConge.bootstrapTable('refresh', { url: baseUrl +"/paie/simulcalculalenvers?categorie="+ jQuery('#categorie').val()+"&montantTransp="+jQuery('#montantTransp').val()+"&situationmatrimoniale="+jQuery('#situationmatrimoniale').val()+"&nombreenfant="+jQuery('#nombreenfant').val()+"&salaireNet="+jQuery('#salaireNet').val()});
            jQuery("#formMouvementConge")[0].reset();
        }
    });
    return false;
});
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

function listMouvementConge(idCtrat,idPersonnel) {
	jQuery('#lpom').val(idCtrat);

    loadMouvementCongeByPersonnel(idPersonnel);
    loadContratPersonnel(idPersonnel);
    jQuery('#idPersonnelp').val(idPersonnel);
    jQuery('#lpom').val(idCtrat);
    console.log(idCtrat);
 
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
	var option = '<a onclick="cherchprime(\''+row.id+'\')"  href="#" title="Modifier prime [LIBELLE : '+row.prime.libelle+' ]">  <span class="glyphicon glyphicon-pencil"></span></a>&nbsp;';
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
	//jQuery('#lpom').val(idFonction);
//	jQuery('#infoPersonnelmo1').html(idFonction);
//	jQuery('#idPersonnel').val(idFonction);
//	jQuery('#idpersc').val(idFonction);
	jQuery.ajax({
        type: "POST",
        url: baseUrl+"/paie/delpretIndividuel1",
        data: {id: idFonction},
        cache: false,
		success: function (response) {
        	if (response != null) {
        		jQuery("#formMouvementConge")[0].reset(); 
        		$tableConge.bootstrapTable('load', response.rows);
        	//loadDataToForm(response.id,response.montant,response.valeur,response.prime.id,response.contratPersonnel.id);
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


/* function editPret(idFonction){
//	var $scope = angular.element(document.getElementById("formDelete")).scope();

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
                jQuery("#formMouvementConge")[0].reset();
                loadDataToForm(response.id,response.montant,response.valeur,response.prime);
                /* 	jQuery('#montant1').val(response.montant);
                    jQuery('#periodepaie1').val(response.periode.id);
                    jQuery("#periodepaie1").val(response.periode.id).trigger('change');
                    jQuery('#periodepaie1').trigger('liszt:updated');
                    jQuery('#dEmprunt1').val(response.dEmprunt);
                    jQuery('#echelonage1').val(response.echelonage);
                    jQuery('#idpers1').val(response.personnel.id);
                    jQuery('#idpret').val(response.id); */

                //tabledata += "";
                /* jQuery('#typeService, #typeServicePop').html(tabledata);
                jQuery('#typeService, #typeServicePop').val("1").trigger("change");
            } else {
                alert('Failure! An error has occurred!');
            }
        },
        error: function () {

        },
    });
    jQuery('#tablef'). bootstrapTable ('refresh', {  url: baseUrl +'/paie/pretPersonneljson' });
} */
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