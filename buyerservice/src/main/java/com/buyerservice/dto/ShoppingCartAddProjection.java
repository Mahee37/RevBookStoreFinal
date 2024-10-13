package com.buyerservice.dto;
public interface ShoppingCartAddProjection { 
    Long getCartId();
    Long getProductId();
    String getProductName();
    String getProductDescription();
    double getTotalPrice();
    int getQuantity();
    Long getUserId();
    String getUserName();
}
