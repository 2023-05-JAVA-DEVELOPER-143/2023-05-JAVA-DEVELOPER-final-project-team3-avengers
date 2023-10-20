package com.danaga.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.annotation.*;
import org.springframework.transaction.annotation.*;

import com.danaga.dao.*;
import com.danaga.dto.*;
import com.danaga.entity.*;
import com.danaga.repository.*;
@SpringBootTest
class RefundServiceImplTest {

	@Autowired
	private RefundService refundService;
	
    @Autowired
    private RefundDao refundDao;
	
	@Autowired
	private RefundRepository refundRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	@Test
	//@Disabled
	@Transactional
	@Rollback(false)
	void testFindRefundByOrdersId() {
		Refund findRefund = refundService.findRefundByOrdersId(1L);
		System.out.println("##################" + findRefund);
		System.out.println("##################" + findRefund.getOrders());
	}

//	@Test
//	// @Disabled
//	@Transactional
//	@Rollback(false)
//	void testsaveRefund() {
//		Optional<Orders> optionalOrders = orderRepository.findById(1L);
//		Orders orders = null;
//		if (optionalOrders.isPresent()) {
//			orders = optionalOrders.get();
//			System.out.println("이제 orders 객체를 사용할 수 있습니다");
//		} else {
//			System.out.println("주문이 존재하지 않을 경우");
//
//		}
//		Refund refund = new Refund(0L, "환불사유1", "계좌번호1", orders);
//		Refund findRefund = refundService.saveRefund(refund);
//		System.out.println("##################" + findRefund);
//		System.out.println("##################" + findRefund.getOrders());
//	}
}
