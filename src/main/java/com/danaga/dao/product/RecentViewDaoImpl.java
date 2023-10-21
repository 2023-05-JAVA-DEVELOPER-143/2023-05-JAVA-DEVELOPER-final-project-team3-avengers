package com.danaga.dao.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.dto.product.RecentViewDto;
import com.danaga.entity.OptionSet;
import com.danaga.entity.RecentView;
import com.danaga.repository.product.RecentViewRepository;

@Repository
public class RecentViewDaoImpl implements RecentViewDao{

	@Autowired
	private RecentViewRepository repository;
	
	//나의 최근본 상품 하나 선택 삭제
	@Override
	public void delete(RecentViewDto dto) {
		repository.deleteByMemberIdAndOptionSetId(dto.getMemberId(),dto.getOptionSetId());
	}
	//나의 최근본 상품 전체 삭제
	@Override
	public void deleteAll(Long memberId) {
		repository.deleteByMemberId(memberId);
	}

	//recentView 추가
	@Override
	public RecentViewDto save(RecentViewDto dto) {
		return repository.save(dto);
	}
	
}
