package com.danaga.dto;


import com.danaga.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
	private String brand;

	public ProductDto(final Product entity) {//처리한 후 다시 유저한테 보내는 entity를 dto로 바꿔서 보내줌
	}
	public static Product toEntity(final ProductDto dto) {//dto로 요청을 받아서 entity로 바꿔 서비스 처리하고 
		return Product.builder()
				.build();
		
	}
}
