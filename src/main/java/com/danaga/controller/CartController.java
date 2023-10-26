package com.danaga.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danaga.dto.CartDto;
import com.danaga.dto.FUserCartResponseDto;
import com.danaga.dto.SUserCartResponseDto;
import com.danaga.service.CartService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
	private final CartService cartService;

	static List<CartDto> fUserCarts = new ArrayList<>(); // 비회원 장바구니(세션)
	static String sUserId; // 로그인 유저 아이디

	@GetMapping("/list")
	public String findCarts(HttpSession session, Model model) throws Exception {
		// @PathVariable("sUserId") String sUserId,
		// sUserId = (String) session.getAttribute("sUserId");
		//sUserId = "User1"; 
		// log.info("sUserId = {} ", sUserId);

		CartDto a = CartDto.builder().qty(3).id(12L).build();
		CartDto b = CartDto.builder().qty(4).id(10L).build();
		CartDto c = CartDto.builder().qty(5).id(88L).build();
		CartDto d = CartDto.builder().qty(7).id(11L).build();
		CartDto e = CartDto.builder().qty(5).id(75L).build();
		fUserCarts.add(a);
		fUserCarts.add(b);
		fUserCarts.add(c);
		fUserCarts.add(d);
		fUserCarts.add(e);
		session.setAttribute("fUserCarts", fUserCarts);

		if (sUserId != null) {
			List<SUserCartResponseDto> carts = cartService.findsUserCartList(sUserId);
			model.addAttribute("cart", carts);
			return "cart/cart_form";
		}
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		List<FUserCartResponseDto> responseDto = new ArrayList<>();
		for (int i = 0; i < fUserCarts.size(); i++) {
			responseDto.add(cartService.findfUserCartList(fUserCarts.get(i)));
		}
		model.addAttribute("cart", responseDto);

		return "cart/cart_form";
	}
}
