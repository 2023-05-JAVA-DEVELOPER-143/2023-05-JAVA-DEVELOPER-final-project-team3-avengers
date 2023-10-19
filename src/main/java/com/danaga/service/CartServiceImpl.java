package com.danaga.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.danaga.dao.CartDao;
import lombok.RequiredArgsConstructor;
import com.danaga.entity.Cart;
import com.danaga.repository.CartRepository;
import com.danaga.repository.MemberRepository;
import com.danaga.repository.OptionSetRepository;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
	
	private final CartDao cartDao;
	private final CartRepository cartRepository;
	private final MemberRepository memberRepository;
	private final OptionSetRepository optionSetRepository;
	
	// 유저 카트 리스트
	@Override
	public List<Cart> findCartList(String memberId) {
		return cartRepository.findByMemberId(memberId);
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
	
	

	// static boolean isDuplicateOptionset; // 카트 제품 중복체크

	// boolean checkOptionSet(Long id) {
	// }

	// 제품 중복 체크 -> 수량 증가 or 카트 담기
	// @Override
	// cart -> Dto
	/*
	 * public void addCart(Cart cart) { // isDuplicateOptionset =
	 * checkOptionSet(cart.getOptionSet().getId()); if (isDuplicateOptionset) { //
	 * 장바구니에 담긴 상품 -> // cartCqy 1증가 // cart.setCartQty(cart.getCartQty()+1);
	 * //return cartDao.addCart(cart); } //return cartDao.addCart(cart); }
	 * 
	 * @Override public List<Cart> getCarts(String memberId) { return
	 * cartDao.getCarts(memberId); }
	 */

	/*
	 * 카트 1개 삭제
	 */
//	@Override
//	public void deleteCart(Long id) throws Exception {
//		Optional<Cart> findCart = cartRepository.findById(id);
//		if(findCart.isEmpty()) {
//			throw new Exception();
//		}
//		cartRepository.delete(findCart.get());
//	}
	@Override
	public void deleteCart(Long id) throws Exception {
		cartDao.deleteCart(id);
	}

}
