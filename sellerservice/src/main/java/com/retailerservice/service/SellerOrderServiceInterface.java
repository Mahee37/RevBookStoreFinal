package com.retailerservice.service;

import java.util.List;

import com.retailerservice.dto.OrderDTO;
import com.retailerservice.dto.OrderProjection;

public interface SellerOrderServiceInterface {

	boolean updateStatus(long orderId, String status);

	

	List<OrderDTO> viewOrders(long userId);



	void deleteorderByProductId(Long productId);

}
