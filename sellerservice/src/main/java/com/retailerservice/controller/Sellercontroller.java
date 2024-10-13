package com.retailerservice.controller;



import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import com.retailerservice.dto.ProductProjection;
import com.retailerservice.dto.ReviewDTO;
import com.retailerservice.dto.AddProductDto;
import com.retailerservice.dto.CustomProductProjection;
import com.retailerservice.dto.OrderDTO;
import com.retailerservice.dto.ReviewProjection;
import com.retailerservice.dto.UserProjection;
import com.retailerservice.dto.ProductWithReviews;
import com.retailerservice.entity.Products;
import com.retailerservice.entity.Review;
import com.retailerservice.entity.User;
import com.retailerservice.service.ProductReviewServiceInterface;
import com.retailerservice.service.ReviewServiceInterface;
import com.retailerservice.service.SellerAuthorizationInterface;
import com.retailerservice.service.SellerCartServiceInterface;
import com.retailerservice.service.SellerOrderServiceInterface;
import com.retailerservice.service.SellerServiceInterface;
import com.retailerservice.service.SellerfavServiceInterface;



@RestController
@RequestMapping("/seller")
public class Sellercontroller {


    @Autowired
    private  SellerServiceInterface productService;
    
    @Autowired
    private ReviewServiceInterface reviewService;
    
    
    @Autowired
    private ProductReviewServiceInterface previewService;
    
    @Autowired
    private SellerCartServiceInterface cartService;
    
    @Autowired
    private SellerfavServiceInterface favService;
    
    @Autowired
    private SellerAuthorizationInterface authService;
    
    @Autowired
    private SellerOrderServiceInterface orderService;
    
 //---Login and Register Section
    
