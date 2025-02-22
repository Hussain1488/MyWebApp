<%-- auth/login.jsp--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    request.setAttribute("title", "Login");
    request.setAttribute("content", "/pages/auth/content/login-content.jsp");
%>

<jsp:include page="/pages/auth/layout.jsp" />

<%--default.jsp--%>
<%--<div class="p-6 bg-gray-100 rounded-lg shadow-md">--%>
<%--    <h1 class="text-xl font-bold">Home Page</h1>--%>
<%--    <p>Welcome to the home page!</p>--%>
<%--</div>--%>