package com.danaga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.dao.OptionSetDao;
import com.danaga.dao.OptionsDao;
import com.danaga.dao.ProductDao;
import com.danaga.dto.OptionSetDto;
import com.danaga.dto.ProductDto;
import com.danaga.dto.ResponseDto;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;
import com.danaga.entity.Product;


@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDao productDao;
	@Autowired
	private OptionSetDao optionSetDao;
	@Autowired
	private OptionsDao optionsDao;
	
	public List<ResponseDto<ProductDto>> products(){
		return null;
	}

	@Override
	public List<Product> create(Product entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductDto findById(OptionSetDto optionSetDto) {
		OptionSet optionSets = optionSetDao.findById(optionSetDto.getId());
		Product product = optionSets.getProduct();
		//List<Review> reviews = product.getReviews();
		List<Options> options = optionsDao.findOptionsByOptionSet(optionSets);
		return new ProductDto(product,optionSets,options);
	}
}
