package com.danaga.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.danaga.dto.CartCreateDto;
import com.danaga.service.CartService;
import com.danaga.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/cart")
@Controller
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;
	private final MemberService memberService;

	@GetMapping("/{memberId}")
	public String findCarts(HttpSession session) throws Exception {
		String sUserId = (String) session.getAttribute("sUserId");
		if (isLogin(sUserId)) {
			cartService.findCartList(sUserId);
		}
		CartCreateDto fUserCart = (CartCreateDto) session.getAttribute("fUserCart");

		return null;
	}

	@GetMapping("/popup")
	public String popup() {
		return "팝업 템플릿";
	}

	// 장바구니 담기 컨트롤러...
	@PostMapping
	public String addCart(HttpSession session, CartCreateDto dto) throws Exception {
		String sUserId = (String) session.getAttribute("sUserId");
		List<CartCreateDto> findFUserCartList = (ArrayList<CartCreateDto>) session.getAttribute("fUserCarts");
		// 로그인 유저 + 장바구니 비어있음
		if (isLogin(sUserId) && findFUserCartList == null) {
			cartService.addCart(dto, sUserId);
		} else if (isLogin(sUserId) && findFUserCartList != null) /* 로그인 유저 +장바구니에 제품 존재 */ {
			cartService.addCart(dto, sUserId);
			for (int i = 0; i < findFUserCartList.size(); i++) {
				cartService.addCart(findFUserCartList.get(i), sUserId);
			}
		} // 여기서 나오는 경우 == 비회원 ..........
		if (!isLogin(sUserId) && findFUserCartList == null) {
			List<CartCreateDto> fUserCarts = new ArrayList<>();
			fUserCarts.add(dto);
			session.setAttribute("fUserCarts", fUserCarts);
		} else if (!isLogin(sUserId) && findFUserCartList != null) {
			addFUserCart(findFUserCartList, dto);
		}
		return "팝업컨트롤러 url";
	}

	// 로그인 체크
	boolean isLogin(String id) throws Exception {
		// 아이디 존재 유무 확인
		if (!memberService.isDuplicate(id)) {
			return true;
		}
		return false;
	}

	// 비회원 장바구니 아이템 넣기
	void addFUserCart(List<CartCreateDto> fUserCarts, CartCreateDto dto) throws Exception {
		for (int i = 0; i < fUserCarts.size(); i++) {
			if (dto.getOptionset().getId() == fUserCarts.get(i).getOptionset().getId()) {
				fUserCarts.get(i).setQty(fUserCarts.get(i).getQty() + dto.getQty());
			}
			fUserCarts.add(dto);
		}

	}

}
