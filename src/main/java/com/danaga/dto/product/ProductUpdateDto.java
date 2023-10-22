package com.danaga.dto.product;

import java.util.Optional;

import com.danaga.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDto {
	
	private Long id;
	private Optional<String> name;
	private Optional<String> brand;
	private Optional<Integer> price;
	private Optional<String> descImage;
	private Optional<String> prevDesc;
	private Optional<String> img;
	private Optional<Double> rating;
	
	public Product toEntity() {
		return Product.builder()
				.id(id)
				.name(name.get())
				.brand(brand.get())
				.prevDesc(prevDesc.get())
				.descImage(descImage.get())
				.img(img.get())
				.rating(rating.get())
				.price(price.get())
				.build();
	}
	
}
