package com.revbookstoreclientapp.dto;

public interface ShoppingCartProjection {
	
	long getCartId();
	long getProductId();
	long getQuantity();
	double getTotalPrice();
	String getProductDescription();
	String getProductName();
//	double getprice();

}