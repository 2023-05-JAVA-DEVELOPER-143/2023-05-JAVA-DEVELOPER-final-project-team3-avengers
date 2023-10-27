package com.danaga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.danaga.entity.Statistic;
import com.danaga.service.BoardService;
import com.danaga.service.MemberService;
import com.danaga.service.OrderService;
import com.danaga.service.StatisticService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class StatisticController {
	private final StatisticService statisticService;
	private final MemberService memberService;
	private final OrderService orderService;
	private final BoardService boardService;
	
	@GetMapping
	public String main(Model model) {
		model.addAttribute("statisticList", changeDateFormat(statisticService.latest7DaysStatistic()));
		return "admin/admin";
	}
	@GetMapping("/admin_product_insert")
	public String adminProductInsert() {
		return "admin/admin_product_insert";
	}
	@GetMapping("/admin_product_list")
	public String adminProductList() {
		return "admin/admin_product_list";
	}
	@GetMapping("/admin_order_list")
	public String adminOrderList() {
		return "admin/admin_order_list";
	}
	@Operation(summary = "admin : 회원리스트 출력")
	@GetMapping("/admin_member_list")
	public String adminMemberList(Model model) {
		model.addAttribute("memberList", memberService.getMembers());
		return "admin/admin_member_list";
	}
	@GetMapping("/admin_board_list")
	public String adminBoardList() {
		return "admin/admin_board_list";
	}
	
	public List<Statistic> changeDateFormat(List<Statistic> statistics) {
		for (Statistic statistic : statistics) {
			statistic.setId(statistic.getId().substring(0,4) + "-" + statistic.getId().substring(4,6) + "-" + statistic.getId().substring(6,8));
		}
		return statistics;
	}
}
