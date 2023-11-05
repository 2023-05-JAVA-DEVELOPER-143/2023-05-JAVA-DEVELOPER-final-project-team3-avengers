package com.danaga.dto.product;

import com.danaga.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaveDto {
	
	private String name;
	private String brand;
	private Integer price;
	private String descImage;
	private String prevImage;
	private String img;
	
	public Product toEntity() {
		return Product.builder()
				.name(name)
				.brand(brand)
				.descImage(descImage)
				.prevImage(prevImage)
				.img(img)
				.price(price)
				.build();
	}
	
}
