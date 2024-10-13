package com.buyerservice.dto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
	    private Long orderId;
	    private Double totalPrice;
	    private Timestamp orderDate; // Use LocalDate if you want just the date; otherwise use LocalDateTime
	    private String paymentMode; // Updated from paymentMethod for consistency
	    private String shoppingAddress;
	    private String city;
	    private String pincode;
	    private String phoneNumber;
	    private String status;
	    private Long UserId;
	    private Long ProductId;
	    private List<Long> productIds;
	    private String Productname;
	    // Getters and Setters
	    public Long getOrderId() {
	        return orderId;
	    }

	    public void setOrderId(Long orderId) {
	        this.orderId = orderId;
	    }

	    public Double getTotalPrice() {
	        return totalPrice;
	    }

	    public void setTotalPrice(Double totalPrice) {
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

	    public String getCity() {
	        return city;
	    }

	    public void setCity(String city) {
	        this.city = city;
	    }

	    public String getPincode() {
	        return pincode;
	    }

	    public void setPincode(String pincode) {
	        this.pincode = pincode;
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


