package com.danaga.service;

import java.util.List;

import com.danaga.dto.CartCreateDto;
import com.danaga.entity.Cart;
import com.danaga.entity.Member;

public interface CartService {
	
	// 카트 리스트
	List<Cart> findCartList(String member_id);
	// 수량 증가 감소
//	Long updateQty();
	// 카트 추가
	Cart create(CartCreateDto cartCreateDto, String member_id);
	// 카트 1개 삭제
	void deleteCart(Long id) throws Exception;
	
}
