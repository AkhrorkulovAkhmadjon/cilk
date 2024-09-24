<%@ page import="dataBase.DB" %>
<%@ page import="entity.Card" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.History" %><%--
  Created by IntelliJ IDEA.
  User: Akhmadjon
  Date: 17.08.2024
  Time: 4:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f0f0f0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
        flex-direction: column;
    }

    div.container {
        background-color: #fff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
        width: 300px;
        margin-bottom: 20px;
    }

    h4 {
        margin: 5px 0;
    }

    .success {
        color: #28a745;
        font-size: 18px;
    }

    .warning {
        color: #dc3545;
        font-size: 18px;
    }

    .icon {
        font-size: 24px;
        vertical-align: middle;
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
<head>
    <title>Card History</title>
</head>
<body>
<%
    List<History> cardHistory = DB.getCardHistoryByCardNumber((String) request.getSession().getAttribute("cardNumber"));
    if (cardHistory != null && !cardHistory.isEmpty()) {
        for (History history : cardHistory) {
%>
<div class="container">
    <h4>Title: <%= history.getTitle() %></h4>
    <h4>Date: <%= history.getData() %></h4>
    <h4>For Card: <%= history.getForCard() %></h4>
    <h4>To Card: <%= history.getToCard() %></h4>
    <h4>Sum: <%= history.getSum() %></h4>
    <h4>Sum: <%= history.getBalance() %></h4>
    <% if (history.getTitle().equalsIgnoreCase("successful")) { %>
    <h4 class="success"><span class="icon">✅</span> Successful</h4>
    <% } else { %>
    <h4 class="warning"><span class="icon">❌</span> Not Successful</h4>
    <% } %>
</div>
<%
    }
} else {
%>
<div class="container">
    <h4>No history available</h4>
</div>
<%
    }
%>

<!-- 'Cabinet' sahifasiga yo'naltiruvchi tugma -->
<form action="/cabinet/welcome.jsp" method="get">
    <button type="submit">Go to Cabinet</button>
</form>
</body>
</html>
