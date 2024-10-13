<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Sellers</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container mt-5">
        <h1 class="mb-4 text-center">Manage Sellers</h1>

        <!-- Display error message if available -->
      <c:if test="${not empty error}">
            <div class="alert alert-warning text-center">
                ${error}
            </div>
        </c:if>
        
        <!-- Check if seller_list is empty and display the message -->
        <c:if test="${empty seller_list}">
            <div class="alert alert-warning text-center" role="alert">
                <strong>&#x274C; No sellers found.</strong> <!-- Cross mark with message -->
            </div>
        </c:if>
        
        <!-- Sellers Table -->
        <c:if test="${not empty seller_list}">
            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>S.No</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>User Type</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${seller_list}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${user.name}</td>
                            <td>${user.email}</td>
                            <td>${user.userType}</td>
                            <td>${user.status}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${user.status eq 'active'}">
                                        <form method="post" action="${pageContext.request.contextPath}/admin/blockUser" style="display:inline;">
                                            <input type="hidden" name="userId" value="${user.userId}">
                                            <input type="hidden" name="status" value="active">
                                            <input type="hidden" name="userType" value="${user.userType}">
                                            <button type="submit" class="btn btn-warning btn-sm">Block</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form method="post" action="${pageContext.request.contextPath}/admin/activateUser" style="display:inline;">
                                            <input type="hidden" name="userId" value="${user.userId}">
                                            <input type="hidden" name="status" value="blocked">
                                            <input type="hidden" name="userType" value="${user.userType}">
                                            <button type="submit" class="btn btn-success btn-sm">Activate</button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
