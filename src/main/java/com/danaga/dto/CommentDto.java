package com.danaga.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.danaga.entity.Comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	private Long id;
	private String writer;
	private String content;
	private String pw;
	private Long parentId;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	private Long boardId;
	
	public static CommentDto createDto(Comments comment) {
		
		return CommentDto.builder()
				.id(comment.getId()).writer(comment.getWriter()).content(comment.getContent())
				.pw(comment.getPw()).createTime(comment.getCreateTime()).parentId(comment.getParent().getId())
				.boardId(comment.getBoard().getId()).updateTime(comment.getUpdateTime())
				.build();
	}
	
	
	
}
