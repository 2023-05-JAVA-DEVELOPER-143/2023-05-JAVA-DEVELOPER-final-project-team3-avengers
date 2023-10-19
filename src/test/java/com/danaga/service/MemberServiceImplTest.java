package com.danaga.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.danaga.entity.Member;

@SpringBootTest
class MemberServiceImplTest {
	
	@Autowired
	MemberService memberService;
	
	@Test
	void join() throws Exception {
		memberService.joinMember(Member.builder()
				.userName("USER11")
				.password("password11")
				.email("agdslfkj@naver.com")
				.name("유저11")
				.nickname("닉네임11")
				.address("주소11")
				.phoneNo("010-1123-3512")
				.role("Member")
				.build());
	}

}
