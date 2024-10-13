package com.buyerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buyerservice.dao.ProductRepository;
import com.buyerservice.dao.ShoppingCartRepository;
import com.buyerservice.dao.UserRepository;
import com.buyerservice.dto.CartProductDTO;
import com.buyerservice.dto.ShoppingCartAddDTO;
import com.buyerservice.entity.Products;
import com.buyerservice.entity.ShoppingCart;
import com.buyerservice.entity.User;

@Service
@Transactional
public class BuyerCartServiceImpl implements BuyerCartService {
	
	 @Autowired
	    private ShoppingCartRepository cartRepo;
	 
	 @Autowired
	 private ProductRepository  productRepo;
	 
	 @Autowired
	 private UserRepository userRepo;
	 

	 public CartProductDTO saveCart(CartProductDTO cartProductDTO) {
	        // Create a ShoppingCart entity from the DTO
	        ShoppingCart cart = new ShoppingCart();

	        // Retrieve product and user entities based on the IDs
	        Products product = productRepo.findProductById(cartProductDTO.getProductId()); // Fetch product details
	        if (product == null) {
	            throw new IllegalArgumentException("Product not found"); // Handle case where product doesn't exist
	        }

	        User user = userRepo.findUserById(cartProductDTO.getUserId()); // Fetch user details
	        if (user == null) {
	            throw new IllegalArgumentException("User not found"); // Handle case where user doesn't exist
	        }

	        // Set other properties on the ShoppingCart entity
	        cart.setProduct(product); // Set the Products entity
	        cart.setUser(user); // Set the User entity
	        cart.setQuantity(cartProductDTO.getQuantity());
	        cart.setTotalPrice(cartProductDTO.getTotalPrice());
	        cart.setProductName(cartProductDTO.getProductName());
	        cart.setProductDescription(cartProductDTO.getProductDescription());

	        // Save the ShoppingCart entity to the database
	        cartRepo.save(cart); // Save using the repository

	        return cartProductDTO; // Return DTO or any relevant response
	    }
}
