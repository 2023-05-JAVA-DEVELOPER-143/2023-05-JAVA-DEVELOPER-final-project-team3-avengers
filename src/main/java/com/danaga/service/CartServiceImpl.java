package com.danaga.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Service;
import com.danaga.dao.MemberDao;
import com.danaga.dao.product.OptionSetDao;
import com.danaga.dto.CartDto;
import com.danaga.dto.FUserCartResponseDto;
import com.danaga.dto.SUserCartResponseDto;

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
	public List<SUserCartResponseDto> findsUserCartList(String sUserId) throws Exception {
		Long memberId = findMemberId(sUserId);
		System.out.println(">>>>>>>>>>>>>" + memberId);
		List<Cart> findCarts = cartRepository.findByMemberId(memberId);
		System.out.println(">>>>>>>>>> findCarts 리스트의 사이즈 == " + findCarts.size());
		List<SUserCartResponseDto> list = new ArrayList<>();
		for (int i = 0; i < findCarts.size(); i++) {
			list.add(SUserCartResponseDto.toDto(findCarts.get(i)));
		}
		return list;
	}

	@Override
	public FUserCartResponseDto findfUserCartList(CartDto dto) {
		OptionSet findOptionset = optionSetDao.findById(dto.getId());
		return FUserCartResponseDto.toDto(findOptionset, dto.getQty());
	}

	// 카트에 동일제품 체크 후 장바구니 수량 업데이트 or 카트 인서트
	@Override
	public void addCart(CartDto dto, String value) throws Exception {
		// 로그인 유저 아이디와 OptionSetId 로 동일제품 찾기
		Cart findCart = cartRepository.findByOptionSetIdAndMemberId(dto.getId(), memberDao.findMember(value).getId());
		// 인서트할 카트
		Cart addCart = Cart.builder().member(memberDao.findMember(value)).optionSet(optionSetDao.findById(dto.getId()))
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
	public CartDto updateCartQty(CartDto dto, String value) throws Exception {
		Long memberId = memberDao.findMember(value).getId();
		Cart findCart = cartRepository.findByOptionSetIdAndMemberId(dto.getId(), memberId);
		findCart.setQty(dto.getQty());
		cartRepository.save(findCart);
		return CartDto.builder().id(findCart.getId()).qty(dto.getQty()).build();
	}

	@Override
	public Cart findCart(Long id) {
		return cartRepository.findById(id).get();
	}

	// 옵션셋 변경
	@Override
	public List<CartDto> updateCartOptionSet(List<Long> ids, String sUserId) throws Exception {
		List<CartDto> updateCarts = new ArrayList<>();
		Long memberId = memberDao.findMember(sUserId).getId(); // 회원의 MemberPk
		Long oldOptionsetId = ids.get(0); // 기존 카트에 담겨있던 OptionsetId
		Long changeOptionsetId = ids.get(1); // 변경하고자 하는 OptionsetId
		// 옵션셋 변경할 카트
		Cart findCart = cartRepository.findByOptionSetIdAndMemberId(oldOptionsetId, memberId); // 기존 옵션셋이 담긴 카트
		// 기존 옵션셋 카트 수량 2 이상일시 사용할 기존 옵션셋카트

		Cart newCart = Cart.builder().member(findCart.getMember()).optionSet(optionSetDao.findById(ids.get(1))).qty(1)
				.build(); // 변경된 옵션셋

		// 변경하고자하는 옵션셋과 멤버아이디로 이미 존재하는지 체크
		Cart findDuplicateCart = cartRepository.findByOptionSetIdAndMemberId(ids.get(1), findCart.getMember().getId());

		if (findDuplicateCart == null) {
			// 변경할 카트 수량 1일시 findCart == 옵션셋이 변경된 카트
			if (findCart.getQty() == 1) {
				// 기존 옵션셋아이디와 멤버아이디로 찾은 카트의 옵션셋을 변경 후 세이브
				findCart.setOptionSet(optionSetDao.findById(changeOptionsetId));
				cartRepository.save(findCart);

				// updateCarts.add(CartUpdateResponseDto.toDto(findCart));
			} else if (findCart.getQty() >= 2) {
				// 기존 카트의 수량이 2 이상 , 중복 옵션셋 카트 X == 수량 변경시 기존 카트 수량 -1 후 세이브 , 변경 카트 인써트
				findCart.setQty(findCart.getQty() - 1);
				cartRepository.save(findCart);
				cartRepository.save(newCart);

				// updateCarts.add(CartUpdateResponseDto.toDto(newCart));
				// updateCarts.add(CartUpdateResponseDto.toDto(findCart));
			}
		} else {
			if (findCart.getQty() == 1) {
				// 기존 카트의 수량 1 , 중복 옵션셋 카트 O == 중복 옵션셋 카트 수량 + 1 , 기존 카트는 삭제
				findDuplicateCart.setQty(findDuplicateCart.getQty() + 1);
				cartRepository.save(findDuplicateCart);
				cartRepository.deleteById(findCart.getId());

			} else if (findCart.getQty() >= 2) {
				// 기존 카트의 수량 2 이상 , 중복 옵션셋 카트 O == 중복 옵션셋 카트 수량 + 1 , 기존 변경전 옵션셋 수량 -1
				findCart.setQty(findCart.getQty() - 1);
				findDuplicateCart.setQty(findDuplicateCart.getQty() + 1);
				cartRepository.save(findCart);
				cartRepository.save(findDuplicateCart);

				// updateCarts.add(CartUpdateResponseDto.toDto(findCart));
				// updateCarts.add(CartUpdateResponseDto.toDto(findDuplicateCart));
			}
		}
		return null;
	}

	/*
	 * 카트 1개 삭제
	 */
	@Override
	public void deleteCart(Long optionSetId, String value) throws Exception {
		Long memberId = memberDao.findMember(value).getId();
		// 삭제할 카트
		Cart findCart = cartRepository.findByOptionSetIdAndMemberId(optionSetId, memberId);
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
	};

}
/*****************************************************************************************/