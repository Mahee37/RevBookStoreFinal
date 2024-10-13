package com.revbookstoreclientapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.revbookstoreclientapp.dto.AddProductDto;
import com.revbookstoreclientapp.dto.OrderDTO;
import com.revbookstoreclientapp.dto.OrderProjection;
import com.revbookstoreclientapp.dto.ProductDTO;
import com.revbookstoreclientapp.dto.ProductProjection;
import com.revbookstoreclientapp.dto.ProductWithReviews;
import com.revbookstoreclientapp.dto.Products;
import com.revbookstoreclientapp.dto.ReviewDTO;



@Controller
public class ClientSellerController {

    @Autowired
    private DiscoveryClient dclient;

    @Autowired
    private RestTemplate restTemplate;
    
    @GetMapping("/inventory")
	public ModelAndView viewProducts(HttpSession session ) {
        ModelAndView mv = new ModelAndView();
		Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        mv.addObject("error", "User ID not found in session");
	        mv.setViewName("error"); 
	        return mv;
	    }
        List<ServiceInstance> instances = dclient.getInstances("SELLERSERVICE");
        if (instances.isEmpty()) {
            mv.addObject("error", "SELLERSERVICE instance not available");
            mv.setViewName("error");
            return mv;
        }

        // First instance
        ServiceInstance serviceInstance = instances.get(0);
        String baseUrl = serviceInstance.getUri().toString() + "/seller/viewProducts?userId=" + userId;
        System.out.println("Requesting from URL: " + baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Make the GET request
            ResponseEntity<Products[]> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Products[].class);
            Products[] productsArray = response.getBody();
            
            System.out.println("Products fetched: " + Arrays.toString(productsArray));

            if (response.getStatusCode().is2xxSuccessful() && productsArray != null) {
                List<Products> products = Arrays.asList(productsArray);
                
                mv.addObject("products", products);
                mv.setViewName("/seller-views/inventory"); 
            } else {
                mv.addObject("error", "Failed to fetch the products");
                mv.setViewName("/seller-views/inventory");
            }
        } catch (Exception e) {
            mv.addObject("error", "An error occurred while fetching products: " + e.getMessage());
            mv.setViewName("error");
            e.printStackTrace(); 
        }

        return mv;
    }
    
	
//-----------------------------------------------------------------------
    @PostMapping("/RemoveProduct")
    public ModelAndView deleteProduct(@RequestParam("productId") Long productId, HttpSession session) {
        ModelAndView mv = new ModelAndView();

        // Fetch the seller ID from session
        Long sellerId = (Long) session.getAttribute("userId");
        if (sellerId == null) {
            mv.addObject("error", "Seller ID not found. Please log in.");
            mv.setViewName("error");
            return mv;
        }

        // Fetch service instances for SELLERSERVICE using DiscoveryClient
        List<ServiceInstance> instances = dclient.getInstances("SELLERSERVICE");
        if (instances.isEmpty()) {
            mv.addObject("error", "Seller service is currently unavailable. Please try again later.");
            mv.setViewName("error");
            return mv;
        }

        ServiceInstance serviceInstance = instances.get(0);
        String baseUrl = serviceInstance.getUri().toString() + "/seller/deleteProduct";
        System.out.println("Base URL: " + baseUrl);

        // Create headers and request body for the REST call
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);
        requestBody.put("sellerId", sellerId);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Make the POST request to delete the product
            ResponseEntity<List<ProductProjection>> response = restTemplate.exchange(
                    baseUrl,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<List<ProductProjection>>() {}
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                List<ProductProjection> updatedProducts = response.getBody();
                if (updatedProducts != null && !updatedProducts.isEmpty()) {
                    mv.addObject("products", updatedProducts); // Add updated products to the model
                    mv.setViewName("/seller-views/inventory");
                } else {
                    mv.addObject("message", "Product deleted successfully, but no products are left in your inventory.");
                    mv.setViewName("/seller-views/inventory");
                }
            } else {
                mv.addObject("error", "Failed to delete the product. Please check the product ID or your authorization.");
                mv.setViewName("error");
            }
        } catch (Exception e) {
            mv.addObject("error", "An unexpected error occurred: " + e.getMessage());
            mv.setViewName("error");
        }

        return mv;
    }



