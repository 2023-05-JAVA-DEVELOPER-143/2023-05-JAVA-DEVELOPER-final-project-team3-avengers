package com.danaga.dto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.danaga.entity.Orders;

import jakarta.persistence.criteria.Order;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrdersDto {
	private String userName;
	private Long optionSetId;
	private Integer orderItem_qty;
	private String name;
	private String phoneNumber;
	private String address;
	
	public static OrdersDto orderDto(Orders entity) {
		
		return OrdersDto.builder()
						.userName(entity.getMember().getUserName())
						.optionSetId(entity.getOrderItems().get(0).getOptionSet().getId())
						.orderItem_qty(entity.getOrderItems().get(0).getQty())
						.name(entity.getDelivery().getName())
						.phoneNumber(entity.getDelivery().getPhoneNumber())
						.address(entity.getDelivery().getAddress())
						.build();
	}
	
	
	
}
