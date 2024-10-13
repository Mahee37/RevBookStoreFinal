package com.buyerservice.entity;

import java.sql.Timestamp;
import java.util.List;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class Orders {

		
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long orderId;

	    private double totalPrice;
	    private Timestamp orderDate;
	    private String paymentMode;
	    private String shoppingAddress;
	    private String phoneNumber;
	    private String status;
	    private String pincode; 
	    private String city;
	    
	    
	    @ManyToOne
	    private User user;

	    @ManyToOne
	    private Products product;
	    
	    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    private List<OrderItem> orderItems; // OrderItem is another entity that maps individual items
		
	
	    

	    // Getters and Setters
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

	    public String getPincode() { // Corrected getter
	        return pincode;
	    }

	    public void setPincode(String pincode) { // Corrected setter
	        this.pincode = pincode;
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
}
	 
