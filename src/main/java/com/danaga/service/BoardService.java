package com.danaga.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.dto.BoardDto;
import com.danaga.dto.LikeConfigDto;
import com.danaga.entity.Board;
import com.danaga.entity.BoardGroup;
import com.danaga.entity.LikeConfig;
import com.danaga.entity.Member;
import com.danaga.repository.BoardGroupRepository;
import com.danaga.repository.BoardRepository;
import com.danaga.repository.LikeConfigRepository;
import com.danaga.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class BoardService {

	@Autowired
	private BoardRepository bRepository;
	@Autowired
	private MemberRepository mRepository;
	@Autowired
	private BoardGroupRepository bgRepository;
	@Autowired
	private LikeConfigRepository lcRepository;

	//인기 게시물 출력
	public List<BoardDto> popularPost(){
		return bRepository.findTop10ByOrderByReadCountDesc().stream().map(t -> BoardDto.responseDto(t)).collect(Collectors.toList());
	}
	
	// 게시물별 출력
	public List<BoardDto> boards(Long boardGroupId) {
		return bRepository.findByBoardGroupIdOrderByCreateTimeDesc(boardGroupId).stream()
				.map(board -> BoardDto.responseDto(board)).collect(Collectors.toList());
	}

	public BoardDto boardDetail(Long id) {
		Board target = bRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("대상이없습니다."));
		target.readCountUp(target);
		bRepository.save(target);
		BoardDto temp = BoardDto.responseDto(target);
		return temp;
	}

	// 생성
	@Transactional
	public BoardDto createBoard(BoardDto dto) {
	    // 게시글 조회 및 예외처리
	    Member memberT = mRepository.findById(dto.getMemberId())
	            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
	    BoardGroup boardGroupT = bgRepository.findById(dto.getBoardGroupId())
	            .orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));
	    BoardDto create=BoardDto.createDto(dto);
	    log.info("create : {}",create);
		/*
		 * if (create.getId() != null) { throw new
		 * IllegalArgumentException("이미 존재하는 게시물ID입니다."); }
		 */
	    
	    // 게시글 엔티티 생성
	    Board board = Board.toEntity(create, memberT, boardGroupT);
	    log.info("board : {} ",board);
	    // 게시글 엔티티를 DB에 저장
	    Board created = bRepository.save(board);

	    // LikeConfig 엔티티 생성 및 저장
	    LikeConfig likeConfig = LikeConfig.createConfig(created, memberT);
	    lcRepository.save(likeConfig);

	    // 게시글 엔티티를 DTO로 변환 및 반환
	    return BoardDto.responseDto(board);
	}


	// 좋아요
	@Transactional
	public BoardDto upIsLike(Long boardId, Long memberId) {
		// board와 member config 객체 조회 및 예외처리
		Board board = bRepository.findById(boardId)
				.orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다.(대상게시글이 없음)"));
		Member member = mRepository.findById(memberId)
				.orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다.(회원정보없음)"));
		Optional<LikeConfig> config = lcRepository.findByBoardAndMember(board, member);
		LikeConfig obj = null;
		// 좋아요 상태가 없는(좋아요를 누른적없는) 회원의 config like값이 0인 객체를 생성
		if (config.isEmpty()) {
			obj = LikeConfig.createConfig(board, member);
			if (obj.getIsLike() == 0) {
				obj.setIsLike(1);// 좋아요 상태를 1로 만들어줌
				lcRepository.save(obj);
			} else if (obj.getIsLike() == 1) {
				obj.setIsLike(0);// 이미 누른회원이 또 누르면 0으로 만들어줌
				lcRepository.save(obj);
			}

		} else { // config의 상태가 있던경우
			if (obj.getIsLike() == 0) {
				obj.setIsLike(1);// 좋아요 상태를 1로 만들어줌
				lcRepository.save(obj);
			} else if (obj.getIsLike() == 1) {
				obj.setIsLike(0);// 이미 누른회원이 또 누르면 0으로 만들어줌
				lcRepository.save(obj);
			}
		}
		// board entity상태 변경후 db에 갱신
		board.updateConfig(obj);
		Board response = bRepository.save(board);

		// 저장후 dto객체로 변환 및 반환
		BoardDto responseDto = BoardDto.responseDto(response);
		return responseDto;

	}

	// 싫어요
	@Transactional
	public BoardDto upDisLike(Long boardId, Long boardGroupId, Long memberId, Long likeConfigId) {
		// board와 member config 객체 조회 및 예외처리
		Board board = bRepository.findById(boardId)
				.orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다.(대상게시글이 없음)"));
		Member member = mRepository.findById(memberId)
				.orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다.(회원정보없음)"));
		Optional<LikeConfig> config = lcRepository.findByBoardAndMember(board, member);
		LikeConfig obj = null;
		// 좋아요 상태가 없는(좋아요를 누른적없는) 회원의 config like값이 0인 객체를 생성
		if (config.isEmpty()) {
			obj = LikeConfig.createConfig(board, member);
			if (obj.getDisLike() == 0) {
				obj.setDisLike(1);// 좋아요 상태를 1로 만들어줌
				lcRepository.save(obj);
			} else if (obj.getDisLike() == 1) {
				obj.setDisLike(0);// 이미 누른회원이 또 누르면 0으로 만들어줌
				lcRepository.save(obj);
			}

		} else { // config의 상태가 있던경우
			if (obj.getDisLike() == 0) {
				obj.setDisLike(1);// 좋아요 상태를 1로 만들어줌
				lcRepository.save(obj);
			} else if (obj.getDisLike() == 1) {
				obj.setDisLike(0);// 이미 누른회원이 또 누르면 0으로 만들어줌
				lcRepository.save(obj);
			}
		}
		// board entity상태 변경후 db에 갱신
		board.updateConfig(obj);
		Board response = bRepository.save(board);

		// 저장후 dto객체로 변환 및 반환
		BoardDto responseDto = BoardDto.responseDto(response);
		return responseDto;
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
		return BoardDto.responseDto(updated);
	}

	// 게시물 삭제
	@Transactional
	public BoardDto delete(BoardDto dto) {
		// config상태 삭제
		lcRepository.deleteByBoard_Id(dto.getId());
		// 게시물 조회 및 예외처리
		Board target = bRepository.findById(dto.getId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

		// 게시물 DB에서 삭제
		bRepository.delete(target);

		// 삭제된 댓글을 dto로 반환
		return BoardDto.responseDto(target);
	}
}
