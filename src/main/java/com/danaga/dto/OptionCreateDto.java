package com.danaga.dto;

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
public class OptionCreateDto {
	private String name;
	private String value;
	private Integer extraPrice;
	private Long optionSetId;
	
	public Options toEntity() {
		return Options.builder()
				.name(name)
				.value(value)
				.extraPrice(extraPrice)
				.optionSet(OptionSet.builder().id(optionSetId).build())
				.build();
	}
}
