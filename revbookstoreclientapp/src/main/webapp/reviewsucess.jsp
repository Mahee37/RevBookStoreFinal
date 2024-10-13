<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="${error == null ? 'Review Submitted' : 'Review Submission Failed'}" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5 text-center">
    <div class="review-confirmation mt-4">
        <!-- Conditional content based on error message -->
        <c:choose>
            <c:when test="${error == null}">
                <!-- Animated green circle with checkmark GIF for success -->
                <div class="checkmark-container">
                    <img src="${pageContext.request.contextPath}/images/orderplaced.gif" alt="Review Submitted Successfully" class="checkmark-image" />
                </div>

                <!-- Success message -->
                <h1 class="mt-4 text-success">Review Submitted Successfully!</h1>
                <p class="lead">Thank you for your review. Your feedback has been successfully submitted.</p>

                <div class="mt-4">
                    <a href="${pageContext.request.contextPath}/BuyerInventory" class="btn btn-primary">Continue Shopping</a>
                </div>
            </c:when>
            <c:otherwise>
                <!-- Animated red circle with crossmark GIF for failure -->
                <div class="checkmark-container">
                    <img src="${pageContext.request.contextPath}/images/reviewfailed.gif" alt="Review Submission Failed" class="checkmark-image" />
                </div>

                <!-- Error message -->
                <h1 class="mt-4 text-danger">Review Submission Failed!</h1>
                <p class="lead">We're sorry, but your review could not be submitted at this time.</p>

                <div class="mt-4">
                    <a href="${pageContext.request.contextPath}/BuyerInventory" class="btn btn-danger">Try Again</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<!-- CSS Styling -->
<style>
    .checkmark-container {
        position: relative;
        display: inline-block;
        margin-top: 20px;
    }

    .checkmark-image {
        width: 150px; /* Adjust width as needed */
        height: 150px; /* Adjust height as needed */
    }

    .text-danger {
        color: red;
    }

    .btn-danger {
        background-color: red;
        border-color: red;
    }
</style>
