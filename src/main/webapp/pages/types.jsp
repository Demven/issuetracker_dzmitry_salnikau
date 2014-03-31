<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><c:out value="${attr.pageTitle}"/></title>
<link rel="stylesheet" href="./css/_header.css" type="text/css" />
<link rel="stylesheet" href="./css/types.css" type="text/css" />
<link rel="stylesheet" href="./css/_footer.css" type="text/css" />
</head>

<body>
<%@ include file="_header.jsp" %>
            
<div class="content">
	<div id="content_title"><c:out value="${attr.pageTitle}"/></div>
    <div id="table_container">
    	<div class="type_table">
            <div class="header_line">
              <div class="name">Name</div>
            </div>
            <div id="type_lines">
            	<c:forEach items="${attr.types}" var="type">
                    <div class="type_line">
                    	<div class="name">
                            <a href="main?command=editType&typeId=${type.typeId}">
                                <c:out value="${type.name}"/>
                            </a>
                        </div>
                    </div>
                </c:forEach>
          	</div>
        </div>
    </div>
</div>

<div class="footer"></div>

</body>
</html>