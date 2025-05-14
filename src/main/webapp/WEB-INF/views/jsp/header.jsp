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
                    <img src="/static/back-office/assets/images/users/user2.jpg" class="pull-left" alt="John Doe"/>
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
<%--<div class="page-header-inner ">--%>
    <%--<!-- BEGIN LOGO -->--%>
    <%--<div class="page-logo">--%>
        <%--<a href="index.html">--%>
            <%--<img src="${contextPath}/resources/assets/layouts/layout/img/logo1.png" alt="logo" class="logo-default" /> </a>--%>
        <%--<div class="menu-toggler sidebar-toggler">--%>
            <%--<span></span>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<!-- END LOGO -->--%>
    <%--<!-- BEGIN RESPONSIVE MENU TOGGLER -->--%>
    <%--<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">--%>
        <%--<span></span>--%>
    <%--</a>--%>
    <%--<!-- END RESPONSIVE MENU TOGGLER -->--%>
    <%--<!-- BEGIN TOP NAVIGATION MENU -->--%>
    <%--<div class="top-menu">--%>
        <%--<ul class="nav navbar-nav pull-right">--%>
            <%--<!-- BEGIN NOTIFICATION DROPDOWN -->--%>
            <%--<!-- DOC: Apply "dropdown-dark" class after "dropdown-extended" to change the dropdown styte -->--%>
            <%--<!-- DOC: Apply "dropdown-hoverable" class after below "dropdown" and remove data-toggle="dropdown" data-hover="dropdown" data-close-others="true" attributes to enable hover dropdown mode -->--%>
            <%--<!-- DOC: Remove "dropdown-hoverable" and add data-toggle="dropdown" data-hover="dropdown" data-close-others="true" attributes to the below A element with dropdown-toggle class -->--%>
            <%--<li class="dropdown dropdown-extended dropdown-notification" id="header_notification_bar">--%>
                <%--<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">--%>
                    <%--<i class="icon-bell"></i>--%>
                    <%--<span class="badge badge-default"> 7 </span>--%>
                <%--</a>--%>
                <%--<ul class="dropdown-menu">--%>
                    <%--<li class="external">--%>
                        <%--<h3>--%>
                            <%--<span class="bold">12 pending</span> notifications</h3>--%>
                        <%--<a href="page_user_profile_1.html">view all</a>--%>
                    <%--</li>--%>

                <%--</ul>--%>
            <%--</li>--%>
            <%--<!-- END NOTIFICATION DROPDOWN -->--%>
            <%--<!-- BEGIN INBOX DROPDOWN -->--%>
            <%--<!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->--%>

            <%--<li class="dropdown dropdown-extended dropdown-tasks" id="header_task_bar">--%>
                <%--<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">--%>
                    <%--<i class="icon-calendar"></i>--%>
                    <%--<span class="badge badge-default"> 3 </span>--%>
                <%--</a>--%>
                <%--<ul class="dropdown-menu extended tasks">--%>
                    <%--<li class="external">--%>
                        <%--<h3>You have--%>
                            <%--<span class="bold">12 pending</span> tasks</h3>--%>
                        <%--<a href="app_todo.html">view all</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<ul class="dropdown-menu-list scroller" style="height: 275px;" data-handle-color="#637283">--%>
                            <%--<li>--%>
                                <%--<a href="javascript:;">--%>
                                                    <%--<span class="task">--%>
                                                        <%--<span class="desc">New release v1.2 </span>--%>
                                                        <%--<span class="percent">30%</span>--%>
                                                    <%--</span>--%>
                                    <%--<span class="progress">--%>
                                                        <%--<span style="width: 40%;" class="progress-bar progress-bar-success" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100">--%>
                                                            <%--<span class="sr-only">40% Complete</span>--%>
                                                        <%--</span>--%>
                                                    <%--</span>--%>
                                <%--</a>--%>
                            <%--</li>--%>
                            <%--<li>--%>
                                <%--<a href="javascript:;">--%>
                                                    <%--<span class="task">--%>
                                                        <%--<span class="desc">Application deployment</span>--%>
                                                        <%--<span class="percent">65%</span>--%>
                                                    <%--</span>--%>
                                    <%--<span class="progress">--%>
                                                        <%--<span style="width: 65%;" class="progress-bar progress-bar-danger" aria-valuenow="65" aria-valuemin="0" aria-valuemax="100">--%>
                                                            <%--<span class="sr-only">65% Complete</span>--%>
                                                        <%--</span>--%>
                                                    <%--</span>--%>
                                <%--</a>--%>
                            <%--</li>--%>
                            <%--<li>--%>
                                <%--<a href="javascript:;">--%>
                                                    <%--<span class="task">--%>
                                                        <%--<span class="desc">Mobile app release</span>--%>
                                                        <%--<span class="percent">98%</span>--%>
                                                    <%--</span>--%>
                                    <%--<span class="progress">--%>
                                                        <%--<span style="width: 98%;" class="progress-bar progress-bar-success" aria-valuenow="98" aria-valuemin="0" aria-valuemax="100">--%>
                                                            <%--<span class="sr-only">98% Complete</span>--%>
                                                        <%--</span>--%>
                                                    <%--</span>--%>
                                <%--</a>--%>
                            <%--</li>--%>
                            <%--<li>--%>
                                <%--<a href="javascript:;">--%>
                                                    <%--<span class="task">--%>
                                                        <%--<span class="desc">Database migration</span>--%>
                                                        <%--<span class="percent">10%</span>--%>
                                                    <%--</span>--%>
                                    <%--<span class="progress">--%>
                                                        <%--<span style="width: 10%;" class="progress-bar progress-bar-warning" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100">--%>
                                                            <%--<span class="sr-only">10% Complete</span>--%>
                                                        <%--</span>--%>
                                                    <%--</span>--%>
                                <%--</a>--%>
                            <%--</li>--%>
                            <%--<li>--%>
                                <%--<a href="javascript:;">--%>
                                                    <%--<span class="task">--%>
                                                        <%--<span class="desc">Web server upgrade</span>--%>
                                                        <%--<span class="percent">58%</span>--%>
                                                    <%--</span>--%>
                                    <%--<span class="progress">--%>
                                                        <%--<span style="width: 58%;" class="progress-bar progress-bar-info" aria-valuenow="58" aria-valuemin="0" aria-valuemax="100">--%>
                                                            <%--<span class="sr-only">58% Complete</span>--%>
                                                        <%--</span>--%>
                                                    <%--</span>--%>
                                <%--</a>--%>
                            <%--</li>--%>
                            <%--<li>--%>
                                <%--<a href="javascript:;">--%>
                                                    <%--<span class="task">--%>
                                                        <%--<span class="desc">Mobile development</span>--%>
                                                        <%--<span class="percent">85%</span>--%>
                                                    <%--</span>--%>
                                    <%--<span class="progress">--%>
                                                        <%--<span style="width: 85%;" class="progress-bar progress-bar-success" aria-valuenow="85" aria-valuemin="0" aria-valuemax="100">--%>
                                                            <%--<span class="sr-only">85% Complete</span>--%>
                                                        <%--</span>--%>
                                                    <%--</span>--%>
                                <%--</a>--%>
                            <%--</li>--%>
                            <%--<li>--%>
                                <%--<a href="javascript:;">--%>
                                                    <%--<span class="task">--%>
                                                        <%--<span class="desc">New UI release</span>--%>
                                                        <%--<span class="percent">38%</span>--%>
                                                    <%--</span>--%>
                                    <%--<span class="progress progress-striped">--%>
                                                        <%--<span style="width: 38%;" class="progress-bar progress-bar-important" aria-valuenow="18" aria-valuemin="0" aria-valuemax="100">--%>
                                                            <%--<span class="sr-only">38% Complete</span>--%>
                                                        <%--</span>--%>
                                                    <%--</span>--%>
                                <%--</a>--%>
                            <%--</li>--%>
                        <%--</ul>--%>
                    <%--</li>--%>
                <%--</ul>--%>
            <%--</li>--%>
            <%--<!-- END TODO DROPDOWN -->--%>
            <%--<!-- BEGIN USER LOGIN DROPDOWN -->--%>
            <%--<!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->--%>
            <%--<li class="dropdown dropdown-user">--%>
                <%--<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">--%>
                    <%--<img alt="" class="img-circle" src="${contextPath}/resources/assets/layouts/layout/img/avatar3_small.jpg" />--%>
                    <%--<span class="username username-hide-on-mobile"> ${user.nomComplet} </span>--%>
                    <%--<i class="fa fa-angle-down"></i>--%>
                <%--</a>--%>
                <%--<ul class="dropdown-menu dropdown-menu-default">--%>
                    <%--<li>--%>
                        <%--<a href="page_user_profile_1.html">--%>
                            <%--<i class="icon-user"></i> My Profile </a>--%>
                    <%--</li>--%>

                    <%--<li class="divider"> </li>--%>
                    <%--<li>--%>
                        <%--<a href="page_user_lock_1.html">--%>
                            <%--<i class="icon-lock"></i> Lock Screen </a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a href="${contextPath}j_spring_security_logout">D&eacute;connexion</a>--%>
                    <%--</li>--%>
                <%--</ul>--%>
            <%--</li>--%>
            <%--<!-- END USER LOGIN DROPDOWN -->--%>
            <%--<!-- BEGIN QUICK SIDEBAR TOGGLER -->--%>
            <%--<!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->--%>
            <%--&lt;%&ndash;<li class="dropdown dropdown-quick-sidebar-toggler">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<a href="javascript:;" class="dropdown-toggle">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<i class="icon-logout"></i>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</a>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</li>&ndash;%&gt;--%>
            <%--<!-- END QUICK SIDEBAR TOGGLER -->--%>
        <%--</ul>--%>
    <%--</div>--%>
    <%--<!-- END TOP NAVIGATION MENU -->--%>
