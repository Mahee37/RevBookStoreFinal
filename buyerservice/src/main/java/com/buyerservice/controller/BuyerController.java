package com.buyerservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.buyerservice.entity.Complaint;
import com.buyerservice.entity.Products;
import com.buyerservice.entity.Review;

import com.buyerservice.entity.User;
import com.buyerservice.service.BuyerAuthorizationService;
import com.buyerservice.service.BuyerCartService;
import com.buyerservice.service.BuyerComplaintService;
import com.buyerservice.service.BuyerFavService;
import com.buyerservice.service.BuyerOrderService;
import com.buyerservice.service.BuyerProductService;
import com.buyerservice.service.BuyerReviewService;

import com.buyerservice.service.BuyerServiceInterface;
import com.buyerservice.service.BuyerUserService;

import jakarta.ws.rs.NotFoundException;

import com.buyerservice.dto.CartProductDTO;
import com.buyerservice.dto.FavoritesDTO;
import com.buyerservice.dto.OrderDTO;
import com.buyerservice.dto.ProductDTO;
import com.buyerservice.dto.ProductDetailsResponse;
import com.buyerservice.dto.ProductProjection;
import com.buyerservice.dto.ReviewDTO;
import com.buyerservice.dto.UserDTO;
import com.buyerservice.dto.UserProjection;

@RestController
@RequestMapping("/buyer")
public class BuyerController {
	
	@Autowired
	private BuyerServiceInterface buyerService;
	
	@Autowired
	private BuyerReviewService reviewService;
	
	@Autowired
	private BuyerUserService userService;
	
	@Autowired
	private BuyerProductService productService;
	
	@Autowired
	private BuyerCartService cartService;
	
	
	@Autowired
	private BuyerOrderService orderService;
	
	@Autowired
	private BuyerFavService favService;
	
