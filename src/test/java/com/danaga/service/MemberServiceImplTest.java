package com.danaga.service;


import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.danaga.dto.MemberInsertGuestDto;
import com.danaga.dto.MemberUpdateDto;
import com.danaga.entity.Member;


@SpringBootTest
class MemberServiceImplTest {
	
	@Autowired
	MemberService memberService;
	
	@Test
	@Disabled
	void joinMember() throws Exception {
		memberService.joinMember(Member.builder()
				.userName("USER11")
				.password("password11")
				.email("agdslfkj@naver.com")
				.name("유저11")
				.nickname("닉네임11")
				.address("주소11")
				.birthday(new Date("2023/10/15"))
				.phoneNo("010-1123-3512")
				.role("Member")
				.build());
	}
	@Test
	@Disabled
	void joinGuest() throws Exception {
		memberService.joinGuest(MemberInsertGuestDto.builder()
				.name("유저11")
				.phoneNo("010-1123-3512")
				.role("Guest")
				.build());
	}
	
	@Test
	@Disabled
	void update() throws Exception {
		memberService.updateMember(MemberUpdateDto.builder()
				.id(1L)
				.userName("USER11")
				.password("password11")
				.email("agdslfkj@google.com")
				.nickname("닉네임11")
				.address("주소11")
				.phoneNo("010-1123-3512")
				.build());
	}
	@Test
	@Disabled
	void delete() throws Exception {
		
	}
	@Test
	//@Disabled
	void isDuplicate() throws Exception {
		memberService.isDuplicate("010-1123-3512");
	}
	@Test
	void login(String userName, String password) throws Exception {
		
	}

}
