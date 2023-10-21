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
	private String delivaryName;
	private String delivaryPhoneNumber;
	private String delivaryAddress;
	
	public static OrdersDto orderDto(Orders entity) {
		
		return OrdersDto.builder()
						.userName(entity.getMember().getUserName())
						.optionSetId(entity.getOrderItems().get(0).getOptionSet().getId())
						.orderItem_qty(entity.getOrderItems().get(0).getQty())
						.delivaryName(entity.getDelivery().getName())
						.delivaryPhoneNumber(entity.getDelivery().getPhoneNumber())
						.delivaryAddress(entity.getDelivery().getAddress())
						.build();
	}
	
	
	
}
