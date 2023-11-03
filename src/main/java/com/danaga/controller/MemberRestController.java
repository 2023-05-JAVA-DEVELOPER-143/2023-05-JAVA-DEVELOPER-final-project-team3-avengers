package com.danaga.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.danaga.dto.MemberLoginDto;
import com.danaga.dto.MemberResponseDto;
import com.danaga.dto.MemberUpdateDto;
import com.danaga.entity.Member;
import com.danaga.exception.ExistedMemberByEmailException;
import com.danaga.exception.ExistedMemberByNicknameException;
import com.danaga.exception.ExistedMemberByPhoneNoException;
import com.danaga.exception.ExistedMemberByUserNameException;
import com.danaga.exception.MemberNotFoundException;
import com.danaga.exception.PasswordMismatchException;
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
	

//	@PostMapping("/login")
//	public ResponseEntity<MemberResponse> member_login_action(@RequestBody MemberResponseDto memberResponseDto, HttpSession session) throws Exception {
//		memberService.login(memberResponseDto.getUserName(), memberResponseDto.getPassword());
//		session.setAttribute("sUserId", memberResponseDto.getUserName());
//		
//		MemberResponse response = new MemberResponse();
//		response.setStatus(MemberResponseStatusCode.LOGIN_SUCCESS);
//		response.setMessage(MemberResponseMessage.LOGIN_SUCCESS);
//		response.setData(memberResponseDto);
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//		return new ResponseEntity<MemberResponse>(response, httpHeaders, HttpStatus.OK);
//	}
	@PostMapping(value = "/login_rest",produces = "application/json;charset=UTF-8")
	public Map member_login_action_rest(@RequestBody MemberLoginDto memberLoginDto, HttpSession session) throws Exception {
		HashMap map = new HashMap<>();
		//MemberResponseDto memberResponseDto = MemberResponseDto.builder().userName(userName).password(password).build();
		int result = 2;
		
		try {
			memberService.login(memberLoginDto.getUserName(), memberLoginDto.getPassword());
		} catch (MemberNotFoundException e) {
			result = 0;
			map.put("result", result);
			map.put("member", memberLoginDto);
			return map;
		} catch (PasswordMismatchException e) {
			result = 1;
			map.put("result", result);
			return map;
		}
		MemberResponseDto loginUser = memberService.getMemberBy(memberLoginDto.getUserName());
		session.setAttribute("sUserId", loginUser.getUserName());
		if (loginUser.getRole().equals("Admin")) {
			session.setAttribute("role", loginUser.getRole());
		}
		
		map.put("result", result);
		return map;
	}
	
//	@LoginCheck
//	@GetMapping("/logout")
//	public ResponseEntity<MemberResponse> member_logout_action(HttpSession session) throws Exception {
//		session.invalidate();
//		MemberResponse response = new MemberResponse();
//		response.setStatus(MemberResponseStatusCode.LOGOUT_USER);
//		response.setMessage(MemberResponseMessage.LOGOUT_USER);
//
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//		return new ResponseEntity<MemberResponse>(response, httpHeaders, HttpStatus.OK);
//	}

//	@PostMapping("/join")
//	public ResponseEntity<MemberResponse> member_join_action(@RequestBody Member member) throws Exception {
//		memberService.joinMember(member);
//		
//		MemberResponse response = new MemberResponse();
//		response.setStatus(MemberResponseStatusCode.CREATED_USER);
//		response.setMessage(MemberResponseMessage.CREATED_USER);
//		response.setData(member);
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//		return new ResponseEntity<MemberResponse>(response, httpHeaders, HttpStatus.CREATED);
//	}
	
	@PostMapping("/join_rest")
	public Map member_join_action(@RequestBody Member member) throws Exception {
		HashMap map = new HashMap<>();
		//	MemberResponseDto memberResponseDto = MemberResponseDto.builder().userName(userName).password(password).build();
			int result = 5;
			
			try {
				memberService.joinMember(member);
				
			} catch (ExistedMemberByUserNameException e) {
				result = 1;
				map.put("result", result);
				map.put("msg", member.getUserName() + "는 사용중인 아이디입니다.");
				return map;
			} catch (ExistedMemberByEmailException e) {
				result = 2;
				map.put("result", result);
				map.put("msg", member.getEmail() + "는 사용중인 이메일입니다.");
				return map;
			} catch (ExistedMemberByPhoneNoException e) {
				result = 3;
				map.put("result", result);
				map.put("msg", member.getPhoneNo() + "는 사용중인 번호입니다.");
				return map;
			} catch (ExistedMemberByNicknameException e) {
				result = 4;
				map.put("result", result);
				map.put("msg", member.getNickname() + "는 사용중인 닉네임입니다.");
				return map;
			}
			map.put("result", result);
			return map;
	}

