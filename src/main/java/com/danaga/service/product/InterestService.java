package com.danaga.service.product;

import java.util.List;

import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.InterestDto;
import com.danaga.entity.OptionSet;

public interface InterestService {
	//제품에서 하트 누르면 관심제품 추가
	//제품에서 하트 누르면 관심제품 삭제
	ResponseDto<?> clickHeart(InterestDto dto);
	//나의 관심상품 전체 조회 
	ResponseDto<?> myInterestingList(Long memberId);
	//나의 관심상품 전체 삭제
	ResponseDto<?> emptyMyInterestingList(Long memberId);
	ResponseDto<?> deleteHeart(InterestDto dto);
}
