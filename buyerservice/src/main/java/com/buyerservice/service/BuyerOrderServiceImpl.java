package com.buyerservice.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buyerservice.dao.OrderRepository;
import com.buyerservice.dao.ProductRepository;
import com.buyerservice.dao.UserRepository;
import com.buyerservice.dto.OrderDTO;
import com.buyerservice.entity.Orders;
import com.buyerservice.entity.Products;
import com.buyerservice.entity.User;


@Service
@Transactional
public class BuyerOrderServiceImpl implements BuyerOrderService{
	
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
	
	@Autowired
	private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
	
	
	
	public List<OrderDTO> createOrder(OrderDTO orderDTO) {
	    // Fetch the User entity based on user information
	    User user = userRepo.findById(orderDTO.getUserId())
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    // Initialize a list to hold the created orders
	    List<OrderDTO> createdOrderDTOs = new ArrayList<>();

	    // Loop through the productIds in the OrderDTO
	    for (Long productId : orderDTO.getProductIds()) {
	        // Fetch the Product entity based on productId
	        Products product = productRepo.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	        // Map OrderDTO to Orders entity
	        Orders order = new Orders();
	        order.setTotalPrice(orderDTO.getTotalPrice());
	        order.setPaymentMode(orderDTO.getPaymentMode());
	        order.setShoppingAddress(orderDTO.getShoppingAddress());
	        order.setPhoneNumber(orderDTO.getPhoneNumber());
	        order.setPincode(orderDTO.getPincode());
	        order.setCity(orderDTO.getCity());
	        order.setStatus("Pending");
	        order.setUser(user); // Set the User entity
	        order.setProduct(product); // Set the Product entity
	        order.setOrderDate(Timestamp.from(Instant.now())); // Set the order date

	        // Save the order to the database
	        Orders savedOrder = orderRepo.save(order);

	        // Convert the saved order to OrderDTO and add to the list
	        OrderDTO savedOrderDTO = new OrderDTO();
	        savedOrderDTO.setOrderId(savedOrder.getOrderId());
	        savedOrderDTO.setTotalPrice(savedOrder.getTotalPrice());
	        savedOrderDTO.setPaymentMode(savedOrder.getPaymentMode());
	        savedOrderDTO.setShoppingAddress(savedOrder.getShoppingAddress());
	        savedOrderDTO.setPhoneNumber(savedOrder.getPhoneNumber());
	        savedOrderDTO.setPincode(savedOrder.getPincode());
	        savedOrderDTO.setCity(savedOrder.getCity());
	        savedOrderDTO.setStatus(savedOrder.getStatus());
	        savedOrderDTO.setUserId(savedOrder.getUser().getUserId());
	        savedOrderDTO.setProductId(savedOrder.getProduct().getProductId());
	        
	       
	        createdOrderDTOs.add(savedOrderDTO);
//	        kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(""+savedOrderDTO.getOrderId()));
	        //Kafka Implementation to send the producers messages directly to the Consumer
	       
	    }

	    return createdOrderDTOs; // Return the list of OrderDTOs
	}

}
