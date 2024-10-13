package com.buyerservice.dto;

import java.util.List;

import com.buyerservice.entity.Products;
import com.buyerservice.entity.Review;
import com.buyerservice.entity.User;

public class ProductDetailsViewModel {
    public Products getProduct() {
		return product;
	}
	public void setProduct(Products product) {
		this.product = product;
	}
	public User getSeller() {
		return seller;
	}
	public void setSeller(User seller) {
		this.seller = seller;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	private Products product;
    private User seller;
    private List<Review> reviews;

    // Getters and Setters
}
