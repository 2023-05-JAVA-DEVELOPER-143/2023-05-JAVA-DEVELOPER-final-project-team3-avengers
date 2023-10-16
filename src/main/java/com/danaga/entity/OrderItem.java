package com.danaga.entity;

import java.sql.*;
import java.time.*;

import org.hibernate.annotations.*;

import com.danaga.dto.*;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "orderItem")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
	@Id
	@SequenceGenerator(name = "ORDERITEM_ORDERITEM_NO_SEQ", sequenceName = "ORDERITEM_ORDERITEM_NO_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERITEM_ORDERITEM_NO_SEQ")
	private Long oi_no;
	private Integer oi_qty;

	public static OrderItem toEntity(OrderItemDto dto) {
		return OrderItem.builder()
				.oi_no(dto.getOi_no())
				.oi_qty(dto.getOi_qty())
				.build();
	}
}
