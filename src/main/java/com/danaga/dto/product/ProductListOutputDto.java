package com.danaga.dto.product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.danaga.dto.product.OptionDto.OptionBasicDto;
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
	private String updateTime;
	private String pImage;
	private Integer totalPrice;
	private Long osId;
	@Builder.Default
	private List<OptionDto.OptionBasicDto> optionSet = new ArrayList<>();
	private Boolean isInterested;
	private String optionSetDesc;
	
	
	public ProductListOutputDto(OptionSet entity) {
		this.brand=entity.getProduct().getBrand();
		this.name=entity.getProduct().getName();
		this.rating=entity.getProduct().getRating();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.updateTime = entity.getUpdateTime().format(formatter);
		this.pImage=entity.getProduct().getImg();
		this.totalPrice=entity.getTotalPrice();
		this.osId=entity.getId();
		this.optionSet = entity.getOptions().stream().map(t -> new OptionDto.OptionBasicDto(t)).collect(Collectors.toList());
		this.isInterested=false;
		StringBuilder sb = new StringBuilder();
		int count = 1;
		for (OptionBasicDto option : this.optionSet) {
		    sb.append(option.getName()+":"+option.getValue());
		    if (count % 3 == 0) {
		        sb.append("\n"); // 세번째 값은 줄바꿈
		    } else {
		        sb.append("/"); // 나머지 값은 '/'
		    }
		    count++;
		}
		String result = sb.toString();
		if (result.endsWith("/")) {
		    result = result.substring(0, result.length() - 1); // 마지막 '/' 제거
		}
		this.optionSetDesc=result;
	}
	public ProductListOutputDto(OptionSet entity, String username) {
		this.brand=entity.getProduct().getBrand();
		this.name=entity.getProduct().getName();
		this.rating=entity.getProduct().getRating();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.updateTime = entity.getUpdateTime().format(formatter);
		this.pImage=entity.getProduct().getImg();
		this.totalPrice=entity.getTotalPrice();
		this.osId=entity.getId();
		this.optionSet = entity.getOptions().stream().map(t -> new OptionDto.OptionBasicDto(t)).collect(Collectors.toList());
		this.isInterested=entity.getInterests().stream().anyMatch(t -> t.getMember().getUserName().equals(username));
		StringBuilder sb = new StringBuilder();
		int count = 1;
		for (OptionBasicDto option : this.optionSet) {
		    sb.append(option.getName()+":"+option.getValue());
		    if (count % 3 == 0) {
		        sb.append("\n"); // 세번째 값은 줄바꿈
		    } else {
		        sb.append("/"); // 나머지 값은 '/'
		    }
		    count++;
		}
		String result = sb.toString();
		if (result.endsWith("/")) {
		    result = result.substring(0, result.length() - 1); // 마지막 '/' 제거
		}
		this.optionSetDesc=result;
	}
}
