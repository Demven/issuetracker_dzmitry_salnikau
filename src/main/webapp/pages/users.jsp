<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><c:out value="${pageTitle}"/></title>
<link rel="stylesheet" href="./css/_header.css" type="text/css" />
<link rel="stylesheet" href="./css/users.css" type="text/css" />
<link rel="stylesheet" href="./css/_footer.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>
            
<div class="content">
	<div id="content_title"><c:out value="${pageTitle}"/></div>
  
    <div id="search_user_container">
    	<form id="search_user_form" name="search_user_form" onSubmit="findUser();return false;">
    		<input type="text" id="name_search" value="${filter}" placeholder="Name">
            <div id="name_submit" onClick="findUser();"></div>
        </form>
        <form name="hidden_search_user_form" action="main">
    		<input type="hidden" name="command" value="viewUsers"/>
            <input type="hidden" name="filter" value=""/>
        </form>
    </div>
    
    <c:choose>
        <c:when test="${empty users}">
            <div id="table_replacement_title">No matches :(</div>
        </c:when>
       	<c:otherwise>
            <div id="table_container">
                <div class="user_table">
                    <div class="header_line">
                      <div class="name">User</div>
                    </div>
                    <div id="user_lines">
                        <c:forEach items="${users}" var="user">
                            <div class="user_line">
                                <div class="name">
                                    <a href="main?command=editUser&userId=${user.userId}">
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

    <script type="text/javascript" language="javascript">
		var name_search = document.getElementById("name_search");
		var hidden_form = document.forms.hidden_search_user_form;
		
		function findUser(){
			var name = name_search.value.trim();
			if(isNameValid(name)){
				hidden_form.filter.value = name;
				hidden_form.submit();
			}
		}
		
		function isNameValid(name){
			var regexp = /\!|\@|\№|\%|\^|\&|\*|\(|\)|\-|\_|\=|\+|\?|\"|\'|\;|\<|\>|\,|\.|\`|\~/;
			if(name.search(regexp) != -1){
				showErrorPopupWindow("Name can not contain invalid characters!");
				return false;
			} else if(name.length > 40){
				showErrorPopupWindow("Name is too long! It cannot be more than 40 characters.");
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