package com.danaga.dto;

import com.danaga.entity.OptionSet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateDto {
	private Long id;
	private int cartQty;
	private OptionSet optionSet;
}
