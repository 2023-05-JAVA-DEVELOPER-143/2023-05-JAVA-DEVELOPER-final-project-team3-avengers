package com.danaga.repository;

import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.danaga.entity.Cart;
import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CartRepositoryTest {
	
	@Test
	void saveTest() throws Exception{
		Member member = Member.builder().userName("USER11")
				.password("password11")
				.email("agdslfkj@naver.com")
				.name("유저11")
				.nickname("닉네임11")
				.address("주소11")
				.birthday(new Date("2023/10/15"))
				.phoneNo("010-1123-3512")
				.role("Member") .build();
		OptionSet os=new OptionSet(1L,1000,10,3, 2, null, null, null, null, null);
		
		
		
		Cart cart = Cart.builder().qty(1).member(member).optionSet(os).build();
		Assertions.assertThat(cart).isInstanceOf(Cart.class);
	
	}
	
	
	
}
