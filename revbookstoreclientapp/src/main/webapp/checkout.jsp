<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Checkout" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4">Checkout</h1>

    <form method="post" action="${pageContext.request.contextPath}/submitorder"> <!-- Updated action URL -->
        <div class="row">
            <!-- Shipping Details -->
            <div class="col-md-6">
                <h3>Shipping Details</h3>
                <div class="mb-3">
                    <label for="city" class="form-label">City</label>
                    <input type="text" class="form-control" id="city" name="city" required>
                </div>
                <div class="mb-3">
                    <label for="shoppingAddress" class="form-label">Shipping Address</label> <!-- Updated field name -->
                    <input type="text" class="form-control" id="shoppingAddress" name="shoppingAddress" required>
                </div>
                <div class="mb-3">
                    <label for="pincode" class="form-label">Pincode</label>
                    <input type="text" class="form-control" id="pincode" name="pincode" required>
                </div>
                <div class="mb-3">
                    <label for="phoneNumber" class="form-label">Phone Number</label>
                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" required>
                </div>
            </div>

            <!-- Payment Details -->
            <div class="col-md-6">
                <h3>Payment Details</h3>
                <div class="mb-3">
                    <label for="paymentMode" class="form-label">Payment Method</label> <!-- Updated field name -->
                    <select class="form-select" id="paymentMode" name="paymentMode" required>
                        <option value="cod">Cash on Delivery</option>
                        <!-- You can add more payment options here -->
                    </select>
                </div>
            </div>
        </div>

        <!-- Total Price -->
        <input type="hidden" name="totalPrice" value="${totalPrice}" /> <!-- Hidden input for total price -->
        
        <!-- Product Details for Multiple Products -->
        <c:forEach var="item" items="${cartItems}">
            <input type="hidden" name="productId" value="${item.productId}" /> <!-- Hidden input for each productId -->
        </c:forEach>
        
        <div class="text-end mt-4">
            <h4>Total Payable Amount: &#8377;${totalPrice}</h4>
            <button type="submit" class="btn btn-success">Place Order</button>
        </div>
    </form>
</div>
<form>
<script src="https://checkout.razorpay.com/v1/payment-button.js" data-payment_button_id="pl_P6C9prLtlRt6EX" async>
</script>
</form>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
