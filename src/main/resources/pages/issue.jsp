<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><c:out value="${pageTitle}"/></title>
<link rel="stylesheet" href="/issuetracker/resources/css/_header.css" type="text/css" />
<c:choose>
    <c:when test="${empty editIssue}">
        <!-- Create issue --> 
        <link rel="stylesheet" href="/issuetracker/resources/css/issue_create.css" type="text/css" />
    </c:when>
    <c:when test="${not empty editIssue}">
        <!-- Edit issue -->
        <link rel="stylesheet" href="/issuetracker/resources/css/issue_edit.css" type="text/css" />
    </c:when>
</c:choose>
<link rel="stylesheet" href="/issuetracker/resources/css/_footer.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>

<div class="content">

	 <spring:message code="label.issue.js.popup.summary_short" var="i18n_summary_short"/>
     <spring:message code="label.issue.js.popup.summary_invalid" var="i18n_summary_invalid"/>
     <spring:message code="label.issue.js.popup.summary_long" var="i18n_summary_long"/>
     <spring:message code="label.issue.js.popup.summary_should_enter" var="i18n_summary_should_enter"/>
     
     <spring:message code="label.issue.js.popup.description_short" var="i18n_description_short"/>
     <spring:message code="label.issue.js.popup.description_invalid" var="i18n_description_invalid"/>
     <spring:message code="label.issue.js.popup.description_long" var="i18n_description_long"/>
     <spring:message code="label.issue.js.popup.description_should_enter" var="i18n_description_should_enter"/>
     
     <spring:message code="label.issue.js.popup.status_choose" var="i18n_status_choose"/>
     <spring:message code="label.issue.js.popup.resolution_choose" var="i18n_resolution_choose"/>
     <spring:message code="label.issue.js.popup.type_choose" var="i18n_type_choose"/>
     <spring:message code="label.issue.js.popup.priority_choose" var="i18n_priority_choose"/>
     <spring:message code="label.issue.js.popup.project_choose" var="i18n_project_choose"/>
     <spring:message code="label.issue.js.popup.build_choose" var="i18n_build_choose"/>
     <spring:message code="label.issue.js.popup.assignee_choose" var="i18n_assignee_choose"/>

    <c:choose>
        <c:when test="${empty editIssue}">
            <!-- Create issue --> 
            <div id="content_title"><c:out value="${pageTitle}"/></div>
            
            <form id="issue_form" action="/issuetracker/issue" method="POST">
            	<spring:message code="label.issue.issue_form.summary" var="i18n_summary"/>
                <input id="issue_summary" name="summary" type="text" maxlength="45" placeholder="${i18n_summary}">
                <spring:message code="label.issue.issue_form.description" var="i18n_description"/>
                <textarea id="issue_description" name="description" rows="4" placeholder="${i18n_description}"></textarea>
                <select id="issue_status" name="statusIndex" size="1" onChange="setStatusChanged();">
                    <option selected disabled value="0"><spring:message code="label.issue.issue_form.status"/></option>
                    <option value="1">New</option>
                    <option value="2">Assigned</option>
                </select>
                <select id="issue_type" name="typeId" size="1">
                    <option selected disabled value="0"><spring:message code="label.issue.issue_form.type"/></option>
                    <c:forEach items="${types}" var="type">
                        <option value="${type.typeId}"><c:out value="${type.name}"/></option>
                    </c:forEach>
                </select>
                <select id="issue_priority" name="priorityId" size="1">
                    <option selected disabled value="0"><spring:message code="label.issue.issue_form.priority"/></option>
                    <c:forEach items="${priorities}" var="priority">
                        <option value="${priority.priorityId}"><c:out value="${priority.name}"/></option>
                    </c:forEach>
                </select>
                <select id="issue_project" name="projectId" size="1" onChange="setProjectChanged();">
                    <option selected disabled value="0"><spring:message code="label.issue.issue_form.project"/></option>
                    <c:forEach items="${projects}" var="project">
                        <option value="${project.projectId}"><c:out value="${project.name}"/></option>
                    </c:forEach>
                </select>
                <select id="issue_build" name="buildId" size="1">
                    <option selected disabled value="0"><spring:message code="label.issue.issue_form.build"/></option>
                </select>
                <select id="issue_assignee" name="assigneeId" size="1">
                    <option selected disabled value="0"><spring:message code="label.issue.issue_form.assignee"/></option>
                    <c:forEach items="${users}" var="assignee">
                        <option value="${assignee.userId}"><c:out value="${assignee.firstName}"/> <c:out value="${assignee.lastName}"/></option>
                    </c:forEach>
                </select>
                <spring:message code="label.issue.issue_form.done" var="i18n_done"/>
                <input id="issue_submit" type="button" onClick="trySubmit();" value="${i18n_done}">
            </form>
            
            <c:if test="${not empty builds}">
            	<script type="text/javascript" language="javascript">
					var projects = <c:out value="${builds}" escapeXml="false"/>;
				</script>
            </c:if>
            
			<script type="text/javascript" language="javascript">
                var STATUS_INDEX_NEW = 1;
                var STATUS_INDEX_ASSIGNED = 2;
                
                var isAssigned = false;
                
                var statusSelect = document.getElementById("issue_status");
                var assigneeSelect = document.getElementById("issue_assignee");
                var projectSelect = document.getElementById("issue_project");
                var buildSelect = document.getElementById("issue_build");
                
                var buildTemplate = buildSelect.innerHTML;
                
                function setStatusChanged(){
                    var index = statusSelect.selectedIndex;
                    if(index == STATUS_INDEX_NEW){
                        // Hide assigneeSelect
                        assigneeSelect.style.display = "none";
                        isAssigned = false;
                    } else if(index == STATUS_INDEX_ASSIGNED){
                        // Show assigneeSelect
                        assigneeSelect.style.display = "block";
                        isAssigned = true;
                    }
                }
                
                function setProjectChanged(){
                    var projectId = projectSelect.value;
                    if(projectId != 0){
                        // Show builds for this project
                        showBuilds(projectId);
                    }
                }
                
                function showBuilds(projectId){
                    // At first - clear all old build-options
                    buildSelect.innerHTML = buildTemplate;
                    
                    // Create new build-options
                    for(i=0; i < projects.length; i++){
                        if(projects[i].id == projectId){
                            var builds = projects[i].builds;
                            for(i=0; i < builds.length; i++){
                                addBuildOption(builds[i].name, builds[i].id);
                            }
                            break;
                        }
                    }
                    
                    // show this builds in the select-list
                    buildSelect.style.display = "block";
                }
                
                // Create new option with received values and add it to the select-list of builds
                function addBuildOption(text, value){
                    var option = document.createElement("option");
                    option.appendChild(document.createTextNode(text));
                    option.setAttribute("value", value);
                    
                    buildSelect.appendChild(option);
                }
            </script>
            
            <script type="text/javascript" language="javascript">
                var issueForm = document.getElementById("issue_form");
                
                // Check all fields and if all is fine - submit 
                function trySubmit(){
                    if(isSummaryValid()){
                        if(isDescriptionValid()){
                            if(isStatusValid()){
                                if(isTypeValid()){
                                    if(isPriorityValid()){
                                        if(isProjectValid()){
                                            if(isBuildValid()){
                                                if(isAssigned){
                                                    // Check assignee-list
                                                    if(isAssigneeValid()){
                                                        // all is fine - submit!
                                                        issueForm.submit();
                                                    }
                                                } else{
                                                    // all is fine - submit!
                                                    issueForm.submit();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            
                function isSummaryValid(){
                    var summary = document.getElementById("issue_summary").value.trim();
                    var regexp = /\<|\>|\~/;
                    if(summary != ""){
                        if(summary.length < 15){
                            showErrorPopupWindow("${i18n_summary_short}");
                            return false;
                        } else if(summary.search(regexp) != -1){
                            showErrorPopupWindow("${i18n_summary_invalid}");
                            return false;
                        } else if(summary.length > 20){
                            showErrorPopupWindow("${i18n_summary_long}");
                            return false;
                        } else{
                            // summary is ok
                            return true;
                        }
                    } else{
                        showErrorPopupWindow("${i18n_summary_should_enter}");
                        return false;
                    }
                }
                
                function isDescriptionValid(){
                    var description = document.getElementById("issue_description").value.trim();
                    var regexp = /\<|\>|\~/;
                    if(description != ""){
                        if(description.length < 15){
                            showErrorPopupWindow("${i18n_description_short}");
                            return false;
                        } else if(description.search(regexp) != -1){
                            showErrorPopupWindow("${i18n_description_invalid}");
                            return false;
                        } else if(description.length > 500){
                            showErrorPopupWindow("${i18n_description_long}");
                            return false;
                        } else{
                            // description is ok
                            return true;
                        }
            
                    } else{
                        showErrorPopupWindow("${i18n_description_should_enter}");
                        return false;
                    }
                }
                
                function isStatusValid(){
                    var status = document.getElementById("issue_status").value;
                    if(status != 0){
                        // status is ok
                        return true;
                    } else{
                        showErrorPopupWindow("${i18n_status_choose}");
                        return false;
                    }
                }
                
                function isTypeValid(){
                    var type = document.getElementById("issue_type").value;
                    if(type != 0){
                        // type is ok
                        return true;
                    } else{
                        showErrorPopupWindow("${i18n_type_choose}");
                        return false;
                    }
                }
                
                function isPriorityValid(){
                    var priority = document.getElementById("issue_priority").value;
                    if(priority != 0){
                        // priority is ok
                        return true;
                    } else{
                        showErrorPopupWindow("${i18n_priority_choose}");
                        return false;
                    }
                }
                
                function isProjectValid(){
                    var project = document.getElementById("issue_project").value;
                    if(project != 0){
                        // project is ok
                        return true;
                    } else{
                        showErrorPopupWindow("${i18n_project_choose}");
                        return false;
                    }
                }
                
                function isBuildValid(){
                    var build = document.getElementById("issue_build").value;
                    if(build != 0){
                        // build is ok
                        return true;
                    } else{
                        showErrorPopupWindow("${i18n_build_choose}");
                        return false;
                    }
                }
                
                function isAssigneeValid(){
                    var assignee = document.getElementById("issue_assignee").value;
                    if(assignee != 0){
                        // assignee is ok
                        return true;
                    } else{
                        showErrorPopupWindow("${i18n_assignee_choose}");
                        return false;
                    }
                }
            </script>
            
        </c:when>
       	<c:when test="${not empty editIssue}">
        	<!-- Edit issue -->
            
            <div class="form_container">
                <div id="content_title"><c:out value="${pageTitle}"/></div>
                <form id="issue_form" action="/issuetracker/issue/${editIssue.issueId}" method="POST">
                    <div id="issue_subtitle">Id:</div>
                    <div id="issue_id" class="text_field"><c:out value="${editIssue.issueId}"/></div>
                    <div id="issue_subtitle"><spring:message code="label.issue.issue_form.create_date"/>:</div>
                    <div id="issue_create_date" class="text_field"><c:out value="${editIssue.createDate}"/></div>
                    <div id="issue_subtitle"><spring:message code="label.issue.issue_form.created_by"/>:</div>
                    <div id="issue_created_by" class="text_field"><c:out value="${editIssue.createdBy.firstName}"/> <c:out value="${editIssue.createdBy.lastName}"/></div>
                    <div id="issue_subtitle"><spring:message code="label.issue.issue_form.modify_date"/>:</div>
                    <div id="issue_modify_date" class="text_field"><c:out value="${editIssue.modifyDate}"/></div>
                    <div id="issue_subtitle"><spring:message code="label.issue.issue_form.modified_by"/>:</div>
                    <div id="issue_modified_by" class="text_field"><c:out value="${editIssue.modifiedBy.firstName}"/> <c:out value="${editIssue.modifiedBy.lastName}"/></div>
                    <div id="issue_subtitle"><spring:message code="label.issue.issue_form.summary"/>:</div>
                    <input id="issue_summary" class="text_field" name="summary" type="text" maxlength="45" value="${editIssue.summary}" placeholder="Summary">
                    <div id="issue_subtitle"><spring:message code="label.issue.issue_form.description"/>:</div>
                    <spring:message code="label.issue.issue_form.description" var="i18n_description"/>
                    <textarea id="issue_description" name="description" rows="4" placeholder="${i18n_description}"><c:out value="${editIssue.description}"/></textarea>
                    <select id="issue_status" class="drop_list" name="statusIndex" size="1" onChange="setStatusChanged();">
                        <option selected disabled value="0"><spring:message code="label.issue.issue_form.status"/></option>
                        <c:forEach items="${statuses}" var="status">
                            <c:choose>
                                <c:when test="${status.statusId eq editIssue.status.statusId}">
                                    <option value="${status.statusId}" selected><c:out value="${status.name}"/></option>
                                </c:when>
                                <c:otherwise>
                                     <option value="${status.statusId}"><c:out value="${status.name}"/></option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <select id="issue_resolution" class="drop_list" name="resolutionId" size="1">
                        <option selected disabled value="0"><spring:message code="label.issue.issue_form.resolution"/></option>
                        <c:forEach items="${resolutions}" var="resolution">
                            <c:choose>
                                <c:when test="${resolution.resolutionId eq editIssue.resolution.resolutionId}">
                                    <option value="${resolution.resolutionId}" selected><c:out value="${resolution.name}"/></option>
                                </c:when>
                                <c:otherwise>
                                     <option value="${resolution.resolutionId}"><c:out value="${resolution.name}"/></option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <select id="issue_type" class="drop_list" name="typeId" size="1">
                        <option selected disabled value="0"><spring:message code="label.issue.issue_form.type"/></option>
                        <c:forEach items="${types}" var="type">
                            <c:choose>
                                <c:when test="${type.typeId eq editIssue.type.typeId}">
                                    <option value="${type.typeId}" selected><c:out value="${type.name}"/></option>
                                </c:when>
                                <c:otherwise>
                                     <option value="${type.typeId}"><c:out value="${type.name}"/></option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <select id="issue_priority" class="drop_list" name="priorityId" size="1">
                        <option selected disabled value="0"><spring:message code="label.issue.issue_form.priority"/></option>
                        <c:forEach items="${priorities}" var="priority">
                            <c:choose>
                                <c:when test="${priority.priorityId eq editIssue.priority.priorityId}">
                                    <option value="${priority.priorityId}" selected><c:out value="${priority.name}"/></option>
                                </c:when>
                                <c:otherwise>
                                     <option value="${priority.priorityId}"><c:out value="${priority.name}"/></option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <select id="issue_project" class="drop_list" name="projectId" size="1" onChange="setProjectChanged();">
                        <option selected disabled value="0"><spring:message code="label.issue.issue_form.project"/></option>
                        <c:forEach items="${projects}" var="project">
                            <c:choose>
                                <c:when test="${project.projectId eq editIssue.project.projectId}">
                                    <option value="${project.projectId}" selected><c:out value="${project.name}"/></option>
                                </c:when>
                                <c:otherwise>
                                     <option value="${project.projectId}"><c:out value="${project.name}"/></option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <select id="issue_build" class="drop_list" name="buildId" size="1">
                        <option selected disabled value="0"><spring:message code="label.issue.issue_form.build"/></option>
                        <option value="${editIssue.buildFound.buildId}"><c:out value="${editIssue.buildFound.version}"/></option>
                    </select>
                    <select id="issue_assignee" class="drop_list" name="assigneeId" size="1">
                        <option selected disabled value="0"><spring:message code="label.issue.issue_form.assignee"/></option>
                        <c:forEach items="${users}" var="user">
                            <c:choose>
                                <c:when test="${user.userId eq editIssue.assignee.userId}">
                                    <option value="${user.userId}" selected><c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></option>
                                </c:when>
                                <c:otherwise>
                                     <option value="${user.userId}"><c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <spring:message code="label.issue.issue_form.done" var="i18n_done"/>
                    <input id="issue_submit" type="button" onClick="trySubmit();" value="${i18n_done}">
                </form>
            </div>
            
            <c:if test="${not empty builds}">
            	<script type="text/javascript" language="javascript">
					var projects = <c:out value="${builds}" escapeXml="false"/>;
				</script>
            </c:if>
            
            <script type="text/javascript" language="javascript">
				var STATUS_INDEX_NEW = 1;
				var STATUS_INDEX_CLOSED = 5;
				
				var isAssigned = false;
				var isClosed = false;
				
				var statusSelect = document.getElementById("issue_status");
				var resolutionSelect = document.getElementById("issue_resolution");
				var assigneeSelect = document.getElementById("issue_assignee");
				var projectSelect = document.getElementById("issue_project");
				var buildSelect = document.getElementById("issue_build");
				
				var buildFirstOption = buildSelect.options[0];
				var buildDefaultOptionId = buildSelect.options[1].value;
				
				function setStatusChanged(){
					var index = statusSelect.selectedIndex;
					if(index == STATUS_INDEX_NEW){
						// Hide assigneeSelect
						assigneeSelect.style.display = "none";
						isAssigned = false;
						// Hide resolutionSelect
						resolutionSelect.style.display = "none";
						isClosed = false;
					} else if(index == STATUS_INDEX_CLOSED){
						// Show resolutionSelect
						resolutionSelect.style.display = "block";
						isClosed = true;
						// Show assigneeSelect
						assigneeSelect.style.display = "block";
						isAssigned = true;
					} else{
						// Show assigneeSelect
						assigneeSelect.style.display = "block";
						isAssigned = true;
					}
				}
				
				function setProjectChanged(){
					var projectId = projectSelect.value;
					if(projectId != 0){
						// Show builds for this project
						showBuilds(projectId);
					}
				}
				
				function showBuilds(projectId){
					// At first - clear all old build-options
					buildSelect.innerHTML = "";
					
					// Add first default option
					buildSelect.appendChild(buildFirstOption);
					// Create new build-options
					for(i=0; i < projects.length; i++){
						if(projects[i].id == projectId){
							var builds = projects[i].builds;
							for(i=0; i < builds.length; i++){
								if(builds[i].id == buildDefaultOptionId){
									addBuildOption(builds[i].name, builds[i].id, true);
								} else{
									addBuildOption(builds[i].name, builds[i].id, false);
								}
							}
							break;
						}
					}
					
					// show this builds in the select-list
					buildSelect.style.display = "block";
				}
				
				// Create new option with received values and add it to the select-list of builds
				function addBuildOption(text, value, isSelected){
					var option = document.createElement("option");
					option.appendChild(document.createTextNode(text));
					option.setAttribute("value", value);
					
					if(isSelected){
						option.selected = true;
					}
					
					buildSelect.appendChild(option);
				}
			</script>
			
			<script type="text/javascript" language="javascript">
				var issueForm = document.getElementById("issue_form");
				
				// Check the first part of fields
				function trySubmit(){
					if(isSummaryValid()){
						if(isDescriptionValid()){
							if(isStatusValid()){
								if(isClosed){
									// Check resolution-list
									if(isResolutionValid()){
										proceedSubmit();
									}
								} else{
									proceedSubmit();
								}
							}
						}
					}
				}
				
				// Check other fields and if all is fine - submit 
				function proceedSubmit(){
					if(isTypeValid()){
						if(isPriorityValid()){
							if(isProjectValid()){
								if(isBuildValid()){
									if(isAssigned){
										// Check assignee-list
										if(isAssigneeValid()){
											// all is fine - submit!
											issueForm.submit();
										}
									} else{
										// all is fine - submit!
										issueForm.submit();
									}
								}
							}
						}
					}
				}
			
				function isSummaryValid(){
					var summary = document.getElementById("issue_summary").value.trim();
					var regexp = /\<|\>|\~/;
					if(summary != ""){
						if(summary.length < 15){
							showErrorPopupWindow("${i18n_summary_short}");
							return false;
						} else if(summary.search(regexp) != -1){
							showErrorPopupWindow("${i18n_summary_invalid}");
							return false;
						} else if(summary.length > 20){
							showErrorPopupWindow("${i18n_summary_long}");
							return false;
						} else{
							// summary is ok
							return true;
						}
					} else{
						showErrorPopupWindow("${i18n_summary_should_enter}");
						return false;
					}
				}
				
				function isDescriptionValid(){
					var description = document.getElementById("issue_description").value.trim();
					var regexp = /\<|\>|\~/;
					if(description != ""){
						if(description.length < 15){
							showErrorPopupWindow("${i18n_description_short}");
							return false;
						} else if(description.search(regexp) != -1){
							showErrorPopupWindow("${i18n_description_invalid}");
							return false;
						} else if(description.length > 500){
							showErrorPopupWindow("${i18n_description_long}");
							return false;
						} else{
							// description is ok
							return true;
						}
					} else{
						showErrorPopupWindow("${i18n_description_should_enter}");
						return false;
					}
				}
				
				function isStatusValid(){
					var status = document.getElementById("issue_status").value;
					if(status != 0){
						// status is ok
						return true;
					} else{
						showErrorPopupWindow("${i18n_status_choose}");
						return false;
					}
				}
				
				function isResolutionValid(){
					var resolution = document.getElementById("issue_resolution").value;
					if(resolution != 0){
						// resolution is ok
						return true;
					} else{
						showErrorPopupWindow("${i18n_resolution_choose}");
						return false;
					}
				}
				
				function isTypeValid(){
					var type = document.getElementById("issue_type").value;
					if(type != 0){
						// type is ok
						return true;
					} else{
						showErrorPopupWindow("${i18n_type_choose}");
						return false;
					}
				}
				
				function isPriorityValid(){
					var priority = document.getElementById("issue_priority").value;
					if(priority != 0){
						// priority is ok
						return true;
					} else{
						showErrorPopupWindow("${i18n_priority_choose}");
						return false;
					}
				}
				
				function isProjectValid(){
					var project = document.getElementById("issue_project").value;
					if(project != 0){
						// project is ok
						return true;
					} else{
						showErrorPopupWindow("${i18n_project_choose}");
						return false;
					}
				}
				
				function isBuildValid(){
					var build = document.getElementById("issue_build").value;
					if(build != 0){
						// build is ok
						return true;
					} else{
						showErrorPopupWindow("${i18n_build_choose}");
						return false;
					}
				}
				
				function isAssigneeValid(){
					var assignee = document.getElementById("issue_assignee").value;
					if(assignee != 0){
						// assignee is ok
						return true;
					} else{
						showErrorPopupWindow("${i18n_assignee_choose}");
						return false;
					}
				}
				
				setProjectChanged();
				setStatusChanged();
			</script>
		
			<div class="side_container">
				<div class="tabs_container">
					<spring:message code="label.issue.tabs.show_comments" var="i18n_show_comments"/>
					<div id="tab_comments" class="tab_selected" title="${i18n_show_comments}" onClick="showComments();"><img src="/issuetracker/resources/img/icons/tab_comments.png" /></div>
					<spring:message code="label.issue.tabs.show_attachments" var="i18n_show_attachments"/>
					<div id="tab_attachments" class="tab" title="${i18n_show_attachments}" onClick="showAttachments();"><img src="/issuetracker/resources/img/icons/tab_attachments.png" /></div>
				</div>
				
				<div id="side_title"><spring:message code="label.issue.side_title.comments"/></div>
				<div id="comments_container">
					<c:if test="${not empty comments}">
						<c:forEach items="${comments}" var="comment">
							<div class="comment">
								<div class="comment_header">
									<div class="name"><c:out value="${comment.user.firstName}"/> <c:out value="${comment.user.lastName}"/></div>
									<div class="time">at <c:out value="${comment.time}"/></div><div class="date"><c:out value="${comment.date}"/></div>
								</div>
							  <div class="comment_text"><c:out value="${comment.text}"/></div>
							</div>
                        </c:forEach>
					</c:if>
					
					<div class="add_comment">
						<div class="add_comment_header">
							<div class="add_name"><c:out value="${loginUser.firstName}"/> <c:out value="${loginUser.lastName}"/></div>
						</div>
						<form action="/issuetracker/issue/${editIssue.issueId}" method="POST">
							<input type="hidden" name="userId" value="${loginUser.userId}"/>
							<spring:message code="label.issue.comments.new_comment" var="i18n_new_comment"/>
							<textarea id="add_comment_text" name="comment" placeholder="${i18n_new_comment}"></textarea>
							<spring:message code="label.issue.comments.done" var="i18n_done"/>
							<input id="add_comment_submit" type="submit" value="${i18n_done}"/>
						</form>
				  	</div>
				</div>
			   
				<div id="attachments_container">
					<c:if test="${not empty attachments}">
						<c:forEach items="${attachments}" var="attachment">
							<div class="attachment">
								<div class="attachment_header">
								  <div class="name"><c:out value="${attachment.user.firstName}"/> <c:out value="${attachment.user.lastName}"/></div>
								  <div class="time">at <c:out value="${attachment.time}"/></div><div class="date"><c:out value="${attachment.date}"/></div>
								</div>
								<a href="/issuetracker/issue/${editIssue.issueId}/download/${attachment.attachmentId}" target="_blank">
									<div class="attachment_link"><c:out value="${attachment.reference}"/></div>
								</a>
							</div>
                        </c:forEach>
					</c:if>
					
					<spring:message code="label.issue.attachments.upload" var="i18n_upload"/>
					<input id="add_attachment_submit" class="attachment_button" type="button" value="${i18n_upload}"/>
					
					<form name="upload_form" action="/issuetracker/issue/${editIssue.issueId}/upload" enctype="multipart/form-data" method="POST">
						<input type="hidden" name="userId" value="${loginUser.userId}"/>
						<input id="file_input" type="file" name="file" onChange="submitAttachment();"/>
					</form>
				</div>
			</div>
            
            <spring:message code="label.issue.side_title.comments" var="i18n_comments"/>
            <spring:message code="label.issue.side_title.attachments" var="i18n_attachments"/>
            
            <script type="text/javascript" language="javascript">
				var tabComments = document.getElementById("tab_comments");
				var tabAttachments = document.getElementById("tab_attachments");
				
				var sideTitle = document.getElementById("side_title");
				var commentsContainer = document.getElementById("comments_container");
				var attachmentsContainer = document.getElementById("attachments_container");
		
				var TAB_DEFAULT_CLASS = "tab";
				var TAB_SELECTED_CLASS = "tab_selected";
				
				var TITLE_COMMENTS = "${i18n_comments}";
				var TITLE_ATTACHMENTS = "${i18n_attachments}";
				
				var CONTAINER_MIN_HEIGHT = "0px";
				var CONTAINER_MAX_HEIGHT = "1375px";
				
				function showComments(){
					tabComments.className = TAB_SELECTED_CLASS;
					tabAttachments.className = TAB_DEFAULT_CLASS;
					attachmentsContainer.style.height = CONTAINER_MIN_HEIGHT;
					//attachmentsContainer.style.display = "none";
					sideTitle.innerHTML = TITLE_COMMENTS;
					//commentsContainer.style.display = "block";
					commentsContainer.style.height = CONTAINER_MAX_HEIGHT;
				}
				
				function showAttachments(){
					tabAttachments.className = TAB_SELECTED_CLASS;
					tabComments.className = TAB_DEFAULT_CLASS;
					commentsContainer.style.height = CONTAINER_MIN_HEIGHT;
					//commentsContainer.style.display = "none";
					sideTitle.innerHTML = TITLE_ATTACHMENTS;
					//attachmentsContainer.style.display = "block";
					attachmentsContainer.style.height = CONTAINER_MAX_HEIGHT;
				}
			</script>
			
			<script type="text/javascript" language="javascript">
				var addAttachmentButton = document.getElementById("add_attachment_submit");
				var fileInputButton = document.getElementById("file_input");
				
				var ATTACHMENT_BUTTON_DEFAULT = "attachment_button";
				var ATTACHMENT_BUTTON_HOVER = "attachment_button_hover";
				// Assign event listeners on this button
				fileInputButton.onmouseover = function(){
					addAttachmentButton.className = ATTACHMENT_BUTTON_HOVER;
				};
				fileInputButton.onmouseout = function(){
					addAttachmentButton.className = ATTACHMENT_BUTTON_DEFAULT;
				};
				
				function submitAttachment(){
					document.forms.upload_form.submit();
				}
			</script>
       </c:when>
	</c:choose>

</div>

<%@ include file="_footer.jsp" %>

</body>
</html>