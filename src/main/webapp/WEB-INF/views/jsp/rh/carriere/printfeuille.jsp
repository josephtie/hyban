<%@page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:url value="/" var="contextPath"/>



<HTML>
<HEAD>
    <META HTTP-EQUIV="CONTENT-TYPE" CONTENT="text/html; charset=utf-8">
    <TITLE>fiche de route</TITLE>
    <META NAME="GENERATOR" CONTENT="LibreOffice 4.1.6.2 (Linux)">
    <META NAME="AUTHOR" CONTENT="namene">
    <META NAME="CREATED" CONTENT="20200325;44800000000000">
    <META NAME="CHANGEDBY" CONTENT="CHRISLAIN GNANHOUAN">
    <META NAME="CHANGED" CONTENT="20200325;44800000000000">
    <META NAME="AppVersion" CONTENT="15.0000">
    <META NAME="DocSecurity" CONTENT="0">
    <META NAME="HyperlinksChanged" CONTENT="false">
    <META NAME="LinksUpToDate" CONTENT="false">
    <META NAME="ScaleCrop" CONTENT="false">
    <META NAME="ShareDoc" CONTENT="false">
    <STYLE TYPE="text/css">
        <!--
        @page { margin-left: 0.89in; margin-right: 0.69in }
        P { margin-bottom: 0.08in; direction: ltr; widows: 2; orphans: 2 }
        body {
            position: relative;
            width: 21cm;
            height: 29.7cm;
            margin: 0 auto;
            color: #555555;
            background: #FFFFFF;
        }
        -->
    </STYLE>
</HEAD>
<BODY LANG="fr-FR" DIR="LTR">
<button id="cmd">generate PDF</button>
<div id="content">
<P STYLE="margin-bottom: 0.14in">
    <IMG SRC="<%=request.getContextPath()%>/resources/front-end/images/oneci.png" NAME="Picture 191" ALIGN=LEFT HSPACE=12 WIDTH=70 HEIGHT=65 BORDER=0><SPAN DIR="LTR" STYLE="float: left; width: 1.98in; height: 0.36in; border: none; padding: 0.05in 0.1in; background: #ffffff">
<P ALIGN=CENTER STYLE="margin-top: 0.19in; margin-bottom: 0.19in; line-height: 100%">
    <FONT FACE="Times New Roman, serif"><FONT SIZE=3><FONT COLOR="#000000"><FONT FACE="Arial Narrow, serif"><FONT SIZE=2 STYLE="font-size: 9pt"><B>                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;REPUBLIQUE
        DE  COTE D’IVOIRE</B></FONT></FONT></FONT><FONT COLOR="#000000"><FONT SIZE=2 STYLE="font-size: 9pt">
    </FONT></FONT></FONT></FONT>
</P>
<P ALIGN=CENTER STYLE="margin-top: 0.19in; margin-bottom: 0.19in; line-height: 100%">
    <FONT FACE="Times New Roman, serif"><FONT SIZE=3><FONT COLOR="#000000"><FONT FACE="Arial Narrow, serif"><FONT SIZE=2 STYLE="font-size: 9pt">                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Union-Discipline-Travail</FONT></FONT></FONT></FONT></FONT></P>
</SPAN>
<SPAN DIR="LTR" STYLE="float: left; width: 1.96in; height: 0.56in; border: none; padding: 0.05in 0.1in; background: #ffffff">
	<P STYLE="margin-bottom: 0in; line-height: 100%"><FONT FACE="Arial Narrow, serif"><FONT SIZE=2 STYLE="font-size: 9pt"><B>MINISTERE
	DE L’ADMINISTRATION</B></FONT></FONT></P>
	<P STYLE="margin-bottom: 0in; line-height: 100%"><FONT FACE="Arial Narrow, serif"><FONT SIZE=2 STYLE="font-size: 9pt"><B>DU
	TERRITOIRE ET DE LA DECENTRALISATION</B></FONT></FONT></P>
	<P STYLE="margin-bottom: 0in; line-height: 100%"><BR>
	</P>
