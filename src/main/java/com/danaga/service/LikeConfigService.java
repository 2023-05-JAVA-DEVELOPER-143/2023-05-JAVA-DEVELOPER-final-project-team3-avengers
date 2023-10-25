package com.danaga.service;

import java.util.List;
import java.util.stream.Collectors;

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
	
	public List<LikeConfigDto>selectAll() {
		return lcRepository.findAll().stream().map(config -> LikeConfigDto.createConfigDto(config)).collect(Collectors.toList());
	}
	
	public LikeConfig selectOne(Long boardId, Long memberId) {
		return lcRepository.findByBoard_IdAndMember_id(boardId, boardId);
	}
	public List<LikeConfigDto> create(Long boardId,Long memberId) {
		//상태 조회 및 예외 처리
		List<LikeConfigDto> configs = selectAll();
		Member memberT= mRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("존재하지않는 회원입니다."));
		Board boardT=bRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("존재하지않는 게시글입니다."));
		LikeConfig isHave=selectOne(memberT.getId(), boardT.getId());
		if(isHave!=null) {
			LikeConfigDto configDto=LikeConfigDto.createConfigDto(isHave);
			configs.add(configDto);
		}
		LikeConfig creat=LikeConfig.builder()
				.id(null).isLike(0).disLike(0).board(isHave.getBoard()).member(isHave.getMember()).build();
		
		
		LikeConfig created=lcRepository.save(creat);
		LikeConfigDto config = LikeConfigDto.createConfigDto(created);
		configs.add(config);
		
		return configs;
	}
	public LikeConfigDto updateIsLikeConfig(LikeConfigDto dto) {
		LikeConfig target = lcRepository.findById(dto.getId()).orElseThrow(()->new IllegalArgumentException("존재하지않는 상태입니다."));
		LikeConfigDto dtoTemp =null;
		if(!target.getMember().getId().equals(dto.getMemberId())) {
			throw new IllegalArgumentException("정상적인 접근바랍니다.");
		}
		if(target.getIsLike()==0) {
			target.setIsLike(1);
			target.patch(dto);
			lcRepository.save(target);
			dtoTemp = LikeConfigDto.createConfigDto(target);
		}
		return dtoTemp;
	}
	public LikeConfigDto updateDisLikeConfig(LikeConfigDto dto) {
		LikeConfig target = lcRepository.findById(dto.getId()).orElseThrow(()->new IllegalArgumentException("존재하지않는 상태입니다."));
		LikeConfigDto dtoTemp =null;
		if(!target.getMember().getId().equals(dto.getMemberId())) {
			throw new IllegalArgumentException("정상적인 접근바랍니다.");
		}
		if(target.getDisLike()==0) {
			target.setDisLike(1);
			target.patch(dto);
			lcRepository.save(target);
			dtoTemp = LikeConfigDto.createConfigDto(target);
		}
		return dtoTemp;
	}
	
	/*
	 * public LikeConfigDto deleteConfigs(BoardDto dto) { //대상찾기 LikeConfig target =
	 * lcRepository.findById(dto.getId()).orElseThrow(()->new
	 * IllegalArgumentException("존재하지않는 상태입니다."));
	 * 
	 * //대상삭제 lcRepository.delete(target); //dto로 변환 및 반환 return
	 * LikeConfigDto.createConfigDto(target); }
	 */
}
