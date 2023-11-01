package com.danaga.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.danaga.dto.AdminOrderUpdate;
import com.danaga.entity.Member;
import com.danaga.entity.Statistic;
import com.danaga.repository.BoardRepository;
import com.danaga.repository.MemberRepository;
import com.danaga.service.BoardService;
import com.danaga.service.BoardServiceImpl;
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
	private MemberRepository memberRepository;
	@Autowired
	private OrderService orderService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private BoardRepository boardRepository;
	
	@GetMapping("/daily_update")
	public Map<String, Object> dailyUpdate() {
		statisticService.updateAt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		Map<String, Object> updatedData = new HashMap<>();
		updatedData.put("updatedMonthlyStat", statisticService.thisMonthStatistic());
		updatedData.put("updated7dStat", statisticService.latest7DaysStatistic());
		return updatedData;
	}
	
	@GetMapping("/option_year")
	public List<Statistic> optionYear() {
		List<Statistic> list = statisticService.yearlyStatistic("2023");
		return list;
	}
	
	@GetMapping("/option_days")
	public List<Statistic> optionDays() {
		List<Statistic> list = statisticService.latest7DaysStatistic();
		return list;
	}
	
	@PutMapping("/order/{id}")
	public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody AdminOrderUpdate adminOrderUpdate) {
		 try {
			 	statisticService.updateOrderStatement(adminOrderUpdate.getOrderId(),adminOrderUpdate.getStatement());
	            return new ResponseEntity<>("Order statement changed successfully", HttpStatus.OK);
	        } catch (Exception e) {
	        	e.printStackTrace();
	            return new ResponseEntity<>("Error changing order statement", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	}
	
	@DeleteMapping("/member/{id}")
	public ResponseEntity<?> deleteMember(@PathVariable Long id) {
		 try {
	            memberService.deleteMember(memberRepository.findById(id).get().getUserName());
	            return new ResponseEntity<>("Member deleted successfully", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting member", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	}
	
	@DeleteMapping("/board/{id}")
	public ResponseEntity<?> deleteBoard(@PathVariable Long id) {
		 try {
			 boardRepository.deleteById(id);
	            return new ResponseEntity<>("Board deleted successfully", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting member", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	}
	
}
