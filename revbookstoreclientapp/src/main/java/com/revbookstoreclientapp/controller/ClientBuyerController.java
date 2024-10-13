package com.revbookstoreclientapp.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.revbookstoreclientapp.Exception.NoProductsFoundException;
import com.revbookstoreclientapp.dto.CartProductDTO;
import com.revbookstoreclientapp.dto.Complaint;
import com.revbookstoreclientapp.dto.Favorites;
import com.revbookstoreclientapp.dto.FavoritesDTO;
import com.revbookstoreclientapp.dto.FavoritesProjection;
import com.revbookstoreclientapp.dto.OrderDTO;
import com.revbookstoreclientapp.dto.Orders;
import com.revbookstoreclientapp.dto.ProductDTO;
import com.revbookstoreclientapp.dto.ProductDetailsResponse;
import com.revbookstoreclientapp.dto.ProductDetailsViewModel;
import com.revbookstoreclientapp.dto.ProductWithReviews;
import com.revbookstoreclientapp.dto.ProductWithReviewsDTO;
import com.revbookstoreclientapp.dto.Products;
import com.revbookstoreclientapp.dto.Review;
import com.revbookstoreclientapp.dto.ReviewDTO;
import com.revbookstoreclientapp.dto.ShoppingCart;
import com.revbookstoreclientapp.dto.ShoppingCartAddDTO;
import com.revbookstoreclientapp.dto.ShoppingCartDTO;
import com.revbookstoreclientapp.dto.ShoppingCartProjection;
import com.revbookstoreclientapp.dto.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class ClientBuyerController {

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private RestTemplate restTemplate;

//------------------------------Working
	@GetMapping("/BuyerInventory") // viewProducts
	public ModelAndView viewProducts() {
	    ModelAndView mv = new ModelAndView();
	    List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
	    
	    if (instances.isEmpty()) {
	        throw new NoProductsFoundException("No products are currently available."); // Handle no instances found
	    }

	    ServiceInstance serviceInstance = instances.get(0);
	    String baseUrl = serviceInstance.getUri().toString() + "/buyer/viewProducts";

	    ResponseEntity<List<Products>> responseEntity;

	    try {
	        responseEntity = restTemplate.exchange(baseUrl, HttpMethod.GET, null,
	                new ParameterizedTypeReference<List<Products>>() {});

	        // Check if responseEntity is null or if response body is null
	        if (responseEntity == null || responseEntity.getBody() == null) {
	            throw new NoProductsFoundException("No products are currently available."); // Handle null response
	        }

	        List<Products> products = responseEntity.getBody();

	        // Check if the products list is empty
	        if (products.isEmpty()) {
	            throw new NoProductsFoundException("No products are currently available."); // Handle empty list
	        }

	        // Map products to ProductDTO
	        List<ProductDTO> productDTOs = products.stream().map(product -> {
	            ProductDTO productDTO = new ProductDTO();
	            productDTO.setProductId(product.getProductId());
	            productDTO.setProductName(product.getProductName());
	            productDTO.setProductDescription(product.getProductDescription());
	            productDTO.setImageUrl(product.getImageUrl());
	            productDTO.setProductCategory(product.getProductCategory());
	            productDTO.setPrice(product.getPrice());
	            productDTO.setDiscountPrice(product.getDiscountPrice());
	            productDTO.setQuantity(product.getQuantity());
	            return productDTO;
	        }).collect(Collectors.toList());

	        mv.addObject("productresult", productDTOs); // Add products to model

	    } catch (HttpClientErrorException e) {
	        mv.addObject("error", "Unable to fetch products at this moment. Please try again later.");
	        e.printStackTrace(); // Log the error for debugging
	    } catch (Exception e) {
	        mv.addObject("error", "An unexpected error occurred: " + e.getMessage());
	        e.printStackTrace(); // Log the error for debugging
	    }

	    mv.setViewName("products"); // Set view to products.jsp
	    return mv;
	}



//---------------Working
	@GetMapping("/BuyerProductDetails")
	public ModelAndView viewProductDetails(@RequestParam("id") Long productId) {
		ModelAndView mv = new ModelAndView();

		List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
		if (instances.isEmpty()) {
			mv.addObject("errorMessage", "Service unavailable.");
			mv.setViewName("error");
			return mv;
		}

		ServiceInstance serviceInstance = instances.get(0);
		String baseUrl = serviceInstance.getUri().toString() + "/buyer/viewProductDetails/" + productId;
		ProductDetailsResponse response = restTemplate.getForObject(baseUrl, ProductDetailsResponse.class);

		if (response != null) {
			ProductDetailsViewModel viewModel = new ProductDetailsViewModel();
			viewModel.setProduct(response.getProduct());
			viewModel.setSeller(response.getSeller());
			viewModel.setReviews(response.getReviews());
			mv.addObject("productdetails", viewModel);
		} else {
			mv.addObject("errorMessage", "Product not found.");
		}

		mv.setViewName("productinfo");
		return mv;
	}

//---------------------------------Working
	@GetMapping("/BuyerCart")
	public ModelAndView viewCartProducts(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		 Long userId = (Long) session.getAttribute("userId");

		    if (userId == null) {
		        mv.addObject("error", "User ID not found in session");
		        mv.setViewName("error"); 
		        return mv;
		    }
		List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");

		if (instances.isEmpty()) {
			throw new RuntimeException("No instances of BUYERSERVICE available");
		}

		ServiceInstance serviceInstance = instances.get(0);
		String baseUrl = serviceInstance.getUri().toString() + "/buyer/cart/" + userId;

		ResponseEntity<List<CartProductDTO>> responseEntity;

		try {
			responseEntity = restTemplate.exchange(baseUrl, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<CartProductDTO>>() {
					});
			List<CartProductDTO> cartItems = responseEntity.getBody();

			if (cartItems != null) {
				mv.addObject("cartItems", cartItems); // Passing cartItems
			} else {
				mv.addObject("message", "No items found in the cart.");
			}
		} catch (Exception e) {
			mv.setViewName("error");
			mv.addObject("message", "An unexpected error occurred.");
			return mv;
		}

		mv.setViewName("cart"); // Pointing to the same JSP
		return mv;
	}

//-------------------------------------Working
	@RequestMapping("/searchProducts")
	public ModelAndView browseProductsByCategory(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("productCategory") String productCategory) {
		ModelAndView mv = new ModelAndView();

		try {
			List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
			if (instances == null || instances.isEmpty()) {
				throw new IllegalStateException("No instances of BUYERSERVICE found");
			}

			ServiceInstance serviceInstance = instances.get(0);
			String baseUrl = serviceInstance.getUri().toString() + "/buyer/category/" + productCategory;

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<ProductDTO[]> responseEntity = restTemplate.getForEntity(baseUrl, ProductDTO[].class);

			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				ProductDTO[] products = responseEntity.getBody();
				mv.addObject("productresult", Arrays.asList(products));
				mv.setViewName("products");
			} else {
				mv.addObject("errorMessage", "No products found for the category: " + productCategory);
				mv.setViewName("errorPage"); // Set to an error view
			}
		} catch (Exception e) {
			mv.addObject("errorMessage", "An error occurred while fetching products: " + e.getMessage());
			mv.setViewName("errorPage"); // Set to an error view
		}

		return mv;
	}

