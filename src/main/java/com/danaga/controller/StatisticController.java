package com.danaga.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.CategoryDto;
import com.danaga.dto.product.ProductDto;
import com.danaga.dto.product.QueryStringDataDto;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Statistic;
import com.danaga.repository.BoardRepository;
import com.danaga.repository.OrderRepository;
import com.danaga.repository.product.OptionSetQueryData;
import com.danaga.repository.product.OptionSetRepository;
import com.danaga.repository.product.ProductRepository;
import com.danaga.service.BoardService;
import com.danaga.service.BoardServiceImpl;
import com.danaga.service.MemberService;
import com.danaga.service.OrderService;
import com.danaga.service.StatisticService;
import com.danaga.service.product.CategoryService;
import com.danaga.service.product.OptionSetService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class StatisticController {
	private final StatisticService statisticService;
	private final MemberService memberService;
	private final OrderService orderService;
	private final OrderRepository orderRepository;
	private final BoardService boardService;
	private final BoardRepository boardRepository;
	private final OptionSetRepository optionSetRepository;
	
	@GetMapping
	public String main(Model model) {
		model.addAttribute("thisMonthList", statisticService.thisMonthStatistic());
		model.addAttribute("todayStat", statisticService.updateAt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
		model.addAttribute("latest7List", changeDateFormat(statisticService.latest7DaysStatistic()));
		//model.addAttribute("yearList", statisticService.latest7DaysStatistic());
		return "admin/admin";
	}
	@GetMapping("/admin_product_insert")
	public String adminProductInsert() {
		return "admin/admin_product_insert";
	}
	@GetMapping("/admin_product_list")
	public String adminProductList(Model model) {
		List<OptionSet> productList = optionSetRepository.findAll();
		model.addAttribute("productList",productList);
		return "admin/admin_product_list";
	}
	@GetMapping("/admin_order_list")
	public String adminOrderList(Model model) {
		model.addAttribute("orderList", orderRepository.findAll());
		return "admin/admin_order_list";
	}
	@Operation(summary = "admin : 회원리스트 출력")
	@GetMapping("/admin_member_list")
	public String adminMemberList(Model model) {
		model.addAttribute("memberList", memberService.getMembers());
		return "admin/admin_member_list";
	}
	@GetMapping("/admin_board_list")
	public String adminBoardList(Model model) {
		model.addAttribute("boardList", boardRepository.findAll());
		return "admin/admin_board_list";
	}
	
	public List<Statistic> changeDateFormat(List<Statistic> statistics) {
		for (Statistic statistic : statistics) {
			statistic.setId(statistic.getId().substring(0,4) + "-" + statistic.getId().substring(4,6) + "-" + statistic.getId().substring(6,8));
		}
		return statistics;
	}
}
