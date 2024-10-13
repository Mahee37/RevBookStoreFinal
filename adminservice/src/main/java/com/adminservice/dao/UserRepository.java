package com.adminservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adminservice.dto.UserProjection;
import com.adminservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	@Query(nativeQuery = true, value ="SELECT * FROM User u WHERE u.user_type = 'seller'")
	List<UserProjection> findAllSellers();

	@Query(nativeQuery = true, value="select * from user u where u.user_type = 'buyer'")
	List<UserProjection> findAllBuyers();

	@Query(nativeQuery = true, value="select * from user u where u.email = :email and u.password = :password")
	User findByEmailAndPassword(@Param("email") String email,@Param("password") String password);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value="update user set status = 'active' where user_id = :userId and status = 'blocked'")
	void activateUserById(@Param("userId") Long userId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value="update user set status = 'blocked' where user_id = :userId and status = 'active'")
	void blockUserById(@Param("userId") Long userId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value="delete from user u where u.user_id=:userId")
	void deleteUserById(@Param("userId") Long userId);

	@Query(nativeQuery = true, value = "SELECT * FROM user u WHERE u.user_type = 'buyer'")
	List<User> findbuyer();
	
	@Query(nativeQuery = true, value="select * from user where user_type=:str")
	List<User> findseller(@Param("str") String str);

	
}
