package com.danaga.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.dao.CartDao;
import com.danaga.dto.CartCreateDto;
import com.danaga.entity.Cart;
import com.danaga.entity.Member;
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
	
//	@Override
//	public void create(Member member, Long optionset_id, int cartQty) throws Exception {
//		Cart findCart = cartRepository.findByUserIdAndOptionSetId(member.getMemberId(), optionset_id).get();
//		if(findCart == null) {
//			OptionSet optionSet = optionSetRepository.findById(optionset_id).orElseThrow(() -> {
//				return new Exception("상품이 없습니다");
//			});
//			Cart cart = Cart.builder()
//					.member(member)
//					.optionSet(optionSet)
//					.build();
//		} else {
//			int prev = findCart.getCartQty();
//			findCart.setCartQty(prev+cartQty);
//			cartRepository.save(findCart);
//		}
//	}

	/*
	 * 카트 담기
	 */
//	@Override
//	public List<Cart> create(CartCreateDto cart) {
//		Cart findCart = cartRepository.findByUserIdAndOptionSetId(cart.getMember().getMemberId(), cart.getOptionSet().getId()).get();
//		CartCreateDto cartCreateDto = CartCreateDto.toEntity(findCart);
//		//save
//		//member id로 내 카트 아이템 전부 조회 해서 반환
//		return cartCreateDto;
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

@Override
public Cart create(CartCreateDto cartCreateDto, String member_id) {
	// TODO Auto-generated method stub
	return null;
}

	
	


	
	

	
	
}
