package com.danaga.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.danaga.dto.CartCreateDto;
import com.danaga.entity.Cart;
import com.danaga.entity.Member;

@Repository
public interface CartDao {

	// 카트 리스트
	List<Cart> findCartList(Long memberId);

	// 카트 한개 보기 [멤버아이디,제품아이디]
	Cart findCart(Long optionSetId,Long memberId);
	
	// 카트 추가
	void addCart(Cart cart);

	// 카트 1개 삭제
	void deleteCart(Long id);
	
	
	
}
