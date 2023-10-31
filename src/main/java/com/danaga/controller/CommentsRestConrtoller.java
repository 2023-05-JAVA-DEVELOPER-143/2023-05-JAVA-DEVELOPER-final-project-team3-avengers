package com.danaga.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.dto.CommentDto;
import com.danaga.service.CommentsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentsRestConrtoller {

	private final CommentsService cService;
	
	@PostMapping("/{boardId}/create")
	public ResponseEntity<CommentDto> create (@PathVariable("boardId")Long boardId, @RequestBody CommentDto dto) {
		
		log.info("boardId : {}",boardId);
		log.info("dto : {}",dto);
		CommentDto target = cService.createComment(dto,boardId);
		log.info("target : {}",target);
		return ResponseEntity.status(HttpStatus.OK).body(target);
	}
	
	@GetMapping("/{boardId}/list")
	public ResponseEntity<List<CommentDto>> getConmments(@PathVariable("boardId")Long boardId){
		List<CommentDto> comments= cService.comments(boardId);
		return ResponseEntity.status(HttpStatus.OK).body(comments);
	}
	
	@PatchMapping("/{commentId}/edit")
	public ResponseEntity<CommentDto> update(@PathVariable("commentId")Long commentId,@RequestBody CommentDto dto){
		CommentDto target = cService.update(commentId, dto);
		return ResponseEntity.status(HttpStatus.OK).body(target);
	}
	
	@DeleteMapping("/{commentId}/delete")
	public ResponseEntity<CommentDto> update(@PathVariable("commentId")Long commentId){
		CommentDto target = cService.delete(commentId);
		return (target!=null)? ResponseEntity.status(HttpStatus.NO_CONTENT).body(target) :
			ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
}
