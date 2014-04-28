<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><c:out value="${pageTitle}"/></title>
<link rel="stylesheet" href="/issuetracker/resources/css/_header.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/profile.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/_footer.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>

<div class="content">
	<div id="content_title"><c:out value="${pageTitle}"/></div>
    
    <c:choose>
        <c:when test="${not empty editUser}">
        	<!-- Show user data to administrator -->
            <form id="user_form" name="user_form" action="/issuetracker/profile/${editUser.userId}" method="POST">
            	<spring:message code="label.profile.user_form.first_name" var="i18n_first_name"/>
                <input id="user_first_name" name="firstName" value="${editUser.firstName}" type="text" maxlength="45" placeholder="${i18n_first_name}"/>
                <spring:message code="label.profile.user_form.last_name" var="i18n_last_name"/>
                <input id="user_last_name" name="lastName" value="${editUser.lastName}" type="text" maxlength="45" placeholder="${i18n_last_name}"/>
                <spring:message code="label.profile.user_form.email_address" var="i18n_email_address"/>
                <input id="user_email" name="email" value="${editUser.email}" type="email" maxlength="45" placeholder="${i18n_email_address}"/>
                <div id="password_container">
                    <spring:message code="label.profile.user_form.password"/>
                    <spring:message code="label.profile.user_form.change_password" var="i18n_change_password"/>
                    <div id="new_password" title="${i18n_change_password}" onClick="newPassword();"></div>
                </div>
                <div id="new_password_container">
                	<spring:message code="label.profile.user_form.new_password" var="i18n_new_password"/>
                    <input id="user_password" name="password" type="password" maxlength="45" placeholder="${i18n_new_password}"/>
                    <spring:message code="label.profile.user_form.repeat_password" var="i18n_repeat_password"/>
                    <input id="user_repeat_password" name="repeatPassword" type="password" maxlength="45" placeholder="${i18n_repeat_password}"/>
                    <spring:message code="label.profile.user_form.close_password" var="i18n_close_password"/>
                    <div id="clear_password" title="${i18n_close_password}" onClick="closePassword();"></div>
                </div>
                <select id="user_role" name="roleId" size="1">
                  <option selected disabled value="0"><spring:message code="label.profile.user_form.role"/></option>
                    <c:forEach items="${roles}" var="role">
                        <c:choose>
                            <c:when test="${role.roleId eq editUser.role.roleId}">
                                <option value="${role.roleId}" selected><c:out value="${role.name}"/></option>
                            </c:when>
                            <c:otherwise>
                                 <option value="${role.roleId}"><c:out value="${role.name}"/></option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
              </select>
              	<spring:message code="label.profile.user_form.save" var="i18n_save"/>
                <input id="user_submit" type="button" onClick="trySubmit();" value="${i18n_save}">
            </form>
            <script type="text/javascript" language="javascript">
				// Check all fields and if all is fine - submit 
				function trySubmit(){
					if(isFirstNameValid()){
						if(isLastNameValid()){
							if(isEmailValid()){
								if(isRoleValid()){
									if(isPasswordChanged){
										// if a user tried to change password - check it too
										if(isPasswordValid()){
											// all is fine - submit!
											document.forms.user_form.submit();
										}
									} else{
										// all is fine - submit!
										document.forms.user_form.submit();
									}
								}
							}
						}
					}
				}
			</script>
        </c:when>
        <c:when test="${not empty editProfile}">
        	<!-- Show profile to a user -->
        	<form id="user_form" name="user_form" action="/issuetracker/profile/${editProfile.userId}" method="POST">
        		<spring:message code="label.profile.user_form.first_name" var="i18n_first_name"/>
                <input id="user_first_name" name="firstName" value="${editProfile.firstName}" type="text" maxlength="45" placeholder="${i18n_first_name}"/>
                <spring:message code="label.profile.user_form.last_name" var="i18n_last_name"/>
                <input id="user_last_name" name="lastName"  value="${editProfile.lastName}" type="text" maxlength="45" placeholder="${i18n_last_name}"/>
                <div id="password_container">
                    <spring:message code="label.profile.user_form.password"/>
                    <spring:message code="label.profile.user_form.change_password" var="i18n_change_password"/>
                  	<div id="new_password" title="${i18n_change_password}" onClick="newPassword();"></div>
                </div>
                <div id="new_password_container">
                	<spring:message code="label.profile.user_form.new_password" var="i18n_new_password"/>
                    <input id="user_password" name="password" type="password" maxlength="45" placeholder="${i18n_new_password}"/>
                    <spring:message code="label.profile.user_form.repeat_password" var="i18n_repeat_password"/>
                    <input id="user_repeat_password" name="repeatPassword" type="password" maxlength="45" placeholder="${i18n_repeat_password}"/>
                    <spring:message code="label.profile.user_form.close_password" var="i18n_close_password"/>
                    <div id="clear_password" title="${i18n_close_password}" onClick="closePassword();"></div>
                </div>
                <spring:message code="label.profile.user_form.email_address" var="i18n_email_address"/>
                <input id="user_email" name="email" value="${editProfile.email}" type="email" maxlength="45" placeholder="${i18n_email_address}"/>
                <spring:message code="label.profile.user_form.save" var="i18n_save"/>
                <input id="user_submit" type="button" onClick="trySubmit();" value="${i18n_save}">
            </form>
            <script type="text/javascript" language="javascript">
				// Check all fields and if all is fine - submit 
				function trySubmit(){
					if(isFirstNameValid()){
						if(isLastNameValid()){
							if(isEmailValid()){
								if(isPasswordChanged){
									// if a user tried to change password - check it too
									if(isPasswordValid()){
										// all is fine - submit!
										document.forms.user_form.submit();
									}
								} else{
									// all is fine - submit!
									document.forms.user_form.submit();
								}
							}
						}
					}
				}
			</script>
        </c:when>
	</c:choose>

