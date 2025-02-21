<%-- in '/' layout.jsp--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>${title}</title>
    <!-- Add common CSS or JS files here -->
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">--%>
</head>
<body>
<c:if test="${empty isAuthPage}">
    <!-- Include header and sidebar for non-auth pages -->
    <header>
        <%@ include file="templet/header.jsp" %>
    </header>
    <aside>
        <%@ include file="templet/sidebar.jsp" %>
    </aside>
</c:if>

<main>
    <!-- Dynamic content will be inserted here -->
    <jsp:include page="${content}"/>
</main>

<c:if test="${empty isAuthPage}">
    <!-- Include footer for non-auth pages -->
    <footer>
        <%@ include file="templet/footer.jsp" %>
    </footer>
</c:if>
</body>
</html>
