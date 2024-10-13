<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Seller Pending Orders" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4">Seller Pending Orders</h1>

    <!-- If there are orders, display them in a table -->
    <c:if test="${not empty orders}">
        <h2 class="mt-5">Your Pending Orders</h2>
        <table class="table table-striped" id="ordersTable">
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Product Name</th>
                    <th>Total Price</th>
                    <th>Order Date</th>
                    <th>Payment Method</th>
                    <th>Shipping Address</th>
                    <th>City</th>
                    <th>Pincode</th>
                    <th>Phone Number</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr data-order-id="${order.orderId}">
                        <td><c:out value="${order.orderId}" /></td>
                        <td><c:out value="${order.productname}" /></td>
                        <td>$<c:out value="${order.totalPrice}" /></td>
                        <td><c:out value="${order.orderDate}" /></td>
                        <td><c:out value="${order.paymentMode}" /></td>
                        <td><c:out value="${order.shoppingAddress}" /></td>
                        <td><c:out value="${order.city}" /></td>
                        <td><c:out value="${order.pincode}" /></td>
                        <td><c:out value="${order.phoneNumber}" /></td>
                        <td>
                            <select name="status" class="form-select status-dropdown" required>
                                <option value="Pending" <c:if test="${order.status == 'Pending'}">selected</c:if>>Pending</option>
                                <option value="Shipping" <c:if test="${order.status == 'Shipping'}">selected</c:if>>Shipping</option>
                                <option value="Delivered" <c:if test="${order.status == 'Delivered'}">selected</c:if>>Delivered</option>
                            </select>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <!-- If there are no orders, display a message -->
    <c:if test="${empty orders}">
        <div class="alert alert-info" role="alert">You have no pending orders yet.</div>
    </c:if>

    <!-- Display any message sent from the controller -->
    <c:if test="${not empty message}">
        <div class="alert alert-warning" role="alert">${message}</div>
    </c:if>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

<!-- jQuery for AJAX -->
<<!-- jQuery for AJAX -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        // Event listener for the status dropdown change
        $('.status-dropdown').change(function() {
            var selectedStatus = $(this).val();
            var orderId = $(this).closest('tr').data('order-id');

            $.ajax({
                url: '${pageContext.request.contextPath}/updateStatus',  // Make sure this path is correct
                type: 'POST',
                data: {
                    orderId: orderId,
                    status: selectedStatus
                },
                success: function(response) {
                    // Update the status in the table cell to reflect the new status
                    $(this).closest('tr').find('td:nth-child(10)').text(selectedStatus);  // Updated to the correct column index for status
                    
                    // Display a success message using the response, if needed
                    alert('Successfully changed the order status to: ' + selectedStatus);
                }.bind(this),
                error: function(xhr, status, error) {
                    alert('Succesffuly Updated Status');
                }
            });
        });
    });
</script>

</body>
</html>
