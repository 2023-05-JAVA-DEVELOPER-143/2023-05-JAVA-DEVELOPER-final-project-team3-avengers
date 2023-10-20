package com.danaga.dto;

import java.util.List;

import com.danaga.entity.Options;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryStringDataDto {
	private String orderType;
	private String brand;
	private List<Options> optionset;
	private int minPrice=0;
	private int maxPrice=Integer.MAX_VALUE;
	private String nameKeyword;
	private String category;
}
