<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Motorcycle Parts Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f4f4f4;
        }
        h1 {
            color: #333;
            text-align: center;
        }
        .nav-container {
            max-width: 600px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            margin: 15px 0;
        }
        a {
            text-decoration: none;
            color: #007bff;
            font-size: 18px;
        }
        a:hover {
            text-decoration: underline;
            color: #0056b3;
        }
        .footer {
            text-align: center;
            margin-top: 20px;
            color: #666;
        }
    </style>
</head>
<body>
    <h1>Motorcycle Parts Management System</h1>
    <div class="nav-container">
        <ul>
            <li><a href="/customers">Manage Customers</a></li>
            <li><a href="/orders">Manage Orders</a></li>
            <li><a href="/parts">Manage Parts</a></li>
            <li><a href="/report">View Sales Report</a></li>
        </ul>
    </div>
    <div class="footer">
        <p>Developed by [Your Name] | Powered by Spring Boot</p>
    </div>
</body>
</html>