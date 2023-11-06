package com.danaga.dto.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.danaga.entity.OptionSet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtherOptionSetDto {
	private Long osId;
	@Builder.Default
	private List<OptionDto> optionSet = new ArrayList<>();
	@Builder.Default
	private Boolean isInStock = true;
	public OtherOptionSetDto(OptionSet entity){
		this.optionSet = entity.getOptions().stream().map(t -> new OptionDto(t)).collect(Collectors.toList());
		this.isInStock = true;
		this.osId = entity.getId();
		if(entity.getStock()==0) {
			this.isInStock=false;
		}
	}
}
