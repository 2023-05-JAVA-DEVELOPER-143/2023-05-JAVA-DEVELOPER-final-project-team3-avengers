package com.danaga.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Category;
import com.danaga.entity.Product;


public interface CategoryRepository extends JpaRepository<Category, Long>{
//	List<Category> findByCategoryName(String categoryName);
//	List<Category> findByChildTypes(List<Category> childTypes);
//	List<Category> findByProducts(List<Product> products);
	//상위타입 선택시 하위타입 카테고리 반환
	List<Category> findChildTypesByParent_Id(Long id);
	//하위 타입 선택시 상위 타입 카테고리 반환은 그냥 category의 supertype property 바로 접근하면 
	List<Category> findByParentNull();
	List<Category> findByCategorySets_Product_OptionSets_Id(Long optionSetId);
}
