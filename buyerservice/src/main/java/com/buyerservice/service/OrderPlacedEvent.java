package com.buyerservice.service;


public class OrderPlacedEvent {
	
	
    private String orderId;
    
    public OrderPlacedEvent(String orderId) {
    	this.orderId=orderId;
    }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
    
    
}
