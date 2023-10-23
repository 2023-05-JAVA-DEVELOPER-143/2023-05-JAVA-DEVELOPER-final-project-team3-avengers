package com.danaga.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.dto.BoardDto;
import com.danaga.entity.Board;
import com.danaga.entity.BoardGroup;
import com.danaga.entity.LikeConfig;
import com.danaga.entity.Member;
import com.danaga.repository.BoardGroupRepository;
import com.danaga.repository.BoardRepository;
import com.danaga.repository.LikeConfigRepository;
import com.danaga.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BoardService {

	@Autowired
	private BoardRepository bRepository;
	@Autowired
	private MemberRepository mRepository;
	@Autowired
	private BoardGroupRepository bgRepository;
	@Autowired
	private LikeConfigRepository lcRepository;

	// 게시물별 출력
	public List<LikeConfig> getConfigs() {
		return lcRepository.findAll();
	}

	public List<BoardDto> boards(Long boardGroupId) {
		return bRepository.findByBoardGroupIdOrderByCreateTimeDesc(boardGroupId).stream().map(board -> BoardDto.createBoardDto(board)).collect(Collectors.toList());
	}

	public BoardDto boardDetail(Long id) {
		Board target=bRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("대상이없습니다."));
		target.readCountUp(target);
		bRepository.save(target);
		BoardDto temp= BoardDto.createBoardDto(target);
		return temp;
	}
	

	// 생성
	@Transactional
	public BoardDto createBoard(BoardDto dto,List<LikeConfig> configs) {
		// 게시글 조회 및 예외처리
		Member memberT = mRepository.findById(dto.getMemberId())
				.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		BoardGroup boardGroupT = bgRepository.findById(dto.getBoardGroupId())
				.orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));
		
		if (dto.getId() != null) {
			throw new IllegalArgumentException("이미 존재하는 게시물ID입니다.");
		}
		// 게시글 엔티티 생성
		Board board = Board.createBoard(dto, memberT, boardGroupT, configs);

		// 게시글 엔티티를 DB에 저장
		Board created = bRepository.save(board);

		return BoardDto.createBoardDto(board);
	}

	// 좋아요
	@Transactional
	public BoardDto upIsLike(Long boardId, Long boardGroupId, Long memberId, Long likeConfigId) {
		Board target = bRepository.findByBoardGroup_IdAndId(boardGroupId, boardId);
		if(target.getId()==null) {
			throw new IllegalArgumentException("존재하지 않는 게시물입니다.");
		}
		Member member = mRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("접근이 잘못됬어요(회원정보없음)"));
		
		LikeConfig config = lcRepository.findByBoard_IdAndMember_id(boardId, memberId);
		if (config.getIsLike() == 0) {
			config.setIsLike(1);
			target.setIsLike(target.getIsLike() + 1);
			bRepository.save(target);
			BoardDto updated= BoardDto.createBoardDto(target);
			return updated;
		} else {
			System.out.println("이미 좋아요를 눌렀습니다.");
			return null;
		}
	}

	// 싫어요
	@Transactional
	public BoardDto upDisLike(Long boardId, Long boardGroupId, Long memberId, Long likeConfigId) {
		Board target = bRepository.findByBoardGroup_IdAndId(boardGroupId, boardId);
		if(target.getId()==null) {
			throw new IllegalArgumentException("존재하지 않는 게시물입니다.");
		}
		Member member = mRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("접근이 잘못됬어요(회원정보없음)"));
		
		LikeConfig config = lcRepository.findByBoard_IdAndMember_id(boardId, memberId);
		if (config.getDisLike() == 0) {
			config.setDisLike(1);
			target.setDisLike(target.getDisLike() + 1);
			bRepository.save(target);
			BoardDto updated= BoardDto.createBoardDto(target);
			return updated;
		} else {
			System.out.println("이미 좋아요를 눌렀습니다.");
			return null;
		}

	}

	// 게시물수정
	@Transactional
	public BoardDto update(BoardDto dto) {
		// 타겟 게시물 조회 및 예외처리
		Board target = bRepository.findById(dto.getId()).orElseThrow(() -> new IllegalArgumentException("대상이없습니다."));
		
		// 댓글 수정
		target.patch(dto);

		// DB로 갱신
		Board updated = bRepository.save(target);

		// 게시물 엔티티를 DTO로 변환 및 반환
		return BoardDto.createBoardDto(updated);
	}

	// 게시물 삭제
	@Transactional
	public BoardDto delete(BoardDto dto) {
		// 게시물 조회 및 예외처리
		lcRepository.deleteByBoard_Id(dto.getId());
		
		Board target = bRepository.findById(dto.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
		
		// 게시물 DB에서 삭제
		bRepository.delete(target);

		// 삭제된 댓글을 dto로 반환
		return BoardDto.createBoardDto(target);
	}
}
