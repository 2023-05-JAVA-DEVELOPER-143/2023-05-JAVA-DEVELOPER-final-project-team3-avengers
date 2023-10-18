package com.danaga.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import com.danaga.entity.Cart;
import com.danaga.repository.CartRepository;

public class CartDaoImpl implements CartDao{
	
	@Autowired
	CartRepository cartRepository;
	
	/*
	 * 카트리스트 보기
	 */
	@Override
	public List<Cart> findCartList(String member_id) {
		List<Cart> cartList = cartRepository.findAll();
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
	public Cart create(Cart cart) {
		boolean exists = cartRepository.existsById(cart.getOptionSet().getId());
		if(exists) {
//			cart.setCartQty(cart.getCartQty()+1);
			Cart insertCart = cartRepository.save(cart);
			return insertCart;
		}
		return cartRepository.save(cart);
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
	/*
	 * 카트 수량 변경
	 */
	@Override
	public Long increaseQty(Cart cart) {
		Cart findCart = cartRepository.findById(cart.getId()).get();
		
		return null;
	}

	@Override
	public Long decreaseQty(Cart cart) {
		return null;
	}
	

	


	
	


	

	
	

}
