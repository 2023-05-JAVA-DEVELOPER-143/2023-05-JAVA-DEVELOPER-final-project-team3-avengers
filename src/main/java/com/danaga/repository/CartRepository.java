package com.danaga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Cart;


// 카트 전체보기 , 카트전체삭제, 카트개별삭제, 카트 추가, 카트업데이트 
public interface CartRepository extends JpaRepository<Cart,Long> {
	
	// 장바구니 수량 
	//int countByMemberId(String memberId);
	
	// 유저 카트리스트
	//List<Cart> findCartByMemberId(String memberId);
	
	// 유저 카트 -> 제품 한개 
	//Cart findCartByOptionSetIdAndMemberId(String memberId,Long optionSetId);
	
	
	
}
