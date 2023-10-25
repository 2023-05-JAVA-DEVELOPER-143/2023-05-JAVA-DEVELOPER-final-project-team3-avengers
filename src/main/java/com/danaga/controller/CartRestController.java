package com.danaga.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.dao.product.OptionSetDao;
import com.danaga.dto.CartDto;
import com.danaga.entity.Cart;
import com.danaga.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
/*****************************************************************************************/
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartRestController {
	private final CartService cartService;
	private final OptionSetDao op;

	static List<CartDto> fUserCarts = new ArrayList<>(); // 비회원 장바구니(세션)
	static String sUserId; // 로그인 유저 아이디

	
	@Operation(summary = "카트담기")
	@PostMapping("/add")
	public void addCart(@RequestBody CartDto dto, HttpSession session) throws Exception {
//		String sUserId = (String)session.getAttribute("sUserId");
		String sUserId = "User2";
		cartService.addCart(dto, sUserId);
	}
	
	
	// 회원, 비회원 테스트 성공
	@Operation(summary = "카트 수량 변경")
	@PutMapping("/qty")
	public ResponseEntity<CartDto> updateQty(@RequestBody CartDto dto, HttpSession session)
			throws Exception {
		// 로그인 유저 체크
//		sUserId = (String) session.getAttribute("sUserId");
		sUserId = "User2";
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		if (sUserId != null) {
			// 회원이면 cartService 로직 호출
			return ResponseEntity.status(HttpStatus.OK).body(cartService.updateCartQty(dto, sUserId));
		}
		for (int i = 0; i < fUserCarts.size(); i++) {
			// 비회원일 경우 카트리스트를 돌리면서 dto의 optionsetId 와 동일한 옵션셋 아이디 체크
			if (dto.getId() == fUserCarts.get(i).getId()) {
				// 동일한 세션카트의 옵션셋 꺼내서 수량변경  후 세션에 다시 저장
				fUserCarts.get(i).setQty(dto.getQty());
				fUserCarts.add(fUserCarts.get(i));
				session.setAttribute("fUserCarts", fUserCarts);
				break;
			}
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(CartDto.builder().id(dto.getId()).qty(dto.getQty()).build());
		// 비회원일경우 body에 업데이트된 세션카트를 CartUpdateQtyDto 타입으로 변환 후 리턴
	}

	// 회원,비회원 테스트 성공
	// 선택삭제 optionsetId 리스트로 받기 --> 체크박스 2개 이상 선택시 여러개 존재
	@Operation(summary = "카트 선택삭제")
	@DeleteMapping("/check")
	public void deleteCart(HttpSession session, @RequestParam(name = "optionsetId") List<Long> idList)
			throws Exception {
		// Long id == 회원일시 카트 pk , 비회원 일시 optionsetId
//		sUserId = (String) session.getAttribute("sUserId");
		sUserId = "User2";
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		// 회원일 경우
		if (sUserId != null) {
			for (int i = 0; i < idList.size(); i++) {
				cartService.deleteCart(idList.get(i), sUserId);
			}
		} // 비회원일 경우
		// 선택 optionsetId 와 카트리스트의 optionsetId 동일한 것 찾고 삭제 후 세션에 저장
		for (int i = 0; i < idList.size(); i++) {
			for (int j = 0; j < fUserCarts.size(); j++) {
				if (idList.get(i) == fUserCarts.get(j).getId()) {
					fUserCarts.remove(j);
					break;
				}
			}
		}
		session.setAttribute("fUserCarts", fUserCarts); // 위치 for문 안에 ? 끝나고 난 후에 ?
		countCarts(session); // 세션에 장바구니수량
	}

	// responseDto 생성하기 
	// 회원 성공 + 비회원 성공
	@Operation(summary = "카트리스트 보기")
	@GetMapping
	public ResponseEntity<Long> findCarts(HttpSession session) throws Exception {
//		sUserId = (String) session.getAttribute("sUserId");
		sUserId = "User1";
		if (sUserId != null) {
			List<Cart> carts = cartService.findCartList(sUserId);
			return ResponseEntity.status(HttpStatus.OK).body(carts.get(0).getId());
		}
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		return ResponseEntity.status(HttpStatus.OK).body(fUserCarts.get(0).getId());
	}
	
	
	void countCarts(HttpSession session) throws Exception {
		sUserId = (String) session.getAttribute("sUserId");
		if (sUserId != null) {
			session.setAttribute("countCarts", cartService.countCarts(sUserId));
		} // 비회원 일시 장바구니 리스트의 사이즈
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		session.setAttribute("countCarts", fUserCarts.size());
	};

}
/*****************************************************************************************/