package com.danaga.dto;



import com.danaga.entity.OrderItem;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderItemDto {
	private Long oi_no;
	private Integer oi_qty;
	private Long o_no;
	
	
	public static OrderItemDto toDto(OrderItem entity) {
		return OrderItemDto.builder()
				.o_no(entity.getOrders().getONo())
				.oi_no(entity.getOiNo())
				.oi_qty(entity.getOiQty())
				.build();
	}
}
