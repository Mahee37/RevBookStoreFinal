package com.retailerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.retailerservice.entity.ShoppingCart;

@Repository
public interface SellerCartRepository extends JpaRepository<ShoppingCart, Long>{
	
	@Modifying
	@Query("DELETE FROM ShoppingCart sc WHERE sc.product.id = :productId")
	void deletecartByProductId(@Param("productId") Long productId);


}
