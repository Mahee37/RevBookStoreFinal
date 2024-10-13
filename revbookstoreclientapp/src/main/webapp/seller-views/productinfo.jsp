<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp">
	<jsp:param name="pageTitle" value="Product Details" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
	<h1 class="mb-4">Product Details</h1>

	<div class="row">
		<!-- Product Image Section (Left Side) -->
		<div class="col-md-4">
			<c:choose>
				<c:when test="${not empty product.imageUrl}">
					<img src="${product.imageUrl}" alt="${product.name}"
						class="img-fluid mb-4">
				</c:when>
				<c:otherwise>
					<img src="https://via.placeholder.com/400" alt="Placeholder Image"
						class="img-fluid mb-4">
				</c:otherwise>
			</c:choose>
		</div>

		<!-- Product Details and Reviews Section (Right Side) -->
		<div class="col-md-8">
			<c:choose>
				<c:when test="${not empty product}">
					<div class="card mb-4">
						<div class="card-body">
							<h2 class="card-title">${product.name}</h2>
							<p class="card-text">${product.description}</p>
							<p class="card-text">
								<strong>Category:</strong> ${product.category}
							</p>
							<p class="card-text">
								<strong>Price:</strong> &#8377;${product.price}
							</p>

							<!-- Reviews Section -->
							<h3 class="mb-4 mt-4">Reviews</h3>
							<c:choose>
								<c:when test="${not empty reviews}">
									<ul class="list-group mb-4">
										<c:forEach var="review" items="${reviews}">
											<li class="list-group-item">
												<h5 class="mb-1">${review.username}</h5>
												<p class="mb-1">${review.message}</p> <small>Rating:
													${review.rating} / 5</small>
											</li>
										</c:forEach>
									</ul>
								</c:when>
								<c:otherwise>
									<div class="alert alert-info" role="alert">No reviews
										yet.</div>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-danger" role="alert">Product not
						found.</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>

<!-- Bootstrap JS and dependencies -->
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
