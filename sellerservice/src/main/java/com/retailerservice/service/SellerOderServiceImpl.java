package com.retailerservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailerservice.entity.Orders;
import com.retailerservice.dao.OrderRepository;
import com.retailerservice.dto.OrderDTO;
import com.retailerservice.dto.OrderProjection;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SellerOderServiceImpl implements SellerOrderServiceInterface{
	
	@Autowired
	private OrderRepository orderRepo;

	@Override
	public boolean updateStatus(long orderId, String status) {
		orderRepo.updateStatus(orderId,status);
		return false;
	}
	
	
	@Override
	public List<OrderDTO> viewOrders(long userId) {
	    List<Orders> allOrders = orderRepo.findAll();
	    List<OrderDTO> userOrdersList = new ArrayList<>();

	    for (Orders order : allOrders) {
	        if (order.getUser().getUserId() == userId) {
	            // Create OrderDTO
	            OrderDTO orderDTO = new OrderDTO();
	            orderDTO.setOrderId(order.getOrderId());
	            orderDTO.setTotalPrice(order.getTotalPrice());
	            orderDTO.setOrderDate(order.getOrderDate());
	            orderDTO.setPaymentMode(order.getPaymentMode());
	            orderDTO.setShoppingAddress(order.getShoppingAddress());
	            orderDTO.setCity(order.getCity());
	            orderDTO.setPincode(order.getPincode());
	            orderDTO.setPhoneNumber(order.getPhoneNumber());
	            orderDTO.setStatus(order.getStatus());
	           
	            
	            // Retrieve the product associated with the order
	            Long productId = order.getProduct() != null ? order.getProduct().getProductId() : 0; // Assuming product can be null
	            orderDTO.setProductId(productId);
	            String productname = order.getProduct() != null ? order.getProduct().getProductName() : "null";
	            orderDTO.setProductname(productname);

	            userOrdersList.add(orderDTO);
	        }
	    }

	    return userOrdersList;
	}


	@Override
	public void deleteorderByProductId(Long productId) {
		// TODO Auto-generated method stub
		 orderRepo.deleteorderByProductId(productId);
		
	}


}
