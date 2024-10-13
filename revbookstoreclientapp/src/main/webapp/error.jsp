<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Error</title>
    <!-- Bootstrap for styling -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #ece9e6, #ffffff);
            font-family: 'Poppins', sans-serif;
        }

        .error-container {
            margin-top: 100px; /* Adjust margin to center vertically */
        }
    </style>
</head>
<body>
    <div class="container error-container">
        <div class="alert alert-danger">
            <h1>Error</h1>
            <p>${error}</p> <!-- This displays the error message passed from the GlobalExceptionHandler -->
        </div>
        
        <!-- Display detailed error message if available -->
        <c:if test="${not empty detailedError}">
            <div class="alert alert-danger" role="alert">${detailedError}</div>
        </c:if>

        <!-- Optional: Add a back button or link to redirect to the home page -->
     

        <!-- Add a button to go back to the previous page -->
        <button class="btn btn-secondary mt-3" onclick="goBack()">Go Back to Previous Page</button>
    </div>

    <script>
        // JavaScript function to go back to the previous page
        function goBack() {
            window.history.back();
        }
    </script>
</body>
</html>
