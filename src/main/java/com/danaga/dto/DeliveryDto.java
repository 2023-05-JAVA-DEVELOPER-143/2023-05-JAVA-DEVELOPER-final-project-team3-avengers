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
// 클라이언트가 배달요청 입력한것 받는 데이터