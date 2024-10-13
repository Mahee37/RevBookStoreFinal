<div class="container mt-5">
    <h1 class="mb-4">Add New Product</h1>

    <form method="post" action="${pageContext.request.contextPath}/retailer/inventory/add">
        <div class="mb-3">
            <label for="productName" class="form-label">Product Name</label>
            <input type="text" class="form-control" id="productName" name="name" required>
        </div>
        
        <div class="mb-3">
            <label for="productImage" class="form-label">Product image URL</label>
            <input type="text" class="form-control" id="productImage" name="imageUrl" required>
        </div>

        <div class="mb-3">
            <label for="productDescription" class="form-label">Description</label>
            <textarea class="form-control" id="productDescription" name="description" rows="4" required></textarea>
        </div>

        <div class="mb-3">
            <label for="productCategory" class="form-label">Category</label>
            <input type="text" class="form-control" id="productCategory" name="category" required>
        </div>

        <div class="mb-3">
            <label for="productPrice" class="form-label">Price</label>
            <input type="number" step="0.01" class="form-control" id="productPrice" name="price" min="0" required>
        </div>

        <div class="mb-3">
            <label for="productQuantity" class="form-label">Quantity</label>
            <input type="number" class="form-control" id="productQuantity" name="quantity" min="0" required>
        </div>

        <button type="submit" class="btn btn-primary">Add Product</button>
    </form>
</div>
