package com.danaga.dto.product;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionNameValueDto {
	private List<String> optionNames;
	private List<String> optionValues;
	
	
}
