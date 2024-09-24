<%@ page import="dataBase.DB" %>
<%@ page import="java.util.Optional" %>
<%@ page import="entity.Users" %>
<%@ page import="entity.Card" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.UUID" %> <!-- UUID klassini import qilish -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>User's Cards</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
            color: #333;
        }

        h1 {
            text-align: center;
            color: #4CAF50;
            margin-bottom: 20px;
        }

        table {
            width: 80%;
            margin: 0 auto;
            border-collapse: collapse;
            background-color: #fff;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        p {
            text-align: center;
            font-size: 18px;
        }
    </style>
</head>
<body>
<h1>User's Cards</h1>

<%
    String userIdParam = request.getParameter("userId");
    if (userIdParam != null) {
        UUID userId = UUID.fromString(userIdParam);
        Optional<Users> users = DB.getUserById(userId.toString());
        List<Card> cards = List.of();
        if (users.isPresent()) {
            cards = users.get().getCards();
        }
%>

<%
    if (!cards.isEmpty()) {
%>
<table>
    <tr>
        <th>Card Name</th>
        <th>Card Number</th>
        <th>Balance</th>
        <th>Card Data</th>
        <th>Card Status</th>
        <th>Actions</th>
    </tr>
    <% for (Card card : cards) { %>
    <tr>
        <td><%= card.getCardName() %></td>
        <td><%= card.getCardNumber() %></td>
        <td><%= card.getBalance() %></td>
        <td><%= card.getCardData() %></td>
        <td><%= card.getCardStatus() %></td>
        <td>
            <form action="/cabinet/admin/editCard.jsp" method="get">
                <input type="hidden" name="cardId" value="<%= card.getId() %>">
                <button type="submit">Edit</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>

<% } else { %>
<p>No cards found for this user.</p>
<% } %>

<% } else { %>
<p>Invalid user ID.</p>
<% } %>

</body>
</html>
