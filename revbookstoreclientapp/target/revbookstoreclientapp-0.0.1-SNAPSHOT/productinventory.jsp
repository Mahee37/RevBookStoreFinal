<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Inventory</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />"> <!-- Link to your CSS file -->
</head>
<body>
    <h1>Product Inventory</h1>
    
    <c:if test="${not empty error}">
        <div class="error">
            <p><c:out value="${error}" /></p>
        </div>
    </c:if>

    <c:if test="${not empty product_list}">
       <table border="1">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Category</th>
            <th>Price</th>
            <th>Discount Price</th>
            <th>Quantity</th>
            <th>Image</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="product" items="${product_list}">
            <tr>
                <td><c:out value="${product.productId}" /></td>
                <td><c:out value="${product.productName}" /></td>
                <td><c:out value="${product.productDescription}" /></td>
                <td><c:out value="${product.productCategory}" /></td>
                <td><c:out value="${product.price}" /></td>
                <td><c:out value="${product.discountPrice}" /></td>
                <td><c:out value="${product.quantity}" /></td>
                <td>
                    <c:if test="${not empty product.imageUrl}">
                        <img src="<c:out value="${product.imageUrl}" />" alt="<c:out value="${product.productName}" />" width="100" height="100" />
                    </c:if>
                    <c:if test="${empty product.imageUrl}">
                        No Image Available
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
    </c:if>
</body>
</html>
