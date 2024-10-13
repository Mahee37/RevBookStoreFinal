<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Request Product Reviews</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2>Request Product Reviews</h2>

        <form action="ProductReviews" method="get">
            <button type="submit" class="btn btn-primary">View Product Reviews</button>
        </form>

        <!-- Display error if present -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger mt-3">
                ${error}
            </div>
        </c:if>

        <!-- Display message if present -->
        <c:if test="${not empty message}">
            <div class="alert alert-info mt-3">
                ${message}
            </div>
        </c:if>
    </div>
</body>
</html>
