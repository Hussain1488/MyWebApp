<%-- register.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    request.setAttribute("title", "register");
    request.setAttribute("content", "/pages/auth/content/register-content.jsp");
%>

<jsp:include page="/pages/auth/layout.jsp" />

