<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding = "utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">

<div class="popup_window" id="popup_success" style="visibility:hidden;">
	<div id="popup_message"></div>
</div>
<script type="text/javascript" language="javascript">
	var success_popup_id = "popup_success";
	var error_popup_id = "popup_error";
	
	var popup_window = document.querySelector(".popup_window");
	var popup_message = document.getElementById("popup_message");

	function showSuccessPopupWindow(message){
		popup_window.id = success_popup_id;
		popup_window.style.visibility = "visible";
		popup_message.innerHTML = message;
		popup_window.style.opacity = 1.0; 
		setTimeout(hidePopupWindow, 4000); 
	}
	
	function showErrorPopupWindow(message){
		popup_window.id = error_popup_id;
		popup_window.style.visibility = "visible";
		popup_message.innerHTML = message;
		popup_window.style.opacity = 0.9;
		setTimeout(hidePopupWindow, 4000);
	}
	
	function hidePopupWindow(){
		popup_message.innerHTML = "";
		popup_window.style.opacity = 0;
		setTimeout("popup_window.style.visibility = 'hidden'", 1000);
	}
</script>

<c:if test="${not empty successMessage}">
    <script type="text/javascript" language="javascript">
		showSuccessPopupWindow("${successMessage}");
	</script>
</c:if>
<c:if test="${not empty errorMessage}">
    <script type="text/javascript" language="javascript">
		showErrorPopupWindow("${errorMessage}");
	</script>
</c:if>

<div class="footer">
	<div class="locale_container">
    <a href="?lang=en">English</a> | <a href="?lang=ru">Русский</a>
</div>

</html>

