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
	//@Disabled
	void testMemberProductOrderSave()throws Exception {
		
		System.out.println("0000000000000000000000000000000000000");
		
		OrdersDto ordersDto = OrdersDto.builder()
									   .delivaryAddress("dd")
									   .delivaryName("dd")
									   .delivaryPhoneNumber("11")
									   .orderItem_qty(6)
									   .userName("User3")
									   .build();
		
		orderService.memberProductOrderSave(ordersDto, 6L,"주문자이름","010-324-2323");
	}
	
	
	
//	@Transactional
//	@Rollback(false)
//	@Test
//	@Disabled
//	void testMemberCartOrderSave()throws Exception {
//
//		OrdersDto ordersDto = OrdersDto.builder()
//				   .delivaryAddress("dd")
//				   .delivaryName("dd")
//				   .delivaryPhoneNumber("11")
//				   .optionSetId(2L)
//				   .orderItem_qty(3)
//				   .userName("User1")
//				   .build();
//		
//		orderService.memberCartOrderSave(ordersDto);
//	}
//	@Transactional
//	@Rollback(false)
//	//@Disabled
//	@Test
//	void testMemberOrderList() {
//		
//		System.out.println("-----------------------------"+orderService.memberOrderList("User1"));
//		
//	}
//
//	@Transactional
//	@Rollback(false)
//	@Test
//	@Disabled
//	void testMemberCartSelectOrderSave()throws Exception {
//		String[] cart_item_noStr_array = {"102"};
//		OrdersDto ordersDto = OrdersDto.builder()
//				   .delivaryAddress("ff")
//				   .delivaryName("ff")
//				   .delivaryPhoneNumber("22")
//				   .optionSetId(20L)
//				   .orderItem_qty(3)
//				   .userName("User2")
//				   .build();
//	 System.out.println("********************************"+orderService.memberCartSelectOrderSave(ordersDto, cart_item_noStr_array));	
//	}
//	@Test
//	@Disabled
//	void testmemberOrderDetail()throws Exception {
//		System.out.println("777777777777777777777777"+orderService.memberOrderDetail(3L)); 
//	}
//	@Test
//	@Disabled
//	void testUpdateStatementByNormalOrder() {
//		
//		System.out.println("44444444444444444444"+orderService.updateStatementByNormalOrder(5L));
//		orderService.updateStatementByNormalOrder(10L);
//		orderService.updateStatementByNormalOrder(24L);
//		orderService.updateStatementByNormalOrder(16L);
//		orderService.updateStatementByNormalOrder(18L);
//	}
//	@Test
//	@Disabled
//	void testUpdateStatementByCancleOrder() {
//		
//		orderService.updateStatementByCancleOrder(13L);
//		orderService.updateStatementByCancleOrder(19L);
//	}
//	@Test
//	@Disabled
//	void testUpdateStatementByRefundOrder() {
//		
//		orderService.updateStatementByRefundOrder(10L);
//	}
//	
	
	
}
