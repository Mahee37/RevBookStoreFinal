package com.buyerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buyerservice.entity.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {

}
