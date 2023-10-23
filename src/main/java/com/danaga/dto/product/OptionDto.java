package com.danaga.dto.product;

import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {
private Long id;
private String name;
private String value;
private Integer extraPrice;
private Long optionSetId;

public Options toEntity() {
	return Options.builder()
			.extraPrice(extraPrice)
			.id(id)
			.name(name)
			.optionSet(OptionSet.builder().id(optionSetId).build())
			.value(value)
			.build();
}
public OptionDto(Options entity) {
	this.id=entity.getId();
	this.name=entity.getName();
	this.value=entity.getValue();
	this.extraPrice=entity.getExtraPrice();
	this.optionSetId=entity.getOptionSet().getId();
}
}
