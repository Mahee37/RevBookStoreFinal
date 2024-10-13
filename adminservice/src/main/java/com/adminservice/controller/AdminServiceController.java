package com.adminservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adminservice.dto.ComplaintProjection;
import com.adminservice.dto.UserProjection;
import com.adminservice.entity.Complaint;
import com.adminservice.entity.Orders;
import com.adminservice.entity.User;
import com.adminservice.service.AdminServiceInterface;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
@RequestMapping("/admin")
public class AdminServiceController {
	
	
	Logger log = Logger.getLogger("adminservice");	
	
	@Autowired
	private AdminServiceInterface aservice;
	
//--------------------------------Authorization Phase
	
	
	@PostMapping("/register")
	public ResponseEntity<String> registerAdmin(@RequestBody User user) {
	    try {
	        log.info("Inside Admin Registration method of AdminService Controller");
	        
	        // Set userType and status explicitly
	        user.setUserType("admin");
	        user.setStatus("active");  // Assuming default status is "active"

	        // Call the service to register the user
	        aservice.register(user);

	        // Return success message
	        return new ResponseEntity<>("Registered", HttpStatus.OK);
	    } catch (Exception e) {
	        // Handle any unexpected exceptions and return an error response
	        return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@GetMapping("/login")
	public ResponseEntity<List<Object>> login(){
		   ResponseEntity<List<Object>> rEntity=null;
		List<Object> ll=new ArrayList<Object>();
		 List<Complaint> ll1= aservice.getAllProducts();
	    	int allcomplaint=ll1.size();
	    	List<Orders> ll2=aservice.getAllOrderes();
	    	int allorders=ll2.size();
	    	List<User> ll3=aservice.getAllBuyer();
	    	int allcustomer=ll3.size();
	    	List<User> ll4=aservice.getAllSeller();
	    	int allseller=ll4.size();
	    	System.out.println(allcomplaint);
	    	System.out.println(allcustomer);
	    	ll.add(allcomplaint);
	    	ll.add(allorders);
	    	ll.add(allcustomer);
	    	ll.add(allseller);
	    	rEntity=new ResponseEntity<List<Object>>(ll, HttpStatus.OK);
	    	return rEntity;
	}
//----------------------------
	
	@GetMapping("/viewSellers")
	public List<UserProjection> viewSellers()
	{
		List<UserProjection> sellers = aservice.viewSellers();
		return sellers;
		
	}
	
	//Resilence implementation
	@GetMapping("/viewBuyers")
	@RateLimiter(name = "inventory", fallbackMethod = "getMessageFallBack")
	public List<UserProjection> viewBuyer()
	{
		List<UserProjection> buyers = aservice.viewBuyers();
		return buyers;
	}
	public ResponseEntity<String> getMessageFallBack(RequestNotPermitted exception) {

	      //  logger.info("Rate limit has applied, So no further calls are getting accepted");

	        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
	        .body("Too many requests : No further request will be accepted. Please try after sometime");
	    }
	
	
	@GetMapping("/viewComplaints")
	public List<ComplaintProjection> viewComplaint()
	{
		List<ComplaintProjection> complaint = aservice.viewComplaintOnSeller();
		return complaint;
	}
	
	@PostMapping("/adminLogin")
	public ResponseEntity<String> adminLogin(@RequestBody User user)
	{
		int result = aservice.login(user);
        if (result > 0) {
            return new ResponseEntity<>("Login successful" , HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Login Failed", HttpStatus.UNAUTHORIZED);
        }
			
	}
			
	@PutMapping("/activateUser/{userId}")
	public ResponseEntity<String> activateUser(@PathVariable("userId") Long userId)
	{
int active = aservice.activateUser(userId);
		if(active > 0)
		{
			return new ResponseEntity<>("Active",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>("Blocked",HttpStatus.UNAUTHORIZED);

		}		
	}
	
	@PutMapping("/blockUser/{userId}")
	public ResponseEntity<String> blockUser(@PathVariable("userId") Long userId)
	{
		int block = aservice.blockUser(userId);
		if(block > 0)
		{
			return new ResponseEntity<>("Blocked",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>("Active",HttpStatus.UNAUTHORIZED);

		}	
	}
	
//	@DeleteMapping("/deleteUser/{userId}")
//	public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId)
//	{
//		int delete = aservice.deleteUser(userId);
//		if(delete > 0)
//		{
//			return new ResponseEntity<>("deleted",HttpStatus.OK);
//		}
//		else
//		{
//			return new ResponseEntity<>("not deleted",HttpStatus.UNAUTHORIZED);	
//		}
//	}	
	
}
