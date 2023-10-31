package com.danaga.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danaga.dto.MemberResponseDto;
import com.danaga.entity.Member;
import com.danaga.service.MemberService;
import com.danaga.service.product.InterestService;
import com.danaga.service.product.RecentViewService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyProductController {
	private final RecentViewService recentViewService;
	private final InterestService interestService;
	private final MemberService memberService;

	
	// 나의 관심상품 리스트 전체 조회
	@GetMapping("/wishlist")
//	@LoginCheck
	public String myWishList(Model model, HttpSession session) {
		try {
			String username = (String) session.getAttribute("sUserId");
			MemberResponseDto loginUser = memberService.getMemberBy(username);
			// 로그인체크해서 로그인한 멤버 id 찾기
			model.addAttribute("productList", interestService.myInterestingList(loginUser.getId()).getData());//memberId
			// 찾은 id로 그 멤버의 위시리스트 찾아서 wish속성으로 model에 저장하고 member/wishlist url로 포워딩
			// member/wishlist 페이지에서 wish리스트 데이터 받아서 뿌리기
			model.addAttribute("loginUser",loginUser);
			return "product/wishlist";
		} catch (Exception e) {
			// error페이지, 페이지내 에러 메세지 넘겨주기
			e.printStackTrace();
			model.addAttribute("errorMsg", e.getMessage());
			return "redirect:exception.html";
		}
	}

	// 나의 최근본 상품 전체 조회
	@GetMapping("/recentview")
	public String myRecentViews(Model model, HttpSession session) {
		try {
			String username = (String) session.getAttribute("sUserId");
			MemberResponseDto loginUser = memberService.getMemberBy(username);
			// 로그인체크해서 로그인한 멤버 id 찾기
			model.addAttribute("productList", recentViewService.myAllRecentViews(loginUser.getId()).getData());//memberId
			// myViews 속성에 나의 최근 본 상품 리스트 담기
			model.addAttribute("loginUser",loginUser);
			return "product/recent_view";
		} catch (Exception e) {
			// error페이지, 페이지내 에러 메세지 넘겨주기
			e.printStackTrace();
			model.addAttribute("errorMsg", e.getMessage());
			return "redirect:exception.html";
		}
	}
	// 나의 관심상품 리스트 전체 삭제
			@GetMapping("/wishlist_delete")
//			@LoginCheck
			public String deleteAllwishs(HttpSession session, Model model) {
				try {
					String username = (String) session.getAttribute("sUserId");
					MemberResponseDto loginUser = memberService.getMemberBy(username);
					// 로그인체크해서 로그인한 멤버 id 찾기
					interestService.emptyMyInterestingList(loginUser.getId());//memberId
					model.addAttribute("loginUser",loginUser);
					return "product/wishlist";//빈박스 이미지 넘겨주기
				} catch (Exception e) {
					// error페이지, 페이지내 에러 메세지 넘겨주기
					e.printStackTrace();
					model.addAttribute("errorMsg", e.getMessage());
					return "redirect:exception.html";
				}
			}
			// 나의 최근본 상품 전체 삭제
			@GetMapping("/recentview_delete")
//			@LoginCheck
			public String deleteViewRecords(HttpSession session, Model model) {
				try {
					String username = (String) session.getAttribute("sUserId");
					MemberResponseDto loginUser = memberService.getMemberBy(username);
					// 로그인체크해서 로그인한 멤버 id 찾기
					recentViewService.removeMyRecentViews(loginUser.getId());//memberId
					model.addAttribute("loginUser",loginUser);
					return "product/recent_view";//빈박스 이미지 넘겨주기
				} catch (Exception e) {
					// error페이지, 페이지내 에러 메세지 넘겨주기
					e.printStackTrace();
					model.addAttribute("errorMsg", e.getMessage());
					return "redirect:exception.html";
				}
			}

}
