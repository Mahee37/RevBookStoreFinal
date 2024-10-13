package com.retailerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.retailerservice.dto.UserProjection;
import com.retailerservice.entity.User;

public interface SellerAuthorizationRepo extends JpaRepository<User, Long>{

	@Query(nativeQuery = true, value = "select * from user where email = :email and password = :password")
	UserProjection login(@Param("email") String email, @Param("password") String password);
	
	
}
