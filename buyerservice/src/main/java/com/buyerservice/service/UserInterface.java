package com.buyerservice.service;

import com.buyerservice.dto.UserProjection;

public interface UserInterface {

	UserProjection login(String email, String password);
	
}
