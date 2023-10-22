package com.danaga.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.dto.product.RecentViewDto;
import com.danaga.entity.RecentView;

public interface RecentViewRepository extends JpaRepository<RecentView, Long>{

	void deleteByMemberIdAndOptionSetId(Long memberId, Long optionSetId);
	
	void deleteByMemberId(Long memberId);
	
	RecentView save(RecentView entity);
	
}
