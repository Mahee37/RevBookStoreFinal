package com.retailerservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailerservice.dao.SellerCartRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SellerCartServiceImpl implements SellerCartServiceInterface{

	@Autowired
	private SellerCartRepository cartRepo;
	
	@Override
	public void deleteCartByProductId(Long productId) {
		// TODO Auto-generated method stub
		 cartRepo.deletecartByProductId(productId);
		
	}

}
