package com.danaga.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.test.annotation.Rollback;

import com.danaga.config.OrderStateMsg;
import com.danaga.dto.DeliveryDto;
import com.danaga.dto.OrdersDto;
import com.danaga.dto.OrdersProductDto;
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
		
		OrdersProductDto ordersDto = OrdersProductDto.builder()
									   .delivaryAddress("dd")
									   .delivaryName("dd")
									   .delivaryPhoneNumber("221")
									   .optionSetId(2L)
									   .orderItem_qty(3)
									   .build();
		
		orderService.memberProductOrderSave("User5",ordersDto);
	}
	@Transactional
	@Rollback(false)
	@Test
	@Disabled
	void testMemberCartOrderSave()throws Exception {

		
		DeliveryDto deliveryDto = DeliveryDto.builder()
									.address("ff")
									.name("ff")
									.phoneNumber("010-3023-323232")
									.build();
		
		
		orderService.memberCartOrderSave("User2",deliveryDto);
	}
	@Transactional
	@Rollback(false)
	@Disabled
	@Test
	void testMemberOrderList()throws Exception {
		
		System.out.println("-----------------------------"+orderService.memberOrderList("User5"));
		
	}

	@Transactional
	@Rollback(false)
	@Test
	@Disabled
	void testMemberCartSelectOrderSave()throws Exception {
		DeliveryDto deliveryDto = DeliveryDto.builder()
				.address("ff")
				.name("ff")
				.phoneNumber("010-3023-323232")
				.build();
		
		
		List<Long> optionSetIdArray = new ArrayList<>();
		optionSetIdArray.add(1L);
		optionSetIdArray.add(2L);
		optionSetIdArray.add(3L);
		optionSetIdArray.add(4L);
		optionSetIdArray.add(5L);
		
	 System.out.println("********************************"+orderService.memberCartSelectOrderSave("User1",deliveryDto,optionSetIdArray));	
	}
	@Test
	@Disabled
	void testmemberOrderDetail()throws Exception {
		System.out.println("777777777777777777777777"+orderService.memberOrderDetail(5L)); 
	}
	@Test
	//@Disabled
	void testUpdateStatementByNormalOrder() {
		
		System.out.println("44444444444444444444"+orderService.updateStatementByNormalOrder(5L));
		orderService.updateStatementByNormalOrder(1L);
		orderService.updateStatementByNormalOrder(2L);
		orderService.updateStatementByNormalOrder(3L);
	}
	@Test
	//@Disabled
	void testUpdateStatementByCancleOrder() {
		
		orderService.updateStatementByCancleOrder(6L);
		orderService.updateStatementByCancleOrder(19L);
	}
	@Test
	//@Disabled
	void testUpdateStatementByRefundOrder() {
		
		orderService.updateStatementByRefundOrder(1L);
		orderService.updateStatementByRefundOrder(2L);
		orderService.updateStatementByRefundOrder(3L);
	}
	@Test
	//@Disabled
	void testUpdateStatementByResetOrder() {
		
		orderService.updateStatementByResetOrder(19L);
	}
	


}
