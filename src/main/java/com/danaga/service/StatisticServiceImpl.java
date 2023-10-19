package com.danaga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.entity.Statistic;
import com.danaga.repository.StatisticRepository;

@Service
public class StatisticServiceImpl implements StatisticService{
	@Autowired
	private StatisticRepository statisticRepository;
	
	@Override
	public List<Statistic> Statistics() {
		List<Statistic> statisticList = statisticRepository.findAll();
		return statisticList;
	}
	
	@Override
	public Statistic updateAt(String findDate) {
		Long SalesTotQty = statisticRepository.countTotSalesAt(findDate);
		Long SalesRevenue = statisticRepository.countTotRevenueAt(findDate);
		Long NewMember = statisticRepository.countNewMembersAt(findDate);
		Statistic updatedStatistic = Statistic.builder().id(findDate).dailySalesTotQty(SalesTotQty).dailySalesRevenue(SalesRevenue).dailyNewMember(NewMember).build();
		return updatedStatistic;
	}
	
	//오래걸림 주의, 매 00시 실행
	@Override
	public List<Statistic> updateAll() {
		List<Statistic> statisticList = statisticRepository.findAll();
		for (Statistic statistic : statisticList) {
			String findDate = statistic.getId();
			updateAt(findDate);
		}
		statisticList = statisticRepository.findAll();
		return statisticList;
	}
	
}
