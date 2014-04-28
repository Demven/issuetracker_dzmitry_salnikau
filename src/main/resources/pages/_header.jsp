<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding = "utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">

<div class="header">
	<!-- If loginUser variable in session exists - we have to show this menu-->
    <c:if test="${not empty sessionScope.loginUser}">
        <div id="menu_btn">
            <div class="menu_btn_icon"></div>
            <ul class="menu">
              <div id="user_menu">
                    <a href="/issuetracker/profile/${sessionScope.loginUser.userId}">
                    	<li class="menu_item"><spring:message code="label.header.menu.profile"/></li>
                    </a>
                    <a href="/issuetracker/issue"><li class="menu_item"><spring:message code="label.header.menu.submit_issue"/></li></a>
                    <a href="/issuetracker/logout"><li class="menu_item"><spring:message code="label.header.menu.log_out"/></li></a>
              </div>
              
              <c:if test="${sessionScope.loginUser.role.name eq 'Administrator'}">
              	   	<div id="admin_menu">
						<div class="top_line"></div>
            			<div class="bottom_line"></div>
                      	<li class="menu_item" id="create">&#9654; <spring:message code="label.header.menu.create"/>
                            <ul class="create_menu">
                                 <a href="/issuetracker/project"><li class="menu_item"><spring:message code="label.header.menu.project"/></li></a>
                                 <a href="/issuetracker/resolution"><li class="menu_item"><spring:message code="label.header.menu.resolution"/></li></a>
                                 <a href="/issuetracker/priority"><li class="menu_item"><spring:message code="label.header.menu.priority"/></li></a>
                                 <a href="/issuetracker/type"><li class="menu_item"><spring:message code="label.header.menu.type"/></li></a>
                          </ul>
                      	</li>
                        <li class="menu_item" id="users">&#9654; <spring:message code="label.header.menu.users"/>
                            <ul class="users_menu">
                                 <a href="/issuetracker/users"><li class="menu_item"><spring:message code="label.header.menu.find_user"/></li></a>
                                 <a href="/issuetracker/user"><li class="menu_item"><spring:message code="label.header.menu.add_user"/></li></a>
                            </ul>
                        </li>
                        <li class="menu_item" id="view">&#9654; <spring:message code="label.header.menu.view"/>
                            <ul class="view_menu">
                                 <a href="/issuetracker/projects"><li class="menu_item"><spring:message code="label.header.menu.projects"/></li></a>
                                 <a href="/issuetracker/statuses"><li class="menu_item"><spring:message code="label.header.menu.statuses"/></li></a>
                                 <a href="/issuetracker/resolutions"><li class="menu_item"><spring:message code="label.header.menu.resolutions"/></li></a>
                                 <a href="/issuetracker/priorities"><li class="menu_item"><spring:message code="label.header.menu.priorities"/></li></a>
                                 <a href="/issuetracker/types"><li class="menu_item"><spring:message code="label.header.menu.types"/></li></a>
                            </ul>
                        </li>
                    </div>
				</c:if>
            </ul>
        </div>
    </c:if>
 
    <a href="/issuetracker/">
    	<spring:message code="label.header.logo.to_main_page" var="i18n_to_main_page"/> 
    	<div class="logo" title="${i18n_to_main_page}"></div>
    </a>
    
    <!-- If loginUser variable exists - we have to show this greeting block-->
    <c:if test="${not empty sessionScope.loginUser}">
        <div id="user_welcome">
            <a href="/issuetracker/issue"><span id="submit_issue"><spring:message code="label.header.user_welcome.submit_issue"/></span></a> | 
            <a href="/issuetracker/profile/${sessionScope.loginUser.userId}">
            	<span id="user_login"><c:out value="${sessionScope.loginUser.firstName}"/> <c:out value="${sessionScope.loginUser.lastName}"/></span>
            </a> | 
            <a href="/issuetracker/logout"><span id="log_out"><spring:message code="label.header.user_welcome.log_out"/></span></a>
        </div>
    </c:if>
    
    <div class="overall_menu">
    	<div class="search_container">
			<form name="search_form" action="/issuetracker/search/" method="GET" onSubmit="searchIssue();return false;">
				<spring:message code="label.header.search_form.find_bug" var="i18n_find_bug"/>
				<input type="text" id="find_field" placeholder="${i18n_find_bug}"/>
				<spring:message code="label.header.search_form.search" var="i18n_search"/> 
				<div class="search_icon" title="${i18n_search}" onClick="searchIssue();"></div>
			</form>
		</div>
		
		<spring:message code="label.header.js.popup.enter_search_request" var="i18n_enter_search_request"/>
		<spring:message code="label.header.js.popup.invalid_characters" var="i18n_invalid_characters"/>
		<spring:message code="label.header.js.popup.name_too_long" var="i18n_name_too_long"/>
		<script type="text/javascript" language="javascript">
			var find_field = document.getElementById("find_field");
			var search_url = "/issuetracker/search/";
			var page_url = "/page/1";
			
			function searchIssue(){
				var filter = find_field.value.trim();
				if(isSearchValid(filter)){
					document.location.href = search_url+ filter + page_url;
				}
			}
			
			function isSearchValid(filter){
				var regexp = /\<|\>|\~/;
				if(filter == ""){
					showErrorPopupWindow("${i18n_enter_search_request}");
					return false;
				} else if(filter.search(regexp) != -1){
					showErrorPopupWindow("${i18n_invalid_characters}");
					return false;
				} else if(filter.length > 40){
					showErrorPopupWindow("${i18n_name_too_long}");
					return false;
				} else{
					// filter is ok
					return true;
				}
			}
		</script>
		
        <!-- If loginUser variable doesn't exists in session - we have to show this form, so user will log in -->
        <c:choose>
        	<c:when test="${empty sessionScope.loginUser}">
                <div class="auth_container">
                    <form name="auth_form" action="/issuetracker/auth" method="post">
                    	<spring:message code="label.header.auth.sign_in" var="i18n_sign_in"/>
                        <input type="submit" id="sign_in_btn" value="${i18n_sign_in}"/>
                        <div id="auth">
                        	<spring:message code="label.header.auth.email" var="i18n_email"/>
                            <input type="email" name="email" id="email" placeholder="${i18n_email}" title="${i18n_email}"/>
                            <spring:message code="label.header.auth.password" var="i18n_password"/>
                            <input type="password" name="password" id="password" placeholder="${i18n_password}" title="${i18n_password}"/>
                        </div>
                    </form>
                </div>
        	</c:when>
        	<c:otherwise>
            	<script type="text/javascript" language="javascript">
                    // Margin for the search container
                    var search_container = document.getElementsByClassName("search_container")[0];
                    search_container.style.marginRight = "10px";
                </script>
            </c:otherwise>
    	</c:choose>
    </div>
</div>

</html>

