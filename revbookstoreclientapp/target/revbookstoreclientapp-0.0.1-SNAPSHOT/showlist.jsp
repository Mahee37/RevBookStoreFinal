<%@ page import="java.util.List" %>
<%@ page import="com.revbookstoreclientapp.dto.Products" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2>Product List</h2>

        <% 
        List<Products> products = (List<Products>) request.getAttribute("products");
        if (products != null && !products.isEmpty()) { 
        %>
            <table class="table">
                <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Discount Price</th>
                        <th>Quantity</th>
                        <th>Description</th>
                   
                    </tr>
                </thead>
                <tbody>
                    <% for (Products product : products) { %>
                        <tr>
                            <td><%= product.getProductId() %></td>
                            <td><%= product.getProductName() %></td>
                            <td><%= product.getProductCategory() %></td>
                            <td><%= product.getPrice() %></td>
                            <td><%= product.getDiscountPrice() %></td>
                            <td><%= product.getQuantity() %></td>
                            <td><%= product.getProductDescription() %></td>
                            <td>
                               <form action="/RevBookStore/deleteProduct" method="post">
						<%-- 	    <input type="hidden" name="id" value="${product.productId}"> --%>
							    <button type="submit" class="btn btn-danger">Delete</button>
								</form>

    							
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <div class="alert alert-info">No products found for the given seller.</div>
        <% } %>
    </div>
</body>
</html>
