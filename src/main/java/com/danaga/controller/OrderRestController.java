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
	
	/*
	 * 주문상세보기(회원)
	 */
	@GetMapping("/member_order_detail/{orderNo}")
	public ResponseEntity<?> memberOrderDetail(@PathVariable Long orderNo){
		
		try {
			OrdersDto ordersDto= orderService.memberOrderDetail(orderNo);
			
			return ResponseEntity.ok(ordersDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
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
//	//상품에서 직접주문(완료)
//	@PostMapping("/add")
//	public ResponseEntity<?> memberProductOrderAdd(HttpSession httpSession, OrdersProductDto ordersProductDto)throws Exception{
//		
//		try {
//			
//			String sUserId = "User1";
//			OrdersDto ordersDto= orderService.memberProductOrderSave(sUserId, ordersProductDto);
//			return ResponseEntity.ok(ordersDto);
//		}catch(Exception e) {
//			e.printStackTrace();
//			 return ResponseEntity.badRequest().body(e.getMessage());
//		}
//		
//	
//	}
//	/*
//	 * 주문상세보기(이미 완성됨)
//	 */
//	@GetMapping("/findOrder/{orderNo}")
//	public ResponseEntity<OrdersDto> memberOrderDetail(@PathVariable Long orderNo)throws Exception{
//		
//		OrdersDto ordersDto2= orderService.memberOrderDetail(orderNo);
//		
//		return ResponseEntity.ok().body(ordersDto2);
//	}
//	
//	/*
//	 * 비회원 카트에서 주문(완료)
//	 */
//	@PostMapping("/guestadd/")
//	public ResponseEntity<?> guestCartOrderSave(HttpSession session, DeliveryDto deliveryDto, OrderGuestDto orderGuestDto ) {
//		try {
//			//List<CartDto> fUserCarts // 지금 session을 받아올 수 없어서?
//			List<CartDto> cartDtoList = new ArrayList<>();
//			CartDto cartDto = new CartDto();
//			cartDto.setId(2L);
//			cartDto.setQty(2);
//			cartDtoList.add(cartDto);
////			List<CartDto> fUserCarts = (List<CartDto>) session.getAttribute("fUserCarts");
////			System.out.println("@@@@@@@@@@@@fUserCarts: " + fUserCarts);
//			OrdersDto ordersDto =  orderService.guestCartOrderSave(cartDtoList, deliveryDto, orderGuestDto);
//			return ResponseEntity.ok(ordersDto);
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//			 return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//	
//
//	/*
//	 * cart에서 주문(완료)
//	 */	
//	@PostMapping("/cartAdd")
//	public ResponseEntity<?> memberCartOrderSave(HttpSession session,DeliveryDto deliveryDto) {
//		try {
//			
////			String sUserId = (String)session.getAttribute("sUserId");
//			String sUserId = "User1";//테스트 당시 이 멤버만 카트 있었음
//			OrdersDto ordersDto =  orderService.memberCartOrderSave(sUserId, deliveryDto);
//			return ResponseEntity.ok(ordersDto);
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//			 return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//	/*
//	 * cart에서 선택주문(완료)
//	 */
//	@PostMapping("/cartSelectAdd/{optionSetIdArray}")
//	public ResponseEntity<?> memberCartSelectOrderSave(HttpSession session,DeliveryDto deliveryDto,@PathVariable(value = "optionSetIdArray") List<Long> optionSetIdArray ) {
//		try {
//			
////			String sUserId = (String)session.getAttribute("sUserId");
//			String sUserId = "User3";//테스트 당시 이 멤버만 카트 있었음
//			
//			OrdersDto ordersDto =  orderService.memberCartSelectOrderSave(sUserId, deliveryDto,optionSetIdArray);
//			return ResponseEntity.ok(ordersDto);
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//			 return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//	
//	
//	/*
//	 * 주문+주문아이템 목록(완료)// UserName을 세션으로 받는거 맞죠?
//	 */
//	//List<OrdersDto> memberOrderList(String userName)throws Exception;
//	@GetMapping("/selectAll")
//	public ResponseEntity<?> memberOrderList(HttpSession session) {
//		try {
//			
////			String sUserId = (String)session.getAttribute("sUserId");
//			String sUserId = "User73";
//			
//			List<OrdersDto> ordersDtoList =  orderService.memberOrderList(sUserId);
//			return ResponseEntity.ok(ordersDtoList);
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//			 return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//
//	/*
//	 * 주문 상세보기(완료)
//	 */
//	//OrdersDto memberOrderDetail(Long orderNo) throws Exception;
//	@GetMapping("/select/{orderId}")
//	public ResponseEntity<?> memberOrderDetail(HttpSession session, @PathVariable(value = "orderId")Long orderId) {
//		try {
//			
////			String sUserId = (String)session.getAttribute("sUserId");
//			String sUserId = "User5";//테스트 당시 이 멤버만 카트 있었음
//			
//			OrdersDto ordersDto =  orderService.memberOrderDetail(orderId);
//			return ResponseEntity.ok(ordersDto);
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//			 return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//	
//	
//	
//	// 주문상태업데이트(특정주문)
//	      
//	 //1.정상주문(완료)
//		//OrdersDto updateStatementByNormalOrder(Long orderNo);
//	@PutMapping("/updateNormal/{orderId}")
//	public ResponseEntity<?> updateStatementByNormalOrder(HttpSession session, @PathVariable(value = "orderId")Long orderId) {
//		try {
//			
////			String sUserId = (String)session.getAttribute("sUserId");
////			String sUserId = "User1";//테스트 당시 이 멤버만 카트 있었음
//			
//			OrdersDto ordersDto =  orderService.updateStatementByNormalOrder(orderId);
//			return ResponseEntity.ok(ordersDto);
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//			 return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//	
//	/*
//	 * 2.취소주문(완료)
//	 */
//	// OrdersDto updateStatementByCancleOrder(Long orderNo)
//	
//	@PutMapping("/updateCancel/{orderId}")
//	public ResponseEntity<?> updateStatementByCancleOrder(HttpSession session, @PathVariable(value = "orderId")Long orderId) {
//		try {
//			
////			String sUserId = (String)session.getAttribute("sUserId");
////			String sUserId = "User1";//테스트 당시 이 멤버만 카트 있었음
//			OrdersDto ordersDto =  orderService.updateStatementByCancleOrder(orderId);
//			return ResponseEntity.ok(ordersDto);
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//			 return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//	
//	
//	
//	/*
//	 * 3.환불주문(완료)
//	 */
//////	 OrdersDto updateStatementByRefundOrder(Long orderNo) 
////	@PutMapping("/updateRefund/{orderId}")
////	public ResponseEntity<?> updateStatementByRefundOrder(HttpSession session, @PathVariable(value = "orderId")Long orderId) {
////		try {
////			
//////			String sUserId = (String)session.getAttribute("sUserId");
//////			String sUserId = "User1";//테스트 당시 이 멤버만 카트 있었음
////			OrdersDto ordersDto =  orderService.updateStatementByRefundOrder(orderId);
////			return ResponseEntity.ok(ordersDto);
////			
////		}catch(Exception e) {
////			e.printStackTrace();
////			 return ResponseEntity.badRequest().body(e.getMessage());
////		}
////	}
//
//	/*
//	 * 4.상태리셋( 배송중→입금대기중  완료)
//	 */
//	//	OrdersDto updateStatementByResetOrder(Long orderNo) 
//	@PutMapping("/updateReset/{orderId}")
//	public ResponseEntity<?> updateStatementByResetOrder(HttpSession session, @PathVariable(value = "orderId")Long orderId) {
//		try {
//			
////			String sUserId = (String)session.getAttribute("sUserId");
////			String sUserId = "User1";//테스트 당시 이 멤버만 카트 있었음
//			OrdersDto ordersDto =  orderService.updateStatementByResetOrder(orderId);
//			return ResponseEntity.ok(ordersDto);
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//			 return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//}
