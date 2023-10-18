package com.danaga.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.dto.OptionSetDto;
import com.danaga.dto.ProductDto;
import com.danaga.dto.ResponseDto;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Product;
import com.danaga.service.ProductService;

@RequestMapping("/product")
@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	//List<ProductDto> dtos = entities.stream().map(ProductDto::new).collect(Collectors.toList());
		
}
