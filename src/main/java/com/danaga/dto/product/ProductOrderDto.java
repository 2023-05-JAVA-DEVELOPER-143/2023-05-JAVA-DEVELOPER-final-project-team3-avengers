package com.danaga.dto.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.danaga.entity.Category;
import com.danaga.entity.CategorySet;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;
import com.danaga.entity.Product;
import com.fasterxml.jackson.core.sym.Name;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductOrderDto {
	private String name;
	private String pImage;
	//@Builder.Default
	//private List<Review> reviews = new ArrayList<>();
//	@Builder.Default
//	private List<Category> categorySet=new ArrayList<>();
	private Integer totalPrice;
	private Long osId;
	@Builder.Default
	private List<OptionDto> optionSet = new ArrayList<>();
	
	public ProductOrderDto(OptionSet entity) {
		this.name=entity.getProduct().getName();
		this.pImage=entity.getProduct().getImg();
		this.totalPrice=entity.getTotalPrice();
		this.osId=entity.getId();
		this.optionSet = entity.getOptions().stream().map(t -> new OptionDto(t)).collect(Collectors.toList());
	}
}
