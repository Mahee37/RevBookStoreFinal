<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Submit Review</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f4f4f4;
}

.container {
	width: 50%;
	margin: 50px auto;
	padding: 20px;
	background-color: white;
	border-radius: 8px;
	box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

h2 {
	text-align: center;
}

.form-group {
	margin-bottom: 15px;
}

label {
	display: block;
	font-weight: bold;
	margin-bottom: 5px;
}

input[type="text"], textarea {
	width: 100%;
	padding: 10px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

input[type="submit"] {
	background-color: #4CAF50;
	color: white;
	padding: 10px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

input[type="submit"]:hover {
	background-color: #45a049;
}
</style>
</head>
<body>
	<div class="container">
		<h2>Submit Your Review</h2>

		<form action="${pageContext.request.contextPath}/buyer/submitreview"
			method="post">
			<!-- Product ID -->
			<div class="form-group">
				<label for="productId">Product ID:</label> <input type="text"
					id="productId" name="productId" required />
			</div>

			<!-- Review Text -->
			<div class="form-group">
				<label for="reviewText">Review:</label>
				<textarea id="reviewText" name="reviewText" rows="5" required></textarea>
			</div>

			<!-- Rating -->
			<div class="mb-3">
				<label class="form-label">Rating</label>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" id="rating1"
						name="rating" value="1" required> <label
						class="form-check-label" for="rating1">1 - Poor</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" id="rating2"
						name="rating" value="2"> <label class="form-check-label"
						for="rating2">2 - Fair</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" id="rating3"
						name="rating" value="3"> <label class="form-check-label"
						for="rating3">3 - Good</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" id="rating4"
						name="rating" value="4"> <label class="form-check-label"
						for="rating4">4 - Very Good</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" id="rating5"
						name="rating" value="5"> <label class="form-check-label"
						for="rating5">5 - Excellent</label>
				</div>
			</div>

			<!-- Submit Button -->
			<div class="form-group">
				<input type="submit" value="Submit Review" />
			</div>
		</form>
	</div>
</body>
</html>