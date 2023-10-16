package com.danaga.dto;

import java.sql.*;



import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrdersDto {
	private Long o_no;
	private Date o_date;
	private String o_desc;
	private Integer o_price;
	private String o_state;
}
