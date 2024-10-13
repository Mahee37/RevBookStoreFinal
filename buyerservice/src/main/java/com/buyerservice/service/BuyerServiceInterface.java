package com.buyerservice.service;

import java.util.List;
import java.util.Optional;

import com.buyerservice.dto.CartProductDTO;
import com.buyerservice.dto.FavoritesProjection;
import com.buyerservice.dto.OrderDTO;
import com.buyerservice.dto.ProductDTO;
import com.buyerservice.dto.ProductProjection;
import com.buyerservice.dto.ShoppingCartDTO;
import com.buyerservice.dto.ShoppingCartProjection;
import com.buyerservice.dto.UserProjection;
import com.buyerservice.entity.Favorites;
import com.buyerservice.entity.Orders;
import com.buyerservice.entity.Products;
import com.buyerservice.entity.ShoppingCart;
import com.buyerservice.entity.User;

public interface BuyerServiceInterface {

	List<CartProductDTO> viewCartProducts(Long customerId);

	List<Products> viewProducts();

	Optional<Products> viewProductDetails(long productId);

	List<OrderDTO> viewOrderByHistory(long userId);

//	ShoppingCartDTO updateQuantityofCart(long cartId, long quantity);

	UserProjection login(String email, String password);

	ShoppingCart addProductToCart(ShoppingCart shoppingcart, Long userId);

	Optional<Favorites> addProductToFavorite(Favorites favorites, Long userId);

//	List<FavoritesProjection> viewFavoriteProducts(Long userId);

	Boolean deleteProductFromFavorite(Long favoriteId);

	List<CartProductDTO> updateCartQuantity(Long userId, Long cartId, Long quantity);

	UserProjection getUser(long uid);

	ProductProjection getProduct(long pid);

	CartProductDTO deleteProductFromCart(Long userId, Long cartId);

	List<ProductDTO> getProductsByCategory(String productCategory);
	
//	User getByUserId(Long uid);

}
