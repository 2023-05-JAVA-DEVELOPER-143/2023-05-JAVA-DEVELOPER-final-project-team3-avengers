package com.danaga.dto;

import java.util.List;

import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateDto {
	private Long id;
	private int qty;
	private OptionSet optionSet;
}
