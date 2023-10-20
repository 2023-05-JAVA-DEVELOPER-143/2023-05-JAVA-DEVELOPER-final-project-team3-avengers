package com.danaga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.danaga.entity.Statistic;
import com.danaga.service.StatisticService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/admin")
public class StatisticRestController {
	@Autowired
	private StatisticService statisticService;
//	@Operation(summary = "admin : 전체 통계 호출")
//	@GetMapping
//	public ResponseEntity<List<Statistic>> getStatisticList() {
//		List<Statistic> statistics = statisticService.Statistics();
//		return ResponseEntity.status(HttpStatus.OK).body(statistics);
//	}
	@Operation(summary = "admin : 전체 통계 호출")
	@GetMapping
	public ModelAndView getStatisticList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin");
		modelAndView.addObject(statisticService.Statistics());
		return modelAndView;
	}
	@Operation(summary = "admin : yyyymmdd 통계 업데이트")
	@GetMapping("/{date}")
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
}