//-------------------------------------------------Working
	@RequestMapping("/updateQuantity")
	public ModelAndView updateQuantity(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("quantity") String quantity, @RequestParam("cartId") Long cartId) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        mv.addObject("error", "User ID not found in session");
	        mv.setViewName("error"); 
	        return mv;
	    }
		long quantity1 = Long.parseLong(quantity);//casting the quantity 

		List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
		if (instances.isEmpty()) {
			throw new RuntimeException("No instances of BUYERSERVICE available");
		}

		ServiceInstance serviceInstance = instances.get(0);
		String baseUrl = serviceInstance.getUri().toString();

		// Update the cart quantity
		String updateUrl = baseUrl + "/buyer/updateQuantity/" + userId + "/" + cartId + "/" + quantity1;
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.exchange(updateUrl, HttpMethod.PUT, null, Object.class);

		// Fetch updated cart items
		String cartUrl = baseUrl + "/buyer/cart/" + userId;
		ResponseEntity<List<CartProductDTO>> responseEntity = restTemplate.exchange(cartUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CartProductDTO>>() {
				});

		List<CartProductDTO> updatedCartItems = responseEntity.getBody();

		if (updatedCartItems != null) {
			mv.addObject("cartItems", updatedCartItems); // Use the same attribute name 'cartItems'
		} else {
			mv.addObject("error", "Product not found in cart.");
		}

		mv.setViewName("cart"); // Pointing to the same JSP file
		return mv;
	}

