package com.danaga.dto;

import com.danaga.entity.OptionSet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CartCreateDto {
	private int qty;
	private OptionSet optionset;
}
