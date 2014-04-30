<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><c:out value="${pageTitle}"/></title>
<link rel="stylesheet" href="/issuetracker/resources/css/_header.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/status.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/_footer.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>

<div class="content">
	<div id="content_title"><c:out value="${pageTitle}"/></div>

    <c:if test="${not empty editStatus}">
        <!-- Edit status -->
        <form id="status_form" action="/issuetracker/status/${editStatus.statusId}" method="POST">
            <spring:message code="label.status.status_form.name" var="i18n_name"/>
            <input id="status_name" name="name" value="${editStatus.name}" type="text" maxlength="45" placeholder="${i18n_name}"/>
            <spring:message code="label.status.status_form.done" var="i18n_done"/>
            <input id="status_submit" type="submit" name="submit" value="${i18n_done}"/>
        </form>
    </c:if>
</div>

<%@ include file="_footer.jsp" %>

</body>
</html>