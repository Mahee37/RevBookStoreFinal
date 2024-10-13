<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp">
    <jsp:param name="favorites" value="Favorites" />
</jsp:include>

<%@ page import="com.revbookstoreclientapp.dto.FavoritesDTO" %>
<%@ page import="java.util.List" %>

<% 
    // Fetching messages and product lists from the request or session
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
    FavoritesDTO favoriteProduct = (FavoritesDTO) request.getAttribute("favoriteProduct");
    List<FavoritesDTO> fav = (List<FavoritesDTO>) request.getAttribute("fav");
%>

<!-- Displaying Messages -->
<div class="container mt-5">
    <% if (message != null) { %>
        <div class="alert alert-success" role="alert"><%= message %></div>
    <% } %>
    <% if (error != null) { %>
        <div class="alert alert-danger" role="alert"><%= error %></div>
    <% } %>

    <% if (favoriteProduct != null) { %>
        <h2>Favorite Product Details:</h2>
        <p><strong>Product Name:</strong> <%= favoriteProduct.getProductName() %></p>
        <p><strong>Description:</strong> <%= favoriteProduct.getProductDescription() %></p>
        <p><strong>Discount Price:</strong> &#8377;<%= favoriteProduct.getDiscountPrice() %></p>
        <p><strong>Total Price:</strong> &#8377;<%= favoriteProduct.getTotalPrice() %></p>
    <% } %>
</div>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4">Your Favorite Products</h1>

    <% if (fav != null && !fav.isEmpty()) { %>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th scope="col">Product Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Discount Price</th>
                    <th scope="col">Total Price</th>
                    <th scope="col">Action</th>
                </tr>
            </thead>
            <tbody>
                <% for (FavoritesDTO product : fav) { %>
                    <tr>
                        <td><%= product.getProductName() %></td>
                        <td class="product-description"><%= product.getProductDescription() %></td>
                        <td>&#8377;<%= product.getDiscountPrice() %></td>
                        <td>&#8377;<%= product.getTotalPrice() %></td>
                        <td>
                            <form action="<%= request.getContextPath() %>/removeFromFavorite" method="post" onsubmit="return confirm('Are you sure you want to remove this product from favorites?');">
                                <!-- Pass favoriteId for backend to identify the favorite item -->
                                <input type="hidden" name="favoriteId" value="<%= product.getFavoriteId() %>">
                                <button type="submit" class="btn btn-danger">Remove</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } else { %>
        <div class="alert alert-warning text-center" role="alert">No favorite products</div>
    <% } %>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
