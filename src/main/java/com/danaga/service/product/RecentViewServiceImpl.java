package com.danaga.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.dao.product.InterestDao;
import com.danaga.dao.product.OptionSetDao;
import com.danaga.dao.product.OptionsDao;
import com.danaga.dao.product.ProductDao;
import com.danaga.dao.product.RecentViewDao;
import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.ProductDto;
import com.danaga.dto.product.ProductListOutputDto;
import com.danaga.dto.product.RecentViewDto;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;
import com.danaga.entity.Product;
import com.danaga.entity.RecentView;
import com.danaga.exception.product.AlreadyExistsException.ExistsRecentViewException;
import com.danaga.exception.product.FoundNoObjectException;
import com.danaga.exception.product.FoundNoObjectException.FoundNoMemberException;
import com.danaga.exception.product.FoundNoObjectException.FoundNoOptionSetException;
import com.danaga.exception.product.ProductSuccessMsg;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RecentViewServiceImpl implements RecentViewService{
	private final RecentViewDao recentViewDao;
	private final OptionSetDao optionSetDao;
	
	//product detail 조회시 recentView 추가 
	public ResponseDto<?> addRecentView(RecentViewDto dto){
		try {
			recentViewDao.save(dto.toEntity(dto));
		} catch (FoundNoMemberException e) {
			e.printStackTrace();
			return ResponseDto.builder().error(e.getMsg()).build();
		} catch (FoundNoOptionSetException e) {
			e.printStackTrace();
			return ResponseDto.builder().error(e.getMsg()).build();
		} catch (ExistsRecentViewException e) {
			e.printStackTrace();
			return ResponseDto.builder().error(e.getMsg()).build();
		}
		return ResponseDto.builder().msg(ProductSuccessMsg.ADD_RECENTVIEW).build();
	}
	
	//나의 최근본 상품 전체 삭제 
	@Transactional()
	public ResponseDto<?> removeMyRecentViews(Long memberId){
		try {
			recentViewDao.deleteAll(memberId);
		} catch (FoundNoObjectException e) {
			e.printStackTrace();
			return ResponseDto.builder().error(e.getMsg()).build();
		}
		return ResponseDto.<ProductDto>builder().msg(ProductSuccessMsg.REMOVE_MY_RECENTVIEWS).build();
	}
	
	//최근본상품 하나 삭제 
	@Transactional
	public ResponseDto<?> removeRecentView(RecentViewDto dto){
		try {
			recentViewDao.delete(dto.toEntity(dto));
		} catch (FoundNoObjectException e) {
			e.printStackTrace();
			return ResponseDto.builder().error(e.getMsg()).build();
		}
		return ResponseDto.builder().msg(ProductSuccessMsg.REMOVE_RECENTVIEW).build();
	}
	
	//나의 최근 본 상품 전체 조회 
	public ResponseDto<?> myAllRecentViews(Long memberId){
		List<ProductListOutputDto> myRecentViews=new ArrayList<>();
		try {
			myRecentViews = optionSetDao.findAllByRecentView_MemberId(memberId).stream().map(t -> new ProductListOutputDto(t)).collect(Collectors.toList());
		} catch (FoundNoMemberException e) {
			e.printStackTrace();
			return ResponseDto.builder().error(e.getMsg()).build();
		};
		return ResponseDto.<ProductListOutputDto>builder().data(myRecentViews).msg(ProductSuccessMsg.FIND_MY_RECENTVIEWS).build();
	}
	
	//30일 지난 상품 삭제 
	public ResponseDto<?> removeOldRecents(){
		recentViewDao.removeOldRecents();
		return ResponseDto.<String>builder().msg(ProductSuccessMsg.REMOVE_OLD_RECENTVIEWS).build();
	}
	
}
