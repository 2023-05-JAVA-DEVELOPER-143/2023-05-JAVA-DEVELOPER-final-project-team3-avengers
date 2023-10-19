package com.danaga.dto;

import com.danaga.entity.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RefundResponseDto {
	private Long id;
	private String description;
	private String acNo;
	public static RefundResponseDto toDto(Refund entity) {
		return RefundResponseDto.builder()
				.id(entity.getId())
				.description(entity.getDescription())
				.acNo(entity.getAcNo())
				.build();
	}
}
// 환불처리 결과 보여주려고
//주문목록창에 현재 주문상태 환불완료일 경우에  아래에 환불완료 아래에 환불상세페이지 이동하도록