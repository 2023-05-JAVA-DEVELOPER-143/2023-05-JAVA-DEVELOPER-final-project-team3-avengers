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
public class BoardService {

	@Autowired
	private BoardRepository bRepository;
	@Autowired
	private MemberRepository mRepsitory;
	@Autowired
	private BoardGroupRepository bgRepository;
	@Autowired
	private LikeConfigRepository lcRepository;
	
	public List<LikeConfig> configs() {
		List<LikeConfig> configs = lcRepository.findAll();
		return configs;
	}
	//게시물별 출력
	public List<Board> findWhichBoards(Long boardGroupId){
		return bRepository.findByBoardGroupIdOrderByCreateTime(boardGroupId);
	}
	//특정게시판의 특정게시물 읽기
	public Board boardDetail(Long boardGroupId, Long boardId) {
		Board board= bRepository.findByBoardGroup_IdAndId(boardGroupId, boardId);
		BoardGroup gb= bgRepository.findById(boardGroupId).get();
		if(board ==null) {
			return null;
		}else {
			board.setReadCount(+1);
			return board;
		}
		
		
	}
	 // 생성
    @Transactional
    public Board createBoard(BoardDto dto, Long memberId, Long boardGroupId) {
        Member memberT = mRepsitory.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        BoardGroup boardGroupT = bgRepository.findById(boardGroupId)
                .orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));
        List<LikeConfig> configs = lcRepository.findAll();

        if (dto.getId() != null) {
            throw new IllegalArgumentException("새 게시물을 생성하기 위해 ID는 null이어야 합니다.");
        }

        System.out.println("생성 가능!");
        Board target = BoardDto.toEntity(dto, memberT, boardGroupT, configs);
        return bRepository.save(target);
    }
	//좋아요
	public Board upIsLike(Long boardId,Long boardGroupId,Long memberId) {
		Board target = bRepository.findByBoardGroup_IdAndId(boardGroupId, boardId);
		Board isLike=null;
		if(target==null) {
			System.out.println("해당게시물이 존재하지않습니다.");
		}
		Member member = mRepsitory.findById(memberId).get();
		List<LikeConfig> configs= lcRepository.findAll();
		//조건문 : target과 boardId,memberId가 List에 동일한거 먼저 찾아내고 찾아낸 LikeConfig객체의 isLike를 찾아내어 0이면 1로변환후
		//target의 isLike를 기존에있던 데이터에서 +1을 해주어야함.
		for (LikeConfig likeConfig : configs) {
			if(likeConfig.getBoard().getId() ==target.getId() && likeConfig.getMember().getId()==member.getId()) {
				if(likeConfig.getIsLike()==0) {
					likeConfig.setIsLike(1);
					target.setIsLike(target.getIsLike()+1);
					isLike=bRepository.save(target);
					return isLike;
				}else {
					System.out.println("이미 좋아요를 눌렀습니다.");
					return isLike;
				}
			}
		}
		return isLike;
		
	}
	//싫어요
	public Board disLike(Long boardId,Long boardGroupId,Long memberId) {
		Board target = bRepository.findByBoardGroup_IdAndId(boardGroupId, boardId);
		Board disLike=null;
		if(target==null) {
			System.out.println("해당게시물이 존재하지않습니다.");
		}
		Member member = mRepsitory.findById(memberId).get();
		List<LikeConfig> configs= lcRepository.findAll();

		for (LikeConfig likeConfig : configs) {
			if(likeConfig.getBoard().getId() ==target.getId() && likeConfig.getMember().getId()==member.getId()) {
				if(likeConfig.getDisLike()==0) {
					likeConfig.setDisLike(1);
					target.setDisLike(target.getDisLike()+1);
					disLike=bRepository.save(target);
					return disLike;
				}else {
					System.out.println("이미 싫어요를 눌렀습니다.");
					return disLike;
				}
			}
		}
		return disLike;
		
	}
	//게시물 삭제
	@Transactional
	public void delete(Long boardGroupId,Long boardId) {
		Board target = bRepository.findByBoardGroup_IdAndId(boardGroupId, boardId);
		if(target !=null) {
			bRepository.deleteByBoardGroup_IdAndId(boardGroupId, boardId);
			
		}
		
	}
	
	//게시물수정
	public Board update(BoardDto dto,Member member ,BoardGroup group,List<LikeConfig> configs) {
		Member memberT= mRepsitory.findById(member.getId()).get();
		BoardGroup bgT= bgRepository.findById(group.getId()).get();
		
		Board board = dto.toEntity(dto, memberT, bgT,configs);
		Board target = bRepository.findById(dto.getId()).get();
		
		if(target==null || dto.getId().equals(board.getId())) {
			return null;
		}
		target.patch(board);
		Board updated = bRepository.save(target);
		return updated;
	}
}