    @PostMapping("/register")
	public ResponseEntity<String> registerSeller(@RequestParam Map<String, String> userDetails) {
	    try {
	       
	        User seller = new User();
	        seller.setName(userDetails.get("name"));
	        seller.setEmail(userDetails.get("email"));
	        seller.setPassword(userDetails.get("password"));
	        seller.setPhoneNumber(userDetails.get("phone_number"));
	        seller.setAddress(userDetails.get("address"));
	        seller.setPincode(userDetails.get("pincode"));
	        seller.setUserType("seller");  
	        seller.setStatus("active"); 

	      
	        authService.register(seller);

	       
	        return new ResponseEntity<>("Registered", HttpStatus.OK);
	    } catch (Exception e) {
	       
	        return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
    
    @GetMapping("/login/{email}/{password}")
	public ResponseEntity<User> sellerlogin(@PathVariable("email") String email, @PathVariable("password") String password) {
		
		System.out.println("rest" +email);
		System.out.println("rest" +password);
		
		UserProjection userProjection = authService.login(email, password);
	    
	    if (userProjection != null) {
	        // Manually map the projection to a User entity if needed
	        User user = new User();
	        user.setUserId(userProjection.getUserId());  // Assuming UserProjection has getUserId()
	        user.setEmail(userProjection.getEmail());    // Assuming UserProjection has getEmail()
	        user.setName(userProjection.getName());      // Assuming UserProjection has getName()
	        user.setUserType(userProjection.getUserType());
	        user.setStatus(userProjection.getStatus());
	        // Map other fields as necessary
	        
	        return new ResponseEntity<>(user, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	    }
	}
    
    
    
    
    
    
    
    //-----------------------------------
    
    @GetMapping("/viewProducts")
    public ResponseEntity<List<ProductProjection>> viewProducts(@RequestParam("userId") Long userId) {
        
        System.out.println("Received request to view products for userId: " + userId);

        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<ProductProjection> products = productService.viewProducts(userId);

        if (products != null && !products.isEmpty()) {
            System.out.println("Products found: " + products.size());
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            String error = "No products found for this seller";
            System.out.println(error);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    
//----------------------------------------------------------------------
   
//     //from Client Controller Iam getting this
//    //we are using post mapping to handle multiple parameters in the requestBody
//    //? wild card entry decides the output response /return type
    @PostMapping("/deleteProduct")
    public ResponseEntity<List<ProductProjection>> deleteProduct(@RequestBody Map<String, Object> request) {
        try {
            System.out.println("Incoming request: " + request);

            // Extract productId and sellerId from the request body
            Long productId = request.containsKey("productId") ? ((Number) request.get("productId")).longValue() : null;
            Long sellerId = request.containsKey("sellerId") ? ((Number) request.get("sellerId")).longValue() : null;

            // Missing fields checking
            if (productId == null || sellerId == null) {
                System.out.println("Product or seller ID is missing");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.emptyList());
            }

            // Fetch product by ID
            Products product = productService.getProductById(productId);
            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.emptyList());
            }

            // Check seller authorization
            if (!product.getUser().getUserId().equals(sellerId)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.emptyList());
            }

            // Cascade Deletions - handle related entities like cart, favorites, and reviews
            cartService.deleteCartByProductId(productId);
            System.out.println("Deleted related shopping cart entries for product: " + productId);

            favService.deletefavByProductId(productId);
            System.out.println("Deleted related favorite entries for product: " + productId);

            previewService.deletereviewByProductId(productId);
            System.out.println("Deleted related reviews for product: " + productId);
            
            orderService.deleteorderByProductId(productId);
            System.out.println("Deleted related reviews for product: " + productId);

            // Delete the product itself
            productService.deleteProductById(productId);
            System.out.println("Product deleted: " + productId);

            // Retrieve updated products for the seller
            List<ProductProjection> updatedProducts = productService.getProductsBySellerId(sellerId);
            if (updatedProducts == null || updatedProducts.isEmpty()) {
                System.out.println("No products found for seller after deletion.");
                return ResponseEntity.ok(Collections.emptyList());
            }

            System.out.println("Updated products: " + updatedProducts);
            return ResponseEntity.ok(updatedProducts);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }



//----------------------------------------------------------
    
    
    @GetMapping("/ProductReviews")
    public ResponseEntity<Object> viewProductReviews(@RequestParam("userId") Long sellerId) {
        try {
            System.out.println("UserID in REST controller: " + sellerId);

            // Fetch product projections based on seller ID
            List<CustomProductProjection> productProjections = reviewService.getProductsBySellerId(sellerId);

            if (productProjections.isEmpty()) {
                return ResponseEntity.ok("No products available for the seller.");
            }

            // Extract product IDs
            List<Long> productIds = productProjections.stream().map(CustomProductProjection::getProductId).collect(Collectors.toList());
            System.out.println("Product IDs size: " + productIds.size());

            // Fetch reviews for the products
            List<ReviewProjection> reviewProjections = reviewService.getReviewsByProductId(productIds);
            System.out.println("Reviews found: " + reviewProjections.size());

            // Map reviews by product ID
            Map<Long, List<Review>> reviewsByProductId = reviewProjections.stream()
                .collect(Collectors.groupingBy(
                    ReviewProjection::getProductId,
                    Collectors.mapping(
                        reviewProj -> {
                            Review review = new Review();
                            review.setReviewId(reviewProj.getReviewId());
                            review.setProductId(reviewProj.getProductId());
                            review.setUserId(reviewProj.getUserId());
                            review.setReviewText(reviewProj.getReviewText());
                            review.setRating(reviewProj.getRating());
                            return review;
                        },
                        Collectors.toList()
                    )
                ));


            List<ProductWithReviews> productsWithReviews = productProjections.stream().map(prod -> {
                ProductWithReviews product = new ProductWithReviews();
                product.setProductId(prod.getProductId());
                product.setProductName(prod.getProductName());
                product.setProductDescription(prod.getProductDescription());
                product.setProductCategory(prod.getProductCategory());
                product.setReviews(reviewsByProductId.getOrDefault(prod.getProductId(), Collections.emptyList()));
                return product;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(productsWithReviews);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching reviews: " + e.getMessage());
        }
    }
    
    
 //------------------------------------------------------
    @PostMapping("/addProduct/{userId}")
    public ResponseEntity<Object> addProduct(@PathVariable("userId") Long userId, @RequestBody AddProductDto addProductDto) {
        System.out.println("Received request to add product for userId: " + userId);

        // Create a Products entity from the DTO
        Products product = new Products();
        product.setUserId(userId); // Assuming userId is part of the Products entity
        product.setProductName(addProductDto.getName());
        product.setProductDescription(addProductDto.getDescription());
        product.setProductCategory(addProductDto.getCategory());
        product.setPrice(addProductDto.getPrice());
        product.setDiscountPrice(addProductDto.getDiscountPrice());
        product.setQuantity(addProductDto.getQuantity());
        product.setImageUrl(addProductDto.getImageUrl());

        // Add product using the service
        Optional<Products> optionalProduct = productService.addProduct(product);

        if (optionalProduct.isPresent()) {
            Products addedProduct = optionalProduct.get();
            // Create a response DTO to return only necessary fields
            AddProductDto responseDto = new AddProductDto();
            responseDto.setName(addedProduct.getProductName());
            responseDto.setDescription(addedProduct.getProductDescription());
            responseDto.setCategory(addedProduct.getProductCategory());
            responseDto.setPrice(addedProduct.getPrice());
            responseDto.setDiscountPrice(addedProduct.getDiscountPrice());
            responseDto.setQuantity(addedProduct.getQuantity());
            responseDto.setImageUrl(addedProduct.getImageUrl());

            return new ResponseEntity<>(responseDto, HttpStatus.CREATED); // Return the DTO
        } else {
            return new ResponseEntity<>("Product could not be added", HttpStatus.BAD_REQUEST);
        }
    }

//-----------------------------
    @PostMapping("/edit/{productId}")
    public ResponseEntity<Object> editProduct(@PathVariable("productId") long productId, @RequestBody AddProductDto addProductDto) {
        System.out.println("Received request to edit product with productId: " + productId);

        // Create a Products entity from the DTO
        Products product = new Products();
        product.setProductId(productId); // Set the product ID
        product.setProductName(addProductDto.getName());
        product.setProductDescription(addProductDto.getDescription());
        product.setProductCategory(addProductDto.getCategory());
        product.setPrice(addProductDto.getPrice());
        product.setDiscountPrice(addProductDto.getDiscountPrice());
        product.setQuantity(addProductDto.getQuantity());
        product.setImageUrl(addProductDto.getImageUrl());

        // Update product using the service
        boolean productEdited = productService.editProduct(product);
        
        if (productEdited) {
            // Create a response DTO to return only necessary fields
            AddProductDto responseDto = new AddProductDto();
            responseDto.setName(product.getProductName());
            responseDto.setDescription(product.getProductDescription());
            responseDto.setCategory(product.getProductCategory());
            responseDto.setPrice(product.getPrice());
            responseDto.setDiscountPrice(product.getDiscountPrice());
            responseDto.setQuantity(product.getQuantity());
            responseDto.setImageUrl(product.getImageUrl());

            return ResponseEntity.ok(responseDto); // Return the DTO
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }

    //--------------
    @PostMapping("/updateStatus/{orderId}/{status}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable("orderId") long orderId,@PathVariable("status") String status){
    	
    	System.out.println(orderId);
    	System.out.println(status);
    	
    	boolean updated = orderService.updateStatus(orderId,status);
    	String message = "fail";
    	if(updated) {
    		message = "pass";
    		return ResponseEntity.ok(message);
    	}else {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    	}
    }
    
 //
    @GetMapping("/viewOrders/{userId}")
	public ResponseEntity<List<OrderDTO>> viewOrderByHistory(@PathVariable("userId") long userId) {
	    List<OrderDTO> orderDTOs = orderService.viewOrders(userId); // This now returns a List<OrderDTO>

	    if (orderDTOs != null && !orderDTOs.isEmpty()) {
	        return new ResponseEntity<>(orderDTOs, HttpStatus.OK); 
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
    
    //-----Fetch ProductId to Edit
    @GetMapping("/geteditproductId/{productId}")
    public ResponseEntity<ProductProjection> getEditProductById(@PathVariable Long productId) {
        ProductProjection product = productService.getEditProductById(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
 //------- 
    
    @GetMapping("/getSellerAllReviewsByUserId/{userId}")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsByUserId(@PathVariable Long userId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByUserId(userId);
        if (reviews != null && !reviews.isEmpty()) {
            return ResponseEntity.ok(reviews);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
	
}

