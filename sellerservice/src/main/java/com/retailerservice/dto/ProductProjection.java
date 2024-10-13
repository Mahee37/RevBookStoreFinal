package com.retailerservice.dto;

public interface ProductProjection {
    
    Long getProductId();
    String getProductName();
    String getProductDescription();
    String getImageUrl();
    String getProductCategory();
    double getPrice();
    double getDiscountPrice();
    long getQuantity();
    
}