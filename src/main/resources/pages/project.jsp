<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><c:out value="${pageTitle}"/></title>
<link rel="stylesheet" href="./css/_header.css" type="text/css" />
<link rel="stylesheet" href="./css/project.css" type="text/css" />
<link rel="stylesheet" href="./css/_footer.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>

<div class="content">
	<div id="content_title"><c:out value="${pageTitle}"/></div>
    
    <c:choose>
        <c:when test="${empty editProject}">
            <!-- Create project -->
            <form id="project_form" action="main">
            	<input type="hidden" name="command" value="createProject" />
                <input id="project_name" name="name" type="text" maxlength="45" placeholder="Name of project">
                <textarea id="project_description" name="description" rows="4" placeholder="Description"></textarea>
                <input id="project_build" name="buildName" type="text" maxlength="45" placeholder="Initial build">
                <select id="project_manager" name="managerId" size="1">
                    <option selected disabled value="0">Manager</option>
                    <c:forEach items="${managers}" var="manager">
                        <option value="${manager.userId}"><c:out value="${manager.firstName}"/> <c:out value="${manager.lastName}"/></option>
                    </c:forEach>
                </select>
                <input id="project_submit" type="submit" name="submit" value="Create">
            </form>
        </c:when>
       	<c:when test="${not empty editProject}">
        	<!-- Edit project -->
            <form id="project_form" action="main">
            	<input type="hidden" name="command" value="editProject" />
                <input type="hidden" name="projectId" value="${editProject.projectId}" />
                <input id="project_name" name="name" value="${editProject.name}" type="text" maxlength="45" placeholder="Name of project">
                <textarea id="project_description" name="description" rows="4" placeholder="Description"><c:out value="${editProject.description}"/></textarea>
                <div class="build_container">
                	<!-- List of builds -->
                    <select id="project_select_build" name="buildId" size="1">
                        <option selected disabled value="0">Build</option>
                        <c:forEach items="${builds}" var="build">
                        	<option value="${build.buildId}"><c:out value="${build.version}"/></option>
                   	    </c:forEach>
                    </select>
                    <!-- Text field for a new build -->
                    <input id="project_new_build" name="buildName" type="text" maxlength="45" placeholder="New build" style="display:none">
                    <!-- Buttons add-clear -->
                    <div id="clear_build" title="Return to the list" onClick="clearBuild();" style="display:none"></div>
                    <div id="add_build" title="Add new build" onClick="addBuild();"></div>
                </div>
                <select id="project_select_manager" name="managerId" size="1">
                  	<option selected disabled value="0">Manager</option>
                    <c:forEach items="${managers}" var="manager">
                    	<c:choose>
                            <c:when test="${manager.userId eq editProject.manager}">
                                <option value="${manager.userId}" selected><c:out value="${manager.firstName}"/> <c:out value="${manager.lastName}"/></option>
                            </c:when>
                            <c:otherwise>
                                 <option value="${manager.userId}"><c:out value="${manager.firstName}"/> <c:out value="${manager.lastName}"/></option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
                <input id="project_submit" type="submit" name="submit" value="Done">
            </form>
            <script type="text/javascript" language="javascript">
                var add_build = document.getElementById("add_build");
                var clear_build = document.getElementById("clear_build");
                var project_new_build = document.getElementById("project_new_build");
                var project_select_build = document.getElementById("project_select_build");
                
                // Replace list of builds by text field to unput new build
                function addBuild(){
                    // hide list of builds
                    project_select_build.style.display= "none";
                    // show text field
                    project_new_build.style.display = "block";
                    // change buttons add-clear
                    add_build.style.display= "none";
                    clear_build.style.display = "block";
                }
                
                // Replace text field for a new build by list of builds
                function clearBuild(){
                    // show list of builds
                    project_select_build.style.display = "block";
                    // hide text field
                    project_new_build.style.display= "none";
                    // change buttons add-clear
                    add_build.style.display= "block";
                    clear_build.style.display = "none";
                }
            </script>
       	</c:when>
	</c:choose>
</div>

<%@ include file="_footer.jsp" %>

</body>
</html>