package com.buyerservice.service;

import java.util.ArrayList;
import java.util.Collections;
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
import com.buyerservice.dto.ReviewDTO;
import com.buyerservice.dto.UserDTO;
import com.buyerservice.entity.Products;
import com.buyerservice.entity.Review;
import com.buyerservice.entity.User;

import jakarta.persistence.EntityNotFoundException;


@Service
@Transactional
public class BuyerReviewServiceImpl implements BuyerReviewService {
	
	@Autowired
	private BuyerReviewRepositry reviewrepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProductRepository productRepo;


	@Override
    public List<Review> getReviewsByProductId(Long productId) {
		 List<Review> reviews = reviewrepo.findByProductId(productId);
		    
		    
		    if (reviews.isEmpty()) {
		       
		        System.out.println("No reviews found for product ID: " + productId);
		    } else {
		        System.out.println("Found " + reviews.size() + " reviews for product ID: " + productId);
		    }
		    
		    return reviews; 
	}
	@Override
	public int submitreview(ReviewDTO reviewDTO) {
	    // Validate the incoming reviewDTO
	    if (reviewDTO == null) {
	        throw new IllegalArgumentException("ReviewDTO cannot be null");
	    }

	    // Create a new Review entity
	    Review review = new Review();
	    review.setReviewText(reviewDTO.getReviewText());
	    review.setRating(reviewDTO.getRating());

	    // Fetch the Product entity using the productId from the reviewDTO
	    if (reviewDTO.getProductId() == null) {
	        throw new IllegalArgumentException("Product ID cannot be null");
	    }
	    
	    Products product = productRepo.findById(reviewDTO.getProductId())
	        .orElseThrow(() -> new EntityNotFoundException("Product not found"));
	    review.setProduct(product); // Set the Product entity

	    // Fetch user using the userId from reviewDTO
	    if (reviewDTO.getUserId() == null) {
	        throw new IllegalArgumentException("User ID cannot be null");
	    }
	    
	    User user = userRepo.findById(reviewDTO.getUserId())
	        .orElseThrow(() -> new EntityNotFoundException("User not found"));
	    review.setUser(user); // Set the User entity

	    // Save the review entity to the repository
	    reviewrepo.save(review);

	    return 1;
	}
	@Override
    public List<ReviewDTO> getReviewsByUserId(Long userId) {
        List<Review> reviews = reviewrepo.findByUserId(userId);
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

	private ReviewDTO convertToDTO(Review review) {
	    ReviewDTO reviewDTO = new ReviewDTO();
	    reviewDTO.setReviewId(review.getReviewId());
	    reviewDTO.setReviewText(review.getReviewText());
	    reviewDTO.setRating(review.getRating());

	    // Populate userId and productId from the associated User and Product entities
	    if (review.getUser() != null) {
	        reviewDTO.setUserId(review.getUser().getUserId());
	        reviewDTO.setUserName(review.getUser().getName()); 
	    }
	    
	    if (review.getProduct() != null) {
	        reviewDTO.setProductId(review.getProduct().getProductId()); 
	        reviewDTO.setProductName(review.getProduct().getProductName()); 
	        reviewDTO.setProductDescription(review.getProduct().getProductDescription()); 
	    }

	    return reviewDTO;
	}
	
	
	public List<ReviewDTO> getAllReviewsByProductId(Long productId) {
        List<Review> reviews = reviewrepo.findByProductId(productId); // Implement this query in the repository
        return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ReviewDTO convertToReviewDTo(Review review) {
        ReviewDTO dto = new ReviewDTO();
        
        if (review.getUser() != null) {
        	
	        dto.setUserId(review.getUser().getUserId());
	        dto.setUserName(review.getUser().getName()); 
	    }
        if (review.getProduct() != null) {
	        dto.setProductId(review.getProduct().getProductId()); 
	        dto.setProductName(review.getProduct().getProductName()); 
	        dto.setProductDescription(review.getProduct().getProductDescription()); 
	    }

        dto.setReviewText(review.getReviewText());
        dto.setRating(review.getRating());
        
        return dto;
    }
    
    
	@Override
	public Boolean deleteReviewById(Long reviewId) {
		// TODO Auto-generated method stub
		
		
		Optional<Review> review = reviewrepo.findReviewByProductId(reviewId);
		if (review.isPresent()) {
		
			reviewrepo.delete(review.get());
			return true;
		} else {
			return false;
		}

	}
	
	
	 @Override
	    public List<ReviewDTO> getAllReviewsByUserId(Long userId) {
	        // Fetch reviews for the specified user from the repository
	        List<Review> reviews = reviewrepo.findByUserId(userId);
	        
	        
	        if (reviews.isEmpty()) {
	            return Collections.emptyList(); // Return an empty list if no reviews found
	        }

	        // Convert the list of Review entities to ReviewDTOs
	        return reviews.stream()
	                      .map(this::convertToDTO) // Convert each Review entity to ReviewDTO
	                      .collect(Collectors.toList());
	    }
	    	private ReviewDTO convertToDTO1(Review review) {
	    	    ReviewDTO reviewDTO = new ReviewDTO();
	    	    reviewDTO.setReviewId(review.getReviewId());
	    	    reviewDTO.setReviewText(review.getReviewText());
	    	    reviewDTO.setRating(review.getRating());

	    	    // Populate userId and productId from the associated User and Product entities
	    	    if (review.getUser() != null) {
	    	        reviewDTO.setUserId(review.getUser().getUserId());
	    	        reviewDTO.setUserName(review.getUser().getName()); 
	    	    }
	    	    
	    	    if (review.getProduct() != null) {
	    	        reviewDTO.setProductId(review.getProduct().getProductId()); 
	    	        reviewDTO.setProductName(review.getProduct().getProductName()); 
	    	        reviewDTO.setProductDescription(review.getProduct().getProductDescription()); 
	    	    }

	    	    return reviewDTO;
	    	
	    }
			
}
	
	
