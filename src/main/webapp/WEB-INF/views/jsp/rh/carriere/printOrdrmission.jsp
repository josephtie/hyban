<%@page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:url value="/" var="contextPath"/>


<html>

<head>
<style>
    body {
        position: relative;
        width: 21cm;
        height: 29.7cm;
        margin: 0 auto;
        color: #555555;
        background: #FFFFFF;
    }
    <!--
    /* Font Definitions */
    @font-face
    {font-family:"Cambria Math";
        panose-1:2 4 5 3 5 4 6 3 2 4;}
    @font-face
    {font-family:"Calibri Light";
        panose-1:2 15 3 2 2 2 4 3 2 4;}
    @font-face
    {font-family:Calibri;
        panose-1:2 15 5 2 2 2 4 3 2 4;}
    @font-face
    {font-family:"Segoe UI";
        panose-1:2 11 5 2 4 2 4 2 2 3;}
    @font-face
    {font-family:Cambria;
        panose-1:2 4 5 3 5 4 6 3 2 4;}
    @font-face
    {font-family:"Arial Narrow";
        panose-1:2 11 6 6 2 2 2 3 2 4;}
    @font-face
    {font-family:"Arial Black";
        panose-1:2 11 10 4 2 1 2 2 2 4;}
    /* Style Definitions */
    p.MsoNormal, li.MsoNormal, div.MsoNormal
    {margin:0cm;
        margin-bottom:.0001pt;
        font-size:12.0pt;
        font-family:"Times New Roman",serif;}
    p{
        text-align: left;
        text-indent: 0px;
        widows: 2;
        orphans: 2;
        padding: 0px 0px 0px 0px;
        margin: 0px 0px 0px 0px;
    }
    h1
    {mso-style-link:"Titre 1 Car";
        margin-top:0cm;
        margin-right:0cm;
        margin-bottom:0cm;
        margin-left:279.0pt;
        margin-bottom:.0001pt;
        text-align:justify;
        page-break-after:avoid;
        font-size:12.0pt;
        font-family:"Times New Roman",serif;}
    h2
    {mso-style-link:"Titre 2 Car";
        margin-top:12.0pt;
        margin-right:0cm;
        margin-bottom:3.0pt;
        margin-left:0cm;
        page-break-after:avoid;
        font-size:14.0pt;
        font-family:"Calibri Light",sans-serif;
        font-style:italic;}
    p.MsoHeader, li.MsoHeader, div.MsoHeader
    {mso-style-link:"En-tête Car";
        margin:0cm;
        margin-bottom:.0001pt;
        font-size:12.0pt;
        font-family:"Times New Roman",serif;
    }
    p.MsoFooter, li.MsoFooter, div.MsoFooter
    {mso-style-link:"Pied de page Car";
        margin:0cm;
        margin-bottom:.0001pt;
        font-size:12.0pt;
        font-family:"Times New Roman",serif;}
    p.MsoBodyText2, li.MsoBodyText2, div.MsoBodyText2
    {mso-style-link:"Corps de texte 2 Car";
        margin:0cm;
        margin-bottom:.0001pt;
        text-align:justify;
        font-size:14.0pt;
        font-family:"Times New Roman",serif;}
    p.MsoAcetate, li.MsoAcetate, div.MsoAcetate
    {mso-style-link:"Texte de bulles Car";
        margin:0cm;
        margin-bottom:.0001pt;
        font-size:9.0pt;
        font-family:"Segoe UI",sans-serif;}
    span.Titre1Car
    {mso-style-name:"Titre 1 Car";
        mso-style-link:"Titre 1";
        font-family:"Times New Roman",serif;
        font-weight:bold;}
    span.En-tteCar
    {mso-style-name:"En-tête Car";
        mso-style-link:En-tête;
        font-family:"Times New Roman",serif;}
    span.PieddepageCar
    {mso-style-name:"Pied de page Car";
        mso-style-link:"Pied de page";
        font-family:"Times New Roman",serif;}
    span.TextedebullesCar
    {mso-style-name:"Texte de bulles Car";
        mso-style-link:"Texte de bulles";
        font-family:"Segoe UI",sans-serif;}
    span.Corpsdetexte2Car
    {mso-style-name:"Corps de texte 2 Car";
        mso-style-link:"Corps de texte 2";
        font-family:"Times New Roman",serif;}
    span.Titre2Car
    {mso-style-name:"Titre 2 Car";
        mso-style-link:"Titre 2";
        font-family:"Calibri Light",sans-serif;
        font-weight:bold;
        font-style:italic;}
    .MsoChpDefault
    {font-family:"Calibri",sans-serif;}
    /* Page Definitions */
    @page WordSection1
    {size:595.3pt 841.9pt;
        margin:177.2pt 35.35pt 14.75pt 35.45pt;}
    div.WordSection1
    {page:WordSection1;}
    -->
