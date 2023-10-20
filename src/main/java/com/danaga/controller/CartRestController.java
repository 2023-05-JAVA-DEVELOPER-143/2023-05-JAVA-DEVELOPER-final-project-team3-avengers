package com.danaga.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.dto.CartCreateDto;
import com.danaga.entity.Cart;
import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;
import com.danaga.repository.CartRepository;
import com.danaga.repository.MemberRepository;
import com.danaga.repository.OptionSetRepository;
import com.danaga.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

	
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
