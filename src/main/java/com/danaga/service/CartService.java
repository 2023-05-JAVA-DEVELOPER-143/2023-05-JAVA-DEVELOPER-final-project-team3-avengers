package com.danaga.service;

import java.util.List;

import com.danaga.dto.CartUpdateOptionSetDto;
import com.danaga.dto.CartDto;
import com.danaga.dto.FUserCartResponseDto;
import com.danaga.dto.SUserCartResponseDto;
import com.danaga.dto.CartUpdateResponseDto;
import com.danaga.entity.Cart;
import jakarta.transaction.Transactional;

/*****************************************************************************************/
@Transactional
public interface CartService {

	// 회원 카트 리스트
	List<SUserCartResponseDto> findsUserCartList(String value) throws Exception;

	// 비회원 카트 리스트
	FUserCartResponseDto findfUserCartList(CartDto dto);

	// 카트 추가
	void addCart(CartDto dto, String value) throws Exception;

	// 카트 1개 삭제
	void deleteCart(Long optionSetId, String value) throws Exception;

	// 멤버 아이디로 카트삭제 [전체 삭제]
	void deleteCarts(String sUserId) throws Exception;

	// 장바구니 아이콘 위에 숫자..
	int countCarts(String value) throws Exception;

	// 장바구니 수량 변경
	CartDto updateCartQty(CartDto dto, String value) throws Exception;

	// 장바구니 옵션 변경
	List<CartDto> updateCartOptionSet(List<Long> ids, String sUserId) throws Exception;

	// 장바구니 pk로 장바구니찾기
	Cart findCart(Long id);

}
/*****************************************************************************************/