package com.danaga.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.dto.CategoryCreateDto;
import com.danaga.entity.Category;
import com.danaga.repository.CategoryRepository;
@Repository
public class CategoryDaoImpl implements CategoryDao{
	
	@Autowired
	private CategoryRepository repository;
	@Override
	public List<Category> findChildTypesByParentId(Long id){
		return repository.findChildTypesByParent_Id(id);
	}
	@Override
	public Category create(CategoryCreateDto dto) {
		Category entity = dto.toEntity();
		Category saved = repository.save(entity);
		return repository.findById(saved.getId()).get();
	}
	
	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}
	@Override
	public Category findById(Long id) {
		return repository.findById(id).get();
	}
}
