package com.danaga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.dto.BoardDto;
import com.danaga.dto.LikeConfigDto;
import com.danaga.entity.Board;
import com.danaga.entity.LikeConfig;
import com.danaga.entity.Member;
import com.danaga.repository.BoardRepository;
import com.danaga.repository.LikeConfigRepository;
import com.danaga.repository.MemberRepository;

@Service
public class LikeConfigService {
	
	@Autowired
	private LikeConfigRepository lcRepository;
	@Autowired
	private MemberRepository mRepository;
	@Autowired
	private BoardRepository bRepository;
	
	public List<LikeConfig>selectAll() {
		return lcRepository.findAll();
	}
	
	public LikeConfig selectOne(Long boardId, Long memberId) {
		return lcRepository.findByBoard_IdAndMember_id(boardId, boardId);
	}
	public List<LikeConfig> create(BoardDto dto) {
		//상태 조회 및 예외 처리
		List<LikeConfig> configs = selectAll();
		Member memberT= mRepository.findById(dto.getMemberId()).orElseThrow(()->new IllegalArgumentException("존재하지않는 회원입니다."));
		Board boardT=bRepository.findById(dto.getId()).orElseThrow(()->new IllegalArgumentException("존재하지않는 게시글입니다."));
		LikeConfigDto lcDto = LikeConfigDto.createConfigDto(dto);
		LikeConfig created= LikeConfig.createConfig(lcDto, boardT, memberT);
		
		configs.add(created);
		
		return configs;
	}
	public LikeConfig updateIsLikeConfig(LikeConfigDto dto) {
		LikeConfig target = lcRepository.findByBoard_IdAndMember_id(dto.getBoardId(), dto.getMemberId());
		if(target ==null) {
			throw new IllegalArgumentException("상태가 존재하지 않습니다.");
			
		}
		if(target.getIsLike()==0) {
			target.setIsLike(1);
			target.patch(dto);
			lcRepository.save(target);
		}
		return target;
	}
	public LikeConfig updateDisLikeConfig(LikeConfigDto dto) {
		LikeConfig target = lcRepository.findByBoard_IdAndMember_id(dto.getBoardId(), dto.getMemberId());
		if(target ==null) {
			throw new IllegalArgumentException("상태가 존재하지 않습니다.");
			
		}
		if(target.getDisLike()==0) {
			target.setDisLike(1);
			target.patch(dto);
			lcRepository.save(target);
		}
		return target;
	}
}
