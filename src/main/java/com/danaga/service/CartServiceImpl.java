package com.danaga.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.danaga.dao.CartDao;
import com.danaga.dao.MemberDao;
import com.danaga.dto.CartCreateDto;

import lombok.RequiredArgsConstructor;
import com.danaga.entity.Cart;
import com.danaga.entity.Member;
import com.danaga.repository.CartRepository;
import com.danaga.repository.MemberRepository;
import com.danaga.repository.OptionSetRepository;

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
				.qty(dto.getCartQty()).build();
		if (findCart == null) {
			cartRepository.save(addCart);
		} else {
			findCart.setQty(findCart.getQty() + dto.getCartQty());
		}
	}

	// 카트 한개 삭제
	@Override
	public void deleteCart(Long id) throws Exception {
		cartRepository.deleteById(id);
	}
}
