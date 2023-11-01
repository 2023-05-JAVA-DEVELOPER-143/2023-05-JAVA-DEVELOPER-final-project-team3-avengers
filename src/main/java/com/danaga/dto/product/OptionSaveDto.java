package com.danaga.dto.product;

import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionSaveDto {
	private Long id;
	private String name;
	private String value;
	private Integer extraPrice;
	private Long optionSetId;
	
	public Options toEntity() {
		return Options.builder()
				.id(id)
				.name(name)
				.value(value)
				.extraPrice(extraPrice)
				.optionSet(OptionSet.builder().id(optionSetId).build())
				.build();
	}
	public OptionSaveDto(Options entity){
		this.id=entity.getId();
		this.name=entity.getName();
		this.value=entity.getValue();
		this.extraPrice=entity.getExtraPrice();
		this.optionSetId=entity.getOptionSet().getId();
				
	}
}
