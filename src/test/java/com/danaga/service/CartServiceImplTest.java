package com.danaga.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.danaga.dto.CartDto;
import com.danaga.repository.CartRepository;
import com.danaga.repository.MemberRepository;
import com.danaga.repository.product.OptionSetRepository;

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
	OptionSetRepository or;

	//
	@Test
	@Disabled
	void 카트수량변경() throws Exception {
		cs.updateCartQty(CartDto.builder().id(1L).qty(5).build(), "User1");
		// cs.updateCartQty(CartUpdateQtyDto.builder().id(4L).qty(30).build());
		// cs.updateCartQty(CartUpdateQtyDto.builder().id(5L).qty(30).build());
		// cs.updateCartQty(CartUpdateQtyDto.builder().id(6L).qty(30).build());
		// cs.updateCartQty(CartUpdateQtyDto.builder().id(7L).qty(30).build());
		// cs.updateCartQty(CartUpdateQtyDto.builder().id(8L).qty(30).build());
	}

	@Test
	@Disabled
	void 카트옵션변경() throws Exception {
		// 중복 옵션 X + 장바구니 수량 1 ok
		// 중복 옵션 X + 장바구니 수량 >=2 ok
		// 중복 옵션 O + 장바구니 수량 1 ok
		// 중복 옵션 O + 장바구니 수량 >=2 ok
		// cs.updateCartOptionSet(CartUpdateOptionSetDto.builder().id(1052L).optionSet(or.findById(5L).get()).build());

	}

	// ok
	@Test
	@Disabled
	void 카트삭제() throws Exception {
		String testValue = "User5";
		cs.deleteCart(3L, testValue);
	}

	// ok
//	@Test
//	@Disabled
//	void addCart() throws Exception {
	// CartCreateDto createDto =
	// CartCreateDto.builder().qty(3).optionset(or.findById(1L).get()).build();
	// cs.addCart(createDto, "User2");
//	}
//	@Test
//	@Disabled
//	void addCart() throws Exception {
//		CartDto createDto = CartDto.builder().qty(3).optionSet(or.findById(1L).get()).build();
//		cs.addCart(createDto, "User2");
//	}
	/*
	 * @Test void updateCart() throws Exception { CartUpdateDto cartUpdateDto =
	 * CartUpdateDto.builder().id(9L).qty(5).build(); cs.updateCart(cartUpdateDto);
	 * 
	 * CartCreateDto dto =
	 * CartCreateDto.builder().cartQty(11).optionset(or.findById(1L).get()).build();
	 * cs.addCart(dto, "User5"); }
	 */

	// ok
//	@Test
//	
//	void 수량변경() {
//		CartUpdateDto dto = CartUpdateDto.builder().id(9L).qty(100).build();
//		cs.updateCart(dto);
//	@Disabled
//	void updateCart() throws Exception {
//		CartUpdateDto cartUpdateDto = CartUpdateDto.builder().id(9L).qty(5).build();
//		cartService.updateCart(cartUpdateDto);
//		
//	}
//	
//	@Test
//	void cartList() throws Exception {
//		 System.out.println(cartService.findCartList("User1"));
//	}
//	

	@Test
	@Disabled
	void 수량파악() throws Exception {
		CartDto dto = CartDto.builder().id(9L).qty(100).build();
		String value = "User1";
		System.out.println(cs.countCarts(value));
	}

	@Test
	void 카트리스트() throws Exception {
		System.out.println(cs.findsUserCartList("User1").get(0));

	}

}
