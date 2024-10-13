<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Container styling */
        .container {
            margin-top: 2rem;
        }

        label {
            font-weight: 500;
            font-size: 15px;
        }

        /* Button styling */
        .btn-primary {
            background-color: black;
            color: white;
            border: none;
            transition: background-color 0.3s, color 0.3s;
        }

        .btn-primary:hover {
            background-color: grey;
            color: white;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="mb-4 text-center">Sign Up</h1>

        <!-- Display success or error messages -->
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            String successMessage = (String) request.getAttribute("successMessage");
            if (errorMessage != null && !errorMessage.isEmpty()) {
        %>
            <div class="alert alert-danger" role="alert">
                <%= errorMessage %>
            </div>
        <%
            } else if (successMessage != null && !successMessage.isEmpty()) {
        %>
            <div class="alert alert-success" role="alert">
                <%= successMessage %>
            </div>
        <%
            }
        %>

        <div class="row justify-content-center">
            <div class="col-md-6">
                <form action="<%= request.getContextPath() %>/register" method="post">
                    <div class="mb-3">
                        <label class="form-label">Register as</label>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="userType" id="buyer" value="buyer" checked>
                            <label class="form-check-label" for="buyer">Buyer</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="userType" id="seller" value="seller">
                            <label class="form-check-label" for="seller">Seller</label>
                        </div>
                    </div>

                    <!-- Name -->
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" id="username" name="name" required>
                    </div>

                    <!-- Email -->
                    <div class="mb-3">
                        <label for="email" class="form-label">Email address</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>

                    <!-- Password -->
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>

                    <!-- Phone Number -->
                    <div class="mb-3">
                        <label for="phone" class="form-label">Phone Number</label>
                        <input type="text" class="form-control" id="phone" name="phone_number" pattern="[0-9]{10}" title="Phone number should be a 10-digit number" required>
                    </div>

                    <!-- Address -->
                    <div class="mb-3">
                        <label for="address" class="form-label">Address</label>
                        <input type="text" class="form-control" id="address" name="address" required>
                    </div>

                    <!-- Pin Code -->
                    <div class="mb-3">
                        <label for="pincode" class="form-label">Pin Code</label>
                        <input type="text" class="form-control" id="pincode" name="pincode" required>
                    </div>

                    <!-- Submit Button -->
                    <button type="submit" class="btn btn-primary w-100">Sign Up</button>
                </form>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
