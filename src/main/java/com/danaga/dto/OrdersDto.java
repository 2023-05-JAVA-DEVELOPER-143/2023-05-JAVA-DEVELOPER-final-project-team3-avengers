package com.danaga.dto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.danaga.entity.Orders;

import jakarta.persistence.criteria.Order;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrdersDto {
	private Long oNo;
	private LocalDateTime oDate;
	private String oDesc;
	private Integer oPrice;
	private String oState;
	private String oFindNo;
	private String memberId;
	private List<OrderItemDto> orderItemDtos = new ArrayList<>(); 
	
	
	
	
}
