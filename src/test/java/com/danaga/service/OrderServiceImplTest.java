package com.danaga.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.test.annotation.Rollback;

import com.danaga.dto.OrdersDto;
import com.danaga.entity.Cart;
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
	@Autowired
	CartService cartService;
	@Transactional
	@Rollback(false)
	@Test
	@Disabled
	void testMemberProductOrderSave()throws Exception {
		
		System.out.println("0000000000000000000000000000000000000");
		
//		Orders orders= Orders.builder()
//			  .member(memberService.getMemberBy("User1"))
//			  .orderItems(orderItemRepository.findAll())
//			  .delivery(Delivery.builder().name("name")
//					  					  .phoneNumber("010-0000-0000")
//					  					  .address("서울시 신림")
//					  					  .build())
//			  .build();
//		System.out.println("******************************************"+orders.getOrderItems());
//		OrdersDto ordersDto= OrdersDto.builder().build();
//		
		OrdersDto ordersDto = OrdersDto.builder()
									   .delivaryAddress("dd")
									   .delivaryName("dd")
									   .delivaryPhoneNumber("11")
									   .optionSetId(2L)
									   .orderItem_qty(3)
									   .userName("User1")
									   .build();
//			
		orderService.memberProductOrderSave(ordersDto,"주문자이름","010-324-2323");
	}
	@Transactional
	@Rollback(false)
	@Test
	@Disabled
	void testMemberCartOrderSave()throws Exception {
		
//		Orders orders= Orders.builder()
//				  .member(memberService.getMemberBy("User2"))
//				  .orderItems(orderItemRepository.findAll())
//				  .delivery(Delivery.builder().name("testname")
//						  					  .phoneNumber("010-1111-1111")
//						  					  .address("서울시 개봉")
//						  					  .build())
//				  .build();
		
		OrdersDto ordersDto = OrdersDto.builder()
				   .delivaryAddress("dd")
				   .delivaryName("dd")
				   .delivaryPhoneNumber("11")
				   .optionSetId(2L)
				   .orderItem_qty(3)
				   .userName("User1")
				   .build();
//		OrdersDto ordersDto = OrdersDto.orderDto(orders);
		
		orderService.memberCartOrderSave(ordersDto);
	}

	@Test
	
	void testMemberOrderList() {
		
		
		
		System.out.println("-----------------------------"+orderService.memberOrderList("User1"));
		
	}

	@Test
	void testMemberOrderListWithOrderItem() {
		
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++"+orderService.memberOrderListWithOrderItem("User1")); 
	}
//
//	@Test
//	void testOrderListWithOrderItem() {
//		fail("Not yet implemented");
//	}

}
