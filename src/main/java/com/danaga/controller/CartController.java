package com.danaga.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.danaga.service.CartService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/cart")
@RestController
public class CartController {
	private final CartService cartService;
	/*
	 * // 비회원 장바구니 ?
	 * 
	 * @PostMapping public ResponseEntity<Cart> createCart(Cart cart,HttpSession
	 * session){ // 로그인 체크 if(session.getAttribute("sUserId") ==
	 * cart.getMember().getMemberId()) { // return
	 * ResponseEntity.status(HttpStatus.OK).body(cartService.addCart(cart)); }
	 * return ResponseEntity.status(HttpStatus.OK).body(null); }
	 * 
	 * @GetMapping("/{memberId}") public ResponseEntity<List<Cart>>
	 * getCartsByMemberId(String memberId){ return
	 * ResponseEntity.status(HttpStatus.OK).body(cartService.getCarts(memberId)); }
	 */

}