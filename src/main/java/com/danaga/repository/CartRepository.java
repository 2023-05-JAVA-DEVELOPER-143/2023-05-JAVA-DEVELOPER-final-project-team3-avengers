package com.danaga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Cart;
import com.danaga.entity.OptionSet;

public interface CartRepository extends JpaRepository<Cart, Long>{

	// 카트 리스트
	List<Cart> findByMemberId(String memberId);
	
	// 유저 카트 -> 수량체크
	Cart findCartByOptionSetIdAndMemberId(String memberId, Long optionSetId);
	
	
}
