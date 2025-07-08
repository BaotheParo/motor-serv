<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sales Report</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        .report-container { max-width: 600px; margin: 0 auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .form-container { margin-bottom: 20px; }
        .form-container input { margin: 5px; padding: 5px; }
        .footer { text-align: center; margin-top: 20px; color: #666; }
    </style>
</head>
<body>
    <h1>Sales Report</h1>
    <div class="report-container">
        <p>Total Revenue: ${totalRevenue}</p>
        <c:if test="${not empty revenueByDate}">
            <p>Revenue from ${startDate} to ${endDate}: ${revenueByDate}</p>
        </c:if>
        <div class="form-container">
            <form action="/report/date" method="get">
                <input type="date" name="startDate" required>
                <input type="date" name="endDate" required>
                <input type="submit" value="Filter by Date">
            </form>
        </div>
        <h2>Revenue by Customer</h2>
        <table>
            <tr>
                <th>Customer ID</th>
                <th>Total Revenue</th>
            </tr>
            <c:forEach items="${revenueByCustomer}" var="entry">
                <tr>
                    <td>${entry.key}</td>
                    <td>${entry.value}</td>
                </tr>
            </c:forEach>
        </table>
        <a href="/report/pdf">Export to PDF</a>
        <br>
        <a href="/">Back to Home</a>
    </div>
    <div class="footer">
        <p>Developed by [Your Name] | Powered by Spring Boot</p>
    </div>
</body>
</html>