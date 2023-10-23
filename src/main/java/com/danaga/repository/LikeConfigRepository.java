package com.danaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.LikeConfig;

public interface LikeConfigRepository extends JpaRepository<LikeConfig, Long>{

	LikeConfig findByBoard_IdAndMember_id(Long boardId,Long MemberId);
	
	void deleteByBoard_Id(Long boardId);
	
}
