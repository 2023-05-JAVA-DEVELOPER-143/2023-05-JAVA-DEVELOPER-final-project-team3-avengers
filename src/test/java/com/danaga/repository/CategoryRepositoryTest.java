package com.danaga.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class CategoryRepositoryTest {
	@Autowired
	private CategoryRepository repository;
	@Test
	void test() {
		System.out.println(repository.findChildTypesByParent_Id(1L));
	}

}
