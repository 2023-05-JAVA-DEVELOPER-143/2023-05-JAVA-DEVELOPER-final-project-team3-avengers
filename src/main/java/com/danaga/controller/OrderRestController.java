package com.danaga.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.dto.OrdersDto;
import com.danaga.dto.OrdersProductDto;
import com.danaga.service.OrderService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderRestController {
	
	private final OrderService orderService;
	
	@PostMapping("/add")
	public ResponseEntity<OrdersDto> memberProductOrderAdd(HttpSession httpSession,@RequestBody OrdersProductDto ordersProductDto)throws Exception{
		
			
		
		String sUserId= (String)httpSession.getAttribute("sUserId");
			
		OrdersDto ordersDto= orderService.memberProductOrderSave(sUserId, ordersProductDto);
		return ResponseEntity.ok(ordersDto);
			
	}
	
	
}
