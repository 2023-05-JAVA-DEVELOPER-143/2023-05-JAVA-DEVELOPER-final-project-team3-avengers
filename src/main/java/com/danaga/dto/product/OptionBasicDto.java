package com.danaga.dto.product;

import com.danaga.entity.Options;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionBasicDto {
	
	private String name;
	private String value;
	
	public OptionBasicDto(Options t) {
		this.name=t.getName();
		this.value=t.getValue();
	}
}
