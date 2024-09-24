<%@ page import="dataBase.DB" %>
<%@ page import="java.util.Optional" %>
<%@ page import="entity.Users" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users Table</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            color: #333;
        }

        h1 {
            text-align: center;
            color: #4CAF50;
            margin-top: 20px;
        }

        table {
            width: 80%;
            margin: 20px auto;
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

        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 8px 16px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 4px;
        }

        button:hover {
            background-color: #45a049;
        }

        .button-container {
            display: flex;
            gap: 10px;
        }

        p {
            text-align: center;
            font-size: 18px;
        }
    </style>
</head>
<body>
<form>3
    <h1>Users Table</h1>
    <%
        Optional<List<Users>> resUsers = DB.getAllUsers();
        if (resUsers.isPresent()) {
            List<Users> users = resUsers.get();
    %>
    <table>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Account status</th>
            <th>Tel number</th>
            <th>Action</th>
        </tr>
        <% for (Users user : users) { %>
        <tr>
            <td><%= user.getName() %></td>
            <td><%= user.getEmail() %></td>
            <td><%= user.getStatus() %></td>
            <td><%= user.getTelNumber() %></td>
            <td class="button-container">
                <form action="editUser.jsp" method="get" style="display:inline;">
                    <input type="hidden" name="userId" value="<%= user.getUser_id() %>">
                    <button type="submit">Edit</button>
                </form>
                <form action="/cabinet/admin/selectCard.jsp" method="get" style="display:inline;">
                    <input type="hidden" name="userId" value="<%= user.getUser_id() %>">
                    <button type="submit">Select Card</button>
                </form>
            </td>
        </tr>
        <% } %>
        <div>
            <form action="/cabinet/admin/question.jsp" method="post">
                <button name="question" type="submit">Question</button>
            </form>
        </div>
    </table>
    <form action="/cabinet/welcome.jsp" method="get">
        <button type="submit" class="back-btn">Back</button>
    </form>
    <%
    } else {
    %>
    <p>No users found.</p>
    <%
        }
    %>

</form>
</body>
</html>
