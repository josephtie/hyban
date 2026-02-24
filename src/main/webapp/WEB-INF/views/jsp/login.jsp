<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<form id='login' class="form-horizontal" action="<c:url value='../hyban/j_spring_security_check' />" method='POST'>
<!-- <form id='login' class="form-horizontal" action="<c:url value='../j_spring_security_check' />" method='POST'>-->
    <div class="form-group">
        <div class="col-md-12">
            <input type="text" class="form-control"  id="j_username" name="j_username"  placeholder="email" />
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-12">
            <input type="password" class="form-control" placeholder="Password" id="j_password" name="j_password" />
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-6">
            <a href="#" data-toggle="modal" data-target="#rhpModal" class="btn btn-link btn-block">Mot de passe oublie?</a>
        </div>
        <div class="col-md-6">
            <button class="btn btn-info btn-block" type="submit">Se connecter</button>
        </div>
    </div>



</form>

<div class="modal fade" id="rhpModal" tabindex="-1" role="dialog" aria-labelledby="rhpModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formMail" class="form-horizontal" role="form" novalidate="novalidate">
                <div class="modal-header">
                 	<button type="button" class="close label-white" data-dismiss="modal" aria-label="Close"><span class="label-white" aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Mot de passe perdu ?</h4>
				</div>
                <div class="modal-body">
                	<div class="form-group">
                        <label for="email" class="col-md-5 control-label">Veuillez entrer votre adresse e-mail</label>
                        <div class="col-md-5">
                            <input type="text" id="email" name="email" ng-model="email" class="form-control" placeholder="Votre adresse e-mail" required="required" />
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-success">Valider</button>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                	<span></span>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
var actionUrl = "/parametrages/motdepasseoublie";
var $table;
jQuery(document).ready(function($){
	//Envoi des donnees
	$("#formMail").submit(function(e){
		e.preventDefault();
		if($.trim($("#email").val()) == ""){
			alert("Saisie invalide");
			$("#email").val("").focus();
			return;
		}
		
        var formData = $(this).serialize();
        console.log("form", formData);
        $.ajax({
            type: "POST",
            url: baseUrl + actionUrl,
            cache: false,
            data: formData,
            success: function (reponse) {
                if (reponse.result == "succes") {
                	alert("Un mail vous a �t� envoy�")
                }
                else if(reponse.result == "erreur_champ_obligatoire"){
                	alert("Champ obligatoire");
                }
                else{
                	alert("Verifier que vous �tes connect�s au serveur ou contactez votre administrateur.");
                }
            },
            error: function (err) {
            	$("#rhpModal .modal-body div.alert").alert();
            	$("#rhpModal .modal-body .alert h4").html("Erreur survenue");
            	$("#rhpModal .modal-body .alert p").html("Verifier que vous �tes connect�s au serveur.");
            	$("#rhpModal .modal-footer span").removeClass('loader');
            },
            beforeSend: function () {
                $("#formAjout").attr("disabled", true);
                $("#rhpModal .modal-footer span").addClass('loader');
            },
            complete: function () {
                $("#formAjout").removeAttr("disabled");
                $("#rhpModal .modal-footer span").removeClass('loader');
            }
        });
	});
});
</script>