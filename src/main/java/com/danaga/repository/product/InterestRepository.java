package com.danaga.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.dto.product.InterestDto;
import com.danaga.entity.Interest;

public interface InterestRepository extends JpaRepository<Interest, Long> {

	//관심상품 추가, 삭제 그냥 save, delete
	
	//유저 아이디로 관심상품 List 조회
	//List<Interest> findByMemberId(Long id);
	//이건 옵션셋에서 멤버아이디로 옵션셋 list 조회 
	
	//상품페이지에서 유저가 관심상품 체크했는지 표시하려면 
	Boolean existsByMemberIdAndOptionSetId(Long memberId,Long optionSetId);
	
	void deleteByOptionSetIdAndMemberId(Long optionSetId, Long memberId);
	
	void deleteByMemberId(Long memberId);
}
