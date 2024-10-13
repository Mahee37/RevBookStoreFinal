<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp">
	<jsp:param name="pageTitle" value="Seller Orders" />
</jsp:include>

<div class="container mt-5">
	<h1 class="mb-4 text-center display-4" style="font-weight: 700; letter-spacing: 2px;">Orders</h1>

	<div class="table-responsive">
		<table class="table table-hover align-middle text-center" style="background-color: rgba(255, 255, 255, 0.8); border-radius: 15px;">
			<thead class="thead-dark" style="background-color: #343a40; color: white;">
				<tr>
					<th>Order ID</th>
					<th>Product ID</th>
				<!-- 	<th>Product Name</th> -->
					<th>Quantity</th>
					<th>Order Price</th>
					<th>Buyer ID</th>
					<th>Shipping Address</th>
					<th>Order Date</th>
					<th>Status</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="order" items="${order_list}">
					<tr style="transition: all 0.3s ease;">
						<td>${order.orderId}</td>
						<td>${order.productId}</td>
						<%-- <td>${order.name}</td> --%>
						<td>${order.productQuantity}</td>
						<td>&#8377;${order.totalPrice}</td>
						<td>${order.customerId}</td>
						<td>${order.shippingAddress}<br> - ${order.pincode}<br> Phone: ${order.phoneNumber}</td>
						<td>${order.orderDate}</td>
						<td>${order.status}</td>
						<td>
							<c:if test="${order.status ne 'Delivered' && order.status ne 'Cancelled'}">
								<form method="post" action="${pageContext.request.contextPath}/product/updateStatus">
									<input type="hidden" name="orderId" value="${order.orderId}">
									<input type="hidden" name="productId" value="${order.productId}">
									<select name="status" class="form-select form-select-sm mb-2" required style="border-radius: 10px;">
										<option value="Started" ${order.status == 'Started' ? 'selected' : ''}>Started</option>
										<option value="Opt for Delivery" ${order.status == 'Opt for Delivery' ? 'selected' : ''}>Opt for Delivery</option>
										<option value="Delivered" ${order.status == 'Delivered' ? 'selected' : ''}>Delivered</option>
										<option value="Cancelled" ${order.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
									</select>
									<button type="submit" class="btn btn-outline-primary btn-sm rounded-pill">Update</button>
								</form>
							</c:if>
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
