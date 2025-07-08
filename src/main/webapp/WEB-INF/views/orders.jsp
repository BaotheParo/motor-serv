<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Orders List</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        tr:hover { background-color: #f5f5f5; }
        .form-container { margin-bottom: 20px; }
        .form-container input, .form-container select { margin: 5px; padding: 5px; }
        .search-container { margin-bottom: 20px; }
        .error { color: red; }
    </style>
</head>
<body>
    <h1>Orders List</h1>
    <div class="search-container">
        <form action="/orders/search" method="get">
            <select name="customerId">
                <option value="">Select Customer</option>
                <c:forEach var="customer" items="${customers}">
                    <option value="${customer.id}">${customer.name}</option>
                </c:forEach>
            </select>
            <input type="submit" value="Search">
        </form>
    </div>
    <c:if test="${not empty errorMessage}">
        <p class="error">${errorMessage}</p>
    </c:if>
    <div class="form-container">
        <form:form modelAttribute="order" action="/orders/add" method="post">
            <form:input path="id" type="hidden"/> <!-- Thêm trường ẩn để gửi id -->
            <form:select path="customerId" required="true">
                <form:option value="" label="Select Customer"/>
                <form:options items="${customers}" itemValue="id" itemLabel="name"/>
            </form:select>
            <form:errors path="customerId" cssClass="error"/>
            <br>
            <form:select path="partId" required="true">
                <form:option value="" label="Select Part"/>
                <form:options items="${parts}" itemValue="id" itemLabel="name"/>
            </form:select>
            <form:errors path="partId" cssClass="error"/>
            <br>
            <form:input path="quantity" type="number" placeholder="Quantity" required="true"/>
            <form:errors path="quantity" cssClass="error"/>
            <br>
            <form:input path="orderDate" type="date" required="true"/>
            <form:errors path="orderDate" cssClass="error"/>
            <br>
            <input type="submit" value="${order.id != null ? 'Update Order' : 'Add Order'}"/>
        </form:form>
    </div>
    <table>
        <tr>
            <th>ID</th>
            <th>Customer ID</th>
            <th>Part ID</th>
            <th>Quantity</th>
            <th>Order Date</th>
            <th>Total Price</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.id}</td>
                <td>${order.customerId}</td>
                <td>${order.partId}</td>
                <td>${order.quantity}</td>
                <td>${order.orderDate}</td>
                <td>${order.totalPrice}</td>
                <td>
                    <a href="/orders/edit/${order.id}">Edit</a>
                    <a href="/orders/delete/${order.id}" onclick="return confirm('Are you sure?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>