//-------------------------------Working
	@GetMapping("/OrderDetails")
	public ModelAndView viewOrderByHistory(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		//No need Of Session
		Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        mv.addObject("error", "User ID not found in session");
	        mv.setViewName("error"); 
	        return mv;
	    }
		// Log the userId for debugging
		System.out.println("Fetching order history for user ID: " + userId);

		List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance = instances.isEmpty() ? null : instances.get(0);
		String baseUrl = serviceInstance != null ? serviceInstance.getUri().toString() + "/buyer/viewOrderHistory/" + userId: "";

		RestTemplate restTemplate = new RestTemplate();
		List<OrderDTO> orderList = new ArrayList<>();

		try {
			// Fetch the order history directly as a list of OrderDTO
			ResponseEntity<List<OrderDTO>> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<OrderDTO>>() {
					});

			// Check the response status
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				orderList = responseEntity.getBody();
				// Log the retrieved order history for debugging
				System.out.println("Retrieved order history: " + orderList);
			} else {
				System.err.println("No orders found for this user.");
				mv.addObject("message", "No order history found for this user.");
				mv.setViewName("orderHistory");
				return mv;
			}
		} catch (Exception e) {
			// Log the exception
			System.err.println("Error fetching order history: " + e.getMessage());
			mv.addObject("message", "Error fetching order history.");
			mv.setViewName("orders");
			return mv;
		}

		if (orderList != null && !orderList.isEmpty()) {
			mv.addObject("orders", orderList);
			mv.setViewName("orders");
		} else {
			mv.addObject("message", "No order history found for this user.");
			mv.setViewName("orders");
		}

		return mv;
	}

//----D Working	
	
	@RequestMapping("/addProductsToCarts")
	public ModelAndView addProductsToCarts(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("productId") Long productId, @RequestParam("productName") String productName,
			@RequestParam("productDescription") String productDescription,
			@RequestParam("discountPrice") Double discountPrice, @RequestParam("quantity") Long quantity) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        mv.addObject("error", "User ID not found in session");
	        mv.setViewName("error"); 
	        return mv;
	    }

		// Create a new CartProductDTO object
		CartProductDTO cartProductDTO = new CartProductDTO();
		cartProductDTO.setUserId(userId);
		cartProductDTO.setProductId(productId);
		cartProductDTO.setProductName(productName);
		cartProductDTO.setProductDescription(productDescription);
		cartProductDTO.setQuantity(quantity);
		cartProductDTO.setTotalPrice(discountPrice);

		// Fetch the BUYERSERVICE instance
		List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
		if (instances.isEmpty()) {
			throw new RuntimeException("No instances of BUYERSERVICE available");
		}
		ServiceInstance serviceInstance = instances.get(0);
		String baseUrl = serviceInstance.getUri().toString();

		// Add product to cart using CartProductDTO
		String addToCartUrl = baseUrl + "/buyer/addProductToCart/" + userId;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<CartProductDTO> addToCartResponse = restTemplate.postForEntity(addToCartUrl, cartProductDTO,
				CartProductDTO.class);

		// Fetch updated cart items
		String cartUrl = baseUrl + "/buyer/cart/" + userId;
		ResponseEntity<List<CartProductDTO>> responseEntity = restTemplate.exchange(cartUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CartProductDTO>>() {
				});

		List<CartProductDTO> cartItems = responseEntity.getBody();

		if (cartItems != null) {
			mv.addObject("cartItems", cartItems); // Use the consistent attribute name
		} else {
			mv.addObject("error", "Product not found in cart.");
		}

		mv.setViewName("cart"); // Pointing to the same JSP file
		return mv;
	}
