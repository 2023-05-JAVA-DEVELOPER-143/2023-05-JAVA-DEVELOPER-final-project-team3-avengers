package com.danaga.service.product;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.danaga.dto.product.InterestDto;

import jakarta.transaction.Transactional;
@SpringBootTest
class InterestServiceImplTest {
	@Autowired
	private InterestService service;
	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void testClickHeart() {
		System.out.println(service.clickHeart(InterestDto.builder()
				.memberId(1L)
				.optionSetId(1L)
				.build()));
	}

	@Test
	@Disabled
	void testMyInterestingList() {
		System.out.println(service.myInterestingList(2L));
	}

	@Test
	@Transactional
	@Rollback(false)
	void testEmptyMyInterestingList() {
		service.emptyMyInterestingList(2L);
	}

}

