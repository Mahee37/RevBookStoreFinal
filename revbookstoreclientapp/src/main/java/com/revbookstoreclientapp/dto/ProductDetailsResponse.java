package com.revbookstoreclientapp.dto;
import java.util.List;

public class ProductDetailsResponse {
	

    private Products product;
    private User seller;
    private List<Review> reviews;

    // Getters and Setters
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
}
