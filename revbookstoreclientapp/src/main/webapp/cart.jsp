<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.revbookstoreclientapp.dto.CartProductDTO"%>
<jsp:include page="header.jsp">
	<jsp:param name="pageTitle" value="Your Cart" />
</jsp:include>

<%
// Assuming 'cartItems' is a List of CartProductDTO objects passed from the controller
List<CartProductDTO> cartItems = (List<CartProductDTO>) request.getAttribute("cartItems");
double totalPrice = 0.0;

// Calculate total price
if (cartItems != null) {
	for (CartProductDTO item : cartItems) {
		totalPrice += item.getTotalPrice(); // Summing up the total price from each cart item
	}
}
%>

<div class="container mt-5">
	<h1 class="mb-4">Shopping Cart</h1>

	<%
	if (cartItems != null && !cartItems.isEmpty()) {
	%>
	<table class="table table-bordered rounded">
		<thead>
			<tr>
				<th scope="col">CartId</th>
				<th scope="col">ProductId</th>
				<th scope="col">Product Name</th>
				<th scope="col">Description</th>
				<th scope="col">Quantity</th>
				<th scope="col">Total Price</th>
				<th scope="col">Actions</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (CartProductDTO item : cartItems) {
			%>
			<tr>
				<td><%=item.getCartId()%></td>
				<td><%=item.getProductId()%></td>
				<td><%=item.getProductName()%></td>
				<td><%=item.getProductDescription()%></td>
				<td>
					<form action="<%=request.getContextPath()%>/updateQuantity"
						method="post" class="d-flex align-items-center">
						<input type="hidden" name="cartId" value="<%=item.getCartId()%>">
						<input type="number" class="form-control rounded me-2"
							name="quantity" value="<%=item.getQuantity()%>" min="1"
							style="width: 70px;">
						<button type="submit" class="btn btn-primary rounded">Update</button>
					</form>
				</td>
				<td><%=item.getTotalPrice()%> ₹</td>
				<td>
					<form
						action="<%=request.getContextPath()%>/removeProductFromCart"
						method="post">
						<input type="hidden" name="cartId" value="<%=item.getCartId()%>">
						<button type="submit" class="btn btn-danger rounded">Remove</button>
					</form>
				</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>

	<div class="text-end">
		<h4>
			Total Price:
			<%=totalPrice%>
			₹
		</h4>
		<!-- Displaying total price -->
		<form action="<%=request.getContextPath()%>/checkout" method="post">
        <input type="hidden" name="totalPrice" value="<%=totalPrice%>"> <!-- Pass the total price if needed -->
        <button type="submit" class="btn btn-success mt-3 rounded">Proceed to Checkout</button>
    	</form>
	</div>
	<%
	} else {
	%>
	<div class="alert alert-warning text-center">Your cart is empty.</div>
	<a href="<%=request.getContextPath()%>/BuyerInventory"
		class="btn btn-primary rounded">Continue Shopping</a>
	<%
	}
	%>
</div>
