package com.retailerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.retailerservice.entity.Review;

@Repository
public interface SellerreviewRepository extends JpaRepository<Review, Long>{

	
	@Modifying
	@Query("DELETE FROM Review r WHERE r.product.id = :productId")
	void deleteReviewByProductId(Long productId);

}
