<%@ page import="dataBase.DB" %>
<%@ page import="java.util.Optional" %>
<%@ page import="entity.Card" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Card Management</title>
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

        .message {
            margin-top: 10px;
            padding: 10px;
            border-radius: 4px;
            text-align: center;
        }

        .success {
            background-color: #d4edda;
            color: #155724;
        }

        .warning {
            background-color: #f8d7da;
            color: #721c24;
        }
    </style>
</head>
<body>
<div class="container">
    <%
        String toCardNumber = request.getParameter("toCard");
        Optional<Card> toCard = Optional.empty();
        String message = null;
        String messageType = null;

        if (toCardNumber != null && !toCardNumber.isEmpty()) {
            toCard = DB.getByCardNumber(toCardNumber);
            if (toCard.isPresent()) {
                if (!(toCard.get().getCardStatus().name().equals("working"))) {
                    message = "This card is not working.";
                    messageType = "warning";
                }
            } else {
                message = "Card not found. Please enter a valid card number.";
                messageType = "warning";
            }
        }
        if ("POST".equalsIgnoreCase(request.getMethod()) && message == null) {
            String sum = request.getParameter("sum");
            if (sum != null && !sum.isEmpty() && toCard.isPresent()) {
                boolean isSuccessful = DB.addHistory((String) request.getSession().getAttribute("cardNumber"), toCardNumber, sum);
                if (isSuccessful) {
                    message = "Transfer successful!";
                    messageType = "success";
                } else {
                    message = "Transfer failed. Please try again.";
                    messageType = "warning";
                }
            }
        }
    %>

    <div>
        <form action="input_card_number.jsp" method="post">
            <input name="toCard" type="number" placeholder="Enter transfer card number" required
                   value="<%= toCardNumber != null ? toCardNumber : "" %>">
            <%
                if (toCard.isPresent() && toCard.get().getCardStatus().name().equals("working")) {
            %>
            <input type="number" name="sum" placeholder="Enter sum" required>
            <button type="submit">Transfer</button>
            <%
                }
            %>
        </form>

        <% if (message != null) { %>
        <div class="message <%= messageType %>"><%= message %></div>
        <form action="/cabinet/welcome.jsp" method="get">
            <button type="submit">Go to Welcome Page</button>
        </form>
        <% } %>
    </div>
</div>
</body>
</html>