//	@LoginCheck
//	@PutMapping("/{id}")
//	public ResponseEntity<MemberResponse> member_modify_action(@PathVariable(name = "id") String id,
//			@RequestBody MemberUpdateDto memberUpdateDto) throws Exception {
//		MemberResponseDto updatedMember = memberService.updateMember(memberUpdateDto);
//
//		MemberResponse response = new MemberResponse();
//		response.setStatus(MemberResponseStatusCode.UPDATE_USER);
//		response.setMessage(MemberResponseMessage.UPDATE_USER);
//		response.setData(updatedMember);
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//		return new ResponseEntity<MemberResponse>(response, httpHeaders, HttpStatus.OK);
//	}
	@LoginCheck
	@PutMapping(value = "/modify_action_rest",produces = "application/json;charset=UTF-8")
	public Map member_modify_action(@RequestBody MemberUpdateDto memberUpdateDto,HttpSession session) throws Exception {
		HashMap map = new HashMap<>();
		int result = 2;
		MemberResponseDto updatedMember = new MemberResponseDto();
		try {
			String sUserId = (String) session.getAttribute("sUserId");
			Long sUserLongId = memberService.getMemberBy(sUserId).getId();
			memberUpdateDto.setId(sUserLongId);
			updatedMember = memberService.updateMember(memberUpdateDto);
		} catch (ExistedMemberByNicknameException e) {
			result = 1;
			map.put("result", result);
			map.put("msg", memberUpdateDto.getNickname() + "는 사용중인 닉네임입니다.");
			return map;
		}
		map.put("result", result);
		return map;
	}

//	@GetMapping("/list")
//	public ResponseEntity<MemberResponse> member_list() {
//		List<MemberResponseDto> memberList = memberService.getMembers();
//		MemberResponse response = new MemberResponse();
//		response.setStatus(MemberResponseStatusCode.READ_USER);
//		response.setMessage(MemberResponseMessage.READ_USER);
//		response.setData(memberList);
//
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//		return new ResponseEntity<MemberResponse>(response, httpHeaders, HttpStatus.OK);
//	}
//	@LoginCheck
//	@GetMapping("/{id}")
//	public ResponseEntity<MemberResponse> member_info(@PathVariable(name = "id") String id) throws Exception {
//		MemberResponseDto loginUser = memberService.getMemberBy(id);
//		MemberResponse response = new MemberResponse();
//		response.setStatus(MemberResponseStatusCode.READ_USER);
//		response.setMessage(MemberResponseMessage.READ_USER);
//		response.setData(loginUser);
//		
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//		return new ResponseEntity<MemberResponse>(response, httpHeaders, HttpStatus.OK);
//	}
	@LoginCheck
	@DeleteMapping(value = "/delete_action_rest",produces = "application/json;charset=UTF-8")
	public Map delete_action_rest(@RequestBody MemberLoginDto memberLoginDto,HttpSession session) throws Exception {
		HashMap map = new HashMap<>();
		int result = 2;
		try {
			String sUserId = (String) session.getAttribute("sUserId");
			MemberResponseDto member = memberService.getMemberBy(sUserId);
			if (member.getPassword().equals(memberLoginDto.getPassword())) {
				memberService.deleteMember(member.getUserName());
				session.invalidate();
			} else {
				throw new PasswordMismatchException("비밀번호가 일치하지않습니다.");
			}
		} catch (PasswordMismatchException e) {
			result = 1;
			map.put("result", result);
			map.put("msg", "비밀번호가 일치하지않습니다.");
			return map;
		}
		map.put("result", result);
		return map;
	}
	

//	@ExceptionHandler(value = MemberNotFoundException.class)
//	public ResponseEntity<MemberResponse> member_not_found_exception_handler(MemberNotFoundException e) throws Exception {
//		MemberResponse response = new MemberResponse();
//		response.setStatus(MemberResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER);
//		response.setMessage(MemberResponseMessage.LOGIN_FAIL_NOT_FOUND_USER);
//		response.setData(e.getData());
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//		return new ResponseEntity<MemberResponse>(response, httpHeaders, HttpStatus.OK);
//	}
//
//	@ExceptionHandler(value = PasswordMismatchException.class)
//	public ResponseEntity<MemberResponse> member_password_mismatch_handler(PasswordMismatchException e) throws Exception {
//		MemberResponse response = new MemberResponse();
//		response.setStatus(MemberResponseStatusCode.LOGIN_FAIL_PASSWORD_MISMATCH_USER);
//		response.setMessage(MemberResponseMessage.LOGIN_FAIL_PASSWORD_MISMATCH_USER);
//		response.setData(e.getData());
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//		return new ResponseEntity<MemberResponse>(response, httpHeaders, HttpStatus.OK);
//	}

}