<script type="text/javascript" language="javascript">
	var isPasswordChanged = false;
	
	var passwordContainer = document.getElementById("password_container");
	var newPasswordContainer = document.getElementById("new_password_container");

	// Shows fields to change password
	function newPassword(){
		passwordContainer.style.display = "none";
		newPasswordContainer.style.display = "block";
		// set flag
		isPasswordChanged = true;
	}
	
	// Hides fields to change password
	function closePassword(){
		newPasswordContainer.style.display = "none";
		passwordContainer.style.display = "block";
		// set flag
		isPasswordChanged = false;
	}
</script>

<spring:message code="label.profile.js.popup.first_name_short" var="i18n_first_name_short"/>
<spring:message code="label.profile.js.popup.first_name_invalid" var="i18n_first_name_invalid"/>
<spring:message code="label.profile.js.popup.first_name_long" var="i18n_first_name_long"/>
<spring:message code="label.profile.js.popup.first_name_should_correct" var="i18n_first_name_should_correct"/>

<spring:message code="label.profile.js.popup.last_name_short" var="i18n_last_name_short"/>
<spring:message code="label.profile.js.popup.last_name_invalid" var="i18n_last_name_invalid"/>
<spring:message code="label.profile.js.popup.last_name_long" var="i18n_last_name_long"/>
<spring:message code="label.profile.js.popup.first_name_should_correct" var="i18n_last_name_should_correct"/>

<spring:message code="label.profile.js.popup.email_enter_valid" var="i18n_email_enter_valid"/>
<spring:message code="label.profile.js.popup.email_should_correct" var="i18n_email_should_correct"/>

<spring:message code="label.profile.js.popup.role_should_choose" var="i18n_role_should_choose"/>

<spring:message code="label.profile.js.popup.password_short" var="i18n_password_short"/>
<spring:message code="label.profile.js.popup.password_long" var="i18n_password_long"/>
<spring:message code="label.profile.js.popup.password_do_not_match" var="i18n_password_do_not_match"/>
<spring:message code="label.profile.js.popup.password_invalid" var="i18n_password_invalid"/>
<spring:message code="label.profile.js.popup.password_should_correct" var="i18n_password_should_correct"/>

