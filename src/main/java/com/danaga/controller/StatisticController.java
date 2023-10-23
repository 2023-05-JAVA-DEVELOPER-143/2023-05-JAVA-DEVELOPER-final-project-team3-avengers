package com.danaga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.danaga.service.StatisticService;


@Controller
@RequestMapping("/admin")
public class StatisticController {
	@Autowired
	private StatisticService statisticService;
	@GetMapping("/admin_product_list")
	public ModelAndView adminProductList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/admin_product_list");
		return modelAndView;
	}
	@GetMapping("/admin_member_list")
	public ModelAndView adminMemberList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/admin_member_list");
		return modelAndView;
	}
	@GetMapping("/admin_board_list")
	public ModelAndView adminBoardList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/admin_board_list");
		return modelAndView;
	}
	
}
