package com.danaga.service;

import java.util.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.danaga.dao.CartDao;
import com.danaga.dao.MemberDao;
import com.danaga.dto.CartCreateDto;
import com.danaga.dto.CartUpdateDto;
import com.danaga.entity.Cart;
import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Product;
import com.danaga.repository.CartRepository;
import com.danaga.repository.MemberRepository;
import com.danaga.repository.OptionSetQueryRepository;
import com.danaga.repository.OptionSetQueryRepositoryImpl;
import com.danaga.repository.OptionSetRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class CartServiceImplTest {
	@Autowired
	CartService cartService;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	OptionSetRepository oq;

	//ok
	@Test
	@Disabled
	void 카트삭제() throws Exception {
		cartService.deleteCart(5L);
	}

	@Test
	@Disabled
	void addCart() throws Exception {
		CartCreateDto createDto = CartCreateDto.builder().qty(3).optionset(oq.findById(1L).get()).build();
		cartService.addCart(createDto, "User7");
		
	}
	@Test
	@Disabled
	void updateCart() throws Exception {
		CartUpdateDto cartUpdateDto = CartUpdateDto.builder().id(9L).qty(5).build();
		cartService.updateCart(cartUpdateDto);
		
	}
	
	@Test
	void cartList() throws Exception {
		 System.out.println(cartService.findCartList("User1"));
	}
	
	

}
