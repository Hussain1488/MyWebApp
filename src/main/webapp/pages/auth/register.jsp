
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="register" scope="request" />
<c:set var="content" value="/pages/auth/content/register-content.jsp" scope="request" />
<c:set var="isAuthPage" value="true" scope="request" />

<jsp:include page="/layout.jsp" />
