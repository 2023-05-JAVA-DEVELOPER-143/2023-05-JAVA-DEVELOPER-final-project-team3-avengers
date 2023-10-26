package com.danaga.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.CategoryDto;
import com.danaga.dto.product.ProductDto;
import com.danaga.dto.product.QueryStringDataDto;
import com.danaga.dto.product.RecentViewDto;
import com.danaga.entity.Category;
import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Product;
import com.danaga.repository.product.OptionSetQueryData;
import com.danaga.repository.product.OptionSetSearchQuery;
import com.danaga.service.MemberService;
import com.danaga.service.product.CategoryService;
import com.danaga.service.product.OptionSetService;
import com.danaga.service.product.RecentViewService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

	private final OptionSetService service;
	private final CategoryService categoryService;
	private final RecentViewService recentViewService;
	private final MemberService memberService;
	// 상품디테일에서 같은 카테고리 인기상품 노출 ㅇㅇ
	// 상품 클릭해서 디테일 들어갈때 조회수 업뎃 ㅇㅇ
	// product detail 조회시 recentView 추가 ㅇㅇ

	
	//옵션명,값 뽑는거 다시  옵션:list<값> 으로 나오게 ㅇㅇ 

	//필터 쿼리 짜기....
	//th:link들 하고 핸들바{}하고 
	
	
	//상단바의 검색 으로 키워드 검색 하는거 만들기 
	@GetMapping("/site_search")
	public String site_search(Model model) {
		try {
			String nameKeyword = (String) model.getAttribute("site_search");
			//검색 메인화면에 최상위 카테고리 선택할수 있게 표시
			ResponseDto<CategoryDto> categoryResponseDto = categoryService.AncestorCategories();
			List<CategoryDto> categoryList = categoryResponseDto.getData();
			ResponseDto<ProductDto> responseDto = service.searchProducts(//주문수로 전체상품 정렬하여 조회
					QueryStringDataDto.builder()
					.nameKeyword(nameKeyword)
					.orderType(OptionSetQueryData.BY_ORDER_COUNT)
					.build());
			List<ProductDto> productList = responseDto.getData();
			log.warn(productList.toString()+categoryList.toString());
			model.addAttribute("productList",productList);
			model.addAttribute("categoryList",categoryList);
			return "product/product";
		} catch (Exception e) {
			// error페이지, 페이지내 에러 메세지 넘겨주기
			e.printStackTrace();
			model.addAttribute("errorMsg", e.getMessage());
			return null;
		}
	}
	//전체상품 
	@GetMapping
	public String searchProduct(Model model) {
		try {
			//검색 메인화면에 최상위 카테고리 선택할수 있게 표시
			ResponseDto<CategoryDto> categoryResponseDto = categoryService.AncestorCategories();
			List<CategoryDto> categoryList = categoryResponseDto.getData();
			ResponseDto<ProductDto> responseDto = service.searchProducts(//주문수로 전체상품 정렬하여 조회
					QueryStringDataDto.builder()
					.orderType(OptionSetQueryData.BY_ORDER_COUNT)
					.build());
			List<ProductDto> productList = responseDto.getData();
			log.warn(productList.toString()+categoryList.toString());
			model.addAttribute("productList",productList);
			model.addAttribute("categoryList",categoryList);
			return "product/product";
		} catch (Exception e) {
			// error페이지, 페이지내 에러 메세지 넘겨주기
			e.printStackTrace();
			model.addAttribute("errorMsg", e.getMessage());
			return null;
		}
	}
	//대분류 카테고리를 선택하고 검색메인화면으로 들어간 경우
	

	//제품디테일 ++++++관련상품 20개 뽑기 옆으로 스크롤해서+++++같은 프로덕트의 다른 옵션들 뽑기
	@GetMapping("/{optionSetId}")
	public String productDetail(HttpSession session, @PathVariable Long optionSetId, Model model) {
		try {
			ProductDto productList =(ProductDto) service.findById(optionSetId).getData().get(0);
			//해당 옵션셋 찾아서 뿌리기
			List<ProductDto> optionSets = (List<ProductDto>) service.showOtherOptionSets(optionSetId).getData();
			service.updateViewCount(optionSetId);
			//디테일 들어갈때 조회수도 증가
			List<ProductDto> hits = service.displayHitProducts(optionSetId).getData();
			//같은 카테고리의 히트상품도 표시
			if(session.getAttribute("sUserId")!=null) {//로그인유저일시
//				String username =(String)session.getAttribute("sUserId");
//				Long memberId = memberService.findIdByUsername(username);
			recentViewService.addRecentView(RecentViewDto.builder()
					.memberId(1L)//memberId
					.optionSetId(optionSetId)
					.build());
			}//최근본상품에 추가
			log.warn(productList.toString()+hits.toString());
			model.addAttribute("productList",productList);
			model.addAttribute("otherOptions",optionSets);
			model.addAttribute("hits",hits);
			return "product/product_detail";
		} catch (Exception e) {
			// error페이지, 페이지내 에러 메세지 넘겨주기
			e.printStackTrace();
			model.addAttribute("errorMsg", e.getMessage());
			return "redirect:exception.html";
		}
	}
	

}
