package com.danaga.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.danaga.dto.BoardDto;
import com.danaga.entity.LikeConfig;

@SpringBootTest
class BoardServiceTest {

	@Autowired
	BoardService service;
	@Autowired
	LikeConfigService lcService;

	@Test
	@Disabled
	void 자유게시판_리스트들() {
		List<BoardDto> list = service.boards(1L);
		System.out.println(">>>>>>>>>>>> " + list);
	}

	@Test
	@Disabled
	void 게시글_디테일() {
		 BoardDto board= service.boardDetail(3L);
		 System.out.println(">>>>> 3번글 :"+board);
	}

	@Test
	void 게시물_생성() {
		BoardDto dto = new BoardDto();
		dto.setTitle("Test Title");
		dto.setContent("Test Content");
		dto.setBoardGroupId(1L);
		dto.setMemberId(3L);
//		List<LikeConfig> configs = lcService.create(dto);
//		BoardDto createdBoard = service.createBoard(dto, configs);

		
//		 assertNotNull(createdBoard);
//		 assertEquals("Test Title",createdBoard.getTitle());
//		 assertEquals("Test Content",createdBoard.getContent());
//		 System.out.println(createdBoard);
		 
	}

	@Test
	@Disabled
	void 좋아요_누르기() {
		
	}
}
