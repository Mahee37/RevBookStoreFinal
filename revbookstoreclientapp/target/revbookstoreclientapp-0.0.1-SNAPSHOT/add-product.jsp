<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib uri="jakarta.tags.core" prefix="c" %> --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Form</title>
</head>
<body>
    <h1>Product Form</h1>
    <form action="product" method="post">
        <table>
            <tr>
                <td><label for="name">Name:</label></td>
                <td><input type="text" id="name" name="name" required/></td>
            </tr>
            <tr>
                <td><label for="description">Description:</label></td>
                <td><textarea id="description" name="description" required></textarea></td>
            </tr>
            <tr>
                <td><label for="category">Category:</label></td>
                <td><input type="text" id="category" name="category" required/></td>
            </tr>
            <tr>
                <td><label for="price">Price:</label></td>
                <td><input type="number" id="price" name="price" step="0.01" required/></td>
            </tr>
            <tr>
                <td><label for="discount_price">Discount Price:</label></td>
                <td><input type="number" id="discount_price" name="discount_price" step="0.01"/></td>
            </tr>
            <!-- <tr>
                <td><label for="sellerId">Seller ID:</label></td>
                <td><input type="number" id="sellerId" name="sellerId" required/></td>
            </tr> -->
            <tr>
                <td><label for="imageUrl">Image URL:</label></td>
                <td><input type="text" id="imageUrl" name="imageUrl"/></td>
            </tr>
        </table>
        <button type="submit">Submit</button>
    </form>
    <form action="product/viewProducts" method="post">
    	<button type="submit">Inventory</button>
    </form>
   
</body>
</html>
