package com.danaga.dao;

import java.util.List;
import java.util.Optional;
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
	public List<Cart> findCartList(String memberId) {
		return cartRepository.findByMemberId(memberId);
	}
	
	/*
	 * 카트생성
	 */
//	@Override
//	public Cart create(Cart cart) {
//		boolean exists = cartRepository.existsById(cart.getOptionSet().getId());
//		if(exists) {
//			cart.setCartQty(cart.getCartQty()+1);
//			Cart insertCart = cartRepository.save(cart);
//			return insertCart;
//	@Override
//	public Cart create(Member member, Cart cart) {
//		Cart createCart = cartRepository.findCartByOptionSetIdAndMemberId(member.getMemberId(), cart.getOptionSet().getId());
//		
//		if(createCart == null) {
//			return cartRepository.save(createCart);
//		}
//		return cartRepository.save(cart);
//	} else
//		createCart.setCartQty(createCart.getCartQty()+1);
//		return cartRepository.save(createCart);
//	}
//	
//	
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
	public Cart create(Member member, Cart cart) {
		return null;
	}
	
	

	
	


	

}
