package com.revbookstoreclientapp.dto;


public interface FavoritesProjection {

	long getFavoriteId();

	String getProductName();

	double getPrice();

	double getDiecountPrice();

	String getProductDescription();

}