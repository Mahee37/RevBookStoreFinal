<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Update Profile" />
</jsp:include>

<div class="container mt-5">
    <div class="row">
        <!-- Retailer Info Display on the Left -->
        <div class="col-md-4 mb-4">
            <div class="p-3 bg-light rounded shadow-sm">
                <p><strong>Username:</strong> ${username}</p>
                <p><strong>Registered Email:</strong> ${email}</p>
            </div>
        </div>

        <!-- Profile Update Form on the Right -->
        <div class="col-md-8">
            <h1 class="mb-4">Profile information</h1>
            
            <form action="${pageContext.request.contextPath}/retailer/profile" method="post">
                
                <!-- Name Field -->
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" class="form-control" id="name" name="name" value="${retailer.name}" required>
                </div>
                
                <!-- Contact Email Field -->
                <div class="mb-3">
                    <label for="contactEmail" class="form-label">Contact Email</label>
                    <input type="email" class="form-control" id="contactEmail" name="contactEmail" value="${retailer.contactEMail}" required>
                </div>
                
                <!-- Address Field -->
				<div class="mb-3">
                    <label for="address" class="form-label">Address</label>
                    <textarea class="form-control" id="address" name="address" rows="3" required>${retailer.address}</textarea>
                </div>
                
                <!-- Phone Number Field -->
                <div class="mb-3">
                    <label for="phoneNumber" class="form-label">Phone Number</label>
                    <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" value="${retailer.phoneNumber}" required>
                </div>
                
                <!-- Submit Button -->
                <button type="submit" class="btn btn-primary">Update Profile</button>
                
            </form>
        </div>
    </div>
</div>

<!-- Toast Notification -->
<c:if test="${not empty message}">
    <div class="toast-container position-fixed bottom-0 end-0 p-3">
        <div id="liveToast" class="toast show align-items-center text-bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body">
                    ${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    </div>
</c:if>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
