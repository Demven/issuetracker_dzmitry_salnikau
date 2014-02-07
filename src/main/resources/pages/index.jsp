<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>BugTracker</title>

<link rel="stylesheet" href="./css/_header.css" type="text/css" />
<link rel="stylesheet" href="./css/index.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>

<div class="content">

	<!-- title depends on the type of the issues' list -->
	<c:choose>
        <c:when test="${not empty latestIssues}">
            <div id="content_title">Latest issues</div>
        </c:when>
        <c:when test="${not empty assignedIssues}">
        	<div id="content_title">Assigned issues</div>
        </c:when>
    </c:choose>
    
    <div class="issue_table">
    	<div class="header_line">
       	  <div class="id">Id</div>
            <div class="priority">Priority</div>
            <div class="assignee">Assignee</div>
          <div class="type">Type</div>
       	  <div class="status">Status</div>
       	  <div class="summary">Summary</div>
        </div>
        <div id="issue_lines">
            <c:choose>
                <c:when test="${not empty latestIssues and not empty assignees}">
                    <c:forEach items="${latestIssues}" var="issue">
                        <c:forEach items="${assignees}" var="assignee">
                            <c:if test="${issue.assignee eq assignee.userId}">
                                <!-- issue-line (for a guest, with latest issue) -->
                                <div class="issue_line">
                                    <div class="id"><c:out value="${issue.issueId}"/></div>
                                    <div class="priority" id="${fn:toLowerCase(issue.priority)}"><c:out value="${fn:toLowerCase(issue.priority)}"/></div>
                                    <div class="assignee"><c:out value="${assignee.firstName}"/> <c:out value="${assignee.lastName}"/></div>
                                    <div class="type"><c:out value="${fn:toLowerCase(issue.type)}"/></div>
                                    <div class="status"><c:out value="${fn:toLowerCase(issue.status)}"/></div>
                                    <div class="summary"><c:out value="${issue.summary}"/></div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </c:when>
                <c:when test="${not empty assignedIssues and not empty loginUser}">
                	<c:forEach items="${assignedIssues}" var="issue">
                        <!-- issue-line (for an authorized user, with assigned issue) -->
                        <div class="issue_line">
                            <div class="id"><c:out value="${issue.issueId}"/></div>
                            <div class="priority" id="${fn:toLowerCase(issue.priority)}"><c:out value="${fn:toLowerCase(issue.priority)}"/></div>
                            <div class="assignee"><c:out value="${loginUser.firstName}"/> <c:out value="${loginUser.lastName}"/></div>
                            <div class="type"><c:out value="${fn:toLowerCase(issue.type)}"/></div>
                            <div class="status"><c:out value="${fn:toLowerCase(issue.status)}"/></div>
                            <div class="summary"><c:out value="${issue.summary}"/></div>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>    
		</div>
    </div>
</div>
	
<div class="footer"></div>

</body>
</html>