//------------------------Deepti--Working

	@RequestMapping("/removeProductFromCart")
	public ModelAndView removeProductFromCart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("cartId") Long cartId) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        mv.addObject("error", "User ID not found in session");
	        mv.setViewName("error"); 
	        return mv;
	    }
		System.out.println("Cart ID: " + cartId);
		System.out.println("User ID: " + userId);

		// Prepare the base URL for the BUYERSERVICE
		List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance = instances.get(0);
		String baseUrl = serviceInstance.getUri().toString(); // e.g., http://localhost:8080
		String deleteUrl = baseUrl + "/buyer/deleteProductFromCart/" + userId + "/" + cartId;

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Void> responseEntity = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, null, Void.class);

		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			// Use the existing method to retrieve updated cart items
			ResponseEntity<List<CartProductDTO>> cartResponse = restTemplate.exchange(baseUrl + "/buyer/cart/" + userId,
					HttpMethod.GET, null, new ParameterizedTypeReference<List<CartProductDTO>>() {
					});

			if (cartResponse.getStatusCode() == HttpStatus.OK) {
				List<CartProductDTO> cartItems = cartResponse.getBody();
				if (cartItems != null && !cartItems.isEmpty()) {
					mv.addObject("cartItems", cartItems);
					mv.setViewName("cart");
				} else {
					mv.addObject("error", "No items found in cart.");
					mv.setViewName("cart");
				}
			} else {
				mv.addObject("error", "Failed to retrieve cart items.");
				mv.setViewName("cart");
			}
		} else {
			// Handle the case where the deletion was unsuccessful
			mv.addObject("error", "Product not found in cart or cart is empty.");
			mv.setViewName("cart");
			
		}

		return mv;
	}

//------------------------Working 

	// Hardcoded for sometime use session

	@RequestMapping("/addProductToFavorite")
	public ModelAndView addProductToFavorite(HttpServletRequest request, @RequestParam("productId") Long productId,
			@RequestParam("productName") String productName,
			@RequestParam("productDescription") String productDescription,
			@RequestParam("discountPrice") Double discountPrice, @RequestParam("totalPrice") Double totalPrice) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        mv.addObject("error", "User ID not found in session");
	        mv.setViewName("error"); 
	        return mv;
	    }

		// Create a new FavoritesDTO object
		FavoritesDTO favoritesDTO = new FavoritesDTO();
		favoritesDTO.setUserId(userId);
		favoritesDTO.setProductId(productId);
		favoritesDTO.setProductName(productName);
		favoritesDTO.setProductDescription(productDescription);
		favoritesDTO.setDiscountPrice(discountPrice);
		favoritesDTO.setTotalPrice(totalPrice);

		List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
		if (instances.isEmpty()) {
			mv.addObject("error", "No instances of BUYERSERVICE available");
			mv.setViewName("error");
			return mv;
		}
		ServiceInstance serviceInstance = instances.get(0);
		String baseUrl = serviceInstance.getUri().toString();

		try {
			// Add product to favorites using FavoritesDTO
			String addToFavoritesUrl = baseUrl + "/buyer/addProductToFavorite/" + userId;
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<FavoritesDTO> addToFavoritesResponse = restTemplate.postForEntity(addToFavoritesUrl,
					favoritesDTO, FavoritesDTO.class);

			System.out.println("Response from adding to favorites: " + addToFavoritesResponse.getBody());
			if (addToFavoritesResponse.getStatusCode() == HttpStatus.OK) {

				String fetchFavoritesUrl = baseUrl + "/buyer/wishlist/" + userId;
				ResponseEntity<List<FavoritesDTO>> favoritesResponse = restTemplate.exchange(fetchFavoritesUrl,
						HttpMethod.GET, null, new ParameterizedTypeReference<List<FavoritesDTO>>() {
						});
				if (favoritesResponse.getStatusCode() == HttpStatus.OK) {
					mv.addObject("fav", favoritesResponse.getBody());
					mv.addObject("message", "Item added to favorites successfully");
				} else {
					mv.addObject("error", "Failed to retrieve favorite products");
				}
			} else {
				mv.addObject("error", "Failed to add item to favorites");
			}
		} catch (Exception e) {
			mv.addObject("error", "An error occurred: " + e.getMessage());
		}

		mv.setViewName("favorites");
		return mv;
	}

