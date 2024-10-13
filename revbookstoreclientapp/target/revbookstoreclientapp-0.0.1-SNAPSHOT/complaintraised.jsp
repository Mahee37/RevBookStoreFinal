<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Complaint Raised" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5 text-center">
    <h1>Complaint Raised Successfully!</h1>
    <div class="mt-4">
        <!-- Circular icon with blue tick -->
        <div class="icon-container">
            <div class="tick-icon">
                &#10003; <!-- This is a checkmark symbol -->
            </div>
        </div>
        <h4 class="mt-3">${message}</h4> <!-- Displays the message from the model -->
    </div>
</div>

<!-- Custom CSS for icon -->
<style>
    .icon-container {
        width: 100px; /* Adjust size as needed */
        height: 100px; /* Adjust size as needed */
        border-radius: 50%;
        background-color: #e0f7fa; /* Light blue background */
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 0 auto; /* Center the icon */
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Optional shadow */
    }

    .tick-icon {
        font-size: 48px; /* Size of the tick mark */
        color: #00796b; /* Dark teal color for the tick mark */
    }
</style>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
