package com.retailerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailerservice.dao.SellerfavRepository;
import com.retailerservice.dao.SellerreviewRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductreviewDeleteServiceImpl implements ProductReviewServiceInterface{

	
	@Autowired
	private SellerreviewRepository reviewRepo;
	
	@Override
	public void deletereviewByProductId(Long productId) {
		// TODO Auto-generated method stub
		 reviewRepo.deleteReviewByProductId(productId);
	}

}
