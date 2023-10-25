package com.danaga.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.danaga.dao.MemberDao;
import com.danaga.dao.product.OptionSetDao;
import com.danaga.dto.CartUpdateOptionSetDto;
import com.danaga.dto.CartDto;
import com.danaga.dto.CartUpdateResponseDto;

import lombok.RequiredArgsConstructor;
import com.danaga.entity.Cart;
import com.danaga.entity.OptionSet;
import com.danaga.repository.CartRepository;



/*****************************************************************************************/
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	private final MemberDao memberDao;
	private final CartRepository cartRepository;
	private final OptionSetDao optionSetDao;

	// 유저 카트 리스트
	@Override
	public List<Cart> findCartList(String sUserId) throws Exception {
		Long memberId = findMemberId(sUserId);
		return cartRepository.findByMemberId(memberId);
	}

	// 카트에 동일제품 체크 후 장바구니 수량 업데이트 or 카트 인서트
	@Override
	public void addCart(CartDto dto, String value) throws Exception {
		// 로그인 유저 아이디와 OptionSetId 로 동일제품 찾기
		Cart findCart = cartRepository.findByOptionSetIdAndMemberId(dto.getId(),
				memberDao.findMember(value).getId());
		// 인서트할 카트
		OptionSet optionSet = optionSetDao.findById(dto.getId());
		Cart addCart = Cart.builder().member(memberDao.findMember(value)).optionSet(optionSet)
				.qty(dto.getQty()).build();
		if (findCart == null) {
			cartRepository.save(addCart);
		} else {
			findCart.setQty(findCart.getQty() + dto.getQty());
			cartRepository.save(findCart);
		}
	}

	// 제품 수량 변경
	@Override
	public CartDto updateCartQty(CartDto dto,String value) throws Exception {
		Long memberId = memberDao.findMember(value).getId();
		Cart findCart = cartRepository.findByOptionSetIdAndMemberId(dto.getId(),memberId);
		findCart.setQty(dto.getQty());
		cartRepository.save(findCart);
		return CartDto.builder().id(findCart.getId()).qty(dto.getQty()).build();
	}

	@Override
	public Cart findCart(Long id) {
		return cartRepository.findById(id).get();
	}
	
	
//	// 옵션셋 변경
//	@Override
//	public List<CartUpdateResponseDto> updateCartOptionSet(CartUpdateOptionSetDto dto) {
//		List<CartUpdateResponseDto> updateCarts = new ArrayList<>();
//		// 옵션셋 변경할 카트
//		Cart findCart = cartRepository.findById(dto.getId()).get();
//		// 기존 옵션셋 카트 수량 2 이상일시 임시로 사용할 기존 옵션셋카트
//		Cart newCart = Cart.builder().member(findCart.getMember()).optionSet(dto.getOptionSet()).qty(1).build();
//
//		// 변경된 옵션셋과 멤버아이디로 이미 존재하는지 체크
//		Cart findDuplicateCart = cartRepository.findByOptionSetIdAndMemberId(newCart.getOptionSet().getId(),
//				newCart.getMember().getId());
//
//		// 장바구니에 중복 옵션셋 X
//		if (findDuplicateCart == null) {
//			// 변경할 카트 수량 1일시 findCart == 옵션셋이 변경된 카트
//			if (findCart.getQty() == 1) {
//				cartRepository.save(findCart);
//				updateCarts.add(CartUpdateResponseDto.toDto(findCart));
//			} else if (findCart.getQty() >= 2) {
//				findCart.setQty(findCart.getQty() - 1);
//				cartRepository.save(findCart);
//				cartRepository.save(newCart);
//				updateCarts.add(CartUpdateResponseDto.toDto(newCart));
//				updateCarts.add(CartUpdateResponseDto.toDto(findCart));
//			}
//		} else { // 장바구니에 중복 옵션셋 O
//			if (findCart.getQty() == 1) {
//				// 중복 옵션셋 카트 수량 + 1 , 기존 변경전 옵션셋 삭제
//				findDuplicateCart.setQty(findDuplicateCart.getQty() + 1);
//				cartRepository.save(findDuplicateCart);
//				cartRepository.deleteById(findCart.getId());
//			} else if (findCart.getQty() >= 2) {
//				// 중복 옵션셋 카트 수량 + 1 , 기존 변경전 옵션셋 수량 -1
//				findCart.setQty(findCart.getQty() - 1);
//				findDuplicateCart.setQty(findDuplicateCart.getQty() + 1);
//				cartRepository.save(findCart);
//				cartRepository.save(findDuplicateCart);
//				updateCarts.add(CartUpdateResponseDto.toDto(findCart));
//				updateCarts.add(CartUpdateResponseDto.toDto(findDuplicateCart));
//			}
//		}
//		return updateCarts;
//	}

	/*
	 * 카트 1개 삭제
	 */
	@Override
	public void deleteCart(Long optionSetId,String value) throws Exception {
		Long memberId = memberDao.findMember(value).getId();
		// 삭제할 카트
		Cart findCart = cartRepository.findByOptionSetIdAndMemberId(optionSetId,memberId);
		// 찾은 카트의 Id로 삭제
		cartRepository.deleteById(findCart.getId());
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

	Long findMemberId(String sUserId) throws Exception {
		return memberDao.findMember(sUserId).getId();
	}

	@Override
	public List<CartUpdateResponseDto> updateCartOptionSet(CartUpdateOptionSetDto dto) {
		// TODO Auto-generated method stub
		return null;
	};


}
/*****************************************************************************************/