</SPAN>
</P>
<P ALIGN=CENTER STYLE="margin-bottom: 0.14in"><BR><BR>
</P>
<P ALIGN=CENTER STYLE="margin-bottom: 0.17in"><BR><BR>
</P>
<P ALIGN=CENTER STYLE="margin-left: 0.39in; margin-bottom: 0in"><FONT SIZE=6><B>FEUILLE
    DE DEPLACEMENT N°</B></FONT><FONT SIZE=1 style="font-size:21.0pt;">  ${feuilleDeplacement.id}</FONT></P>
<OL ALIGN=LEFT>
    <LI><P STYLE="margin-bottom: 0.04in"><FONT SIZE=1 STYLE="font-size: 8pt">Nom
        et prénoms</FONT></P>
    <LI><P STYLE="margin-bottom: 0.04in"><A NAME="_GoBack"></A><FONT SIZE=1 STYLE="font-size: 8pt">Grade
        ou fonction</FONT></P>
    <LI><P STYLE="margin-bottom: 0.04in"><FONT SIZE=1 STYLE="font-size: 8pt">Catégorie</FONT></P>
    <LI><P STYLE="margin-bottom: 0.04in"><FONT SIZE=1 STYLE="font-size: 8pt">Nom et grade de l’Autorité qui délivre la feuille de déplacement</FONT></P>


</OL>

</P>
<P ALIGN=JUSTIFY STYLE="margin-left: 0.20in; margin-right: -0.1in; margin-bottom: 0in; line-height: 150%">
    Délivrée à  (1)
    M.<FONT SIZE=1; STYLE="margin-left: 0.20in; margin-right: -0.1in; margin-bottom: 0in; line-height: 150%;font-size:14.0pt;">${feuilleDeplacement.missionsPersonnel.personnel.nomComplet}</FONT>
    <%--<SPAN DIR="LTR" STYLE="float: left;alignment: left; width: 1.4in; height: 1.58in; border: 1px solid #000000; padding: 0.05in 0.1in; background: #ffffff">--%>
<%--<P STYLE="margin-bottom: 0.14in"><BR><BR>--%>
<%--</P>--%>
<%--<P STYLE="margin-bottom: 0.14in"><FONT SIZE=1 STYLE="font-size: 8pt">Signature--%>
    <%--du titulaire     de la présente feuille</FONT></P>--%>
<%--</SPAN>--%>
</P></br>
<P ALIGN=JUSTIFY STYLE="margin-left: 0.20in; margin-right: -0.2in; margin-bottom: 0in; line-height: 150%">
    <SUB>(2)  </SUB><FONT SIZE=1 style="font-size:14.0pt;">${feuilleDeplacement.contratpersonnel.fonction.libelle}</FONT>

</P>
<P ALIGN=JUSTIFY STYLE="margin-left: 0.20in; margin-right: -0.1in; margin-bottom: 0in; line-height: 150%;font-size:14.0pt;">
     partant de &nbsp;&nbsp;&nbsp;&nbsp;<FONT SIZE=1 style="font-size:14.0pt;">&nbsp;&nbsp;${feuilleDeplacement.partantDe} &nbsp;&nbsp;</FONT>le<FONT SIZE=1 style="font-size:14.0pt;">&nbsp;&nbsp;${feuilleDeplacement.dCreation} &nbsp;&nbsp;</FONT>à<FONT SIZE=1>….…………………………</FONT>heures</P>
<P ALIGN=JUSTIFY STYLE="margin-left: 0.20in; margin-right: -0.1in; margin-bottom: 0in; line-height: 150%">
    pour se rendre
    à<FONT SIZE=1 style="font-size:14.0pt;">&nbsp;&nbsp;${feuilleDeplacement.destinantionMission}&nbsp;&nbsp;</FONT></P>
<P ALIGN=JUSTIFY STYLE="margin-left: 0.20in; margin-bottom: 0in; line-height: 150%;font-size:14.0pt;">
    ………………………………………………………………<FONT SIZE=1>
</FONT>suivant ordre de service n°<FONT SIZE=1 style="font-size:14.0pt;">&nbsp;&nbsp;${feuilleDeplacement.missionsPersonnel.id}</FONT>&nbsp;&nbsp;en
    <br>   date du&nbsp;&nbsp;<FONT SIZE=1 style="font-size:14.0pt;">&nbsp;&nbsp; ${feuilleDeplacement.missionsPersonnel.dCreation}</FONT>de<FONT SIZE=1>10:40</FONT></P>
