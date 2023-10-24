package com.danaga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danaga.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/login_form")
	public String member_login_form() {
		return "member/member_login_form";
	}
	
	@GetMapping("/join_form")
	public String member_join_form() {
		return "member/member_join_form";
	}
	@GetMapping("/find_password_form")
	public String member_findpassword_form() {
		return "member/member_find_password_form";
	}
	@GetMapping("/info_form")
	public String member_info_form() {
		return "member/member_info_form";
	}
	
}
