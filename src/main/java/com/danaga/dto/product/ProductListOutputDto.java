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
public class ProductListOutputDto {//리스트,히트상품,관심,최근상품리스트
	private String brand;
	private String name;
	private Double rating;
	private LocalDateTime updateTime;
	private String pImage;
	private Integer totalPrice;
	private Long osId;
	@Builder.Default
	private List<OptionDto.OptionBasicDto> optionSet = new ArrayList<>();
	private Boolean isInterested;
	
	
	public ProductListOutputDto(OptionSet entity) {
		this.brand=entity.getProduct().getBrand();
		this.name=entity.getProduct().getName();
		this.rating=entity.getProduct().getRating();
		this.updateTime=entity.getUpdateTime();
		this.pImage=entity.getProduct().getImg();
		this.totalPrice=entity.getTotalPrice();
		this.osId=entity.getId();
		this.optionSet = entity.getOptions().stream().map(t -> new OptionDto.OptionBasicDto(t)).collect(Collectors.toList());
		this.isInterested=false;
	}
	public ProductListOutputDto(OptionSet entity, String username) {
		this.brand=entity.getProduct().getBrand();
		this.name=entity.getProduct().getName();
		this.rating=entity.getProduct().getRating();
		this.updateTime=entity.getUpdateTime();
		this.pImage=entity.getProduct().getImg();
		this.totalPrice=entity.getTotalPrice();
		this.osId=entity.getId();
		this.optionSet = entity.getOptions().stream().map(t -> new OptionDto.OptionBasicDto(t)).collect(Collectors.toList());
		this.isInterested=entity.getInterests().stream().anyMatch(t -> t.getMember().getUserName().equals(username));
	}
}
