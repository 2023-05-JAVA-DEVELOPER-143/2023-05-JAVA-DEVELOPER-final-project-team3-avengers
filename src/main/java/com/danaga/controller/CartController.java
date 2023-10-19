package com.danaga.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.danaga.service.CartService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/cart")
@RestController
@RequiredArgsConstructor
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


	
	
	
	// 카트 담기
//	@PostMapping()
//	public ResponseEntity<CartCreateDto> createCart(@RequestBody Cart cart) {
//		return ResponseEntity.status(HttpStatus.OK).body(cartService.saveCart());
//	}
	
	

}
