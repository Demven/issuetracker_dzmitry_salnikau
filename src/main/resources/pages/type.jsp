<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><c:out value="${pageTitle}"/></title>
<link rel="stylesheet" href="/issuetracker/resources/css/_header.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/type.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/_footer.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>

<div class="content">
	<div id="content_title"><c:out value="${pageTitle}"/></div>
    
    <c:choose>
        <c:when test="${empty editType}">
            <!-- Create type -->
            <form id="type_form" action="/issuetracker/type" method="POST">
                <input id="type_name" name="name" type="text" maxlength="45" placeholder="Name of type"/>
                <input id="type_submit" type="submit" name="submit" value="Create"/>
            </form>
        </c:when>
       	<c:when test="${not empty editType}">
        	<!-- Edit type -->
            <form id="type_form" action="/issuetracker/type/${editType.typeId}" method="POST">
                <input id="type_name" name="name" value="${editType.name}" type="text" maxlength="45" placeholder="Name of type"/>
                <input id="type_submit" type="submit" name="submit" value="Done"/>
            </form>
       	</c:when>
	</c:choose>
</div>

<%@ include file="_footer.jsp" %>

</body>
</html>