<%@ page contentType="text/html"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- Modal -->
<div class="modal fade" id="myModalTypeEngagementForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-body">
        
        <div class="row">

                        <div class="col-lg-12">

                            <div class="panel panel-default">

                                 <div class="panel-heading">
                                    <h4> 
                                        <span>Ajout d'un type d'engagement</span>
                                    </h4>
                                </div>
                                <div class="panel-body">
                                	
                                   <form:form id="formTypeEngagementModal" class="form-horizontal" method="post" commandName="pret" action="${pageContext.request.contextPath}/action/pret/setting/postTypeEngegamentJson">
                                                                               
                                        <div class="form-group">
                                            <label class="col-lg-4 control-label" for="object">Type engagement</label>
                                            <div class="col-lg-8">
                                                <form:input id="pret" path="pret" data-validation="length" data-validation-length="min2" type="text" class="form-control" placeholder="Type engagement" />
                                            </div>
                                        </div><!-- End .form-group  -->                        
                                   
                                        <div class="form-group">
                                            <div class="col-lg-offset-3 col-lg-9">
                                                <button type="submit" class="btn btn-info">Valider</button>
                                                <button type="button" class="btn btn-default" onclick="history.back(); return false;">Annuler</button>
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
jQuery("#formTypeEngagementModal").submit(function(){
	
	 var urlenv = "${pageContext.request.contextPath}/action/pret/setting/postTypeEngagementJson";
		jQuery.ajax({
           type: "POST",
           url: urlenv,
           cache: false,
           dataType: "json",
           timeout: 3000,
           data: jQuery('#formTypeEngagementModal').serialize(),
           success: function(data) {
           	console.log(data);
           	
           	jQuery('#myModalTypeEngagementForm').modal('hide');
           	if(data.id > 0 ){
           		jQuery('#sel').append('<option value="'+data.id+'" selected="selected">'+data.pret+'</option>');
           		jQuery("#sel").select2('val', data.id);
           	}
           	
           	jQuery('#formTypeEngagementModal')[0].reset();
           	
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
			