package com.danaga.dto;

import com.danaga.entity.Cart;
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
public class CartCreateDto {

	private int cartQty;
	private Member member;
	private OptionSet optionset;
		
	public static Cart toEntity(CartCreateDto cartCreateDto) {
		return Cart.builder()
				.cartQty(cartCreateDto.getCartQty())
				.member(cartCreateDto.getMember())
				.optionSet(cartCreateDto.getOptionset())
				.build();
	}
	
}
