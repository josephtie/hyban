<%@page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:url value="/" var="contextPath"/>
<style>
@keyframes blink {
  0% { opacity: 1; }
  50% { opacity: 0.3; }
  100% { opacity: 1; }
}

.blink {
  animation: blink 1s infinite;
}
</style>
<ul class="x-navigation x-navigation-horizontal x-navigation-panel">
    <!-- TOGGLE NAVIGATION -->

    <li class="xn-icon-button">
        <a href="#" class="x-navigation-minimize"><span class="fa fa-dedent"></span></a>
    </li>
    <li class="xn-search">
        <form role="form">
            <input type="text" name="search" placeholder="Search..."/>
        </form>
    </li>
    <!-- END SEARCH -->
    <!-- POWER OFF -->
    <li class="xn-icon-button pull-right last">
        <a href="#"><span class="fa fa-power-off"></span></a>
        <ul class="xn-drop-left animated zoomIn">
            <li><a href="#" data-toggle="modal" data-target="#rhpModalmodif" onclick="edit(${user.id})"><span class="fa fa-lock"></span> Profile</a></li>
            <%--<li><a href="pages-lock-screen.html"><span class="fa fa-lock"></span> Verouiller</a></li>--%>
            <li><a href="${contextPath}j_spring_security_logout" ><span class="fa fa-sign-out"></span> Deconnexion</a></li>
        </ul>
    </li>
    <li class="xn-icon-button pull-right "  id="contratStat" ng-class="{'blink': contratAterme.nombre > 0}">
        <a href="#"><span class="fa fa-comments blink"></span></a>
        <div class="informer informer-danger">{{contratAterme.nombre}}</div>
        <div class="panel panel-primary animated zoomIn xn-drop-left xn-panel-dragging"  ng-controller="contratStatCtrl">
            <div class="panel-heading">
                <h3 class="panel-title"><span class="panel-title-box"></span> Les Contrats qui expirent: 15 jrs</h3>
                <div class="pull-right">
                    <span class="label label-danger">{{contratAterme.nombre}} new</span>
                </div>
            </div>
            <div class="panel-body list-group list-group-contacts scroll" style="height: 200px;">
             <%--<li ng-repeat="contrat in contratAterme.contrats">--%>
                    <%--<a href="#">{{contrat.personnel.nomComplet}} - {{contrat.dFin}}</a>--%>
                 <%--</li>--%>
                <a href="#" class="list-group-item" ng-repeat="contrat in contratAterme.contrats">
                    <div class="list-group-status status-online"></div>
                    <img src="/hyban/static/back-office/assets/images/users/user2.jpg" class="pull-left" alt="John Doe"/>
                    <span class="contacts-title">{{contrat.personnel.nomComplet}} - {{contrat.dFin}}</span>

                </a>

            </div>
            <div class="panel-footer text-center">
                <a href="${contextPath}personnels/contrat">voir plus contrats</a>
            </div>
        </div>
    </li>

    <!-- END TOGGLE NAVIGATION -->
</ul>
<div class="modal fade" id="rhpModalmodif" tabindex="-1" role="dialog" aria-labelledby="rhpModalmodifLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formModif" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
                 	<button type="button" class="close label-white" data-dismiss="modal" aria-label="Close"><span class="label-white" aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Changer Mot de passe  ?</h4>
				</div>
                <div class="modal-body">
                	<div class="form-group">
                        <label for="email" class="col-md-4 control-label"> e-mail/Ancien Mot de passe</label>
                        <div class="col-md-4">
                            <input type="text" id="emailm" name="emailm" class="form-control" placeholder="Votre adresse e-mail" required="required" />
                        </div>
                        <div class="col-md-4">
                            <input type="text" id="ancien" name="ancien"  class="form-control" placeholder="Ancien mot de passe" required="required" />
                        </div>
                    </div>
                    	<div class="form-group">
                        <label for="email" class="col-md-4 control-label">Nouveau Mot de passe</label>
                        <div class="col-md-4">
                            <input type="text" id="nouveau" name="nouveau" class="form-control" placeholder="mot de passe" required="required" />
                        </div>
                        <div class="col-md-4">
                           <span></span>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                	 <input type="hidden" id="id" name="id" class="form-control" placeholder="mot de passe" required="required" />
                	 <button type="submit" class="btn btn-success" >Valider</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
function edit(id){
		jQuery.ajax({
            type: "POST",
            url: baseUrl+"/parametrages/cherchutilisateur",
            cache: false,
            data: {id: id},
			success: function (response) {
            	if (response != null) {
            		console.log(response);
            		jQuery("#emailm").val(response.email);
            		jQuery("#id").val(response.id); 
            		 $("#rhpModalmodif").modal('show');
            	//	loadDataToForm(response.id, response.role.id, response.utilisateur.nomComplet, response.utilisateur.dNaissance, response.utilisateur.email, response.utilisateur.telephone, response.utilisateur.adresse);
            		//loadDataToForm(response.id, response.branch.id, response.name, response.bday, response.phoneNumber, response.address, response.email, null);
				} else {
					alert('Impossible de charger cet objet');
				}
            },
            error: function () {
                
            }
        });
	}
jQuery("#formModif").submit(function(){
	var formData = jQuery(this).serialize();
	
	jQuery.ajax({
        type: "POST",
        url:  baseUrl + "/parametrages/chagermotdepasse",
        cache: false,
        data: formData,
        success: function (reponse) {
        	/*if (response != null) {
        		$("#rhpModal").modal("hide");
        		jQuery('#table').bootstrapTable('refresh');
        		//loadDataToForm(null, null, null, null, null, null, null, null);
				jAlert("'Enregistré avec succès", "UTILISATEUR");
			}
        	else {
				alert('Impossible d\'effectuer cet enregistrement');
			}*/
        	
        	if (reponse.result == "succes") {
               	//if(action == "ajout"){
               		//Rechargement de la liste (le tableau)
                   alert(reponse.result)
               		jQuery("#formModif")[0].reset(); //Initialisation du formulaire
               		jQuery("#rhpModalmodif").modal("hide");
               /* 	}
               	else{ */
               		//MAJ de la ligne modifiée
               	//	$table.bootstrapTable('updateRow', {
                 //          index: $table.find('tr.selected').data('index'),
                  //         row: reponse.data
                      // });
               		/* jQuery("#formModif")[0].reset(); //Initialisation du formulaire
               		jQuery("#rhpModalmodif").modal("hide"); */
               //	}
               }
               else {reponse.result = "erreur_champ_obligatoire";
           	alert("Saisie invalide");
               }
               
               //}
        },
       /*  beforeSend: function () {
        	jQuery("#formModif").attr("disabled", true);
        	jQuery("#rhpModal .modal-footer span").addClass('loader');
           },
        error: function () {
        	jQuery("#rhpModal .modal-body div.alert").alert();
        	jQuery("#rhpModal .modal-body .alert h4").html("Erreur survenue");
        	jQuery("#rhpModal .modal-body .alert p").html("Verifier que vous êtes connectés au serveur.");
        	jQuery("#rhpModal .modal-footer span").removeClass('loader');
           },
        complete: function () {
        	/* jQuery("#formAjout").removeAttr("disabled");
        	jQuery("#rhpModal .modal-footer span").removeClass('loader');
               $table.bootstrapTable('refresh', {
                   url: baseUrl + '/parametrages/listutilisateurjson'
               }); 
               jQuery("#rhpModalmodif").modal("hide");
           } */
    });
	return false;
})
</script>	
