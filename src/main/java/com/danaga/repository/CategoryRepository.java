package com.danaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