//------------------------------------------------------------------------
	    @GetMapping("/GetReviews")
	    public ModelAndView viewProductReviews(HttpSession session) {
	        ModelAndView mv = new ModelAndView();
	        
	        
	        Long sellerId = (Long) session.getAttribute("userId");  
			
	        if (sellerId == null) {
	            mv.addObject("error", "Seller ID not found");
	            mv.setViewName("error");
	            return mv;
	        }

	        
	        System.out.println("Using hardcoded sellerId: " + sellerId);

	        List<ServiceInstance> instances = dclient.getInstances("SELLERSERVICE");
	        if (instances.isEmpty()) {
	            mv.addObject("error", "Service instance not available");
	            mv.setViewName("error");
	            return mv;
	        }

	        ServiceInstance serviceInstance = instances.get(0);
	        String baseUrl = serviceInstance.getUri().toString();
	        String productReviewsUrl = baseUrl + "/seller/ProductReviews?userId=" + sellerId;

	        HttpHeaders headers = new HttpHeaders();
	        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	        HttpEntity<String> entity = new HttpEntity<>(headers);

	        try {
	            // Fetch products with reviews from the service
	            ResponseEntity<ProductWithReviews[]> response = restTemplate.exchange(productReviewsUrl, HttpMethod.GET, entity, ProductWithReviews[].class);

	            ProductWithReviews[] productsWithReviews = response.getBody();
	            System.out.println("Products with reviews in client side: " + (productsWithReviews != null ? productsWithReviews.length : 0));

	            if (response.getStatusCode().is2xxSuccessful() && productsWithReviews != null && productsWithReviews.length > 0) {
	                mv.addObject("products", productsWithReviews);
	                mv.setViewName("/seller-views/productReviews");
	            } else {
	                mv.addObject("message", "No products or reviews found.");
	                mv.setViewName("error");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            mv.addObject("error", "Error fetching data from service: " + e.getMessage());
	            mv.setViewName("error");
	        }

	        return mv;
	    }
 //----------------------------------------------------------
	    @PostMapping("/addProduct")
	    public ModelAndView addProduct(@RequestParam("name") String name,
	                                   @RequestParam("description") String description,
	                                   @RequestParam("category") String category,
	                                   @RequestParam("price") String price,
	                                   @RequestParam("discount_price") String discount_price,
	                                   @RequestParam("quantity") String quantity,
	                                   @RequestParam("imageUrl") String imageUrl,
	                                   HttpSession session) {
	        
	        ModelAndView mv = new ModelAndView();

	        Long userId = (Long) session.getAttribute("userId");

	        if (userId == null) {
	            mv.addObject("error", "Seller ID not found");
	            mv.setViewName("error");
	            return mv;
	        }

	        // Create an instance of AddProductDto
	        AddProductDto addProductDto = new AddProductDto();
	        addProductDto.setName(name);
	        addProductDto.setDescription(description);
	        addProductDto.setCategory(category);
	        
	        // Validate and set price
	        double parsedPrice;
	        try {
	            parsedPrice = Double.parseDouble(price);
	            addProductDto.setPrice(parsedPrice);  // Set price in DTO
	        } catch (NumberFormatException e) {
	            mv.addObject("error", "Invalid price format");
	            mv.setViewName("add.jsp");
	            return mv;
	        }

	        // Validate and set discount price
	        if (discount_price != null && !discount_price.isEmpty()) {
	            try {
	                double discountPrice = Double.parseDouble(discount_price);
	                addProductDto.setDiscountPrice(discountPrice);  // Set discount price in DTO
	            } catch (NumberFormatException e) {
	                mv.addObject("error", "Invalid discount price format");
	                mv.setViewName("add.jsp");
	                return mv;
	            }
	        }

	        // Validate and set quantity
	        long parsedQuantity;
	        try {
	            parsedQuantity = Long.parseLong(quantity);
	            addProductDto.setQuantity(parsedQuantity);  // Set quantity in DTO
	        } catch (NumberFormatException e) {
	            mv.addObject("error", "Invalid quantity format");
	            mv.setViewName("add.jsp");
	            return mv;
	        }

	        addProductDto.setImageUrl(imageUrl);


	        // Prepare the REST API call to add the product
	        List<ServiceInstance> instances = dclient.getInstances("SELLERSERVICE");
	        if (instances.isEmpty()) {
	            mv.addObject("error", "SELLERSERVICE instance not available");
	            mv.setViewName("error");
	            return mv;
	        }

	        ServiceInstance serviceInstance = instances.get(0);
	        String baseUrl = serviceInstance.getUri().toString() + "/seller/addProduct/"+userId;

	        RestTemplate restTemplate = new RestTemplate();
	        try {
	            // Send the product data as an AddProductDto
	            Products productAdded = restTemplate.postForObject(baseUrl, addProductDto, Products.class);
	            if (productAdded != null) {
	                // Fetch updated product list
	                String viewProductsUrl = serviceInstance.getUri().toString() + "/seller/viewProducts?userId=" + userId;
	                ResponseEntity<ProductProjection[]> response = restTemplate.getForEntity(viewProductsUrl, ProductProjection[].class);

	                if (response.getStatusCode().is2xxSuccessful()) {
	                    mv.addObject("products", Arrays.asList(response.getBody()));
	                    mv.setViewName("/seller-views/inventory");
	                } else {
	                    mv.addObject("error", "Failed to load products after addition");
	                    mv.setViewName("error");
	                }
	            } else {
	                mv.addObject("error", "Something went wrong");
	                mv.setViewName("add");
	            }
	        } catch (Exception e) {
	            mv.addObject("error", "An error occurred: " + e.getMessage());
	            mv.setViewName("error");
	        }

	        return mv;
	    }


//------------------------------
	    @PostMapping("/editProducts")
	    public ModelAndView editProducts(
	            @RequestParam("productId") Long productId, 
	            @RequestParam("name") String name,
	            @RequestParam("description") String description,
	            @RequestParam("category") String category,
	            @RequestParam("price") String price,
	            @RequestParam("discountPrice") String discountPrice, // Updated here
	            @RequestParam("quantity") String quantity,
	            @RequestParam("imageUrl") String imageUrl,
	            HttpSession session) {

	        ModelAndView mv = new ModelAndView();

	        // Check for userId in session
	        Long userId = (Long) session.getAttribute("userId");  
	        if (userId == null) {
	            mv.addObject("error", "Seller ID not found");
	            mv.setViewName("error");
	            return mv;
	        }

	        System.out.println("Received Product ID: " + productId);
	        System.out.println("Received Name: " + name);
	        System.out.println("Received Description: " + description);
	        System.out.println("Received Category: " + category);
	        System.out.println("Received Price: " + price);
	        System.out.println("Received Discount Price: " + discountPrice); // Updated here
	        System.out.println("Received Quantity: " + quantity);
	        System.out.println("Received Image URL: " + imageUrl);

	        // Create an instance of AddProductDto
	        AddProductDto addProductDto = new AddProductDto();
	        addProductDto.setName(name);
	        addProductDto.setDescription(description);
	        addProductDto.setCategory(category);
	        addProductDto.setImageUrl(imageUrl);
	        
	        // Parse price and quantity
	        try {
	            double parsedPrice = Double.parseDouble(price);
	            addProductDto.setPrice(parsedPrice);

	            if (discountPrice != null && !discountPrice.isEmpty()) { // Updated here
	                double parsedDiscountPrice = Double.parseDouble(discountPrice); // Updated here
	                addProductDto.setDiscountPrice(parsedDiscountPrice);
	            }

	            long parsedQuantity = Long.parseLong(quantity);
	            addProductDto.setQuantity(parsedQuantity);
	        } catch (NumberFormatException e) {
	            mv.addObject("error", "Invalid number format");
	            mv.addObject("product", addProductDto); // Add the current product details
	            mv.setViewName("/seller-views/edit-product"); // Redirect back to the edit page
	            return mv;
	        }

	        // Communicating with SELLERSERVICE
	        List<ServiceInstance> instances = dclient.getInstances("SELLERSERVICE");
	        if (instances.isEmpty()) {
	            mv.addObject("error", "SELLERSERVICE is not available");
	            mv.addObject("product", addProductDto); // Add the current product details
	            mv.setViewName("/seller-views/edit-product"); // Redirect back to the edit page
	            return mv;
	        }

	        ServiceInstance serviceInstance = instances.get(0);
	        String baseUrl = serviceInstance.getUri().toString();
	        String url = baseUrl + "/seller/edit/" + productId;

	        RestTemplate rest = new RestTemplate();
	        try {
	            // Send the updated product data as an AddProductDto
	            Products productEdited = rest.postForObject(url, addProductDto, Products.class);

	            // Handling the response from SELLERSERVICE
	            if (productEdited != null) {
	                String viewProductsUrl = baseUrl + "/seller/viewProducts?userId=" + userId;
	                ResponseEntity<ProductProjection[]> response = rest.getForEntity(viewProductsUrl, ProductProjection[].class);

	                if (response.getStatusCode().is2xxSuccessful()) {
	                    mv.addObject("products", Arrays.asList(response.getBody()));
	                    mv.setViewName("/seller-views/inventory");
	                } else {
	                    mv.addObject("error", "Failed to load products after edit");
	                    mv.setViewName("error");
	                }
	            } else {
	                mv.addObject("error", "Something went wrong while editing the product");
	                mv.addObject("product", addProductDto); // Add the current product details
	                mv.setViewName("/seller-views/edit-product"); // Redirect back to the edit page
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); // Print the stack trace for debugging
	            mv.addObject("error", "An error occurred: " + e.getMessage());
	            mv.addObject("product", addProductDto); // Add the current product details
	            mv.setViewName("/seller-views/edit-product"); // Redirect back to the edit page
	        }

	        return mv;
	    }



//-----------
	    @GetMapping("/getOrders")
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

			List<ServiceInstance> instances = dclient.getInstances("SELLERSERVICE");
			ServiceInstance serviceInstance = instances.isEmpty() ? null : instances.get(0);
			String baseUrl = serviceInstance != null ? serviceInstance.getUri().toString() + "/seller/viewOrders/" + userId: "";

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
					mv.addObject("message", "No orders found for this user.");
					mv.setViewName("/seller-views/orderscheck");
					return mv;
				}
			} catch (Exception e) {
				// Log the exception
				System.err.println("Error fetching orders: " + e.getMessage());
				mv.addObject("message", "Error fetching orders.");
				mv.setViewName("/seller-views/orderscheck");
				return mv;
			}

			if (orderList != null && !orderList.isEmpty()) {
				mv.addObject("orders", orderList);
				mv.setViewName("/seller-views/orderscheck");
			} else {
				mv.addObject("message", "No orders found for this user.");
				mv.setViewName("/seller-views/orderscheck");
			}

			return mv;
		}
	   
