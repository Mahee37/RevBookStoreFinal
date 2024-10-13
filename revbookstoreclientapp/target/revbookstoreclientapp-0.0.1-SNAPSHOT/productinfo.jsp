<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style>
/* Your existing styles */

/* New style for the orange button */
.btn-orange {
	background-color: orange;
	color: white;
	border: none;
	padding: 10px 20px;
	font-size: 16px;
	cursor: pointer;
}

.btn-orange:hover {
	background-color: darkorange; /* Darker shade on hover */
}

/* Add to Cart button style */
.btn-success {
	padding: 10px 20px;
	font-size: 16px;
}

/* View in Cart button style - soft blue */
.btn-soft-blue {
	background-color: #4da6ff;
	color: white;
	border: none;
	padding: 10px 20px;
	font-size: 16px;
	cursor: pointer;
}

.btn-soft-blue:hover {
	background-color: #3399ff; /* Darker blue on hover */
}

/* Icons */
.cart-icon {
	margin-right: 8px;
}

/* To place buttons side by side */
.action-buttons {
	display: flex;
	gap: 15px;
}
</style>

<script>
	function showSuccessPopup() {
		alert("Review submitted successfully!"); // You can customize the popup message as needed
	}
</script>
</head>
<body>

	<jsp:include page="header.jsp">
		<jsp:param name="pageTitle" value="Product Details" />
	</jsp:include>

	<div class="container mt-5">
		<h1 class="mb-4">Product Details</h1>

		<!-- Success Message Display -->
		<c:if test="${not empty message}">
			<div class="alert alert-success" role="alert">${message}</div>
			<script>
				showSuccessPopup(); // Call the function to show the popup
			</script>
		</c:if>

		<div class="row">
			<div class="col-md-4">
				<c:choose>
					<c:when test="${not empty productdetails.product.imageUrl}">
						<img src="${productdetails.product.imageUrl}"
							alt="${productdetails.product.productName}"
							class="img-fluid mb-4">
					</c:when>
					<c:otherwise>
						<img src="https://via.placeholder.com/400" alt="Placeholder Image"
							class="img-fluid mb-4">
					</c:otherwise>
				</c:choose>
			</div>

			<div class="col-md-8">
				<c:choose>
					<c:when test="${not empty productdetails.product}">
						<div class="card mb-4">
							<div class="card-body">
								<h2 class="card-title">${productdetails.product.productName}</h2>
								<p class="card-text">${productdetails.product.productDescription}</p>
								<p class="card-text">
									<strong>Category:</strong>
									${productdetails.product.productCategory}
								</p>

								<!-- Displaying price with discount logic -->
								<p class="card-text">
									<strong>Price:</strong>
									<c:choose>
										<c:when test="${productdetails.product.discountPrice > 0}">
											<span style="text-decoration: line-through;">&#8377;${productdetails.product.price}</span>
											<span style="color: green;">&#8377;${productdetails.product.discountPrice}</span>
										</c:when>
										<c:otherwise>
                                            &#8377;${productdetails.product.price}
                                        </c:otherwise>
									</c:choose>
								</p>

								<!-- Buttons for Add to Cart and View in Cart -->
								<div class="action-buttons">
									<form method="post"
										action="${pageContext.request.contextPath}/addProductsToCarts">
										<input type="hidden" name="productId"
											value="${productdetails.product.productId}"> <input
											type="hidden" name="productName"
											value="${productdetails.product.productName}"> <input
											type="hidden" name="productDescription"
											value="${productdetails.product.productDescription}">
										<input type="hidden" name="discountPrice"
											value="${productdetails.product.discountPrice}"> <input
											type="hidden" name="quantity" value="1">

										<c:choose>
											<c:when test="${incart}">
												<a href="${pageContext.request.contextPath}/Buyercart"
													class="btn btn-primary">View in Cart</a>
											</c:when>
											<c:otherwise>
												<button type="submit" class="btn btn-success mt-3">Add
													to Cart</button>
											</c:otherwise>
										</c:choose>
									</form>

									<!-- New button to view cart (beside Add to Cart button) -->
									<form action="${pageContext.request.contextPath}/BuyerCart"
										method="get">
										<button type="submit" class="btn btn-success mt-3">
											<i class="cart-icon fa fa-shopping-cart"></i> View in Cart
										</button>
									</form>
								</div>

								<!-- Seller Information -->
								<c:if test="${not empty productdetails.seller}">
									<div class="mt-4">
										<h4>Seller Information</h4>
										<p>
											<strong>Name:</strong> ${productdetails.seller.name}
										</p>
										<p>
											<strong>Address:</strong> ${productdetails.seller.address}
										</p>
									</div>
								</c:if>

								<!-- Reviews Section -->
								<h3 class="mb-4 mt-4">Reviews</h3>
								<c:choose>
									<c:when test="${not empty productdetails.reviews}">
										<ul>
											<c:forEach var="review" items="${productdetails.reviews}">
												<li><strong>Rating:</strong> ${review.rating}<br /> <strong>Review:</strong>
													${review.reviewText}</li>
											</c:forEach>
										</ul>
									</c:when>
									<c:otherwise>
										<div class="alert alert-info" role="alert">No reviews
											yet.</div>
									</c:otherwise>
								</c:choose>

								<!-- Post a Review Section -->
								<h5 class="mb-4">Post a Review</h5>
								<c:choose>
									<c:when test="${sessionScope['user-role'] == 'buyer'}">
										<form method="post"
											action="${pageContext.request.contextPath}/submitreview">
											<input type="hidden" name="productId"
												value="${productdetails.product.productId}">
											<div class="mb-3">
												<label for="reviewText" class="form-label">Your
													Review</label>
												<textarea class="form-control" id="reviewText"
													name="reviewText" rows="4" required></textarea>
											</div>
											<div class="mb-3">
												<label class="form-label">Rating</label>
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="radio" id="rating1"
														name="rating" value="1" required> <label
														class="form-check-label" for="rating1">1 - Poor</label>
												</div>
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="radio" id="rating2"
														name="rating" value="2"> <label
														class="form-check-label" for="rating2">2 - Fair</label>
												</div>
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="radio" id="rating3"
														name="rating" value="3"> <label
														class="form-check-label" for="rating3">3 - Good</label>
												</div>
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="radio" id="rating4"
														name="rating" value="4"> <label
														class="form-check-label" for="rating4">4 - Very
														Good</label>
												</div>
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="radio" id="rating5"
														name="rating" value="5"> <label
														class="form-check-label" for="rating5">5 -
														Excellent</label>
												</div>
											</div>
											<button type="submit" class="btn btn-primary">Submit
												Review</button>
										</form>
									</c:when>
									<c:otherwise>
										<div class="alert alert-warning" role="alert">You must
											be logged in to post a review.</div>
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
</body>
</html>
