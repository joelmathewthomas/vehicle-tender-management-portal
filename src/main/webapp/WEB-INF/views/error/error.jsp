<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String role = null;
boolean isLoggedIn = false;

if (session != null) {
    role = (String)session.getAttribute("userrole");
    if (role != null) {
        isLoggedIn = true;
    }
}
%>

<!DOCTYPE html>
<html>
<head>
<title>Error</title>

<script type="text/javascript">
window.onload = function() {
    let encoded = "<%=request.getParameter("e")%>";
    let decoded = atob(encoded.replace(/-/g, '+').replace(/_/g, '/'));
    alert(decoded);

    <% if (!isLoggedIn) { %>
        window.location.href = "<%=request.getContextPath()%>";
    <% } else { %>
        window.location.href = "<%=request.getContextPath()%>/<%=role%>";
    <% } %>
};
</script>
</head>
<body></body>
</html>
