<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.revbookstoreclientapp.dto.ReviewDTO" %>
<%@ page import="com.revbookstoreclientapp.dto.ProductWithReviews" %>
<%@ page import="java.util.List" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Reviewed Product" />
</jsp:include>

<div class="container mt-5">
    <h1 class="mb-4">Reviewed Product</h1>

    <!-- Display error message if present -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">
            ${error}
        </div>
    </c:if>

    <!-- Check for ProductWithReviews object -->
    <c:set var="productWithReviews" value="${requestScope.product}" />
    <c:set var="reviews" value="${requestScope.reviews}" />

    <c:if test="${not empty productWithReviews}">
        <!-- Product Information Table with Black Banner -->
        <div class="bg-dark text-white p-3 mb-4 rounded">
            <h5>Product Details</h5>
            <table class="table table-bordered table-striped bg-white text-dark rounded">
                <thead>
                    <tr>
                        <th scope="col">Product ID</th>
                        <th scope="col">Product Name</th>
                        <th scope="col">Description</th>
                        <th scope="col">Category</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${productWithReviews.productId}</td>
                        <td>${productWithReviews.productName}</td>
                        <td>${productWithReviews.productDescription}</td>
                        <td>${productWithReviews.productCategory}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- Reviews Section -->
        <c:if test="${not empty reviews}">
            <h5 class="mt-4">Your Reviews:</h5>
            <table class="table table-bordered table-striped rounded">
                <thead>
                    <tr>
                        <th scope="col">Review ID</th> <!-- New Review ID column -->
                        <th scope="col">Rating</th>
                        <th scope="col">Review</th>
                        <th scope="col">Action</th> <!-- Action column for remove button -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${reviews}" var="review">
                        <tr>
                            <td>${review.reviewId}</td> <!-- Display Review ID -->
                            <td>${review.rating}</td>
                            <td>${review.reviewText}</td>
                            <td>
                                <!-- Form for removing review -->
                                <form action="${pageContext.request.contextPath}/deleteReview" method="post">
                                    <input type="hidden" name="reviewId" value="${review.reviewId}" />
                                    <button type="submit" class="btn btn-danger btn-sm rounded">
                                        Remove
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty reviews}">
            <div class="alert alert-warning" role="alert">
                No reviews available for this product.
            </div>
        </c:if>
    </c:if>

    <c:if test="${empty productWithReviews}">
        <div class="alert alert-warning" role="alert">
            No product information available.
        </div>
    </c:if>

    <!-- Go back button styled in green -->
    <div class="text-end mt-4">
        <a href="${pageContext.request.contextPath}/BuyerInventory" class="btn btn-success rounded">Go back to Products</a> <!-- Green button -->
    </div>
</div>

<!-- Optional Bootstrap JS and jQuery for additional functionality -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
