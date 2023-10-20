package com.danaga.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class CartRepositoryTest {
	@Autowired
	private  CartRepository cr;
	
	
	@Test
	void findTest() {
		cr.findByMemberId(1L);
	}
	
	
	
}
