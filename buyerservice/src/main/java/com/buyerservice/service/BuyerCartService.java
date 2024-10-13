package com.buyerservice.service;

import com.buyerservice.dto.CartProductDTO;


public interface BuyerCartService {
	
	CartProductDTO saveCart(CartProductDTO cartDTO);

}
