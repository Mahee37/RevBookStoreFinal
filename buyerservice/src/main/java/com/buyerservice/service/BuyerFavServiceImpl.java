package com.buyerservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buyerservice.dao.BuyerFavRepository;
import com.buyerservice.dao.BuyerProductDAOInterface;
import com.buyerservice.dao.UserDAOInterface;
import com.buyerservice.dto.FavoritesDTO;
import com.buyerservice.entity.Favorites;
import com.buyerservice.entity.Products;
import com.buyerservice.entity.User;

@Service
@Transactional
public class BuyerFavServiceImpl implements BuyerFavService{
	
	@Autowired
	private BuyerProductDAOInterface buyerProductDAO;
	
	
	@Autowired
	private UserDAOInterface buser;

	@Autowired
	private BuyerFavRepository favRepo;
	
	public Optional<FavoritesDTO> addProductToFavorite(FavoritesDTO favoritesDTO, Long userId) {
	    // Fetch the user by userId, throw an exception if not found
	    User user = buser.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    // Fetch the product by productId from favoritesDTO, throw an exception if not found
	    Products product = buyerProductDAO.findById(favoritesDTO.getProductId())
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	   
	    Favorites favorites = new Favorites();
	    favorites.setUser(user);
	    favorites.setProduct(product);
//	    favorites.setFavoriteId(favoritesDTO.getFavoriteId());
	    favorites.setProductName(favoritesDTO.getProductName());
	    favorites.setProductDescription(favoritesDTO.getProductDescription());
	    favorites.setPrice(favoritesDTO.getTotalPrice());
	    favorites.setDiscountPrice(favoritesDTO.getDiscountPrice() != null ? favoritesDTO.getDiscountPrice() : 0.0);

	
	    Favorites savedFavorite = favRepo.save(favorites);

	   
	    FavoritesDTO savedFavoritesDTO = new FavoritesDTO();
	    savedFavoritesDTO.setFavoriteId(savedFavorite.getFavoriteId());
	    savedFavoritesDTO.setProductId(savedFavorite.getProduct().getProductId()); 
	    savedFavoritesDTO.setProductName(savedFavorite.getProductName());
	    savedFavoritesDTO.setProductDescription(savedFavorite.getProductDescription());
	    savedFavoritesDTO.setTotalPrice(savedFavorite.getPrice());
	    savedFavoritesDTO.setDiscountPrice(savedFavorite.getDiscountPrice());

	    return Optional.of(savedFavoritesDTO);
	}
	
	
	
//fecthing those Favorites To view Again
	public List<FavoritesDTO> getFavoritesByUserId(Long userId) {
        List<Favorites> favorites = favRepo.findByUserId(userId); // Fetch favorites from the repository
        List<FavoritesDTO> favoritesDTOs = new ArrayList<>();
        
        for (Favorites favorite : favorites) {
            FavoritesDTO dto = new FavoritesDTO();
            dto.setFavoriteId(favorite.getFavoriteId());
            dto.setProductId(favorite.getProduct().getProductId());
            dto.setProductName(favorite.getProductName());
            dto.setProductDescription(favorite.getProductDescription());
            dto.setDiscountPrice(favorite.getDiscountPrice());
            dto.setTotalPrice(favorite.getPrice());
            favoritesDTOs.add(dto);
        }

        return favoritesDTOs; 
    }

}