//---Deepti
	@RequestMapping("/removeFromFavorite")
	public ModelAndView removeFromFavorite(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("favoriteId") Long favoriteId) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        mv.addObject("error", "User ID not found in session");
	        mv.setViewName("error"); 
	        return mv;
	    }

		List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
		if (instances.isEmpty()) {
			mv.addObject("error", "No instances of BUYERSERVICE available");
			mv.setViewName("error");
			return mv;
		}

		ServiceInstance serviceInstance = instances.get(0);
		String baseUrl = serviceInstance.getUri().toString();

		try {

			String removeFromFavoritesUrl = baseUrl + "/buyer/deleteProductFromFavorite/" + userId + "/" + favoriteId;

			// Perform the DELETE request using RestTemplate
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<Void> entity = new HttpEntity<>(null); // Create an empty entity for the DELETE request
			ResponseEntity<String> removeResponse = restTemplate.exchange(removeFromFavoritesUrl, HttpMethod.DELETE,
					entity, String.class);

			if (removeResponse.getStatusCode() == HttpStatus.OK) {
				mv.addObject("message", "Item removed from favorites successfully");

				// Fetch the updated favorites list after removing the product
				String fetchFavoritesUrl = baseUrl + "/buyer/wishlist/" + userId;
				ResponseEntity<List<FavoritesDTO>> favoritesResponse = restTemplate.exchange(fetchFavoritesUrl,
						HttpMethod.GET, null, new ParameterizedTypeReference<List<FavoritesDTO>>() {
						});

				// Check if the list of favorites was retrieved successfully
				if (favoritesResponse.getStatusCode() == HttpStatus.OK) {
					mv.addObject("fav", favoritesResponse.getBody());
				} else {
					mv.addObject("error", "Failed to retrieve updated favorite products");
				}
			} else {
				mv.addObject("error", "Failed to remove item from favorites");
			}
		} catch (Exception e) {
			mv.addObject("error", "An error occurred: " + e.getMessage());
		}

		mv.setViewName("favorites"); // Set the view to display the updated favorites list
		return mv;
	}
//----Fetch Wishlist
	@RequestMapping("/getWishlistByUserId")
	public ModelAndView getWishlistByUserId(HttpSession session) {
	    ModelAndView mv = new ModelAndView();

	    // Retrieve userId from the session
	    Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        mv.addObject("error", "User ID not found in session");
	        mv.setViewName("error");
	        return mv;
	    }

	    // Retrieve instances of FAVORITESERVICE using discovery client
	    List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
	    if (instances.isEmpty()) {
	        mv.addObject("error", "No instances of BUYERSERVICE available.");
	        mv.setViewName("error");
	        return mv;
	    }

	    // Use the first available instance
	    ServiceInstance serviceInstance = instances.get(0);
	    String baseUrl = serviceInstance.getUri().toString();

	    try {
	        // Construct the URL for fetching favorites
	        String fetchWishlistUrl = baseUrl + "/buyer/wishlist/" + userId;
	        RestTemplate restTemplate = new RestTemplate();

	       
	        ResponseEntity<List<FavoritesDTO>> wishlistResponse = restTemplate.exchange(
	                fetchWishlistUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<FavoritesDTO>>() {}
	        );

	        // Check if the response is OK
	        if (wishlistResponse.getStatusCode() == HttpStatus.OK) {
	            List<FavoritesDTO> favorites = wishlistResponse.getBody();

	            // Handle the case where no favorites are found
	            if (favorites == null || favorites.isEmpty()) {
	                mv.addObject("message", "No favorites found for the specified user.");
	            } else {
	                mv.addObject("fav", favorites); // Pass favorites to the model
	            }
	        } else {
	            mv.addObject("error", "Failed to fetch favorites, status code: " + wishlistResponse.getStatusCode());
	        }
	    } catch (Exception e) {
	        // Handle exceptions
	        mv.addObject("error", "An error occurred: " + e.getMessage());
	    }

	    // Set the view name to your JSP page for displaying the wishlist
	    mv.setViewName("favorites");
	    return mv;
	}

