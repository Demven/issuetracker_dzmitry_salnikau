﻿<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><c:out value="${pageTitle}"/></title>
<link rel="stylesheet" href="/issuetracker/resources/css/_header.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/priority.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/_footer.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>

<div class="content">
	<div id="content_title"><c:out value="${pageTitle}"/></div>
    
    <c:choose>
        <c:when test="${empty editPriority}">
            <!-- Create priority -->
            <form id="priority_form" action="/issuetracker/priority" method="POST">
            	<spring:message code="label.priority.priority_form.name" var="i18n_name"/>
                <input id="priority_name" name="name" type="text" maxlength="45" placeholder="${i18n_name}"/>
                <spring:message code="label.priority.priority_form.create" var="i18n_create"/>
                <input id="priority_submit" type="submit" name="submit" value="${i18n_create}"/>
            </form>
        </c:when>
       	<c:when test="${not empty editPriority}">
        	<!-- Edit priority -->
            <form id="priority_form" action="/issuetracker/priority/${editPriority.priorityId}" method="POST">
            	<spring:message code="label.priority.priority_form.name" var="i18n_name"/>
                <input id="priority_name" name="name" value="${editPriority.name}" type="text" maxlength="45" placeholder="${i18n_name}"/>
                <spring:message code="label.priority.priority_form.done" var="i18n_done"/>
                <input id="priority_submit" type="submit" name="submit" value="${i18n_done}"/>
            </form>
       	</c:when>
	</c:choose>
</div>

<%@ include file="_footer.jsp" %>

</body>
</html>