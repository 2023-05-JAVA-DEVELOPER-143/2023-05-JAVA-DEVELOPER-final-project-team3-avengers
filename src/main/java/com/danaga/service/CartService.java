package com.danaga.service;

import java.util.List;

import com.danaga.dto.CartCreateDto;
import com.danaga.dto.CartUpdateOptionSetDto;
import com.danaga.dto.CartUpdateQtyDto;
import com.danaga.dto.CartUpdateResponseDto;
import com.danaga.entity.Cart;
import jakarta.transaction.Transactional;

@Transactional
public interface CartService {

	// 카트 리스트
	List<Cart> findCartList(String value) throws Exception;

	// 카트 추가
	void addCart(CartCreateDto dto, String value) throws Exception;

	// 카트 1개 삭제
	void deleteCart(Long id) throws Exception;

	// 멤버 아이디로 카트삭제 [전체 삭제]
	void deleteCarts(String sUserId) throws Exception;

	// 장바구니 아이콘 위에 숫자..
	int countCarts(String value) throws Exception;

	// 장바구니 수량 변경
	CartUpdateResponseDto updateCartQty(CartUpdateQtyDto dto);

	// 장바구니 옵션 변경
	List<CartUpdateResponseDto> updateCartOptionSet(CartUpdateOptionSetDto dto);

}
