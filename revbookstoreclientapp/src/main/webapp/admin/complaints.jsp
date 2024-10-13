<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Complaints</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container mt-5">
        <h1 class="mb-4 text-center">Complaints</h1>
        
        <!-- Error Message if No Complaints -->
        <c:if test="${not empty error}">
            <div class="alert alert-warning text-center">
                ${error}
            </div>
        </c:if>

        <!-- Complaints Table -->
        <c:if test="${not empty complaints}">
            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>S.No</th>
                        <th>Complaint ID</th>
                        <th>Complaint Text</th>
                        <th>User Name</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="complaint" items="${complaints}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${complaint.complaintId}</td>
                            <td>${complaint.complaintText}</td>
                            <td>${complaint.userName}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
         <button class="btn btn-secondary mt-3" onclick="goBack()">Go Back to Previous Page</button>
    </div>

    <script>
        // JavaScript function to go back to the previous page
        function goBack() {
            window.history.back();
        }
    </script>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