<P ALIGN=JUSTIFY STYLE="margin-left: 0.20in; margin-right: -0.1in; margin-bottom: 0in; line-height: 150%;font-size:14.0pt;">
    accompagné
    de &nbsp;&nbsp;<FONT SIZE=1 style="font-size:14.0pt;">${feuilleDeplacement.accompagnateur}</FONT></P>
<br ALIGN=JUSTIFY STYLE="margin-left: 0.20in; margin-bottom: 0in; line-height: 150%">
    groupe&nbsp;&nbsp;<FONT SIZE=1 style="font-size:14.0pt;">${feuilleDeplacement.groupe}</FONT>
    <SUB>(3) </SUB> indice &nbsp;&nbsp; ${feuilleDeplacement.indice} itinéraire à suivre, avances à faire, le cas
   échéant<FONT SIZE=1 style="font-size:14.0pt;">&nbsp;&nbsp;${feuilleDeplacement.itineraireAccompagnateur}</FONT></br>Nature
    du déplacement (temporaire ou définitif)<FONT SIZE=1 style="font-size:14.0pt;">
    &nbsp;&nbsp; ${feuilleDeplacement.naturDeplacemnt}</FONT><SPAN DIR="LTR" STYLE="float: left; width: 1.29in; height: 1.44in; border: none; padding: 0.05in 0.1in; background: #ffffff">
<P ALIGN=JUSTIFY STYLE="margin-bottom: 0.14in"><FONT SIZE=1 STYLE="font-size: 8pt">Le
    titulaire de la présente feuille doit s’assurer que toutes les
    indications nécessaires à la constatation des droits ont été
    apposées par le fonctionnaire ou agent compétent</FONT></P>
</SPAN>
</P>
<P ALIGN=CENTER STYLE="margin-left: 0.89in; margin-bottom: 0in; line-height: 150%">
    Poids de bagages dont le transport est autorisé<FONT SIZE=1>.………………………………………..……………..…..…………</FONT></P>

<P ALIGN=CENTER STYLE="margin-left: 0.89in; margin-right: -0.1in; margin-bottom: 0in; line-height: 150%;">
    Délivrée par nous, <SUB>(4) </SUB><FONT SIZE=1 style="font-size:14.0pt;">  ${feuilleDeplacement.autoriteAutorise}&nbsp;&nbsp; LE ${feuilleDeplacement.titreAutorise}</FONT></P>

<P ALIGN=JUSTIFY STYLE="margin-bottom: 0in; line-height: 150%">

    Abidjan le,<FONT SIZE=1> ……………………………………………………………………………..…………….…………..…</FONT></P>

<P ALIGN=JUSTIFY STYLE="margin-bottom: 0in; line-height: 150%"><BR>
</P>
<P ALIGN=JUSTIFY STYLE="margin-bottom: 0in; line-height: 150%"><BR>
</P>
<P ALIGN=JUSTIFY STYLE="margin-bottom: 0in; line-height: 150%"><BR>
</P>
<SPAN DIR="LTR" STYLE="float: left;alignment: left; width: 1.4in; height: 1.58in; border: 1px solid #000000; padding: 0.05in 0.1in; background: #ffffff">
    <P STYLE="margin-bottom: 0.14in"><BR><BR>
   </P>
    <P STYLE="margin-bottom: 0.14in"><FONT SIZE=1 STYLE="font-size: 8pt">Signature
    du titulaire     de la présente feuille</FONT></P>
    </SPAN>
<P ALIGN=JUSTIFY STYLE="margin-bottom: 0in; line-height: 150%"><BR>
</P>
<P ALIGN=JUSTIFY STYLE="margin-bottom: 0in; line-height: 150%"><BR>

</P>
<P STYLE="margin-bottom: 0in; line-height: 150%"><BR>
</P>
<P STYLE="margin-bottom: 0in; line-height: 150%"><BR>
</P>
    </div>
<script type="text/javascript">
    var doc = new jsPDF();
    window.html2canvas = html2canvas;
    function genPDF() {
        html2canvas(document.getElementById("content"), {
            onrendered: function(canvas) {

                var imgData = canvas.toDataURL('image/png');
                console.log('Report Image URL: '+imgData);
                var doc = new jsPDF('p','mm',[210,350]); //{unit: 'mm', format: 'a4', orientation: 'portrait' }210mm wide and 297mm high

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
</BODY>
</HTML>