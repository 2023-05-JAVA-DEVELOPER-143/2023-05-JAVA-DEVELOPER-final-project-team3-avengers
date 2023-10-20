package com.danaga.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.danaga.entity.Cart;
import com.danaga.entity.Member;


@Repository
public interface CartDao {

	// 카트 리스트
	List<Cart> findCartList(String username);
	
	// 카트 추가
//	void addCart(Cart cart)
//	Cart create(Member member, Cart cart);

	// 카트 1개 삭제
//	void deleteCart(Long id) throws Exception;
	

	

	
}
