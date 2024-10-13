package com.revbookstoreclientapp.dto;

public class Favorites {
	
	
	private long favoriteId;
	
	private String productName;
	private double price;
	private double discountPrice;
	private String productDescription;
	

	private User user;
	
	
	private Products product;

	public long getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(long favoriteId) {
		this.favoriteId = favoriteId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}

	

}