package com.danaga.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.dao.CartDao;
import com.danaga.dto.CartCreateDto;
import com.danaga.dto.CartDto;
import com.danaga.entity.Cart;
import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;
import com.danaga.repository.CartRepository;
import com.danaga.repository.MemberRepository;
import com.danaga.repository.OptionSetRepository;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	CartDao cartDao;
	
	@Autowired
	CartRepository cartRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	OptionSetRepository optionSetRepository;
	
	
	@Override
	public List<Cart> findCartList(String member_id) {
		return cartRepository.findAll();
	}
	
//	public void saveCart(Member member, Long optionset_id, int cartQty) throws Exception {
//		Cart findCart = cartRepository.findByOptionSetIdAndUserId( optionset_id, member.getMemberId());
//		if(findCart == null) {
//			OptionSet optionSet = optionSetRepository.findById(optionset_id).orElseThrow(() -> {
//				return new Exception("상품이 없습니다");
//			});
//			
//			cartRepository.save(findCart);
//		} else {
//			Cart cart = cartDao.create(findCart);
//		}
//	}
	
	

	/*
	 * 카트 담기
	 */
//	@Override
//	public void saveCart(CartDto cartDto) {
//		Cart cart = cartRepository.findByMemberId(null)
//	}
	
	
	
	
	
	/*
	 * 카트 1개 삭제
	 */
	@Override
	public void deleteCart(Long id) throws Exception {
		Optional<Cart> findCart = cartRepository.findById(id);
		if(findCart.isEmpty()) {
			throw new Exception();
		}
		cartRepository.delete(findCart.get());
	}






	
	


	
	

	
	
}
