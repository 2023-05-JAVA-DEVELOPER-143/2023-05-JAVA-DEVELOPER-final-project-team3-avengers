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
	private Long id;
	private Integer qty;
	private Long orderId;
	private Long optionSetId;
	
	public static OrderItemDto toDto(OrderItem entity) {
		return OrderItemDto.builder()
							.id(entity.getId())
							.qty(entity.getQty())
							.orderId(entity.getOrders().getId())
							.optionSetId(entity.getOptionSet().getId())
							.build();
	}
}
