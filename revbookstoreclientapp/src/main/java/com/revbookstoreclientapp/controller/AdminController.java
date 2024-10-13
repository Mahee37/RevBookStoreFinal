package com.revbookstoreclientapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.revbookstoreclientapp.Exception.NoSellersFoundException;
import com.revbookstoreclientapp.dto.ComplaintProjection;
import com.revbookstoreclientapp.dto.UserProjection;


//import com.example.exception.NoSellersFoundException;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.client.RestTemplate;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private DiscoveryClient discoveryClient;



	@RequestMapping("/viewSellers")
	public ModelAndView viewSellers(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView mv = new ModelAndView();

	    // Get the list of service instances for ADMINSERVICE
	    List<ServiceInstance> instances = discoveryClient.getInstances("ADMINSERVICE");

	    if (instances != null && !instances.isEmpty()) {
	        // Proceed only if instances are available
	        ServiceInstance serviceInstance = instances.get(0);

	        // Construct the base URL for the target service
	        String baseUrl = serviceInstance.getUri().toString(); // e.g., http://localhost:8080
	        baseUrl = "http://localhost:8181" + "/admin/viewSellers";

	        // Fetch the seller list from the target service
	        RestTemplate restTemplate = new RestTemplate();
	        List<UserProjection> sellers = restTemplate.getForObject(baseUrl, List.class);

	        if (sellers != null && !sellers.isEmpty()) {
	            mv.addObject("seller_list", sellers);
	        } else {
	            throw new NoSellersFoundException("No sellers found in the system.");
	        }
	    } else {
	        throw new NoSellersFoundException("ADMINSERVICE is currently unavailable.");
	    }

	    // Set the view to display
	    mv.setViewName("/admin/users");
	    return mv;
	}

	@RequestMapping("/viewBuyers")
	public ModelAndView viewBuyers(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();

		List<ServiceInstance> instances = discoveryClient.getInstances("ADMINSERVICE");
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
		baseUrl = "http://localhost:8181" + "/admin/viewBuyers";

		RestTemplate restTemplate = new RestTemplate();
		List<UserProjection> buyers = restTemplate.getForObject(baseUrl, List.class);
		System.out.println(buyers);

		if (buyers != null) {
			mv.addObject("buyer_list", buyers);
			mv.setViewName("/admin/buyers");
		} else {
			mv.addObject("error", "No Product");
			mv.setViewName("/admin/buyers");
		}

		return mv;
	}

	@RequestMapping("/viewComplaints")
	public ModelAndView viewComplaints(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();

		List<ServiceInstance> instances = discoveryClient.getInstances("ADMINSERVICE");
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
		baseUrl = "http://localhost:8181" + "/admin/viewComplaints";

		RestTemplate restTemplate = new RestTemplate();
		List<ComplaintProjection> complaints = restTemplate.getForObject(baseUrl, List.class);
		System.out.println(complaints);

		if (complaints != null && !complaints.isEmpty()) {
			mv.addObject("complaints", complaints);
			mv.setViewName("/admin/complaints");
		} else {
			mv.addObject("error", "No Product");
			mv.setViewName("/admin/complaints");
		}

		return mv;
	}

	@RequestMapping("/blockUser")
	public ModelAndView blockUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("userId") Long userId, @RequestParam("status") String status,
			@RequestParam("userType") String userType) {
		System.out.println(status);
		ModelAndView mv = new ModelAndView();
		if ("active".equals(status)) {

			List<ServiceInstance> instances = discoveryClient.getInstances("ADMINSERVICE");
			ServiceInstance serviceInstance = instances.get(0);

			String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
			baseUrl = "http://localhost:8181"+ "/admin/blockUser/" + userId;

			RestTemplate restTemplate = new RestTemplate();
			String blocked = restTemplate.exchange(baseUrl, HttpMethod.PUT, null, String.class).getBody();
			System.out.println(blocked);
			if ("Blocked".equals(blocked)) {
				if ("buyer".equals(userType)) {
					List<ServiceInstance> instances1 = discoveryClient.getInstances("ADMINSERVICE");
					ServiceInstance serviceInstance1 = instances1.get(0);

					String baseUrl1 = serviceInstance1.getUri().toString(); // return http://localhost:8080
					baseUrl1 = "http://localhost:8181" + "/admin/viewBuyers";

					RestTemplate restTemplate1 = new RestTemplate();
					List<UserProjection> buyers = restTemplate1.getForObject(baseUrl1, List.class);
					System.out.println(buyers);

					if (buyers != null) {
						mv.addObject("buyer_list", buyers);
						mv.setViewName("/admin/buyers");
					}
				} else if ("seller".equals(userType)) {
					List<ServiceInstance> instances2 = discoveryClient.getInstances("ADMINSERVICE");

					if (instances2 != null && !instances2.isEmpty()) {
						// Proceed only if instances are available
						ServiceInstance serviceInstance2 = instances2.get(0);

						// Construct the base URL for the target service
						String baseUrl2 = serviceInstance2.getUri().toString(); // e.g., http://localhost:8080
						baseUrl2 = "http://localhost:8181" + "/admin/viewSellers";
						System.out.println(baseUrl2); // Fetch the seller list from the target service
						RestTemplate restTemplate2 = new RestTemplate();
						List<UserProjection> sellers = restTemplate2.getForObject(baseUrl2, List.class);
						System.out.println(sellers);

						if (sellers != null && !sellers.isEmpty()) {
							mv.addObject("seller_list", sellers);
							mv.setViewName("/admin/users");
						}
					}
				}
			}
		}
		return mv;
	}

	@RequestMapping("/activateUser")
	public ModelAndView activateUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("userId") Long userId, @RequestParam("status") String status,
			@RequestParam("userType") String userType) {
		System.out.println(status);
		ModelAndView mv = new ModelAndView();
		if ("blocked".equals(status)) {

			List<ServiceInstance> instances = discoveryClient.getInstances("ADMINSERVICE");
			ServiceInstance serviceInstance = instances.get(0);

			String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
			baseUrl = "http://localhost:8181" + "/admin/activateUser/" + userId;

			RestTemplate restTemplate = new RestTemplate();
			String active = restTemplate.exchange(baseUrl, HttpMethod.PUT, null, String.class).getBody();
			System.out.println(active);
			if ("Active".equals(active)) {
				if ("buyer".equals(userType)) {
					List<ServiceInstance> instances1 = discoveryClient.getInstances("ADMINSERVICE");
					ServiceInstance serviceInstance1 = instances1.get(0);

					String baseUrl1 = serviceInstance1.getUri().toString(); // return http://localhost:8080
					baseUrl1 = "http://localhost:8181" + "/admin/viewBuyers";

					RestTemplate restTemplate1 = new RestTemplate();
					List<UserProjection> buyers = restTemplate1.getForObject(baseUrl1, List.class);
					System.out.println(buyers);

					if (buyers != null) {
						mv.addObject("buyer_list", buyers);
						mv.setViewName("/admin/buyers");
					}
				} else if ("seller".equals(userType)) {
					List<ServiceInstance> instances2 = discoveryClient.getInstances("ADMINSERVICE");

					if (instances2 != null && !instances2.isEmpty()) {
						// Proceed only if instances are available
						ServiceInstance serviceInstance2 = instances2.get(0);

						// Construct the base URL for the target service
						String baseUrl2 = serviceInstance2.getUri().toString(); // e.g., http://localhost:8080
						baseUrl2 = "http://localhost:8181" + "/admin/viewSellers";
						System.out.println(baseUrl2); // Fetch the seller list from the target service
						RestTemplate restTemplate2 = new RestTemplate();
						List<UserProjection> sellers = restTemplate2.getForObject(baseUrl2, List.class);
						System.out.println(sellers);

						if (sellers != null && !sellers.isEmpty()) {
							mv.addObject("seller_list", sellers);
							mv.setViewName("/admin/users");
						}
					}
				}
			}
		}
		return mv;
	}
}