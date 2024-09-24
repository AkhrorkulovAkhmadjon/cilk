<%@ page import="dataBase.DB" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Card" %>
<%@ page import="java.util.Optional" %>
<%@ page import="entity.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            margin-bottom: 5px;
        }

        .btn-working {
            background-color: #28a745; /* Green */
        }

        .btn-information {
            background-color: #dc3545; /* Red */
        }

        button:hover {
            opacity: 0.8;
        }

        .back-btn {
            background-color: #6c757d; /* Grey */
        }
    </style>
</head>
<body>
<form action="/cabinet/admin/edit_card_status" method="post">
    <% Optional<List<Card>> resCard = DB.getCardByStatus(); %>
    <% if (resCard.isPresent()) { %>
    <% for (Card card : resCard.get()) { %>
    <% Users resUser = DB.getUserForCard(card.getId()); %>
    <div class="container">
        <p><strong>Name:</strong> <%= resUser.getName() %></p>
        <p><strong>Passport Number:</strong> <%= resUser.getPassportNumber() %></p>
        <p><strong>Address:</strong> <%= resUser.getAddress() %></p>
        <p><strong>Birthday:</strong> <%= resUser.getBirthday() %></p>
        <p><strong>Tel Number:</strong> <%= resUser.getTelNumber() %></p>
        <p><strong>Card Name:</strong> <%= card.getCardName() %></p>
        <p><strong>Card Number:</strong> <%= card.getCardNumber() %></p>
        <p><strong>Card Data:</strong> <%= card.getCardData() %></p>
        <p><strong>Balance:</strong> <%= card.getBalance() %></p>
        <p><strong>Status:</strong> <%= card.getCardStatus() %></p>
        <p><strong>Password:</strong> <%= card.getPasswordCard() %></p>

        <input type="hidden" name="cardId" value="<%= card.getCardNumber() %>"/>

        <button type="submit" name="status" value="working" class="btn-working">Working</button>
        <button type="submit" name="status" value="information is scarce" class="btn-information">Information is Scarce</button>
    </div>
    <hr/>
    <% } %>
    <% } else { %>
    <h1>Now not question</h1>
    <% } %>
</form>

<form action="/cabinet/admin/admin.jsp" method="get">
    <button type="submit" class="back-btn">Back</button>
</form>

</body>
</html>
