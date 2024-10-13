<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Submit Review</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        form {
            max-width: 400px;
            margin: auto;
            border: 1px solid #ccc;
            padding: 20px;
            border-radius: 5px;
        }
        div {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        textarea {
            width: 100%;
            height: 100px;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
        .message {
            text-align: center;
            margin-bottom: 20px;
            color: green;
        }
        .error {
            text-align: center;
            margin-bottom: 20px;
            color: red;
        }
    </style>
</head>
<body>
    <h2>Submit a Review</h2>

    <%-- Display any success message --%>
    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>

    <%-- Display any error message --%>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/submitreview" method="post">
        <div>
            <label for="productId">Product ID:</label>
            <input type="text" id="productId" name="productId" required />
        </div>
        
        <div>
            <label for="reviewText">Review Text:</label>
            <textarea id="reviewText" name="reviewText" required></textarea>
        </div>
        
        <div>
            <label for="rating">Rating (1 to 5):</label>
            <select id="rating" name="rating" required>
                <option value="">Select Rating</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
        </div>
        
        <div>
            <button type="submit">Submit Review</button>
        </div>
    </form>
</body>
</html>
