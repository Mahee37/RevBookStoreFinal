package com.buyerservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buyerservice.dao.BuyerReviewRepositry;
import com.buyerservice.dao.ProductRepository;
import com.buyerservice.dao.UserRepository;
import com.buyerservice.dto.ProductDTO;
import com.buyerservice.dto.ProductWithReviews;
import com.buyerservice.dto.ProductWithReviewsDTO;
import com.buyerservice.dto.ReviewDTO;
import com.buyerservice.dto.UserDTO;
import com.buyerservice.entity.Products;
import com.buyerservice.entity.Review;
import com.buyerservice.entity.User;

import jakarta.persistence.EntityNotFoundException;


@Service
@Transactional
public class BuyerProductServiceImpl  implements BuyerProductService{
	
	@Autowired
	private ProductRepository productRepo;
	
	
	@Autowired
	private BuyerReviewRepositry reviewRepo;

	@Override
	public Products findProductById(Long productId) {
		// TODO Auto-generated method stub
		 return productRepo.findById(productId).orElse(null);
		
	}

	@Override
	public void updateProductQuantity(Long productId, long quantity) {
		// TODO Auto-generated method stub
		 Products product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

		    // Update the quantity
		    product.setQuantity(quantity);
		    productRepo.save(product);
		
	}

	@Override
	public Products getProductById(Long productId) {
		// TODO Auto-generated method stub
		
		 Optional<Products> product = productRepo.findById(productId);
	        return product.orElse(null);
	
	}

	public ProductWithReviews getProductWithReviews(Long productId, Long userId) {
	    // Fetch product and reviews
	    Products product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
	    
	    List<Review> reviews = reviewRepo.findByProduct(product);

	    // Convert reviews to ReviewDTO
	    List<ReviewDTO> reviewDTOs = reviews.stream()
	        .map(review -> {
	            ReviewDTO dto = new ReviewDTO();
	            dto.setReviewId(review.getReviewId());
	            dto.setReviewText(review.getReviewText());
	            dto.setRating(review.getRating());

	            // Access userId from User entity
	            User user = review.getUser();
	            if (user != null) {
	                dto.setUserId(user.getUserId()); // Set userId from Review entity
	            }

	            // Access productId from Products entity
	            Products productEntity = review.getProduct();
	            if (productEntity != null) {
	                dto.setProductId(productEntity.getProductId()); // Set productId if available in Review entity
	            }

	            // Create UserDTO as needed
	            UserDTO userDTO = new UserDTO();
	            userDTO.setUserId(user.getUserId());
	            // Set additional user details if available
	            dto.setUser(userDTO);

	            return dto;
	        }).collect(Collectors.toList());

	    // Create ProductWithReviews object and set its properties
	    ProductWithReviews productWithReviews = new ProductWithReviews();
	    productWithReviews.setProductId(product.getProductId());
	    productWithReviews.setProductName(product.getProductName());
	    productWithReviews.setProductDescription(product.getProductDescription());
	    productWithReviews.setProductCategory(product.getProductCategory());
	    productWithReviews.setReviews(reviewDTOs); // Set the list of ReviewDTOs

	    return productWithReviews;
	}
}