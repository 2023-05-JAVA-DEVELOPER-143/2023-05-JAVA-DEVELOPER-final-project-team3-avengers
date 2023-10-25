package com.danaga.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.danaga.dao.product.CategoryDao;
import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.CategoryDto;
import com.danaga.entity.Category;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryDao categoryDao;
	
	//최상위 카테고리만 조회
	@Override
	public ResponseDto<CategoryDto> AncestorCategories() {
		List<CategoryDto> data = categoryDao.findByParentEmpty().stream().map(t->new CategoryDto(t)).collect(Collectors.toList());
		return ResponseDto.<CategoryDto>builder().data(data).build();
	}
	//카테고리의 상위, 자식들 조회
	@Override
	public ResponseDto<?> categoryFamily(Long id) {
		List<CategoryDto> data = categoryDao.findChildTypesByParentId(id).stream().map(t->new CategoryDto(t)).collect(Collectors.toList());
		return ResponseDto.<CategoryDto>builder().data(data).build();
	}

	//카테고리 생성
	@Override
	public ResponseDto<?> create(CategoryDto dto) {
		List<CategoryDto> data = new ArrayList<>();
		data.add(categoryDao.create(dto));
		return ResponseDto.<CategoryDto>builder().data(data).build();
	}
	//카테고리 수정
	@Override
	@Transactional
	public ResponseDto<?> update(CategoryDto dto) {
		List<CategoryDto> data = new ArrayList<>();
		CategoryDto updateDto = categoryDao.update(dto);
		data.add(updateDto); 
		return ResponseDto.<CategoryDto>builder().data(data).build();
	}
	//카테고리 삭제
	@Override
	public ResponseDto<?> delete(Long id) {
		categoryDao.delete(id);
		List<CategoryDto> data = new ArrayList<>();
		return ResponseDto.<CategoryDto>builder().data(data).build();
	}
	//최하위 카테고리인지 
	@Override
	public Boolean isYoungest(Long id){
		List<Category> children = categoryDao.findChildTypesByParentId(id);
		return children.size()==0 ? true : false;
	}

}
