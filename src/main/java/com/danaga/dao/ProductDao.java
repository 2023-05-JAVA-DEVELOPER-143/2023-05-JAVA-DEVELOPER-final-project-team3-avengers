package com.danaga.dao;

import com.danaga.dto.ProductCreateDto;
import com.danaga.entity.Product;

public interface ProductDao {

	Product findById(Long id);
	Product findByOptionSetId(Long optionSetId);
	Product create(ProductCreateDto dto);
	void deleteById(Long id);
}
