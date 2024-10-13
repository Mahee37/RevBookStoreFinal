package com.buyerservice.dto;

import java.util.List;

public class ProductWithReviewsDTO {
    private ProductDTO product;
    private List<ReviewDTO> reviews;

    
    public ProductWithReviewsDTO(ProductDTO product, List<ReviewDTO> reviews) {
        this.product = product;
        this.reviews = reviews;
    }

    // Getters and Setters
    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
}
