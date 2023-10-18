package com.danaga.dto;



import com.danaga.entity.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DeliveryResponseDto {
	private Long de_no;
	private String de_name;	
	private String de_phonenumber;    
	private String de_address;	 
	public static DeliveryResponseDto toDto(Delivery entity) {
		return DeliveryResponseDto.builder()
				.de_no(entity.getDeNo())
				.de_name(entity.getDeName())
				.de_phonenumber(entity.getDePhonenumber())
				.de_address(entity.getDeAddress())
				.build();
	}
}
