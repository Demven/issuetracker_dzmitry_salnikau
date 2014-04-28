<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>BugTracker</title>

<link rel="stylesheet" href="/issuetracker/resources/css/_header.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/index.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/_footer.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>

<div class="content">
    <div id="content_title"><c:out value="${pageTitle}"/><a href="?lang=en">en</a>|<a href="?lang=ru">ru</a></div>
    
    <c:if test="${not empty latestIssues or not empty assignedIssues or not empty searchedIssues}">
        <div class="issue_table">
            <div class="header_line">
                <div class="id">Id</div>
                <div class="priority"><spring:message code="label.index.priority"/></div>
                <div class="assignee"><spring:message code="label.index.assignee"/></div>
                <div class="type"><spring:message code="label.index.type"/></div>
                <div class="status"><spring:message code="label.index.status"/></div>
                <div class="summary"><spring:message code="label.index.summary"/></div>
            </div>
            <div id="issue_lines">
                <c:choose>
                    <c:when test="${not empty latestIssues}">
                        <c:forEach items="${latestIssues}" var="issue">
                            <!-- issue-line (for a guest, with latest issue) -->
                            <div class="issue_line">
                                <div class="id"><c:out value="${issue.issueId}"/></div>
                                <div class="priority" id="${fn:toLowerCase(issue.priority.name)}"><c:out value="${issue.priority.name}"/></div>
                                <div class="assignee"><c:out value="${issue.assignee.firstName}"/> <c:out value="${issue.assignee.lastName}"/></div>
                                <div class="type"><c:out value="${issue.type.name}"/></div>
                                <div class="status"><c:out value="${issue.status.name}"/></div>
                                <div class="summary"><c:out value="${issue.summary}"/></div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:when test="${not empty assignedIssues}">
                        <c:forEach items="${assignedIssues}" var="issue">
                            <!-- issue-line (for an authorized user, with assigned issue) -->
                            <div class="issue_line">
                                <div class="id"><a href="/issuetracker/issue/${issue.issueId}"><c:out value="${issue.issueId}"/></a></div>
                                <div class="priority" id="${fn:toLowerCase(issue.priority.name)}"><c:out value="${issue.priority.name}"/></div>
                                <div class="assignee"><c:out value="${issue.assignee.firstName}"/> <c:out value="${issue.assignee.lastName}"/></div>
                                <div class="type"><c:out value="${issue.type.name}"/></div>
                                <div class="status"><c:out value="${issue.status.name}"/></div>
                                <div class="summary"><c:out value="${issue.summary}"/></div>
                            </div>
                        </c:forEach>
                    </c:when>
					<c:when test="${not empty searchedIssues}">
                        <c:forEach items="${searchedIssues}" var="issue">
                            <!-- issue-line (for an issue that matches the search query) -->
                            <div class="issue_line">
                                <div class="id"><a href="/issuetracker/issue/${issue.issueId}"><c:out value="${issue.issueId}"/></a></div>
                                <div class="priority" id="${fn:toLowerCase(issue.priority.name)}"><c:out value="${issue.priority.name}"/></div>
                                <div class="assignee"><c:out value="${issue.assignee.firstName}"/> <c:out value="${issue.assignee.lastName}"/></div>
                                <div class="type"><c:out value="${issue.type.name}"/></div>
                                <div class="status"><c:out value="${issue.status.name}"/></div>
                                <div class="summary"><c:out value="${issue.summary}"/></div>
                            </div>
                        </c:forEach>
                    </c:when>
                </c:choose>    
            </div>
        </div>
	</c:if>
	
	<c:if test="${not empty searchFilter and not empty pages and not empty currentPage}">
        <!-- pages -->
        <div class="page_container">
            <c:forEach items="${pages}" varStatus="index">
                <c:choose>
                    <c:when test="${currentPage eq index.count}">
                        <a href="/issuetracker/search/${searchFilter}/page/${index.count}">
                        	<div class="page" id="current_page"><c:out value="${index.count}"/></div>
                        </a>
                    </c:when>
                    <c:otherwise>
                    	<a href="/issuetracker/search/${searchFilter}/page/${index.count}">
                        	<div class="page"><c:out value="${index.count}"/></div>
                        </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </c:if>
</div>
	
<%@ include file="_footer.jsp" %>

</body>
</html>