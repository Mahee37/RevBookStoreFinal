<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Products</title>
  
</head>
<body>

    <div class="container">
        <h2 class="mt-5">View Products</h2>

        <!-- Form to fetch products -->
        <form action="<c:url value='/viewProducts' />" method="get">
            <button type="submit" class="btn btn-primary">View Products</button>
        </form>

        <!-- Display message if any -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger mt-3">${error}</div>
        </c:if>
    </div>

   
</body>
</html>
