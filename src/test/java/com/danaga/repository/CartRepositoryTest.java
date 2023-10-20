package com.danaga.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.danaga.entity.Cart;

@SpringBootTest
public class CartRepositoryTest {
	@Autowired
	private CartRepository cr;

	@Test
	@Disabled
	void 유저카트제품한개() {
		// cr.findByMemberId(1L);
		Cart cart = cr.findByOptionSetIdAndMemberId(2L, 1L);

		System.out.println(">>>>>>>>>>>>>>>>>>>>" + cart.getId());
	}

	@Test
	void 유저카트리스트() {
		List<Cart> carts = cr.findByMemberId(1L);

		Assertions.assertThat(carts.get(0).getId()).isEqualTo(3L);
		Assertions.assertThat(carts.get(1).getId()).isEqualTo(4L);
		Assertions.assertThat(carts.get(2).getId()).isEqualTo(5L);

		System.out.println(">>>>>>>>>>>>>>>>>>>>" + carts.get(0).getId());
		System.out.println(">>>>>>>>>>>>>>>>>>>>" + carts.get(1).getId());
		System.out.println(">>>>>>>>>>>>>>>>>>>>" + carts.get(2).getId());
	}

	@Test
	void 유저장바구니수량() {
		int count = cr.countByMemberId(1L);
		System.out.println("장바구니 수량 >>>>>>>>>>>>>" + count);
		Assertions.assertThat(count).isEqualTo(4);
	}
}
