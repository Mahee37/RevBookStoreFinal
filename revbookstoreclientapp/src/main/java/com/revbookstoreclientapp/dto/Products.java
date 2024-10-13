package com.revbookstoreclientapp.dto;

import java.util.List;

public class Products {
    private Long productId;
    private String productName;
    private String productDescription;
    private String productCategory;
    private Double price;
    private Double discountPrice;
    private Long quantity;
    private List<Review> reviews;  
    private String imageUrl;

    private User user;
    
//	public Object product;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getUserId() {
        return user != null ? user.getUserId() : null;
    }

    // Optional: Setter for userId (though it's generally not recommended)
    public void setUserId(Long userId) {
        if (user == null) {
            user = new User();
        }
        user.setUserId(userId);
    }
}
