package com.buyerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.buyerservice.dto.ProductWithReviewsDTO;
import com.buyerservice.entity.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long>{

	@Query("SELECT p FROM Products p WHERE p.productId = :productId")
    Products findProductById(@Param("productId") Long productId);
	
	



}

