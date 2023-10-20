package com.danaga.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.test.annotation.Rollback;

import com.danaga.dto.OrdersDto;
import com.danaga.entity.Delivery;
import com.danaga.entity.Orders;
import com.danaga.repository.DeliveryRepository;
import com.danaga.repository.OrderItemRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
class OrderServiceImplTest {

	@Autowired
	OrderService orderService;
	@Autowired
	MemberService memberService;
	@Autowired
	OrderItemRepository orderItemRepository;
	@Autowired
	DeliveryRepository deliveryRepository;
	@Transactional
	@Rollback(false)
	@Test
	void testMemberProductOrderSave()throws Exception {
		
		Orders orders= Orders.builder()
			  .member(memberService.getMemberBy("User1"))
			  .orderItems(orderItemRepository.findAll())
			  .delivery(Delivery.builder().name("name")
					  					  .phoneNumber("010-0000-0000")
					  					  .address("서울시 신림")
					  					  .build())
			  .build();
		OrdersDto ordersDto= OrdersDto.orderDto(orders);
		
		orderService.memberProductOrderSave(ordersDto);
	}

	@Test
	void testMemberSave() {
		
	}
//
//	@Test
//	void testGuestSave() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testOrderList() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testOrderListWithOrderItem() {
//		fail("Not yet implemented");
//	}

}
