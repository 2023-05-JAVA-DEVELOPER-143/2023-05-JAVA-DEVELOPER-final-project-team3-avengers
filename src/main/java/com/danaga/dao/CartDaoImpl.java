package com.danaga.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.danaga.entity.Cart;
import com.danaga.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CartDaoImpl implements CartDao {
	private final CartRepository cartRepository;
	
	// 카트번호로 찾기 
	@Override
	public Cart getCart(Long cartNo) {
		return cartRepository.findById(cartNo).get();
		
		
	}

	// 유저 장바구니 목록
	/*
	 * @Override public List<Cart> getCarts(String memberId) { return
	 * cartRepository.findCartByMemberId(memberId); }
	 */

	// 카트 담기 
	@Override
	public void addCart(Cart cart) {
		/*
		 * final Cart findCart =
		 * cartRepository.findCartByOptionSetIdAndMemberId(cart.getMember().getMemberId(
		 * ),cart.getOptionSet().getId()); // 있으면 수량 업데이트 -->> 이미 담겨있으면 팝업창 ? 메쏘드 실행
		 * if(findCart != null) { findCart.setCartQty(findCart.getCartQty()+1); //return
		 * findCart; } // 없으면 인서트 //return cartRepository.save(cart);
		 */
	}

	// 카트 목록 확인 
	/*
	 * @Override public Cart getCartByOptionSetId(String memberId, Long optionSetId)
	 * { return cartRepository.findCartByOptionSetIdAndMemberId(memberId,
	 * optionSetId); }
	 */
	
	// 카트삭제
	@Override
	public void deleteCart(Long cartNo) {
		cartRepository.deleteById(cartNo);
	}
	
	// 담긴 장바구니 목록 수량
	/*
	 * @Override public int countByMemberId(String memberId) { return
	 * cartRepository.countByMemberId(memberId); }
	 */
	
	// 장바구니 제품 수량 변경
	//@Override
//	public Cart updateCartQty(Cart updateCart) throws Exception{
//		final Optional<Cart> findCart =  cartRepository.findById(updateCart.getId());
//		// 조건문 필요? 이미 장바구니에 담겨있는 상황에서 버튼클릭
//		if(findCart.isPresent()) {
//			Cart cart = findCart.get();
//			cart.setCartQty(updateCart.getCartQty());
//			return cart;
//		}
//		throw new Exception("장바구니에 안담겨있음");
//	}
	
}