//yet to work on this
	    
	    @RequestMapping("/updateStatus")
		public ModelAndView UpdateStatus(@RequestParam("orderId") long orderId , @RequestParam("status") String status) {
			ModelAndView mv = new ModelAndView();
			List<ServiceInstance> instances =dclient.getInstances("SELLERSERVICE");
			ServiceInstance serviceInstance = instances.get(0);
			
			String baseUrl = serviceInstance.getUri().toString();
			baseUrl = baseUrl + "/seller/updateStatus/" +orderId+"/"+status;
			
			RestTemplate rest = new RestTemplate();
			String productEdited = rest.postForObject(baseUrl, null, String.class);
			
			List<ServiceInstance> instances1 =dclient.getInstances("SELLERSERVICE");
			ServiceInstance serviceInstance1 = instances1.get(0);
			
			String baseUrl1 = serviceInstance1.getUri().toString();
			baseUrl1 = baseUrl1 + "/seller/viewOrders";
			RestTemplate rest1 = new RestTemplate();
			List<OrderProjection> orders = rest1.getForObject(baseUrl1, List.class);
			
			if(orders!=null) {
				mv.addObject("orders",orders);
				mv.setViewName("/seller-views/orderscheck");
				return mv;
			}else {
				String err = "Something went wrong";
				mv.addObject("error",err);
				mv.setViewName("add");
				return mv;
			}

		}
	    
