package com.danaga.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.danaga.dto.BoardDto;
import com.danaga.entity.Board;
import com.danaga.entity.BoardGroup;
import com.danaga.entity.LikeConfig;
import com.danaga.entity.Member;
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
	//@Disabled
	void 게시글_생성_자유게시판() {
		List<LikeConfig> configs=service.configs();
		
		BoardDto dto = BoardDto.builder()
				.boardGroupId(1L).memberId(3L)
				.title("test1").content("test1").img1("testimg1").img2("img2")
				.img3("img3").img4("img4").img5("img5").isLike(0).disLike(0)
				.readCount(0).isAdmin(0).lConfigs(configs)
				.build();
		
		Board board = service.createBoard(dto,1L,1L);
		System.out.println(">>>>>board: "+board);
		assertNotNull(board);
	}
	@Test
	@Disabled
	void 게시글_삭제_자유게시판() {
		Long boardGroupId = 3L;
	    Long boardId = 11L;

	    // 게시물을 삭제하기 위해 delete 메서드를 호출합니다.
	    service.delete(boardGroupId, boardId);

	    // 게시물이 삭제되었는지 확인합니다.
	    Board deletedBoard = service.boardDetail(boardGroupId, boardId);
	    assertNull(deletedBoard, "게시물은 삭제되었어요");
	}
	@Test
	void 좋아요() {
		System.out.println("좋아요전 : "+service.boardDetail(1L, 1L));
		service.upIsLike(1L, 1L, 3L);
		System.out.println("좋아요후 : "+service.boardDetail(1L, 1L));
	}
}
