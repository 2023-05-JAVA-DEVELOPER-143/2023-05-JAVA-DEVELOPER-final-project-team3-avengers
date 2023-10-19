package com.danaga.dao;

import java.util.List;

import com.danaga.entity.Cart;

import com.danaga.entity.Member;

public interface CartDao {

	// 카트 리스트
	//List<Cart> findCartList(String member_id);
	// 카트 추가
	
	//Cart create(Cart cart);
	
	// 카트 1개 삭제
	//void deleteCart(Long id) throws Exception;
	// 수량 증가
	//Long increaseQty(Cart cart);
	// 수량 감소
	//Long decreaseQty(Cart cart);


	// 카트 담기
	void addCart(Cart cart); 

	// 카트리스트
	//List<Cart> getCarts(String memberId);

	// 카트 보기
	Cart getCart(Long cartNo);

	// 카트 제품
	//Cart getCartByOptionSetId(String memberId, Long optionSetId);
	
	// 카트 삭제 
	//void deleteCart(Long cartNo);
	
	// 담긴 장바구니 목록 수량
	//int countByMemberId(String memberId);
	
	// 장바구니 제품 수량 업데이트 
	//Cart updateCartQty(Cart updateCart) throws Exception;

	
}
