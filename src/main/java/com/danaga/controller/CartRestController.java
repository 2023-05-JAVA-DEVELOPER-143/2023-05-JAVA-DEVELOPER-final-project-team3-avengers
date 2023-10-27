package com.danaga.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.danaga.dao.product.OptionSetDao;
import com.danaga.dto.CartDto;
import com.danaga.dto.FUserCartResponseDto;
import com.danaga.dto.ResponseDto;
import com.danaga.dto.SUserCartResponseDto;
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

	@Operation(summary = " 카트 추가 ")
	@PostMapping
	public void addCart(@RequestBody CartDto dto, HttpSession session) throws Exception {
		sUserId = (String) session.getAttribute("sUserId");
		session.setAttribute("fUserCarts", fUserCarts);
		fUserCarts = (ArrayList<CartDto>) session.getAttribute("fUserCarts");
		System.out.println("장바구니 담기 액션 하기전 세션 사이즈 = 0 true  -->>>> " + fUserCarts.size());
		// 1번 경우 = 회원 + 세션 장바구니 비어있음
		if (sUserId != null && fUserCarts.isEmpty()) {
			cartService.addCart(dto, sUserId);
			countCarts(session);
		} else if (sUserId != null && !fUserCarts.isEmpty()) {
			// 2번 경우 = 회원 + 세션 장바구니 존재
			cartService.addCart(dto, sUserId);
			for (int i = 0; i < fUserCarts.size(); i++) {
				// addCart 메쏘드실행하면 로직에 따라 수량 체크 -> 업데이트 or 인서트
				cartService.addCart(fUserCarts.get(i), sUserId);
				// 세션 -> db 로 데이타 인서트 후 세션 데이타 초기화 후 세션카트 카운트
			}
			fUserCarts.clear();
			session.setAttribute("fUserCarts", fUserCarts);
			countCarts(session);
		} // 3번 경우 = 비회원 + 세션 장바구니 X
		if (sUserId == null && fUserCarts == null) {
			// 들어온 dto -> list에 추가 후 세션에 저장
			// fUserCarts.add(dto);
			// addFUserCart(fUserCarts, dto, session);
			fUserCarts.add(dto);
			session.setAttribute("fUserCarts", fUserCarts);
			countCarts(session);
		} else if (sUserId == null && fUserCarts != null) {
			// 4번 경우 = 비회원 + 세션 장바구니 O
			// addFUserCart 메쏘드 실행 후 세션에 수량,제품 저장 확인하기
			int findIndex = findFUserCart(fUserCarts, dto);
			if (findIndex == -1) {
				fUserCarts.add(dto);
			} else {
				fUserCarts.get(findIndex).setQty(fUserCarts.get(findIndex).getQty() + dto.getQty());
			}
			session.setAttribute("fUserCarts", fUserCarts);
			countCarts(session);
		}

	}

	@Operation(summary = "카트 옵션 변경")
	@PutMapping("/optionset")
	public ResponseEntity<CartDto> updateOptionset(@RequestParam List<Long> ids, HttpSession session) throws Exception {
		sUserId = (String) session.getAttribute("sUserId");
		Long oldId = ids.get(0); // 기존 옵션셋 아이디
		Long changeId = ids.get(1); // 변경하고자 하는 옵션셋 아이디
		cartService.updateCartOptionSet(ids, sUserId);

		return null;
	}

	// 회원, 비회원 테스트 성공
	@Operation(summary = "카트 수량 변경")
	@PutMapping("/qty")
	public ResponseEntity<CartDto> updateQty(@RequestBody CartDto dto, HttpSession session) throws Exception {
		// 로그인 유저 체크
		sUserId = (String) session.getAttribute("sUserId");
		System.out.println("sUserId");
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		// System.out.println("카트 수량변경 컨트롤러 요청들어올때의 사이즈" + fUserCarts.size());
		if (sUserId != null) {
			// 회원이면 cartService 로직 호출
			return ResponseEntity.status(HttpStatus.OK).body(cartService.updateCartQty(dto, sUserId));
		} else {
			for (int i = 0; i < fUserCarts.size(); i++) {
				// 비회원일 경우 카트리스트를 돌리면서 dto의 optionsetId 와 동일한 옵션셋 아이디 체크
				if (dto.getId() == fUserCarts.get(i).getId()) {
					// 동일한 세션카트의 옵션셋 꺼내서 수량변경 후 세션에 다시 저장
					System.out.println("fUserCarts의 카트중 osid가 똑같은 제품의 변경 전 수량" + fUserCarts.get(i).getQty());
					System.out.println("변경 하고자 하는 osid = " + dto.getId() + "변경 하고자 하는 수량" + dto.getQty());
					fUserCarts.get(i).setQty(dto.getQty());
					System.out.println("fUserCarts 의 카트중 osid가 똑같은 제품의 변경 후 수량" + fUserCarts.get(i).getQty());
					session.setAttribute("fUserCarts", fUserCarts);
					break;
				}
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(CartDto.builder().id(dto.getId()).qty(dto.getQty()).build());
		// 비회원일경우 body에 업데이트된 세션카트를 CartUpdateQtyDto 타입으로 변환 후 리턴
	}

	// 회원,비회원 테스트 성공
	// 선택삭제 optionsetId 리스트로 받기 --> 체크박스 2개 이상 선택시 여러개 존재
	@Operation(summary = "카트 선택삭제")
	@DeleteMapping("check/{osId}")
	public void deleteCart(@PathVariable(name = "osId") List<Long> idList, HttpSession session) throws Exception {
		// Long id == 회원일시 카트 pk , 비회원 일시 optionsetId
//		sUserId = (String) session.getAttribute("sUserId");
		sUserId = "User3";
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		System.out.println("제품 선택 삭제 컨트롤러 들어올때 카트 사이즈 = " + fUserCarts.size());
		// 회원일 경우
		if (sUserId != null) {
			for (int i = 0; i < idList.size(); i++) {
				cartService.deleteCart(idList.get(i), sUserId);
			}
		} // 비회원일 경우
			// 선택 optionsetId 와 카트리스트의 optionsetId 동일한 것 찾고 삭제 후 세션에 저장
		System.out.println("선택 하고자 하는 idList의 사이즈 = " + idList.size());
		for (int i = 0; i < idList.size(); i++) {
			for (int j = 0; j < fUserCarts.size(); j++) {
				if (idList.get(i) == fUserCarts.get(j).getId()) {
					fUserCarts.remove(j);
					break;
				}
			}
		}
		System.out.println("제품 선택 삭제 컨트롤러 실행 후 세션에 저장 될 카트 사이즈" + fUserCarts.size());
		session.setAttribute("fUserCarts", fUserCarts); // 위치 for문 안에 ? 끝나고 난 후에 ?
		countCarts(session); // 세션에 장바구니수량
	}

	// 회원 성공 + 비회원 성공

	// 장바구니에 몇개 담겼는지 숫자 체크
	void countCarts(HttpSession session) throws Exception {
		sUserId = (String) session.getAttribute("sUserId");
		if (sUserId != null) {
			session.setAttribute("countCarts", cartService.countCarts(sUserId));
		} // 비회원 일시 장바구니 리스트의 사이즈
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		session.setAttribute("countCarts", fUserCarts.size());
	};

	// 비회원 장바구니 아이템 넣기 [fUserCarts : 세션 장바구니 ,dto : 장바구니 담을 제품]
	int findFUserCart(List<CartDto> fUserCarts, CartDto dto) throws Exception {
		for (int i = 0; i < fUserCarts.size(); i++) {
			if (dto.getId() == fUserCarts.get(i).getId()) {
				return i;
			}
		}
		return -1;
	}

}

/*****************************************************************************************/