package com.danaga.controller;

import java.util.List;

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

import com.danaga.dto.AdminOrderUpdate;
import com.danaga.dto.AdminProductInsertDto;
import com.danaga.entity.Options;
import com.danaga.entity.Statistic;
import com.danaga.repository.BoardRepository;
import com.danaga.repository.MemberRepository;
import com.danaga.repository.product.OptionSetRepository;
import com.danaga.service.MemberService;
import com.danaga.service.StatisticService;
import com.danaga.service.product.OptionSetService;

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
	private BoardRepository boardRepository;
	@Autowired
	private OptionSetService optionSetService;
	@Autowired
	private OptionSetRepository optionSetRepository;

	@GetMapping("/daily_update")
	public ResponseEntity<?> dailyUpdate() {
		statisticService.updateLatest7Days();
		return new ResponseEntity<>("Statistic updated successfully", HttpStatus.OK);
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

	@PostMapping("/product")
	public ResponseEntity<?> createProduct(@RequestBody AdminProductInsertDto adminProductInsertDto) {
		try {
			statisticService.createProduct(adminProductInsertDto);
			return new ResponseEntity<>("Product created successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error creating product", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		try {
			List<Options> optionList = optionSetRepository.findById(id).get().getOptions();
			for (Options options : optionList) {
				optionSetService.deleteOption(options.getId());
			}
			optionSetService.deleteOptionSet(id);
			return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error deleting product", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/order/{id}")
	public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody AdminOrderUpdate adminOrderUpdate) {
		try {
			statisticService.updateOrderStatement(adminOrderUpdate.getOrderId(), adminOrderUpdate.getStatement());
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
