<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Sign Up</title>
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

        form {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            width: 350px;
            text-align: center;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border-radius: 5px;
            border: 1px solid #ccc;
            box-sizing: border-box;
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

        input:focus {
            border-color: #007BFF;
            outline: none;
        }
    </style>
</head>
<body>
<form action="/auth/sign_up" method="post">
    <div>
        <input name="name" type="text" placeholder="Enter Name" required>
    </div>
    <div>
        <input name="email" type="email" placeholder="Enter Email" required>
    </div>
    <div>
        <input name="password" type="password" placeholder="Enter Password" required>
    </div>
    <div>
        <button type="submit">Sign Up</button>
    </div>
</form>
</body>
</html>
