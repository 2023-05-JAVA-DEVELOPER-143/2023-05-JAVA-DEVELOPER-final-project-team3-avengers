package com.danaga.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;
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

@Controller
@RequiredArgsConstructor
public class CartController {
	private final CartService cartService;

	static String sUserId; // 로그인 유저 아이디
	static List<CartDto> fUserCarts; // 비회원 장바구니(세션)

	@GetMapping("/cart_list")
	public String findCarts(HttpSession session, Model model) throws Exception {
		sUserId = (String) session.getAttribute("sUserId");
		fUserCarts=(List<CartDto>)session.getAttribute("fUserCarts");
		if (sUserId != null) { // 회원
			List<SUserCartResponseDto> carts = cartService.findsUserCartList(sUserId);
			model.addAttribute("cart", carts);
		} else if (fUserCarts != null) {
			List<FUserCartResponseDto> responseDto = new ArrayList<>();
			for (int i = 0; i < fUserCarts.size(); i++) {
				responseDto.add(cartService.findfUserCartList(fUserCarts.get(i)));
			}
			model.addAttribute("cart", responseDto);
		}
		return "cart/cart_form";
	}
}
