package com.buyerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyerservice.dao.BuyerAuthorizationRepo;
import com.buyerservice.dto.UserProjection;
import com.buyerservice.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BuyerAuthorizationServiceImpl implements BuyerAuthorizationService{
	
	@Autowired
	private BuyerAuthorizationRepo authBuyerRepo;

	@Override
	public int register(User buyer) {
		authBuyerRepo.save(buyer);
		return 1;
	}

	@Override
	public UserProjection login(String email, String password) {
		UserProjection user=authBuyerRepo.login(email,password);
		return user;
	}
}
