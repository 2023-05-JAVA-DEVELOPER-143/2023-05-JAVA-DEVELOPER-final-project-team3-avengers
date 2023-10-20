package com.danaga.repository;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.danaga.AvengersApplication;
import com.danaga.entity.Cart;

class CartRepositoryTest extends AvengersApplication{

	@Autowired
	CartRepository cartRepository;
	
	@Test
	void test() {
		List<Cart> cartList = cartRepository.findByMember_UserName("User1");
		System.out.println(cartList);
		
	}

}