//--Edit Product ProductId Fetch Assistance
	    
	    @GetMapping("/editProductId")
	    public ModelAndView GetProductIdForEdit(@RequestParam("productId") Long productId) {
	        // Build the base URL for SELLERSERVICE from service discovery
	        List<ServiceInstance> instances = dclient.getInstances("SELLERSERVICE");
	        if (instances.isEmpty()) {
	            throw new RuntimeException("No instances of SELLERSERVICE available");
	        }
	        ServiceInstance serviceInstance = instances.get(0);
	        String baseUrl = serviceInstance.getUri().toString();

	        String productUrl = baseUrl + "/seller/geteditproductId/" + productId;
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<ProductProjection> responseEntity = restTemplate.getForEntity(productUrl, ProductProjection.class);

	        ProductProjection product = responseEntity.getBody();

	        // Check if the product exists
	        if (product == null) {
	            ModelAndView errorModelAndView = new ModelAndView("error");
	            errorModelAndView.addObject("error", "Product not found");
	            return errorModelAndView;
	        }

	        ModelAndView modelAndView = new ModelAndView("/seller-views/edit-product");
	        modelAndView.addObject("product", product);
	        return modelAndView;
	    }
	    
//-------------
	    @RequestMapping("/getSellerReviewsByUserId")
		public ModelAndView getReviewsByUserId(HttpSession session) {
		    ModelAndView mv = new ModelAndView();
		  
			Long userId = (Long) session.getAttribute("userId");

		    if (userId == null) {
		        mv.addObject("error", "User ID not found in session");
		        mv.setViewName("error"); 
		        return mv;
		    }
		    List<ServiceInstance> instances = dclient.getInstances("SELLERSERVICE");
		    if (instances.isEmpty()) {
		        mv.addObject("error", "No instances of SELLERSERVICE available.");
		        mv.setViewName("error");
		        return mv;
		    }

		    ServiceInstance serviceInstance = instances.get(0);
		    String baseUrl = serviceInstance.getUri().toString();

		    try {
		        String fetchReviewsUrl = baseUrl + "/seller/getSellerAllReviewsByUserId/" + userId;
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

		    mv.setViewName("/seller-views/sellerreviews"); // Ensure this matches your JSP file
		    return mv;
		}


}