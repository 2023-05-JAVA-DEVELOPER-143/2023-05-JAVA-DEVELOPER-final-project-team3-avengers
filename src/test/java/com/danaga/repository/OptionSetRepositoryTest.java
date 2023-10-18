package com.danaga.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class OptionSetRepositoryTest {

	@Autowired
	OptionSetRepository optionSetRepository;
	@Test
	void test() {
		optionSetRepository.findById(1L);
	}

}
