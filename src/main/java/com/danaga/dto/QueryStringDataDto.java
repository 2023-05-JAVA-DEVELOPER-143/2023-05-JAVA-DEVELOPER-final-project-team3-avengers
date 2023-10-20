package com.danaga.dto;

import java.util.List;

import com.danaga.entity.Options;

import lombok.Data;
@Data
public class QueryStringDataDto {
	private String orderType;
	private String brand;
	private List<Options> optionset;
	private int minPrice;
	private int maxPrice;
	private String nameKeyword;
	private String category;
}
