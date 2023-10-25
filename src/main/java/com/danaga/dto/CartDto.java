package com.danaga.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.danaga.dto.product.ProductDto;
import com.danaga.entity.Cart;
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