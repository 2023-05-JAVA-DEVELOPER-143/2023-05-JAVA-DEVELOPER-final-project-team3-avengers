package com.danaga.dto.product;

import com.danaga.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategorySaveDto {
	private String name;
	private Long parentId;
	
	public Category toEntity() {
		return Category.builder()
				.name(name)
				.parent(Category.builder().id(parentId).build())
				.build();
	}
	CategorySaveDto(Category entity){
		this.name=entity.getName();
		this.parentId=entity.getParent().getId();
	}
}
