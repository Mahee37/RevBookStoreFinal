package com.retailerservice.dto;

import java.sql.Timestamp;
import java.util.List;

import com.retailerservice.entity.Products;
import com.retailerservice.entity.User;




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