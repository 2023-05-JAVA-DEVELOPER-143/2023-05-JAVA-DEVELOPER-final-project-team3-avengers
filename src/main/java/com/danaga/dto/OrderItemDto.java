package com.danaga.dto;



import com.danaga.entity.OptionSet;
import com.danaga.entity.OrderItem;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderItemDto {
	private Long oiNo;
	private Integer oiQty;
	private Long oNo;
	private OptionSet optionSet;
	
	public static OrderItemDto toDto(OrderItem entity) {
		return OrderItemDto.builder()
				.oNo(entity.getOrders().getONo())
				.oiNo(entity.getOiNo())
				.oiQty(entity.getOiQty())
				.optionSet(entity.getOptionSet())
				.build();
	}
}
