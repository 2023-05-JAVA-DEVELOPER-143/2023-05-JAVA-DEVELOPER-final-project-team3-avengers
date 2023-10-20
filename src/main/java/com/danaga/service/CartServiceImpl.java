package com.danaga.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.danaga.dao.MemberDao;
import com.danaga.dto.CartCreateDto;
import com.danaga.dto.CartUpdateDto;

import lombok.RequiredArgsConstructor;
import com.danaga.entity.Cart;
import com.danaga.repository.CartRepository;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	private final MemberDao memberDao;
	private final CartRepository cartRepository;

	// 유저 카트 리스트
	@Override
	public List<Cart> findCartList(String value) throws Exception {
		Long memberId = memberDao.findMember(value).getId();
		return cartRepository.findByMemberId(memberId);
	}

	// 카트에 동일제품 체크 후 장바구니 수량 업데이트 or 카트 인서트
	@Override
	public void addCart(CartCreateDto dto, String value) throws Exception {
		// 로그인 유저 아이디와 OptionSetId 로 동일제품 찾기
		Cart findCart = cartRepository.findByOptionSetIdAndMemberId(dto.getOptionset().getId(),
				memberDao.findMember(value).getId());
		// 인서트할 카트
		Cart addCart = Cart.builder().member(memberDao.findMember(value)).optionSet(dto.getOptionset())
				.qty(dto.getQty()).build();
		if (findCart == null) {
			cartRepository.save(addCart);
		} else {
			findCart.setQty(findCart.getQty() + dto.getQty());
			cartRepository.save(findCart);
		}
	}

	/*
	 * 카트 1개 삭제
	 */
	@Override
	public void deleteCart(Long id) throws Exception {
		cartRepository.deleteById(id);
	}
	
	
	// 장바구니 수량 변경
	public void updateCart(CartUpdateDto dto) {
		Cart findCart = cartRepository.findById(dto.getId()).get();
		findCart.setQty(dto.getQty());
		cartRepository.save(findCart);
		
	}
	
	// 장바구니 수량변경 or 옵션 변경
	@Override
	public Cart updateCart(Cart updateCart) {
		Cart findCart = cartRepository.findById(updateCart.getId()).get();
		findCart.setQty(updateCart.getQty());
		findCart.setOptionSet(updateCart.getOptionSet());
		return findCart;
	}
	
}
