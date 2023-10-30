package com.danaga.repository.product;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.danaga.dto.product.QueryStringDataDto;
@SpringBootTest
class OptionSetQueryRepositoryTest {

	@Autowired
	OptionSetQueryRepository repository;
	@Test
	void test() {
		System.out.println(repository.findForMemberByFilter(
				QueryStringDataDto.builder()
				.orderType(OptionSetQueryData.BY_TOTAL_PRICE)
				.build(),"User1"));
	}

}
