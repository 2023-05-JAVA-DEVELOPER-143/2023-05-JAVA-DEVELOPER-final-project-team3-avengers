package com.danaga.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.InterestDto;
import com.danaga.dto.product.RecentViewDto;
import com.danaga.exception.product.ProductExceptionMsg;
import com.danaga.exception.product.ProductSuccessMsg;
import com.danaga.service.MemberService;
import com.danaga.service.product.InterestService;
import com.danaga.service.product.OptionSetService;
import com.danaga.service.product.RecentViewService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MyProductRestController {

	private final OptionSetService service;
	private final InterestService interestService;
	private final RecentViewService recentViewService;
	private final MemberService memberService;

	// 관심상품에서 하트 누르면 관심제품 추가
	@PostMapping(value = "/wishlist/{optionSetId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "관심제품 추가")
	public ResponseEntity<?> tappedHeart(@PathVariable Long optionSetId, HttpSession session) {
		ResponseDto<?> responseBody = new ResponseDto<>();
		if (session.getAttribute("sUserId") == null) {
			responseBody.setMsg(ProductExceptionMsg.NEED_LOGIN);
			return ResponseEntity.badRequest().body(responseBody);
		}
		if (service.findById(optionSetId).getMsg().equals(ProductSuccessMsg.FIND_OPTIONSET_BY_ID)) {
			try {
				String username = (String) session.getAttribute("sUserId");
				Long memberId = memberService.findIdByUsername(username);
				ResponseDto<?> response = interestService.clickHeart(InterestDto.builder()
						.memberId(memberId)
						.optionSetId(optionSetId).build());
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			} catch (Exception e) {
				responseBody.setMsg(ProductExceptionMsg.FOUND_NO_MEMBER);
				return ResponseEntity.badRequest().body(responseBody);
			}
		} else {
			responseBody.setMsg(ProductExceptionMsg.FOUND_NO_OPTIONSET);
			return ResponseEntity.badRequest().body(responseBody);
		}
	}

	// 관심상품에서 하트 누르면 관심제품 삭제
	@DeleteMapping(value = "/wishlist/{optionSetId}", produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> untappedHeart(@PathVariable Long optionSetId, HttpSession session) {
		ResponseDto<?> responseBody = new ResponseDto<>();
		if (session.getAttribute("sUserId") == null) {
			responseBody.setMsg(ProductExceptionMsg.NEED_LOGIN);
			return ResponseEntity.badRequest().body(responseBody);
		}
		if (service.findById(optionSetId).getMsg().equals(ProductSuccessMsg.FIND_OPTIONSET_BY_ID)) {
			try {
				String username = (String) session.getAttribute("sUserId");
				Long memberId = memberService.findIdByUsername(username);
				ResponseDto<?> response = interestService.deleteHeart(InterestDto.builder()
						.memberId(memberId)
						.optionSetId(optionSetId).build());
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} catch (Exception e) {
				responseBody.setMsg(ProductExceptionMsg.FOUND_NO_MEMBER);
				return ResponseEntity.badRequest().body(responseBody);
			}
		} else {
			responseBody.setMsg(ProductExceptionMsg.FOUND_NO_OPTIONSET);
			return ResponseEntity.badRequest().body(responseBody);
		}
	}

	// 나의 최근본 상품 하나 삭제
	@DeleteMapping(value = "/recentView/{optionSetId}", produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> removeViewRecord(@PathVariable Long optionSetId, HttpSession session) {
		ResponseDto<?> responseBody = new ResponseDto<>();
		if (session.getAttribute("sUserId") == null) {
			responseBody.setMsg(ProductExceptionMsg.NEED_LOGIN);
			return ResponseEntity.badRequest().body(responseBody);
		}
		if (service.findById(optionSetId).getMsg().equals(ProductSuccessMsg.FIND_OPTIONSET_BY_ID)) {
			try {
				String username = (String) session.getAttribute("sUserId");
				Long memberId = memberService.findIdByUsername(username);
				ResponseDto<?> response = recentViewService.removeRecentView(RecentViewDto.builder()
						.memberId(memberId)
						.optionSetId(optionSetId).build());
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} catch (Exception e) {
				responseBody.setMsg(ProductExceptionMsg.FOUND_NO_MEMBER);
				return ResponseEntity.badRequest().body(responseBody);
			}
		} else {
			responseBody.setMsg(ProductExceptionMsg.FOUND_NO_OPTIONSET);
			return ResponseEntity.badRequest().body(responseBody);
		}
	}

}
