package com.danaga.dto;

import java.util.List;

import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
	private Long id; //optionsetId
	private Integer qty;
}
