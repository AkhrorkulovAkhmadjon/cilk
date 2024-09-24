<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<%@ page import="dataBase.DB" %>
<%@ page import="entity.Users" %>
<%@ page import="entity.Card" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        div.container {
            text-align: center;
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
            width: 300px;
            margin: 10px auto;
        }

        input[type="text"],
        input[type="email"] {
            width: 100%;
            padding: 10px;
            border-radius: 4px;
            border: 1px solid #ccc;
            box-sizing: border-box;
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
            margin-bottom: 10px;
        }

        button:hover {
            background-color: #0056b3;
        }

        button.admin {
            background-color: #FF5733;
            border: 1px solid #FFBD00;
        }

        button.admin:hover {
            background-color: #C70039;
        }

        input[type="text"]:focus,
        input[type="email"]:focus {
            border-color: #007BFF;
            outline: none;
        }

        img {
            width: 150px;
            margin-bottom: 20px;
        }

        .card-details {
            color: white;
            background-color: #444;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 10px;
            text-decoration: none;
            display: block;
        }

        .card-details:hover {
            background-color: #555;
        }
    </style>
    <title>Card Management</title>
</head>
<body>
<%
    Optional<Users> userByCookies = DB.getUserByCookies(request);
    if (userByCookies.isPresent()) {
        List<Card> selectCard = userByCookies.get().getCards();
        String[] colors = {"#FFD700", "#FF5733", "#33FF57", "#5733FF", "#FF33A1"};
        int colorIndex = 0;
        if (!selectCard.isEmpty()) {
            for (Card card : selectCard) {
                if ("working".equals(card.getCardStatus().name())) {
                    String cardColor = colors[colorIndex % colors.length];
                    colorIndex++;
%>
<div>
    <form action="/cabinet/card_mun.jsp" method="post" class="card-details" style="background-color:<%= cardColor %>;">
        <input type="hidden" name="cardNumber" value="<%= card.getCardNumber() %>">
        <button type="submit" style="background-color:<%= cardColor %>;">
            <div>
                <p><strong>Card Name:</strong> <%= card.getCardName() %></p>
                <p><strong>Card Number:</strong> <%= card.getCardNumber() %></p>
                <p><strong>Balance:</strong> <%= card.getBalance() %></p>
            </div>
        </button>
    </form>
</div>
<%
            }
        }
    }
} else {
%>
<p style="color: white;">No cards available.</p>
<%
    }
%>
<div class="container">
    <h1 style="color: white;">Choose Command</h1>
    <form action="/cabinet/create_card.jsp" method="post">
        <button type="submit">Create Card</button>
    </form>

    <% if (userByCookies.isPresent() && "ADMIN".equals(userByCookies.get().getRole().name())) { %>
    <form action="/cabinet/admin/admin.jsp" method="post">
        <button type="submit" class="admin">Admin page</button>
    </form>
    <% } %>
</div>
</body>
</html>
