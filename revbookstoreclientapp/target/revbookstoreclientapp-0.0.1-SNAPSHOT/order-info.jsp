<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Order Details" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4">Order Details</h1>

    <c:choose>
        <c:when test="${not empty order}">
            <div class="row">
                <!-- Order Details Column -->
                <div class="col-md-6">
                    <div class="card mb-4">
                        <div class="card-body">
                            <h2 class="card-title">Order ID: ${order.orderId}</h2>
                            <p class="card-text"><strong>Total Price:</strong> &#8377;${order.totalPrice}</p>
                            <p class="card-text"><strong>Order Date:</strong> ${order.orderDate}</p>
                            <p class="card-text"><strong>Payment Method:</strong> ${order.paymentMethod}</p>
                            <p class="card-text"><strong>Shipping Address:</strong> ${order.shippingAddress}</p>
                            <p class="card-text"><strong>City:</strong> ${order.city}</p>
                            <p class="card-text"><strong>Pincode:</strong> ${order.pincode}</p>
                            <p class="card-text"><strong>Phone Number:</strong> ${order.phoneNumber}</p>
                            <p class="card-text"><strong>Status:</strong> <span class="badge ${order.status == 'Completed' ? 'bg-success' : 'bg-warning'}">${order.status}</span></p>
                        </div>
                    </div>
                </div>

                <!-- Ordered Products Column -->
                <div class="col-md-6">
                    <h3 class="mb-4">Ordered Products</h3>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Product Name</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Total</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${orderedProducts}">
                                <tr>
                                    <td><c:out value="${product.name}" /></td>
                                    <td><c:out value="${product.quantity}" /></td>
                                    <td>&#8377;<c:out value="${product.price}" /></td>
                                    <td>&#8377;<c:out value="${product.quantity * product.price}" /></td>
                                    <td><c:out value="${product.status}"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-danger" role="alert">
                Order not found.
            </div>
        </c:otherwise>
    </c:choose>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
