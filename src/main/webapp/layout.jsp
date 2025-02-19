<html>
<head>
  <title>My Application</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<%@ include file="templet/header.jsp" %>

<div class="content">
  <%
    String content = request.getParameter("content");
    if (content == null || content.isEmpty()) {
      content = "index.jsp"; // Default page if no content is provided
    }
  %>
  <jsp:include page="<%= content %>" />
</div>

<%@ include file="templet/footer.jsp" %>

</body>
</html>


