package com.revbookstoreclientapp.dto;

import java.sql.Timestamp;
import java.util.List;

public class Orders {
	
	
	private long orderId;
	
	private double totalPrice;
	private Timestamp orderDate;
	private String paymentMode;
	private String shoppingAddress;
	private String phoneNumber;
	private String status;
	private String pipncode;
	private String city;
	
	
	private User user;
	

	private Products product;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getShoppingAddress() {
		return shoppingAddress;
	}

	public void setShoppingAddress(String shoppingAddress) {
		this.shoppingAddress = shoppingAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPipncode() {
		return pipncode;
	}

	public void setPipncode(String pipncode) {
		this.pipncode = pipncode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}
	
	  private List<OrderDTO> orderList;

	    // Getter and Setter
	    public List<OrderDTO> getOrderList() {
	        return orderList;
	    }

	    public void setOrderList(List<OrderDTO> orderList) {
	        this.orderList = orderList;

	    }
}