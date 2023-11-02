package com.danaga.service;

import java.util.List;

import com.danaga.dto.BoardDto;

public interface BoardService {
	List<BoardDto> popularPost();
	
	List<BoardDto> boards(Long boardGroupId);
	
	BoardDto boardDetail(Long id);
	
	BoardDto createBoard(BoardDto dto);
	
	BoardDto upIsLike(Long boardId, String userName);
	
	BoardDto upDisLike(Long boardId, String userName);
	
	BoardDto update(BoardDto dto);
	
	BoardDto delete(BoardDto dto,String userName);
	String getBoardGroupName(Long boardGroupId);
}
