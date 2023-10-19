package com.danaga.service;

import java.util.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartServiceImplTest {
	@Autowired
	CartService cartService;
	@Autowired
	MemberService memberService;

	// ok
	@Test
	@Disabled
	void 카트삭제() throws Exception {
		cartService.deleteCart(1L);
	}

	@Test
	void addCart() throws Exception {
		// cartService.deleteCart(1L);

		// memberService.getMemberBy("User8");

		/*
		 * String value ="";
		 * 
		 * 
		 * OptionSet oSet = new OptionSet(1L, 10000, 5, 3, 1, null, null, null, null,
		 * null); CartCreateDto cartCreateDto = new CartCreateDto(1, oSet);
		 * cartService.addCart(cartCreateDto, value);
		 */
	}

}
