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
				.de_no(entity.getDe_no())
				.de_name(entity.getDe_name())
				.de_phonenumber(entity.getDe_phonenumber())
				.de_address(entity.getDe_address())
				.build();
	}
}
