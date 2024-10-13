package com.buyerservice.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.buyerservice.dto.CartProductDTO;
import com.buyerservice.dto.ShoppingCartProjection;
import com.buyerservice.entity.ShoppingCart;

@Repository
public interface BuyerShoppingDAOInterface extends JpaRepository<ShoppingCart, Long> {
	 @Query("SELECT new com.buyerservice.dto.CartProductDTO(s.cartId, s.product.productId, s.quantity, s.totalPrice, s.productDescription, s.productName) " +
	           "FROM ShoppingCart s WHERE s.user.userId = :userId")
	    List<CartProductDTO> findByUserId(@Param("userId") Long userId);
	 
	 
	 @Query("SELECT sc FROM ShoppingCart sc WHERE sc.cartId = :cartId")
	    ShoppingCart findByCartId(@Param("cartId") long cartId);


	@Query(nativeQuery = true, value ="select * from shopping_cart sc where sc.user_user_id = :userId")
	Optional<ShoppingCart> findCartByUserID(@Param("userId") long userId);


	@Query(nativeQuery = true, value ="select * from shopping_cart sc where sc.user_user_id = :userId and sc.cart_id = :cartId")
	Optional<ShoppingCart> findCartItem(@Param("userId") long userId, @Param("cartId") long cartId);


	 @Query("SELECT sc FROM ShoppingCart sc WHERE sc.user.id = :userId")
	    List<ShoppingCart> findCartByUserId(@Param("userId") Long userId);

	@Query("SELECT new com.buyerservice.dto.CartProductDTO(c.cartId, c.productName, c.quantity, c.totalPrice) FROM ShoppingCart c WHERE c.user.userId = :userId AND c.cartId = :cartId")
	CartProductDTO findCartProductById(@Param("userId") Long userId, @Param("cartId") Long cartId);



	 @Modifying
	 @Query("DELETE FROM ShoppingCart sc WHERE sc.product.productId = :productId")
	 void deleteByProductId(@Param("productId") Long productId);

}

