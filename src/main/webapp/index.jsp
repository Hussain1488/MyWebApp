<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    request.setAttribute("title", "Home Page");
    request.setAttribute("content", "pages/default.jsp");
    request.setAttribute("isAuthPage", null);
%>

<jsp:include page="layout.jsp" />
