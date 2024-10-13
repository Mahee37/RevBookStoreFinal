package com.revbookstoreclientapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse; // Missing import

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.revbookstoreclientapp.dto.ProductDTO;
import com.revbookstoreclientapp.dto.ProductProjection;
import com.revbookstoreclientapp.dto.Products;
import com.revbookstoreclientapp.dto.User;

@Controller
public class LoginSignupController {

    @Autowired
    private DiscoveryClient discoveryClient;
    
    
 //---Working
    
    @RequestMapping("/register")
    public ModelAndView userRegister(
                                     @RequestParam("email") String email, @RequestParam("password") String password,
                                     @RequestParam("name") String name, @RequestParam("phone_number") String phoneNumber,
                                     @RequestParam("address") String address, @RequestParam("pincode") String pincode,
                                     @RequestParam("userType") String userType) {
        ModelAndView mv = new ModelAndView();
        String status = "active";
        
        // Create a HashMap to store key-value pairs for user registration details
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("name", name);
        userDetails.put("email", email);
        userDetails.put("password", password);
        userDetails.put("phone_number", phoneNumber);
        userDetails.put("address", address);
        userDetails.put("pincode", pincode);
        userDetails.put("userType", userType);
        userDetails.put("status", status);
        
        String registered = null;
        
        try {
         
            String baseUrl = getBaseUrlForService(userType);
            
          
            RestTemplate restTemplate = new RestTemplate();
            
          
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // Set content type to form data
            
            // Convert the userDetails map to MultiValueMap
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.setAll(userDetails);  // Copy all key-value pairs from the map
            
           
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            
          
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                baseUrl, HttpMethod.POST, requestEntity, String.class
            );
            
            
            registered = responseEntity.getBody();
        } catch (Exception e) {
          
            mv.addObject("errorMessage", "Registration failed: " + e.getMessage());
            mv.setViewName("signup");
            return mv;
        }
        
       
        if (!"Registered".equalsIgnoreCase(registered)) {
            mv.addObject("errorMessage", "Registration failed.");
            mv.setViewName("signup");
        } else {
            mv.addObject("successMessage", "Registration successful.");
            mv.setViewName("login"); // Redirect to login after successful registration
        }

