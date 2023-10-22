package com.danaga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.danaga.entity.Member;
import com.danaga.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Autowired
	private MemberService memberService;

	@GetMapping("/login")
	public String member_login_form() {

		return "";
	}

//	@PostMapping("/login_action")
//	public String post_login_action(@ModelAttribute("fmember") Member member,Model model,HttpSession session) throws Exception {
//		try {
//		if (memberService.login(member.getUserName(), member.getPassword())) {
//			session.setAttribute("sMemberId", Member.getUserName());
//			return "redirect:main";
//		}
//		}catch (Exception e) {
//			e.printStackTrace();
//			model.addAttribute("msg1",e.getMessage());
//			return "login";
//		}catch (Exception e) {
//			e.printStackTrace();
//			model.addAttribute("msg2",e.getMessage());
//			return "login";
//		}
//		return "";
//	}

	@GetMapping("/join")
	public String member_join_form() {
		
		return "";
	}

	@GetMapping("/modify")
	public String member_modify_form() {
		
		return "";
	}

	@GetMapping("/info")
	public String member_info_form() {
		
		return "";
	}
}
