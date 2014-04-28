<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><c:out value="${pageTitle}"/></title>
<link rel="stylesheet" href="/issuetracker/resources/css/_header.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/project.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/_footer.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>

<div class="content">
	<div id="content_title"><c:out value="${pageTitle}"/></div>
    
    <c:choose>
        <c:when test="${empty editProject}">
            <!-- Create project -->
            <form id="project_form" name="project_form" action="/issuetracker/project" method="POST">
            	<spring:message code="label.project.project_form.name" var="i18n_name"/>
                <input id="project_name" name="name" type="text" maxlength="45" placeholder="${i18n_name}">
                <spring:message code="label.project.project_form.description" var="i18n_description"/>
                <textarea id="project_description" name="description" rows="4" placeholder="${i18n_description}"></textarea>
                <spring:message code="label.project.project_form.init_build" var="i18n_init_build"/>
                <input id="project_build" name="buildName" type="text" maxlength="45" placeholder="${i18n_init_build}">
                <select id="project_manager" name="managerId" size="1">
                    <option selected disabled value="0"><spring:message code="label.project.project_form.manager"/></option>
                    <c:forEach items="${managers}" var="manager">
                        <option value="${manager.userId}"><c:out value="${manager.firstName}"/> <c:out value="${manager.lastName}"/></option>
                    </c:forEach>
                </select>
                <spring:message code="label.project.project_form.create" var="i18n_create"/>
                <input id="project_submit" type="button" onClick="trySubmit();" value="${i18n_create}">
            </form>
			<script type="text/javascript" language="javascript">
				// Check all fields and if all is fine - submit 
				function trySubmit(){
					if(isNameValid()){
						if(isDescriptionValid()){
							if(isBuildValid()){
								if(isManagerValid()){
									// all is fine - submit!
									document.forms.project_form.submit();
								}
							}
						}
					}
				}
			</script>
        </c:when>
       	<c:when test="${not empty editProject}">
        	<!-- Edit project -->
            <form name="project_form" id="project_form" action="/issuetracker/project/${editProject.projectId}" method="POST">
            	<spring:message code="label.project.project_form.name" var="i18n_name"/>
                <input id="project_name" name="name" value="${editProject.name}" type="text" maxlength="45" placeholder="${i18n_name}">
                <spring:message code="label.project.project_form.description" var="i18n_description"/>
                <textarea id="project_description" name="description" rows="4" placeholder="${i18n_description}"><c:out value="${editProject.description}"/></textarea>
                <div class="build_container">
                	<!-- List of builds -->
                    <select id="project_select_build" name="buildId" size="1">
                        <option selected disabled value="0"><spring:message code="label.project.project_form.build"/></option>
                        <c:forEach items="${builds}" var="build">
                        	<option value="${build.buildId}"><c:out value="${build.version}"/></option>
                   	    </c:forEach>
                    </select>
                    <!-- Text field for a new build -->
                    <spring:message code="label.project.project_form.new_build" var="i18n_new_build"/>
                    <input id="project_new_build" name="buildName" type="text" maxlength="45" placeholder="${i18n_new_build}" style="display:none">
                    <!-- Buttons add-clear -->
                    <spring:message code="label.project.project_form.return_to_list" var="i18n_return_to_list"/>
                    <div id="clear_build" title="${i18n_return_to_list}" onClick="clearBuild();" style="display:none"></div>
                    <spring:message code="label.project.project_form.add_build" var="i18n_add_build"/>
                    <div id="add_build" title="${i18n_add_build}" onClick="addBuild();"></div>
                </div>
                <select id="project_manager" name="managerId" size="1">
                  	<option selected disabled value="0"><spring:message code="label.project.project_form.manager"/></option>
                    <c:forEach items="${managers}" var="manager">
                    	<c:choose>
                            <c:when test="${manager.userId eq editProject.manager.userId}">
                                <option value="${manager.userId}" selected><c:out value="${manager.firstName}"/> <c:out value="${manager.lastName}"/></option>
                            </c:when>
                            <c:otherwise>
                                 <option value="${manager.userId}"><c:out value="${manager.firstName}"/> <c:out value="${manager.lastName}"/></option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
                <spring:message code="label.project.project_form.done" var="i18n_done"/>
                <input id="project_submit" type="button" onClick="trySubmit();" value="${i18n_done}">
            </form>
			<script type="text/javascript" language="javascript">
				// Check all fields and if all is fine - submit 
				function trySubmit(){
					if(isNameValid()){
						if(isDescriptionValid()){
							if(isBuildWithNewBuldValid()){
								if(isManagerValid()){
									// all is fine - submit!
									document.forms.project_form.submit();
								}
							}
						}
					}
				}
			</script>
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

