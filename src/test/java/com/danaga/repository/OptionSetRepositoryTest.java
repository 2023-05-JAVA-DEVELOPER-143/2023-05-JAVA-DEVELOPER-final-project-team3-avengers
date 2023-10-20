package com.danaga.repository;


import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.danaga.dto.QueryStringDataDto;
@SpringBootTest
class OptionSetRepositoryTest {

	@Autowired
	OptionSetRepository optionSetRepository;
	@Autowired
	OptionSetQueryRepository queryRepository;
	
	@Test
	@Disabled
	void test() {
		QueryStringDataDto dto = QueryStringDataDto.builder()
				.orderType(Optional.empty())
				.brand(Optional.of("Brand E"))
				.optionset(Optional.empty())
				.nameKeyword(Optional.empty())
				.category(Optional.empty())
				.build();
		
		System.out.println(queryRepository.findByFilter(dto));
	}
	
	@Test
	void test2() {
		System.out.println(optionSetRepository.findByInterests_MemberId(1L));
	}

}
