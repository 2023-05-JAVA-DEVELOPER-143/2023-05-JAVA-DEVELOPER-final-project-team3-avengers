package com.danaga.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.annotation.*;
import org.springframework.transaction.annotation.*;

import com.danaga.dto.*;
import com.danaga.entity.*;
@SpringBootTest
class DeliveryServiceTest {

	@Autowired
	DeliveryService deliveryService;
	
	@Test
	//@Disabled
	@Transactional
	@Rollback(false)
	void testSaveDelivery() {
		DeliveryDto deliveryDto = new DeliveryDto("김덕배","01012345678", "아이티윌");
		Delivery delivery = deliveryService.saveDelivery(deliveryDto);
		System.out.println("@@@@@@@@@@@@@@"+delivery);
		//여기서 ResponseDto를 entity로
	}

}
