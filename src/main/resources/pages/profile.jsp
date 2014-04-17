<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                <input id="user_first_name" name="firstName" value="${editUser.firstName}" type="text" maxlength="45" placeholder="First name"/>
                <input id="user_last_name" name="lastName" value="${editUser.lastName}" type="text" maxlength="45" placeholder="Last name"/>
                <input id="user_email" name="email" value="${editUser.email}" type="email" maxlength="45" placeholder="Email address"/>
                <div id="password_container">
                    Password
                    <div id="new_password" title="Change password" onClick="newPassword();"></div>
                </div>
                <div id="new_password_container">
                    <input id="user_password" name="password" type="password" maxlength="45" placeholder="New password"/>
                    <input id="user_repeat_password" name="repeatPassword" type="password" maxlength="45" placeholder="Repeat password"/>
                    <div id="clear_password" title="Close password fields" onClick="closePassword();"></div>
                </div>
                <select id="user_role" name="roleId" size="1">
                  <option selected disabled value="0">Role</option>
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
                <input id="user_submit" type="button" onClick="trySubmit();" value="Save">
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
                <input id="user_first_name" name="firstName" value="${editProfile.firstName}" type="text" maxlength="45" placeholder="First name"/>
                <input id="user_last_name" name="lastName"  value="${editProfile.lastName}" type="text" maxlength="45" placeholder="Last name"/>
                <div id="password_container">
                    Password
                  	<div id="new_password" title="Change password" onClick="newPassword();"></div>
                </div>
                <div id="new_password_container">
                    <input id="user_password" name="password" type="password" maxlength="45" placeholder="New password"/>
                    <input id="user_repeat_password" name="repeatPassword" type="password" maxlength="45" placeholder="Repeat password"/>
                    <div id="clear_password" title="Close password fields" onClick="closePassword();"></div>
                </div>
                <input id="user_email" name="email" value="${editProfile.email}" type="email" maxlength="45" placeholder="Email address"/>
                <input id="user_submit" type="button" onClick="trySubmit();" value="Save">
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

<script type="text/javascript" language="javascript">
	function  isFirstNameValid(){
		var firstName = document.getElementById("user_first_name").value.trim();
		var regexp = /\!|\@|\№|\%|\^|\&|\*|\(|\)|\-|\_|\=|\+|\?|\"|\'|\;|\<|\>|\,|\.|\`|\~/;
		if(firstName != ""){
			if(firstName.length < 2){
				showErrorPopupWindow("First name is too short! It cannot be less than 2 characters.");
				return false;
			} else if(firstName.search(regexp) != -1){
				showErrorPopupWindow("First name contains invalid characters!");
				return false;
			} else if(firstName.length > 20){
				showErrorPopupWindow("First name is too long! It cannot be more than 20 characters.");
				return false;
			} else{
				// firstName is ok
				return true;
			}
		} else{
			showErrorPopupWindow("You should enter a correct first name!");
			return false;
		}
	}
	
	function  isLastNameValid(){
		var lastName = document.getElementById("user_last_name").value.trim();
		var regexp = /\!|\@|\№|\%|\^|\&|\*|\(|\)|\-|\_|\=|\+|\?|\"|\'|\;|\<|\>|\,|\.|\`|\~/;
		if(lastName != ""){
			if(lastName.length < 2){
				showErrorPopupWindow("Last name is too short! It cannot be less than 2 characters.");
				return false;
			} else if(lastName.search(regexp) != -1){
				showErrorPopupWindow("Last name contains invalid characters!");
				return false;
			} else if(lastName.length > 40){
				showErrorPopupWindow("Last name is too long! It cannot be more than 40 characters.");
				return false;
			} else{
				// lastName is ok
				return true;
			}
		} else{
			showErrorPopupWindow("You should enter a correct last name!");
			return false;
		}
	}
	
	function  isEmailValid(){
		var email = document.getElementById("user_email").value.trim();
		var regexp = /[a-z0-9]+[_]?[a-z0-9]*@[a-z0-9]+.[a-z0-9]+[.[a-z0-9]*]*/i;
		if(email != ""){
			if(email.split(regexp)[0] != ""){
				showErrorPopupWindow("Please enter a valid email address!");
				return false;
			} else{
				// email is ok
				return true;
			}
		} else{
			showErrorPopupWindow("You should enter a correct email address!");
			return false;
		}
	}
	
	function  isRoleValid(){
		var role = document.getElementById("user_role").value;
		if(role != 0){
			// role is ok
			return true;
		} else{
			showErrorPopupWindow("You should choose a role!");
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
				showErrorPopupWindow("Password is too short! It cannot be less than 5 characters.");
				return false;
			} else if(password.length > 40){
				showErrorPopupWindow("Password is too long! It cannot be more than 40 characters.");
				return false;
			} else if(password != repeatPassword){
				showErrorPopupWindow("Passwords do not match!");
				return false;
			} else if(password.split(regexp)[0] != ""){
				showErrorPopupWindow("Password contains invalid characters!");
				return false;
			} else if(password.search(regexp_forbidden) != -1){
				showErrorPopupWindow("Password contains invalid characters!");
				return false;
			} else {
				// password is ok
				return true;
			}
		} else{
			showErrorPopupWindow("You should enter a correct password!");
			return false;
		}
	}
</script>
</div>

<%@ include file="_footer.jsp" %>

</body>
</html>