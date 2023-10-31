package com.danaga.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.dto.MemberResponseDto;
import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.InterestDto;
import com.danaga.dto.product.ProductDto;
import com.danaga.dto.product.QueryStringDataDto;
import com.danaga.entity.Member;
import com.danaga.service.MemberService;
import com.danaga.service.product.CategoryService;
import com.danaga.service.product.InterestService;
import com.danaga.service.product.OptionSetService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductRestController {

	private final OptionSetService service;
	private final MemberService memberService;
	private final InterestService interestService;
	private final CategoryService categoryService;
	
	//제품디테일에서 하트 누르면 관심제품 추가
	@PostMapping("/heart/{optionSetId}")
//	@LoginCheck
	public ResponseEntity<?> tapHeartInDetail(HttpSession session,@PathVariable Long optionSetId){
		try {
			//만약 로그인유저가 아니라면 그냥 아무것도 안하고 리턴하는 처리 추가 필요
			String username=(String)session.getAttribute("sUserId");
			MemberResponseDto member = memberService.getMemberBy(username);
			ResponseDto<?> response =interestService.clickHeart(InterestDto.builder()
					.memberId(member.getId())//임시//원래는 memberId
					.optionSetId(optionSetId)
					.build());
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	//제품디테일에서 하트 누르면 관심제품 삭제
	@DeleteMapping("/heart/{optionSetId}")
//	@LoginCheck
	public ResponseEntity<?> untapHeartInDetail(HttpSession session,@PathVariable Long optionSetId){
		try {
			String username=(String)session.getAttribute("sUserId");
			MemberResponseDto member = memberService.getMemberBy(username);
			ResponseDto<?> response =interestService.deleteHeart(InterestDto.builder()
					.memberId(member.getId())//임시//원래는 memberId
					.optionSetId(optionSetId)
					.build());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	//카테고리의 상위, 자식들 조회 
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<?> chooseCategory(@PathVariable Long categoryId){
		try {
			ResponseDto<?> response =categoryService.categoryFamily(categoryId);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	//최하위 선택시 선택가능한 옵션명, 옵션값 반환
	@GetMapping("/category/options/{categoryId}")
	public ResponseEntity<?> showOptionFilter(@PathVariable Long categoryId){
		try {
			//만약 부모 id이면 showAllOptionNameValue
			//아니면 showOptionNameValue
			ResponseDto<?> response= new ResponseDto();
			if(categoryService.isYoungest(categoryId)) {
				response =service.showOptionNameValues(categoryId);
			}else {
				response =service.showAllOptionNameValues(categoryId);
			}
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	//조건에 해당하는 리스트 전체 조회 
	//datadto에서 category가 부모 카테고리(전체)이면 어떡할지 여기서 처리 
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchResult(@RequestBody QueryStringDataDto filterDto,HttpSession session){
		try {//emptySTring 걸러주고 search>>query에서 걸르
			if(session.getAttribute("sUserId")==null) {
			ResponseDto<?> response =service.searchProducts(filterDto);
			return ResponseEntity.status(HttpStatus.OK).body(response);
			}else {
				ResponseDto<?> response =service.searchProductsForMember(filterDto,(String)session.getAttribute("sUserId"));
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
