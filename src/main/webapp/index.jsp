<%
    request.setAttribute("title", "Home Page");
    request.setAttribute("content", "pages/default.jsp");
    RequestDispatcher dispatcher = request.getRequestDispatcher("layout.jsp");
    dispatcher.forward(request, response);
%>