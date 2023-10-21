package com.danaga.dao.product;

import com.danaga.dto.product.ProductSaveDto;
import com.danaga.entity.Product;

public interface ProductDao {

	Product findById(Long id);
	Product findByOptionSetId(Long optionSetId);
	Product create(ProductSaveDto dto);
	void deleteById(Long id);
}
