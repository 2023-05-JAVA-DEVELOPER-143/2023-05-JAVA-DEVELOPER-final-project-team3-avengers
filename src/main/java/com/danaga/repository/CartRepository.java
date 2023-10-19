package com.danaga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Cart;
import com.danaga.entity.OptionSet;

public interface CartRepository extends JpaRepository<Cart, Long>{

	// 유저 카트리스트
	List<Cart> findByMember_UserName(String userName);
	
	// 유저 카트 제품한개
	Cart findCartByOptionSetIdAndMember_UserName(Long optionSetId,String memberId);
	
	// 장바구니 수량
	int countByMember_UserName(String memberId);
	
	
	 

	
	
}
