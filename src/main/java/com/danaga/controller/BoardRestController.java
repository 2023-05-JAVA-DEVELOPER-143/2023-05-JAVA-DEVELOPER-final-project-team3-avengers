package com.danaga.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.dto.BoardDto;
import com.danaga.service.BoardService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardRestController {

	public final BoardService bService;
	
	@PatchMapping("/{id}/upIsLike")
	public ResponseEntity<BoardDto> upIsLike(@PathVariable("boardId") Long boardId, @RequestBody Long memberId) {
		log.info("memberId",memberId);
		log.info("boardId",boardId);
		BoardDto dtos = bService.upIsLike(boardId,memberId);
		log.info("dto = {}",dtos);
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}
	
	@PatchMapping("/{id}/upDisLike")
	public ResponseEntity<BoardDto> upDisLike(@PathVariable("boardId") Long boardId, @RequestBody Long memberId) {
		log.info("boardId",boardId);
		BoardDto dtos = bService.upDisLike(boardId,memberId);
		log.info("dto = {}",dtos);
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}
}
