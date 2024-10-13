package com.buyerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.buyerservice.entity.Review;

@Repository
public interface BuyerReviewDAOInterface extends JpaRepository<Review, Long>{


	@Modifying
    @Transactional
    @Query("DELETE FROM Review r WHERE r.product.productId = :productId")
    void deleteByProductId(@Param("productId") Long productId);

}
