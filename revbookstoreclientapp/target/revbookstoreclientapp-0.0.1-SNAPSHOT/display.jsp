<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Product Deletion Response</title>
</head>
<body>
    <h1>Product Deletion Response</h1>

    <!-- Display the message about the deletion operation -->
    <c:choose>
        <c:when test="${not empty errorMessage}">
            <p style="color: red;">${errorMessage}</p>
        </c:when>
        <c:otherwise>
            <p style="color: green;">Product has been successfully deleted.</p>
        </c:otherwise>
    </c:choose>

    <!-- Display the updated product list -->
    <c:if test="${not empty products}">
        <h2>Updated Product List</h2>
        <table border="1">
            <thead>
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Product Description</th>
                    <th>Price</th>
                    <th>Discount Price</th>
                    <th>Quantity</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.productId}</td>
                        <td>${product.productName}</td>
                        <td>${product.productDescription}</td>
                        <td>${product.price}</td>
                        <td>${product.discountPrice}</td>
                        <td>${product.quantity}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <!-- Provide a link to go back to the product list -->
    <a href="/products">Back to Product List</a>
</body>
</html>
