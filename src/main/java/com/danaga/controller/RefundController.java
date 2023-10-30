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
import lombok.extern.slf4j.*;
@Slf4j
@Controller
@RequiredArgsConstructor
public class RefundController {

//	@Autowired
//	private OrderRepository orderRepository;
	@Autowired
	private RefundService refundService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberRepository memberRepository;
	
	
	// 주문목록에서 환불하기 페이지로 가기
	@GetMapping("/refund_main_form/{orderId}")
	public String refund_main_form(Model model, @PathVariable Long orderId, HttpServletRequest request)
			throws Exception {
		System.out.println("RefundController의 refund_main_form메소드에서 orderId는 " + orderId);
		OrdersDto ordersDto = orderService.memberOrderDetail(orderId);
		model.addAttribute("ordersDto", ordersDto);// id있어
		model.addAttribute("refund",new RefundDto());
		System.out.println("@@@@@@@@@@@@@@ordersDto= "+ordersDto);

		// 세션에 orderId 저장
		HttpSession session = request.getSession();
		session.setAttribute("orderId", orderId);
		System.out.println("@@@@@@@@@@@@@@session=" + session);
		return "refund/refund_main_form";
	}

	// 환불하기 Insert
	@PostMapping("saveRefund{orderId}")
	public String saveRefund(Model model, @ModelAttribute("refundDto") RefundDto refundDto, @PathVariable Long orderId, HttpServletRequest request)
			throws Exception {

		try {
			System.out.println("@@@@@@@@@@@@@@refundDto = "+refundDto);
			System.out.println("@@@@@@@@@@@@@@orderId = "+orderId);
			
			
			
			log.info("받은 값 : {} ", refundDto );
			RefundResponseDto refundResponseDto = refundService.saveRefund(refundDto, orderId);
			System.out.println("@@@@@@@@@@@@@@refundResponseDto = "+refundResponseDto);
			String loginUser = (String) request.getSession().getAttribute("sUserId");
			System.out.println("@@@@@@@@@@@@@@loginUser = "+loginUser);

			List<OrdersDto> orderDtoList = orderService.memberOrderList(loginUser);
			System.out.println("@@@@@@@@@@@@@@orderDtoList = "+orderDtoList);

			model.addAttribute("orderDtoList", orderDtoList);
			
			Long id= memberService.findIdByUsername(loginUser);
			Member member = memberRepository.findById(id).get();
			model.addAttribute("loginUser", member);
			return "orders/orders1";
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
			return "refund/refund_main_form";

		}

	}
}