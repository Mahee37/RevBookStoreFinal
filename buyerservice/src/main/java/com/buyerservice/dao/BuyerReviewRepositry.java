package com.buyerservice.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.buyerservice.entity.Products;
import com.buyerservice.entity.Review;

@Repository
public interface BuyerReviewRepositry extends JpaRepository<Review ,Long>{

	@Query("SELECT r FROM Review r WHERE r.product.productId = :productId")
    List<Review> findByProductId(@Param("productId") Long productId);

	List<Review> findByProduct(Products product);

	   @Query("SELECT r FROM Review r WHERE r.user.id = :userId")
	List<Review> findByUserId(Long userId);

	   
	@Query(nativeQuery = true, value ="select * from review r where r.review_id = :reviewId ")
	Optional<Review> findReviewByProductId(Long reviewId);

	
	
	


}
