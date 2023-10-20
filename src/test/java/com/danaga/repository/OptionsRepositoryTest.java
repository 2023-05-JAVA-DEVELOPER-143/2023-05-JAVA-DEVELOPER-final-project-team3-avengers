package com.danaga.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OptionsRepositoryTest {

	@Autowired
	private OptionsRepository repository;
	
	@Test
	void test() {
		System.out.println("ㅁaaaaa");
		System.out.println(repository.findDistinctNameByOptionSet_Product_CategorySets_Category_Id(4L).get(0));
		System.out.println(repository.findDistinctValueByOptionSet_Product_CategorySets_Category_Id(4L).get(0));
	}

}
