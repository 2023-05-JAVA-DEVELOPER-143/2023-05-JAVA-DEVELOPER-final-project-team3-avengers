package com.danaga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Category;
import com.danaga.entity.Product;


public interface CategoryRepository extends JpaRepository<Category, Long>{
//	List<Category> findByCategoryName(String categoryName);
//	List<Category> findByChildTypes(List<Category> childTypes);
//	List<Category> findByProducts(List<Product> products);
//	List<Category> findBySuperType(Category superType);
}
