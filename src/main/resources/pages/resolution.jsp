<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><c:out value="${pageTitle}"/></title>
<link rel="stylesheet" href="/issuetracker/resources/css/_header.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/resolution.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/_footer.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>

<div class="content">
	<div id="content_title"><c:out value="${pageTitle}"/></div>
    
    <c:choose>
        <c:when test="${empty editResolution}">
            <!-- Create resolution -->
            <form id="resolution_form" action="/issuetracker/resolution" method="POST">
            	<spring:message code="label.resolution.resolution_form.name" var="i18n_name"/>
                <input id="resolution_name" name="name" type="text" maxlength="45" placeholder="${i18n_name}"/>
                <spring:message code="label.resolution.resolution_form.create" var="i18n_create"/>
                <input id="resolution_submit" type="submit" name="submit" value="${i18n_create}"/>
            </form>
        </c:when>
       	<c:when test="${not empty editResolution}">
        	<!-- Edit resolution -->
            <form id="resolution_form" action="/issuetracker/resolution/${editResolution.resolutionId}" method="POST">
                <spring:message code="label.resolution.resolution_form.name" var="i18n_name"/>
                <input id="resolution_name" name="name" value="${editResolution.name}" type="text" maxlength="45" placeholder="${i18n_name}"/>
                <spring:message code="label.resolution.resolution_form.done" var="i18n_done"/>
                <input id="resolution_submit" type="submit" name="submit" value="${i18n_done}"/>
            </form>
       	</c:when>
	</c:choose>
</div>

<%@ include file="_footer.jsp" %>

</body>
</html>