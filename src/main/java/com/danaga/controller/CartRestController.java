package com.danaga.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.cassandra.CassandraProperties.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.dto.CartCheckResponseDto;
import com.danaga.dto.CartDto;
import com.danaga.dto.ResponseDto;
import com.danaga.dto.SUserCartResponseDto;
import com.danaga.dto.product.ProductDto;
import com.danaga.entity.Cart;
import com.danaga.entity.Product;
import com.danaga.service.CartService;
import com.danaga.service.product.OptionSetService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/*****************************************************************************************/
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartRestController {
	private final CartService cartService;
	private final OptionSetService optionSetService;

	static List<CartDto> fUserCarts; // 비회원 장바구니(세션)
	static String sUserId; // 로그인 유저 아이디
	static final Integer CART_TUNG = 1000;
	static final Integer NOT_CART_TUNG = 2000;
	static final Integer CART_QTY_MAX = 2100;
	static final Integer NOT_CART_QTY_MAX = 2200;

	@GetMapping("/{optionSetId}")
	public ResponseEntity<Integer> isDuplicate(@PathVariable Long optionSetId, HttpSession session) throws Exception {
		sUserId = (String) session.getAttribute("sUserId");
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		boolean check = false;
		Integer returnCode = 0;
		if (sUserId != null) {
			Cart findCart = cartService.findCart(sUserId, optionSetId);
			if (findCart == null) {
				returnCode = CART_TUNG;
			} else if (findCart != null) {
				returnCode = NOT_CART_TUNG;
			}
		} else if (sUserId == null && fUserCarts == null) {
			returnCode = CART_TUNG;
		} else if (sUserId == null && fUserCarts != null) {
			check = isDuplicate(optionSetId, fUserCarts);
			if (check == true) {
				returnCode = NOT_CART_TUNG;
			} else {
				returnCode = CART_TUNG;
			}
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(500);
		}
		return ResponseEntity.status(HttpStatus.OK).body(returnCode);
	}

	@PostMapping
	public ResponseEntity<Integer> addCart(@RequestBody CartDto dto, HttpSession session) throws Exception {
		sUserId = (String) session.getAttribute("sUserId");
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		Integer returnNo = NOT_CART_QTY_MAX;
		System.out.println(">>>>>>>>>>>>>>>>>>>>"+dto);
		// 1번 경우 = 회원 + 세션 장바구니 비어있음
		if (sUserId != null && fUserCarts == null) {
			int qty = cartService.isDuplicateProduct(sUserId, dto.getOptionSetId());
			// 장바구니에 담을 수량 제한
			if (qty + dto.getQty() > 5) {
				returnNo = CART_QTY_MAX;
			} else {
				cartService.addCart(dto, sUserId);
				countCarts(session, fUserCarts);
			}
		} else {// 3번 경우 = 비회원 + 세션 장바구니 X
			if (sUserId == null && fUserCarts == null) {
				System.out.println(">>>>>>>>>>> 여기 실행돼야함 ㄴㄴㄴ 111 <<<<<<<<<");
				// 들어온 dto -> list에 추가 후 세션에 저장
				fUserCarts = new ArrayList<>();
				fUserCarts.add(dto);
				session.setAttribute("fUserCarts", fUserCarts);
				countCarts(session, fUserCarts);
			} else if (sUserId == null && fUserCarts != null) {
				// 4번 경우 = 비회원 + 세션 장바구니 O
				System.out.println(">>>>>>>>>>> 여기 실행돼야함 111 <<<<<<<<<");
				System.out.println("fUserCarts"+fUserCarts+"dto....."+dto);
				int findIndex = findFUserCart(fUserCarts, dto);
				System.out.println(">>>>>>>>>>"+findIndex);
				if (findIndex == -1) {
					System.out.println(">>>>>>>>>>> 여기 실행돼야함 ㄴㄴㄴ 222 <<<<<<<<<");
					fUserCarts.add(dto);
					countCarts(session, fUserCarts);
				} else {
					if (fUserCarts.get(findIndex).getQty() + dto.getQty() > 5) {
						returnNo = CART_QTY_MAX;
					} else {
						System.out.println(">>>>>>>>>>> 여기 실행돼야함 222<<<<<<<<<");
						fUserCarts.get(findIndex).setQty(fUserCarts.get(findIndex).getQty() + dto.getQty());
					}
					session.setAttribute("fUserCarts", fUserCarts);
					countCarts(session, fUserCarts);
				}
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(returnNo);
	}

	@PostMapping("/optionset")
	public ResponseEntity<Integer> updateOptionset(@RequestBody List<Long> ids, HttpSession session) throws Exception {
		sUserId = (String) session.getAttribute("sUserId");
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		Long oldId = ids.get(0); // 기존 옵션셋 아이디
		Long changeId = ids.get(1); // 변경하고자 하는 옵션셋 아이디
		CartDto oldFUserCart = null;
		CartDto changeFUserCart = null;
		boolean isDuplicateId = false;// 변경하고자 하는 옵션셋 아이디 존재 체크
		Integer returnNo = NOT_CART_QTY_MAX;
		if (sUserId != null) {
			if (cartService.findCart(sUserId, changeId).getQty() == 5) {
				returnNo = CART_QTY_MAX;
			}else {
			cartService.updateCartOptionSet(ids, sUserId);
			}// return ResponseEntity.status(HttpStatus.OK).body(list);
		} else if (fUserCarts != null) {
			// 기존 제품 == 변경하고자 하는 제품 예외

			for (int i = 0; i < fUserCarts.size(); i++) {
				// 기존옵션셋 아이디 수량 , 변경하고자 하는옵션셋 아이디 수량 ,
				if (oldId == fUserCarts.get(i).getOptionSetId()) {
					oldFUserCart = fUserCarts.get(i);
				} else if (changeId == fUserCarts.get(i).getOptionSetId()) {
					isDuplicateId = true;
					changeFUserCart = fUserCarts.get(i);
				}
			}
			int oldFUserCartIndex = fUserCarts.indexOf(oldFUserCart);
			int ChangeFUserIndex = fUserCarts.indexOf(changeFUserCart);
			// [ 중복 제품 O ]
			if (isDuplicateId == true) {
				if (changeFUserCart.getQty() == 5) {
					returnNo = CART_QTY_MAX;
				}else {
				// 변경 전 수량 1
				if (oldFUserCart.getQty() == 1) {
					changeFUserCart.setQty(changeFUserCart.getQty() + 1);
					fUserCarts.set(ChangeFUserIndex, changeFUserCart);
					fUserCarts.remove(oldFUserCart);
					// session.setAttribute("fUserCarts", fUserCarts);
					countCarts(session, fUserCarts);
				} else if (oldFUserCart.getQty() >= 2) {
					// 변경 전 수량 >=2
					oldFUserCart.setQty(oldFUserCart.getQty() - 1);
					changeFUserCart.setQty(changeFUserCart.getQty() + 1);
					fUserCarts.set(oldFUserCartIndex, oldFUserCart);
					fUserCarts.set(ChangeFUserIndex, changeFUserCart);
					session.setAttribute("fUserCarts", fUserCarts);
				}
				}
			} else if (isDuplicateId == false) { // 중복 제품 X
				// 변경 전 수량 1
				if (oldFUserCart.getQty() == 1) {
					fUserCarts.remove(oldFUserCart);
					CartDto newFUserCart = CartDto.builder().optionSetId(changeId).qty(1).build();
					fUserCarts.add(newFUserCart);
					session.setAttribute("fUserCarts", fUserCarts);
				} else if (oldFUserCart.getQty() >= 2) {
					// 변경 전 수량 >= 2
					oldFUserCart.setQty(oldFUserCart.getQty() - 1);
					CartDto newFUserCart = CartDto.builder().optionSetId(changeId).qty(1).build();
					fUserCarts.set(oldFUserCartIndex, oldFUserCart);
					fUserCarts.add(newFUserCart);
					session.setAttribute("fUserCarts", fUserCarts);
				}
			}
		} else {
			returnNo=500;
			System.out.println("이거 보이면 나도모름 말도안되는 에러");
		}
		return ResponseEntity.status(HttpStatus.OK).body(returnNo);
	}

	@PutMapping("/qty")
	public void updateQty(@RequestBody CartDto dto, HttpSession session) throws Exception {
		// 로그인 유저 체크
		sUserId = (String) session.getAttribute("sUserId");
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		if (sUserId != null) {
			// 회원이면 cartService 로직 호출
			cartService.updateCartQty(dto, sUserId);
			// return
			// ResponseEntity.status(HttpStatus.OK).body(cartService.updateCartQty(dto,
			// sUserId));
		} else {
			for (int i = 0; i < fUserCarts.size(); i++) {
				// 비회원일 경우 카트리스트를 돌리면서 dto의 optionsetId 와 동일한 옵션셋 아이디 체크
				if (dto.getOptionSetId() == fUserCarts.get(i).getOptionSetId()) {
					// 동일한 세션카트의 옵션셋 꺼내서 수량변경 후 세션에 다시 저장
					fUserCarts.get(i).setQty(dto.getQty());
					session.setAttribute("fUserCarts", fUserCarts);
					break;
				}
			}
			/*
			 * return ResponseEntity.status(HttpStatus.OK)
			 * .body(CartDto.builder().optionSetId(dto.getOptionSetId()).qty(dto.getQty()).
			 * build());
			 */
			// 비회원일경우 body에 업데이트된 세션카트를 CartUpdateQtyDto 타입으로 변환 후 리턴
		}

	}

	@DeleteMapping(value = "/deletecart")
	public void deleteCart(@RequestParam(name = "idlist[]") List<Long> idList, HttpSession session) throws Exception {
		sUserId = (String) session.getAttribute("sUserId");
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		// 회원일 경우
		if (sUserId != null) {
			for (int i = 0; i < idList.size(); i++) {
				cartService.deleteCart(idList.get(i), sUserId);
			}
		} else if (sUserId ==null&&fUserCarts != null) { // 비회원일 경우
			// 선택 optionsetId 와 카트리스트의 optionsetId 동일한 것 찾고 삭제 후 세션에 저장
			for (int i = 0; i < idList.size(); i++) {
				for (int j = 0; j < fUserCarts.size(); j++) {
					if (idList.get(i) == fUserCarts.get(j).getOptionSetId()) {
						fUserCarts.remove(j);
						break;
					}
				}
			}
			countCarts(session, fUserCarts);
			if (fUserCarts.isEmpty()) {
				session.setAttribute("fUserCarts", null);
			} else if (fUserCarts != null) {
				session.setAttribute("fUserCarts", fUserCarts);
			}
		}

	}

	// 장바구니에 몇개 담겼는지 숫자 체크
	void countCarts(HttpSession session, List<CartDto> list) throws Exception {
		sUserId = (String) session.getAttribute("sUserId");
		if (sUserId != null) {
			session.setAttribute("countCarts", cartService.countCarts(sUserId));
		} else if (list != null) { // 비회원 일시 장바구니 리스트의 사이즈
			// fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
			session.setAttribute("countCarts", list.size());
		}
	};

	// 비회원 장바구니 아이템 넣기 [fUserCarts : 세션 장바구니 ,dto : 장바구니 담을 제품]
	int findFUserCart(List<CartDto> fUserCarts, CartDto dto) throws Exception {
		int findIndex = -1;
		System.out.println("여기는 메쏘드" + fUserCarts.size());
		for (int i = 0; i < fUserCarts.size(); i++) {
			if (dto.getOptionSetId() == fUserCarts.get(i).getOptionSetId()) {
				System.out.println("여기 실행안돼...?");
				findIndex = i;
			}
		}
		System.out.println("메쏘드 return 직전 " +findIndex);
		return findIndex;
	}

	// 장바구니에 담겨있는지 체크
	static boolean isDuplicate(Long id, List<CartDto> lists) {
		boolean check = false;
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).getOptionSetId() == id) {
				check = true;
			}
		}
		return check;
	}

}

/*****************************************************************************************/