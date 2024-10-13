package com.retailerservice.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.retailerservice.entity.Orders;

import jakarta.transaction.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long>{

    @Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.status = :status WHERE o.orderId = :orderId")
	void updateStatus(long orderId, String status);
    
    
    @Modifying
    @Query("DELETE FROM Orders o WHERE o.product.productId = :productId")
    void deleteorderByProductId(@Param("productId") Long productId);

	
    
  


}
