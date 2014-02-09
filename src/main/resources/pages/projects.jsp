<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><c:out value="${pageTitle}"/></title>
<link rel="stylesheet" href="./css/_header.css" type="text/css" />

<style type="text/css">
/* ------- */
/* CONTENT */
.content{
	height: auto;
	padding: 130px 0 70px 0;
}
#content_title{
	height: 90px;
	font-family: "Century Gothic";
	font-size: 75px;
	padding-bottom: 20px;
	width: 1080px;
	margin: 0 auto;
}
/* table */
.project_table{
	height: auto;
	border-radius: 6px;
	border: 1px solid #bbb;
	width: 1080px;
	margin: 0 auto;
}
/* header line */
.header_line{
	height: 40px;
	text-align: center;
	font-size: 20px;
	line-height: 40px;
	font-family: Verdana;
	background-color: #ddd;
	width: 1080px;
	border-radius: 6px 6px 0 0;
}
.header_line .name{
	float: left;
	height: 40px;
	width: 200px;
	border-right: 1px solid #bbb;
}
.header_line .manager{
	float: left;
	height: 40px;
	width: 160px;
	border-right: 1px solid #bbb;
}
.header_line .description{
	float: left;
	height: 40px;
	width: 717px;
}
/* project line */
.project_line{
	height: 46px;
	text-align: center;
	font-size: 12px;
	line-height: 46px;
	font-family: Verdana;
	width: 1080px;
	border-top: 1px solid #bbb;
}
.project_line:hover{
	background-color:#FFFFFF;
}
.project_line .name{
	float: left;
	height: 46px;
	width: 200px;
	border-right: 1px solid #bbb;
	cursor: pointer;
}
.project_line .name:hover{
	text-decoration:underline;
}
.project_line .manager{
	float: left;
	height: 46px;
	width: 160px;
	border-right: 1px solid #bbb;
}
.project_line .description{
	float: left;
	height: 46px;
	width: 711px;
	line-height: 46px;
	padding: 0 3px 0 3px;
	font-size: 11px;
}

/* pages */
.page_container{
	height: auto;
	width: 1080px;
	margin: 40px auto 0 auto;
}
.page_container .page{
	float: left;
	width: 32px;
	height: 32px;
	border: 1px solid #ccc;
	border-radius: 16px;
	text-align: center;
	line-height: 32px;
	margin-right: 12px;
	font-size: 18px;
	font-family: Verdana;
	cursor: pointer;
	color: #555;
}
.page#current_page{
	background-color: #689E3C;
	color: #FFFFFF;
}

/* ------- */
/* FOOTER */
.footer{
	height: 50px;
	background-color: #FFFFFF;
	position: fixed;
	bottom: 0px;
	z-index: 999;
	width: 100%;
	border-top: 1px solid #ccc;
}
</style>


</head>

<body>
<%@ include file="_header.jsp" %>

<div class="content">
	<div id="content_title">Projects</div>
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