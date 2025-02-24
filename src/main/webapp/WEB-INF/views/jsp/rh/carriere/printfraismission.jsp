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

    /* ========== Text Styles ========== */
    hr { color: #000000}
    body, table, span.rvts0 /* Font Style */
    {
        font-size: 12pt;
        font-family: 'Times New Roman', 'Times', serif;
        font-style: normal;
        font-weight: normal;
        color: #000000;
        text-decoration: none;
    }
    span.rvts1
    {
        font-size: 14pt;
        font-family: 'Cambria';
    }
    span.rvts2
    {
        font-size: 1pt;
        font-family: 'Cambria';
    }
    span.rvts3
    {
        font-size: 10pt;
        font-family: 'Amphion';
        font-weight: bold;
    }
    span.rvts4
    {
        font-size: 14pt;
        font-family: 'Calibri Light';
        font-style: italic;
        font-weight: bold;
        color: #000080;
        text-decoration: underline;
    }
    span.rvts5
    {
        font-family: 'Britannic Bold';
        font-weight: bold;
    }
    span.rvts6
    {
        font-weight: bold;
    }
    span.rvts7
    {
        font-weight: bold;
    }
    span.rvts8
    {
    }
    span.rvts9
    {
        /*background-color: #f0f0f0;*/
    }
    span.rvts10
    {
        font-weight: bold;
    }
    span.rvts11
    {
        font-size: 14pt;
        font-weight: bold;
    }
    span.rvts12
    {
        font-size: 8pt;
        font-weight: bold;
    }
    span.rvts13
    {
        font-size: 14pt;
        font-style: italic;
        font-weight: bold;
    }
    span.rvts14
    {
        font-size: 14pt;
        font-weight: bold;
    }
    span.rvts15
    {
        font-size: 13pt;
        font-family: 'Cambria';
    }
    span.rvts16
    {
        font-size: 24pt;
        font-family: 'Cambria';
    }
    span.rvts17
    {
        font-size: 36pt;
        font-family: 'Cambria';
    }
    span.rvts18
    {
        font-family: 'Calibri';
    }
    span.rvts19
    {
        font-size: 16pt;
        font-family: 'Cambria';
        font-weight: bold;
        text-decoration: underline;
    }
    span.rvts20
    {
        font-size: 16pt;
        font-family: 'Cambria';
    }
    /* ========== Para Styles ========== */
    p,ul,ol /* Paragraph Style */
    {
        text-align: left;
        text-indent: 0px;
        widows: 2;
        orphans: 2;
        padding: 0px 0px 0px 0px;
        margin: 0px 0px 0px 0px;
    }
    .rvps1
    {
        text-align: justify;
        text-justify: inter-word;
        text-align-last: auto;
        text-indent: 47px;
        widows: 2;
        orphans: 2;
        margin: 0px 0px 0px 425px;
    }
    .rvps2
    {
        text-align: justify;
        text-justify: inter-word;
        text-align-last: auto;
        widows: 2;
        orphans: 2;
    }
    .rvps3
    {
        text-align: center;
        widows: 2;
        orphans: 2;
    }
    .rvps4
    {
        text-align: center;
        text-indent: 0px;
        page-break-after: avoid;
        widows: 2;
        orphans: 2;
        padding: 0px 0px 0px 0px;
        margin: 16px 0px 4px 0px;
    }
    .rvps5
    {
        text-align: center;
        widows: 2;
        orphans: 2;
    }
    .rvps6
    {
        text-align: left;
        text-indent: 0px;
        page-break-after: avoid;
        widows: 2;
        orphans: 2;
        padding: 0px 0px 0px 0px;
        margin: 16px 0px 4px 0px;
    }
    .rvps7
    {
        text-align: center;
        text-indent: 0px;
        page-break-after: avoid;
        widows: 2;
        orphans: 2;
        padding: 0px 0px 0px 0px;
        margin: 16px 0px 4px 0px;
    }
    .rvps8
    {
        widows: 2;
        orphans: 2;
        margin: 0px 0px 0px 1px;
    }
    .rvps9
    {
        text-align: justify;
        text-align-last: auto;
        text-indent: 0px;
        page-break-after: avoid;
        widows: 2;
        orphans: 2;
        padding: 0px 0px 0px 0px;
        margin: 0px 0px 8px 372px;
    }
    .rvps10
    {
        text-align: justify;
        text-align-last: auto;
        text-indent: 0px;
        page-break-after: avoid;
        widows: 2;
        orphans: 2;
        padding: 0px 0px 0px 0px;
        margin: 0px 0px 0px 372px;
    }
    .rvps11
    {
        text-align: justify;
        text-align-last: auto;
        text-indent: 47px;
        page-break-after: avoid;
        widows: 2;
        orphans: 2;
        padding: 0px 0px 0px 0px;
        margin: 0px 0px 0px 0px;
    }
    .rvps12
    {
        text-indent: -283px;
        widows: 2;
        orphans: 2;
        margin: 0px 0px 0px 283px;
    }
    .rvps13
    {
        widows: 2;
        orphans: 2;
    }
    .rvps14
    {
        text-align: justify;
        text-justify: inter-word;
        text-align-last: auto;
        text-indent: 9px;
        widows: 2;
        orphans: 2;
        margin: 0px 0px 0px 416px;
    }
    -->
</style>
</head>
<body lang=FR>
</br>
<button id="cmd">generate PDF</button>
</br>
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
    <p class=MsoNormal style='margin-left:318.6pt;text-align:justify;text-indent:
35.4pt'><span lang=EN-AU style='font-size:14.0pt;font-family:"Cambria",serif'>&nbsp;</span></p>

    <p class=MsoNormal style='margin-left:318.6pt;text-align:justify;text-indent:
35.4pt'><span lang=EN-AU style='font-size:14.0pt;font-family:"Cambria",serif'> Abidjan,
le</span></p>

    <p class=MsoNormal style='margin-right:-14.2pt;text-align:justify'><span
            lang=EN-AU style='font-size:14.0pt;font-family:"Cambria",serif'>N°_____________/MATED/ONECI/DG
</span></p>
    </br></br>
    <p class=MsoNormal><span lang=EN-AU style='font-size:1.0pt;font-family:"Cambria",serif'>&nbsp;</span></p>

    <p class=MsoNormal><span lang=EN-AU style='font-size:16.0pt;font-family:"Cambria",serif'>&nbsp;</span></p>
    <p class=rvps1><span class=rvts1><br></span></p>
    <p><span class=rvts2><br></span></p>
    <table width=790 border="3"    style="border-width: 3px; border-collapse: collapse;">
        <tbody>
        <tr>
            <td style="width: 529.016px;"  class="rvts4" >
                </br>
                <p style="text-align: center;color:#0c57a3;font-size:20.0pt;text-transform: uppercase"><strong>${ordreMission.missions.natureMission}</strong></p>
                <p><strong>&nbsp;</strong></p>
                <p style="text-align: center;color:#0c57a3"><strong>DU ${ordreMission.dDebut} AU ${ordreMission.dRet}</strong></p>
                <p><strong>&nbsp;</strong></p>
                <p style="text-align: center;color: #cd0a0a" ><strong>${ordreMission.lieuMission}</strong></p>
            </td>
        </tr>
        </tbody>
    </table>
    <p class=rvps3><span class=rvts3><br></span></p>
    <h2 class=rvps4><span class=rvts0><span class=rvts4>NOTE DE FRAIS</span></span></h2>
    <div class=rvps8>
        <table width=700 border="3" style="border-width: 3px; border-collapse: collapse;">
        <tr valign=top>
            <td width=18 height=26 valign=top style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><p class=rvps5><span class=rvts5><br></span></p>
                <p class=rvps3><span class=rvts5><br></span></p>
            </td>
            <td width=211 height=26 valign=middle style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><p class=rvps3><span class=rvts6>NOM ET PRENOMS</span></p>
            </td>
            <td width=135 height=26 valign=middle style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><p class=rvps3><span class=rvts6>FONCTION</span></p>
            </td>
            <td width=57 height=26 valign=middle style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><p class=rvps3><span class=rvts6>Nbre&nbsp; nuitées</span></p>
            </td>
            <td width=87 height=26 valign=middle style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><h4 class=rvps6><span class=rvts0><span class=rvts7>MONTANT</span></span></h4>
                <p class=rvps3><span class=rvts6>journalier</span></p>
            </td>
            <td width=96 height=26 valign=middle style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><p class=rvps3><span class=rvts6> Total frais&nbsp; déplacement</span></p>
            </td>
            <td width=96 height=26 valign=middle style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><p class=rvps3><span class=rvts6>Emargement</span></p>
            </td>
        </tr>
        <tr valign=top>
            <td width=18 height=49 valign=middle style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><p class=rvps3><span class=rvts8>1</span></p>
            </td>
            <td width=211 height=49 valign=middle style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><p class=rvps3><span class=rvts9>${ordreMission.personnel.nomComplet} (Mle : ${ordreMission.personnel.matricule})</span></p>
            </td>
            <td width=135 height=49 valign=middle style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><p class=rvps3><span class=rvts9>${ordreMission.fonctionMission}</span></p>
            </td>
            <td width=57 height=49 valign=middle style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><h3 class=rvps7><span class=rvts0><span class=rvts10>${ordreMission.nbrenuite}</span></span></h3>
            </td>
            <td width=87 height=49 valign=middle style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><p class=rvps3><span class=rvts8>${ordreMission.mMontant}</span></p>
            </td>
            <td width=96 height=49 valign=middle style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><p class=rvps3><span class=rvts8>${ordreMission.mtotal}</span></p>
            </td>
            <td width=96 height=49 valign=middle style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><p class=rvps3><span class=rvts8><br></span></p>
            </td>
        </tr>
        <tr valign=top>
            <td colspan=5 width=472 height=38 valign=middle style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><p class=rvps3><span class=rvts11>TOTAL</span></p>
            </td>
            <td width=96 height=38 valign=middle style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><p class=rvps3><span class=rvts6>${ordreMission.mtotal}</span></p>
            </td>
            <td width=96 height=38 valign=top style="border-width : 2px; border-color: #000000; border-style: solid; padding: 0px 5px;"><p class=rvps3><span class=rvts11><br></span></p>
            </td>
        </tr>
    </table>
    </div>
    <%--<h1 class=rvps9><span class=rvts0><span class=rvts12><br></span></span></h1>--%>
    <%--<h1 class=rvps10><span class=rvts0><span class=rvts12><br></span></span></h1>--%>
    <%--<h1 class=rvps10><span class=rvts0><span class=rvts13><br></span></span></h1>--%>
    <h1 class=rvps11><span class=rvts0><span class=rvts13>Arrêté la présente note de frais à la somme de</span></span><span class=rvts0><span class=rvts14> : ${somLettre} CFA</span></span></h1>


    <p class=rvps12><span class=rvts1><br></span></p>
    <p class=rvps12><span class=rvts17><br></span></p>
    <p class=rvps13><span class=rvts1>&nbsp;</span><span class=rvts18>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></p>
    <p class=rvps14><span class=rvts19>Sitionni Gnénin KAFANA</span></p>
    <p class=rvps14><span class=rvts20>&nbsp;&nbsp;&nbsp;&nbsp; </span><span class=rvts1>Le Directeur Général</span></p>
    <%--<p class=MsoNormal style='margin-left:311.8pt;text-align:justify;text-indent:--%>
<%--6.8pt'><b><u><span style='font-size:16.0pt;font-family:"Cambria",serif'>Sitionni--%>
<%--Gnénin KAFANA</span></u></b></p>--%>

    <%--<p class=MsoNormal style='margin-left:311.8pt;text-align:justify;text-indent:--%>
<%--6.8pt'><span style='font-size:16.0pt;font-family:"Cambria",serif'>       </span><span--%>
            <%--style='font-size:14.0pt;font-family:"Cambria",serif'>Le Directeur Général</span></p>--%>

    <p class=MsoNormal style='text-align:justify'><span style='font-family:"Cambria",serif'>&nbsp;</span></p>
    <p align="center" style="margin-top: 0.08in; margin-bottom: 0in; border-top: none; border-bottom: 3px solid #000001; border-left: none; border-right: none; padding-top: 0in; padding-bottom: 0.01in; padding-left: 0in; padding-right: 0in; line-height: 150%">

    </p>
    <p align="center" style="margin-bottom: 0in; line-height: 150%"><FONT FACE="Arial Narrow, serif"><FONT SIZE=2 STYLE="font-size: 9pt">19
        boulevard Botreau Roussel; BPV 168 Abidjan; Tel: (225) 20 30 79 00&nbsp;;
        Fax&nbsp;: (225) 20 24 29 13&nbsp;; Site WEB&nbsp;: www.oneci.ci</FONT></FONT></p>
</div>
<script type="text/javascript">
    var doc = new jsPDF();
    window.html2canvas = html2canvas;
    function genPDF() {
        html2canvas(document.getElementById("content"), {
            onrendered: function(canvas) {

                var imgData = canvas.toDataURL('image/png');
                console.log('Report Image URL: '+imgData);
                var doc = new jsPDF('p','mm',[221,310]); //{unit: 'mm', format: 'a4', orientation: 'portrait' }210mm wide and 297mm high

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

       // genPDF();
// $table = $('#table');
//     $table.bootstrapTable();
    });
</script>
</body>

</html>

