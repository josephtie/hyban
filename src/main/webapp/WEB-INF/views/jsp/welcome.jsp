<%@page contentType="text/html"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url value="/" var="contextPath"/>
<%--<div style="margin-top: 0px" align="center">--%>
	<%--<img alt="" src="${contextPath}/static/front-end/images/bg_welcome.png" />--%>
<%--</div>--%>

<div class="row">

	<div class="col-md-3">

		<div class="widget widget-default widget-item-icon" >
			<div class="widget-item-left">
				<span class="fa fa fa-credit-card"></span>
			</div>
			<div class="widget-data">
				<div class="widget-int num-count" id="cptegeneral">${ctratTrue}</div>
				<div class="widget-title">Contrats </div>
				<div class="widget-subtitle">en cours</div>
			</div>
			<%--<div class="widget-controls">--%>
                <%--<a href="#" class="widget-control-right widget-remove" data-toggle="tooltip" data-placement="top" title="Remove Widget"><span class="fa fa-times"></span></a>--%>
            <%--</div>--%>
		</div>

	</div>

	<div class="col-md-3">
		<div class="widget widget-default widget-item-icon">
			<div class="widget-item-left">
				<span class="fa fa-users"></span>
			</div>
			<div class="widget-data">
				<div class="widget-int num-count" id="clients">${nbrEmpl}</div>
				<div class="widget-title">Employes</div>
				<div class="widget-subtitle">enregistres</div>
			</div>
			<%--<div class="widget-controls">--%>
                 <%--<a href="#" class="widget-control-right widget-remove" data-toggle="tooltip" data-placement="top" title="Remove Widget"><span class="fa fa-times"></span></a>--%>
             <%--</div>--%>
		</div>

	</div>
	<div class="col-md-3">

        <div class="widget widget-default widget-item-icon">
            <div class="widget-item-left">
                <span class="fa fa-money"></span>
            </div>
            <div class="widget-data">
                <div class="widget-int num-count">${massSalar}</div>
                <div class="widget-title">Masse salariale</div>
                <div class="widget-subtitle">mois precedent</div>
            </div>

        </div>

    </div>
</div>

<br>
<div class="row">
    <div class="col-md-6">

        <!-- START BAR CHART -->
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Type de contrats</h3>
            </div>
            <div class="panel-body">
                <canvas id="retraitebar1" style="height: 300px;"></canvas>
            </div>
        </div>
        <!-- END BAR CHART -->

    </div>

    <div class="col-md-6">

        <!-- START BAR CHART -->
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Masse salariale</h3>
            </div>
            <div class="panel-body">
                <canvas id="retraitebar2" style="height: 300px;"></canvas>
            </div>
        </div>
        <!-- END BAR CHART -->

    </div>
</div>
<div class="row">
    <div class="col-md-6">

        <!-- START LINE CHART -->
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Effectif & Masse salariale par Site</h3>
            </div>
            <div class="panel-body">
                <canvas id="myChartSiteEffec" style="height: 300px;"></canvas>
            </div>
        </div>
        <!-- END LINE CHART -->

    </div>
    <div class="col-md-6">

        <!-- START Area CHART -->
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"> Conges Annuels</h3>
            </div>
            <div class="panel-body">
                <canvas id="mychartConge" style="height: 300px;"></canvas>
            </div>
        </div>
        <!-- END Area CHART -->

    </div>
</div>



<div class="row">
    <div class="col-md-6">

        <!-- START BAR CHART -->
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Prevision de retraites sur cinq ans</h3>
            </div>
            <div class="panel-body">
                <canvas id="retraitebar" style="height: 300px;"></canvas>
            </div>
        </div>
        <!-- END BAR CHART -->

    </div>
    <div class="col-md-6">

        <!-- START DONUT CHART -->

             <!-- START LINE CHART -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Recrutement Sur les 5 annees anterieures</h3>
                    </div>
                    <div class="panel-body">
                        <canvas id="myChartEffec" style="height: 300px;"></canvas>
                    </div>
                </div>
                <!-- END LINE CHART -->


        <!-- END DONUT CHART -->

    </div>
</div>
<div class="row">
    <div class="col-md-6">



    </div>
        <div class="col-md-6">  </div>
