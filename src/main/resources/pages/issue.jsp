<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><c:out value="${pageTitle}"/></title>
<link rel="stylesheet" href="./css/_header.css" type="text/css" />
<c:choose>
    <c:when test="${empty editIssue}">
        <!-- Create issue --> 
        <link rel="stylesheet" href="./css/issue_create.css" type="text/css" />
    </c:when>
    <c:when test="${not empty editIssue}">
        <!-- Edit issue -->
        <link rel="stylesheet" href="./css/issue_edit.css" type="text/css" />
    </c:when>
</c:choose>
<link rel="stylesheet" href="./css/_footer.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>

<div class="content">

    <c:choose>
        <c:when test="${empty editIssue}">
            <!-- Create issue --> 
            <div id="content_title"><c:out value="${pageTitle}"/></div>
            
            <form id="issue_form" action="main">
                <input type="hidden" name="command" value="createIssue"/>
                <input id="issue_summary" name="summary" type="text" maxlength="45" placeholder="Summary">
                <textarea id="issue_description" name="description" rows="4" placeholder="Description"></textarea>
                <select id="issue_status" name="statusIndex" size="1" onChange="setStatusChanged();">
                    <option selected disabled value="0">Status</option>
                    <option value="1">New</option>
                    <option value="2">Assigned</option>
                </select>
                <select id="issue_type" name="typeId" size="1">
                    <option selected disabled value="0">Type</option>
                    <c:forEach items="${types}" var="type">
                        <option value="${type.typeId}"><c:out value="${type.name}"/></option>
                    </c:forEach>
                </select>
                <select id="issue_priority" name="priorityId" size="1">
                    <option selected disabled value="0">Priority</option>
                    <c:forEach items="${priorities}" var="priority">
                        <option value="${priority.priorityId}"><c:out value="${priority.name}"/></option>
                    </c:forEach>
                </select>
                <select id="issue_project" name="projectId" size="1" onChange="setProjectChanged();">
                    <option selected disabled value="0">Project</option>
                    <c:forEach items="${projects}" var="project">
                        <option value="${project.projectId}"><c:out value="${project.name}"/></option>
                    </c:forEach>
                </select>
                <select id="issue_build" name="buildId" size="1">
                    <option selected disabled value="0">Build</option>
                </select>
                <select id="issue_assignee" name="assigneeId" size="1">
                    <option selected disabled value="0">Assignee</option>
                    <c:forEach items="${users}" var="assignee">
                        <option value="${assignee.userId}"><c:out value="${assignee.firstName}"/> <c:out value="${assignee.lastName}"/></option>
                    </c:forEach>
                </select>
                <input id="issue_submit" type="button" onClick="trySubmit();" value="Done">
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
                            showErrorPopupWindow("Summary is too short! It cannot be less than 15 characters.");
                            return false;
                        } else if(summary.search(regexp) != -1){
                            showErrorPopupWindow("Summary contains invalid characters!");
                            return false;
                        } else if(summary.length > 20){
                            showErrorPopupWindow("Summary is too long! It cannot be more than 45 characters.");
                            return false;
                        } else{
                            // summary is ok
                            return true;
                        }
                    } else{
                        showErrorPopupWindow("You should enter a summary for a new issue!");
                        return false;
                    }
                }
                
                function isDescriptionValid(){
                    var description = document.getElementById("issue_description").value.trim();
                    var regexp = /\<|\>|\~/;
                    if(description != ""){
                        if(description.length < 15){
                            showErrorPopupWindow("Description is too short! It cannot be less than 15 characters.");
                            return false;
                        } else if(description.search(regexp) != -1){
                            showErrorPopupWindow("Description contains invalid characters!");
                            return false;
                        } else if(description.length > 500){
                            showErrorPopupWindow("Description is too long! It cannot be more than 500 characters.");
                            return false;
                        } else{
                            // description is ok
                            return true;
                        }
            
                    } else{
                        showErrorPopupWindow("You should enter a description of a new issue!");
                        return false;
                    }
                }
                
                function isStatusValid(){
                    var status = document.getElementById("issue_status").value;
                    if(status != 0){
                        // status is ok
                        return true;
                    } else{
                        showErrorPopupWindow("You should choose a status of a new issue!");
                        return false;
                    }
                }
                
                function isTypeValid(){
                    var type = document.getElementById("issue_type").value;
                    if(type != 0){
                        // type is ok
                        return true;
                    } else{
                        showErrorPopupWindow("You should choose a type of a new issue!");
                        return false;
                    }
                }
                
                function isPriorityValid(){
                    var priority = document.getElementById("issue_priority").value;
                    if(priority != 0){
                        // priority is ok
                        return true;
                    } else{
                        showErrorPopupWindow("You should choose a priority for a new issue!");
                        return false;
                    }
                }
                
                function isProjectValid(){
                    var project = document.getElementById("issue_project").value;
                    if(project != 0){
                        // project is ok
                        return true;
                    } else{
                        showErrorPopupWindow("You should choose a project for a new issue!");
                        return false;
                    }
                }
                
                function isBuildValid(){
                    var build = document.getElementById("issue_build").value;
                    if(build != 0){
                        // build is ok
                        return true;
                    } else{
                        showErrorPopupWindow("You should choose a build for a new issue!");
                        return false;
                    }
                }
                
                function isAssigneeValid(){
                    var assignee = document.getElementById("issue_assignee").value;
                    if(assignee != 0){
                        // assignee is ok
                        return true;
                    } else{
                        showErrorPopupWindow("You should choose an assignee for a new issue!");
                        return false;
                    }
                }
            </script>
            
        </c:when>
       	<c:when test="${not empty editIssue}">
        	<!-- Edit issue -->
            
            <div class="form_container">
                <div id="content_title"><c:out value="${pageTitle}"/></div>
                <form id="issue_form" action="main">
                    <input type="hidden" name="command" value="editIssue"/>
                    <input type="hidden" name="issueId" value="${editIssue.issueId}"/>
                    <div id="issue_subtitle">Id:</div>
                    <div id="issue_id" class="text_field"><c:out value="${editIssue.issueId}"/></div>
                    <div id="issue_subtitle">Create date:</div>
                    <div id="issue_create_date" class="text_field"><c:out value="${editIssue.createDate}"/></div>
                    <div id="issue_subtitle">Created by:</div>
                    <div id="issue_created_by" class="text_field"><c:out value="${editIssue.createdBy.firstName}"/> <c:out value="${editIssue.createdBy.lastName}"/></div>
                    <div id="issue_subtitle">Modify date:</div>
                    <div id="issue_modify_date" class="text_field"><c:out value="${editIssue.modifyDate}"/></div>
                    <div id="issue_subtitle">Modified by:</div>
                    <div id="issue_modified_by" class="text_field"><c:out value="${editIssue.modifiedBy.firstName}"/> <c:out value="${editIssue.modifiedBy.lastName}"/></div>
                    <div id="issue_subtitle">Summary:</div>
                    <input id="issue_summary" class="text_field" name="summary" type="text" maxlength="45" value="${editIssue.summary}" placeholder="Summary">
                    <div id="issue_subtitle">Description:</div>
                    <textarea id="issue_description" name="description" rows="4" placeholder="Description"><c:out value="${editIssue.description}"/></textarea>
                    <select id="issue_status" class="drop_list" name="status" size="1" onChange="setStatusChanged();">
                        <option selected disabled value="0">Status</option>
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
                    <select id="issue_resolution" class="drop_list" name="resolution" size="1">
                        <option selected disabled value="0">Resolution</option>
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
                    <select id="issue_type" class="drop_list" name="type" size="1">
                        <option selected disabled value="0">Type</option>
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
                    <select id="issue_priority" class="drop_list" name="priority" size="1">
                        <option selected disabled value="0">Priority</option>
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
                    <select id="issue_project" class="drop_list" name="project" size="1" onChange="setProjectChanged();">
                        <option selected disabled value="0">Project</option>
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
                    <select id="issue_build" class="drop_list" name="build" size="1">
                        <option selected disabled value="0">Build</option>
                        <option value="${editIssue.buildFound.buildId}"><c:out value="${editIssue.buildFound.version}"/></option>
                    </select>
                    <select id="issue_assignee" class="drop_list" name="assignee" size="1">
                        <option selected disabled value="0">Assignee</option>
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
                    <input id="issue_submit" type="button" onClick="trySubmit();" value="Done">
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
							showErrorPopupWindow("Summary is too short! It cannot be less than 15 characters.");
							return false;
						} else if(summary.search(regexp) != -1){
							showErrorPopupWindow("Summary contains invalid characters!");
							return false;
						} else if(summary.length > 20){
							showErrorPopupWindow("Summary is too long! It cannot be more than 45 characters.");
							return false;
						} else{
							// summary is ok
							return true;
						}
					} else{
						showErrorPopupWindow("You should enter a summary for a new issue!");
						return false;
					}
				}
				
				function isDescriptionValid(){
					var description = document.getElementById("issue_description").value.trim();
					var regexp = /\<|\>|\~/;
					if(description != ""){
						if(description.length < 15){
							showErrorPopupWindow("Description is too short! It cannot be less than 15 characters.");
							return false;
						} else if(description.search(regexp) != -1){
							showErrorPopupWindow("Description contains invalid characters!");
							return false;
						} else if(description.length > 500){
							showErrorPopupWindow("Description is too long! It cannot be more than 500 characters.");
							return false;
						} else{
							// description is ok
							return true;
						}
					} else{
						showErrorPopupWindow("You should enter a description of a new issue!");
						return false;
					}
				}
				
				function isStatusValid(){
					var status = document.getElementById("issue_status").value;
					if(status != 0){
						// status is ok
						return true;
					} else{
						showErrorPopupWindow("You should choose a status of a new issue!");
						return false;
					}
				}
				
				function isResolutionValid(){
					var resolution = document.getElementById("issue_resolution").value;
					if(resolution != 0){
						// resolution is ok
						return true;
					} else{
						showErrorPopupWindow("You should choose a resolution for the closed issue!");
						return false;
					}
				}
				
				function isTypeValid(){
					var type = document.getElementById("issue_type").value;
					if(type != 0){
						// type is ok
						return true;
					} else{
						showErrorPopupWindow("You should choose a type of a new issue!");
						return false;
					}
				}
				
				function isPriorityValid(){
					var priority = document.getElementById("issue_priority").value;
					if(priority != 0){
						// priority is ok
						return true;
					} else{
						showErrorPopupWindow("You should choose a priority for a new issue!");
						return false;
					}
				}
				
				function isProjectValid(){
					var project = document.getElementById("issue_project").value;
					if(project != 0){
						// project is ok
						return true;
					} else{
						showErrorPopupWindow("You should choose a project for a new issue!");
						return false;
					}
				}
				
				function isBuildValid(){
					var build = document.getElementById("issue_build").value;
					if(build != 0){
						// build is ok
						return true;
					} else{
						showErrorPopupWindow("You should choose a build for a new issue!");
						return false;
					}
				}
				
				function isAssigneeValid(){
					var assignee = document.getElementById("issue_assignee").value;
					if(assignee != 0){
						// assignee is ok
						return true;
					} else{
						showErrorPopupWindow("You should choose an assignee for a new issue!");
						return false;
					}
				}
				
				setProjectChanged();
				setStatusChanged();
			</script>
		
			<div class="side_container">
				<div class="tabs_container">
					<div id="tab_comments" class="tab_selected" title="Show comments" onClick="showComments();"><img src="./img/icons/tab_comments.png" /></div>
					<div id="tab_attachments" class="tab" title="Show attachments" onClick="showAttachments();"><img src="./img/icons/tab_attachments.png" /></div>
				</div>
				
				<div id="side_title">Comments</div>
				<div id="comments_container">
					<div class="comment">
						<div class="comment_header">
							<div class="name">Dzmitry Salnikau</div>
							<div class="time">at 12:34</div><div class="date">15.06.2013</div>
						</div>
					  <div class="comment_text">I think it's much better than it were before. Thank you for your time!
						 I think it's much better than it were before.</div>
					</div>
					<div class="comment">
						<div class="comment_header">
							<div class="name">Sergey German</div>
							<div class="time">at 12:34</div><div class="date">15.06.2013</div>
						</div>
					  <div class="comment_text">I think it's much better than it were before. Thank you for your time!</div>
					</div>
					<div class="add_comment">
						<div class="add_comment_header">
							<div class="add_name">Sergey German</div>
						</div>
						<form action="main">
							<input type="hidden" name="command" value="newComment"/>
							<input type="hidden" name="issueId" value="1"/>
							<textarea id="add_comment_text" name="comment" placeholder="New comment"></textarea>
							<input id="add_comment_submit" type="submit" value="Done"/>
						</form>
				  	</div>
				</div>
			   
				<div id="attachments_container">
					<div class="attachment">
						<div class="attachment_header">
						  <div class="name">Dzmitry Salnikau</div>
						  <div class="time">at 12:34</div><div class="date">15.06.2013</div>
						</div>
						<div class="attachment_link">Bug_screenshot.jpeg</div>
					</div>
					<div class="attachment">
						<div class="attachment_header">
						  <div class="name">Sergey German</div>
						  <div class="time">at 12:34</div><div class="date">15.06.2013</div>
						</div>
					  <div class="attachment_link">list of proposals.docx</div>
					</div>
					
					<input id="add_attachment_submit" class="attachment_button" type="button" value="Uploud"/>
					
					<form name="upload_form" action="main" enctype="multipart/form-data" method="post">
						<input type="hidden" name="command" value="newAttachment"/>
						<input type="hidden" name="issueId" value="1"/>
						<input id="file_input" type="file" name="file"/>
					</form>
				</div>
			</div>
            
            <script type="text/javascript" language="javascript">
				var tabComments = document.getElementById("tab_comments");
				var tabAttachments = document.getElementById("tab_attachments");
				
				var sideTitle = document.getElementById("side_title");
				var commentsContainer = document.getElementById("comments_container");
				var attachmentsContainer = document.getElementById("attachments_container");
		
				var TAB_DEFAULT_CLASS = "tab";
				var TAB_SELECTED_CLASS = "tab_selected";
				
				var TITLE_COMMENTS = "Comments";
				var TITLE_ATTACHMENTS = "Attachments";
				
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
			</script>
       </c:when>
	</c:choose>

</div>

<%@ include file="_footer.jsp" %>

</body>
</html>