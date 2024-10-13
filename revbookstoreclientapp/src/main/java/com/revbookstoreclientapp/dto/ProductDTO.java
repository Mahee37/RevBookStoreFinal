package com.revbookstoreclientapp.dto;

import java.util.List;

public class ProductDTO {
    private Long productId; 
    private String productName; 
    private String productDescription; 
    private String imageUrl; 
    private String productCategory; 
    private double price; 
    private double discountPrice; 
    private long quantity; 
    private UserDTO seller;
    
    private List<ReviewDTO> reviews;
    private Long userId;

    // Default constructor
    public ProductDTO() {}

    // Getters and Setters
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
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getProductCategory() {
        return productCategory;
    }
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getDiscountPrice() {
        return discountPrice;
    }
    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }
    public long getQuantity() {
        return quantity;
    }
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
    public UserDTO getSeller() {
        return seller;
    }
    public void setSeller(UserDTO seller) {
        this.seller = seller;
    }
    public List<ReviewDTO> getReviews() {
        return reviews;
    }
    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
    
}
