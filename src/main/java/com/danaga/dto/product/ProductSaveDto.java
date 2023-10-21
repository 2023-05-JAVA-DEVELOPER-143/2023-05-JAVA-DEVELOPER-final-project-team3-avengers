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
	
	private Long id;
	private String name;
	private String brand;
	private Integer price;
	private String descImage;
	private String prevDesc;
	private String img;
	private Double rating;
	
	public Product toEntity() {
		return Product.builder()
				.id(id)
				.name(name)
				.brand(brand)
				.prevDesc(prevDesc)
				.descImage(descImage)
				.img(img)
				.rating(rating)
				.price(price)
				.build();
	}
	
}
