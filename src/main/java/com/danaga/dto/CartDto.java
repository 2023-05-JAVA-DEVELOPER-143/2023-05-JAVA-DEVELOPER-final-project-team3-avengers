package com.danaga.dto;

import com.danaga.entity.Cart;
import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class CartDto {
	private Long id;
	private Integer cartQty;
	private Member member;
	private OptionSet optionSet;
	
	
	public static CartDto toDto(Cart cart) {
		return CartDto.builder()
				.id(cart.getId())
				.cartQty(cart.getCartQty())
				.member(cart.getMember())
				.optionSet(cart.getOptionSet())
				.build();
	}
	
	
}
