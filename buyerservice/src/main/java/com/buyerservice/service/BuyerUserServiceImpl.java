package com.buyerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buyerservice.dao.UserRepository;
import com.buyerservice.entity.User;

@Service
@Transactional
public class BuyerUserServiceImpl implements BuyerUserService {
	
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public User findUserById(Long userId) {
		// TODO Auto-generated method stub
		return  userRepo.findById(userId).orElse(null);
	}

}
