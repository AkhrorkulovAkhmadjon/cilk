<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #333; /* Qoraytirilgan fon rangi */
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
            background-color: #007BFF; /* Tugma rangi ko'k */
            color: white; /* Yozuvlar oq rangda */
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        button:hover {
            background-color: #0056b3; /* Hover effekti uchun quyuqroq ko'k */
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
    </style>
</head>
<body>
<div class="container">
    <h1><%= "Hello World!" %></h1>
    <div>
        <a href="/auth/sign_in">SIGN_IN</a>
    </div>
    <div>
        <a href="/auth/sign_up">SIGN_UP</a>
    </div>
</div>
</body>
</html>
