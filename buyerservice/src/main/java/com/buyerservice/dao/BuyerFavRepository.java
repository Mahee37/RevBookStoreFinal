package com.buyerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.buyerservice.entity.Favorites;

@Repository
public interface BuyerFavRepository extends JpaRepository<Favorites,Long>{
	
	@Query("SELECT f FROM Favorites f WHERE f.user.userId = :userId")
	List<Favorites> findByUserId(Long userId);

}
