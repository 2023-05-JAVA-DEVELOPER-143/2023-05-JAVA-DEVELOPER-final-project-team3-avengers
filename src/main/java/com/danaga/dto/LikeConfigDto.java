package com.danaga.dto;

import com.danaga.entity.LikeConfig;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeConfigDto {

	private Long id;
	private Long BoardId;
	private Long MemberId;
	private Integer isLike;
	private Integer disLike;
	
	public static LikeConfigDto createConfigDto(Long boardId, Long memberId) {
		
		return LikeConfigDto.builder()
				.BoardId(boardId).MemberId(memberId).isLike(0).disLike(0).build();
	}
	
	public static LikeConfigDto responsDto(LikeConfig config) {
		return LikeConfigDto.builder()
				.id(config.getId()).isLike(config.getIsLike()).disLike(config.getDisLike()).BoardId(config.getBoard().getId()).MemberId(config.getMember().getId())
				.build();
		
	}
}
