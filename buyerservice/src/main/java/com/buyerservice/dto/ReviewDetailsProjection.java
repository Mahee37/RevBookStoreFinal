package com.buyerservice.dto;
public interface ReviewDetailsProjection {

    Long getReviewId();
    
    String getReviewText();
    
    int getRating();
    
    // User who wrote the review
    UserDetailsProjection getUser();
}
