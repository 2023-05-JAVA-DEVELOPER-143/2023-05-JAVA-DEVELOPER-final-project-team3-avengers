package com.danaga.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.danaga.entity.Board;
@SpringBootTest
class BoardServiceTest {

	@Autowired
	BoardService service;
	@Test
	void 자유게시판_리스트들() {
		List<Board>list=service.findWhichBoards(1L);
		System.out.println(">>>>>>>>>>>> "+list);
	}

	@Test
	void 게시글_디테일() {
		Board board= service.boardDetail(1L, 3L);
		System.out.println(">>>>> 1번게시판안에 3번글 :"+board);
	}
	
}
