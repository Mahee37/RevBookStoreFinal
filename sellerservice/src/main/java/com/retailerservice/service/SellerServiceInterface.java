package com.retailerservice.service;

import java.util.List;
import java.util.Optional;

import com.retailerservice.dto.ProductDTO;
import com.retailerservice.dto.ProductProjection;

import com.retailerservice.entity.Products;

public interface SellerServiceInterface {


	List<ProductProjection> viewProducts(Long userId);

	List<ProductProjection> getProductsBySellerId(Long sellerId);


	Products getProductById(Long productId);

	void deleteProductById(Long productId);

	Optional<Products> addProduct(Products product);

	boolean editProduct(Products product);

	ProductProjection getEditProductById(Long productId);

}
