package com.danaga.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.ProductDto;

@RestController
@RequestMapping("/product")
public class ProductRestController {
//	@GetMapping("/{osid}")
//	public ResponseEntity<?> detail(@PathVariable Long osid){
//		try {
//			OptionSetDto optionSetDto = OptionSetDto.builder().id(osid).build();
//			ProductDto productDto = productService.findById(optionSetDto);
//			List<ProductDto> dtos = new ArrayList<>();
//			dtos.add(productDto);
//			ResponseDto<ProductDto> response = ResponseDto.<ProductDto>builder().data(dtos).build();
//			return ResponseEntity.ok().body(response);
//		}catch(Exception e) {
//			ResponseDto<ProductDto> response = ResponseDto.<ProductDto>builder().error(e.getMessage()).build();
//			return ResponseEntity.badRequest().body(response);
//		}
//	}
}
