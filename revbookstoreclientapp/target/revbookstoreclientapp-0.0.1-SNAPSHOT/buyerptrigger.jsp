<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Trigger View Product Details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h1>Product Details</h1>
    <form action="${pageContext.request.contextPath}/BuyerProductDetails" method="get">
        <div class="form-group">
            <label for="productId">Enter Product ID:</label>
            <input type="number" class="form-control" id="productId" name="productId" required>
        </div>
        <button type="submit" class="btn btn-primary">View Product Details</button>
    </form>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger mt-3" role="alert">
            ${errorMessage}
        </div>
    </c:if>

    <c:if test="${not empty productresult1}">
        <h2 class="mt-4">${productresult1.name}</h2>
        <p><strong>Description:</strong> ${productresult1.description}</p>
        <p><strong>Category:</strong> ${productresult1.category}</p>
        <p><strong>Price:</strong> &#8377;${productresult1.price}</p>
        <img src="${productresult1.imageUrl}" alt="${productresult1.name}" class="img-fluid mb-4">
    </c:if>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
