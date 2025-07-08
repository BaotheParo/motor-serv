<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Parts List</title>
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
        .pagination { margin-top: 20px; text-align: center; }
        .pagination a { margin: 0 5px; text-decoration: none; color: #007bff; }
        .pagination a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>Parts List</h1>
    <div class="search-container">
        <form action="/parts/search" method="get">
            <input type="text" name="name" placeholder="Search by Part Name">
            <input type="submit" value="Search">
        </form>
    </div>
    <c:if test="${not empty errorMessage}">
        <p class="error">${errorMessage}</p>
    </c:if>
    <div class="form-container">
        <form:form modelAttribute="part" action="/parts/add" method="post">
            <form:input path="id" type="hidden"/> <!-- Thêm trường ẩn để gửi id -->
            <form:input path="name" placeholder="Name" required="true"/>
            <form:errors path="name" cssClass="error"/>
            <br>
            <form:input path="price" type="number" step="0.01" placeholder="Price" required="true"/>
            <form:errors path="price" cssClass="error"/>
            <br>
            <form:input path="stock" type="number" placeholder="Stock" required="true"/>
            <form:errors path="stock" cssClass="error"/>
            <br>
            <input type="submit" value="${part.id != null ? 'Update Part' : 'Add Part'}"/>
        </form:form>
    </div>
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="part" items="${parts}">
            <tr>
                <td>${part.id}</td>
                <td>${part.name}</td>
                <td>${part.price}</td>
                <td>${part.stock}</td>
                <td>
                    <a href="/parts/edit/${part.id}">Edit</a>
                    <a href="/parts/delete/${part.id}" onclick="return confirm('Are you sure?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div class="pagination">
        <c:if test="${totalPages > 1}">
            <c:forEach begin="0" end="${totalPages - 1}" var="i">
                <a href="/parts?page=${i}">${i + 1}</a>
            </c:forEach>
        </c:if>
    </div>
</body>
</html>