package com.danaga.service.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.danaga.dao.product.InterestDao;
import com.danaga.dao.product.OptionSetDao;
import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.InterestDto;
import com.danaga.entity.OptionSet;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class InterestServiceImpl implements InterestService {

	private final InterestDao interestDao;
	private final OptionSetDao optionSetDao;
	
	
	//제품에서 하트 누르면 관심제품 추가
	//제품에서 하트 누르면 관심제품 삭제
	@Override
	@Transactional
	public ResponseDto<?> clickHeart(InterestDto dto) {
		if(interestDao.isInterested(dto)) {
			interestDao.delete(dto);
		}else {
			interestDao.save(dto);
		}
		OptionSet clickedOptionSet = optionSetDao.findById(dto.getOptionSetId());
		//옵션셋을 반환할지 그냥 아무것도 반환 안해도 되는지 
		//이미 리스트로 뿌린 상태인데 굳이? 
		List<OptionSet> data = new ArrayList<OptionSet>();
		data.add(clickedOptionSet);
		return ResponseDto.<OptionSet>builder().data(data).build();
	}
	//나의 관심상품 리스트 전체 조회
	@Override
	public ResponseDto<?> myInterestingList(Long memberId) {
		List<OptionSet> data = optionSetDao.findAllByInterest_MemberId(memberId);
		return ResponseDto.<OptionSet>builder().data(data).build();
	}
	//나의 관심상품 리스트 전체 삭제
	@Override
	public ResponseDto<?> emptyMyInterestingList(Long memberId) {
		interestDao.deleteAll(memberId);
		List<OptionSet> data = optionSetDao.findAllByInterest_MemberId(memberId);
		return ResponseDto.<OptionSet>builder().data(data).build();
	}
	
}
