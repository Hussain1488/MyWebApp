<%-- auth/layout.jsp--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <title><%= request.getAttribute("title") %>
    </title>
    <!-- Add common CSS or JS files here -->
    <%-- <link rel="stylesheet" href="<%= request.getContextPath() %>/styles/main.css"> --%>
</head>
<body>

<!-- Include header and sidebar for non-auth pages -->
<header>
    <%@ include file="/template/header.jsp" %>
</header>
<div class="flex h-screen">

    <main class="flex-1 p-6">
        <%
            String content = (String) request.getAttribute("content");
        %>
        <jsp:include page="<%= content %>"/>
    </main>
</div>



<footer>
    <%@ include file="/template/footer.jsp" %>
</footer>

</body>
</html>
