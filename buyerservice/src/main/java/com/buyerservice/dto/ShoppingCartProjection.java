package com.buyerservice.dto;

public interface ShoppingCartProjection {
	
	long getCartId();
	long getQuantity();
	double getTotalPrice();
	String getProductDescription();
	String getProductName();
//	double getprice();
	long getProductId();

}