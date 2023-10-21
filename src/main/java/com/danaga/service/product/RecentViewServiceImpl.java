package com.danaga.service.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.dao.product.InterestDao;
import com.danaga.dao.product.OptionSetDao;
import com.danaga.dao.product.OptionsDao;
import com.danaga.dao.product.ProductDao;
import com.danaga.dao.product.RecentViewDao;
import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.ProductDto;
import com.danaga.dto.product.RecentViewDto;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;
import com.danaga.entity.Product;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RecentViewServiceImpl implements RecentViewService{
	private final RecentViewDao recentViewDao;
	private final OptionSetDao optionSetDao;
	
	//product detail 조회시 recentView 추가 
	public ResponseDto<?> addRecentView(RecentViewDto dto){
		RecentViewDto savedDto = recentViewDao.save(dto);
		List<RecentViewDto> data = new ArrayList<RecentViewDto>();
		data.add(savedDto);
		return ResponseDto.<RecentViewDto>builder().data(data).build();
	}
	
	//나의 최근본 상품 전체 삭제 
	public ResponseDto<?> removeMyRecentViews(Long memberId){
		recentViewDao.deleteAll(memberId);
		List<OptionSet> data = optionSetDao.findAllByRecentView_MemberId(memberId);
		return ResponseDto.<OptionSet>builder().data(data).build();
	}
	
	//최근본상품 하나 삭제 
	public ResponseDto<?> removeRecentView(RecentViewDto dto){
		recentViewDao.delete(dto);
		List<OptionSet> data = optionSetDao.findAllByRecentView_MemberId(dto.getMemberId());
		return ResponseDto.<OptionSet>builder().data(data).build();
	}
	
	//나의 최근 본 상품 전체 조회 
	public ResponseDto<?> myAllRecentViews(Long memberId){
		List<OptionSet> myRecentViews = optionSetDao.findAllByRecentView_MemberId(memberId);
		return ResponseDto.<OptionSet>builder().data(myRecentViews).build();
	}
	
	
	
}
