package com.buyerservice.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.buyerservice.dto.UserProjection;
import com.buyerservice.entity.User;

@Repository
public interface UserDAOInterface extends JpaRepository<User,Long> {
	
	
	
	@Query(nativeQuery = true, value = "select * from user where email = :email and password = :password")
	UserProjection login(String email, String password);



	@Query("select u from User u where u.userId = :userId")
	User getByUserId(Long userId);
	

}
