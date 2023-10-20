package com.danaga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.LikeConfig;

public interface LikeConfigRepository extends JpaRepository<LikeConfig, Long>{

	/*
	 	like or dis_like status = 0:아무것도 안눌린 상태, 1:좋아요 눌린 상태
	 	
	 	LikeConfig (pk) : id+boardId+memberId가 복합키로 설정되어있다.
	 	
	 	만약 id 1번은 10번게시물에 3번유저가 좋아요를 눌렀다면 like:1이 상태유지가 된다.
	 
		모든 메소드에서 List의 정보를 담아 넣어주면 끝.
	 */
}
