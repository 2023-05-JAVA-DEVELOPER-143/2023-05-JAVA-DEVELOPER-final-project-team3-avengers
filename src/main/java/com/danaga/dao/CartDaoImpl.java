

	
	/*
	 * 카트리스트 보기
	 */
	/*@Override
	public List<Cart> findCartList(String member_id) {
		List<Cart> cartList = cartRepository.findAll();
		return cartList;
	}*/
	
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
	/*@Override
	public Cart create(Cart cart) {
		boolean exists = cartRepository.existsById(cart.getOptionSet().getId());
		if(exists) {
			cart.setCartQty(cart.getCartQty()+1);
			Cart insertCart = cartRepository.save(cart);
			return insertCart;
		}
		return cartRepository.save(cart);
	}*/
	
	
	/*
	 * 카트 1개 삭제
	 */
	/*@Override
	public void deleteCart(Long id) throws Exception {
		Optional<Cart> findCart = cartRepository.findById(id);
		if(findCart.isEmpty()) {
			throw new Exception();
		}
		cartRepository.delete(findCart.get());
	}*/
	/*
	 * 카트 수량 변경
	 */
	/*@Override
	public Long increaseQty(Cart cart) {
		Cart findCart = cartRepository.findById(cart.getId()).get();
		
		return null;
	}

	@Override
	public Long decreaseQty(Cart cart) {
		return null;
	}*/
package com.danaga.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.entity.Cart;
import com.danaga.repository.CartRepository;	



import org.springframework.stereotype.Repository;

import com.danaga.entity.Cart;
import com.danaga.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CartDaoImpl implements CartDao {
//	private final CartRepository cartRepository;
//	
//	// 카트번호로 찾기 
//	@Override
//	public Cart getCart(Long cartNo) {
//		return cartRepository.findById(cartNo).get();
//		
//		
//	}
//
//	// 유저 장바구니 목록
//	/*
//	 * @Override public List<Cart> getCarts(String memberId) { return
//	 * cartRepository.findCartByMemberId(memberId); }
//	 */
//
//	// 카트 담기 
//	@Override
//	public void addCart(Cart cart) {
//		/*
//		 * final Cart findCart =
//		 * cartRepository.findCartByOptionSetIdAndMemberId(cart.getMember().getMemberId(
//		 * ),cart.getOptionSet().getId()); // 있으면 수량 업데이트 -->> 이미 담겨있으면 팝업창 ? 메쏘드 실행
//		 * if(findCart != null) { findCart.setCartQty(findCart.getCartQty()+1); //return
//		 * findCart; } // 없으면 인서트 //return cartRepository.save(cart);
//		 */
//	}
//
//	// 카트 목록 확인 
//	/*
//	 * @Override public Cart getCartByOptionSetId(String memberId, Long optionSetId)
//	 * { return cartRepository.findCartByOptionSetIdAndMemberId(memberId,
//	 * optionSetId); }
//	 */
//	
//	// 카트삭제
//	/*@Override
//	public void deleteCart(Long cartNo) {
//		cartRepository.deleteById(cartNo);
//	}*/
//	
//	// 담긴 장바구니 목록 수량
//	/*
//	 * @Override public int countByMemberId(String memberId) { return
//	 * cartRepository.countByMemberId(memberId); }
//	 */
//	
//	// 장바구니 제품 수량 변경
//	//@Override
////	public Cart updateCartQty(Cart updateCart) throws Exception{
////		final Optional<Cart> findCart =  cartRepository.findById(updateCart.getId());
////		// 조건문 필요? 이미 장바구니에 담겨있는 상황에서 버튼클릭
////		if(findCart.isPresent()) {
////			Cart cart = findCart.get();
////			cart.setCartQty(updateCart.getCartQty());
////			return cart;
////		}
////		throw new Exception("장바구니에 안담겨있음");
////	}
	

}
