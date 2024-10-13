//package com.revbookstoreclientapp.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.revbookstoreclientapp.dto.Favorites;
//import com.revbookstoreclientapp.dto.FavoritesProjection;
//import com.revbookstoreclientapp.dto.Products;
//import com.revbookstoreclientapp.dto.ShoppingCart;
//import com.revbookstoreclientapp.dto.ShoppingCartProjection;
//import com.revbookstoreclientapp.dto.User;
//
//
//
//@Controller
//@RequestMapping("/buyer")
//public class CustomerController {
//	
//	@Autowired
//	private DiscoveryClient discoveryClient;
//	
//	@RequestMapping("/addProductsToCarts")
//	public ModelAndView addProductsToCarts(HttpServletRequest request, HttpServletResponse response)
//	{
//				
//		ModelAndView mv = new ModelAndView();
//		
//		long productId = Long.parseLong(request.getParameter("productId"));
//		String discount_Price = request.getParameter("discountPrice");
//		double discountPrice = Double.parseDouble(discount_Price);
//		String total_Price = request.getParameter("totalPrice");
//		long totalPrice = Long.parseLong(total_Price);
//		String productName = request.getParameter("productName");
//		String productDescription = request.getParameter("productDescription");
//		HttpSession hs = request.getSession();
//		ShoppingCart addToCart=null;
//		if((Long) hs.getAttribute("userId") == null)
//		{
//			mv.setViewName("login.jsp");
//		}else
//		{
//			long userId = (long) hs.getAttribute("userId");
//			
//			List<ServiceInstance> instances1 = discoveryClient.getInstances("RETAILERSERVICE");
//			ServiceInstance serviceInstance1 = instances1.get(0);
//
//			String baseUrl1 = serviceInstance1.getUri().toString(); // return http://localhost:8080
//
//			baseUrl1 = baseUrl1 + "/buyer/getUser/"+userId;
//			
//			RestTemplate restTemplate2 = new RestTemplate();
//			User user = restTemplate2.getForObject(baseUrl1, User.class);
//			
//			//User uu= new User();
//			List<ServiceInstance> instances2 = discoveryClient.getInstances("RETAILERSERVICE");
//			ServiceInstance serviceInstance2 = instances2.get(0);
//
//			String baseUrl2 = serviceInstance2.getUri().toString(); // return http://localhost:8080
//
//			baseUrl2 = baseUrl2 + "/buyer/getProduct/"+productId;
//			
//			RestTemplate restTemplate1 = new RestTemplate();
//			Products product = restTemplate1.getForObject(baseUrl1, Products.class);
//			
//			ShoppingCart cart = new ShoppingCart();
//			cart.setCartId(1);
//			cart.setProduct(product);
//			cart.setUser(user);
//			cart.setProductDescription(productDescription);
//			cart.setProductName(productName);
//			cart.setQuantity(1);
//			cart.setTotalPrice(totalPrice);
//			cart.setDiscountPrice(discountPrice);
//			
//			List<ServiceInstance> instances = discoveryClient.getInstances("RETAILERSERVICE");
//			ServiceInstance serviceInstance = instances.get(0);
//
//			String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
//
//			baseUrl = baseUrl + "/buyer/addProductsToCarts/"+userId;
//			
//			RestTemplate restTemplate = new RestTemplate();
//			addToCart = restTemplate.postForObject(baseUrl, cart, ShoppingCart.class);
//		}
//		
//		if(addToCart!=null)
//		{
//			mv.addObject("products", addToCart);
//			mv.setViewName("/cart.jsp");
//		}
//		else 
//		{
//			mv.addObject("message", "Failed to add product to cart");
//			mv.setViewName("index.jsp");
//		}
//		return mv;
//		
//	}
//	
//	@RequestMapping("/addProductToFavorite")
//	public ModelAndView addProductToFavorite(HttpServletRequest request,HttpServletResponse response)
//	{
//		ModelAndView mv = new ModelAndView();
//		
//		long productId = Long.parseLong(request.getParameter("productId"));
//		String discount_Price = request.getParameter("discountPrice");
//		long discountPrice = Long.parseLong(discount_Price);
//		String total_Price = request.getParameter("totalPrice");
//		long totalPrice = Long.parseLong(total_Price);
//		String productName = request.getParameter("productName");
//		String productDescription = request.getParameter("productDescription");
//		HttpSession hs = request.getSession();
//		
//		long userId = (long) hs.getAttribute("userId");
//		
//		List<ServiceInstance> instances1 = discoveryClient.getInstances("RETAILERSERVICE");
//		ServiceInstance serviceInstance1 = instances1.get(0);
//
//		String baseUrl1 = serviceInstance1.getUri().toString(); // return http://localhost:8080
//
//		baseUrl1 = baseUrl1 + "/buyer/getUser/"+userId;
//		
//		RestTemplate restTemplate2 = new RestTemplate();
//		User user = restTemplate2.getForObject(baseUrl1, User.class);
//		
//		List<ServiceInstance> instances2 = discoveryClient.getInstances("RETAILERSERVICE");
//		ServiceInstance serviceInstance2 = instances2.get(0);
//
//		String baseUrl2 = serviceInstance2.getUri().toString(); // return http://localhost:8080
//
//		baseUrl2 = baseUrl2 + "/buyer/getProduct/"+productId;
//		
//		RestTemplate restTemplate1 = new RestTemplate();
//		Products product = restTemplate1.getForObject(baseUrl1, Products.class);
//		
//		Favorites fav = new Favorites();
//		fav.setProductName(productName);
//		fav.setProductDescription(productDescription);
//		fav.setPrice(totalPrice);
//		fav.setDiscountPrice(discountPrice);
//		fav.setProduct(product);
//		fav.setUser(user);
//		
//		List<ServiceInstance> instances = discoveryClient.getInstances("RETAILERSERVICE");
//		ServiceInstance serviceInstance = instances.get(0);
//
//		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
//
//		baseUrl = baseUrl + "/buyer/addProductToFavorite/" + userId;
//		
//		RestTemplate restTemplate = new RestTemplate();
//		String addToFav = restTemplate.postForObject(baseUrl, fav, String.class);
//		
//		if(addToFav == "Item added successfully")
//		{
//			mv.addObject("message", "Item added successfully");
//			mv.setViewName("products.jsp");
//		}
//		else
//		{
//			mv.addObject("message", "Failed to add item");
//			mv.setViewName("products.jsp");
//		}			
//		return mv;
//	}
//	
//	@RequestMapping("/cart")
//	public ModelAndView viewCartProducts(HttpServletRequest request,HttpServletResponse response)
//	{
//		ModelAndView mv = new ModelAndView();
//		HttpSession hs = request.getSession();
////		long userId = (long) hs.getAttribute("userId");
//		long userId =203l;
//		List<ServiceInstance> instances = discoveryClient.getInstances("RETAILERSERVICE");
//		ServiceInstance serviceInstance = instances.get(0);
//
//		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
//
//		baseUrl = baseUrl + "/buyer/cart/" + userId;
//		
//		RestTemplate restTemplate = new RestTemplate();
//		List<ShoppingCartProjection> cartItems = restTemplate.getForObject(baseUrl, List.class);
//		System.out.println(cartItems);
//		if(cartItems != null)
//		{
//			mv.addObject("products",cartItems);
//			mv.setViewName("cart.jsp");
//		}
//		else
//		{
//			mv.addObject("error", "Product not found in cart.");
//			mv.setViewName("cart.jsp");
//		}
//		
//		return mv;
//	}
//	
//	@RequestMapping("/wishlist")
//	public ModelAndView viewFavorite(HttpServletRequest request,HttpServletResponse response)
//	{
////		ModelAndView mv = new ModelAndView();
////		HttpSession hs = request.getSession();
////		long userId = (long) hs.getAttribute("userId");
////		
////		List<ServiceInstance> instances = discoveryClient.getInstances("RETAILERSERVICE");
////		ServiceInstance serviceInstance = instances.get(0);
////
////		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
////		baseUrl = baseUrl + "/buyer/wishlist/" + userId;
////		
////		RestTemplate restTemplate = new RestTemplate();
////		List<FavoritesProjection> favorites = restTemplate.getForObject(baseUrl, List.class);
////		
////		if(favorites != null)
////		{
////			mv.addObject("fav_list",favorites);
////			mv.setViewName("favorites.jsp");
////		}
////		else
////		{
////			mv.addObject("error", "No Product");
////			mv.setViewName("favorites.jsp");
////		}
////		
////		return mv;
//		ModelAndView mv = new ModelAndView();
//		//HttpSession hs = request.getSession();
//		//long userId = (long) hs.getAttribute("userId");
//		Long userId = 203l;
//		System.out.println(userId);
//		List<ServiceInstance> instances = discoveryClient.getInstances("RETAILERSERVICE");
//		ServiceInstance serviceInstance = instances.get(0);
//
//		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
//
//		baseUrl = baseUrl + "/buyer/wishlist/" + userId;
//		
//		RestTemplate restTemplate = new RestTemplate();
//		List<FavoritesProjection> favorites = restTemplate.getForObject(baseUrl, List.class);
//		System.out.println(favorites);
//		
//		if(favorites != null)
//		{
//			mv.addObject("fav_list",favorites);
//			mv.setViewName("favorites");
//		}
//		else
//		{
//			mv.addObject("error", "No Product");
//			mv.setViewName("favorites.jsp");
//		}
//		
//		return mv;
//	}
//	
//	@RequestMapping("/removeProductFromCart")
//	public ModelAndView removeProductFromCart(HttpServletRequest request,HttpServletResponse response,@RequestParam("productId") Long productId)
//	{
//		ModelAndView mv = new ModelAndView();
//		HttpSession hs = request.getSession();
//		
//		long userId = (long) hs.getAttribute("userId");
//		
//		List<ServiceInstance> instances = discoveryClient.getInstances("RETAILERSERVICE");
//		ServiceInstance serviceInstance = instances.get(0);
//
//		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
//
//		baseUrl = baseUrl + "/buyer/removeProductFromCart/" + userId +"/"+ productId;
//		
//		RestTemplate restTemplate = new RestTemplate();
//		Object removed = restTemplate.postForObject(baseUrl, null,ShoppingCart.class,userId,productId);
//		
//		mv.addObject("products",removed);
//		mv.setViewName("cart.jsp");
//		return mv;
//	}
//	
//	@RequestMapping("/removeFromFavorite")
//	public ModelAndView removeFromFavorite(HttpServletRequest request,HttpServletResponse response,@RequestParam("productId") Long productId)
//	{
//		ModelAndView mv = new ModelAndView();
//		HttpSession hs = request.getSession();
//		
//		Long userId = (long) hs.getAttribute("userId");
//		
//		List<ServiceInstance> instances = discoveryClient.getInstances("RETAILERSERVICE");
//		ServiceInstance serviceInstance = instances.get(0);
//
//		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
//
//		baseUrl = baseUrl + "/buyer/removeFromFavorite/" + userId +"/"+ productId;
//		
//		RestTemplate restTemplate = new RestTemplate();
//		Object removed = restTemplate.postForObject(baseUrl, null,Favorites.class,userId,productId);
//		
//		mv.addObject("fav_list",removed);
//		mv.setViewName("/favorites.jsp"); 
//		return mv;
//	}
//	
//	@RequestMapping("/updateQuantity")
//	public ModelAndView updateQuantity(HttpServletRequest request,HttpServletResponse response,@RequestParam("quantity") String quantity,@RequestParam("productId") Long productId)
//	{
//		ModelAndView mv = new ModelAndView();
//		HttpSession hs = request.getSession();
//		Long userId = (long) hs.getAttribute("userId");
//		
//		List<ServiceInstance> instances = discoveryClient.getInstances("RETAILERSERVICE");
//		ServiceInstance serviceInstance = instances.get(0);
//
//		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
//
//		baseUrl = baseUrl + "/buyer/updateQuantity/" + userId +"/"+ productId +"/"+ quantity;
//		
//		RestTemplate restTemplate = new RestTemplate();
//		List<ShoppingCart> updated = restTemplate.postForObject(baseUrl, null,List.class,userId,productId,quantity);
//		
//		mv.addObject("products", updated);
//		mv.setViewName("/cart.jsp");
//		return mv;	
//	}
//
//}