</style>
</head>
<body lang=FR>
<button id="cmd">generate PDF</button>
<div id="content">
<p>MINISTERE DE L&rsquo;ADMINISTRATION &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong> REPUBLIQUE DE COTE D&rsquo;IVOIRE</strong></p>
<p>DU TERRITOIRE ET DE LA &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Union-Discipline-Travail</p>
 <p>DECENTRALISATION</p>
    <div id="logo">
        <img src="<%=request.getContextPath()%>/resources/front-end/images/oneci.png"><br>
        <img src="<%=request.getContextPath()%>/resources/front-end/images/titre.png">
        <p><strong>DIRECTION GENERALE</strong></p>
        <p align="center" style="margin-top: 0.08in; margin-bottom: 0in; border-top: none; border-bottom: 2px solid #000001; border-left: none; border-right: none; padding-top: 0in; padding-bottom: 0.01in; padding-left: 0in; padding-right: 0in; line-height: 150%">

        </p>
</span></p>
</div>
<div class=WordSection1>

    <p class=MsoNormal style='margin-left:318.6pt;text-align:justify;text-indent:
35.4pt'><span lang=EN-AU style='font-size:14.0pt;font-family:"Cambria",serif'>&nbsp;</span></p>

    <p class=MsoNormal style='margin-left:318.6pt;text-align:justify;text-indent:
35.4pt'><span lang=EN-AU style='font-size:14.0pt;font-family:"Cambria",serif'> Abidjan,
le</span></p>

    <p class=MsoNormal style='margin-right:-14.2pt;text-align:justify'><span
            lang=EN-AU style='font-size:14.0pt;font-family:"Cambria",serif'>N°_____________/MATED/ONECI/DG
</span></p>

    <p class=MsoNormal><span lang=EN-AU style='font-size:1.0pt;font-family:"Cambria",serif'>&nbsp;</span></p>

    <p class=MsoNormal><span lang=EN-AU style='font-size:16.0pt;font-family:"Cambria",serif'>&nbsp;</span></p>

    <p class=MsoNormal align=center style='text-align:center'><b><u><span
            style='font-size:20.0pt;font-family:"Cambria",serif'>ORDRE DE MISSION</span></u></b></p>

    <p class=MsoNormal align=center style='text-align:center'><b><u><span
            style='font-size:18.0pt;font-family:"Cambria",serif'><span style='text-decoration:
 none'>&nbsp;</span></span></u></b></p>

    <p class=MsoNormal style='text-align:justify'><span style='font-size:16.0pt;
font-family:"Cambria",serif'>Le Directeur Général de l’Office National de
l’Etat Civil et de l’Identification donne l’ordre à&nbsp;:</span></p>

    <p class=MsoNormal><span style='font-size:7.0pt;font-family:"Cambria",serif'>&nbsp;</span></p>

    <p class=MsoNormal><span style='font-size:8.0pt;font-family:"Cambria",serif'> </span><b><span
            style='font-size:18.0pt;font-family:"Cambria",serif'>         </span></b></p>

    <p class=MsoNormal align=center style='text-align:center;text-indent:16.05pt'><b><span
            style='font-size:16.0pt;font-family:"Cambria",serif'> <c:if test="${ordreMission.personnel.sexe eq 'Masculin'}">  Monsieur ${ordreMission.personnel.nomComplet}</c:if>
        <c:if test="${ordreMission.personnel.sexe eq 'Feminin'}">  Madame ${ordreMission.personnel.nomComplet}</c:if>
</span></b></p>

    <p class=MsoNormal style='text-indent:14.05pt'><b><span style='font-size:14.0pt;
