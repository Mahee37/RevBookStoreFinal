package com.buyerservice.dto;
public interface ProductProjection {
    long getProductId();
    String getProductName();
    String getProductDescription();
    double getPrice();
    double getDiscountPrice();
    
}