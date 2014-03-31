<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><c:out value="${pageTitle}"/></title>
<link rel="stylesheet" href="./css/_header.css" type="text/css" />
<link rel="stylesheet" href="./css/projects.css" type="text/css" />
<link rel="stylesheet" href="./css/_footer.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>

<div class="content">
	<div id="content_title"><c:out value="${pageTitle}"/></div>
    <div class="project_table">
    	<div class="header_line">
   	  	  <div class="name">Name</div>
          <div class="manager">Manager</div>
            <div class="description">Description</div>
        </div>
        <div id="project_lines">
        	<c:forEach items="${projects}" var="project">
                <div class="project_line">
                    <div class="name">
                    	<a href="main?command=editProject&projectId=${project.projectId}">
                    		<c:out value="${project.name}"/>
                        </a>
                    </div>
                    <div class="manager"><c:out value="${project.manager.firstName}"/> <c:out value="${project.manager.lastName}"/></div>
                    <div class="description"><c:out value="${project.description}"/></div>
                </div>
            </c:forEach>
	  </div>
    </div>
    
    <c:if test="${not empty pages and not empty currentPage}">
        <!-- pages -->
        <div class="page_container">
            <c:forEach items="${pages}" varStatus="index">
                <c:choose>
                    <c:when test="${currentPage eq index.count}">
                        <a href="main?command=viewProjects&page=${index.count}">
                        	<div class="page" id="current_page"><c:out value="${index.count}"/></div>
                        </a>
                    </c:when>
                    <c:otherwise>
                    	<a href="main?command=viewProjects&page=${index.count}">
                        	<div class="page"><c:out value="${index.count}"/></div>
                        </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </c:if>
</div>


<div class="footer"></div>

</body>
</html>