package com.danaga.dto.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryStringDataDto {
	
	private String orderType;
	@Builder.Default
	private List<OptionNameValueMapDto> optionset=new ArrayList<OptionNameValueMapDto>();
	private Integer minPrice;
	private Integer maxPrice;
	private String nameKeyword;
	private CategoryDto category;
	@Builder.Default
	private Integer firstResult=0;
	
}
