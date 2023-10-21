package com.danaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.dto.RecentViewDto;
import com.danaga.entity.RecentView;

public interface RecentViewRepository extends JpaRepository<RecentView, Long>{

	void deleteByMemberIdAndOptionSetId(Long memberId, Long optionSetId);
	
	void deleteByMemberId(Long memberId);
	
	RecentViewDto save(RecentViewDto dto);
}