//---------------//implement final jsp 
	@RequestMapping("/submitreview")
	public ModelAndView submitreview(HttpServletRequest request, 
	                                  @RequestParam("productId") String productId,
	                                  @RequestParam("reviewText") String reviewText, 
	                                  @RequestParam("rating") String rating) {

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        mv.addObject("error", "User ID not found in session");
	        mv.setViewName("error"); 
	        return mv;
	    }

	    ReviewDTO reviewDTO = new ReviewDTO();
	    reviewDTO.setUserId(userId);
	    reviewDTO.setProductId(Long.parseLong(productId));
	    reviewDTO.setReviewText(reviewText);
	    reviewDTO.setRating(Integer.parseInt(rating));

	    // Discover the buyer service instances
	    List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
	    if (instances.isEmpty()) {
	        mv.addObject("error", "No instances of BUYERSERVICE available");
	        mv.setViewName("error");
	        return mv;
	    }

	    ServiceInstance serviceInstance = instances.get(0);
	    String baseUrl = serviceInstance.getUri().toString();

	    try {
	        // Send review to the buyer service
	        String submitReviewUrl = baseUrl + "/buyer/submitreview";
	        RestTemplate restTemplate = new RestTemplate();

	        HttpEntity<ReviewDTO> entity = new HttpEntity<>(reviewDTO);
	        ResponseEntity<String> responseEntity = restTemplate.postForEntity(submitReviewUrl, entity, String.class);

	        // Check response status
	        if (responseEntity.getStatusCode() == HttpStatus.OK) {
	            mv.addObject("message", "Review submitted successfully!");
	            mv.setViewName("reviewsucess"); // Ensure this matches your JSP file
	        } else {
	            mv.addObject("error", "Failed to submit review.");
	            mv.setViewName("productinfo"); // Still return to the result view
	        }
	    } catch (Exception e) {
	        mv.addObject("error", "An error occurred: " + e.getMessage());
	        mv.setViewName("productinfo"); // Ensure this matches your JSP file
	    }

	    return mv;
	}
	
