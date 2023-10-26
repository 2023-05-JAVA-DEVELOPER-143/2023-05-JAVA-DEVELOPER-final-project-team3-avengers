package com.danaga.dto;

import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CartOptionDto {
	private String name ;
	private String value ;

	
	public CartOptionDto(Options entity) {
		this.name=entity.getName();
		this.value=entity.getValue();
	}
	
}
