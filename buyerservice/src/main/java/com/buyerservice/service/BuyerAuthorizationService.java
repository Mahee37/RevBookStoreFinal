package com.buyerservice.service;

import com.buyerservice.dto.UserProjection;
import com.buyerservice.entity.User;

public interface BuyerAuthorizationService {

	int register(User user);

	UserProjection login(String email, String password);

}