        return mv;
    }

    
    

    private String getBaseUrlForService(String userType) {
        List<ServiceInstance> instances;
        
        // Discover service instances based on userType
        if (userType.equalsIgnoreCase("admin")) {
            instances = discoveryClient.getInstances("ADMINSERVICE");
        } else if (userType.equalsIgnoreCase("buyer")) {
            instances = discoveryClient.getInstances("BUYERSERVICE");
        } else {
            instances = discoveryClient.getInstances("SELLERSERVICE");
        }
        
        // Ensure a service instance is available
        if (instances.isEmpty()) {
            throw new IllegalStateException("No service instance found for user type: " + userType);
        }
        
        // Construct the URL for the appropriate service
        return instances.get(0).getUri() + "/" + userType.toLowerCase() + "/register";
    }

    
  //-----------
    
    
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam("email") String email, @RequestParam("password") String password,
                              HttpSession session) {
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        ModelAndView mv = new ModelAndView();

        try {
            // Step 1: Authenticate user
            User us = authenticateUser(email, password);

            if (us == null) {
                System.err.println("User not found or invalid credentials.");
                mv.setViewName("index");
                return mv;
            }

            long userId = us.getUserId();
            System.out.println("User Type: " + us.getUserType());
            System.out.println("User Status: " + us.getStatus());

            // Step 2: Determine user type and fetch respective data
            switch (us.getUserType()) {
                case "buyer":
                    if ("active".equals(us.getStatus())) {
                        session.setAttribute("userId", userId);
                        session.setAttribute("user-role", us.getUserType());
                        session.setAttribute("name", us.getName());

                        // Fetch products for buyers
                        String productsUrl = getServiceUrl("BUYERSERVICE") + "/buyer/viewProducts";
                        System.out.println("Fetching Products from URL: " + productsUrl);

                        ResponseEntity<List<Products>> responseEntity = fetchProducts(productsUrl);
                        List<ProductDTO> productDTOs = convertToProductDTOs(responseEntity.getBody());

                        mv.addObject("productresult", productDTOs);
                        mv.setViewName("products");
                    } else {
                        mv.setViewName("index"); // User status is not active
                    }
                    break;

                case "seller":
                    if ("active".equals(us.getStatus())) {
                        session.setAttribute("userId", userId);
                        session.setAttribute("user-role", us.getUserType());
                        session.setAttribute("name", us.getName());

                        String baseUrl2 = getServiceUrl("SELLERSERVICE") + "/seller/viewProducts?userId=" + userId;

                        // Fetching products using projection
                        ResponseEntity<List<ProductProjection>> responseEntity = fetchSellerProducts(baseUrl2);
                        handleSellerProductsResponse(responseEntity, mv);
                    } else {
                        mv.setViewName("index"); // User status is not active
                    }
                    break;

                case "admin":
                    session.setAttribute("userId", userId);
                    session.setAttribute("user-role", us.getUserType());
                    session.setAttribute("name", us.getName());

                    List<Object> counts = fetchAdminCounts();
                    handleAdminCountsResponse(counts, mv);
                    break;

                default:
                    mv.setViewName("index");
                    break;
            }

        } catch (HttpClientErrorException e) {
            mv.setViewName("index");
        }

        return mv;
    }
    
    //--Authenticating the user for to get UserType
    private User authenticateUser(String email, String password) {
        // Check for buyers
        List<ServiceInstance> buyerInstances = discoveryClient.getInstances("BUYERSERVICE");
        if (!buyerInstances.isEmpty()) {
            String buyerBaseUrl = buyerInstances.get(0).getUri().toString() + "/buyer/login/" + email + "/" + password;
            RestTemplate restTemplate = new RestTemplate();
            User buyerUser = restTemplate.getForObject(buyerBaseUrl, User.class);
            if (buyerUser != null) {
                return buyerUser; // Return buyer if authenticated
            }
        }

        // Check for sellers
        List<ServiceInstance> sellerInstances = discoveryClient.getInstances("SELLERSERVICE");
        if (!sellerInstances.isEmpty()) {
            String sellerBaseUrl = sellerInstances.get(0).getUri().toString() + "/seller/login/" + email + "/" + password;
            RestTemplate restTemplate = new RestTemplate();
            User sellerUser = restTemplate.getForObject(sellerBaseUrl, User.class);
            if (sellerUser != null) {
                return sellerUser; // Return seller if authenticated
            }
        }

        // Check for admins
        List<ServiceInstance> adminInstances = discoveryClient.getInstances("ADMINSERVICE");
        if (!adminInstances.isEmpty()) {
            String adminBaseUrl = adminInstances.get(0).getUri().toString() + "/admin/login/" + email + "/" + password;
            RestTemplate restTemplate = new RestTemplate();
            User adminUser = restTemplate.getForObject(adminBaseUrl, User.class);
            if (adminUser != null) {
                return adminUser; // Return admin if authenticated
            }
        }

        return null; // Authentication failed
    }
    
    
    
    
    //-----Checking Service is Available or Not
    private String getServiceUrl(String serviceName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        if (instances.isEmpty()) {
            System.err.println("No instances of " + serviceName + " available.");
            return null;
        }
        return instances.get(0).getUri().toString();
    }
    
    
    
    //--TO Fetch The Buyer Inventory
    private ResponseEntity<List<Products>> fetchProducts(String productsUrl) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(productsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Products>>() {});
    }

    private List<ProductDTO> convertToProductDTOs(List<Products> products) {
        return products.stream().map(product -> {
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
    }
    
    
    
    
    
 //--To Fetch The Seller Inventory
    private ResponseEntity<List<ProductProjection>> fetchSellerProducts(String baseUrl) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductProjection>>() {});
    }

    private void handleSellerProductsResponse(ResponseEntity<List<ProductProjection>> responseEntity, ModelAndView mv) {
        List<ProductProjection> productProjections = responseEntity.getBody();
        if (productProjections != null && !productProjections.isEmpty()) {
            mv.addObject("products", productProjections);
            mv.setViewName("/seller-views/inventory");
        } else {
            mv.addObject("error", "No products found");
            mv.setViewName("/seller-views/inventory");
        }
    }
    
    
    

    
 //------Admin Actions 
    private List<Object> fetchAdminCounts() {
        List<ServiceInstance> instances = discoveryClient.getInstances("ADMINSERVICE");
        if (!instances.isEmpty()) {
            String baseUrl3 = instances.get(0).getUri().toString() + "/admin/login";
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(baseUrl3, List.class);
        }
        return null;
    }
    //--Fetching the RevBookStore Counts into the Dashboard
    
    @RequestMapping("/homepage")
    private void handleAdminCountsResponse(List<Object> counts, ModelAndView mv) {
        if (counts != null && counts.size() >= 4) {
            int noofcomplaint = (Integer) counts.get(0);
            int nooforder = (Integer) counts.get(1);
            int noofbuyer = (Integer) counts.get(2);
            int noofseller = (Integer) counts.get(3);

            mv.addObject("noofcomplaint", noofcomplaint);
            mv.addObject("nooforder", nooforder);
            mv.addObject("noofcustomer", noofbuyer);
            mv.addObject("noofproduct", noofseller);
            mv.setViewName("/admin/adminDashboard");
        } else {
            System.err.println("Invalid counts response: " + counts);
            mv.setViewName("index");
        }
    }



}
