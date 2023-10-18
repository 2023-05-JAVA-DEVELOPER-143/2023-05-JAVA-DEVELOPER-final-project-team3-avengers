package com.danaga.dto;

import com.danaga.entity.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RefundResponseDto {
	private Long reNo;
	private String reDesc;
	private String reAcno;
	public static RefundResponseDto toDto(Refund entity) {
		return RefundResponseDto.builder()
				.reNo(entity.getReNo())
				.reDesc(entity.getReDesc())
				.reAcno(entity.getReAcno())
				.build();
	}
}
