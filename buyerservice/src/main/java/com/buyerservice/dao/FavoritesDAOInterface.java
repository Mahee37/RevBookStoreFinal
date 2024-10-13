package com.buyerservice.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.buyerservice.dto.FavoritesProjection;
import com.buyerservice.entity.Favorites;

@Repository
public interface FavoritesDAOInterface extends JpaRepository<Favorites, Long> {

	Optional<Favorites> findById(Long userId);
	
//	@Query(nativeQuery = true, value = "SELECT * FROM favorites f WHERE f.user_user_id = :userId")
//	List<FavoritesProjection> getFavoritesByUserId(Long userId);

	@Query(nativeQuery = true, value ="select * from favorites f where f.favorite_id = :favoriteId ")
	Optional<Favorites> findItemByUserIdAndProductId(Long favoriteId);



}
