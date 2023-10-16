package com.danaga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.dao.ProductDao;
import com.danaga.dto.ProductDto;
import com.danaga.dto.ResponseDto;
import com.danaga.entity.Product;


@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDao productDao;
	
	public List<ResponseDto<ProductDto>> products(){
		return null;
	}

	@Override
	public List<Product> create(Product entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDto> findById(Long osid) {
		// TODO Auto-generated method stub
		return null;
	}
}
