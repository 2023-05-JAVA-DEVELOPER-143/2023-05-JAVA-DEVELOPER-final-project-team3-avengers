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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.*;
@Controller
@RequestMapping("/refund")
public class RefundController {

	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private RefundService refundService;
	@Autowired
	private OrderService orderService;
	
	
	//주문목록에서 환불하기 페이지로 가기
	@GetMapping("/refund_main_form/{orderId}")
	public String refund_main_form(Model model,@PathVariable Long orderId,HttpServletRequest request) throws Exception{
		System.out.println("RefundController의 refund_main_form메소드에서 orderId는 "+orderId);
		OrdersDto ordersDto = orderService.memberOrderDetail(orderId);
		model.addAttribute("ordersDto", ordersDto);
		
	    // 세션에 orderId 저장
	    HttpSession session = request.getSession();
	    session.setAttribute("orderId", orderId);
		return "refund/refund_main_form";
	}
	
	
	
		
	
	//환불하기 Insert
	@PostMapping("/saveRefund/{orderId}")
	public String saveRefund(Model model,RefundDto refundDto, @PathVariable Long orderId) throws Exception {
		 RefundResponseDto refundResponseDto = refundService.saveRefund(refundDto, orderId);
		 model.addAttribute("refundResponseDto", refundResponseDto);
		 return "orders/orders_list";
	}
	
	

	
	
	
	
}
