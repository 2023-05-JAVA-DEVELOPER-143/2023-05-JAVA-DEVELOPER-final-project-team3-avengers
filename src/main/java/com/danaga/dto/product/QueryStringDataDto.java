package com.danaga.dto.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.danaga.entity.OptionSet;
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
	@Builder.Default
	private List<OptionDto> optionset=new ArrayList();
	private Integer minPrice;
	private Integer maxPrice;
	private String nameKeyword;
	private String category;
	
}
