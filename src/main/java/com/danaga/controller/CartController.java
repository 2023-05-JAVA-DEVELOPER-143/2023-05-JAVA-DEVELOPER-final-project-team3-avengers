package com.danaga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.dto.CartCreateDto;
import com.danaga.entity.Cart;
import com.danaga.service.CartService;

@RestController
@RequestMapping("cart")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	// 카트 담기
//	@PostMapping()
//	public ResponseEntity<CartCreateDto> createCart(@RequestBody Cart cart) {
//		return ResponseEntity.status(HttpStatus.OK).body(cartService.saveCart());
//	}
	
	
}
