package com.danaga.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.danaga.entity.Member;

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
				.birthday(new Date("1999-09-19"))
				.phoneNo("010-1123-3512")
				.role("Member")
				.build());
	}

}
