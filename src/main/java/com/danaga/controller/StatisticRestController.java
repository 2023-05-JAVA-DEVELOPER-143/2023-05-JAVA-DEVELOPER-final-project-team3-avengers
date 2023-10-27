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
import com.danaga.service.BoardService;
import com.danaga.service.MemberService;
import com.danaga.service.OrderService;
import com.danaga.service.StatisticService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/admin_data")
public class StatisticRestController {
	@Autowired
	private StatisticService statisticService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private BoardService boardService;
	
	@Operation(summary = "admin : 전체 통계 호출")
	@GetMapping("/list")
	public ResponseEntity<List<Statistic>> getStatisticListAll() {
		List<Statistic> statistics = statisticService.thisMonthStatistic();
		return ResponseEntity.status(HttpStatus.OK).body(statistics);
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
}
