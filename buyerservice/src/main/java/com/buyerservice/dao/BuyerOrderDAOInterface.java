package com.buyerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buyerservice.entity.Orders;

@Repository
public interface BuyerOrderDAOInterface extends JpaRepository<Orders, Long>{

	//List<Orders> viewOrderByHistory(long userId);

}