<%--</div>--%>



<%--<div class="header">--%>
    <%--<div class="logo">--%>
        <%--<a href="${contextPath}"><img src="${contextPath}${urllogo}" alt=""  style="width: 126px;"/></a>--%>
    <%--</div>--%>
    <%--<div class="headerinner">--%>
        <%--<ul class="headmenu">--%>
            <%--<li class="odd" id="contratStat" ng-controller="contratStatCtrl">--%>
                <%--<a class="dropdown-toggle" data-toggle="dropdown" href="#">--%>
                    <%--<span class="count">{{contratAterme.nombre}}</span>--%>
                    <%--<span class="head-icon head-calendar"></span>--%>
                    <%--<span class="headmenu-label">Fin de contrats</span>--%>
                <%--</a>--%>
                <%--<ul class="dropdown-menu" id="contratAterme">--%>
                    <%--<!--li class="nav-header">Contrat &agrave; terme</li-->--%>
                    <%--<li class="nav-header">Fin de contrats</li>--%>
                    <%--<li ng-repeat="contrat in contratAterme.contrats">--%>
                        <%--<a href="#">{{contrat.personnel.nomComplet}} - {{contrat.dFin}}</a>--%>
                    <%--</li>--%>
                    <%--<li class="viewmore"><a href="${contextPath}personnels/contrat">Voir plus</a></li>--%>
                <%--</ul>--%>
            <%--</li>--%>
     <%----%>
            <%--<li class="right">--%>
                <%--<div class="userloggedinfo">--%>
                    <%--<img src="${contextPath}/resources/front-end/images/photos/thumb1.png" alt="" />--%>
                    <%--<div class="userinfo">--%>
                        <%--<h5>${user.nomComplet} </small></h5>--%>
                        <%--<h5><small>${user.email}</small></h5>--%>
                        <%--<ul>--%>
                            <%--<li><a href="#">Modifier Info. Perso.</a></li>--%>
                            <%--<li><a href="${contextPath}j_spring_security_logout">D&eacute;connexion</a></li>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</li>--%>
        <%--</ul><!--headmenu-->--%>
    <%--</div>--%>
<%--</div>--%>