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
