package com.danaga.service.product;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.danaga.dto.product.OptionDto;
import com.danaga.dto.product.OptionSaveDto;
import com.danaga.dto.product.OptionSetCreateDto;
import com.danaga.dto.product.OptionSetUpdateDto;
import com.danaga.dto.product.ProductSaveDto;
import com.danaga.dto.product.QueryStringDataDto;
import com.danaga.dto.product.UploadProductDto;
import com.danaga.entity.Options;
import com.danaga.repository.product.OptionSetQueryData;

import jakarta.transaction.Transactional;
@SpringBootTest
class OptionSetServiceImplTest {
	@Autowired
	private OptionSetService service;
	@Test
	@Transactional
	@Rollback(false)
	@Disabled//아직
	void testDeleteProduct() {
		System.out.println(service.deleteProduct(8L, QueryStringDataDto.builder()
				.orderType(OptionSetQueryData.BY_ORDER_COUNT)
				.build()));
	}

	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void testDeleteOptionSet() {
		System.out.println(service.deleteOptionSet(2L, QueryStringDataDto.builder()
				.build()));
	}

	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void testDeleteOption() {
		System.out.println(service.deleteOption(1L));
	}


	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void testUpdateStock() {
		List<OptionDto> list =new ArrayList<OptionDto>();
		list.add(OptionDto.builder().build());
		System.out.println(service.updateStock(OptionSetUpdateDto.builder()
				.id(11L)
				.stock(999)
				.options(list)
				.build()));
	}

	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void testUpdateOrderCount() {
		System.out.println(service.updateOrderCount(11L, 939));
	}

	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void testUpdateViewCount() {
		System.out.println(service.updateViewCount(11L));
	}

	@Test
	@Disabled
	void testDisplayHitProducts() {
		System.out.println(service.displayHitProducts(11L));
	}

	@Test
//	@Disabled
	void testSearchProducts() {
		System.out.println(service.searchProducts(QueryStringDataDto.builder()
				.orderType(OptionSetQueryData.BY_ORDER_COUNT)
				.build()));
	}

	@Test
	@Disabled
	void testShowOtherOptionSets() {
		System.out.println(service.showOtherOptionSets(11L));
	}

	@Test
	@Disabled//쿼리 실행 결과에 이상있음  
	void testShowOptionNameValues() {
		System.out.println(service.showOptionNameValues(13L));
	}

	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void testUpdateOptionSaveDto() {
		System.out.println(service.update(OptionSaveDto.builder()
				.extraPrice(10000)
				.id(30L)
				.name("update")
				.build()));
	}

	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void testUploadProduct() {
		List<OptionSaveDto> list =new ArrayList<OptionSaveDto>();
		list.add(OptionSaveDto.builder()
				.extraPrice(10000000)
				.name("내가만든")
				.value("내가만든")
				.build());
		System.out.println(service.uploadProduct(UploadProductDto.builder()
				.product(ProductSaveDto.builder()
						.brand("custom")
						.descImage("custom")
						.prevDesc("custom")
						.img("custome")
						.price(300000000)
						.name("custom")
						.build())
				.optionSet(OptionSetCreateDto.builder()
						.stock(10000000)
						.build())
				.options(list)
				.build()));
	}

	@Test
	@Disabled
	void testFindById() {
		System.out.println(service.findById(3L));
	}

	@Test
	@Disabled//service미구현
	@Transactional
	@Rollback(false)
	void testUpdateProductUpdateDto() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled//service미구현
	@Transactional
	@Rollback(false)
	void testUpdateRating() {
		fail("Not yet implemented");
	}

}

