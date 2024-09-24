<%--
  Created by IntelliJ IDEA.
  User: Akhmadjon
  Date: 16.08.2024
  Time: 23:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Choose Command</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        div.container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
            width: 300px;
        }

        input[type="number"] {
            width: 100%;
            padding: 10px;
            border-radius: 4px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            margin-bottom: 10px;
        }

        button {
            width: 100%;
            padding: 10px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<% var cardNumber = request.getParameter("cardNumber");%>
<body>
<div>
    <form action="/cabinet/input_card_number.jsp" method="post">
        <%request.getSession().setAttribute("cardNumber", cardNumber);%>
        <button type="submit">Search card</button>
    </form>
</div>

<div>
    <form action="/cabinet/company.jsp" method="post">
        <%request.getSession().setAttribute("cardNumber", cardNumber);%>
        <button type="submit">Search company</button>
    </form>
</div>

<div>
    <form action="/cabinet/cardHistoriy.jsp" method="post">
        <%request.getSession().setAttribute("cardNumber", cardNumber);%>
        <button type="submit">Select history</button>
    </form>
</div>
</body>
</html>
