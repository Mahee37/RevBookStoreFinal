package com.buyerservice.dto;

import java.util.List;

public interface ProductDetailsProjection {
	Long getProductId(); 

	String getProductName(); 

	String getProductDescription(); 

	String getImageUrl();
	
	String getProductCategory(); 

	double getPrice();
	
	double getDiscountPrice(); 

	long getQuantity(); 

	
	UserDetailsProjection getUser(); 

	
	List<ReviewDetailsProjection> getReviews(); 
}
