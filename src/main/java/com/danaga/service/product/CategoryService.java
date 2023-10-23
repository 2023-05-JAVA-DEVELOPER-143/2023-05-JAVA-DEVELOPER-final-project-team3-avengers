package com.danaga.service.product;

import java.util.List;

import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.CategoryDto;
import com.danaga.entity.Category;

public interface CategoryService {
	//최상위 카테고리들만 조회
	ResponseDto<?> AncestorCategories();
	
	//카테고리 선택하면 그 카테고리의 직계자식카테고리만 조회 
	ResponseDto<?> categoryFamily(Long id);
	
	//카테고리 추가
	ResponseDto<?> create(CategoryDto dto);
	
	//카테고리 수정
	ResponseDto<?> update(CategoryDto dto);
	
	//카테고리 삭제
	ResponseDto<?> delete(Long id);

	//자식 카테고리가 있는지 확인 있다면 false, 가장 어린 자식이면 true
	Boolean isYoungest(Long id);
	
}