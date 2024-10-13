package com.buyerservice.dto;

import java.util.List;

public class ProductDetailsResponse {
    private ProductDTO product;
    private UserDTO seller;
    private List<ReviewDTO> reviews;

    // Getters and Setters
    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
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
}
