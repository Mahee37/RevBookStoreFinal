package com.retailerservice.entity;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity

public class Products {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	
	private String productName;
	private String productDescription;
	private String imageUrl;
	private String productCategory;
	private double price;
	private double discountPrice;
	private long quantity;
	
	@ManyToOne
	@JoinColumn(name = "sellerId", referencedColumnName = "userId") 
	//
	private User user;
	
	@OneToMany(mappedBy = "product",cascade= CascadeType.ALL, orphanRemoval = true)
	private List<ShoppingCart> shoppingcart;
	
	@OneToMany(mappedBy = "product",cascade= CascadeType.ALL, orphanRemoval = true)
	private List<Favorites> favorites;
	
	@OneToMany(mappedBy = "product",cascade= CascadeType.ALL, orphanRemoval = true)
	private List<Orders> orders;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Review> review;


	
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public List<Review> getReview() {
		return review;
	}
	public void setReviews(List<Review> review) {
	
		this.review = review;
	}

	public Long getUserId() {
        return user != null ? user.getUserId() : null;
    }

    // Optional: Setter for userId (though it's generally not recommended)
    public void setUserId(Long userId) {
        if (user == null) {
            user = new User();
        }
        user.setUserId(userId);
    }


	

	
	
	

}