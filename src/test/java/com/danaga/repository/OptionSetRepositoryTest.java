package com.danaga.repository;


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
	
//	@Test
//	void test() {
//		QueryStringDataDto dto = QueryStringDataDto.builder()
//				.brand("Brand E")
//				.build();
//		
//		System.out.println(queryRepository.findByFilter(dto));
//	}

}
