<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customers List</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        tr:hover { background-color: #f5f5f5; }
        .form-container { margin-bottom: 20px; }
        .form-container input { margin: 5px; padding: 5px; }
        .search-container { margin-bottom: 20px; }
        .error { color: red; }
    </style>
</head>
<body>
    <h1>Customers List</h1>
    <div class="search-container">
        <form action="/customers/search" method="get">
            <input type="text" name="name" placeholder="Search by Customer Name">
            <input type="submit" value="Search">
        </form>
    </div>
    <c:if test="${not empty errorMessage}">
        <p class="error">${errorMessage}</p>
    </c:if>
    <div class="form-container">
        <form:form modelAttribute="customer" action="/customers/add" method="post">
            <form:input path="id" type="hidden"/> <!-- Thêm trường ẩn để gửi id -->
            <form:input path="name" placeholder="Name" required="true"/>
            <form:errors path="name" cssClass="error"/>
            <br>
            <form:input path="email" placeholder="Email" required="true"/>
            <form:errors path="email" cssClass="error"/>
            <br>
            <form:input path="phone" placeholder="Phone" required="true"/>
            <form:errors path="phone" cssClass="error"/>
            <br>
            <input type="submit" value="${customer.id != null ? 'Update Customer' : 'Add Customer'}"/>
        </form:form>
    </div>
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="customer" items="${customers}">
            <tr>
                <td>${customer.id}</td>
                <td>${customer.name}</td>
                <td>${customer.email}</td>
                <td>${customer.phone}</td>
                <td>
                    <a href="/customers/edit/${customer.id}">Edit</a>
                    <a href="/customers/delete/${customer.id}" onclick="return confirm('Are you sure?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>