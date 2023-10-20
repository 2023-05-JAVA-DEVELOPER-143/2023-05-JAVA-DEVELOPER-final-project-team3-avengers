package com.danaga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

	/*
	 
	 			pk 분류 
	 			1. 자유게시판
	 			2. 1:1문의
	 			3. FAQ
	 			4. 공지
			
			like_config
				ex ) pk :1 + status:1 = 유저OO의 0번 게시물에 좋아요누른상태 
				ex ) pk :1 + status:2 = 유저OO의 0번 게시물에 좋아요누른상태 
	 */
	//게시판 별로 게시물 리스트 뽑아주기.
	//List<Board> findByBoardConfig_IdOrderByCreateTime(Long configId);
	//하나의 게시판에 하나의 게시물디테일
	//Board findByBoardConfig_IdAndBoard_Id(Long configId,Long boardId);
	
	//공지사항
	//List<Board> findByIsAdminOrderByReadCount(Integer isAdmin);
	
}