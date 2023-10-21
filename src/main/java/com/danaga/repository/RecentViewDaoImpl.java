package com.danaga.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.dto.RecentViewDto;

@Repository
public class RecentViewDaoImpl implements RecentViewDao{

	@Autowired
	private RecentViewRepository repository;
	
	@Override
	public void delete(RecentViewDto dto) {
		repository.deleteByMemberIdAndOptionSetId(dto.getMemberId(),dto.getOptionSetId());
	}

	@Override
	public void deleteAll(Long memberId) {
		repository.deleteByMemberId(memberId);
	}

	@Override
	public RecentViewDto save(RecentViewDto dto) {
		return repository.save(dto);
	}
	
}
