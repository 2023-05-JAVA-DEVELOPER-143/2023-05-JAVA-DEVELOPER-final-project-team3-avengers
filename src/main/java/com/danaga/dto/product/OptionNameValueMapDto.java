package com.danaga.dto.product;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionNameValueMapDto {
	private String optionName;
	@Builder.Default
	private List<String> optionValue=new ArrayList<String>();
}
