package com.retailerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.retailerservice.dto.ReviewProjection;
import com.retailerservice.entity.Review;

import jakarta.transaction.Transactional;

public interface ReviewDAOInterface extends JpaRepository<Review, Long> {
	
	@Query("SELECT r.reviewId AS reviewId, r.reviewText AS reviewText, r.rating AS rating, " +
	           "r.user.userId AS userId, r.product.productId AS productId " +
	           "FROM Review r WHERE r.product.productId IN :productIds")
	List<ReviewProjection> findReviewsByProductIds(@Param("productIds") List<Long> productIds);

	
	
	 @Query(value = "SELECT r.review_id AS reviewId, r.review_text AS reviewText, r.rating, r.product_product_id AS productId, r.user_user_id AS userId " +
             "FROM review r WHERE r.product_product_id IN :productIds", nativeQuery = true)
	 List<ReviewProjection> findByProductIdIn(@Param("productIds") List<Long> productIds);


	
	    @Modifying
	    @Transactional
	    @Query("DELETE FROM Review r WHERE r.product.productId = :productId")
	    void deleteByProductId(@Param("productId") Long productId);


	    @Query("SELECT r FROM Review r WHERE r.user.id = :userId")
		List<Review> findByUserId(Long userId);;
	 
}
	
	
	


  
