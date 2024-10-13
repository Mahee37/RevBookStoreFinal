package com.revbookstoreclientapp.dto;
public class FavoritesDTO {
	private long FavoriteId;
    private Long userId;
    private Long productId;
    private String productName;
    private String productDescription;
    private Double discountPrice;
    private Double totalPrice;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
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
	public Double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public long getFavoriteId() {
		return FavoriteId;
	}
	public void setFavoriteId(long favoriteId) {
		FavoriteId = favoriteId;
	}
    
    // Getters and setters
    // Constructors, if necessary
}
