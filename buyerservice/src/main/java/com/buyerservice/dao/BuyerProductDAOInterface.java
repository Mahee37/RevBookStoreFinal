package com.buyerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.buyerservice.dto.ProductDetailsProjection;
import com.buyerservice.dto.ProductDetailsResponse;
import com.buyerservice.entity.Products;

@Repository
public interface BuyerProductDAOInterface extends JpaRepository<Products, Long>{
	
	
	
	@Query("SELECT p FROM Products p WHERE p.productId = :productId")
	Products findProjectedById(@Param("productId") Long productId);

	@Query("SELECT p FROM Products p WHERE p.productCategory = :productCategory")
	List<Products> findByProductCategory(@Param("productCategory") String productCategory);
	
}
