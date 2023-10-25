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
	private Integer isLike;
	private Integer disLike;
	
	private Long BoardId;
	private Long MemberId;
	
	public static LikeConfigDto createConfigDto(LikeConfig config) {
		
		return LikeConfigDto.builder()
				.id(config.getId()).isLike(config.getIsLike()).disLike(config.getDisLike()).BoardId(config.getId()).MemberId(config.getMember().getId()).build();
	}
}
