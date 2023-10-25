package com.danaga.dto;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import com.danaga.config.OrderStateMsg;
import com.danaga.entity.Orders;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrdersDto {

	
	@Autowired
	OrderItemDto orderItemDto;
	
	private Long id;
	private String description;
	private Integer price;
	private OrderStateMsg stateMsg;
	private LocalDateTime createDate;
	private String userName;

	
	public static OrdersDto orderDto(Orders entity) {
		return OrdersDto.builder()
						.id(entity.getId())
						.description(entity.getDescription())
						.price(entity.getPrice())
						.stateMsg(entity.getStatement())
						.userName(entity.getMember().getUserName())
						.build();
	}
	
	
	
}
