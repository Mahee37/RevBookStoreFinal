package com.retailerservice.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.retailerservice.dto.ProductProjection;
import com.retailerservice.entity.Products;

public interface SellerProductDAOinterface extends JpaRepository<Products, Long>{

	 @Query("SELECT p FROM Products p WHERE p.productId = :productId")
	    Optional<ProductProjection> findProductById(@Param("productId") Long productId);

}
