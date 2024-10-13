package com.retailerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.retailerservice.entity.Favorites;

@Repository
public interface SellerfavRepository extends JpaRepository<Favorites, Long>{

	
	@Modifying
	@Query("DELETE FROM Favorites f WHERE f.product.id = :productId")
	void deletefavByProductId(Long productId);

}
