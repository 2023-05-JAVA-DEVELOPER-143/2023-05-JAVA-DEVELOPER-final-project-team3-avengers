package com.danaga.service;

import java.util.List;

import com.danaga.dto.CartCreateDto;
import com.danaga.dto.CartUpdateDto;
import com.danaga.entity.Cart;
import jakarta.transaction.Transactional;

@Transactional
public interface CartService {
	
	// 카트 리스트
	List<Cart> findCartList(String value)throws Exception;
	
	//카트 추가
	void addCart(CartCreateDto dto,String value) throws Exception;
	
	// 카트 1개 삭제
	void deleteCart(Long id) throws Exception;
	
	// 장바구니 수량변경 
	void updateCart(CartUpdateDto dto);
	
	
}
