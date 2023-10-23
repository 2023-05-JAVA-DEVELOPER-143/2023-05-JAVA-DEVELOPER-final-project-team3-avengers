package com.danaga.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.danaga.dao.MemberDao;
import com.danaga.dto.CartCreateDto;
import com.danaga.dto.CartUpdateDto;

import lombok.RequiredArgsConstructor;
import com.danaga.entity.Cart;
import com.danaga.entity.OptionSet;
import com.danaga.repository.CartRepository;

import groovyjarjarantlr4.v4.parse.ANTLRParser.exceptionGroup_return;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	private final MemberDao memberDao;
	private final CartRepository cartRepository;
	//private final OptionSetDao optionSetDao;

	
	
	// 유저 카트 리스트
	@Override
	public List<Cart> findCartList(String sUserId) throws Exception {
		Long memberId = findMemberId(sUserId);
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
			findCart.setQty(findCart.getQty() + dto.getQty()); //이거 뭐죠?? ㅋㅋ
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
	
	@Override
	public void deleteByMemberId(Long memberId) {
		cartRepository.deleteByMemberId(memberId);
	}

	// 카트 전체삭제 [세션(Controller) -> sUserId -> memberId -> delete ]
	public void deleteCarts(String sUserId) throws Exception {
		Long memberId = findMemberId(sUserId);
		cartRepository.deleteByMemberId(memberId);
	};
	
	// 헤더영역 장바구니 아이콘에 몇개 담긴지 숫자 표시 
	@Override
	public int countCarts(String sUserId) throws Exception {
		Long memberId = findMemberId(sUserId);
		return cartRepository.countByMemberId(memberId);
	}
	
	
	Long findMemberId(String sUserId) throws Exception{
		return memberDao.findMember(sUserId).getId();
	};
}
