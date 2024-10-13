<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${pageTitle}" /></title>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
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
	}
	.nav-link {
		font-weight: 500;
		transition: color 0.3s ease;
	}
	.nav-link:hover {
		color: #007bff;
	}
	.navbar-toggler {
		border-color: #343a40;
	}
	.btn-outline-danger {
		border-radius: 50px;
		padding: 0.375rem 1rem;
		transition: background-color 0.3s ease, color 0.3s ease;
	}
	.btn-outline-danger:hover {
		background-color: #dc3545;
		color: white;
	}
	.navbar-brand, .nav-link, .btn-outline-danger {
		font-family: 'Poppins', sans-serif;
	}
</style>
</head>
<body>
	<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-light">
		<div class="container-fluid">
			<a class="navbar-brand"
				href="${pageContext.request.contextPath}/inventory">RevBookStore</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav me-auto">
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/inventory">Inventory</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/getOrders">View Orders</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/getSellerReviewsByUserId">Product Reviews</a></li>
				</ul>
				<ul class="navbar-nav ms-auto">
					<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/inventory">
						<i class="bi bi-person-circle"></i> Hi, ${sessionScope.name}
					</a></li>
					<li class="nav-item"><a class="btn btn-outline-danger ms-3"
						href="${pageContext.request.contextPath}/logout"> <i class="bi bi-box-arrow-right"></i> Logout
					</a></li>
				</ul>
			</div>
		</div>
	</nav>
</body>
</html>
