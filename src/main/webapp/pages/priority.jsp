<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><c:out value="${pageTitle}"/></title>
<link rel="stylesheet" href="./css/_header.css" type="text/css" />
<link rel="stylesheet" href="./css/priority.css" type="text/css" />
<link rel="stylesheet" href="./css/_footer.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>

<div class="content">
	<div id="content_title"><c:out value="${pageTitle}"/></div>
    
    <c:choose>
        <c:when test="${empty editPriority}">
            <!-- Create priority -->
            <form id="priority_form" action="main">
                <input type="hidden" name="command" value="createPriority" />
                <input id="priority_name" name="name" type="text" maxlength="45" placeholder="Name of priority"/>
                <input id="priority_submit" type="submit" name="submit" value="Create"/>
            </form>
        </c:when>
       	<c:when test="${not empty editPriority}">
        	<!-- Edit priority -->
            <form id="priority_form" action="main">
                <input type="hidden" name="command" value="editPriority" />
                <input type="hidden" name="priorityId" value="${editPriority.priorityId}" />
                <input id="priority_name" name="name" value="${editPriority.name}" type="text" maxlength="45" placeholder="Name of priority"/>
                <input id="priority_submit" type="submit" name="submit" value="Done"/>
            </form>
       	</c:when>
	</c:choose>
</div>

<%@ include file="_footer.jsp" %>

</body>
</html>