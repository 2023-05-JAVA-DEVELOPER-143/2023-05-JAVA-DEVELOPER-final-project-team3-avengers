package com.danaga.dto.product;

import com.danaga.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	
	private Long id;
	private String name;
	private Long parentId;
	
	public Category toEntity() {
		return Category.builder()
				.id(id)
				.name(name)
				.parent(Category.builder().id(getParentId()).build())
				.build();
	}
	
	public CategoryDto(Category entity){
		this.id=entity.getId();
		this.name=entity.getName();
		this.parentId=entity.getParent().getId();
	}
}
