package com.danaga.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
@SpringBootTest
class RefundRepositoryTest {
	@Autowired
	private RefundRepository refundRepository;
	
	@Test
	void testFindRefundByOrderId() {
		
	}

}
