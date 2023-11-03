package com.danaga.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.dto.CartDto;
import com.danaga.dto.SUserCartResponseDto;
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

	@Operation(summary = " 카트 추가 ")
	@PostMapping
	public ResponseEntity<String> addCart(@RequestBody CartDto dto, HttpSession session) throws Exception {
		System.out.println("----------------------------- 비회원 카트 추가 -----------------------------");
		sUserId = (String) session.getAttribute("sUserId");
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		System.out.println(">>>>>>>>>> 비회원 인서트 " + sUserId);
		System.out.println(">>>>>>>>>> 비회원 인서트 " + dto);
		// 1번 경우 = 회원 + 세션 장바구니 비어있음
		if (sUserId != null && fUserCarts == null) {
			cartService.addCart(dto, sUserId);
			countCarts(session);
		 
		} else {// 3번 경우 = 비회원 + 세션 장바구니 X
			if (sUserId == null && fUserCarts == null) {
				System.out.println(" >>>>>>>>>> 처음 인서트 실행 로직 "+ dto);
				System.out.println(" >>>>>>>>>> 처음 인서트 실행 전   " +fUserCarts);
				// 들어온 dto -> list에 추가 후 세션에 저장
				fUserCarts = new ArrayList<>();
				fUserCarts.add(dto);
				session.setAttribute("fUserCarts", fUserCarts);
				countCarts(session);
				System.out.println(" >>>>>>>>>> 처음 인서트 실행 후 1나와야함  =  " +fUserCarts.size());
			} else if (sUserId == null && fUserCarts != null) {
				System.out.println(">>>>>>>>>> 이후 인서트 실행 로직  =  " + dto);
				// 4번 경우 = 비회원 + 세션 장바구니 O
				int findIndex = findFUserCart(fUserCarts, dto);
				System.out.println(">>>>>>>>>> 중복 제품 O 이면 0 or 양수 , x 면 -1  =  " + findIndex);
				System.out.println(">>>>>>>>>> 제품 인서트 전의 카트 사이즈는 최소 1  =  " + fUserCarts.size());
				if (findIndex == -1) {
					fUserCarts.add(dto);
				} else {
					System.out.println("중복 제품 존재 상황 기존 QTY   =   " + fUserCarts.get(findIndex).getQty());
					fUserCarts.get(findIndex).setQty(fUserCarts.get(findIndex).getQty() + dto.getQty());
					System.out.println("중복 제품 존재 상황 이후 QTY (무조건 증가해야함)  =   " + fUserCarts.get(findIndex).getQty());
				}
				session.setAttribute("fUserCarts", fUserCarts);
				countCarts(session);
				System.out.println("인서트 로직 종료 -1 이면 +1 , 아니면 그대로  =  " + fUserCarts.size());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body("ㄴ");
	}

	@Operation(summary = "카트 옵션 변경")
	@PostMapping("/optionset")
	public void updateOptionset(@RequestBody List<Long> ids, HttpSession session) throws Exception {
		sUserId = (String) session.getAttribute("sUserId");
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		System.out.println("----------------------------- 비회원 옵션 변경 -----------------------------");
		System.out.println(">>>>>>>>>> 비회원 인서트  =  " + sUserId);
		System.out.println(">>>>>>>>>> 비회원 옵션변경 전 사이즈  =  " + fUserCarts.size());
		System.out.println(">>>>>>>>>> 비회원 ids 사이즈는 무조건 2  =  " + ids.size());
		Long oldId = ids.get(0); // 기존 옵션셋 아이디
		Long changeId = ids.get(1); // 변경하고자 하는 옵션셋 아이디
		CartDto oldFUserCart = null;
		CartDto changeFUserCart = null;
		boolean isDuplicateId = false;// 변경하고자 하는 옵션셋 아이디 존재 체크

		if (sUserId != null) {
			List<SUserCartResponseDto> list = cartService.updateCartOptionSet(ids, sUserId);
			// return ResponseEntity.status(HttpStatus.OK).body(list);
			System.out.println(">>>>> 이거 찍히면 좀 곤란 <<<<<");
		} else if (fUserCarts != null) {
			// 기존 제품 == 변경하고자 하는 제품 예외
			System.out.println("else if (fUserCarts != null) 로직 실행중 must 여기서 실행 ");
			
			for (int i = 0; i < fUserCarts.size(); i++) {
				// 기존옵션셋 아이디 수량 , 변경하고자 하는옵션셋 아이디 수량 ,
				if (oldId == fUserCarts.get(i).getOptionSetId()) {
					oldFUserCart = fUserCarts.get(i);
				} else if (changeId == fUserCarts.get(i).getOptionSetId()) {
					isDuplicateId = true;
					changeFUserCart = fUserCarts.get(i);
				}
			}
			System.out.println("null X 여야함  =  " + oldFUserCart);
			System.out.println("true면 null x false 면 null  =  "+isDuplicateId + " --> " + changeFUserCart);
			int oldFUserCartIndex = fUserCarts.indexOf(oldFUserCart);
			int ChangeFUserIndex = fUserCarts.indexOf(changeFUserCart);
			System.out.println(">>>>>>>>>> 인덱스 검사 =  OLD  =  " + oldFUserCartIndex +"  //  new  =  "+ ChangeFUserIndex);
			// 1. 변경 전 옵션셋 아이디 수량 1 + 변경 want 옵션셋 존재 --> 변경 전 CartDto 삭제 , 변경 want CartDto 수량 + 1
			// 2. 변경 전 옵션셋 아이디 수량 1 + 변경 want 옵션셋 존재 X --> 변경 전 CartDto 삭제 , 변경 want CartDto 생성
			// 3. 변경 전 옵션셋 아이디 수량 >=2 + 변경 want 옵션셋 존재 --> 변경 전 CartDto 수량 -1 , 변경 want CartDto 수량 +1
			// 4. 변경 전 옵션셋 아이디 수량 >=2 + 변경 want 옵션셋 X --> 변경 전 CartDto 수량 -1 , 변경 want CartDto 생성

			// 변경하고자하는 옵션셋 아이디 이미 존재
			if (isDuplicateId == true) {
				// 1번 경우
				if (oldFUserCart.getQty() == 1) {
					System.out.println(">>>>> 1. 변경 전 옵션셋 아이디 수량 1 + 변경 want 옵션셋 존재 <<<<<");
					System.out.println(">>>>>>>>>> 인덱스 제거 전 fUserCarts 사이즈  =  " + fUserCarts.size());
					fUserCarts.remove(oldFUserCart);
					System.out.println(">>>>>>>>>> 변경 want 변경 전 옵션셋 수량  =  " + fUserCarts.get(ChangeFUserIndex).getQty());
					changeFUserCart.setQty(changeFUserCart.getQty() + 1);
					fUserCarts.set(ChangeFUserIndex, changeFUserCart);
					System.out.println(">>>>>>>>>> 변경 want 변경 후 옵션셋 수량  =  " + fUserCarts.get(ChangeFUserIndex).getQty());
					session.setAttribute("fUserCarts", fUserCarts);
					countCarts(session);
					System.out.println(">>>>>>>>>> 로직 후 fUserCarts 사이즈 위에거보다 + 1  =  " + fUserCarts.size());
				} else if (oldFUserCart.getQty() >= 2) {
					// 3번 경우
					System.out.println(">>>>> 3. 변경 전 옵션셋 아이디 수량 >=2 + 변경 want 옵션셋 존재 <<<<<");
					System.out.println(">>>>>>>>>> 3번 로직 실행 전 fUserCarts 사이즈  =  " + fUserCarts.size());
					System.out.println(">>>>>>>>>> 기존 옵션셋 변경 전 옵션셋 수량  =   " + fUserCarts.get(oldFUserCartIndex).getQty());
					System.out.println(">>>>>>>>>> 변경 want 변경 전 옵션셋 수량  =  " + fUserCarts.get(ChangeFUserIndex).getQty());
					oldFUserCart.setQty(oldFUserCart.getQty() - 1);
					changeFUserCart.setQty(changeFUserCart.getQty() + 1);
					fUserCarts.set(oldFUserCartIndex, oldFUserCart);
					fUserCarts.set(ChangeFUserIndex, changeFUserCart);
					System.out.println(">>>>>>>>>> 기존 옵션셋 변경 후 옵션셋 수량  =  " + fUserCarts.get(oldFUserCartIndex).getQty());
					System.out.println(">>>>>>>>>> 변경 want 변경 후 옵션셋 수량  =  " + fUserCarts.get(ChangeFUserIndex).getQty());
					
					session.setAttribute("fUserCarts", fUserCarts);
					System.out.println(">>>>>>>>>> 3번 로직 실행 후 fUserCarts 사이즈 그대로여야함  =  " + fUserCarts.size());
				}
			} else if (isDuplicateId == false) {
				// 2번경우
				if (oldFUserCart.getQty() == 1) {
					System.out.println(">>>>> 2. 변경 전 옵션셋 아이디 수량 1 + 변경 want 옵션셋 존재 X <<<<<");
					System.out.println(">>>>>>>>>> 2번 로직 실행 전 fUserCarts 사이즈  =  " + fUserCarts.size());
					fUserCarts.remove(oldFUserCart);
					CartDto newFUserCart = CartDto.builder().optionSetId(changeId).qty(1).build();
					System.out.println(">>>>>>>>>> 새로운 CartDto 존재 확인 false 떠야함  =  " + fUserCarts.contains(newFUserCart));
					fUserCarts.add(newFUserCart);
					session.setAttribute("fUserCarts", fUserCarts);
					System.out.println(">>>>>>>>>> 2번 로직 실행 후 fUserCarts 사이즈 그대로여야함  =  " + fUserCarts.size());
				} else if (oldFUserCart.getQty() >= 2) {
					// 4번 경우
					System.out.println(">>>>> 4. 변경 전 옵션셋 아이디 수량 >=2 + 변경 want 옵션셋 X <<<<<");
					System.out.println(">>>>>>>>>> 4번 로직 실행 전 fUserCarts 사이즈  =  " + fUserCarts.size());
					System.out.println(">>>>>>>>>> 기존 옵션셋 변경 전 옵션셋 수량  =  " + fUserCarts.get(oldFUserCartIndex).getQty());
					oldFUserCart.setQty(oldFUserCart.getQty() - 1);
					CartDto newFUserCart = CartDto.builder().optionSetId(changeId).qty(1).build();
					System.out.println(">>>>>>>>>> 새로운 CartDto 존재 확인 false 떠야함  =  " + fUserCarts.contains(newFUserCart));
					fUserCarts.set(oldFUserCartIndex, oldFUserCart);
					fUserCarts.add(newFUserCart);
					session.setAttribute("fUserCarts", fUserCarts);
					System.out.println(">>>>>>>>>> 기존 옵션셋 변경 후 옵션셋 수량 -1 돼야함  =  " + fUserCarts.get(oldFUserCartIndex).getQty());
					System.out.println(">>>>>>>>>> 4번 로직 실행 후 fUserCarts 사이즈 +1 돼야함  =  " + fUserCarts.size());
				}

			}

			// return null;
		} else {
			System.out.println("이거 보이면 나도모름 말도안되는 에러");
			// 말도 안되는 상황
			// return null;
		}

	}

	// 회원, 비회원 테스트 성공
	@Operation(summary = "카트 수량 변경")
	@PutMapping("/qty")
	public ResponseEntity<CartDto> updateQty(@RequestBody CartDto dto, HttpSession session) throws Exception {
		// 로그인 유저 체크
		System.out.println(" ---------------------- 비회원 카트 수량 변경 -----------------------");
		System.out.println(">>> dto  =  "+dto);
		System.out.println("변경 하고자 하는 수량  =  " + dto.getQty());
		
		sUserId = (String) session.getAttribute("sUserId");
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		if (sUserId != null) {
			// 회원이면 cartService 로직 호출
			return ResponseEntity.status(HttpStatus.OK).body(cartService.updateCartQty(dto, sUserId));
		} else {
			System.out.println("수량 변경 로직 실행전 사이즈  =  " +fUserCarts.size());
			for (int i = 0; i < fUserCarts.size(); i++) {
				// 비회원일 경우 카트리스트를 돌리면서 dto의 optionsetId 와 동일한 옵션셋 아이디 체크
				if (dto.getOptionSetId() == fUserCarts.get(i).getOptionSetId()) {
					// 동일한 세션카트의 옵션셋 꺼내서 수량변경 후 세션에 다시 저장
					System.out.println(" 동일한 아이디의 수량 변경 전  =  "+fUserCarts.get(i).getQty());
					fUserCarts.get(i).setQty(dto.getQty());
					session.setAttribute("fUserCarts", fUserCarts);
					System.out.println(" 동일한 아이디의 수량 변경 후  =  "+fUserCarts.get(i).getQty());
					break;
				}
			}
			System.out.println("수량 변경 로직 실행 후 사이즈 그대로  =  " +fUserCarts.size());
			return ResponseEntity.status(HttpStatus.OK)
					.body(CartDto.builder().optionSetId(dto.getOptionSetId()).qty(dto.getQty()).build());
			// 비회원일경우 body에 업데이트된 세션카트를 CartUpdateQtyDto 타입으로 변환 후 리턴
		}
	}

	// 회원,비회원 테스트 성공
	// 선택삭제 optionsetId 리스트로 받기 --> 체크박스 2개 이상 선택시 여러개 존재
	@Operation(summary = "카트 선택삭제")
	@DeleteMapping(value = "/deletecart")
	public void deleteCart(@RequestParam(name = "idlist[]") List<Long> idList, HttpSession session) throws Exception {
		System.out.println("------------------ 비회원 카트 삭제 --------------------------");
		System.out.println(" 비회원 카트에서 삭제할 옵션셋 아이디들 " + idList);
		// Long id == 회원일시 카트 pk , 비회원 일시 optionsetId
		sUserId = (String) session.getAttribute("sUserId");
		fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
		// 회원일 경우
		if (sUserId != null) {
			for (int i = 0; i < idList.size(); i++) {
				cartService.deleteCart(idList.get(i), sUserId);
			}
		} else if (fUserCarts != null) { // 비회원일 경우
			// 선택 optionsetId 와 카트리스트의 optionsetId 동일한 것 찾고 삭제 후 세션에 저장
			System.out.println(" >>>>>>>>>> 비회원 삭제 전 카트 사이즈 " + fUserCarts.size());
			for (int i = 0; i < idList.size(); i++) {
				for (int j = 0; j < fUserCarts.size(); j++) {
					if (idList.get(i) == fUserCarts.get(j).getOptionSetId()) {
						fUserCarts.remove(j);
						break;
					}
				}
			}
		}
		session.setAttribute("fUserCarts", fUserCarts); // 위치 for문 안에 ? 끝나고 난 후에 ?
		countCarts(session); // 세션에 장바구니수량
		System.out.println(" >>>>>>>>>> 비회원 삭제 후 카트 사이즈 " + fUserCarts.size());
	}

	// 회원 성공 + 비회원 성공

	// 장바구니에 몇개 담겼는지 숫자 체크
	void countCarts(HttpSession session) throws Exception {
		sUserId = (String) session.getAttribute("sUserId");
		// sUserId = "User3";
		if (sUserId != null) {
			session.setAttribute("countCarts", cartService.countCarts(sUserId));
		} else if (fUserCarts != null) { // 비회원 일시 장바구니 리스트의 사이즈
			fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
			//System.out.println(">>>>>>>>>"+fUserCarts.size());
			session.setAttribute("countCarts", fUserCarts.size());
		}
	};

	// 비회원 장바구니 아이템 넣기 [fUserCarts : 세션 장바구니 ,dto : 장바구니 담을 제품]
	int findFUserCart(List<CartDto> fUserCarts, CartDto dto) throws Exception {
		int findIndex = -1;
		for (int i = 0; i < fUserCarts.size(); i++) {
			if (dto.getOptionSetId() == fUserCarts.get(i).getOptionSetId()) {
				findIndex = i;
			}
		}
		return findIndex;
	}

}

/*****************************************************************************************/