	@Autowired
	private BuyerComplaintService compService;
	
	
	@Autowired
	private BuyerAuthorizationService authBuyerService;
	/*
	 * @Autowired private BuyerSellerService sellerService;
	 */	
//------------------Authorization Phase
	@PostMapping("/register")
	public ResponseEntity<String> registerBuyer(@RequestParam Map<String, String> userDetails) {
	    try {
	       
	        User buyer = new User();
	        buyer.setName(userDetails.get("name"));
	        buyer.setEmail(userDetails.get("email"));
	        buyer.setPassword(userDetails.get("password"));
	        buyer.setPhoneNumber(userDetails.get("phone_number"));
	        buyer.setAddress(userDetails.get("address"));
	        buyer.setPincode(userDetails.get("pincode"));
	        buyer.setUserType("buyer"); 
	        buyer.setStatus("active");  

	   
	        authBuyerService.register(buyer);
	        // Return success message
	        return new ResponseEntity<>("Registered", HttpStatus.OK);
	    } catch (Exception e) {
	       
	        return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@GetMapping("/login/{email}/{password}")
	public ResponseEntity<User> login(@PathVariable("email") String email, @PathVariable("password") String password) {
		
		System.out.println("rest" +email);
		System.out.println("rest" +password);
		
		UserProjection userProjection = authBuyerService.login(email, password);
	    
	    if (userProjection != null) {
	        // Manually map the projection to a User entity 
	        User user = new User();
	        user.setUserId(userProjection.getUserId());  
	        user.setEmail(userProjection.getEmail());    
	        user.setName(userProjection.getName());      
	        user.setUserType(userProjection.getUserType());
	        user.setStatus(userProjection.getStatus());

	        
	        return new ResponseEntity<>(user, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	    }
	}


	
//-----------------------------
		@GetMapping("/viewProducts")
		public ResponseEntity<Object> viewProducts() {
		    // Retrieve the list of Products from the service
		    List<Products> productList = buyerService.viewProducts();
		    System.out.println("prodcuts list"+ productList.size());

		    // Create a list to hold the mapped ProductDTOs
		    List<ProductDTO> productDTOs = new ArrayList<>();
		    // Map Products to ProductDTO
		    for (Products product : productList) {
		        ProductDTO productDTO = new ProductDTO();
		        productDTO.setProductId(product.getProductId());
		        productDTO.setProductName(product.getProductName());
		        productDTO.setProductDescription(product.getProductDescription());
		        productDTO.setImageUrl(product.getImageUrl());
		        productDTO.setProductCategory(product.getProductCategory());
		        productDTO.setPrice(product.getPrice());
		        productDTO.setDiscountPrice(product.getDiscountPrice());
		        productDTO.setQuantity(product.getQuantity());

		        productDTOs.add(productDTO); // Add the mapped DTO to the list
		    }

		    // Check if the list of DTOs is empty
		    if (productDTOs.isEmpty()) {
		        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		    } else {
		        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
		    }
		}
//---------------------------
		@GetMapping("viewProductDetails/{productId}")
		public ResponseEntity<ProductDetailsResponse> viewProductDetails(@PathVariable("productId") Long productId) {
		    Optional<Products> optionalProduct = buyerService.viewProductDetails(productId);

		    // Check if the product exists
		    if (!optionalProduct.isPresent()) {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }

		    Products product = optionalProduct.get();

		    // Fetch the seller information from the product object
		    User seller = product.getUser();
		    if (seller == null) {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }

		    // Fetch reviews for the product
		    List<Review> reviews = reviewService.getReviewsByProductId(productId);

		    // Construct the response object
		    ProductDetailsResponse response = new ProductDetailsResponse();

		    // Map product to ProductDTO
		    ProductDTO productDTO = new ProductDTO();
		    productDTO.setProductId(product.getProductId());
		    productDTO.setProductName(product.getProductName());
		    productDTO.setProductDescription(product.getProductDescription());
		    productDTO.setImageUrl(product.getImageUrl());
		    productDTO.setProductCategory(product.getProductCategory());
		    productDTO.setPrice(product.getPrice());
		    productDTO.setDiscountPrice(product.getDiscountPrice());
		    productDTO.setQuantity(product.getQuantity());

		    // Map seller to UserDTO
		    UserDTO sellerDTO = new UserDTO();
		    sellerDTO.setUserId(seller.getUserId());
		    sellerDTO.setName(seller.getName());
		    sellerDTO.setAddress(seller.getAddress());

		    // Map reviews to ReviewDTOs
		    List<ReviewDTO> reviewDTOs = reviews.stream().map(review -> {
		        ReviewDTO reviewDTO = new ReviewDTO();
		        reviewDTO.setReviewId(review.getReviewId());
		        reviewDTO.setReviewText(review.getReviewText());
		        reviewDTO.setRating(review.getRating());

		        // Map user who wrote the review to UserDTO
		        User reviewUser = review.getUser();
		        if (reviewUser != null) {
		            UserDTO reviewUserDTO = new UserDTO();
		            reviewUserDTO.setUserId(reviewUser.getUserId());
		            reviewUserDTO.setName(reviewUser.getName());
		            reviewUserDTO.setAddress(reviewUser.getAddress());
		            reviewDTO.setUser(reviewUserDTO);
		        }

		        return reviewDTO;
		    }).collect(Collectors.toList());

		    // Set product and reviews in the response
		    response.setProduct(productDTO);
		    response.setSeller(sellerDTO);
		    response.setReviews(reviewDTOs);

		    // Return the response with status OK
		    return new ResponseEntity<>(response, HttpStatus.OK);
		}

	
//------------------
		@GetMapping("/category/{productCategory}")
		public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable("productCategory") String productCategory)
		{
			List<ProductDTO> products= buyerService.getProductsByCategory(productCategory);
			if (products.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
			else
			{
				// Return the list of products in the response
		        return new ResponseEntity<>(products, HttpStatus.OK);
			}
	        
		}
//--------------------------
		@GetMapping("/cart/{userId}")
		public ResponseEntity<List<CartProductDTO>> viewCartProducts(@PathVariable("userId") Long userId) {
		    List<CartProductDTO> cartItems = buyerService.viewCartProducts(userId);
		    
		    if (cartItems.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		    }
		    
		    return new ResponseEntity<>(cartItems, HttpStatus.OK); 
		}

		
		
//----------------------------------------
		@PutMapping("/updateQuantity/{userId}/{cartId}/{quantity}")
		public ResponseEntity<List<CartProductDTO>> updateCartQuantity(@PathVariable("userId") Long userId,
		                                                               @PathVariable("cartId") Long cartId,
		                                                               @PathVariable("quantity") Long quantity) {
		    System.out.println("Updating cart for userId: " + userId + ", cartId: " + cartId + ", quantity: " + quantity);

		    // Call the service to update the cart quantity
		    List<CartProductDTO> updatedCartItems = buyerService.updateCartQuantity(userId, cartId, quantity);
		    
		    // Return updated cart items as the response
		    return new ResponseEntity<>(updatedCartItems, HttpStatus.OK);
		}


//------------------------
		@GetMapping("/viewOrderHistory/{userId}")
		public ResponseEntity<List<OrderDTO>> viewOrderByHistory(@PathVariable("userId") long userId) {
		    List<OrderDTO> orderDTOs = buyerService.viewOrderByHistory(userId); // This now returns a List<OrderDTO>

		    if (orderDTOs != null && !orderDTOs.isEmpty()) {
		        return new ResponseEntity<>(orderDTOs, HttpStatus.OK); 
		    } else {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
		}
		
		
//D
//-----------------------------------D
		 @PostMapping("/addProductToCart/{userId}")
		    public ResponseEntity<CartProductDTO> addProductToCart(@PathVariable Long userId,
		                                                           @RequestBody CartProductDTO cartProductDTO) {
		        // Find the user based on the userId provided
		        User user = userService.findUserById(userId);
		        if (user == null) {
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
		        }

		        // Find the product based on the product ID from the DTO
		        Products product = productService.findProductById(cartProductDTO.getProductId());
		        if (product == null) {
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if product not found
		        }

		        // Populate CartProductDTO with the necessary details
		        cartProductDTO.setUserId(user.getUserId());
		        cartProductDTO.setProductName(product.getProductName());
		        cartProductDTO.setProductDescription(product.getProductDescription());

		        // Save or update the cart entry in the database
		        CartProductDTO savedCartDTO = cartService.saveCart(cartProductDTO); // Assuming saveCart returns the saved DTO

		        return ResponseEntity.ok(savedCartDTO); // Return the saved DTO with status 200 OK
		    }
//------------------------------------D Working
			@DeleteMapping("/deleteProductFromCart/{userId}/{cartId}")
			public ResponseEntity<CartProductDTO> deleteProductFromCart(@PathVariable Long userId, @PathVariable Long cartId) {
			    CartProductDTO deletedProduct = buyerService.deleteProductFromCart(userId, cartId);
			    if (deletedProduct != null) {
			        return ResponseEntity.ok(deletedProduct);
			    } else {
			        return ResponseEntity.notFound().build();
			    }
			}
//----------------------------------D
//Modified - Add Products to Favorite
		 
		 //Adding the Products to Favorite
		 @PostMapping("/addProductToFavorite/{userId}")
		 public ResponseEntity<FavoritesDTO> addProductToFavorite(@RequestBody FavoritesDTO favoritesDTO, @PathVariable("userId") Long userId) {
		     System.out.println("Adding product to favorite for user: " + userId);
		     favoritesDTO.setUserId(userId); // Ensure the userId is set

		     Optional<FavoritesDTO> favorite = favService.addProductToFavorite(favoritesDTO, userId);
		     if (favorite.isPresent()) {
		         System.out.println("Favorite added: " + favorite.get().getProductName());
		         return new ResponseEntity<>(favorite.get(), HttpStatus.OK); // Return the FavoritesDTO
		     } else {
		         System.out.println("Failed to add favorite for user: " + userId);
		         return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		     }
		 }  
		 
		 
//-----------------------Fetching the Favorite Products
		  //fetching the updated Favourites List
		 @GetMapping("/wishlist/{userId}")
		    public ResponseEntity<List<FavoritesDTO>> getFavorites(@PathVariable Long userId) {
		        try {
		            List<FavoritesDTO> favorites = favService.getFavoritesByUserId(userId); 
		            return new ResponseEntity<>(favorites, HttpStatus.OK); 
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }

//--------------------------------------D
		 @DeleteMapping("/deleteProductFromFavorite/{userId}/{favoriteId}")
		 public ResponseEntity<Object> deleteProductFromFavorite(@PathVariable("userId") Long userId,
		                                                         @PathVariable("favoriteId") Long favoriteId) {
		     System.out.println(userId);
		     System.out.println(favoriteId);

		     // Call service to delete product from favorites
		     Boolean deleted = buyerService.deleteProductFromFavorite(favoriteId);

		     String message = "Failed to delete";
		     if (deleted) {
		         message = "Deleted Successfully";
		         return new ResponseEntity<>(message, HttpStatus.OK);
		     } else {
		         return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
		     }
		 }
	
//------------------------Fetching and Assistance
		@GetMapping("/getUser/{id}")
		public UserProjection getUser(@PathVariable("id") long uid) {
		    UserProjection user = buyerService.getUser(uid);
		    if (user == null) {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		    }
		    return user;
		}
		
		
		@GetMapping("/getProduct/{id}")
		public ProductProjection getProduct(@PathVariable("id") long pid) {
		    ProductProjection product = buyerService.getProduct(pid);
		    if (product == null) {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		    }
		    return product;
		}
		 @GetMapping("/{productId}")
		    public Products getProductById(@PathVariable Long productId) {
		        Products product = productService.getProductById(productId);
		        if (product == null) {
		            throw new NotFoundException("Product not found with id: " + productId);
		        }
		        return product;
		    }

//-----------------------------------Reviews Handling
		 @PostMapping("/submitreview")
		 public ResponseEntity<String> submitreview(@RequestBody ReviewDTO reviewDTO) {
		     try {
		         // Validate the incoming review DTO
		         if (reviewDTO == null || reviewDTO.getProductId() == null || reviewDTO.getUserId() == null || 
		             reviewDTO.getReviewText() == null || reviewDTO.getRating() < 1 || reviewDTO.getRating() > 5) {
		             return new ResponseEntity<>("Invalid review data provided.", HttpStatus.BAD_REQUEST);
		         }

		         // Log the received review for debugging
		         System.out.println("Received review: " + reviewDTO);

		         // Call the review service to submit the review
		         int result = reviewService.submitreview(reviewDTO);

		         // Construct the response message based on the submission result
		         String message = result > 0 ? "Review submitted successfully!" : "Failed to submit review.";
		         System.out.println("Response message: " + message);

		         // Return a successful response with the message
		         return new ResponseEntity<>(message, HttpStatus.OK);
		     } catch (Exception e) {
		         String errorMessage = "An error occurred while submitting the review: " + e.getMessage();
		         System.err.println(errorMessage);
		         return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		     }
		 }

//
//		 @GetMapping("/getProductWithReviews/{productId}/{userId}")
//		 public ResponseEntity<ProductWithReviews> getProductWithReviews(
//		         @PathVariable Long productId,
//		         @PathVariable Long userId) {
//		     
//		     try {
//		         // Fetch the product with reviews using the service
//		         ProductWithReviews productWithReviews = productService.getProductWithReviews(productId, userId);
//		         
//		         // Check if the product or reviews are not found
//		         if (productWithReviews == null || productWithReviews.getReviews() == null) {
//		             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//		         }
//		         
//		         return ResponseEntity.ok(productWithReviews);
//		     } catch (Exception e) {
//		         String errorMessage = "An error occurred while fetching product reviews: " + e.getMessage();
//		         System.err.println(errorMessage);
//		         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		     }
//		 } 
//----------------
		 @GetMapping("/getAllReviewsByUserId/{userId}")
		    public ResponseEntity<List<ReviewDTO>> getAllReviewsByUserId(@PathVariable Long userId) {
		        List<ReviewDTO> reviews = reviewService.getReviewsByUserId(userId);
		        if (reviews != null && !reviews.isEmpty()) {
		            return ResponseEntity.ok(reviews);
		        } else {
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		        }
		    }
		 
		 
		 @GetMapping("/getAllReviewsByProductId/{productId}")
		    public ResponseEntity<List<ReviewDTO>> getAllReviewsByProductId(@PathVariable Long productId) {
		        List<ReviewDTO> reviews = reviewService.getAllReviewsByProductId(productId);
		        if (reviews.isEmpty()) {
		            return ResponseEntity.noContent().build(); // Return 204 if no reviews found
		        }
		        return ResponseEntity.ok(reviews); // Return 200 with the list of reviews
		    }
		 
		 
		 
//-------------------------
		 
		 @DeleteMapping("/reviews/{reviewId}")
		 public ResponseEntity<Object> deleteReview(@PathVariable("reviewId") Long reviewId) {
		     System.out.println("Attempting to delete review with ID: " + reviewId);
		     String message;

		     try {
		         Boolean deleted = reviewService.deleteReviewById(reviewId);
		         
		         if (deleted) {
		             message = "Deleted Successfully";
		             return new ResponseEntity<>(message, HttpStatus.OK);
		         } else {
		             message = "Failed to delete the review. Review ID may not exist.";
		             return new ResponseEntity<>(message, HttpStatus.NOT_FOUND); // Return NOT_FOUND if review ID doesn't exist
		         }
		     } catch (Exception e) {
		         message = "An error occurred while deleting the review: " + e.getMessage();
		         return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR); // Return internal server error for any unexpected issues
		     }
		 }
//------------------------
		 @GetMapping("/UpdatedReviews/{userId}")
		 public ResponseEntity<Object> getUpdatedReviews(@PathVariable("userId") Long userId) {
		     try {
		       
		         List<ReviewDTO> updatedReviews = reviewService.getAllReviewsByUserId(userId);
		         
		         if (updatedReviews.isEmpty()) {
		             return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return NO_CONTENT if no reviews found
		         }
		         
		         return new ResponseEntity<>(updatedReviews, HttpStatus.OK); // Return the list of reviews with OK status
		     } catch (Exception e) {
		         // Handle any exceptions that may occur
		         String message = "An error occurred while fetching reviews: " + e.getMessage();
		         return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR); // Return internal server error for any unexpected issues
		     }
		 }
//------------------------
		 @PostMapping("/createorder")
		 public ResponseEntity<List<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO) {
		     List<OrderDTO> createdOrders = orderService.createOrder(orderDTO);
		     return new ResponseEntity<>(createdOrders, HttpStatus.CREATED);
		 }

//-------------------
		 @PostMapping("/submitcomplaint")
			public ResponseEntity<Object> submitcomplaint(@RequestBody Complaint complaint) {
				
				
				int i=compService.submitcomplaint(complaint);
				String message="success";
				if(i>0) {
					message="success";
				}
				ResponseEntity<Object> rentity=new ResponseEntity<Object>(message ,HttpStatus.OK);
				return rentity;	
			

		}
		 
}

//-------------------------------------------BUYER MODULE END---------------------------------------------//