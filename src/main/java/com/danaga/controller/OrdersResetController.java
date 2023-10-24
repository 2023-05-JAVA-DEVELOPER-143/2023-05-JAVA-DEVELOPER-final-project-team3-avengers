package com.danaga.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.danaga.dto.*;
import com.danaga.repository.*;
import com.danaga.service.*;

import jakarta.servlet.http.*;
import lombok.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersResetController {
	@Autowired
	private OrderService orderService;
	
	@GetMapping
	public ModelAndView orders() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("orders/orders");
		return modelAndView;
	}
	
	//insert
	//상품에서 직접 주문
	@PostMapping("/insertOptionSet/{optionSetId}")
	public ResponseEntity<?> insertRefund(	HttpSession session,
											@PathVariable(value = "optionSetId") Long optionSetId,	
											@RequestBody OrdersDto ordersDto) {
		
		try {
			Long id = (Long)session.getAttribute("sUserId");
			OrdersResponseDto orderResponseDto = orderService.memberProductOrderSave(ordersDto,optionSetId, null, null);
			return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDto);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("#######################");
			 return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