//-----------
	@RequestMapping("/getReviewsByUserId")
	public ModelAndView getReviewsByUserId(HttpSession session) {
	    ModelAndView mv = new ModelAndView();
	  
		Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        mv.addObject("error", "User ID not found in session");
	        mv.setViewName("error"); 
	        return mv;
	    }
	    List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
	    if (instances.isEmpty()) {
	        mv.addObject("error", "No instances of BUYERSERVICE available.");
	        mv.setViewName("error");
	        return mv;
	    }

	    ServiceInstance serviceInstance = instances.get(0);
	    String baseUrl = serviceInstance.getUri().toString();

	    try {
	        String fetchReviewsUrl = baseUrl + "/buyer/getAllReviewsByUserId/" + userId;
	        RestTemplate restTemplate = new RestTemplate();

	        ResponseEntity<List<ReviewDTO>> reviewsResponse = restTemplate.exchange(
	                fetchReviewsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {}
	        );

	        if (reviewsResponse.getStatusCode() == HttpStatus.OK) {
	            List<ReviewDTO> reviews = reviewsResponse.getBody();

	            if (reviews == null || reviews.isEmpty()) {
	                mv.addObject("message", "No reviews found for the specified user.");
	            } else {
	                mv.addObject("userReviews", reviews);
	            }
	        } else {
	            mv.addObject("error", "Failed to fetch reviews.");
	        }
	    } catch (Exception e) {
	        mv.addObject("error", "An error occurred: " + e.getMessage());
	    }

	    mv.setViewName("reviews"); // Ensure this matches your JSP file
	    return mv;
	}
	
	//---------------------
	@RequestMapping("/DeleteReviews")
	public ModelAndView reviews(HttpServletRequest request, HttpServletResponse response, @RequestParam("reviewId") String reviewId) {
	    long reviewId1 = Long.parseLong(reviewId);
	    
	    HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");

	    List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
	    
	    if (instances.isEmpty()) {
	        ModelAndView mv = new ModelAndView("error");
	        mv.addObject("error", "No instances of BUYERSERVICE available.");
	        return mv;
	    }

	    ServiceInstance serviceInstance = instances.get(0);
	    String baseUrl = serviceInstance.getUri().toString();
	    
	    // Construct the URL for deleting the review
	    String deleteUrl = baseUrl + "/buyer/reviews/" + reviewId1;
	    
	    RestTemplate restTemplate = new RestTemplate();
	    HttpEntity<Void> entity = new HttpEntity<>(null); // Create an empty entity for DELETE request
	    
	    try {
	        // Send the DELETE request
	        ResponseEntity<String> deleteResponse = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, entity, String.class);
	        if (deleteResponse.getStatusCode() != HttpStatus.OK) {
	            // Handle unsuccessful delete response
	            ModelAndView mv = new ModelAndView("error");
	            mv.addObject("error", "Failed to delete the review.");
	            return mv;
	        }
	    } catch (Exception e) {
	        ModelAndView mv = new ModelAndView("error");
	        mv.addObject("error", "An error occurred while deleting the review: " + e.getMessage());
	        return mv;
	    }

	    // Fetching the updated list of reviews
	    String fetchReviewsUrl = baseUrl + "/buyer/UpdatedReviews/" + userId;

	    List<ReviewDTO> userReviews;
	    try {
	        ResponseEntity<List<ReviewDTO>> reviewsResponse = restTemplate.exchange(
	                fetchReviewsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {}
	        );

	        if (reviewsResponse.getStatusCode() == HttpStatus.OK) {
	            userReviews = reviewsResponse.getBody();
	        } else {
	            userReviews = Collections.emptyList(); // Handle no reviews case
	        }
	    } catch (Exception e) {
	        userReviews = Collections.emptyList(); // Handle any exception during fetching reviews
	    }

	    // Prepare the ModelAndView
	    ModelAndView mv = new ModelAndView();
	    mv.addObject("userReviews", userReviews);
	    mv.setViewName("reviews"); // Ensure this matches your JSP file name
	    return mv;
	}

//-----------------------//used for to get the total price before ordering in checkout page 
	@RequestMapping("/checkout")
	public ModelAndView checkout(HttpServletRequest request) {
	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        mv.addObject("error", "User ID not found in session");
	        mv.setViewName("error"); 
	        return mv;
	    }

	    // Fetch the BUYERSERVICE instance
	    List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
	    if (instances.isEmpty()) {
	        throw new RuntimeException("No instances of BUYERSERVICE available");
	    }
	    ServiceInstance serviceInstance = instances.get(0);
	    String baseUrl = serviceInstance.getUri().toString();

	    // Fetch updated cart items
	    String cartUrl = baseUrl + "/buyer/cart/" + userId;
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<List<CartProductDTO>> responseEntity = restTemplate.exchange(
	            cartUrl, 
	            HttpMethod.GET, 
	            null, 
	            new ParameterizedTypeReference<List<CartProductDTO>>() {}
	    );

	    List<CartProductDTO> cartItems = responseEntity.getBody();
	    double totalPrice = 0.0;

	    // Calculate total price
	    if (cartItems != null) {
	        for (CartProductDTO item : cartItems) {
	            totalPrice += item.getTotalPrice(); // Assuming getTotalPrice() returns the total for each cart item
	        }
	        mv.addObject("cartItems", cartItems);
	        mv.addObject("totalPrice", totalPrice); // Pass the total price to the view
	    } else {
	        mv.addObject("error", "Your cart is empty.");
	    }
	    mv.setViewName("checkout"); // Assuming your checkout view is named checkout.jsp
	    return mv;
	}
