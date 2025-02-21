<%-- in '/' index.jsp--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="Home Page" scope="request" />
<c:set var="content" value="/pages/default.jsp" scope="request" />
<%--<c:set var="isAuthPage" value="false" scope="request" />--%>

<jsp:include page="/layout.jsp" />
