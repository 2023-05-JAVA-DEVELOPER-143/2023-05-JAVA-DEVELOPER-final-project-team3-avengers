package com.danaga.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.danaga.entity.*;
import com.danaga.service.*;

import io.swagger.v3.oas.annotations.*;
import lombok.*;
@RestController
@RequestMapping("/refund")
public class RefundController {
	//주문목록에서 환불하기 누르면 환불페이지 이동
	
	//주문 상세보기에서 전체 환불하기 누르면 환불페이지 이동
	//환불페이지에서 '환불하시겠습니까 네' 누르면 주문목록으로 이동
	
	//주문페이지 
	
//	@Autowired
//	private OrderRepository orderRepository;
//	@Operation(summary = "refund : 환불페이지")
//	@GetMapping("/refundWrite")
//	public ResponseEntity<List<Statistic>> getStatisticListAll() {
//		List<Statistic> statistics = statisticService.thisMonthStatistic();
//		return ResponseEntity.status(HttpStatus.OK).body(statistics);
//	}
	
	
	//환불내역확인을 먼저 해보자
	
	
	
	
	
}
