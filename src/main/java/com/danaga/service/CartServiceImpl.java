package com.danaga.service;

import org.springframework.stereotype.Service;

import com.danaga.dao.CartDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	private final CartDao cartDao;

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

}
