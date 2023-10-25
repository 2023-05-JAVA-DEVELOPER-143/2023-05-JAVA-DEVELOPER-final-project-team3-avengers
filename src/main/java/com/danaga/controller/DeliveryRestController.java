package com.danaga.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.danaga.dto.*;
import com.danaga.repository.*;
import com.danaga.service.*;

import lombok.*;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryRestController {
	
	@Autowired
	private DeliveryService deliveryService;
	
	@GetMapping
	public ModelAndView delivery() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("delivery/delivery");
		return modelAndView;
	}
	
	//배송 insert
	@PostMapping("/insert/{orderId}")
	public ResponseEntity<?> insertDelivery(
											@PathVariable(value = "orderId") Long orderId,
											@RequestBody DeliveryDto deliveryDto) {
		try {
			DeliveryResponseDto deliveryResponseDto = deliveryService.saveDeliveryByOrdersId(deliveryDto, orderId);
			return ResponseEntity.status(HttpStatus.CREATED).body(deliveryResponseDto);
		}catch(Exception e) {
			e.printStackTrace();
			 return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	//환불 select
	@GetMapping("/select/{orderId}")
	public ResponseEntity<?> findRefundByOrderId(@PathVariable(value = "orderId") Long orderId) {
		try {
			DeliveryResponseDto deliveryResponseDto = deliveryService.findDeliveryByOrdersId(orderId);
			return ResponseEntity.status(HttpStatus.CREATED).body(deliveryResponseDto);		
			} catch(Exception e) {
			e.printStackTrace();
			 return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
