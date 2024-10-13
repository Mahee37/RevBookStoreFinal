package com.retailerservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailerservice.dao.ReviewDAOInterface;
import com.retailerservice.dao.SellerDAOInterface;
import com.retailerservice.dao.SellerProductDAOinterface;
import com.retailerservice.dto.ProductDTO;
import com.retailerservice.dto.ProductProjection;
import com.retailerservice.entity.Products;
import com.retailerservice.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SellerService implements SellerServiceInterface {
    
    @Autowired
    private SellerDAOInterface sellerdao;
    
   @Autowired
   private SellerProductDAOinterface productdao;
    
    @Autowired
    private ReviewDAOInterface reviewdao;
    
    @Override
    @Transactional
    public void deleteProductById(Long productId) {
    	reviewdao.deleteByProductId(productId);
        int deletedCount = sellerdao.deleteProductByProductId(productId);
        if (deletedCount > 0) {
            System.out.println("Product with ID " + productId + " successfully deleted.");
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }
    
    
    
    @Override
	public Products getProductById(Long productId) {
		// TODO Auto-generated method stub
		Optional<Products> products = sellerdao.findById(productId);
		
		if(products.isPresent())
		{
			return products.get();
		}
		return null;
//		throw new ProductNotFoundException(("No Product Found With This Id"+productId));
	}

    @Override
    public List<ProductProjection> viewProducts(Long userId) {
    	  return sellerdao.findProductsByUserId(userId);
    }



    @Override
    public List<ProductProjection> getProductsBySellerId(Long sellerId) {
        return sellerdao.findAllByUserId(sellerId);
    }
    
    
    
    @Override
    public Optional<Products> addProduct(Products product) {
        // Find the user by ID and set the user for the product
        Optional<User> userOptional = sellerdao.findUserById(product.getUserId());
        if (userOptional.isPresent()) {
            product.setUser(userOptional.get());
        } else {
            // If the user does not exist, return an empty Optional
            return Optional.empty();
        }

        // Save the product
        Products savedProduct = sellerdao.save(product);
        return Optional.of(savedProduct);
    }

    
    
    @Override
    public boolean editProduct(Products product) {
        try {
            // Fetch the existing product by ID
            Optional<Products> existingProductOpt = sellerdao.findById(product.getProductId());
            
            if (existingProductOpt.isPresent()) {
                Products existingProduct = existingProductOpt.get();

                // Debugging: Log the existing product details before updating
                System.out.println("Existing Product Quantity: " + existingProduct.getQuantity());
                
                // Update the existing product's fields
                existingProduct.setProductName(product.getProductName());
                existingProduct.setProductDescription(product.getProductDescription());
                existingProduct.setImageUrl(product.getImageUrl());
                existingProduct.setProductCategory(product.getProductCategory());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setDiscountPrice(product.getDiscountPrice());
                existingProduct.setQuantity(product.getQuantity());

                // Save the updated product back to the database
                sellerdao.save(existingProduct);
                return true; // Product successfully updated
            }
            return false; // Product not found
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            return false; // Indicate failure
        }
    }



    public ProductProjection getEditProductById(Long productId) {
        // Retrieve the product using the repository and return the projection
        return productdao.findProductById(productId).orElse(null);
    }
	

}
