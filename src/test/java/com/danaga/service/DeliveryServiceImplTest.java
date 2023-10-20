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
class DeliveryServiceImplTest {
	
	@Autowired
	DeliveryService deliveryService;
	@Autowired
	OrderRepository orderRepository; 
	
	@Test
	//@Disabled
	@Transactional
	@Rollback(false)
	void testSaveDeliveryByOrdersId() {
		Optional<Orders> optionalOrders = orderRepository.findById(1L);
		Orders orders = null;
		if (optionalOrders.isPresent()) {
		    orders = optionalOrders.get();
		    // 이제 'orders' 객체를 사용할 수 있습니다.
		}
		
		
		Delivery delivery = new Delivery(0L, "이름1","폰넘버1", "주소1", orders);
		
		
		Delivery saveDelivery = deliveryService.saveDeliveryByOrdersId(delivery);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+saveDelivery);
	}  
	
	
	
	
	
	
	
	@Test
	//@Disabled
	@Transactional
	@Rollback(false)
	void testFindDeliveryByOrdersId() {
		Delivery findDelivery = deliveryService.findDeliveryByOrdersId(1L);
		System.out.println("##################"+findDelivery.getOrders());
	}

}

























