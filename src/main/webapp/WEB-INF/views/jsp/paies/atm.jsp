<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	var contextPath = "<%=request.getContextPath() %>";
	
	jQuery(document).ready(function() {
		
		loadCards();
		loadDataToForm(null, null);
	});
	
	jQuery('#reset').click(function(){
		loadDataToForm(null, null);
	});
	
	function loadDataToForm(code, amount){
		jQuery('#code').val(code);
		jQuery('#amount').val(amount);
	}
	
	function loadCards(){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/cards/loadcards",
            cache: false,
            success: function (response) {
            	if (response != null) {
					tabledata = '';
	        		for(i =0 ; i < response.length ; i++){
	        			tabledata += '<option value="'+response[i].id+'" >'+response[i].cardNumber+' - ['+response[i].account.customer.customerId+'] ['+response[i].account.customer.name+'] ['+response[i].account.number+']</option>';
	        		}
	        		//tabledata += "";
		    	  	jQuery('#card').html(tabledata);
		    	  	jQuery("#card").trigger("liszt:updated");
		    	  	jQuery('#card option:selected').val()
				} else {
					alert('Failure! An error has occurred!');
				}
            },
            error: function () {
                
            },
        });
	}
	
	function validate(){
		jQuery.ajax({
            type: "POST",
            url: contextPath+"/operations/savetransactionwithdrawalatm",
            cache: false,
            data: {
				id: jQuery('#id').val(),
				card: jQuery('#card').val(),
				code: jQuery('#code').val(),
				amount: jQuery('#amount').val()
			},
            beforeSend: function () {
        		//jQuery('#idButton').hide();
        		//jQuery('#idWait').show();
            },
            success: function (response) {
            	if (response != null) {
            		loadDataToForm(null, null);
					alert('Enregistré avec succès');
				} else {
					//alert('L\'utilisateur à été enregistré avec succès');
					alert('Impossible d\'effectuer cet enregistrement');
				}
            },
            error: function () {
                
            },
            complete: function () {
        		jQuery('#table').bootstrapTable('refresh', {
                    url: contextPath + '/customers/cardsjson'
                });
            }
        });
	}
</script>

<div class="widget">
	<div class="widgetbox">                        
	    <div class="headtitle">
	        <div class="btn-group">
	            <button data-toggle="dropdown" class="btn dropdown-toggle">Action <span class="caret"></span></button>
				<ul class="dropdown-menu">
	              	<li><a href="#" onclick="widgetView('list')">Listes</a></li>
	              	<li class="divider"></li>
	              	<li><a href="#" onclick="widgetView('new')">Nouveau</a></li>
	            </ul>
	        </div>
	        <h4 id="widgetTitle" class="widgettitle">Liste Cartes</h4>
	    </div>
	    
	    <div id="formWidget" class="widgetcontent">
	        
	            <div class="row">
                    <div class="form-group col-md-4">
                    	<label for="bin">Carte</label>
                        <select data-placeholder="Inserez votre carte ..." style="width:300px" class="chzn-select" id="card" tabindex="1"></select>
                    </div>
                </div>
                
                <div class="row">
                    <div class="form-group col-md-4">
                    	<label for="bin">Code Secret</label>
                        <input type="password" maxlength="4" class="form-control input-sm" id="code" placeholder="Code Secret">
                    </div>
                </div>
                
                <div class="row">
                    <div class="form-group col-md-4">
                    	<label for="bin">Montant</label>
                        <input type="text" class="form-control input-sm" id="amount" placeholder="Montant">
                    </div>
                </div>
	            <input type="hidden" class="form-control input-default" id="id" placeholder="id">
	            
	            <p class="stdformbutton">
                    <button class="btn btn-primary" onclick="validate()">Valider</button>
                    <button id="reset" type="reset" class="btn btn-default">Annuler</button>
                </p>
	        
	    </div><!-- widgetcontent-->
    </div><!--widgetbox-->
</div><!-- widgetcontent-->