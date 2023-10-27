package com.danaga.controller;

import java.util.*;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.jaxb.SpringDataJaxb.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.danaga.dto.*;
import com.danaga.entity.*;
import com.danaga.service.*;

import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/order")
public class OrderController {
   
   @Autowired
   private OrderService orderService;
   @Autowired
   private MemberService memberService;
   
/*******************************공용****************************/
	/*
	 * 주문상세보기(회원)
	 */
  //OrdersDto memberOrderDetail(Long orderNo)
   @GetMapping("/memberOrderDetail/{orderNo}")
   public String memberOrderDetail(Model model,@PathVariable  Long orderNo)throws Exception {
	   try {

		   OrdersDto ordersDto = orderService.memberOrderDetail(orderNo);
		   model.addAttribute("ordersDto", ordersDto);
		   return "orders_detail";
	   }catch(Exception e) {
           e.printStackTrace();
           model.addAttribute("errorMsg", e.getMessage());
           return null;
	   }
   }

   /*
	 * 주문+주문아이템 목록
	 */
//로그인 한 후에 메뉴에서 주문목록보기 클릭하면 나오게하기
   @GetMapping("/memberOrderList")
   public String memberOrderList(Model model, HttpSession session) {// 세션으로 userName받을듯
	   try {
//		   String sUserId = "User1";
		   String sUserId = (String)session.getAttribute("sUserId");
		   List<OrdersDto> orderDtoList = orderService.memberOrderList(sUserId);
		   model.addAttribute("orderDtoList", orderDtoList);
		   return "orders/orders";
	   } catch(Exception e) {
           e.printStackTrace();
           model.addAttribute("주문목록이 없습니다?", e.getMessage());
           return null;
	   }
   }
	/*
	 * 주문상세보기(회원)
	 */
   //OrdersDto memberOrderDetail(Long orderNo)
   
   
   
   
   
   
	/*
	 * 주문+주문아이템 목록(비회원)
	 */
   @GetMapping("/guestOrderList/{orderNo},{phoneNumber}")
   public String guestOrderList(Model model, @PathVariable Long orderNo, @PathVariable String phoneNumber) {
       try {
    	   
    	   //원래는 @PathVariable로 인자 두개 받아서 해야됌
    	   //orderNo 32        
    	   //phoneNumber 123-123123232322

    	   
           List<OrdersDto> ordersDtoList = orderService.guestOrderList(orderNo, phoneNumber);
               model.addAttribute("ordersDtoList", ordersDtoList);
               return "orders/orders_guest";
       
       } catch (Exception e) {
           e.printStackTrace();
           model.addAttribute("errorMsg", e.getMessage());
           return null;
       }
   }




   






   
   
   //비회원 카트  선택주문
//   guestOrderList

   
   // 비회원 상품에서 주문
//   guestProductOrderSave
   
   //비회원 카트에서 주문(완료)
//   guestCartOrderSave
   
   //상품에서 직접주문
//   memberProductOrderAdd
   
   //cart에서 주문
//   memberCartOrderSave
   
   //cart에서 선택주문 (완료) 
//   memberCartSelectOrderSave
   
   //주문+주문아이템 목록(완료)
//   memberOrderList
   
   
   //주문상세보기(이미 완성됨)
//   memberOrderDetail
   
   
   
   
   
   
   
   
   
   
   
}