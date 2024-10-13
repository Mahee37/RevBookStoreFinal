package com.buyerservice.service;

import java.util.List;
import java.util.Optional;

import com.buyerservice.dto.FavoritesDTO;

public interface BuyerFavService {

	Optional<FavoritesDTO> addProductToFavorite(FavoritesDTO favoritesDTO, Long userId);

	List<FavoritesDTO> getFavoritesByUserId(Long userId);

}
