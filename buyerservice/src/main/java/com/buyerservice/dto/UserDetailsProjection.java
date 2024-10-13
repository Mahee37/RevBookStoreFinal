package com.buyerservice.dto;

import java.util.List;

public interface UserDetailsProjection {

    Long getUserId();
    
    String getName();
    
    String getEmail();
    
    String getAddress();
    
    String getPincode();
    
    String getPhoneNumber();
    
    String getUserType();
    
    String getStatus();
    
  
    List<ProductDetailsResponse> getProduct();

   
    List<ReviewDetailsProjection> getReviews();
}