font-family:"Cambria",serif'>&nbsp;</span></b></p>

    <p class=MsoNormal style='margin-left:212.4pt;text-align:justify;text-indent:
-212.4pt;line-height:115%'><b><span style='font-size:14.0pt;line-height:115%;
font-family:"Cambria",serif'>MATRICULE                                       
</span></b><span style='font-size:14.0pt;line-height:115%;font-family:"Cambria",serif'>:<b>&nbsp;&nbsp;</b><span
    >${ordreMission.personnel.matricule}</span></span></p>

    <p class=MsoNormal style='text-indent:14.05pt'><b><span style='font-size:14.0pt;
font-family:"Cambria",serif'>&nbsp;</span></b></p>

    <p class=MsoNormal style='margin-left:212.4pt;text-align:justify;text-indent:
-212.4pt;line-height:115%'><b><span style='font-size:14.0pt;line-height:115%;
font-family:"Cambria",serif'>FONCTION                                          
</span></b><span style='font-size:14.0pt;line-height:115%;font-family:"Cambria",serif'>:<b>   </b><span
            >${ordreMission.fonctionMission}</span></span></p>

    <p class=MsoNormal style='margin-left:212.4pt;text-align:justify;text-indent:
-212.4pt;line-height:115%'><span style='font-size:8.0pt;line-height:115%;
font-family:"Cambria",serif'>&nbsp;</span></p>

    <p class=MsoNormal style='line-height:150%'><b><span style='font-size:14.0pt;
line-height:150%;font-family:"Cambria",serif'>SERVICE                                                </span></b><span
            style='font-size:14.0pt;line-height:150%;font-family:"Cambria",serif'>:<b>    </b>${ordreMission.missions.service}
</span></p>

    <h1 style='margin-left:212.4pt;text-indent:-212.4pt;line-height:150%'><span
            lang=ES style='font-size:14.0pt;line-height:150%;font-family:"Cambria",serif'>DE
SE RENDRE A</span><span lang=ES style='font-size:14.0pt;line-height:150%;
font-family:"Cambria",serif;font-weight:normal'>                               :
  ${ordreMission.lieuMission} </span></h1>

    <p class=MsoNormal style='margin-left:7.0cm;text-align:justify;text-indent:
-7.0cm'><b><span lang=ES style='font-size:14.0pt;font-family:"Cambria",serif'>NATURE
DE LA MISSION</span></b><b><span style='font-size:14.0pt;font-family:"Cambria",serif'>   
          </span></b><span lang=ES style='font-size:15.0pt;font-family:"Cambria",serif'>:
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style='font-size:15.0pt;font-family:"Cambria",serif'>
        ${ordreMission.missions.natureMission}</span><b><span style='font-size:18.0pt;font-family:
"Arial",sans-serif;color:#002060'> </span></b></p>

    <h1 style='margin-left:212.4pt;text-indent:-212.4pt;line-height:150%'><span
            style='font-size:14.0pt;line-height:150%;font-family:"Cambria",serif;
font-weight:normal'>&nbsp;</span></h1>

    <h1 style='margin-left:0cm;line-height:150%'><span lang=X-NONE
                                                       style='font-size:14.0pt;line-height:150%;font-family:"Cambria",serif'>DATE DE
DEPART</span><span lang=X-NONE style='font-size:14.0pt;line-height:150%;
font-family:"Cambria",serif;font-weight:normal'>                            </span><span
            lang=X-NONE style='font-size:14.0pt;line-height:150%;font-family:"Cambria",serif'>  :</span><span
            lang=X-NONE style='font-size:14.0pt;line-height:150%;font-family:"Cambria",serif;
font-weight:normal'>    </span><span style='font-size:14.0pt;line-height:150%;
font-family:"Cambria",serif;font-weight:normal'>${ordreMission.dDebut}</span></h1>

    <p class=MsoNormal style='line-height:150%'><b><span style='font-size:14.0pt;
line-height:150%;font-family:"Cambria",serif'>DATE DE RETOUR                           </span></b><span
            style='font-size:14.0pt;line-height:150%;font-family:"Cambria",serif'> :<b>    </b>${ordreMission.dRet}</span></p>

    <p class=MsoNormal style='line-height:150%'><b><span style='font-size:14.0pt;
