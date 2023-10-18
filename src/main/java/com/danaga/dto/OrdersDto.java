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
@Builder

public class OrdersDto {
	private Long oNo;
	private String oState;

	
	
	
}
