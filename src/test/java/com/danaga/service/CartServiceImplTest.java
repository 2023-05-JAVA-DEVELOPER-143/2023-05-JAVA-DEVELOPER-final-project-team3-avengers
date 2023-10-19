package com.danaga.service;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.danaga.entity.Cart;
import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;
@SpringBootTest
public class CartServiceImplTest {
	@Autowired
	CartService cartService;
	MemberServiceImpl memberServiceImpl;
	//@Test
	/*void add() {
		cartService.addCart(Cart.builder().qty(2).member(Member.builder()
				.userName("USER11")
				.password("password11")
				.email("agdslfkj@naver.com")
				.name("유저11")
				.nickname("닉네임11")
				.address("주소11")
				.birthday(new Date("2023/10/15"))
				.phoneNo("010-1123-3512")
				.role("Member")
				.build()).optionSet(new OptionSet()).build());
	}*/
	
	
}
