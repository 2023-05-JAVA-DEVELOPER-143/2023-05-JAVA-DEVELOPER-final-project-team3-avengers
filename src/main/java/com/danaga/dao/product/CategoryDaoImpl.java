package com.danaga.dao.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.dto.product.CategoryDto;
import com.danaga.entity.Category;
import com.danaga.repository.product.CategoryRepository;
@Repository
public class CategoryDaoImpl implements CategoryDao{
	
	@Autowired
	private CategoryRepository repository;
	@Override
	public List<Category> findChildTypesByParentId(Long id){
		return repository.findChildTypesByParent_Id(id);
	}
	@Override
	public CategoryDto create(CategoryDto dto) {
		Category entity = dto.toEntity();
		Category saved = repository.save(entity);
		return new CategoryDto(repository.findById(saved.getId()).get());
	}
	
	public CategoryDto update(CategoryDto dto) {
		Category findEntity = repository.findById(dto.getId()).get();
		findEntity.setName(dto.getName());
		findEntity.setParent(Category.builder().id(dto.getParentId()).build());
		Category updated = repository.save(findEntity);
		return new CategoryDto(repository.findById(updated.getId()).get());
	}
	
	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}
	@Override
	public Category findById(Long id) {
		return repository.findById(id).get();
	}
	@Override
	public List<Category> findByParentEmpty() {
		return repository.findByParentNull();
	}
	@Override
	public List<Category> findByOptionSetId(Long optionSetId) {
		//optionset_id로 카테고리 찾기 
		return repository.findByCategorySets_Product_OptionSets_Id(optionSetId);
	}
}
