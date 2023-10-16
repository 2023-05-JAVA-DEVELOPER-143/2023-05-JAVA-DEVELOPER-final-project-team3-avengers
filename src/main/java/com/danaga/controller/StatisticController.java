package com.danaga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.service.StatisticService;

@RestController
@RequestMapping("/admin")
public class StatisticController {
	@Autowired
	private StatisticService statisticService;
	
}
