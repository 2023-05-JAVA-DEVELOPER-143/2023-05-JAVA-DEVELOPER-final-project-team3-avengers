package com.danaga.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.danaga.dto.*;
import com.danaga.entity.*;
import com.danaga.repository.*;
import com.danaga.service.*;
import com.danaga.service.product.*;

import jakarta.servlet.http.*;
import lombok.*;

@RestController
@RequestMapping("/refund")
@RequiredArgsConstructor
public class RefundRestController {
	private final OrderRepository orderRepository;
	private final MemberService memberService;
	private final RefundService refundService;
	
	
	//환불insert
	@PostMapping("/insert/{orderId}")
	public ResponseEntity<?> insertRefund(HttpSession session,
											@PathVariable Long orderId,
											@RequestBody RefundDto refundDto) {
		try {
			// 1. 환불 사유와 환불 계좌번호를 refundDto에서 가져온다.

			
			RefundResponseDto refundResponseDto = refundService.saveRefund(refundDto, orderId);
			return ResponseEntity.ok(refundResponseDto);
		}catch(Exception e) {
			e.printStackTrace();
			 return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	
	//환불 findbyOrderId
	
	
	
}












