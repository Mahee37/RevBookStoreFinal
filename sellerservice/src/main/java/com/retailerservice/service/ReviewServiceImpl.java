package com.retailerservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailerservice.entity.Review;
import com.retailerservice.dao.CustomRepositry;
import com.retailerservice.dao.ReviewDAOInterface;
import com.retailerservice.dto.CustomProductProjection;
import com.retailerservice.dto.ReviewDTO;
import com.retailerservice.dto.ReviewProjection;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewServiceInterface {

	 @Autowired
	    private ReviewDAOInterface reviewdao;
	 
	 @Autowired
	 private CustomRepositry customdao;
	 
	 @Override
	    public List<ReviewProjection> getReviewsByProductId(List<Long> productIds) {
		 System.out.println("Fetching reviews for product IDs: " + productIds);
	        return reviewdao.findReviewsByProductIds(productIds);
	    }

	@Override
	public List<CustomProductProjection> getProductsBySellerId(Long sellerId) {
		// TODO Auto-generated method stub
		System.out.println("Fetching Products based Upon SellerID: " + sellerId);
        return customdao.findproductsBySellerId(sellerId);
    
	}

	@Override
	public List<ReviewProjection> getReviewsByProductIds(List<Long> productIds) {
		// TODO Auto-generated method stub
		return reviewdao.findByProductIdIn(productIds);
		
	}

	@Override
    public List<ReviewDTO> getReviewsByUserId(Long userId) {
        List<Review> reviews = reviewdao.findByUserId(userId);
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


}
