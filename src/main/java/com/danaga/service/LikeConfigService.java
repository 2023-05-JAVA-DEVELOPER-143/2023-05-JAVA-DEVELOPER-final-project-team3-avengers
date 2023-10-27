package com.danaga.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
	

	
	/*
	 * public LikeConfigDto deleteConfigs(BoardDto dto) { //대상찾기 LikeConfig target =
	 * lcRepository.findById(dto.getId()).orElseThrow(()->new
	 * IllegalArgumentException("존재하지않는 상태입니다."));
	 * 
	 * //대상삭제 lcRepository.delete(target); //dto로 변환 및 반환 return
	 * LikeConfigDto.createConfigDto(target); }
	 */
}
