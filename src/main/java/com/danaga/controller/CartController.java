package com.danaga.controller;

import org.hibernate.sql.Delete;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.danaga.dto.CartCreateDto;
import com.danaga.dto.CartUpdateResponseDto;
import com.danaga.dto.CartUpdateQtyDto;
import com.danaga.service.CartService;
import com.danaga.service.MemberService;

import groovyjarjarantlr4.v4.parse.ANTLRParser.exceptionGroup_return;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/cart")
@Controller
@RequiredArgsConstructor
public class CartController {
	/*
	 * 비회원 장바구니 업데이트 or 딜리트 시 pk 역할 할 요소 체크
	 */
	private final CartService cartService;
	private final MemberService memberService;

	static List<CartCreateDto> fUserCarts = new ArrayList<>(); // 비회원 장바구니(세션)
	static String sUserId; // 로그인 유저 아이디

	// 장바구니 담기 컨트롤러...
	@SuppressWarnings("unchecked")
	@PostMapping
	public String addCart(HttpSession session, @RequestBody CartCreateDto dto) throws Exception {
		sUserId = (String) session.getAttribute("sUserId");
		fUserCarts = (ArrayList<CartCreateDto>) session.getAttribute("fUserCarts");
		// 로그인 유저 + 세션 장바구니 비어있음
		if (isLogin(sUserId) && fUserCarts.isEmpty()) {
			cartService.addCart(dto, sUserId);
			countCarts(session);
		} else if (isLogin(sUserId) && !fUserCarts.isEmpty()) {
			// 로그인 유저 +세션 장바구니에 제품 존재 --> 멤버 컨트롤러 로그인 메쏘드에서 실행?
			cartService.addCart(dto, sUserId); // 로그인 유저가 넣은 제품 인서트
			for (int i = 0; i < fUserCarts.size(); i++) {
				// fUserCarts.get(i) == CartCreateDto, add메쏘드 실행하면 수량 체크 -> 업데이트 or 인서트
				cartService.addCart(fUserCarts.get(i), sUserId);
				fUserCarts.clear();
				session.setAttribute("fUserCarts", fUserCarts);
				// 장바구니에서 로그인 유저 장바구니로 옮기고 session 장바구니 초기화
				countCarts(session);
			}
		} // 여기서 나오는 경우 == 비회원 ..........
		if (!isLogin(sUserId) && fUserCarts.isEmpty()) {
			fUserCarts.add(dto);
			session.setAttribute("fUserCarts", fUserCarts);
			countCarts(session);
		} else if (!isLogin(sUserId) && !fUserCarts.isEmpty()) {
			addFUserCart(fUserCarts, dto);
			session.setAttribute("fUserCarts", fUserCarts);
			countCarts(session);
		}
		return "redirect:/cart/popup";

	}

	@GetMapping()
	public String findCarts(HttpSession session) throws Exception {
		// 로그인 유저로 찾기
		sUserId = (String) session.getAttribute("sUserId");
		// 회원이면 아이디로 찾기
		if (isLogin(sUserId)) {
			cartService.findCartList(sUserId);
		} // 비회원일시 세션 리스트
		fUserCarts = (List<CartCreateDto>) session.getAttribute("fUserCarts");
		// 타임리프 문법으로 템플릿에서 하나씩 뿌려주기
		return "리다이랙트 장바구니페이지";
	}

	@GetMapping("/popup")
	public String popup() {
		/*
		 * 계속쇼핑 true or false 버튼 [ 베스트상품 ? ]
		 */

		return "팝업 템플릿 경로 ";
	}

	// 장바구니 삭제
	@DeleteMapping
	// id == 회원일시 cartId , 비회원일시 장바구니에 담긴 옵션셋 아이디
	public String deleteCart(HttpSession session, @PathVariable Long id) throws Exception {
		// Long id == 회원일시 카트 pk , 비회원 일시 optionsetId
		sUserId = (String) session.getAttribute("sUserId");
		if (isLogin(sUserId)) {
			cartService.deleteCart(id);
			countCarts(session);
		}
		fUserCarts = (List<CartCreateDto>) session.getAttribute("fUserCarts");
		// 세션에 담긴 장바구니 리스트 돌리면서 옵션셋 아이디와 동일한 것 찾고 remove 후 session에 다시 담기
		for (int i = 0; i < fUserCarts.size(); i++) {
			if (id == fUserCarts.get(i).getOptionset().getId()) {
				fUserCarts.remove(fUserCarts.get(i));
			}
		}
		// for문 종료 후 나오는 fUserCarts == 삭제할 카트가 모두 제거된 장바구니 -> 세션에 저장
		session.setAttribute("fUserCart", fUserCarts);
		countCarts(session);
		return null;
	}

	// 장바구니 전체삭제
	// @DeleteMapping
	public void deleteAllCart(HttpSession session) throws Exception {
		sUserId = (String) session.getAttribute("sUserId");
		if (isLogin(sUserId)) {
			cartService.deleteCarts(sUserId);
			countCarts(session);
		}
		// 비회원일시 그냥 세션 초기화 --> 비회원 세션에는 카트밖에없음 초기화 가능
		session.invalidate();
		countCarts(session);
	}
	// 수량 변경
	@PutMapping
	public void updateCart(HttpSession session, @RequestBody CartUpdateQtyDto dto) throws Exception {
		sUserId = (String) session.getAttribute("sUserId");
		fUserCarts = (List<CartCreateDto>) session.getAttribute("fUserCarts");
		if (isLogin(sUserId)) {
		}
	}

	/******************* 컨트롤러 메쏘드 내부에서 필요한 메쏘드 ****************************/
	// 로그인 체크
	boolean isLogin(String id) throws Exception {
		// 아이디 존재 유무 확인
		if (!memberService.isDuplicate(id)) {
			return true;
		}
		return false;
	}

	// 비회원 장바구니 아이템 넣기 [fUserCarts : 세션 장바구니 ,dto : 장바구니 담을 제품]
	void addFUserCart(List<CartCreateDto> fUserCarts, CartCreateDto dto) throws Exception {
		for (int i = 0; i < fUserCarts.size(); i++) {
			// 세션 장바구니의 리스트에 똑같은 제품이 있으면 수량증가
			if (dto.getOptionset().getId() == fUserCarts.get(i).getOptionset().getId()) {
				fUserCarts.get(i).setQty(fUserCarts.get(i).getQty() + dto.getQty());
			} // 중복 제품이 없다면 그냥 리스트에 추가
			fUserCarts.add(dto);
		}

	}

	void countCarts(HttpSession session) throws Exception {
		String sUserId = (String) session.getAttribute("sUserId");
		if (isLogin(sUserId)) {
			session.setAttribute("countCarts", cartService.countCarts(sUserId));
		} // 비회원 일시 장바구니 리스트의 사이즈
		fUserCarts = (List<CartCreateDto>) session.getAttribute("fUserCarts");
		session.setAttribute("countCarts", fUserCarts.size());
	};

}
