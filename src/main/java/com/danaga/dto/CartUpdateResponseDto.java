package com.danaga.dto;

import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.danaga.entity.Cart;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class CartUpdateResponseDto {
	private Long id;
	private Integer qty;

}
