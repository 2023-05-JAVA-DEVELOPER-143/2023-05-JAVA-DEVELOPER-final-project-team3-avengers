package com.danaga.dto.product;

import java.util.List;
import java.util.Optional;

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
	
	private Optional<String> orderType;
	private Optional<String> brand;
	private Optional<List<Options>> optionset;
	private int minPrice=0;
	private int maxPrice=Integer.MAX_VALUE;
	private Optional<String> nameKeyword;
	private Optional<String> category;
	
}
