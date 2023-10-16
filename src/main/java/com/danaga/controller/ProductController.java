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
import com.danaga.service.InterestService;
import com.danaga.service.ProductService;
import com.danaga.service.ReviewService;

@RequestMapping("/product")
@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private InterestService interestService;
	@GetMapping("/{osid}")
	public ResponseEntity<?> productDetail(@RequestBody ProductDto dto, @PathVariable Long osid){
		try {
			ResponseDto<ProductDto> response = ResponseDto.<ProductDto>builder().data(productService.findById(osid)).build();
			return ResponseEntity.ok().body(response);
		}catch(Exception e) {
			ResponseDto<ProductDto> response = ResponseDto.<ProductDto>builder().error("").build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody ProductDto dto){
		try {
			//1.todoEntity로 변환
			Product entity = ProductDto.toEntity(dto);
			//2.id를 null로 초기화한다. 생성 당시에는 id가 없어야 한다
			entity.setId(null);
			//3.임시 유저 아이디를 설정해준다. 
			//인가 기능이 아직 없으므로 temporary-user만 로그인 없이 사용 가능한 애플리케이션
			//Authentication Bearer Token을 통해 받은 userId를 넘긴다.
			entity.setId(1093L);
			//4.서비스를 이용해 Todo 엔터티를 생성한다.
			List<Product> entities = productService.create(entity);
			//5.자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
			List<ProductDto> dtos = entities.stream().map(ProductDto::new).collect(Collectors.toList());
			//6.변환된 todoDTO리스트를 responseDTO에 초기화
			ResponseDto<ProductDto> response = ResponseDto.<ProductDto>builder().data(dtos).build();
			//7.REsponseDTO를 리턴
			return ResponseEntity.ok().body(response);
		}catch (Exception e) {
			//8.예외 발생시 dto 대신 error에 메시지 넣어 리턴
			String error = e.getMessage();
			ResponseDto<ProductDto> response = ResponseDto.<ProductDto> builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}
