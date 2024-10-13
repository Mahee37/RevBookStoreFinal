<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.revbookstoreclientapp.dto.ProductWithReviews" %>
<%@ page import="com.revbookstoreclientapp.dto.Review" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Product Reviews" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4">Product Reviews</h1>

    <c:choose>
        <c:when test="${not empty products}">
            <ul class="list-group">
                <c:forEach items="${products}" var="product">
                    <li class="list-group-item">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <!-- Display Product Name -->
                                <h5 class="mb-1" style="color: black;"><strong>Product Name:</strong> ${product.productName}</h5>
                                <!-- Label for Product Description -->
                                <p class="mb-1" style="color: green;"><strong>Description:</strong> ${product.productDescription}</p>
                                <!-- Label for Product Category -->
                                <p class="mb-1" style="color: black;"><strong>Category:</strong> ${product.productCategory}</p>

                                <h6 style="color: black;">Reviews:</h6>
                                <c:if test="${not empty product.reviews}">
                                    <ul class="list-unstyled">
                                        <c:forEach items="${product.reviews}" var="review">
                                            <li class="mb-2">
                                                <div class="card" style="border: 2px solid black;">
                                                    <div class="card-body">
                                                        <p class="card-text" style="color: green;"><strong>Rating:</strong> ${review.rating}/5</p>
                                                        <p class="card-text" style="color: black;">${review.reviewText}</p>
                                                    </div>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                                <c:if test="${empty product.reviews}">
                                    <p>No reviews available for this product.</p>
                                </c:if>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info" role="alert">No products found.</div>
        </c:otherwise>
    </c:choose>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
