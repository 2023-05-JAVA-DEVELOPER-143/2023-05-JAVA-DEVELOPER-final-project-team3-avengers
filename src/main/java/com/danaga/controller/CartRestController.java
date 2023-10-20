package com.danaga.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.entity.Cart;
import com.danaga.service.CartService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartRestController {
	private final CartService cartService;
	
	@GetMapping("/{memberId}")
	public ResponseEntity<List<Cart>> cartList(HttpSession session){
		
		return null;  
	}
}
