package com.danaga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.repository.StatisticRepository;

@Service
public class StatisticServiceImpl implements StatisticService{
	@Autowired
	private StatisticRepository statisticRepository;
	
	
	
}
