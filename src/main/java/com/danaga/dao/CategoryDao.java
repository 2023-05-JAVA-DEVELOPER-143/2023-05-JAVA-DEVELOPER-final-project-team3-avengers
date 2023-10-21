package com.danaga.dao;

import java.util.List;

import com.danaga.dto.CategoryCreateDto;
import com.danaga.entity.Category;

public interface CategoryDao {

	List<Category> findChildTypesByParentId(Long id);

	Category create(CategoryCreateDto dto);

	void delete(Long id);

	Category findById(Long id);
}
