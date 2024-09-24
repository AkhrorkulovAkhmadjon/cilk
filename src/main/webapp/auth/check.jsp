<%@ page import="entity.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Verification</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4; /* Yengil fon rangi */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            color: #333;
        }

        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            text-align: center;
        }

        h1 {
            color: #007BFF;
            margin-bottom: 20px;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: calc(100% - 22px);
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ddd;
            box-sizing: border-box;
            margin-bottom: 15px;
            font-size: 16px;
        }

        button {
            width: 100%;
            padding: 10px;
            background-color: #007BFF; /* Tugma rangi ko'k */
            color: white; /* Yozuvlar oq rangda */
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 18px;
            margin-top: 10px;
        }

        button:hover {
            background-color: #0056b3; /* Hover effekti uchun quyuqroq ko'k */
        }

        button:focus {
            outline: none;
        }

        a {
            text-decoration: none;
        }

        .message {
            color: #d9534f; /* Xatolik uchun qizil rang */
            font-size: 18px;
            margin: 20px 0;
        }

        .success {
            color: #5bc0de; /* Muvaffaqiyatli xabar uchun moviy rang */
        }
    </style>
</head>
<body>
<div class="container">
    <%
        String id = String.valueOf(request.getAttribute("Id"));
        String message = (String) request.getAttribute("message");
    %>
    <form method="post" action="/auth/checkEmail">
        <% if (message == null) { %>
        <h1>Verify Your Account</h1>
        <input type="password" name="code" placeholder="Enter check code" required>
        <input type="hidden" name="id" value="<%=id%>">
        <button type="submit">Submit</button>
        <% } else { %>
        <div class="message"><%= message %></div>
        <a href="/">
            <button type="button">Sign In</button>
        </a>
        <% } %>
    </form>
</div>
</body>
</html>
