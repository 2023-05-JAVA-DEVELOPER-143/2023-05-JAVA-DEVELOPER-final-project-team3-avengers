package com.danaga.dto;

import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
	
	private long id;
	private int cartQty;
	private Member member;
	private OptionSet optionSet;
}
