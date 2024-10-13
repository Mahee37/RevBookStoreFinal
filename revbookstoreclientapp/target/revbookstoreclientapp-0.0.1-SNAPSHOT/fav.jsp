<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Favorite Products</title>
    <link rel="stylesheet" href="<c:url value='/css/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
    <div class="container">
        <h1>Your Favorite Products</h1>

        <!-- Display success or error message -->
        <c:if test="${not empty message}">
            <div class="alert alert-success">
                ${message}
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                ${error}
            </div>
        </c:if>

        <!-- Favorite Products Table -->
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Product Name</th>
                    <th>Description</th>
                    <th>Discount Price</th>
                    <th>Total Price</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty fav}">
                    <c:forEach var="favorite" items="${fav}">
                        <tr>
                            <td>${favorite.productName}</td>
                            <td>${favorite.productDescription}</td>
                            <td>${favorite.discountPrice}</td>
                            <td>${favorite.totalPrice}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty fav}">
                    <tr>
                        <td colspan="4">You have no favorite products.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <a href="<c:url value='/products'/>" class="btn btn-primary">Back to Products</a>
    </div>

    <script src="<c:url value='/js/bootstrap.bundle.min.js'/>"></script>
</body>
</html>
