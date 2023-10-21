package com.danaga.dto;

import com.danaga.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateDto {
	
	private String name;
	private Long parentId;
	
	public Category toEntity() {
		return Category.builder()
				.name(name)
				.parent(Category.builder().id(getParentId()).build())
				.build();
		
	}
}
