package com.danaga.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.danaga.entity.Cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SUserCartResponseDto {
	private Long id;
	private Long memberId;

	private Integer qty;
	private String productName;
	private Integer totalPrice; // 토탈 금액
	private String pImage; // 프로덕트 사진
	private Long osId; // 옵션셋 아이디

	@Builder.Default
	private List<CartOptionDto> options = new ArrayList<>(); // 옵션 네임 밸류

	public static SUserCartResponseDto toDto(Cart entity) {

		return SUserCartResponseDto.builder().id(entity.getId()).memberId(entity.getMember().getId())
				.qty(entity.getQty()).productName(entity.getOptionSet().getProduct().getName())
				.totalPrice(entity.getOptionSet().getTotalPrice()).pImage(entity.getOptionSet().getProduct().getImg())
				.osId(entity.getOptionSet().getId()).options(entity.getOptionSet().getOptions().stream()
						.map(t -> new CartOptionDto(t)).collect(Collectors.toList()))
				.build();
	}

}