</div>
<script type="text/javascript">
    jQuery(document).ready(function(){
   
        statEffectif();
       statSiteEffectif();
        statConge();
        statRetraite();
      //  statMoyenage();
        stattypcontrat();
        statMassesalariale();


    });

    //function diagrameMoyenneAge(dataVal){
            //var randomScalingFactor = function(){ return Math.round(Math.random()*10000)};

           // var myObject = [];
           // var val = {};
          //  jQuery.each(dataVal, function(i,dataValRet){
           //     var col = "rgb(" + (Math.floor(Math.random() * 256)) + "," + (Math.floor(Math.random() * 256)) + "," + (Math.floor(Math.random() * 256)) + ")";
             //   var col1 = "rgb(" + (Math.floor(Math.random() * 256)) + "," + (Math.floor(Math.random() * 256)) + "," + (Math.floor(Math.random() * 256)) + ")";

              //  val.value = dataValRet.i1;
             //   val.color = col;
           //     val.highlight = col1;
             //   val.label = dataValRet.title1;

             //   myObject.push(val);

                //val = {};
             ////   var legen = '<div>'+
               //     '<div class="input-append color"	data-color="'+col+'" data-color-format="rgb" style="float:left;">'+
              //      '<input type="hidden" class="span2" value="" readonly>'+
             //       '<span class="add-on" style="margin-left: 20px;">'+
              //      '<i style="background-color: '+col+';"></i>'+
             //       '</span>'+
              //      '</div>'+
               //     '<div style="float:left;">'+
               //     '<b>'+dataValRet.title1+'</b>'+
                //    '</div>'+
             //       '</div>';
        //        jQuery('#legDirection').append(legen);


      //      });

         //   console.log(myObject);

      //      var ctxLineMoyAgefrfrfrfr = document.getElementById("chartarea").getContext("2d");
    //        window.myPie = new Chart(ctxLineMoyAgefrfrfrfr).Pie(myObject);

    //        window.myPie = new Chart(ctxLineMoyAgefrfrfrfr,{
  //              type: 'bar',
   //             data: myObject,
     //           responsive: true
      //      });

  //      }
    var anAff = '${anneId}';

    urlenv =baseUrl+"/personnels/effectifPersonnel?id="+anAff;
    //alert("le lien est     "+urlenv);
    jQuery.ajax({
        type: "GET",
        url: urlenv,
        cache: false,
        dataType: "json",
        timeout: 9000,
        success: function(data) {
            console.log(data);

            diagrameEffectif(data);

        },
        error: function(err) {
            console.log(err);
            //alert("Impossible de joindre le serveur.");
        },
        beforeSend: function() {
            //jQuery("#csharge").show();
        },
        complete: function() {
            //jQuery("#charge").hide();
        }

    });

