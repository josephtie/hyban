<%@page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:url value="/" var="contextPath"/>
<ul class="breadcrumb">
    <li><a href="#">${littleTitle}</a></li>
    <li class="active">${bigTitle}</li>
</ul>
<!-- END BREADCRUMB -->

<div class="page-title">
    <h2><span class="${icon}"></span> ${bigTitle}</h2>
</div>

<%--<ul class="breadcrumbs">--%>
    <%--<li><a href="${contextPath}main/welcome"><i class="iconfa-home"></i></a> <span class="separator"></span></li>--%>
    <%--<li>${littleTitle} <span class="separator"></span></li>--%>
    <%--<li>${bigTitle}</li>--%>
<%--</ul>--%>

<%--<div class="pageheader">--%>
    <%--<div class="pageicon"><span class="${icon}"></span></div>--%>
    <%--<div class="pagetitle">--%>
        <%--<h5>${littleTitle}</h5>--%>
        <%--<h1>${bigTitle}</h1>--%>
    <%--</div>--%>
<%--</div><!--pageheader-->--%>

<%--<div class="page-bar">--%>
    <%--<ul class="page-breadcrumb">--%>
        <%--<li>--%>
            <%--<a href="index.html">${littleTitle}</a>--%>
            <%--<i class="fa fa-circle"></i>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<span>${bigTitle}</span>--%>
        <%--</li>--%>
    <%--</ul>--%>
    <%--<div class="page-toolbar">--%>
        <%--<div id="dashboard-report-range" class="pull-right tooltips btn btn-sm" data-container="body" data-placement="bottom" data-original-title="Change dashboard date range">--%>
            <%--<i class="icon-calendar"></i>&nbsp;--%>
            <%--<span class="thin uppercase hidden-xs"></span>&nbsp;--%>
            <%--<i class="fa fa-angle-down"></i>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
<%--<div>--%>
    <%--&lt;%&ndash;<i><span class="${icon}"></span></i>&ndash;%&gt;--%>
    <%--<h1 >${littleTitle}</h1>--%>
    <%--<h5>${bigTitle}</h5>--%>

<%--</div>--%>