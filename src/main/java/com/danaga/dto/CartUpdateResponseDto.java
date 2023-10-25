package com.danaga.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class CartUpdateResponseDto {
	private Long id;
	private Integer qty;
	private Long optionSetId;

}
