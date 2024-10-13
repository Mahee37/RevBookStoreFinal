package com.buyerservice.service;

import java.util.List;

import com.buyerservice.dto.ReviewDTO;
import com.buyerservice.entity.Review;

public interface BuyerReviewService {

	List<Review> getReviewsByProductId(Long productId);

	int submitreview(ReviewDTO reviewDTO);

	List<ReviewDTO> getReviewsByUserId(Long userId);

	Boolean deleteReviewById(Long reviewId);

	List<ReviewDTO> getAllReviewsByUserId(Long userId);

	List<ReviewDTO> getAllReviewsByProductId(Long productId);

	

}
