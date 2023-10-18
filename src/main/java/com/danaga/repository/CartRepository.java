package com.danaga.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Cart;
import com.danaga.entity.OptionSet;

public interface CartRepository extends JpaRepository<Cart, Long>{

	
//	List<Cart> findByUserId(String member_id);
	Cart findByMemberId(String member_id);
	Long updateQty(Long id, int cartQty);
	Long deleteByCartNo(Long id);
	Cart findByOptionSetIdAndUserId(Long optionset_id, String member_id);
	
}
