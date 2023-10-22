package com.danaga.dao.product;

import java.util.List;

import com.danaga.dto.product.CategoryDto;
import com.danaga.entity.Category;

public interface CategoryDao {

	List<Category> findChildTypesByParentId(Long id);

	CategoryDto create(CategoryDto dto);
	CategoryDto update(CategoryDto dto);

	void delete(Long id);

	Category findById(Long id);
	
	List<Category> findByParentEmpty();
	
	List<Category> findByOptionSetId(Long optionSetId);
}
