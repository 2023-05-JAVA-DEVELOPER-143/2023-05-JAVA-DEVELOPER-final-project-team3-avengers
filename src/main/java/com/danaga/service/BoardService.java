package com.danaga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.danaga.dao.BoardDaoImpl;
import com.danaga.entity.Board;

@Service
public class BoardService {

	@Autowired
	private BoardDaoImpl dao;
	
	public List<Board> findWhichBoards(Long boardGroupId){
		return dao.findWhichBoards(boardGroupId);
	}
	
	public Board boardDetail(Long boardGroupId, Long boardId) {
		return dao.boardDetail(boardGroupId, boardId);
	}
	
}
