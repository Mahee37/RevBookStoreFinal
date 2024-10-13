package com.retailerservice.dto;

import java.sql.Timestamp;
import java.util.List;

import com.retailerservice.entity.Products;
import com.retailerservice.entity.User;

public class OrderDTO {
	 private long orderId;
	    private double totalPrice;
	    private Timestamp orderDate;
	    private String paymentMode;
	    private String shoppingAddress;
	    private String phoneNumber;
	    private String status;
	    private String pincode; // Corrected spelling from "pipncode" to "pincode"
	    private String city;
	    private User user;
	    private Products product;
	    private Long UserId;
	    private Long ProductId;
	    private String Productname;
	    private List<Long> productIds;
	    private List<OrderDTO> orderList; // List to hold multiple OrderDTOs

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

	    public String getPincode() {
	        return pincode;
	    }

	    public void setPincode(String pincode) {
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

		public Long getProductId() {
			return ProductId;
		}

		public void setProductId(Long productId) {
			ProductId = productId;
		}

		public Long getUserId() {
			return UserId;
		}

		public void setUserId(Long userId) {
			UserId = userId;
		}

		public List<Long> getProductIds() {
			return productIds;
		}

		public void setProductIds(List<Long> productIds) {
			this.productIds = productIds;
		}

		public String getProductname() {
			return Productname;
		}

		public void setProductname(String productname) {
			Productname = productname;
		}

}
