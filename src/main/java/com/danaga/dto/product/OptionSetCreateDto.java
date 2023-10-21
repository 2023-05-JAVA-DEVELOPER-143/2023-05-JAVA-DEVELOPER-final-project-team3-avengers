package com.danaga.dto.product;

import com.danaga.entity.OptionSet;
import com.danaga.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionSetCreateDto {
	private Integer stock;
	private Long productId;
	private Integer productPrice;
	
	public OptionSet toEntity() {
		return OptionSet.builder()
				.stock(stock)
				.product(Product.builder().id(productId).build())
				.totalPrice(productPrice)
				.build();
	}

	
}
