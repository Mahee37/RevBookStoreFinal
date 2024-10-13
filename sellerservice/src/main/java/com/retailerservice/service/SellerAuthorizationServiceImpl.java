package com.retailerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailerservice.dao.SellerAuthorizationRepo;
import com.retailerservice.dto.UserProjection;
import com.retailerservice.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SellerAuthorizationServiceImpl implements SellerAuthorizationInterface{
	
	@Autowired
	private SellerAuthorizationRepo authUserRepo;

	@Override
	public int register(User seller) {
		authUserRepo.save(seller);
		return 1;
	}

	@Override
	public UserProjection login(String email, String password) {
		UserProjection user=authUserRepo.login(email,password);
		return user;
	}

}
