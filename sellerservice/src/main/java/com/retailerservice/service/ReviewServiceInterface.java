package com.retailerservice.service;

import java.util.List;

import com.retailerservice.dto.CustomProductProjection;
import com.retailerservice.dto.ReviewDTO;
import com.retailerservice.dto.ReviewProjection;

public interface ReviewServiceInterface {

	List<ReviewProjection> getReviewsByProductId(List<Long> productIds);

	List<CustomProductProjection> getProductsBySellerId(Long sellerId);

	List<ReviewProjection> getReviewsByProductIds(List<Long> productIds);

	List<ReviewDTO> getReviewsByUserId(Long userId);



	
}
