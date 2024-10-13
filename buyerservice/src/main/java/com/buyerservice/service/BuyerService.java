package com.buyerservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyerservice.dao.BuyerOrderDAOInterface;
import com.buyerservice.dao.BuyerProductDAOInterface;
import com.buyerservice.dao.BuyerReviewDAOInterface;
import com.buyerservice.dao.BuyerShoppingDAOInterface;
import com.buyerservice.dao.FavoritesDAOInterface;
import com.buyerservice.dao.UserDAOInterface;
import com.buyerservice.dto.CartProductDTO;
import com.buyerservice.dto.FavoritesProjection;
import com.buyerservice.dto.OrderDTO;
import com.buyerservice.dto.ProductDTO;
import com.buyerservice.dto.ProductDetailsProjection;
import com.buyerservice.dto.ProductDetailsResponse;
import com.buyerservice.dto.ProductProjection;
import com.buyerservice.dto.ShoppingCartDTO;
import com.buyerservice.dto.ShoppingCartProjection;
import com.buyerservice.dto.UserProjection;
import com.buyerservice.entity.Favorites;
import com.buyerservice.entity.Orders;
import com.buyerservice.entity.Products;
import com.buyerservice.entity.ShoppingCart;
import com.buyerservice.entity.User;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class BuyerService implements BuyerServiceInterface{
	
	@Autowired
	BuyerShoppingDAOInterface buyerShoppingDAO;
	
	@Autowired
	BuyerProductDAOInterface buyerProductDAO;
	
	@Autowired
	BuyerOrderDAOInterface buyerOrderDAO;
	
	@Autowired
	BuyerReviewDAOInterface buyerReviewDAO;
	
	@Autowired
	private UserDAOInterface buser;
	
	
	@Autowired
	private FavoritesDAOInterface bfav;
	
	@Override
	public List<CartProductDTO> viewCartProducts(Long userId) {
		return buyerShoppingDAO.findByUserId(userId);
	}

	@Override
	public List<Products> viewProducts() {
		return buyerProductDAO.findAll();
	}
	@Override
	public List<OrderDTO> viewOrderByHistory(long userId) {
	    List<Orders> allOrders = buyerOrderDAO.findAll();
	    List<OrderDTO> userOrdersList = new ArrayList<>();

	    for (Orders order : allOrders) {
	        if (order.getUser().getUserId() == userId) {
	            // Create OrderDTO
	            OrderDTO orderDTO = new OrderDTO();
	            orderDTO.setOrderId(order.getOrderId());
	            orderDTO.setTotalPrice(order.getTotalPrice());
	            orderDTO.setOrderDate(order.getOrderDate());
	            orderDTO.setPaymentMode(order.getPaymentMode());
	            orderDTO.setShoppingAddress(order.getShoppingAddress());
	            orderDTO.setCity(order.getCity());
	            orderDTO.setPincode(order.getPincode());
	            orderDTO.setPhoneNumber(order.getPhoneNumber());
	            orderDTO.setStatus(order.getStatus());
	            
	            // Retrieve the product associated with the order
	            Long productId = order.getProduct() != null ? order.getProduct().getProductId() : 0; // Assuming product can be null
	            orderDTO.setProductId(productId);

	            userOrdersList.add(orderDTO);
	        }
	    }

	    return userOrdersList;
	}


	@Override
	public Optional<Products> viewProductDetails(long productId) {
	    return Optional.ofNullable(buyerProductDAO.findProjectedById(productId));
	}
	
	 @Override
		public List<ProductDTO> getProductsByCategory(String productCategory) {
			List<Products> products= buyerProductDAO.findByProductCategory(productCategory);
			return products.stream().map(this::convertToDTO).collect(Collectors.toList());
		}
		
		private ProductDTO convertToDTO(Products products)
		{
			ProductDTO productDTO=new ProductDTO();
			productDTO.setProductId(products.getProductId());
			productDTO.setProductName(products.getProductName());
			productDTO.setProductDescription(products.getProductDescription());
			productDTO.setPrice(products.getPrice());
			productDTO.setProductCategory(products.getProductCategory());
			return productDTO;
		}

//-----DEEPTHI
	@Override
	public UserProjection login(String email, String password) {
		UserProjection user=buser.login(email,password);
		return user;
	}
	
//-----------------------DEEPTHI

	@Override
	public ShoppingCart addProductToCart(ShoppingCart shoppingcart,Long userId) {

		Optional<ShoppingCart> existingCart = buyerShoppingDAO.findCartByUserID(userId);
		User user=buser.getByUserId(userId);
		if (existingCart.isPresent()) {
	        ShoppingCart cartItems = existingCart.get();
	        
	        cartItems.setQuantity(cartItems.getQuantity() + shoppingcart.getQuantity());
	        cartItems.setTotalPrice(cartItems.getTotalPrice() + shoppingcart.getTotalPrice());

	        ShoppingCart updatedCart = buyerShoppingDAO.save(cartItems);
	        
	        return updatedCart;

	    } else {
	        shoppingcart.setUser(user);
	        ShoppingCart newCart = buyerShoppingDAO.save(shoppingcart);
	        
	        return newCart;
	    }
	}	
//------------------DEEPTI
	@Override
	public Optional<Favorites> addProductToFavorite(Favorites favorites, Long userId) {
	    Optional<Favorites> existingFavorite = bfav.findById(userId);

	    if (existingFavorite.isPresent()) {
	       
	        return existingFavorite;
	    } else {
	        // Save the new favorite and return it
	        Favorites savedFavorite = bfav.save(favorites);
	        return Optional.of(savedFavorite);
	    }
	}
//-------------------------	
	public CartProductDTO deleteProductFromCart(Long userId, Long cartId) {
	    // Optionally, fetch the product first if needed before deleting
	    CartProductDTO cartProduct = buyerShoppingDAO.findCartProductById(userId, cartId);
	    if (cartProduct != null) {
	    	buyerShoppingDAO.deleteById(cartId); // Assuming you have this method
	    }
	    return cartProduct; // Return the deleted product details or null if not found
	}


	@Override
	public Boolean deleteProductFromFavorite(Long favoriteId) {
		// TODO Auto-generated method stub
		Optional<Favorites> favItem = bfav.findItemByUserIdAndProductId(favoriteId);
		if (favItem.isPresent()) {
			bfav.delete(favItem.get());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<CartProductDTO> updateCartQuantity(Long userId, Long cartId, Long quantity) {
	    // Find the cart item for the given userId and cartId
	    Optional<ShoppingCart> cartItemOpt = buyerShoppingDAO.findCartItem(userId, cartId);

	    if (cartItemOpt.isPresent()) {
	        ShoppingCart cartItem = cartItemOpt.get();
	        cartItem.setQuantity(quantity);

	        // Update total price based on the quantity and product price
	        double unitPrice = cartItem.getProduct().getPrice();
	        cartItem.setTotalPrice(unitPrice * quantity);

	        // Save the updated cart item
	        buyerShoppingDAO.save(cartItem);
	    }

	    // Fetch the updated cart for the user and convert to CartProductDTO
	    List<ShoppingCart> cartItems = buyerShoppingDAO.findCartByUserId(userId);
	    
	    // Convert each ShoppingCart item to CartProductDTO
	    List<CartProductDTO> cartProductDTOs = cartItems.stream()
	        .map(cart -> new CartProductDTO(
	            cart.getCartId(),
	            cart.getProduct().getProductId(),
	            cart.getQuantity(),
	            cart.getTotalPrice(),
	            cart.getProduct().getProductDescription(),
	            cart.getProduct().getProductName()
	        ))
	        .collect(Collectors.toList());

	    return cartProductDTOs;
	}

	
	
	  @Override
	    public UserProjection getUser(long uid) {
	        return buser.findById(uid)
	                .map(user -> new UserProjection() {
	                    public long getUserId() { return user.getUserId(); }
	                    public String getName() { return user.getName(); }
	                    public String getEmail() { return user.getEmail(); }
	                    public String getPassword() { return user.getPassword(); }
	                    public String getAddress() { return user.getAddress(); }
	                    public String getPincode() { return user.getPincode(); }
	                    public String getPhoneNumber() { return user.getPhoneNumber(); }
	                    public String getUserType() { return user.getUserType(); }
	                    public String getStatus() { return user.getStatus(); }
	                })
	                .orElse(null);
	    }

	

	  @Override
	    public ProductProjection getProduct(long pid) {
	        return buyerProductDAO.findById(pid)
	                .map(product -> new ProductProjection() {
	                    public long getProductId() { return product.getProductId(); }
	                    public String getProductName() { return product.getProductName(); }
	                    public String getProductDescription() { return product.getProductDescription(); }
	                    public double getPrice() { return product.getPrice(); }
	                    public double getDiscountPrice() { return product.getDiscountPrice(); }
	                })
	                .orElse(null);
	    }

	 


}
