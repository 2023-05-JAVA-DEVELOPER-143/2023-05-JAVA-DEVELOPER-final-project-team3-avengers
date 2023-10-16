package com.danaga.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DeliveryDto {
	private Long de_no;
	private String de_name;
	private Integer de_phonenumber;
	private String de_address;
	
}
