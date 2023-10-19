package com.danaga.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.entity.Cart;
import com.danaga.entity.Member;
import com.danaga.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CartDaoImpl implements CartDao{
	
	private final CartRepository cartRepository;
	
	/*
	 * 카트리스트 보기
	 */
	@Override
	public List<Cart> findCartList(String member_id) {
		List<Cart> cartList = cartRepository.findByMemberId(member_id);
		return cartList;
	}
	
	/*
	 * 카트생성
	 */
//	@Override
//	public void create(Member member, Long optionset_id , int count) throws Exception {
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
//			findCart.setCartQty(prev+count);
//			cartRepository.save(findCart);
//		}
//	}
	
	/*
	 * 카트생성
	 */
	@Override
	public Cart create(Member member, Cart cart) {
		Cart createCart = cartRepository.findCartByOptionSetIdAndMemberId(member.getMemberId(), cart.getOptionSet().getId());
		
		if(createCart == null) {
			return cartRepository.save(createCart);
		}
		createCart.setCartQty(createCart.getCartQty()+1);
		return cartRepository.save(createCart);
	}
	
	
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
