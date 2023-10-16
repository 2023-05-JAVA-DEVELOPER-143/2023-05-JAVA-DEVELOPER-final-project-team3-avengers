package com.danaga.dto;



import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderItemDto {
	private Long oi_no;
	private Integer oi_qty;
	
}
