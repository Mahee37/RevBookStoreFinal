package com.retailerservice.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.retailerservice.dto.ProductProjection;
import com.retailerservice.entity.Products;
import com.retailerservice.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface SellerDAOInterface extends JpaRepository<Products, Long> {

		@Query("SELECT p FROM Products p WHERE p.user.userId = :userId")
	    List<ProductProjection> findAllByUserId(@Param("userId") Long userId);

		
		
		
		
	    @Query("SELECT p FROM Products p WHERE p.user.userId = :userId")
		List<ProductProjection> findByUserId(Long userId);

	    
	    
	    
	    @Query("SELECT p FROM Products p WHERE p.user.userId = :userId")
	    List<ProductProjection> findProductsByUserId(@Param("userId") Long userId);
	    
	    
	    
	    @Modifying
	    @Transactional
	    @Query("DELETE FROM Products p WHERE p.productId = :productId")
	    int deleteProductByProductId(@Param("productId") Long productId);

	    
	    
	    @Query("select u from User u where u.userId = :userId")
		Optional<User> findUserById(Long userId);
		
	    
}
