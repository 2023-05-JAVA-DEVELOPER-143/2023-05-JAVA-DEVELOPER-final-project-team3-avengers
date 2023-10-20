package com.danaga.repository;


import java.util.Optional;

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
	void test() {
		QueryStringDataDto dto = QueryStringDataDto.builder()
				.orderType(Optional.empty())
				.brand(Optional.of("Brand E"))
				.optionset(Optional.empty())
				.minPrice(0)
				.maxPrice(0)
				.nameKeyword(Optional.empty())
				.category(Optional.empty())
				.build();
		
		System.out.println(queryRepository.findByFilter(dto));
	}

}
