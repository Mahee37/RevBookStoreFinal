package com.adminservice.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	
	private String name;
	private String email;
	private String password;
	private String address;
	private String pincode;
	private String phoneNumber;
	private String userType;
	private String status;
	
	@OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
	private List<Products> product;
	
	@OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
	private List<Orders> orders;
	
	@OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
	private List<ShoppingCart> shoppingcart;
	
	@OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
	private List<Favorites> favorites;
	
	@OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
	private List<Review> review;
	
	@OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
	private List<Complaint> complaint;
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Products> getProduct() {
		return product;
	}

	public void setProduct(List<Products> product) {
		this.product = product;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public List<ShoppingCart> getShoppingcart() {
		return shoppingcart;
	}

	public void setShoppingcart(List<ShoppingCart> shoppingcart) {
		this.shoppingcart = shoppingcart;
	}

	public List<Favorites> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<Favorites> favorites) {
		this.favorites = favorites;
	}

	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}
	
}
