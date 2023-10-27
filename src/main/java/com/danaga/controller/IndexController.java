package com.danaga.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.ProductDto;
import com.danaga.dto.product.QueryStringDataDto;
import com.danaga.repository.product.OptionSetQueryData;
import com.danaga.service.product.OptionSetService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
public class IndexController {
	
	//메인페이지에서 카테고리별 인기상품
	//최신상품 뽑는 거 
	@RequestMapping("/index")
	public String main() {
		try {
//			ResponseDto<ProductDto> responseDto = service.searchProducts(//주문수로 전체상품 정렬하여 조회
//					QueryStringDataDto.builder()
//					.orderType(OptionSetQueryData.BY_ORDER_COUNT)
//					.build());
//			List<ProductDto> productList = responseDto.getData();
//			model.addAttribute("productList",productList);
			return "index";
		} catch (Exception e) {
			// error페이지, 페이지내 에러 메세지 넘겨주기
			e.printStackTrace();
			return null;
		}
	}
	
}
