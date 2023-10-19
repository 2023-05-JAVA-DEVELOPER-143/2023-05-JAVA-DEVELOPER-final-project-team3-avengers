package com.danaga.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DeliveryDto {
	private Long id;
	private String name;
	private String phoneNumber;
	private String address;
}
// 클라이언트가 배달요청 입력한것 받는 데이터