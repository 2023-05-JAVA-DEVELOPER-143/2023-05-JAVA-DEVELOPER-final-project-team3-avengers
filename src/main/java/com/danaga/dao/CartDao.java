package com.danaga.dao;

import java.util.List;

import com.danaga.entity.Cart;
import com.danaga.entity.Member;

public interface CartDao {

	// 카트 리스트
	List<Cart> findCartList(String member_id);
	// 카트 추가
	Cart create(Member member, Cart cart);
	// 카트 1개 삭제
	void deleteCart(Long id) throws Exception;
	
	
	
	

	
}
