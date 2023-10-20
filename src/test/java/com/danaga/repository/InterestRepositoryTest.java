package com.danaga.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class InterestRepositoryTest {

	@Autowired
	private InterestRepository repository;
	@Test
	void test() {
		System.out.println(repository.existsByMemberIdAndOptionSetId(2L, 4L));
	}

}
