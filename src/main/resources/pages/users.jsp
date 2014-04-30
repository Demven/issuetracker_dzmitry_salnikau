<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><c:out value="${pageTitle}"/></title>
<link rel="stylesheet" href="/issuetracker/resources/css/_header.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/users.css" type="text/css" />
<link rel="stylesheet" href="/issuetracker/resources/css/_footer.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>
            
<div class="content">
	<div id="content_title"><c:out value="${pageTitle}"/></div>
  
    <div id="search_user_container">
    	<form id="search_user_form" name="search_user_form" onSubmit="findUser();return false;">
        	<spring:message code="label.users.search_user_form.name" var="i18n_name"/>
    		<input type="text" id="name_search" value="${filter}" placeholder="${i18n_name}">
            <div id="name_submit" onClick="findUser();"></div>
        </form>
        <form name="hidden_search_user_form" action="/issuetracker/users" method="POST"></form>
    </div>
    
    <c:choose>
        <c:when test="${empty users}">
            <div id="table_replacement_title"><spring:message code="label.users.no_matches"/> :(</div>
        </c:when>
       	<c:otherwise>
            <div id="table_container">
                <div class="user_table">
                    <div class="header_line">
                      <div class="name"><spring:message code="label.users.user_table.user"/></div>
                    </div>
                    <div id="user_lines">
                        <c:forEach items="${users}" var="user">
                            <div class="user_line">
                                <div class="name">
                                    <a href="/issuetracker/user/${user.userId}">
                                        <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/>
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
    	</c:otherwise>
	</c:choose>

	<spring:message code="label.users.js.popup.name_invalid" var="i18n_name_invalid"/>
	<spring:message code="label.users.js.popup.name_long" var="i18n_name_long"/>
    
    <script type="text/javascript" language="javascript">
		var name_search = document.getElementById("name_search");
		var hidden_form = document.forms.hidden_search_user_form;
		
		function findUser(){
			var name = name_search.value.trim();
			if(isNameValid(name)){
				if(name != ""){
					hidden_form.action = hidden_form.action + "/filter/" + name;
				}
				hidden_form.submit();
			}
		}
		
		function isNameValid(name){
			var regexp = /\!|\@|\№|\%|\^|\&|\*|\(|\)|\-|\_|\=|\+|\?|\"|\'|\;|\<|\>|\,|\.|\`|\~/;
			if(name.search(regexp) != -1){
				showErrorPopupWindow("${i18n_name_invalid}");
				return false;
			} else if(name.length > 40){
				showErrorPopupWindow("${i18n_name_long}");
				return false;
			} else{
				// name is ok
				return true;
			}
		}
	</script>
</div>

<%@ include file="_footer.jsp" %>

</body>
</html>