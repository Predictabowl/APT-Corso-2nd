<%@page import="org.apache.commons.lang3.SystemUtils"%>
<%@page import="java.util.Date"%>
<html>
<body>
<h2>Hello  <%= SystemUtils.USER_NAME %>!</h2>
<%= new Date() %>
</body>
</html>
