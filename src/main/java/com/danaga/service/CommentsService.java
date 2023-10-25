package com.danaga.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.dto.CommentDto;
import com.danaga.dto.ResponseDto;
import com.danaga.entity.Board;

import com.danaga.entity.Comments;
import com.danaga.repository.BoardRepository;
import com.danaga.repository.CommentsRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentsService {

	@Autowired
	private CommentsRepository cRepository;

	@Autowired
	private BoardRepository bRepository;

	
	public List<CommentDto> comments(Long boardId) {
		return cRepository.findByBoard_Id(boardId).stream().map(comments -> CommentDto.createDto(comments))
				.collect(Collectors.toList());
	}

	@Transactional
	public CommentDto createComment(CommentDto commentDto) {

		Board board = bRepository.findById(commentDto.getBoardId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

		Comments parent = null;
		// 자식댓글인 경우
		if (commentDto.getParentId() != null) {
			parent = cRepository.findById(commentDto.getParentId())
					.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상위 댓글입니다."));

			// 부모댓글의 게시글 번호와 자식댓글의 게시글 번호 같은지 체크하기
			if (parent.getBoard().getId() != commentDto.getBoardId()) {
				throw new IllegalArgumentException("존재하지 않는 게시물입니다.");
			}
		}

		Comments comment = Comments.builder().writer(commentDto.getWriter()).board(board)
				.content(commentDto.getContent()).build();
		if (null != parent) {
			comment.updateParent(parent);
		}
		cRepository.save(comment);

		CommentDto commentResponseDto = null;
		if (parent != null) {
			commentResponseDto = CommentDto.builder().id(comment.getId()).writer(comment.getWriter())
					.content(comment.getContent()).createTime(comment.getCreateTime())
					.updateTime(comment.getUpdateTime()).parentId(comment.getParent().getId()).build();
		} else {
			commentResponseDto = CommentDto.builder().id(comment.getId()).writer(comment.getWriter())
					.content(comment.getContent()).createTime(comment.getCreateTime())
					.updateTime(comment.getUpdateTime()).build();
		}

		return commentResponseDto;
	}

	@Transactional
	public CommentDto update(Long id, CommentDto dto) {
		// 댓글 조회 및 예외
		Comments target = cRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));

		if (dto.getPw() == target.getPw()) {
			throw new IllegalArgumentException("댓글 수정 실패! 대상 게시글의 비밀번호가 틀립니다.");
		}
		// 댓글 수정
		target.update(dto);
		// DB로 갱신
		Comments updated = cRepository.save(target);
		// 댓글 엔티티를 DTO로 변환 및 반환
		return CommentDto.createDto(updated);
	}

	@Transactional
	public CommentDto delete(Long id) {
		// 댓글 조회 및 예외
		Comments target = cRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상 댓글을 찾을수 없습니다."));
		// 댓글을 DB에서 삭제
		cRepository.delete(target);
		// 삭제 댓글을 DTO로 반환
		return CommentDto.createDto(target);
	}
}
