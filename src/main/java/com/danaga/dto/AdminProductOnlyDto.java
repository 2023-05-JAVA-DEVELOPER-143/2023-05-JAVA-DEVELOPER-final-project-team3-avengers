package com.danaga.dto;

import java.util.List;

import com.danaga.dto.product.CategoryDto;
import com.danaga.dto.product.ProductSaveDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductOnlyDto {
	private List<CategoryDto> categoryDto;
	private ProductSaveDto productSaveDto;

}
