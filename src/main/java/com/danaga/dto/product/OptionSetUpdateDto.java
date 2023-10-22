package com.danaga.dto.product;

import java.util.List;

import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionSetUpdateDto {
	private Long id;
	private Integer stock;
	private List<Options> options;
	public OptionSetUpdateDto(OptionSet createdOptionSet) {
		this.id = createdOptionSet.getId();
		this.stock = createdOptionSet.getStock();
		this.options = createdOptionSet.getOptions();
	}
}
