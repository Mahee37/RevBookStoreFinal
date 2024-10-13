<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:out value="${pageTitle}" /></title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Custom CSS for dynamic styling -->
    <style>
        .navbar {
            background-color: rgba(255, 255, 255, 0.8);
            backdrop-filter: blur(10px);
            border-radius: 15px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .navbar-brand {
            font-weight: 700;
            letter-spacing: 1px;
            color: #343a40;
            display: flex;
            align-items: center;
        }
        .navbar-brand i {
            margin-right: 5px;
        }
        .nav-link {
            font-weight: 500;
            transition: color 0.3s ease;
        }
        .nav-link.active, .nav-link:hover {
            color: #007bff;
        }
        .navbar-toggler {
            border-color: #343a40;
        }
        .btn-outline-danger, .btn-outline-success {
            border-radius: 50px;
            padding: 0.375rem 1rem;
            transition: background-color 0.3s ease, color 0.3s ease;
        }
        .btn-outline-danger:hover {
            background-color: #dc3545;
            color: white;
        }
        .btn-outline-success:hover {
            background-color: #28a745;
            color: white;
        }
        .navbar-brand, .nav-link, .btn-outline-danger, .btn-outline-success {
            font-family: 'Poppins', sans-serif;
        }
        .form-control {
            border-radius: 25px;
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/BuyerInventory">
                <i class="bi bi-book"></i> RevBookStore
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/BuyerInventory">
                            All Products
                        </a>
                    </li>
                    <!-- Show additional options if the user is a buyer -->
                    <c:if test="${sessionScope['user-role'] == 'buyer'}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/OrderDetails">
                                My Orders
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/getReviewsByUserId">
                                My Reviews
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/BuyerCart">
                                Cart
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/getWishlistByUserId">
                                Wishlist
                            </a>
                        </li>
                    </c:if>
                </ul>
                <!-- Search Form -->
                <form class="d-flex" method="get" action="${pageContext.request.contextPath}/searchProducts">
                    <input class="form-control me-2" type="search" name="productCategory" placeholder="Search products" aria-label="Search" value="${param.productCategory}">
                    <button class="btn btn-outline-success" type="submit">
                        <i class="bi bi-search"></i>
                    </button>
                </form>

                <!-- User greeting and login/logout button -->
                <ul class="navbar-nav ms-auto">
                    <c:choose>
                        <c:when test="${sessionScope['user-role'] == 'buyer'}">
                            <li class="nav-item">
                                <a class="nav-link" href="#">
                                    <i class="bi bi-person-circle"></i> Hi, ${sessionScope.name}
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="btn btn-outline-danger ms-3" href="${pageContext.request.contextPath}/login.jsp">
                                    <i class="bi bi-box-arrow-right"></i> Logout
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp">
                                    <i class="bi bi-box-arrow-in-right"></i> Login
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
