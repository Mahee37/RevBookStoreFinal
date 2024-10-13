<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Products</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Custom styles */
        body {
            background-color: #f8f9fa;
            font-family: 'Arial', sans-serif;
        }

        .container {
            margin-top: 5rem;
            background-color: #ffffff;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }

        h1 {
            margin-bottom: 1.5rem;
            color: #343a40;
        }

        .table {
            border-collapse: collapse;
        }

        .table th, .table td {
            padding: 1rem;
            text-align: center;
            vertical-align: middle;
        }

        .table thead th {
            background-color: #007bff;
            color: white;
            font-weight: bold;
        }

        .table tbody tr:nth-child(odd) {
            background-color: #f2f2f2;
        }

        .table tbody tr:hover {
            background-color: #e9ecef;
            cursor: pointer;
        }

        .table a {
            text-decoration: none;
            color: #007bff;
        }

        .table a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp" />
    <div class="container">
        <h1 class="text-center">All Products</h1>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>S.No</th>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Seller Username</th>
                    <th>Total Orders</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${products}" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td><a href="${pageContext.request.contextPath}/products/info?id=${product.id}">${product.name}</a></td>
                        <td>${product.category}</td>
                        <td>${product.price}</td>
                        <td>${product.sellerUsername}</td>
                        <td>${product.totalOrders}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
