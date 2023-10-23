package com.danaga.controller;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.dto.MemberUpdateDto;
import com.danaga.entity.Member;
import com.danaga.memberResponse.MemberResponse;
import com.danaga.memberResponse.MemberResponseMessage;
import com.danaga.memberResponse.MemberResponseStatusCode;
import com.danaga.service.MemberService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberRestController {
	@Autowired
	private MemberService memberService;

	@PostMapping("/login")
	public ResponseEntity<MemberResponse> member_login_action(@RequestBody Member member, HttpSession session) throws Exception {
		memberService.login(member.getUserName(), member.getPassword());
		session.setAttribute("sUserId", member.getUserName());

		MemberResponse response = new MemberResponse();
		response.setStatus(MemberResponseStatusCode.LOGIN_SUCCESS);
		response.setMessage(MemberResponseMessage.LOGIN_SUCCESS);
		response.setData(member);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<MemberResponse>(response, httpHeaders, HttpStatus.OK);

	}

	@PostMapping("/join")
	public ResponseEntity<MemberResponse> member_join_action(@RequestBody Member member) throws Exception {
		memberService.joinMember(member);

		MemberResponse response = new MemberResponse();
		response.setStatus(MemberResponseStatusCode.CREATED_USER);
		response.setMessage(MemberResponseMessage.CREATED_USER);
		response.setData(member);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<MemberResponse>(response, httpHeaders, HttpStatus.CREATED);
	}

	@LoginCheck
	@PutMapping("/{id}")
	public ResponseEntity<MemberResponse> member_modify_action(@PathVariable(name = "id") String id,
			@RequestBody MemberUpdateDto memberUpdateDto) throws Exception {
		MemberUpdateDto updatedMember = memberService.updateMember(memberUpdateDto);

		MemberResponse response = new MemberResponse();
		response.setStatus(MemberResponseStatusCode.UPDATE_USER);
		response.setMessage(MemberResponseMessage.UPDATE_USER);
		response.setData(updatedMember);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<MemberResponse>(response, httpHeaders, HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<MemberResponse> member_list() {
		List<Member> memberList = memberService.getMembers();
		MemberResponse response = new MemberResponse();
		response.setStatus(MemberResponseStatusCode.READ_USER);
		response.setMessage(MemberResponseMessage.READ_USER);
		response.setData(memberList);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<MemberResponse>(response, httpHeaders, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MemberResponse> member_info(@PathVariable(name = "id") String id) throws Exception {
		Member loginUser = memberService.getMemberBy(id);
		MemberResponse response = new MemberResponse();
		response.setStatus(MemberResponseStatusCode.READ_USER);
		response.setMessage(MemberResponseMessage.READ_USER);
		response.setData(loginUser);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<MemberResponse>(response, httpHeaders, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MemberResponse> member_delete(@PathVariable(name = "id") String id, HttpSession session) throws Exception {
		memberService.deleteMember(id);
		session.invalidate();

		MemberResponse response = new MemberResponse();
		response.setStatus(MemberResponseStatusCode.DELETE_USER);
		response.setMessage(MemberResponseMessage.DELETE_USER);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<MemberResponse>(response, httpHeaders, HttpStatus.OK);
	}
}
