package com.danaga.controller;

import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.InterestDto;
import com.danaga.dto.product.RecentViewDto;
import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;
import com.danaga.service.MemberService;
import com.danaga.service.product.InterestService;
import com.danaga.service.product.RecentViewService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MyProductRestController {

	private final InterestService interestService;
	private final RecentViewService recentViewService;
	private final MemberService memberService;
	//관심상품에서 하트 누르면 관심제품 추가
	@PostMapping(value="/wishlist/{optionSetId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "관심제품 추가")
//	@LoginCheck
	public ResponseEntity<?> tappedHeart(@PathVariable Long optionSetId,HttpSession session ){
		try {
		String username = (String)session.getAttribute("sUserId");
		Long memberId = memberService.findIdByUsername(username);
		//memberId 찾기 
		ResponseDto<?> response = interestService.clickHeart(InterestDto.builder()
				.memberId(1L)//임시//원래는 memberId
				.optionSetId(optionSetId)
				.build());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	//관심상품에서 하트 누르면 관심제품 삭제
	@DeleteMapping(value="/wishlist/{optionSetId}",produces = "application/json;charset=UTF-8")
//	@LoginCheck
	public ResponseEntity<?> untappedHeart(@PathVariable Long optionSetId, HttpSession session){
		try {
			String username = (String)session.getAttribute("sUserId");
			Long memberId = memberService.findIdByUsername(username);
			//memberId 찾기 
			ResponseDto<?> response = interestService.deleteHeart(InterestDto.builder()
					.memberId(1L)//임시//원래는 memberId
					.optionSetId(optionSetId)
					.build());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	//나의 최근본 상품 하나 삭제 
	@DeleteMapping(value="/recentView/{optionSetId}",produces = "application/json;charset=UTF-8")
//	@LoginCheck
	public ResponseEntity<?> removeViewRecord(@PathVariable Long optionSetId, HttpSession session){
		try {
			String username = (String)session.getAttribute("sUserId");
			Long memberId = memberService.findIdByUsername(username);
			//memberId 찾기 
			ResponseDto<?> response = recentViewService.removeRecentView(
						RecentViewDto.builder()
						.memberId(1L)//임시//원래는 memberId
						.optionSetId(optionSetId)
						.build());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
		
}