//------------
	//submit order
	@PostMapping("/submitorder")
	public ModelAndView submitOrder(@RequestParam("totalPrice") double totalPrice,
	                                 @RequestParam("paymentMode") String paymentMode,
	                                 @RequestParam("shoppingAddress") String shoppingAddress,
	                                 @RequestParam("phoneNumber") String phoneNumber,
	                                 @RequestParam("pincode") String pincode,
	                                 @RequestParam("city") String city,
	                                 @RequestParam("productId") List<Long> productIds, // Accept a List of productIds
	                                 HttpServletRequest request) {
	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        mv.addObject("error", "User ID not found in session");
	        mv.setViewName("error"); 
	        return mv;
	    }

	    // Create OrderDTO object and set its properties
	    OrderDTO orderDTO = new OrderDTO();
	    orderDTO.setTotalPrice(totalPrice);
	    orderDTO.setPaymentMode(paymentMode);
	    orderDTO.setShoppingAddress(shoppingAddress);
	    orderDTO.setPhoneNumber(phoneNumber);
	    orderDTO.setPincode(pincode);
	    orderDTO.setCity(city);
	    orderDTO.setUserId(userId);
	    orderDTO.setProductIds(productIds); // Set productIds as a list
	    orderDTO.setStatus("Pending");
	    orderDTO.setOrderDate(Timestamp.from(Instant.now())); 

	    // Discover the BUYERSERVICE instance
	    List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
	    if (instances.isEmpty()) {
	        mv.addObject("error", "No instances of BUYERSERVICE available");
	        mv.setViewName("error");
	        return mv;
	    }
	    ServiceInstance serviceInstance = instances.get(0);
	    String baseUrl = serviceInstance.getUri().toString();

	    try {
	        // Send order data to the BUYERSERVICE
	        String submitOrderUrl = baseUrl + "/buyer/createorder";
	        RestTemplate restTemplate = new RestTemplate();
	        HttpEntity<OrderDTO> requestEntity = new HttpEntity<>(orderDTO);
	        
	        // Use exchange instead of postForEntity
	        ResponseEntity<List<OrderDTO>> responseEntity = restTemplate.exchange(
	            submitOrderUrl,
	            HttpMethod.POST,
	            requestEntity,
	            new ParameterizedTypeReference<List<OrderDTO>>() {}
	        );

	        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
	            List<OrderDTO> createdOrders = responseEntity.getBody();
	            mv.addObject("message", "Order(s) placed successfully!");
	            mv.addObject("orders", createdOrders); // Add created orders to the model if needed
	        } else {
	            mv.addObject("error", "Failed to place order: " + responseEntity.getBody());
	        }
	    } catch (Exception e) {
	        mv.addObject("error", "An error occurred: " + e.getMessage());
	    }

	    mv.setViewName("orderConfirmation"); // Redirect to an order confirmation page
	    return mv;
	}
//------Complaints
	@RequestMapping("/submitcomplaint")
	public ModelAndView submitcomplaint(HttpServletRequest request,HttpServletResponse response,@RequestParam("username") String username,@RequestParam("complaintText") String comtext) {
		
		RestTemplate restTemplate=new RestTemplate();
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");

		   User user = new User();
		   user.setUserId(userId);
		
	 // User user =restTemplate.getForObject(baseUrl1,User.class);
		Complaint complaint=new Complaint();
		complaint.setUserName(username);
		complaint.setUser(user);
		complaint.setComplaintText(comtext);
		
	List<ServiceInstance> instances=discoveryClient.getInstances("BUYERSERVICE");
	ServiceInstance serviceInstance=instances.get(0);
	
	String baseUrl=serviceInstance.getUri().toString(); //return http://localhost:8080
	
	baseUrl=baseUrl+"/buyer/submitcomplaint";

	RestTemplate rs = new RestTemplate();

	String addToFav = rs.postForObject(baseUrl, complaint, String.class);
	System.out.println(addToFav);

			//.getBody();
	int i=0;
	ModelAndView mv= new ModelAndView();
	String message="success";
	mv.addObject("message",message);
	mv.setViewName("complaintraised");
	return mv;
}


}