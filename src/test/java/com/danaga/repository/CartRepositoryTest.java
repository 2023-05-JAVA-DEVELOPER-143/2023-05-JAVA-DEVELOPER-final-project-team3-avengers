package com.danaga.repository;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.danaga.entity.Cart;
import com.danaga.entity.Member;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CartRepositoryTest {
	
	
	@Test
	void saveTest() {
		Member member = Member.builder().userName("USER11")
				.password("password11")
				.email("agdslfkj@naver.com")
				.name("유저11")
				.nickname("닉네임11")
				.address("주소11")
				.birthday(new Date("2023/10/15"))
				.phoneNo("010-1123-3512")
				.role("Member") .build();
		Cart cart = Cart.builder().qty(1).member(member).optionSet(null).build();
	}
	
	
	
}
