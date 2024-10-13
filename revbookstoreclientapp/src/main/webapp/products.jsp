<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <style>
        body {
            background: linear-gradient(135deg, #ece9e6, #ffffff);
            font-family: 'Poppins', sans-serif;
        }

        /* Dynamic Card styling */
        .card {
            border: none;
            border-radius: 20px;
            overflow: hidden;
            transition: transform 0.4s, box-shadow 0.4s;
            background: linear-gradient(145deg, #f0f0f0, #ffffff);
            box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1);
            height: 100%;
            display: flex;
            flex-direction: column;
            position: relative; /* Added for positioning buttons */
        }

        /* Hover effect for cards */
        .card:hover {
            transform: translateY(-10px) scale(1.02);
            box-shadow: 0 20px 30px rgba(0, 0, 0, 0.2);
        }

        .card-img-top {
            border-bottom: 1px solid #ddd;
            transition: transform 0.3s, opacity 0.3s;
            border-radius: 20px 20px 0 0;
        }

        .card-title {
            font-size: 1.5rem;
            font-weight: 700;
            margin-bottom: 0.75rem;
            color: #333;
        }

        .card-text {
            color: #666;
            margin-bottom: 1rem;
        }

        /* Dynamic button styling */
        .btn-grad {
            background-image: linear-gradient(to right, #4caf50, #a4c8e1); /* Gradient from green to semi-violet */
            margin: 10px;
            padding: 15px 45px;
            text-align: center;
            font-size: 18px;
            text-transform: uppercase;
            transition: transform 0.3s, box-shadow 0.3s; /* Include z-index in transition */
            background-size: 200% auto;
            color: white;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
            border-radius: 30px; /* More rounded corners */
            display: inline-block; /* Changed to inline-block for better alignment */
            border: none;
            cursor: pointer;
            position: relative; /* Added for positioning */
            z-index: 1; /* Set a default z-index */
        }

        .btn-info {
            background-image: linear-gradient(to right, #6a11cb, #2575fc);
            margin: 10px;
            padding: 15px 45px;
            text-align: center;
            font-size: 18px;
            text-transform: uppercase;
            transition: transform 0.3s, box-shadow 0.3s; /* Include z-index in transition */
            background-size: 200% auto;
            color: white;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
            border-radius: 30px; /* More rounded corners */
            display: inline-block; /* Changed to inline-block for better alignment */
            border: none;
            cursor: pointer;
            position: relative; /* Added for positioning */
            z-index: 1; /* Set a default z-index */
        }

        /* Hover effect for both buttons */
        .btn-grad:hover,
        .btn-info:hover {
            z-index: 2; /* Bring it to the front */
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.4); /* Slightly enhance shadow */
        }

        /* Keep other buttons in place and change size */
        .btn-info:hover + .btn-grad {
            transform: scale(0.8); /* Shrink the favorite button */
        }

        /* Button hover effect for favorite button */
        .btn-grad:hover {
            transform: translateY(-10px); /* Move the button up slightly */
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Product Details" />
</jsp:include>

<!-- Displaying Messages -->
<div class="container mt-5">
    <c:if test="${not empty message}">
        <div class="alert alert-success" role="alert">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">${error}</div>
    </c:if>
</div>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4">Books List</h1>
    <div class="row row-cols-2 row-cols-md-4 g-4">
        <c:forEach var="product" items="${productresult}">
            <div class="col">
                <div class="card">
                    <img src="${product.imageUrl}" class="card-img-top" alt="${product.productName}">
                    <div class="card-body">
                        <h5 class="card-title">${product.productName}</h5>
                        <p class="card-text">${product.productDescription}</p>
                        <p class="card-text">
                            <strong>MRP Price:</strong> &#8377;${product.price}
                            <br>
                            <strong style="color: Red">Discount Price:</strong> <strong style="font-weight:500;color:Red;">&#8377;${product.discountPrice}</strong>
                        </p>
                        <a style="text-decoration: none;" href="${pageContext.request.contextPath}/BuyerProductDetails?id=${product.productId}" class="btn-info" style="font-weight:900;font-size:18px;">View Details</a>
                        
                        <!-- Favorite Button with Hidden Inputs -->
                        <form action="${pageContext.request.contextPath}/addProductToFavorite" method="post" style="display:inline;">
                            <input type="hidden" name="productId" value="${product.productId}">
                            <input type="hidden" name="discountPrice" value="${product.discountPrice}">
                            <input type="hidden" name="totalPrice" value="${product.price}">
                            <input type="hidden" name="productName" value="${product.productName}">
                            <input type="hidden" name="productDescription" value="${product.productDescription}">
                            <button type="submit" class="btn-grad" style="font-weight:900;font-size:18px;">Favorite</button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:if test="${empty productresult}">
            <div class="col">
                <div class="alert alert-warning text-center" role="alert">No products found.</div>
            </div>
        </c:if>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
