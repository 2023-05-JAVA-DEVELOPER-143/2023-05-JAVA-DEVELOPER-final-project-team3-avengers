package com.danaga.dto.product;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class uploadProductDto {
	private ProductSaveDto product;
	private List<OptionSaveDto> options;
	private OptionSetCreateDto optionSet;
}
