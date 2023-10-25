package com.danaga.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
	private Long id; //optionsetId
	private Integer qty;
	private String productname;
	private Integer totalPrice;
	private String productImage;
	private Long osId;
	
	
	
}
