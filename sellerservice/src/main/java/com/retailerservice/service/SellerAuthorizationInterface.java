package com.retailerservice.service;

import com.retailerservice.dto.UserProjection;
import com.retailerservice.entity.User;

public interface SellerAuthorizationInterface {

	int register(User user);

	UserProjection login(String email, String password);

}
