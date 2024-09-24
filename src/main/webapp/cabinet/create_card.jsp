<%--
  Created by IntelliJ IDEA.
  User: Akhmadjon
  Date: 14.08.2024
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Card</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #333;
            color: #fff; /* Oq rangda matnlar */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            text-align: center;
        }

        form {
            background-color: #444; /* Quyuqroq fon */
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.5);
            width: 350px;
            margin: 20px auto;
        }

        input[type="text"],
        input[type="email"],
        input[type="tel"],
        input[type="search"],
        input[type="number"],
        input[type="date"] {
            width: 100%;
            padding: 12px;
            border-radius: 5px;
            border: 1px solid #777;
            margin-bottom: 15px;
            box-sizing: border-box;
            background-color: #555; /* Qoraroq input maydonlari */
            color: #fff; /* Oq rangda yozuvlar */
        }

        input[type="text"]:focus,
        input[type="email"]:focus,
        input[type="tel"]:focus,
        input[type="search"]:focus,
        input[type="number"]:focus,
        input[type="date"]:focus {
            border-color: #007BFF;
            outline: none;
        }

        button {
            width: 100%;
            padding: 12px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px;
        }

        button:hover {
            background-color: #0056b3;
        }

        h1 {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Create Card</h1>
    <form action="/cabinet/create_card" method="post">
        <div>
            <input name="address" type="search" placeholder="Enter address">
        </div>
        <div>
            <input name="passport_number" type="text" placeholder="Enter passport number">
        </div>
        <div>
            <input name="date" type="date" placeholder="Enter birthday">
        </div>
        <div>
            <input name="tel" type="tel" placeholder="Enter telephone number">
        </div>
        <div>
            <input name="cardName" type="text" placeholder="Enter card name">
        </div>
        <div>
            <input name="balance" type="number" placeholder="Enter default balance">
        </div>
        <div>
            <input name="password" type="number" placeholder="Enter for card password">
        </div>

        <button name="submit" type="submit">Submit</button>
    </form>
</div>
</body>
</html>
