package com.danaga.dto;

import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CartInsertDto {

	private int cartQty;
	private Member member;
	private OptionSet optionSet;
}
