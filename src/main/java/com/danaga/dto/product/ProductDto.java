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
	private String name;
	private Double rating;
	private LocalDateTime updateTime;
	private String descImage;
	private String prevDesc;
	private String pImage;
	//@Builder.Default
	//private List<Review> reviews = new ArrayList<>();
//	@Builder.Default
//	private List<Category> categorySet=new ArrayList<>();
	private Integer stock;
	private Integer totalPrice;
	private Long osId;
	@Builder.Default
	private List<OptionDto> optionSet = new ArrayList<>();
	
	public ProductDto(OptionSet entity) {
		this.brand=entity.getProduct().getBrand();
		this.name=entity.getProduct().getName();
		this.rating=entity.getProduct().getRating();
		this.updateTime=entity.getUpdateTime();
		this.descImage=entity.getProduct().getDescImage();
		this.prevDesc=entity.getProduct().getPrevDesc();
		this.pImage=entity.getProduct().getImg();
		this.stock=entity.getStock();
		this.totalPrice=entity.getTotalPrice();
		this.osId=entity.getId();
		this.optionSet = entity.getOptions().stream().map(t -> new OptionDto(t)).collect(Collectors.toList());
	}
}
