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


@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

	private final OptionSetService service;
	private final CategoryService categoryService;
	private final RecentViewService recentViewService;
	private final MemberService memberService;
	// 상품디테일에서 같은 카테고리 인기상품 노출
	// 상품 클릭해서 디테일 들어갈때 조회수 업뎃
	// product detail 조회시 recentView 추가

	//전체상품 
	@GetMapping
	public String searchProduct(Model model) {
		try {
			//검색 메인화면에 최상위 카테고리 선택할수 있게 표시
			ResponseDto<CategoryDto> categoryResponseDto = categoryService.AncestorCategories();
			List<CategoryDto> ancestorCategories = categoryResponseDto.getData();
			ResponseDto<ProductDto> responseDto = service.searchProducts(//주문수로 전체상품 정렬하여 조회
					QueryStringDataDto.builder()
					.orderType(OptionSetQueryData.BY_ORDER_COUNT)
					.build());
			List<ProductDto> productList = responseDto.getData();
			
			model.addAttribute("productList",productList);
			model.addAttribute("ancestorCategory",ancestorCategories);
			return "product/product";
		} catch (Exception e) {
			// error페이지, 페이지내 에러 메세지 넘겨주기
			e.printStackTrace();
			model.addAttribute("errorMsg", e.getMessage());
			return null;
		}
	}
	//대분류 카테고리를 선택하고 검색메인화면으로 들어간 경우
	

	//제품디테일 
	@GetMapping(value = "/product_detail",params = "optionSetId")
	public String productDetail(HttpSession session, @RequestParam Long optionSetId, @ModelAttribute Model model) {
		try {
			OptionSet findOptionSet = (OptionSet) service.findById(optionSetId).getData().get(0);
			//해당 옵션셋 찾아서 뿌리기
			service.updateViewCount(optionSetId);
			//디테일 들어갈때 조회수도 증가
			List<ProductDto> hitProducts = service.displayHitProducts(optionSetId).getData();
			//같은 카테고리의 히트상품도 표시
			if(session.getAttribute("sUserId")!=null) {//로그인유저일시
//				String username =(String)session.getAttribute("sUserId");
//				Long memberId = memberService.findIdByUsername(username);
			recentViewService.addRecentView(RecentViewDto.builder()
					.memberId(1L)//memberId
					.optionSetId(optionSetId)
					.build());
			}//최근본상품에 추가
			model.addAttribute("findOptionSet",findOptionSet);
			model.addAttribute("hitProducts",hitProducts);
			return "product/product_detail";
		} catch (Exception e) {
			// error페이지, 페이지내 에러 메세지 넘겨주기
			e.printStackTrace();
			model.addAttribute("errorMsg", e.getMessage());
			return "redirect:exception.html";
		}
	}
	//제품디테일 파라메터 없을때 내용 수정 필요
	@GetMapping(value = "product_detail",params = "!optionSetId")
	public String noProductDetail(HttpSession session, @RequestParam Long optionSetId, @ModelAttribute Model model) {
		try {
			OptionSet findOptionSet = (OptionSet) service.findById(optionSetId).getData().get(0);
			//해당 옵션셋 찾아서 뿌리기
			service.updateViewCount(optionSetId);
			//디테일 들어갈때 조회수도 증가
			List<ProductDto> hitProducts = service.displayHitProducts(optionSetId).getData();
			//같은 카테고리의 히트상품도 표시
			if(session.getAttribute("sUserId")!=null) {//로그인유저일시
//				String username =(String)session.getAttribute("sUserId");
//				Long memberId = memberService.findIdByUsername(username);
				recentViewService.addRecentView(RecentViewDto.builder()
						.memberId(1L)//memberId
						.optionSetId(optionSetId)
						.build());
			}//최근본상품에 추가
			model.addAttribute("findOptionSet",findOptionSet);
			model.addAttribute("hitProducts",hitProducts);
			return "product/product_detail";
		} catch (Exception e) {
			// error페이지, 페이지내 에러 메세지 넘겨주기
			e.printStackTrace();
			model.addAttribute("errorMsg", e.getMessage());
			return "redirect:exception.html";
		}
	}

}
