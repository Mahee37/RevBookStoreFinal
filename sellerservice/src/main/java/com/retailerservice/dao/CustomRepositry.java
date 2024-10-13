package com.retailerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.retailerservice.dto.CustomProductProjection;
import com.retailerservice.dto.ReviewProjection;
import com.retailerservice.entity.Products;

@Repository
public interface CustomRepositry extends JpaRepository<Products , Long>{

	@Query("SELECT p FROM Products p WHERE p.user.userId = :sellerId")
	List<CustomProductProjection> findproductsBySellerId(Long sellerId);


}
