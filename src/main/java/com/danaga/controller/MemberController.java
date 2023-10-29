package com.danaga.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.danaga.dto.MemberLoginDto;
import com.danaga.dto.MemberResponseDto;
import com.danaga.dto.MemberUpdateDto;
import com.danaga.entity.Member;
import com.danaga.exception.ExistedMemberException;
import com.danaga.exception.MemberNotFoundException;
import com.danaga.exception.PasswordMismatchException;
import com.danaga.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	@GetMapping("/member_login_form")
	public String member_login_form() {
		return "member/member_login_form";
	}

	@LoginCheck
	@PostMapping("/member_login_action")
	public String member_login_action(@ModelAttribute("fuser") MemberLoginDto member, Model model, HttpSession session)
			throws Exception {
		String forwardPath = "";
		try {
			memberService.login(member.getUserName(), member.getPassword());
			session.setAttribute("sUserId", member.getUserName());
			forwardPath = "redirect:/index";
		} catch (MemberNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("msg1", e.getMessage());
			forwardPath = "member/member_login_form";
		} catch (PasswordMismatchException e) {
			e.printStackTrace();
			model.addAttribute("msg2", e.getMessage());
			forwardPath = "member/member_login_form";
		}
		return forwardPath;
	}

	@GetMapping("/member_join_form")
	public String member_join_form() {
		return "member/member_join_form";
	}

	@PostMapping("/member_join_action")
	public String member_write_action(@ModelAttribute("fuser") Member member, Model model, 
			@RequestParam("birthday") @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday) throws Exception {
		String forward_path = "";
		try {
			System.out.println("#################################"+birthday);
			member.setBirthday(birthday);
			memberService.joinMember(member);
			forward_path = "redirect:member_login_form";
		} catch (ExistedMemberException e) {
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("fuser", member);
			forward_path = "member/member_join_form";
		}
		return forward_path;
	}

	@GetMapping("/member_find_password_form")
	public String member_findpassword_form() {
		return "member/member_find_password_form";
	}

	@LoginCheck
	@GetMapping("/member_info_form")
	public String member_info_form(HttpServletRequest request, Model model) throws Exception {
		/************** login check **************/
		/****************************************/
		String loginUser = (String) request.getSession().getAttribute("sUserId");
		MemberResponseDto member = memberService.getMemberBy(loginUser);
		model.addAttribute("loginUser", member);
		return "member/member_info_form";
	}

	@LoginCheck
	@PostMapping("/member_modify_action")
	public String member_modify_action(@ModelAttribute MemberUpdateDto member, HttpServletRequest request) throws Exception {
		/************** login check **************/
		/****************************************/
		
		String sUserId = (String) request.getSession().getAttribute("sUserId");
		Long sUserLongId = memberService.getMemberBy(sUserId).getId();
		member.setId(sUserLongId);

		memberService.updateMember(member);
		return "redirect:member_info_form";
	}

	@LoginCheck
	@GetMapping("/member_logout_action")
	public String user_logout_action(HttpServletRequest request) {

		/************** login check **************/
		/****************************************/
		request.getSession(false).invalidate();

		return "redirect:index";
	}

	@LoginCheck
	@PostMapping("/member_remove_action")
	public String member_remove_action(HttpServletRequest request) throws Exception {
		/************** login check **************/
		/****************************************/
		String sUserId = (String) request.getSession().getAttribute("sUserId");
		memberService.deleteMember(sUserId);
		request.getSession().invalidate();
		return "redirect:index";
	}

	@GetMapping({ "/member_join_action", "/member_login_action", "/member_modify_action", "/member_remove_action" })
	public String user_get() {
		String forwardPath = "redirect:/index";
		return forwardPath;
	}

}
