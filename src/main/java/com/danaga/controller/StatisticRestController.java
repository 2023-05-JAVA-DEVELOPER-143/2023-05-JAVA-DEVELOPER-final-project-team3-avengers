package com.danaga.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.danaga.entity.Member;
import com.danaga.entity.Statistic;
import com.danaga.service.MemberService;
import com.danaga.service.StatisticService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/admin")
public class StatisticRestController {
	@Autowired
	private StatisticService statisticService;
	
	@Autowired
	private MemberService memberService;
	
	@Operation(summary = "admin : 전체 통계 호출")
	@GetMapping("/list")
	public ResponseEntity<List<Statistic>> getStatisticListAll() {
		List<Statistic> statistics = statisticService.thisMonthStatistic();
		return ResponseEntity.status(HttpStatus.OK).body(statistics);
	}
	@Operation(summary = "admin : 전체 통계 호출")
	@GetMapping
	public ModelAndView getStatisticList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/admin");
		modelAndView.addObject("statisticList",statisticService.Statistics());
		return modelAndView;
	}
	@Operation(summary = "admin : ")
	@GetMapping("/daily")
	public ResponseEntity<List<Statistic>> getTodayList() {
		String today = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		statisticService.updateAt(today);
		List<Statistic> statistics = statisticService.updateAll();
		return ResponseEntity.status(HttpStatus.OK).body(statistics);
	}
	@Operation(summary = "admin : yyyymmdd 통계 업데이트")
	@PostMapping("/{date}")
	public ResponseEntity<List<Statistic>> manualUpdate(@PathVariable(value = "date") String findDate) {
		statisticService.updateAt(findDate);
		List<Statistic> statistics = statisticService.Statistics();
		return ResponseEntity.status(HttpStatus.OK).body(statistics);
	}
	@Operation(summary = "admin : 과거 통계 업데이트")
	@GetMapping("/update")
	public ResponseEntity<List<Statistic>> entireUpdate() {
		List<Statistic> statistics = statisticService.updateAll();
		return ResponseEntity.status(HttpStatus.OK).body(statistics);
	}
	@Operation(summary = "admin : YYYYMM월 데이터")
	@GetMapping("/month/{month}")
	public ResponseEntity<List<Statistic>> searchMonth(@PathVariable(value = "month") String month) {
		List<Statistic> statistics = statisticService.monthlyStatistic(month);
		return ResponseEntity.status(HttpStatus.OK).body(statistics);
	}
	@Operation(summary = "admin : 이번달 데이터")
	@GetMapping("/this")
	public ResponseEntity<List<Statistic>> thisMonth() {
		List<Statistic> statistics = statisticService.thisMonthStatistic();
		return ResponseEntity.status(HttpStatus.OK).body(statistics);
	}
	@GetMapping("/admin_product_insert")
	public ModelAndView adminProductInsert() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/admin_product_insert");
		return modelAndView;
	}
	@GetMapping("/admin_product_list")
	public ModelAndView adminProductList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/admin_product_list");
		return modelAndView;
	}
	@Operation(summary = "admin : 회원리스트 출력")
	@GetMapping("/admin_member_list")
	public ModelAndView adminMemberList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/admin_member_list");
		return modelAndView;
	}
	@GetMapping("/admin_board_list")
	public ModelAndView adminBoardList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/admin_board_list");
		return modelAndView;
	}
	
}
