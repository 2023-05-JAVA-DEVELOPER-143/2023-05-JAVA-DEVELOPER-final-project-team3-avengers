package com.danaga.service;

import java.util.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.danaga.dto.CartCreateDto;
import com.danaga.dto.CartUpdateDto;
import com.danaga.entity.Cart;
import com.danaga.entity.OptionSet;
import com.danaga.repository.OptionSetRepository;

@SpringBootTest
public class CartServiceImplTest {
	@Autowired
	CartService cs;
	@Autowired
	MemberService memberService;
	@Autowired
	OptionSetRepository or;

	// ok
	@Test
	@Disabled
	void 카트삭제() throws Exception {
		cs.deleteCart(1L);
	}

	// ok
	@Test
	void addCart() throws Exception {
		CartCreateDto dto = CartCreateDto.builder().cartQty(11).optionset(or.findById(1L).get()).build();
		cs.addCart(dto, "User5");
	}

	// ok
	@Test
	
	void 수량변경() {
		CartUpdateDto dto = CartUpdateDto.builder().id(9L).qty(100).build();
		cs.updateCart(dto);
	}

	void 카트리스트() throws Exception{
		System.out.println(cs.findCartList("User1").get(0)); 
		
	}
	
	
	
}
