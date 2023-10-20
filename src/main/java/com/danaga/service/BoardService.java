package com.danaga.service;

import java.util.List;

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
	private MemberRepository mRepsitory;
	@Autowired
	private BoardGroupRepository bgRepository;
	@Autowired
	private LikeConfigRepository lcRepository;
	
	//게시물별 출력
	public List<Board> findWhichBoards(Long boardGroupId){
		return bRepository.findByBoardGroupIdOrderByCreateTime(boardGroupId);
	}
	//특정게시판의 특정게시물 읽기
	public Board boardDetail(Long boardGroupId, Long boardId) {
		return bRepository.findByBoardGroup_IdAndId(boardGroupId, boardId);
	}
	//생성
	public Board createBoard(BoardDto dto) {
		Member member = mRepsitory.findById(dto.getMemberId()).get();
		BoardGroup boardGroup = bgRepository.findById(dto.getBoardGroupId()).get(); 
		if(dto.getId()==null) {
			System.out.println("생성가능!");
			Board target = BoardDto.toEntity(dto,member,boardGroup);
			return bRepository.save(target);
		}else {
			System.out.println("생성불가");
			return null;
		}
	}
	//좋아요
	public Board upIsLike(Long boardId,Long boardGroupId,Long memberId, Long likeConfigId) {
		Board target = bRepository.findByBoardGroup_IdAndId(boardGroupId, boardId);
		if(target==null) {
			System.out.println("해당게시물이 존재하지않습니다.");
		}
		Member member = mRepsitory.findById(memberId).get();
		LikeConfig config= lcRepository.findById(likeConfigId).get();
		if(config.getIsLike()==0) {
			config.setIsLike(1);
			target.setIsLike(target.getIsLike()+1);
			return bRepository.save(target);
		}else {
			System.out.println("이미 좋아요를 눌렀습니다.");
			return null;
		}
		
	}
	//싫어요
	public Board upDisLike(Long boardId,Long boardGroupId,Long memberId, Long likeConfigId) {
		Board target = bRepository.findByBoardGroup_IdAndId(boardGroupId, boardId);
		Member member = mRepsitory.findById(memberId).get();
		LikeConfig config= lcRepository.findById(likeConfigId).get();
		if(target==null) {
			System.out.println("해당게시물이 존재하지않습니다.");
		}
		if(member.getId() ==null) {
			System.out.println("존재하지않는 회원입니다.");
		}
		if(config.getId() ==null) {
			System.out.println("존재하지 않는 상태입니다.");
		}
		
		if(config.getIsLike()==0) {
			config.setIsLike(1);
			target.setDisLike(target.getDisLike()+1);
			return bRepository.save(target);
		}else {
			System.out.println("이미 싫어요를 눌렀습니다.");
			return null;
		}
		
	}
	//게시물 삭제
	public void delete(Long boardGroupId,Long boardId) {
		Board target = bRepository.findByBoardGroup_IdAndId(boardGroupId, boardId);
		if(target !=null) {
			System.out.println(target+" 이 ");
			bRepository.deleteByBoardGroup_IdAndId(boardGroupId, boardId);
			System.out.println("삭제되었어요");
		}
		
	}
	
	//게시물수정
	public Board update(BoardDto dto,Member member ,BoardGroup group) {
		
		Board board = dto.toEntity(dto, member, group);
		Board target = bRepository.findById(dto.getId()).get();
		
		if(target==null || dto.getId().equals(board.getId())) {
			return null;
		}
		target.patch(board);
		Board updated = bRepository.save(target);
		return updated;
	}
}
