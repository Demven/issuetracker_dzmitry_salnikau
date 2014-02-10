<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding = "utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">

<div class="header">
	<!-- If loginUser variable in session exists - we have to show this menu-->
    <c:if test="${not empty sessionScope.loginUser}">
        <div id="menu_btn">
            <div class="menu_btn_icon"></div>
            <ul class="menu">
              <div id="user_menu">
                    <li class="menu_item">Profile</li>
                    <li class="menu_item">Submit issue</li>
                    <a href="main?command=logout"><li class="menu_item">Log out</li></a>
              </div>
              
              <c:if test="${sessionScope.loginUser.role.name eq 'Administrator'}">
              	   <div id="admin_menu">
                      <li class="menu_item" id="create">&#9654; Create
                            <ul class="create_menu">
                                 <a href="main?command=createProject"><li class="menu_item">Project</li></a>
                                 <a href="main?command=createResolution"><li class="menu_item">Resolution</li></a>
                                 <a href="main?command=createPriority"><li class="menu_item">Priority</li></a>
                                 <a href="main?command=createType"><li class="menu_item">Type</li></a>
                          </ul>
                      </li>
                        <li class="menu_item" id="users">&#9654; Users
                            <ul class="users_menu">
                                 <li class="menu_item">Find user</li>
                                 <a href="main?command=createUser"><li class="menu_item">Add user</li></a>
                            </ul>
                        </li>
                        <li class="menu_item" id="view">&#9654; View
                            <ul class="view_menu">
                                 <a href="main?command=viewProjects"><li class="menu_item">Projects</li></a>
                                 <a href="main?command=viewStatuses"><li class="menu_item">Statuses</li></a>
                                 <a href="main?command=viewResolutions"><li class="menu_item">Resolutions</li></a>
                                 <a href="main?command=viewPriorities"><li class="menu_item">Priorities</li></a>
                                 <a href="main?command=viewTypes"><li class="menu_item">Types</li></a>
                            </ul>
                        </li>
                    </div>
				</c:if>
            </ul>
        </div>
    </c:if>
 
    <div class="logo"></div>
    
    <!-- If loginUser variable exists - we have to show this greeting block-->
    <c:if test="${not empty sessionScope.loginUser}">
        <div id="user_welcome">
            <a href=""><span id="submit_issue">Submit issue</span></a> | 
            <a href=""><span id="user_login"><c:out value="${sessionScope.loginUser.firstName}"/> <c:out value="${sessionScope.loginUser.lastName}"/></span></a> | 
            <a href="main?command=logout"><span id="log_out">Log out</span></a>
        </div>
    </c:if>
    
    <div class="overall_menu">
    	<div class="search_container">
        	<input type="text" id="find_field" placeholder="Find bug"/><div class="search_icon" title="Search"></div>
        </div>
        <!-- If loginUser variable doesn't exists in session - we have to show this form, so user will log in -->
        <c:choose>
        	<c:when test="${empty sessionScope.loginUser}">
                <div class="auth_container">
                    <form name="auth_form" action="main" method="get">
                        <input type="submit" id="sign_in_btn" value="Sign In"/>
                        <div id="auth">
                            <input type="hidden" name="command" value="auth"/>
                            <input type="email" name="email" id="email" placeholder="Email" title="Email"/>
                            <input type="password" name="password" id="password" placeholder="Password" title="Password"/>
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

