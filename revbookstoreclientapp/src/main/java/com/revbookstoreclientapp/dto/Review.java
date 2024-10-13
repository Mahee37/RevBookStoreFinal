package com.revbookstoreclientapp.dto;

public class Review {
    private Long reviewId;
    private Long productId;
    private Long userId;
    private String reviewText;
    private Integer rating;
    
    	private User user;
	
	
	public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Products getProduct() {
			return product;
		}

		public void setProduct(Products product) {
			this.product = product;
		}

	private Products product;
   
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
