package com.buyerservice.service;

import java.util.List;

import com.buyerservice.dto.OrderDTO;
import com.buyerservice.entity.Orders;

public interface BuyerOrderService {

	List<OrderDTO> createOrder(OrderDTO orderDTO);

}
