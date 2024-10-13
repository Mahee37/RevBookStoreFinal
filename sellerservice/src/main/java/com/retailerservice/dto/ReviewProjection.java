package com.retailerservice.dto;

public interface ReviewProjection {

	 	Long getReviewId();
	    Long getProductId();
	    Long getUserId();
	    String getReviewText();
	    Integer getRating();

}
