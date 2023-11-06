package com.danaga.dto;

import java.util.List;

import com.danaga.dto.product.CategoryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductInsertDto {
	private List<CategoryDto> categoryDto;
	private List<AdminOptionDto> adminOptionDto;
	private AdminProductDto adminProductDto;

}
