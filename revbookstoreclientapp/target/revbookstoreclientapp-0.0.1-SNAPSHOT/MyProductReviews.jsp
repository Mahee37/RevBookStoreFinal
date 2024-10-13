<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Reviews</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2>Product Reviews</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        
        <c:if test="${not empty reviews}">
            <div class="list-group">
                <c:forEach items="${reviews}" var="review">
                    <div class="list-group-item">
                        <h5 class="mb-1">Product Name: ${review.productName}</h5> <!-- Displaying Product Name -->
                        <p class="mb-1">Description: ${review.productDescription}</p> <!-- Displaying Product Description -->
                        <p class="mb-1">User ID: ${review.userId}</p> <!-- Displaying User ID -->
                        <p class="mb-1">Rating: ${review.rating}</p>
                        <p class="mb-1">Review: ${review.reviewText}</p>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty reviews}">
            <p>No reviews found for this user.</p>
        </c:if>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
