package com.danaga.service;

import java.util.List;

import com.danaga.dto.ProductDto;
import com.danaga.entity.Product;

public interface ProductService {

	List<Product> create(Product entity);

	List<ProductDto> findById(Long osid);

}
