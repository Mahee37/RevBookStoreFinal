<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homepage</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <style>
        /* Custom styles */
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }

        .hero {
            background: rgba(0, 0, 0, 0.5); /* Semi-transparent overlay for readability */
            color: #ffffff;
            height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
            padding: 0 2rem;
            position: relative;
            z-index: 1; /* Ensure content is above the blurred background */
        }

        .hero::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: url('${pageContext.request.contextPath}/images/hero.jpg') no-repeat center center;
            background-size: cover;
            filter: blur(8px);
            z-index: -1; /* Place behind content */
        }

        .hero h1 {
            font-size: 4rem;
            margin-bottom: 1rem;
            font-weight: 700;
        }

        .hero p {
            font-size: 1.25rem;
            margin-bottom: 2rem;
            font-weight: 300;
        }

        .btn-container {
            display: flex;
            gap: 1rem; /* Space between buttons */
        }

        .btn-container .btn {
            padding: 0.5rem 1.5rem; /* Adjust padding for normal size */
            font-size: 0.9rem; /* Adjust font size for normal size */
            font-weight: 400; /* Normal font weight */
            border-radius: 20px; /* Adjust border-radius for a softer look */
            transition: background 0.3s, color 0.3s;
        }

        .hero .btn {
            background: #ffffff;
            color: #333;
        }

        .hero .btn:hover {
            background: #333;
            color: #ffffff;
        }

        .card {
            border: 1px solid #ddd;
            border-radius: 10px;
            overflow: hidden;
            transition: transform 0.3s, box-shadow 0.3s;
        }

        .card:hover {
            transform: translateY(-10px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
        }

        .card-body {
            padding: 2rem;
        }

        .footer {
            background-color: #222;
            color: #ffffff;
            text-align: center;
            padding: 1rem;
        }

        .footer a {
            color: #ffffff;
            text-decoration: none;
            margin: 0 0.5rem;
            font-weight: 500;
        }

        .footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <!-- Main Section -->
    <main>
        <!-- Hero Section with Blurred Background Image -->
        <div class="hero">
            <h1 class="display-4">Rev Book Store</h1>
            <p class="lead">Discover the world of books with us. Browse our collection and find your next read!</p>
            <div class="btn-container">
                <%-- <a href="${pageContext.request.contextPath}/products" class="btn">Browse Products</a> --%>
                <a href="${pageContext.request.contextPath}/login.jsp" class="btn">Login</a>
                <a href="${pageContext.request.contextPath}/signup.jsp" class="btn">Create Account</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/buyer/cart">
                                Cart
                            </a>
            </div>
        </div>

        <!-- Benefits Cards -->
        <div class="container my-5">
            <div class="row text-center">
                <!-- Buyer Benefits Card -->
                <div class="col-md-6 mb-4">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Benefits for Buyers</h5>
                            <p class="card-text">Enjoy exclusive discounts, a wide range of book collections, and a seamless shopping experience.</p>
                            <a href="${pageContext.request.contextPath}/signup" class="btn btn-light">Create Account</a>
                        </div>
                    </div>
                </div>
                <!-- Retailer Benefits Card -->
                <div class="col-md-6 mb-4">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Benefits for Retailers</h5>
                            <p class="card-text">Access a vast market, manage your inventory efficiently, and boost your sales with us.</p>
                            <a href="${pageContext.request.contextPath}/signup" class="btn btn-light">Create Account</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <!-- Footer -->
    <footer class="footer">
        <p class="mb-0">© 2024 Rev Book Store. All rights reserved.</p>
        <p class="mb-0">
            <a href="${pageContext.request.contextPath}/contact">Contact Us</a> |
            <a href="${pageContext.request.contextPath}/about">About Us</a>
        </p>
    </footer>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