line-height:150%;font-family:"Cambria",serif'>MOYEN DE TRANSPORT              </span></b><span
            style='font-size:14.0pt;line-height:150%;font-family:"Cambria",serif'>  :<b>    </b>Transport
en commun</span></p>

    <p class=MsoNormal style='margin-left:212.4pt;text-indent:-212.25pt'><b><span
            style='font-size:14.0pt;font-family:"Cambria",serif'>IMPUTATION BUDGETAIRE</span></b><span
            style='text-align: left;font-size:14.0pt;font-family:"Cambria",serif'>&nbsp;                 :   ${ordreMission.missions.imputationBudg}</span></p>

    <p class=MsoNormal style='margin-left:212.4pt;text-indent:-212.25pt'><span
            style='font-size:13.0pt;font-family:"Cambria",serif'>&nbsp;</span></p>

    <p class=MsoNormal style='margin-left:212.4pt;text-indent:-212.25pt'><span
            style='font-size:11.0pt;font-family:"Cambria",serif'>&nbsp;</span></p>

    <p class=MsoNormal style='margin-left:212.4pt;text-indent:-212.25pt'><span
            style='font-size:28.0pt;font-family:"Cambria",serif'>&nbsp;</span></p>

    <p class=MsoNormal style='margin-left:212.4pt;text-indent:-212.25pt'><span
            style='font-size:28.0pt;font-family:"Cambria",serif'>&nbsp;</span></p>

    <p class=MsoNormal style='margin-left:212.4pt;text-indent:-212.25pt'><span
            style='font-size:16.0pt;font-family:"Cambria",serif'>&nbsp;</span></p>

    <p class=MsoNormal><span style='font-size:14.0pt;font-family:"Cambria",serif'> </span><span
            style='font-family:"Calibri",sans-serif'>                </span></p>

    <p class=MsoNormal style='margin-left:311.8pt;text-align:justify;text-indent:
6.8pt'><b><u><span style='font-size:16.0pt;font-family:"Cambria",serif'>Sitionni
Gnénin KAFANA</span></u></b></p>

    <p class=MsoNormal style='margin-left:311.8pt;text-align:justify;text-indent:
6.8pt'><span style='font-size:16.0pt;font-family:"Cambria",serif'>       </span><span
            style='font-size:14.0pt;font-family:"Cambria",serif'>Le Directeur Général</span></p>

    <p class=MsoNormal style='text-align:justify'><span style='font-family:"Cambria",serif'>&nbsp;</span></p>
    <p align="center" style="margin-top: 0.08in; margin-bottom: 0in; border-top: none; border-bottom: 3px solid #000001; border-left: none; border-right: none; padding-top: 0in; padding-bottom: 0.01in; padding-left: 0in; padding-right: 0in; line-height: 150%">

    </p>
    <p align="center" style="margin-bottom: 0in; line-height: 150%"><FONT FACE="Arial Narrow, serif"><FONT SIZE=2 STYLE="font-size: 9pt">19
        boulevard Botreau Roussel; BPV 168 Abidjan; Tel: (225) 20 30 79 00&nbsp;;
        Fax&nbsp;: (225) 20 24 29 13&nbsp;; Site WEB&nbsp;: www.oneci.ci</FONT></FONT></p>

</div>
</div>
<script type="text/javascript">
    var doc = new jsPDF();
    window.html2canvas = html2canvas;
    function genPDF() {
        html2canvas(document.getElementById("content"), {
            onrendered: function(canvas) {

                var imgData = canvas.toDataURL('image/png');
                console.log('Report Image URL: '+imgData);
                var doc = new jsPDF('p','mm',[231,350]); //{unit: 'mm', format: 'a4', orientation: 'portrait' }210mm wide and 297mm high

                doc.addImage(imgData, 'PNG', 10, 10);
                doc.save('sample.pdf');
            }
        });

    }

    $('#cmd').click(function() {
       // alert("pommmm");
        genPDF();
    });
    jQuery(document).ready(function ($) {
        //genPDF();

// $table = $('#table');
//     $table.bootstrapTable();
    });
</script>
</body>

</html>

