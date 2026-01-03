<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
	<div class="col-md-12">
		<div class="panel panel-warning">
			<div class="panel-heading">
				<div class="panel-title-box">
					<h3>Historique des bulletins</h3>
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
	    	<div class="form-group">
                    <div class="col-md-4">
                        <div class="row">
                            <div class="col-md-4">			
                		<select id="periodePaieImpression" name="periodePaieImpression" class="form-control select2" required="required"></select>
                		<br/>
                	</div>
               	<div class="col-md-4">			
                		<input type="text" class="form-control input-default" id="search" name="search"  placeholder="Zone de recherche" >
                		<br/>
                	</div> 
                	 <div class="col-md-4">			
                		<button type="submit" class="btn btn-info" id="chargerbulletinParPeriode" >Rechercher</button>
                		<br/>
                	</div>                	
                	</div>
                	</div>

	  <table id="table" class="table table-info table-striped"
			         data-toggle="table"
                   data-click-to-select="true"
                   data-single-select="true"
                   data-show-columns="true"
                   data-show-export="true"
                   data-export-dataType="all"   	
                   data-pagination="true"
                   data-side-pagination="server"
                   data-page-list="[20, 50, 100, 200, 500,2000]"
                   data-show-export="true"
                   data-export-dataType="all"
                   data-search="true"
					>
					<thead>
						<tr>

								<th data-field="contratPersonnel" data-formatter="matriculesFormatter"  data-align="left" data-sortable="true">Matricule</th>
								<th data-field="contratPersonnel" data-formatter="nomcompletFormatter" data-align="left" data-sortable="true">Nom</th>
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
								<th data-field="cn"  data-align="left" data-sortable="true">CN</th>
								<th data-field="igr"  data-align="left" data-sortable="true">IGR</th>
								<th data-field="totalretenuefiscal"  data-align="left">Retenue fiscale</th>
								<th data-field="basecnps"  data-align="left">Base cnps</th>
								<th data-field="cnps"  data-align="center">CNPS</th>
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
								<th data-field="id" data-formatter="optionFormatterbull" data-align="center">Options</th>
							
						</tr>
					</thead>
		
				</table>
	    </div><!--widgetcontent-->
    </div><!--widgetbox-->
</div><!-- widgetcontent-->
				</div><!--widgetbox-->
			</div>
<script type="text/javascript">
//AngularJS


var actionUrl = "/personnels/enregistercategorie";
var actionDeleteUrl = "/personnels/supprimercategorie";
var action = "ajout";
var $table;
jQuery(document).ready(function($){
	$table = $('#table');
	jQuery( ".select2" ).select2();
	chargerPeriodePaie();
	//Fermeture du modal
});	
function optionFormatter(id, row, index) {
	var option = '<a onclick="edit('+id+')" data-toggle="modal" data-target="#rhpModal" href="#" title="Modifier categorie [LIBELLE : '+row.libelle+' ]">  <span class="glyphicon glyphicon-file"></span></a>&nbsp;';
	option += '&nbsp;<a onclick="del('+id+')" data-toggle="modal" data-target=".deleteModal" href="#" title="Suprimer categorie [LIBELLE : '+row.libelle+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';
	
    return option;
}


jQuery("#chargerbulletinParPeriode").click(function () {
	$table = jQuery('#table');
	$table.bootstrapTable('removeAll');
	$table.bootstrapTable ('refresh', {url: baseUrl +'/paie/chargerbulletinparperiode?id='+jQuery('#periodePaieImpression').val()+'&search1='+ jQuery('#search').val()});
    $table.bootstrapTable('scrollTo', 0);    
/*    jQuery.ajax({
        type: "GET",
        url: baseUrl + "/paie/chargerbulletinparperiode",
        cache: false,
        data: {
        	id: jQuery('#periodePaieImpression').val(),
        	search: jQuery('#search').val()
        },
        success: function (response) {
            console.log('chargement', response);
            if (response.result = true)
                if (response.length > 0)
                  for (i = 0; i < response.length; i++) {
                    response.rows.push({
                        id : response.rows[i].id
                    });

        }
            $table.bootstrapTable('load', response.rows);
       //
        },
        error: function () {

        },
        complete: function () {
        	//jQuery('#tableWidget').show('slow');	
    		//jQuery('#tableWidgethisto').show('slow');
        }
    }); */ 
    
    
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

function nomstatutFormatter(contratPersonnel, row, index) {
	if(row.contratPersonnel== ''){
		return "";
	}
	if(row.contratPersonnel.personnel.carec==true){
		   if(row.contratPersonnel.personnel.stage==true)
		        statfonct = "Stagiaire";
		   else
			   statfonct = "Fonctionnaire";
	}else{
		statfonct = "Contractuel";
	}
	return statfonct;
} 

function optionFormatterbull(id, row, index) {
	var option = '<a   target="_blank" href="${pageContext.request.contextPath}/paie/jrBulletinPersonnel?idbul='+row.id+'" title="Modifier Personnel [NOM : '+row.contratPersonnel.personnel.nom+' '+row.contratPersonnel.personnel.prenom+' ]"><span class="glyphicon glyphicon-print"></span> </a>&nbsp;';
	//option += '&nbsp;<a data-toggle="modal" href="#" title="Suprimer bulletin [LIBELLE : '+row.salaireBase+' ]"> <span class="glyphicon glyphicon-trash"></span></a>';
	
    return option;
} 
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

</script>
