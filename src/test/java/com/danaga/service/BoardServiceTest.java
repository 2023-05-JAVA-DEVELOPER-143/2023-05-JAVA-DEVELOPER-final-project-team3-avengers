package com.danaga.service;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.danaga.dto.BoardDto;
import com.danaga.entity.Board;
@SpringBootTest
class BoardServiceTest {

	@Autowired
	BoardService service;
	@Test
	@Disabled
	void 자유게시판_리스트들() {
		List<Board>list=service.findWhichBoards(1L);
		System.out.println(">>>>>>>>>>>> "+list);
	}

	@Test
	@Disabled
	void 게시글_디테일() {
		Board board= service.boardDetail(1L, 3L);
		System.out.println(">>>>> 1번게시판안에 3번글 :"+board);
	}
	
	@Test
	void 게시글_생성_자유게시판() {
		
		BoardDto dto = BoardDto.builder()
				.id(11L).boardGroupId(1L).memberId(3L)
				.title("test1").content("test1").img1("testimg1")
				.isLike(null).disLike(null).readCount(null).isAdmin(null)
				.build();
		Board board = service.createBoard(dto);
		System.out.println(board);
	}
}
