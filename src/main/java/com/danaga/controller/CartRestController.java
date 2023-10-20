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

@RequestMapping("/cart")
@RestController
@RequiredArgsConstructor
public class CartRestController {

	private final MemberRepository memberRepository;
	private final CartRepository cartRepository;
	private final OptionSetRepository optionSetRepository;
	private final CartService cartService;
	
//	@Operation(summary = "카트담기")
//	@PostMapping("/test")
//	public ResponseEntity<Cart> create(CartCreateDto cartCreateDto)throws Exception {
//		String username = "User7";   //(String) httpSession.getAttribute("sUserId");
//		OptionSet optionSet = optionSetRepository.findById(2L).get();
//		Member findMember = memberRepository.findByUserName(username).orElseThrow(() -> new Exception("아이디읍다"));
//		cartService.addCart(cartCreateDto, findMember.getUserName());
//		return ResponseEntity.status(HttpStatus.OK).body(null)
//	}
	
	
	
//	@Operation(summary = "카트삭제")
//	@DeleteMapping
//	public ResponseEntity<T> delete(@PathVariable(name = "id") Long id) {
//		return ResponseEntity.status(HttpStatus.OK).body(cartService.deleteCart(id));
//	}
	
}
