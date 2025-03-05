<%@page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:url value="/" var="contextPath"/>

<div class="page-sidebar page-sidebar-fixed scroll mCustomScrollbar _mCS_1 mCS-autoHide" style="height: 359px;">
<% String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort(); %>
        <%--<div id="mCSB_1_container" class="mCSB_container"  style="position: relative; top: -350px; left: 0px;" dir="ltr"></div>--%>
    <!-- START X-NAVIGATION -->
    <ul class="x-navigation">
        <li class="xn-logo">
            <a href="${pageContext.request.contextPath}/welcome">BETHEL</a>
            <a href="#" class="x-navigation-control"></a>
        </li>
        <li class="xn-profile">
            <a href="#" class="profile-mini">
               <img src="<%= baseUrl %>/static/logo/logodefis1.png" alt="John Doe" />
            </a>
            <div class="profile">
                <div class="profile-image">
                     <img src="<%= baseUrl %>/${urllogo}" style="height: 90px;width: 90px" alt="John Doe"/>
                </div>
                <div class="profile-data">
                    <div class="profile-data-name">${user.nomComplet} </div>
                    <div class="profile-data-title">${user.email} </div>
                </div>
                <div class="profile-controls">
                    <a href="#" class="profile-control-left"><span class="fa fa-info"></span></a>
                    <a href="#" class="profile-control-right"><span class="fa fa-envelope"></span></a>
                </div>
            </div>
        </li>
        <li class="xn-title">Navigation <span style="color: white">${periode}</span></li>
        <li>
            <a href="${contextPath}/welcome"><span class="fa fa-desktop"></span> <span class="xn-text">Tableau de bord</span></a>
        </li>

        <c:if test="${profil eq 'ADMIN'}">
            <li class="xn-openable ${activeOrganisation}">
                <a href="#"><span class="fa fa-sitemap"></span> <span class="xn-text">Organisation</span></a>
                <ul>
                    <li class="${activeService}"><a href="${contextPath}personnels/services"><span class="fa fa-image"></span> Services</a></li>
                </ul>
            </li>
            <li class="xn-openable ${activeEmployers}" >
                <a href="#"><span class="fa fa-users"></span> <span class="xn-text">Gestion RH</span></a>
                <ul>
                    <li class="${activeEmployer}"><a href="${contextPath}personnels/personnel">Personnel</a></li>
                    <li class="${activeContract}"><a href="${contextPath}personnels/contrat">Contrat</a></li>
                    <li class="${activeCategory}"><a href="${contextPath}personnels/categorie">Categorie</a></li>
                    <li class="${activeFunction}"><a href="${contextPath}personnels/fonction">Emploi&Fonction</a></li>
                    <li class="${activeHoliday}"><a href="${contextPath}personnels/planningconges">Planning congé</a></li>
                </ul>
            </li>
            <li class="xn-openable ${activeTicking}">
                <a href="#"><span class="fa fa-bullhorn"></span> <span class="xn-text">Pointage</span></a>
                <ul>
                    <li class="${activePointage}"><a href="${contextPath}personnel/pointages"><span class="fa fa-heart"></span> Pointage</a></li>
                    <li><a href="ui-elements.html"><span class="fa fa-cogs"></span> Impression</a></li>

                </ul>
            </li>


            <li class="xn-openable ${activePayroll}">
                <a href="#"><span class="fa fa-th-list"></span> <span class="xn-text">Gestion de la Paie</span></a>
                <ul>
                    <li class="xn-openable">
                        <a href="#"><span class="fa fa-tasks"></span> Paie</a>
                        <ul>
                            <li class="${activeLend}"><a href="${contextPath}paie/primespersonnel"><span class="fa fa-align-justify"></span> Saisie des elements</a></li>
                            <li class="${activePayrollBook}"><a href="${contextPath}paie/livrepaie"><span class="fa fa-align-justify"></span> Livre de paie</a></li>
                            <li class="${activePayrollBook}"><a href="${contextPath}paie/histobull"><span class="fa fa-align-justify"></span> Historique Bulletin</a></li>
                            <li class="${activeRappelBook}"><a href="${contextPath}rappel/livrerappel"><span class="fa fa-list-alt"></span>Rappel</a></li>
                            <li class="${activeDepartBook}"><a href="${contextPath}departCdd/livredepart"><span class="fa fa-list-alt"></span>Depart CDD</a></li>
                            <li class="${activeHolidayPayroll}"><a href="${contextPath}conge/provisionconge"><span class="fa fa-th-large"></span> Provision congé </a></li>
                            <li class="${activeGratificationBook}"><a href="${contextPath}gratification/livrepaiegratification"><span class="fa fa-table"></span> Livre de gratification</a></li>

                        </ul>
                    </li>
                    <li class="${activeSimulPaie}"><a href="${contextPath}paie/simulPaie"><span class="fa fa-file-text-o"></span> Simulation Paie</a></li>

                    <li class="${activePret}"><a href="${contextPath}paie/pretpersonnel"><span class="fa fa-file-text-o"></span> Prêts</a><div class="informer informer-danger">New!</div></li>
                    <li class="${activeModalite}"><a href="${contextPath}paie/echelonnement"><span class="fa fa-list-alt"></span> Echeanciers</a></li>

                </ul>
            </li>
            <li class="xn-openable ${activeCotisation}">
                <a href="#"><span class="fa fa-bar-chart-o"></span> <span class="xn-text">Cotisation</span></a>
                <ul>
                    <li class="${activeSocialPayrool}"><a href="${contextPath}paie/etat">CNPS</a></li>
                    <li class="${activeITSFDFP}"><a href="${contextPath}paie/etatimp">FDFP/ITS</a></li>
                    <li class="${activeState301}" ><a href="${contextPath}paie/etatimptab">Etat 301</a></li>
                </ul>
            </li>
            <li class="xn-openable ${activeSetting}">
                <a href="tables.html"><span class="fa fa-cogs"></span> <span class="xn-text">Parametrages</span></a>
                <ul>
                    <li class="${activeExercise}"><a href="${contextPath}parametrages/exercice"><span class="fa fa-align-justify"></span> Exercice</a></li>
                    <li class="${activePeriod}"><a href="${contextPath}parametrages/periode"><span class="fa fa-sort-alpha-desc"></span> Periodes</a></li>
                    <li class="${activebanque}"><a href="${contextPath}parametrages/banques"><span class="fa fa-download"></span> Banques</a></li>
                    <li class="${activecptviremt}"><a href="${contextPath}parametrages/cptevirement"><span class="fa fa-file-text-o"></span> Comptes Virement</a><div class="informer informer-danger">New!</div></li>
                    <li class="${activebanque}"><a href="${contextPath}parametrages/rubriques"><span class="fa fa-list-alt"></span> Rubrique de paie</a></li>

                    <li class="${activeAbsence}"><a href="${contextPath}absence/absences"><span class="fa fa-file-text-o"></span> Absences</a><div class="informer informer-danger">New!</div></li>
                    <li class="${activeSanctionType}"><a href="${contextPath}carriere/typesanction"><span class="fa fa-list-alt"></span> Type de sanction</a></li>
                    <li class="${activeSantion}"><a href="${contextPath}carriere/sanctions"><span class="fa fa-list-alt"></span> Sanction</a></li>
                    <li class="${activeUser}"><a href="${contextPath}parametrages/utilisateur"><span class="fa fa-list-alt"></span> Utilisateurs</a></li>
                    <li class="${activeSociety}"><a href="${contextPath}parametrages/societe"><span class="fa fa-list-alt"></span> Societe</a></li>
                </ul>
            </li>
        </c:if>
        <c:if test="${profil eq 'RH'}">

            <li class="xn-openable ${activeOrganisation}">
                <a href="#"><span class="fa fa-sitemap"></span> <span class="xn-text">Organisation</span></a>
                <ul>
                    <li class="${activeService}"><a href="${contextPath}personnels/"><span class="fa fa-image"></span> Services</a></li>
                </ul>
            </li>
            <li class="xn-openable ${activeEmployers}" >
                <a href="#"><span class="fa fa-users"></span> <span class="xn-text">Gestion RH</span></a>
                <ul>
                    <li class="${activeEmployer}"><a href="${contextPath}personnels/personnel">Personnel</a></li>
                    <li class="${activeContract}"><a href="${contextPath}personnels/contrat">Contrat</a></li>
                    <li class="${activeCategory}"><a href="${contextPath}personnels/categorie">Categorie</a></li>
                    <li class="${activeFunction}"><a href="${contextPath}personnels/fonction">Emploi&Fonction</a></li>
                    <li class="${activeHoliday}"><a href="${contextPath}personnels/planningconges">Planning congé</a></li>
                </ul>
            </li>
            <li class="xn-openable ${activeTicking}">
                <a href="#"><span class="fa fa-bullhorn"></span> <span class="xn-text">Pointage</span></a>
                <ul>
                    <li class="${activePointage}"><a href="${contextPath}personnel/pointages"><span class="fa fa-heart"></span> Pointage</a></li>
                    <li><a href="ui-elements.html"><span class="fa fa-cogs"></span> Impression</a></li>

                </ul>
            </li>

            <li class="xn-openable ${activeSetting}">
                <a href="tables.html"><span class="fa fa-cogs"></span> <span class="xn-text">Parametrages</span></a>
                <ul>
                        <%--    <li class="${activeExercise}"><a href="${contextPath}parametrages/exercice"><span class="fa fa-align-justify"></span> Exercice</a></li>
                           <li class="${activePeriod}"><a href="${contextPath}parametrages/periode"><span class="fa fa-sort-alpha-desc"></span> Periodes</a></li>
                           <li class="${activebanque}"><a href="${contextPath}parametrages/banques"><span class="fa fa-download"></span> Banques</a></li>
                           <li class="${activecptviremt}"><a href="${contextPath}parametrages/cptevirement"><span class="fa fa-file-text-o"></span> Comptes Virement</a><div class="informer informer-danger">New!</div></li>
                           <li class="${activebanque}"><a href="${contextPath}parametrages/rubriques"><span class="fa fa-list-alt"></span> Rubrique de paie</a></li> --%>

                    <li class="${activeAbsence}"><a href="${contextPath}absence/absences"><span class="fa fa-file-text-o"></span> Absences</a><div class="informer informer-danger">New!</div></li>
                    <li class="${activeSanctionType}"><a href="${contextPath}carriere/typesanction"><span class="fa fa-list-alt"></span> Type de sanction</a></li>
                    <li class="${activeSantion}"><a href="${contextPath}carriere/sanctions"><span class="fa fa-list-alt"></span> Sanction</a></li>
                        <%-- <li class="${activeUser}"><a href="${contextPath}parametrages/utilisateur"><span class="fa fa-list-alt"></span> Utilisateurs</a></li> --%>
                        <%-- <li class="${activeSociety}"><a href="${contextPath}parametrages/societe"><span class="fa fa-list-alt"></span> Societe</a></li> --%>
                </ul>
            </li>
        </c:if>

        <c:if test="${profil eq 'DAF'}">
            <li class="xn-openable ${activePayroll}">
                <a href="#"><span class="fa fa-th-list"></span> <span class="xn-text">Gestion de la Paie</span></a>
                <ul>
                    <li class="xn-openable">
                        <a href="#"><span class="fa fa-tasks"></span> Paie</a>
                        <ul>
                            <li class="${activeLend}"><a href="${contextPath}paie/primespersonnel"><span class="fa fa-align-justify"></span> Saisie des elements</a></li>
                            <li class="${activePayrollBook}"><a href="${contextPath}paie/livrepaie"><span class="fa fa-align-justify"></span> Livre de paie</a></li>
                            <li class="${activeHolidayPayroll}"><a href="${contextPath}conge/provisionconge"><span class="fa fa-th-large"></span> Prévision congé </a></li>
                            <li class="${activeGratificationBook}"><a href="${contextPath}gratification/livrepaiegratification"><span class="fa fa-table"></span> Livre de gratification</a></li>

                        </ul>
                    </li>
                    <li class="${activePret}"><a href="${contextPath}paie/pretpersonnel"><span class="fa fa-file-text-o"></span> Prêts</a><div class="informer informer-danger">New!</div></li>
                    <li class="${activeModalite}"><a href="${contextPath}paie/echelonnement"><span class="fa fa-list-alt"></span> Echeanciers</a></li>

                </ul>
            </li>
            <li class="xn-openable ${activeCotisation}">
                <a href="#"><span class="fa fa-bar-chart-o"></span> <span class="xn-text">Cotisation</span></a>
                <ul>
                    <li class="${activeSocialPayrool}"><a href="${contextPath}paie/etat">CNPS</a></li>
                    <li class="${activeITSFDFP}"><a href="${contextPath}paie/etatimp">FDFP/ITS</a></li>
                    <li class="${activeState301}" ><a href="${contextPath}paie/etatimptab">Etat 301</a></li>
                </ul>
            </li>

            <li class="xn-openable ${activeSetting}">
                <a href="tables.html"><span class="fa fa-cogs"></span> <span class="xn-text">Parametrages</span></a>
                <ul>
                    <li class="${activeExercise}"><a href="${contextPath}parametrages/exercice"><span class="fa fa-align-justify"></span> Exercice</a></li>
                    <li class="${activePeriod}"><a href="${contextPath}parametrages/periode"><span class="fa fa-sort-alpha-desc"></span> Periodes</a></li>
                    <li class="${activebanque}"><a href="${contextPath}parametrages/banques"><span class="fa fa-download"></span> Banques</a></li>
                    <li class="${activecptviremt}"><a href="${contextPath}parametrages/cptevirement"><span class="fa fa-file-text-o"></span> Comptes Virement</a><div class="informer informer-danger">New!</div></li>
                    <li class="${activebanque}"><a href="${contextPath}parametrages/rubriques"><span class="fa fa-list-alt"></span> Rubrique de paie</a></li>

                        <%--  <li class="${activeAbsence}"><a href="${contextPath}absence/absences"><span class="fa fa-file-text-o"></span> Absences</a><div class="informer informer-danger">New!</div></li>
                         <li class="${activeSanctionType}"><a href="${contextPath}carriere/typesanction"><span class="fa fa-list-alt"></span> Type de sanction</a></li>
                         <li class="${activeSantion}"><a href="${contextPath}carriere/sanctions"><span class="fa fa-list-alt"></span> Sanction</a></li>
                         <li class="${activeUser}"><a href="${contextPath}parametrages/utilisateur"><span class="fa fa-list-alt"></span> Utilisateurs</a></li>
                         <li class="${activeSociety}"><a href="${contextPath}parametrages/societe"><span class="fa fa-list-alt"></span> Societe</a></li> --%>
                </ul>
            </li>
        </c:if>
    </ul>
    <!-- END X-NAVIGATION -->



</div>
<!-- START PAGE SIDEBAR -->


