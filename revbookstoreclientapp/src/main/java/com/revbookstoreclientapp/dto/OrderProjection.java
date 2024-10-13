package com.revbookstoreclientapp.dto;

import java.sql.Timestamp;
import java.util.List;



public interface OrderProjection {
	
	long getOrderId();
	double getTotalPrice();
	Timestamp getOrderDate();
	String getPaymentMode();
	String getShoppingAddress();
	String getPhoneNumber();
	String getStatus();
	String getPipncode();
	String getCity();
	User getUser();
	Products getProduct();
	List<Long> getProductIds(); 
}