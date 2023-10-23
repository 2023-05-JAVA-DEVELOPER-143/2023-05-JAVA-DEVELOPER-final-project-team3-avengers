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
	CartRepository cartRepository;
	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	OptionSetRepository oq;
	OptionSetRepository or;

	//ok
	@Test
	@Disabled
	void 카트삭제() throws Exception {
		cs.deleteCart(1L);
	}

	// ok
	@Test
	@Disabled
	void addCart() throws Exception {
		CartCreateDto createDto = CartCreateDto.builder().qty(3).optionset(oq.findById(1L).get()).build();
		cs.addCart(createDto, "User7");
		
	}
	/*
	 * @Test void updateCart() throws Exception { CartUpdateDto cartUpdateDto =
	 * CartUpdateDto.builder().id(9L).qty(5).build(); cs.updateCart(cartUpdateDto);
	 * 
	 * CartCreateDto dto =
	 * CartCreateDto.builder().cartQty(11).optionset(or.findById(1L).get()).build();
	 * cs.addCart(dto, "User5"); }
	 */

	// ok
	@Test
	
	void 수량변경() {
		CartUpdateDto dto = CartUpdateDto.builder().id(9L).qty(100).build();
		//cs.updateCart(dto);
	}

	void 카트리스트() throws Exception{
		System.out.println(cs.findCartList("User1").get(0)); 
		
	}
	
	
	
}
