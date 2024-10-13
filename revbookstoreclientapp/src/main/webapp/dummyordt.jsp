<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Dummy Order History Page" />
</jsp:include>

<div class="container mt-5">
    <h1 class="mb-4">Order History</h1>
    <form action="<%= request.getContextPath() %>/OrderDetails" method="get">
        <div class="mb-3">
            <label for="userId" class="form-label">Enter User ID:</label>
            <input type="number" id="userId" name="userId" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">View Order History</button>
    </form>
</div>
