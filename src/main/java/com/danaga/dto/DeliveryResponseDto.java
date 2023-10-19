package com.danaga.dto;



import com.danaga.entity.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DeliveryResponseDto {
	private Long deNo;
	private String deName;	
	private String dePhonenumber;    
	private String deAddress;	 
//	public static DeliveryResponseDto toDto(Delivery entity) {
//		return DeliveryResponseDto.builder()
//				.deNo(entity.getDeNo())
//				.deName(entity.getDeName())
//				.dePhonenumber(entity.getDePhonenumber())
//				.deAddress(entity.getDeAddress())
//				.build();
//	}
}
//클라이언트에게 배달접수받은거 보여주는 데이터