package com.danaga.controller;

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

import com.danaga.dto.ProductDto;
import com.danaga.dto.ResponseDto;
import com.danaga.entity.Product;
import com.danaga.service.ProductService;

@RequestMapping("/product")
@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	@GetMapping("/{osid}")
	public ResponseEntity<?> detail(@RequestBody ProductDto productDto, @PathVariable Long osid){
		try {
			ResponseDto<ProductDto> response = ResponseDto.<ProductDto>builder().data(productService.findById(osid)).build();
			return ResponseEntity.ok().body(response);
		}catch(Exception e) {
			ResponseDto<ProductDto> response = ResponseDto.<ProductDto>builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	//List<ProductDto> dtos = entities.stream().map(ProductDto::new).collect(Collectors.toList());
		
}