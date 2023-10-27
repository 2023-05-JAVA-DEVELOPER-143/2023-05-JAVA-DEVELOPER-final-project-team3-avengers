package com.danaga.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.danaga.dto.*;
import com.danaga.entity.*;
import com.danaga.repository.*;
import com.danaga.service.*;

import io.swagger.v3.oas.annotations.*;
import lombok.*;
@Controller
@RequestMapping("/refund")
public class RefundController {

	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private RefundService refundService;
	//환불하기 Insert
	@PostMapping("/saveRefund/{orderId}")
	public String saveRefund(Model model,RefundDto refundDto, @PathVariable Long orderId) throws Exception {
		 RefundResponseDto refundResponseDto = refundService.saveRefund(refundDto, orderId);
		 model.addAttribute("refundResponseDto", refundResponseDto);
		 return "refund/refund";
	}
	
	

	
	
	
	
}