function diagrameEffectifSite(dataVal){

    var lineChartData = {
        labels: dataVal.map(item => item.s1),
        datasets: [
            {
                label: "Effectif",
                fill: true,
                borderColor: "#3e95cd",
                backgroundColor: "#3e95cd",
                data: dataVal.map(item => item.i1),
            },
            {
                label: "Masse salariale",
                fill: true,
                borderColor: "#8e5ea2",
                backgroundColor: "#8e5ea2",
                data: dataVal.map(item => item.value1),
            }
        ]
    };
    var ctxLine = document.getElementById("myChartSiteEffec").getContext("2d");
new Chart(ctxLine, {
    type: 'line',
    data: lineChartData,
    options: {
        responsive: true,
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});




}




    function diagrameEffectif(dataVal){

        var barChartData = {
            labels : [dataVal[4].s1,dataVal[3].s1, dataVal[2].s1, dataVal[1].s2,dataVal[0].s1],
            datasets : [
                {
                    borderColor: "#3e95cd",
                    backgroundColor: "#3e95cd",
                    data : [dataVal[4].i1,dataVal[3].i1, dataVal[2].i1, dataVal[1].i1, dataVal[0].i1],
                    label: dataVal[0].title1
                },
                {

                    borderColor: "#8e5ea2",
                    backgroundColor: "#8e5ea2",
                    data : [dataVal[4].i2,dataVal[3].i2, dataVal[2].i2, dataVal[1].i2, dataVal[0].i2],
                    label: dataVal[0].title2,

                }
            ],
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero:true
                        }
                    }]
                }
            }


        }
       $('#myChartEffec').replaceWith('<canvas id="myChartEffec"></canvas>');
        var ctx = document.getElementById("myChartEffec").getContext("2d");
        window.myBart = new Chart(ctx, {
            type: 'bar',
            data: barChartData
        });

    }

    function diagrameConge(dataVal){

        var lineChartData = {
            //labels : ["January","February","March","April","May","June","July"],
            labels : [dataVal[0].s1, dataVal[1].s1, dataVal[2].s1,dataVal[3].s1, dataVal[4].s1, dataVal[5].s1, dataVal[6].s1,dataVal[7].s1, dataVal[8].s1, dataVal[9].s1, dataVal[10].s1,dataVal[11].s1],
            datasets : [

                {
                    label: "Planing",
                    fill: true,
                    borderColor: "#3e95cd",
                    backgroundColor: "#3e95cd",
                    strokeColor : "rgba(151,187,205,1)",
                    pointColor : "rgba(151,187,205,1)",
                    pointStrokeColor : "#fff",
                    pointHighlightFill : "#fff",
                    pointHighlightStroke : "rgba(151,187,205,1)",
                    data : [dataVal[0].i2, dataVal[1].i2, dataVal[2].i2, dataVal[3].i2, dataVal[4].i2, dataVal[5].i2, dataVal[6].i2, dataVal[7].i2, dataVal[8].i2, dataVal[9].i2, dataVal[10].i2, dataVal[11].i2]
                },
                {
                    label: "Conge",
                    fill: true,
                    borderColor: "#8e5ea2",
                    backgroundColor: "#8e5ea2",
                    strokeColor : "rgba(220,220,220,1)",
                    pointColor : "rgba(220,220,220,1)",
                    pointStrokeColor : "#fff",
                    pointHighlightFill : "#fff",
                    pointHighlightStroke : "rgba(220,220,220,1)",
                    data : [dataVal[0].i1, dataVal[1].i1, dataVal[2].i1, dataVal[3].i1, dataVal[4].i1, dataVal[5].i1, dataVal[6].i1, dataVal[7].i1, dataVal[8].i1, dataVal[9].i1, dataVal[10].i1, dataVal[11].i1]
                },
            ]

        }

        var ctxLine = document.getElementById("mychartConge").getContext("2d");

        window.myLine = new Chart(ctxLine,{
            type: 'line',
            data: lineChartData,
            responsive: true
        });

    }
    function diagrameTypecontrat(dataVal){

        new Chart(document.getElementById("retraitebar1"), {
            type: 'doughnut',
            data: {
                labels: [dataVal[0].s1, dataVal[1].s1, dataVal[2].s1, dataVal[3].s1],
                datasets: [
                    {
                        label: "Population (millions)",
                        backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#ff0033"],
                        data: [dataVal[0].i1,dataVal[1].i1,dataVal[2].i1,dataVal[3].i1]
                    }
                ]
            },
            options: {
                title: {
                    display: true,
                    text: 'Employes par type de contrats'
                }
            }
        });

    }

    function diagramemassesalariale(dataVal){

        new Chart(document.getElementById("retraitebar2"), {
            type: 'pie',
            data: {
                labels: [dataVal[0].s1, dataVal[1].s1, dataVal[2].s1],
                datasets: [
                    {
                        label: "Population (millions)",
                        backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f"],
                        data: [dataVal[0].i1,dataVal[1].i1,dataVal[2].i1]
                    }
                ]
            },
            options: {
                title: {
                    display: true,
                    text: 'Masse salariale par type de contrats'
                }
            }
        });

    }

    function diagrameRetraite(dataVal){

        var labRet = [], dataGarcon = [], dataFemme = [];
        jQuery.each(dataVal, function(i,dataValRet){
            labRet.push(dataValRet.s1);
            dataGarcon.push(dataValRet.i1);
            dataFemme.push(dataValRet.i2);
        });
        //console.log(dataGarcon);

        var barChartDataRetraite = {
            labels : labRet,
            datasets : [
                {
                    fillColor : "rgba(93,227,142,0.5)",
                    strokeColor : "rgba(93,227,142,0.8)",
                    highlightFill: "rgba(93,227,142,0.75)",
                    highlightStroke: "rgba(93,227,142,1)",
                    data : dataGarcon,
                    label: dataVal[0].title1
                },
                {

                    fillColor : "rgba(219,211,98,0.5)",
                    strokeColor : "rgba(219,211,98,0.8)",
                    highlightFill : "rgba(219,211,98,0.75)",
                    highlightStroke : "rgba(219,211,98,1)",
                    data : dataFemme,
                    label: dataVal[0].title2,

                }
            ]


        }

        var ctx2 = document.getElementById("retraitebar").getContext("2d");

        window.myBar = new Chart(ctx2,{
            type: 'bar',
            data: barChartDataRetraite,
            responsive: true
        });
    }



    function statEffectif(){

        var anAff = '${anneId}';

        urlenv =baseUrl +"/personnels/effectifPersonnel?id="+anAff;
        //alert("le lien est     "+urlenv);
        jQuery.ajax({
            type: "GET",
            url: urlenv,
            cache: false,
            dataType: "json",
            timeout: 9000,
            success: function(data) {
                console.log(data);

                diagrameEffectif(data);

            },
            error: function(err) {
                console.log(err);
                //alert("Impossible de joindre le serveur.");
            },
            beforeSend: function() {
                //jQuery("#csharge").show();
            },
            complete: function() {
                //jQuery("#charge").hide();
            }

        });
    }


   function statSiteEffectif(){

        var anAff = '${anneId}';

        urlenv1 =baseUrl +"/personnels/effectifparsite?id="+anAff;
        //alert("le lien est     "+urlenv);
        jQuery.ajax({
            type: "GET",
            url: urlenv1,
            cache: false,
            dataType: "json",
            timeout: 9000,
            success: function(data) {
                console.log(data);

                diagrameEffectifSite(data);

            },
            error: function(err) {
                console.log(err);
                //alert("Impossible de joindre le serveur.");
            },
            beforeSend: function() {
                //jQuery("#csharge").show();
            },
            complete: function() {
                //jQuery("#charge").hide();
            }

        });
    }

    function stattypcontrat(){

        var anAff = '${anneId}';
        urlenv =baseUrl +"/personnels/stat/typecontrat?id="+anAff;
        jQuery.ajax({
            type: "GET",
            url: urlenv,
            cache: false,
            dataType: "json",
            timeout: 9000,
            success: function(data) {
                console.log(data);

                diagrameTypecontrat(data);

            },
            error: function(err) {
                console.log(err);
                //alert("Impossible de joindre le serveur.");
            },
            beforeSend: function() {
                //jQuery("#csharge").show();
            },
            complete: function() {
                //jQuery("#charge").hide();
            }

        });
    }
    function statMassesalariale(){

        var anAff = '${anneId}';
        urlenv =baseUrl +"/personnels/stat/massesalariale?id="+anAff;
        jQuery.ajax({
            type: "GET",
            url: urlenv,
            cache: false,
            dataType: "json",
            timeout: 9000,
            success: function(data) {
                console.log(data);

                diagramemassesalariale(data);

            },
            error: function(err) {
                console.log(err);
                //alert("Impossible de joindre le serveur.");
            },
            beforeSend: function() {
                //jQuery("#csharge").show();
            },
            complete: function() {
                //jQuery("#charge").hide();
            }

        });
    }
    function statConge(){
    urlenv =baseUrl +"/personnels/stat/conge?id="+anAff;
    //alert("le lien est     "+urlenv);
    jQuery.ajax({
        type: "GET",
        url: urlenv,
        cache: false,
        dataType: "json",
        timeout: 9000,
        success: function(data) {
            console.log(data);

            diagrameConge(data);

        },
        error: function(err) {
            console.log(err);
            //alert("Impossible de joindre le serveur.");
        },
        beforeSend: function() {
            //jQuery("#csharge").show();
        },
        complete: function() {
            //jQuery("#charge").hide();
        }

    });
    }

     function statRetraite(){
        urlenv =baseUrl +"/personnels/stat/effectifPersonnelRetraite?id="+anAff;
        //alert("le lien est     "+urlenv);
        jQuery.ajax({
            type: "GET",
            url: urlenv,
            cache: false,
            dataType: "json",
            timeout: 9000,
            success: function(data) {
                console.log(data);

                diagrameRetraite(data);

            },
            error: function(err) {
                console.log(err);
                //alert("Impossible de joindre le serveur.");
            },
            beforeSend: function() {
                //jQuery("#csharge").show();
            },
            complete: function() {
                //jQuery("#charge").hide();
            }

        });

    }
     //function statMoyenage(){
      //  urlenv =baseUrl+"/personnels/stat/moyenAge?aid="+anAff;
        //alert("le lien est     "+urlenv);
      //   jQuery.ajax({
       //      type: "GET",
         //    url: urlenv,
       //      cache: false,
       //      dataType: "json",
       //      timeout: 9000,
        //     success: function(data) {
        //         console.log(data);

          //       diagrameMoyenneAge(data);
         //    },
          //   error: function(err) {
         //        console.log(err);
                //alert("Impossible de joindre le serveur.");
           //  },
            // beforeSend: function() {
             //    //jQuery("#csharge").show();
            // },
           //  complete: function() {
                //jQuery("#charge").hide();
           //  }

        // });

         //new Chart(document.getElementById("retrait1"), {
           // type: 'doughnut',
           // data: {
           //      labels: ["Africa", "Asia", "Europe", "Latin America", "North America"],
           //      datasets: [
           //          {
           //              label: "Population (millions)",
             //            backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
              //           data: [2478,5267,734,784,433]
             //       }
              //   ]
           //  },
            // options: {
            //     title: {
             //        display: true,
            //         text: 'Employes par type de contrats'
              //  }
            // }
         //});
     // }

</script>