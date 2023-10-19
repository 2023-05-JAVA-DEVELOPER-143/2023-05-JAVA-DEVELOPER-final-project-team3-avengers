package com.danaga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Cart;
import com.danaga.entity.OptionSet;

public interface CartRepository extends JpaRepository<Cart, Long>{

	

//	List<Cart> findByUserId(String member_id);
	/*
	 * Cart findByMemberId(String member_id); Long updateQty(Long id, int cartQty);
	 * Long deleteByCartNo(Long id); Cart findByOptionSetIdAndUserId(Long
	 * optionset_id, String member_id);
	 */

	// 장바구니 수량 
	//int countByMemberId(String memberId);
	
	// 유저 카트리스트
	//List<Cart> findCartByMemberId(String memberId);
	
	// 유저 카트 -> 제품 한개 
	//Cart findCartByOptionSetIdAndMemberId(String memberId,Long optionSetId);
	
	

	
}
