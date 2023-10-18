package com.danaga.service;

import java.util.List;

import com.danaga.dto.OptionSetDto;
import com.danaga.dto.ProductDto;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Product;

public interface ProductService {

	List<Product> create(Product entity);

	ProductDto findById(OptionSetDto optionSetDto);

}
