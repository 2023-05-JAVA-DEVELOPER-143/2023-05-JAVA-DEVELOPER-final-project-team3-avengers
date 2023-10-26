package com.danaga.controller;

import java.util.*;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.danaga.dto.*;
import com.danaga.service.OrderService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderRestController {
	
	private final OrderService orderService;
	
	
	// 주문상태업데이트(특정주문)
	      
	 //1.정상주문(완료)
		//OrdersDto updateStatementByNormalOrder(Long orderNo);
	@PutMapping("/updateNormal/{orderId}")
	public ResponseEntity<?> updateStatementByNormalOrder(@PathVariable(value = "orderId")Long orderId) {
		try {
			
			OrdersDto ordersDto =  orderService.updateStatementByNormalOrder(orderId);
			return ResponseEntity.ok(ordersDto);
			
		}catch(Exception e) {
			e.printStackTrace();
			 return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	/*
	 * 2.취소주문(완료)
	 */
	// OrdersDto updateStatementByCancleOrder(Long orderNo)
	
	@PutMapping("/updateCancel/{orderId}")
	public ResponseEntity<?> updateStatementByCancleOrder(@PathVariable(value = "orderId")Long orderId) {
		try {
			
			OrdersDto ordersDto =  orderService.updateStatementByCancleOrder(orderId);
			return ResponseEntity.ok(ordersDto);
			
		}catch(Exception e) {
			e.printStackTrace();
			 return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	
	/*
	 * 3.환불주문(환불대기중)
	 */
//	 OrdersDto updateStatementByRefundOrder(Long orderNo) 
	@PutMapping("/updateClientRefund/{orderId}")
	public ResponseEntity<?> updateStatementByClientRefundOrder(@PathVariable(value = "orderId")Long orderId) {
		try {
			
			OrdersDto ordersDto =  orderService.updateStatementByClientRefundOrder(orderId);
			return ResponseEntity.ok(ordersDto);
			
		}catch(Exception e) {
			e.printStackTrace();
			 return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	/*
	 * 4.환불주문(완료)
	 */
//	 OrdersDto updateStatementByRefundOrder(Long orderNo) 
	@PutMapping("/updateAdminRefund/{orderId}")
	public ResponseEntity<?> updateStatementByAdminRefundOrder(@PathVariable(value = "orderId")Long orderId) {
		try {
			
			OrdersDto ordersDto =  orderService.updateStatementByAdminRefundOrder(orderId);
			return ResponseEntity.ok(ordersDto);
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/*
	 * 4.상태리셋( 배송중→입금대기중  완료)
	 */
	//	OrdersDto updateStatementByResetOrder(Long orderNo) 
	@PutMapping("/updateReset/{orderId}")
	public ResponseEntity<?> updateStatementByResetOrder(@PathVariable(value = "orderId")Long orderId) {
		try {
			
			OrdersDto ordersDto =  orderService.updateStatementByResetOrder(orderId);
			return ResponseEntity.ok(ordersDto);
			
		}catch(Exception e) {
			e.printStackTrace();
			 return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
}