<script type="text/javascript" language="javascript">
	function  isFirstNameValid(){
		var firstName = document.getElementById("user_first_name").value.trim();
		var regexp = /\!|\@|\№|\%|\^|\&|\*|\(|\)|\-|\_|\=|\+|\?|\"|\'|\;|\<|\>|\,|\.|\`|\~/;
		if(firstName != ""){
			if(firstName.length < 2){
				showErrorPopupWindow("${i18n_first_name_short}");
				return false;
			} else if(firstName.search(regexp) != -1){
				showErrorPopupWindow("${i18n_first_name_invalid}");
				return false;
			} else if(firstName.length > 20){
				showErrorPopupWindow("${i18n_first_name_long}");
				return false;
			} else{
				// firstName is ok
				return true;
			}
		} else{
			showErrorPopupWindow("${i18n_first_name_should_correct}");
			return false;
		}
	}
	
	function  isLastNameValid(){
		var lastName = document.getElementById("user_last_name").value.trim();
		var regexp = /\!|\@|\№|\%|\^|\&|\*|\(|\)|\-|\_|\=|\+|\?|\"|\'|\;|\<|\>|\,|\.|\`|\~/;
		if(lastName != ""){
			if(lastName.length < 2){
				showErrorPopupWindow("${i18n_last_name_short}");
				return false;
			} else if(lastName.search(regexp) != -1){
				showErrorPopupWindow("${i18n_last_name_invalid}");
				return false;
			} else if(lastName.length > 40){
				showErrorPopupWindow("${i18n_last_name_long}");
				return false;
			} else{
				// lastName is ok
				return true;
			}
		} else{
			showErrorPopupWindow("${i18n_last_name_should_correct}");
			return false;
		}
	}
	
	function  isEmailValid(){
		var email = document.getElementById("user_email").value.trim();
		var regexp = /[a-z0-9]+[_]?[a-z0-9]*@[a-z0-9]+.[a-z0-9]+[.[a-z0-9]*]*/i;
		if(email != ""){
			if(email.split(regexp)[0] != ""){
				showErrorPopupWindow("${i18n_email_enter_valid}");
				return false;
			} else{
				// email is ok
				return true;
			}
		} else{
			showErrorPopupWindow("${i18n_email_should_correct}");
			return false;
		}
	}
	
	function  isRoleValid(){
		var role = document.getElementById("user_role").value;
		if(role != 0){
			// role is ok
			return true;
		} else{
			showErrorPopupWindow("${i18n_role_should_choose}");
			return false;
		}
	}

	function  isPasswordValid(){
		var password = document.getElementById("user_password").value.trim();
		var repeatPassword = document.getElementById("user_repeat_password").value.trim();
		var regexp = /[a-z0-9]{5,30}/i;
		var regexp_forbidden = /\#|\№|\^|\&|\*|\(|\)|\=|\+|\"|\'|\<|\>|\`|\~/;
		if(password != ""){
			if(password.length < 5){
				showErrorPopupWindow("${i18n_password_short}");
				return false;
			} else if(password.length > 40){
				showErrorPopupWindow("${i18n_password_long}");
				return false;
			} else if(password != repeatPassword){
				showErrorPopupWindow("${i18n_password_do_not_match}");
				return false;
			} else if(password.split(regexp)[0] != ""){
				showErrorPopupWindow("${i18n_password_invalid}");
				return false;
			} else if(password.search(regexp_forbidden) != -1){
				showErrorPopupWindow("${i18n_password_invalid}");
				return false;
			} else {
				// password is ok
				return true;
			}
		} else{
			showErrorPopupWindow("${i18n_password_should_correct}");
			return false;
		}
	}
</script>
</div>

<%@ include file="_footer.jsp" %>

</body>
</html>