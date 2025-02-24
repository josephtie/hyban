<%@ page contentType="text/html"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- Modal -->
<div class="modal fade" id="myModalAnneeForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-body">
        
        <div class="row">

                        <div class="col-lg-12">

                            <div class="panel panel-default">

                                 <div class="panel-heading">
                                    <h4> 
                                        <span>Ajout d'une année</span>
                                    </h4>
                                </div>
                                <div class="panel-body">
                                	
                                   <form:form id="formAnneeModal" class="form-horizontal" method="post" commandName="annee" action="${pageContext.request.contextPath}/action/annee/setting/postAnneeJson">
                                                                               
                                        <div class="form-group">
                                            <label class="col-lg-4 control-label" for="Annee">Annee</label>
                                            <div class="col-lg-8">
                                                <form:input id="anne"  path="annee" type="text" class="form-control" placeholder="Annee" data-validation="length" data-validation-length="min2"/>
                                            </div>
                                        </div><!-- End .form-group  --> 
                                        
                                        <div class="form-group">
                                            <div class="col-lg-offset-3 col-lg-9">
                                                <button type="submit" class="btn btn-info">Valider</button>
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                                            </div>
                                        </div><!-- End .form-group  -->                   

                                    </form:form>
                                    
                                </div>
                            </div><!-- End .panel -->
                        </div><!-- End .span6 -->
                    </div><!-- End .row -->
			      </div>
			    </div>
			  </div>
			</div>

              
<script type="text/javascript">
//Validation du formulaire formPieceModal
jQuery("#formAnneeModal").submit(function(){
	
	 var urlenv = "${pageContext.request.contextPath}/action/annee/setting/postAnneeJson";
		jQuery.ajax({
           type: "POST",
           url: urlenv,
           cache: false,
           dataType: "json",
           timeout: 3000,
           data: jQuery('#formAnneeModal').serialize(),
           success: function(data) {
           	console.log(data);
           	
           	jQuery('#myModalAnneeForm').modal('hide');
           	if(data.id > 0 ){
           		jQuery('#select_e').append('<option value="'+data.id+'" selected="selected">'+data.annee+'</option>');
           		jQuery("#select_e").select2('val', data.id);
           		
           		jQuery('#annee1').append('<option value="'+data.id+'" selected="selected">'+data.annee+'</option>');
                jQuery("#annee1").select2('val', data.id);
                
                jQuery('#enreg').append('<option value="'+data.id+'" selected="selected">'+data.annee+'</option>');
                jQuery("#enreg").select2('val', data.id); 
                
                jQuery('#annee4').append('<option value="'+data.id+'" selected="selected">'+data.annee+'</option>');
                jQuery("#annee4").select2('val', data.id); 
                
           	}
           	
           	jQuery('#formAnneeModal')[0].reset();
           	
           },
           error: function(err) {
           	
           	console.log(err);

               alert("Impossible de joindre le serveur.");
               //jQuery("#charge").hide();
           },
           beforeSend: function() {
               //jQuery("#csharge").show();
           },
           complete: function() {
               //jQuery("#charge").hide();
               
               
           }
           
       });
	
	return false;
}); 
</script>
			