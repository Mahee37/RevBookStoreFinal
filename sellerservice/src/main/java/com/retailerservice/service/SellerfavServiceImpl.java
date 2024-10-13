package com.retailerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailerservice.dao.SellerfavRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SellerfavServiceImpl implements SellerfavServiceInterface {

	@Autowired
	private SellerfavRepository favRepo;
	@Override
	public void deletefavByProductId(Long productId) {
		// TODO Auto-generated method stub
		favRepo.deletefavByProductId(productId);
		
	}

}