<spring:message code="label.project.js.popup.name_short" var="i18n_name_short"/>
<spring:message code="label.project.js.popup.name_invalid" var="i18n_name_invalid"/>
<spring:message code="label.project.js.popup.name_long" var="i18n_name_long"/>
<spring:message code="label.project.js.popup.name_should_enter" var="i18n_name_should_enter"/>

<spring:message code="label.project.js.popup.description_short" var="i18n_description_short"/>
<spring:message code="label.project.js.popup.description_should_enter" var="i18n_description_should_enter"/>

<spring:message code="label.project.js.popup.initial_build_should_enter" var="i18n_initial_build_should_enter"/>
<spring:message code="label.project.js.popup.build_should_choose" var="i18n_build_should_choose"/>
<spring:message code="label.project.js.popup.manager_should_choose" var="i18n_manager_should_choose"/>

<script type="text/javascript" language="javascript">
	function  isNameValid(){
		var projectName = document.getElementById("project_name").value.trim();
		var regexp = /\!|\@|\№|\%|\^|\&|\*|\(|\)|\_|\=|\+|\?|\"|\'|\;|\<|\>|\,|\.|\`|\~/;
		if(projectName != ""){
			if(projectName.length < 2){
				showErrorPopupWindow("${i18n_name_short}");
				return false;
			} else if(projectName.search(regexp) != -1){
				showErrorPopupWindow("${i18n_name_invalid}");
				return false;
			} else if(projectName.length > 40){
				showErrorPopupWindow("${i18n_name_long}");
				return false;
			} else{
				// projectName is ok
				return true;
			}
		} else{
			showErrorPopupWindow("${i18n_name_should_enter}");
			return false;
		}
	}
	
	function  isDescriptionValid(){
		var projectDescription = document.getElementById("project_description").value.trim();
		if(projectDescription != ""){
			if(projectDescription.length < 20){
				showErrorPopupWindow("${i18n_description_short}");
				return false;
			} else{
				// projectDescription is ok
				return true;
			}
		} else{
			showErrorPopupWindow("${i18n_description_should_enter}");
			return false;
		}
	}
	
	function  isBuildValid(){
		var projectBuild = document.getElementById("project_build").value.trim();
		if(projectBuild != ""){
			// projectBuild is ok
			return true;
		} else{
			showErrorPopupWindow("${i18n_initial_build_should_enter}");
			return false;
		}
	}
	
	function  isBuildWithNewBuldValid(){
		var projectSelectBuild = document.getElementById("project_select_build").value;
		var projectNewBuild = document.getElementById("project_new_build").value.trim();
		if(projectSelectBuild != 0 || projectNewBuild != ""){
			// projectBuild is ok
			return true;
		} else{
			showErrorPopupWindow("${i18n_build_should_choose}");
			return false;
		}
	}
	
	function  isManagerValid(){
		var projectManager = document.getElementById("project_manager").value;
		if(projectManager != 0){
			// projectManager is ok
			return true;
		} else{
			showErrorPopupWindow("${i18n_manager_should_choose}");
			return false;
		}
	}
</script>

<%@ include file="_footer.jsp" %>

</body>
</html>