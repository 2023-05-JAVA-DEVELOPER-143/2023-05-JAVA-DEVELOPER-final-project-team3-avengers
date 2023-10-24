package com.danaga.dto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.danaga.config.OrderStateMsg;
import com.danaga.entity.OrderItem;
import com.danaga.entity.Orders;

import jakarta.persistence.criteria.Order;
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

	
	public static OrdersDto orderDto(Orders entity) {

		return OrdersDto.builder()
						.id(entity.getId())
						.description(entity.getDescription())
						.price(entity.getPrice())
						.stateMsg(entity.getStatement())
						.createDate(entity.getCreateDate())
						.build();
	}
	
	
	
}
