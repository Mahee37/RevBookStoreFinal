<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Inventory Management" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4 text-center display-4" style="font-weight: 700; letter-spacing: 2px;">Inventory Management</h1>

    <!-- Add New Product Button -->
    <div class="d-flex justify-content-center mb-5">
        <a href="${pageContext.request.contextPath}/add.jsp" class="btn btn-dark btn-lg shadow-lg" style="border-radius: 30px;">Add New Product</a>
    </div>

    <!-- Inventory Table -->
    <div class="table-responsive" style="max-height: 600px; overflow-y: auto;">
        <table class="table table-hover align-middle text-center" style="background-color: rgba(255, 255, 255, 0.8); border-radius: 15px;">
            <thead class="thead-dark" style="background-color: #343a40; color: white;">
                <tr>
                    <th scope="col">Product ID</th>
                    <th scope="col">Image</th>
                    <th scope="col">Product Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Category</th>
                    <th scope="col">Price</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Discount Price</th>
                    <th scope="col">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${products}">
                    <tr style="transition: all 0.3s ease;">
                        <td>${product.productId}</td>
                        <td>
                            <img src="${product.imageUrl}" alt="${product.productName}" class="img-thumbnail" style="max-width: 100px; max-height: 100px; border-radius: 10px;">
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/retailer/inventory?productId=${product.productId}" class="text-decoration-none" style="color: #343a40; font-weight: 600;">${product.productName}</a>
                        </td>
                        <td>${product.productDescription}</td>
                        <td>${product.productCategory}</td>
                        <td>&#8377;${product.price}</td>
                        <td>${product.quantity}</td>
                        <td>&#8377;${product.discountPrice}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/editProductId?productId=${product.productId}" class="btn btn-outline-warning btn-sm rounded-pill">Edit</a>
							<form method="post" action="${pageContext.request.contextPath}/RemoveProduct" style="display:inline;">
							    <input type="hidden" name="productId" value="${product.productId}">
							    <button type="submit" class="btn btn-outline-danger btn-sm rounded-pill">Delete</button>
							</form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
